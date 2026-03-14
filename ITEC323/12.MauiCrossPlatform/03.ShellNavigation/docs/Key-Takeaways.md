# Key Takeaways

- Shell provides structured app navigation using URI-like routes.
- Routes are registered once and reused from ViewModels via GoToAsync.
- Query strings allow passing lightweight state such as taskId between pages.
- [QueryProperty] maps query parameters directly into bindable ViewModel properties.
- Relative route .. supports predictable back navigation in hierarchical flows.
- Keep route names centralized in one constants file to avoid typos.
- DI still composes pages, view models, and services cleanly in MAUI apps.
- MVVM keeps navigation logic and state in ViewModels, not in XAML code-behind events.
- A list -> detail -> edit chain is a practical baseline for many business apps.
