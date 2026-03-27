# Quick Start

## Prerequisites

- .NET 10 SDK

## Run The API

```bash
cd 22-MinimalAPIs-REST/01.HelloMinimalApi
dotnet run
```

Open the local URL shown in the terminal, or test the API with your browser or `curl`.

This sample is set up so the `http://localhost:5622/...` `curl` commands work directly without needing `-L`.

## Example Requests

### Get the API overview

```bash
curl http://localhost:5622/
```

### Get the language list

```bash
curl http://localhost:5622/api/languages
```

### Get a personalized greeting

```bash
curl http://localhost:5622/api/greet/Ada
```

### Get a sample submission resource

```bash
curl http://localhost:5622/api/submissions/latest
```

### Post a sample submission

```bash
curl -X POST http://localhost:5622/api/submissions \
  -H "Content-Type: application/json" \
  -d '{"name":"Ada Lovelace","email":"ada@example.com","favoriteLanguage":"C#"}'
```

## Run The Unit Tests

```bash
cd 22-MinimalAPIs-REST/01.HelloMinimalApi/tests/01.HelloMinimalApi.Tests
dotnet restore
dotnet build
dotnet test
```

## Build Check

```bash
dotnet build
```
