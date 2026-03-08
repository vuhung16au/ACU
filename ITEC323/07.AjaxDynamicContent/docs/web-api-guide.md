# Web API Guide

## What is a Web API?

Server endpoint that returns **JSON data** (not HTML) for consumption by JavaScript, mobile apps, or other services.

**Use for:** AJAX requests, mobile backends, microservices

## Creating Web APIs in ASP.NET Core

Two approaches:

1. **API Controllers** - Dedicated controllers in `Controllers/` folder
2. **PageHandlers** - Simple API endpoints in Razor Pages

## Approach 1: API Controllers (Recommended)

### Setup

Add controller to `Program.cs`:

```csharp
var builder = WebApplication.CreateBuilder(args);

builder.Services.AddControllers();  // Add this
builder.Services.AddRazorPages();

var app = builder.Build();

app.MapControllers();  // Add this
app.MapRazorPages();
app.Run();
```

### Create Controller

**Location:** `Controllers/ProductsController.cs`

```csharp
using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("api/[controller]")]
public class ProductsController : ControllerBase
{
    // GET: api/products
    [HttpGet]
    public IActionResult GetAll()
    {
        var products = new[]
        {
            new { Id = 1, Name = "Widget", Price = 19.99 },
            new { Id = 2, Name = "Gadget", Price = 29.99 }
        };
        
        return Ok(products);  // 200 OK
    }
    
    // GET: api/products/5
    [HttpGet("{id}")]
    public IActionResult GetById(int id)
    {
        var product = new { Id = id, Name = "Widget", Price = 19.99 };
        
        if (product == null)
            return NotFound();  // 404
        
        return Ok(product);  // 200
    }
    
    // POST: api/products
    [HttpPost]
    public IActionResult Create([FromBody] CreateProductDto dto)
    {
        if (!ModelState.IsValid)
            return BadRequest(ModelState);  // 400
        
        var product = new { Id = 3, dto.Name, dto.Price };
        
        return CreatedAtAction(
            nameof(GetById), 
            new { id = product.Id }, 
            product);  // 201 Created
    }
    
    // PUT: api/products/5
    [HttpPut("{id}")]
    public IActionResult Update(int id, [FromBody] CreateProductDto dto)
    {
        // Update logic
        return NoContent();  // 204 No Content
    }
    
    // DELETE: api/products/5
    [HttpDelete("{id}")]
    public IActionResult Delete(int id)
    {
        // Delete logic
        return NoContent();  // 204
    }
}

public class CreateProductDto
{
    public string Name { get; set; }
    public decimal Price { get; set; }
}
```

## HTTP Verbs and Status Codes

### Common HTTP Verbs

| Verb | Purpose | Example |
|------|---------|---------|
| **GET** | Read/retrieve | Get product list |
| **POST** | Create new | Create product |
| **PUT** | Update existing | Update product |
| **DELETE** | Remove | Delete product |
| **PATCH** | Partial update | Update price only |

### Common Status Codes

| Code | Meaning | When to Use |
|------|---------|-------------|
| **200 OK** | Success | GET/PUT successful |
| **201 Created** | Created | POST successful |
| **204 No Content** | Success, no body | DELETE successful |
| **400 Bad Request** | Invalid input | Validation failed |
| **404 Not Found** | Resource missing | ID doesn't exist |
| **500 Server Error** | Server problem | Unexpected error |

## Return Types

### IActionResult (Flexible)

```csharp
[HttpGet]
public IActionResult Get()
{
    return Ok(data);        // 200
    return NotFound();      // 404
    return BadRequest();    // 400
    return Conflict();      // 409
}
```

### ActionResult&lt;T&gt; (Typed)

```csharp
[HttpGet("{id}")]
public ActionResult<Product> GetById(int id)
{
    var product = _service.Get(id);
    
    if (product == null)
        return NotFound();
    
    return product;  // Implicit 200 OK
}
```

## Model Binding

### From URL Parameters

```csharp
// GET: api/products?category=electronics&minPrice=100
[HttpGet]
public IActionResult Get(string category, decimal minPrice)
{
    var products = _service.Filter(category, minPrice);
    return Ok(products);
}
```

### From Route

```csharp
// GET: api/products/5
[HttpGet("{id}")]
public IActionResult Get(int id)
{
    // id from URL
}
```

### From Body (JSON)

```csharp
// POST: api/products
[HttpPost]
public IActionResult Create([FromBody] Product product)
{
    // product parsed from JSON body
}
```

### From Header

```csharp
[HttpGet]
public IActionResult Get([FromHeader(Name = "X-Api-Key")] string apiKey)
{
    // apiKey from HTTP header
}
```

## Approach 2: PageHandlers (Simple APIs)

**Use for:** Small APIs, quick endpoints

