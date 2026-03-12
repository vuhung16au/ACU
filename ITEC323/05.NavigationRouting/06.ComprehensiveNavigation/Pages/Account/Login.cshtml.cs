using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace ComprehensiveNavigation.Pages.Account;

public class LoginModel : PageModel
{
    public void OnGet()
    {
    }

    public IActionResult OnPost(string username, string password)
    {
        // Dummy login, redirects to orders
        // Note: For educational purposes, simulating a login redirect
        
        TempData["SuccessMessage"] = "Successfully logged in!";
        return RedirectToPage("/Account/Orders");
    }
}
