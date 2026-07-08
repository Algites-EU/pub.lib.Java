package eu.algites.lib.common.documentation;

/**
 * Represents a code block in the documentation AST.
 * <p>
 * The language identifier is renderer-neutral. Renderers may use it for syntax
 * highlighting, language labels, or output-specific metadata.
 */
public interface AIiDocumentationCodeBlock extends AIiDocumentationElement {

    /**
     * Returns the language identifier of this code block.
     *
     * @return language identifier, for example {@code java}, {@code xml}, or {@code text}
     */
    String getLanguageId();

    /**
     * Returns the code block content.
     *
     * @return code text
     */
    String getCode();
}
