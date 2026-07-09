package eu.algites.lib.common.documentation;

import java.util.Objects;

/**
 * Default implementation of {@link AIiDocumentationText}.
 * <p>
 * See {@link AIiDocumentationText} for the text contract.
 */
public class AIcDocumentationText
        extends AIcDocumentationInlineElement
        implements AIiDocumentationText {

    private final String text;

    public AIcDocumentationText(
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
