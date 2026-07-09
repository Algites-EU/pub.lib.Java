package eu.algites.lib.common.documentation;

import java.util.List;

/**
 * Represents a cell in a documentation table row.
 * <p>
 * The cell references a column by its column identifier and stores ordered
 * inline content. This allows table cells to contain not only plain text, but
 * also links, semantic references, line breaks, and inline code.
 */
public interface AIiDocumentationTableCell extends AIiDocumentationElement {

    /**
     * Returns the identifier of the column this cell belongs to.
     *
     * @return table-local column identifier
     */
    String getColumnId();

    /**
     * Returns cell inline elements in their rendering order.
     *
     * @return ordered inline element list
     */
    List<AIiDocumentationInlineElement> getInlineElements();

    /**
     * Adds an inline element to the cell.
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
