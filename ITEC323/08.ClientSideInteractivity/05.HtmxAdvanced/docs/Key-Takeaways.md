# Key Takeaways - 05.HtmxAdvanced

## 1. Debounced search avoids request spam

Use `hx-trigger="keyup changed delay:400ms"` on an input to wait until the user pauses typing.
This reduces server calls and produces smoother UX.

## 2. Infinite scrolling can be pure HTML

A "load more" sentinel with `hx-trigger="revealed"` loads the next page when visible.
No custom intersection observer code is needed.

## 3. One HTMX response can update many regions

`hx-swap-oob` lets the response update elements outside the primary target.
This is ideal for syncing counters, timestamps, and summary cards.

## 4. Polling is declarative

`hx-trigger="every 8s"` creates periodic refresh for dashboard widgets.
The server can return HTML fragments that HTMX swaps in place.

## 5. Keep server fragments focused

Small, purpose-built partials (`_FeedChunk`, `_FeedItem`, `_HealthPanel`) are easier to reason about,
reuse, and debug than large mixed responses.

## 6. Advanced HTMX still fits Razor Pages naturally

Page handlers (`OnGetFeed`, `OnGetHealth`) return standard partials.
The same patterns from basic CRUD scale to richer interactions.
