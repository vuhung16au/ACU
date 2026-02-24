# ITEC323 – Week 4 Lab 2 Assignment  
## Step-by-Step Implementation Guide

This guide walks you (or a coding agent like Cursor, Copilot, or Antigravity) through building the **Week 4 Lab 2** web application.

The final application will:

- Ask the user for their **name**
- Let them choose their **favourite programming language** from a dropdown (`C#`, `Java`, `Python`)
- Show a message:

  > Your name is &lt;name&gt; and your favourite programming language is &lt;language&gt;

All steps are designed for **beginners**.

---

## 1. Create the Project (if starting from scratch)

> If you are using the prepared project in the `ITEC323` repository, you can **skip to section 2**.

### 1.1 Using the .NET CLI

1. Open a terminal.
2. Navigate to your working folder.
3. Run:

   ```bash
   dotnet new webapp -n Week4Lab2Assignment
   ```

4. Change into the new folder:

   ```bash
   cd Week4Lab2Assignment
   ```

5. Make sure the project targets **.NET 10.0** in `Week4Lab2Assignment.csproj`:

   ```xml
   <TargetFramework>net10.0</TargetFramework>
   ```

### 1.2 Using Visual Studio (GUI)

1. Open **Visual Studio**.
2. Select **Create a new project**.
3. Choose **ASP.NET Core Web App** (Razor Pages).
4. Name the project **Week4Lab2Assignment**.
5. Choose **.NET 8.0** as the target framework.
6. Click **Create**.

---

## 2. Understand the Project Structure

In this assignment, we use a simple structure:

- `Program.cs` – sets up the web application and Razor Pages
- `Pages/Index.cshtml` – the HTML and Razor markup (what the user sees)
- `Pages/Index.cshtml.cs` – the C# page model (handles form input)
- `UserPreferenceFormatter.cs` – a small helper class that builds the output sentence

You will mainly work with these three files:

- `Pages/Index.cshtml`
- `Pages/Index.cshtml.cs`
- `UserPreferenceFormatter.cs`

---

## 3. Configure the Web Application (`Program.cs`)

Open `Program.cs` and make sure it looks similar to this (simplified for learning):

```csharp
using Microsoft.AspNetCore.Builder;
using Microsoft.Extensions.Hosting;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddRazorPages();

var app = builder.Build();

if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error");
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseStaticFiles();

app.UseRouting();

app.MapRazorPages();

app.Run();
```

- `AddRazorPages()` tells ASP.NET Core we want to use Razor Pages.
- `MapRazorPages()` makes Razor Pages endpoints available in the app.

---

## 4. Build the User Interface (`Pages/Index.cshtml`)

Open `Pages/Index.cshtml` and create a simple form:

- A text box for the **name**
- A dropdown list for the **favourite language**
- A **Submit** button
- A paragraph to display the final message

Key ideas:

- Use a `<form method="post">` so the browser sends a **POST** request.
- Give the inputs `name="Name"` and `name="FavouriteLanguage"` so they bind to C# properties.

Make sure your page:

- Shows a heading (e.g., "Week 4 Lab 2 Assignment")
- Has:
  - An `<input>` for the name
  - A `<select>` with three `<option>` values: `C#`, `Java`, `Python`
  - A `<button type="submit">Submit</button>`
- Displays the result message only when it is not empty.

---

## 5. Handle the Form in C# (`Pages/Index.cshtml.cs`)

Open `Pages/Index.cshtml.cs`. This is the **page model** class.

### 5.1 Add Properties for Data Binding

Add three properties:

- `Name` – stores the value from the name input box
- `FavouriteLanguage` – stores the selected language
- `ResultMessage` – the sentence shown back to the user

Use `[BindProperty]` on the input properties so ASP.NET Core automatically fills them when the form is submitted:

- `[BindProperty] public string Name { get; set; }`
- `[BindProperty] public string FavouriteLanguage { get; set; }`

`ResultMessage` can be a simple read-only property with a private setter.

