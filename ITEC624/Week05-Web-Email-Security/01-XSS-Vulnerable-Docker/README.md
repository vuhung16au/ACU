# XSS Lab: Reflected XSS (Intentionally Vulnerable)

This lab demonstrates a classic reflected Cross-Site Scripting (XSS) vulnerability in a tiny Node.js/Express app using EJS templates. It lets you safely experiment with exploitation and then compare to a safe rendering path.

Suggested image name: `xss-lab-reflected` (alternatives: `xss-lab-vulnerable`, `xss-playground-reflected`).

## Run with Docker

```bash
# From this directory
docker build -t xss-lab-reflected .

# Run on port 3000
docker run --rm -p 3000:3000 xss-lab-reflected
```

Open `http://localhost:3000` in your browser.

## App Overview

- `/` (vulnerable): Reflected XSS. Echoes the `q` query param using unescaped EJS output.
- `/safe` (safe): Escapes the same input to prevent execution.

Files:
- `server.js`: Express server with two routes.
- `views/index.ejs`: Vulnerable view using EJS `<%- %>` (UNESCAPED) to reflect input.
- `views/safe.ejs`: Safe view using EJS `<%= %>` (ESCAPED) to reflect input.

## What is XSS and why this is vulnerable

Cross-Site Scripting (XSS) allows an attacker to inject and execute arbitrary JavaScript in a victim's browser. In reflected XSS, malicious input is immediately reflected in the response.

In this app, the vulnerable view uses EJS unescaped interpolation:

- `<%- userInput %>` renders raw HTML. If the attacker supplies HTML/JS, the browser executes it.
- By contrast, `<%= userInput %>` HTML-escapes special characters, preventing execution.

## How to exploit (for learning)

1. Visit:
   - `http://localhost:3000/?q=%3Cimg%20src%3Dx%20onerror%3Dalert('xss')%3E`
2. You should see an alert box.

Other payloads to try:
- `<script>alert('xss')</script>`
- `<svg onload=alert(1)>`

Hint: Check `views/index.ejs` and notice the unescaped EJS tag `<%- %>`.

## How to fix (in this app)

- Use escaped EJS interpolation `<%= userInput %>` instead of unescaped `<%- userInput %>`.
- Or sanitize with a robust HTML sanitizer before rendering (e.g., `sanitize-html`, or `DOMPurify` with JSDOM server-side).
- Avoid setting `innerHTML` on the client with untrusted data.

The `/safe` route in this app shows the correct escaping.

## How to prevent (general guidance)

- Escape by context:
  - HTML text: HTML-escape `<`, `>`, `&`, `'`, `"`.
  - HTML attributes: also escape `"`, `'` and validate allowed characters.
  - JavaScript contexts: do not inject untrusted data; prefer JSON encoding and safer templating.
  - URL contexts: URL-encode and validate schemes (e.g., only `https`/`http`).
- Prefer templating engines/frameworks that escape by default; don’t opt out.
- Apply strict Content Security Policy (CSP) to reduce impact (avoid `unsafe-inline`, use nonces/hashes).
- Validate and sanitize user input on the server, especially if you must render HTML.
- Avoid dangerous sinks (`innerHTML`, `document.write`, `eval`).

## Cleanup

Stop the container with Ctrl+C (or remove with `docker ps` / `docker stop`).

## Name suggestions

- `xss-lab-reflected` (recommended)
- `xss-lab-vulnerable`
- `xss-playground-reflected`
- `xss-lab-ejs-reflected`
