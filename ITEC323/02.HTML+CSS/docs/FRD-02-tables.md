# FRD-02-tables.html – Code Explanation

This document explains how the HTML table page is built and what each part does.

---

## File overview

`FRD-02-tables.html` is an HTML5 page that displays a **table** with six columns: **Name**, **Age**, **Gender**, **Email**, **Phone**, and **Address**. The table has a header row and sample data rows.

---

## Document structure

The top of the file matches a standard HTML5 page:

- **`<!DOCTYPE html>`** – Declares the document as HTML5.
- **`<html lang="en">`** – Root element with language set to English.
- **`<head>`** – Contains:
  - **`<meta charset="UTF-8">`** – Character encoding.
  - **`<meta name="viewport" ...>`** – Mobile-friendly viewport.
  - **`<title>HTML Tables - FRD-02</title>`** – Browser tab title.
- **`<body>`** – Holds the visible content: a heading and the table.

---

## The table

HTML tables are built from these elements:

| Element   | Purpose |
|----------|---------|
| `<table>` | Wraps the entire table. |
| `<thead>` | Header section: column titles. |
| `<tbody>` | Body section: data rows. |
| `<tr>`    | A single **row** (horizontal line). |
| `<th>`    | A **header cell** in the header row (bold by default). |
| `<td>`    | A **data cell** in the body. |

### Header row (`<thead>`)

- One **`<tr>`** (row) inside **`<thead>`**.
- Six **`<th>`** cells: Name, Age, Gender, Email, Phone, Address.
- **`<th>`** is used for column headers so browsers and screen readers understand the structure.

### Data rows (`<tbody>`)

- Each **`<tr>`** is one person’s row.
- Each **`<td>`** is one value (name, age, gender, email, phone, address).
- The number of **`<td>`** cells per row matches the number of **`<th>`** columns (six).

---

## Summary

- **`<table>`** – Defines the table.
- **`<thead>`** – Contains the header row with **`<th>`** for each column name.
- **`<tbody>`** – Contains data rows; each **`<tr>`** has six **`<td>`** cells.

Using **`<thead>`** and **`<tbody>`** keeps the structure clear and helps with styling and accessibility. You can add more **`<tr>`** blocks inside **`<tbody>`** to add more rows.
