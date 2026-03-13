using HtmxPartialUpdates.Services;

var builder = WebApplication.CreateBuilder(args);

// Register Razor Pages and the in-memory student service.
builder.Services.AddRazorPages();
builder.Services.AddSingleton<StudentService>();

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
