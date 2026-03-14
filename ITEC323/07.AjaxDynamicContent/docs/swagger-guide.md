# Swagger Guide

## What is Swagger?

**Swagger (OpenAPI)** - Automatic API documentation and testing tool for Web APIs.

**Benefits:**
- Interactive API documentation
- Test endpoints in browser
- Auto-generates from your code
- Standardized API specification

## Installation

```bash
dotnet add package Swashbuckle.AspNetCore
```

## Setup in Program.cs

```csharp
var builder = WebApplication.CreateBuilder(args);

builder.Services.AddControllers();

// Add Swagger
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// Enable Swagger in development
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.MapControllers();
app.Run();
```

## Accessing Swagger UI

Run your app:
```bash
dotnet run
```

Browse to:
```
https://localhost:5001/swagger
```

## Basic API Documentation

Swagger automatically documents your controllers:

```csharp
[ApiController]
[Route("api/[controller]")]
public class ProductsController : ControllerBase
{
    // Appears in Swagger automatically
    [HttpGet]
    public IActionResult GetAll()
    {
        var products = new[] { new { Id = 1, Name = "Widget" } };
        return Ok(products);
    }
    
    [HttpGet("{id}")]
    public IActionResult GetById(int id)
    {
        return Ok(new { Id = id, Name = "Widget" });
    }
    
    [HttpPost]
    public IActionResult Create([FromBody] CreateProductDto dto)
    {
        return CreatedAtAction(nameof(GetById), new { id = 1 }, dto);
    }
}
```

## Adding XML Documentation

### Enable XML Comments

**Edit .csproj:**

```xml
<Project Sdk="Microsoft.NET.Sdk.Web">
  <PropertyGroup>
    <TargetFramework>net10.0</TargetFramework>
    <GenerateDocumentationFile>true</GenerateDocumentationFile>
    <NoWarn>$(NoWarn);1591</NoWarn>
  </PropertyGroup>
</Project>
```

### Configure Swagger to Use XML

```csharp
builder.Services.AddSwaggerGen(options =>
{
    var xmlFile = $"{Assembly.GetExecutingAssembly().GetName().Name}.xml";
    var xmlPath = Path.Combine(AppContext.BaseDirectory, xmlFile);
    options.IncludeXmlComments(xmlPath);
});
```

### Add XML Comments to Controller

```csharp
/// <summary>
/// Products API
/// </summary>
[ApiController]
[Route("api/[controller]")]
public class ProductsController : ControllerBase
{
    /// <summary>
    /// Get all products
    /// </summary>
    /// <returns>List of products</returns>
    /// <response code="200">Returns the product list</response>
    [HttpGet]
    [ProducesResponseType(StatusCodes.Status200OK)]
    public IActionResult GetAll()
    {
        var products = new[] { new { Id = 1, Name = "Widget", Price = 19.99 } };
        return Ok(products);
    }
    
    /// <summary>
    /// Get a specific product by ID
    /// </summary>
    /// <param name="id">The product ID</param>
    /// <returns>The product</returns>
    /// <response code="200">Returns the product</response>
    /// <response code="404">Product not found</response>
    [HttpGet("{id}")]
    [ProducesResponseType(StatusCodes.Status200OK)]
    [ProducesResponseType(StatusCodes.Status404NotFound)]
    public IActionResult GetById(int id)
    {
        var product = new { Id = id, Name = "Widget", Price = 19.99 };
        return Ok(product);
    }
    
    /// <summary>
    /// Create a new product
    /// </summary>
    /// <param name="dto">Product details</param>
    /// <returns>The created product</returns>
    /// <response code="201">Product created successfully</response>
    /// <response code="400">Invalid input</response>
    [HttpPost]
    [ProducesResponseType(StatusCodes.Status201Created)]
    [ProducesResponseType(StatusCodes.Status400BadRequest)]
    public IActionResult Create([FromBody] CreateProductDto dto)
    {
        var product = new { Id = 1, dto.Name, dto.Price };
        return CreatedAtAction(nameof(GetById), new { id = product.Id }, product);
    }
}

/// <summary>
/// Product creation request
/// </summary>
public class CreateProductDto
{
    /// <summary>
    /// Product name
    /// </summary>
    /// <example>Widget</example>
    [Required]
    public string Name { get; set; }
    
    /// <summary>
    /// Product price in dollars
    /// </summary>
    /// <example>19.99</example>
    [Range(0.01, 10000)]
    public decimal Price { get; set; }
}
```

## Swagger Configuration

### Add API Information

