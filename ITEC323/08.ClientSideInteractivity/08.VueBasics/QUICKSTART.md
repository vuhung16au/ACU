# Quickstart - 08.VueBasics

## Prerequisites

- .NET SDK 10 
- Node.js 20+

## 1. Run backend API

From the `08.VueBasics/` folder:

```bash
cd 08.VueBasics
dotnet run
```

Or from the workspace root:

```bash
dotnet run --project 08.VueBasics/08.VueBasics.csproj --launch-profile http
```

Backend URL: `http://localhost:5100`

## 2. Run Vue client

In a second terminal:

```bash
cd 08.VueBasics/ClientApp
npm install
npm run dev
```

Frontend URL: `http://localhost:5175`

## 3. Test expected flow

1. Tips and lesson cards load from `/api/*`.
2. The beginner lesson count updates via `computed` automatically.
3. Add a new lesson in the form using `v-model`.
4. Confirm the new card appears immediately.

## Troubleshooting

- If CORS errors appear, confirm backend is running on `http://localhost:5100`.
- If port 5175 is in use, run `npm run dev -- --port 5176` and update proxy if needed.
