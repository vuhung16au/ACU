# Blazor Basics Guide

## What is Blazor?

C#-based framework for building **interactive web UIs** without JavaScript. Runs C# code in the browser or on the server.

**Use for:** Single-page applications (SPAs), real-time dashboards, interactive components

## Blazor Server vs WebAssembly

| Feature | Blazor Server | Blazor WebAssembly |
|---------|---------------|-------------------|
| **Runs where** | Server | Browser |
| **Connection** | SignalR WebSocket | None needed |
| **First load** | Fast | Slow (downloads .NET runtime) |
| **Works offline** | ❌ No | ✅ Yes |
| **Real-time** | ✅ Built-in | ❌ Requires SignalR |
| **Best for** | Dashboards, LOB apps | Offline apps |

**This guide:** Focus on **Blazor Server** (simpler for beginners)

## Creating Blazor Server App

```bash
dotnet new blazor -o MyBlazorApp
cd MyBlazorApp
dotnet run
```

Browse to: https://localhost:5001

## Component Structure

### Counter.razor

```razor
@page "/counter"

<h3>Counter</h3>

<p>Current count: @currentCount</p>

<button class="btn btn-primary" @onclick="IncrementCount">
    Click me
</button>

@code {
    private int currentCount = 0;

    private void IncrementCount()
    {
        currentCount++;
    }
}
```

**Parts:**
- `@page "/counter"` - Route URL
- HTML markup with `@` for C# expressions
- `@code { }` - C# logic

## Key Concepts

### 1. Data Binding

```razor
<input @bind="name" />
<p>Hello, @name!</p>

@code {
    private string name = "World";
}
```

Type in input → Variable updates → Page updates automatically

### 2. Event Handling

```razor
<button @onclick="HandleClick">Click</button>
<button @onclick="() => count++">Increment</button>

@code {
    private int count = 0;
    
    private void HandleClick()
    {
        count++;
    }
}
```

### 3. Parameters

```razor
<!-- WeatherCard.razor -->
<div class="card">
    <h5>@City</h5>
    <p>@Temperature°C</p>
</div>

@code {
    [Parameter]
    public string City { get; set; }
    
    [Parameter]
    public int Temperature { get; set; }
}
```

**Usage:**

```razor
<WeatherCard City="Sydney" Temperature="25" />
```

### 4. Component Communication

**Parent to Child:** Use Parameters

```razor
<!-- Parent -->
<ChildComponent Message="@parentMessage" />

<!-- Child receives via [Parameter] -->
```

**Child to Parent:** Use EventCallback

```razor
<!-- Child -->
<button @onclick="NotifyParent">Click</button>

@code {
    [Parameter]
    public EventCallback OnClick { get; set; }
    
    private async Task NotifyParent()
    {
        await OnClick.InvokeAsync();
    }
}

<!-- Parent -->
<ChildComponent OnClick="HandleChildClick" />

@code {
    private void HandleChildClick()
    {
        Console.WriteLine("Child clicked!");
    }
}
```

## Lifecycle Methods

```razor
@code {
    protected override void OnInitialized()
    {
        // Runs once when component loads
        Console.WriteLine("Component initialized");
    }
    
    protected override async Task OnInitializedAsync()
    {
        // Async initialization (e.g., fetch data)
        data = await httpClient.GetFromJsonAsync<Data[]>("/api/data");
    }
    
    protected override void OnParametersSet()
    {
        // Runs when parameters change
    }
}
```

## Dependency Injection

```razor
@inject HttpClient Http
@inject NavigationManager NavManager

<button @onclick="FetchData">Load Data</button>

@code {
    private async Task FetchData()
    {
        var data = await Http.GetFromJsonAsync<Product[]>("/api/products");
    }
}
```

## Complete Example: Todo List

```razor
@page "/todos"

<h3>My Todos</h3>

<input @bind="newTodo" placeholder="New todo..." />
<button @onclick="AddTodo">Add</button>

<ul>
    @foreach (var todo in todos)
    {
        <li>
            @todo
            <button @onclick="() => RemoveTodo(todo)">❌</button>
        </li>
    }
</ul>

@code {
    private List<string> todos = new();
    private string newTodo = "";
    
    private void AddTodo()
    {
        if (!string.IsNullOrWhiteSpace(newTodo))
        {
            todos.Add(newTodo);
            newTodo = "";
        }
    }
    
    private void RemoveTodo(string todo)
    {
        todos.Remove(todo);
    }
}
```

## Real-Time Updates (SignalR Automatic)

Blazor Server uses SignalR automatically. Changes sync across all connected clients:

```razor
@page "/counter"

<h3>Shared Counter</h3>
<p>Count: @currentCount</p>
<button @onclick="IncrementCount">+1</button>

@code {
    [CascadingParameter]
    private SharedState State { get; set; }
    
    private int currentCount => State.Count;
    
    private void IncrementCount()
    {
        State.Count++;
    }
}
```

Open in multiple tabs → All update together!

## Forms and Validation

```razor
<EditForm Model="person" OnValidSubmit="HandleSubmit">
    <DataAnnotationsValidator />
    <ValidationSummary />
    
    <InputText @bind-Value="person.Name" />
    <ValidationMessage For="() => person.Name" />
    
    <button type="submit">Submit</button>
</EditForm>

@code {
    private Person person = new();
    
    private void HandleSubmit()
    {
        Console.WriteLine($"Submitted: {person.Name}");
    }
}

public class Person
{
    [Required]
    [StringLength(50)]
    public string Name { get; set; }
}
```

## Blazor vs JavaScript SPAs

| Feature | Blazor | React/Vue/Angular |
|---------|--------|-------------------|
| **Language** | C# | JavaScript |
| **Learning curve** | Low (if you know C#) | Medium-High |
| **Real-time** | ✅ Built-in (SignalR) | Manual setup |
| **SEO** | ⚠️ Blazor Server OK | Requires SSR |
| **Ecosystem** | Smaller | Huge |

## Best Practices

✅ **Do:**
- Use Blazor Server for real-time apps
- Keep components small and focused
- Use dependency injection
- Handle async with `OnInitializedAsync`

❌ **Don't:**
- Use for static content (use Razor Pages)
- Forget `@code` block
- Block UI with synchronous calls
- Overuse Blazor for simple pages

## When to Use Blazor

**Use Blazor when:**
- You know C# well, JavaScript less so
- Need real-time updates across clients
- Building internal business apps
- Want to share code with backend

**Don't use Blazor when:**
- Building public-facing marketing site
- SEO is critical (use Razor Pages)
- Simple forms (use Razor Pages + HTMX)
- Need cutting-edge JavaScript features

## Quick Reference

```razor
@page "/example"
@inject HttpClient Http

<h3>@title</h3>

<input @bind="text" />
<button @onclick="HandleClick">Click</button>

<p>Count: @count</p>

@code {
    private string title = "My Component";
    private string text = "";
    private int count = 0;
    
    private void HandleClick()
    {
        count++;
    }
    
    protected override async Task OnInitializedAsync()
    {
        // Load data
    }
}
```
