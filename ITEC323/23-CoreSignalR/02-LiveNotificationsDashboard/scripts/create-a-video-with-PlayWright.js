const fs = require("node:fs");
const path = require("node:path");
const http = require("node:http");
const { spawn } = require("node:child_process");
const { chromium } = require("playwright");

const projectDir = path.resolve(__dirname, "..");
const artifactsDir = path.join(projectDir, "artifacts");
const outputPath = path.join(artifactsDir, "live-notifications-dashboard-demo.webm");
const appUrl = process.env.HELLO_SIGNALR_URL || "http://127.0.0.1:5062";
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
 * Starts the ASP.NET Core project with a fixed local URL.
 * @returns {import("node:child_process").ChildProcess} The running child process.
 */
function startApp() {
  return spawn(dotnetCommand, ["run", "--no-launch-profile", "--urls", appUrl], {
    cwd: projectDir,
    stdio: "inherit"
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

async function main() {
  fs.mkdirSync(artifactsDir, { recursive: true });
  removeOldVideo();

  const appProcess = startApp();

  try {
    await waitForServer(appUrl, 30000);

    const browser = await chromium.launch({ headless: true });
    const context = await browser.newContext({
      viewport: { width: 1440, height: 900 },
      recordVideo: {
        dir: artifactsDir,
        size: { width: 1440, height: 900 }
      }
    });

    const page = await context.newPage();
    const video = page.video();

    await page.goto(appUrl, { waitUntil: "networkidle" });
    await page.waitForTimeout(900);

    await page.click("button[data-title='New submission received']");
    await page.waitForSelector("text=New submission received");
    await page.waitForTimeout(1200);

    await page.click("button[data-title='Deployment completed']");
    await page.waitForSelector("text=Deployment completed");
    await page.waitForTimeout(1200);

    await page.click("button[data-title='Background job warning']");
    await page.waitForSelector("text=Background job warning");
    await page.waitForTimeout(2400);

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
