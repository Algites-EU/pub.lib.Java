/**
 * Renderer-neutral documentation element model.
 * <p>
 * The package contains interfaces and default implementations for a small
 * documentation object tree. The model is intentionally independent from MPS,
 * HTML, Markdown, and any other source or target technology.
 * <p>
 * The model distinguishes block elements, which form the document structure,
 * from inline elements, which form paragraph-like textual content. Semantic
 * references are represented separately from plain links so renderers can
 * resolve references to documented source objects in an output-specific way.
 */
package eu.algites.lib.common.documentation;