### 5.2 Implement `OnGet` and `OnPost`

- `OnGet()` runs when the page is first loaded (HTTP GET).
  - You can clear `ResultMessage` here.
- `OnPost()` runs when the form is submitted (HTTP POST).
  - Here you will call a helper method to build the final sentence.

Example flow in `OnPost()`:

1. Read the `Name` and `FavouriteLanguage` properties.
2. Pass them to a helper called `UserPreferenceFormatter.FormatMessage`.
3. Store the returned string in `ResultMessage`.

---

## 6. Create the Helper Class (`UserPreferenceFormatter.cs`)

Create a new file `UserPreferenceFormatter.cs` in the project root.

Requirements:

- Namespace: `Week4Lab2Assignment`
- Class: `UserPreferenceFormatter`
- Type: `public static class`
- Method:
  - Name: `FormatMessage`
  - Parameters: `string name`, `string favouriteLanguage`
  - Returns: `string`

The method should **build exactly** this sentence:

```text
Your name is <name> and your favourite programming language is <language>
```

Implementation idea:

- Use **string interpolation**:

  ```csharp
  return $"Your name is {name} and your favourite programming language is {favouriteLanguage}";
  ```

This keeps the logic very easy to understand and also easy to test.

---

## 7. Connect the Pieces

At this point:

- The HTML form posts `Name` and `FavouriteLanguage` to the server.
- ASP.NET Core fills the C# properties on the page model.
- `OnPost()` calls `UserPreferenceFormatter.FormatMessage(...)`.
- The result is stored in `ResultMessage`.
- `Index.cshtml` checks `Model.ResultMessage` and shows it to the user.

Make sure:

- The `@model` directive at the top of `Index.cshtml` matches the page model class name and namespace:
  - `@model Week4Lab2Assignment.Pages.IndexModel`
- `UserPreferenceFormatter` is in the `Week4Lab2Assignment` namespace so you can call `UserPreferenceFormatter.FormatMessage(...)` without extra configuration.

---

## 8. Run and Test the Application

1. Open a terminal in the `Week4Lab2Assignment` folder.
2. Build the project:

   ```bash
   dotnet build
   ```

3. Run the project:

   ```bash
   dotnet run
   ```

4. Open the URL shown in the console (for example `https://localhost:5001`) in your browser.
5. Try different combinations:
   - `Name`: Alice, `Language`: C#
   - `Name`: Bob, `Language`: Java
   - `Name`: Carol, `Language`: Python
6. Confirm the sentence matches the format:

   > Your name is Alice and your favourite programming language is C#

---

## 9. Add a Simple Unit Test (Optional but Recommended)

To practise testing:

1. Create a `tests/Week4Lab2Assignment.Tests` project using xUnit.
2. Add a reference to the main `Week4Lab2Assignment` project.
3. Write a test for `UserPreferenceFormatter.FormatMessage`, for example:
   - Call `FormatMessage("Alice", "C#")`
   - Assert that the result is:

     ```text
     Your name is Alice and your favourite programming language is C#
     ```

4. Run tests with:

   ```bash
   dotnet test
   ```

This helps you (and coding agents) check that the core logic is correct.

---

## 10. Guidelines for Coding Agents

When a coding agent (Cursor, Copilot, Antigravity, etc.) works on this assignment, it should:

- **Follow the ITEC323 `AGENTS.md` guidelines**, noting that this assignment has been updated to target **.NET 10.0**:
  - Use **clear, beginner-friendly** code
  - Add XML documentation to public members where helpful
  - Place tests in a `tests/` folder and use **xUnit**
- **Avoid over-engineering**:
  - No complex patterns
  - No unnecessary layers or abstractions
- **Keep behaviour consistent** with this guide and the FRD:
  - Same message format
  - Same list of languages
  - Single-page interaction

Following these steps will produce a clean, simple solution that matches the requirements for Week 4 Lab 2 and is easy for students to read and modify.

