package eu.algites.lib.common.enumdata;

/**
 * <p>
 * Title: {@link AIiGloballyUniqueEnumData}
 * </p>
 * <p>
 * Description: Basic interface for enums and enum-like classes
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @param <R> type of the Uid parts record
 * @author linhart1
 * @date 20.01.26 6:58
 */
public interface AIiGloballyUniqueEnumData<R extends AIiUidPartsRecord,
		O extends AIiEnumDataOrigin,
		GUEDT extends AIiGloballyUniqueEnumDataType<? extends R, O>> {

	/**
	 * Gets the data type of the enum.
	 * @return data type
	 */
	GUEDT getDataType();

	/**
	 * Gets the uid of the enum. It is the value, used for writing into databases
	 * or configuration files, the final globally unique how the enum item or the record equivalent
	 * to its in the case of dynamically is presented
	 * outside of the java classes.
	 * @return globally unique Id of the item. It is globally unique for the given role not only between the .
	 */
	String uid();

	/**
	 * Gets the UID parsed data record.
	 * @return parsed Uid data record
	 */
	default R getUidPartsRecord() {
		return AIsEnumDataUtils.parseUid(getDataType(), uid());
	}

}
