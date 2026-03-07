# FRD-13-grid-layout – Code Explanation

This document explains **CSS Grid** as used in FRD-13.

---

## File overview

- **`FRD-13-grid-layout.html`** – Two grids: a simple 3×2 set of cells, and a layout with header, sidebar, main, footer.
- **`FRD-13-grid-layout.css`** – Uses **`display: grid`**, **`grid-template-columns`**, **`grid-template-rows`**, **`grid-template-areas`**, **`grid-area`**, **`gap`**.

---

## What is CSS Grid?

Grid defines a **grid of rows and columns**. The parent is the **grid container**; direct children are **grid items** and can be placed into specific cells or areas.

- **Container**: `display: grid` and template properties.
- **Items**: Optionally **`grid-column`**, **`grid-row`**, or **`grid-area`** to place them.

---

## Simple grid (columns and rows)

```css
.grid-simple {
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
    grid-template-rows: auto auto;
    gap: 0.5rem;
}
```

- **`display: grid`** – Makes the element a grid container.
- **`grid-template-columns: 1fr 1fr 1fr`** – Three columns of equal width. **`1fr`** = one fraction of the free space.
- **`grid-template-rows: auto auto`** – Two rows, height based on content.
- **`gap`** – Space between grid tracks (rows and columns). Items fill cells in order (row by row).

---

## Grid with named areas

```css
.grid-areas {
    display: grid;
    grid-template-columns: 1fr 2fr;
    grid-template-rows: auto 1fr auto;
    grid-template-areas:
        "header header"
        "sidebar main"
        "footer footer";
}

.grid-areas .header { grid-area: header; }
.grid-areas .sidebar { grid-area: sidebar; }
.grid-areas .main { grid-area: main; }
.grid-areas .footer { grid-area: footer; }
```

- **`grid-template-areas`** – Defines named regions. Each string is a row; each word is a cell. The same name can span multiple cells (e.g. **header** across both columns).
- **`grid-area: header`** – Puts that item into the **header** region. Same for **sidebar**, **main**, **footer**.
- **`1fr 2fr`** – Second column is twice the width of the first.
- **`auto 1fr auto`** – Top and bottom rows size to content; middle row takes remaining space.

---

## Summary

| Property | Purpose |
|----------|---------|
| **display: grid** | Make the element a grid container. |
| **grid-template-columns** | Define columns (e.g. **1fr 1fr 1fr**, **100px 1fr**). |
| **grid-template-rows** | Define rows (e.g. **auto**, **1fr**). |
| **grid-template-areas** | Name regions with strings; items use **grid-area** to fill them. |
| **gap** | Space between tracks. |

The HTML links to **`../css/FRD-13-grid-layout.css`** because the page is in `html/` and the CSS is in `css/`.
