# Key Takeaways — 04.HtmxPartialUpdates

## 1. HTMX turns HTML attributes into HTTP requests

Any element can make a request and update the page.
You do not write JavaScript — you declare intent in attributes:

```html
<button hx-get="/HtmxLab?handler=EditRow&id=1"
        hx-target="#student-row-1"
        hx-swap="outerHTML">Edit</button>
```

## 2. Partial views are just HTML fragments

A Razor Pages handler method can return `Partial(viewName, model)`, which renders
a `.cshtml` file without the shared layout. HTMX inserts that fragment directly
into the page — no JSON, no client-side rendering.

```csharp
public IActionResult OnGetEditRow(int id)
{
    var student = _studentService.GetById(id);
    return Partial("Partials/_StudentEditRow", student);
}
```

## 3. `hx-swap` controls how the response is inserted

| Value | Meaning |
| --- | --- |
| `innerHTML` | Replace the content inside the target (default) |
| `outerHTML` | Replace the target element itself |
| `beforeend` | Append inside the target after existing children |
| `afterbegin` | Prepend inside the target before existing children |
| `delete` | Remove the target element (no response body needed) |

Delete uses `outerHTML` with an empty 200 response — the row element is replaced with nothing, so it vanishes.

## 4. `hx-indicator` shows a loading spinner automatically

Add a spinner element with class `htmx-indicator` and point to it with `hx-indicator`:

```html
<span id="table-spinner" class="htmx-indicator spinner"></span>

<tbody hx-indicator="#table-spinner"> … </tbody>
```

HTMX sets `opacity: 1` on the indicator while the request is in-flight, and `opacity: 0` when it completes.

## 5. `hx-confirm` prevents accidental deletes

```html
<button hx-post="…" hx-confirm="Delete Ava? This cannot be undone.">Delete</button>
```

The browser shows a native confirm dialog. If the user cancels, the request is never sent.

## 6. HTMX vs Alpine.js — choose the right tool

| Scenario | Better choice |
| --- | --- |
| Fetching and displaying server data | **HTMX** |
| Inline editing a table row | **HTMX** |
| Dropdown, modal, accordion (no server) | **Alpine.js** |
| Form with live validation feedback | **Alpine.js** or **HTMX** |
| Real-time chat or live dashboard | Blazor or WebSockets |
