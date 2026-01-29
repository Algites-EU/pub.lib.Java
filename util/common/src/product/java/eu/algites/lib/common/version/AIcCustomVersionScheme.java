package eu.algites.lib.common.version;

import jakarta.annotation.Nonnull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * <p>
 * Title: {@link AIcCustomVersionScheme}
 * </p>
 * <p>
 * Description: Simple reusable implementation of {@link AIiVersionScheme}.
 * </p>
 * <p>
 * This class exists to make it trivial to introduce a new version scheme without changing
 * an enum. You can use it in production code (for registries/configuration) as well as in tests.
 * </p>
 *
 * @author linhart1
 * @date 26.01.26
 */
public final class AIcCustomVersionScheme implements AIiVersionScheme, Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Nonnull
	private final String code;

	@Nonnull
	private final AIiVersionComparator versionComparator;

	private final boolean versionBeforeBuild;

	@Nonnull
	private final String buildDelimiter;

	@Nonnull
	private final AInVersionBuildComparisonPolicy buildComparisonPolicy;

	@Nonnull
	private final AInVersionBuildFormatPolicy buildFormatPolicy;

	@Nonnull
	private final AIiVersionStructure versionStructure;

	@Nonnull
	private final AIiVersionFormatSpec versionFormatSpec;

	public AIcCustomVersionScheme(@Nonnull final String aCode, @Nonnull final AIiVersionComparator aVersionComparator) {
		this(
			aCode,
			aVersionComparator,
			true,
			"",
			AInVersionBuildComparisonPolicy.IGNORE,
			AInVersionBuildFormatPolicy.OMIT
		);
	}

	public AIcCustomVersionScheme(
			@Nonnull final String aCode,
			@Nonnull final AIiVersionComparator aVersionComparator,
			final boolean aVersionBeforeBuild,
			@Nonnull final String aBuildDelimiter,
			@Nonnull final AInVersionBuildComparisonPolicy aBuildComparisonPolicy,
			@Nonnull final AInVersionBuildFormatPolicy aBuildFormatPolicy
	) {
		code = Objects.requireNonNull(aCode, "Code must not be null");
		versionComparator = Objects.requireNonNull(aVersionComparator, "Comparator must not be null");
		versionBeforeBuild = aVersionBeforeBuild;
		buildDelimiter = Objects.requireNonNull(aBuildDelimiter, "Build delimiter must not be null");
		buildComparisonPolicy = Objects.requireNonNull(aBuildComparisonPolicy, "Build comparison policy must not be null");
		buildFormatPolicy = Objects.requireNonNull(aBuildFormatPolicy, "Build format policy must not be null");

		versionStructure = new AIcCustomVersionStructure(code + "-structure", versionBeforeBuild, buildDelimiter, buildComparisonPolicy);
		versionFormatSpec = new AIcCustomVersionFormatSpec(code + "-format", buildFormatPolicy);

		if (code.trim().isEmpty()) {
			throw new IllegalArgumentException("Code must not be empty");
		}
	}

	@Override
	@Nonnull
	public String code() {
		return code;
	}

	@Override
	@Nonnull
	public AIiVersionComparator versionComparator() {
		return versionComparator;
	}

	@Override
	public boolean versionBeforeBuild() {
		return versionBeforeBuild;
	}

	@Override
	@Nonnull
	public String buildDelimiter() {
		return buildDelimiter;
	}

	@Override
	@Nonnull
	public AInVersionBuildComparisonPolicy buildComparisonPolicy() {
		return buildComparisonPolicy;
	}

	@Override
	@Nonnull
	public AInVersionBuildFormatPolicy buildFormatPolicy() {
		return buildFormatPolicy;
	}
	@Override
	@Nonnull
	public AIiVersionStructure versionStructure() {
		return versionStructure;
	}

	@Override
	@Nonnull
	public AIiVersionFormatSpec versionFormatSpec() {
		return versionFormatSpec;
	}
}
