# Key Takeaways

- MVVM separates UI definition (Views) from presentation logic (ViewModels).
- In .NET MAUI, bindings in XAML connect controls to ViewModel properties and commands.
- [ObservableProperty] automatically generates property notification boilerplate.
- [RelayCommand] removes repetitive ICommand code and keeps actions testable.
- Dependency Injection in MauiProgram.cs centralizes object creation and wiring.
- Constructor injection helps pages and ViewModels receive dependencies explicitly.
- Two-way Entry binding keeps user input and ViewModel state synchronized.
- Services encapsulate reusable logic and keep ViewModels focused on UI state.
- Accessibility support can still be handled in the View layer while business logic stays in the ViewModel.
