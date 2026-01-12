package eu.algites.lib.common.object.stringoutput;

import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

/**
 * <p>
 * Title: {@link AIsStringOutputUtils}
 * </p>
 * <p>
 * Description: General utilities for the objects functionality handling.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @author linhart1
 * @date 12.01.26 4:14
 */
public class AIsStringOutputUtils {

	private static ThreadLocal<AIiStringOutputMode> STRING_OUTPUT_MODE = ThreadLocal.withInitial(() -> AInStringOutputMode.DEFAULT);

	/**
	 * Gets the information if the current String output is user-friendly with external namings
	 * @param aStringOutputMode output mode to be checked
	 * @return true if the Strings have to be produced with passed outputmode parameter and false otherwise.
	 */
	public static boolean isUsedStringOutputMode(final AIiStringOutputMode aStringOutputMode) {
		return usedStringOutputMode() != aStringOutputMode;
	}

	/**
	 * Defines the currently used output mode
	 * @return oputput mode currently used
	 */
	public static AIiStringOutputMode usedStringOutputMode() {
		return STRING_OUTPUT_MODE.get();
	}

	/**
	 * Defines as current String output to be the given mode
	 * @param aStringOutputMode mode to be used for the string outputs
	 */
	public static void useStringOutputMode(final AIiStringOutputMode aStringOutputMode) {
		STRING_OUTPUT_MODE.set(aStringOutputMode);
	}

	/**
	 * Resolves the string output according to the current mode. The supplier can use the method
	 * @param aStringOutputSupplier supplier to be called, delivering the message. The supplier should ask for the message
	 *    according to the current mode returned by usage of call {@link #usedStringOutputMode()}.
	 * @param aStringOutputMode string output mode to be used for evaluation
	 * @return the string output according to the current mode returned from the supplier
	 */
	public static String resolveStringOutput(@NotNull Supplier<String> aStringOutputSupplier, final AIiStringOutputMode aStringOutputMode) {
		AIiStringOutputMode locOriginalMode = usedStringOutputMode();
		useStringOutputMode(aStringOutputMode);
		try {
			return aStringOutputSupplier.get();
		} finally {
			useStringOutputMode(locOriginalMode);
		}
	}
}
