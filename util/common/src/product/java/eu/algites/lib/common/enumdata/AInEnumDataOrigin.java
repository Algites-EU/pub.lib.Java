package eu.algites.lib.common.enumdata;

import org.jetbrains.annotations.Nullable;

/**
 * <p>
 * Title: {@link AInEnumDataOrigin}
 * </p>
 * <p>
 * Description: Definition of the origin of the given data - builtin or custom.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @author linhart1
 * @date 13.01.26 17:00
 */
public enum AInEnumDataOrigin implements AIiEnumDataOrigin {
	BUILTIN("builtin", false),
	CUSTOM("custom", true);

	private final String code;
	private boolean namespaceUsed;

	AInEnumDataOrigin(String aCode, final boolean aNamespaceUsed) {
		code = aCode;
		namespaceUsed = aNamespaceUsed;
	}

	/**
	 * @return kind class code used in UIDs ({@code builtin} or {@code custom})
	 */
	public String code() {
		return code;
	}

	/**
	 * Parse origin from an output type code.
	 *
	 * @param aCode kind class code
	 * @return parsed enum value
	 * @throws IllegalArgumentException if the code is unknown
	 */
	public static AInEnumDataOrigin getByCodeOrThrow(String aCode) throws IllegalArgumentException {
		final AInEnumDataOrigin builtin = findByCodeOrNull(aCode);
		if (builtin != null)
			return builtin;
		throw new IllegalArgumentException("Unsupported enumItemOrigin: '" + aCode + "'");
	}

	/**
	 * Parse origin from an output type code.
	 *
	 * @param aCode kind class code
	 * @return parsed enum value or null if no item matches
	 */
	public static @Nullable AInEnumDataOrigin findByCodeOrNull(final String aCode) {
		if (BUILTIN.code.equals(aCode)) {
			return BUILTIN;
		}
		if (CUSTOM.code.equals(aCode)) {
			return CUSTOM;
		}
		return null;
	}

	@Override
	public boolean namespaceUsed() {
		return namespaceUsed;
	}
}
