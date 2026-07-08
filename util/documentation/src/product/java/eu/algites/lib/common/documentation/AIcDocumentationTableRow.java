package eu.algites.lib.common.documentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Default implementation of {@link AIiDocumentationTableRow}.
 * <p>
 * See {@link AIiDocumentationTableRow} for the table row contract.
 */
public class AIcDocumentationTableRow
        extends AIcDocumentationElement
        implements AIiDocumentationTableRow {

    private final List<AIiDocumentationTableCell> cells = new ArrayList<>();

    public AIcDocumentationTableRow(String aElementId) {
        super(aElementId);
    }

    @Override
    public List<AIiDocumentationTableCell> getCells() {
        return Collections.unmodifiableList(cells);
    }

    @Override
    public void addCell(AIiDocumentationTableCell aCell) {
        cells.add(Objects.requireNonNull(aCell, "Parameter aCell must not be null."));
    }
}
