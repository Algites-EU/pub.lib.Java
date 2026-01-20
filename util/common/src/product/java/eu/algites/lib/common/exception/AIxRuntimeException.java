package eu.algites.lib.common.exception;

import static eu.algites.lib.common.object.stringoutput.AIsStringOutputUtils.resolveStringOutput;

import eu.algites.lib.common.object.stringoutput.AInStringOutputMode;

import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

/**
 * <p>
 * Title: {@link AIxRuntimeException}
 * </p>
 * <p>
 * Description: Exception for runtime errors
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @author linhart1
 * @date 12.01.26 4:11
 */
public class AIxRuntimeException extends RuntimeException{
	private final AInStringOutputMode messageOutputMode;

	public AIxRuntimeException(final Supplier<String> aMessageSupplier, final Throwable aCause, final AInStringOutputMode aMessageOutputMode) {
		super(resolveStringOutput(aMessageSupplier, aMessageOutputMode), aCause);
		messageOutputMode = aMessageOutputMode;
	}

	public AIxRuntimeException(
			final Supplier<String> aMessageSupplier,
			final Throwable aCause,
			final boolean aEnableSuppression,
			final boolean aWritableStackTrace,
			final AInStringOutputMode aMessageOutputMode) {
		super(resolveStringOutput(aMessageSupplier, aMessageOutputMode), aCause, aEnableSuppression, aWritableStackTrace);
		messageOutputMode = aMessageOutputMode;
	}

	public AIxRuntimeException(@NotNull final Supplier<String> aMessageSupplier, AInStringOutputMode aMessageOutputMode) {
		super(resolveStringOutput(aMessageSupplier, aMessageOutputMode));
		messageOutputMode = aMessageOutputMode;
	}

	/**
	 * @return the messageOutputMode
	 */
	public AInStringOutputMode getMessageOutputMode() {
		return messageOutputMode;
	}
}
