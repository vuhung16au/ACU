# Key Takeaways

- Partial Views are reusable `.cshtml` fragments, not full pages.
- Store shared partials in `Pages/Shared/`.
- Use `<partial name="_Header" />` for static shared markup.
- Use `<partial name="_ProductCard" model="product" />` to pass data into a strongly-typed partial.
- Use Partial Views for presentation reuse only. If backend logic is needed, use a View Component instead.
