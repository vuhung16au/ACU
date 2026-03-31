# Quick Start

## Prerequisites

- .NET 10 SDK
- Visual Studio Code
- a modern browser
- Node.js only if you want to run the Playwright video script

## Run The App

```bash
cd 23-CoreSignalR/02-LiveNotificationsDashboard
dotnet run
```

Open the local URL shown in the terminal.

## What To Try

1. open the dashboard in two browser tabs
2. wait a few seconds for the automatic server events
3. click the manual event buttons
4. confirm that both tabs update instantly

## Important Files

- `Program.cs` registers SignalR, the in-memory store, and the hosted service
- `Hubs/NotificationHub.cs` broadcasts dashboard updates
- `Services/NotificationGeneratorService.cs` creates fake server-side events
- `Services/NotificationStore.cs` keeps the latest notifications
- `wwwroot/js/notifications.js` updates the dashboard in the browser

## Record A Short Demo Video

```bash
cd 23-CoreSignalR/02-LiveNotificationsDashboard
npm install
node scripts/create-a-video-with-PlayWright.js
```

The script saves a `.webm` recording in `artifacts/`.

## Build Check

```bash
dotnet build
```
