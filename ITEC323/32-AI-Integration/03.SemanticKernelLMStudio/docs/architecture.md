# Architecture Notes

## Request Flow

### Chat flow

1. The client sends a prompt to `POST /api/chat`.
2. `Program.cs` passes the request to `ChatService`.
3. `ChatService` uses Semantic Kernel's chat completion service.
4. Semantic Kernel forwards the request to LM Studio's OpenAI-compatible endpoint.
5. The generated response is returned to the client.

### RAG flow

1. The application reads `KnowledgeBase/documents.txt` at startup.
2. `RagService` splits the text into smaller chunks and generates embeddings.
3. Each chunk embedding is stored in an in-memory list.
4. When a user calls `POST /api/rag`, the question is embedded.
5. The question embedding is compared against stored chunk embeddings by cosine similarity.
6. The top matching chunks are inserted into a grounded prompt.
7. Semantic Kernel sends the grounded prompt to LM Studio and returns the answer with source chunks.

## Design Choices

- The project uses an in-memory index so students can focus on the RAG idea first.
- LM Studio keeps the demo local and avoids any cloud dependency.
- Small service classes make it easier to extend the sample later with a database or UI.
