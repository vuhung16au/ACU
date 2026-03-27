#!/usr/bin/env sh

set -eu

SCRIPT_DIR="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"
PROJECT_DIR="$(cd "${SCRIPT_DIR}/.." && pwd)"
TEST_DIR="${PROJECT_DIR}/tests/01.RazorPagesPlaywrightDemo.Tests"
ARTIFACT_DIR="${PROJECT_DIR}/artifacts"

find_dotnet() {
    if [ -n "${DOTNET_CMD:-}" ]; then
        printf '%s\n' "${DOTNET_CMD}"
        return
    fi

    if command -v dotnet >/dev/null 2>&1; then
        command -v dotnet
        return
    fi

    if [ -x "/usr/local/share/dotnet/dotnet" ]; then
        printf '%s\n' "/usr/local/share/dotnet/dotnet"
        return
    fi

    if [ -x "/usr/share/dotnet/dotnet" ]; then
        printf '%s\n' "/usr/share/dotnet/dotnet"
        return
    fi

    printf 'dotnet was not found. Install the .NET SDK or set DOTNET_CMD.\n' >&2
    exit 1
}

DOTNET_CMD="$(find_dotnet)"
export DOTNET_CMD
export PLAYWRIGHT_ARTIFACTS_DIR="${ARTIFACT_DIR}"

mkdir -p "${ARTIFACT_DIR}"

echo "Using dotnet: ${DOTNET_CMD}"
echo "Artifacts folder: ${ARTIFACT_DIR}"

"${DOTNET_CMD}" build "${TEST_DIR}/01.RazorPagesPlaywrightDemo.Tests.csproj"

PLAYWRIGHT_PS1="${TEST_DIR}/bin/Debug/net10.0/playwright.ps1"
PLAYWRIGHT_SH="${TEST_DIR}/bin/Debug/net10.0/playwright.sh"
PLAYWRIGHT_OUTPUT_DIR="${TEST_DIR}/bin/Debug/net10.0/.playwright"
PLAYWRIGHT_NODE_DIR="${PLAYWRIGHT_OUTPUT_DIR}/node"
PLAYWRIGHT_PACKAGE_DIR="${PLAYWRIGHT_OUTPUT_DIR}/package"
PLAYWRIGHT_CLI_JS="${PLAYWRIGHT_PACKAGE_DIR}/cli.js"

find_playwright_node() {
    if [ -x "${PLAYWRIGHT_NODE_DIR}/darwin-arm64/node" ]; then
        printf '%s\n' "${PLAYWRIGHT_NODE_DIR}/darwin-arm64/node"
        return
    fi

    if [ -x "${PLAYWRIGHT_NODE_DIR}/darwin-x64/node" ]; then
        printf '%s\n' "${PLAYWRIGHT_NODE_DIR}/darwin-x64/node"
        return
    fi

    if [ -x "${PLAYWRIGHT_NODE_DIR}/linux-arm64/node" ]; then
        printf '%s\n' "${PLAYWRIGHT_NODE_DIR}/linux-arm64/node"
        return
    fi

    if [ -x "${PLAYWRIGHT_NODE_DIR}/linux-x64/node" ]; then
        printf '%s\n' "${PLAYWRIGHT_NODE_DIR}/linux-x64/node"
        return
    fi

    return 1
}

if [ -f "${PLAYWRIGHT_PS1}" ]; then
    echo "Playwright install script found at: ${PLAYWRIGHT_PS1}"
fi

if [ -f "${PLAYWRIGHT_SH}" ]; then
    echo "Playwright shell install script found at: ${PLAYWRIGHT_SH}"
fi

if command -v pwsh >/dev/null 2>&1 && [ -f "${PLAYWRIGHT_PS1}" ]; then
    echo "Installing Playwright browsers with PowerShell..."
    pwsh "${PLAYWRIGHT_PS1}" install
elif [ -f "${PLAYWRIGHT_SH}" ]; then
    echo "Installing Playwright browsers with shell script..."
    sh "${PLAYWRIGHT_SH}" install
elif [ -f "${PLAYWRIGHT_CLI_JS}" ]; then
    PLAYWRIGHT_NODE="$(find_playwright_node || true)"

    if [ -z "${PLAYWRIGHT_NODE}" ]; then
        echo "Playwright CLI was found, but a bundled Node runtime was not found." >&2
        echo "Expected to find a Node binary under: ${PLAYWRIGHT_NODE_DIR}" >&2
        exit 1
    fi

    echo "Playwright CLI found at: ${PLAYWRIGHT_CLI_JS}"
    echo "Bundled Playwright Node runtime found at: ${PLAYWRIGHT_NODE}"
    echo "Installing Playwright browsers with bundled Node..."
    "${PLAYWRIGHT_NODE}" "${PLAYWRIGHT_CLI_JS}" install
else
    echo "Playwright install script was not found in the build output." >&2
    echo "Expected one of these files:" >&2
    echo "  ${PLAYWRIGHT_PS1}" >&2
    echo "  ${PLAYWRIGHT_SH}" >&2
    echo "  ${PLAYWRIGHT_CLI_JS}" >&2
    echo "Build the test project with:" >&2
    echo "  ${DOTNET_CMD} build ${TEST_DIR}/01.RazorPagesPlaywrightDemo.Tests.csproj" >&2
    exit 1
fi

echo "Running Playwright automation test..."
"${DOTNET_CMD}" test "${TEST_DIR}/01.RazorPagesPlaywrightDemo.Tests.csproj" --no-build

echo "Created files:"
echo " - ${ARTIFACT_DIR}/playwright-form-demo.png"
echo " - ${ARTIFACT_DIR}/playwright-form-demo.webm"
if [ -f "${ARTIFACT_DIR}/playwright-form-demo.gif" ]; then
    echo " - ${ARTIFACT_DIR}/playwright-form-demo.gif"
else
    echo " - GIF not created because ffmpeg is not installed."
fi
