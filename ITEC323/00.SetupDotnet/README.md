# .NET Setup Guide

## Overview

This guide provides quick installation instructions for setting up the .NET SDK on Windows, Linux (Ubuntu), and macOS. Follow the platform-specific steps to get started with .NET development.

## What is .NET?

.NET is a free, open-source, cross-platform framework developed by Microsoft for building modern applications. The .NET SDK includes the runtime, CLI tools (`dotnet` command), and libraries needed to develop and run .NET applications across web, desktop, mobile, cloud, and IoT platforms.

## Installation

### Windows

The fastest way is using Windows Package Manager (winget):

```powershell
winget install dotnet-sdk-10
```

Alternatively, download and run the installer from the [official download page](https://dotnet.microsoft.com/download/dotnet).

### Linux (Ubuntu / Debian)

Install via APT package manager:

```bash
sudo apt-get update && sudo apt-get install -y dotnet-sdk-10.0
```

**For other distributions:**
- **Fedora/RHEL:** Use `dnf` instead of `apt-get`
- **Snap:** Run `snap install dotnet-sdk-100 --classic`

### macOS

Use Homebrew (recommended):

```bash
brew install --cask dotnet-sdk
```

Or download the official installer:
- **Apple Silicon (M1/M2/M3):** Download Arm64 version
- **Intel Processors:** Download x64 version

Visit the [official download page](https://dotnet.microsoft.com/download/dotnet) to get the installer.

## Troubleshooting

### Verify Installation

After installation, verify that .NET is installed correctly:

```bash
dotnet --version
```

This should display the installed SDK version.

### Common Issues

- **Command not found:** Restart your terminal or add .NET to your PATH manually
- **Permission errors on Linux:** Use `sudo` for installation commands
- **Multiple versions:** Use `dotnet --list-sdks` to view all installed versions

## References

- [Download .NET SDK](https://dotnet.microsoft.com/download/dotnet) - Official download page
- [Install .NET on Windows](https://dotnet.microsoft.com/download/dotnet)
- [Install .NET on Linux](https://learn.microsoft.com/en-us/dotnet/core/install/linux)
- [Install .NET on macOS](https://dotnet.microsoft.com/download/dotnet)
- [.NET Documentation](https://learn.microsoft.com/en-us/dotnet/) - Official Microsoft documentation
- [Getting Started with .NET](https://dotnet.microsoft.com/learn/dotnet/hello-world-tutorial) - Tutorial for beginners
