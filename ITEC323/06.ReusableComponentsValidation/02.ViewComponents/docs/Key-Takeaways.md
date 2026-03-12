# Key Takeaways

- View Components are reusable UI units with C# logic plus a Razor view.
- Create the class in `ViewComponents/` and the view in `Views/Shared/Components/<Name>/Default.cshtml`.
- Use `InvokeAsync` to fetch data, filter records, or calculate values before rendering.
- Render components with tag helpers like `<vc:shopping-cart></vc:shopping-cart>`.
- Register the project assembly in `Pages/_ViewImports.cshtml` with `@addTagHelper *, ViewComponentsDemo`.
