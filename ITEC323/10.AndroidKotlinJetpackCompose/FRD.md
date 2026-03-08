# Functional Requirements Document
## Week 10: Android Development 1 - Kotlin & Jetpack Compose

### Document Information
- **Course**: ITEC323 - Web and Mobile App Development
- **Institution**: Australian Catholic University
- **Week**: 10
- **Target Platform**: Android 16 Baklava (API Level 36)
- **Last Updated**: March 2026

---

## 1. Purpose Statement

This module introduces students to native Android mobile app development using Kotlin and Jetpack Compose. Students will learn to build modern Android applications with Material Design 3, understand Android architecture, and implement core mobile UI patterns.

### Educational Goals
- Enable students to build production-ready Android applications
- Teach modern Android development practices (Kotlin + Compose)
- Prepare students for advanced Android topics (Week 11)
- Provide transferable mobile development skills

---

## 2. Target Audience

**Primary**: ITEC323 students in Week 10
- Have completed Weeks 1-9 (HTML, CSS, C#, Razor Pages, client-side JS)
- Familiar with object-oriented programming concepts
- No prior mobile development experience required
- No prior Kotlin or Java experience required

---

## 3. Functional Requirements

### FR-1: Development Environment Setup
**Priority**: Critical
- Students must successfully install Android Studio
- Configure Android SDK for API Level 36 (Android 16)
- Create and run Android Virtual Device (emulator)
- Verify "Hello World" app runs successfully
- Complete setup within 60 minutes

**Success Criteria**: Student can create and run Empty Activity (Compose) project

### FR-2: Hello World Application
**Priority**: Critical
- Create first Android app using Empty Activity template
- Display custom text in Jetpack Compose
- Implement clickable button with counter
- Use Material Design 3 components
- Run app on emulator or physical device

**Success Criteria**: App displays "Hello Android 16!" and counter increments on button click

### FR-3: Compose Basics Understanding
**Priority**: High
- Demonstrate `@Composable` function usage
- Implement layout composables: `Column`, `Row`, `Box`, `LazyColumn`
- Create interactive UI with state: `remember`, `mutableStateOf`
- Use common widgets: `TextField`, `Button`, `Text`, `Checkbox`
- Apply modifiers: padding, size, colors, alignment
- Preview composables in Android Studio

**Success Criteria**: Student can build simple form with multiple input types

### FR-4: Material Design 3 Implementation
**Priority**: High
- Apply Material You theming (dynamic colors)
- Use Material 3 components: `Card`, `TopAppBar`, `FAB`
- Implement light/dark theme toggle
- Customize color scheme and typography
- Follow Material Design guidelines

**Success Criteria**: App has cohesive Material Design 3 appearance with custom colors

### FR-5: State Management with ViewModel
**Priority**: High
- Create ViewModel class for business logic
- Use `StateFlow` / `MutableStateFlow` for reactive state
- Separate UI layer from business logic (MVVM)
- Handle configuration changes (screen rotation)
- Observe state changes in Composables

**Success Criteria**: Counter app maintains state through screen rotation

### FR-6: Multi-Screen Navigation
**Priority**: High
- Implement Jetpack Navigation Compose
- Navigate between 2+ screens with type safety
- Pass data between destinations
- Handle back stack properly
- Create bottom navigation bar (optional)

**Success Criteria**: App navigates between Welcome → List → Detail screens

### FR-7: Comprehensive Application
**Priority**: Medium
- Integrate all learned concepts in one app
- Create Student Profile Manager with CRUD operations
- Implement multiple screens with navigation
- Use ViewModel for state management
- Apply Material Design 3 consistently
- Basic data persistence (in-memory for now)

**Success Criteria**: App manages student list, add/edit/delete profiles, persists until app closes

---

## 4. Non-Functional Requirements

### NFR-1: Performance
- App launches in < 3 seconds on emulator
- UI interactions respond instantly (< 100ms)
- Smooth scrolling in lists (60 fps target)

### NFR-2: Code Quality
- Follow Kotlin coding conventions
- Use meaningful variable/function names
- Include XML documentation comments for public functions
- Maximum 25 lines per Composable function (refactor if larger)

### NFR-3: Educational Quality
- Each project builds on previous concepts progressively
- Code examples are beginner-friendly (no advanced Kotlin features)
- Documentation explains "why", not just "what"
- Projects completable in 45-90 minutes each

### NFR-4: Compatibility
- Target SDK: API 36 (Android 16)
- Minimum SDK: API 26 (Android 8.0) for broad device support
- Test on Pixel emulator (medium screen size)
- Must run on Apple Silicon and Intel Mac systems

### NFR-5: Accessibility
- Minimum touch target size: 48dp × 48dp
- Sufficient color contrast ratios (WCAG AA)
- Descriptive content descriptions for interactive elements
- Support system font scaling

---

## 5. Technical Constraints

### TC-1: Development Environment
- Android Studio (latest stable version)
- macOS 13.0+ or Windows 10+ or Linux
- 8GB RAM minimum, 16GB recommended
- 10GB free disk space

### TC-2: Build System
- Gradle with Kotlin DSL
- Kotlin 1.9+ compiler
- Compose Compiler compatible with Kotlin version
- Target Java 17 bytecode

### TC-3: Dependencies
- Jetpack Compose BOM (Bill of Materials)
- Jetpack Navigation Compose
- Lifecycle ViewModel Compose
- Material 3 (androidx.compose.material3)

### TC-4: Restrictions
- No XML layouts (Compose only)
- No Java code (Kotlin only)
- No network calls (covered in Week 11)
- No persistent database (Room in Week 11)

---

## 6. Project-Specific Requirements

### 01.HelloWorldKotlin
- Display custom greeting text
- Implement button with click counter using `remember`
- Use `Column` layout with centered alignment
- Apply basic padding and colors

### 02.ComposeBasics
- Create form with `TextField`, `Checkbox`, `Switch`
- Display list with `LazyColumn`
- Implement state for each input control
- Show preview of form data

### 03.MaterialDesign3
- Implement `Scaffold` with `TopAppBar` and `FAB`
- Use `Card` composables for content
- Create custom theme (color scheme)
- Toggle between light/dark modes

### 04.ViewModelState
- Create `CounterViewModel` with `StateFlow`
- Observe ViewModel state in UI
- Handle increment/decrement actions
- Demonstrate state survival through rotation

### 05.NavigationBasics
- Define `NavHost` with 3 destinations
- Pass student ID to detail screen
- Implement back navigation
- Show current route in TopAppBar

### 06.ComprehensiveApp
- Welcome screen with navigation button
- Student list screen (FAB to add new)
- Add/Edit form screen with validation
- Detail view screen with delete option
- Persistent state via ViewModel (in-memory)

---

## 7. Success Criteria

### Module Completion
- [ ] Android Studio installed and configured
- [ ] All 6 projects created and running successfully
- [ ] Student can explain Android architecture concepts
- [ ] Student can build simple Compose UI from scratch
- [ ] Student understands MVVM pattern basics
- [ ] Student can navigate between multiple screens
- [ ] Student completed comprehensive app independently

### Assessment Readiness
Students should be able to:
1. Create new Android project with Compose
2. Build responsive UI with Material Design 3
3. Manage state using ViewModel
4. Navigate between screens
5. Debug common Android issues
6. Explain architecture choices

---

## 8. Out of Scope (Week 11 Topics)

- Room database persistence
- Network API calls (Retrofit)
- Advanced coroutines usage
- WorkManager background tasks
- Camera/sensors integration
- Location services
- Push notifications
- Complex animations

---

## 9. Documentation Requirements

Each project must include:
- **README.md**: Overview, learning objectives, usage
- **Code comments**: Explaining complex logic
- **Architecture notes**: Why decisions were made

Module must include:
- Setup guide (QUICKSTART.md)
- Architecture documentation (docs/)
- Troubleshooting guide

---

## 10. Acceptance Criteria

### For Students
- Can build and run Android app independently
- Understands when to use ViewModel vs local state
- Can debug common Compose recomposition issues
- Ready to proceed to Week 11 advanced topics

### For Instructors
- All projects run without errors on fresh installation
- Documentation is clear for first-time learners
- Projects completable within lab time (3 hours)
- Code follows Android best practices
