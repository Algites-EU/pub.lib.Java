package eu.algites.lib.common.documentation;

import java.util.Objects;

/**
 * Default implementation of {@link AIiDocumentationSection}.
 * <p>
 * See {@link AIiDocumentationSection} for the section contract.
 */
public class AIcDocumentationSection
        extends AIcDocumentationContainerElement
        implements AIiDocumentationSection {

    private final String sectionTitle;

    private final String sectionDescription;

    public AIcDocumentationSection(
            String aElementId,
            String aSectionTitle,
            String aSectionDescription) {
        super(aElementId);
        sectionTitle = Objects.requireNonNull(aSectionTitle, "Parameter aSectionTitle must not be null.");
        sectionDescription = Objects.requireNonNull(aSectionDescription, "Parameter aSectionDescription must not be null.");
    }

    @Override
    public String getSectionTitle() {
        return sectionTitle;
    }

    @Override
    public String getSectionDescription() {
        return sectionDescription;
    }
}
