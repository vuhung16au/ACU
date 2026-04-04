# Functional Requirements Document

## Purpose

Create a beginner-friendly ASP.NET Core Minimal API that demonstrates direct text generation with Google Gemini and a simple JSON-based RAG workflow.

## Functional Requirements

1. The application must expose a root endpoint that describes the available API routes.
2. The application must expose a document endpoint that returns the local knowledge base metadata.
3. The application must expose a text generation endpoint that accepts a prompt and returns Gemini output.
4. The application must expose a RAG endpoint that accepts a question, retrieves relevant local documents, and returns an answer plus sources.
5. The application must load the Google API key from `.env.local` through environment variables.
6. The application must validate empty prompts and empty questions and return `400 Bad Request`.
7. The solution must include unit tests for environment loading, retrieval, knowledge base loading, and RAG prompt composition.

## Non-Functional Requirements

1. The project must target .NET 10.0.
2. The code must stay simple enough for beginners to follow.
3. The project must avoid hardcoded secrets.
4. The project must keep retrieval local and lightweight without a vector database.

## Success Criteria

- `dotnet build` succeeds
- `dotnet test` succeeds
- students can run the API locally with a `.env.local` file
- students can compare direct generation with grounded RAG responses
