# Quick Start

## Prerequisites

- Shared PostgreSQL services running from [`40-Advanced-EFCore`](../)
- .NET 10 SDK

## Run The Project

```bash
cd 40-Advanced-EFCore/03.NPlusOne
dotnet restore
dotnet run
```

The app creates and seeds its lesson database automatically on startup.

## Demo Flow

1. Open `/QueryLab`.
2. Compare the naive repeated-query workflow with the improved workflow.
3. Focus on the query counts and the SQL list.
4. Open `/Orders` to see the improved version used in a more realistic page.

## Inspect SQL

- Read the query list shown on `/QueryLab`
- Watch the terminal logs while loading the page
- Use pgAdmin or Azure Data Studio to inspect the `Orders`, `OrderItems`, and `Products` tables
