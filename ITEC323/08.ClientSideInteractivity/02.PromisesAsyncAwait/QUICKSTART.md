# Quick Start

## Prerequisites

- .NET 10 SDK installed

## Run The Project

```bash
cd 08.ClientSideInteractivity/02.PromisesAsyncAwait
dotnet run
```

Open the local URL shown in the terminal.

## Pages To Check

- `/` module home page with async learning goals
- `/AsyncLab` Promise chain and async/await demo
- `/Privacy` notes about local-only sample data
- `/api/learningsessions` raw list JSON endpoint
- `/api/learningsessions/summary` raw summary JSON endpoint

## Build Check

```bash
dotnet build
```

## Classroom Flow Suggestion

1. Use **Load With Promise Chain** and inspect the network calls.
2. Use **Load With Async/Await** and compare the JavaScript flow.
3. Use **Simulate API Error** to practice error messaging.
4. Submit the form to create a session and observe table refresh.
