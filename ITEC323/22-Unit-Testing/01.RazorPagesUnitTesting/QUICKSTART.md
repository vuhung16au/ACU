# Quick Start

## Prerequisites

- .NET 10 SDK

## Run The Razor Pages App

```bash
cd 22-Unit-Testing/01.RazorPagesUnitTesting
dotnet run
```

Open the local URL shown in the terminal.

## Run The Unit Tests

```bash
cd 22-Unit-Testing/01.RazorPagesUnitTesting/tests/01.RazorPagesUnitTesting.Tests
dotnet restore
dotnet build
dotnet test
```

## Pages To Check

- `/` shows the form and validation summary

## What The Unit Tests Cover

The tests check:

1. required name validation
2. required email validation
3. email format validation
4. valid input processing
5. unselected language display behavior

## Build Check

```bash
dotnet build
```

## Troubleshooting

If the tests do not run:

- make sure the .NET 10 SDK is installed
- run `dotnet restore` before `dotnet test`
