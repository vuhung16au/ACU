# QUICKSTART — 05.HardwareAndPlatformAPIs

## 1. Prerequisites

| Requirement | Command to verify |
| --- | --- |
| .NET 10 SDK | `dotnet --version` |
| MAUI workload | `dotnet workload list` |
| Xcode tools | `xcode-select -p` |
| Android SDK, optional | Android Studio SDK Manager |

Install the MAUI workload if needed:

```bash
dotnet workload install maui
```

## 2. Build

From the repository root:

```bash
dotnet build 05.HardwareAndPlatformAPIs/HardwareAndPlatformAPIs.csproj -f net10.0-maccatalyst
dotnet build 05.HardwareAndPlatformAPIs/HardwareAndPlatformAPIs.csproj -f net10.0-android
```

## 3. Run

### Run on macOS

```bash
dotnet build -t:Run --project 05.HardwareAndPlatformAPIs/HardwareAndPlatformAPIs.csproj -f net10.0-maccatalyst
```

### Run on Android

Start an emulator first, then run:

```bash
dotnet build -t:Run --project 05.HardwareAndPlatformAPIs/HardwareAndPlatformAPIs.csproj -f net10.0-android
```

## 4. Permissions

### macOS permissions

If Camera or Location was previously denied, open System Settings > Privacy & Security and re-enable access for 05.HardwareAndPlatformAPIs.

### Android permissions

Accept Camera and Location prompts when the app asks. For GPS testing in the emulator, use Extended Controls > Location to send a mock fix.

## 5. Troubleshooting

| Symptom | Fix |
| --- | --- |
| Camera action returns nothing | Check runtime permission and camera access in system settings |
| Location action returns nothing | Enable location services and grant permission |
| Android emulator has no GPS fix | Send a mock location from emulator controls |
| Build fails because MAUI workload is missing | Run `dotnet workload install maui` |
| Placeholder remains visible after photo selection | Rebuild and confirm the page is using the current code-behind |

## 6. Success Checklist

- [ ] The app builds successfully for `net10.0-maccatalyst`
- [ ] Taking a photo shows it in the image area
- [ ] Picking a gallery photo shows it in the image area
- [ ] Getting location shows latitude and longitude values
- [ ] Saving and loading file content restores the editor text
- [ ] Denying a permission shows a status message instead of crashing
