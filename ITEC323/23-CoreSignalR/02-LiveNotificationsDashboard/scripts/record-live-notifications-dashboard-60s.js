const fs = require("node:fs");
const http = require("node:http");
const path = require("node:path");
const { spawn } = require("node:child_process");
const { chromium } = require("playwright");

const projectDir = path.resolve(__dirname, "..");
const artifactsDir = path.join(projectDir, "artifacts");
const outputPath = path.join(artifactsDir, "live-notifications-dashboard-60s-demo.webm");
const appUrl = process.env.HELLO_SIGNALR_URL || "http://127.0.0.1:5062";
const dotnetCommand = process.env.DOTNET_CMD || "dotnet";
const projectFile = path.join(projectDir, "02-LiveNotificationsDashboard.csproj");

/**
 * Pauses execution to keep the recording readable.
 * @param {number} milliseconds The delay duration.
 * @returns {Promise<void>} A promise that resolves after the delay.
 */
function delay(milliseconds) {
  return new Promise((resolve) => setTimeout(resolve, milliseconds));
}

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
 * Runs a dotnet command and waits for it to finish.
 * @param {string[]} args The dotnet CLI arguments.
 * @returns {Promise<void>} A promise that resolves when the command succeeds.
 */
function runDotnet(args) {
  return new Promise((resolve, reject) => {
    const child = spawn(dotnetCommand, args, {
      cwd: projectDir,
      stdio: "inherit"
    });

    child.on("error", reject);
    child.on("close", (code) => {
      if (code === 0) {
        resolve();
        return;
      }

      reject(new Error(`dotnet ${args.join(" ")} failed with exit code ${code}`));
    });
  });
}

/**
 * Starts the ASP.NET Core project with a fixed local URL.
 * @returns {import("node:child_process").ChildProcess} The running child process.
 */
function startApp() {
  return spawn(dotnetCommand, ["run", "--no-build", "--no-launch-profile", "--project", projectFile, "--urls", appUrl], {
    cwd: projectDir,
    stdio: "inherit",
    env: {
      ...process.env,
      ASPNETCORE_ENVIRONMENT: "Development"
    }
  });
}

/**
 * Deletes the target output file if a previous recording already exists.
 */
function removeOldVideo() {
  if (fs.existsSync(outputPath)) {
    fs.unlinkSync(outputPath);
  }
}

/**
 * Waits for a notification title to appear in the live feed.
 * The locator is scoped to the feed so repeated text elsewhere does not
 * trigger Playwright strict-mode errors.
 * @param {import("playwright").Frame} frame The embedded dashboard frame.
 * @param {string} title The notification title to wait for.
 * @returns {Promise<void>} A promise that resolves when the item is visible.
 */
async function waitForNotification(frame, title) {
  await frame
    .locator("#notificationsList .notification-item strong")
    .filter({ hasText: title })
    .first()
    .waitFor();
}

/**
 * Stops the ASP.NET Core child process and waits for it to exit.
 * @param {import("node:child_process").ChildProcess} appProcess The running child process.
 * @returns {Promise<void>} A promise that resolves when the process has ended.
 */
function stopApp(appProcess) {
  return new Promise((resolve) => {
    if (appProcess.exitCode !== null || appProcess.killed) {
      resolve();
      return;
    }

    appProcess.once("close", () => resolve());
    appProcess.kill("SIGTERM");

    setTimeout(() => {
      if (appProcess.exitCode === null) {
        appProcess.kill("SIGKILL");
      }
    }, 3000);
  });
}

/**
 * Builds the recording page that places two live dashboard panels side by side.
 * @param {string} url The app URL shown in both frames.
 * @returns {string} The full HTML document.
 */
