package eu.algites.lib.common.object.props.labels;

import eu.algites.lib.common.object.stringoutput.AIiStringOutputMode;
import eu.algites.lib.common.object.stringoutput.AIiStringOutputModeResolver;
import eu.algites.lib.common.object.stringoutput.AInStringOutputMode;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * <p>
 * Title: {@link AItsFieldLabelUtilsTest}
 * </p>
 * <p>
 * Description: TestNG tests for {@link AIsFieldLabelUtils}
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @author linhart1
 */
public class AItsFieldLabelUtilsTest {

	@Test
	public void testFindLabelUsesMappedLabelsForExplicitMode() {
		String locLabel = AIsFieldLabelUtils.findLabel(
				AIcTestMappedLabels.class,
				"myProperty",
				AInStringOutputMode.USER
		);

		Assert.assertEquals(locLabel, "MyUserPropertyLabel", "Mapped label for USER must be returned");
	}

	@Test
	public void testFindLabelFallsBackToFieldNameIfNoAnnotation() {
		String locLabel = AIsFieldLabelUtils.findLabel(
				AIcTestNoAnnotation.class,
				"myProperty",
				AInStringOutputMode.USER
		);

		Assert.assertEquals(locLabel, "myProperty", "Without annotation the field name must be returned");
	}

	@Test
	public void testFindLabelUsesLabelResolverIfPresent() {
		String locLabel = AIsFieldLabelUtils.findLabel(
				AIcTestLabelResolver.class,
				"myProperty",
				AInStringOutputMode.USER
		);

		Assert.assertEquals(locLabel, "ResolvedLabel", "Label resolver must override mapping");
	}

	@Test
	public void testFindLabelUsesModeResolverWhenExplicitModeIsNull() {
		String locLabel = AIsFieldLabelUtils.findLabel(
				AIcTestModeResolverWithMapping.class,
				"myProperty",
				null
		);

		Assert.assertEquals(locLabel, "MyUserPropertyLabel", "Mode resolver should drive the mapping");
	}

	@Test
	public void testFindLabelFindsInheritedFieldLabelInParent() {
		String locLabel = AIsFieldLabelUtils.findLabel(
				AIcChildWithoutFields.class,
				"myProperty",
				AInStringOutputMode.USER
		);

		Assert.assertEquals(locLabel, "ParentUserLabel", "Inherited field label should be resolved from parent");
	}

	@Test
	public void testFindLabelPrefersChildShadowedFieldOverParentField() {
		String locLabel = AIsFieldLabelUtils.findLabel(
				AIcChildWithShadowedField.class,
				"myProperty",
				AInStringOutputMode.USER
		);

		Assert.assertEquals(locLabel, "ChildUserLabel", "Shadowed field in child should win over parent");
	}

	@Test
	public void testInvalidAnnotationCombinationThrowsException() {
		boolean locThrown = false;
		try {
			AIsFieldLabelUtils.findLabel(
					AIcTestInvalidAnnotation.class,
					"myProperty",
					AInStringOutputMode.USER
			);
		} catch (IllegalArgumentException locException) {
			locThrown = true;
		}

		Assert.assertTrue(locThrown, "Invalid annotation configuration must throw IllegalArgumentException");
	}

	private static final class AIcTestNoAnnotation {
		private String myProperty;
	}

	private static final class AIcTestMappedLabels {
		@AIaFieldLabel(labels = {
				@AIaFieldLabel.Entry(mode = AInStringOutputMode.USER, label = "MyUserPropertyLabel"),
				@AIaFieldLabel.Entry(mode = AInStringOutputMode.SYSTEM, label = "MySystemPropertyLabel")
		})
		private String myProperty;
	}

	private static final class AIcTestLabelResolver {
		@AIaFieldLabel(labelResolver = AIcConstantLabelResolver.class)
		private String myProperty;
	}

