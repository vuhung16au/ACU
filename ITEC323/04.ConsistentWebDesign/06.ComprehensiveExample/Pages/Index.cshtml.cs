using Microsoft.AspNetCore.Mvc.RazorPages;
using ComprehensiveExample.Models;

namespace ComprehensiveExample.Pages;

public class IndexModel : PageModel
{
    public HeroViewModel Hero { get; private set; } = new();
    public IReadOnlyList<ProjectCardViewModel> FeaturedProjects { get; private set; } = Array.Empty<ProjectCardViewModel>();
    public IReadOnlyList<PostCardViewModel> FeaturedPosts { get; private set; } = Array.Empty<PostCardViewModel>();
    public CalloutViewModel Callout { get; private set; } = new();

    public void OnGet()
    {
        Hero = new HeroViewModel
        {
            Title = "Build consistent web experiences",
            Subtitle = "A complete Razor Pages sample that combines layout inheritance, partial views, and section-based customization.",
            PrimaryActionText = "View Projects",
            PrimaryActionUrl = "/Projects",
            SecondaryActionText = "Read Blog",
            SecondaryActionUrl = "/Blog"
        };

        FeaturedProjects = new[]
        {
            new ProjectCardViewModel
            {
                Name = "Campus Event Hub",
                Summary = "Multi-role app with public listing and admin content workflows.",
                Stack = "ASP.NET Core Razor Pages, PostgreSQL",
                Status = "Live"
            },
            new ProjectCardViewModel
            {
                Name = "Study Planner",
                Summary = "Assignment planner with reusable UI components and section-based scripts.",
                Stack = "Razor Pages, Tailwind",
                Status = "Beta"
            }
        };

        FeaturedPosts = new[]
        {
            new PostCardViewModel
            {
                Title = "Choosing between layout and partial",
                Excerpt = "How to decide where shared markup belongs in Razor Pages.",
                Category = "Architecture",
                PublishedOn = "12 Mar 2026"
            },
            new PostCardViewModel
            {
                Title = "Section patterns for page-specific scripts",
                Excerpt = "Use optional sections to avoid loading scripts globally.",
                Category = "Best Practices",
                PublishedOn = "10 Mar 2026"
            }
        };

        Callout = new CalloutViewModel
        {
            Tone = "success",
            Title = "Production-ready direction",
            Message = "This structure scales: shared layout shell, reusable partials, and clear folder boundaries."
        };
    }
}