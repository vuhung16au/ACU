using Microsoft.AspNetCore.Mvc.RazorPages;
using PartialViews.Models;

namespace PartialViews.Pages;

public class AboutModel : PageModel
{
    public AlertViewModel PageAlert { get; private set; } = new();
    public SidebarViewModel Sidebar { get; private set; } = new();

    public void OnGet()
    {
        PageAlert = new AlertViewModel
        {
            Tone = "info",
            Title = "Partial view rule of thumb",
            Message = "Extract a partial when repeated markup has one clear responsibility and should stay consistent."
        };

        Sidebar = new SidebarViewModel
        {
            Title = "Partials in this project",
            Description = "These five partials remove duplication from pages and the layout.",
            Items = new[]
            {
                "_Navigation.cshtml",
                "_Footer.cshtml",
                "_Alert.cshtml",
                "_FeatureCard.cshtml",
                "_Sidebar.cshtml"
            }
        };
    }
}