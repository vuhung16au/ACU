# QUICKSTART - 05.HtmxAdvanced

## Prerequisites

| Tool | Version |
| --- | --- |
| .NET SDK | 8.0 or later (project targets .NET 10) |
| Browser | Chrome, Edge, Firefox, or Safari |
| Internet | Required - HTMX is loaded from CDN |

No Node.js, no npm, no jQuery, and no Bootstrap.

## Run the project

```bash
cd 08.ClientSideInteractivity/05.HtmxAdvanced

dotnet run
```

Then open the URL shown in the terminal.

## Build only (no run)

```bash
dotnet build
```

## URLs once running

| URL | What you see |
| --- | --- |
| `/` | Project overview and learning outcomes |
| `/HtmxAdvancedLab` | Advanced HTMX lab (search, infinite scroll, polling, OOB swaps) |
| `/Privacy` | Privacy policy stub |

## Classroom walkthrough (5 steps)

1. Open `/HtmxAdvancedLab` and inspect the Network tab.
2. Type a search term and observe debounced requests.
3. Scroll until the load-more sentinel appears and auto-loads the next page.
4. Observe summary cards updating via `hx-swap-oob` from the same feed response.
5. Watch polling metrics refresh every 8 seconds without reloading the page.

## Troubleshooting

| Problem | Fix |
| --- | --- |
| Nothing updates when typing | Verify HTMX script loads in `Pages/Shared/_Layout.cshtml` |
| Infinite scroll does not continue | Confirm load-more sentinel exists in `_FeedChunk.cshtml` and response has `HasMore=true` |
| Polling panel stays empty | Check `/HtmxAdvancedLab?handler=Health` returns 200 and HTML fragment |
| `dotnet run` port conflict | Kill existing process on the port or run with `ASPNETCORE_URLS=http://localhost:5165 dotnet run` |
