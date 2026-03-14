# Functional Requirements Document: Cross-Platform App Development with .NET MAUI

## Purpose
This module teaches students to build native mobile and desktop applications from a single C# codebase using .NET MAUI. Students will learn MAUI architecture, MVVM patterns, Shell navigation, hardware integration, and cross-platform deployment.

## Learning Objectives
By completing this module, students will be able to:
1. Understand .NET MAUI architecture and cross-platform app development concepts
2. Set up MAUI development environment with workloads, emulators, and tooling
3. Implement MVVM pattern using .NET Community Toolkit with data binding
4. Build multi-page apps with Shell-based URI navigation
5. Create responsive layouts with MAUI controls and CollectionView
6. Access device hardware (camera, GPS, sensors) through cross-platform APIs
7. Apply theming and styling for light/dark modes
8. Build and run apps on multiple platforms (Android, macOS, Windows)

## Target Audience
- Students with C# and ASP.NET Core Razor Pages experience
- Learners familiar with MVVM, dependency injection, and data binding from web development
- First-time mobile/desktop app developers transitioning from web to cross-platform apps

## Prerequisites
- C# programming proficiency (Week 3-6 content)
- Understanding of MVVM and dependency injection (from ASP.NET Core)
- .NET 10.0 SDK with MAUI workload installed
- Android emulator or macOS/Windows for testing
- Visual Studio, Visual Studio for Mac, or VS Code with C# Dev Kit

---

## Functional Requirements

### FR1: MAUI Environment Setup (Priority: High)
**Description**: Students must successfully set up .NET MAUI development environment.

**Acceptance Criteria**:
- .NET SDK with MAUI workload installed (`dotnet workload install maui`)
- Android SDK and emulator configured (or physical device)
- macOS/Windows target available for testing
- Ability to create and run MAUI project template
- First app successfully builds and runs on at least one platform

---

### FR2: Hello World MAUI Application (Priority: High)
**Description**: Create and customize a basic MAUI app to understand project structure and XAML UI.

**Acceptance Criteria**:
- New MAUI project created from template
- XAML UI modified with custom labels, buttons, and styling
- Code-behind handles button clicks and updates UI
- App runs on macOS or Android target
- Students understand project structure: `MauiProgram.cs`, `App.xaml`, `MainPage.xaml`, `Platforms/`

**Example Features**:
- Button that increments counter with custom message
- Label displaying dynamic text
- Image and styled layout

---

### FR3: MVVM with .NET Community Toolkit (Priority: High)
**Description**: Implement MVVM pattern using modern toolkit attributes for observable properties and commands.

**Acceptance Criteria**:
- ViewModel class inherits from `ObservableObject`
- Properties use `[ObservableProperty]` attribute
- UI commands use `[RelayCommand]` for sync/async actions
- XAML binds to ViewModel properties with `{Binding}`
- ViewModels and Pages registered in DI container in `MauiProgram.cs`
- Constructor injection used to resolve dependencies

**Example Implementation**:
- ViewModel with observable string and integer properties
- Command that modifies observable properties
- XAML page with `BindingContext` set to ViewModel
- Demonstrate two-way binding with Entry control

---

### FR4: Shell Navigation and Routing (Priority: High)
**Description**: Implement multi-page navigation using MAUI Shell with URI-based routing.

**Acceptance Criteria**:
- `AppShell.xaml` defines app structure with tabs or flyout
- Routes registered for detail/edit pages
- Navigation uses `Shell.Current.GoToAsync("route")`
- Query parameters passed and received with `[QueryProperty]`
- Back navigation implemented with relative URIs (`..`)
- Routes support hierarchical navigation (list → detail → edit)

**Example Navigation Flow**:
- Home page with list of items
- Navigate to detail page with item ID parameter
- Navigate back with Shell back button
- Pass data between pages via query strings or navigation parameters

---

### FR5: Advanced Layouts and Collections (Priority: High)
**Description**: Build responsive UIs with MAUI layouts and data-bound collections.

**Acceptance Criteria**:
- Use Grid with rows/columns for complex layouts
- Implement VerticalStackLayout and HorizontalStackLayout
- Display lists with `CollectionView` bound to `ObservableCollection`
- Define `ItemTemplate` with DataTemplate for list items
- Add `EmptyView` for empty state messaging
- Implement pull-to-refresh with `RefreshView`

**Example Features**:
- Grid-based form layout with multiple input fields
- List of items with image, title, and description
- Empty state message when no data
- Pull-to-refresh action that reloads data

---

### FR6: Hardware and Platform APIs (Priority: Medium)
**Description**: Access device hardware and platform-specific features from shared code.

**Acceptance Criteria**:
- Use `MediaPicker` API to capture photo from camera or gallery
- Implement `Geolocation` API to get current GPS coordinates
- Access file system with `FileSystem` APIs
- Request and handle permissions for camera, location, storage
- Display captured photo in Image control
- Show location coordinates in UI

