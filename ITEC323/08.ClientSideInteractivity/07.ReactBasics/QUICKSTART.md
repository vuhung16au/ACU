# Quickstart - 07.ReactBasics

## Prerequisites

- .NET SDK 10 preview (matching this repository)
- Node.js 20+

## 1. Run backend API


```bash
dotnet run
```
<!-- 
From workspace root:

```bash
dotnet run --project 07.ReactBasics/07.ReactBasics.csproj --launch-profile http
``` -->

Backend URL: `http://localhost:5090`

## 2. Run React client

In a second terminal:

```bash
cd 07.ReactBasics/ClientApp
npm install
npm run dev
```

Frontend URL: `http://localhost:5173`

## 3. Test expected flow

1. Tips and lesson cards load from `/api/*`.
2. Add a new lesson in the form.
3. Confirm the new card appears immediately.

## Troubleshooting

- If CORS errors appear, confirm backend is running on `http://localhost:5090`.
- If port 5173 is in use, run `npm run dev -- --port 5174` and update proxy if needed.
