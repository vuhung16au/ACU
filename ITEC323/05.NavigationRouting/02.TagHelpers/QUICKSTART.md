# Quick Start Guide: 02.TagHelpers

## Prerequisites

Before running the project, confirm:

- .NET 10.0 SDK is installed
- You can run terminal commands from the repository root

Check SDK version:

```bash
dotnet --version
```

Expected: `10.0.x`

## Step 1: Go to the Project

```bash
cd 05.NavigationRouting/02.TagHelpers
```

## Step 2: Restore and Build

```bash
dotnet restore
dotnet build
```

Build must complete with no errors.

## Step 3: Run

```bash
dotnet run --project /Users/vuhung/00.Work/02.ACU/github/ITEC323/05.NavigationRouting/02.TagHelpers/02.TagHelpers.csproj
```

Then open the URL shown in the terminal.

## Step 4: Verify FR2 Acceptance Criteria

Open the app home page and test these links:

1. `asp-page` sample:
   Click About in the top navigation.

2. `asp-route-id` sample:
   Go to Products and open any product details.

3. Multiple route parameters:
   Go to Blog and open a post using year/month/slug.

4. `asp-page-handler` sample:
   Use the Handler Demo link in the top navigation or open preview from Products.

## Step 5: URLs to Check Manually

1. `/`
2. `/About`
3. `/Products`
4. `/Products/Details/2`
5. `/Products/Edit/2`
6. `/Products/Edit/2?handler=Preview`
7. `/Blog/2026/3/getting-started-with-routing`

## Step 6: Read Code in This Order

1. `Pages/Shared/_Layout.cshtml` (navigation links + active styling)
2. `Pages/Index.cshtml` (Tag Helper comparison and examples)
3. `Pages/Products/Index.cshtml` (single route param + handler links)
4. `Pages/Products/Edit.cshtml` and `Edit.cshtml.cs` (named handler)
5. `Pages/Blog/Index.cshtml` (multiple route parameters)

## Troubleshooting

### Build fails

Run:

```bash
dotnet --list-sdks
```

Ensure .NET 10 is available.

### Port conflict

Run with another URL:

```bash
dotnet run --project /Users/vuhung/00.Work/02.ACU/github/ITEC323/05.NavigationRouting/02.TagHelpers/02.TagHelpers.csproj --urls "http://localhost:5056"
```

### Handler link does not work

Check:

1. Link uses `asp-page-handler`.
2. Page model contains matching handler name (for example `OnGetPreview`).
3. Required route values are included with `asp-route-*`.
