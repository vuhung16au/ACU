# Quick Start

## Prerequisites

- .NET 10 SDK installed
- `dotnet ef` tool installed
- `dotnet-aspnet-codegenerator` tool installed

Install tools if needed:

```bash
dotnet tool install --global dotnet-ef
dotnet tool install --global dotnet-aspnet-codegenerator
```

## Run The Project

```bash
cd 09.DataPersistenceEFCore/05.ScaffoldingCRUD
dotnet restore
dotnet ef database update
dotnet run
```

Open the URL shown in the terminal (typically `http://localhost:5124`).

## Scaffolding Command Examples

```bash
# Install scaffolding design package (if missing)
dotnet add package Microsoft.VisualStudio.Web.CodeGeneration.Design

# Generate Razor Pages CRUD for Product
dotnet aspnet-codegenerator razorpage \
  -m Product \
  -dc AppDbContext \
  -udl \
  -outDir Pages/Products \
  --referenceScriptLibraries
```

## Pages To Check

- `/` module home page and goals
- `/Products` product list with category included
- `/Products/Create` product form with category dropdown
- `/Products/Edit/{id}` scaffolded update flow
- `/Products/Details/{id}` related data display
- `/Products/Delete/{id}` delete confirmation

## Build Check

```bash
dotnet build
```

## Migration Workflow Reminder

When you change models:

```bash
dotnet ef migrations add DescribeYourChange
dotnet ef database update
```

## Troubleshooting

### `dotnet aspnet-codegenerator` not found

```bash
export PATH="$PATH:$HOME/.dotnet/tools"
dotnet aspnet-codegenerator --help
```

### Reset local database

```bash
rm -f Data/scaffolding-crud.db
dotnet ef database update
```
