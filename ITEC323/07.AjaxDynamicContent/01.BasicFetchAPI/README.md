# 01.BasicFetchAPI

Simple ASP.NET Core Razor Pages project showing how the browser Fetch API can call a local JSON API and update the page without a full reload.


## Screenshot


## Learning Objectives

- Send asynchronous `GET`, `POST`, `PUT`, and `DELETE` requests with JavaScript `fetch()`
- Return JSON from ASP.NET Core API endpoints with clear HTTP status codes
- Update only the task summary and task table instead of refreshing the whole page
- Show beginner-friendly loading, success, and error states during requests
- Use simple polling to refresh data automatically every 15 seconds

## What Is Included

- Razor Pages frontend with a dedicated Fetch API demo page
- `StudyTasksController` with local JSON CRUD endpoints
- `StudyTaskService` using in-memory data so the app runs fully offline
- Plain JavaScript file that loads, creates, updates, and deletes tasks
- Beginner-focused documentation in `QUICKSTART.md` and `docs/Key-Takeaways.md`

## Project Structure

```text
01.BasicFetchAPI/
├── Controllers/
├── Models/
├── Pages/
│   ├── Index.cshtml
│   ├── Privacy.cshtml
│   ├── Tasks.cshtml
│   └── Shared/
├── Services/
├── docs/
├── QUICKSTART.md
└── README.md
```

## Key Idea

Fetch API is enough for many beginner AJAX tasks: request JSON from a local ASP.NET Core API, then replace only the part of the page that needs new data.
