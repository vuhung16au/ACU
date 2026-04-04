using System.Text.RegularExpressions;
using GeminiTextAndRagRazorDemo.Data;

namespace GeminiTextAndRagRazorDemo.Services;

/// <summary>
/// Performs simple keyword-based retrieval over the local knowledge base.
/// </summary>
public class KeywordSearchService
{
    private static readonly HashSet<string> IgnoredWords =
    [
        "a", "an", "and", "are", "be", "do", "for", "how", "i", "in", "is",
        "of", "on", "or", "should", "the", "to", "what", "why", "with"
    ];

    /// <summary>
    /// Retrieves the highest scoring documents for a question.
    /// </summary>
    /// <param name="documents">The documents to search.</param>
    /// <param name="question">The user question.</param>
    /// <param name="maxDocuments">The maximum number of documents to return.</param>
    /// <returns>The ordered list of matching documents.</returns>
    public IReadOnlyList<KnowledgeDocument> Search(IEnumerable<KnowledgeDocument> documents, string question, int maxDocuments)
    {
        var tokens = Tokenize(question);

        return documents
            .Select(document => new
            {
                Document = document,
                Score = CalculateScore(document, tokens)
            })
            .Where(result => result.Score > 0)
            .OrderByDescending(result => result.Score)
            .ThenBy(result => result.Document.Title)
            .Take(maxDocuments)
            .Select(result => result.Document)
            .ToList();
    }

    private static int CalculateScore(KnowledgeDocument document, IEnumerable<string> tokens)
    {
        var score = 0;
        var title = document.Title.ToLowerInvariant();
        var category = document.Category.ToLowerInvariant();
        var content = document.Content.ToLowerInvariant();
        var keywords = document.Keywords.Select(keyword => keyword.ToLowerInvariant()).ToList();

        foreach (var token in tokens)
        {
            if (title.Contains(token, StringComparison.Ordinal))
            {
                score += 4;
            }

            if (category.Contains(token, StringComparison.Ordinal))
            {
                score += 2;
            }

            if (keywords.Any(keyword => keyword.Contains(token, StringComparison.Ordinal)))
            {
                score += 5;
            }

            if (content.Contains(token, StringComparison.Ordinal))
            {
                score += 1;
            }
        }

        return score;
    }

    private static List<string> Tokenize(string input)
    {
        return Regex.Split(input.ToLowerInvariant(), @"[^a-z0-9\.]+")
            .Where(token => !string.IsNullOrWhiteSpace(token))
            .Where(token => !IgnoredWords.Contains(token))
            .Distinct()
            .ToList();
    }
}
