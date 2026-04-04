using GeminiTextAndRagRazorDemo.Services;

var builder = WebApplication.CreateBuilder(args);

var environmentFileLoader = new EnvironmentFileLoader();
environmentFileLoader.Load(Path.Combine(builder.Environment.ContentRootPath, ".env.local"));

builder.Services.Configure<GeminiOptions>(builder.Configuration.GetSection(GeminiOptions.SectionName));
builder.Services.Configure<RagOptions>(builder.Configuration.GetSection(RagOptions.SectionName));

builder.Services.AddRazorPages();
builder.Services.AddDistributedMemoryCache();
builder.Services.AddSession(options =>
{
    options.Cookie.Name = ".GeminiTextAndRagRazor.Session";
    options.Cookie.HttpOnly = true;
    options.Cookie.IsEssential = true;
    options.IdleTimeout = TimeSpan.FromMinutes(30);
});

builder.Services.AddSingleton(environmentFileLoader);
builder.Services.AddSingleton<KnowledgeBaseService>();
builder.Services.AddSingleton<KeywordSearchService>();
builder.Services.AddSingleton<ITextGenerationService, GeminiTextService>();
builder.Services.AddSingleton<RagAnswerService>();
builder.Services.AddSingleton<ChatInputValidator>();
builder.Services.AddSingleton<ChatHistorySessionService>();

var app = builder.Build();

if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error");
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseStaticFiles();
app.UseRouting();
app.UseSession();
app.UseAuthorization();

app.MapRazorPages();

app.Run();
