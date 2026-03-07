# FRD-01-index.html – Code Explanation

This document explains the structure and meaning of each part of the simple "Hello, World!" HTML page.

---

## File overview

`FRD-01-index.html` is a minimal, valid HTML5 page. When opened in a browser, it displays the text **Hello, World!** on the page.

---

## Line-by-line explanation

### `<!DOCTYPE html>`

- **Purpose**: Declares that the document is an **HTML5** document.
- **Why it matters**: Tells the browser how to interpret the file and helps avoid older "quirks" rendering modes. Every HTML5 page should start with this line.

---

### `<html lang="en">`

- **Purpose**: The root element that wraps the entire page.
- **`lang="en"`**: Indicates the page content is in English. This helps screen readers and search engines, and is good practice for accessibility and SEO.

---

### `<head>...</head>`

The **head** section holds **metadata** (information about the page) and resources. Nothing inside `<head>` is shown directly on the page.

- **`<meta charset="UTF-8">`**  
  Sets the character encoding to **UTF-8**. This allows the browser to correctly display letters, numbers, and symbols from many languages (including accents and emoji). It should appear early in the `<head>`.

- **`<meta name="viewport" content="width=device-width, initial-scale=1.0">`**  
  Configures how the page behaves on mobile devices. `width=device-width` makes the layout use the device’s screen width; `initial-scale=1.0` sets the initial zoom to 100%. This is the standard way to make a simple page work on phones and tablets.

- **`<title>Hello World</title>`**  
  Sets the text that appears in the browser tab (and in bookmarks and search results). It does not appear inside the page content.

---

### `<body>...</body>`

The **body** contains everything that is **visible** on the page: text, images, links, etc.

---

### `<p>Hello, World!</p>`

- **`<p>`** is a **paragraph** element. It is the appropriate tag for a block of text.
- The text **Hello, World!** is the content that the user sees when they open the page.

---

## Summary

| Part              | Role                                                                 |
|-------------------|----------------------------------------------------------------------|
| `<!DOCTYPE html>` | Declare HTML5                                                        |
| `<html>`          | Root element; `lang="en"` for language                               |
| `<head>`          | Metadata (charset, viewport, title) – not visible on the page        |
| `<body>`          | Visible content                                                      |
| `<p>...</p>`      | Paragraph containing the "Hello, World!" message                      |

Together, these elements form a small but complete, standards-compliant HTML5 page that displays **Hello, World!** in the browser.
