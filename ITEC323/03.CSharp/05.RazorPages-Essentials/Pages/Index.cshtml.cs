using Microsoft.AspNetCore.Mvc.RazorPages;
using System;

namespace RazorPagesEssentials.Pages
{
    /// <summary>
    /// The page model for the Index (home) page.
    /// This is a simple landing page that welcomes users.
    /// </summary>
    public class IndexModel : PageModel
    {
        /// <summary>
        /// A welcome message to display.
        /// </summary>
        public string WelcomeMessage { get; set; } = string.Empty;

        /// <summary>
        /// This method runs when the page is loaded via HTTP GET request.
        /// </summary>
        public void OnGet()
        {
            WelcomeMessage = "Welcome to Razor Pages Essentials!";
        }
    }
}
