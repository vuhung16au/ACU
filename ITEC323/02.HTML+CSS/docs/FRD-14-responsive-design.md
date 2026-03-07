# FRD-14-responsive-design – Code Explanation

This document explains the **responsive design** in FRD-14: fluid layout plus **media queries** so the page adapts to different screen widths.

---

## File overview

- **`FRD-14-responsive-design.html`** – Header, main (three cards), footer.
- **`FRD-14-responsive-design.css`** – Base (mobile-first) styles and **`@media`** rules that change layout and typography at **600px** and **900px** breakpoints.

---

## Viewport meta tag (HTML)

```html
<meta name="viewport" content="width=device-width, initial-scale=1.0">
```

- Tells the browser to use the device width as the layout width and not scale the page on load. Needed so media queries and fluid layout work correctly on phones.

---

## Base (mobile) styles

- **`box-sizing: border-box`** – Widths include padding and border.
- **`body`** – Default font size (e.g. **16px**).
- **`.main`** – **`display: flex; flex-direction: column`** so cards **stack** on small screens.
- **`.card`** – Padding, background, border; no fixed width so they can shrink/grow.

Starting with a single-column, flexible layout is a **mobile-first** approach.

---

## Media queries

**Media queries** apply CSS only when the viewport matches a condition (e.g. minimum width).

### Breakpoint at 600px (tablet and up)

```css
@media (min-width: 600px) {
    .main {
        flex-direction: row;
        flex-wrap: wrap;
    }

    .card {
        flex: 1 1 200px;
    }
}
```

- **`@media (min-width: 600px)`** – Rules inside apply when the viewport width is **600px or more**.
- **`flex-direction: row`** – Cards sit in a **row**.
- **`flex-wrap: wrap`** – Cards wrap to the next line if there isn’t enough space.
- **`flex: 1 1 200px`** – Cards can grow and shrink, with a preferred base of **200px**, so you get a flexible multi-column layout.

### Breakpoint at 900px (desktop)

```css
@media (min-width: 900px) {
    body { font-size: 18px; }
    .header h1 { font-size: 1.5rem; }
    .main { padding: 1.5rem; }
}
```

- On wider screens, font sizes and padding increase slightly for readability.

---

## Summary

| Topic | Purpose |
|-------|---------|
| **Viewport meta** | Use device width and sensible initial scale so layout works on mobile. |
| **Mobile-first** | Base styles for narrow screens; add or override with **min-width** media queries. |
| **@media (min-width: …)** | Apply different layout (e.g. flex row, grid) and typography at breakpoints. |
| **Fluid layout** | Use **max-width**, **%**, **flex**, **fr** instead of fixed widths so content adapts. |

The HTML links to **`../css/FRD-14-responsive-design.css`**. Resize the browser or use DevTools device mode to see the layout change at 600px and 900px.
