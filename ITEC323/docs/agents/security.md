# Security Guidelines for AI Agents

This document provides focused security guidance for AI agents contributing to the ITEC323 repository.

## Overview

Use these guidelines whenever you write code, configuration, documentation, or examples that handle user input, data access, or application settings.

## Core Security Rules

### 1. Never Store Secrets in Source Code

Do not include any of the following in committed code or documentation examples:

- API keys
- Connection strings
- Passwords or credentials
- Tokens or secrets

Use safer alternatives instead:

- Environment variables
- Configuration files
- Secure credential storage

```csharp
// Bad: Hardcoded secret
var apiKey = "sk_live_abc123xyz";

// Good: Read from configuration
var apiKey = configuration["ApiSettings:ApiKey"];
```

### 2. Validate Input

- Validate all user input before processing it
- Apply validation on both client and server when applicable
- Treat API input as untrusted, even if it comes from your own frontend
- Show clear validation messages so students can understand what went wrong

### 3. Protect Data Access

- Use parameterized queries for database operations
- Prefer Entity Framework or other safe data access patterns where appropriate
- Avoid building SQL statements by concatenating user input

### 4. Encode Output

- Encode output in web applications to reduce cross-site scripting (XSS) risk
- Do not render raw user input into HTML unless it has been safely handled

### 5. Check Authorization

- Check authorization before showing data or allowing actions
- Do not assume a hidden button or page link is enough protection

### 6. Avoid Leaking Sensitive Information

- Do not expose secrets in code, config files committed to source control, logs, or error messages
- Keep error messages helpful for learners without revealing sensitive system details

## Short Policies

- Secure by default
- Validate before processing
- Encode before displaying
- Parameterize before querying
- Keep secrets out of the repository

## When in Doubt

1. Assume all input is untrusted
2. Choose the simplest secure approach students can understand
3. Keep secrets in configuration, not source code
4. Refer to the main [AGENTS.md](../../AGENTS.md) for repository-wide guidance
