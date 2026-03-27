# Quick Start

## Run The API

```bash
cd 22-MinimalAPIs-REST/05.ErrorHandlingAndStatusCodes
dotnet run
```

## Example Requests

```bash
curl http://localhost:5626/api/tasks/1
curl http://localhost:5626/api/tasks/99
curl -X POST http://localhost:5626/api/tasks -H "Content-Type: application/json" -d '{"title":"Review API status codes","description":"Duplicate title example.","priority":"Medium"}'
curl -X POST http://localhost:5626/api/tasks/fail
```

## Run The Unit Tests

```bash
cd 22-MinimalAPIs-REST/05.ErrorHandlingAndStatusCodes/tests/05.ErrorHandlingAndStatusCodes.Tests
dotnet restore
dotnet build
dotnet test
```
