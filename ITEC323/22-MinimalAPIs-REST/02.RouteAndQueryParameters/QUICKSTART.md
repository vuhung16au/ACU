# Quick Start

## Prerequisites

- .NET 10 SDK

## Run The API

```bash
cd 22-MinimalAPIs-REST/02.RouteAndQueryParameters
dotnet run
```

## Example Requests

```bash
curl http://localhost:5623/
curl http://localhost:5623/api/tasks/2
curl "http://localhost:5623/api/tasks/search?priority=High"
curl "http://localhost:5623/api/tasks/filter?completed=false"
```

## Run The Unit Tests

```bash
cd 22-MinimalAPIs-REST/02.RouteAndQueryParameters/tests/02.RouteAndQueryParameters.Tests
dotnet restore
dotnet build
dotnet test
```
