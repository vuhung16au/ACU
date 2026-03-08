# FRD-Copilot – Code generation guidelines (Greeting Card)

Guidance for AI assistants (e.g. GitHub Copilot, Cursor) when generating or modifying code for the Greeting Card project.

## Project context

- **App:** Single-screen Android app showing a customisable greeting.
- **Stack:** Kotlin, Jetpack Compose (Material 3), Gradle Kotlin DSL.
- **Audience:** First-time learners of Android and Compose (ITEC323, Week 11).

## Code style

- **Kotlin:** Use idiomatic Kotlin; prefer `val` where possible; use meaningful names.
- **Composables:** PascalCase names; `@Composable` annotation; no return value; `Modifier` as first optional parameter.
- **Comments:** Add short KDoc or inline comments to explain *why* or *what* for beginners (e.g. role of `onCreate`, `setContent`, `Surface`, `Modifier.padding`).
- **Formatting:** Follow standard Kotlin style (4 spaces, standard brace placement). Use **Optimize Imports** (alphabetical, remove unused).

## Do

- Keep UI in Compose; use `Surface`, `Text`, `Modifier` as in the codelab.
- Preserve or extend the existing theme (`GreetingCardTheme`, `Color.kt`, `Type.kt`) rather than hardcoding colours/typography everywhere.
- Add or update `@Preview` composables for new or changed UI so students can use the Design view.
- Follow the existing package layout: `com.example.greetingcard` with `ui.theme` for theme.

## Don’t

- Introduce unnecessary complexity (e.g. navigation, databases, networking) unless required by an updated FRD.
- Use deprecated Compose or Android APIs when a stable alternative exists.
- Hardcode API keys, credentials, or non-public data.
- Remove or bypass the existing `Greeting(name: String, modifier: Modifier)` signature used in the codelab without updating docs and FRD.

## File and structure

- **Main UI:** `app/src/main/java/com/example/greetingcard/MainActivity.kt` (activity + `Greeting` + `GreetingPreview`).
- **Theme:** `app/src/main/java/com/example/greetingcard/ui/theme/` (Color.kt, Theme.kt, Type.kt).
- **Resources:** `app/src/main/res/values/` (strings, themes); use `strings.xml` for user-facing text when appropriate.
- **Build:** `app/build.gradle.kts` – keep Compose, Kotlin, and SDK versions consistent with the rest of the repo (minSdk 24, targetSdk 35).

## Testing (if extended)

- Prefer Compose UI tests or simple unit tests for any new logic; keep tests in standard Android test source sets and name clearly (e.g. `GreetingPreview_rendersCorrectly`).

## Documentation

- When adding features, update README.md and QUICKSTART.md if setup or run steps change, and FRD.md if requirements change. Keep FRD-Copilot.md in sync with any new conventions or patterns.
