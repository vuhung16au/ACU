# FRD-07-css – Code Explanation

This document explains the CSS used for **backgrounds** and **color** in `FRD-07-css.css` and how the HTML page uses it.

---

## File overview

- **`FRD-07-css.html`** – HTML page that links to the stylesheet and contains sections with different classes.
- **`FRD-07-css.css`** – Stylesheet that demonstrates:
  - **`color`** – text color
  - **`background-color`** – solid background color
  - **`background-image`** – image as background (with **`background-size`**, **`background-position`**)

---

## Linking the stylesheet (HTML)

In the `<head>` of `FRD-07-css.html`:

```html
<link rel="stylesheet" href="FRD-07-css.css">
```

- **`rel="stylesheet"`** – The linked file is a CSS stylesheet.
- **`href="FRD-07-css.css"`** – Path to the CSS file (same folder as the HTML). The browser loads this and applies the rules to the page.

---

## The `color` property

**`color`** sets the **text color** of an element.

Example from the stylesheet:

```css
.section-color {
    color: #1a365d;
    padding: 1rem;
    margin-bottom: 1rem;
}
```

- **`color: #1a365d;`** – Dark blue-gray text. Values can be hex (`#1a365d`), RGB, or color names (e.g. `navy`).
- **`padding`** and **`margin`** – Spacing; they do not change color but help layout.

The HTML uses **`class="section-color"`** on a `<div>` so this rule applies to that block.

---

## The `background-color` property

**`background-color`** sets a **solid color** behind the content (and padding) of an element.

Example:

```css
body {
    background-color: #f0f4f8;
}

.section-background-color {
    background-color: #e2e8f0;
    color: #2d3748;
    padding: 1rem;
    ...
}
```

- **`body`** – Page background. Here it’s a light gray-blue.
- **`.section-background-color`** – A different background for that section, with **`color`** set so text stays readable (contrast).

You can use any valid CSS color (hex, rgb(), color names, etc.).

---

## The `background-image` property

**`background-image`** uses an **image** as the background of an element.

Example:

```css
.section-background-image {
    background-color: #cbd5e0;
    background-image: url("https://via.placeholder.com/400x200/...");
    background-size: cover;
    background-position: center;
    color: white;
    padding: 2rem;
    min-height: 120px;
    ...
}
```

- **`background-color: #cbd5e0;`** – Fallback color if the image fails to load or while it loads.
- **`background-image: url("...");`** – URL of the image. Can be relative (e.g. `url("images/bg.png")`) or absolute.
- **`background-size: cover;`** – Scales the image so it covers the whole area; may crop edges. Other options include `contain`, or explicit width/height.
- **`background-position: center;`** – Centers the image in the element. You can also use values like `top left`, `50% 50%`, etc.
- **`color: white;`** – Text color chosen so it’s readable on the image.
- **`min-height: 120px;`** – Gives the block enough height so the background is visible.

---

## Using `color` and `background-color` together

Example:

```css
.section-combined {
    color: #2c5282;
    background-color: #bee3f8;
    padding: 1rem;
    border-radius: 4px;
}
```

- **`color`** – Text color.
- **`background-color`** – Background of the box.
- Picking contrasting values (e.g. dark text on light background) keeps text readable.

---

## Summary

| Property            | Purpose |
|---------------------|---------|
| **`color`**         | Sets the **text color** of the element. |
| **`background-color`** | Sets a **solid background color**. Often used with `color` for contrast. |
| **`background-image`** | Sets an **image** as the background. Use **`background-size`** and **`background-position`** to control how it appears. |
| **Fallback**        | When using **`background-image`**, set **`background-color`** as a fallback. |

The HTML file is in the same folder as the CSS file and uses **`<link rel="stylesheet" href="FRD-07-css.css">`** so the browser loads and applies these rules. Open `FRD-07-css.html` in a browser to see the result.
