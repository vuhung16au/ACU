using NavigationMenus.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace NavigationMenus.Pages.Courses;

/// <summary>
/// Demonstrates a regex route constraint using a course code.
/// </summary>
public class LookupModel : PageModel
{
    /// <summary>
    /// Gets the course selected by the regex route value.
    /// </summary>
    public CourseReference? Course { get; private set; }

    /// <summary>
    /// Handles GET requests for the course lookup page.
    /// </summary>
    /// <param name="code">The course code captured from the route.</param>
    /// <returns>The page result when the course exists; otherwise a 404 response.</returns>
    public IActionResult OnGet(string code)
    {
        Course = DemoData.FindCourse(code);

        if (Course is null)
        {
            return NotFound();
        }

        return Page();
    }
}