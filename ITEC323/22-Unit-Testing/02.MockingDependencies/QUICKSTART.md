# Quick Start

## Prerequisites

- .NET 10 SDK

## Run The Razor Pages App

```bash
cd 22-Unit-Testing/02.MockingDependencies
dotnet run
```

Open the local URL shown in the terminal.

## Run The Unit Tests

```bash
cd 22-Unit-Testing/02.MockingDependencies/tests/02.MockingDependencies.Tests
dotnet restore
dotnet build
dotnet test
```

## Pages To Check

- `/` shows the form, validation summary, and successful submission result

## Sample Duplicate Emails

The running app treats these emails as already registered:

- `ada@example.com`
- `grace@example.com`

## What The Unit Tests Cover

The tests check:

1. required name validation
2. required email validation
3. email format validation
4. duplicate email validation
5. interaction with the mocked email registry
6. valid input processing
7. unselected language display behavior

## Build Check

```bash
dotnet build
```
