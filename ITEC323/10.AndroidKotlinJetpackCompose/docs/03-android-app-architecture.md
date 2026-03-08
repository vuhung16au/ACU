# Android App Architecture

## Why Architecture Matters

**Problem**: Without proper architecture, apps become:
- Hard to test
- Difficult to maintain
- Prone to bugs
- Tightly coupled (UI mixed with business logic)

**Solution**: Follow **MVVM** (Model-View-ViewModel) pattern recommended by Google.

---

## MVVM Architecture Pattern

### Overview

```
┌─────────────────────────────────────────┐
│           UI LAYER (View)               │
│  ┌──────────────────────────────────┐  │
│  │  Composables (Screens)           │  │ ← What user sees
│  │  - StudentListScreen             │  │
│  │  - StudentDetailScreen           │  │
│  └──────────────────────────────────┘  │
│                 ↕                       │
│  ┌──────────────────────────────────┐  │
│  │  ViewModel                       │  │ ← Business logic
│  │  - StudentViewModel              │  │
│  │  - Exposes StateFlow             │  │
│  └──────────────────────────────────┘  │
└─────────────────────────────────────────┘
                  ↕
┌─────────────────────────────────────────┐
│         DATA LAYER (Model)              │
│  ┌──────────────────────────────────┐  │
│  │  Repository                      │  │ ← Data source
│  │  - StudentRepository             │  │
│  └──────────────────────────────────┘  │
│                 ↕                       │
│  ┌────────────────┬─────────────────┐  │
│  │  Room Database │  Remote API     │  │
│  │  (local data)  │  (network data) │  │
│  └────────────────┴─────────────────┘  │
└─────────────────────────────────────────┘
```

### Layers Explained

#### 1. UI Layer (View)
- **What**: Composables that display data and handle user interactions
- **Responsibilities**:
  - Render UI based on state
  - Handle user input (button clicks, text entry)
  - Display data from ViewModel
- **Examples**: `StudentListScreen()`, `LoginScreen()`
- **No Business Logic**: Just UI rendering

#### 2. ViewModel
- **What**: Lifecycle-aware component that holds UI state
- **Responsibilities**:
  - Expose data to UI via `StateFlow` or `State`
  - Handle user actions (button clicks → logic)
  - Manage UI-related logic
  - Survive configuration changes (screen rotation)
- **Examples**: `StudentViewModel`, `LoginViewModel`
- **Never references UI**: No Composables or View references

#### 3. Data Layer (Model & Repository)
- **What**: Manages app data sources
- **Responsibilities**:
  - Fetch data from database or network
  - Cache data
  - Provide clean API to ViewModels
- **Examples**: `StudentRepository`, `UserRepository`
- **Multiple Sources**: Local (Room) + Remote (Retrofit API)

---

## Code Example: MVVM in Action

### Data Layer: Model

```kotlin
// Data class representing a student
data class Student(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String
) {
    val fullName: String get() = "$firstName $lastName"
}
```

### Data Layer: Repository

```kotlin
// Repository manages data sources
class StudentRepository {
    private val students = mutableListOf(
        Student(1, "Alice", "Johnson", "alice@example.com"),
        Student(2, "Bob", "Smith", "bob@example.com")
    )
    
    fun getAllStudents(): List<Student> = students.toList()
    
    fun getStudentById(id: Int): Student? {
        return students.find { it.id == id }
    }
    
    fun addStudent(student: Student) {
        students.add(student)
    }
    
    fun deleteStudent(id: Int) {
        students.removeIf { it.id == id }
    }
}
```

### ViewModel

```kotlin
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StudentViewModel : ViewModel() {
    private val repository = StudentRepository()
    
    // Private mutable state
    private val _students = MutableStateFlow<List<Student>>(emptyList())
    // Public read-only state
    val students: StateFlow<List<Student>> = _students.asStateFlow()
    
    init {
        loadStudents()
    }
    
    private fun loadStudents() {
        _students.value = repository.getAllStudents()
    }
    
    fun addStudent(firstName: String, lastName: String, email: String) {
        val newStudent = Student(
            id = (students.value.maxOfOrNull { it.id } ?: 0) + 1,
            firstName = firstName,
            lastName = lastName,
            email = email
        )
        repository.addStudent(newStudent)
        loadStudents() // Refresh list
    }
    
    fun deleteStudent(id: Int) {
        repository.deleteStudent(id)
        loadStudents()
    }
}
```

### UI Layer: Composable Screen

```kotlin
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun StudentListScreen(
    viewModel: StudentViewModel = viewModel()
) {
    // Collect state from ViewModel
    val students by viewModel.students.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Students") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Navigate to Add screen */ }) {
                Icon(Icons.Default.Add, "Add Student")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(students) { student ->
                StudentCard(
                    student = student,
                    onDelete = { viewModel.deleteStudent(student.id) }
                )
            }
        }
    }
}

@Composable
fun StudentCard(
    student: Student,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(student.fullName, style = MaterialTheme.typography.titleMedium)
                Text(student.email, style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, "Delete")
            }
        }
    }
}
```

---

## Key Concepts

### State Management

#### Local State (UI-only)
Use `remember` for simple UI state that doesn't need to survive configuration changes:

```kotlin
@Composable
fun CounterScreen() {
    var count by remember { mutableStateOf(0) }
    
    Button(onClick = { count++ }) {
        Text("Count: $count")
    }
}
```

