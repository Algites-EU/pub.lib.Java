package eu.algites.lib.common.object.props.labels;

import eu.algites.lib.common.object.stringoutput.AIiStringOutputMode;
import eu.algites.lib.common.object.stringoutput.AIiStringOutputModeResolver;
import eu.algites.lib.common.object.stringoutput.AInStringOutputMode;
import eu.algites.lib.common.object.stringoutput.AIsStringOutputUtils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Title: {@link AIsFieldLabelUtils}
 * </p>
 * <p>
 * Description: Utilities for the field labeling processing
 * </p>
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
		/* Record as data carrier. */
	}

	private record AIcFieldMetaNode(
			Map<String, AIcFieldMeta> declaredByField,
			AIcFieldMetaNode parentOrNull
	) {
		/* Record as data carrier. */
	}

	private static final ClassValue<AIcFieldMetaNode> CACHE = new ClassValue<>() {
		@Override
		protected AIcFieldMetaNode computeValue(Class<?> type) {
			AIcFieldMetaNode parentOrNull = null;

			Class<?> superclass = type.getSuperclass();
			if (superclass != null && superclass != Object.class) {
				parentOrNull = CACHE.get(superclass);
			}

			Map<String, AIcFieldMeta> declaredByField = new HashMap<>();

			for (Field f : type.getDeclaredFields()) {
				AIaFieldLabel ann = f.getAnnotation(AIaFieldLabel.class);
				if (ann == null) {
					continue;
				}

				validate(ann, f);

				AIcFieldMeta meta = buildMetaFromAnnotation(ann);
				declaredByField.put(f.getName(), meta);
			}

			return new AIcFieldMetaNode(Collections.unmodifiableMap(declaredByField), parentOrNull);
		}
	};

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
		Class<? extends AIiFieldLabelResolver> rc = ann.labelResolver();
		if (rc == null || rc == AIaFieldLabel.NoLabelResolver.class) {
			return null;
		}
		return newInstanceOrNull(rc);
	}

	private static AIiStringOutputModeResolver instantiateModeResolverIfAny(AIaFieldLabel ann) {
		Class<? extends AIiStringOutputModeResolver> rc = ann.modeResolver();
		if (rc == null || rc == AIaFieldLabel.NoModeResolver.class) {
			return null;
		}
		return newInstanceOrNull(rc);
	}

	private static <T> T newInstanceOrNull(Class<? extends T> cls) {
		try {
			return cls.getDeclaredConstructor().newInstance();
		} catch (ReflectiveOperationException ex) {
			return null;
		}
	}

	private static void validate(AIaFieldLabel aFieldLabelAnnotation, Field aField) {
		boolean hasLabels = aFieldLabelAnnotation.labels().length > 0;
		boolean hasLabelResolver = aFieldLabelAnnotation.labelResolver() != AIaFieldLabel.NoLabelResolver.class;

		int labelChoices = (hasLabels ? 1 : 0) + (hasLabelResolver ? 1 : 0);
		if (labelChoices > 1) {
			throw new IllegalArgumentException(
					"Field " + aField + ": choose only one of labels/labelResolver"
			);
		}

		boolean hasModeResolver = aFieldLabelAnnotation.modeResolver() != AIaFieldLabel.NoModeResolver.class;
		if (hasModeResolver && hasLabels) {
			/* This is allowed: modeResolver selects active mode, labels map mode->label. */
		}
	}

	private static AIcFieldMeta findMeta(Class<?> aOwnerClass, String aFieldName) {
		AIcFieldMetaNode node = CACHE.get(aOwnerClass);
		while (node != null) {
			AIcFieldMeta meta = node.declaredByField.get(aFieldName);
			if (meta != null) {
				return meta;
			}
			node = node.parentOrNull;
		}
		return null;
	}

	/**
	 * Example API for callers (toString code) to get a label for a field name.
	 * @param aOwnerClass class where the call is made
	 * @param aFieldName field name used
	 * @return label for the field name
	 */
	public static String findLabel(Class<?> aOwnerClass, String aFieldName) {
		return findLabel(aOwnerClass, aFieldName, null);
	}

	/**
	 * Example API for callers (toString code) to get a label for a field name.
	 * @param aOwnerClass class where the call is made
	 * @param aFieldName field name used
	 * @param aStringOutputMode to be used for label resolution
	 * @return label for the field name
	 */
	public static String findLabel(Class<?> aOwnerClass, String aFieldName, AIiStringOutputMode aStringOutputMode) {
		AIcFieldMeta meta = findMeta(aOwnerClass, aFieldName);
		if (meta == null) {
			return aFieldName;
		}

		AIiStringOutputMode resolvedMode = aStringOutputMode;
		if (resolvedMode == null && meta.modeResolverOrNull != null) {
			resolvedMode = meta.modeResolverOrNull.resolveMode();
		}
		if (resolvedMode == null) {
			resolvedMode = AIsStringOutputUtils.usedStringOutputMode();
		}

		if (meta.labelResolverOrNull != null) {
			String resolved = meta.labelResolverOrNull.resolveLabel(aOwnerClass, aFieldName, resolvedMode);
			if (resolved != null && !resolved.isBlank()) {
				return resolved;
			}
		}

		String mapped = meta.labelsByModeCode.get(resolvedMode.code());
		return (mapped != null && !mapped.isBlank()) ? mapped : aFieldName;
	}
}
