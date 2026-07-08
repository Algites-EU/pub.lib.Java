package eu.algites.lib.common.documentation;

import java.util.Objects;

/**
 * Default implementation of {@link AIiDocumentationLink}.
 * <p>
 * See {@link AIiDocumentationLink} for the link contract.
 */
public class AIcDocumentationLink
        extends AIcDocumentationElement
        implements AIiDocumentationLink {

    private final String label;

    private final String target;

    private final String title;

    public AIcDocumentationLink(
            String aElementId,
            String aLabel,
            String aTarget,
            String aTitle) {
        super(aElementId);
        label = Objects.requireNonNull(aLabel, "Parameter aLabel must not be null.");
        target = Objects.requireNonNull(aTarget, "Parameter aTarget must not be null.");
        title = Objects.requireNonNull(aTitle, "Parameter aTitle must not be null.");
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getTarget() {
        return target;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
