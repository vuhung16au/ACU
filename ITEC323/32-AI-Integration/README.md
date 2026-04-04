# 32-AI-Integration

## Overview

This module introduces beginner-friendly AI integration in .NET by connecting an ASP.NET Core Minimal API backend to the Google Gemini API.

The module includes two sample projects:

- `01.GeminiTextAndRagApi` - a Minimal API that demonstrates direct text generation and simple Retrieval-Augmented Generation (RAG)
- `02.GeminiTextAndRagRazor` - a Razor Pages web chat that lets users ask grounded questions from the browser

## Learning Objectives

By working through this module, students will learn how to:

- call the Google Gemini API from a C# backend
- store an API key safely in `.env.local`
- organize AI logic into small service classes
- build a simple RAG workflow with local JSON documents
- return clear JSON responses from Minimal API endpoints

## Project List

- [01.GeminiTextAndRagApi](01.GeminiTextAndRagApi/README.md)
- [02.GeminiTextAndRagRazor](02.GeminiTextAndRagRazor/README.md)

## Solution File

- `32-AI-Integration.sln` contains the app project and its unit tests
