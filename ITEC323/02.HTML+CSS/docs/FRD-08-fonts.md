# FRD-08-fonts – Code Explanation

This document explains the CSS **font** properties used in `FRD-08-fonts.css`: **font-family**, **font-size**, **font-weight**, and **font-style**.

---

## File overview

- **`FRD-08-fonts.html`** – HTML page that links to the stylesheet and contains sections for each font property.
- **`FRD-08-fonts.css`** – Stylesheet that demonstrates:
  - **font-family** – typeface (serif vs sans-serif, fallbacks)
  - **font-size** – size of the text (e.g. rem)
  - **font-weight** – light, normal, bold
  - **font-style** – normal, italic, oblique

---

## Linking the stylesheet (HTML)

In the `<head>` of `FRD-08-fonts.html`:

```html
<link rel="stylesheet" href="FRD-08-fonts.css">
```

The browser loads this CSS file and applies the rules to the page.

---

## font-family

**font-family** sets which **typeface** (font) is used. You give a list of font names; the browser uses the first one it can find.

Example from the stylesheet:

```css
body {
    font-family: Georgia, "Times New Roman", serif;
}

.section-family {
    font-family: Arial, Helvetica, sans-serif;
    ...
}
```

- **`Georgia, "Times New Roman", serif`** – Prefer Georgia, then Times New Roman; **serif** is the generic fallback (any serif font).
- **`Arial, Helvetica, sans-serif`** – Font names with spaces are in **quotes** (e.g. `"Times New Roman"`). **sans-serif** is the generic fallback.

**Generic families** (always last in the list): `serif`, `sans-serif`, `monospace`, `cursive`, `fantasy`. They ensure something sensible is used even if the named fonts are missing.

---

## font-size

**font-size** sets how **big** the text is. Common units: **rem** (relative to root font size), **em**, **px**.

Example:

```css
h1 {
    font-size: 2rem;
}

.section-size {
    font-size: 1.25rem;
}

.section-size small {
    font-size: 0.875rem;
}
```

- **`2rem`** – Twice the root font size (often 16px), so about 32px.
- **`1.25rem`** – 1.25 × root size (e.g. 20px).
- **`0.875rem`** – Smaller text (e.g. 14px).

Using **rem** keeps sizes consistent and accessible when users change their default font size.

---

## font-weight

**font-weight** sets how **thick** the strokes are: light, normal, or bold. Values can be keywords or numbers (e.g. 100–900).

Example:

```css
.section-weight .light {
    font-weight: 300;
}

.section-weight .normal {
    font-weight: normal;
}

.section-weight .bold {
    font-weight: bold;
}
```

- **`300`** – Light. Common numeric values: 100, 200, 300 (light), 400 (normal), 500, 600, 700 (bold), 800, 900.
- **`normal`** – Same as **400**.
- **`bold`** – Same as **700**.

Not every font has all weights; the browser will use the closest available.

---

## font-style

**font-style** controls **slant**: normal, italic, or oblique.

Example:

```css
.section-style .normal {
    font-style: normal;
}

.section-style .italic {
    font-style: italic;
}

.section-style .oblique {
    font-style: oblique;
}
```

- **`normal`** – Upright text (default).
- **`italic`** – Italic variant (often a separate font design).
- **`oblique`** – Slanted version of the normal font. Similar to italic but may be a simple slant.

Many fonts only have normal and italic; oblique may fall back to italic.

---

## Summary

| Property       | Purpose |
|----------------|---------|
| **font-family** | Typeface; use a list with a generic family at the end (e.g. `sans-serif`). |
| **font-size**   | Text size; **rem** is good for consistency and accessibility. |
| **font-weight** | Thickness: light (300), normal (400), bold (700), or other 100–900. |
| **font-style**  | Slant: **normal**, **italic**, or **oblique**. |

Open `FRD-08-fonts.html` in a browser to see all four properties applied in the demo sections.
