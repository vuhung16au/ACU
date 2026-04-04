# Quick Start

## Create The Local API Key File

Create a `.env.local` file in `32-AI-Integration/01.GeminiTextAndRagApi`:

```bash
cp .env.local.example .env.local
```

Open `.env.local` and replace the placeholder value with your real Google Gemini API key.

The sample is configured to try `gemini-3-flash-preview` first, then fall back to `gemini-2.5-flash-lite`, `gemini-2.5-flash`, and `gemini-2.0-flash`.

## Create a Gemini API Key

1. Go to https://aistudio.google.com/app/api-keys 
2. Click "Create API Key" and follow the prompts to set up a new key.
3. Once the key is created, copy the API key string and paste it into your `.env.local` file as the value for `GOOGLE_API_KEY`.
4. Save the file and keep it secure. Do not share your API key publicly or commit it to version control.
5. Add `.env.local` to your `.gitignore` file if it's not already there, to prevent accidental commits of sensitive information.

## Restore And Build

```bash
cd 32-AI-Integration/01.GeminiTextAndRagApi
dotnet restore
dotnet build
```

## Run The API

```bash
dotnet run
```

By default, the app runs on:

- `http://localhost:5632`

## Example Requests

```bash
curl http://localhost:5632/
curl http://localhost:5632/api/documents
curl -X POST http://localhost:5632/api/generate -H "Content-Type: application/json" -d '{"prompt":"Explain dependency injection in one short paragraph."}'
curl -X POST http://localhost:5632/api/rag-answer -H "Content-Type: application/json" -d '{"question":"Why should I store an API key in .env.local?"}'
```

## Example Response

```bash
> curl -X POST http://localhost:5632/api/generate -H "Content-Type: application/json" -d '{"prompt":"Explain dependency injection in one short paragraph."}'
                                                                
```

```json
{"prompt":"Explain dependency injection in one short paragraph.","model":"gemini-3-flash-preview","responseText":"Dependency injection is a software design pattern where an object receives its dependencies from an external source rather than creating them itself. By passing required components into a class—typically through its constructor—instead of hard-coding them inside, you decouple the object's logic from its specific implementations. This makes code significantly easier to test, maintain, and reuse, as it allows you to swap out dependencies (such as replacing a real database with a mock version for testing) without modifying the class's internal code."}
```

## Run The Unit Tests

```bash
cd tests/01.GeminiTextAndRagApi.Tests
dotnet restore
dotnet build
dotnet test
```

## Troubleshooting

### Missing API Key

If the API returns an error about a missing key:

- make sure `.env.local` exists
- confirm `GOOGLE_API_KEY` has a real value
- restart `dotnet run` after changing the file

### Package Restore Fails

If `dotnet restore` fails, check that:

- the internet connection is available
- NuGet package restore is not blocked by a firewall or proxy
