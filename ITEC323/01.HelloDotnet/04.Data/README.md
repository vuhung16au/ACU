# Hello World Data (EF Core + SQLite)

A simple .NET Console application that demonstrates data access with Entity Framework Core and SQLite. This project is designed for first-time learners who want to understand CRUD operations (Create, Read, Update, Delete) in a clear and practical way.

## Screenshots / Execution Output

```bash
-> % dotnet run                            
Starting CRUD demonstration with SQLite and EF Core...
CREATE: Added Student with Id=1, Name=Alice
READ: Found Student with Id=1, Name=Alice
UPDATE: Student name changed to 'Alice Updated'.
READ AFTER UPDATE: Id=1, Name=Alice Updated
DELETE: Student record removed.
FINAL CHECK: Total students in database = 0
CRUD demonstration complete.
```

## The command line interface (CLI) to create and run the data app
*Coming soon - screenshots will be added after first run*

## The console output showing CRUD operations
*Coming soon - screenshots will be added after first run*

## Learning Objectives

By completing this project, you will learn:

- How to create a .NET console app for data processing
- How to add EF Core SQLite dependencies with the .NET CLI
- How to create a simple model class (`Student`)
- How to configure a `DbContext` for a local SQLite file (`app.db`)
- How to run EF Core migrations from the command line
- How to perform basic CRUD operations in C#

## What is EF Core?

Entity Framework Core (EF Core) is an Object-Relational Mapper (ORM). It lets you work with database data using C# objects instead of writing SQL for every operation.

In this project:
- `Student` class represents a table row
- `AppDbContext` manages database access
- SQLite stores data in a local file (`app.db`)

## Prerequisites

To work with this project, you'll need:

- **.NET 10.0 SDK or later**
- A code editor such as **Visual Studio Code**, **Visual Studio**, or **Rider**
- Internet access for restoring NuGet packages
- Basic C# syntax knowledge

## Quick Links

- [QUICKSTART.md](QUICKSTART.md) - Step-by-step instructions to build and run this project
- [FRD.md](FRD.md) - Functional Requirements Document
- [docs/EntityFrameworkSqliteBasics.md](docs/EntityFrameworkSqliteBasics.md) - Detailed explanation of EF Core + SQLite concepts

## Technology Stack

- **Application Type**: .NET Console Application
- **Language**: C#
- **Target Framework**: .NET 10.0
- **Database**: SQLite (local file)
- **ORM**: Entity Framework Core
- **NuGet Packages**:
  - `Microsoft.EntityFrameworkCore.Sqlite`
  - `Microsoft.EntityFrameworkCore.Design`

## Project Structure

```
04.Data/
├── Data/
│   └── AppDbContext.cs               # EF Core DbContext configuration
├── Models/
│   └── Student.cs                    # Data model class
├── docs/
│   └── EntityFrameworkSqliteBasics.md # Detailed concepts and explanations
├── images/                           # Screenshots and diagrams
├── Program.cs                        # CRUD demonstration logic
├── HelloWorldData.csproj             # Project file and package references
├── README.md                         # This file
├── QUICKSTART.md                     # Getting started guide
└── FRD.md                            # Functional requirements
```

## Key Files

### Program.cs
Contains a full CRUD walkthrough:
- Creates database if needed
- Adds a student record
- Reads the record from database
- Updates the record name
- Deletes the record
- Confirms final row count

### Data/AppDbContext.cs
Defines the EF Core database context and configures SQLite using:

`Data Source=app.db`

### Models/Student.cs
Defines the `Student` entity with two properties:
- `Id` (primary key)
- `Name`

## What's Next?

After running this beginner project:

1. Add a second model (for example, `Course`)
2. Add one-to-many relationships (Student -> Enrollments)
3. Write LINQ queries with filtering and sorting
4. Move from console to ASP.NET Core Web API
5. Add validation rules for input data

## Educational Notes

This project is intentionally simple to keep focus on fundamentals:

- No repository pattern
- No dependency injection setup in `Program.cs`
- No advanced architecture
- Straightforward, readable CRUD flow

## Additional Resources

- [EF Core Documentation](https://learn.microsoft.com/ef/core/)
- [SQLite Provider for EF Core](https://learn.microsoft.com/ef/core/providers/sqlite/)
- [dotnet ef CLI tools](https://learn.microsoft.com/ef/core/cli/dotnet)

# Reference

[Getting Started with EF Core](https://learn.microsoft.com/en-us/ef/core/get-started/overview/first-app?tabs=netcore-cli&WT.mc_id=dotnet-35129-website)
