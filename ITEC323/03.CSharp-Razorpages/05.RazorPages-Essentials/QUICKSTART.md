# Quick Start Guide - Razor Pages Essentials

This guide will help you build and run the Razor Pages Essentials project step-by-step.

## Prerequisites Check

Before starting, verify you have the required software:

```bash
# Check .NET SDK version (should be 8.0 or later)
dotnet --version

# Check if you're in the correct directory
pwd
# Should show: .../ITEC323/03.CSharp/05.RazorPages-Essentials
```

## Step 1: Navigate to the Project

```bash
# From the ITEC323 repository root
cd 03.CSharp/05.RazorPages-Essentials
```

## Step 2: Restore Dependencies

This downloads any NuGet packages the project needs:

```bash
dotnet restore
```

**Expected Output**:
```
  Determining projects to restore...
  Restored .../RazorPagesEssentials.csproj (in X ms).
```

## Step 3: Build the Project

Compile the C# code into executable form:

```bash
dotnet build
```

**Expected Output**:
```
Build succeeded.
    0 Warning(s)
    0 Error(s)
```

If you see errors, check:
- ✅ You're in the correct directory
- ✅ All files were created properly
- ✅ Your .NET SDK is version 8.0 or later

## Step 4: Run the Application

Start the web server:

```bash
dotnet run
```

**Expected Output**:
```
info: Microsoft.Hosting.Lifetime[14]
      Now listening on: https://localhost:5001
info: Microsoft.Hosting.Lifetime[0]
      Application started. Press Ctrl+C to shut down.
```

## Step 5: Open in Browser

1. Open your web browser
2. Navigate to: `https://localhost:5001`
3. You may see a security warning about the SSL certificate:
   - Click "Advanced" or "Show Details"
   - Click "Proceed to localhost" or "Accept Risk"
   - This is normal for local development

## Step 6: Explore the Application

### Home Page
- You should see the welcome page
- Click the "Contact Us" link in the navigation

### Contact Form
Try submitting the form with different inputs:

**Valid Submission**:
1. Name: `John Doe`
2. Email: `john@example.com`
3. Message: `This is a test message that is longer than 10 characters.`
4. Click "Send Message"
5. You should be redirected to the Success page

**Invalid Submissions** (to test validation):

**Test 1 - Empty Form**:
- Leave all fields empty
- Click "Send Message"
- You should see error messages: "Please enter your name", etc.

**Test 2 - Invalid Email**:
- Name: `Jane Doe`
- Email: `not-an-email` (missing @ symbol)
- Message: `Test message that is long enough`
- Click "Send Message"
- You should see: "Please enter a valid email address"

**Test 3 - Message Too Short**:
- Name: `Bob Smith`
- Email: `bob@example.com`
- Message: `Too short` (less than 10 characters)
- Click "Send Message"
- You should see: "The Message must be at least 10 characters."

## Step 7: Stop the Application

When you're done testing:
1. Go back to the terminal where `dotnet run` is running
2. Press `Ctrl+C`
3. The server will shut down gracefully

## Common Issues and Solutions

### Issue: Port Already in Use

**Error**: `Failed to bind to address https://localhost:5001: address already in use`

**Solution**:
```bash
# Find what's using the port
lsof -ti:5001 | xargs kill -9

# Or specify a different port
dotnet run --urls "https://localhost:6001"
```

### Issue: Build Errors

**Error**: `The type or namespace name 'X' could not be found`

**Solution**:
```bash
# Clean and rebuild
dotnet clean
dotnet restore
dotnet build
```

### Issue: Changes Not Reflecting

**Solution**:
```bash
# Stop the app (Ctrl+C) and rebuild
dotnet build
dotnet run
```

Or use watch mode for automatic rebuilds:
```bash
dotnet watch run
```

### Issue: SSL Certificate Not Trusted

**On macOS**:
```bash
dotnet dev-certs https --trust
```

**On Windows**:
```bash
dotnet dev-certs https --trust
```

**On Linux**:
```bash
dotnet dev-certs https --check --trust
```

## Development Workflow

For active development, use watch mode:

```bash
# Automatically rebuild when files change
dotnet watch run
```

This automatically:
- Detects file changes
- Rebuilds the project
- Restarts the server
- Refreshes your browser (in some cases)

## Project Tour

Once the application is running, explore these pages:

1. **Home Page** (`/`)
   - Simple welcome page
   - Links to other pages
   - Demonstrates basic navigation

2. **Contact Page** (`/Contact`)
   - Form with three fields
   - Client-side and server-side validation
   - Demonstrates the POST-Redirect-GET pattern

3. **Success Page** (`/Success`)
   - Shows confirmation message
   - Demonstrates TempData usage
   - Buttons to return or submit another message

4. **Privacy Page** (`/Privacy`)
   - Sample privacy policy
   - Accessible from footer

## Understanding the Output

When you run `dotnet run`, you'll see various log messages:

```
info: Microsoft.Hosting.Lifetime[14]
      Now listening on: https://localhost:5001
```
- **What it means**: The web server is running and listening for requests

```
info: Microsoft.AspNetCore.Hosting.Diagnostics[1]
      Request starting HTTP/2 GET https://localhost:5001/
```
- **What it means**: A browser made a request to your application

```
info: Microsoft.AspNetCore.Routing.EndpointMiddleware[0]
      Executing endpoint '/Index'
```
- **What it means**: The application is processing a specific page

## Next Steps

After successfully running the project:

1. **Read the Code** - Open the files in your code editor and read the comments
2. **Experiment** - Try modifying the validation rules
3. **Learn More** - Read [docs/Key-Takeaways.md](docs/Key-Takeaways.md) for detailed explanations
4. **Extend** - Add new pages or form fields

## Getting Help

If you encounter issues:

1. **Check this guide** - Make sure you followed all steps
2. **Read error messages** - They usually tell you what's wrong
3. **Search the documentation** - [ASP.NET Core Docs](https://docs.microsoft.com/aspnet/core/)
4. **Ask your instructor** - They can help with course-specific questions

---

**Need Help?** If you're stuck, review the [README.md](README.md) or consult [docs/Key-Takeaways.md](docs/Key-Takeaways.md) for more detailed information.
