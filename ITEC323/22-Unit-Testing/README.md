# 22-Unit-Testing

## Overview

This module introduces **unit testing** in .NET with **C#**, **xUnit**, and **FluentAssertions**.

Students learn how to:

- identify small pieces of logic to test
- write beginner-friendly unit tests
- follow the Arrange-Act-Assert pattern
- separate validation and processing logic from a Razor Pages PageModel

## Why This Module Matters

Unit tests help students check business logic without opening a browser or clicking through the full user interface.

This module focuses on:

- fast feedback
- clear test cases
- simple classes with one responsibility
- behavior-focused assertions

## How This Differs From 31-Playwright-Testing

`31-Playwright-Testing` shows browser automation and UI recording.

`22-Unit-Testing` focuses on testing small C# classes directly. It teaches students to test:

- validation rules
- result-building logic
- expected outputs for different inputs

## Project Structure

- `01.RazorPagesUnitTesting` - a Razor Pages sample form with unit-tested validation and submission logic
- `02.MockingDependencies` - a follow-up project that teaches mocked dependencies and interaction verification
- `03.AsyncUnitTesting` - a follow-up project that teaches async unit testing with awaited dependencies and async mocks

## Technology Stack

- .NET 10
- C# 14
- ASP.NET Core Razor Pages
- xUnit
- FluentAssertions
