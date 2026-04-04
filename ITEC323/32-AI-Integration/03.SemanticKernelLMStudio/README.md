# 03.SemanticKernelLMStudio

## Overview

This project demonstrates how to connect an ASP.NET Core Minimal API to a local LM Studio server by using Microsoft Semantic Kernel.

Students learn two related workflows:

- direct chat completion with a local model
- Retrieval-Augmented Generation (RAG) using embedded local documents

## Learning Objectives

By working through this project, students will learn how to:

- configure Semantic Kernel for an OpenAI-compatible local endpoint
- call a local chat model from C#
- generate embeddings for a small knowledge base
- perform simple in-memory similarity search for RAG
- separate AI logic into clear service classes

## Main Features

- `GET /` returns a short API overview
- `POST /api/chat` sends a prompt directly to LM Studio through Semantic Kernel
- `POST /api/rag` retrieves relevant document chunks before generating an answer
- `scripts/playwright-semantic-kernel-lm-studio.js` records walkthrough screenshots and a video into `artifacts/`
- `scripts/playwright-chat-only.js` and `scripts/playwright-rag-only.js` create shorter focused demo clips

## Project Structure

```text
03.SemanticKernelLMStudio/
├── 03.SemanticKernelLMStudio.csproj
├── Program.cs
├── KnowledgeBase/
├── Models/
├── Properties/
├── Services/
├── docs/
├── README.md
├── QUICKSTART.md
└── FRD.md
```

## Related Files

- [QUICKSTART.md](QUICKSTART.md)
- [FRD.md](FRD.md)
- [docs/architecture.md](docs/architecture.md)

## Prerequisites

- .NET 10.0 SDK
- LM Studio running locally
- one chat-capable model loaded in LM Studio
- one embedding model loaded in LM Studio

## Technology Stack

- ASP.NET Core Minimal API
- C# 14 / .NET 10.0
- Microsoft Semantic Kernel
- LM Studio OpenAI-compatible local API
- in-memory vector-style similarity search
- Playwright for automated screenshots and video capture
