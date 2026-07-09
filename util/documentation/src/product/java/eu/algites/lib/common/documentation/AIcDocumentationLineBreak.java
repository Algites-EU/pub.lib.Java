package eu.algites.lib.common.documentation;

/**
 * Default implementation of {@link AIiDocumentationLineBreak}.
 * <p>
 * See {@link AIiDocumentationLineBreak} for the line break contract.
 */
public class AIcDocumentationLineBreak
        extends AIcDocumentationInlineElement
        implements AIiDocumentationLineBreak {

    public AIcDocumentationLineBreak(String aElementId) {
        super(aElementId);
    }
}
