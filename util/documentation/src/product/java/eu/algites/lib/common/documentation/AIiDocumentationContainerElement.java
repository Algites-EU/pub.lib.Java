package eu.algites.lib.common.documentation;

import java.util.List;

/**
 * Represents a block-level documentation element that can contain child blocks.
 * <p>
 * Containers are used for document roots, sections, and any future structural
 * block elements that need to aggregate other block elements. Inline elements
 * such as links, text, and references belong inside paragraph-like elements, not
 * directly inside containers.
 */
public interface AIiDocumentationContainerElement extends AIiDocumentationBlockElement {

    /**
     * Returns child block elements in their rendering order.
     *
     * @return ordered child block element list
     */
    List<AIiDocumentationBlockElement> getChildElements();

    /**
     * Adds a child block element.
     *
     * @param aChildElement child block element to add
     */
    void addChildElement(AIiDocumentationBlockElement aChildElement);

    /**
     * Adds multiple child block elements in the provided order.
     *
     * @param aChildElements child block elements to add
     */
    void addChildElements(List<? extends AIiDocumentationBlockElement> aChildElements);
}
