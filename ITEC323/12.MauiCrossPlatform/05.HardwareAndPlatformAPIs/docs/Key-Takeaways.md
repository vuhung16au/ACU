# Key Takeaways — 05.HardwareAndPlatformAPIs

## FR6 Learning Points

1. MAUI Essentials provides cross-platform wrappers for device hardware APIs such as `MediaPicker`, `Geolocation`, and `FileSystem`.
2. Camera and gallery access should copy the selected `FileResult` into `FileSystem.AppDataDirectory` so the app owns a stable local file path.
3. Geolocation requests should specify an accuracy level and timeout to balance responsiveness, battery use, and precision.
4. Hardware APIs must handle denied permissions and unavailable features gracefully instead of assuming the device can always satisfy the request.
5. Android requires manifest permissions, while iOS and Mac Catalyst require matching usage-description keys in `Info.plist`.
6. Wrapping platform APIs in `IHardwareService` keeps the ViewModel platform-agnostic and easier to test.
7. Relay commands with `CanExecute = nameof(IsIdle)` prevent users from starting overlapping hardware operations.
8. `FileSystem.AppDataDirectory` is the correct sandboxed location for app-private text files and cached media.
9. A small amount of code-behind is acceptable when it avoids adding unnecessary infrastructure for a simple UI concern.
10. This project sets up the transition from isolated feature demos to a larger app that combines hardware access, MVVM, navigation, and persistence.
