# Jetpack Compose Overview

## What is Jetpack Compose?

**Jetpack Compose** is Android's modern, declarative UI toolkit for building native Android apps. It simplifies and accelerates UI development with less code, powerful tools, and intuitive Kotlin APIs.

### Key Features
- **Declarative UI**: Describe what UI should look like, not how to build it
- **Less Code**: Significantly fewer lines than XML layouts
- **Type-Safe**: Kotlin compiler catches UI errors
- **Live Previews**: See changes instantly without running app
- **Material Design 3**: Built-in modern components
- **Interoperable**: Works with existing View-based code

**Official Status**: Recommended by Google for all new Android apps (since July 2021)

---

## Declarative vs Imperative

### Imperative (Old Way - XML + findViewById)

```kotlin
// XML Layout
<TextView
    android:id="@+id/textView"
    android:text="Hello"
    android:textSize="16sp" />

// Kotlin Code
val textView = findViewById<TextView>(R.id.textView)
textView.text = "Hello World"
textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
```

**Problem**: Manual updates, verbose, error-prone

### Declarative (New Way - Compose)

```kotlin
@Composable
fun Greeting(name: String) {
    Text(
        text = "Hello $name",
        fontSize = 20.sp
    )
}
```

**Benefits**: Describe UI once, Compose handles updates automatically

---

## Core Concepts

### 1. Composable Functions

Functions annotated with `@Composable` that describe UI:

```kotlin
@Composable
fun WelcomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Welcome to Android!")
        Button(onClick = { /* Navigate */ }) {
            Text("Get Started")
        }
    }
}
```

**Rules**:
- Can call other Composables
- Can be called from other Composables (not regular functions)
- Function names should be PascalCase (like classes)
- Should be side-effect free (pure functions ideally)

### 2. Recomposition

Compose automatically re-renders UI when data changes:

```kotlin
@Composable
fun Counter() {
    var count by remember { mutableStateOf(0) }
    
    Column {
        Text("Count: $count") // ← Recomposes when count changes
        Button(onClick = { count++ }) {
            Text("Increment")
        }
    }
}
```

**How it works**:
1. User clicks button
2. `count` state changes
3. Compose detects change
4. Only `Text("Count: $count")` reruns, not entire function
5. UI updates efficiently

**Smart Recomposition**: Compose only updates changed parts, not entire screen.

### 3. State Management

#### `remember` - Preserves State Across Recompositions

```kotlin
@Composable
fun TextFieldDemo() {
    var text by remember { mutableStateOf("") }
    
    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Enter name") }
    )
}
```

**Without `remember`**: State resets to "" on every recomposition (bug!)

#### `rememberSaveable` - Survives Configuration Changes

```kotlin
@Composable
fun CounterScreen() {
    var count by rememberSaveable { mutableStateOf(0) }
    
    Button(onClick = { count++ }) {
        Text("Count: $count")
    }
}
```

**Difference**: `rememberSaveable` survives screen rotation, `remember` doesn't.

---

## Layout Composables

### Column (Vertical Stack)

```kotlin
@Composable
fun VerticalLayout() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Item 1")
        Text("Item 2")
        Text("Item 3")
    }
}
```

### Row (Horizontal Stack)

```kotlin
@Composable
fun HorizontalLayout() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Left")
        Text("Center")
        Text("Right")
    }
}
```

### Box (Overlapping Content)

```kotlin
@Composable
fun OverlappingContent() {
    Box(contentAlignment = Alignment.Center) {
        Image(painter = painterResource(R.drawable.background), contentDescription = null)
        Text("Text Over Image", color = Color.White)
    }
}
```

### LazyColumn (Scrollable List)

```kotlin
@Composable
fun StudentList(students: List<String>) {
    LazyColumn {
        items(students) { student ->
            Text(
                text = student,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
```

**Performance**: Only renders visible items (like RecyclerView)

---

## Modifiers

Modifiers configure Composables (size, padding, colors, etc.):

```kotlin
@Composable
fun StyledButton() {
    Button(
        onClick = { },
        modifier = Modifier
            .fillMaxWidth()           // Take full width
            .padding(16.dp)           // Add padding
            .height(56.dp)            // Set height
            .background(Color.Blue)   // Background color
            .clip(RoundedCornerShape(8.dp)) // Rounded corners
    ) {
        Text("Click Me")
    }
}
```

**Order Matters**:
```kotlin
// ❌ Padding applied BEFORE background (extra space outside)
Modifier.padding(16.dp).background(Color.Blue)

// ✅ Background THEN padding (padding inside colored area)
Modifier.background(Color.Blue).padding(16.dp)
```

### Common Modifiers

