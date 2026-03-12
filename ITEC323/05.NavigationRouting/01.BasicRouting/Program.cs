using BasicRouting.Routing;

/// <summary>
/// BasicRouting demonstrates how ASP.NET Core Razor Pages maps URLs to files.
/// Students can explore default routes, route parameters, route constraints,
/// and friendly URLs in a small beginner-friendly project.
/// </summary>
var builder = WebApplication.CreateBuilder(args);

// Add Razor Pages and register a custom route constraint used by the blog example.
builder.Services.AddRazorPages();
builder.Services.Configure<RouteOptions>(options =>
{
    options.ConstraintMap.Add("slug", typeof(SlugRouteConstraint));
});

var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error");
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseStaticFiles();
app.UseRouting();
app.UseAuthorization();

// Map Razor Pages using the conventional folder-based routing system.
app.MapRazorPages();

app.Run();
