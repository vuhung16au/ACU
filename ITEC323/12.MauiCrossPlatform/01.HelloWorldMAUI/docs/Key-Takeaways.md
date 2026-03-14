# Key Takeaways

- .NET MAUI lets you build Android, iOS, macOS, and Windows apps from one C# project.
- `MainPage.xaml` defines UI layout, while `MainPage.xaml.cs` handles UI events in code-behind.
- `MauiProgram.cs` is the startup file where MAUI app services and fonts are configured.
- `App.xaml` contains shared resources such as colors and styles.
- `AppShell.xaml` is the app's main navigation container, even in simple starter apps.
- Click event handlers (`Clicked="..."`) connect button actions in XAML to C# methods.
- Updating label and button text after a click demonstrates basic UI state changes.
- `SemanticScreenReader.Announce(...)` improves accessibility feedback after interactions.
- Building with a specific target framework (for example, `net10.0-maccatalyst` or `net10.0-android`) helps test one platform at a time.
