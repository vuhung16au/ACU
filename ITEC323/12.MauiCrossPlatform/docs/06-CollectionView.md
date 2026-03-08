# CollectionView in .NET MAUI

## What is CollectionView?

**CollectionView** displays scrollable lists of data with efficient virtualization and flexible templates.

**Use CollectionView for**:
- Lists of items (todos, products, messages)
- Grids of cards
- Scrollable content with repeated structure

**CollectionView vs ListView**:
- CollectionView: Modern, flexible, better performance
- ListView: Legacy, limited customization (use CollectionView instead)

## Basic CollectionView

```xml
<CollectionView ItemsSource="{Binding Items}">
    <CollectionView.ItemTemplate>
        <DataTemplate x:DataType="model:TodoItem">
            <Label Text="{Binding Title}" Padding="10" />
        </DataTemplate>
    </CollectionView.ItemTemplate>
</CollectionView>
```

## ItemsSource Binding

Bind to `ObservableCollection` for automatic UI updates:

**ViewModel**:
```csharp
public partial class TaskListViewModel : ObservableObject
{
    [ObservableProperty]
    private ObservableCollection<TodoItem> tasks = new();

    [RelayCommand]
    private void AddTask()
    {
        Tasks.Add(new TodoItem { Title = "New Task" });
        // UI updates automatically!
    }
}
```

**XAML**:
```xml
<CollectionView ItemsSource="{Binding Tasks}">
    <!-- Template -->
</CollectionView>
```

## ItemTemplate

Define how each item looks:

### Simple Label
```xml
<CollectionView ItemsSource="{Binding Items}">
    <CollectionView.ItemTemplate>
        <DataTemplate x:DataType="model:TodoItem">
            <Label Text="{Binding Title}" />
        </DataTemplate>
    </CollectionView.ItemTemplate>
</CollectionView>
```

### Card with Image and Text
```xml
<CollectionView ItemsSource="{Binding Products}">
    <CollectionView.ItemTemplate>
        <DataTemplate x:DataType="model:Product">
            <Frame Padding="10" Margin="5" BorderColor="LightGray">
                <Grid ColumnDefinitions="Auto,*" ColumnSpacing="10">
                    <Image Grid.Column="0" 
                           Source="{Binding ImageUrl}" 
                           WidthRequest="60" 
                           HeightRequest="60"
                           Aspect="AspectFill" />
                    
                    <VerticalStackLayout Grid.Column="1" VerticalOptions="Center">
                        <Label Text="{Binding Name}" FontAttributes="Bold" />
                        <Label Text="{Binding Price, StringFormat='${0:F2}'}" 
                               TextColor="Green" />
                    </VerticalStackLayout>
                </Grid>
            </Frame>
        </DataTemplate>
    </CollectionView.ItemTemplate>
</CollectionView>
```

### Item with Buttons
```xml
<CollectionView ItemsSource="{Binding Tasks}">
    <CollectionView.ItemTemplate>
        <DataTemplate x:DataType="model:TodoItem">
            <SwipeView>
                <!-- Swipe actions -->
                <SwipeView.RightItems>
                    <SwipeItems>
                        <SwipeItem Text="Delete" 
                                   IconImageSource="delete.png"
                                   BackgroundColor="Red"
                                   Command="{Binding Source={RelativeSource AncestorType={x:Type vm:TaskListViewModel}}, Path=DeleteTaskCommand}"
                                   CommandParameter="{Binding .}" />
                    </SwipeItems>
                </SwipeView.RightItems>
                
                <!-- Item content -->
                <Grid Padding="15" ColumnDefinitions="Auto,*,Auto">
                    <CheckBox Grid.Column="0" IsChecked="{Binding IsCompleted}" />
                    <Label Grid.Column="1" Text="{Binding Title}" VerticalOptions="Center" />
                    <Image Grid.Column="2" Source="chevron_right.png" />
                </Grid>
            </SwipeView>
        </DataTemplate>
    </CollectionView.ItemTemplate>
</CollectionView>
```

## EmptyView

Show message when list is empty:

