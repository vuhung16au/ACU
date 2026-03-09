/// <summary>
/// TailwindTheme - Demonstrates Tailwind CSS Integration with ASP.NET Core Razor Pages.
///
/// This project teaches:
/// - Tailwind CSS integration via CDN script
/// - Utility-first styling approach
/// - Responsive layouts using breakpoint utilities
/// - Reusable layout structure with Razor Pages
/// </summary>
var builder = WebApplication.CreateBuilder(args);

// Add Razor Pages services.
builder.Services.AddRazorPages();

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
app.MapRazorPages();

app.Run();
