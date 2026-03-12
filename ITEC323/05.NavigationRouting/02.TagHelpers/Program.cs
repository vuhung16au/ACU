using TagHelpers.Routing;

/// <summary>
/// TagHelpers demonstrates how ASP.NET Core Razor Pages generates links using
/// Tag Helpers such as asp-page, asp-route-*, and asp-page-handler.
/// </summary>
var builder = WebApplication.CreateBuilder(args);

// Add Razor Pages and register a custom route constraint used by the blog examples.
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

// Map Razor Pages and let Tag Helpers generate URLs to these endpoints.
app.MapRazorPages();

app.Run();
