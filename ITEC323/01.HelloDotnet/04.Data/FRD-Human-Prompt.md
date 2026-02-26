Act as an expert .NET instructor. I want to build a very simple, educational data-driven application. Please walk me through creating a **.NET Console Application** that performs basic CRUD (Create, Read, Update, Delete) operations against a local **SQLite** database using **Entity Framework (EF) Core**.

I am using **macOS** and the **.NET CLI**.

Please provide a step-by-step tutorial following these exact requirements:

1. **Project Setup:** Provide the exact `.NET CLI` commands to create a new console app and navigate into its directory.
2. **Dependencies:** Provide the exact `.NET CLI` commands to install the necessary EF Core SQLite and EF Core Design NuGet packages.
3. **The Model:** Define a very simple C# class to represent a record in our database (e.g., a `Student` or a `Book` with just an ID and a Name/Title).
4. **The Database Context:** Show me how to create the `DbContext` class that connects our model to a local SQLite file (e.g., `app.db`). Keep the connection string hardcoded and simple for this educational example.
5. **Migrations:** Provide the `.NET CLI` commands (using `dotnet ef`) to create the initial database migration and apply it to create the physical SQLite file.
6. **The Logic:** Provide the code for `Program.cs` that demonstrates adding a new record, reading it back from the database, updating it, and finally deleting it.

Please explain what each block of code and CLI command does briefly and plainly. Do not introduce complex enterprise patterns like repositories or dependency injection; keep all the logic straightforward so I can easily understand how EF Core interacts with SQLite.

Reference: https://learn.microsoft.com/en-us/ef/core/get-started/overview/first-app?tabs=netcore-cli&WT.mc_id=dotnet-35129-website