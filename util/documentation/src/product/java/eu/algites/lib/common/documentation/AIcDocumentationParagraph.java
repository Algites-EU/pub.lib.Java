package eu.algites.lib.common.documentation;

import java.util.Objects;

/**
 * Default implementation of {@link AIiDocumentationParagraph}.
 * <p>
 * See {@link AIiDocumentationParagraph} for the paragraph contract.
 */
public class AIcDocumentationParagraph
        extends AIcDocumentationElement
        implements AIiDocumentationParagraph {

    private final String text;

    public AIcDocumentationParagraph(
            String aElementId,
            String aText) {
        super(aElementId);
        text = Objects.requireNonNull(aText, "Parameter aText must not be null.");
    }

    @Override
    public String getText() {
        return text;
    }
}