```xml
<CollectionView ItemsSource="{Binding Tasks}">
    <CollectionView.EmptyView>
        <VerticalStackLayout HorizontalOptions="Center" VerticalOptions="Center">
            <Image Source="empty_box.png" WidthRequest="100" />
            <Label Text="No tasks yet" FontSize="18" HorizontalOptions="Center" />
            <Label Text="Tap + to add your first task" 
                   TextColor="Gray" 
                   HorizontalOptions="Center" />
        </VerticalStackLayout>
    </CollectionView.EmptyView>
    
    <CollectionView.ItemTemplate>
        <!-- Item template -->
    </CollectionView.ItemTemplate>
</CollectionView>
```

## Selection

### Single Selection

```xml
<CollectionView ItemsSource="{Binding Items}"
                SelectionMode="Single"
                SelectedItem="{Binding SelectedItem}">
    <CollectionView.ItemTemplate>
        <DataTemplate x:DataType="model:Item">
            <Label Text="{Binding Name}" />
        </DataTemplate>
    </CollectionView.ItemTemplate>
</CollectionView>
```

**ViewModel**:
```csharp
[ObservableProperty]
private Item selectedItem;

partial void OnSelectedItemChanged(Item value)
{
    if (value != null)
    {
        // Navigate or show details
        Shell.Current.GoToAsync($"details?id={value.Id}");
        
        // Clear selection
        SelectedItem = null;
    }
}
```

### Multiple Selection

```xml
<CollectionView ItemsSource="{Binding Items}"
                SelectionMode="Multiple"
                SelectedItems="{Binding SelectedItems}">
    <!-- Template -->
</CollectionView>
```

**ViewModel**:
```csharp
[ObservableProperty]
private ObservableCollection<Item> selectedItems = new();

[RelayCommand]
private void DeleteSelected()
{
    foreach (var item in SelectedItems.ToList())
    {
        Items.Remove(item);
    }
    SelectedItems.Clear();
}
```

## RefreshView (Pull to Refresh)

```xml
<RefreshView IsRefreshing="{Binding IsRefreshing}"
             Command="{Binding RefreshCommand}">
    <CollectionView ItemsSource="{Binding Items}">
        <CollectionView.ItemTemplate>
            <DataTemplate x:DataType="model:Item">
                <Label Text="{Binding Name}" />
            </DataTemplate>
        </CollectionView.ItemTemplate>
    </CollectionView>
</RefreshView>
```

**ViewModel**:
```csharp
[ObservableProperty]
private bool isRefreshing;

[RelayCommand]
private async Task Refresh()
{
    IsRefreshing = true;
    try
    {
        var data = await _dataService.GetDataAsync();
        Items = new ObservableCollection<Item>(data);
    }
    finally
    {
        IsRefreshing = false;
    }
}
```

## ItemsLayout (Grid/Horizontal)

### Vertical List (Default)
```xml
<CollectionView ItemsSource="{Binding Items}">
    <!-- Default vertical layout -->
</CollectionView>
```

### Horizontal List
```xml
<CollectionView ItemsSource="{Binding Items}">
    <CollectionView.ItemsLayout>
        <LinearItemsLayout Orientation="Horizontal" ItemSpacing="10" />
    </CollectionView.ItemsLayout>
    
    <CollectionView.ItemTemplate>
        <DataTemplate x:DataType="model:Item">
            <Frame WidthRequest="150" Padding="10">
                <Label Text="{Binding Name}" />
            </Frame>
        </DataTemplate>
    </CollectionView.ItemTemplate>
</CollectionView>
```

### Grid Layout
```xml
<CollectionView ItemsSource="{Binding Items}">
    <CollectionView.ItemsLayout>
        <GridItemsLayout Orientation="Vertical" 
                         Span="2" 
                         HorizontalItemSpacing="10"
                         VerticalItemSpacing="10" />
    </CollectionView.ItemsLayout>
    
    <CollectionView.ItemTemplate>
        <DataTemplate x:DataType="model:Item">
            <Frame>
                <VerticalStackLayout>
                    <Image Source="{Binding ImageUrl}" Aspect="AspectFill" HeightRequest="150" />
                    <Label Text="{Binding Name}" HorizontalOptions="Center" />
                </VerticalStackLayout>
            </Frame>
        </DataTemplate>
    </CollectionView.ItemTemplate>
</CollectionView>
```

## Header and Footer

