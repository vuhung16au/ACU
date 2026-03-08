# Android Platform Architecture

## What is Android?

Android is a **Linux-based, open-source mobile operating system** designed primarily for touchscreen devices like smartphones and tablets. It's the world's most popular mobile OS with over 70% global market share.

### Key Characteristics
- **Open Source**: Based on AOSP (Android Open Source Project)
- **Linux Kernel**: Built on Linux for device drivers, security, and process management
- **Extensive APIs**: Rich framework for accessing device hardware and services
- **Multi-Platform**: Runs on phones, tablets, watches, TVs, cars, and IoT devices
- **Kotlin-First**: Google's recommended language since 2019 (previously Java)

---

## Android Architecture Stack

Android architecture consists of **5 main layers**:

```
┌────────────────────────────────────────┐
│      APPLICATIONS LAYER                │  ← Your Apps Live Here
│  (Phone, Contacts, Browser, Your App) │
├────────────────────────────────────────┤
│   APPLICATION FRAMEWORK                │  ← Android APIs
│  (Activity Manager, Content Providers, │
│   View System, Resource Manager)       │
├────────────────────────────────────────┤
│   ANDROID RUNTIME + LIBRARIES          │  ← ART, Core Libraries
│  (ART/Dalvik, libc, SQLite, OpenGL)   │
├────────────────────────────────────────┤
│   HARDWARE ABSTRACTION LAYER (HAL)     │  ← Hardware Interface
│  (Camera, Audio, Sensors, Bluetooth)   │
├────────────────────────────────────────┤
│   LINUX KERNEL                          │  ← OS Foundation
│  (Drivers, Memory, Power Management)   │
└────────────────────────────────────────┘
```

---

## Layer 1: Linux Kernel

**Foundation of Android OS**

### What It Does
- **Device Drivers**: Camera, display, audio, touchscreen, WiFi, Bluetooth
- **Memory Management**: Allocates RAM to apps, handles low-memory situations
- **Process Management**: Runs multiple apps simultaneously
- **Security**: User-based permissions, sandboxing apps
- **Power Management**: Battery optimization, sleep modes

### Why Linux?
- Mature, stable, battle-tested operating system
- Excellent hardware support
- Open source with large community
- Strong security model (process isolation)

**Analogy**: The kernel is like the engine of a car - you don't interact with it directly, but nothing works without it.

---

## Layer 2: Hardware Abstraction Layer (HAL)

**Bridge Between Hardware and Software**

### What It Does
- Provides standard interface for hardware features
- Allows Android framework to work with different hardware implementations
- Abstracts manufacturer-specific hardware details

### Key Modules
- **Camera HAL**: Access front/rear cameras
- **Audio HAL**: Microphone and speaker control
- **Sensor HAL**: Accelerometer, gyroscope, proximity sensor
- **Bluetooth/WiFi HAL**: Wireless communication
- **GPS HAL**: Location services

**Why HAL Matters**: Your app calls Camera API → HAL translates to specific Samsung/Google/OnePlus camera hardware.

---

## Layer 3: Android Runtime & Libraries

### Android Runtime (ART)

**Executes Your App Code**

- **Ahead-of-Time (AOT) Compilation**: Compiles app code to native machine code at installation
- **Garbage Collection**: Automatic memory management
- **Improved Performance**: Faster app execution than old Dalvik runtime
- **DEX Format**: Apps compiled to `.dex` (Dalvik Executable) bytecode

**Before Android 5.0**: Used Dalvik VM (Just-in-Time compilation)  
**Android 5.0+**: ART replaced Dalvik (better performance)

### Core Libraries

- **libc**: C library for low-level system operations
- **SQLite**: Embedded database (used by Room)
- **OpenGL ES**: 2D/3D graphics rendering
- **Media Framework**: Audio/video codecs (MP4, MP3, etc.)
- **WebKit**: Web browser engine
- **SSL**: Secure network connections

---

## Layer 4: Application Framework

**Android APIs Your Apps Use**

This layer provides Java/Kotlin APIs that apps interact with:

### Key Components

#### 1. Activity Manager
- Manages app lifecycle (activity stack)
- Handles navigation between screens
- Manages back button behavior

#### 2. Content Providers
- Share data between apps securely
- Access contacts, calendar, media files
- Standard interface for data access

#### 3. View System
- UI rendering (historically XML layouts)
- Now: **Jetpack Compose** (declarative UI)
- Touch event handling

#### 4. Resource Manager
- Loads strings, images, layouts
- Handles localization (multiple languages)
- Adapts to different screen sizes

#### 5. Notification Manager
- Displays notifications in status bar
- Handles notification channels (Android 8+)

#### 6. Package Manager
- Installs/uninstalls apps
- Queries installed apps
- Manages permissions

