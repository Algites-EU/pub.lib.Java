package eu.algites.lib.common.documentation;

import java.util.List;

/**
 * Represents a tabular documentation element.
 * <p>
 * A table consists of column definitions and rows. Cells are associated with
 * columns through column identifiers, which allows renderers to keep cell
 * values stable even if column order changes.
 */
public interface AIiDocumentationTable extends AIiDocumentationElement {

    /**
     * Returns the human-readable table title.
     *
     * @return table title
     */
    String getTableTitle();

    /**
     * Returns table columns in their rendering order.
     *
     * @return ordered column list
     */
    List<AIiDocumentationTableColumn> getColumns();

    /**
     * Returns table rows in their rendering order.
     *
     * @return ordered row list
     */
    List<AIiDocumentationTableRow> getRows();

    /**
     * Adds a table column.
     *
     * @param aColumn column to add
     */
    void addColumn(AIiDocumentationTableColumn aColumn);

    /**
     * Adds a table row.
     *
     * @param aRow row to add
     */
    void addRow(AIiDocumentationTableRow aRow);
}
