# Key Takeaways

- Optimistic concurrency does not lock a row while a user is editing.
- EF Core checks the original concurrency token during `SaveChanges`.
- If the database row no longer matches the original token, EF Core throws `DbUpdateConcurrencyException`.
- A good user experience explains the conflict and gives clear next steps.
- The generated SQL is worth reading because it shows exactly what EF Core is protecting.
