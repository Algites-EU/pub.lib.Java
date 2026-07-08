package eu.algites.lib.common.documentation;

import java.util.Map;

/**
 * Represents a common documentation AST element.
 * <p>
 * Each element has a stable element identifier and optional string metadata.
 * The element identifier can be used by renderers for anchors, cross-links,
 * diagnostics, or source mapping.
 */
public interface AIiDocumentationElement {

    /**
     * Returns the stable identifier of this documentation element.
     *
     * @return stable element identifier
     */
    String getElementId();

    /**
     * Returns metadata associated with this documentation element.
     * <p>
     * Implementations should return a read-only view unless mutability is
     * explicitly part of the implementation contract.
     *
     * @return element metadata keyed by metadata name
     */
    Map<String, String> getMetadata();

    /**
     * Returns a single metadata value.
     *
     * @param aKey metadata key
     * @return metadata value, or {@code null} if no value exists for the key
     */
    String getMetadataValue(String aKey);

    /**
     * Adds or replaces a metadata value.
     *
     * @param aKey metadata key
     * @param aValue metadata value
     */
    void putMetadataValue(String aKey, String aValue);

    /**
     * Removes a metadata value.
     *
     * @param aKey metadata key
     */
    void removeMetadataValue(String aKey);
}
