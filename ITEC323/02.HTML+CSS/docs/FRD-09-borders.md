# FRD-09-borders – Code Explanation

This document explains the CSS used in `FRD-09-borders.css`: **border**, **display**, **float**, **height/width**, **padding**, **margin**, and **visibility**.

---

## File overview

- **`FRD-09-borders.html`** – HTML page that links to the stylesheet and contains one section per topic.
- **`FRD-09-borders.css`** – Stylesheet demonstrating each property with simple, visible examples.

---

## Linking the stylesheet (HTML)

In the `<head>`:

```html
<link rel="stylesheet" href="FRD-09-borders.css">
```

---

## Border

**border** draws a line around the element (outside padding, inside margin). Shorthand: `border: width style color`.

```css
.box {
    border: 2px solid #334155;
    border-radius: 6px;
    padding: 1rem;
}

.box-dashed {
    border: 2px dashed #64748b;
    border-radius: 4px;
}
```

- **`2px`** – Border width (e.g. `1px`, `3px`).
- **`solid`** – Line style: `solid`, `dashed`, `dotted`, `double`, etc.
- **`#334155`** – Border color.
- **`border-radius`** – Rounded corners (e.g. `6px`, `50%` for a circle).

You can also set **`border-top`**, **`border-left`**, etc., or **`border-width`**, **`border-style`**, **`border-color`** separately.

---

## Display

**display** controls how the element is laid out: inline (in a line), block (stacked), or inline-block (inline with a box).

```css
span { display: inline; }   /* Flows with text, no width/height */
div  { display: block; }    /* New line, full width by default */
span { display: inline-block; width: 80px; }  /* Inline but can have width/height */
```

- **`inline`** – Stays in the text flow. Ignores `width`/`height` and top/bottom margin.
- **`block`** – Starts on a new line; by default takes full width.
- **`inline-block`** – Flows inline but you can set `width`, `height`, and vertical margin.

Other values: **`none`** (hidden and removed from layout), **`flex`**, **`grid`**.

---

## Float

**float** moves an element to the left or right so content wraps around it. Often used for simple two-column layouts.

```css
.float-left {
    float: left;
    width: 45%;
}

.float-right {
    float: right;
    width: 45%;
}

.section-float::after {
    content: "";
    display: table;
    clear: both;
}
```

- **`float: left`** / **`float: right`** – Element shifts to that side; following content wraps beside it.
- **Clearfix** – The parent uses **`::after`** with **`clear: both`** so it wraps around the floated children and content below doesn’t overlap. **`clear: both`** on the next element also works.

Modern layouts often use **Flexbox** or **Grid** instead of float.

---

## Height & Width

**height** and **width** set the size of the content area. **box-sizing** controls whether padding/border are included.

```css
.fixed-box {
    width: 200px;
    height: 80px;
    padding: 1rem;
    box-sizing: border-box;
}

.max-width-box {
    max-width: 300px;
}
```

- **`width`** / **`height`** – Fixed size (e.g. `px`, `rem`, `%`).
- **`max-width`** – Cap on width; useful for responsive text blocks.
- **`box-sizing: border-box`** – Width and height include padding and border, so the total size is predictable.

---

## Padding & Margin

- **padding** – Space **inside** the border (between content and border).
- **margin** – Space **outside** the border (between this element and others).

```css
.padding-demo {
    padding: 20px;
    margin: 10px;
}

.margin-demo {
    margin: 20px 10px;   /* top/bottom 20px, left/right 10px */
    padding: 10px;
}
```

Shorthand: **`margin: 20px 10px`** = top/bottom 20px, left/right 10px. **`margin: 10px 20px 10px 20px`** = top, right, bottom, left (clockwise).

---

## Visibility

**visibility** hides the element visually but can keep its space in the layout.

```css
.visible { visibility: visible; }   /* default */
.hidden  { visibility: hidden; }    /* invisible, space remains */
```

- **`visible`** – Normal (default).
- **`hidden`** – Element is not seen but still takes up space (unlike **`display: none`**, which removes it from the layout).

Use **`visibility: hidden`** when you need to reserve space; use **`display: none`** when you want the element gone from the flow.

---

## Summary

| Property / topic | Purpose |
|------------------|---------|
| **border** | Line around the element; shorthand `width style color`; **border-radius** for rounded corners. |
| **display** | Layout type: **inline**, **block**, **inline-block**, **none**, or **flex**/**grid**. |
| **float** | Move element left/right; use **clear** or clearfix so content below doesn’t wrap wrongly. |
| **height / width** | Size of the box; **max-width** for caps; **box-sizing: border-box** to include padding/border. |
| **padding** | Space inside the border. |
| **margin** | Space outside the border. |
| **visibility** | **visible** or **hidden**; hidden still takes space (unlike **display: none**). |

Open `FRD-09-borders.html` in a browser to see all examples.
