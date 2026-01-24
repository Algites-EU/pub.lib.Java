package eu.algites.lib.common.object.props.labels;

import eu.algites.lib.common.object.stringoutput.AIiStringOutputMode;
import eu.algites.lib.common.object.stringoutput.AIiStringOutputModeResolver;
import eu.algites.lib.common.object.stringoutput.AInStringOutputMode;
import eu.algites.lib.common.object.stringoutput.AIsStringOutputUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * Title: {@link AIsFieldLabelUtils}
 * </p>
 * <p>
 * Description: Utilities for the field labeling processing.
 * Supports annotations declared on:
 * </p>
 * <ul>
 *   <li>fields</li>
 *   <li>methods (getters/accessors)</li>
 *   <li>record components</li>
 *   <li>interface methods (e.g., record implements interface)</li>
 * </ul>
 * <p>
 * In the presence of multiple annotations for the same property name, the following precedence is applied:
 * </p>
 * <ol>
 *   <li>record component annotation</li>
 *   <li>field annotation</li>
 *   <li>declared class method annotation</li>
 *   <li>interface method annotation</li>
 * </ol>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @author linhart1
 * @date 12.01.26 6:57
 */
public class AIsFieldLabelUtils {

	private record AIcFieldMeta(
			Map<String, String> labelsByModeCode,
			AIiFieldLabelResolver labelResolverOrNull,
			AIiStringOutputModeResolver modeResolverOrNull
	) {
		/* Record as uiddata carrier. */
	}

	private record AIcFieldMetaNode(
			Map<String, AIcFieldMeta> declaredByPropertyName,
			AIcFieldMetaNode parentOrNull
	) {
		/* Record as uiddata carrier. */
	}

	private static final ClassValue<AIcFieldMetaNode> CACHE = new ClassValue<>() {
		@Override
		protected AIcFieldMetaNode computeValue(Class<?> type) {
			AIcFieldMetaNode locParentOrNull = null;

			Class<?> locSuperclass = type.getSuperclass();
			if (locSuperclass != null && locSuperclass != Object.class) {
				locParentOrNull = CACHE.get(locSuperclass);
			}

			Map<String, AIcFieldMeta> locDeclaredByPropertyName = buildDeclaredMetaMap(type);

			return new AIcFieldMetaNode(Collections.unmodifiableMap(locDeclaredByPropertyName), locParentOrNull);
		}
	};

	private static Map<String, AIcFieldMeta> buildDeclaredMetaMap(Class<?> aOwnerClass) {
		Map<String, AIcFieldMeta> locDeclaredByPropertyName = new HashMap<>();

		/*
		 * Lowest precedence first: interface method annotations.
		 */
		collectFromInterfaces(aOwnerClass, locDeclaredByPropertyName);

		/*
		 * Class declared methods override interface method annotations.
		 */
		collectFromDeclaredMethods(aOwnerClass, locDeclaredByPropertyName);

		/*
		 * Fields override method-based annotations.
		 */
		collectFromDeclaredFields(aOwnerClass, locDeclaredByPropertyName);

		/*
		 * Record components override everything else.
		 */
		collectFromRecordComponents(aOwnerClass, locDeclaredByPropertyName);

		return locDeclaredByPropertyName;
	}

	private static void collectFromDeclaredFields(Class<?> aOwnerClass, Map<String, AIcFieldMeta> aDeclaredByPropertyName) {
		for (Field locField : aOwnerClass.getDeclaredFields()) {
			AIaFieldLabel locAnnotation = locField.getAnnotation(AIaFieldLabel.class);
			if (locAnnotation == null) {
				continue;
			}

			validate(locAnnotation, "Field " + locField);

			AIcFieldMeta locMeta = buildMetaFromAnnotation(locAnnotation);
			aDeclaredByPropertyName.put(locField.getName(), locMeta);
		}
	}

