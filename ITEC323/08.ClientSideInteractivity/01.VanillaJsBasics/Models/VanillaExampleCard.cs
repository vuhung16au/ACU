namespace VanillaJsBasics.Models;

/// <summary>
/// Represents a small card shown in the DOM selection exercise.
/// </summary>
/// <param name="Title">The title displayed on the card.</param>
/// <param name="Description">Short explanation of the skill the card demonstrates.</param>
public sealed record VanillaExampleCard(string Title, string Description);
