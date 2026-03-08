# Documentation Index

Comprehensive documentation for the 12.MauiCrossPlatform module.

## Phase 1: Foundation
**Core concepts for getting started with .NET MAUI**

1. [MAUI Architecture](01-MAUI-Architecture.md) - Single-project structure, handlers, platform abstraction
2. [MVVM Pattern](02-MVVM-Pattern.md) - Community Toolkit MVVM, [ObservableProperty], [RelayCommand]
3. [Data Binding](03-Data-Binding.md) - Binding modes, StringFormat, value converters, x:DataType
4. [Dependency Injection](11-Dependency-Injection.md) - MauiProgram.cs DI, service lifetimes, constructor injection

## Phase 2: Navigation & Layouts
**Building interactive multi-page apps**

5. [Shell Navigation](04-Shell-Navigation.md) - URI-based routing, query parameters, TabBar, FlyoutItem
6. [Layouts Guide](05-Layouts-Guide.md) - Grid, StackLayouts, FlexLayout, responsive design
7. [CollectionView](06-CollectionView.md) - ItemsSource, ItemTemplate, EmptyView, RefreshView, grouping
8. [Styling & Theming](10-Styling-Theming.md) - ResourceDictionary, Styles, light/dark themes, AppThemeBinding

## Phase 3: Platform Integration
**Accessing device features and native APIs**

9. [Platform APIs](07-Platform-APIs.md) - Camera, GPS, battery, sensors, FileSystem, Preferences
10. [Permissions](08-Permissions.md) - CheckStatusAsync, RequestAsync, platform configuration
11. [Platform-Specific Code](09-Platform-Specific-Code.md) - Conditional compilation, DI, Platform folders

## Phase 4: Development Workflow
**Debugging, optimization, and deployment**

12. [Debugging MAUI](12-Debugging-MAUI.md) - Breakpoints, logcat, Exception handling, logging service
13. [Hot Reload](13-Hot-Reload.md) - XAML/C# Hot Reload, live UI updates, limitations
14. [Deployment](14-Deployment.md) - Publishing to Play Store, App Store, Microsoft Store
15. [Local Data Storage](15-Local-Data-Storage.md) - Preferences, SecureStorage, FileSystem, SQLite

---

## Quick Links

- [Main README](../README.md) - Module overview and project descriptions
- [QUICKSTART](../QUICKSTART.md) - Environment setup and first app
- [FRD](../FRD.md) - Functional requirements and learning objectives
- [FRD-Copilot](../FRD-Copilot.md) - AI agent development guidelines

## Documentation Guidelines

All documentation follows these principles:
- **Beginner-friendly**: Clear explanations for first-time MAUI learners
- **Code examples**: Practical, runnable code snippets
- **Short and sharp**: Concise content focused on essentials
- **Progressive learning**: Builds from basics to advanced topics
- **Cross-platform**: Covers Android, iOS, Windows, macOS differences

For project-specific information, see the README in each project folder.