	private static final class AIcTestModeResolverWithMapping {
		@AIaFieldLabel(
				modeResolver = AIcUserModeResolver.class,
				labels = {
						@AIaFieldLabel.Entry(mode = AInStringOutputMode.USER, label = "MyUserPropertyLabel"),
						@AIaFieldLabel.Entry(mode = AInStringOutputMode.SYSTEM, label = "MySystemPropertyLabel")
				}
		)
		private String myProperty;
	}

	private static class AIcParentWithField {
		@AIaFieldLabel(labels = {
				@AIaFieldLabel.Entry(mode = AInStringOutputMode.USER, label = "ParentUserLabel")
		})
		private String myProperty;
	}

	private static final class AIcChildWithoutFields extends AIcParentWithField {
	}

	private static class AIcParentWithShadowedField {
		@AIaFieldLabel(labels = {
				@AIaFieldLabel.Entry(mode = AInStringOutputMode.USER, label = "ParentUserLabel")
		})
		private String myProperty;
	}

	private static final class AIcChildWithShadowedField extends AIcParentWithShadowedField {
		@AIaFieldLabel(labels = {
				@AIaFieldLabel.Entry(mode = AInStringOutputMode.USER, label = "ChildUserLabel")
		})
		private String myProperty;
	}

	private static final class AIcTestInvalidAnnotation {
		@AIaFieldLabel(
				labelResolver = AIcConstantLabelResolver.class,
				labels = {
						@AIaFieldLabel.Entry(mode = AInStringOutputMode.USER, label = "MyUserPropertyLabel")
				}
		)
		private String myProperty;
	}

	public static final class AIcConstantLabelResolver implements AIiFieldLabelResolver {
		@Override
		public String resolveLabel(Class<?> aOwnerClass, String aFieldName, AIiStringOutputMode aOutputMode) {
			return "ResolvedLabel";
		}
	}

	public static final class AIcUserModeResolver implements AIiStringOutputModeResolver {
		@Override
		public AIiStringOutputMode resolveMode() {
			return AInStringOutputMode.USER;
		}
	}


	@Test
	public void testFindLabelUsesMethodAnnotationOnGetter() {
		String locLabel = AIsFieldLabelUtils.findLabel(
				AIcTestMethodAnnotation.class,
				"myProperty",
				AInStringOutputMode.USER
		);

		Assert.assertEquals(locLabel, "GetterUserLabel", "Getter annotation must be used when field has no annotation");
	}

	@Test
	public void testFindLabelUsesRecordComponentAnnotation() {
		String locLabel = AIsFieldLabelUtils.findLabel(
				AIcTestRecordComponent.class,
				"myProperty",
				AInStringOutputMode.USER
		);

		Assert.assertEquals(locLabel, "RecordComponentUserLabel", "Record component annotation must be used for records");
	}

	@Test
	public void testFindLabelResolvesAnnotationFromInterfaceMethodForRecord() {
		String locLabel = AIsFieldLabelUtils.findLabel(
				AIcTestRecordImplementsInterface.class,
				"myProperty",
				AInStringOutputMode.USER
		);

		Assert.assertEquals(locLabel, "InterfaceUserLabel", "Interface method annotation must be resolved for record implementing the interface");
	}


@Test
public void testFindLabelResolvesInterfaceAnnotationEvenIfClassDeclaresUnannotatedGetter() {
	String locLabel = AIsFieldLabelUtils.findLabel(
			AIcTestClassImplementsInterfaceWithUnannotatedGetter.class,
			"myProperty",
			AInStringOutputMode.USER
	);

	Assert.assertEquals(locLabel, "InterfaceGetterUserLabel", "Interface getter annotation must be resolved even if class declares an unannotated getter");
}

	@Test
	public void testFindLabelFieldAnnotationOverridesGetterAnnotation() {
		String locLabel = AIsFieldLabelUtils.findLabel(
				AIcTestFieldOverridesMethod.class,
				"myProperty",
				AInStringOutputMode.USER
		);

		Assert.assertEquals(locLabel, "FieldUserLabel", "Field annotation must override getter annotation");
	}

