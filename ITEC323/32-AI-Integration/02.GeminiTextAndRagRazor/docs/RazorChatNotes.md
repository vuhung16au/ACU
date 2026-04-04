# Razor Chat Notes

This project keeps the same Gemini and RAG ideas as the API sample, but moves the user interaction into a Razor Pages interface.

The Razor sample also tries `gemini-3-flash-preview` first, then falls back to `gemini-2.5-flash-lite`, `gemini-2.5-flash`, and `gemini-2.0-flash`.

I could verify `gemini-3-flash-preview` and the Gemini 2.5 model codes in the official Gemini API model list. I could not verify an official `gemini-3.1-flash-lite` API model code there, so the project uses supported model names.

## Request Flow

1. The user enters a question on the web page.
2. `IndexModel` validates the input.
3. `RagAnswerService` retrieves matching local documents and asks Gemini for a grounded answer.
4. `ChatHistorySessionService` stores the conversation in session.
5. The page re-renders with the updated chat transcript and source cards.

## Why Use Session?

Session is a simple beginner-friendly way to keep short-lived chat history without needing a database. Each browser session keeps its own conversation until it expires or the user clears it.
