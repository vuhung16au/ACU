# Kotlin for Android Development

## What is Kotlin?

**Kotlin** is a modern, statically-typed programming language that runs on the Java Virtual Machine (JVM). It's Google's recommended language for Android development since 2019.

### Why Kotlin for Android?

- **Concise**: 40% less code than Java
- **Safe**: Null safety prevents crashes
- **Interoperable**: Works seamlessly with Java
- **Expressive**: Modern language features
- **Official**: Fully supported by Google and Android Studio

---

## Kotlin Basics

### Variables

```kotlin
// Immutable (cannot reassign) - Preferred
val name: String = "Alice"
val age = 25  // Type inferred

// Mutable (can reassign)
var score = 0
score = 10  // OK

// ❌ Error
name = "Bob"  // Cannot reassign val
```

**Best Practice**: Use `val` by default, `var` only when needed

### Types

```kotlin
val integer: Int = 42
val long: Long = 100L
val double: Double = 3.14
val float: Float = 3.14f
val boolean: Boolean = true
val char: Char = 'A'
val string: String = "Hello"
```

### Null Safety

```kotlin
// Non-nullable (cannot be null)
var name: String = "Alice"
name = null  // ❌ Compilation error

// Nullable (can be null)
var email: String? = null
email = "alice@example.com"  // OK

// Safe call operator
val length = email?.length  // Returns null if email is null

// Elvis operator (default value)
val displayName = email ?: "Unknown"

// Safe cast
val student: Student? = person as? Student
```

**Why This Matters**: Kotlin prevents most `NullPointerException` crashes at compile time!

### String Templates

```kotlin
val name = "Alice"
val age = 25

// String interpolation
val greeting = "Hello, $name!"
val info = "$name is $age years old"

// Expressions in strings
val message = "Next year: ${age + 1}"
```

---

## Functions

### Basic Function

```kotlin
fun greet(name: String): String {
    return "Hello, $name!"
}

// Single-expression function
fun greet(name: String) = "Hello, $name!"

// Usage
val message = greet("Alice")
```

### Default Parameters

```kotlin
fun createStudent(
    firstName: String,
    lastName: String,
    age: Int = 18,
    email: String = ""
): Student {
    return Student(firstName, lastName, age, email)
}

// Usage
val student1 = createStudent("Alice", "Johnson")
val student2 = createStudent("Bob", "Smith", age = 20, email = "bob@example.com")
```

### Named Arguments

```kotlin
// More readable
val student = createStudent(
    firstName = "Alice",
    lastName = "Johnson",
    email = "alice@example.com"
)
```

---

## Data Classes

