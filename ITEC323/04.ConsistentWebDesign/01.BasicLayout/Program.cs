/// <summary>
/// BasicLayout - Demonstrates ASP.NET Core Razor Pages Layout System
/// 
/// This project teaches the fundamentals of the layout system:
/// - _Layout.cshtml: Master template for all pages
/// - _ViewStart.cshtml: Specifies which layout to use
/// - _ViewImports.cshtml: Imports namespaces and tag helpers
/// - @RenderBody(): Injects page content into layout
/// - @RenderSection(): Optional page-specific content sections
/// </summary>
/// 
var builder = WebApplication.CreateBuilder(args);

// Add Razor Pages services
builder.Services.AddRazorPages();

var app = builder.Build();

// Configure the HTTP request pipeline
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error");
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseStaticFiles();
app.UseRouting();
app.UseAuthorization();

// Map Razor Pages
app.MapRazorPages();

app.Run();
