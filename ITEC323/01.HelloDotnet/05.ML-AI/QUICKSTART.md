# Quick Start Guide - Hello World AI Chat (LM Studio)

This guide walks you through running a simple .NET CLI chat app that connects to a local LLM served by LM Studio.

## Prerequisites Check

Verify .NET SDK:

```bash
dotnet --version
```

Expected output: `10.0.xxx` (or newer)

## Step 1: Start LM Studio and Load a Model

1. Open LM Studio.
2. Download or select a local model.
3. Load the model.
4. Start the local server.

Confirm server URL is available at:

- `http://localhost:1234`

## Step 2: Navigate to Project Directory

From repository root:

```bash
cd 01.HelloDotnet/05.ML-AI
```

## Step 3: Restore and Build

```bash
dotnet restore
dotnet build
```

Expected output:

```
Build succeeded.
    0 Warning(s)
    0 Error(s)
```

## Step 4: Run the Chat App

```bash
dotnet run
```

You should see:

```
Hello World AI Chat (.NET CLI + LM Studio)
Endpoint: http://localhost:1234/v1/chat/completions
Model: local-model
Type your message and press Enter.
Type 'exit' to quit.
```

Then interact:

```text
You: Hello
Assistant: ...model response...
```

## Step 5: Exit

Type:

```text
exit
```

## Optional: Set a Custom Model Name

You can set the model name sent in the request:

```bash
export LMSTUDIO_MODEL="qwen2.5-7b-instruct"
dotnet run
```

## Verification Checklist

- [ ] LM Studio is running and model is loaded
- [ ] Local server is enabled on `localhost:1234`
- [ ] `dotnet build` succeeds
- [ ] App accepts user input and prints assistant responses

## Common Issues and Solutions

### Issue 1: Could not connect to LM Studio endpoint

Cause:
- LM Studio server is not running.

Solution:
1. Open LM Studio.
2. Load a model.
3. Start local server.
4. Re-run `dotnet run`.

### Issue 2: HTTP 400 or model not found

Cause:
- Model name in request does not match loaded model.

Solution:
- Set the model name with environment variable:

```bash
export LMSTUDIO_MODEL="your-loaded-model-name"
```

### Issue 3: Timeout errors

Cause:
- Model is large or system resources are limited.

Solution:
- Wait for model warm-up and try a shorter prompt.

## Create This Project From Scratch (Reference)

```bash
# 1) Create console app
dotnet new console -n HelloWorldAiChat
cd HelloWorldAiChat

# 2) Replace Program.cs with chat logic
# 3) Build and run
dotnet restore
dotnet build
dotnet run
```

---

For detailed explanations, see [docs/LmStudioChatBasics.md](docs/LmStudioChatBasics.md).