	private static void collectFromDeclaredMethods(Class<?> aOwnerClass, Map<String, AIcFieldMeta> aDeclaredByPropertyName) {
		for (Method locMethod : aOwnerClass.getDeclaredMethods()) {
			AIaFieldLabel locAnnotation = locMethod.getAnnotation(AIaFieldLabel.class);
			if (locAnnotation == null) {
				continue;
			}

			String locPropertyNameOrNull = resolvePropertyNameFromMethod(locMethod);
			if (locPropertyNameOrNull == null || locPropertyNameOrNull.isBlank()) {
				continue;
			}

			validate(locAnnotation, "Method " + locMethod);

			AIcFieldMeta locMeta = buildMetaFromAnnotation(locAnnotation);
			aDeclaredByPropertyName.put(locPropertyNameOrNull, locMeta);
		}
	}

	private static void collectFromRecordComponents(Class<?> aOwnerClass, Map<String, AIcFieldMeta> aDeclaredByPropertyName) {
		if (!aOwnerClass.isRecord()) {
			return;
		}

		RecordComponent[] locComponents = aOwnerClass.getRecordComponents();
		if (locComponents == null || locComponents.length == 0) {
			return;
		}

		for (RecordComponent locComponent : locComponents) {
			AIaFieldLabel locAnnotation = locComponent.getAnnotation(AIaFieldLabel.class);
			if (locAnnotation == null) {
				continue;
			}

			validate(locAnnotation, "Record component " + locComponent);

			AIcFieldMeta locMeta = buildMetaFromAnnotation(locAnnotation);
			aDeclaredByPropertyName.put(locComponent.getName(), locMeta);
		}
	}

	private static void collectFromInterfaces(Class<?> aOwnerClass, Map<String, AIcFieldMeta> aDeclaredByPropertyName) {
		Set<Class<?>> locVisitedInterfaces = new HashSet<>();
		for (Class<?> locInterface : aOwnerClass.getInterfaces()) {
			collectFromInterfaceRecursive(locInterface, locVisitedInterfaces, aDeclaredByPropertyName);
		}
	}

	private static void collectFromInterfaceRecursive(
			Class<?> aInterface,
			Set<Class<?>> aVisitedInterfaces,
			Map<String, AIcFieldMeta> aDeclaredByPropertyName
	) {
		if (aInterface == null || !aInterface.isInterface()) {
			return;
		}
		if (!aVisitedInterfaces.add(aInterface)) {
			return;
		}

		for (Method locMethod : aInterface.getMethods()) {
			AIaFieldLabel locAnnotation = locMethod.getAnnotation(AIaFieldLabel.class);
			if (locAnnotation == null) {
				continue;
			}

			String locPropertyNameOrNull = resolvePropertyNameFromMethod(locMethod);
			if (locPropertyNameOrNull == null || locPropertyNameOrNull.isBlank()) {
				continue;
			}

			validate(locAnnotation, "Interface method " + locMethod);

			AIcFieldMeta locMeta = buildMetaFromAnnotation(locAnnotation);

			/*
			 * Lowest precedence: only set if absent, because later collectors override.
			 */
			aDeclaredByPropertyName.putIfAbsent(locPropertyNameOrNull, locMeta);
		}

		for (Class<?> locParentInterface : aInterface.getInterfaces()) {
			collectFromInterfaceRecursive(locParentInterface, aVisitedInterfaces, aDeclaredByPropertyName);
		}
	}

	private static String resolvePropertyNameFromMethod(Method aMethod) {
		if (aMethod == null) {
			return null;
		}
		if (aMethod.getParameterCount() != 0) {
			return null;
		}
		if (aMethod.getReturnType() == Void.TYPE) {
			return null;
		}

		String locName = aMethod.getName();
		if (locName == null || locName.isBlank()) {
			return null;
		}

		if (locName.startsWith("get") && locName.length() > 3) {
			return decapitalize(locName.substring(3));
		}
		if (locName.startsWith("is") && locName.length() > 2) {
			Class<?> locReturnType = aMethod.getReturnType();
			if (locReturnType == boolean.class || locReturnType == Boolean.class) {
				return decapitalize(locName.substring(2));
			}
		}

		/*
		 * Record accessor or property-like method name.
		 */
		return locName;
	}

	private static String decapitalize(String aValue) {
		if (aValue == null || aValue.isBlank()) {
			return aValue;
		}
		char locFirst = aValue.charAt(0);
		char locLower = Character.toLowerCase(locFirst);
		if (locFirst == locLower) {
			return aValue;
		}
		return locLower + aValue.substring(1);
	}

