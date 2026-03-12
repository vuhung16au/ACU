# 07.ComprehensiveApp

Comprehensive ASP.NET Core Razor Pages capstone project combining Fetch API CRUD, polling, HTMX-style fragment rendering, CORS configuration, and Swagger API documentation.

## Screenshots

![07.ComprehensiveApp demo](images/07.gif)

## Learning Objectives

- Combine multiple AJAX techniques in one local application
- Use Fetch API for JSON CRUD operations
- Use HTMX-style attributes for server-rendered HTML fragments
- Refresh summary data automatically with polling
- Configure CORS and document APIs with Swagger

## What Is Included

- Razor Pages frontend with a unified capstone dashboard
- `CapstoneTasksController` exposing RESTful JSON endpoints
- `CapstoneFragmentsController` returning HTML fragments for server-rendered panels
- `CapstoneDataService` storing all sample data in memory
- Local `htmx-lite.js` helper for the declarative fragment examples
- Swagger UI and XML-comment-backed API documentation
- Beginner-focused documentation in `QUICKSTART.md` and `docs/Key-Takeaways.md`

## Project Structure

```text
07.ComprehensiveApp/
├── Controllers/
├── Models/
├── Pages/
│   ├── Dashboard.cshtml
│   ├── Index.cshtml
│   ├── Privacy.cshtml
│   └── Shared/
├── Services/
├── Views/
│   └── Shared/Fragments/
├── docs/
├── QUICKSTART.md
└── README.md
```

## Key Idea

The capstone demonstrates that different update styles solve different problems: Fetch is good for JSON-driven logic, HTMX-style fragments are good for small HTML swaps, and polling is good for lightweight dashboard refreshes.
