package eu.algites.lib.common.version;

import static eu.algites.lib.common.enums.uiddata.AInUidEnumDataOrigin.BUILTIN;
import static eu.algites.lib.common.enums.uiddata.AIsUidEnumDataUtils.createBuiltinUid;
import static eu.algites.lib.common.enums.uiddata.AIsUidEnumDataUtils.parseUid;

import eu.algites.lib.common.enums.uiddata.AInUidEnumDataOrigin;
import eu.algites.lib.common.enums.uiddata.AIsUidEnumDataUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.gradle.internal.impldep.com.fasterxml.jackson.annotation.JsonCreator;
import org.jetbrains.annotations.Nullable;

/**
 * <p>
 * Title: {@link AInBuiltinVersionScheme}
 * </p>
 * <p>
 * Description: Common built-in handling modes defining how version precedence is computed.
 * </p>
 * <p>
 * Extendability pattern:
 * </p>
 * <ul>
 *   <li>Create a new {@link AIiVersionComparator} implementation (e.g. {@code AIcMyStyleVersionComparator}).</li>
 *   <li>Either add a new enum constant here, or use {@link AIcCustomVersionScheme} to avoid enum changes.</li>
 *   <li>Use {@link AIsVersionComparator#compare(AIcVersion, AIcVersion, AIiVersionScheme)} everywhere.</li>
 * </ul>
 * <ul>
 *   <li>{@link #MAVEN}: Practical Maven/Gradle-friendly ordering, tolerant to common patterns.</li>
 *   <li>{@link #SEMVER}: Semantic Versioning precedence (build metadata after '+' ignored for precedence).</li>
 *   <li>{@link #CALVER}: Example calendar-versioning comparator.</li>
 * </ul>
 *
 * @author linhart1
 * @date 26.01.26
 */
public enum AInBuiltinVersionScheme implements AIiVersionSchemeData {

	MAVEN("maven", new AIcMavenLikeVersionComparator()),
	SEMVER("semver", new AIcSemverLikeVersionComparator()),
	CALVER("calver", new AIcCalverLikeVersionComparator());

	private final String code;

	private final AIiVersionComparator versionComparator;
	private final String uid;

	private static final AIiVersionSchemeDataType DATA_TYPE = new AIcVersionSchemeDataType();

	AInBuiltinVersionScheme(final String aCode, final AIiVersionComparator aVersionComparator) {
		code = aCode;
		versionComparator = aVersionComparator;
		uid = AIsUidEnumDataUtils.createBuiltinUid(
				List.of(code),
				AIiVersionSchemeDataUidRecord.RECORD_SPECIFIC_PARTS_METADATA);

	}

	@Override
	public String code() {
		return code;
	}

	@Override
	public AIiVersionComparator versionComparator() {
		return versionComparator;
	}

	@Override
	public AIiVersionSchemeDataType getDataType() {
		return DATA_TYPE;
	}

	@Override
	public String uid() {
		return uid;
	}

	@Override
	public AInUidEnumDataOrigin origin() {
		return AInUidEnumDataOrigin.BUILTIN;
	}

	@Override
	public String namespace() {
		return "";
	}

	@Override
	public String schemeCode() {
		return code();
	}

	/**
	 * @param aCode code to be searched for
	 * @return the enum value
	 * @throws IllegalArgumentException if the code is not null but unknown
	 */
	@JsonCreator
	public static AInBuiltinVersionScheme getByCodeOrThrow(final String aCode) throws IllegalArgumentException {
		final AInBuiltinVersionScheme value = findByCode(aCode);
		if (value != null)
			return value;
		throw new IllegalArgumentException("Unknown sourceSet: " + aCode);
	}

	/**
	 * @param aCode code to be searched for
	 * @return the enum value or null if not found
	 */
	public static @Nullable AInBuiltinVersionScheme findByCode(final String aCode) {
		for (AInBuiltinVersionScheme locValue : values()) {
			if (locValue.code().equalsIgnoreCase(aCode)) {
				return locValue;
			}
		}
		return null;
	}

	/**
	 * Gets the {@link AInBuiltinVersionScheme} by its properties.
	 *
	 * @param aOrigin the origin 
	 * @param aNamespace the namespace
	 * @param aSourceSetsCode the source Sets Code (last UID component)
	 * @return the found source set or throws an exception if not found
	 * @throws IllegalArgumentException if origin class is not {@link AInUidEnumDataOrigin#BUILTIN}
	 *                                  or {@link #getByUidOrThrow(String)} throws an exception
	 */
	public static AInBuiltinVersionScheme getByPropsOrThrow(
			final AInUidEnumDataOrigin aOrigin,
			final String aNamespace,
			final String aSourceSetsCode) throws IllegalArgumentException {
		if (aOrigin != BUILTIN) {
			throw new IllegalArgumentException("Unsupported origin: '" + aOrigin + "' for parameters namespace='"
					+ aNamespace + "', source-set-id='" + aSourceSetsCode + "'");
		}
		return getByUidOrThrow(createBuiltinUid(List.of(aSourceSetsCode), AIiVersionSchemeDataUidRecord.RECORD_SPECIFIC_PARTS_METADATA));
	}

	/**
	 * Gets the {@link AInBuiltinVersionScheme} by its source set UID.
	 *
	 * @param aUid source set UID
	 * @return the found source set or throws an exception if not found
	 * @throws IllegalArgumentException if the UID is invalid or not found among enum values
	 */
	public static AInBuiltinVersionScheme getByUidOrThrow(final String aUid) throws IllegalArgumentException {
		return findByUid(aUid)
				.orElseThrow(() -> new IllegalArgumentException("Illegal source set Uid: '" + aUid + "'"));
	}

	/**
	 * Finds the given builtin by the builtin-UID.
	 *
	 * @param aUid UID, for which the built-in item has to be searched for
	 * @return the found built-in item or empty optional, if no built-in item was found
	 */
	public static Optional<AInBuiltinVersionScheme> findByUid(final String aUid) {
		if (aUid == null || aUid.isBlank()) {
			return Optional.empty();
		}
		AIiVersionSchemeDataUidRecord locParsedRecord = (AIiVersionSchemeDataUidRecord) parseUid(
				DATA_TYPE, aUid);
		if (locParsedRecord.origin() != BUILTIN) {
			return Optional.empty();
		}
		return Stream.of(values())
				.filter(locItem -> locItem.code().equals(locParsedRecord.code()))
				.findAny();
	}
	
}
