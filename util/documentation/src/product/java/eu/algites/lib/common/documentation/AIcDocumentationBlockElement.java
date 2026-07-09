package eu.algites.lib.common.documentation;

/**
 * Default base implementation of {@link AIiDocumentationBlockElement}.
 * <p>
 * See {@link AIiDocumentationBlockElement} for the block element contract.
 */
public abstract class AIcDocumentationBlockElement
        extends AIcDocumentationElement
        implements AIiDocumentationBlockElement {

    protected AIcDocumentationBlockElement(String aElementId) {
        super(aElementId);
    }
}
