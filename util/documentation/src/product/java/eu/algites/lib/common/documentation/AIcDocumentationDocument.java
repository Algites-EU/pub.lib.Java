package eu.algites.lib.common.documentation;

import java.util.Objects;

/**
 * Default implementation of {@link AIiDocumentationDocument}.
 * <p>
 * See {@link AIiDocumentationDocument} for the document contract.
 */
public class AIcDocumentationDocument
        extends AIcDocumentationContainerElement
        implements AIiDocumentationDocument {

    private final String documentTitle;

    private final String documentDescription;

    public AIcDocumentationDocument(
            String aElementId,
            String aDocumentTitle,
            String aDocumentDescription) {
        super(aElementId);
        documentTitle = Objects.requireNonNull(aDocumentTitle, "Parameter aDocumentTitle must not be null.");
        documentDescription = Objects.requireNonNull(aDocumentDescription, "Parameter aDocumentDescription must not be null.");
    }

    @Override
    public String getDocumentTitle() {
        return documentTitle;
    }

    @Override
    public String getDocumentDescription() {
        return documentDescription;
    }
}
