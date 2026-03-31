# Quick Start

## Prerequisites

- .NET 10 SDK
- Visual Studio Code
- a modern browser
- Node.js only if you want to run the Playwright video script


## Run The App

```bash
cd 23-CoreSignalR/01-HelloSignalR
dotnet run
```

Open the local URL shown in the terminal.

## What To Try

1. open the app in two browser tabs
2. type a name and message in one tab
3. click `Send Message`
4. confirm that both tabs show the new message immediately

## Important Files

- `Program.cs` registers SignalR and maps the hub route
- `Hubs/ChatHub.cs` broadcasts messages
- `Pages/Index.cshtml` contains the UI
- `wwwroot/js/chat.js` connects the browser to the hub

## Create A Short Demo Video

The recording script assumes the app is running locally and that Playwright is installed in this project folder.

```bash
cd 23-CoreSignalR/01-HelloSignalR
npm install playwright
node scripts/create-a-video-with-PlayWright.js
```

The script saves a `.webm` recording in `artifacts/`.

## Record Two Browsers Side By Side

This helper script starts the app for you, records a side-by-side demo of two embedded live SignalR chat panels, and saves a `.webm` video in `artifacts/`.

```bash
cd 23-CoreSignalR/01-HelloSignalR
npm install playwright
node scripts/show-two-browsers-side-by-side.js
```

## Build Check

```bash
dotnet build
```
