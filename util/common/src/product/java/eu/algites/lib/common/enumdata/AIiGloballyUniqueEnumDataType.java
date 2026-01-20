package eu.algites.lib.common.enumdata;

import java.util.List;
import java.util.function.Function;

import org.gradle.internal.impldep.org.apache.commons.lang3.function.TriFunction;

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
public interface AIiGloballyUniqueEnumDataType<R extends AIiUidPartsRecord> {

	/**
	 * Gets the constructor for the UID record.
	 * @return the constructor
	 */
	TriFunction<AIiGloballyUniqueEnumDataType, String, List<String>, R> getUidRecordConstructor();

	/**
	 * Gets the origin resolver for this enum data type. The resolver throws an exception
	 * if the origin is unknown.
	 * @return the origin resolver. By default it resolves from {@link AInEnumDataOrigin#getByCodeOrThrow(String)}.
	 */
	default Function<String, AIiEnumDataOrigin> getOriginGetter() {
		return AInEnumDataOrigin::getByCodeOrThrow;
	}

	/**
	 * Gets the origin resolver for this enum data type. The resolver throws an exception
	 * if the origin is unknown.
	 * @return the origin resolver. By default it resolves from {@link AInEnumDataOrigin#findByCodeOrNull(String)}.
	 */
	default Function<String, AIiEnumDataOrigin> getOriginFinder() {
		return AInEnumDataOrigin::findByCodeOrNull;
	}

	/**
	 * Gets the metadata for the specific UID parts of this enum data type.
	 * @return the metadata for the specific uid parts
	 */
	List<AIiUidPartMetadata> getSpecificUidPartsMetadata();

	/**
	 * Gets the count of UID parts for this enum data type.
	 * It is equal to the count of specific parts plus 1 for origin prefix plus 1 for namespace prefix.
	 * @return the count
	 */
	default int getUidPartCount() {
		/* Count of specific parts plus origin prefix plus namespace prefix */
		return getSpecificUidPartsMetadata().size() + 2;
	}

}
