using Microsoft.EntityFrameworkCore;
using NPlusOne.Data;
using NPlusOne.Services;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddRazorPages();
builder.Services.AddSingleton<QueryCaptureInterceptor>();
builder.Services.AddDbContext<AppDbContext>((serviceProvider, options) =>
{
    var connectionString = builder.Configuration.GetConnectionString("DefaultConnection")
        ?? "Host=localhost;Port=55432;Database=advanced_efcore_nplusone;Username=advanced_efcore_user;Password=advanced_efcore_password";

    options.UseNpgsql(connectionString);
    options.EnableSensitiveDataLogging();
    options.AddInterceptors(serviceProvider.GetRequiredService<QueryCaptureInterceptor>());
    options.LogTo(message =>
    {
        if (!string.IsNullOrWhiteSpace(message))
        {
            Console.WriteLine(message);
        }
    });
});
builder.Services.AddScoped<NPlusOneLabService>();

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
