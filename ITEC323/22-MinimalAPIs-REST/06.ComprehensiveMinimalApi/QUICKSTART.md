# Quick Start

## Run The API

```bash
cd 22-MinimalAPIs-REST/06.ComprehensiveMinimalApi
dotnet run
```

## Example Requests

```bash
curl http://localhost:5627/
curl http://localhost:5627/api/tasks
curl "http://localhost:5627/api/tasks/search?priority=High&completed=false"
curl http://localhost:5627/api/tasks/summary
curl -X POST http://localhost:5627/api/tasks -H "Content-Type: application/json" -d '{"title":"Record curl examples","description":"Use realistic sample data.","priority":"Low"}'
curl -X PUT http://localhost:5627/api/tasks/1 -H "Content-Type: application/json" -d '{"title":"Plan the final demo","description":"Updated from curl.","priority":"High","isCompleted":true}'
curl -X DELETE http://localhost:5627/api/tasks/2
```

## Run The Unit Tests

```bash
cd 22-MinimalAPIs-REST/06.ComprehensiveMinimalApi/tests/06.ComprehensiveMinimalApi.Tests
dotnet restore
dotnet build
dotnet test
```
