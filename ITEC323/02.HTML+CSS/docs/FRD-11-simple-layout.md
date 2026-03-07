# FRD-11-simple-layout – Code Explanation

This document explains the **simple layout** used in FRD-11: a header, main area (sidebar + content), and footer.

---

## File overview

- **`FRD-11-simple-layout.html`** – Semantic structure: `<header>`, `<main>` (with `<aside>` and `<article>`), `<footer>`.
- **`FRD-11-simple-layout.css`** – Styles for a basic page layout: full-height column with a main section that has a fixed-width sidebar and flexible content.

---

## HTML structure

- **`<header>`** – Site title and navigation.
- **`<main>`** – Wraps the primary content.
  - **`<aside class="sidebar">`** – Sidebar (links or extra content).
  - **`<article class="content">`** – Main content area.
- **`<footer>`** – Copyright or footer text.

Using these elements gives a clear structure and helps accessibility and SEO.

---

## CSS layout ideas

- **`box-sizing: border-box`** – Widths include padding and border so calculations are simple.
- **`body`** – `min-height: 100vh` and `display: flex; flex-direction: column` so the page fills the viewport and the footer stays at the bottom when content is short.
- **`.main`** – `flex: 1` so it grows and pushes the footer down; `max-width: 900px; margin: 0 auto` to centre the main block; inner `display: flex` for sidebar + content.
- **`.sidebar`** – Fixed `width: 200px`; **`.content`** – `flex: 1` so it takes the rest of the space.

The stylesheet link in the HTML uses **`../css/FRD-11-simple-layout.css`** because the HTML file is in `html/` and the CSS is in `css/`.

---

## Summary

A simple layout is: **header** at top, **main** (sidebar + content) in the middle, **footer** at bottom. Flexbox on `body` and inside `main` keeps the footer at the bottom and arranges sidebar and content side by side.
