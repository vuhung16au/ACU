# FRD-03-elements.html – Code Explanation

This document explains how the page demonstrates **HTML elements**, **attributes**, and **headings**.

---

## File overview

`FRD-03-elements.html` is an HTML5 page that shows:

- **Headings** from level 1 (`h1`) to level 6 (`h6`)
- **Common elements**: paragraphs, links, images, lists, and text formatting
- **Attributes**: `id`, `class`, `href`, `title`, `src`, `alt`, `width`, `height`

---

## Document setup

The page uses the usual HTML5 structure:

- `<!DOCTYPE html>`, `<html lang="en">`, `<head>`, `<body>`
- In `<head>`: `charset="UTF-8"`, viewport meta, and `<title>`

---

## Headings

Headings define the outline of the page. There are six levels:

| Tag   | Role |
|-------|------|
| `<h1>` | Main page title – use **one** per page |
| `<h2>` | Major sections |
| `<h3>` | Sub-sections |
| `<h4>`, `<h5>`, `<h6>` | Deeper levels (smaller, less important) |

- **Elements**: `<h1>` … `<h6>` are the heading **elements**.
- **Best practice**: Use headings in order (e.g. don’t skip from `h2` to `h4`) so the structure makes sense for accessibility and SEO.

---

## Text and inline elements

- **`<p>`** – Paragraph. Block of text.
- **`<strong>`** – Strong importance (usually **bold**).
- **`<em>`** – Emphasis (usually *italic*).
- **`<code>`** – Inline code (e.g. tag or keyword names).

These are **elements**: the tag name (e.g. `strong`) defines what the content means.

---

## Attributes

**Attributes** appear inside the opening tag and give extra information. Format: `name="value"`.

### `id`

- **Example**: `<h2 id="attributes-section">Attributes</h2>`
- **Purpose**: Gives that element a **unique** identifier on the page.
- **Use**: Links (e.g. `#attributes-section`), JavaScript, or CSS targeting that element.

### `class`

- **Example**: `<p class="intro">...</p>`
- **Purpose**: Groups elements so you can style or script them together.
- **Use**: One element can have multiple classes, e.g. `class="intro highlight"`.

### Links: `href` and `title`

- **`<a href="https://www.example.com">Example link</a>`**
  - **`href`** – The URL the link goes to (required for links).
- **`title="Visit Example"`** – Tooltip shown when the user hovers (optional).

### Images: `src`, `alt`, `width`, `height`

- **`<img src="..." alt="..." width="150" height="150">`**
  - **`src`** – Path or URL of the image (required).
  - **`alt`** – Short description of the image (required for accessibility and when the image doesn’t load).
  - **`width`** and **`height`** – Size in pixels (optional but can prevent layout shift).

---

## Lists

- **`<ul>`** – Unordered list (bullet list). Each item is in a **`<li>`**.
- **`<ol>`** – Ordered list (numbered list). Each item is also in a **`<li>`**.

Only **`<li>`** should be direct children of `<ul>` or `<ol>`.

---

## Summary

| Concept    | Meaning |
|-----------|---------|
| **Element** | A tag and its content, e.g. `<p>...</p>`, `<h2>...</h2>`. |
| **Attribute** | Name–value pair on a tag, e.g. `id="section"`, `href="https://..."`. |
| **Headings** | `<h1>`–`<h6>` for document structure; one `h1`, then logical order. |

Together, elements and attributes let you structure content and add behaviour (links, images) and hooks for styling (id, class).