function buildShowcasePage(url) {
  return `<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Live Notifications Dashboard Demo</title>
  <style>
    :root {
      color-scheme: light;
      --bg: #eef3f7;
      --panel: #ffffff;
      --border: #dbe4ea;
      --ink: #1f2937;
      --muted: #52606d;
      --accent: #0f766e;
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
        radial-gradient(circle at top left, rgba(15, 118, 110, 0.12), transparent 24%),
        linear-gradient(180deg, #f7fafc 0%, var(--bg) 100%);
    }

    .shell {
      width: min(1560px, calc(100% - 32px));
      margin: 0 auto;
      padding: 18px 0 14px;
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
      margin: 0 0 8px;
      font-size: 42px;
      line-height: 1.02;
    }

    p {
      margin: 0;
      color: var(--muted);
      font-size: 20px;
      line-height: 1.3;
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
      box-shadow: 0 16px 36px rgba(15, 23, 42, 0.08);
      overflow: hidden;
    }

    .frame-label {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 10px 14px;
      border-bottom: 1px solid rgba(219, 228, 234, 0.85);
      font-size: 18px;
      font-weight: 700;
      background: rgba(255, 255, 255, 0.95);
    }

    .frame-label span:last-child {
      color: var(--muted);
      font-size: 14px;
      font-weight: 400;
    }

    iframe {
      display: block;
      width: 100%;
      height: 760px;
      border: 0;
      background: white;
    }
  </style>
</head>
<body>
  <main class="shell">
    <p class="eyebrow">23-CoreSignalR / 02-LiveNotificationsDashboard / 60s Demo</p>
    <h1>Two live notification dashboards side by side</h1>
    <p>Both panels are connected to the same SignalR app so counts and events update together in real time.</p>

    <section class="stage">
      <article class="frame-card">
        <div class="frame-label">
          <span>Dashboard A</span>
          <span>Instructor View</span>
        </div>
        <iframe id="leftFrame" src="${url}" title="Dashboard A"></iframe>
      </article>

      <article class="frame-card">
        <div class="frame-label">
          <span>Dashboard B</span>
          <span>Student View</span>
        </div>
        <iframe id="rightFrame" src="${url}" title="Dashboard B"></iframe>
      </article>
    </section>
  </main>
</body>
</html>`;
}

/**
 * Injects a compact presentation mode into an embedded dashboard frame.
 * @param {import("playwright").Frame} frame The embedded application frame.
 * @returns {Promise<void>} A promise that resolves after the styles are injected.
 */
async function applyCompactFrameStyles(frame) {
  await frame.addStyleTag({
    content: `
      html { font-size: 12px; }
      body { margin-bottom: 0; }
      .container { width: min(100%, calc(100% - 14px)); }
      .site-header .container { padding: 0.55rem 0; }
      .navbar-brand { font-size: 1rem; }
      .site-nav { gap: 0.55rem; }
      .hero { margin: 0.35rem 0 0.9rem; }
      .eyebrow { margin-bottom: 0.35rem; font-size: 0.68rem; }
      .hero h1 { margin-bottom: 0.45rem; font-size: 1.65rem; }
      .lead, .panel-text, .notification-message, .notification-meta, .summary-label {
        font-size: 0.84rem;
        line-height: 1.3;
      }
      .summary-grid { gap: 0.55rem; margin-bottom: 0.85rem; }
      .summary-card { padding: 0.7rem 0.8rem; border-radius: 14px; }
      .summary-value { font-size: 1.4rem; }
      .dashboard-layout { gap: 0.8rem; }
      .panel { padding: 0.9rem; border-radius: 16px; }
      .panel-heading { margin-bottom: 0.7rem; }
      .panel h2 { margin-bottom: 0.2rem; font-size: 1.1rem; }
      .status-badge { padding: 0.35rem 0.65rem; font-size: 0.78rem; }
      .button-grid { gap: 0.6rem; margin-top: 0.7rem; }
      .event-button { padding: 0.7rem 0.8rem; border-radius: 12px; font-size: 0.84rem; }
      .notes-box { margin-top: 0.8rem; padding: 0.8rem 0.8rem 0.65rem; border-radius: 14px; }
      .notes-box h3 { margin-bottom: 0.4rem; font-size: 1rem; }
      .notes-box ul { margin: 0.35rem 0 0; padding-left: 1rem; }
      .notification-list { gap: 0.55rem; }
      .notification-item { padding: 0.75rem 0.8rem; border-radius: 14px; }
      .notification-header { gap: 0.4rem; margin-bottom: 0.3rem; }
      .footer { padding: 0.7rem 0 0.9rem; }
    `
  });
}

/**
 * Creates a small floating banner that explains the current demo step.
 * @param {import("playwright").Page} page The recording page.
 * @returns {Promise<void>} A promise that resolves when the banner is ready.
 */
