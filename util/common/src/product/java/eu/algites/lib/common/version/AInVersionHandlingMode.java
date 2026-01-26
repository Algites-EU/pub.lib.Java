package eu.algites.lib.common.version;

/**
 * <p>
 * Title: {@link AInVersionHandlingMode}
 * </p>
 * <p>
 * Description: Common built-in handling modes defining how version precedence is computed.
 * </p>
 * <p>
 * Extendability pattern:
 * </p>
 * <ul>
 *   <li>Create a new {@link AIiVersionComparator} implementation (e.g. {@code AIcMyStyleVersionComparator}).</li>
 *   <li>Either add a new enum constant here, or use {@link AIcCustomVersionHandlingMode} to avoid enum changes.</li>
 *   <li>Use {@link AIsVersionComparator#compare(AIcVersion, AIcVersion, AIiVersionHandlingMode)} everywhere.</li>
 * </ul>
 * <ul>
 *   <li>{@link #MAVEN}: Practical Maven/Gradle-friendly ordering, tolerant to common patterns.</li>
 *   <li>{@link #SEMVER}: Semantic Versioning precedence (build metadata after '+' ignored for precedence).</li>
 *   <li>{@link #CALVER}: Example calendar-versioning comparator.</li>
 * </ul>
 *
 * @author linhart1
 * @date 26.01.26
 */
public enum AInVersionHandlingMode implements AIiVersionHandlingMode {

	MAVEN("maven", new AIcMavenLikeVersionComparator()),
	SEMVER("semver", new AIcSemverLikeVersionComparator()),
	CALVER("calver", new AIcCalverLikeVersionComparator());

	private final String code;

	private final AIiVersionComparator versionComparator;

	AInVersionHandlingMode(final String aCode, final AIiVersionComparator aVersionComparator) {
		code = aCode;
		versionComparator = aVersionComparator;
	}

	@Override
	public String code() {
		return code;
	}

	@Override
	public AIiVersionComparator versionComparator() {
		return versionComparator;
	}
}
