# Code-Behind

In .NET UI frameworks, **code-behind** means splitting the visual layout from the application logic.

- The UI file defines what the user sees.
- The code-behind file defines what the app does.

This avoids mixing markup and logic in one file, which quickly becomes hard to read and maintain.

## How It Works

Code-behind usually uses a **partial class**.

- `MainWindow.xaml` or `Page.aspx` contains the layout.
- `MainWindow.xaml.cs` or `Page.aspx.cs` contains the C# logic.

At build time, .NET combines both parts into one class.

## Why It Matters

- Keeps code easier to read
- Makes UI changes safer
- Helps separate design from behavior

## Modern Direction

Code-behind is useful, but it still links UI directly to logic.

That is why modern .NET apps often use patterns like:

- **MVC** for web apps
- **MVVM** for desktop and mobile apps

These patterns push logic further away from the UI and improve maintainability.
