using Microsoft.AspNetCore.Mvc.RazorPages;
using PartialViews.Models;

namespace PartialViews.Pages;

public class ContactModel : PageModel
{
    public AlertViewModel ContactAlert { get; private set; } = new();
    public SidebarViewModel Sidebar { get; private set; } = new();

    public void OnGet()
    {
        ContactAlert = new AlertViewModel
        {
            Tone = "warning",
            Title = "Demo-only form",
            Message = "The form below is static. Its purpose is to show how page-specific content can sit beside shared partials."
        };

        Sidebar = new SidebarViewModel
        {
            Title = "Data passed into partials",
            Description = "These values come from the page model and are rendered by the sidebar partial.",
            Items = new[]
            {
                "Title text comes from ContactModel",
                "Description comes from ContactModel",
                "List items come from ContactModel"
            }
        };
    }
}