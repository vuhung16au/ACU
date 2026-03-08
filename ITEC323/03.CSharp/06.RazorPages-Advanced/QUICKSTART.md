# Quick Start Guide

Follow these steps to get the RazorPages Advanced project running on your machine.

## Prerequisites

- .NET 8.0 SDK or later installed
- Terminal/Command Prompt access
- Web browser

Check your .NET version:
```bash
dotnet --version
```

## Step 1: Navigate to Project Directory

```bash
cd /path/to/ITEC323/03.CSharp/06.RazorPages-Advanced
```

## Step 2: Restore Dependencies

Download and install all required NuGet packages:

```bash
dotnet restore
```

This installs:
- Entity Framework Core
- SQLite database provider
- Design-time tools

## Step 3: Build the Project

Compile the application:

```bash
dotnet build
```

You should see: `Build succeeded. 0 Error(s)`

## Step 4: Create the Database

The database is automatically created when you run the application for the first time. The `Program.cs` file contains code that:
1. Checks if the database exists
2. Creates it if it doesn't
3. Seeds it with 5 sample users

No manual migration commands needed!

## Step 5: Run the Application

Start the web server:

```bash
dotnet run
```

You'll see output like:
```
Now listening on: https://localhost:5001
Now listening on: http://localhost:5000
```

## Step 6: Open in Browser

Open your web browser and navigate to:
```
https://localhost:5001
```

or

```
http://localhost:5000
```

## What You Should See

The home page displays:
- **User Directory** heading
- A table with 5 users (Alice, Bob, 田中太郎, Maria, 李明)
- User information: ID, Name, Email, Country, Created Date
- Total user count
- JSON representation of the data

## Testing the Language Switcher

The language switcher dropdown is visible in the navigation bar. Select "日本語 (Japanese)" to see the page reload with the Japanese culture parameter:
```
https://localhost:5001?culture=ja
```

Note: Full localization strings are not yet implemented, but the infrastructure is ready.

## Common Issues

### Port Already in Use
If port 5000/5001 is busy, edit `Properties/launchSettings.json` to change ports.

### Database File Locked
If you see "database is locked" errors:
1. Stop the application (Ctrl+C)
2. Delete `users.db` file
3. Run `dotnet run` again

### Build Errors
If you encounter build errors:
```bash
dotnet clean
dotnet restore
dotnet build
```

## Stopping the Application

Press `Ctrl+C` in the terminal to stop the web server.

## Exploring the Code

Key files to review:
1. [Models/User.cs](Models/User.cs) - Data model
2. [Data/AppDbContext.cs](Data/AppDbContext.cs) - Database context with seed data
3. [Pages/Index.cshtml](Pages/Index.cshtml) - Razor page with @foreach loop
4. [Pages/Index.cshtml.cs](Pages/Index.cshtml.cs) - Page model loading data
5. [Program.cs](Program.cs) - Application configuration

## Next Steps

- Modify the User model to add new properties
- Add more seed data in `AppDbContext.cs`
- Create a new page to add users
- Implement full localization with resource files

---

For more detailed explanations, see [docs/Key-Takeaways.md](docs/Key-Takeaways.md).
