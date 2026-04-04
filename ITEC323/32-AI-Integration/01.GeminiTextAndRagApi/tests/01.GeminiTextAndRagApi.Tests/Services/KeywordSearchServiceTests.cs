using FluentAssertions;
using GeminiTextAndRagApiDemo.Data;
using GeminiTextAndRagApiDemo.Services;
using Xunit;

namespace GeminiTextAndRagApiDemo.Tests.Services;

/// <summary>
/// Tests for the <see cref="KeywordSearchService"/> class.
/// </summary>
public class KeywordSearchServiceTests
{
    private readonly KeywordSearchService _service = new();

    /// <summary>
    /// Verifies that the best matching document is returned first.
    /// </summary>
    [Fact]
    public void Search_WithRelevantQuestion_ReturnsBestMatchesInScoreOrder()
    {
        // Arrange
        var documents = new List<KnowledgeDocument>
        {
            CreateDocument("doc-1", "Minimal APIs", "ASP.NET Core", "Minimal APIs define HTTP endpoints in Program.cs.", [ "minimal api", "endpoint" ]),
            CreateDocument("doc-2", "API Keys", "Security", "Store API keys in .env.local so they stay out of Git.", [ ".env.local", "api key" ]),
            CreateDocument("doc-3", "Dependency Injection", "Architecture", "Dependency injection supplies services to classes.", [ "dependency injection", "services" ])
        };

        // Act
        var results = _service.Search(documents, "Why should I store my API key in .env.local?", 2);

        // Assert
        results.Should().NotBeEmpty();
        results[0].Id.Should().Be("doc-2");
    }

    /// <summary>
    /// Verifies that a question with no useful overlap returns no documents.
    /// </summary>
    [Fact]
    public void Search_WithNoMatches_ReturnsEmptyList()
    {
        // Arrange
        var documents = new List<KnowledgeDocument>
        {
            CreateDocument("doc-1", "Minimal APIs", "ASP.NET Core", "Endpoints are mapped in Program.cs.", [ "minimal api" ])
        };

        // Act
        var results = _service.Search(documents, "Tell me about volcanoes on Mars", 3);

        // Assert
        results.Should().BeEmpty();
    }

    private static KnowledgeDocument CreateDocument(string id, string title, string category, string content, List<string> keywords)
    {
        return new KnowledgeDocument
        {
            Id = id,
            Title = title,
            Category = category,
            Content = content,
            Keywords = keywords
        };
    }
}