#### 7. Location Manager
- GPS and network-based location
- Geofencing
- Location updates

#### 8. Telephony Manager
- Phone calls, SMS
- Network information (carrier, signal strength)

---

## Layer 5: Applications

**Where Your App Lives**

### Types of Apps

1. **System Apps**: Pre-installed (Phone, Messages, Camera, Settings)
2. **Google Apps**: Gmail, Maps, YouTube, Chrome
3. **Third-Party Apps**: Installed from Play Store
4. **Your Apps**: What you build in this course!

### App Components

Every Android app is built from these building blocks:

#### Activities
- Single screen with UI (e.g., Login screen)
- Entry point for user interaction
- Has lifecycle methods: `onCreate()`, `onResume()`, `onPause()`

#### Services
- Background work without UI (e.g., music player)
- Runs even when app is not visible

#### Broadcast Receivers
- Listens for system-wide events (e.g., battery low, WiFi connected)

#### Content Providers
- Manages app data and shares with other apps

---

## How Apps Interact with the System

### Example: Taking a Photo

```
Your App (Kotlin Code)
    ↓ [Calls Camera API]
Application Framework (Camera Manager)
    ↓ [Requests access]
Hardware Abstraction Layer (Camera HAL)
    ↓ [Translates to hardware commands]
Linux Kernel (Camera Driver)
    ↓ [Controls physical camera]
Camera Hardware (Sensor)
```

### Security Model

- **Sandboxing**: Each app runs in isolated process (separate user ID)
- **Permissions**: Apps must request dangerous permissions (camera, location, storage)
- **App Signing**: All apps must be digitally signed
- **SELinux**: Enforces mandatory access control

---

## Android Versions & API Levels

| Version | API Level | Codename | Year |
|---------|-----------|----------|------|
| Android 16 | 36 | Baklava | 2025 |
| Android 15 | 35 | Vanilla Ice Cream | 2024 |
| Android 14 | 34 | Upside Down Cake | 2023 |
| Android 13 | 33 | Tiramisu | 2022 |
| Android 12 | 31-32 | Snow Cone | 2021 |
| Android 11 | 30 | Red Velvet Cake | 2020 |
| Android 10 | 29 | Quince Tart | 2019 |
| Android 9 | 28 | Pie | 2018 |
| Android 8 | 26-27 | Oreo | 2017 |

**API Level**: Number developers use to specify features  
**Minimum SDK**: Lowest Android version your app supports  
**Target SDK**: Version your app is designed for (should be latest)

---

## Modern Android Development

### Jetpack Libraries

Google's recommended architecture components:

- **Compose**: Declarative UI toolkit (replaces XML)
- **ViewModel**: Lifecycle-aware data management
- **Room**: SQLite database abstraction
- **Navigation**: Type-safe screen navigation
- **WorkManager**: Background task scheduling
- **DataStore**: Modern data persistence (replaces SharedPreferences)

### Architecture Recommendations

Google recommends **MVVM** (Model-View-ViewModel):
- **Model**: Data layer (database, API)
- **View**: UI layer (Composables)
- **ViewModel**: Business logic, exposes data to UI

*Detailed in [03-android-app-architecture.md](03-android-app-architecture.md)*

---

## Key Takeaways

### For Developers
1. **You work at Application Framework level** (Jetpack APIs)
2. **Linux kernel handles low-level tasks** (you rarely interact directly)
3. **ART executes your Kotlin code** (compiled to DEX bytecode)
4. **HAL abstracts hardware differences** (Camera API works on all devices)
5. **Security is built-in** (sandboxing, permissions)

### Why This Matters
- Understanding architecture helps debug issues
- Know where performance bottlenecks occur
- Appreciate why APIs exist (abstraction layers)
- Make informed architecture decisions in your apps

---

## Comparison to Other Platforms

| Aspect | Android | iOS | Windows |
|--------|---------|-----|---------|
| **Kernel** | Linux | Darwin (Unix-based) | NT Kernel |
| **Language** | Kotlin/Java | Swift/Objective-C | C#/C++ |
| **UI Framework** | Jetpack Compose | SwiftUI | WinUI/MAUI |
| **Runtime** | ART | Native | .NET Runtime |
| **Open Source** | Yes (AOSP) | Partially | Partially |

---

## Resources

- [Android Platform Architecture (Official)](https://developer.android.com/guide/platform)
- [Android Runtime (ART)](https://source.android.com/docs/core/runtime)
- [HAL Overview](https://source.android.com/docs/core/architecture/hal)
- [Linux Kernel in Android](https://source.android.com/docs/core/architecture/kernel)

---

**Next**: [03-android-app-architecture.md](03-android-app-architecture.md) - How to structure your Android apps

---

*Understanding the platform foundation helps you build better apps!*
