# Greeting Card – Quick start

Step-by-step setup and run instructions for the Greeting Card Android app.

## Prerequisites

- **Android Studio** (latest stable, e.g. Ladybug or newer).  
  Download: [developer.android.com/studio](https://developer.android.com/studio)
- **JDK 17** (usually bundled with Android Studio)
- An **Android device** or **emulator** (API 24+)

## 1. Open the project

1. Launch Android Studio.
2. **File → Open** and select the `11.AndroidGreetingCards` folder (the one containing `build.gradle.kts` and `app/`).
3. Wait for Gradle sync to finish (first time may take a few minutes).

## 2. Build the project

- **Build → Make Project** (or `Ctrl+F9` / `Cmd+F9`).
- Fix any reported errors (e.g. missing SDK, wrong JDK) using the prompts in the IDE.

## 3. Run the app

**On an emulator**

1. **Tools → Device Manager**; create an emulator if needed (e.g. Pixel, API 34 or 35).
2. Start the emulator.
3. Click the **Run** button (green triangle) or use **Run → Run 'app'**.
4. Select the running emulator. The app launches with “Hi, my name is Android!” on a cyan background.

**On a physical device**

1. Enable **Developer options** and **USB debugging** on the device.
2. Connect via USB.
3. Click **Run** and choose your device.

## 4. Preview without running

1. Open `app/src/main/java/com/example/greetingcard/MainActivity.kt`.
2. Switch to **Split** or **Design** view (top-right of the editor).
3. Click **Build & Refresh** in the Preview pane to see `GreetingPreview` (e.g. “Hi, my name is Meghan!”).

## Expected result

- One screen with theme background.
- A cyan area showing: **Hi, my name is Android!** (or the name passed to `Greeting`) with padding.

## Customisation

- Change the greeting: edit the `text` in `Greeting()` in `MainActivity.kt`.
- Change the name at runtime: replace `Greeting("Android")` in `setContent { ... }` with another string.
- Change colour: replace `Color.Cyan` in `Greeting()` with e.g. `Color.Green`, `Color.Magenta`, or a custom `Color(0xFF…)`.
- Change padding: adjust `24.dp` in `modifier.padding(24.dp)`.

## Troubleshooting

| Issue | Suggestion |
|-------|------------|
| Gradle sync failed | Check internet; use **File → Invalidate Caches / Restart** if needed. |
| “SDK not found” | **File → Project Structure → SDK Location**; set Android SDK path. |
| Emulator won’t start | In Device Manager, use “Cold Boot Now” or create a new AVD with a lower API level. |
| Preview blank | Ensure **Build & Refresh** was run; check that `GreetingPreview()` is annotated with `@Preview` and `@Composable`. |

## Project structure (relevant parts)

```
11.AndroidGreetingCards/
├── app/
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/example/greetingcard/
│       │   ├── MainActivity.kt
│       │   └── ui/theme/
│       │       ├── Color.kt
│       │       ├── Theme.kt
│       │       └── Type.kt
│       └── res/
│           ├── values/strings.xml, themes.xml
│           └── drawable/
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

Launcher icon: the app uses a simple drawable icon. To use a custom launcher icon: **File → New → Image Asset** and point the manifest to the new resource.
