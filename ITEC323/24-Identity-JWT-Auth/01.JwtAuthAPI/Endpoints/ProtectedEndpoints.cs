using System.Security.Claims;

namespace _01.JwtAuthAPI.Endpoints;

public static class ProtectedEndpoints
{
    public static void MapProtectedEndpoints(this IEndpointRouteBuilder app)
    {
        // RequireAuthorization enforces JWT authentication for this entire group
        var group = app.MapGroup("/api/user").RequireAuthorization();

        group.MapGet("/profile", (ClaimsPrincipal user) =>
        {
            // Extract claims populated by JWT
            var email = user.FindFirst(ClaimTypes.Email)?.Value;
            var id = user.FindFirst(ClaimTypes.NameIdentifier)?.Value;
            
            return Results.Ok(new 
            { 
                Message = "This is a protected endpoint.",
                UserId = id,
                Email = email
            });
        });
    }
}
