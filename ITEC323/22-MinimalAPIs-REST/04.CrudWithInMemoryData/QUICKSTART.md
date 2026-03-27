# Quick Start

## Run The API

```bash
cd 22-MinimalAPIs-REST/04.CrudWithInMemoryData
dotnet run
```

## Example Requests

```bash
curl http://localhost:5624/api/tasks
curl http://localhost:5624/api/tasks/1
curl -X POST http://localhost:5624/api/tasks -H "Content-Type: application/json" -d '{"title":"Add a new task","description":"Store it in memory.","priority":"Low"}'
curl -X PUT http://localhost:5624/api/tasks/1 -H "Content-Type: application/json" -d '{"title":"Plan the CRUD routes","description":"Now updated.","priority":"High","isCompleted":true}'
curl -X DELETE http://localhost:5624/api/tasks/2
```

## Run The Unit Tests

```bash
cd 22-MinimalAPIs-REST/04.CrudWithInMemoryData/tests/04.CrudWithInMemoryData.Tests
dotnet restore
dotnet build
dotnet test
```
