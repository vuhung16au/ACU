# SEO & Sitemap Guide

## What is SEO?

**SEO (Search Engine Optimization)** makes your website easier for search engines (Google, Bing) to find, understand, and rank.

## Why Sitemaps Matter

A **sitemap.xml** file tells search engines:
- What pages exist on your site
- How pages relate to each other
- When pages were last updated
- Which pages are most important

**Result**: Better indexing = more visitors from search engines

## URL Best Practices for SEO

### ✅ SEO-Friendly URLs

```
Good Examples:
https://mysite.com/products/laptops
https://mysite.com/blog/2026/03/getting-started-with-dotnet
https://mysite.com/about-us
```

**Why they work**:
- Descriptive (humans understand them)
- Keywords included (search engines understand them)
- Clean structure (no clutter)
- Lowercase with hyphens

### ❌ SEO-Unfriendly URLs

```
Bad Examples:
https://mysite.com/page.aspx?id=123&cat=5
https://mysite.com/products_view/item_details_FINAL
https://mysite.com/P/12345
```

**Problems**:
- No keywords
- Hard to read
- Query strings less favored by search engines
- Cryptic IDs don't help rankings

## Implementing SEO-Friendly Routes

### Route Design

```csharp
// ❌ Bad: Query string
@page
// URL: /Products/Details?id=5

// ✅ Good: Route parameter
@page "{id:int}"
// URL: /Products/Details/5

// ✅ Better: Descriptive slug
@page "{id:int}/{slug}"
// URL: /Products/Details/5/laptop-dell-xps-15
```

### Slug Generation

```csharp
public string GenerateSlug(string title)
{
    return title
        .ToLower()
        .Replace(" ", "-")
        .Replace("&", "and")
        .Trim('-');
}

// "MacBook Pro 16\"" → "macbook-pro-16"
// "C# & .NET Guide" → "c-sharp-and-dotnet-guide"
```

### Implementation Example

```csharp
// Product.cs
public class Product
{
    public int Id { get; set; }
    public string Name { get; set; }
    public string Slug { get; set; }
}

// Details.cshtml
@page "{id:int}/{slug}"

// Details.cshtml.cs
public class DetailsModel : PageModel
{
    public Product Product { get; set; }
    
    public IActionResult OnGet(int id, string slug)
    {
        Product = _db.Products.Find(id);
        
        if (Product == null)
            return NotFound();
        
        // Redirect if slug doesn't match (handles old bookmarks)
        if (Product.Slug != slug)
            return RedirectToPage(new { id = id, slug = Product.Slug });
        
        return Page();
    }
}
```

### Link Generation

```html
<!-- Generates: /Products/Details/5/laptop-dell-xps-15 -->
<a asp-page="/Products/Details" 
   asp-route-id="@product.Id" 
   asp-route-slug="@product.Slug">
    @product.Name
</a>
```

## Sitemap.xml Generation

### Manual Sitemap

Create `wwwroot/sitemap.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
    <url>
        <loc>https://mysite.com/</loc>
        <lastmod>2026-03-15</lastmod>
        <changefreq>daily</changefreq>
        <priority>1.0</priority>
    </url>
    <url>
        <loc>https://mysite.com/about</loc>
        <lastmod>2026-02-01</lastmod>
        <changefreq>monthly</changefreq>
        <priority>0.8</priority>
    </url>
    <url>
        <loc>https://mysite.com/products</loc>
        <lastmod>2026-03-14</lastmod>
        <changefreq>weekly</changefreq>
        <priority>0.9</priority>
    </url>
</urlset>
```

**Fields**:
- `<loc>`: Page URL (required)
- `<lastmod>`: Last modified date (optional but recommended)
- `<changefreq>`: How often page changes (optional)
- `<priority>`: Page importance 0.0-1.0 (optional)

### Dynamic Sitemap

Create `Sitemap.cshtml`:

```csharp
@page
@model SitemapModel
@{
    Layout = null;
    Context.Response.ContentType = "application/xml";
}
<?xml version="1.0" encoding="UTF-8"?>
<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
    @foreach (var url in Model.Urls)
    {
        <url>
            <loc>@url.Location</loc>
            <lastmod>@url.LastModified.ToString("yyyy-MM-dd")</lastmod>
            <changefreq>@url.ChangeFrequency</changefreq>
            <priority>@url.Priority.ToString("F1")</priority>
        </url>
    }
</urlset>
```

```csharp
// Sitemap.cshtml.cs
public class SitemapModel : PageModel
{
    private readonly ApplicationDbContext _db;
    
    public List<SitemapUrl> Urls { get; set; }
    
    public void OnGet()
    {
        var baseUrl = "https://mysite.com";
        
        Urls = new List<SitemapUrl>();
        
        // Static pages
        Urls.Add(new SitemapUrl
        {
            Location = baseUrl + "/",
            LastModified = DateTime.Now,
            ChangeFrequency = "daily",
            Priority = 1.0
        });
        
        Urls.Add(new SitemapUrl
        {
            Location = baseUrl + "/About",
            LastModified = new DateTime(2026, 2, 1),
            ChangeFrequency = "monthly",
            Priority = 0.8
        });
        
        // Dynamic pages from database
        var products = _db.Products.ToList();
        foreach (var product in products)
        {
            Urls.Add(new SitemapUrl
            {
                Location = $"{baseUrl}/Products/Details/{product.Id}/{product.Slug}",
                LastModified = product.UpdatedAt,
                ChangeFrequency = "weekly",
                Priority = 0.7
            });
        }
    }
}

public class SitemapUrl
{
    public string Location { get; set; }
    public DateTime LastModified { get; set; }
    public string ChangeFrequency { get; set; }
    public double Priority { get; set; }
}
```

