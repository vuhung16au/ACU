using HtmxPartialUpdates.Models;
using HtmxPartialUpdates.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace HtmxPartialUpdates.Pages;

/// <summary>
/// Main HTMX CRUD lab page.
///
/// [IgnoreAntiforgeryToken] is applied here for classroom simplicity.
/// In a real application you would configure HTMX to forward the
/// RequestVerificationToken header on every request:
///
///   document.addEventListener('htmx:configRequest', evt => {
///     evt.detail.headers['RequestVerificationToken'] =
///       document.querySelector('input[name="__RequestVerificationToken"]')?.value ?? '';
///   });
///
/// Handler conventions used here:
///   OnGet           — full page load, renders the whole student table.
///   OnGetEditRow    — returns a partial: one row in edit mode.
///   OnGetViewRow    — returns a partial: one row in read mode (cancel edit).
///   OnPostCreate    — creates a student, returns the new row prepended to the tbody.
///   OnPostUpdate    — updates a student, returns the updated row in read mode.
///   OnPostDelete    — deletes a student, returns an empty string (row is removed).
/// </summary>
[IgnoreAntiforgeryToken]
public class HtmxLabModel : PageModel
{
    private readonly StudentService _studentService;

    public HtmxLabModel(StudentService studentService)
    {
        _studentService = studentService;
        Students = Array.Empty<Student>();
    }

    /// <summary>All students — populated on full GET only.</summary>
    public IReadOnlyList<Student> Students { get; private set; }

    // ─── Full page load ────────────────────────────────────────────────────

    public void OnGet()
    {
        Students = _studentService.GetAll();
    }

    // ─── Inline row helpers ────────────────────────────────────────────────

    /// <summary>
    /// Returns the edit-mode partial for one row.
    /// HTMX swaps this into the table in place of the read-mode row.
    /// </summary>
    public IActionResult OnGetEditRow(int id)
    {
        var student = _studentService.GetById(id);
        if (student is null) return NotFound();
        return Partial("Partials/_StudentEditRow", student);
    }

    /// <summary>
    /// Returns the read-mode partial for one row.
    /// Used to cancel an edit without saving.
    /// </summary>
    public IActionResult OnGetViewRow(int id)
    {
        var student = _studentService.GetById(id);
        if (student is null) return NotFound();
        return Partial("Partials/_StudentRow", student);
    }

    // ─── Create ────────────────────────────────────────────────────────────

    /// <summary>
    /// Handles the "Add student" form.
    /// On success, returns the new table row so HTMX can prepend it to the tbody.
    /// </summary>
    public IActionResult OnPostCreate(
        string name, string course, int grade, bool isActive = false)
    {
        if (string.IsNullOrWhiteSpace(name) || string.IsNullOrWhiteSpace(course))
        {
            // Return 422 so HTMX shows the error panel via hx-swap-oob or simply ignores.
            return new UnprocessableEntityObjectResult("Name and Course are required.");
        }

        var student = _studentService.Add(name, course, grade, isActive);
        return Partial("Partials/_StudentRow", student);
    }

    // ─── Update ────────────────────────────────────────────────────────────

    /// <summary>
    /// Saves changes from an inline edit row.
    /// Returns the updated row in read mode.
    /// </summary>
    public IActionResult OnPostUpdate(
        int id, string name, string course, int grade, bool isActive = false)
    {
        if (string.IsNullOrWhiteSpace(name) || string.IsNullOrWhiteSpace(course))
        {
            return new UnprocessableEntityObjectResult("Name and Course are required.");
        }

        var student = _studentService.Update(id, name, course, grade, isActive);
        if (student is null) return NotFound();

        return Partial("Partials/_StudentRow", student);
    }

    // ─── Delete ────────────────────────────────────────────────────────────

    /// <summary>
    /// Deletes a student.
    /// Returns 200 OK with an empty body — HTMX swaps outerHTML of the row with nothing,
    /// effectively removing it from the DOM.
    /// </summary>
    public IActionResult OnPostDelete(int id)
    {
        _studentService.Delete(id);
        // Return an empty 200 response; HTMX will replace the row with nothing.
        return Content(string.Empty, "text/html");
    }
}
