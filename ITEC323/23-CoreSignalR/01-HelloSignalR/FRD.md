# Functional Requirements Document

## Purpose

Create a first SignalR sample for ITEC323 that introduces real-time communication with the smallest useful example.

## Functional Requirements

1. The app must run as an ASP.NET Core Razor Pages project on .NET 10.
2. The app must provide a page where a user can enter a name and message.
3. The app must send the message to the server through a SignalR hub.
4. The app must broadcast the message to all connected browsers.
5. The app must display received messages on the page without a refresh.
6. The project must include short learning documentation.
7. The project must include a script that can record a short browser demo with Playwright.

## Non-Functional Requirements

1. The code must stay short and beginner-friendly.
2. The sample must be understandable in Visual Studio Code with the .NET CLI.
3. The UI must work on desktop and mobile-sized screens.
4. The project must avoid unnecessary architecture or abstraction.

## Success Criteria

- students can run the app locally
- students can open two tabs and observe real-time updates
- students can identify where SignalR is configured on the server and client