	private static AIcFieldMeta buildMetaFromAnnotation(AIaFieldLabel ann) {
		Map<String, String> labels = new HashMap<>();

		for (AIaFieldLabel.Entry e : ann.labels()) {
			String modeCode = resolveModeCode(e);
			if (modeCode != null && !modeCode.isBlank()) {
				labels.put(modeCode, e.label());
			}
		}

		AIiFieldLabelResolver labelResolver = instantiateLabelResolverIfAny(ann);
		AIiStringOutputModeResolver modeResolver = instantiateModeResolverIfAny(ann);

		return new AIcFieldMeta(
				Collections.unmodifiableMap(labels),
				labelResolver,
				modeResolver
		);
	}

	private static String resolveModeCode(AIaFieldLabel.Entry e) {
		if (e.mode() != AInStringOutputMode.DEFAULT) {
			return e.mode().code();
		}
		if (e.modeCode() != null && !e.modeCode().isBlank()) {
			return e.modeCode();
		}
		return null;
	}

	private static AIiFieldLabelResolver instantiateLabelResolverIfAny(AIaFieldLabel ann) {
		Class<? extends AIiFieldLabelResolver> locResolverClass = ann.labelResolver();
		if (locResolverClass == null || locResolverClass == AIaFieldLabel.NoLabelResolver.class) {
			return null;
		}
		return newInstanceOrNull(locResolverClass);
	}

	private static AIiStringOutputModeResolver instantiateModeResolverIfAny(AIaFieldLabel ann) {
		Class<? extends AIiStringOutputModeResolver> locResolverClass = ann.modeResolver();
		if (locResolverClass == null || locResolverClass == AIaFieldLabel.NoModeResolver.class) {
			return null;
		}
		return newInstanceOrNull(locResolverClass);
	}

	private static <T> T newInstanceOrNull(Class<? extends T> cls) {
		try {
			return cls.getDeclaredConstructor().newInstance();
		} catch (ReflectiveOperationException ex) {
			return null;
		}
	}

	private static void validate(AIaFieldLabel aFieldLabelAnnotation, String aElementDescription) {
		boolean locHasLabels = aFieldLabelAnnotation.labels().length > 0;
		boolean locHasLabelResolver = aFieldLabelAnnotation.labelResolver() != AIaFieldLabel.NoLabelResolver.class;

		int locLabelChoices = (locHasLabels ? 1 : 0) + (locHasLabelResolver ? 1 : 0);
		if (locLabelChoices > 1) {
			throw new IllegalArgumentException(
					aElementDescription + ": choose only one of labels/labelResolver"
			);
		}

		boolean locHasModeResolver = aFieldLabelAnnotation.modeResolver() != AIaFieldLabel.NoModeResolver.class;
		if (locHasModeResolver && locHasLabels) {
			/*
			 * This is allowed: modeResolver selects active mode, labels map mode->label.
			 */
		}
	}

	private static AIcFieldMeta findMeta(Class<?> aOwnerClass, String aPropertyName) {
		AIcFieldMetaNode locNode = CACHE.get(aOwnerClass);
		while (locNode != null) {
			AIcFieldMeta locMeta = locNode.declaredByPropertyName.get(aPropertyName);
			if (locMeta != null) {
				return locMeta;
			}
			locNode = locNode.parentOrNull;
		}
		return null;
	}

	/**
	 * Example API for callers (toString code) to get a label for a field name.
	 * @param aOwnerClass class where the call is made
	 * @param aPropertyName property name used (field/record component/getter)
	 * @return label for the property name
	 */
	public static String findLabel(Class<?> aOwnerClass, String aPropertyName) {
		return findLabel(aOwnerClass, aPropertyName, null);
	}