```csharp
// Pages/api/GetProducts.cshtml.cs
public class GetProductsModel : PageModel
{
    public IActionResult OnGet()
    {
        var products = new[]
        {
            new { Id = 1, Name = "Widget" }
        };
        
        return new JsonResult(products);
    }
    
    public IActionResult OnPost()
    {
        var name = Request.Form["name"];
        // Process...
        return new JsonResult(new { success = true });
    }
}
```

**URL:** `/api/GetProducts`

## Error Handling

### Try-Catch

```csharp
[HttpGet("{id}")]
public IActionResult GetById(int id)
{
    try
    {
        var product = _service.GetById(id);
        if (product == null)
            return NotFound(new { message = "Product not found" });
        
        return Ok(product);
    }
    catch (Exception ex)
    {
        return StatusCode(500, new { message = "Internal error", error = ex.Message });
    }
}
```

### Validation

```csharp
[HttpPost]
public IActionResult Create([FromBody] CreateProductDto dto)
{
    if (!ModelState.IsValid)
    {
        return BadRequest(new 
        { 
            message = "Validation failed",
            errors = ModelState.Values
                .SelectMany(v => v.Errors)
                .Select(e => e.ErrorMessage)
        });
    }
    
    // Save product...
    return Created();
}
```

## Complete CRUD Example

```csharp
[ApiController]
[Route("api/[controller]")]
public class TodosController : ControllerBase
{
    private static List<Todo> _todos = new();
    private static int _nextId = 1;
    
    // GET: api/todos
    [HttpGet]
    public IActionResult GetAll()
    {
        return Ok(_todos);
    }
    
    // GET: api/todos/5
    [HttpGet("{id}")]
    public IActionResult GetById(int id)
    {
        var todo = _todos.FirstOrDefault(t => t.Id == id);
        if (todo == null)
            return NotFound();
        
        return Ok(todo);
    }
    
    // POST: api/todos
    [HttpPost]
    public IActionResult Create([FromBody] CreateTodoDto dto)
    {
        var todo = new Todo
        {
            Id = _nextId++,
            Title = dto.Title,
            IsComplete = false
        };
        
        _todos.Add(todo);
        return CreatedAtAction(nameof(GetById), new { id = todo.Id }, todo);
    }
    
    // PUT: api/todos/5
    [HttpPut("{id}")]
    public IActionResult Update(int id, [FromBody] UpdateTodoDto dto)
    {
        var todo = _todos.FirstOrDefault(t => t.Id == id);
        if (todo == null)
            return NotFound();
        
        todo.Title = dto.Title;
        todo.IsComplete = dto.IsComplete;
        
        return NoContent();
    }
    
    // DELETE: api/todos/5
    [HttpDelete("{id}")]
    public IActionResult Delete(int id)
    {
        var todo = _todos.FirstOrDefault(t => t.Id == id);
        if (todo == null)
            return NotFound();
        
        _todos.Remove(todo);
        return NoContent();
    }
}

public class Todo
{
    public int Id { get; set; }
    public string Title { get; set; }
    public bool IsComplete { get; set; }
}

public class CreateTodoDto
{
    [Required]
    public string Title { get; set; }
}

public class UpdateTodoDto
{
    [Required]
    public string Title { get; set; }
    public bool IsComplete { get; set; }
}
```

## Testing with JavaScript Fetch

```javascript
// GET all
const response = await fetch('/api/todos');
const todos = await response.json();

// POST create
await fetch('/api/todos', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ title: 'New Todo' })
});

// PUT update
await fetch('/api/todos/5', {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ title: 'Updated', isComplete: true })
});

// DELETE
await fetch('/api/todos/5', { method: 'DELETE' });
```

## Best Practices

✅ **Do:**
- Use API Controllers for complex APIs
- Return proper HTTP status codes
- Validate input with ModelState
- Use DTOs (Data Transfer Objects)
- Handle errors gracefully
- Use async methods for I/O

❌ **Don't:**
- Return 200 for errors
- Expose internal exception details
- Use GET for data modification
- Forget [ApiController] attribute
- Mix HTML and JSON responses

## Quick Reference

```csharp
[ApiController]
[Route("api/[controller]")]
public class ItemsController : ControllerBase
{
    [HttpGet]
    public IActionResult GetAll() => Ok(items);
    
    [HttpGet("{id}")]
    public IActionResult GetById(int id) => Ok(item);
    
    [HttpPost]
    public IActionResult Create([FromBody] ItemDto dto) 
        => CreatedAtAction(nameof(GetById), new { id }, item);
    
    [HttpPut("{id}")]
    public IActionResult Update(int id, [FromBody] ItemDto dto) 
        => NoContent();
    
    [HttpDelete("{id}")]
    public IActionResult Delete(int id) => NoContent();
}
```
