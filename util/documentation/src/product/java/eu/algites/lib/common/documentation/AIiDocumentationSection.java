package eu.algites.lib.common.documentation;

/**
 * Represents a hierarchical documentation section.
 * <p>
 * Sections are structural documentation elements. A section may contain
 * paragraphs, tables, links, code blocks, and nested sections.
 */
public interface AIiDocumentationSection extends AIiDocumentationContainerElement {

    /**
     * Returns the human-readable section title.
     *
     * @return section title
     */
    String getSectionTitle();

    /**
     * Returns the section description.
     *
     * @return section description
     */
    String getSectionDescription();
}
