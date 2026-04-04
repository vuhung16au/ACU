#!/usr/bin/env bash

set -euo pipefail

readonly REPO_URL="https://github.com/dotnet/eShop.git"
readonly PINNED_COMMIT="b81ad9557090cc37233b9d1a0a729db7b44b6f14"
readonly APPHOST_PROJECT="src/eShop.AppHost/eShop.AppHost.csproj"
readonly REQUIRED_SDK_MAJOR="10"
readonly TARGET_DIR="${ESHOP_DEMO_DIR:-${TMPDIR:-/tmp}/eshop-demo}"
readonly SKIP_DEV_CERT_CHECK="${ESHOP_SKIP_DEV_CERT_CHECK:-0}"
readonly OPEN_DASHBOARD="${ESHOP_OPEN_DASHBOARD:-1}"

GIT_BIN=""
DOTNET_BIN=""
DOCKER_BIN=""
PLATFORM=""
OPEN_CMD=""

info() {
    printf '[INFO] %s\n' "$1"
}

warn() {
    printf '[WARN] %s\n' "$1"
}

error() {
    printf '[ERROR] %s\n' "$1" >&2
}

print_line() {
    printf '%s\n' "$1"
}

require_command() {
    local command_name="$1"

    if ! command -v "$command_name" >/dev/null 2>&1; then
        error "Required command '$command_name' was not found."
        exit 1
    fi
}

detect_platform() {
    local uname_s
    uname_s="$(uname -s)"

    case "$uname_s" in
        Darwin)
            PLATFORM="macos"
            ;;
        Linux)
            if grep -qi microsoft /proc/version 2>/dev/null; then
                PLATFORM="wsl"
            else
                PLATFORM="linux"
            fi
            ;;
        *)
            PLATFORM="unknown"
            ;;
    esac
}

detect_open_command() {
    case "$PLATFORM" in
        macos)
            if command -v open >/dev/null 2>&1; then
                OPEN_CMD="$(command -v open)"
            fi
            ;;
        linux)
            if command -v xdg-open >/dev/null 2>&1; then
                OPEN_CMD="$(command -v xdg-open)"
            fi
            ;;
        wsl)
            if command -v wslview >/dev/null 2>&1; then
                OPEN_CMD="$(command -v wslview)"
            elif command -v xdg-open >/dev/null 2>&1; then
                OPEN_CMD="$(command -v xdg-open)"
            fi
            ;;
    esac
}

resolve_dotnet() {
    if command -v dotnet >/dev/null 2>&1; then
        DOTNET_BIN="$(command -v dotnet)"
        return
    fi

    if [ -x "/usr/local/share/dotnet/dotnet" ]; then
        DOTNET_BIN="/usr/local/share/dotnet/dotnet"
        return
    fi

    if [ -x "/usr/share/dotnet/dotnet" ]; then
        DOTNET_BIN="/usr/share/dotnet/dotnet"
        return
    fi

    error "Required command 'dotnet' was not found."
    exit 1
}

resolve_docker() {
    if command -v docker >/dev/null 2>&1; then
        DOCKER_BIN="$(command -v docker)"
        return
    fi

    if [ -x "/usr/local/bin/docker" ]; then
        DOCKER_BIN="/usr/local/bin/docker"
        return
    fi

    if [ -x "/Applications/Docker.app/Contents/Resources/bin/docker" ]; then
        DOCKER_BIN="/Applications/Docker.app/Contents/Resources/bin/docker"
        return
    fi

    error "Required command 'docker' was not found."
    exit 1
}

ensure_required_dotnet() {
    resolve_dotnet

    if ! "$DOTNET_BIN" --list-sdks | grep -q "^${REQUIRED_SDK_MAJOR}\."; then
        error ".NET ${REQUIRED_SDK_MAJOR} SDK was not found. Install .NET ${REQUIRED_SDK_MAJOR}, then rerun this script."
        exit 1
    fi
}

ensure_docker_running() {
    resolve_docker

    if ! "$DOCKER_BIN" info >/dev/null 2>&1; then
        case "$PLATFORM" in
            macos|windows|wsl)
                error "Docker is installed but does not appear to be running. Start Docker Desktop, then rerun this script."
                ;;
            linux)
                error "Docker is installed but does not appear to be running. Start the Docker daemon or service, then rerun this script."
                ;;
            *)
                error "Docker is installed but does not appear to be running."
                ;;
        esac
        exit 1
    fi
}