```xml
<CollectionView ItemsSource="{Binding Items}">
    <!-- Header -->
    <CollectionView.Header>
        <VerticalStackLayout Padding="20" BackgroundColor="LightBlue">
            <Label Text="My Tasks" FontSize="24" FontAttributes="Bold" />
            <Label Text="{Binding Items.Count, StringFormat='Total: {0} tasks'}" />
        </VerticalStackLayout>
    </CollectionView.Header>
    
    <!-- Items -->
    <CollectionView.ItemTemplate>
        <DataTemplate x:DataType="model:TodoItem">
            <Label Text="{Binding Title}" Padding="15" />
        </DataTemplate>
    </CollectionView.ItemTemplate>
    
    <!-- Footer -->
    <CollectionView.Footer>
        <Label Text="End of list" 
               Padding="20" 
               HorizontalOptions="Center" 
               TextColor="Gray" />
    </CollectionView.Footer>
</CollectionView>
```

## Grouping

Display items in groups (e.g., tasks by category):

**Model**:
```csharp
public class TaskGroup : List<TodoItem>
{
    public string CategoryName { get; set; }
    
    public TaskGroup(string categoryName, List<TodoItem> items) : base(items)
    {
        CategoryName = categoryName;
    }
}
```

**ViewModel**:
```csharp
[ObservableProperty]
private ObservableCollection<TaskGroup> groupedTasks = new();

private void LoadGroupedTasks()
{
    var workTasks = new TaskGroup("Work", new List<TodoItem>
    {
        new TodoItem { Title = "Meeting at 10am" },
        new TodoItem { Title = "Finish report" }
    });
    
    var personalTasks = new TaskGroup("Personal", new List<TodoItem>
    {
        new TodoItem { Title = "Buy groceries" },
        new TodoItem { Title = "Call mom" }
    });
    
    GroupedTasks.Add(workTasks);
    GroupedTasks.Add(personalTasks);
}
```

**XAML**:
```xml
<CollectionView ItemsSource="{Binding GroupedTasks}" IsGrouped="True">
    <!-- Group header -->
    <CollectionView.GroupHeaderTemplate>
        <DataTemplate x:DataType="model:TaskGroup">
            <Label Text="{Binding CategoryName}" 
                   FontSize="20" 
                   FontAttributes="Bold"
                   BackgroundColor="LightGray"
                   Padding="10" />
        </DataTemplate>
    </CollectionView.GroupHeaderTemplate>
    
    <!-- Items -->
    <CollectionView.ItemTemplate>
        <DataTemplate x:DataType="model:TodoItem">
            <Label Text="{Binding Title}" Padding="15,5" />
        </DataTemplate>
    </CollectionView.ItemTemplate>
</CollectionView>
```

## Scrolling Control

```csharp
// Scroll to item
var item = Items[5];
collectionView.ScrollTo(item, animate: true);

// Scroll to index
collectionView.ScrollTo(10, animate: true);

// Scroll to position
collectionView.ScrollTo(0, position: ScrollToPosition.Start, animate: true);
```

## Complete Example

**Model**:
```csharp
public class Todo
{
    public int Id { get; set; }
    public string Title { get; set; }
    public bool IsCompleted { get; set; }
    public DateTime DueDate { get; set; }
}
```

**ViewModel**:
```csharp
public partial class TodoViewModel : ObservableObject
{
    [ObservableProperty]
    private ObservableCollection<Todo> todos = new();

    [ObservableProperty]
    private bool isRefreshing;

    [ObservableProperty]
    private Todo selectedTodo;

    [RelayCommand]
    private async Task LoadTodos()
    {
        var items = await _todoService.GetTodosAsync();
        Todos = new ObservableCollection<Todo>(items);
    }

    [RelayCommand]
    private async Task Refresh()
    {
        IsRefreshing = true;
        await LoadTodos();
        IsRefreshing = false;
    }

    [RelayCommand]
    private async Task DeleteTodo(Todo todo)
    {
        Todos.Remove(todo);
        await _todoService.DeleteAsync(todo.Id);
    }

    [RelayCommand]
    private void ToggleComplete(Todo todo)
    {
        todo.IsCompleted = !todo.IsCompleted;
    }

    partial void OnSelectedTodoChanged(Todo value)
    {
        if (value != null)
        {
            Shell.Current.GoToAsync($"details?id={value.Id}");
            SelectedTodo = null;
        }
    }
}
```

