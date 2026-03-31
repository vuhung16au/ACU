const fs = require("node:fs/promises");
const path = require("node:path");
const { execFile } = require("node:child_process");
const { promisify } = require("node:util");
const { chromium } = require("playwright");

const execFileAsync = promisify(execFile);
const viewport = { width: 390, height: 844 };
const durationMs = 5000;
const outputDirectory = path.resolve(__dirname, "..", "artifacts");
const intermediateVideoPath = path.join(outputDirectory, "kinematic-typography.webm");
const finalVideoPath = path.join(outputDirectory, "kinematic-typography.webp");

/**
 * Returns a random integer between min and max.
 * @param {number} min The smallest allowed value.
 * @param {number} max The largest allowed value.
 * @returns {number} A whole number within the inclusive range.
 */
function randomBetween(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

/**
 * Pauses execution for a short period to space out mouse moves.
 * @param {number} milliseconds The delay duration.
 * @returns {Promise<void>} A promise that resolves after the delay.
 */
function delay(milliseconds) {
  return new Promise((resolve) => setTimeout(resolve, milliseconds));
}

/**
 * Converts the Playwright .webm recording into an animated .webp file.
 * @returns {Promise<void>} A promise that resolves when conversion completes.
 */
async function convertVideoToWebp() {
  await execFileAsync("/opt/homebrew/bin/ffmpeg", [
    "-y",
    "-i",
    intermediateVideoPath,
    "-loop",
    "0",
    "-vf",
    `fps=20,scale=${viewport.width}:${viewport.height}:flags=lanczos`,
    finalVideoPath
  ]);
}

/**
 * Records the local typography demo with random mouse movement.
 * @returns {Promise<void>} A promise that resolves when recording completes.
 */
async function main() {
  await fs.mkdir(outputDirectory, { recursive: true });

  const browser = await chromium.launch();
  const context = await browser.newContext({
    viewport,
    recordVideo: {
      dir: outputDirectory,
      size: viewport
    }
  });

  const page = await context.newPage();
  const fileUrl = `file://${path.resolve(__dirname, "..", "kinematic-typography.html")}`;

  await page.goto(fileUrl);
  await page.waitForLoadState("load");

  const startTime = Date.now();
  while (Date.now() - startTime < durationMs) {
    const x = randomBetween(20, viewport.width - 20);
    const y = randomBetween(20, viewport.height - 20);
    const steps = randomBetween(10, 35);

    await page.mouse.move(x, y, { steps });
    await delay(randomBetween(100, 260));
  }

  await context.close();
  await browser.close();

  const recordedFiles = await fs.readdir(outputDirectory);
  const webmFileName = recordedFiles.find(
    (fileName) => fileName.endsWith(".webm") && fileName !== path.basename(intermediateVideoPath)
  );

  if (!webmFileName) {
    throw new Error("Playwright did not produce a .webm recording.");
  }

  await fs.rename(path.join(outputDirectory, webmFileName), intermediateVideoPath);
  await convertVideoToWebp();

  console.log(`Saved recording: ${intermediateVideoPath}`);
  console.log(`Saved animated WebP: ${finalVideoPath}`);
}

main().catch((error) => {
  console.error(error);
  process.exitCode = 1;
});
