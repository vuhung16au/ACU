# CORS Guide

## What is CORS?

**Cross-Origin Resource Sharing** - Security mechanism that controls whether a web page can make requests to a different domain.

**Origin = Protocol + Domain + Port**

Examples:
- `https://example.com:443` 
- `http://localhost:5000`

## Why CORS Exists

**Security:** Prevents malicious websites from accessing your API without permission.

**Example attack without CORS:**
1. User visits `evil.com`
2. `evil.com` makes request to `yourbank.com/api/transfer`
3. Browser sends user's cookies (logged in)
4. Money stolen! 💰

**CORS blocks this:** Browser prevents cross-origin requests unless explicitly allowed.

## Same-Origin vs Cross-Origin

### Same-Origin (No CORS needed)

```
Frontend: https://example.com/page.html
API:      https://example.com/api/data
✅ Same origin - works without CORS
```

### Cross-Origin (CORS needed)

```
Frontend: http://localhost:3000
API:      http://localhost:5000
❌ Different port = different origin
```

```
Frontend: https://example.com
API:      https://api.example.com
❌ Different subdomain = different origin
```

## When You Need CORS

**Development scenarios:**
- Frontend on `localhost:3000`, API on `localhost:5000`
- Testing API from different URL
- Using Swagger UI from different port

**Production scenarios:**
- Frontend on `app.example.com`, API on `api.example.com`
- Mobile app calling your API
- Third-party apps accessing your API

## Enabling CORS in ASP.NET Core

### Program.cs

```csharp
var builder = WebApplication.CreateBuilder(args);

// Add CORS service
builder.Services.AddCors(options =>
{
    options.AddPolicy("AllowAll", policy =>
    {
        policy.AllowAnyOrigin()
              .AllowAnyMethod()
              .AllowAnyHeader();
    });
});

builder.Services.AddControllers();

var app = builder.Build();

// Use CORS middleware (must be before MapControllers)
app.UseCors("AllowAll");

app.MapControllers();
app.Run();
```

## CORS Policies

### 1. Allow All (Development Only)

```csharp
builder.Services.AddCors(options =>
{
    options.AddPolicy("DevPolicy", policy =>
    {
        policy.AllowAnyOrigin()
              .AllowAnyMethod()
              .AllowAnyHeader();
    });
});
```

⚠️ **Never use in production!** Security risk.

### 2. Specific Origins (Production)

```csharp
builder.Services.AddCors(options =>
{
    options.AddPolicy("ProductionPolicy", policy =>
    {
        policy.WithOrigins(
                "https://example.com",
                "https://app.example.com"
              )
              .AllowAnyMethod()
              .AllowAnyHeader();
    });
});
```

### 3. Localhost Development

```csharp
builder.Services.AddCors(options =>
{
    options.AddPolicy("LocalDev", policy =>
    {
        policy.WithOrigins(
                "http://localhost:3000",
                "http://localhost:5173"  // Vite default
              )
              .AllowAnyMethod()
              .AllowAnyHeader()
              .AllowCredentials();  // Allow cookies
    });
});
```

### 4. Environment-Based

```csharp
var builder = WebApplication.CreateBuilder(args);

builder.Services.AddCors(options =>
{
    options.AddPolicy("AppPolicy", policy =>
    {
        if (builder.Environment.IsDevelopment())
        {
            policy.AllowAnyOrigin()
                  .AllowAnyMethod()
                  .AllowAnyHeader();
        }
        else
        {
            policy.WithOrigins("https://example.com")
                  .AllowAnyMethod()
                  .AllowAnyHeader();
        }
    });
});
```

## Applying CORS

### Global (All Endpoints)

```csharp
app.UseCors("MyPolicy");  // Before MapControllers
app.MapControllers();
```

### Per Controller

```csharp
[ApiController]
[Route("api/[controller]")]
[EnableCors("MyPolicy")]
public class ProductsController : ControllerBase
{
    // ...
}
```

### Per Action

```csharp
[HttpGet]
[EnableCors("MyPolicy")]
public IActionResult Get()
{
    // ...
}
```

## CORS Preflight Request

For complex requests (POST/PUT/DELETE with custom headers), browser sends **OPTIONS** request first.

**Sequence:**
1. Browser sends OPTIONS request (preflight)
2. Server responds with allowed origins/methods/headers
3. Browser sends actual request (if allowed)

**You don't handle this manually** - ASP.NET Core does it automatically when CORS is enabled.

## Testing CORS

### JavaScript

```javascript
// From http://localhost:3000 to http://localhost:5000
fetch('http://localhost:5000/api/products')
    .then(response => response.json())
    .then(data => console.log(data))
    .catch(error => {
        console.error('CORS error:', error);
    });
```

### Browser Console

**CORS Error:**
```
Access to fetch at 'http://localhost:5000/api/products' 
from origin 'http://localhost:3000' has been blocked by 
CORS policy: No 'Access-Control-Allow-Origin' header is 
present on the requested resource.
```

**Success:**
```
Access-Control-Allow-Origin: http://localhost:3000
```

## Allowing Credentials (Cookies)

```csharp
builder.Services.AddCors(options =>
{
    options.AddPolicy("WithCredentials", policy =>
    {
        policy.WithOrigins("https://example.com")
              .AllowAnyMethod()
              .AllowAnyHeader()
              .AllowCredentials();  // Allow cookies/auth
    });
});
```

**JavaScript:**

```javascript
fetch('https://api.example.com/data', {
    credentials: 'include'  // Send cookies
})
```

⚠️ **Cannot use `AllowAnyOrigin()` with `AllowCredentials()`** - must specify exact origins.

## Common CORS Errors

### Error: "No 'Access-Control-Allow-Origin' header"

**Solution:** Enable CORS in `Program.cs`

### Error: "Origin not allowed by Access-Control-Allow-Origin"

**Solution:** Add your frontend URL to `WithOrigins()`

### Error: "Credentials mode 'include' not supported"

**Solution:** Add `.AllowCredentials()` and use `WithOrigins()` (not `AllowAnyOrigin()`)

### Error: Preflight request failed

**Solution:** Ensure `app.UseCors()` is **before** `app.MapControllers()`

## Best Practices

✅ **Do:**
- Use `AllowAnyOrigin()` only in development
- Specify exact origins in production
- Put `UseCors()` before `MapControllers()`
- Use environment-based configuration
- Test CORS from different domains

❌ **Don't:**
- Use `AllowAnyOrigin()` in production
- Forget to add CORS to `Program.cs`
- Put `UseCors()` after `MapControllers()`
- Allow credentials with `AllowAnyOrigin()`

## Quick Reference

```csharp
// Program.cs
var builder = WebApplication.CreateBuilder(args);

builder.Services.AddCors(options =>
{
    options.AddPolicy("MyPolicy", policy =>
    {
        policy.WithOrigins("http://localhost:3000")
              .AllowAnyMethod()
              .AllowAnyHeader();
    });
});

builder.Services.AddControllers();

var app = builder.Build();

app.UseCors("MyPolicy");  // Before MapControllers!
app.MapControllers();
app.Run();
```

## Development vs Production

```csharp
var builder = WebApplication.CreateBuilder(args);

var allowedOrigins = builder.Environment.IsDevelopment()
    ? new[] { "http://localhost:3000", "http://localhost:5173" }
    : new[] { "https://example.com", "https://app.example.com" };

builder.Services.AddCors(options =>
{
    options.AddDefaultPolicy(policy =>
    {
        policy.WithOrigins(allowedOrigins)
              .AllowAnyMethod()
              .AllowAnyHeader();
    });
});
```
