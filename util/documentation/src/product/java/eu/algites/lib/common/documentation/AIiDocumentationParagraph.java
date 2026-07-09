package eu.algites.lib.common.documentation;

import java.util.List;

/**
 * Represents a paragraph with ordered inline content.
 * <p>
 * A paragraph is a block element whose visible text is composed from inline
 * elements such as plain text, links, semantic references, line breaks, and
 * inline code. Renderers should preserve the order of inline elements and apply
 * output-specific escaping and formatting.
 */
public interface AIiDocumentationParagraph extends AIiDocumentationBlockElement {

    /**
     * Returns paragraph inline elements in their rendering order.
     *
     * @return ordered inline element list
     */
    List<AIiDocumentationInlineElement> getInlineElements();

    /**
     * Adds an inline element to the paragraph.
     *
     * @param aInlineElement inline element to add
     */
    void addInlineElement(AIiDocumentationInlineElement aInlineElement);

    /**
     * Adds multiple inline elements in the provided order.
     *
     * @param aInlineElements inline elements to add
     */
    void addInlineElements(List<? extends AIiDocumentationInlineElement> aInlineElements);
}
