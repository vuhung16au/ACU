# Key takeaways – Greeting Card (Android Compose)

Summary of important concepts and practices from the Greeting Card codelab and project.

## 1. Android app entry point

- In Kotlin/Java apps, `main()` is the entry point; in Android, the **Activity** is.
- **`onCreate(savedInstanceState: Bundle?)`** is where your activity starts. It runs once when the activity is created.
- **`setContent { ... }`** tells Jetpack Compose what to draw. Everything inside the block is built using composable functions.

## 2. Composable functions

- **`@Composable`** marks a function as a Compose UI building block.
- **Naming:** Use **PascalCase** (e.g. `Greeting`, `GreetingPreview`).
- **Return type:** Composables do **not** return a value; they describe UI.
- **Parameters:** Prefer a **`Modifier`** as the first optional parameter so callers can customize layout and behaviour.

## 3. Building UI with Compose

- **`Text(text = "...")`** displays a string.
- **`Surface(...)`** is a container; use it to set background colour, shape, or borders.
- **`Modifier`** chains behaviour:
  - **`Modifier.padding(24.dp)`** – space around the content.
  - **`Modifier.fillMaxSize()`** – take all available space.
- Order of modifiers can matter (e.g. padding then size vs size then padding).

## 4. Preview

- **`@Preview(showBackground = true)`** + **`@Composable`** defines a preview that appears in Android Studio’s Design view.
- Use **Build & Refresh** to update the preview after code changes.
- Preview lets you iterate on UI without running the app on a device or emulator.

## 5. Project structure

- **Package** (e.g. `com.example.greetingcard`) groups source files; the Project view in Android Studio reflects this.
- **Theme** is usually in `ui.theme` (Color, Theme, Typography) and applied via a root composable (e.g. `GreetingCardTheme`).
- **Resources** (strings, themes, drawables) live under `res/` and are referenced from code or XML.

## 6. Best practices

- Keep composables small and focused; pass data in via parameters.
- Use **Optimize Imports** to keep imports alphabetical and remove unused ones.
- Prefer **Material 3** components and theme (e.g. `MaterialTheme.colorScheme`) for consistency and theming.
- Comment code for learners: explain *why* (e.g. “Surface gives us a background colour”) not only *what*.

## 7. Codelab steps (recap)

1. **Before you begin** – Install Android Studio; basic Kotlin.
2. **Create project** – New Project → Empty Activity; name “Greeting Card”; min SDK 24.
3. **Find project files** – Android view vs Project view; package structure.
4. **Update text** – Change greeting to “Hi, my name is $name!”; personalise preview name.
5. **Background colour** – Wrap text in `Surface(color = Color.Cyan)`; add `Color` import.
6. **Padding** – Add `modifier.padding(24.dp)` to `Text`.
7. **Review solution** – Compare with provided code.
8. **Conclusion** – Run on emulator/device; explore further.

## References

- [Create your first Android app (codelab)](https://developer.android.com/codelabs/basic-android-kotlin-compose-first-app)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Compose Modifier](https://developer.android.com/jetpack/compose/modifiers)
