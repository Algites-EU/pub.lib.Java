package eu.algites.lib.common.exception;

import static eu.algites.lib.common.object.stringoutput.AInStringOutputMode.SYSTEM;

import eu.algites.lib.common.object.stringoutput.AInStringOutputMode;

import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

/**
 * <p>
 * Title: {@link AIxDevelopmentErrorException}
 * </p>
 * <p>
 * Description: General internal development error exception
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @author linhart1
 * @date 20.01.26 7:59
 */
public class AIxDevelopmentErrorException extends AIxRuntimeException {
	public AIxDevelopmentErrorException(
			final Supplier<String> aMessageSupplier,
			final Throwable aCause,
			final AInStringOutputMode aMessageOutputMode) {
		super(aMessageSupplier, aCause, aMessageOutputMode);
	}

	public AIxDevelopmentErrorException(
			final Supplier<String> aMessageSupplier,
			final Throwable aCause,
			final boolean aEnableSuppression,
			final boolean aWritableStackTrace,
			final AInStringOutputMode aMessageOutputMode) {
		super(getDevelopmentErrorMessageSupplier(aMessageSupplier), aCause, aEnableSuppression, aWritableStackTrace, aMessageOutputMode);
	}

	public AIxDevelopmentErrorException(
			final @NotNull Supplier<String> aMessageSupplier,
			final AInStringOutputMode aMessageOutputMode) {
		super(getDevelopmentErrorMessageSupplier(aMessageSupplier), aMessageOutputMode);
	}
	public AIxDevelopmentErrorException(
			final String aMessage,
			final Throwable aCause) {
		super(getDevelopmentErrorMessageSupplier(aMessage), aCause, SYSTEM);
	}

	public AIxDevelopmentErrorException(
			final String aMessage,
			final Throwable aCause,
			final boolean aEnableSuppression,
			final boolean aWritableStackTrace) {
		super(getDevelopmentErrorMessageSupplier(aMessage), aCause, aEnableSuppression, aWritableStackTrace, SYSTEM);
	}

	public AIxDevelopmentErrorException(
			final @NotNull String aMessage) {
		super(getDevelopmentErrorMessageSupplier(aMessage), SYSTEM);
	}

	private static @NotNull Supplier<String> getDevelopmentErrorMessageSupplier(final @NotNull String aMessage) {
		return () -> getDevelopmentErrorMessageSupplier(() -> aMessage).get();
	}

	private static @NotNull Supplier<String> getDevelopmentErrorMessageSupplier(final @NotNull Supplier<String> aMessageSupplier) {
		return () -> "\\\\Development error:" + aMessageSupplier.get();
	}
}
