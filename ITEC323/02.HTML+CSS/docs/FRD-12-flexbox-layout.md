# FRD-12-flexbox-layout – Code Explanation

This document explains **Flexbox** (Flexible Box Layout) as used in FRD-12.

---

## File overview

- **`FRD-12-flexbox-layout.html`** – Several sections, each with a flex container and child boxes.
- **`FRD-12-flexbox-layout.css`** – Uses **`display: flex`** and properties like **`flex-direction`**, **`justify-content`**, **`flex-wrap`**, **`gap`**.

---

## What is Flexbox?

Flexbox lays out **children** of a container in a **row** or **column**. The parent is the **flex container**; the direct children are **flex items**.

- **Container**: `display: flex` (or `inline-flex`).
- **Items**: Automatically become flex items; you can use `flex-grow`, `flex-shrink`, `flex-basis` on them.

---

## Main properties used

### display: flex

```css
.flex-row {
    display: flex;
    gap: 0.5rem;
}
```

- **`display: flex`** – Makes the element a flex container. Children are laid out in a **row** by default.
- **`gap`** – Space between flex items (no need for margins between items).

### flex-direction

```css
.flex-col {
    display: flex;
    flex-direction: column;
}
```

- **`flex-direction: column`** – Stack items **vertically**. Options: **`row`** (default), **`row-reverse`**, **`column`**, **`column-reverse`**.

### justify-content

Controls alignment along the **main axis** (row = horizontal, column = vertical).

```css
.flex-center {
    justify-content: center;
}

.flex-between {
    justify-content: space-between;
}
```

- **`center`** – Items centred.
- **`space-between`** – First at start, last at end, equal space between.
- Others: **`flex-start`**, **`flex-end`**, **`space-around`**, **`space-evenly`**.

### flex-wrap

```css
.flex-wrap {
    display: flex;
    flex-wrap: wrap;
    gap: 0.5rem;
}
```

- **`flex-wrap: wrap`** – Items wrap to the next line when there isn’t enough space. Default is **`nowrap`**.

---

## Summary

| Property | Purpose |
|----------|---------|
| **display: flex** | Make the element a flex container. |
| **flex-direction** | **row** (default) or **column** (and reverse variants). |
| **justify-content** | Align/distribute items on the main axis. |
| **flex-wrap** | **wrap** so items can go to the next line. |
| **gap** | Space between items. |

The HTML links to the stylesheet with **`../css/FRD-12-flexbox-layout.css`** because the page is in `html/` and the CSS is in `css/`.
