const fs = require("node:fs");
const path = require("node:path");

const stepPauseMs = 900;
const readingPauseMs = 1800;

async function pause(page, duration = stepPauseMs) {
    await page.waitForTimeout(duration);
}

async function main() {
    const { chromium } = require("playwright");
    const baseUrl = process.env.PLAYWRIGHT_BASE_URL || "http://127.0.0.1:5008";
    const artifactDirectory = path.resolve(__dirname, "..", "artifacts");
    const temporaryVideoDirectory = path.join(artifactDirectory, "temporary-videos");
    const screenshotPath = path.join(artifactDirectory, "playwright-concurrency.png");
    const videoPath = path.join(artifactDirectory, "playwright-concurrency.webm");

    fs.mkdirSync(artifactDirectory, { recursive: true });
    fs.mkdirSync(temporaryVideoDirectory, { recursive: true });

    const browser = await chromium.launch({ headless: true });
    const context = await browser.newContext({
        recordVideo: {
            dir: temporaryVideoDirectory,
            size: { width: 1280, height: 720 }
        }
    });

    const pageA = await context.newPage();
    const pageB = await context.newPage();

    try {
        await pageA.goto(`${baseUrl}/Products`);
        await pause(pageA);
        await pageB.goto(`${baseUrl}/Products`);
        await pause(pageB);

        await pageA.getByRole("link", { name: "Edit Concurrency Drill Product" }).click();
        await pause(pageA, readingPauseMs);
        await pageB.getByRole("link", { name: "Edit Concurrency Drill Product" }).click();
        await pause(pageB, readingPauseMs);

        await pageA.getByLabel("Price").fill("199.99");
        await pause(pageA);
        await pageA.getByLabel("Notes").fill("Updated from browser A.");
        await pause(pageA);
        await pageA.getByRole("button", { name: "Save Changes" }).click();
        await pageA.getByText("Editable products").waitFor();
        await pause(pageA, readingPauseMs);

        await pageB.bringToFront();
        await pause(pageB);
        await pageB.getByLabel("Price").fill("149.99");
        await pause(pageB);
        await pageB.getByLabel("Notes").fill("Updated from browser B after browser A.");
        await pause(pageB);
        await pageB.getByRole("button", { name: "Save Changes" }).click();
        await pageB.waitForLoadState("networkidle");

        const conflictBanner = pageB.getByText("Concurrency conflict detected");
        const conflictMessage = pageB.getByText("Another user saved newer values for this product.");

        if (await conflictBanner.count()) {
            await conflictBanner.waitFor();
        } else {
            await conflictMessage.waitFor();
        }

        await pause(pageB, readingPauseMs);

        await pageB.locator(".conflict-banner").scrollIntoViewIfNeeded();
        await pause(pageB);
        await pageB.locator(".panel").nth(1).scrollIntoViewIfNeeded();
        await pause(pageB, readingPauseMs);
        await pageB.getByRole("button", { name: "Refresh Current Values" }).scrollIntoViewIfNeeded();
        await pause(pageB, readingPauseMs);

        await pageB.screenshot({ path: screenshotPath, fullPage: true });
        await pause(pageB, readingPauseMs);

        const conflictVideo = pageB.video();

        if (conflictVideo) {
            await pageA.close();
            await pageB.close();
            await context.close();
            await conflictVideo.saveAs(videoPath);
        } else {
            await pageA.close();
            await pageB.close();
            await context.close();
        }
    } finally {
        await browser.close();
    }
}

main().catch((error) => {
    console.error(error);
    process.exitCode = 1;
});
