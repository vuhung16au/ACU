const path = require("node:path");
const { artifactsDir, runRecording } = require("./playwright-common");

const screenshotPath = path.join(artifactsDir, "semantic-kernel-lm-studio-chat-only.png");
const videoPath = path.join(artifactsDir, "semantic-kernel-lm-studio-chat-only.webm");
const summaryPath = path.join(artifactsDir, "semantic-kernel-lm-studio-chat-only-summary.json");

runRecording({
    screenshotPaths: [screenshotPath],
    videoPath,
    summaryPath,
    steps: [
        {
            title: "Direct Chat Completion",
            endpoint: "/api/chat",
            method: "POST",
            payload: {
                prompt: "Explain Semantic Kernel in one sentence for beginner developers.",
                maxTokens: 120,
                temperature: 0.4
            },
            screenshotPath
        }
    ]
}).catch((error) => {
    console.error(error);
    process.exitCode = 1;
});
