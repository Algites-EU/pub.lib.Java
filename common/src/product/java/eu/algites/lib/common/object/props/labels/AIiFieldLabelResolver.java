package eu.algites.lib.common.object.props.labels;

import eu.algites.lib.common.object.stringoutput.AIiStringOutputMode;

/**
 * <p>
 * Title: {@link AIiFieldLabelResolver}
 * </p>
 * <p>
 * Description: Interface defining the field label resolution
 * </p>
 * <p>
 * Copyright: Copyright (c) 2026 Artur Linhart, Algites
 * </p>
 * <p>
 * Company: Algites
 * </p>
 *
 * @author linhart1
 * @date 12.01.26 6:10
 */
public interface AIiFieldLabelResolver {
	/**
	 * Performs the class property label resolution
	 * @param aOwnerClass
	 * @param aFieldName
	 * @param aOutputMode
	 * @return label or null => fallback
	 */
	String resolveLabel(Class<?> aOwnerClass, String aFieldName, AIiStringOutputMode aOutputMode);
}
