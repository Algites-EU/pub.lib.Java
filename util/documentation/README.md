# Algites Documentation Element Java Sources

Package:

```text
eu.algites.lib.common.documentation
```

This source set defines a renderer-neutral documentation element model.

The model intentionally contains no MPS-specific API and no HTML/Markdown rendering logic.

The element hierarchy distinguishes:

```text
AIiDocumentationElement
├─ AIiDocumentationBlockElement
│  ├─ AIiDocumentationContainerElement
│  │  ├─ AIiDocumentationDocument
│  │  └─ AIiDocumentationSection
│  ├─ AIiDocumentationParagraph
│  ├─ AIiDocumentationCodeBlock
│  └─ AIiDocumentationTable
├─ AIiDocumentationInlineElement
│  ├─ AIiDocumentationText
│  ├─ AIiDocumentationLineBreak
│  ├─ AIiDocumentationLink
│  ├─ AIiDocumentationReference
│  └─ AIiDocumentationInlineCode
├─ AIiDocumentationReferenceTarget
├─ AIiDocumentationTableColumn
├─ AIiDocumentationTableRow
└─ AIiDocumentationTableCell
```

Paragraphs and table cells contain ordered inline elements, so text such as
"see API documentation" can be represented as plain text + link/reference +
plain text instead of a single string.

Semantic references to documented source objects are represented by
`AIiDocumentationReference` and `AIiDocumentationReferenceTarget`, rather than
being reduced to URLs. Renderers or documentation link resolvers decide how such
references are resolved in the final output.
