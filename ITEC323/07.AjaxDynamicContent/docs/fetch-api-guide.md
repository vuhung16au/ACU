# JavaScript Fetch API Guide

## What is Fetch API?

Modern browser API for making HTTP requests. Replaces older XMLHttpRequest (XHR).

**Use for:** Asynchronous data fetching without page reload

## Basic Syntax

### GET Request

```javascript
fetch('/api/data')
    .then(response => response.json())
    .then(data => {
        console.log(data);
        document.getElementById('result').textContent = data.message;
    })
    .catch(error => {
        console.error('Error:', error);
    });
```

### Async/Await (Recommended)

```javascript
async function fetchData() {
    try {
        const response = await fetch('/api/data');
        const data = await response.json();
        document.getElementById('result').textContent = data.message;
    } catch (error) {
        console.error('Error:', error);
    }
}
```

## HTTP Methods

### GET (Read Data)

```javascript
const response = await fetch('/api/products');
const products = await response.json();
```

### POST (Create Data)

```javascript
const response = await fetch('/api/products', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify({
        name: 'Widget',
        price: 19.99
    })
});
const newProduct = await response.json();
```

### PUT (Update Data)

```javascript
await fetch('/api/products/5', {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ name: 'Updated Widget', price: 24.99 })
});
```

### DELETE (Remove Data)

```javascript
await fetch('/api/products/5', {
    method: 'DELETE'
});
```

## Checking Response Status

```javascript
const response = await fetch('/api/data');

if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`);
}

const data = await response.json();
```

## Common Response Methods

| Method | Description |
|--------|-------------|
| `.json()` | Parse JSON response |
| `.text()` | Get plain text |
| `.blob()` | Get binary data (images, files) |
| `.ok` | Boolean: status 200-299 |
| `.status` | HTTP status code (200, 404, etc.) |

## Complete Example with Loading State

```javascript
async function loadProducts() {
    const loading = document.getElementById('loading');
    const error = document.getElementById('error');
    const productList = document.getElementById('products');
    
    // Show loading
    loading.style.display = 'block';
    error.style.display = 'none';
    
    try {
        const response = await fetch('/api/products');
        
        if (!response.ok) {
            throw new Error('Failed to load products');
        }
        
        const products = await response.json();
        
        // Display products
        productList.innerHTML = products.map(p => 
            `<div class="product">${p.name} - $${p.price}</div>`
        ).join('');
        
    } catch (err) {
        error.textContent = err.message;
        error.style.display = 'block';
    } finally {
        loading.style.display = 'none';
    }
}
```

## Razor Pages API Endpoint

```csharp
// Pages/api/Products.cshtml.cs
public class ProductsModel : PageModel
{
    public IActionResult OnGet()
    {
        var products = new[]
        {
            new { Id = 1, Name = "Widget", Price = 19.99 },
            new { Id = 2, Name = "Gadget", Price = 29.99 }
        };
        
        return new JsonResult(products);
    }
    
    public IActionResult OnPost([FromBody] Product product)
    {
        // Save product (in-memory or database)
        return new JsonResult(new { id = 3, message = "Created" });
    }
}
```

## Error Handling Patterns

### Network Errors

```javascript
try {
    const response = await fetch('/api/data');
    const data = await response.json();
} catch (error) {
    if (error.message.includes('Failed to fetch')) {
        alert('Network error - check your connection');
    } else {
        alert('Unexpected error: ' + error.message);
    }
}
```

### HTTP Errors

```javascript
const response = await fetch('/api/data');

switch (response.status) {
    case 200:
        const data = await response.json();
        break;
    case 404:
        alert('Resource not found');
        break;
    case 500:
        alert('Server error');
        break;
    default:
        alert('Request failed');
}
```

## Best Practices

✅ **Do:**
- Always use async/await for readability
- Check `response.ok` before parsing
- Handle errors with try-catch
- Show loading states during requests
- Use proper HTTP methods (GET/POST/PUT/DELETE)

❌ **Don't:**
- Forget error handling
- Block UI without loading indicators
- Use synchronous XHR (deprecated)
- Ignore response status codes

## Axios Alternative

### Installation

```html
<!-- CDN -->
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
```

### Basic Axios Syntax

```javascript
// GET request
const response = await axios.get('/api/data');
const data = response.data;  // Automatic JSON parsing!
```

### Axios Examples

```javascript
// GET
const { data } = await axios.get('/api/products');
console.log(data);  // Already parsed JSON

// POST
const newProduct = await axios.post('/api/products', {
    name: 'Widget',
    price: 19.99
});
console.log(newProduct.data);

// PUT
await axios.put('/api/products/5', {
    name: 'Updated Widget',
    price: 24.99
});

// DELETE
await axios.delete('/api/products/5');

// Error handling (automatic)
try {
    const { data } = await axios.get('/api/data');
} catch (error) {
    console.error(error.response.status);  // 404, 500, etc.
    console.error(error.response.data);    // Error details
}
```

### Complete Axios Example

```javascript
async function loadProducts() {
    const loading = document.getElementById('loading');
    const productList = document.getElementById('products');
    
    loading.style.display = 'block';
    
    try {
        // Automatic JSON parsing, automatic error on non-200
        const { data } = await axios.get('/api/products');
        
        productList.innerHTML = data.map(p => 
            `<div>${p.name} - $${p.price}</div>`
        ).join('');
        
    } catch (error) {
        if (error.response) {
            // Server responded with error status
            alert(`Error ${error.response.status}: ${error.response.data}`);
        } else if (error.request) {
            // Request made but no response
            alert('Network error');
        } else {
            alert('Error: ' + error.message);
        }
    } finally {
        loading.style.display = 'none';
    }
}
```

## Fetch vs Axios

| Feature | Fetch API | Axios |
|---------|-----------|-------|
| **Built-in** | ✅ Yes (native) | ❌ Needs CDN/install |
| **JSON parsing** | Manual (`.json()`) | ✅ Automatic |
| **Error handling** | Manual check | ✅ Auto throw on errors |
| **Syntax** | More verbose | Cleaner |
| **Error details** | Limited | `error.response.*` |
| **Browser support** | Modern only | Wider support |

**Fetch Example:**
```javascript
const response = await fetch('/api/data');
if (!response.ok) throw new Error('Failed');
const data = await response.json();
```

**Axios Example:**
```javascript
const { data } = await axios.get('/api/data');
// Automatically throws on error, automatically parses JSON
```

**Recommendation:** Start with Fetch (no dependencies), use Axios for simpler syntax and better error handling.

## Quick Reference

**Fetch API:**
```javascript
// GET
const response = await fetch('/api/data');
if (!response.ok) throw new Error('Failed');
const data = await response.json();

// POST
await fetch('/api/data', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ name: 'John' })
});
```

**Axios:**
```javascript
// GET
const { data } = await axios.get('/api/data');

// POST
await axios.post('/api/data', { name: 'John' });
```
