# Quick Start

## Purpose

This module helps you run the official `dotnet/eShop` sample as a local class demo while studying its architecture.

## Prerequisites

- Docker installed and running
- `.NET 10 SDK` installed (not 9)
- Git installed
- internet access for cloning the upstream repository and restoring NuGet packages
- usable ASP.NET Core development certificate for HTTPS endpoints

The current helper script pins the demo to this upstream commit:

```text
b81ad9557090cc37233b9d1a0a729db7b44b6f14
```

### Install `dotnet` SDK 10

On macOS, you can use Homebrew:

```bash
brew install --cask dotnet-sdk
```

On Ubuntu, Debian, or WSL, install the Microsoft package feed and `.NET 10 SDK` from Microsoft's Linux instructions:

- [.NET install for Ubuntu](https://learn.microsoft.com/dotnet/core/install/linux-ubuntu)
- [.NET install for Debian](https://learn.microsoft.com/dotnet/core/install/linux-debian)

Then verify:

```bash
dotnet --list-sdks
```

## Option 1. Run The Helper Script

```bash
cd 35-eShop-Architecture
./scripts/run-eshop-demo.sh
```

The script will:

- verify Docker is available
- verify Docker Desktop is running
- verify `.NET 10` is installed
- verify an ASP.NET Core development certificate exists
- clone or refresh `dotnet/eShop` in `/tmp/eshop-demo`
- checkout the pinned commit
- detect the Aspire dashboard URL from startup logs
- try to open the Aspire dashboard automatically when a browser opener is available
- run `src/eShop.AppHost/eShop.AppHost.csproj`

When you run the script, you should see logs similar to:

```text
➜  35-eShop-Architecture (main) ./scripts/run-eshop-demo.sh                                                    ✭ ✱
[INFO] Fetching latest upstream references
[INFO] Checking out pinned commit b81ad9557090cc37233b9d1a0a729db7b44b6f14
HEAD is now at b81ad95 Update to Aspire 13.2 (#975)

[INFO] eShop is starting.
[INFO] Next steps for the class demo:
[INFO] 1. Wait for Aspire to print a dashboard login URL such as http://localhost:19888/login?t=...
[INFO] 2. Open the Aspire dashboard and inspect the running resources.
[INFO] 3. Confirm Redis, RabbitMQ, and PostgreSQL are running as Docker-managed infrastructure.
[INFO] 4. Open the 'webapp' endpoint from Aspire to show the storefront.
[INFO] 5. Use Docker Desktop to show students the supporting containers.

Using launch settings from src/eShop.AppHost/Properties/launchSettings.json...
Building...
info: Aspire.Hosting.DistributedApplication[0]
      Aspire version: 13.2.0+1b339b0aab41b049e8e0d21ed1a79596cf8b8509
info: Aspire.Hosting.DistributedApplication[0]
      Distributed application starting.
info: Aspire.Hosting.DistributedApplication[0]
      Application host directory is: /private/tmp/eshop-demo/src/eShop.AppHost
info: Aspire.Hosting.DistributedApplication[0]
      Now listening on: https://localhost:19888
info: Aspire.Hosting.DistributedApplication[0]
      Login to the dashboard at https://localhost:19888/login?t=84b37da10919e861eb40bf8720bedded -> this is your Aspire dashboard URL for the demo. Click the link if your terminal supports it, or copy and paste it into a browser to open the dashboard.
info: Aspire.Hosting.DistributedApplication[0]
      Distributed application started. Press Ctrl+C to shut down.
```

## Helpful Environment Variables

You can customize the helper script if needed:

```bash
ESHOP_DEMO_DIR=/your/temp/folder ./scripts/run-eshop-demo.sh
```

```bash
ESHOP_SKIP_DEV_CERT_CHECK=1 ./scripts/run-eshop-demo.sh
```

Use `ESHOP_SKIP_DEV_CERT_CHECK=1` only if you already know you want to use the HTTP storefront endpoint from Aspire.

```bash
ESHOP_OPEN_DASHBOARD=0 ./scripts/run-eshop-demo.sh
```

Use `ESHOP_OPEN_DASHBOARD=0` if you do not want the script to auto-open the Aspire dashboard in your browser.

## Option 2. Run The Demo Manually

```bash
git clone https://github.com/dotnet/eShop /tmp/eshop-demo
cd /tmp/eshop-demo
git checkout b81ad9557090cc37233b9d1a0a729db7b44b6f14
dotnet run --project src/eShop.AppHost/eShop.AppHost.csproj
```

If `/tmp/eshop-demo` already exists, run:

```bash
cd /tmp/eshop-demo
git fetch origin
git checkout b81ad9557090cc37233b9d1a0a729db7b44b6f14
dotnet run --project src/eShop.AppHost/eShop.AppHost.csproj
```

## What To Expect

After the app starts, Aspire prints a dashboard login URL similar to:

```text
http://localhost:19888/login?t=...
```

From the Aspire dashboard, you should be able to:

- see the `webapp` frontend
- see supporting services such as `catalog-api`, `basket-api`, `ordering-api`, `identity-api`, and `webhooks-api`
- inspect Docker-backed infrastructure resources including Redis, RabbitMQ, and PostgreSQL
- view health, logs, and endpoints for each resource

The storefront is typically exposed by the `webapp` resource from Aspire. Open the `webapp` endpoint shown in the dashboard.

If you are on Linux or WSL and browser trust is not set up for local HTTPS certificates yet, prefer `Online Store (http)` from the `webapp` row.

## Demo Verification Checklist

Confirm these checks during class:

- Aspire dashboard opens successfully
- Docker containers are visible for infrastructure services
- the online store frontend opens
- product catalog data loads
- login and service endpoints appear in the dashboard
- RabbitMQ, Redis, and PostgreSQL appear as supporting infrastructure

## Teardown

To stop the demo:

1. Press `Ctrl+C` in the terminal running `dotnet run`
2. Wait a few seconds for Aspire to stop the application
3. Optionally stop or remove containers from Docker Desktop if you want a clean environment

The helper script uses `/tmp/eshop-demo`, so no upstream source files are stored inside this repository.

## Troubleshooting

**Issue:** `docker` command is missing  
**Solution:** Install Docker Desktop on macOS or WSL, or Docker Engine on Linux, then restart your terminal.

**Issue:** Docker is installed but not running  
**Solution:** Start Docker Desktop on macOS or WSL, or start the Docker service on Linux, then rerun the script.

**Issue:** `.NET 10 SDK` is missing  
**Solution:** Install `.NET 10 SDK`.

**Issue:** Aspire dashboard shows SSL, gRPC, or `UntrustedRoot` errors  
**Solution:** Try trusting the local development certificate, then rerun the demo:

```bash
dotnet dev-certs https --trust
```

If the certificate state is broken, clean and recreate it:

```bash
dotnet dev-certs https --clean
dotnet dev-certs https --trust
```

On Linux or WSL, browser trust may still require extra distro-specific setup. If so, use the HTTP storefront endpoint from Aspire for the demo.

**Issue:** The existing `/tmp/eshop-demo` folder has local changes  
**Solution:** Remove that temporary folder or clean it manually before rerunning the script.
