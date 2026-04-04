const path = require("node:path");
const { artifactsDir, runRecording } = require("./playwright-common");

const filePaths = {
    appInfoScreenshot: path.join(artifactsDir, "semantic-kernel-lm-studio-home.png"),
    chatScreenshot: path.join(artifactsDir, "semantic-kernel-lm-studio-chat.png"),
    ragScreenshot: path.join(artifactsDir, "semantic-kernel-lm-studio-rag.png"),
    video: path.join(artifactsDir, "semantic-kernel-lm-studio-demo.webm"),
    summary: path.join(artifactsDir, "semantic-kernel-lm-studio-summary.json")
};

runRecording({
    screenshotPaths: [
        filePaths.appInfoScreenshot,
        filePaths.chatScreenshot,
        filePaths.ragScreenshot
    ],
    videoPath: filePaths.video,
    summaryPath: filePaths.summary,
    steps: [
        {
            title: "App Overview",
            endpoint: "/",
            method: "GET",
            payload: null,
            screenshotPath: filePaths.appInfoScreenshot
        },
        {
            title: "Direct Chat Completion",
            endpoint: "/api/chat",
            method: "POST",
            payload: {
                prompt: "Explain Semantic Kernel in one sentence for beginner developers.",
                maxTokens: 120,
                temperature: 0.4
            },
            screenshotPath: filePaths.chatScreenshot
        },
        {
            title: "RAG Response With Sources",
            endpoint: "/api/rag",
            method: "POST",
            payload: {
                question: "What does LM Studio provide for local development?",
                topK: 3,
                maxTokens: 180
            },
            screenshotPath: filePaths.ragScreenshot
        }
    ]
}).catch((error) => {
    console.error(error);
    process.exitCode = 1;
});
