# Android Studio Project Types (Wizard)

This document provides a breakdown of the "New Project" templates available in the latest version of Android Studio. The templates are distinctly categorized by the underlying UI technology (modern Compose vs. traditional XML) and specific use cases like gaming.

## Quick Reference Table

| Project Type | Use Case | Key Differences |
|-------------|----------|-----------------|
| **Empty Activity** | Modern Android apps with Jetpack Compose | Industry standard, single blank screen, declarative UI with Kotlin |
| **Navigation UI Activity** | Apps requiring complex navigation (bottom nav, drawer) | Pre-configured navigation graph using Jetpack Compose |
| **Empty Views Activity** | Legacy Android projects | Classic XML-based layout, traditional Views system |
| **Basic Views Activity** | Simple apps with standard UI components | XML-based with App Bar and FAB pre-configured |
| **Responsive Views Activity** | Apps targeting multiple screen sizes (tablets, foldables) | XML layouts with adaptive/responsive design patterns |
| **Native C++** | Performance-critical apps, cross-platform C++ code | Uses Android NDK, C++ core logic with JNI interface |
| **Game Activity (C++)** | Native game development | Direct graphics/input access, bypasses standard UI overhead |
| **No Activity** | Background services, libraries, custom architecture | Minimal structure - only Gradle scripts and Manifest |

## Detailed Breakdown

### 1. Modern Declarative UI (Jetpack Compose)

These templates feature the **3D cube logo**. They represent the modern standard for Android development, using Kotlin and Jetpack Compose instead of XML files for building the user interface.

#### Empty Activity (The Default)
This is the **current industry standard** starting point. It provides a single, blank screen configured purely with Jetpack Compose. 

> ⚠️ **Important:** If you are learning modern Android development, this is the template you should choose.

#### Navigation UI Activity
This generates a Jetpack Compose project that is pre-configured with a complex navigation graph (such as a bottom navigation bar or a side-drawer menu), saving you the hassle of setting up routing from scratch.

---

### 2. Traditional XML UI (Legacy "Views")

If a template has the word **"Views"** in its name, it relies on the older, traditional approach: drawing screens using XML layout files and wiring them up with Kotlin/Java.

#### Empty Views Activity
The classic starting point for legacy development. It creates one blank screen using an XML file.

#### Basic Views Activity
An XML-based project that comes pre-built with a top App Bar and a circular Floating Action Button (FAB) in the bottom right corner.

#### Responsive Views Activity
This replaces older, static templates. It is designed to demonstrate how to build XML layouts that adapt seamlessly between standard mobile phones and larger screens (like tablets or foldable devices), often using sliding panes or adaptive grids.

---

### 3. High-Performance & Native (C/C++)

These templates are for specialized performance needs, bypassing the standard Java Virtual Machine (JVM) using the Android NDK (Native Development Kit).

#### Native C++
Used when you need to write core app logic in C++ (often for performance-heavy tasks like audio processing or sharing a C++ codebase across iOS and Android) and interface with it via JNI.

#### Game Activity (C++)
Specifically tailored for native game development, integrating directly with Android's underlying graphics and input subsystems without the overhead of standard UI components.

---

### 4. Barebones & AI Generation

#### No Activity
This generates the absolute bare minimum Android project structure (just the Gradle build scripts and a Manifest file). It does not create any UI or screens. Developers use this when building background services, Android libraries, or architecting an app entirely from scratch.

#### Create with AI... (Sidebar Menu)
Notice the **sparkle icon** on the left sidebar. This is a newer feature where you can use Google's Gemini AI to scaffold your initial project structure or generate specific boilerplate code based on a prompt before the project even opens.

---

## Common Mistake ⚠️

The most common mistake beginners make is confusing **"Empty Activity"** (Jetpack Compose) with **"Empty Views Activity"** (XML-based). Selecting the wrong one will completely change the structure of the code generated for your project!

- **Empty Activity** → Modern Jetpack Compose (no XML layouts)
- **Empty Views Activity** → Traditional XML layouts

Make sure to select the correct template based on which approach you're learning or using in your project!

---

## Screenshots

The workspace includes example projects:
- `EmptyActivity/` - Demonstrates the modern Jetpack Compose approach
- `NavigationUIActivity/` - Shows pre-configured navigation with Compose

---

*Last updated: March 2026*
