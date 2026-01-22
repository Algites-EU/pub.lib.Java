package eu.algites.lib.common.enumdata;

import java.util.Map;
import java.util.function.Supplier;

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
 * @param displayLabelSupplier field name of the field in the UID record to be used for the resolution of the display label in UI
 * @param requiredForOrigin whether the field is required for given origin
 * @param <O> type of the origin
 *
 * @author linhart1
 * @date 20.01.26 7:44
 */
public record AIrUidPartMetadata<O extends AIiEnumDataOrigin>(
		Supplier<String> displayLabelSupplier,
		Map<O, Boolean> requiredForOrigin
) implements AIiUidPartMetadata<O> {

}
