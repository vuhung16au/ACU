# Quick Start Guide - Hello World API

This guide will walk you through building and running your first minimal API application step by step.

## Prerequisites Check

Before you begin, verify you have the required tools installed:

```bash
# Check .NET version (should be 8.0 or later)
dotnet --version

# Expected output: 10.0.xxx or 9.0.xxx or 8.0.xxx
```

If you don't have .NET installed, visit [https://dotnet.microsoft.com/download](https://dotnet.microsoft.com/download)

## Step 1: Navigate to the Project Directory

Open your terminal or command prompt and navigate to this project's directory:

```bash
cd /path/to/01.HelloDotnet/02.HelloWorldApi
```

Or if you're starting from the repository root:

```bash
cd 01.HelloDotnet/02.HelloWorldApi
```

## Step 2: Restore Dependencies

Restore all NuGet packages required by the project:

```bash
dotnet restore
```

**Expected Output**:
```
Determining projects to restore...
Restored /path/to/HelloWorldApi.csproj (in X ms).
```

**What this does**: Downloads all required libraries and dependencies specified in the `.csproj` file. For minimal APIs, this is usually just the ASP.NET Core framework itself.

## Step 3: Build the Project

Compile the application to check for any errors:

```bash
dotnet build
```

**Expected Output**:
```
Build succeeded.
    0 Warning(s)
    0 Error(s)

Time Elapsed 00:00:XX.XX
```

**What this does**: Compiles your C# code into an executable format and validates that everything is correct.

## Step 4: Run the Application

Start the web API:

```bash
dotnet run
```

**Expected Output**:
```
info: Microsoft.Hosting.Lifetime[14]
      Now listening on: http://localhost:5050
info: Microsoft.Hosting.Lifetime[14]
      Now listening on: https://localhost:7050
info: Microsoft.Hosting.Lifetime[0]
      Application started. Press Ctrl+C to shut down.
info: Microsoft.Hosting.Lifetime[0]
      Hosting environment: Development
info: Microsoft.Hosting.Lifetime[0]
      Content root path: /path/to/02.HelloWorldApi
```

**What this does**: Starts the Kestrel web server and begins listening for HTTP requests on the configured ports.

## Step 5: Test the API

Now you can test your API in several ways:

### Option 1: Web Browser

1. Open your web browser
2. Navigate to one of the URLs displayed in the terminal:
   - HTTP: `http://localhost:5050`
   - HTTPS: `https://localhost:7050` (recommended)

**What you should see**:
- The text "Hello World!" displayed in your browser

### Option 2: Command Line (curl)

If you have `curl` installed (included with most systems):

```bash
curl http://localhost:5050
```

**Expected Output**:
```
Hello World!
```

### Option 3: PowerShell

On Windows, you can use PowerShell:

```powershell
Invoke-WebRequest http://localhost:5050 | Select-Object -ExpandProperty Content
```

**Expected Output**:
```
Hello World!
```

### Option 4: VS Code REST Client

If you have the REST Client extension installed in VS Code:

1. Create a file called `test.http`
2. Add the following content:
```http
GET http://localhost:5050
```
3. Click "Send Request" above the line

## Step 6: Stop the Application

To stop the running application, press:
- **Windows/Linux**: `Ctrl + C`
- **macOS**: `Cmd + C` or `Ctrl + C`

**Expected Output**:
```
info: Microsoft.Hosting.Lifetime[0]
      Application is shutting down...
```

## Alternative: Run with Hot Reload

For development, you can run with hot reload enabled. This automatically restarts the app when you make code changes:

```bash
dotnet watch run
```

**What you'll see**:
```
dotnet watch 🔥 Hot reload enabled. For a list of supported edits, see https://aka.ms/dotnet/hot-reload.
  💡 Press "Ctrl + R" to restart.
dotnet watch 🔧 Building...
...
dotnet watch 🚀 Started
```

**Try it**:
1. Leave the application running
2. Edit `Program.cs` and change `"Hello World!"` to `"Hello API!"`
3. Save the file
4. The app will automatically restart
5. Refresh your browser to see the change

## Verification Checklist

After completing all steps, verify:

- [ ] Project builds without errors (`dotnet build`)
- [ ] Application starts successfully (`dotnet run`)
- [ ] Browser displays "Hello World!" at `http://localhost:5050`
- [ ] Application can be stopped with Ctrl+C
- [ ] No error messages in the terminal

## Common Issues and Solutions

### Issue: "Port already in use"

