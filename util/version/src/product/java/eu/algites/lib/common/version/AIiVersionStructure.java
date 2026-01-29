package eu.algites.lib.common.version;

import eu.algites.lib.common.enums.AIiEnumItem;
import jakarta.annotation.Nonnull;

/**
 * <p>
 * Title: {@link AIiVersionStructure}
 * </p>
 * <p>
 * Description: Structural interpretation of version strings for a given {@link AIiVersionScheme}.
 * </p>
 * <p>
 * A structure defines where the build-identification part is located (before/after the version),
 * how it is delimited, and whether it participates in precedence comparison.
 * </p>
 *
 * @author linhart1
 * @date 28.01.26
 */
public interface AIiVersionStructure extends AIiEnumItem {

	/**
	 * @return true if the precedence-relevant version part is placed before the build-identification part
	 */
	boolean versionBeforeBuild();

	/**
	 * Build delimiter separating the precedence-relevant version part and the build-identification part.
	 * <p>
	 * An empty delimiter means that there is no dedicated build-identification section.
	 * </p>
	 *
	 * @return build delimiter or empty string
	 */
	@Nonnull
	String buildDelimiter();

	/**
	 * @return policy defining whether and how the build-identification part participates in comparison
	 */
	@Nonnull
	AInVersionBuildComparisonPolicy buildComparisonPolicy();
}
