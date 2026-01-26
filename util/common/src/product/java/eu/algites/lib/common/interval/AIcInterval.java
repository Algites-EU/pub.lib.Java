package eu.algites.lib.common.interval;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * <p>
 * Title: {@link AIcInterval}
 * </p>
 * <p>
 * Description: Default immutable implementation of {@link AIiInterval}.
 * </p>
 * <p>
 * Notes:
 * </p>
 * <ul>
 *   <li>The boundary value may be {@code null} if {@link AInIntervalBoundary#isBoundaryValueIgnored()} returns {@code true}.</li>
 *   <li>This class does not attempt to reinterpret boundary semantics; it follows the contract of {@link AIiInterval}.</li>
 * </ul>
 *
 * @author linhart1
 * @date 26.01.26
 */
public final class AIcInterval<T extends Comparable> implements AIiInterval<T>, Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Nonnull
	private final AInIntervalBoundary leftBoundary;

	@Nullable
	private final T leftValue;

	@Nonnull
	private final AInIntervalBoundary rightBoundary;

	@Nullable
	private final T rightValue;

	/**
	 * Creates a new interval.
	 *
	 * @param aLeftBoundary left boundary
	 * @param aLeftValue left value (may be {@code null} if the boundary ignores its value)
	 * @param aRightBoundary right boundary
	 * @param aRightValue right value (may be {@code null} if the boundary ignores its value)
	 */
	public AIcInterval(@Nonnull final AInIntervalBoundary aLeftBoundary,
			@Nullable final T aLeftValue,
			@Nonnull final AInIntervalBoundary aRightBoundary,
			@Nullable final T aRightValue) {
		leftBoundary = Objects.requireNonNull(aLeftBoundary, "Left boundary must not be null");
		rightBoundary = Objects.requireNonNull(aRightBoundary, "Right boundary must not be null");
		leftValue = aLeftValue;
		rightValue = aRightValue;

		AIsIntervalUtils.validateInterval(this);
	}

	@Override
	@Nonnull
	public AInIntervalBoundary getLeftBoundary() {
		return leftBoundary;
	}

	@Override
	public T getLeftValue() {
		return leftValue;
	}

	@Override
	@Nonnull
	public AInIntervalBoundary getRightBoundary() {
		return rightBoundary;
	}

	@Override
	public T getRightValue() {
		return rightValue;
	}

	@Override
	public boolean equals(final Object aOther) {
		if (this == aOther) {
			return true;
		}
		if (!(aOther instanceof AIiInterval)) {
			return false;
		}
		AIiInterval<?> locOther = (AIiInterval<?>) aOther;
		return leftBoundary == locOther.getLeftBoundary()
				&& rightBoundary == locOther.getRightBoundary()
				&& Objects.equals(leftValue, locOther.getLeftValue())
				&& Objects.equals(rightValue, locOther.getRightValue());
	}

	@Override
	public int hashCode() {
		return Objects.hash(leftBoundary, leftValue, rightBoundary, rightValue);
	}

	@Override
	public String toString() {
		return AIsIntervalUtils.toStringRepresentation(this);
	}
}
