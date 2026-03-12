# Key Takeaways

- Use partial views for repeated markup, not for page-level routing.
- Keep `_Layout.cshtml` for the site shell and partials for reusable fragments inside it.
- Pass a typed model into a partial when the fragment needs its own data.
- Start with duplication: if the same UI appears in multiple places, extract it.
- Good partials stay small, focused, and easy to understand.
