using BasicFetchAPI.Services;

var builder = WebApplication.CreateBuilder(args);

// Register Razor Pages for the user interface and controllers for the JSON API.
builder.Services.AddRazorPages();
builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();

// Development CORS keeps the example ready for future cross-origin demos.
builder.Services.AddCors(options =>
{
    options.AddPolicy(
        "DevelopmentClient",
        policy =>
        {
            policy.AllowAnyOrigin()
                  .AllowAnyHeader()
                  .AllowAnyMethod();
        });
});

builder.Services.AddSingleton<StudyTaskService>();

var app = builder.Build();

if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error");
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseStaticFiles();
app.UseRouting();

if (app.Environment.IsDevelopment())
{
    app.UseCors("DevelopmentClient");
}

app.UseAuthorization();

app.MapControllers();
app.MapRazorPages();

app.Run();
