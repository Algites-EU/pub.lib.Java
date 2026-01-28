package eu.algites.lib.common.version;

import jakarta.annotation.Nonnull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * <p>
 * Title: {@link AIcCustomVersionScheme}
 * </p>
 * <p>
 * Description: Simple reusable implementation of {@link AIiVersionScheme}.
 * </p>
 * <p>
 * This class exists to make it trivial to introduce a new version handling mode without changing
 * an enum. You can use it in production code (for registries/configuration) as well as in tests.
 * </p>
 *
 * @author linhart1
 * @date 26.01.26
 */
public final class AIcCustomVersionScheme implements AIiVersionScheme, Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Nonnull
	private final String code;

	@Nonnull
	private final AIiVersionComparator versionComparator;

	public AIcCustomVersionScheme(@Nonnull final String aCode, @Nonnull final AIiVersionComparator aVersionComparator) {
		code = Objects.requireNonNull(aCode, "Code must not be null");
		versionComparator = Objects.requireNonNull(aVersionComparator, "Comparator must not be null");

		if (code.trim().isEmpty()) {
			throw new IllegalArgumentException("Code must not be empty");
		}
	}

	@Override
	@Nonnull
	public String code() {
		return code;
	}

	@Override
	@Nonnull
	public AIiVersionComparator versionComparator() {
		return versionComparator;
	}
}
