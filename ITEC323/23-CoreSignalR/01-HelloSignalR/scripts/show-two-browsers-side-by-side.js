const fs = require("node:fs");
const http = require("node:http");
const path = require("node:path");
const { spawn } = require("node:child_process");
const { chromium } = require("playwright");

const projectDir = path.resolve(__dirname, "..");
const artifactsDir = path.join(projectDir, "artifacts");
const outputPath = path.join(artifactsDir, "hello-signalr-two-embedded-chat-panels.webm");
const appUrl = process.env.HELLO_SIGNALR_URL || "http://127.0.0.1:5056";
const dotnetCommand = process.env.DOTNET_CMD || "dotnet";

/**
 * Waits until the local ASP.NET Core app responds over HTTP.
 * @param {string} url The app URL to poll.
 * @param {number} timeoutMs The maximum wait time.
 * @returns {Promise<void>} A promise that resolves when the app is reachable.
 */
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

/**
 * Starts the ASP.NET Core project on a fixed local URL.
 * @returns {import("node:child_process").ChildProcess} The running child process.
 */
function startApp() {
  return spawn(dotnetCommand, ["run", "--no-launch-profile", "--urls", appUrl], {
    cwd: projectDir,
    stdio: "inherit"
  });
}

/**
 * Pauses execution so the recording remains readable.
 * @param {number} milliseconds The delay duration.
 * @returns {Promise<void>} A promise that resolves after the delay.
 */
function delay(milliseconds) {
  return new Promise((resolve) => setTimeout(resolve, milliseconds));
}

/**
 * Removes the previous video if it already exists.
 */
function removeOldVideo() {
  if (fs.existsSync(outputPath)) {
    fs.unlinkSync(outputPath);
  }
}

/**
 * Builds the recording page that places two live app views side by side.
 * @param {string} url The app URL shown in both frames.
 * @returns {string} The full HTML document.
 */
function buildShowcasePage(url) {
  return `<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Hello SignalR Side By Side</title>
  <style>
    :root {
      color-scheme: light;
      --bg: #f6f1e8;
      --panel: #fffdf8;
      --border: #d8ccb7;
      --ink: #1f2933;
      --muted: #52606d;
      --accent: #b44d12;
    }

    * {
      box-sizing: border-box;
    }

    body {
      margin: 0;
      min-height: 100vh;
      font-family: Georgia, "Times New Roman", serif;
      color: var(--ink);
      background:
        radial-gradient(circle at top left, rgba(180, 77, 18, 0.16), transparent 24%),
        linear-gradient(180deg, #fbf7ef 0%, var(--bg) 100%);
    }

    .shell {
      width: min(1480px, calc(100% - 32px));
      margin: 0 auto;
      padding: 18px 0 16px;
    }

    .heading {
      margin-bottom: 12px;
    }

    .eyebrow {
      margin: 0 0 8px;
      color: var(--accent);
      font-size: 13px;
      font-weight: 700;
      letter-spacing: 0.08em;
      text-transform: uppercase;
    }

    h1 {
      margin: 0 0 6px;
      font-size: 42px;
      line-height: 1.02;
    }

    p {
      margin: 0;
      color: var(--muted);
      font-size: 20px;
      line-height: 1.35;
    }

    .stage {
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 18px;
      margin-top: 16px;
    }

    .frame-card {
      background: var(--panel);
      border: 1px solid var(--border);
      border-radius: 20px;
      box-shadow: 0 20px 45px rgba(31, 41, 51, 0.08);
      overflow: hidden;
    }

    .frame-label {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 10px 14px;
      border-bottom: 1px solid rgba(216, 204, 183, 0.85);
      background: rgba(255, 253, 248, 0.95);
      font-size: 18px;
      font-weight: 700;
    }

    .frame-label span:last-child {
      color: var(--muted);
      font-size: 14px;
      font-weight: 400;
    }

    iframe {
      display: block;
      width: 100%;
      height: 700px;
      border: 0;
      background: white;
    }
  </style>
</head>
<body>
  <main class="shell">
    <section class="heading">
      <p class="eyebrow">23-CoreSignalR / Recorded Demo</p>
      <h1>Two SignalR chat clients side by side</h1>
      <p>Both panels are connected live to the same ASP.NET Core SignalR app.</p>
    </section>

    <section class="stage">
      <article class="frame-card">
        <div class="frame-label">
          <span>Browser A</span>
          <span>Student A</span>
        </div>
        <iframe id="leftFrame" src="${url}" title="Browser A"></iframe>
      </article>

      <article class="frame-card">
        <div class="frame-label">
          <span>Browser B</span>
          <span>Student B</span>
        </div>
        <iframe id="rightFrame" src="${url}" title="Browser B"></iframe>
      </article>
    </section>
  </main>
</body>
</html>`;
}

