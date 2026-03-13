# Quick Start

## Prerequisites

- .NET 10 SDK installed
- `dotnet ef` tool installed

Install[ EF CLI tool](https://learn.microsoft.com/en-us/ef/core/cli/dotnet) if needed:

```bash
dotnet tool install --global dotnet-ef
```

## Run The Project

```bash
cd 09.DataPersistenceEFCore/01.BasicEFCore
dotnet restore
dotnet ef migrations add InitialCreate
dotnet ef database update
dotnet run
```

Open the URL shown in the terminal (usually `http://localhost:5000` with this project setup).

## Pages To Check

- `/` module home page and learning goals
- `/Products` product list (read)
- `/Products/Create` create form
- `/Products/Edit/{id}` update form
- `/Products/Details/{id}` record details
- `/Products/Delete/{id}` delete confirmation

## Build Check

```bash
dotnet build
```

## Migration Workflow Reminder

When you change `Models/Product.cs`:

```bash
dotnet ef migrations add DescribeYourChange
dotnet ef database update
```

## Troubleshooting

### `dotnet ef` not found

```bash
export PATH="$PATH:$HOME/.dotnet/tools"
dotnet ef --version
```

### SQLite database reset

Delete the local DB and re-run migration:

```bash
rm -f Data/basic-efcore.db
dotnet ef database update
```
