# MVVM Pattern in .NET MAUI

## What is MVVM?

**Model-View-ViewModel** is a design pattern that separates UI (View) from business logic (ViewModel) and data (Model).

```
┌─────────┐         ┌──────────────┐         ┌─────────┐
│  View   │ ◄─────► │  ViewModel   │ ◄─────► │  Model  │
│ (XAML)  │ Binding │ (Logic/State)│         │ (Data)  │
└─────────┘         └──────────────┘         └─────────┘
```

**Benefits**:
- ✅ Testable (ViewModels testable without UI)
- ✅ Maintainable (separation of concerns)
- ✅ Reusable (ViewModels reusable across pages)
- ✅ Designer-friendly (XAML can be edited independently)

## Components

### Model
**Purpose**: Data and business entities

```csharp
public class TodoItem
{
    public int Id { get; set; }
    public string Title { get; set; }
    public bool IsCompleted { get; set; }
}
```

### View
**Purpose**: XAML UI that displays data

```xml
<ContentPage xmlns="http://schemas.microsoft.com/dotnet/2021/maui"
             x:Class="MyApp.MainPage"
             x:DataType="vm:MainViewModel">
    
    <VerticalStackLayout Padding="20">
        <Label Text="{Binding Title}" FontSize="24" />
        <Button Text="Click Me" Command="{Binding IncrementCommand}" />
    </VerticalStackLayout>
</ContentPage>
```

### ViewModel
**Purpose**: Exposes data and commands for binding

```csharp
public class MainViewModel
{
    public string Title { get; set; }
    public ICommand IncrementCommand { get; }
    
    // Logic here
}
```

## .NET Community Toolkit MVVM

The **CommunityToolkit.Mvvm** NuGet package simplifies MVVM with source generators.

### Installation

```bash
dotnet add package CommunityToolkit.Mvvm
```

### Key Attributes

| Attribute | Purpose | Generates |
|-----------|---------|-----------|
| `[ObservableProperty]` | Auto-property with `INotifyPropertyChanged` | Public property + change notification |
| `[RelayCommand]` | Create command from method | `ICommand` property |
| `[NotifyPropertyChangedFor]` | Notify when related property changes | Additional notifications |
| `[NotifyCanExecuteChangedFor]` | Update command CanExecute | Command state refresh |

## ObservableObject Base Class

All ViewModels inherit from `ObservableObject`:

```csharp
using CommunityToolkit.Mvvm.ComponentModel;

public partial class MainViewModel : ObservableObject
{
    // Your properties and commands here
}
```

**What it provides**:
- `INotifyPropertyChanged` implementation
- `OnPropertyChanged()` helpers
- `SetProperty()` methods

## Observable Properties

### Old Way (Manual)
```csharp
private string _title;
public string Title
{
    get => _title;
    set
    {
        if (_title != value)
        {
            _title = value;
            OnPropertyChanged();
        }
    }
}
```

### New Way (Toolkit)
```csharp
using CommunityToolkit.Mvvm.ComponentModel;

public partial class MainViewModel : ObservableObject
{
    [ObservableProperty]
    private string title;  // Generates "Title" property automatically
}
```

**Result**: Source generator creates the full property with change notification!

### Multiple Properties Example

```csharp
public partial class TodoViewModel : ObservableObject
{
    [ObservableProperty]
    private string taskName;

    [ObservableProperty]
    private bool isCompleted;

    [ObservableProperty]
    private DateTime dueDate;
}
```

**Generated properties**: `TaskName`, `IsCompleted`, `DueDate` (all with `INotifyPropertyChanged`)

## Relay Commands

### Synchronous Commands

```csharp
using CommunityToolkit.Mvvm.Input;

public partial class CounterViewModel : ObservableObject
{
    [ObservableProperty]
    private int count;

    [RelayCommand]
    private void Increment()
    {
        Count++;
    }

    [RelayCommand]
    private void Reset()
    {
        Count = 0;
    }
}
```

**Generated**: `IncrementCommand` and `ResetCommand` properties

**XAML Binding**:
```xml
<Button Text="Increment" Command="{Binding IncrementCommand}" />
<Button Text="Reset" Command="{Binding ResetCommand}" />
```

### Async Commands

```csharp
[RelayCommand]
private async Task LoadData()
{
    IsBusy = true;
    try
    {
        var data = await _dataService.GetDataAsync();
        Items = new ObservableCollection<Item>(data);
    }
    finally
    {
        IsBusy = false;
    }
}
```

**Generated**: `LoadDataCommand` (handles async execution)

### Commands with Parameters

```csharp
[RelayCommand]
private void DeleteItem(TodoItem item)
{
    Items.Remove(item);
}
```

**XAML**:
```xml
<Button Text="Delete" 
        Command="{Binding DeleteItemCommand}"
        CommandParameter="{Binding .}" />
```

### Commands with CanExecute

```csharp
[ObservableProperty]
private string name;

[RelayCommand(CanExecute = nameof(CanSave))]
private async Task Save()
{
    await _repository.SaveAsync(Name);
}

private bool CanSave()
{
    return !string.IsNullOrWhiteSpace(Name);
}
```

Button automatically disables when `CanSave()` returns `false`.

## Complete Example

### Model
```csharp
public class Task
{
    public int Id { get; set; }
    public string Title { get; set; }
    public bool IsDone { get; set; }
}
```

