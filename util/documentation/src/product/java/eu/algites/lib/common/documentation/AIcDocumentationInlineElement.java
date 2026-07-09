package eu.algites.lib.common.documentation;

/**
 * Default base implementation of {@link AIiDocumentationInlineElement}.
 * <p>
 * See {@link AIiDocumentationInlineElement} for the inline element contract.
 */
public abstract class AIcDocumentationInlineElement
        extends AIcDocumentationElement
        implements AIiDocumentationInlineElement {

    protected AIcDocumentationInlineElement(String aElementId) {
        super(aElementId);
    }
}
