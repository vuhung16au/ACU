var builder = WebApplication.CreateBuilder(args);

builder.Services.AddCors(options =>
{
	options.AddPolicy("ReactClient", policy =>
	{
		policy.WithOrigins("http://localhost:5173")
			.AllowAnyHeader()
			.AllowAnyMethod();
	});
});

var app = builder.Build();

app.UseHttpsRedirection();
app.UseDefaultFiles();
app.UseStaticFiles();

app.UseCors("ReactClient");

var tips = new[]
{
	"Components are reusable UI building blocks.",
	"useState stores local, reactive component state.",
	"useEffect runs side effects such as API calls.",
	"Keep components focused and small while learning."
};

var lessons = new List<LessonCard>
{
	new(1, "JSX Basics", "Build UI with declarative component syntax.", "Beginner"),
	new(2, "useState Hook", "Update UI instantly when state changes.", "Beginner"),
	new(3, "useEffect Hook", "Fetch API data after first render.", "Beginner"),
	new(4, "Props & Composition", "Pass data into child components.", "Intermediate")
};

app.MapGet("/", () => Results.Ok(new
{
	module = "07.ReactBasics",
	message = "React + .NET backend is running. Start ClientApp with npm run dev."
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
