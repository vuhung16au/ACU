# References

## Introduction

This document provides a brief introduction to referencing. **Note:** This is an advanced topic that involves understanding academic writing standards, citation formats, and proper attribution of sources. Proper referencing is essential for academic integrity and avoiding plagiarism.

## What is a Reference?

A **reference** (also called a **citation** or **bibliographic entry**) is a detailed description of a source you have used in your work. It provides enough information for readers to locate and verify the original source.

**Simple Analogy:** Like a library catalog card, a reference tells readers exactly where to find the book, article, or website you used—who wrote it, when it was published, where it was published, and how to access it.

References serve two main purposes:
1. **Attribution**: Give credit to the original authors
2. **Verification**: Allow readers to check your sources and read more

## How to Write a Good Reference?

A good reference includes all necessary information in the correct format. The required elements depend on the source type:

### For Books:
- Author(s) name(s)
- Year of publication
- Title (in italics)
- Edition (if not first)
- Publisher
- Place of publication

### For Journal Articles:
- Author(s) name(s)
- Year of publication
- Article title
- Journal name (in italics)
- Volume and issue number
- Page numbers

### For Websites:
- Author(s) or organization
- Year of publication (or last updated)
- Page title
- Website name
- URL
- Date accessed

**Key Principles:**
- **Accuracy**: All information must be correct
- **Completeness**: Include all required elements
- **Consistency**: Follow the same style throughout
- **Formatting**: Use proper punctuation, italics, and capitalization

## How to Use References in a Report?

References work in two parts: **in-text citations** and a **reference list**.

### Citation-to-Reference Flow

```
┌─────────────────────────────────────────────────────────┐
│              You Read a Source                          │
│         (Book, Article, Website, etc.)                  │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│         You Use Information in Your Report               │
│    (Quote, Paraphrase, or Reference an Idea)            │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│         Add In-Text Citation                            │
│    Example: (Smith 2023) or (Smith 2023, p. 45)        │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│         Create Full Reference Entry                     │
│    Example: Smith, J 2023, 'Cybersecurity Basics',      │
│    Academic Press, London.                               │
└────────────────────┬────────────────────────────────────┘
                     │
                     ▼
┌─────────────────────────────────────────────────────────┐
│         Add to Reference List at End of Report          │
│    (Alphabetically ordered by author's surname)         │
└─────────────────────────────────────────────────────────┘
```

**Process:**
1. When you use information from a source, immediately add an in-text citation
2. Create a full reference entry with all details
3. Add the reference to your reference list at the end
4. Ensure every in-text citation has a corresponding reference entry

**Example:**
- **In-text**: "Cybersecurity is essential for protecting digital assets (Smith 2023)."
- **In reference list**: Smith, J 2023, 'Cybersecurity Basics', Academic Press, London.

## Difference Between IEEE and Harvard Style Referencing

### IEEE (Institute of Electrical and Electronics Engineers)
- **In-text citations**: Numbered in square brackets [1], [2]
- **Reference list**: Numbered sequentially as they appear in text
- **Format**: Author, "Title," Journal/Book, vol., no., pp., year.
- **Use**: Common in engineering and computer science

**Example:**
- In-text: "Network security protocols [1] are essential..."
- Reference: [1] J. Smith, "Network Security Protocols," IEEE Trans. Security, vol. 5, no. 2, pp. 45-60, 2023.

### Harvard Style (Preferred for This Course)
- **In-text citations**: Author and year in parentheses (Smith 2023)
- **Reference list**: Alphabetically ordered by author's surname
- **Format**: Author, Year, 'Title', Publisher, Place.
- **Use**: Common in social sciences, business, and many Australian universities

**Example:**
- In-text: "Network security protocols (Smith 2023) are essential..."
- Reference: Smith, J 2023, 'Network Security Protocols', Academic Press, London.

**Note:** For this course, **Harvard style** is the preferred referencing format.

## How to Use References in MS Word and LaTeX

### Microsoft Word

1. **Insert Citation:**
   - Go to References tab → Insert Citation → Add New Source
   - Fill in source information (author, title, year, etc.)
   - Word automatically formats in Harvard style

2. **Create Reference List:**
   - Place cursor where reference list should appear
   - Go to References tab → Bibliography → References (or Works Cited)
   - Word automatically generates list from your citations

3. **Manage Sources:**
   - References tab → Manage Sources
   - Add, edit, or delete sources
   - Can import sources from other documents

4. **Change Style:**
   - References tab → Style dropdown
   - Select "Harvard" or your preferred style

### LaTeX

1. **Using BibTeX:**
   - Create a `.bib` file with your references
   - Use `\cite{key}` for in-text citations
   - Use `\bibliography{filename}` to generate reference list

2. **Example `.bib` entry (Harvard style):**
```latex
@book{smith2023,
  author = {Smith, John},
  year = {2023},
  title = {Network Security Protocols},
  publisher = {Academic Press},
  address = {London}
}
```

3. **In your LaTeX document:**
```latex
Network security is essential \cite{smith2023}.

\bibliographystyle{agsm}  % Harvard style
\bibliography{references}  % Your .bib file
```

