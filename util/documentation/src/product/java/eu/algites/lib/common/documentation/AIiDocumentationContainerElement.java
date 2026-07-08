package eu.algites.lib.common.documentation;

import java.util.List;

/**
 * Represents a documentation AST element that can contain child elements.
 * <p>
 * Containers are used for document roots, sections, and any future structural
 * elements that need to aggregate other documentation elements.
 */
public interface AIiDocumentationContainerElement extends AIiDocumentationElement {

    /**
     * Returns child documentation elements in their rendering order.
     *
     * @return ordered child element list
     */
    List<AIiDocumentationElement> getChildElements();

    /**
     * Adds a child documentation element.
     *
     * @param aChildElement child element to add
     */
    void addChildElement(AIiDocumentationElement aChildElement);

    /**
     * Adds multiple child documentation elements in the provided order.
     *
     * @param aChildElements child elements to add
     */
    void addChildElements(List<? extends AIiDocumentationElement> aChildElements);
}
