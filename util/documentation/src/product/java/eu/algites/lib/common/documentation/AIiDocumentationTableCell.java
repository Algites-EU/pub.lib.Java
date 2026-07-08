package eu.algites.lib.common.documentation;

/**
 * Represents a cell in a documentation table row.
 * <p>
 * The cell references a column by its column identifier and stores a
 * renderer-neutral plain text value.
 */
public interface AIiDocumentationTableCell extends AIiDocumentationElement {

    /**
     * Returns the identifier of the column this cell belongs to.
     *
     * @return table-local column identifier
     */
    String getColumnId();

    /**
     * Returns cell text.
     *
     * @return cell text
     */
    String getText();
}
