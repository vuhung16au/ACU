using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace ComprehensiveNavigation.Pages.Admin;

public class DashboardModel : PageModel
{
    public IActionResult OnGet()
    {
        // Example: Programmatic redirect.
        // If a user tries to access a restricted area without auth, we redirect them.
        // For demonstration, we simply always redirect to login.
        
        TempData["AlertMessage"] = "You must be logged in to view the Admin Dashboard.";
        
        // 302 Found (Temporary Redirect)
        return RedirectToPage("/Account/Login");
    }
}
