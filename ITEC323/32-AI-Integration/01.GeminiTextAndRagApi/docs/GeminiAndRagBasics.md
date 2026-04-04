# Gemini And RAG Basics

## Direct Text Generation

Direct text generation sends a prompt straight to the model. In this project, `POST /api/generate` uses the `Google.GenAI` client to send a single prompt to Gemini and return the model response.

The sample configuration uses `gemini-3-flash-preview` as the primary model, then falls back to `gemini-2.5-flash-lite`, `gemini-2.5-flash`, and `gemini-2.0-flash`.

I could verify `gemini-3-flash-preview` and the Gemini 2.5 model codes in the official Gemini API model list. I could not verify an official `gemini-3.1-flash-lite` API model code there, so the project uses supported model names.

This is useful for:

- summaries
- explanations
- brainstorming

The main limitation is that the answer depends on the model's general knowledge and the prompt you provide.

## What Is RAG?

RAG stands for Retrieval-Augmented Generation.

It adds one more step before generation:

1. find relevant documents
2. place those documents into the prompt as context
3. ask the model to answer using that context

This often improves relevance because the model can use course-specific or organization-specific information that is not part of its general training.

## How Retrieval Works In This Project

This sample keeps retrieval simple on purpose.

- the knowledge base is stored in `Data/sample-knowledge-base.json`
- each document has a title, category, content, and keywords
- the retrieval service scores documents by checking whether question words appear in the document
- the top matching documents are added to the RAG prompt

This project does not use:

- embeddings
- a vector database
- semantic search infrastructure

That makes it easier for beginners to understand the flow before learning more advanced AI patterns.

## Why Store The API Key In `.env.local`?

Secrets such as API keys should not be hardcoded into C# source files or committed to Git.

In this project:

- `.env.local.example` shows the expected format
- `.env.local` is ignored by Git
- `EnvironmentFileLoader` reads the file when the app starts

This keeps the key local to the student's machine.

## Comparing The Two Endpoints

`POST /api/generate`

- sends only the user's prompt
- useful for general tasks

`POST /api/rag-answer`

- retrieves matching local documents first
- sends a grounded prompt with context
- useful when answers should stay close to the supplied knowledge base

# Gemini API libraries

https://ai.google.dev/gemini-api/docs/libraries#c
