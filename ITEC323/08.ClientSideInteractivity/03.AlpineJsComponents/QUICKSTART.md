# Quick Start

## Prerequisites

- .NET 10 SDK installed
- Internet access to load Alpine.js from CDN

## Run The Project

```bash
cd 08.ClientSideInteractivity/03.AlpineJsComponents
dotnet run
```

Open the local URL shown in the terminal.

## Pages To Check

- `/` module home page and Alpine tips
- `/AlpineLab` complete Alpine.js component playground
- `/Privacy` notes about local-only classroom data

## Build Check

```bash
dotnet build
```

## Classroom Flow Suggestion

1. Open `/AlpineLab` and identify each component's `x-data` block.
2. Toggle dropdown/modal visibility to compare `x-show` and `x-if`.
3. Switch tabs and accordion panels to observe state changes.
4. Add and remove todo items using `x-model` and `x-on`.