### ViewModel
```csharp
using CommunityToolkit.Mvvm.ComponentModel;
using CommunityToolkit.Mvvm.Input;
using System.Collections.ObjectModel;

public partial class TaskListViewModel : ObservableObject
{
    [ObservableProperty]
    private ObservableCollection<Task> tasks = new();

    [ObservableProperty]
    private string newTaskTitle;

    [RelayCommand]
    private void AddTask()
    {
        if (!string.IsNullOrWhiteSpace(NewTaskTitle))
        {
            Tasks.Add(new Task 
            { 
                Id = Tasks.Count + 1, 
                Title = NewTaskTitle 
            });
            NewTaskTitle = string.Empty;
        }
    }

    [RelayCommand]
    private void DeleteTask(Task task)
    {
        Tasks.Remove(task);
    }

    [RelayCommand]
    private void ToggleComplete(Task task)
    {
        task.IsDone = !task.IsDone;
    }
}
```

### View (XAML)
```xml
<ContentPage xmlns="http://schemas.microsoft.com/dotnet/2021/maui"
             x:Class="MyApp.TaskListPage"
             x:DataType="vm:TaskListViewModel">
    
    <VerticalStackLayout Padding="20">
        <!-- Add new task -->
        <HorizontalStackLayout>
            <Entry Text="{Binding NewTaskTitle}" 
                   Placeholder="New task..."
                   HorizontalOptions="FillAndExpand" />
            <Button Text="Add" Command="{Binding AddTaskCommand}" />
        </HorizontalStackLayout>

        <!-- Task list -->
        <CollectionView ItemsSource="{Binding Tasks}">
            <CollectionView.ItemTemplate>
                <DataTemplate x:DataType="model:Task">
                    <HorizontalStackLayout>
                        <CheckBox IsChecked="{Binding IsDone}" />
                        <Label Text="{Binding Title}" 
                               VerticalOptions="Center" />
                        <Button Text="Delete" 
                                Command="{Binding Source={RelativeSource AncestorType={x:Type vm:TaskListViewModel}}, Path=DeleteTaskCommand}"
                                CommandParameter="{Binding .}" />
                    </HorizontalStackLayout>
                </DataTemplate>
            </CollectionView.ItemTemplate>
        </CollectionView>
    </VerticalStackLayout>
</ContentPage>
```

### View Code-Behind
```csharp
public partial class TaskListPage : ContentPage
{
    public TaskListPage(TaskListViewModel viewModel)
    {
        InitializeComponent();
        BindingContext = viewModel;
    }
}
```

## Setting BindingContext

### Option 1: Constructor Injection (Recommended)
```csharp
public MainPage(MainViewModel viewModel)
{
    InitializeComponent();
    BindingContext = viewModel;
}
```

Register in `MauiProgram.cs`:
```csharp
builder.Services.AddTransient<MainViewModel>();
builder.Services.AddTransient<MainPage>();
```

### Option 2: XAML
```xml
<ContentPage.BindingContext>
    <vm:MainViewModel />
</ContentPage.BindingContext>
```

### Option 3: Code-Behind
```csharp
public MainPage()
{
    InitializeComponent();
    BindingContext = new MainViewModel();
}
```

## Property Change Notifications

### Notify Related Properties

```csharp
[ObservableProperty]
[NotifyPropertyChangedFor(nameof(FullName))]
private string firstName;

[ObservableProperty]
[NotifyPropertyChangedFor(nameof(FullName))]
private string lastName;

public string FullName => $"{FirstName} {LastName}";
```

When `FirstName` or `LastName` changes, `FullName` is also notified.

### Notify Command CanExecute

```csharp
[ObservableProperty]
[NotifyCanExecuteChangedFor(nameof(SaveCommand))]
private string name;

[RelayCommand(CanExecute = nameof(CanSave))]
private void Save() { }

private bool CanSave() => !string.IsNullOrWhiteSpace(Name);
```

## Testing ViewModels

ViewModels are easily testable without UI:

```csharp
[Fact]
public void AddTask_AddsToCollection()
{
    // Arrange
    var viewModel = new TaskListViewModel();
    viewModel.NewTaskTitle = "Test Task";

    // Act
    viewModel.AddTaskCommand.Execute(null);

    // Assert
    Assert.Single(viewModel.Tasks);
    Assert.Equal("Test Task", viewModel.Tasks[0].Title);
}
```

## Best Practices

1. ✅ **Use Community Toolkit**: Reduces boilerplate by 80%+
2. ✅ **Keep ViewModels UI-agnostic**: No `ContentPage`, `Navigation`, etc.
3. ✅ **One ViewModel per View**: Clear separation
4. ✅ **Use async commands**: Never block UI thread
5. ✅ **Inject dependencies**: Services via constructor
6. ✅ **ObservableCollection for lists**: Automatically updates UI

## Common Mistakes

❌ **Forgetting `partial` keyword**
```csharp
// Wrong
public class MainViewModel : ObservableObject

// Correct
public partial class MainViewModel : ObservableObject
```

❌ **Property casing mismatch**
```csharp
[ObservableProperty]
private string title;  // Generates "Title" (capital T)

// In XAML, use "Title", not "title"
```

❌ **Not using `ObservableCollection`**
```csharp
// UI won't update when list changes
public List<Item> Items { get; set; }

// Correct - UI updates automatically
public ObservableCollection<Item> Items { get; set; }
```

## Key Takeaways

- MVVM separates UI (View) from logic (ViewModel)
- Community Toolkit MVVM reduces boilerplate with attributes
- `[ObservableProperty]` generates properties with change notification
- `[RelayCommand]` generates commands from methods
- ViewModels are testable without UI
- Use dependency injection to provide ViewModels to Views

## Next Steps

- Learn [Data Binding](03-Data-Binding.md) in depth
- Explore [Dependency Injection](11-Dependency-Injection.md) setup
- Build project [02.MvvmDependencyInjection](../02.MvvmDependencyInjection/)
