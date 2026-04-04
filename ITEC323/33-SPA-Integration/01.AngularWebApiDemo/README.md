# 01.AngularWebApiDemo

## Overview

This sample demonstrates how to build a beginner-friendly Angular frontend that talks to an ASP.NET Core backend created from `Microsoft.JavaScript.Templates` with the `angularwebapi` template.

The project keeps both sides intentionally small:

- the Angular app loads one startup payload from the backend
- the Angular form sends one POST request to the backend
- the ASP.NET Core API returns simple JSON that students can inspect easily

## Screenshot

![SPA Integration Demo](01.angularwebapidemo.client/images/SPA-Integration-Demo.png)

## Learning Objectives

By working through this sample, students will learn how to:

- use `angularwebapi` on `.NET 10`
- understand the difference between local development flow and published deployment flow
- trace an HTTP request from Angular form input to ASP.NET Core controller to JSON response
- organize a small Angular HTTP service
- validate input on the backend before returning a response

## Project Structure

```text
01.AngularWebApiDemo/
├── 01.AngularWebApiDemo.Server/
│   ├── Controllers/
│   ├── Models/
│   ├── Services/
│   ├── Properties/
│   └── tests/
├── 01.angularwebapidemo.client/
│   └── src/
├── docs/
├── README.md
├── QUICKSTART.md
└── FRD.md
```

## Main Features

- `GET /api/spa-integration/overview` returns the data the Angular page shows on first load
- `POST /api/spa-integration/practice-message` accepts learner input and returns a personalized response
- the Angular page shows loading, success, and error states
- the published app can serve both the built Angular frontend and the backend API from ASP.NET Core

## Prerequisites

- .NET 10.0 SDK
- Node.js and npm
- `Microsoft.JavaScript.Templates`

## Related Files

- [QUICKSTART.md](QUICKSTART.md)
- [FRD.md](FRD.md)
- [docs/IntegrationFlow.md](docs/IntegrationFlow.md)
