using Microsoft.AspNetCore.Mvc.RazorPages;

namespace ComprehensiveExample.Pages;

public class PrintProfileModel : PageModel
{
    public DateTime GeneratedAt { get; private set; }

    public void OnGet()
    {
        GeneratedAt = DateTime.Now;
    }
}