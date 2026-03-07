# FRD-04-links-images.html – Code Explanation

This document explains how **links** and **images** are used in the HTML page.

---

## File overview

`FRD-04-links-images.html` demonstrates:

- **Links**: external URLs, same tab vs new tab, local file links, and in-page anchor links
- **Images**: from a URL, with `alt` and size attributes
- **Image as link**: wrapping an `<img>` inside an `<a>` so the image is clickable

---

## Document setup

The page uses standard HTML5 structure: `<!DOCTYPE html>`, `<html lang="en">`, `<head>` (charset, viewport, title), and `<body>` with a heading and content.

---

## Links (`<a>`)

The anchor element **`<a>`** creates a link. The main attribute is **`href`** (the destination).

### Same-tab link

```html
<a href="https://www.example.com">Example.com</a>
```

- **`href`** – URL to open. Clicking the link loads that URL in the **same** tab.

### New-tab link

```html
<a href="https://www.example.com" target="_blank" rel="noopener noreferrer">Example.com (new tab)</a>
```

- **`target="_blank"`** – Opens the URL in a **new** tab or window.
- **`rel="noopener noreferrer"`** – Improves security and privacy when using `target="_blank"` (recommended).

### Link to a local file

```html
<a href="FRD-01-index.html">FRD-01 Hello World</a>
```

- **`href`** is a **relative path**: same folder, so just the filename. The browser loads that file when the link is clicked.

### Link to a section on the same page (anchor)

```html
<a href="#images-section">Jump to Images</a>
...
<h2 id="images-section">Images</h2>
```

- **`href="#images-section"`** – The `#` means “id on this page”. The browser scrolls to the element with **`id="images-section"`**.
- The target element must have an **`id`** that matches the part after `#`.

---

## Images (`<img>`)

The **`<img>`** element embeds an image. It is **self-closing** (no `</img>`).

### Basic image

```html
<img src="https://via.placeholder.com/300x150/..." alt="A blue placeholder image 300 by 150 pixels" width="300" height="150">
```

| Attribute   | Purpose |
|------------|---------|
| **`src`**  | URL or path of the image (required). |
| **`alt`**  | Short description for accessibility and when the image fails to load (required). |
| **`width`** | Display width in pixels (optional). |
| **`height`** | Display height in pixels (optional). |

Using **`width`** and **`height`** can reduce layout shift while the image loads.

### Image as a link

```html
<a href="https://www.example.com">
    <img src="https://via.placeholder.com/200x80/..." alt="Green button-style image linking to Example.com" width="200" height="80">
</a>
```

- Put the **`<img>`** **inside** an **`<a>`**.
- Clicking the image then acts like clicking the link and goes to the `href` URL.
- The **`alt`** text should describe the image and that it’s a link (for accessibility).

---

## Summary

| Topic        | Key points |
|-------------|------------|
| **Links**   | Use `<a href="...">`. Use `target="_blank"` with `rel="noopener noreferrer"` for new tab. Use `#id` for same-page anchors. |
| **Images**  | Use `<img src="..." alt="...">`. Always provide meaningful `alt`. Optional `width`/`height` help layout. |
| **Image link** | Wrap `<img>` in `<a>` to make the image clickable. |

This page uses placeholder image URLs so it works without any image files in the project.
