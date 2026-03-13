# Key Takeaways

- LINQ lets you write strongly typed queries in C# instead of SQL strings.
- `Where`, `OrderBy`, and `Select` are the core read-query building blocks.
- `Include` loads related data in one query shape.
- `GroupBy` + aggregates (`Count`, `Average`, `Sum`) are useful for dashboards and summaries.
- `Skip` and `Take` support pagination patterns.
- `.AsNoTracking()` is a good default for read-only pages.
- In-memory provider enables fast experimentation but does not persist data after app restart.