4. **Compilation:**
   - Run: `pdflatex document.tex`
   - Run: `bibtex document`
   - Run: `pdflatex document.tex` (twice)

5. **Alternative:** Use `biblatex` package with Harvard style:
```latex
\usepackage[style=authoryear]{biblatex}
\addbibresource{references.bib}
```

## Examples of Good References (Harvard Style)

### Book
```
Smith, J 2023, 'Cybersecurity Fundamentals', 2nd edn, Academic Press, London.
```

### Journal Article
```
Johnson, M & Brown, K 2023, 'Password security in modern systems', 
Journal of Information Security, vol. 15, no. 3, pp. 45-62.
```

### Website
```
Australian Cyber Security Centre 2023, 'Essential Eight Maturity Model', 
Australian Government, viewed 15 March 2023, 
<https://www.cyber.gov.au/resources-business-and-government/essential-eight>.
```

### Conference Paper
```
Williams, A 2023, 'Machine learning in intrusion detection', 
Proceedings of the International Conference on Cybersecurity, 
Sydney, 10-12 May, pp. 123-135.
```

### Chapter in Edited Book
```
Davis, R 2023, 'Cryptographic protocols', in S Thompson (ed.), 
Advanced Security Techniques, Tech Publishers, Melbourne, pp. 78-95.
```

### Multiple Authors
```
Lee, C, Martinez, P & Chen, L 2023, 'Network security analysis', 
Security Review, vol. 8, no. 2, pp. 12-28.
```

**Note:** All references are listed alphabetically by the first author's surname in your reference list.

## Common Mistakes to Avoid

1. **Missing In-Text Citations**
   - ❌ Wrong: "Cybersecurity is important for organizations."
   - ✅ Correct: "Cybersecurity is important for organizations (Smith 2023)."

2. **Incomplete References**
   - ❌ Wrong: "Smith, J, 'Cybersecurity Basics'"
   - ✅ Correct: "Smith, J 2023, 'Cybersecurity Basics', Academic Press, London."

3. **Inconsistent Formatting**
   - ❌ Wrong: Mixing styles or formats
   - ✅ Correct: Use the same style (Harvard) throughout

4. **Incorrect Author Names**
   - ❌ Wrong: "J. Smith" (inconsistent)
   - ✅ Correct: "Smith, J" (consistent format)

5. **Missing Page Numbers for Direct Quotes**
   - ❌ Wrong: "Security is critical" (Smith 2023).
   - ✅ Correct: "Security is critical" (Smith 2023, p. 45).

6. **Not Listing All Sources**
   - ❌ Wrong: Citing in text but missing from reference list
   - ✅ Correct: Every in-text citation has a corresponding reference entry

7. **Incorrect URL Formatting**
   - ❌ Wrong: Forgetting to include "viewed" date for websites
   - ✅ Correct: Include access date: "viewed 15 March 2023, <URL>"

8. **Plagiarism Through Poor Paraphrasing**
   - ❌ Wrong: Changing a few words but keeping the same structure
   - ✅ Correct: Completely rephrase in your own words, then cite

## Citation Management Tools

Citation management tools help you organize, format, and insert references automatically.

### Zotero
- **Free and open-source**
- **Features**: Browser plugin, Word/LaTeX integration, cloud sync
- **Best for**: Students and researchers
- **Website**: zotero.org

**How to use:**
1. Install Zotero and browser plugin
2. Click plugin icon when viewing a source online
3. Zotero automatically captures citation information
4. Use Zotero plugin in Word to insert citations
5. Automatically generates reference list in Harvard style

### Mendeley
- **Free** (with premium options)
- **Features**: PDF management, annotation, social networking
- **Best for**: Researchers who work with many PDFs
- **Website**: mendeley.com

**How to use:**
1. Create Mendeley account
2. Import PDFs or add references manually
3. Install Mendeley plugin for Word
4. Insert citations and generate reference list

### EndNote
- **Paid software** (often provided by universities)
- **Features**: Advanced features, large database access
- **Best for**: Advanced researchers and institutions
- **Website**: endnote.com

### Other Tools
- **RefWorks**: Web-based, often provided by libraries
- **Citavi**: Popular in Europe, combines reference management with knowledge organization
- **Paperpile**: Google Docs integration, subscription-based

**Recommendation for Students:** Start with **Zotero** (free, easy to use, powerful features).

## Conclusion

Proper referencing is a fundamental skill in academic writing. It demonstrates:
- **Academic integrity**: Giving credit where credit is due
- **Scholarly rigor**: Supporting your arguments with evidence
- **Professionalism**: Following established academic standards

Remember:
- Every source you use must be cited in-text and listed in your reference list
- Use **Harvard style** consistently throughout your work
- Missing citations or references are equivalent to plagiarism
- Use citation management tools to make the process easier and more accurate

By following these guidelines and using the Harvard referencing style, you can ensure your assignments meet academic standards and avoid plagiarism.

---

**Important Reminder:** When in doubt, cite it. It's better to over-cite than to risk plagiarism. Always check your assignment requirements for specific referencing guidelines.

