# Quick Start

## Prerequisites

- Shared PostgreSQL services running from [`40-Advanced-EFCore`](../)
- .NET 10 SDK

## Run The Project

```bash
cd 40-Advanced-EFCore/04.RawSQL-Dapper
dotnet restore
dotnet run
```

The app creates and seeds its lesson database automatically on startup.

## Demo Flow

1. Open `/Reports`.
2. Compare the EF Core reporting projection with the Dapper SQL version.
3. Open `/Transactions`.
4. Run the shared transaction demo and review the inserted audit entry and stock update.

## Inspect SQL

- Read the SQL snippets shown on `/Reports`
- Watch the terminal logs for EF Core-generated SQL
- Use pgAdmin or Azure Data Studio to inspect `Products`, `SaleRecords`, and `AuditEntries`
