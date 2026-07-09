package eu.algites.lib.common.documentation;

import java.util.Objects;

/**
 * Default implementation of {@link AIiDocumentationReferenceTarget}.
 * <p>
 * See {@link AIiDocumentationReferenceTarget} for the reference target contract.
 */
public class AIcDocumentationReferenceTarget
        extends AIcDocumentationElement
        implements AIiDocumentationReferenceTarget {

    private final String sourceSystemId;

    private final String targetKind;

    private final String targetId;

    private final String qualifiedName;

    private final String displayName;

    public AIcDocumentationReferenceTarget(
            String aElementId,
            String aSourceSystemId,
            String aTargetKind,
            String aTargetId,
            String aQualifiedName,
            String aDisplayName) {
        super(aElementId);
        sourceSystemId = Objects.requireNonNull(aSourceSystemId, "Parameter aSourceSystemId must not be null.");
        targetKind = Objects.requireNonNull(aTargetKind, "Parameter aTargetKind must not be null.");
        targetId = Objects.requireNonNull(aTargetId, "Parameter aTargetId must not be null.");
        qualifiedName = Objects.requireNonNull(aQualifiedName, "Parameter aQualifiedName must not be null.");
        displayName = Objects.requireNonNull(aDisplayName, "Parameter aDisplayName must not be null.");
    }

    @Override
    public String getSourceSystemId() {
        return sourceSystemId;
    }

    @Override
    public String getTargetKind() {
        return targetKind;
    }

    @Override
    public String getTargetId() {
        return targetId;
    }

    @Override
    public String getQualifiedName() {
        return qualifiedName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}
