using FluentAssertions;
using GeminiTextAndRagApiDemo.Services;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.FileProviders;
using Microsoft.Extensions.Options;
using Xunit;

namespace GeminiTextAndRagApiDemo.Tests.Services;

/// <summary>
/// Tests for the <see cref="RagAnswerService"/> class.
/// </summary>
public class RagAnswerServiceTests
{
    /// <summary>
    /// Verifies that retrieved documents are included in the final prompt and returned as sources.
    /// </summary>
    [Fact]
    public async Task AnswerQuestionAsync_WithMatchingDocuments_ReturnsSourcesAndGeneratedAnswer()
    {
        // Arrange
        var contentRootPath = CreateKnowledgeBaseFolder(
            """
            [
              {
                "Id": "doc-1",
                "Title": "API Key Storage",
                "Category": "Security",
                "Content": "Store the API key in .env.local and ignore that file in Git.",
                "Keywords": [ ".env.local", "api key", "gitignore" ]
              }
            ]
            """);

        var environment = new TestWebHostEnvironment
        {
            ContentRootPath = contentRootPath,
            ContentRootFileProvider = new PhysicalFileProvider(contentRootPath),
            EnvironmentName = "Development",
            ApplicationName = "Tests",
            WebRootPath = contentRootPath,
            WebRootFileProvider = new PhysicalFileProvider(contentRootPath)
        };

        var knowledgeBaseService = new KnowledgeBaseService(environment);
        var keywordSearchService = new KeywordSearchService();
        var fakeTextGenerationService = new FakeTextGenerationService();
        var options = Options.Create(new RagOptions
        {
            MaxDocuments = 2,
            MaxExcerptLength = 80
        });

        var service = new RagAnswerService(
            knowledgeBaseService,
            keywordSearchService,
            fakeTextGenerationService,
            options);

        try
        {
            // Act
            var response = await service.AnswerQuestionAsync("Why should I keep an API key in .env.local?");

            // Assert
            response.Answer.Should().Be("Grounded answer");
            response.Sources.Should().ContainSingle();
            response.Sources[0].DocumentId.Should().Be("doc-1");
            fakeTextGenerationService.LastPrompt.Should().Contain(".env.local");
            fakeTextGenerationService.LastPrompt.Should().Contain("API Key Storage");
        }
        finally
        {
            Directory.Delete(contentRootPath, recursive: true);
        }
    }

    private static string CreateKnowledgeBaseFolder(string json)
    {
        var rootPath = Path.Combine(Path.GetTempPath(), Guid.NewGuid().ToString("N"));
        Directory.CreateDirectory(rootPath);
        Directory.CreateDirectory(Path.Combine(rootPath, "Data"));
        File.WriteAllText(Path.Combine(rootPath, "Data", "sample-knowledge-base.json"), json);
        return rootPath;
    }

    private sealed class FakeTextGenerationService : ITextGenerationService
    {
        public string LastPrompt { get; private set; } = string.Empty;

        public Task<string> GenerateTextAsync(string prompt)
        {
            LastPrompt = prompt;
            return Task.FromResult("Grounded answer");
        }
    }

    private sealed class TestWebHostEnvironment : IWebHostEnvironment
    {
        public string ApplicationName { get; set; } = string.Empty;

        public IFileProvider WebRootFileProvider { get; set; } = null!;

        public string WebRootPath { get; set; } = string.Empty;

        public string EnvironmentName { get; set; } = string.Empty;

        public string ContentRootPath { get; set; } = string.Empty;

        public IFileProvider ContentRootFileProvider { get; set; } = null!;
    }
}
