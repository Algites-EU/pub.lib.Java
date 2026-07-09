package eu.algites.lib.common.documentation;

import java.util.Objects;

/**
 * Default implementation of {@link AIiDocumentationInlineCode}.
 * <p>
 * See {@link AIiDocumentationInlineCode} for the inline code contract.
 */
public class AIcDocumentationInlineCode
        extends AIcDocumentationInlineElement
        implements AIiDocumentationInlineCode {

    private final String code;

    public AIcDocumentationInlineCode(
            String aElementId,
            String aCode) {
        super(aElementId);
        code = Objects.requireNonNull(aCode, "Parameter aCode must not be null.");
    }

    @Override
    public String getCode() {
        return code;
    }
}
