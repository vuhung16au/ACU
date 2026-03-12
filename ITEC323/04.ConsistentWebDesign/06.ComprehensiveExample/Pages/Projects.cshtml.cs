using Microsoft.AspNetCore.Mvc.RazorPages;
using ComprehensiveExample.Models;

namespace ComprehensiveExample.Pages;

public class ProjectsModel : PageModel
{
    public IReadOnlyList<ProjectCardViewModel> Projects { get; private set; } = Array.Empty<ProjectCardViewModel>();

    public void OnGet()
    {
        Projects = new[]
        {
            new ProjectCardViewModel
            {
                Name = "Portfolio CMS",
                Summary = "Content-managed portfolio powered by Razor Pages and admin workflow.",
                Stack = "ASP.NET Core, SQL Server",
                Status = "Live"
            },
            new ProjectCardViewModel
            {
                Name = "Course Companion",
                Summary = "Student-first dashboard using reusable card and callout components.",
                Stack = "Razor Pages, Bootstrap",
                Status = "In Progress"
            },
            new ProjectCardViewModel
            {
                Name = "Team Notes",
                Summary = "Knowledge sharing app with role-aware admin section.",
                Stack = "Razor Pages, Tailwind",
                Status = "Prototype"
            }
        };
    }
}