/**
 * Injects a compact presentation mode into an embedded app frame so the
 * chat messages remain visible during the recording.
 * @param {import("playwright").Frame} frame The embedded application frame.
 * @returns {Promise<void>} A promise that resolves after the styles are injected.
 */
async function applyCompactFrameStyles(frame) {
  await frame.addStyleTag({
    content: `
      html {
        font-size: 12px;
      }

      body {
        margin-bottom: 0;
      }

      .container {
        width: min(100%, calc(100% - 16px));
      }

      .site-header .container {
        padding: 0.6rem 0;
      }

      .navbar-brand {
        font-size: 1.05rem;
      }

      .site-nav {
        gap: 0.65rem;
      }

      .hero {
        margin: 0.35rem 0 1rem;
      }

      .hero h1 {
        font-size: 2rem;
        margin-bottom: 0.55rem;
      }

      .eyebrow {
        margin-bottom: 0.4rem;
        font-size: 0.72rem;
      }

      .lead,
      .panel-text,
      .status,
      .messages li {
        font-size: 0.92rem;
        line-height: 1.35;
      }

      .chat-layout {
        gap: 0.9rem;
      }

      .panel {
        padding: 1rem;
        border-radius: 16px;
      }

      .panel h2 {
        font-size: 1.25rem;
        margin-bottom: 0.4rem;
      }

      .field-group {
        margin-top: 0.65rem;
      }

      .field-group input {
        padding: 0.55rem 0.8rem;
      }

      .actions {
        margin-top: 0.8rem;
        gap: 0.6rem;
      }

      #sendButton {
        padding: 0.6rem 0.95rem;
        font-size: 0.95rem;
      }

      .messages {
        margin-top: 0.65rem;
        padding-left: 1rem;
      }

      .messages li + li {
        margin-top: 0.35rem;
      }

      .footer {
        padding: 0.8rem 0 1rem;
      }
    `
  });
}

async function main() {
  fs.mkdirSync(artifactsDir, { recursive: true });
  removeOldVideo();

  const appProcess = startApp();

  try {
    await waitForServer(appUrl, 30000);

    const browser = await chromium.launch({ headless: true });
    const context = await browser.newContext({
      viewport: { width: 1500, height: 980 },
      recordVideo: {
        dir: artifactsDir,
        size: { width: 1500, height: 980 }
      }
    });

    const page = await context.newPage();
    const video = page.video();
    const showcaseHtml = buildShowcasePage(appUrl);

    await page.setContent(showcaseHtml, { waitUntil: "load" });

    const leftFrameHandle = await page.locator("#leftFrame").elementHandle();
    const rightFrameHandle = await page.locator("#rightFrame").elementHandle();

    const leftFrame = await leftFrameHandle.contentFrame();
    const rightFrame = await rightFrameHandle.contentFrame();

    await applyCompactFrameStyles(leftFrame);
    await applyCompactFrameStyles(rightFrame);

    await leftFrame.locator("#userInput").fill("Student A");
    await rightFrame.locator("#userInput").fill("Student B");
    await delay(900);

    await leftFrame.locator("#messageInput").fill("Hello from Student A.");
    await leftFrame.locator("#sendButton").click();
    await rightFrame.locator("#messagesList").locator("text=Student A: Hello from Student A.").waitFor();
    await delay(1400);

    await rightFrame.locator("#messageInput").fill("Hello from Student B.");
    await rightFrame.locator("#sendButton").click();
    await leftFrame.locator("#messagesList").locator("text=Student B: Hello from Student B.").waitFor();
    await delay(2600);

    await page.close();
    await context.close();
    await browser.close();

    const recordedPath = await video.path();
    fs.renameSync(recordedPath, outputPath);
    console.log(`Saved recording to ${outputPath}`);
  } finally {
    appProcess.kill("SIGTERM");
  }
}

main().catch((error) => {
  console.error(error);
  process.exitCode = 1;
});
