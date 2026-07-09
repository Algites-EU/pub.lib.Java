package eu.algites.lib.common.documentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Default base implementation of {@link AIiDocumentationContainerElement}.
 * <p>
 * See {@link AIiDocumentationContainerElement} for the container contract.
 */
public abstract class AIcDocumentationContainerElement
        extends AIcDocumentationBlockElement
        implements AIiDocumentationContainerElement {

    private final List<AIiDocumentationBlockElement> childElements = new ArrayList<>();

    protected AIcDocumentationContainerElement(String aElementId) {
        super(aElementId);
    }

    @Override
    public List<AIiDocumentationBlockElement> getChildElements() {
        return Collections.unmodifiableList(childElements);
    }

    @Override
    public void addChildElement(AIiDocumentationBlockElement aChildElement) {
        childElements.add(Objects.requireNonNull(aChildElement, "Parameter aChildElement must not be null."));
    }

    @Override
    public void addChildElements(List<? extends AIiDocumentationBlockElement> aChildElements) {
        Objects.requireNonNull(aChildElements, "Parameter aChildElements must not be null.");

        for (AIiDocumentationBlockElement locChildElement : aChildElements) {
            addChildElement(locChildElement);
        }
    }
}
