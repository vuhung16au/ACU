const fs = require("node:fs");
const path = require("node:path");

const stepPauseMs = 900;
const readingPauseMs = 1800;

async function pause(page, duration = stepPauseMs) {
    await page.waitForTimeout(duration);
}

async function main() {
    const { chromium } = require("playwright");
    const baseUrl = process.env.PLAYWRIGHT_BASE_URL || "http://127.0.0.1:5013";
    const artifactDirectory = path.resolve(__dirname, "..", "artifacts");
    const screenshotPath = path.join(artifactDirectory, "playwright-performance-optimization.png");
    const videoPath = path.join(artifactDirectory, "playwright-performance-optimization.webm");

    const browser = await chromium.launch({ headless: true });
    const context = await browser.newContext({
        recordVideo: {
            dir: artifactDirectory,
            size: { width: 1280, height: 720 }
        }
    });

    const page = await context.newPage();

    try {
        console.log("Opening lesson homepage...");
        await page.goto(baseUrl);
        await page.waitForLoadState("networkidle");
        await pause(page, readingPauseMs);

        console.log("Opening optimization lab...");
        await page.goto(`${baseUrl}/OptimizationLab`);
        await page.waitForLoadState("domcontentloaded");
        await pause(page, readingPauseMs);
        await page.locator(".comparison-box").first().scrollIntoViewIfNeeded();
        await pause(page, readingPauseMs);
        await page.locator(".comparison-box").nth(1).scrollIntoViewIfNeeded();
        await pause(page, readingPauseMs);
        await page.locator(".comparison-box").nth(2).scrollIntoViewIfNeeded();
        await pause(page, readingPauseMs);
        await page.locator(".comparison-box").nth(3).scrollIntoViewIfNeeded();
        await pause(page, readingPauseMs);

        console.log("Opening optimized leaderboard...");
        await page.goto(`${baseUrl}/Products`);
        await page.waitForLoadState("domcontentloaded");
        await pause(page, readingPauseMs);
        await page.locator(".data-table").scrollIntoViewIfNeeded();
        await pause(page, readingPauseMs);
        console.log("Moving to next page...");
        await page.goto(`${baseUrl}/Products?pageNumber=2`);
        await page.waitForLoadState("domcontentloaded");
        await pause(page, readingPauseMs);
        await page.locator("pre").scrollIntoViewIfNeeded();
        await pause(page, readingPauseMs);

        console.log("Saving screenshot...");
        await page.screenshot({
            path: screenshotPath,
            fullPage: true
        });
        await pause(page, readingPauseMs);

        const recordedVideo = page.video();
        await page.close();
        await context.close();

        if (recordedVideo) {
            console.log("Saving final video artifact...");
            const recordedVideoPath = await recordedVideo.path();
            fs.copyFileSync(recordedVideoPath, videoPath);
        }
    } finally {
        await browser.close();
    }
}

main().catch((error) => {
    console.error(error);
    process.exitCode = 1;
});