async function addCaptionOverlay(page) {
  await page.evaluate(() => {
    const overlay = document.createElement("div");
    overlay.id = "demoCaption";
    overlay.textContent = "Starting demo...";
    overlay.style.position = "fixed";
    overlay.style.right = "24px";
    overlay.style.bottom = "20px";
    overlay.style.padding = "10px 14px";
    overlay.style.borderRadius = "999px";
    overlay.style.background = "rgba(15, 118, 110, 0.95)";
    overlay.style.color = "#ffffff";
    overlay.style.fontFamily = 'system-ui, sans-serif';
    overlay.style.fontSize = "18px";
    overlay.style.fontWeight = "700";
    overlay.style.boxShadow = "0 12px 24px rgba(15, 23, 42, 0.18)";
    overlay.style.zIndex = "9999";
    document.body.appendChild(overlay);
  });
}

/**
 * Updates the caption text shown on the recording page.
 * @param {import("playwright").Page} page The recording page.
 * @param {string} message The caption text.
 * @returns {Promise<void>} A promise that resolves after the text changes.
 */
async function setCaption(page, message) {
  await page.evaluate((value) => {
    document.getElementById("demoCaption").textContent = value;
  }, message);
}

/**
 * Clicks one of the demo buttons inside a dashboard frame.
 * @param {import("playwright").Frame} frame The embedded application frame.
 * @param {string} title The button title data attribute.
 * @returns {Promise<void>} A promise that resolves after the button click.
 */
async function clickDemoButton(frame, title) {
  await frame.locator(`button[data-title="${title}"]`).click();
}

async function main() {
  fs.mkdirSync(artifactsDir, { recursive: true });
  removeOldVideo();

  await runDotnet(["build", projectFile, "-v", "minimal"]);
  const appProcess = startApp();
  let browser;
  let context;
  let page;
  let video;

  try {
    await waitForServer(appUrl, 30000);

    browser = await chromium.launch({ headless: true });
    context = await browser.newContext({
      viewport: { width: 1600, height: 980 },
      recordVideo: {
        dir: artifactsDir,
        size: { width: 1600, height: 980 }
      }
    });

    page = await context.newPage();
    video = page.video();

    await page.setContent(buildShowcasePage(appUrl), { waitUntil: "load" });
    await addCaptionOverlay(page);

    const leftFrameHandle = await page.locator("#leftFrame").elementHandle();
    const rightFrameHandle = await page.locator("#rightFrame").elementHandle();
    const leftFrame = await leftFrameHandle.contentFrame();
    const rightFrame = await rightFrameHandle.contentFrame();

    await applyCompactFrameStyles(leftFrame);
    await applyCompactFrameStyles(rightFrame);

    await setCaption(page, "Both dashboards connect to the same SignalR hub.");
    await delay(5000);

    await setCaption(page, "Server-generated events appear automatically every few seconds.");
    await leftFrame.locator("#connectionStatus").filter({ hasText: "Connected" }).waitFor();
    await rightFrame.locator("#connectionStatus").filter({ hasText: "Connected" }).waitFor();
    await delay(8000);

    await setCaption(page, "Dashboard A triggers a manual submission event.");
    await clickDemoButton(leftFrame, "New submission received");
    await waitForNotification(rightFrame, "New submission received");
    await delay(7000);

    await setCaption(page, "Dashboard B triggers a success notification.");
    await clickDemoButton(rightFrame, "Deployment completed");
    await waitForNotification(leftFrame, "Deployment completed");
    await delay(7000);

    await setCaption(page, "Background events continue updating both dashboards.");
    await delay(8000);

    await setCaption(page, "Dashboard A triggers a warning event.");
    await clickDemoButton(leftFrame, "Background job warning");
    await waitForNotification(rightFrame, "Background job warning");
    await delay(7000);

    await setCaption(page, "Notice how the counts and feed stay in sync on both sides.");
    await delay(7000);

    await setCaption(page, "This is a practical SignalR pattern for live dashboards and alerts.");
    await delay(6000);

    await page.close();
    await context.close();
    await browser.close();

    const recordedPath = await video.path();
    fs.renameSync(recordedPath, outputPath);
    console.log(`Saved recording to ${outputPath}`);
  } finally {
    if (page && !page.isClosed()) {
      await page.close().catch(() => {});
    }

    if (context) {
      await context.close().catch(() => {});
    }

    if (browser) {
      await browser.close().catch(() => {});
    }

    await stopApp(appProcess);
  }
}

main().catch((error) => {
  console.error(error);
  process.exitCode = 1;
});
