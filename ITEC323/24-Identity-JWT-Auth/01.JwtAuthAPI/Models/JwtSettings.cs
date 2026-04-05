namespace _01.JwtAuthAPI.Models;

/// <summary>
/// Configuration model matching the "JwtSettings" section of appsettings.json.
/// </summary>
public class JwtSettings
{
    public const string SectionName = "JwtSettings";
    public string Secret { get; set; } = string.Empty;
    public string Issuer { get; set; } = string.Empty;
    public string Audience { get; set; } = string.Empty;
    public int ExpiryMinutes { get; set; } = 60;
}
