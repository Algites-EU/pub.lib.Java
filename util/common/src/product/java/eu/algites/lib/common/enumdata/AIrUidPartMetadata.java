package eu.algites.lib.common.enumdata;

/**
 * <p>
 * Title: {@link AIrUidPartMetadata}
 * </p>
 * <p>
 * Description: Metadata for the UID segment handling.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 * @param displayLabel label to be displayed in UI
 * @param required whether the field is required
 *
 * @author linhart1
 * @date 20.01.26 7:44
 */
public record AIrUidPartMetadata(
		String displayLabel,
		boolean required
) implements AIiUidPartMetadata { }
