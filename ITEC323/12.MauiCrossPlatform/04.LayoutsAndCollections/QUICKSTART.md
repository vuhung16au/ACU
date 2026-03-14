# QUICKSTART - 04.LayoutsAndCollections

Follow this guide to build and run the layouts and collections MAUI app.

## 1. Verify Prerequisites

```bash
dotnet --version
dotnet workload list
```

Confirm:

- .NET SDK is 10.0 or higher
- maui workload is installed

If needed:

```bash
dotnet workload install maui
```

## 2. Build The Project

From repository root:

```bash
cd 12.MauiCrossPlatform/04.LayoutsAndCollections

# Clean and restore first if you had build issues
# dotnet clean
# dotnet restore

# Build for macOS (Mac Catalyst)
dotnet build -f net10.0-maccatalyst

# Build for Android
dotnet build -f net10.0-android

# Build all targets enabled on your machine
dotnet build
```

Expected result:

```text
Build succeeded.
```

## 3. Run On macOS (Mac Catalyst)

```bash
# From project folder
dotnet build -t:Run -f net10.0-maccatalyst

# Or from repository root
dotnet build -t:Run --project 12.MauiCrossPlatform/04.LayoutsAndCollections/LayoutsAndCollections.csproj -f net10.0-maccatalyst
```

## 4. Run On Android (Optional)

Start an Android emulator first, then run:

```bash
dotnet build -t:Run --project 12.MauiCrossPlatform/04.LayoutsAndCollections/LayoutsAndCollections.csproj -f net10.0-android
```

## 5. Explore Important Files

- MauiProgram.cs: DI registrations for service, view model, and page
- Views/MainPage.xaml: Grid + stack layouts, RefreshView, and CollectionView template
- ViewModels/LayoutsCollectionsViewModel.cs: ObservableCollection state and commands
- Services/CollectionDataService.cs: In-memory list source with refresh simulation
- Models/CollectionItem.cs: Data model rendered by ItemTemplate

## Common Issues

### Error: MAUI workload missing

```bash
dotnet workload install maui
```

### Error: Android emulator/device unavailable

- Start emulator from Android Studio
- Or run Mac Catalyst target first

### Error: Build fails after SDK or workload changes

```bash
dotnet clean 12.MauiCrossPlatform/04.LayoutsAndCollections/LayoutsAndCollections.csproj
dotnet restore 12.MauiCrossPlatform/04.LayoutsAndCollections/LayoutsAndCollections.csproj
dotnet build 12.MauiCrossPlatform/04.LayoutsAndCollections/LayoutsAndCollections.csproj -f net10.0-maccatalyst
```

## Success Checklist

- Project builds successfully
- App runs on at least one platform
- New items can be added using the Grid form controls
- Pull-to-refresh updates the list and status text
- EmptyView appears after clearing all items
- You can explain why CollectionView + ObservableCollection is preferred for dynamic lists
