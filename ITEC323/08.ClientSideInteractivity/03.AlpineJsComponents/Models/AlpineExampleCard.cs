namespace AlpineJsComponents.Models;

/// <summary>
/// Represents one classroom Alpine.js example card.
/// </summary>
/// <param name="Title">The example title.</param>
/// <param name="Directive">The main Alpine directive used in the example.</param>
/// <param name="Description">A short beginner-friendly description.</param>
public sealed record AlpineExampleCard(string Title, string Directive, string Description);
