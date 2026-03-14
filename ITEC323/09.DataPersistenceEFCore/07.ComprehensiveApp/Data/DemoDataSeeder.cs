using Microsoft.EntityFrameworkCore;

namespace ComprehensiveApp.Data;

/// <summary>
/// Applies database migrations when the app starts.
/// </summary>
public static class DemoDataSeeder
{
    /// <summary>
    /// Ensures the database schema exists and seed data is applied.
    /// </summary>
    /// <param name="context">Application database context.</param>
    public static Task SeedAsync(AppDbContext context)
    {
        return context.Database.MigrateAsync();
    }
}