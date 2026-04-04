# Functional Requirements Document

## Purpose

Create a Razor Pages web application that lets users chat with a simple Gemini-powered RAG workflow through a browser interface.

## Functional Requirements

1. The application must display a chat page where the user can submit a question.
2. The application must answer questions by retrieving relevant local documents and sending grounded context to Gemini.
3. The application must display the answer together with the source documents used.
4. The application must keep chat history in the user session until the session expires or the user clears it.
5. The application must provide a clear chat action.
6. The application must validate empty questions and show a friendly error message.
7. The solution must include unit tests for session history management and basic input validation.

## Non-Functional Requirements

1. The project must target .NET 10.0.
2. The code must stay simple and educational for beginners.
3. The application must avoid hardcoded API keys.
4. The chat page must be readable on phone and desktop screens.
