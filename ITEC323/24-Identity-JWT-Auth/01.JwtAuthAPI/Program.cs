using _01.JwtAuthAPI.Data;
using _01.JwtAuthAPI.Endpoints;
using _01.JwtAuthAPI.Models;
using _01.JwtAuthAPI.Services;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using System.Text;

var builder = WebApplication.CreateBuilder(args);

// Configure Entity Framework with SQLite
builder.Services.AddDbContext<AppDbContext>(options =>
    options.UseSqlite(builder.Configuration.GetConnectionString("DefaultConnection")));

// Configure ASP.NET Core Identity
builder.Services.AddIdentityCore<IdentityUser>(options =>
{
    // Simplified password rules for educational purposes
    options.Password.RequireDigit = false;
    options.Password.RequiredLength = 6;
    options.Password.RequireNonAlphanumeric = false;
    options.Password.RequireUppercase = false;
    options.Password.RequireLowercase = false;
})
.AddEntityFrameworkStores<AppDbContext>();

// Bind JwtSettings from configuration (appsettings.json)
builder.Services.Configure<JwtSettings>(builder.Configuration.GetSection(JwtSettings.SectionName));

// Register our custom token generation service
builder.Services.AddScoped<JwtTokenService>();

// Configure JWT Bearer Authentication
var jwtSettings = builder.Configuration.GetSection(JwtSettings.SectionName).Get<JwtSettings>();
var key = Encoding.UTF8.GetBytes(jwtSettings!.Secret);

builder.Services.AddAuthentication(options =>
{
    options.DefaultAuthenticateScheme = JwtBearerDefaults.AuthenticationScheme;
    options.DefaultChallengeScheme = JwtBearerDefaults.AuthenticationScheme;
})
.AddJwtBearer(options =>
{
    options.TokenValidationParameters = new TokenValidationParameters
    {
        ValidateIssuer = true,
        ValidateAudience = true,
        ValidateLifetime = true,
        ValidateIssuerSigningKey = true,
        ValidIssuer = jwtSettings.Issuer,
        ValidAudience = jwtSettings.Audience,
        IssuerSigningKey = new SymmetricSecurityKey(key)
    };
});

builder.Services.AddAuthorization();

var app = builder.Build();

// Ensure the database is created at startup (for educational simplicity)
using (var scope = app.Services.CreateScope())
{
    var db = scope.ServiceProvider.GetRequiredService<AppDbContext>();
    db.Database.EnsureCreated();
}



// Serve static files (HTML UI)
app.UseDefaultFiles();
app.UseStaticFiles();

// Essential auth middleware
app.UseAuthentication();
app.UseAuthorization();

// Map route endpoints
app.MapAuthEndpoints();
app.MapProtectedEndpoints();

app.Run();

// Make the implicitly generated Program class public so tests can reference it
public partial class Program { }
