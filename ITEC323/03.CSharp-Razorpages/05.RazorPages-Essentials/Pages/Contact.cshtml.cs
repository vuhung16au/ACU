using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using System;
using System.ComponentModel.DataAnnotations;

namespace RazorPagesEssentials.Pages
{
    /// <summary>
    /// The page model for the Contact page.
    /// Demonstrates form handling, validation, and data binding.
    /// </summary>
    public class ContactModel : PageModel
    {
        // ====================================================================
        // BindProperty - Binds form input to this property
        // ====================================================================
        // When the form is submitted (POST), ASP.NET Core automatically
        // fills these properties with the user's input
        // ====================================================================

        /// <summary>
        /// User's name from the contact form.
        /// [Required] ensures this field must be filled in.
        /// [Display] sets the label text shown in the form.
        /// </summary>
        [BindProperty]
        [Required(ErrorMessage = "Name is required")]
        [Display(Name = "Your Name")]
        public string Name { get; set; } = string.Empty;

        /// <summary>
        /// User's email address from the contact form.
        /// [Required] ensures this field must be filled in.
        /// [EmailAddress] validates that it's a proper email format.
        /// </summary>
        [BindProperty]
        [Required(ErrorMessage = "Email is required")]
        [EmailAddress(ErrorMessage = "Please enter a valid email address")]
        [Display(Name = "Email Address")]
        public string Email { get; set; } = string.Empty;

        /// <summary>
        /// User's message from the contact form.
        /// [Required] and [StringLength] validate the input.
        /// </summary>
        [BindProperty]
        [Required(ErrorMessage = "Message is required")]
        [StringLength(500, MinimumLength = 10, 
            ErrorMessage = "Message must be between 10 and 500 characters")]
        [Display(Name = "Your Message")]
        public string Message { get; set; } = string.Empty;

        // ====================================================================
        // OnGet - Handles HTTP GET requests (loading the page)
        // ====================================================================

        /// <summary>
        /// This method runs when the user navigates to the Contact page.
        /// It simply displays the empty form.
        /// </summary>
        public void OnGet()
        {
            // No setup needed for an empty form
        }

        // ====================================================================
        // OnPost - Handles HTTP POST requests (form submission)
        // ====================================================================

        /// <summary>
        /// This method runs when the user submits the contact form.
        /// It validates the input and redirects to the Success page if valid.
        /// </summary>
        /// <returns>ActionResult - either stays on page or redirects</returns>
        public IActionResult OnPost()
        {
            // ModelState.IsValid checks all validation rules
            // If any [Required] or [EmailAddress] validation fails,
            // this will be false
            if (!ModelState.IsValid)
            {
                // Return the page with error messages
                return Page();
            }

            // If validation passes, in a real app you would:
            // - Save to database
            // - Send an email
            // - Log the submission
            
            // For this demo, we'll just redirect to a success page
            // TempData stores data for the next request (survives the redirect)
            TempData["SuccessMessage"] = $"Thank you, {Name}! We received your message.";
            
            // RedirectToPage navigates to another Razor Page
            return RedirectToPage("/Success");
        }
    }
}
