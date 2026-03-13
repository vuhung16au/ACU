# 02.PromisesAsyncAwait

Beginner-friendly ASP.NET Core Razor Pages project that demonstrates asynchronous JavaScript patterns using Promises, `async/await`, and the Fetch API.

## Screenshots / Demos

![Promises and Async Await Demo](images/promise-async-await.gif)

## Learning Objectives

- Understand Promise flow with `.then()`, `.catch()`, and `.finally()`
- Use `async`/`await` with `try/catch` for readable asynchronous code
- Call local ASP.NET Core JSON endpoints using Fetch API
- Handle loading, success, and error states in the UI
- Submit form data asynchronously with a JSON `POST` request

## What Is Included

- Razor Pages frontend with a dedicated async practice page (`/AsyncLab`)
- Minimal API endpoints for GET summary/list and POST create operations
- In-memory `AsyncLearningSessionService` so the app runs offline
- Plain JavaScript demo showing both Promise chain and async/await styles
- Supporting docs in `QUICKSTART.md` and `docs/Key-Takeaways.md`

## Project Structure

```text
02.PromisesAsyncAwait/
├── Models/
├── Pages/
│   ├── Index.cshtml
│   ├── AsyncLab.cshtml
│   ├── Privacy.cshtml
│   └── Shared/
├── Services/
├── docs/
├── wwwroot/
│   ├── css/
│   └── js/
├── QUICKSTART.md
└── README.md
```

## Key Idea

Both Promise chains and `async/await` solve the same asynchronous problem. Students should learn both styles, then prefer the one that keeps code easiest to read and maintain.
