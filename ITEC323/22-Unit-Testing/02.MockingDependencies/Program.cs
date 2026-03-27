using MockingDependenciesDemo.Services;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddRazorPages();
builder.Services.AddSingleton<IEmailRegistry>(_ => new InMemoryEmailRegistry(
[
    "ada@example.com",
    "grace@example.com"
]));
builder.Services.AddSingleton<FormSubmissionValidator>();
builder.Services.AddSingleton<FormSubmissionService>();

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
