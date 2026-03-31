const fs = require("fs");
const path = require("path");
const http = require("http");
const { chromium } = require("playwright");

const rootDir = path.resolve(__dirname, "..");
const artifactsDir = path.join(rootDir, "artifacts");
const outputPath = path.join(artifactsDir, "craft-of-ui-split-flap.webm");

function contentType(filePath) {
  const extension = path.extname(filePath).toLowerCase();

  if (extension === ".html") return "text/html; charset=utf-8";
  if (extension === ".js") return "text/javascript; charset=utf-8";
  if (extension === ".css") return "text/css; charset=utf-8";
  if (extension === ".json") return "application/json; charset=utf-8";
  if (extension === ".svg") return "image/svg+xml";

  return "application/octet-stream";
}

function createServer() {
  return http.createServer((request, response) => {
    const requestPath = request.url === "/" ? "/index.html" : request.url;
    const filePath = path.join(rootDir, decodeURIComponent(requestPath));

    if (!filePath.startsWith(rootDir)) {
      response.writeHead(403);
      response.end("Forbidden");
      return;
    }

    fs.readFile(filePath, (error, content) => {
      if (error) {
        response.writeHead(error.code === "ENOENT" ? 404 : 500);
        response.end(error.code === "ENOENT" ? "Not found" : "Server error");
        return;
      }

      response.writeHead(200, { "Content-Type": contentType(filePath) });
      response.end(content);
    });
  });
}

async function record() {
  fs.mkdirSync(artifactsDir, { recursive: true });

  const server = createServer();

  await new Promise((resolve) => {
    server.listen(4173, "127.0.0.1", resolve);
  });

  const browser = await chromium.launch({ headless: true });
  const context = await browser.newContext({
    viewport: { width: 1280, height: 720 },
    recordVideo: {
      dir: artifactsDir,
      size: { width: 1280, height: 720 },
    },
    colorScheme: "dark",
  });

  const page = await context.newPage();
  const video = page.video();

  try {
    await page.goto("http://127.0.0.1:4173/index.html", { waitUntil: "networkidle" });
    await page.waitForTimeout(1200);
    const animationDuration = await page.evaluate(() => window.playBoardAnimation());
    await page.waitForTimeout(Math.max(9500, Math.ceil(animationDuration * 1000)));
  } finally {
    await page.close();
    const videoPath = await video.path();
    await context.close();
    await browser.close();
    await new Promise((resolve, reject) => {
      server.close((error) => {
        if (error) reject(error);
        else resolve();
      });
    });

    if (fs.existsSync(outputPath)) {
      fs.unlinkSync(outputPath);
    }

    fs.renameSync(videoPath, outputPath);
  }

  console.log(`Saved recording to ${outputPath}`);
}

record().catch((error) => {
  console.error(error);
  process.exitCode = 1;
});
