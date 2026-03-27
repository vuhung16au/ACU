# Quick Start

## Prerequisites

- .NET 10 SDK

## Run The Razor Pages App

```bash
cd 22-Unit-Testing/04.ExceptionTesting
dotnet run
```

Open the local URL shown in the terminal.

## Run The Unit Tests

```bash
cd 22-Unit-Testing/04.ExceptionTesting/tests/04.ExceptionTesting.Tests
dotnet restore
dotnet build
dotnet test
```

## Pages To Check

- `/` shows the form, error message, and successful submission result

## Sample Duplicate Emails

The running app treats these emails as already registered:

- `ada@example.com`
- `grace@example.com`

## What The Unit Tests Cover

The tests check:

1. thrown exceptions for invalid input
2. thrown exceptions for duplicate email
3. thrown exceptions from dependency failures
4. successful result values for valid input

## Build Check

```bash
dotnet build
```
