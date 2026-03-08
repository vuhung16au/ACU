using Microsoft.AspNetCore.Mvc.RazorPages;
using System.Collections.Generic;

// ============================================================================
// Index Page Model (Code-Behind)
// ============================================================================
//
// This is the "code-behind" file for Index.cshtml
// It contains the C# logic for the Index page
//
// Key Concepts:
// - PageModel: Base class for Razor Pages that contains page logic
// - Properties: Data that can be displayed in the .cshtml file
// - OnGet/OnPost: Methods called when the page is requested
// ============================================================================

namespace RazorPagesHelloWorld.Pages
{
    /// <summary>
    /// The page model for the Index page.
    /// This class contains the logic and data for the home page.
    /// </summary>
    public class IndexModel : PageModel
    {
        // ====================================================================
        // Properties - Data accessible from the .cshtml file
        // ====================================================================
        
        /// <summary>
        /// A welcome message to display on the page.
        /// This property can be accessed in Index.cshtml using @Model.WelcomeMessage
        /// </summary>
        public string WelcomeMessage { get; set; } = string.Empty;
        
        /// <summary>
        /// The current date and time.
        /// Demonstrates passing data from C# to HTML
        /// </summary>
        public string CurrentDateTime { get; set; } = string.Empty;
        
        /// <summary>
        /// .NET version information.
        /// Shows the runtime environment details
        /// </summary>
        public string DotNetVersion { get; set; } = string.Empty;
        
        /// <summary>
        /// A list of key concepts covered in this project.
        /// Demonstrates working with collections in Razor Pages
        /// </summary>
        public List<string> KeyConcepts { get; set; } = new List<string>();

        // ====================================================================
        // OnGet Method - Called when page is requested via HTTP GET
        // ====================================================================
        
        /// <summary>
        /// This method runs when the page is loaded (GET request).
        /// It initializes the page data before the page is rendered.
        /// </summary>
        public void OnGet()
        {
            // Set the welcome message
            WelcomeMessage = "Welcome to ASP.NET Core Razor Pages!";
            
            // Get the current date and time
            CurrentDateTime = System.DateTime.Now.ToString("MMMM dd, yyyy - HH:mm:ss");
            
            // Get the .NET runtime version
            DotNetVersion = System.Environment.Version.ToString();
            
            // Initialize the list of key concepts
            KeyConcepts = new List<string>
            {
                "ASP.NET Core framework for building web applications",
                "Razor Pages for page-based programming model",
                "HTML for structuring web content",
                "CSS for styling web pages",
                "HTTP protocol for client-server communication",
                "C# for server-side logic",
                "PageModel for separating logic from presentation"
            };
        }

        // ====================================================================
        // How This Works:
        // ====================================================================
        // 1. User navigates to http://localhost:5000 in browser
        // 2. ASP.NET Core routing maps request to Index.cshtml
        // 3. OnGet() method executes, setting property values
        // 4. Razor engine processes Index.cshtml, accessing @Model properties
        // 5. HTML is generated and sent to browser
        // 6. Browser displays the rendered page
        // ====================================================================
    }
}
