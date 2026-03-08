# Functional Requirements Document – Greeting Card

## Purpose

Define the functional and non-functional requirements for the Greeting Card Android app (Week 11 – Android Development 1: Kotlin and Jetpack Compose). The app demonstrates a minimal Compose UI and aligns with the official codelab [Create your first Android app](https://developer.android.com/codelabs/basic-android-kotlin-compose-first-app).

## Functional requirements

| ID | Requirement | Priority |
|----|-------------|----------|
| FR1 | The app shall display a single screen with a greeting message. | Must |
| FR2 | The greeting text shall be of the form “Hi, my name is [name]!” where [name] is configurable (e.g. “Android” or a user name). | Must |
| FR3 | The greeting shall be shown on a distinct background colour (e.g. cyan) using a Compose `Surface`. | Must |
| FR4 | The greeting text shall have padding around it (e.g. 24 dp). | Must |
| FR5 | The app shall use Jetpack Compose (Material 3) for UI and Kotlin for logic. | Must |
| FR6 | The app shall provide a `@Preview` composable so the greeting can be viewed in Android Studio’s Design view without running the app. | Should |

## Non-functional requirements

| ID | Requirement |
|----|-------------|
| NFR1 | Minimum SDK 24 (Android 7.0). Target SDK 35. |
| NFR2 | Build with Gradle (Kotlin DSL) and Android Gradle Plugin 8.x. |
| NFR3 | Code and documentation suitable for first-time Android/Compose learners. |
| NFR4 | No hardcoded secrets; no unnecessary permissions. |

## Constraints

- Use Android Studio and the Empty Activity (Compose) style project.
- Use Kotlin and Jetpack Compose only for UI (no XML layouts for the main screen).
- Keep scope limited to one screen and one greeting; no navigation or persistence required.

## Success criteria

- [ ] App builds without errors in Android Studio.
- [ ] App runs on emulator or device and shows the greeting with coloured background and padding.
- [ ] Preview works in Design view for the provided preview composable.
- [ ] Project includes README, QUICKSTART, FRD, and Key-Takeaways documentation.
- [ ] Code is commented for beginner understanding.

## References

- Codelab: [Basic Android Kotlin Compose – Create your first app](https://developer.android.com/codelabs/basic-android-kotlin-compose-first-app) (steps 1–8).
- Jetpack Compose: [developer.android.com/jetpack/compose](https://developer.android.com/jetpack/compose).
