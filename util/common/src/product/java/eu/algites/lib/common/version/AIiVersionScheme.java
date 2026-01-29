package eu.algites.lib.common.version;

import eu.algites.lib.common.enums.AIiEnumItem;
import jakarta.annotation.Nonnull;

import java.util.Objects;

/**
 * <p>
 * Title: {@link AIiVersionScheme}
 * </p>
 * <p>
 * Description: General interface for version comparison schemes.
 * </p>
 * <p>
 * Each scheme provides a {@link AIiVersionComparator} implementation that defines
 * how {@link AIcVersion} instances are compared.
 * </p>
 * <p>
 * In addition, a scheme can expose configuration describing how build-identification is represented
 * (delimiter, ordering) and whether it participates in precedence.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @author linhart1
 * @date 26.01.26 22:45
 */
public interface AIiVersionScheme extends AIiEnumItem {

	/**
	 * Provides the comparator implementing the given scheme.
	 *
	 * @return comparator for this scheme
	 */
	AIiVersionComparator versionComparator();

	/**
	 * @return true if the precedence-relevant version part is placed before the build-identification part
	 */
	default boolean versionBeforeBuild() {
		return true;
	}

	/**
	 * Build delimiter separating the precedence-relevant version part and the build-identification part.
	 * <p>
	 * An empty delimiter means that the scheme does not use a dedicated build-identification section.
	 * </p>
	 *
	 * @return build delimiter or empty string
	 */
	@Nonnull
	default String buildDelimiter() {
		return "";
	}

	/**
	 * @return policy defining whether and how the build-identification part participates in comparison
	 */
	@Nonnull
	default AInVersionBuildComparisonPolicy buildComparisonPolicy() {
		return AInVersionBuildComparisonPolicy.IGNORE;
	}

	/**
	 * @return policy defining how the build-identification part is formatted when serializing versions
	 */
	@Nonnull
	default AInVersionBuildFormatPolicy buildFormatPolicy() {
		return AInVersionBuildFormatPolicy.OMIT;
	}

	/**
	 * Splits the provided version text into its precedence-relevant part and its build-identification part.
	 * <p>
	 * The split is based on {@link #buildDelimiter()} and {@link #versionBeforeBuild()}.
	 * </p>
	 *
	 * @param aVersionText raw version text
	 * @return split parts
	 */
	@Nonnull
	default AIrVersionSchemeTextParts splitVersionAndBuildText(@Nonnull final String aVersionText) {
		Objects.requireNonNull(aVersionText, "Version text must not be null");

		String locDelimiter = buildDelimiter();
		if (locDelimiter.isEmpty()) {
			return new AIrVersionSchemeTextParts(aVersionText, "");
		}

		int locIndex = aVersionText.indexOf(locDelimiter);
		if (locIndex < 0) {
			return new AIrVersionSchemeTextParts(aVersionText, "");
		}

		String locFirst = aVersionText.substring(0, locIndex);
		String locSecond = aVersionText.substring(locIndex + locDelimiter.length());

		if (versionBeforeBuild()) {
			return new AIrVersionSchemeTextParts(locFirst, locSecond);
		}
		return new AIrVersionSchemeTextParts(locSecond, locFirst);
	}

	/**
	 * Provides the structural interpretation for this scheme.
	 * <p>
	 * Default implementation derives the structure from legacy methods on this interface.
	 * </p>
	 *
	 * @return version structure
	 */
	@Nonnull
	default AIiVersionStructure versionStructure() {
		return new AIcCustomVersionStructure(
				code() + "-structure",
				versionBeforeBuild(),
				buildDelimiter(),
				buildComparisonPolicy()
		);
	}

	/**
	 * Provides the format specification for this scheme.
	 * <p>
	 * Default implementation derives the specification from legacy methods on this interface.
	 * </p>
	 *
	 * @return version format specification
	 */
	@Nonnull
	default AIiVersionFormatSpec versionFormatSpec() {
		return new AIcCustomVersionFormatSpec(code() + "-format", buildFormatPolicy());
	}

	/**
	 * @return formatter to render versions to strings for this scheme
	 */
	@Nonnull
	default AIiVersionFormatter versionFormatter() {
		return AIcDefaultVersionFormatter.INSTANCE;
	}

	/**
	 * @return codec to parse and format versions for this scheme
	 */
	@Nonnull
	default AIiVersionCodec versionCodec() {
		return AIcDefaultVersionCodec.INSTANCE;
	}

	/**
	 * Convenience wrapper around {@link #versionCodec()}.
	 *
	 * @param aVersion version to format
	 * @return formatted version text
	 */
	@Nonnull
	default String formatVersionText(@Nonnull final AIcVersion aVersion) {
		return versionCodec().formatVersionText(aVersion, this);
	}

	/**
	 * Convenience wrapper around {@link #versionCodec()}.
	 *
	 * @param aVersionText version text
	 * @return parsed version
	 */
	@Nonnull
	default AIcVersion parseVersion(@Nonnull final String aVersionText) {
		return versionCodec().parseVersion(aVersionText, this);
	}

	/**
	 * Convenience wrapper around {@link #versionCodec()}.
	 *
	 * @param aVersionText version text
	 * @return normalized version text
	 */
	@Nonnull
	default String normalizeVersionText(@Nonnull final String aVersionText) {
		return versionCodec().normalizeVersionText(aVersionText, this);
	}

}
