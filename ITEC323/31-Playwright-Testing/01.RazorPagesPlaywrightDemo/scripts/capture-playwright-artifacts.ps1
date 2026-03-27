$ErrorActionPreference = "Stop"

$ScriptDirectory = Split-Path -Parent $MyInvocation.MyCommand.Path
$ProjectDirectory = Split-Path -Parent $ScriptDirectory
$TestDirectory = Join-Path $ProjectDirectory "tests/01.RazorPagesPlaywrightDemo.Tests"
$ArtifactDirectory = Join-Path $ProjectDirectory "artifacts"

function Get-DotNetCommand {
    if ($env:DOTNET_CMD) {
        return $env:DOTNET_CMD
    }

    $dotnetCommand = Get-Command dotnet -ErrorAction SilentlyContinue
    if ($dotnetCommand) {
        return $dotnetCommand.Source
    }

    $windowsDotNet = "C:\Program Files\dotnet\dotnet.exe"
    if (Test-Path $windowsDotNet) {
        return $windowsDotNet
    }

    throw "dotnet was not found. Install the .NET SDK or set DOTNET_CMD."
}

$env:DOTNET_CMD = Get-DotNetCommand
$env:PLAYWRIGHT_ARTIFACTS_DIR = $ArtifactDirectory

New-Item -ItemType Directory -Force -Path $ArtifactDirectory | Out-Null

Write-Host "Using dotnet: $env:DOTNET_CMD"
Write-Host "Artifacts folder: $ArtifactDirectory"

& $env:DOTNET_CMD build (Join-Path $TestDirectory "01.RazorPagesPlaywrightDemo.Tests.csproj")

$playwrightPs1 = Join-Path $TestDirectory "bin/Debug/net10.0/playwright.ps1"

if (-not (Test-Path $playwrightPs1)) {
    throw "Playwright install script was not found. Build the test project first."
}

Write-Host "Installing Playwright browsers..."
pwsh $playwrightPs1 install

Write-Host "Running Playwright automation test..."
& $env:DOTNET_CMD test (Join-Path $TestDirectory "01.RazorPagesPlaywrightDemo.Tests.csproj") --no-build

Write-Host "Created files:"
Write-Host " - $(Join-Path $ArtifactDirectory 'playwright-form-demo.png')"
Write-Host " - $(Join-Path $ArtifactDirectory 'playwright-form-demo.webm')"
if (Test-Path (Join-Path $ArtifactDirectory "playwright-form-demo.gif")) {
    Write-Host " - $(Join-Path $ArtifactDirectory 'playwright-form-demo.gif')"
}
else {
    Write-Host " - GIF not created because ffmpeg is not installed."
}
