# Key Takeaways

- Do not commit connection strings or passwords to the repository.
- User Secrets are the right choice for local development secrets.
- Environment variables are the standard production override mechanism.
- `appsettings.json` can remain secret-free while the app still runs correctly.
- ASP.NET Core configuration precedence lets secure sources override base config.
- Docker Compose should read secrets from local environment files, not hardcoded values.
- Security improvements should not break the normal EF Core CRUD workflow.
