using Microsoft.AspNetCore.SignalR;

namespace _01_HelloSignalR.Hubs;

/// <summary>
/// Sends chat messages from one connected browser to all connected browsers.
/// </summary>
public class ChatHub : Hub
{
    /// <summary>
    /// Broadcasts a chat message to every connected client.
    /// </summary>
    /// <param name="user">The display name entered by the sender.</param>
    /// <param name="message">The message text entered by the sender.</param>
    /// <returns>A task that completes when the message has been sent.</returns>
    public async Task SendMessage(string user, string message)
    {
        await Clients.All.SendAsync("ReceiveMessage", user, message);
    }
}
