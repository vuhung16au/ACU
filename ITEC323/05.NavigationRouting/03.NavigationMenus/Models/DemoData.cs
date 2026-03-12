namespace NavigationMenus.Models;

/// <summary>
/// Provides in-memory sample data for the routing demonstrations.
/// </summary>
public static class DemoData
{
    private static readonly Product[] Products =
    {
        new()
        {
            Id = 1,
            Name = "Student Laptop",
            Category = "Computers",
            Price = 899.00m,
            Summary = "A compact laptop used to demonstrate required product ID routes."
        },
        new()
        {
            Id = 2,
            Name = "Noise-Cancelling Headphones",
            Category = "Audio",
            Price = 249.00m,
            Summary = "A second example product that appears in the details route."
        },
        new()
        {
            Id = 3,
            Name = "Mechanical Keyboard",
            Category = "Accessories",
            Price = 139.00m,
            Summary = "Used to demonstrate optional edit routes with or without an ID."
        }
    };

    private static readonly BlogPost[] Posts =
    {
        new()
        {
            Year = 2026,
            Month = 3,
            Slug = "getting-started-with-routing",
            Title = "Getting Started with Routing",
            Summary = "Introduces how file paths and route templates work together in Razor Pages."
        },
        new()
        {
            Year = 2026,
            Month = 4,
            Slug = "why-friendly-urls-matter",
            Title = "Why Friendly URLs Matter",
            Summary = "Compares readable blog URLs with harder-to-read query string patterns."
        }
    };

    private static readonly CourseReference[] Courses =
    {
        new()
        {
            Code = "ITEC323",
            Title = "Web Application Development",
            Focus = "Routing, layouts, and beginner-friendly Razor Pages patterns."
        },
        new()
        {
            Code = "ITEC624",
            Title = "Enterprise Application Architecture",
            Focus = "Larger application structure and service design."
        }
    };

    /// <summary>
    /// Gets all sample products.
    /// </summary>
    /// <returns>The product list used on the Products pages.</returns>
    public static IReadOnlyList<Product> GetProducts()
    {
        return Products;
    }

    /// <summary>
    /// Finds a product by its identifier.
    /// </summary>
    /// <param name="id">The route value captured from the URL.</param>
    /// <returns>The matching product if it exists; otherwise <see langword="null" />.</returns>
    public static Product? FindProduct(int id)
    {
        return Products.FirstOrDefault(product => product.Id == id);
    }

    /// <summary>
    /// Gets all sample blog posts.
    /// </summary>
    /// <returns>The post list used on the Blog pages.</returns>
    public static IReadOnlyList<BlogPost> GetPosts()
    {
        return Posts;
    }

    /// <summary>
    /// Finds a blog post using multiple route values.
    /// </summary>
    /// <param name="year">The year captured from the URL.</param>
    /// <param name="month">The month captured from the URL.</param>
    /// <param name="slug">The slug captured from the URL.</param>
    /// <returns>The matching blog post if it exists; otherwise <see langword="null" />.</returns>
    public static BlogPost? FindPost(int year, int month, string slug)
    {
        return Posts.FirstOrDefault(post =>
            post.Year == year &&
            post.Month == month &&
            string.Equals(post.Slug, slug, StringComparison.OrdinalIgnoreCase));
    }

    /// <summary>
    /// Finds a course by its course code.
    /// </summary>
    /// <param name="code">The course code captured by the regex constraint.</param>
    /// <returns>The matching course if it exists; otherwise <see langword="null" />.</returns>
    public static CourseReference? FindCourse(string code)
    {
        return Courses.FirstOrDefault(course =>
            string.Equals(course.Code, code, StringComparison.OrdinalIgnoreCase));
    }
}