# FRD-Copilot: Android Development 1 - Kotlin & Jetpack Compose

## AI Agent Instructions

This document provides guidance for AI coding agents (GitHub Copilot, Cursor, etc.) working on Week 10 Android projects.

---

## Target Platform

- **Language**: Kotlin (primary), no Java
- **UI Framework**: Jetpack Compose (no XML layouts)
- **Target SDK**: API 36 (Android 16 Baklava)
- **Minimum SDK**: API 26 (Android 8.0)
- **Architecture**: MVVM with Jetpack ViewModel
- **Build System**: Gradle with Kotlin DSL
- **IDE**: Android Studio

---

## Code Generation Standards

### Kotlin Conventions

**Naming**:
- Classes/Interfaces: `PascalCase` → `StudentViewModel`, `MainActivity`
- Functions/Variables: `camelCase` → `getStudentById`, `studentName`
- Constants: `UPPER_SNAKE_CASE` → `MAX_STUDENTS`, `DEFAULT_TIMEOUT`
- Composables: `PascalCase` → `StudentCard()`, `WelcomeScreen()`
- Private properties: `camelCase` with `private` modifier (no underscore)

**Composable Functions**:
```kotlin
@Composable
fun StudentCard(
    student: Student,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Implementation
}
```

**ViewModel Pattern**:
```kotlin
class StudentViewModel : ViewModel() {
    private val _students = MutableStateFlow<List<Student>>(emptyList())
    val students: StateFlow<List<Student>> = _students.asStateFlow()
    
    fun addStudent(student: Student) {
        _students.value = _students.value + student
    }
}
```

### Documentation Requirements

