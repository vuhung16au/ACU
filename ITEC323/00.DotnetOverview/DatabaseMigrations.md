# Database Migrations

## What Is a Migration?

A migration is a versioned, code-tracked description of a change to a database schema — adding a table, renaming a column, adding an index, and so on.

Instead of running raw `ALTER TABLE` scripts by hand, migrations let you:

- **Track schema history** in source control alongside your code.
- **Apply changes repeatably** across every developer machine, CI pipeline, and production server.
- **Roll back** a bad change by running the previous migration's `Down()` method.

Think of migrations as "git commits for your database schema."

---

## General Workflow (Any Tool)

```
1. Change your model / schema definition in code
2. Generate a migration (diff between last state and current state)
3. Review the generated SQL
4. Apply the migration to the target database
5. Commit migration files to source control
```

The migration file captures both **Up** (apply) and **Down** (revert) operations.

---

## EF Core Migration Workflow

### 1. Install the CLI tool (once)

```bash
dotnet tool install --global dotnet-ef
```

### 2. Create a migration after changing a model

```bash
dotnet ef migrations add <MigrationName>
```

A timestamped file is created in `Migrations/`, e.g. `20260314_AddProductTable.cs`.

### 3. Review the generated migration

Open the file and check the `Up()` and `Down()` methods before applying.

### 4. Apply to the database

```bash
dotnet ef database update
```

### 5. Common maintenance commands

| Task | Command |
|---|---|
| Remove the last unapplied migration | `dotnet ef migrations remove` |
| Preview the SQL without applying | `dotnet ef migrations script` |
| List all migrations and their status | `dotnet ef migrations list` |
| Drop the database | `dotnet ef database drop` |

---

## Key Points

- **Never edit applied migrations.** Create a new one instead.
- **Always commit migration files** — they are source code, not build artefacts.
- **One migration per logical change** keeps history readable.
- Migration names should read like commit messages: `AddProductTable`, `RenameUnitPriceToCost`, `AddCategoryIndex`.
- EF Core auto-detects many changes (new property, removed column, renamed entity) but complex changes (splitting a table) may need manual tweaking.

---

## See Also

- [dotnet-commands.md](dotnet-commands.md) — full `dotnet ef` command reference
- [EF Core Migrations (official docs)](https://learn.microsoft.com/en-us/ef/core/managing-schemas/migrations/)
