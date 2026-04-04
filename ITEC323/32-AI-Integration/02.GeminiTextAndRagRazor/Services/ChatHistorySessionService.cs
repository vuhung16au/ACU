using System.Text.Json;
using GeminiTextAndRagRazorDemo.Models;
using Microsoft.AspNetCore.Http;

namespace GeminiTextAndRagRazorDemo.Services;

/// <summary>
/// Stores and retrieves chat history from the ASP.NET Core session.
/// </summary>
public class ChatHistorySessionService
{
    private const string SessionKey = "GeminiTextAndRagConversation";

    /// <summary>
    /// Gets the current conversation history from session.
    /// </summary>
    /// <param name="session">The session used for storage.</param>
    /// <returns>The stored conversation, or an empty list when no history exists.</returns>
    public List<ChatExchange> GetConversation(ISession session)
    {
        var json = session.GetString(SessionKey);
        if (string.IsNullOrWhiteSpace(json))
        {
            return new List<ChatExchange>();
        }

        return JsonSerializer.Deserialize<List<ChatExchange>>(json) ?? new List<ChatExchange>();
    }

    /// <summary>
    /// Adds one exchange to the stored conversation and returns the updated list.
    /// </summary>
    /// <param name="session">The session used for storage.</param>
    /// <param name="exchange">The exchange to append.</param>
    /// <returns>The updated conversation history.</returns>
    public List<ChatExchange> AddExchange(ISession session, ChatExchange exchange)
    {
        var conversation = GetConversation(session);
        conversation.Add(exchange);
        SaveConversation(session, conversation);
        return conversation;
    }

    /// <summary>
    /// Clears the stored conversation history.
    /// </summary>
    /// <param name="session">The session used for storage.</param>
    public void Clear(ISession session)
    {
        session.Remove(SessionKey);
    }

    private void SaveConversation(ISession session, List<ChatExchange> conversation)
    {
        var json = JsonSerializer.Serialize(conversation);
        session.SetString(SessionKey, json);
    }
}