```csharp
builder.Services.AddSwaggerGen(options =>
{
    options.SwaggerDoc("v1", new OpenApiInfo
    {
        Version = "v1",
        Title = "Products API",
        Description = "API for managing products",
        Contact = new OpenApiContact
        {
            Name = "Support",
            Email = "support@example.com"
        }
    });
    
    // XML comments
    var xmlFile = $"{Assembly.GetExecutingAssembly().GetName().Name}.xml";
    var xmlPath = Path.Combine(AppContext.BaseDirectory, xmlFile);
    options.IncludeXmlComments(xmlPath);
});
```

### Multiple API Versions

```csharp
builder.Services.AddSwaggerGen(options =>
{
    options.SwaggerDoc("v1", new OpenApiInfo { Title = "API", Version = "v1" });
    options.SwaggerDoc("v2", new OpenApiInfo { Title = "API", Version = "v2" });
});

app.UseSwaggerUI(options =>
{
    options.SwaggerEndpoint("/swagger/v1/swagger.json", "API V1");
    options.SwaggerEndpoint("/swagger/v2/swagger.json", "API V2");
});
```

## Testing APIs in Swagger UI

### GET Request

1. Click endpoint (e.g., `GET /api/products`)
2. Click "Try it out"
3. Enter parameters (if any)
4. Click "Execute"
5. View response

### POST Request

1. Click `POST /api/products`
2. Click "Try it out"
3. Edit JSON body:
   ```json
   {
     "name": "New Widget",
     "price": 29.99
   }
   ```
4. Click "Execute"
5. View response (201 Created)

## Response Examples

```csharp
/// <summary>
/// Get product by ID
/// </summary>
/// <response code="200">Success</response>
/// <response code="404">Not found</response>
[HttpGet("{id}")]
[ProducesResponseType(typeof(ProductDto), StatusCodes.Status200OK)]
[ProducesResponseType(StatusCodes.Status404NotFound)]
public IActionResult GetById(int id)
{
    // Implementation
}
```

## Grouping Endpoints

```csharp
/// <summary>
/// Products management
/// </summary>
[ApiController]
[Route("api/[controller]")]
[Tags("Products")]
public class ProductsController : ControllerBase { }

/// <summary>
/// Orders management
/// </summary>
[ApiController]
[Route("api/[controller]")]
[Tags("Orders")]
public class OrdersController : ControllerBase { }
```

## Hide Endpoints from Swagger

```csharp
[HttpGet("internal")]
[ApiExplorerSettings(IgnoreApi = true)]
public IActionResult InternalEndpoint()
{
    // Not shown in Swagger
}
```

## Production Configuration

### Disable Swagger in Production

```csharp
// Only enable in development
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}
```

### Or require authentication

```csharp
app.UseSwagger();
app.UseSwaggerUI(options =>
{
    options.RoutePrefix = "api-docs";  // /api-docs instead of /swagger
});

// Add authentication check before Swagger
app.UseAuthentication();
app.UseAuthorization();
```

## Swagger JSON Specification

Access raw OpenAPI/Swagger JSON:

```
https://localhost:5001/swagger/v1/swagger.json
```

Use for:
- Generating client libraries
- Importing into API testing tools (Postman, Insomnia)
- External documentation

## Best Practices

✅ **Do:**
- Add XML comments for all public endpoints
- Use `[ProducesResponseType]` for status codes
- Provide example values in DTOs
- Group endpoints with tags
- Disable in production (or protect with auth)

❌ **Don't:**
- Expose internal endpoints
- Leave endpoints undocumented
- Forget to enable XML documentation in .csproj
- Allow public access in production

## Quick Reference

```csharp
// Program.cs
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen(options =>
{
    var xmlFile = $"{Assembly.GetExecutingAssembly().GetName().Name}.xml";
    var xmlPath = Path.Combine(AppContext.BaseDirectory, xmlFile);
    options.IncludeXmlComments(xmlPath);
});

if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

// Controller
/// <summary>
/// Get all items
/// </summary>
/// <returns>List of items</returns>
/// <response code="200">Success</response>
[HttpGet]
[ProducesResponseType(StatusCodes.Status200OK)]
public IActionResult GetAll() { }
```

## Swagger UI Customization

```csharp
app.UseSwaggerUI(options =>
{
    options.SwaggerEndpoint("/swagger/v1/swagger.json", "My API V1");
    options.RoutePrefix = "api-docs";  // Change URL
    options.DocumentTitle = "My API Documentation";
    options.DefaultModelsExpandDepth(-1);  // Hide schemas section
    options.DocExpansion(DocExpansion.None);  // Collapse all by default
});
```

## Access Swagger at Different URL

```csharp
app.UseSwaggerUI(options =>
{
    options.RoutePrefix = "docs";  // Access at /docs instead of /swagger
});
```
