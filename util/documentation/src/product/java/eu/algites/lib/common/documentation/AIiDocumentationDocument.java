package eu.algites.lib.common.documentation;

/**
 * Represents the root of a documentation AST.
 * <p>
 * A document contains top-level documentation elements, usually sections.
 * It also carries a document title and an optional document description.
 */
public interface AIiDocumentationDocument extends AIiDocumentationContainerElement {

    /**
     * Returns the human-readable document title.
     *
     * @return document title
     */
    String getDocumentTitle();

    /**
     * Returns the document description.
     *
     * @return document description
     */
    String getDocumentDescription();
}