**Use When**:
- Controlling UI animations
- Tracking text field input temporarily
- Toggle states (expanded/collapsed)

#### ViewModel State (Business Logic)
Use ViewModel + StateFlow for data that should survive configuration changes:

```kotlin
class CounterViewModel : ViewModel() {
    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count.asStateFlow()
    
    fun increment() {
        _count.value++
    }
}

@Composable
fun CounterScreen(viewModel: CounterViewModel = viewModel()) {
    val count by viewModel.count.collectAsState()
    
    Button(onClick = { viewModel.increment() }) {
        Text("Count: $count")
    }
}
```

**Use When**:
- Data should survive screen rotation
- Multiple screens need same data
- Data comes from network or database
- Business logic involved

---

## Unidirectional Data Flow (UDF)

**Principle**: Data flows in one direction, events flow in the opposite direction

```
┌─────────────┐
│  ViewModel  │ ← Events (user clicks)
│   (State)   │
└──────┬──────┘
       │
       ↓ State updates
┌─────────────┐
│     UI      │
│ (Composable)│ → Events (button clicks)
└─────────────┘
```

### Example

```kotlin
// ViewModel exposes state
class FormViewModel : ViewModel() {
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()
    
    // Events handled by ViewModel
    fun onNameChanged(newName: String) {
        _name.value = newName
    }
}

// UI observes state and sends events
@Composable
fun FormScreen(viewModel: FormViewModel = viewModel()) {
    val name by viewModel.name.collectAsState()
    
    TextField(
        value = name,
        onValueChange = { viewModel.onNameChanged(it) }, // Event ↑
        label = { Text("Name") }
    )
}
```

**Benefits**:
- Predictable state changes
- Easier to test
- Easier to debug (single source of truth)

---

## Lifecycle Awareness

### Activity/Fragment Lifecycle

Android components have lifecycles:

```
onCreate() → onStart() → onResume() → [App Running] → onPause() → onStop() → onDestroy()
```

### ViewModel Lifecycle

ViewModel survives configuration changes:

```
Activity Created → ViewModel Created
Activity Rotated → Activity Destroyed → Activity Recreated
                   ViewModel SURVIVES (same instance)
Activity Destroyed (permanent) → ViewModel Destroyed
```

**Why This Matters**:
- Don't lose data on screen rotation
- Network requests continue during rotation
- State preserved automatically

---

## Folder Structure Example

```
app/src/main/java/com/itec323/studentapp/
├── MainActivity.kt
├── ui/
│   ├── screens/
│   │   ├── StudentListScreen.kt
│   │   ├── StudentDetailScreen.kt
│   │   └── AddStudentScreen.kt
│   ├── components/
│   │   ├── StudentCard.kt
│   │   └── CustomTextField.kt
│   └── theme/
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
├── viewmodel/
│   └── StudentViewModel.kt
├── model/
│   └── Student.kt
├── repository/
│   └── StudentRepository.kt
└── navigation/
    └── NavGraph.kt
```

---

## Best Practices

### ✅ Do
- Keep Composables small and focused
- Hoist state up when needed by multiple components
- Use ViewModel for business logic
- Make ViewModels testable (inject dependencies)
- Follow single responsibility principle
- Document complex logic

### ❌ Don't
- Put business logic in Composables
- Reference Composables from ViewModel
- Store UI state in ViewModel if not needed
- Make ViewModel depend on Android framework (except `ViewModel` class)
- Call suspend functions directly in Composables (use ViewModel)

---

## Testing Strategy

### ViewModel Testing
```kotlin
class StudentViewModelTest {
    @Test
    fun addStudent_increasesListSize() {
        val viewModel = StudentViewModel()
        val initialSize = viewModel.students.value.size
        
        viewModel.addStudent("Test", "User", "test@example.com")
        
        assertEquals(initialSize + 1, viewModel.students.value.size)
    }
}
```

### UI Testing (Composable)
```kotlin
@Test
fun studentCard_displaysStudentName() {
    composeTestRule.setContent {
        StudentCard(
            student = Student(1, "Alice", "Johnson", "alice@example.com"),
            onDelete = {}
        )
    }
    
    composeTestRule.onNodeWithText("Alice Johnson").assertIsDisplayed()
}
```

---

## Comparison to Other Patterns

| Pattern | Description | Use Case |
|---------|-------------|----------|
| **MVVM** | Model-View-ViewModel (recommended) | Modern Android with Compose |
| **MVC** | Model-View-Controller (older) | Legacy apps, not recommended |
| **MVP** | Model-View-Presenter (older) | Legacy apps with testing focus |
| **MVI** | Model-View-Intent (advanced) | Complex apps, strict UDF |

---

## Key Takeaways

1. **Separation of Concerns**: UI, logic, and data are separate
2. **ViewModel is the Bridge**: Connects UI to data layer
3. **Unidirectional Data Flow**: State down, events up
4. **Lifecycle Awareness**: ViewModels survive configuration changes
5. **Testability**: Each layer can be tested independently

---

## Resources

- [Guide to App Architecture](https://developer.android.com/topic/architecture)
- [ViewModel Overview](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [StateFlow and SharedFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)
- [Compose State](https://developer.android.com/jetpack/compose/state)

---

**Next**: [04-jetpack-compose-overview.md](04-jetpack-compose-overview.md) - Deep dive into Compose UI

---

*Good architecture makes apps easy to build, test, and maintain!*
