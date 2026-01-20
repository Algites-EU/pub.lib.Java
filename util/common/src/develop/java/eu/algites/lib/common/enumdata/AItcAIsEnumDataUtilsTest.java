package eu.algites.lib.common.enumdata;

import java.util.List;

import org.gradle.internal.impldep.org.apache.commons.lang3.function.TriFunction;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests for {@link AIsEnumDataUtils}.
 *
 * <p>
 * The tests use a small test enum-data-type with the following UID layout:
 * {@code <origin>:<namespace>:<classifier>:<fileType>}.
 * </p>
 */
public class AItcAIsEnumDataUtilsTest {

	private static final AIiGloballyUniqueEnumDataType<AIcTestUidPartsRecord> TEST_ENUM_DATA_TYPE = new AIcTestEnumDataType();

	/**
	 * Test record used as a parsed representation for the test UID format.
	 *
	 * @param origin origin (builtin/custom)
	 * @param namespace namespace part (empty for builtin)
	 * @param classifier optional classifier
	 * @param fileType required file type
	 */
	public record AIcTestUidPartsRecord(
			AInEnumDataOrigin origin,
			String namespace,
			String classifier,
			String fileType
	) implements AIiUidPartsRecord { }

	/**
	 * Test enum-data-type used by tests.
	 */
	private static final class AIcTestEnumDataType implements AIiGloballyUniqueEnumDataType<AIcTestUidPartsRecord> {

		private static final List<AIiUidPartMetadata> SPECIFIC_UID_PARTS_METADATA = List.of(
				new AIrUidPartMetadata("__unused__", false),
				new AIrUidPartMetadata("classifier", false),
				new AIrUidPartMetadata("fileType", true)
		);

		@Override
		public TriFunction<AIiGloballyUniqueEnumDataType, String, List<String>, AIcTestUidPartsRecord> getUidRecordConstructor() {
			return (final AIiGloballyUniqueEnumDataType aEnumDataType,
					final String aUid,
					final List<String> aParts) -> new AIcTestUidPartsRecord(
					AInEnumDataOrigin.getByCodeOrThrow(aParts.get(AIsEnumDataUtils.ORIGIN_CLASS_UID_POSITION)),
					aParts.get(AIsEnumDataUtils.NAMESPACE_UID_POSITION),
					aParts.get(AIsEnumDataUtils.LAST_UID_HEADER_PART_POSITION + 1),
					aParts.get(AIsEnumDataUtils.LAST_UID_HEADER_PART_POSITION + 2)
			);
		}

		@Override
		public List<AIiUidPartMetadata> getSpecificUidPartsMetadata() {
			return SPECIFIC_UID_PARTS_METADATA;
		}

		@Override
		public int getUidPartCount() {
			final int locSpecificPartsCount = getSpecificUidPartsMetadata().size() - 1;
			return locSpecificPartsCount + 2;
		}
	}

	@Test
	public void testCreateBuiltinUidAndParseRoundtrip() {
		final List<String> locSpecificUidParts = List.of("", "jar");
		final List<AIiUidPartMetadata> locSpecificUidPartsMetadata = List.of(
				new AIrUidPartMetadata("classifier", false),
				new AIrUidPartMetadata("fileType", true)
		);
		final String locUid = AIsEnumDataUtils.createBuiltinUid(locSpecificUidParts, locSpecificUidPartsMetadata);
		Assert.assertEquals(locUid, "builtin:::jar");

		final AIcTestUidPartsRecord locParts = AIsEnumDataUtils.parseUid(TEST_ENUM_DATA_TYPE, locUid);
		Assert.assertEquals(locParts.origin(), AInEnumDataOrigin.BUILTIN);
		Assert.assertEquals(locParts.namespace(), "");
		Assert.assertEquals(locParts.classifier(), "");
		Assert.assertEquals(locParts.fileType(), "jar");
	}

	@Test
	public void testGetOriginAndNamespace() {
		final String locUid = "builtin:::jar";
		final AInEnumDataOrigin locOrigin = AIsEnumDataUtils.getOrigin(TEST_ENUM_DATA_TYPE, locUid);
		final String locNamespace = AIsEnumDataUtils.getNamespace(TEST_ENUM_DATA_TYPE, locUid);
		Assert.assertEquals(locOrigin, AInEnumDataOrigin.BUILTIN);
		Assert.assertEquals(locNamespace, "");
	}

	@Test
	public void testBuiltinNamespaceMustBeEmpty() {
		final String locInvalidUid = "builtin:eu.algites::jar";
		Assert.assertFalse(AIsEnumDataUtils.isValidOutputTypeUid(TEST_ENUM_DATA_TYPE, locInvalidUid));
		Assert.expectThrows(IllegalArgumentException.class, () -> AIsEnumDataUtils.validateUid(TEST_ENUM_DATA_TYPE, locInvalidUid));
	}

	@Test
	public void testCustomNamespaceMustBeNonBlank() {
		final String locValidUid = "custom:eu.algites::jar";
		Assert.assertTrue(AIsEnumDataUtils.isValidOutputTypeUid(TEST_ENUM_DATA_TYPE, locValidUid));
		final AIcTestUidPartsRecord locParts = AIsEnumDataUtils.parseUid(TEST_ENUM_DATA_TYPE, locValidUid);
		Assert.assertEquals(locParts.origin(), AInEnumDataOrigin.CUSTOM);
		Assert.assertEquals(locParts.namespace(), "eu.algites");

		final String locInvalidUid = "custom:::jar";
		Assert.assertFalse(AIsEnumDataUtils.isValidOutputTypeUid(TEST_ENUM_DATA_TYPE, locInvalidUid));
		Assert.expectThrows(IllegalArgumentException.class, () -> AIsEnumDataUtils.validateUid(TEST_ENUM_DATA_TYPE, locInvalidUid));
	}

	@Test
	public void testInvalidCharactersAreRejected() {
		final String locInvalidUid = "builtin:::ja$r";
		Assert.assertFalse(AIsEnumDataUtils.isValidOutputTypeUid(TEST_ENUM_DATA_TYPE, locInvalidUid));
		Assert.expectThrows(IllegalArgumentException.class, () -> AIsEnumDataUtils.validateUid(TEST_ENUM_DATA_TYPE, locInvalidUid));
	}

	@Test
	public void testSplitUidIntoPartsRejectsWrongSegmentCount() {
		final String locInvalidUid = "builtin::jar";
		Assert.expectThrows(IllegalArgumentException.class, () -> AIsEnumDataUtils.splitUidIntoParts(TEST_ENUM_DATA_TYPE, locInvalidUid));
	}
}
