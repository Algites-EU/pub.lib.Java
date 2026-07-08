package eu.algites.lib.common.documentation;

/**
 * Represents a plain textual paragraph in the documentation AST.
 * <p>
 * The paragraph text is renderer-neutral plain text. Renderers are responsible
 * for escaping and formatting it for their target output format.
 */
public interface AIiDocumentationParagraph extends AIiDocumentationElement {

    /**
     * Returns paragraph text.
     *
     * @return paragraph text
     */
    String getText();
}