	@Test
	public void testFindLabelRecordComponentAnnotationOverridesAccessorMethodAnnotation() {
		String locLabel = AIsFieldLabelUtils.findLabel(
				AIcTestRecordComponentOverridesMethod.class,
				"myProperty",
				AInStringOutputMode.USER
		);

		Assert.assertEquals(locLabel, "RecordComponentUserLabel", "Record component annotation must override accessor method annotation");
	}

	private static final class AIcTestMethodAnnotation {
		private String myProperty;

		@AIaFieldLabel(labels = {
				@AIaFieldLabel.Entry(mode = AInStringOutputMode.USER, label = "GetterUserLabel")
		})
		public String getMyProperty() {
			return myProperty;
		}
	}

	private record AIcTestRecordComponent(
			@AIaFieldLabel(labels = {
					@AIaFieldLabel.Entry(mode = AInStringOutputMode.USER, label = "RecordComponentUserLabel")
			})
			String myProperty
	) {
		/* Record as data carrier. */
	}

	private interface AIiTestInterfaceWithAnnotatedAccessor {
		@AIaFieldLabel(labels = {
				@AIaFieldLabel.Entry(mode = AInStringOutputMode.USER, label = "InterfaceUserLabel")
		})
		String myProperty();
	}

	private record AIcTestRecordImplementsInterface(
			String myProperty
	) implements AIiTestInterfaceWithAnnotatedAccessor {
		/* Record as data carrier. */
	}


private interface AIiTestInterfaceWithAnnotatedGetter {
	@AIaFieldLabel(labels = {
			@AIaFieldLabel.Entry(mode = AInStringOutputMode.USER, label = "InterfaceGetterUserLabel")
	})
	String getMyProperty();
}

private static final class AIcTestClassImplementsInterfaceWithUnannotatedGetter implements AIiTestInterfaceWithAnnotatedGetter {
	@Override
	public String getMyProperty() {
		return "value";
	}
}

	private static final class AIcTestFieldOverridesMethod {

		@AIaFieldLabel(labels = {
				@AIaFieldLabel.Entry(mode = AInStringOutputMode.USER, label = "FieldUserLabel")
		})
		private String myProperty;

		@AIaFieldLabel(labels = {
				@AIaFieldLabel.Entry(mode = AInStringOutputMode.USER, label = "GetterUserLabel")
		})
		public String getMyProperty() {
			return myProperty;
		}
	}

	private record AIcTestRecordComponentOverridesMethod(
			@AIaFieldLabel(labels = {
					@AIaFieldLabel.Entry(mode = AInStringOutputMode.USER, label = "RecordComponentUserLabel")
			})
			String myProperty
	) {
		@AIaFieldLabel(labels = {
				@AIaFieldLabel.Entry(mode = AInStringOutputMode.USER, label = "GetterUserLabel")
		})
		@Override
		public String myProperty() {
			return myProperty;
		}
	}



	@Test
	public void testRequirePropertyExistsDoesNotThrowForExistingPropertyOnField() {
	    AIsFieldLabelUtils.requirePropertyExists(
	            AIcTestMappedLabels.class,
	            "myProperty"
	    );
	}

	@Test
	public void testRequirePropertyExistsDoesNotThrowForExistingPropertyOnGetterOnly() {
	    AIsFieldLabelUtils.requirePropertyExists(
	            AIcTestGetterOnlyProperty.class,
	            "myProperty"
	    );
	}

	@Test
	public void testRequirePropertyExistsThrowsForUnknownProperty() {
	    boolean locThrown = false;
	    try {
	        AIsFieldLabelUtils.requirePropertyExists(
	                AIcTestMappedLabels.class,
	                "unknownProperty"
	        );
	    } catch (IllegalArgumentException locException) {
	        locThrown = true;
	    }
	    Assert.assertTrue(locThrown, "Unknown property must throw IllegalArgumentException");
	}

	private static final class AIcTestGetterOnlyProperty {
	    public String getMyProperty() {
	        return "value";
	    }
	}
}
