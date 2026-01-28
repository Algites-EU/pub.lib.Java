package eu.algites.lib.common.version;

import eu.algites.lib.common.enums.AIiEnumItem;

/**
 * <p>
 * Title: {@link AIiVersionScheme}
 * </p>
 * <p>
 * Description: General interface for version comparison modes.
 * </p>
 * <p>
 * Each handling mode provides a {@link AIiVersionComparator} implementation that defines
 * how {@link AIcVersion} instances are compared.
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
	 * Provides the comparator implementing the given handling mode.
	 *
	 * @return comparator for this mode
	 */
	AIiVersionComparator versionComparator();
}
