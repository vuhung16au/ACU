# QUICKSTART - 06.ComprehensiveTaskListApp

## 1. Prerequisites

| Requirement | Verify command |
| --- | --- |
| .NET 10 SDK | `dotnet --version` |
| MAUI workload | `dotnet workload list` |
| Xcode tools (macOS) | `xcode-select -p` |
| Android SDK (optional) | Android Studio SDK Manager |

Install MAUI workload if needed:

```bash
dotnet workload install maui
```

## 2. Build

From repository root:

```bash

# Navigate to project directory first
cd 06.ComprehensiveTaskListApp
dotnet build -f net10.0-maccatalyst
# dotnet build -f net10.0-android
# dotnet build -f net10.0-ios

# Or run directly from repository root

# For Mac Catalyst
dotnet build 06.ComprehensiveTaskListApp/ComprehensiveTaskListApp.csproj -f net10.0-maccatalyst

# For Android
dotnet build 06.ComprehensiveTaskListApp/ComprehensiveTaskListApp.csproj -f net10.0-android

# For iOS (requires macOS and Xcode)
dotnet build 06.ComprehensiveTaskListApp/ComprehensiveTaskListApp.csproj -f net10.0-ios
```

## 3. Run

### Run on macOS (Mac Catalyst)

```bash

# Navigate to project directory first
cd 06.ComprehensiveTaskListApp
dotnet build -t:Run -f net10.0-maccatalyst

# Or run directly from repository root
# dotnet build -t:Run --project 06.ComprehensiveTaskListApp/ComprehensiveTaskListApp.csproj -f net10.0-maccatalyst
```

### Run on Android

Start an Android emulator first, then run:

```bash
dotnet build -t:Run --project 06.ComprehensiveTaskListApp/ComprehensiveTaskListApp.csproj -f net10.0-android
```

## 4. Verify Core User Stories

- [ ] I can view a task list.
- [ ] I can add a task with title and description.
- [ ] I can edit an existing task.
- [ ] I can mark a task complete or incomplete.
- [ ] I can delete a task with confirmation.
- [ ] My tasks persist after app restart.

## 5. Troubleshooting

| Symptom | Fix |
| --- | --- |
| Build fails due to missing MAUI workload | Run `dotnet workload install maui` |
| App launches but list is empty after first run | Pull to refresh to reload task data |
| Navigation to detail page fails | Rebuild project and confirm routes are registered in `AppShell.xaml.cs` |
| Android run fails with emulator errors | Start emulator manually and retry run command |
