# Functional Requirements Document (FRD)
## Hello World Data Application (EF Core + SQLite)

**Project Name**: Hello World Data  
**Version**: 1.0  
**Date**: February 2026  
**Course**: ITEC323 - Application Development  
**Institution**: Australian Catholic University (ACU)

---

## 1. Purpose

The purpose of this application is to introduce first-time learners to data-driven .NET development using Entity Framework Core and SQLite in a simple console application.

### Educational Goals
- Teach basic EF Core setup in a console app
- Demonstrate how a C# model maps to a database table
- Explain `DbContext` as the bridge between code and database
- Show how migrations create/update the SQLite schema
- Demonstrate full CRUD operations in one straightforward workflow

---

## 2. Scope

### 2.1 In Scope
This application will:
- Use a .NET console application as the host
- Use EF Core with SQLite provider
- Define one simple model (`Student`)
- Define one `DbContext` (`AppDbContext`)
- Create a local SQLite database file (`app.db`)
- Demonstrate Create, Read, Update, and Delete operations in `Program.cs`
- Include complete beginner-friendly documentation

### 2.2 Out of Scope
This application will NOT:
- Use complex architecture patterns (repositories, services)
- Use dependency injection setup for data access
- Include multiple entities and relationships
- Include authentication, authorization, or APIs
- Use external databases (SQL Server, PostgreSQL, cloud DB)

---

## 3. Functional Requirements

### FR-1: Project Setup
**Priority**: Critical  
**Description**: The project must be creatable and runnable with the .NET CLI on macOS.

**Acceptance Criteria**:
- A console project exists and targets .NET 10.0
- Project restores with `dotnet restore`
- Project builds with `dotnet build`
- Project runs with `dotnet run`

---

### FR-2: EF Core Package Dependencies
**Priority**: Critical  
**Description**: The project must include required EF Core packages for SQLite and migrations.

**Acceptance Criteria**:
- Includes `Microsoft.EntityFrameworkCore.Sqlite`
- Includes `Microsoft.EntityFrameworkCore.Design`
- Package references resolve successfully during restore

---

### FR-3: Data Model Definition
**Priority**: Critical  
**Description**: The application must define one simple entity model.

**Acceptance Criteria**:
- A `Student` class exists
- `Student` includes `Id` property (integer key)
- `Student` includes `Name` property (string)

---

### FR-4: Database Context Definition
**Priority**: Critical  
**Description**: The application must define a `DbContext` for SQLite database access.

**Acceptance Criteria**:
- `AppDbContext` class inherits from `DbContext`
- Contains `DbSet<Student>`
- Configures SQLite connection string as `Data Source=app.db`
- Uses straightforward configuration suitable for beginners

---

### FR-5: Database Migration Workflow
**Priority**: High  
**Description**: The project must support EF Core migrations via CLI.

**Acceptance Criteria**:
- `dotnet ef migrations add InitialCreate` succeeds
- `dotnet ef database update` succeeds
- Local SQLite file (`app.db`) is created
- Schema includes table for `Students`

---

### FR-6: CRUD Demonstration
**Priority**: Critical  
**Description**: The application must demonstrate all CRUD operations in sequence.

**Acceptance Criteria**:
- Create: adds a `Student` row
- Read: retrieves inserted row
- Update: changes `Student.Name`
- Delete: removes the row
- Final check confirms expected row count
- Console output clearly indicates each step

---

### FR-7: Beginner-Friendly Code
**Priority**: High  
**Description**: The code must be easy to read and explain.

**Acceptance Criteria**:
- No advanced architecture patterns
- Minimal number of files/classes
- Clear naming and comments
- Public classes and members include XML documentation comments

---

## 4. Non-Functional Requirements

### NFR-1: Simplicity
- Code should prioritize readability over abstraction
- Concepts should be introduced progressively

### NFR-2: Maintainability
- Naming conventions align with C# standards
- Files are organized logically (`Models`, `Data`)
- Documentation stays consistent with implementation

### NFR-3: Compatibility
- Works with .NET 10 SDK
- Uses SQLite local file to avoid external infrastructure

### NFR-4: Security
- No secrets or credentials are hardcoded
- Database file remains local to project folder

### NFR-5: Documentation
- Must include `README.md`, `QUICKSTART.md`, and this `FRD.md`
- Must include additional concepts doc in `docs/`

---

## 5. Technical Requirements

### 5.1 Technology Stack
- **Application Type**: .NET Console Application
- **Framework**: .NET 10.0
- **Language**: C#
- **ORM**: Entity Framework Core
- **Database**: SQLite

### 5.2 NuGet Dependencies
- `Microsoft.EntityFrameworkCore.Sqlite`
- `Microsoft.EntityFrameworkCore.Design`

### 5.3 CLI Tooling
- `.NET CLI`
- `dotnet-ef` tool for migrations

---

## 6. Success Criteria

This project is successful when:
1. Students can restore, build, and run the app on macOS using CLI
2. Students can create and apply EF Core migrations
3. Students observe each CRUD step in console output
4. Students understand the relationship between model, context, and database
