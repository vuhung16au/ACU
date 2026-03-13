# 09. Comprehensive App

This project is a beginner-friendly comparison hub that ties together all of the Week 8
client-side interactivity samples. It focuses on **when** to choose each tool (vanilla
JavaScript, Alpine.js, HTMX, Blazor, React, and Vue) rather than re-implementing every
example in one codebase.

## Learning goals

- Understand the strengths and trade-offs of each client-side approach.
- Decide when Razor Pages + vanilla JavaScript is enough.
- Recognise when to reach for Alpine.js or HTMX instead of a full SPA.
- See where Blazor, React, and Vue make sense for richer experiences.
- Connect this comparison back to the main module FRD requirements.

## What this project contains

- A small ASP.NET Core Razor Pages app.
- A single overview page that:
  - Describes each approach in plain language.
  - Links conceptually to the dedicated projects:
    - `01.VanillaJsBasics`
    - `02.PromisesAsyncAwait`
    - `03.AlpineJsComponents`
    - `04.HtmxPartialUpdates`
    - `05.HtmxAdvanced`
    - `06.BlazorInteractive`
    - `07.ReactBasics`
    - `08.VueBasics`
  - Suggests practice exercises where students implement the same feature multiple ways.
- Supporting docs in `QUICKSTART.md` and `docs/Key-Takeaways.md`.

## Project structure

```text
09.ComprehensiveApp/
├── Pages/
│   ├── Index.cshtml
│   ├── Index.cshtml.cs
│   ├── _ViewImports.cshtml
│   ├── _ViewStart.cshtml
│   └── Shared/
│       └── _Layout.cshtml
├── wwwroot/
│   └── css/
│       └── site.css
├── docs/
│   └── Key-Takeaways.md
├── 09.ComprehensiveApp.csproj
├── Program.cs
├── QUICKSTART.md
└── README.md
```

## How it relates to the FRD

This project is designed to support **FR9: Framework Comparison** from the module FRD:

- Students compare Blazor, React, and Vue across key dimensions.
- Students understand use cases for each approach.
- Students analyse trade-offs such as performance, learning curve, and ecosystem.
- Students are encouraged to build the **same feature** multiple ways and reflect on the result.