**Example Scenarios**:
- "Take Photo" button that opens camera and displays result
- "Get Location" button that shows latitude/longitude
- Save and load files from app's local storage
- Handle permission denials gracefully with user feedback

---

### FR7: Theming and Styling (Priority: Medium)
**Description**: Apply consistent styling and support light/dark themes.

**Acceptance Criteria**:
- Define colors, fonts, and styles in `Resources/Styles/Colors.xaml` and `Styles.xaml`
- Create reusable styles for buttons, labels, entries
- Support light and dark mode with theme-specific resources
- Apply styles globally and per-page
- Use `DynamicResource` for runtime theme switching

**Example Styling**:
- Primary and secondary color schemes
- Consistent button styling across app
- Typography scale for headings and body text
- Automatic theme switching based on system settings

---

### FR8: Comprehensive Task List App (Priority: High)
**Description**: Build a complete cross-platform task management app combining all learned concepts.

**Acceptance Criteria**:
- MVVM architecture with ViewModels and Models
- Shell navigation between task list and detail pages
- CollectionView displays tasks with add/edit/delete functionality
- Commands for task CRUD operations
- Local data persistence using `Preferences` or SQLite
- Responsive layout with Grid and StackLayouts
- Optional: Attach photos to tasks using camera
- Optional: Light/dark theme support
- App builds and runs on Android and macOS

**User Stories**:
- As a user, I can view a list of my tasks
- As a user, I can add a new task with title and description
- As a user, I can edit an existing task
- As a user, I can delete a task with confirmation
- As a user, I can mark tasks as complete
- As a user, my tasks persist between app sessions

---

## Non-Functional Requirements

### NFR1: Cross-Platform Compatibility
- Apps must build and run on **macOS (Mac Catalyst)** and **Android**
- Windows target is optional (for demonstration on instructor machine)
- iOS target requires Xcode and Apple Developer account (optional for students)

### NFR2: Performance
- UI should be responsive with no blocking operations on main thread
- Use async/await for all I/O operations (file, network)
- CollectionView should handle lists of 100+ items efficiently

### NFR3: Code Quality
- All public methods and classes have XML documentation
- XAML bindings include Mode and UpdateSourceTrigger where appropriate
- Error handling with try-catch in async commands
- Follow naming conventions from AGENTS.md

### NFR4: Educational Value
- Code should be simple and beginner-friendly
- Comments explain MAUI-specific concepts (binding, navigation, platform APIs)
- Progression from simple to complex across projects
- README and QUICKSTART guide students step-by-step

### NFR5: Accessibility
- UI controls should have `AutomationId` for testing
- Labels should have meaningful text for screen readers
- Sufficient color contrast for readability

---

## Project Structure

The module contains 6 progressive projects:

1. **01.HelloWorldMAUI**: Basic MAUI app with XAML and code-behind
2. **02.MvvmDependencyInjection**: MVVM with Community Toolkit and DI
3. **03.ShellNavigation**: Multi-page navigation with routes and parameters
4. **04.LayoutsAndCollections**: Responsive layouts and data-bound lists
5. **05.HardwareAndPlatformAPIs**: Camera, GPS, file system integration
6. **06.ComprehensiveTaskListApp**: Full-featured task management app

Each project builds on the previous, introducing new concepts incrementally.

---

## Constraints
- Development environment: macOS primary (students may have Windows)
- MAUI workload size: ~2-4 GB download for Android SDK and emulators
- iOS development requires macOS with Xcode (optional)
- Emulator performance varies based on hardware (provide guidance on minimum specs)
- Some students may need physical Android devices if emulators are slow

---

## Success Criteria
Students successfully complete the module when they can:
- Build and run a MAUI app on multiple platforms
- Implement MVVM pattern with data binding and commands
- Navigate between pages with parameters
- Access device hardware (camera, location)
- Create a functional task management app with persistence
- Deploy app to Android emulator or physical device
- Explain the benefits of cross-platform development with MAUI

---

## Dependencies
- .NET 10.0 SDK (LTS) with MAUI workload
- Android SDK API 24+ (Android 7.0 Nougat or higher)
- Visual Studio 2022, VS for Mac, or VS Code with C# Dev Kit
- Android emulator or physical device for testing
- Xcode (optional, for iOS/macOS targets)

---

## References
- [.NET MAUI Documentation](https://learn.microsoft.com/en-us/dotnet/maui/)
- [.NET Community Toolkit MVVM](https://learn.microsoft.com/en-us/dotnet/communitytoolkit/mvvm/)
- [MAUI Shell](https://learn.microsoft.com/en-us/dotnet/maui/fundamentals/shell/)
- [MAUI Platform Integration](https://learn.microsoft.com/en-us/dotnet/maui/platform-integration/)

