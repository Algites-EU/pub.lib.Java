package eu.algites.lib.common.exception;

import static eu.algites.lib.common.object.rendering.AInRenderingOutputBuiltinFormat.PLAIN_TEXT;
import static eu.algites.lib.common.object.rendering.AInRenderingOutputBuiltinPurpose.SYSTEM;

import eu.algites.lib.common.object.rendering.AIiRenderingOutputFormat;
import eu.algites.lib.common.object.rendering.AInRenderingOutputBuiltinFormat;
import eu.algites.lib.common.object.rendering.AInRenderingOutputBuiltinPurpose;

import java.util.Locale;
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
			final AInRenderingOutputBuiltinPurpose aMessageOutputPurpose, final AIiRenderingOutputFormat aRenderingOutputFormat,
			final Locale aLocale) {
		super(aMessageSupplier, aCause, aMessageOutputPurpose, aRenderingOutputFormat, aLocale);
	}

	public AIxDevelopmentErrorException(
			final Supplier<String> aMessageSupplier,
			final Throwable aCause,
			final boolean aEnableSuppression,
			final boolean aWritableStackTrace,
			final AInRenderingOutputBuiltinPurpose aMessageOutputPurpose, final AIiRenderingOutputFormat aRenderingOutputFormat,
			final Locale aLocale) {
		super(getDevelopmentErrorMessageSupplier(aMessageSupplier), aCause, aEnableSuppression, aWritableStackTrace, aMessageOutputPurpose,
				aRenderingOutputFormat, aLocale);
	}

	public AIxDevelopmentErrorException(
			final @NotNull Supplier<String> aMessageSupplier,
			final AInRenderingOutputBuiltinPurpose aMessageOutputPurpose, final AIiRenderingOutputFormat aRenderingOutputFormat,
			final Locale aLocale) {
		super(getDevelopmentErrorMessageSupplier(aMessageSupplier), aMessageOutputPurpose, aRenderingOutputFormat, aLocale);
	}
	public AIxDevelopmentErrorException(
			final String aMessage,
			final Throwable aCause) {
		super(getDevelopmentErrorMessageSupplier(aMessage), aCause, null, null, null);
	}

	public AIxDevelopmentErrorException(
			final String aMessage,
			final Throwable aCause,
			final boolean aEnableSuppression,
			final boolean aWritableStackTrace) {
		super(getDevelopmentErrorMessageSupplier(aMessage), aCause, aEnableSuppression, aWritableStackTrace, null, null,	null);
	}

	public AIxDevelopmentErrorException(
			final @NotNull String aMessage) {
		super(getDevelopmentErrorMessageSupplier(aMessage), null, null,	null);
	}

	private static @NotNull Supplier<String> getDevelopmentErrorMessageSupplier(final @NotNull String aMessage) {
		return () -> getDevelopmentErrorMessageSupplier(() -> aMessage).get();
	}

	private static @NotNull Supplier<String> getDevelopmentErrorMessageSupplier(final @NotNull Supplier<String> aMessageSupplier) {
		return () -> "\\\\Development error:" + aMessageSupplier.get();
	}
}
