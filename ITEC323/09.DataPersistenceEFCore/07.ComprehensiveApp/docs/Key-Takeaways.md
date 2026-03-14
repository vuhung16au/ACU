# Key Takeaways

- A final EF Core app becomes much easier to reason about when relationships are explicit in the model layer.
- `Include()` and `ThenInclude()` are essential when pages need related customer, category, and order data.
- Tag helpers and `[BindProperty]` make complex forms manageable without abandoning strong typing.
- EF Core change tracking can coordinate multiple updates in one unit of work, such as creating an order and reducing stock.
- LINQ scales from simple filtering to grouped analytics, so the same syntax supports both CRUD pages and dashboards.
- Secure connection string handling still applies in larger projects: keep secrets in User Secrets or environment variables, not in source control.
