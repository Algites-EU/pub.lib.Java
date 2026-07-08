package eu.algites.lib.common.documentation;

/**
 * Represents a link in the documentation AST.
 * <p>
 * The target may be an external URL, an internal document path, or an anchor,
 * depending on the renderer and linking strategy.
 */
public interface AIiDocumentationLink extends AIiDocumentationElement {

    /**
     * Returns the human-readable link label.
     *
     * @return link label
     */
    String getLabel();

    /**
     * Returns the link target.
     *
     * @return link target
     */
    String getTarget();

    /**
     * Returns an optional human-readable link title.
     *
     * @return link title
     */
    String getTitle();
}
