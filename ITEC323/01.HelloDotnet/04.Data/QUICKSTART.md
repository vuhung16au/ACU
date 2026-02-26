# Quick Start Guide - Hello World Data (EF Core + SQLite)

This guide walks you through building and running a simple data-driven console application using EF Core and SQLite.

## Prerequisites Check

Verify .NET SDK installation:

```bash
dotnet --version
```

Expected output: `10.0.xxx` (or newer)

## Step 1: Navigate to the Project Directory

From repository root:

```bash
cd 01.HelloDotnet/04.Data
```

## Step 2: Restore NuGet Dependencies

```bash
dotnet restore
```

What this does:
- Downloads EF Core packages defined in `HelloWorldData.csproj`

## Step 3: Install EF CLI Tool (if needed)

Check if the Entity Framework CLI tool is installed:

```bash
dotnet ef --version
```

If command is missing, install globally:

```bash
dotnet tool install --global dotnet-ef
```

If already installed, update it:

```bash
dotnet tool update --global dotnet-ef
```

## Step 4: Create the Initial Migration

```bash
dotnet ef migrations add InitialCreate
```

What this does:
- Creates migration files that describe how to build the database schema.

## Step 5: Apply Migration to SQLite

```bash
dotnet ef database update
```

What this does:
- Creates `app.db` in the project directory
- Creates the `Students` table in that database

## Step 6: Build the Application

```bash
dotnet build
```

Expected output:

```
Build succeeded.
    0 Warning(s)
    0 Error(s)
```

## Step 7: Run the CRUD Demo

```bash
dotnet run
```

Expected output (example):

```
Starting CRUD demonstration with SQLite and EF Core...
CREATE: Added Student with Id=1, Name=Alice
READ: Found Student with Id=1, Name=Alice
UPDATE: Student name changed to 'Alice Updated'.
READ AFTER UPDATE: Id=1, Name=Alice Updated
DELETE: Student record removed.
FINAL CHECK: Total students in database = 0
CRUD demonstration complete.
```

## Verification Checklist

- [ ] `dotnet restore` succeeds
- [ ] `dotnet ef migrations add InitialCreate` succeeds
- [ ] `dotnet ef database update` creates `app.db`
- [ ] `dotnet run` prints all CRUD steps
- [ ] Final student count is `0`

## Common Issues and Solutions

### Issue 1: `dotnet ef` command not found

Solution:

```bash
dotnet tool install --global dotnet-ef
```

### Issue 2: Cannot access NuGet.org (`NU1301`)

Cause:
- DNS/network/proxy issue.

Solutions:
1. Check internet connectivity
2. Check NuGet source list:
   ```bash
   dotnet nuget list source
   ```
3. Retry restore after network is available:
   ```bash
   dotnet restore
   ```

### Issue 3: Migration fails because build fails

Solutions:
1. Run `dotnet build` first and fix compile errors
2. Ensure package versions restore correctly
3. Re-run migration command

## Create This Project From Scratch (Reference)

```bash
# 1) Create console app
dotnet new console -n HelloWorldData
cd HelloWorldData

# 2) Install EF Core packages
dotnet add package Microsoft.EntityFrameworkCore.Sqlite
dotnet add package Microsoft.EntityFrameworkCore.Design

# 3) Create migration and database
dotnet ef migrations add InitialCreate
dotnet ef database update

# 4) Run the app
dotnet run
```

---

For detailed explanations, see [docs/EntityFrameworkSqliteBasics.md](docs/EntityFrameworkSqliteBasics.md).
