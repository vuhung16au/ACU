# 01.RazorPagesUnitTesting

## Overview

This project demonstrates a simple ASP.NET Core Razor Pages form while teaching **unit testing** with **xUnit** and **FluentAssertions**.

The form collects:

1. name
2. email
3. favourite language

After the user clicks **Submit**, the page displays the submitted values.

## Learning Objectives

By working through this project, students will learn how to:

- build a simple Razor Pages form
- move validation logic into a dedicated validator class
- move result-building logic into a small service class
- write unit tests for C# classes without opening a browser
- use `Fact` and `Theory` tests
- follow the Arrange-Act-Assert pattern

## Project Structure

```text
01.RazorPagesUnitTesting/
├── 01.RazorPagesUnitTesting.csproj
├── Program.cs
├── Models/
├── Services/
├── Pages/
├── Properties/
├── wwwroot/
├── docs/
├── tests/
├── README.md
├── QUICKSTART.md
└── FRD.md
```

## Main Features

- simple Razor Pages form with three inputs
- basic validation for required name and email
- email format checking
- confirmation section showing submitted values
- unit-tested validation and submission processing

## What Gets Unit Tested

- valid input passes validation
- missing name returns an error
- missing email returns an error
- invalid email returns an error
- processed results trim values and display them clearly
- an unselected language is displayed as `Unselected`

## Related Files

- [QUICKSTART.md](QUICKSTART.md) for setup and run steps
- [FRD.md](FRD.md) for functional requirements
- [docs/Key-Takeaways.md](docs/Key-Takeaways.md) for teaching notes
