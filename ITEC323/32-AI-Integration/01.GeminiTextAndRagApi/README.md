# 01.GeminiTextAndRagApi

## Overview

This project demonstrates how to integrate an ASP.NET Core Minimal API with the Google Gemini API by using the `Google.GenAI` library.

It is configured to try `gemini-3-flash-preview` first, then fall back to `gemini-2.5-flash-lite`, `gemini-2.5-flash`, and `gemini-2.0-flash`.

Students learn two related ideas:

- direct text generation from a prompt
- simple Retrieval-Augmented Generation (RAG) using a local knowledge base

## Learning Objectives

By working through this project, students will learn how to:

- call the Gemini API from C#
- store an API key in `.env.local`
- bind configuration from `appsettings.json`
- separate AI logic into small service classes
- build a beginner-friendly RAG flow without a database or vector store

## Main Features

- `GET /` returns a short API overview
- `GET /api/documents` returns the local knowledge base metadata
- `POST /api/generate` sends a prompt directly to Gemini
- `POST /api/rag-answer` retrieves matching local documents and sends grounded context to Gemini

## Project Structure

```text
01.GeminiTextAndRagApi/
├── 01.GeminiTextAndRagApi.csproj
├── Program.cs
├── Models/
├── Services/
├── Data/
├── Properties/
├── docs/
├── images/
├── tests/
├── README.md
├── QUICKSTART.md
└── FRD.md
```

## Related Files

- [QUICKSTART.md](QUICKSTART.md)
- [FRD.md](FRD.md)
- [docs/GeminiAndRagBasics.md](docs/GeminiAndRagBasics.md)

## Prerequisites

- .NET 10.0 SDK
- a Google Gemini API key
- a local `.env.local` file based on `.env.local.example`

## Technology Stack

- ASP.NET Core Minimal API
- C# 14 / .NET 10.0
- `Google.GenAI`
- local JSON knowledge base for retrieval
