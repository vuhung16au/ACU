using Microsoft.AspNetCore.Mvc.RazorPages;
using PartialViews.Models;

namespace PartialViews.Pages;

public class IndexModel : PageModel
{
    private readonly ILogger<IndexModel> _logger;

    public IndexModel(ILogger<IndexModel> logger)
    {
        _logger = logger;
    }

    public AlertViewModel WelcomeAlert { get; private set; } = new();
    public SidebarViewModel Sidebar { get; private set; } = new();
    public IReadOnlyList<FeatureCardViewModel> FeatureCards { get; private set; } = Array.Empty<FeatureCardViewModel>();

    public void OnGet()
    {
        WelcomeAlert = new AlertViewModel
        {
            Tone = "success",
            Title = "Why partial views matter",
            Message = "This page reuses the same alert, card, and sidebar markup without copying HTML into every page."
        };

        Sidebar = new SidebarViewModel
        {
            Title = "Look for these patterns",
            Description = "The same UI pieces can appear across multiple pages with different data.",
            Items = new[]
            {
                "Layout renders shared navigation and footer partials",
                "Each card is the same partial with a different model",
                "The sidebar stays reusable and focused"
            }
        };

        FeatureCards = new[]
        {
            new FeatureCardViewModel
            {
                Title = "Shared navigation",
                Description = "Move menu markup into one partial so every page stays consistent.",
                LinkText = "Open About",
                LinkUrl = "/About"
            },
            new FeatureCardViewModel
            {
                Title = "Typed card data",
                Description = "Pass a model into a partial instead of hard-coding card text in the view.",
                LinkText = "Open Contact",
                LinkUrl = "/Contact"
            },
            new FeatureCardViewModel
            {
                Title = "Smaller pages",
                Description = "Pages focus on content and intent while shared fragments live in one file.",
                LinkText = "See the contact example",
                LinkUrl = "/Contact"
            }
        };

        _logger.LogInformation("Partial views home page visited at {Time}", DateTime.Now);
    }
}