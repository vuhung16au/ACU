using System.Text.Json;
using GeminiTextAndRagApiDemo.Data;

namespace GeminiTextAndRagApiDemo.Services;

/// <summary>
/// Loads and exposes the local JSON knowledge base used for retrieval.
/// </summary>
public class KnowledgeBaseService
{
    private readonly List<KnowledgeDocument> _documents;

    /// <summary>
    /// Initializes a new instance of the <see cref="KnowledgeBaseService"/> class.
    /// </summary>
    /// <param name="environment">Provides access to the application content root path.</param>
    /// <exception cref="InvalidOperationException">Thrown when the JSON knowledge base cannot be loaded.</exception>
    public KnowledgeBaseService(IWebHostEnvironment environment)
    {
        var filePath = Path.Combine(environment.ContentRootPath, "Data", "sample-knowledge-base.json");
        if (!File.Exists(filePath))
        {
            throw new InvalidOperationException("The sample knowledge base file could not be found.");
        }

        var json = File.ReadAllText(filePath);
        _documents = JsonSerializer.Deserialize<List<KnowledgeDocument>>(json, new JsonSerializerOptions
        {
            PropertyNameCaseInsensitive = true
        }) ?? throw new InvalidOperationException("The sample knowledge base file is empty or invalid.");
    }

    /// <summary>
    /// Returns all knowledge base documents.
    /// </summary>
    /// <returns>A read-only list of documents.</returns>
    public IReadOnlyList<KnowledgeDocument> GetDocuments()
    {
        return _documents;
    }
}
