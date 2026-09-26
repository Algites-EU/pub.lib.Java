# Algites Documentation Element Java Sources

Package:

```text
eu.algites.lib.common.documentation
```

This source set defines a renderer-neutral documentation element model.

The model intentionally contains no MPS-specific API and no HTML/Markdown
rendering logic.

## Read-only and write-access contracts

Every documentation type has two interface contracts:

```text
AIiDocumentation<Type>
    read-only contract

AIiDocumentation<Type>WriteAccess
    read-only contract plus write capabilities inherited from the matching
    write-access hierarchy

AIcDocumentation<Type>
    default implementation of the write-access contract
```

Validators, renderers, and public consumers should depend on read-only
interfaces. Contributors, mergers, and model processors may depend on the
corresponding `WriteAccess` interfaces. Collection getters return read-only
views; mutations are exposed through explicit write-access operations.

The read-only element hierarchy is:

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

The `WriteAccess` hierarchy mirrors this hierarchy exactly. For example:

```text
AIiDocumentationParagraphWriteAccess
├─ extends AIiDocumentationParagraph
└─ extends AIiDocumentationBlockElementWriteAccess
```

Paragraphs and table cells contain ordered inline elements, so text such as
"see API documentation" can be represented as plain text + link/reference +
plain text instead of a single string.

Semantic references to documented source objects are represented by
`AIiDocumentationReference` and `AIiDocumentationReferenceTarget`, rather than
being reduced to URLs. Renderers or documentation link resolvers decide how such
references are resolved in the final output.
