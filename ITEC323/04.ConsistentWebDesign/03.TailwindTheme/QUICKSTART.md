# Quick Start - Tailwind Theme

## Prerequisites

- .NET 10 SDK
- Code editor (VS Code, Visual Studio, or Rider)
- Internet connection (Tailwind CDN)

## Step 1: Navigate to Project

```bash
cd 04.ConsistentWebDesign/03.TailwindTheme
```

## Step 2: Build

```bash
dotnet build
```

Expected: `Build succeeded.`

## Step 3: Run

```bash
dotnet run
```

Expected: App listens on localhost.

## Step 4: Explore Pages

- `/` Home: Tailwind integration overview
- `/Components` Components: alerts, buttons, cards, badges, form
- `/Grid` Layout Utilities: grid and responsive breakpoint examples

## Step 5: Quick Experiments

1. In `Pages/Shared/_Layout.cshtml`, change `bg-slate-50` to `bg-white` on `<body>`.
2. In `Pages/Index.cshtml`, change `bg-sky-700` to `bg-emerald-700` in hero section.
3. In `Pages/Grid.cshtml`, change `md:grid-cols-3` to `md:grid-cols-2` and reload.

## Troubleshooting

- If styles do not load, check internet connectivity (CDN required).
- If port is busy, stop the old process and run again.
