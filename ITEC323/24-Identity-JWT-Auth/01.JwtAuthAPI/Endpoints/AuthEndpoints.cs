using _01.JwtAuthAPI.Services;
using Microsoft.AspNetCore.Identity;

namespace _01.JwtAuthAPI.Endpoints;

public static class AuthEndpoints
{
    public static void MapAuthEndpoints(this IEndpointRouteBuilder app)
    {
        var authGroup = app.MapGroup("/api/auth");

        authGroup.MapPost("/register", async (RegisterRequest req, UserManager<IdentityUser> userManager) =>
        {
            var user = new IdentityUser { UserName = req.Email, Email = req.Email };
            var result = await userManager.CreateAsync(user, req.Password);

            if (result.Succeeded)
            {
                return Results.Ok(new { Message = "User registered successfully." });
            }

            return Results.BadRequest(result.Errors);
        });

        authGroup.MapPost("/login", async (LoginRequest req, UserManager<IdentityUser> userManager, JwtTokenService jwtService) =>
        {
            var user = await userManager.FindByEmailAsync(req.Email);
            if (user == null || !await userManager.CheckPasswordAsync(user, req.Password))
            {
                return Results.Unauthorized();
            }

            var token = jwtService.GenerateToken(user);
            return Results.Ok(new { Token = token, Expiry = DateTime.UtcNow.AddMinutes(60) });
        });
    }
}

public record RegisterRequest(string Email, string Password);
public record LoginRequest(string Email, string Password);
