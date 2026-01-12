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
}
