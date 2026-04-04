# Quick Start

## Step 1: Prepare LM Studio

1. Start LM Studio.
2. Load a chat model in the local server tab.
3. Load an embedding model in the local server tab.
4. Start the local server and confirm it is listening at `http://localhost:1234/v1`.

If your model names differ from the defaults, create `.env.local` from `.env.local.example` and update the values.

## Step 2: Run the Project

```bash
cd 32-AI-Integration/03.SemanticKernelLMStudio
dotnet run
```

The API listens on `http://localhost:5000`.

## Step 3: Test the Endpoints

### Chat

```bash
curl -X POST http://localhost:5000/api/chat \
  -H "Content-Type: application/json" \
  -d '{"prompt":"Explain Semantic Kernel in one sentence."}'
```

### RAG

```bash
curl -X POST http://localhost:5000/api/rag \
  -H "Content-Type: application/json" \
  -d '{"question":"What does LM Studio provide for local development?","topK":3}'
```

## Step 4: Record Screenshots And Video

If the app is not running yet, the script can start it for you:

```bash
/opt/homebrew/bin/node 32-AI-Integration/03.SemanticKernelLMStudio/scripts/playwright-semantic-kernel-lm-studio.js
```

If the app is already running on a custom URL, point the script to it:

```bash
PLAYWRIGHT_START_APP=false PLAYWRIGHT_BASE_URL=http://127.0.0.1:5000 /opt/homebrew/bin/node 32-AI-Integration/03.SemanticKernelLMStudio/scripts/playwright-semantic-kernel-lm-studio.js
```

Generated screenshots, video, and a response summary are saved in `32-AI-Integration/03.SemanticKernelLMStudio/artifacts/`.

For shorter clips, run one of these focused scripts:

```bash
/opt/homebrew/bin/node 32-AI-Integration/03.SemanticKernelLMStudio/scripts/playwright-chat-only.js
/opt/homebrew/bin/node 32-AI-Integration/03.SemanticKernelLMStudio/scripts/playwright-rag-only.js
```

## Troubleshooting

| Issue | Cause | Fix |
| --- | --- | --- |
| `Connection refused` | LM Studio local server is not running | Start the LM Studio local server |
| Chat calls fail | No chat model is loaded | Load a chat model and update `LMStudio__ChatModelId` if needed |
| RAG calls fail during indexing | No embedding model is loaded | Load an embedding model and update `LMStudio__EmbeddingModelId` |
| Unexpected empty answers | The selected model rejected the request | Try a different chat model or lower the token count |
