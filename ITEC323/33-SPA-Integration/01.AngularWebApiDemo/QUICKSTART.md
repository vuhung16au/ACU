# Quick Start Guide

## 1. Install the JavaScript templates

The installed `.NET 10` SDK does not provide `dotnet new angular`.
This sample uses `Microsoft.JavaScript.Templates` and the `angularwebapi` template instead.

```bash
dotnet new install Microsoft.JavaScript.Templates
```

## 2. Check Node.js and npm

```bash
node --version
npm --version
```

If either command is missing, install Node.js before continuing.

## 3. Restore and build the sample

```bash
cd 33-SPA-Integration/01.AngularWebApiDemo/01.angularwebapidemo.client
npm install

cd ../01.AngularWebApiDemo.Server
dotnet restore
dotnet build
```

## 4. Run the app locally

```bash
dotnet run
```

Open the HTTPS URL shown in the terminal, usually:

```text
https://localhost:7252
```

## 5. Verify the local development flow

Confirm these checks:

- the Angular page opens in the browser
- the overview cards appear after the app calls `GET /api/spa-integration/overview`
- submitting the form calls `POST /api/spa-integration/practice-message`
- a friendly response card appears under the form

You can also inspect the API directly:

- Swagger UI: `https://localhost:7252/swagger`
- overview endpoint: `https://localhost:7252/api/spa-integration/overview`

## 6. Run the backend tests

```bash
dotnet test tests/01.AngularWebApiDemo.Server.Tests
```

## 7. Publish the integrated app

From the server folder:

```bash
dotnet publish -c Release -o ../../publish
```

The publish target runs `npm install` and `npm run build` in the Angular app before ASP.NET Core serves the built frontend assets.

Then run the published output:

```bash
cd ../../publish
dotnet 01.AngularWebApiDemo.Server.dll
```

Open the published app URL and confirm:

- the Angular page still loads
- the page still fetches overview data
- the form still sends a POST request successfully

## Troubleshooting

**Issue:** `dotnet new angular` is unavailable.
**Solution:** Install `Microsoft.JavaScript.Templates` and use `angularwebapi`.

**Issue:** The Angular page does not load during local development.
**Solution:** Confirm Node.js and npm are installed, then rerun `dotnet run` from the server project.

**Issue:** The API call fails from the Angular page.
**Solution:** Check the browser developer tools network tab and confirm the backend is still running.
