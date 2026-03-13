using VanillaJsBasics.Services;

var builder = WebApplication.CreateBuilder(args);

// Register Razor Pages and a simple service used by the classroom landing page.
builder.Services.AddRazorPages();
builder.Services.AddSingleton<VanillaTipService>();

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

app.Run();
