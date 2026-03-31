# Key Takeaways

## What SignalR Gives You

SignalR helps a server push updates to connected browsers in real time.

In this sample, that means a new chat message appears straight away in every open tab.

## The Three Main Pieces

The sample is small because it has only three important parts:

1. `Program.cs` enables SignalR and maps the hub route
2. `Hubs/ChatHub.cs` defines the server method clients can call
3. `wwwroot/js/chat.js` connects the browser and listens for incoming messages

## Why A Hub Is Useful

A hub gives us a simple place to handle browser-to-server communication.

Instead of working with lower-level connection code, we can write one method:

- `SendMessage(user, message)`

That method then broadcasts to all connected clients.

## Why The Client Uses `textContent`

User input should be treated as plain text, not HTML.

Using `textContent` helps avoid script injection when showing chat messages on the page.

## What This First Project Does Not Cover Yet

This introduction keeps the scope small.

It does not yet cover:

- groups
- private messages
- database persistence
- authentication
- strongly typed hubs
