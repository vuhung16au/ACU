# Quick Start Guide: 05.SidebarNavigation

## Prerequisites

Before running the project, confirm:

- .NET 10.0 SDK is installed
- You are in the `05.NavigationRouting` workspace

Check SDK version:

```bash
dotnet --version
```

Expected output: `10.0.x`

## Step 1: Run the project

```bash
cd /Users/vuhung/00.Work/02.ACU/github/ITEC323/05.NavigationRouting
dotnet run
```

Open the local URL shown in the terminal.

## Step 2: Verify the desktop sidebar

On a desktop-width browser:

1. Confirm the sidebar stays visible on the left while the content area appears on the right.
2. Confirm the sections `Foundation`, `Product Catalog`, `Publishing`, and `Support` are visible.
3. Open and close each collapsible section.
4. Visit a page such as `/Products/Category/Computers` and confirm the matching sidebar link is highlighted.

## Step 3: Verify the nested navigation

Use these paths:

1. `/Docs/GettingStarted`
2. `/Docs/Guides/SidebarPatterns`
3. `/Docs/Guides/OffcanvasNavigation`
4. `/Products/Category/Computers`
5. `/Blog/Archive/2026`

Expected result:

- `Foundation -> Guides` expands automatically on the Docs guide pages.
- The active page uses the highlighted sidebar style.
- The current section remains open.

## Step 4: Verify mobile Offcanvas behavior

Narrow the browser width until the desktop sidebar disappears.

Then:

1. Click the `Menu` button in the top bar.
2. Confirm the sidebar opens from the left as a Bootstrap Offcanvas panel.
3. Expand `Foundation` and then `Guides`.
4. Select `Offcanvas Navigation` and confirm the page opens with the correct active highlight.

## Step 5: Read the code in this order

1. `Pages/Shared/_Layout.cshtml`
2. `Pages/Shared/_SidebarNavigation.cshtml`
3. `Pages/Index.cshtml`
4. `Pages/Docs/Guides/SidebarPatterns.cshtml`
5. `wwwroot/css/site.css`
