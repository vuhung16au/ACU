using Microsoft.AspNetCore.Mvc.RazorPages;
using ComprehensiveExample.Models;

namespace ComprehensiveExample.Pages;

public class BlogModel : PageModel
{
    public IReadOnlyList<PostCardViewModel> Posts { get; private set; } = Array.Empty<PostCardViewModel>();

    public void OnGet()
    {
        Posts = new[]
        {
            new PostCardViewModel
            {
                Title = "Layout inheritance checklist",
                Excerpt = "A practical sequence for choosing root, folder, and page-level layout settings.",
                Category = "Razor Pages",
                PublishedOn = "11 Mar 2026"
            },
            new PostCardViewModel
            {
                Title = "Partial view anti-patterns",
                Excerpt = "How to avoid giant partials and keep responsibilities clear.",
                Category = "Architecture",
                PublishedOn = "08 Mar 2026"
            },
            new PostCardViewModel
            {
                Title = "Using sections effectively",
                Excerpt = "Keep page-specific scripts optional using named sections in layouts.",
                Category = "Best Practices",
                PublishedOn = "03 Mar 2026"
            }
        };
    }
}