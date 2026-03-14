# Quick Start Guide: 01.BasicRouting

## Prerequisites

Before running the project, make sure you have:

- .NET 10.0 SDK installed
- A code editor such as VS Code, Visual Studio, or Rider
- A terminal window

Verify your SDK version:

```bash
dotnet --version
```

Expected output: `10.0.x`

## Step 1: Open the Project Folder

From the repository root:

```bash
cd 05.NavigationRouting/01.BasicRouting
```

## Step 2: Restore Dependencies

```bash
dotnet restore
```

## Step 3: Build the Project

```bash
dotnet build
```

The build should finish without errors.

## Step 4: Run the Application

```bash
cd 05.NavigationRouting/01.BasicRouting
dotnet run
```

Open the browser when the terminal shows the local URL.

Typical addresses:

- `https://localhost:5001`
- `http://localhost:5000`

## Step 5: Explore the Routing Examples

Open these sample URLs in the browser:

1. `/`
2. `/About`
3. `/Products`
4. `/Products/Details/2`
5. `/Products/Edit`
6. `/Products/Edit/2`
7. `/Blog/2026/3/getting-started-with-routing`
8. `/Courses/Lookup/ITEC323`

## Step 6: What to Observe

As you click around, notice these points:

1. The `Pages` folder controls the default URL structure.
2. `Index.cshtml` becomes the default page for a folder.
3. Route values appear in the page model `OnGet(...)` method.
4. Invalid route values are rejected before the page loads.
5. Friendly URLs are easier to read than query strings.

## Step 7: Test Route Constraints

Try valid and invalid URLs.

### Integer Constraint

Valid:

```text
/Products/Details/1
```

Invalid:

```text
/Products/Details/abc
```

### Optional Parameter

Both are valid:

```text
/Products/Edit
/Products/Edit/3
```

### Regex Constraint

Valid:

```text
/Courses/Lookup/ITEC323
```

Invalid:

```text
/Courses/Lookup/abc123
```

### Custom Slug Constraint

Valid:

```text
/Blog/2026/3/getting-started-with-routing
```

Invalid:

```text
/Blog/2026/3/Bad Slug!
```

## Step 8: Read the Code in This Order

1. `Program.cs`
2. `Pages/Index.cshtml`
3. `Pages/Products/Details.cshtml`
4. `Pages/Products/Edit.cshtml`
5. `Pages/Blog/Post.cshtml`
6. `Routing/SlugRouteConstraint.cs`

## Troubleshooting

### Build Fails

Check that .NET 10 is installed:

```bash
dotnet --list-sdks
```

### Port Already in Use

Run on a different port:

```bash
dotnet run --project 01.BasicRouting/01.BasicRouting.csproj --urls "http://localhost:5055"
```

### Route Returns 404

Check these common issues:

1. The page has `@page` at the top.
2. The route template matches the URL.
3. The route value satisfies the constraint.
4. The page exists under the `Pages` folder.
