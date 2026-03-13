var builder = WebApplication.CreateBuilder(args);

builder.Services.AddRazorPages();
builder.Services.AddServerSideBlazor();
builder.Services.AddHttpClient();
builder.Services.AddSingleton<BlazorInteractive.Services.BlazorDemoService>();
builder.Services.AddScoped(sp =>
{
    var navigationManager = sp.GetRequiredService<Microsoft.AspNetCore.Components.NavigationManager>();
    return new HttpClient { BaseAddress = new Uri(navigationManager.BaseUri) };
});

var app = builder.Build();

if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error");
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseStaticFiles();

app.UseRouting();

app.UseAuthorization();

app.MapRazorPages();
app.MapBlazorHub();

app.MapGet("/api/announcements", () =>
{
    var now = DateTime.UtcNow;
    return Results.Ok(new[]
    {
        new BlazorInteractive.Models.AnnouncementDto
        {
            Id = 1,
            Title = "Assignment 2 Q&A session",
            Audience = "ITEC323",
            StartsAtUtc = now.AddHours(4),
            Mode = "On-campus"
        },
        new BlazorInteractive.Models.AnnouncementDto
        {
            Id = 2,
            Title = "Blazor component review clinic",
            Audience = "All web streams",
            StartsAtUtc = now.AddDays(1),
            Mode = "Online"
        },
        new BlazorInteractive.Models.AnnouncementDto
        {
            Id = 3,
            Title = "Mini demo showcase",
            Audience = "Tutorial groups",
            StartsAtUtc = now.AddDays(2),
            Mode = "Hybrid"
        }
    });
});

app.Run();