**KDoc Comments** (similar to XML docs in C#):
```kotlin
/**
 * Retrieves a student by their unique identifier.
 *
 * @param studentId The unique ID of the student to retrieve
 * @return The student object if found, null otherwise
 * @throws IllegalArgumentException if studentId is negative
 */
fun getStudentById(studentId: Int): Student? {
    require(studentId >= 0) { "Student ID must be non-negative" }
    return studentRepository.findById(studentId)
}
```

**Composable Documentation**:
```kotlin
/**
 * Displays a student profile card with edit/delete actions.
 *
 * @param student The student data to display
 * @param onEdit Callback invoked when edit button is clicked
 * @param onDelete Callback invoked when delete button is clicked
 * @param modifier Optional Modifier for styling
 */
@Composable
fun StudentCard(/* params */) { }
```

---

## Project Structure Standards

### Typical Android App Structure
```
app/
├── build.gradle.kts
├── src/
    └── main/
        ├── AndroidManifest.xml
        ├── java/com/itec323/projectname/
        │   ├── MainActivity.kt
        │   ├── ui/
        │   │   ├── screens/
        │   │   │   ├── WelcomeScreen.kt
        │   │   │   ├── StudentListScreen.kt
        │   │   │   └── StudentDetailScreen.kt
        │   │   ├── components/
        │   │   │   ├── StudentCard.kt
        │   │   │   └── CustomButton.kt
        │   │   └── theme/
        │   │       ├── Color.kt
        │   │       ├── Theme.kt
        │   │       └── Type.kt
        │   ├── viewmodel/
        │   │   └── StudentViewModel.kt
        │   ├── model/
        │   │   └── Student.kt
        │   └── navigation/
        │       └── NavGraph.kt
        └── res/
            ├── values/
            │   └── strings.xml
            └── drawable/
```

### File Organization Rules
- One screen = one file in `ui/screens/`
- Reusable components in `ui/components/`
- ViewModels in `viewmodel/` package
- Data models in `model/` package
- Navigation logic in `navigation/` package

---

## Jetpack Compose Best Practices

### State Management

**Local State** (simple UI state):
```kotlin
@Composable
fun CounterScreen() {
    var count by remember { mutableStateOf(0) }
    
    Button(onClick = { count++ }) {
        Text("Count: $count")
    }
}
```

**ViewModel State** (business logic, survives config changes):
```kotlin
@Composable
fun StudentListScreen(viewModel: StudentViewModel = viewModel()) {
    val students by viewModel.students.collectAsState()
    
    LazyColumn {
        items(students) { student ->
            StudentCard(student = student)
        }
    }
}
```

### Composable Guidelines

1. **Keep Composables Small**: Max 25 lines, extract reusable pieces
2. **Hoist State Up**: Pass state and callbacks down from parent
3. **Use `Modifier.fillMaxWidth()`** instead of hardcoded widths
4. **Provide Default Modifiers**: `modifier: Modifier = Modifier` parameter
5. **Preview Composables**: Add `@Preview` for visual development

```kotlin
@Preview(showBackground = true)
@Composable
fun StudentCardPreview() {
    MaterialTheme {
        StudentCard(
            student = Student(1, "Alice", "Smith"),
            onEdit = {},
            onDelete = {}
        )
    }
}
```

### Material Design 3 Usage

```kotlin
@Composable
fun MyScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Students") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Add */ }) {
                Icon(Icons.Default.Add, "Add Student")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Content
        }
    }
}
```

---

## Navigation Implementation

### Basic Navigation Setup

**NavGraph.kt**:
```kotlin
@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {
        composable("welcome") {
            WelcomeScreen(
                onNavigateToList = { navController.navigate("students") }
            )
        }
        composable("students") {
            StudentListScreen(
                onNavigateToDetail = { id ->
                    navController.navigate("student/$id")
                }
            )
        }
        composable(
            route = "student/{studentId}",
            arguments = listOf(navArgument("studentId") { type = NavType.IntType })
        ) { backStackEntry ->
            val studentId = backStackEntry.arguments?.getInt("studentId")
            StudentDetailScreen(
                studentId = studentId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
```

---

## Common Patterns

### Data Classes
```kotlin
data class Student(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String = "",
    val age: Int = 0
) {
    val fullName: String
        get() = "$firstName $lastName"
}
```

### Form Validation
```kotlin
@Composable
fun StudentForm(onSubmit: (Student) -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf<String?>(null) }
    
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = name,
            onValueChange = { 
                name = it
                nameError = if (it.isBlank()) "Name required" else null
            },
            label = { Text("Name") },
            isError = nameError != null,
            supportingText = nameError?.let { { Text(it) } }
        )
        
        Button(
            onClick = {
                if (name.isNotBlank()) {
                    onSubmit(Student(0, name, "", email))
                }
            },
            enabled = nameError == null
        ) {
            Text("Submit")
        }
    }
}
```

---

## Security & Best Practices

### Never Hardcode
- ❌ API keys in source code
- ❌ Sensitive URLs
- ❌ User credentials

### Use BuildConfig or local.properties
```kotlin
// build.gradle.kts
android {
    defaultConfig {
        buildConfigField("String", "API_URL", "\"https://api.example.com\"")
    }
}

// Usage
val apiUrl = BuildConfig.API_URL
```

---

## Testing Setup (Basic)

### Build Configuration
```kotlin
// app/build.gradle.kts
dependencies {
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
```

### Simple Test Example
```kotlin
class StudentViewModelTest {
    @Test
    fun addStudent_increasesListSize() {
        val viewModel = StudentViewModel()
        val initialSize = viewModel.students.value.size
        
        viewModel.addStudent(Student(1, "Test", "User"))
        
        assertEquals(initialSize + 1, viewModel.students.value.size)
    }
}
```

---

## Educational Focus

### For Beginners
- **Explain concepts**: Add comments explaining "why", not just "what"
- **Progressive complexity**: Start simple, add features incrementally
- **Avoid advanced features**: No inline functions, reified types, DSLs in Week 10
- **Consistent patterns**: Use same structure across projects
- **Error handling**: Use simple `require()` checks, not complex exception hierarchies

### Code Comments
```kotlin
// ✅ Good: Explains reasoning
// We use StateFlow instead of LiveData because it integrates 
// better with Compose's collectAsState() and provides
// better support for Kotlin coroutines
private val _students = MutableStateFlow<List<Student>>(emptyList())

// ❌ Bad: States the obvious
// Create a MutableStateFlow
private val _students = MutableStateFlow<List<Student>>(emptyList())
```

---

## Common Gotchas

### 1. Recomposition Issues
```kotlin
// ❌ Wrong: Direct mutation doesn't trigger recomposition
_students.value.add(student)

// ✅ Correct: Create new list to trigger recomposition
_students.value = _students.value + student
```

### 2. Remember Scope
```kotlin
// ❌ Wrong: State lost on recomposition
var count = 0

// ✅ Correct: State preserved
var count by remember { mutableStateOf(0) }
```

### 3. Modifier Order Matters
```kotlin
// ❌ Wrong: Padding applied before background
Modifier.padding(16.dp).background(Color.Blue)

// ✅ Correct: Background then padding
Modifier.background(Color.Blue).padding(16.dp)
```

---

## Dependencies Template

```kotlin
// app/build.gradle.kts
dependencies {
    // Compose BOM
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    
    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")
    
    // Activity Compose
    implementation("androidx.activity:activity-compose:1.8.2")
    
    // Core
    implementation("androidx.core:core-ktx:1.12.0")
}
```

---

## Error Prevention Checklist

Before completing any project:
- [ ] All Composables have `@Preview` annotations
- [ ] KDoc comments on all public functions
- [ ] No hardcoded strings (use `strings.xml`)
- [ ] ViewModels used for business logic, not UI state only
- [ ] Navigation properly handles back stack
- [ ] Material Design 3 components used consistently
- [ ] App tested on emulator (Pixel 8 Pro, API 36)
- [ ] Builds successfully without warnings

---

## Quick Reference

**Create Composable**: `@Composable fun MyScreen() { }`  
**Remember State**: `var x by remember { mutableStateOf(0) }`  
**ViewModel State**: `val data by viewModel.data.collectAsState()`  
**Navigate**: `navController.navigate("route")`  
**Material Button**: `Button(onClick = { }) { Text("Click") }`  
**Column Layout**: `Column(modifier = Modifier.fillMaxSize()) { }`  
**Card**: `Card(modifier = Modifier.padding(8.dp)) { }`
