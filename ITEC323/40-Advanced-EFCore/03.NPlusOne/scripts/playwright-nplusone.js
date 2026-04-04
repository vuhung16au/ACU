const path = require("node:path");

const stepPauseMs = 900;
const readingPauseMs = 1800;

async function pause(page, duration = stepPauseMs) {
    await page.waitForTimeout(duration);
}

async function main() {
    const { chromium } = require("playwright");
    const baseUrl = process.env.PLAYWRIGHT_BASE_URL || "http://127.0.0.1:5011";
    const artifactDirectory = path.resolve(__dirname, "..", "artifacts");
    const screenshotPath = path.join(artifactDirectory, "playwright-nplusone.png");
    const videoPath = path.join(artifactDirectory, "playwright-nplusone.webm");

    const browser = await chromium.launch({ headless: true });
    const context = await browser.newContext({
        recordVideo: {
            dir: artifactDirectory,
            size: { width: 1280, height: 720 }
        }
    });

    const page = await context.newPage();

    try {
        await page.goto(baseUrl);
        await pause(page, readingPauseMs);

        await page.getByRole("link", { name: "Open Query Lab" }).click();
        await pause(page, readingPauseMs);

        await page.locator(".comparison-box-bad").scrollIntoViewIfNeeded();
        await pause(page, readingPauseMs);
        await page.locator(".comparison-box-good").scrollIntoViewIfNeeded();
        await pause(page, readingPauseMs);
        await page.mouse.wheel(0, 900);
        await pause(page, readingPauseMs);

        await page.getByRole("link", { name: "See the improved orders page" }).click();
        await pause(page, readingPauseMs);
        await page.locator(".data-table").scrollIntoViewIfNeeded();
        await pause(page, readingPauseMs);
        await page.locator("pre code").scrollIntoViewIfNeeded();
        await pause(page, readingPauseMs);

        await page.screenshot({
            path: screenshotPath,
            fullPage: true
        });
        await pause(page, readingPauseMs);

        const recordedVideo = page.video();
        await page.close();
        await context.close();

        if (recordedVideo) {
            await recordedVideo.saveAs(videoPath);
        }
    } finally {
        await browser.close();
    }
}

main().catch((error) => {
    console.error(error);
    process.exitCode = 1;
});
