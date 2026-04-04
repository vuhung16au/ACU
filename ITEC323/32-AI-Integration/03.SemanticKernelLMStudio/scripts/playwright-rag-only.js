const path = require("node:path");
const { artifactsDir, runRecording } = require("./playwright-common");

const screenshotPath = path.join(artifactsDir, "semantic-kernel-lm-studio-rag-only.png");
const videoPath = path.join(artifactsDir, "semantic-kernel-lm-studio-rag-only.webm");
const summaryPath = path.join(artifactsDir, "semantic-kernel-lm-studio-rag-only-summary.json");

runRecording({
    screenshotPaths: [screenshotPath],
    videoPath,
    summaryPath,
    steps: [
        {
            title: "RAG Response With Sources",
            endpoint: "/api/rag",
            method: "POST",
            payload: {
                question: "What does LM Studio provide for local development?",
                topK: 3,
                maxTokens: 180
            },
            screenshotPath
        }
    ]
}).catch((error) => {
    console.error(error);
    process.exitCode = 1;
});
