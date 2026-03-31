# Functional Requirements Document

## Purpose

Create a second SignalR sample that shows how a live dashboard can receive real-time server events.

## Functional Requirements

1. The app must run as an ASP.NET Core Razor Pages project on .NET 10.
2. The app must display a live notification feed.
3. The app must show summary counts for the main notification types.
4. The app must broadcast new notifications to all connected browsers.
5. The app must include server-generated demo notifications.
6. The app must let the user trigger manual demo notifications.
7. The project must include short learning documentation.
8. The project must include a Playwright recording script.

## Non-Functional Requirements

1. The code must stay beginner-friendly.
2. The UI must be responsive on smaller screens.
3. The sample must avoid database complexity in this stage.
4. The app must use an in-memory approach that is easy to explain in class.

## Success Criteria

- students can see live server-generated notifications
- students can trigger manual notifications and watch all tabs update
- students can identify how SignalR is used for server push instead of chat
