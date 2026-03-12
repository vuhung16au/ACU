using Microsoft.AspNetCore.Mvc.RazorPages;

namespace MultipleLayouts.Pages;

public class PrintModel : PageModel
{
    public DateTime GeneratedAt { get; private set; }

    public void OnGet()
    {
        GeneratedAt = DateTime.Now;
    }
}