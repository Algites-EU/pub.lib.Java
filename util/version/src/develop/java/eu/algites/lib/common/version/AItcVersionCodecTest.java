package eu.algites.lib.common.version;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * <p>
 * Title: {@link AItcVersionCodecTest}
 * </p>
 * <p>
 * Description: Tests demonstrating {@link AIiVersionCodec} and {@link AIiVersionFormatter} usage.
 * </p>
 *
 * @author linhart1
 * @date 28.01.26
 */
public class AItcVersionCodecTest {

	@Test
	public void testNormalizeOmitsBuildWhenConfigured() {
		AIiVersionScheme locScheme = new AIcCustomVersionScheme(
				"custom-omit-build",
				new AIcMavenLikeVersionComparator(),
				true,
				"+",
				AInVersionBuildComparisonPolicy.IGNORE,
				AInVersionBuildFormatPolicy.OMIT
		);

		String locNormalized = locScheme.normalizeVersionText("1.2.3+45");

		Assert.assertEquals(locNormalized, "1.2.3", "Build part must be omitted when policy is OMIT");
	}

	@Test
	public void testSemverSchemeEmitsBuildAndKeepsText() {
		AIiVersionScheme locScheme = AInBuiltinVersionScheme.SEMVER_DEFAULT;

		String locNormalized = locScheme.normalizeVersionText("1.2.3+45");

		Assert.assertEquals(locNormalized, "1.2.3+45", "SemVer scheme must keep build metadata in formatted output");
	}

	@Test
	public void testNormalizeMapsBuildToQualifier() {
		AIiVersionScheme locScheme = new AIcCustomVersionScheme(
				"custom-map-build",
				new AIcSemverLikeVersionComparator(),
				true,
				"+",
				AInVersionBuildComparisonPolicy.IGNORE,
				AInVersionBuildFormatPolicy.MAP_TO_QUALIFIER
		);

		String locNormalizedPlain = locScheme.normalizeVersionText("1.2.3+7");
		Assert.assertEquals(locNormalizedPlain, "1.2.3-build.7", "Build part must be mapped into qualifier section");

		String locNormalizedWithQualifier = locScheme.normalizeVersionText("1.2.3-rc1+7");
		Assert.assertEquals(locNormalizedWithQualifier, "1.2.3-rc1.build.7", "Build part must be appended as additional qualifier token");
	}
}