Perfect for model objects (like C# POCOs):

```kotlin
data class Student(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String = ""
) {
    val fullName: String
        get() = "$firstName $lastName"
}

// Auto-generated methods:
val student1 = Student(1, "Alice", "Johnson", "alice@example.com")
val student2 = student1.copy(email = "newemail@example.com")  // Copy with changes

println(student1)  // Pretty toString()
// Output: Student(id=1, firstName=Alice, lastName=Johnson, email=alice@example.com)
```

**Free Features**:
- `equals()` / `hashCode()`
- `toString()`
- `copy()`
- `componentN()` (destructuring)

---

## Collections

### Lists

```kotlin
// Immutable list (read-only)
val students = listOf("Alice", "Bob", "Charlie")

// Mutable list
val names = mutableListOf("Alice", "Bob")
names.add("Charlie")
names.remove("Bob")

// Common operations
students.size             // 3
students[0]               // "Alice"
students.contains("Bob")  // true
students.isEmpty()        // false
```

### Maps

```kotlin
// Immutable map
val ages = mapOf(
    "Alice" to 25,
    "Bob" to 30
)

// Mutable map
val scores = mutableMapOf<String, Int>()
scores["Alice"] = 95
scores["Bob"] = 87

// Access
val aliceAge = ages["Alice"]  // Returns Int? (nullable)
val bobAge = ages.getValue("Bob")  // Returns Int or throws exception
```

### Sets

```kotlin
val uniqueNames = setOf("Alice", "Bob", "Alice")  // Only 2 elements
```

---

## Control Flow

### If Expression

```kotlin
// If as expression (returns value)
val message = if (score >= 50) {
    "Passed"
} else {
    "Failed"
}

// Single line
val grade = if (score >= 90) "A" else if (score >= 80) "B" else "C"
```

### When Expression (like switch)

```kotlin
fun getGrade(score: Int) = when {
    score >= 90 -> "A"
    score >= 80 -> "B"
    score >= 70 -> "C"
    score >= 60 -> "D"
    else -> "F"
}

// With specific values
when (day) {
    1 -> "Monday"
    2 -> "Tuesday"
    3, 4, 5 -> "Mid-week"
    else -> "Weekend"
}

// With types
when (value) {
    is String -> println("String: $value")
    is Int -> println("Number: $value")
    else -> println("Unknown type")
}
```

### Loops

```kotlin
// For loop
for (i in 1..5) {
    println(i)  // 1, 2, 3, 4, 5
}

// Loop through list
val students = listOf("Alice", "Bob", "Charlie")
for (student in students) {
    println(student)
}

// With index
for ((index, student) in students.withIndex()) {
    println("$index: $student")
}

// While loop
var count = 0
while (count < 5) {
    println(count)
    count++
}
```

---

## Lambda Expressions

Functions as values:

```kotlin
// Lambda syntax
val sum = { a: Int, b: Int -> a + b }
println(sum(3, 5))  // 8

// Higher-order functions
fun calculate(a: Int, b: Int, operation: (Int, Int) -> Int): Int {
    return operation(a, b)
}

val result = calculate(10, 5) { x, y -> x * y }  // 50
```

### Collection Operations

```kotlin
val numbers = listOf(1, 2, 3, 4, 5)

// Map (transform each element)
val doubled = numbers.map { it * 2 }  // [2, 4, 6, 8, 10]

// Filter (keep matching elements)
val evens = numbers.filter { it % 2 == 0 }  // [2, 4]

// Find
val firstEven = numbers.find { it % 2 == 0 }  // 2

// Any / All / None
val hasEven = numbers.any { it % 2 == 0 }  // true
val allPositive = numbers.all { it > 0 }    // true

// Sum / Max / Min
val total = numbers.sum()  // 15
val largest = numbers.max()  // 5
```

**Common in Android**:
```kotlin
// Find student by ID
val student = students.find { it.id == studentId }

// Filter adults
val adults = students.filter { it.age >= 18 }

// Map to names
val names = students.map { it.fullName }
```

---

## Classes and Objects

### Classes

```kotlin
class Student(
    val firstName: String,
    val lastName: String
) {
    var gpa: Double = 0.0
    
    fun displayInfo() {
        println("$firstName $lastName - GPA: $gpa")
    }
}

// Usage
val student = Student("Alice", "Johnson")
student.gpa = 3.8
student.displayInfo()
```

### Properties

```kotlin
class Rectangle(val width: Int, val height: Int) {
    // Computed property
    val area: Int
        get() = width * height
    
    // Property with backing field
    var color: String = "white"
        set(value) {
            println("Color changed to $value")
            field = value  // 'field' is the backing field
        }
}
```

### Object (Singleton)

```kotlin
object AppConfig {
    const val API_URL = "https://api.example.com"
    const val TIMEOUT = 30000
}

// Usage
val url = AppConfig.API_URL
```

---

## Extension Functions

Add methods to existing classes:

```kotlin
// Extend String class
fun String.isValidEmail(): Boolean {
    return this.contains("@") && this.contains(".")
}

// Usage
val email = "alice@example.com"
if (email.isValidEmail()) {
    println("Valid email")
}

// Common in Android
fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

// Usage in Activity
showToast("Hello!")
```

---

## Scope Functions

Useful for configuring objects:

```kotlin
// apply: Configure object
val student = Student("Alice", "Johnson").apply {
    gpa = 3.8
    email = "alice@example.com"
}

// also: Side effects
val numbers = mutableListOf(1, 2, 3).also {
    println("Initial list: $it")
}

// let: Transform and work with nullable
val length = email?.let {
    println("Email: $it")
    it.length
}

// with: Group function calls
with(student) {
    println(firstName)
    println(lastName)
    println(gpa)
}
```

---

## Coroutines (Async Programming)

**Brief intro** - More details in Week 11:

```kotlin
// Launch coroutine
viewModelScope.launch {
    val data = fetchDataFromNetwork()  // Suspend function
    updateUI(data)
}

// Suspend function (can be paused)
suspend fun fetchDataFromNetwork(): List<Student> {
    delay(1000)  // Simulated network delay
    return listOf(Student(1, "Alice", "Johnson"))
}
```

**Common in Android**:
- Network requests
- Database operations
- Long-running tasks

---

## Kotlin vs C# Quick Comparison

| Feature | Kotlin | C# |
|---------|--------|-----|
| **Immutable variable** | `val` | `const` / `readonly` |
| **Nullable type** | `String?` | `string?` |
| **String interpolation** | `"Hello $name"` | `$"Hello {name}"` |
| **Data classes** | `data class` | `record` |
| **Extension methods** | Built-in | Built-in |
| **Lambda** | `{ x -> x * 2 }` | `x => x * 2` |
| **Collections** | `listOf`, `mutableListOf` | `List<T>`, `new List<T>` |

---

## Key Kotlin Features for Compose

### Trailing Lambda

```kotlin
// If last parameter is lambda, move it outside parentheses
// Common pattern
Button(onClick = { /* action */ }) {
    Text("Click Me")
}

// Instead of
Button(onClick = { /* action */ }, content = { Text("Click Me") })
```

### Destructuring

```kotlin
data class Student(val name: String, val age: Int)

val (name, age) = Student("Alice", 25)
println("$name is $age years old")

// In loops
for ((id, student) in studentsMap) {
    println("$id: ${student.name}")
}
```

---

## Best Practices for Android

### ✅ Do
- Use `val` over `var` when possible (immutability)
- Leverage null safety (`?.`, `?:`, `!!`)
- Use data classes for model objects
- Use collection operations (`map`, `filter`) over loops
- Add meaningful variable names
- Use default parameters to reduce overloads

### ❌ Don't
- Use `!!` (force unwrap) unless absolutely certain
- Ignore nullable types warnings
- Create mutable collections when immutable works
- Use Java-style getters/setters (use properties)

---

## Resources

- [Kotlin Basics](https://kotlinlang.org/docs/basic-syntax.html)
- [Kotlin for Android](https://developer.android.com/kotlin)
- [Kotlin Koans (Practice)](https://play.kotlinlang.org/koans)
- [Kotlin Standard Library](https://kotlinlang.org/api/latest/jvm/stdlib/)

---

**Next**: [06-android-16-features.md](06-android-16-features.md) - What's new in Android 16

---

*Kotlin makes Android development more enjoyable and productive!*
