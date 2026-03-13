using BlazorInteractive.Services;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace BlazorInteractive.Pages;

public class BlazorLabModel : PageModel
{
    private readonly BlazorDemoService _demoService;

    public BlazorLabModel(BlazorDemoService demoService)
    {
        _demoService = demoService;
        Highlights = Array.Empty<string>();
    }

    public IReadOnlyList<string> Highlights { get; private set; }

    public void OnGet()
    {
        Highlights = _demoService.GetModuleHighlights();
    }
}
