# Quick Start

## Prerequisites

- .NET 10 SDK
- a modern browser
- internet access for first-time NuGet restore and Playwright package restore

## Run The Razor Pages App

```bash
cd 31-Playwright-Testing/01.RazorPagesPlaywrightDemo
dotnet run
```

Open the local URL shown in the terminal.

## Run The Playwright Test Project

```bash
cd 31-Playwright-Testing/01.RazorPagesPlaywrightDemo/tests/01.RazorPagesPlaywrightDemo.Tests
dotnet restore
dotnet build
dotnet test
```

## Install Playwright Browsers

For full platform-specific instructions, see [the Playwright setup guide](../../docs/Playwright.md).

After the test project has restored packages, install the Playwright browser binaries.

On macOS or Linux, you can use the bundled shell-based Playwright CLI without PowerShell:

```bash
cd 31-Playwright-Testing/01.RazorPagesPlaywrightDemo
sh scripts/capture-playwright-artifacts.sh
```

If you want to install browsers manually, the Mac/Linux shell equivalent is:

```bash
tests/01.RazorPagesPlaywrightDemo.Tests/bin/Debug/net10.0/.playwright/node/darwin-arm64/node \
tests/01.RazorPagesPlaywrightDemo.Tests/bin/Debug/net10.0/.playwright/package/cli.js install
```

You can still use PowerShell if you prefer:

```bash
cd 31-Playwright-Testing/01.RazorPagesPlaywrightDemo/tests/01.RazorPagesPlaywrightDemo.Tests
pwsh bin/Debug/net10.0/playwright.ps1 install
```

On Windows PowerShell:

```bash
pwsh bin/Debug/net10.0/playwright.ps1 install
```

If your environment generated `playwright.sh`, you can also run:

```bash
./bin/Debug/net10.0/playwright.sh install
```

Note: on some machines the build output includes `playwright.ps1` but not `playwright.sh`. This project's `capture-playwright-artifacts.sh` script handles that case by using Playwright's bundled Node runtime and CLI directly.

## Capture Screenshot And Video With One Script

On macOS or Linux, run:

```bash
cd 31-Playwright-Testing/01.RazorPagesPlaywrightDemo
./scripts/capture-playwright-artifacts.sh
```

On Windows PowerShell, run:

```powershell
cd 31-Playwright-Testing/01.RazorPagesPlaywrightDemo
./scripts/capture-playwright-artifacts.ps1
```

The script builds the test project, installs Playwright browsers when needed, runs the automated test, and saves files in the project `artifacts` folder.

## What The Test Does

The automated test:

1. starts the Razor Pages app with `dotnet run`
2. opens the form in Chromium
3. enters sample values
4. submits the form
5. checks that the submitted values appear on screen
6. saves `artifacts/playwright-form-demo.png`
7. saves `artifacts/playwright-form-demo.webm`
8. creates `artifacts/playwright-form-demo.gif` if `ffmpeg` is installed

## Generated Files

Look in this folder after the test runs:

- `artifacts/playwright-form-demo.png`
- `artifacts/playwright-form-demo.webm`
- `artifacts/playwright-form-demo.gif`

## Troubleshooting

If the browser does not start:

- make sure Playwright browsers are installed
- run `dotnet build` before the install command

If the `.gif` file is missing:

- check whether `ffmpeg` is installed
- the test still passes when only the `.webm` file is created
