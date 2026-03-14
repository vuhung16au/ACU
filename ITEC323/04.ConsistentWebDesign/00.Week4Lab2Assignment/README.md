# Week4Lab2Assignment

## Overview

The **Week 4 Lab 2 Assignment** is a very simple ASP.NET Core web application that helps students practice:

- **Creating a .NET web project**
- **Working with Razor Pages**
- **Handling form input in C#**
- **Displaying user input back to the browser**

When the application is running, the page allows a user to:

- Enter their **name** in a text box  
- Choose their **favourite programming language** from a dropdown list (C#, Java, Python)  
- Click a **Submit** button  
- See a message that says:

> Your name is &lt;name&gt; and your favourite programming language is &lt;language&gt;

## Learning Objectives

By completing this assignment, students should be able to:

- **Create** a new ASP.NET Core web project targeting **.NET 10.0**
- **Build** a simple HTML form with an input box, dropdown list, and submit button
- **Handle HTTP POST requests** in a Razor Page (`OnPost` method)
- **Bind form fields** to C# properties using `[BindProperty]`
- **Construct and display a result message** using string interpolation
- **Run and test** a .NET web project locally

## Technology Stack

- **Framework**: .NET 10.0 (ASP.NET Core)
- **Language**: C#
- **Project Type**: Razor Pages web app
- **Testing**: xUnit with FluentAssertions (basic example)

## Project Structure

At a high level, this project is organised as follows:

- `Week4Lab2Assignment.csproj` – Main project file (targets .NET 10.0)  
- `Program.cs` – Application entry point and web host configuration  
- `Pages/Index.cshtml` – Razor Page that contains the HTML form and output message  
- `Pages/Index.cshtml.cs` – Page model class that handles GET and POST requests  
- `UserPreferenceFormatter.cs` – Simple helper class that builds the output sentence  
- `tests/Week4Lab2Assignment.Tests/` – xUnit test project with a basic unit test  
- Documentation files in the project root:
  - `README.md` (this file)
  - `QUICKSTART.md`
  - `FRD.md`
  - `ITEC323-Week4Lab2Assignment-Step-by-Step Guide.md`

## Documentation

- **Quick start instructions** for building and running the project: see `QUICKSTART.md`  
- **Functional requirements** for the assignment: see `FRD.md`  
- **Detailed, step-by-step implementation guide** for students and coding agents:  
  `ITEC323-Week4Lab2Assignment-Step-by-Step Guide.md`

These documents are written for **beginners** and are intended to be easy to follow.

