# Key Takeaways

- Start with the **simplest tool that works**:
  - Vanilla JavaScript is usually enough for small, focused interactions inside Razor Pages.
  - Alpine.js is a good next step when you want reactivity without a full SPA build.
  - HTMX shines when Razor and C# should stay in charge of most UI and data logic.

- Use **Blazor** when:
  - Your team is comfortable with C# and wants type safety across client and server.
  - You are building richer forms, dashboards, or multi-step experiences.
  - You prefer sharing .NET models instead of duplicating types in JavaScript.

- Use **React** or **Vue** when:
  - Your UI is highly dynamic, with many components talking to each other.
  - You need a modern SPA experience with routing, client-side state, and many reusable widgets.
  - Your team is already invested in front-end tooling (Vite, NPM, testing libraries).

- When comparing approaches, always consider:
  - **Learning curve** &mdash; how quickly can a new student become productive?
  - **Complexity** &mdash; does the tool add more concepts than the feature really needs?
  - **Ecosystem** &mdash; what libraries, examples, and community support are available?
  - **Integration with ASP.NET Core** &mdash; how easily does the approach talk to your .NET APIs?

- For assessment or portfolio work:
  - Choose one feature (for example, a course planner, announcement board, or todo list).
  - Implement it at least twice using different approaches from this module.
  - Write a short reflection explaining which approach you would choose in a real project and why.

