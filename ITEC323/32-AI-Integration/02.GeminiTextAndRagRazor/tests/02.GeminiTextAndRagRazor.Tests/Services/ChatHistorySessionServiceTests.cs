using FluentAssertions;
using GeminiTextAndRagRazorDemo.Models;
using GeminiTextAndRagRazorDemo.Services;
using Microsoft.AspNetCore.Http;
using Xunit;

namespace GeminiTextAndRagRazorDemo.Tests.Services;

/// <summary>
/// Tests for the <see cref="ChatHistorySessionService"/> class.
/// </summary>
public class ChatHistorySessionServiceTests
{
    /// <summary>
    /// Verifies that a chat exchange is stored and returned from session.
    /// </summary>
    [Fact]
    public void AddExchange_WithValidExchange_PersistsConversation()
    {
        // Arrange
        var session = new TestSession();
        var service = new ChatHistorySessionService();

        var exchange = new ChatExchange
        {
            Question = "Why use .env.local?",
            Answer = "It keeps secrets out of source control.",
            Sources =
            [
                new RagSourceItem
                {
                    DocumentId = "doc-4",
                    Title = "Why Use .env.local",
                    Category = "Security",
                    Excerpt = "API keys should stay out of source control."
                }
            ]
        };

        // Act
        var conversation = service.AddExchange(session, exchange);

        // Assert
        conversation.Should().ContainSingle();
        conversation[0].Question.Should().Be("Why use .env.local?");
        service.GetConversation(session).Should().ContainSingle();
    }

    /// <summary>
    /// Verifies that clearing session history removes the conversation.
    /// </summary>
    [Fact]
    public void Clear_WhenConversationExists_RemovesConversation()
    {
        // Arrange
        var session = new TestSession();
        var service = new ChatHistorySessionService();

        service.AddExchange(session, new ChatExchange
        {
            Question = "Question",
            Answer = "Answer",
            Sources = new List<RagSourceItem>()
        });

        // Act
        service.Clear(session);

        // Assert
        service.GetConversation(session).Should().BeEmpty();
    }

    private sealed class TestSession : ISession
    {
        private readonly Dictionary<string, byte[]> _store = new();

        public IEnumerable<string> Keys => _store.Keys;

        public string Id { get; } = Guid.NewGuid().ToString("N");

        public bool IsAvailable => true;

        public void Clear() => _store.Clear();

        public Task CommitAsync(CancellationToken cancellationToken = default) => Task.CompletedTask;

        public Task LoadAsync(CancellationToken cancellationToken = default) => Task.CompletedTask;

        public void Remove(string key) => _store.Remove(key);

        public void Set(string key, byte[] value) => _store[key] = value;

        public bool TryGetValue(string key, out byte[] value) => _store.TryGetValue(key, out value!);
    }
}
