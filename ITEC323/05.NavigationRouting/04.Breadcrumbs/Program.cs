using Breadcrumbs.Routing;

/// <summary>
/// Breadcrumbs demonstrates breadcrumb navigation patterns in Razor Pages,
/// including manual ViewData-based breadcrumbs and automatic route-derived breadcrumb generation.
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

// Map Razor Pages used by the navbar and dropdown menu links.
app.MapRazorPages();

app.Run();
