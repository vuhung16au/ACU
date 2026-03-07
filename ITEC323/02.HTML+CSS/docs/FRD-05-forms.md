# FRD-05-forms.html – Code Explanation

This document explains how **HTML forms** are used on the page: the form container, common input types, and buttons.

---

## File overview

`FRD-05-forms.html` demonstrates:

- **`<form>`** – Wraps all form controls and defines where and how data is sent
- **Text inputs** – `text`, `email`
- **Dropdown** – `<select>` and `<option>`
- **Radio buttons** – Single choice from a group
- **Checkbox** – Optional or required agreement
- **Textarea** – Multi-line text
- **Buttons** – Submit and reset

---

## Document setup

Standard HTML5 structure: `<!DOCTYPE html>`, `<html lang="en">`, `<head>` (charset, viewport, title), and `<body>` with a heading and one `<form>`.

---

## The form element

```html
<form action="#" method="get">
```

- **`<form>`** – Container for all form fields. When the user clicks Submit, the browser sends the form data.
- **`action="#"`** – URL that receives the data. `#` means “this page” (often replaced later with a server URL).
- **`method="get"`** – Data is sent as query parameters in the URL. Use **`method="post"`** for sensitive or long data (e.g. passwords, long text).

---

## Labels and text inputs

### Label and text box

```html
<label for="name">Name:</label>
<input type="text" id="name" name="name" placeholder="Enter your name">
```

- **`<label for="name">`** – Associates the label with the input whose **`id="name"`**. Clicking the label focuses the input (good for accessibility).
- **`<input type="text">`** – Single-line text field.
- **`name="name"`** – Name of the field when the form is submitted. The server receives key–value pairs using these names.
- **`id="name"`** – Must match **`for="name"`** on the label.
- **`placeholder="..."`** – Hint text shown when the field is empty (optional).

### Email input

```html
<input type="email" id="email" name="email" placeholder="you@example.com" required>
```

- **`type="email"`** – Same as text but browsers can validate email format and show a keyboard suited to email on mobile.
- **`required`** – Browser will block submit until this field is filled (HTML5 validation).

---

## Dropdown (select)

```html
<select id="language" name="language">
    <option value="">-- Choose one --</option>
    <option value="csharp">C#</option>
    <option value="java">Java</option>
    <option value="python">Python</option>
</select>
```

- **`<select>`** – Dropdown list. **`name="language"`** is the name sent with the form.
- **`<option value="csharp">`** – One choice. **`value`** is what is submitted; the text between the tags (e.g. “C#”) is what the user sees.
- The first option often has **`value=""`** as a “no selection” or prompt option.

---

## Radio buttons

```html
<label><input type="radio" name="newsletter" value="yes"> Yes</label>
<label><input type="radio" name="newsletter" value="no"> No</label>
```

- **`type="radio"`** – Only one option can be selected in the group.
- **`name="newsletter"`** – Same **`name`** for all options in the group so they act together.
- **`value="yes"`** / **`value="no"`** – Value sent when that option is selected.
- Wrapping each option in **`<label>`** makes the text clickable and improves accessibility.

---

## Checkbox

```html
<input type="checkbox" name="terms" value="accepted" required>
```

- **`type="checkbox"`** – Can be checked or unchecked.
- **`name="terms"`** – Sent only when the box is checked (with **`value="accepted"`**).
- **`required`** – User must check the box before submitting (e.g. for “I agree” boxes).

---

## Textarea

```html
<textarea id="message" name="message" rows="4" cols="40" placeholder="Your message..."></textarea>
```

- **`<textarea>`** – Multi-line text. Content goes **between** the opening and closing tags (or leave empty).
- **`name="message"`** – Name used when the form is submitted.
- **`rows="4"`** and **`cols="40"`** – Approximate size in lines and characters (can be overridden with CSS).

---

## Buttons

```html
<button type="submit">Submit</button>
<button type="reset">Reset</button>
```

- **`type="submit"`** – Sends the form to the **`action`** URL using the form **`method`**.
- **`type="reset"`** – Clears all form fields back to their initial values (no data is sent).

---

## Summary

| Element / attribute | Purpose |
|--------------------|---------|
| **`<form action="..." method="...">`** | Wraps the form; defines where and how data is sent |
| **`<label for="id">`** | Links label to input; use matching **`id`** on the input |
| **`<input type="text">`** / **`type="email">`** | Single-line text; **`name`** is sent with the form |
| **`<select>`** + **`<option>`** | Dropdown; **`value`** on the chosen **`<option>`** is submitted |
| **`<input type="radio">`** | One choice per group; same **`name`** for the group |
| **`<input type="checkbox">`** | On/off; **`required`** for “must agree” |
| **`<textarea>`** | Multi-line text; **`name`** is sent with the form |
| **`<button type="submit">`** | Sends the form; **`type="reset"`** clears it |

With **`action="#"`** and **`method="get"`**, submitting this form will only reload the page and show the data in the URL; a real application would use a server-side URL and often **`method="post"`** to process the data.
