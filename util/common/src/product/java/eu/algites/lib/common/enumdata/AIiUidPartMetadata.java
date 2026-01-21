package eu.algites.lib.common.enumdata;

/**
 * <p>
 * Title: {@link AIiUidPartMetadata}
 * </p>
 * <p>
 * Description: General interface for the Uid parts metadata
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @author linhart1
 * @date 20.01.26 7:47
 */
public interface AIiUidPartMetadata {
	/**
	 * Label to be displayed in the UI
	 * @return label
	 */
	String displayLabel();

	/**
	 * Whether the field value is required
	 * @return true if required
	 */
	boolean required();
}
