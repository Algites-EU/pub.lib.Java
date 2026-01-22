package eu.algites.lib.common.object.props.labels;

import eu.algites.lib.common.object.stringoutput.AIiStringOutputModeResolver;
import eu.algites.lib.common.object.stringoutput.AInStringOutputMode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Title: {@link AIaFieldLabel}
 * </p>
 * <p>
 * Description: Annotation for the custom field labeling used
 *    for the evaluation what label should be used for the field.
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @author linhart1
 * @date 12.01.26 6:13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.RECORD_COMPONENT})
public @interface AIaFieldLabel {


	/* --- LABEL axis --- */

	/**
	 * Hard label definition (single label mode)
	 * @return hard labe definition
	 */
	String label() default "";

	/**
	 * Mapping, according to mode
	 * @return mapping, according to mode
	 */
	Entry[] labels() default {};

	/**
	 * Resolver, used for the resolution of the labels.
	 * In the case with label resolver, the explicit labels assigned are ignored but are
	 * only resolved the value from the label resolver.
	 * @return label resolver used
	 */
	Class<? extends AIiFieldLabelResolver> labelResolver() default NoLabelResolver.class;

	/* --- MODE axis --- */


	AInStringOutputMode mode() default AInStringOutputMode.DEFAULT; /* nice enum selection in iDE */
	String modeCode() default ""; /* for custom modes without  enums */
	Class<? extends AIiStringOutputModeResolver> modeResolver() default NoModeResolver.class;

	@interface Entry {
		/**
		 * Mode of the label if the default {@link AInStringOutputMode} is sufficient to be used.
		 * @return mode of the label
		 */
		AInStringOutputMode mode() default AInStringOutputMode.DEFAULT;

		/**
		 * Mode of the label if the default {@link AInStringOutputMode} is not sufficient.
		 * @return custom mode code of the label
		 */
		String modeCode() default "";

		/**
		 * Label value oif the label resolver is not used, otherwise ignored
		 * @return label value
		 */
		String label();
	}

	abstract class NoLabelResolver implements AIiFieldLabelResolver {}
	abstract class NoModeResolver implements AIiStringOutputModeResolver {}
}

