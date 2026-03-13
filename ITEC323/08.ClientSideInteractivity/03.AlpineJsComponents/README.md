# 03.AlpineJsComponents

Beginner-friendly ASP.NET Core Razor Pages project that demonstrates Alpine.js components for lightweight client-side interactivity.

## Screenshots / Demos

![Alpine.js Components Demo](images/alpinejs-demo.gif)

## Learning Objectives

- Set up Alpine.js in a Razor Pages project using CDN
- Use `x-data`, `x-show`, `x-if`, and `x-on` directives
- Build beginner components: dropdown, modal, tabs, and accordion
- Handle form input with `x-model` and dynamic rendering
- Combine server-rendered Razor HTML with Alpine reactivity

## What Is Included

- Razor Pages frontend with a dedicated Alpine practice page (`/AlpineLab`)
- Alpine.js loaded from CDN in the shared layout
- Practical examples: counter, dropdown, modal, tabs, accordion, and todo list
- Home page tips from a small in-project service
- Supporting docs in `QUICKSTART.md` and `docs/Key-Takeaways.md`

## Project Structure

```text
03.AlpineJsComponents/
├── Models/
├── Pages/
│   ├── Index.cshtml
│   ├── AlpineLab.cshtml
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

Alpine.js is a strong middle ground between plain JavaScript and full SPA frameworks. It helps students add reactivity quickly while keeping Razor Pages as the primary architecture.
