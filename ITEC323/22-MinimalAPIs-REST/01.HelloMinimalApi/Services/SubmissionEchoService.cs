using HelloMinimalApiDemo.Models;

namespace HelloMinimalApiDemo.Services;

/// <summary>
/// Validates and prepares the sample submission response.
/// </summary>
public class SubmissionEchoService
{
    /// <summary>
    /// Validates the submitted request.
    /// </summary>
    /// <param name="request">The submission request to validate.</param>
    /// <returns>An error message when validation fails; otherwise, <c>null</c>.</returns>
    public string? Validate(FormSubmissionRequest request)
    {
        if (string.IsNullOrWhiteSpace(request.Name))
        {
            return "Name is required.";
        }

        if (string.IsNullOrWhiteSpace(request.Email))
        {
            return "Email is required.";
        }

        return null;
    }

    /// <summary>
    /// Creates the response returned by the submission endpoint.
    /// </summary>
    /// <param name="request">The validated submission request.</param>
    /// <returns>A response containing normalized values.</returns>
    public FormSubmissionResponse CreateResponse(FormSubmissionRequest request)
    {
        return new FormSubmissionResponse
        {
            Name = request.Name.Trim(),
            Email = request.Email.Trim(),
            FavoriteLanguage = string.IsNullOrWhiteSpace(request.FavoriteLanguage)
                ? "Unselected"
                : request.FavoriteLanguage,
            Message = "Submission received successfully."
        };
    }
}
