# Bootstrap Setup Guide for ASP.NET Core Razor Pages

Using Bootstrap with ASP.NET Core Razor Pages is straightforward because **the default Razor Pages template includes Bootstrap out of the box.** Depending on your needs, you can use the pre-installed version or update it via a CDN.

---

## Prerequisites

- .NET SDK installed
- A terminal or command prompt

---

## Approach 1: Using the Default Template (Easiest)

When you create a standard Razor Pages application, Microsoft automatically includes Bootstrap for you.

### Step 1: Create the Project

```bash
dotnet new webapp -n RazorBootstrapApp
cd RazorBootstrapApp
```

### Step 2: Where is Bootstrap Located?

- **Physical files:** Under `wwwroot/lib/bootstrap/` — the CSS and JS files are already downloaded.
- **CSS link:** Open `Pages/Shared/_Layout.cshtml`. In the `<head>` section:

```html
<link rel="stylesheet" href="~/lib/bootstrap/dist/css/bootstrap.min.css" />
```

- **JS link:** At the bottom of `<body>`:

```html
<script src="~/lib/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
```

You can immediately start using Bootstrap classes in your `.cshtml` files — no extra setup needed.

---

## Approach 2: Using a CDN (Latest Version)

The default .NET templates often ship with a slightly older Bootstrap version. To use the latest version without managing local files, switch to a CDN.

### Step 1: Update the CSS Link

Open `Pages/Shared/_Layout.cshtml` and replace the local Bootstrap `<link>` tag with:

```html
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">

    <link rel="stylesheet" href="~/css/site.css" asp-append-version="true" />
</head>
```

### Step 2: Update the JS Link

Just before `</body>`, replace the local script with:

```html
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmxc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"></script>

    <script src="~/js/site.js" asp-append-version="true"></script>
    @await RenderSectionAsync("Scripts", required: false)
</body>
```

> **Tip:** You can safely delete the `wwwroot/lib/bootstrap/` folder once you switch to the CDN.

---

## Step 3: Test It Out

Open `Pages/Index.cshtml` and add some Bootstrap markup to verify it is working:

```html
@page
@model IndexModel
@{
    ViewData["Title"] = "Home page";
}

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8 text-center">
            <h1 class="display-4 text-primary mb-4">Welcome to Razor Pages</h1>

            <div class="card shadow-sm">
                <div class="card-body">
                    <h5 class="card-title">Bootstrap is Working!</h5>
                    <p class="card-text">
                        If this card looks nicely formatted with a shadow and rounded edges,
                        your Bootstrap integration is successful.
                    </p>
                    <a href="#" class="btn btn-success">Action Button</a>
                </div>
            </div>
        </div>
    </div>
</div>
```

Run the app:

```bash
dotnet run
```

You should see a styled Bootstrap card in your browser.

---

## Approach Comparison

| | Default Template | CDN |
|---|---|---|
| **Setup effort** | None | Replace two lines in `_Layout.cshtml` |
| **Bootstrap version** | Bundled (may be older) | Always latest 5.x |
| **Works offline** | Yes | No (requires internet) |
| **Best for** | Quick start, labs | Projects needing latest features |

---

## Related Reading

- [Key-Takeaways.md](Key-Takeaways.md) — Bootstrap theme key concepts
- [../../docs/css-frameworks-comparison.md](../../docs/css-frameworks-comparison.md) — Bootstrap vs Tailwind comparison

---

*Last updated: March 2026*
