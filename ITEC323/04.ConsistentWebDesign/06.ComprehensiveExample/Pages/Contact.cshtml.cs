using Microsoft.AspNetCore.Mvc.RazorPages;
using ComprehensiveExample.Models;

namespace ComprehensiveExample.Pages;

public class ContactModel : PageModel
{
    public CalloutViewModel Callout { get; private set; } = new();

    public void OnGet()
    {
        Callout = new CalloutViewModel
        {
            Tone = "info",
            Title = "Demo contact form",
            Message = "Form submission is simulated; the focus here is reusable page structure and layout consistency."
        };
    }
}