**Error Message**:
```
Unable to bind to http://localhost:5050 on the IPv4 loopback interface: 'Address already in use'.
```

**Solution 1**: Stop the other application using that port, or:

**Solution 2**: Change the port in `Properties/launchSettings.json`:
```json
"applicationUrl": "http://localhost:5051"
```

**Solution 3**: Specify a different port when running:
```bash
dotnet run --urls "http://localhost:5051"
```

### Issue: "HTTPS certificate not trusted"

**Error Message**: Browser warning about untrusted certificate

**Solution**: Trust the development certificate:
```bash
dotnet dev-certs https --trust
```

Then restart the application.

### Issue: "Application doesn't respond"

**Symptoms**: Browser shows "Unable to connect" or similar error

**Solutions**:
1. Verify the application is running (check terminal for startup messages)
2. Check the exact URL shown in the terminal output
3. Try the HTTP URL instead of HTTPS
4. Check firewall settings
5. Try accessing from a different browser

### Issue: "Build failed"

**Solution**: Check for typos in `Program.cs`. The code should match exactly:
```csharp
var builder = WebApplication.CreateBuilder(args);
var app = builder.Build();

app.MapGet("/", () => "Hello World!");

app.Run();
```

## Understanding What's Happening

### When you run `dotnet run`:

1. **Compilation**: Your C# code is compiled to IL (Intermediate Language)
2. **Hosting**: The Kestrel web server starts
3. **Binding**: The server binds to the configured ports (5050 and 7050)
4. **Listening**: The server begins listening for HTTP requests
5. **Ready**: The application is now ready to handle requests

### When you visit `http://localhost:5050`:

1. **Request**: Your browser sends an HTTP GET request to the server
2. **Routing**: ASP.NET Core matches the request to the `/` route
3. **Handler**: The lambda function `() => "Hello World!"` executes
4. **Response**: The string is sent back to the browser
5. **Display**: Your browser displays the text

## Next Steps

Now that your API is running:

### 1. Modify the Message

Edit `Program.cs` and change the message:
```csharp
app.MapGet("/", () => "Welcome to my first API!");
```

Restart the app and refresh your browser.

### 2. Add a New Endpoint

Add another endpoint before `app.Run()`:
```csharp
app.MapGet("/hello", () => "Hello from a different endpoint!");
```

Now you can visit `http://localhost:5050/hello` to see the new response.

### 3. Add Route Parameters

Create a personalized greeting:
```csharp
app.MapGet("/hello/{name}", (string name) => $"Hello, {name}!");
```

Visit `http://localhost:5050/hello/YourName` to test it.

### 4. Return JSON

Return structured data instead of plain text:
```csharp
app.MapGet("/api/info", () => new { 
    Message = "Hello World!", 
    Timestamp = DateTime.Now,
    Version = "1.0"
});
```

The response will automatically be formatted as JSON!

### 5. Add More HTTP Methods

Create a POST endpoint:
```csharp
app.MapPost("/greet", (string name) => $"Nice to meet you, {name}!");
```

Test with curl:
```bash
curl -X POST "http://localhost:5050/greet?name=Alice"
```

## Learning Resources

After completing this quickstart:

1. **Read the Project Documentation**
   - [README.md](README.md) - Project overview and concepts
   - [FRD.md](FRD.md) - Functional requirements
   - [docs/MinimalApiBasics.md](docs/MinimalApiBasics.md) - Deep dive into minimal APIs

2. **Official Documentation**
   - [Minimal APIs Overview](https://docs.microsoft.com/aspnet/core/fundamentals/minimal-apis)
   - [Tutorial: Create a minimal API](https://docs.microsoft.com/aspnet/core/tutorials/min-web-api)

3. **Experiment**
   - Try adding more endpoints
   - Change response types
   - Add query parameters
   - Return different status codes

## Troubleshooting Help

If you encounter issues not covered here:

1. **Check the terminal output** for error messages
2. **Read the error message carefully** - it usually tells you what's wrong
3. **Verify your code** matches the examples exactly
4. **Restart the application** after making changes
5. **Check the project documentation** for more details
6. **Ask for help** with specific error messages

## Success!

If you can see "Hello World!" in your browser, congratulations! You've successfully:

✅ Created your first minimal API  
✅ Compiled and ran an ASP.NET Core application  
✅ Understood the basics of web servers and HTTP  
✅ Tested an API endpoint  

You're now ready to build more complex APIs!

---

**Course**: ITEC323 - Application Development  
**Institution**: Australian Catholic University (ACU)  
**Last Updated**: February 2026
