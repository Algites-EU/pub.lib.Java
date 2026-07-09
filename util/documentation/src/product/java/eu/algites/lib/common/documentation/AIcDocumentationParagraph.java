package eu.algites.lib.common.documentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Default implementation of {@link AIiDocumentationParagraph}.
 * <p>
 * See {@link AIiDocumentationParagraph} for the paragraph contract.
 */
public class AIcDocumentationParagraph
        extends AIcDocumentationBlockElement
        implements AIiDocumentationParagraph {

    private final List<AIiDocumentationInlineElement> inlineElements = new ArrayList<>();

    public AIcDocumentationParagraph(String aElementId) {
        super(aElementId);
    }

    public AIcDocumentationParagraph(
            String aElementId,
            List<? extends AIiDocumentationInlineElement> aInlineElements) {
        this(aElementId);
        addInlineElements(aInlineElements);
    }

    @Override
    public List<AIiDocumentationInlineElement> getInlineElements() {
        return Collections.unmodifiableList(inlineElements);
    }

    @Override
    public void addInlineElement(AIiDocumentationInlineElement aInlineElement) {
        inlineElements.add(Objects.requireNonNull(aInlineElement, "Parameter aInlineElement must not be null."));
    }

    @Override
    public void addInlineElements(List<? extends AIiDocumentationInlineElement> aInlineElements) {
        Objects.requireNonNull(aInlineElements, "Parameter aInlineElements must not be null.");

        for (AIiDocumentationInlineElement locInlineElement : aInlineElements) {
            addInlineElement(locInlineElement);
        }
    }

    public void addText(
            String aElementId,
            String aText) {
        addInlineElement(new AIcDocumentationText(aElementId, aText));
    }

    public void addLineBreak(String aElementId) {
        addInlineElement(new AIcDocumentationLineBreak(aElementId));
    }

    public void addLink(
            String aElementId,
            String aLabel,
            String aTarget,
            String aTitle) {
        addInlineElement(new AIcDocumentationLink(aElementId, aLabel, aTarget, aTitle));
    }

    public void addReference(
            String aElementId,
            String aLabel,
            AIiDocumentationReferenceTarget aReferenceTarget,
            String aTitle) {
        addInlineElement(new AIcDocumentationReference(aElementId, aLabel, aReferenceTarget, aTitle));
    }

    public void addInlineCode(
            String aElementId,
            String aCode) {
        addInlineElement(new AIcDocumentationInlineCode(aElementId, aCode));
    }
}
