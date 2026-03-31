const fs = require("node:fs");
const path = require("node:path");
const http = require("node:http");
const { spawn } = require("node:child_process");
const { chromium } = require("playwright");

const projectDir = path.resolve(__dirname, "..");
const artifactsDir = path.join(projectDir, "artifacts");
const outputPath = path.join(artifactsDir, "hello-signalr-demo.webm");
const appUrl = process.env.HELLO_SIGNALR_URL || "http://127.0.0.1:5000";
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
  return spawn(dotnetCommand, ["run", "--urls", appUrl], {
    cwd: projectDir,
    stdio: "inherit",
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

    const recordingBrowser = await chromium.launch({ headless: true });
    const recordingContext = await recordingBrowser.newContext({
      viewport: { width: 1280, height: 720 },
      recordVideo: {
        dir: artifactsDir,
        size: { width: 1280, height: 720 },
      },
    });

    const helperBrowser = await chromium.launch({ headless: true });
    const helperContext = await helperBrowser.newContext({
      viewport: { width: 1280, height: 720 },
    });

    const recordedPage = recordingContext.pages()[0] || (await recordingContext.newPage());
    const helperPage = await helperContext.newPage();
    const recordedVideo = recordedPage.video();

    await recordedPage.goto(appUrl, { waitUntil: "networkidle" });
    await helperPage.goto(appUrl, { waitUntil: "networkidle" });

    await recordedPage.fill("#userInput", "Student A");
    await helperPage.fill("#userInput", "Student B");

    await helperPage.fill("#messageInput", "Hello from the second tab!");
    await helperPage.click("#sendButton");

    await recordedPage.waitForSelector("text=Student B: Hello from the second tab!");
    await recordedPage.waitForTimeout(1200);

    await recordedPage.fill("#messageInput", "SignalR updates both tabs immediately.");
    await recordedPage.click("#sendButton");

    await recordedPage.waitForSelector("text=Student A: SignalR updates both tabs immediately.");
    await recordedPage.waitForTimeout(1800);

    await recordingContext.close();
    await recordingBrowser.close();
    await helperContext.close();
    await helperBrowser.close();

    const recordedPath = await recordedVideo.path();
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
