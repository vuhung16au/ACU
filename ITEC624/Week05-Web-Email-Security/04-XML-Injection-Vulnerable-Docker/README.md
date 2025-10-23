# XML Injection Lab: XPath Injection (Intentionally Vulnerable)

This lab demonstrates XML/XPath Injection in a tiny Node.js/Express app. The vulnerable endpoint builds an XPath query by string-concatenating untrusted user input, allowing attackers to bypass authentication or query unintended nodes.

Suggested image name: `xml-injection-lab` (alternatives: `xpath-injection-lab`, `xml-playground-injection`).

## Run with Docker

```bash
# From this directory
docker build -t xml-injection-lab .

# Run on port 3000
docker run --rm -p 3000:3000 xml-injection-lab
```

Open `http://localhost:3000` in your browser.

## App Overview

- `/` (vulnerable): Login form that constructs an XPath predicate with your `username` and `password` inputs.
- `/login` (vulnerable POST): Performs the dynamic XPath and returns success if any node matches.
- `/safe-login` (safe POST): Avoids building XPath from user input; iterates nodes and compares values directly.

Files:
- `server.js`: Express server with vulnerable and safe routes.
- `views/index.ejs`: Form and vulnerable usage.
- `views/safe.ejs`: Safe result page.
- `data/users.xml`: Sample XML user store.

## What is XML/XPath Injection and why this is vulnerable

XML/XPath Injection happens when untrusted input is used to build an XML query or structure without proper validation or parameterization. Much like SQL injection, an attacker can break out of intended predicates and inject new conditions to alter the query logic.

In this app, the vulnerable route builds an XPath like:

```
//user[username='USER' and password='PASS']
```

When `USER` or `PASS` contains quotes and boolean logic (e.g., `' or '1'='1`), the predicate can be altered to always be true.

## How to exploit (for learning)

1. Start the container and open `http://localhost:3000`.
2. In the vulnerable form, try:
   - Username: `admin' or '1'='1`
   - Password: `anything`
3. You should see a successful login via the vulnerable path.

Other payloads to try:
- Username: `bob' or contains(password,'b') or 'x'='y`
- Username: `'] | //user[role='admin'] | //*['` (varies by parser)

Hint: Look at `server.js` and find where the XPath string is constructed with `" + username + "` and similar concatenation.

## How to fix (in this app)

- Do not build XPath strings with raw user input.
- Prefer APIs that support parameterization/bound variables. If your XPath engine does not support variables, avoid dynamic XPath and perform lookups by traversing nodes and comparing values (as shown in `/safe-login`).
- Validate and sanitize inputs: whitelist expected characters for usernames/passwords (e.g., `^[a-zA-Z0-9_]{1,32}$`).
- Escape unsafe characters before using them in any query context if dynamic queries cannot be avoided.

## How to prevent (general guidance)

- Treat XML and XPath like you would SQL: parameterize, validate, and avoid string concatenation.
- Disable dangerous XML features when parsing: external entity resolution (XXE), DTDs, XInclude.
- Use a hardened XML parser configuration and avoid evaluating untrusted XPath/XSLT expressions.
- Keep libraries updated and enable safe defaults.
- Enforce least privilege on any back-end actions triggered by query results.

## Cleanup

Stop the container with Ctrl+C (or remove with `docker ps` / `docker stop`).

## Suggested project structure

```
04-XML-Injection-Vulnerable-Docker/
  ├─ Dockerfile
  ├─ package.json
  ├─ server.js
  ├─ data/
  │   └─ users.xml
  └─ views/
      ├─ index.ejs
      └─ safe.ejs
```


