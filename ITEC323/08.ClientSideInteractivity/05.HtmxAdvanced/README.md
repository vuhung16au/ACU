# 05.HtmxAdvanced

Week 8, Project 05 - **Advanced HTMX Patterns**  
Part of the [08.ClientSideInteractivity](../) module for ITEC323 at ACU.

## What this project teaches

This project extends basic HTMX partial updates into richer interaction patterns used in real apps.

| Pattern | What students learn |
| --- | --- |
| Live Search | Debounced requests with `hx-trigger` |
| Infinite Scroll | Progressive paging with `hx-trigger="revealed"` |
| Polling | Periodic refresh using `hx-trigger="every 8s"` |
| Out-of-band swaps | Update multiple regions via `hx-swap-oob` |

## Demo

![HTMX Advanced Demo](images/htmlx-advanced-demo.gif)

## Learning Objectives

By the end of this project students will be able to:

1. Build debounced live search with HTMX without writing custom JavaScript.
2. Implement infinite scrolling using server-side pagination and revealed sentinels.
3. Poll server endpoints for periodic dashboard refresh.
4. Use out-of-band swaps to update summary cards outside the main response target.
5. Compare basic HTMX CRUD patterns vs advanced interaction patterns.

## Project structure

```text
05.HtmxAdvanced/
|- 05.HtmxAdvanced.csproj
|- Program.cs                          # Service registration, middleware
|- Models/
|  |- FeedItem.cs                      # Feed card model for search/scroll demo
|  |- FeedQueryResult.cs               # Paged query result metadata
|  '- HealthSnapshot.cs                # Polling dashboard snapshot model
|- Services/
|  '- FeedService.cs                   # In-memory data + paging/search logic
|- Pages/
|  |- Index.cshtml / .cs               # Module landing page
|  |- HtmxAdvancedLab.cshtml / .cs     # Advanced HTMX lab + handlers
|  |- Partials/
|  |  |- _FeedChunk.cshtml             # Search/scroll response fragment
|  |  |- _FeedItem.cshtml              # Individual feed card fragment
|  |  '- _HealthPanel.cshtml           # Polling dashboard fragment
|  '- Shared/
|     '- _Layout.cshtml                # Shared layout - includes HTMX via CDN
|- docs/
|  '- Key-Takeaways.md
'- wwwroot/css/site.css                # Custom stylesheet (no Bootstrap)
```

## How the advanced flows work

```text
Live Search
  -> user types in search box
  -> hx-trigger waits 400ms (debounce)
  -> GET /HtmxAdvancedLab?handler=Feed&q=term&page=1
  -> server returns _FeedChunk partial
  -> HTMX swaps #feed-results innerHTML

Infinite Scroll
  -> user scrolls to "load more" sentinel
  -> hx-trigger="revealed" fires
  -> GET /HtmxAdvancedLab?handler=Feed&page=2 (+ current q)
  -> server returns next _FeedChunk with another sentinel (if needed)

Out-of-Band Update
  -> same _FeedChunk response includes elements with hx-swap-oob
  -> HTMX updates #results-summary, #results-count, #results-updated
  -> multiple page areas refresh from one response

Polling
  -> hx-trigger="load, every 8s"
  -> GET /HtmxAdvancedLab?handler=Health
  -> server returns _HealthPanel partial
  -> HTMX replaces #health-panel innerHTML
```

## Technology stack

- **Runtime:** .NET 10 (Razor Pages)
- **HTMX:** 1.9.12 via CDN - `https://unpkg.com/htmx.org@1.9.12/dist/htmx.min.js`
- **CSS:** Custom stylesheet, no Bootstrap, no jQuery

## See also

- [QUICKSTART.md](QUICKSTART.md) - how to run the project
- [docs/Key-Takeaways.md](docs/Key-Takeaways.md) - what to remember
- [HTMX Documentation](https://htmx.org/docs/)
