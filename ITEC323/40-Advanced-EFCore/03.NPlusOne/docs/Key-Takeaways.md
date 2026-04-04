# Key Takeaways

- N+1 is usually a query-shape problem, not a syntax problem.
- The database may receive many more commands than the page makes obvious.
- `Include` can help, but projection often produces a smaller result.
- Counting SQL commands is a simple way to make the problem visible.
