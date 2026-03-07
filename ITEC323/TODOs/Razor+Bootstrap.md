Using Bootstrap with ASP.NET Core Razor Pages is incredibly straightforward because **the default Razor Pages template includes Bootstrap out of the box!** Depending on your needs, you can either use the pre-installed version, update it to the latest version via a CDN, or manage it via a package manager. Here is a guide covering the most common approaches.

### Approach 1: Using the Default Template (Easiest)

When you create a standard Razor Pages application, Microsoft automatically includes Bootstrap for you.

**1. Create the project:**
Open your terminal and run:

```bash
dotnet new webapp -n RazorBootstrapApp
cd RazorBootstrapApp

```

**2. Where is Bootstrap located?**

* **Physical files:** If you look in your project under `wwwroot/lib/bootstrap`, you will see the CSS and JS files already downloaded.
* **Link location:** Open `Pages/Shared/_Layout.cshtml`. In the `<head>` section, you will see it linked:
```html
<link rel="stylesheet" href="~/lib/bootstrap/dist/css/bootstrap.min.css" />

```


And at the bottom of the `<body>`, the JavaScript is linked:
```html
<script src="~/lib/bootstrap/dist/js/bootstrap.bundle.min.js"></script>

```



You can immediately start using Bootstrap classes in your `.cshtml` files!

---

### Approach 2: Using a CDN (Best for using the latest version)

The default .NET templates often ship with a slightly older version of Bootstrap (usually a stable 5.x version). If you want the absolute latest version without managing local files, swapping to a Content Delivery Network (CDN) is the best route.

**1. Open your Layout file:**
Navigate to `Pages/Shared/_Layout.cshtml`.

**2. Replace the CSS:**
Find the local bootstrap `<link>` tag in the `<head>` and replace it with the latest CDN link (currently v5.3.x):

```html
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    
    <link rel="stylesheet" href="~/css/site.css" asp-append-version="true" />
    </head>

```

**3. Replace the JavaScript:**
Scroll to the bottom of the file, just before `</body>`, and replace the local script with the CDN script:

```html
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmxc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    
    <script src="~/js/site.js" asp-append-version="true"></script>
    @await RenderSectionAsync("Scripts", required: false)
</body>

```

*(Note: You can safely delete the `wwwroot/lib/bootstrap` folder if you switch to the CDN).*

---

### Step 3: Test It Out

Now you can write some standard Bootstrap HTML in your pages. Open `Pages/Index.cshtml` and replace the contents with a quick test:

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
                    <p class="card-text">If this card looks nicely formatted with a shadow and rounded edges, your Bootstrap integration is successful.</p>
                    <a href="#" class="btn btn-success">Action Button</a>
                </div>
            </div>
        </div>
    </div>
</div>

```

Run your app using `dotnet run` or `dotnet watch`, and you should see a perfectly styled Bootstrap card on your screen.

---

Would you like me to show you how to use **LibMan** (Library Manager) to install and manage specific versions of Bootstrap locally within Visual Studio or the .NET CLI?