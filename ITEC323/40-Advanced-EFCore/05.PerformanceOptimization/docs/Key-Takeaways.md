# Key Takeaways

- Measure first. Do not guess that a query is slow because EF Core is “bad.”
- Read the generated SQL before changing the LINQ.
- `AsNoTracking()` helps read-only pages by removing change-tracker work.
- Projection reduces payload size because EF Core does not need every column.
- Paging keeps the database and browser from processing rows the user cannot see yet.
- Compiled queries help on hot paths after the bigger wins are already in place.
- Database tools matter. Use pgAdmin or Azure Data Studio with `EXPLAIN ANALYZE` to confirm that an optimization really helped.

- Optimization should be guided by evidence, not guesses.
- Smaller payloads and simpler query shapes often matter more than clever code.
- Generated SQL, indexes, and execution plans belong in the debugging workflow.
