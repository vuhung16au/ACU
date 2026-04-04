using ConcurrencyConflicts.Data;
using ConcurrencyConflicts.Services;
using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddRazorPages();
builder.Services.AddDbContext<AppDbContext>(options =>
{
    var connectionString = builder.Configuration.GetConnectionString("DefaultConnection")
        ?? "Host=localhost;Port=5432;Database=advanced_efcore_concurrency;Username=advanced_efcore_user;Password=advanced_efcore_password";

    options.UseNpgsql(connectionString);
    options.EnableSensitiveDataLogging();
    options.LogTo(message =>
    {
        if (!string.IsNullOrWhiteSpace(message))
        {
            Console.WriteLine(message);
        }
    });
});
builder.Services.AddScoped<ConcurrencyConflictDemoService>();

var app = builder.Build();

await using (var scope = app.Services.CreateAsyncScope())
{
    var initializer = new AppDbContextInitializer(
        scope.ServiceProvider.GetRequiredService<AppDbContext>(),
        scope.ServiceProvider.GetRequiredService<ILogger<AppDbContextInitializer>>());

    await initializer.InitializeAsync();
}

if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error");
}

app.UseRouting();
app.UseStaticFiles();
app.UseAuthorization();
app.MapRazorPages();

app.Run();
