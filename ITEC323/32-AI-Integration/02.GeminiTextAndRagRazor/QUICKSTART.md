# Quick Start

## Create The Local API Key File

```bash
cd 32-AI-Integration/02.GeminiTextAndRagRazor
cp .env.local.example .env.local
```

Edit `.env.local` and replace the placeholder value with your real Google Gemini API key.

The sample is configured to try `gemini-3-flash-preview` first, then fall back to `gemini-2.5-flash-lite`, `gemini-2.5-flash`, and `gemini-2.0-flash`.

## Restore And Build

```bash
/usr/local/share/dotnet/dotnet restore
/usr/local/share/dotnet/dotnet build
```

## Run The Web App

```bash
/usr/local/share/dotnet/dotnet run
```

Open:

- `http://localhost:5633`

## Run The Unit Tests

```bash
cd tests/02.GeminiTextAndRagRazor.Tests
/usr/local/share/dotnet/dotnet test
```
