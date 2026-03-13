# 01.VanillaJsBasics

Beginner-friendly ASP.NET Core Razor Pages project that demonstrates modern vanilla JavaScript for client-side interactivity.

## Screenshots / Demos

![Vanilla JS Playground Demo](images/vanilla-demo.gif)

## Learning Objectives

- Select DOM elements using `querySelector` and `querySelectorAll`
- Register events with `addEventListener`
- Toggle visual states with `classList`
- Understand when to use `textContent` and when to use `innerHTML`
- Validate form inputs in JavaScript before rendering dynamic content

## What Is Included

- Razor Pages frontend with a dedicated interactive page (`/Playground`)
- Small service-based tip list for home page guidance
- Vanilla JavaScript file with examples of:
  - DOM selection and button events
  - Class toggling and UI feedback messages
  - Safe text rendering and controlled HTML rendering
  - Simple client-side form validation and dynamic card insertion
- Supporting docs in `QUICKSTART.md` and `docs/Key-Takeaways.md`

## Project Structure

```text
01.VanillaJsBasics/
├── Models/
├── Pages/
│   ├── Index.cshtml
│   ├── Playground.cshtml
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

For many beginner web features, native browser APIs are enough. Students can build clear, responsive interactions in Razor Pages without jQuery or large frontend frameworks.