**View**:
```xml
<ContentPage xmlns="http://schemas.microsoft.com/dotnet/2021/maui"
             x:Class="TodoApp.TodoListPage"
             x:DataType="vm:TodoViewModel">
    
    <RefreshView IsRefreshing="{Binding IsRefreshing}"
                 Command="{Binding RefreshCommand}">
        
        <CollectionView ItemsSource="{Binding Todos}"
                        SelectionMode="Single"
                        SelectedItem="{Binding SelectedTodo}">
            
            <CollectionView.EmptyView>
                <VerticalStackLayout HorizontalOptions="Center" VerticalOptions="Center">
                    <Label Text="No tasks yet" FontSize="20" />
                    <Label Text="Pull down to refresh" TextColor="Gray" />
                </VerticalStackLayout>
            </CollectionView.EmptyView>
            
            <CollectionView.ItemTemplate>
                <DataTemplate x:DataType="model:Todo">
                    <SwipeView>
                        <SwipeView.RightItems>
                            <SwipeItems>
                                <SwipeItem Text="Delete"
                                           BackgroundColor="Red"
                                           Command="{Binding Source={RelativeSource AncestorType={x:Type vm:TodoViewModel}}, Path=DeleteTodoCommand}"
                                           CommandParameter="{Binding .}" />
                            </SwipeItems>
                        </SwipeView.RightItems>
                        
                        <Grid Padding="15" ColumnDefinitions="Auto,*,Auto">
                            <CheckBox Grid.Column="0"
                                      IsChecked="{Binding IsCompleted}"
                                      Command="{Binding Source={RelativeSource AncestorType={x:Type vm:TodoViewModel}}, Path=ToggleCompleteCommand}"
                                      CommandParameter="{Binding .}" />
                            
                            <VerticalStackLayout Grid.Column="1" VerticalOptions="Center">
                                <Label Text="{Binding Title}" FontSize="16" />
                                <Label Text="{Binding DueDate, StringFormat='Due: {0:MMM dd}'}" 
                                       FontSize="12" 
                                       TextColor="Gray" />
                            </VerticalStackLayout>
                            
                            <Image Grid.Column="2" 
                                   Source="chevron_right.png" 
                                   WidthRequest="20" />
                        </Grid>
                    </SwipeView>
                </DataTemplate>
            </CollectionView.ItemTemplate>
        </CollectionView>
    </RefreshView>
</ContentPage>
```

## Best Practices

1. ✅ **Use `ObservableCollection`**: Automatic UI updates
2. ✅ **Always set `x:DataType`**: Compiled bindings for performance
3. ✅ **EmptyView for empty states**: Better UX
4. ✅ **RefreshView for data reload**: Standard pattern
5. ✅ **SwipeView for actions**: Intuitive mobile interaction
6. ✅ **Keep templates simple**: Complex templates slow scrolling

## Performance Tips

- ⚡ Avoid complex layouts in ItemTemplate
- ⚡ Use `x:DataType` for compiled bindings
- ⚡ Limit nested layouts
- ⚡ Use image caching for remote images
- ⚡ Virtualize data (CollectionView does this automatically)

## Common Mistakes

❌ **Using `List` instead of `ObservableCollection`**
```csharp
public List<Item> Items { get; set; }  // UI won't update when items change
```

❌ **Forgetting `x:DataType`**
```xml
<DataTemplate>  <!-- Missing x:DataType - slower runtime binding -->
```

❌ **Complex templates**
```xml
<!-- Too many nested views - slow scrolling -->
<Frame><Grid><StackLayout><Frame>...
```

## Key Takeaways

- CollectionView displays scrollable lists efficiently
- Bind to `ObservableCollection` for automatic updates
- Use `ItemTemplate` with `DataTemplate` for item appearance
- `EmptyView` for empty state messaging
- `RefreshView` for pull-to-refresh
- `SelectionMode` for item selection
- Supports grouping, headers, footers, and grids
- Always use `x:DataType` for performance

## Next Steps

- Learn [Platform APIs](07-Platform-APIs.md) for hardware features
- Explore [Styling & Theming](10-Styling-Theming.md) for design
- Practice with [04.LayoutsAndCollections](../04.LayoutsAndCollections/)
