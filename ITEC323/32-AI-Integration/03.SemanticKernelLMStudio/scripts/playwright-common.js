const fs = require("node:fs");
const path = require("node:path");
const http = require("node:http");
const { spawn } = require("node:child_process");
const { chromium } = require("playwright");

const projectDir = path.resolve(__dirname, "..");
const artifactsDir = path.join(projectDir, "artifacts");
const tempVideoDir = path.join(artifactsDir, "temporary-videos");
const defaultBaseUrl = process.env.PLAYWRIGHT_BASE_URL || "http://127.0.0.1:5000";
const dotnetCommand = process.env.DOTNET_CMD || "/usr/local/share/dotnet/dotnet";
const shouldStartApp = process.env.PLAYWRIGHT_START_APP !== "false";
const viewport = { width: 1440, height: 900 };

function ensureDirectory(directoryPath) {
    fs.mkdirSync(directoryPath, { recursive: true });
}

function removeFiles(filePaths) {
    for (const artifactPath of filePaths) {
        if (fs.existsSync(artifactPath)) {
            fs.unlinkSync(artifactPath);
        }
    }
}

function waitForServer(url, timeoutMs) {
    const deadline = Date.now() + timeoutMs;

    return new Promise((resolve, reject) => {
        const tryConnect = () => {
            http
                .get(url, (response) => {
                    response.resume();
                    resolve();
                })
                .on("error", () => {
                    if (Date.now() >= deadline) {
                        reject(new Error(`Timed out waiting for ${url}`));
                        return;
                    }

                    setTimeout(tryConnect, 500);
                });
        };

        tryConnect();
    });
}

function startApp() {
    return spawn(dotnetCommand, ["run", "--project", path.join(projectDir, "03.SemanticKernelLMStudio.csproj")], {
        cwd: projectDir,
        stdio: "inherit"
    });
}

async function pause(page, duration = 1200) {
    await page.waitForTimeout(duration);
}

async function renderApiSummary(page, title, endpoint, payload, responseJson, screenshotPath) {
    await page.setContent(
        `<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>${title}</title>
  <style>
    :root {
      color-scheme: light;
      --bg: #f6f4ee;
      --card: #fffdf8;
      --ink: #172229;
      --muted: #56656c;
      --accent: #0b7285;
      --accent-soft: #d7eef2;
      --border: #d9ddd1;
      --shadow: 0 20px 50px rgba(23, 34, 41, 0.12);
      --code-bg: #102127;
      --code-ink: #f4f7f8;
    }

    * { box-sizing: border-box; }
    body {
      margin: 0;
      font-family: "Avenir Next", "Segoe UI", sans-serif;
      background:
        radial-gradient(circle at top left, rgba(11, 114, 133, 0.16), transparent 32%),
        linear-gradient(135deg, #f6f4ee 0%, #f3efe4 100%);
      color: var(--ink);
    }

    .frame {
      padding: 40px;
    }

    .hero {
      display: grid;
      grid-template-columns: 1.1fr 0.9fr;
      gap: 24px;
      align-items: start;
    }

    .panel {
      background: rgba(255, 253, 248, 0.92);
      border: 1px solid var(--border);
      border-radius: 24px;
      box-shadow: var(--shadow);
      padding: 28px;
      backdrop-filter: blur(10px);
    }

    .eyebrow {
      display: inline-flex;
      align-items: center;
      gap: 8px;
      padding: 8px 14px;
      border-radius: 999px;
      background: var(--accent-soft);
      color: var(--accent);
      font-size: 14px;
      font-weight: 700;
      letter-spacing: 0.04em;
      text-transform: uppercase;
    }

    h1 {
      margin: 18px 0 12px;
      font-size: 42px;
      line-height: 1.08;
    }

    p {
      margin: 0;
      color: var(--muted);
      font-size: 18px;
      line-height: 1.6;
    }

    .meta {
      margin-top: 24px;
      display: grid;
      gap: 14px;
    }

    .meta-row {
      display: flex;
      justify-content: space-between;
      gap: 16px;
      padding: 14px 16px;
      border-radius: 16px;
      background: #f8faf8;
      border: 1px solid var(--border);
      font-size: 15px;
    }

    .meta-row strong {
      color: var(--ink);
    }

    .code-card {
      background: var(--code-bg);
      color: var(--code-ink);
      border-radius: 22px;
      padding: 22px;
      min-height: 680px;
      display: grid;
      grid-template-rows: auto auto 1fr;
      gap: 18px;
    }

    .dots {
      display: flex;
      gap: 8px;
    }

    .dots span {
      width: 12px;
      height: 12px;
      border-radius: 50%;
      display: inline-block;
    }

    .dots span:nth-child(1) { background: #ff6b6b; }
    .dots span:nth-child(2) { background: #ffd43b; }
    .dots span:nth-child(3) { background: #51cf66; }

    .section-label {
      font-size: 14px;
      letter-spacing: 0.08em;
      text-transform: uppercase;
      color: #b8ced3;
    }

    pre {
      margin: 0;
      white-space: pre-wrap;
      word-break: break-word;
      font-size: 15px;
      line-height: 1.55;
      overflow: hidden;
    }

    .footer {
      margin-top: 24px;
      display: flex;
      justify-content: space-between;
      gap: 20px;
      color: var(--muted);
      font-size: 14px;
    }
  </style>
</head>
<body>
  <main class="frame">
    <section class="hero">
      <article class="panel">
        <div class="eyebrow">Playwright Recording</div>
        <h1>${title}</h1>
        <p>
          This capture shows the ASP.NET Core demo talking to a local LM Studio server.
          The panel on the right renders the live JSON returned by the API.
        </p>
        <div class="meta">
          <div class="meta-row"><strong>Endpoint</strong><span>${endpoint}</span></div>
          <div class="meta-row"><strong>Base URL</strong><span>${defaultBaseUrl}</span></div>
          <div class="meta-row"><strong>Request payload</strong><span>${payload.replace(/</g, "&lt;")}</span></div>
        </div>
        <div class="footer">
          <span>Project: 03.SemanticKernelLMStudio</span>
          <span>Artifacts saved under /artifacts</span>
        </div>
      </article>
      <article class="code-card">
        <div class="dots"><span></span><span></span><span></span></div>
        <div class="section-label">Response Preview</div>
        <pre>${responseJson.replace(/&/g, "&amp;").replace(/</g, "&lt;")}</pre>
      </article>
    </section>
  </main>
</body>
</html>`,
        { waitUntil: "load" }
    );

    await pause(page);
    await page.screenshot({ path: screenshotPath, fullPage: true });
    await pause(page, 1400);
}

