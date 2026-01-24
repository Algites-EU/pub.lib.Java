package eu.algites.lib.common.enumdata;

import static eu.algites.lib.common.enumdata.AIsEnumDataUtils.LAST_UID_HEADER_PART_POSITION;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * <p>
 * Title: {@link AIiGloballyUniqueEnumDataType}
 * </p>
 * <p>
 * Description: Specifies the type of the enum data to be parsed via the uid
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @author linhart1
 * @date 20.01.26 8:36
 */
public interface AIiGloballyUniqueEnumDataType<R extends AIiUidPartsRecord, O extends AIiEnumDataOrigin> {

	/**
	 * Gets the constructor for the UID record.
	 *
	 * @return the constructor
	 */
	BiFunction<String, List<String>, ? extends R> getUidRecordProvider();

	/**
	 * Gets the origin resolver for this enum data type. The resolver throws an exception
	 * if the origin is unknown.
	 * @return the origin resolver. By default it resolves from {@link AInEnumDataOrigin#getByCodeOrThrow(String)}.
	 */
	@SuppressWarnings("unchecked")
	default Function<String, O> getOriginGetter() {
		return aS -> (O)AInEnumDataOrigin.getByCodeOrThrow(aS);
	}

	/**
	 * Gets the origin resolver for this enum data type. The resolver throws an exception
	 * if the origin is unknown.
	 * @return the origin resolver. By default it resolves from {@link AInEnumDataOrigin#findByCodeOrNull(String)}.
	 */
	@SuppressWarnings("unchecked")
	default Function<String, O> getOriginFinder() {
		return aS -> (O)AInEnumDataOrigin.findByCodeOrNull(aS);
	}

	/**
	 * Gets the metadata for the specific UID parts of this enum data type.
	 * @return the metadata for the specific uid parts
	 */
	List<AIiUidPartMetadata<O>> getSpecificUidPartsMetadata();

	/**
	 * Gets the count of UID parts for this enum data type.
	 * It is equal to the count of specific parts plus 1 for origin prefix plus 1 for namespace prefix.
	 * @return the count
	 */
	default int getUidPartCount() {
		/* Count of specific parts plus origin prefix plus namespace prefix */
		return getSpecificUidPartsMetadata().size() + LAST_UID_HEADER_PART_POSITION + 1;
	}

}
