package eu.algites.lib.common.enumdata;

/**
 * <p>
 * Title: {@link AIiEnumDataOrigin}
 * </p>
 * <p>
 * Description: general interface for the enum data origins
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @author linhart1
 * @date 20.01.26 7:54
 */
public interface AIiEnumDataOrigin extends AIiEnumItem {
	/**
	 * Is a namespace required for this enum data origin?
	 * @return true if namespace is required, false otherwise
	 */
	boolean namespaceUsed();
}
