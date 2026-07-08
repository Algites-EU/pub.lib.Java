package eu.algites.lib.common.documentation;

import java.util.List;

/**
 * Represents a row in a documentation table.
 * <p>
 * A row contains cells. The cells may be stored in rendering order, but their
 * meaning is determined by the column identifiers stored on individual cells.
 */
public interface AIiDocumentationTableRow extends AIiDocumentationElement {

    /**
     * Returns row cells.
     *
     * @return ordered cell list
     */
    List<AIiDocumentationTableCell> getCells();

    /**
     * Adds a cell to this row.
     *
     * @param aCell cell to add
     */
    void addCell(AIiDocumentationTableCell aCell);
}
