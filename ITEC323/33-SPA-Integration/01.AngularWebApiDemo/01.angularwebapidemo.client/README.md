# 01.angularwebapidemo.client

This Angular app is the frontend for the `01.AngularWebApiDemo` sample.

## Local Development

The ASP.NET Core backend uses SPA proxy support to launch the Angular development server when you run the server project in development mode.

## Main Frontend Files

- `src/app/app.component.ts` - page state and user actions
- `src/app/services/spa-demo.service.ts` - HTTP calls to the backend API
- `src/proxy.conf.js` - forwards `/api` requests to ASP.NET Core during development

## Build

```bash
npm install
npm run build
```
