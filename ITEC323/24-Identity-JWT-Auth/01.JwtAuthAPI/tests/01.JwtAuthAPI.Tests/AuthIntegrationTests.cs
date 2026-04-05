using FluentAssertions;
using Microsoft.AspNetCore.Mvc.Testing;
using System.Net;
using System.Net.Http.Json;
using System.Net.Http.Headers;
using System.Text.Json;
using Xunit;

namespace _01.JwtAuthAPI.Tests;

public class AuthIntegrationTests : IClassFixture<CustomWebApplicationFactory>
{
    private readonly HttpClient _client;
    
    public AuthIntegrationTests(CustomWebApplicationFactory factory)
    {
        _client = factory.CreateClient();
    }

    [Fact]
    public async Task Register_ValidUser_ReturnsOk()
    {
        var response = await _client.PostAsJsonAsync("/api/auth/register", new { Email = "test@example.com", Password = "password123" });
        
        response.StatusCode.Should().Be(HttpStatusCode.OK);
    }

    [Fact]
    public async Task Login_ValidUser_ReturnsToken()
    {
        // Arrange: Make sure user exists
        await _client.PostAsJsonAsync("/api/auth/register", new { Email = "login@example.com", Password = "password123" });

        // Act
        var response = await _client.PostAsJsonAsync("/api/auth/login", new { Email = "login@example.com", Password = "password123" });

        // Assert
        response.StatusCode.Should().Be(HttpStatusCode.OK);
        var content = await response.Content.ReadAsStringAsync();
        content.Should().Contain("token");
    }

    [Fact]
    public async Task ProtectedEndpoint_WithoutToken_ReturnsUnauthorized()
    {
        var response = await _client.GetAsync("/api/user/profile");
        
        response.StatusCode.Should().Be(HttpStatusCode.Unauthorized);
    }

    [Fact]
    public async Task ProtectedEndpoint_WithValidToken_ReturnsOk()
    {
        // Arrange
        await _client.PostAsJsonAsync("/api/auth/register", new { Email = "protected@example.com", Password = "password123" });
        var loginResponse = await _client.PostAsJsonAsync("/api/auth/login", new { Email = "protected@example.com", Password = "password123" });
        
        var loginContent = await loginResponse.Content.ReadAsStringAsync();
        using var jsonDoc = JsonDocument.Parse(loginContent);
        var token = jsonDoc.RootElement.GetProperty("token").GetString();

        // Act
        var request = new HttpRequestMessage(HttpMethod.Get, "/api/user/profile");
        request.Headers.Authorization = new AuthenticationHeaderValue("Bearer", token);
        var response = await _client.SendAsync(request);

        // Assert
        response.StatusCode.Should().Be(HttpStatusCode.OK);
    }
}
