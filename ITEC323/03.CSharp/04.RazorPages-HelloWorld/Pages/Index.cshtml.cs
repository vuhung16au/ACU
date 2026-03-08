using Microsoft.AspNetCore.Mvc.RazorPages;
using System;

namespace RazorPagesHelloWorld.Pages
{
    /// <summary>
    /// The page model for the Index page.
    /// This class contains the logic and data for the home page.
    /// </summary>
    public class IndexModel : PageModel
    {
        /// <summary>
        /// A simple message to display on the page.
        /// This property is accessed in Index.cshtml using @Model.Message
        /// </summary>
        public string Message { get; set; } = string.Empty;

        /// <summary>
        /// This method runs when the page is loaded via HTTP GET request.
        /// It sets the Message property with "Hello" and the current timestamp.
        /// </summary>
        public void OnGet()
        {
            // Set a simple hello message with the current time
            Message = $"Hello! Current time is {DateTime.Now:yyyy-MM-dd HH:mm:ss}";
        }
    }
}