async function fetchJson(page, url, method = "GET", payload = null) {
    return await page.evaluate(async ({ requestUrl, requestMethod, requestPayload }) => {
        const response = await fetch(requestUrl, {
            method: requestMethod,
            headers: requestPayload ? { "Content-Type": "application/json" } : undefined,
            body: requestPayload ? JSON.stringify(requestPayload) : undefined
        });

        const text = await response.text();
        let json;

        try {
            json = JSON.parse(text);
        } catch {
            json = { raw: text };
        }

        return {
            ok: response.ok,
            status: response.status,
            json
        };
    }, {
        requestUrl: url,
        requestMethod: method,
        requestPayload: payload
    });
}

async function runRecording(config) {
    ensureDirectory(artifactsDir);
    ensureDirectory(tempVideoDir);
    removeFiles([...config.screenshotPaths, config.videoPath, config.summaryPath]);

    let appProcess = null;

    if (shouldStartApp) {
        appProcess = startApp();
    }

    try {
        await waitForServer(`${defaultBaseUrl}/`, 30000);

        const browser = await chromium.launch({ headless: true });
        const context = await browser.newContext({
            viewport,
            recordVideo: {
                dir: tempVideoDir,
                size: viewport
            }
        });

        const page = await context.newPage();
        const recordedVideo = page.video();
        const results = [];

        await page.goto(`${defaultBaseUrl}/`, { waitUntil: "networkidle" });

        for (const step of config.steps) {
            const response = await fetchJson(page, `${defaultBaseUrl}${step.endpoint}`, step.method, step.payload);

            await renderApiSummary(
                page,
                step.title,
                `${step.method} ${step.endpoint}`,
                JSON.stringify(step.payload ?? {}),
                JSON.stringify(response.json, null, 2),
                step.screenshotPath
            );

            results.push({
                title: step.title,
                endpoint: step.endpoint,
                method: step.method,
                payload: step.payload,
                response
            });
        }

        if (!recordedVideo) {
            throw new Error("Playwright did not attach a video recorder to the page.");
        }

        await page.close();
        await recordedVideo.saveAs(config.videoPath);
        await context.close();
        await browser.close();

        fs.writeFileSync(
            config.summaryPath,
            JSON.stringify(
                {
                    baseUrl: defaultBaseUrl,
                    generatedAtUtc: new Date().toISOString(),
                    steps: results,
                    artifacts: {
                        screenshots: config.screenshotPaths,
                        video: config.videoPath
                    }
                },
                null,
                2
            )
        );

        console.log(`Saved artifacts to ${artifactsDir}`);
        console.log(`Saved video to ${config.videoPath}`);
    } finally {
        if (appProcess) {
            appProcess.kill("SIGTERM");
        }
    }
}

module.exports = {
    artifactsDir,
    defaultBaseUrl,
    runRecording
};
