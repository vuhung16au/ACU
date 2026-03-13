var builder = WebApplication.CreateBuilder(args);

builder.Services.AddCors(options =>
{
	options.AddPolicy("VueClient", policy =>
	{
		policy.WithOrigins("http://localhost:5175")
			.AllowAnyHeader()
			.AllowAnyMethod();
	});
});

var app = builder.Build();

app.UseHttpsRedirection();
app.UseDefaultFiles();
app.UseStaticFiles();

app.UseCors("VueClient");

var tips = new[]
{
	"Vue 3 uses the Composition API with ref and reactive.",
	"Single-file components keep template, script, and style together.",
	"v-model creates two-way binding between inputs and reactive state.",
	"computed() derives values that update automatically when dependencies change.",
	"onMounted() is the right place to fetch data from an API."
};

var lessons = new List<LessonCard>
{
	new(1, "Template Syntax", "Bind data to HTML with {{ }} and directives.", "Beginner"),
	new(2, "ref & reactive", "Track reactive state with ref for primitives and reactive for objects.", "Beginner"),
	new(3, "v-model & Forms", "Two-way bind inputs using v-model.", "Beginner"),
	new(4, "computed & watch", "Derive values and react to state changes automatically.", "Intermediate")
};

app.MapGet("/", () => Results.Ok(new
{
	module = "08.VueBasics",
	message = "Vue + .NET backend is running. Start ClientApp with npm run dev."
}));

app.MapGet("/api/tips", () => Results.Ok(tips));

app.MapGet("/api/lessons", () => Results.Ok(lessons));

app.MapPost("/api/lessons", (CreateLessonRequest request) =>
{
	if (string.IsNullOrWhiteSpace(request.Title) || string.IsNullOrWhiteSpace(request.Summary))
	{
		return Results.BadRequest(new { error = "Title and Summary are required." });
	}

	var nextId = lessons.Count == 0 ? 1 : lessons.Max(l => l.Id) + 1;
	var lesson = new LessonCard(nextId, request.Title.Trim(), request.Summary.Trim(), request.Level.Trim());
	lessons.Add(lesson);
	return Results.Created($"/api/lessons/{lesson.Id}", lesson);
});

app.Run();

internal sealed record LessonCard(int Id, string Title, string Summary, string Level);

internal sealed record CreateLessonRequest(string Title, string Summary, string Level);
