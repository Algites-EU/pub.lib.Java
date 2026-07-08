package eu.algites.lib.common.documentation;

import java.util.Objects;

/**
 * Default implementation of {@link AIiDocumentationTableColumn}.
 * <p>
 * See {@link AIiDocumentationTableColumn} for the table column contract.
 */
public class AIcDocumentationTableColumn
        extends AIcDocumentationElement
        implements AIiDocumentationTableColumn {

    private final String columnId;

    private final String columnTitle;

    private final String columnDescription;

    public AIcDocumentationTableColumn(
            String aElementId,
            String aColumnId,
            String aColumnTitle,
            String aColumnDescription) {
        super(aElementId);
        columnId = Objects.requireNonNull(aColumnId, "Parameter aColumnId must not be null.");
        columnTitle = Objects.requireNonNull(aColumnTitle, "Parameter aColumnTitle must not be null.");
        columnDescription = Objects.requireNonNull(aColumnDescription, "Parameter aColumnDescription must not be null.");
    }

    @Override
    public String getColumnId() {
        return columnId;
    }

    @Override
    public String getColumnTitle() {
        return columnTitle;
    }

    @Override
    public String getColumnDescription() {
        return columnDescription;
    }
}