print_dev_cert_help() {
    case "$PLATFORM" in
        macos)
            error "A trusted ASP.NET Core development certificate was not found."
            error "Run: $DOTNET_BIN dev-certs https --trust"
            error "Then rerun this script after macOS trusts the certificate in your login keychain."
            ;;
        wsl)
            error "A usable ASP.NET Core development certificate was not found."
            error "Inside WSL, try: $DOTNET_BIN dev-certs https --trust"
            error "If browser trust still fails, use the HTTP endpoint from Aspire or trust the certificate in the host/browser environment."
            ;;
        linux)
            error "A usable ASP.NET Core development certificate was not found."
            error "Try: $DOTNET_BIN dev-certs https --trust"
            error "If your distro or browser does not trust it automatically, use the HTTP endpoint from Aspire or import the certificate into your local trust store."
            ;;
        *)
            error "A usable ASP.NET Core development certificate was not found."
            error "Try: $DOTNET_BIN dev-certs https --trust"
            ;;
    esac
}

ensure_dev_certificate() {
    if [ "$SKIP_DEV_CERT_CHECK" = "1" ]; then
        warn "Skipping ASP.NET Core development certificate checks because ESHOP_SKIP_DEV_CERT_CHECK=1."
        return
    fi

    if "$DOTNET_BIN" dev-certs https --check >/dev/null 2>&1; then
        return
    fi

    print_dev_cert_help
    exit 1
}

ensure_repo() {
    if [ ! -d "$TARGET_DIR" ]; then
        info "Cloning dotnet/eShop into $TARGET_DIR"
        "$GIT_BIN" clone "$REPO_URL" "$TARGET_DIR"
    fi

    if [ ! -d "$TARGET_DIR/.git" ]; then
        error "$TARGET_DIR exists but is not a git repository. Remove it or set ESHOP_DEMO_DIR to a clean folder."
        exit 1
    fi

    local origin_url
    origin_url="$("$GIT_BIN" -C "$TARGET_DIR" remote get-url origin)"

    if [ "$origin_url" != "$REPO_URL" ]; then
        error "$TARGET_DIR points to a different git remote: $origin_url"
        exit 1
    fi

    info "Fetching latest upstream references"
    "$GIT_BIN" -C "$TARGET_DIR" fetch origin

    info "Checking out pinned commit $PINNED_COMMIT"
    "$GIT_BIN" -C "$TARGET_DIR" checkout "$PINNED_COMMIT"
}

print_next_steps() {
    cat <<EOF

[INFO] eShop is starting.
[INFO] Platform detected: $PLATFORM
[INFO] Source checkout: $TARGET_DIR
[INFO] Browser auto-open: $OPEN_DASHBOARD
[INFO] Next steps for the class demo:
[INFO] 1. Wait for Aspire to print a dashboard login URL such as https://localhost:19888/login?t=...
[INFO] 2. Open the Aspire dashboard and inspect the running resources.
[INFO] 3. Confirm Redis, RabbitMQ, and PostgreSQL are running as Docker-managed infrastructure.
[INFO] 4. Open the 'webapp' row in Aspire, then click 'Online Store (http)' or 'Online Store (https)'.
[INFO] 5. If HTTPS trust is still flaky on Linux or WSL, prefer the HTTP storefront endpoint for the demo.

EOF
}

handle_runtime_output() {
    local dashboard_opened="0"
    local line=""

    while IFS= read -r line; do
        print_line "$line"

        if [ "$dashboard_opened" = "0" ] && [[ "$line" =~ Login\ to\ the\ dashboard\ at:\ (https?://[^[:space:]]+) ]]; then
            local dashboard_url="${BASH_REMATCH[1]}"
            info "Aspire dashboard URL detected: $dashboard_url"
            info "After the dashboard opens, go to the 'webapp' row and click 'Online Store (http)' or 'Online Store (https)'."

            if [ "$OPEN_DASHBOARD" = "1" ] && [ -n "$OPEN_CMD" ]; then
                info "Opening the Aspire dashboard in your browser..."
                "$OPEN_CMD" "$dashboard_url" >/dev/null 2>&1 &
            elif [ "$OPEN_DASHBOARD" = "1" ]; then
                warn "No browser opener was found on this platform. Open the dashboard URL manually."
            fi

            dashboard_opened="1"
        fi
    done
}

main() {
    detect_platform
    detect_open_command
    require_command git
    GIT_BIN="$(command -v git)"
    ensure_docker_running
    ensure_required_dotnet
    ensure_dev_certificate
    ensure_repo

    print_next_steps

    cd "$TARGET_DIR"
    "$DOTNET_BIN" run --project "$APPHOST_PROJECT" 2>&1 | handle_runtime_output
}

main "$@"
