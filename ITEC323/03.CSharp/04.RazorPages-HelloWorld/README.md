# Hello World - ASP.NET Core Razor Pages

## Overview

This is a beginner-friendly introduction to **ASP.NET Core Razor Pages**, demonstrating how to build web applications using C# and .NET. This project serves as your first step into web development, transitioning from console applications to interactive web pages that run in a browser.

## Screenshot

![localhost 5000](images/localhost5000.png)

## 🎯 Learning Objectives

By completing this project, you will learn:

1. **Web Development Basics**
   - How web applications differ from console applications
   - HTTP protocol and client-server communication
   - How browsers request and display web pages

2. **ASP.NET Core Framework**
   - Setting up an ASP.NET Core web application
   - Understanding the application entry point (Program.cs)
   - Configuring middleware pipeline for request processing

3. **Razor Pages**
   - Page-based programming model for web applications
   - Combining HTML with C# using Razor syntax
   - Separating logic (code-behind) from presentation (views)

4. **Project Structure**
   - Organizing web application files and folders
   - Static file serving (CSS, JavaScript, images)
   - Layouts and shared components

## 📋 Prerequisites

Before starting this project, ensure you have:

- **.NET 10.0 SDK** or later installed ([Download here](https://dotnet.microsoft.com/download))
- **Basic C# knowledge** (completed 01.HelloWorldCLI and 02.CSharpCore)
- **Text editor or IDE** (Visual Studio Code, Visual Studio, or Rider)
- **Web browser** (Chrome, Firefox, Edge, or Safari)
- **Command-line familiarity** (Terminal on macOS/Linux, Command Prompt on Windows)

Check your .NET version:
```bash
dotnet --version
```

Expected output: `10.0.103` or higher

## 🏗️ Project Structure

```
04.RazorPages-HelloWorld/
├── Program.cs                      # Application entry point and configuration
├── RazorPagesHelloWorld.csproj    # Project file (.NET 10.0, Web SDK)
│
├── Pages/                          # Razor Pages folder
│   ├── Index.cshtml                # Home page (HTML + Razor syntax)
│   ├── Index.cshtml.cs             # Home page model (C# code-behind)
│   ├── Error.cshtml                # Error page (HTML)
│   ├── Error.cshtml.cs             # Error page model (C# code-behind)
│   ├── _ViewImports.cshtml         # Common using directives
│   ├── _ViewStart.cshtml           # Default layout configuration
│   └── Shared/
│       └── _Layout.cshtml          # Shared layout template
│
├── wwwroot/                        # Static files (served to browser)
│   └── css/
│       └── site.css                # Application stylesheet
│
├── docs/                           # Documentation
│   └── Key-Takeaways.md            # Detailed concept explanations
│
├── README.md                       # This file
└── QUICKSTART.md                   # Step-by-step build and run guide
```

## 🔑 Key Components Explained

### Program.cs
The application entry point that:
- Configures services (dependency injection)
- Sets up the request processing pipeline (middleware)
- Configures Razor Pages support
- Starts the web server (Kestrel)

### Pages/Index.cshtml
The main page that:
- Combines HTML structure with C# code using Razor syntax
- Displays dynamic content from the page model
- Shows system information and project details
- Demonstrates Razor directives (`@page`, `@model`, `@foreach`)

### Pages/Index.cshtml.cs
The page model (code-behind) that:
- Contains the C# logic for the Index page
- Provides data through properties
- Handles HTTP requests via `OnGet()` method
- Separates logic from presentation

### Pages/Shared/_Layout.cshtml
The layout template that:
- Defines the common structure (header, navigation, footer)
- Wraps page content using `@RenderBody()`
- Ensures consistency across all pages
- Includes CSS and other shared resources

### wwwroot/css/site.css
The stylesheet that:
- Controls visual appearance (colors, fonts, spacing)
- Creates responsive design (mobile-friendly)
- Styles cards, sections, and components
- Demonstrates modern CSS techniques

## 🚀 Quick Start

See [QUICKSTART.md](QUICKSTART.md) for detailed instructions on:
- Building the project with `dotnet build`
- Running the application with `dotnet run`
- Accessing the web page in your browser
- Stopping the server
- Troubleshooting common issues

**Quick version:**
```bash
# Navigate to the project directory
cd 03.CSharp/04.RazorPages-HelloWorld

# Build the project
dotnet build

# Run the application
dotnet run

# Open your browser to:
https://localhost:5001
```

Press **Ctrl+C** to stop the server.

## 📚 Key Concepts

This project introduces fundamental web development concepts:

### 1. Web Applications vs Console Applications

**Console Applications** (what you've learned so far):
- Run in a terminal/command prompt
- Use `Console.WriteLine()` for output
- Single user at a time
- Completes and exits when done

**Web Applications** (what you're learning now):
- Run on a web server
- Send HTML to browsers for display
- Multiple users simultaneously
- Stays running, waiting for requests

### 2. HTTP Protocol

**HTTP (HyperText Transfer Protocol)** is how browsers and servers communicate:

```
Browser                              Server
   |                                    |
   |  GET / HTTP/1.1                   |
   | --------------------------------> |
   |                                    |  Program.cs processes request
   |                                    |  Routing finds Index.cshtml
   |                                    |  OnGet() method runs
   |                                    |  Razor engine generates HTML
   |                                    |
   |  <html><body>...</body></html>    |
   | <-------------------------------- |
   |                                    |
Displays page                      Waits for next request
```

### 3. Razor Syntax

Razor allows you to mix C# code with HTML:

```csharp
@page                              // Makes this a Razor Page
@model IndexModel                  // Links to the page model

<h1>@Model.WelcomeMessage</h1>    // Outputs C# property value

@foreach (var item in Model.Items)  // C# loop in HTML
{
    <li>@item</li>
}

@{
    // Multi-line C# code block
    var message = "Hello from C#!";
    var time = DateTime.Now;
}
<p>@message at @time</p>
```

### 4. Page Models (Code-Behind)

Separates logic from presentation:

**Index.cshtml.cs** (Logic):
```csharp
public class IndexModel : PageModel
{
    public string WelcomeMessage { get; set; }
    
    public void OnGet()
    {
        WelcomeMessage = "Hello, World!";
    }
}
```

**Index.cshtml** (Presentation):
```html
<h1>@Model.WelcomeMessage</h1>
```

### 5. Middleware Pipeline

Middleware components process HTTP requests in order:

```
Request → Exception Handler → HTTPS Redirect → Static Files → 
         Routing → Authorization → Razor Pages → Response
```

Each middleware:
- Processes the request
- Either handles it or passes to the next middleware
- Can modify the request or response

For detailed explanations of these and more concepts, see [docs/Key-Takeaways.md](docs/Key-Takeaways.md).

## 🎓 What Makes This Different from Console Apps?

| Aspect | Console App | Web App (Razor Pages) |
|--------|-------------|------------------------|
| **Output** | Terminal text | HTML in browser |
| **User Interface** | Command-line | Graphical web page |
| **Execution** | Runs once, then exits | Stays running, serves many requests |
| **Users** | One at a time | Concurrent users |
| **Interaction** | `Console.ReadLine()` | Forms, buttons, links |
| **Display** | `Console.WriteLine()` | HTML + CSS |
| **Starting** | `dotnet run` (completes) | `dotnet run` (stays running) |
| **Accessing** | Directly in terminal | Browser at localhost:5001 |

## 🔧 Technology Stack

- **.NET 10.0**: Latest Long-Term Support (LTS) version
- **C# 14**: Modern C# language features
- **ASP.NET Core**: Cross-platform web framework
- **Razor Pages**: Page-based programming model
- **Kestrel**: High-performance web server
- **HTML5**: Modern web markup
- **CSS3**: Styling and responsive design

## 📖 Learning Path

This project fits into your C# learning journey:

1. ✅ **01.HelloWorldCLI** - First C# program, basic syntax
2. ✅ **02.CSharpCore** - Core language features (variables, loops, methods)
3. ✅ **03.CSharpAdvanced** - Object-oriented programming (classes, inheritance)
4. **04.RazorPages-HelloWorld** ← **You are here**
5. **Next**: More advanced web applications with databases and user input

## 🧪 Exercises to Try

After running the project, try these modifications:

### Easy (Beginner)
1. Change the welcome message in `Index.cshtml.cs` `OnGet()` method
2. Add your name to the page title in `_Layout.cshtml`
3. Change the background color in `site.css`
4. Add a new property to `IndexModel` and display it on the page

### Medium (Intermediate)
5. Create a new property `public int VisitorCount` and display it
6. Add a new page (About.cshtml) with information about yourself
7. Show the day of the week and format the time nicely
8. Add a list of your favorite programming languages

### Advanced (Challenge)
9. Create a form that accepts user input (name) and displays a greeting
10. Add a second page and link between pages
11. Style the page to match your favorite website
12. Add a navigation menu with multiple pages

## 🐛 Troubleshooting

### Build Errors
```bash
# Clean and rebuild
dotnet clean
dotnet build
```

### Port Already in Use
```bash
# Find and kill the process using port 5001
# macOS/Linux:
lsof -ti:5001 | xargs kill -9

# Windows:
netstat -ano | findstr :5001
taskkill /PID <PID> /F
```

### Certificate Trust Issues
```bash
# Trust the development certificate
dotnet dev-certs https --trust
```

### Page Not Found (404)
- Ensure you're accessing `https://localhost:5001` (not port 5000)
- Check that `Index.cshtml` has `@page` directive at the top
- Verify the server is running (look for "Now listening on..." message)

For more detailed troubleshooting, see [QUICKSTART.md](QUICKSTART.md).

## 📚 Additional Resources

### Official Documentation
- [ASP.NET Core Documentation](https://docs.microsoft.com/aspnet/core)
- [Razor Pages Tutorial](https://docs.microsoft.com/aspnet/core/tutorials/razor-pages)
- [Razor Syntax Reference](https://docs.microsoft.com/aspnet/core/mvc/views/razor)

### Recommended Reading
- [Introduction to Razor Pages](https://docs.microsoft.com/aspnet/core/razor-pages)
- [Routing in Razor Pages](https://docs.microsoft.com/aspnet/core/razor-pages/index#url-generation-for-pages)
- [Page Models](https://docs.microsoft.com/aspnet/core/razor-pages/index#page-models)

### Video Tutorials
- [Razor Pages for Beginners](https://www.youtube.com/results?search_query=asp.net+core+razor+pages+tutorial)
- [Building Web Apps with C#](https://www.youtube.com/results?search_query=asp.net+core+web+apps)

## 🤝 Related Projects

- **01.HelloWorldCLI** - Your first C# console application
- **02.CSharpCore** - Core C# language features
- **03.CSharpAdvanced** - Object-oriented programming concepts
- **AspNetHelloWorld** - Another Razor Pages example

## 💡 Next Steps

After mastering this project:

1. **Experiment** - Modify the code and see what happens
2. **Read** - Study [docs/Key-Takeaways.md](docs/Key-Takeaways.md) for deeper understanding
3. **Practice** - Try the exercises listed above
4. **Build** - Create your own Razor Pages application from scratch
5. **Learn More** - Explore forms, databases, and authentication

## 📝 Course Context

This project is part of the ITEC323 course at ACU, designed to teach web development fundamentals to students who have learned console-based C# programming. It introduces the transition from terminal-based applications to browser-based web applications.

## ❓ Questions?

If you encounter issues or have questions:

1. **Check** [QUICKSTART.md](QUICKSTART.md) for build/run instructions
2. **Read** [docs/Key-Takeaways.md](docs/Key-Takeaways.md) for concept explanations
3. **Review** the error message carefully (they often tell you what's wrong)
4. **Search** online for the specific error message
5. **Ask** your instructor or teaching assistant

## 📄 License

This project is for educational purposes as part of the ITEC323 course at ACU.

---

**Happy Coding! 🎉**

Remember: Every expert was once a beginner. Take your time, experiment, and don't be afraid to make mistakes—that's how you learn!
