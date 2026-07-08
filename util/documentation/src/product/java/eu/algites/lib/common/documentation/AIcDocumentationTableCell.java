package eu.algites.lib.common.documentation;

import java.util.Objects;

/**
 * Default implementation of {@link AIiDocumentationTableCell}.
 * <p>
 * See {@link AIiDocumentationTableCell} for the table cell contract.
 */
public class AIcDocumentationTableCell
        extends AIcDocumentationElement
        implements AIiDocumentationTableCell {

    private final String columnId;

    private final String text;

    public AIcDocumentationTableCell(
            String aElementId,
            String aColumnId,
            String aText) {
        super(aElementId);
        columnId = Objects.requireNonNull(aColumnId, "Parameter aColumnId must not be null.");
        text = Objects.requireNonNull(aText, "Parameter aText must not be null.");
    }

    @Override
    public String getColumnId() {
        return columnId;
    }

    @Override
    public String getText() {
        return text;
    }
}
