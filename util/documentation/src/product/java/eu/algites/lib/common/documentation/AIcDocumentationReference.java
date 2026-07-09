package eu.algites.lib.common.documentation;

import java.util.Objects;

/**
 * Default implementation of {@link AIiDocumentationReference}.
 * <p>
 * See {@link AIiDocumentationReference} for the reference contract.
 */
public class AIcDocumentationReference
        extends AIcDocumentationInlineElement
        implements AIiDocumentationReference {

    private final String label;

    private final AIiDocumentationReferenceTarget referenceTarget;

    private final String title;

    public AIcDocumentationReference(
            String aElementId,
            String aLabel,
            AIiDocumentationReferenceTarget aReferenceTarget,
            String aTitle) {
        super(aElementId);
        label = Objects.requireNonNull(aLabel, "Parameter aLabel must not be null.");
        referenceTarget = Objects.requireNonNull(aReferenceTarget, "Parameter aReferenceTarget must not be null.");
        title = Objects.requireNonNull(aTitle, "Parameter aTitle must not be null.");
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public AIiDocumentationReferenceTarget getReferenceTarget() {
        return referenceTarget;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
