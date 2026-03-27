# 01.HelloMinimalApi

## Overview

This project introduces **ASP.NET Core Minimal APIs** with a short, practical REST API example.

Students learn how to:

- map simple HTTP endpoints in `Program.cs`
- return JSON from `GET` and `POST` handlers
- accept route values and JSON request bodies
- return appropriate HTTP status codes

## Learning Objectives

By working through this project, students will learn how to:

- create a Minimal API project with .NET 10
- understand the role of `WebApplication.CreateBuilder()` and `app.MapGet()`
- compare route parameters and request bodies
- build a small REST-style API without controllers
- unit test small service classes that support API endpoints

## Main Features

- `GET /` returns a simple API overview
- `GET /api/message` returns a welcome message as JSON
- `GET /api/languages` returns a small list of programming languages
- `GET /api/greet/{name}` returns a personalized greeting
- `GET /api/submissions/latest` returns a sample created resource
- `POST /api/submissions` accepts JSON and returns a created response

## Project Structure

```text
01.HelloMinimalApi/
├── 01.HelloMinimalApi.csproj
├── Program.cs
├── Models/
├── Services/
├── Properties/
├── docs/
├── tests/
├── README.md
├── QUICKSTART.md
└── FRD.md
```

## Related Files

- [QUICKSTART.md](QUICKSTART.md) for setup and run steps
- [FRD.md](FRD.md) for functional requirements
- [docs/MinimalApiBasics.md](docs/MinimalApiBasics.md) for learning notes
