# LM Studio Chat Basics

This document explains how the `05.ML-AI` project works.

## 1. Local AI Inference Flow

The chat app runs in your terminal and sends requests to LM Studio:

1. User types a prompt in console
2. App sends HTTP POST JSON request to LM Studio
3. LM Studio runs inference with local model
4. App reads JSON response and prints assistant text

## 2. Why This Design Is Beginner-Friendly

- Uses one `Program.cs` file
- No external AI SDK required
- Minimal request/response classes
- Easy to debug with printed errors

## 3. Request Format

The app sends payload with:

- `model`
- `messages` (one user message)
- `temperature`

Endpoint:

- `http://localhost:1234/v1/chat/completions`

## 4. Response Format Used

The app reads:

- `choices[0].message.content`

If no content is returned, it prints a clear fallback message.

## 5. Typical Extensions

After understanding this baseline, you can add:

1. Multi-turn conversation history
2. Streaming token output
3. Config file for endpoint and model
4. Retry policy for transient errors

## 6. Key Learning Point

A practical AI app starts with simple building blocks:
- user input
- HTTP request
- JSON parse
- output display

Once this loop is clear, larger AI architectures become much easier to understand.