	/**
	 * Example API for callers (toString code) to get a label for a field name.
	 * @param aOwnerClass class where the call is made
	 * @param aPropertyName property name used (field/record component/getter)
	 * @param aStringOutputMode to be used for label resolution
	 * @return label for the property name
	 */
	public static String findLabel(Class<?> aOwnerClass, String aPropertyName, AIiStringOutputMode aStringOutputMode) {
		AIcFieldMeta locMeta = findMeta(aOwnerClass, aPropertyName);
		if (locMeta == null) {
			return aPropertyName;
		}

		AIiStringOutputMode locResolvedMode = aStringOutputMode;
		if (locResolvedMode == null && locMeta.modeResolverOrNull != null) {
			locResolvedMode = locMeta.modeResolverOrNull.resolveMode();
		}
		if (locResolvedMode == null) {
			locResolvedMode = AIsStringOutputUtils.usedStringOutputMode();
		}

		if (locMeta.labelResolverOrNull != null) {
			String locResolved = locMeta.labelResolverOrNull.resolveLabel(aOwnerClass, aPropertyName, locResolvedMode);
			if (locResolved != null && !locResolved.isBlank()) {
				return locResolved;
			}
		}

		String locMapped = locMeta.labelsByModeCode.get(locResolvedMode.code());
		return (locMapped != null && !locMapped.isBlank()) ? locMapped : aPropertyName;
	}




	/**
	 * Validates that the given property name exists on the owner type.
	 * <p>
	 * The property is considered present if at least one of the following is found:
	 * </p>
	 * <ul>
	 *   <li>a field with the same name (searched on the class hierarchy)</li>
	 *   <li>a record component with the same name</li>
	 *   <li>a no-arg, non-void method that maps to the property name (record accessor or JavaBean getter), including interface methods</li>
	 * </ul>
	 *
	 * @param aOwnerClass the owning type
	 * @param aPropertyName property name (field/record component/accessor/getter derived name)
	 * @throws IllegalArgumentException when the property name is blank or not present on the owner type
	 */
	public static void requirePropertyExists(Class<?> aOwnerClass, String aPropertyName) {
	    if (aOwnerClass == null) {
	        throw new IllegalArgumentException("Owner class must not be null");
	    }
	    if (aPropertyName == null || aPropertyName.isBlank()) {
	        throw new IllegalArgumentException("Property name must not be blank");
	    }
	    if (!isPropertyPresent(aOwnerClass, aPropertyName)) {
	        throw new IllegalArgumentException("Unknown property '" + aPropertyName + "' for " + aOwnerClass.getName());
	    }
	}

	/**
	 * Checks whether the given property name exists on the owner type.
	 *
	 * @param aOwnerClass the owning type
	 * @param aPropertyName property name (field/record component/accessor/getter derived name)
	 * @return true if the property exists on the owner type; false otherwise
	 */
	public static boolean isPropertyPresent(Class<?> aOwnerClass, String aPropertyName) {
	    if (aOwnerClass == null || aPropertyName == null || aPropertyName.isBlank()) {
	        return false;
	    }

	    if (containsRecordComponentName(aOwnerClass, aPropertyName)) {
	        return true;
	    }
	    if (containsFieldNameInHierarchy(aOwnerClass, aPropertyName)) {
	        return true;
	    }
	    return containsPropertyMethodName(aOwnerClass, aPropertyName);
	}

	private static boolean containsRecordComponentName(Class<?> aOwnerClass, String aPropertyName) {
	    if (!aOwnerClass.isRecord()) {
	        return false;
	    }

	    RecordComponent[] locComponents = aOwnerClass.getRecordComponents();
	    if (locComponents == null || locComponents.length == 0) {
	        return false;
	    }

	    for (RecordComponent locComponent : locComponents) {
	        if (aPropertyName.equals(locComponent.getName())) {
	            return true;
	        }
	    }
	    return false;
	}

	private static boolean containsFieldNameInHierarchy(Class<?> aOwnerClass, String aPropertyName) {
	    Class<?> locType = aOwnerClass;
	    while (locType != null && locType != Object.class) {
	        try {
	            Field locField = locType.getDeclaredField(aPropertyName);
	            if (locField != null) {
	                return true;
	            }
	        } catch (NoSuchFieldException locException) {
	            /* Ignore. */
	        }
	        locType = locType.getSuperclass();
	    }
	    return false;
	}

	private static boolean containsPropertyMethodName(Class<?> aOwnerClass, String aPropertyName) {
	    for (Method locMethod : aOwnerClass.getMethods()) {
	        if (locMethod.getDeclaringClass() == Object.class) {
	            continue;
	        }
	        String locResolvedPropertyNameOrNull = resolvePropertyNameFromMethod(locMethod);
	        if (aPropertyName.equals(locResolvedPropertyNameOrNull)) {
	            return true;
	        }
	    }
	    return false;
	}
}
