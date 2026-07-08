package eu.algites.lib.common.documentation;

/**
 * Represents metadata of a documentation table column.
 * <p>
 * The column identifier is the stable technical key. The column title is the
 * human-readable label used by renderers.
 */
public interface AIiDocumentationTableColumn extends AIiDocumentationElement {

    /**
     * Returns the stable table-local column identifier.
     *
     * @return column identifier
     */
    String getColumnId();

    /**
     * Returns the human-readable column title.
     *
     * @return column title
     */
    String getColumnTitle();

    /**
     * Returns the column description.
     *
     * @return column description
     */
    String getColumnDescription();
}