| Modifier | Purpose |
|----------|---------|
| `.fillMaxSize()` | Fill entire parent |
| `.fillMaxWidth()` | Fill parent width |
| `.padding(16.dp)` | Add space around element |
| `.size(48.dp)` | Set fixed width & height |
| `.clickable { }` | Make element clickable |
| `.background(Color.Red)` | Background color |
| `.border(1.dp, Color.Gray)` | Add border |

---

## Material Design 3 Components

### Button

```kotlin
Button(onClick = { /* Action */ }) {
    Text("Click Me")
}
```

### OutlinedButton

```kotlin
OutlinedButton(onClick = { }) {
    Text("Outlined")
}
```

### TextField

```kotlin
var text by remember { mutableStateOf("") }

OutlinedTextField(
    value = text,
    onValueChange = { text = it },
    label = { Text("Name") },
    placeholder = { Text("Enter your name") }
)
```

### Card

```kotlin
Card(
    modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
    elevation = CardDefaults.cardElevation(4.dp)
) {
    Text(
        text = "Card Content",
        modifier = Modifier.padding(16.dp)
    )
}
```

### Scaffold (Complete Screen Structure)

```kotlin
@Composable
fun MyScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My App") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        // Main content with proper padding
        Column(modifier = Modifier.padding(paddingValues)) {
            Text("Content")
        }
    }
}
```

---

## State Hoisting

**Principle**: Move state to the lowest common ancestor

```kotlin
// ✅ Good: State hoisted to parent
@Composable
fun ParentScreen() {
    var name by remember { mutableStateOf("") }
    
    NameInput(
        name = name,
        onNameChange = { name = it }
    )
    Greeting(name = name)
}

@Composable
fun NameInput(name: String, onNameChange: (String) -> Unit) {
    TextField(value = name, onValueChange = onNameChange)
}

@Composable
fun Greeting(name: String) {
    Text("Hello $name")
}
```

**Benefits**:
- Reusable components
- Single source of truth
- Easier testing

---

## Previews

View Composables without running app:

```kotlin
@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    MaterialTheme {
        WelcomeScreen()
    }
}
```

**Multiple Previews**:
```kotlin
@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CardPreview() {
    MaterialTheme {
        StudentCard(Student(1, "Alice", "Smith", "alice@example.com"))
    }
}
```

---

## Comparison: Compose vs XML

| Aspect | Jetpack Compose | XML Views |
|--------|----------------|-----------|
| **Paradigm** | Declarative | Imperative |
| **Code Location** | Kotlin files | Separate XML files |
| **Boilerplate** | Low | High |
| **Type Safety** | Yes | No (findViewById risks) |
| **Preview** | Live, interactive | Static |
| **Performance** | Optimized recomposition | Manual optimization |
| **Learning Curve** | Moderate | Steep |
| **Maintenance** | Easier | More complex |

---

## Best Practices

### ✅ Do
- Keep Composables small and focused
- Extract reusable components
- Use meaningful parameter names
- Provide default values for optional parameters
- Add `@Preview` to all screens/components
- Hoist state when needed by multiple components

### ❌ Don't
- Perform slow operations in Composables (use ViewModel)
- Store mutable state without `remember`
- Make network calls directly in Composables
- Forget `modifier` parameter (for styling flexibility)
- Create large, monolithic Composables

---

## Common Patterns

### Conditional UI

```kotlin
@Composable
fun LoadingScreen(isLoading: Boolean) {
    if (isLoading) {
        CircularProgressIndicator()
    } else {
        Text("Content Loaded")
    }
}
```

### List Items

```kotlin
@Composable
fun StudentList(students: List<Student>) {
    LazyColumn {
        items(students, key = { it.id }) { student ->
            StudentCard(student = student)
        }
    }
}
```

### Dialog

```kotlin
@Composable
fun DeleteConfirmDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Delete Student?") },
            text = { Text("This action cannot be undone.") },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )
    }
}
```

---

## Key Takeaways

1. **Declarative UI**: Describe what, not how
2. **Composable Functions**: Building blocks of UI
3. **Recomposition**: Automatic UI updates on state changes
4. **State Management**: Use `remember` and `rememberSaveable`
5. **Modifiers**: Configure appearance and behavior
6. **Material Design 3**: Built-in modern components
7. **Previews**: Fast visual development

---

## Resources

- [Compose Tutorial](https://developer.android.com/jetpack/compose/tutorial)
- [Thinking in Compose](https://developer.android.com/jetpack/compose/mental-model)
- [State and Jetpack Compose](https://developer.android.com/jetpack/compose/state)
- [Layouts in Compose](https://developer.android.com/jetpack/compose/layouts)
- [Material Design 3](https://m3.material.io/)

---

**Next**: [05-kotlin-for-android.md](05-kotlin-for-android.md) - Kotlin language essentials

---

*Compose makes Android UI development fun and efficient!*
