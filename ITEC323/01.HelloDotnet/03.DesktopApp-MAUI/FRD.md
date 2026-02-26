# Functional Requirements Document (FRD)
## Hello World MAUI Desktop Application

**Project Name**: Hello World MAUI Desktop App  
**Version**: 1.0  
**Date**: February 2026  
**Course**: ITEC323 - Application Development  
**Institution**: Australian Catholic University (ACU)

---

## 1. Purpose

The purpose of this application is to provide an introductory, hands-on project for students learning desktop application development with .NET MAUI. The app demonstrates the minimum structure required to build and run a native desktop UI on macOS and Windows from a shared C# and XAML codebase.

### Educational Goals
- Introduce MAUI desktop project structure
- Demonstrate how XAML defines a UI page
- Show startup flow from `MauiProgram` to `App` to `MainPage`
- Teach platform-specific build targets for desktop
- Provide a foundation for future MAUI exercises

---

## 2. Scope

### 2.1 In Scope
This application will:
- Build as a .NET MAUI app for desktop targets
- Display two static labels in the main window
- Start successfully on supported desktop platform targets
- Use beginner-friendly code and documentation
- Serve as a reference for later MAUI activities

### 2.2 Out of Scope
This application will NOT:
- Include mobile targets in coursework tasks
- Include database access
- Include network APIs or authentication
- Include advanced architecture patterns (MVVM, DI-heavy setups)
- Include complex UI controls or navigation flows

---

## 3. Functional Requirements

### FR-1: Application Startup
**Priority**: Critical  
**Description**: The app must start successfully using MAUI desktop targets.

**Acceptance Criteria**:
- App can be launched from CLI with `dotnet build -t:Run`
- On macOS, launch works with `-f net10.0-maccatalyst`
- On Windows, launch works with `-f net10.0-windows10.0.19041.0`
- Application opens a native desktop window
- Application startup completes without runtime exceptions

---

### FR-2: Main Window Content
**Priority**: Critical  
**Description**: The main window must show a simple Hello World UI.

**Acceptance Criteria**:
- Main page contains heading text: `Hello, .NET 10 Desktop World!`
- Main page contains message text: `Welcome to my first .NET 10 MAUI app.`
- Text is centered horizontally
- Content is visible without scrolling at default desktop size

---

### FR-3: XAML-based UI
**Priority**: High  
**Description**: The app UI must be defined in XAML to introduce declarative layout.

**Acceptance Criteria**:
- UI layout is defined in `MainPage.xaml`
- Page code-behind file exists (`MainPage.xaml.cs`)
- Page class and XAML `x:Class` value match
- App compiles XAML successfully

---

### FR-4: MAUI Project Configuration
**Priority**: High  
**Description**: The project file must use MAUI settings for desktop targets.

**Acceptance Criteria**:
- `UseMaui` is enabled
- `SingleProject` is enabled
- Target frameworks include `net10.0-maccatalyst` and `net10.0-windows10.0.19041.0`
- Nullable reference types are enabled
- Implicit usings are enabled

---

### FR-5: Platform Startup Files
**Priority**: High  
**Description**: Platform startup files must exist for supported desktop targets.

**Acceptance Criteria**:
- `Platforms/MacCatalyst` contains startup files
- `Platforms/Windows` contains startup files
- Platform startup classes call `MauiProgram.CreateMauiApp()`
- App can compile for both desktop target frameworks

---

### FR-6: Build and Restore
**Priority**: Critical  
**Description**: The app must restore and build successfully once prerequisites are installed.

**Acceptance Criteria**:
- `dotnet workload install maui` succeeds
- `dotnet restore` completes successfully
- `dotnet build -f <desktop-target>` completes without errors
- Project compiles with .NET 10 SDK and MAUI workload

---

## 4. Non-Functional Requirements

### NFR-1: Educational Simplicity
- Code remains beginner-friendly
- File structure is easy to navigate
- Comments explain intent where helpful

### NFR-2: Maintainability
- Clear naming conventions are followed
- Public members include XML documentation comments
- Documentation files are complete and up to date

### NFR-3: Compatibility
- Project is designed for desktop targets on macOS and Windows
- Build commands clearly identify platform-specific framework

### NFR-4: Security
- No hardcoded credentials, tokens, or secrets
- Uses framework defaults without custom insecure configuration

---

## 5. Technical Requirements

### 5.1 Technology Stack
- **Framework**: .NET MAUI
- **Language**: C# and XAML
- **Target Frameworks**:
  - `net10.0-maccatalyst`
  - `net10.0-windows10.0.19041.0`

### 5.2 Development Environment
- .NET 10 SDK
- MAUI workload installed
- macOS for Mac Catalyst build/run
- Windows 10/11 for WinUI build/run

---

## 6. Success Criteria

The project is successful when:
1. Students can build and run the desktop app on their OS target
2. The app opens and displays the two required Hello World labels
3. Students can identify key MAUI files (`MauiProgram`, `App`, `MainPage`, `Platforms`)
4. Documentation guides first-time learners through setup and execution
