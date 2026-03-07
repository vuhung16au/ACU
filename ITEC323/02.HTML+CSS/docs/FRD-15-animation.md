# FRD-15-animation – Code Explanation

This document explains **CSS animations** in FRD-15: **`@keyframes`** and the **`animation`** shorthand.

---

## File overview

- **`FRD-15-animation.html`** – Four boxes, each with a different animation class.
- **`FRD-15-animation.css`** – Defines **@keyframes** for fade-in, slide+pulse, spin, and color change; applies them with the **animation** property.

---

## @keyframes

**@keyframes** defines the steps of an animation. You give it a name and describe how properties should change at certain points (percentages or **from**/**to**).

Example:

```css
@keyframes fadeIn {
    from {
        opacity: 0;
    }
    to {
        opacity: 1;
    }
}
```

- **fadeIn** – Name of the animation.
- **from** – Start (same as **0%**). **to** – End (same as **100%**).
- Here opacity goes from 0 (invisible) to 1 (fully visible). You can use **0%**, **50%**, **100%** etc. for more steps.

---

## The animation property

To run a keyframes animation on an element, use **animation** (or the longhand properties).

```css
.fade-in {
    animation: fadeIn 1.5s ease-in-out;
}
```

Shorthand: **animation: name duration timing-function delay iteration-count direction fill-mode;**

- **fadeIn** – Name of the @keyframes rule.
- **1.5s** – Duration (e.g. **1s**, **500ms**).
- **ease-in-out** – Timing function (how speed changes). Others: **linear**, **ease**, **ease-in**, **ease-out**.
- Optional: **infinite** (repeat forever), **alternate** (reverse on every other run), delay (e.g. **1s** before start).

---

## Examples in this file

### Fade in

- **@keyframes fadeIn**: opacity 0 → 1.
- **animation: fadeIn 1.5s ease-in-out** – Runs once when the page loads (no **infinite**).

### Slide and pulse

- **@keyframes slidePulse**: at 0% and 100% – no move, scale 1; at 50% – translateX(20px), scale(1.1).
- **animation: slidePulse 2s ease-in-out infinite** – Repeats forever.

### Spin

- **@keyframes spin**: rotate from 0deg to 360deg.
- **animation: spin 3s linear infinite** – Continuous rotation. **linear** keeps speed constant.

### Color change

- **@keyframes colorChange**: background-color purple at 0% and 100%, pink at 50%.
- **animation: colorChange 2s ease-in-out infinite** – Color cycles forever.

---

## Summary

| Topic | Purpose |
|-------|---------|
| **@keyframes name** | Define keyframes with **from**/**to** or **0%** … **100%**; set CSS properties at each step. |
| **animation** | Attach a keyframes animation: **name**, **duration**, **timing-function**; optionally **infinite**, **delay**. |
| **transform** | Use in keyframes for move (**translateX/Y**), scale (**scale**), rotate (**rotate**) without layout shift. |
| **opacity / color** | Animate opacity or color for fade and color-change effects. |

The HTML links to **`../css/FRD-15-animation.css`**. Open the page in a browser to see the animations; the fade-in runs once, the others repeat.