**Access**: `https://mysite.com/Sitemap`

### Submit to Search Engines

1. **Google Search Console**: https://search.google.com/search-console
   - Add property → Enter sitemap URL
2. **Bing Webmaster Tools**: https://www.bing.com/webmasters
   - Add site → Submit sitemap

## Robots.txt

Create `wwwroot/robots.txt`:

```
User-agent: *
Allow: /

Sitemap: https://mysite.com/Sitemap
```

**Rules**:
- `User-agent: *` = applies to all bots
- `Allow: /` = allow crawling entire site
- `Disallow: /admin/` = block admin section
- Always include sitemap location

## Meta Tags for SEO

In `_Layout.cshtml`:

```html
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    
    <!-- SEO Meta Tags -->
    <title>@ViewData["Title"] - MySite</title>
    <meta name="description" content="@ViewData["Description"]" />
    <meta name="keywords" content="@ViewData["Keywords"]" />
    <meta name="author" content="MySite Team" />
    
    <!-- Open Graph (Facebook, LinkedIn) -->
    <meta property="og:title" content="@ViewData["Title"]" />
    <meta property="og:description" content="@ViewData["Description"]" />
    <meta property="og:image" content="@ViewData["Image"]" />
    <meta property="og:url" content="@Context.Request.GetDisplayUrl()" />
    
    <!-- Twitter Card -->
    <meta name="twitter:card" content="summary_large_image" />
    <meta name="twitter:title" content="@ViewData["Title"]" />
    <meta name="twitter:description" content="@ViewData["Description"]" />
    <meta name="twitter:image" content="@ViewData["Image"]" />
</head>
```

In individual pages:

```csharp
// Index.cshtml.cs
public void OnGet()
{
    ViewData["Title"] = "Welcome to MySite";
    ViewData["Description"] = "Discover amazing products and services at MySite.";
    ViewData["Keywords"] = "e-commerce, products, shopping, online store";
    ViewData["Image"] = "https://mysite.com/images/og-image.jpg";
}
```

## Canonical URLs

Prevent duplicate content issues:

```html
<link rel="canonical" href="https://mysite.com/products" />
```

Use when:
- Same content accessible via multiple URLs
- Query string parameters create duplicates
- HTTP and HTTPS both available

## Structured Data (Schema.org)

Help search engines understand your content:

```html
<script type="application/ld+json">
{
  "@@context": "https://schema.org",
  "@@type": "Product",
  "name": "Laptop XYZ",
  "image": "https://mysite.com/images/laptop.jpg",
  "description": "High-performance laptop for professionals",
  "brand": {
    "@@type": "Brand",
    "name": "TechBrand"
  },
  "offers": {
    "@@type": "Offer",
    "price": "1299.99",
    "priceCurrency": "USD",
    "availability": "https://schema.org/InStock"
  }
}
</script>
```

## Redirects (301 vs 302)

### Permanent Redirect (301)

Use when content permanently moved:

```csharp
public IActionResult OnGet()
{
    // Old URL: /old-page
    // New URL: /new-page
    return RedirectToPagePermanent("/NewPage");
}
```

**Effect**: Transfers SEO value (page rank) to new URL

### Temporary Redirect (302)

Use for temporary changes:

```csharp
public IActionResult OnGet()
{
    return RedirectToPage("/Maintenance");
}
```

**Effect**: Preserves SEO value on original URL

## SEO Checklist

✅ **URL Structure**:
- Descriptive keywords in URLs
- Lowercase with hyphens
- Route parameters instead of query strings
- Short and readable

✅ **Sitemap**:
- sitemap.xml exists
- Includes all important pages
- Submitted to search engines
- Updates when content changes

✅ **Meta Tags**:
- Unique title per page (50-60 chars)
- Descriptive meta descriptions (150-160 chars)
- Open Graph tags for social sharing
- Canonical URLs to prevent duplicates

✅ **Content**:
- Meaningful headings (H1, H2, H3)
- Alt text for images
- Internal linking between pages
- Mobile-friendly design

✅ **Technical**:
- Fast load times
- HTTPS enabled
- robots.txt configured
- 404 page exists

## Testing SEO

### Tools

1. **Google Search Console**: Track indexing, errors, performance
2. **Google PageSpeed Insights**: Measure page speed
3. **Lighthouse** (Chrome DevTools): Audit SEO, performance, accessibility
4. **Bing Webmaster Tools**: Bing-specific insights

### Manual Checks

- Search "site:mysite.com" to see indexed pages
- Test meta tags with browser inspector
- Verify sitemap loads: `https://mysite.com/sitemap.xml`
- Check mobile-friendliness: Google Mobile-Friendly Test

## Quick Wins

1. **Add sitemap.xml** (biggest impact)
2. **Unique page titles** with keywords
3. **Meta descriptions** for all pages
4. **Use route parameters** instead of query strings
5. **Enable HTTPS** (ranking factor)
6. **Submit sitemap** to Google/Bing
7. **Create robots.txt** with sitemap link

## Next Steps

- Implement sitemap in **06.ComprehensiveNavigation** project
- Test with Google Search Console
- Monitor site performance
- Learn more: [Google SEO Starter Guide](https://developers.google.com/search/docs/fundamentals/seo-starter-guide)
