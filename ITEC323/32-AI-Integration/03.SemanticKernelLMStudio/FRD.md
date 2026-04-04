# Functional Requirements Document

## Project: Semantic Kernel + LM Studio Demo

### Purpose

Teach students how to build a local AI-enabled web API by using Microsoft Semantic Kernel with LM Studio as an OpenAI-compatible backend.

### Functional Requirements

| # | Feature | Priority | Description |
| --- | --- | --- | --- |
| 1 | Kernel setup | P0 | Configure Semantic Kernel to use LM Studio for chat completion and embedding generation |
| 2 | Chat endpoint | P0 | `POST /api/chat` accepts a prompt and returns a generated response |
| 3 | RAG endpoint | P0 | `POST /api/rag` accepts a question, retrieves relevant chunks, and returns a grounded answer |
| 4 | Knowledge indexing | P1 | Load sample knowledge base text during startup and compute embeddings |
| 5 | Error handling | P1 | Return clear server errors when LM Studio is unavailable or misconfigured |
| 6 | Documentation | P1 | Include README, QUICKSTART, FRD, and architecture notes for students |

### Non-Functional Requirements

- Performance should be acceptable for classroom demos on local hardware.
- Code should stay simple and readable for beginners.
- The project should run without any cloud API key.

### Success Criteria

- The project builds with .NET 10.
- `POST /api/chat` returns a local model response when LM Studio is running.
- `POST /api/rag` returns an answer and source chunks from the local knowledge base.
- Students can update model names through configuration.
