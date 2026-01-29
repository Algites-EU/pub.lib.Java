package eu.algites.lib.common.version;

/**
 * <p>
 * Title: {@link AInBuiltinVersionFormatSpec}
 * </p>
 * <p>
 * Description: Built-in {@link AIiVersionFormatSpec} definitions.
 * </p>
 *
 * @author linhart1
 * @date 28.01.26
 */
public enum AInBuiltinVersionFormatSpec implements AIiVersionFormatSpec {

	/**
	 * Default format: emits build metadata if structure has it.
	 */
	EMIT_BUILD("emit-build", AInVersionBuildFormatPolicy.EMIT),

	/**
	 * Omits build metadata even if present in the input.
	 */
	OMIT_BUILD("omit-build", AInVersionBuildFormatPolicy.OMIT),

	/**
	 * Maps build metadata into qualifier/prerelease section.
	 */
	MAP_BUILD_TO_QUALIFIER("map-build-to-qualifier", AInVersionBuildFormatPolicy.MAP_TO_QUALIFIER),
  ;

	private final String code;
	private final AInVersionBuildFormatPolicy buildFormatPolicy;

	AInBuiltinVersionFormatSpec(final String aCode, final AInVersionBuildFormatPolicy aBuildFormatPolicy) {
		code = aCode;
		buildFormatPolicy = aBuildFormatPolicy;
	}

	@Override
	public String code() {
		return code;
	}

	@Override
	public AInVersionBuildFormatPolicy buildFormatPolicy() {
		return buildFormatPolicy;
	}
}
