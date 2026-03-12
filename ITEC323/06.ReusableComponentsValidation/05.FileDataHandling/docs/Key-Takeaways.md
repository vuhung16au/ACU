# Key Takeaways

- Use `Path.Combine` and `IWebHostEnvironment.ContentRootPath` for cross-platform file paths.
- Use `File.ReadAllLinesAsync()` and `File.AppendAllTextAsync()` for non-blocking file access.
- Validate data before saving it to CSV.
- Parse CSV carefully, especially when fields may contain commas.
- Handle `FileNotFoundException` and `IOException` with user-friendly messages.
