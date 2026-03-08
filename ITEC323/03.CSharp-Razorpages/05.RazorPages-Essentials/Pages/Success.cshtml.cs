using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace RazorPagesEssentials.Pages;

/// <summary>
/// Page model for the Success page that displays a confirmation message after form submission.
/// Demonstrates using TempData for cross-request data storage.
/// </summary>
public class SuccessModel : PageModel
{
    /// <summary>
    /// Holds the success message to display to the user.
    /// This message is passed from another page using TempData.
    /// </summary>
    public string? SuccessMessage { get; set; }

    /// <summary>
    /// Handles GET requests to display the success page.
    /// Retrieves the success message from TempData if it exists.
    /// </summary>
    public void OnGet()
    {
        // TempData is a special dictionary that persists data for exactly one request
        // It's commonly used to pass data during a redirect scenario
        // After we access the data, it's automatically removed
        SuccessMessage = TempData["SuccessMessage"] as string;
        
        // If there's no message in TempData, we'll set a default message
        // This handles cases where someone navigates directly to this page
        if (string.IsNullOrEmpty(SuccessMessage))
        {
            SuccessMessage = "Your action was completed successfully!";
        }
    }
}
