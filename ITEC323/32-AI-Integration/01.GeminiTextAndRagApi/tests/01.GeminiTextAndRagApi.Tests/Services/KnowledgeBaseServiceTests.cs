using FluentAssertions;
using GeminiTextAndRagApiDemo.Services;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.FileProviders;
using Xunit;

namespace GeminiTextAndRagApiDemo.Tests.Services;

/// <summary>
/// Tests for the <see cref="KnowledgeBaseService"/> class.
/// </summary>
public class KnowledgeBaseServiceTests
{
    /// <summary>
    /// Verifies that the sample JSON file is loaded successfully.
    /// </summary>
    [Fact]
    public void Constructor_WithValidJsonFile_LoadsDocuments()
    {
        // Arrange
        var contentRootPath = Path.GetFullPath(Path.Combine(AppContext.BaseDirectory, "..", "..", "..", "..", ".."));
        var environment = new TestWebHostEnvironment
        {
            ContentRootPath = contentRootPath,
            ContentRootFileProvider = new PhysicalFileProvider(contentRootPath),
            EnvironmentName = "Development",
            ApplicationName = "Tests",
            WebRootPath = contentRootPath,
            WebRootFileProvider = new PhysicalFileProvider(contentRootPath)
        };

        // Act
        var service = new KnowledgeBaseService(environment);
        var documents = service.GetDocuments();

        // Assert
        documents.Should().NotBeEmpty();
        documents.Should().Contain(document => document.Id == "doc-4");
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
