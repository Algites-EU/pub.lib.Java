package eu.algites.lib.common.version;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * <p>
 * Title: {@link AItcVersionComparatorTest}
 * </p>
 * <p>
 * Description: TestNG tests for {@link AIsVersionTokenizer} and {@link AIsVersionComparator}.
 * </p>
 *
 * @author linhart1
 * @date 26.01.26
 */
public class AItcVersionComparatorTest {

	@Test
	public void testTokenizerSplitsAlphaNumericAndSeparators() {
		AIcVersion locVersion = new AIcVersion("1.0-rc1+build.7");

		Assert.assertTrue(locVersion.getTokens().size() >= 3, "Token count must be non-trivial");
		Assert.assertEquals(locVersion.getTokens().get(0).getTokenType(), AInVersionTokenType.ALPHANUMERIC, "First token must be alphanumeric");
		Assert.assertEquals(locVersion.getTokens().get(0).getText(), "1", "First alphanumeric token must be '1'");
	}

	@Test
	public void testMavenLikeReleaseBeatsRc() {
		AIcVersion locRelease = new AIcVersion("1.0");
		AIcVersion locRc = new AIcVersion("1.0-rc1");

		int locCmp = AIsVersionComparator.compare(locRelease, locRc, AInBuiltinVersionScheme.MAVEN);
		Assert.assertTrue(locCmp > 0, "Release must be greater than RC");
	}

	@Test
	public void testMavenLikeSnapshotIsLower() {
		AIcVersion locSnapshot = new AIcVersion("1.0-SNAPSHOT");
		AIcVersion locRelease = new AIcVersion("1.0");

		int locCmp = AIsVersionComparator.compare(locSnapshot, locRelease, AInBuiltinVersionScheme.MAVEN);
		Assert.assertTrue(locCmp < 0, "Snapshot must be lower than release");
	}

	@Test
	public void testMavenLikeNumericComparesNumerically() {
		AIcVersion locA = new AIcVersion("1.10");
		AIcVersion locB = new AIcVersion("1.2");

		int locCmp = AIsVersionComparator.compare(locA, locB, AInBuiltinVersionScheme.MAVEN);
		Assert.assertTrue(locCmp > 0, "1.10 must be greater than 1.2");
	}

	@Test
	public void testSemVerIgnoresBuildMetadataForPrecedence() {
		AIcVersion locA = new AIcVersion("1.2.3+build.7");
		AIcVersion locB = new AIcVersion("1.2.3+build.8");

		int locCmp = AIsVersionComparator.compare(locA, locB, AInBuiltinVersionScheme.SEMVER);
		Assert.assertEquals(locCmp, 0, "Build metadata must be ignored for precedence");
	}

	@Test
	public void testCompareDelegatesToComparatorViaMode() {
		AIcVersion locA = new AIcVersion("1.0");
		AIcVersion locB = new AIcVersion("2.0");

		AIiVersionComparator locComparator = (aLeft, aRight) -> 123;
		AIiVersionScheme locMode = new AIcCustomVersionScheme("test", locComparator);

		int locCmp = AIsVersionComparator.compare(locA, locB, locMode);
		Assert.assertEquals(locCmp, 123, "compare(left,right,mode) must delegate to mode.versionComparator()");
	}

	@Test
	public void testCompareDelegatesToProvidedComparator() {
		AIcVersion locA = new AIcVersion("1.0");
		AIcVersion locB = new AIcVersion("2.0");

		AIiVersionComparator locComparator = (aLeft, aRight) -> -456;

		int locCmp = AIsVersionComparator.compare(locA, locB, locComparator);
		Assert.assertEquals(locCmp, -456, "compare(left,right,comparator) must delegate to comparator");
	}

	@Test
	public void testAIcVersionCompareToDelegatesToHandlingModeComparator() {
		AIcVersion locA = new AIcVersion("1.0");
		AIcVersion locB = new AIcVersion("2.0");

		AIiVersionComparator locComparator = (aLeft, aRight) -> 77;
		AIiVersionScheme locMode = new AIcCustomVersionScheme("test2", locComparator);

		int locCmp = locA.compareTo(locB, locMode);
		Assert.assertEquals(locCmp, 77, "AIcVersion.compareTo(other, mode) must delegate to mode.versionComparator()");
	}
}
