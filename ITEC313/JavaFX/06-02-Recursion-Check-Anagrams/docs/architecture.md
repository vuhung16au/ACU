# Application Architecture

## Table of Contents
1. [Overview](#overview)
2. [Architecture Diagram](#architecture-diagram)
3. [Component Design](#component-design)
4. [Class Structure](#class-structure)
5. [Data Flow](#data-flow)
6. [Design Patterns](#design-patterns)
7. [Module System](#module-system)
8. [Testing Architecture](#testing-architecture)

## Overview

The Anagram Checker Demo is a JavaFX application that demonstrates recursive anagram checking. The architecture follows the Model-View-Controller (MVC) pattern with clear separation of concerns between UI components, business logic, and data processing.

### Key Architectural Principles
- **Separation of Concerns**: UI, business logic, and data processing are separated
- **Single Responsibility**: Each class has a single, well-defined purpose
- **Testability**: Business logic is isolated and easily testable
- **Modularity**: Clear module boundaries and dependencies
- **Extensibility**: Easy to add new features and algorithms

## Architecture Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                    Anagram Checker Demo                     │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────────┐    ┌─────────────────┐                │
│  │   JavaFX UI     │    │  Business Logic │                │
│  │                 │    │                 │                │
│  │ AnagramChecker  │◄──►│  AnagramChecker │                │
│  │     Demo        │    │                 │                │
│  │                 │    │                 │                │
│  │ • Text Fields   │    │ • Recursive     │                │
│  │ • Buttons       │    │   Algorithm     │                │
│  │ • Labels        │    │ • Normalization │                │
│  │ • Output Area   │    │ • Validation    │                │
│  └─────────────────┘    └─────────────────┘                │
│           │                       │                        │
│           │                       │                        │
│           ▼                       ▼                        │
│  ┌─────────────────┐    ┌─────────────────┐                │
│  │   Styling       │    │   Testing       │                │
│  │                 │    │                 │                │
│  │ • CSS Styles    │    │ • JUnit Tests   │                │
│  │ • Themes        │    │ • Edge Cases    │                │
│  │ • Animations    │    │ • Performance   │                │
│  └─────────────────┘    └─────────────────┘                │
└─────────────────────────────────────────────────────────────┘
```

## Component Design

### 1. Presentation Layer (JavaFX UI)

#### AnagramCheckerDemo
- **Purpose**: Main application class and UI controller
- **Responsibilities**:
  - Initialize JavaFX application
  - Create and manage UI components
  - Handle user interactions
  - Coordinate between UI and business logic
  - Manage application lifecycle

#### UI Components
- **Text Fields**: Input for two words
- **Buttons**: Check Anagram, Clear, Exit
- **Labels**: Result display and instructions
- **Text Area**: Detailed analysis output

### 2. Business Logic Layer

#### AnagramChecker
- **Purpose**: Core anagram checking algorithms
- **Responsibilities**:
  - Implement recursive anagram checking
  - Provide alternative algorithms
  - Handle word normalization
  - Character frequency analysis
  - Utility methods for word processing

### 3. Data Layer

#### Word Processing
- **Normalization**: Remove spaces, convert case
- **Character Analysis**: Frequency counting, pattern matching
- **Validation**: Input validation and error handling

## Class Structure

### Class Diagram

```
┌─────────────────────────────────┐
│        AnagramCheckerDemo       │
├─────────────────────────────────┤
│ - word1Field: TextField         │
│ - word2Field: TextField         │
│ - checkAnagramBtn: Button       │
│ - clearBtn: Button              │
│ - exitBtn: Button               │
│ - resultLabel: Label            │
│ - outputArea: TextArea          │
│ - anagramChecker: AnagramChecker│
├─────────────────────────────────┤
│ + start(Stage): void            │
│ - createInputSection(): VBox    │
│ - createButtonSection(): HBox   │
│ - createResultSection(): VBox   │
│ - checkAnagram(): void          │
│ - clearAll(): void              │
│ - showResult(String, boolean)   │
│ - appendOutput(String): void    │
└─────────────────────────────────┘
                 │
                 │ uses
                 ▼
┌─────────────────────────────────┐
│        AnagramChecker           │
├─────────────────────────────────┤
├─────────────────────────────────┤
│ + areAnagrams(String, String):  │
│   boolean                       │
│ + areAnagramsWithFrequency(     │
│   String, String): boolean      │
│ + normalizeWord(String): String │
│ + getCharacterCounts(String):   │
│   String                        │
│ + findAllAnagrams(String,       │
│   List<String>): List<String>   │
│ + isPalindrome(String): boolean │
│ - areAnagramsRecursive(String,  │
│   String): boolean              │
│ - countCharactersRecursive(     │
│   String, Map): Map             │
│ - isPalindromeRecursive(String, │
│   int, int): boolean            │
└─────────────────────────────────┘
```

### Package Structure

```
com.acu.javafx.anagram/
├── AnagramCheckerDemo.java       # Main application class
├── AnagramChecker.java           # Business logic class
└── module-info.java              # Module configuration
```

## Data Flow

### 1. User Input Flow

```
User Input → Text Fields → Event Handler → Business Logic → Result Display
     │              │              │              │              │
     ▼              ▼              ▼              ▼              ▼
"listen"      word1Field    checkAnagram()   areAnagrams()   "✓ YES!"
"silent"      word2Field         │              │              │
                                 ▼              ▼              ▼
                            Validation    Recursive      UI Update
                                         Algorithm
```

### 2. Recursive Algorithm Flow

```
areAnagrams("listen", "silent")
    │
    ▼
normalizeWord("listen") → "listen"
normalizeWord("silent") → "silent"
    │
    ▼
length check: 6 == 6 ✓
    │
    ▼
areAnagramsRecursive("listen", "silent")
    │
    ▼
firstChar = 'l', index = 2
    │
    ▼
remaining1 = "isten", remaining2 = "sient"
    │
    ▼
areAnagramsRecursive("isten", "sient")
    │
    ▼
... (continues recursively)
    │
    ▼
areAnagramsRecursive("", "") → true (base case)
```

### 3. UI Update Flow

```
Algorithm Result → Result Processing → UI Update → User Feedback
        │                  │              │              │
        ▼                  ▼              ▼              ▼
    boolean true    showResult()    Label Update    Green Checkmark
        │                  │              │              │
        ▼                  ▼              ▼              ▼
   Analysis Data   appendOutput()   TextArea Update   Detailed Info
```

## Design Patterns

### 1. Model-View-Controller (MVC)

#### Model
- **AnagramChecker**: Contains business logic and data processing
- **Word Processing**: Data normalization and analysis

#### View
- **JavaFX UI Components**: Text fields, buttons, labels, text area
- **CSS Styling**: Visual appearance and themes

#### Controller
- **AnagramCheckerDemo**: Handles user interactions and coordinates between model and view

### 2. Strategy Pattern

```java
// Different anagram checking strategies
public boolean areAnagrams(String word1, String word2) {
    // Strategy 1: Recursive character matching
    return areAnagramsRecursive(word1, word2);
}

public boolean areAnagramsWithFrequency(String word1, String word2) {
    // Strategy 2: Character frequency counting
    return countCharactersRecursive(word1, new HashMap<>())
           .equals(countCharactersRecursive(word2, new HashMap<>()));
}
```

### 3. Template Method Pattern

```java
public boolean areAnagrams(String word1, String word2) {
    // Template method with common steps
    String normalized1 = normalizeWord(word1);
    String normalized2 = normalizeWord(word2);
    
    if (normalized1.length() != normalized2.length()) {
        return false;
    }
    
    // Delegate to specific implementation
    return areAnagramsRecursive(normalized1, normalized2);
}
```

### 4. Observer Pattern

```java
// JavaFX event handling
checkAnagramBtn.setOnAction(e -> checkAnagram());
clearBtn.setOnAction(e -> clearAll());
exitBtn.setOnAction(e -> Platform.exit());
```

## Module System

### Module Configuration

```java
module anagram.checker.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    
    exports com.acu.javafx.anagram;
}
```

### Module Dependencies

```
anagram.checker.demo
    │
    ├── javafx.controls (UI components)
    ├── javafx.fxml (FXML support)
    ├── javafx.graphics (Graphics and rendering)
    └── javafx.base (Base JavaFX functionality)
```

### Benefits of Module System
- **Encapsulation**: Clear module boundaries
- **Dependency Management**: Explicit dependencies
- **Security**: Controlled access to internal APIs
- **Performance**: Optimized class loading
- **Maintainability**: Clear separation of concerns

## Testing Architecture

### Test Structure

```
src/test/java/com/acu/javafx/anagram/
└── AnagramCheckerTest.java
    ├── testBasicAnagrams()
    ├── testNonAnagrams()
    ├── testCaseInsensitive()
    ├── testWithSpaces()
    ├── testEmptyStrings()
    ├── testSingleCharacter()
    ├── testSameWord()
    ├── testDifferentLengths()
    ├── testSpecialCharacters()
    ├── testNumbers()
    ├── testComplexAnagrams()
    ├── testAnagramsWithFrequency()
    ├── testNormalizeWord()
    ├── testGetCharacterCounts()
    ├── testFindAllAnagrams()
    ├── testIsPalindrome()
    ├── testPalindromeWithSpaces()
    ├── testEdgeCases()
    ├── testPerformance()
    └── testNullInputs()
```

### Testing Strategy

#### 1. Unit Testing
- **Business Logic**: Test AnagramChecker methods in isolation
- **Edge Cases**: Empty strings, null inputs, single characters
- **Boundary Conditions**: Different lengths, special characters

#### 2. Integration Testing
- **UI Integration**: Test UI components with business logic
- **Data Flow**: Test complete user interaction flows
- **Error Handling**: Test error scenarios and recovery

#### 3. Performance Testing
- **Algorithm Performance**: Test with various input sizes
- **Memory Usage**: Monitor memory consumption
- **Response Time**: Ensure acceptable user experience

### Test Categories

#### Functional Tests
- Basic anagram checking
- Case insensitivity
- Space handling
- Special characters

#### Non-Functional Tests
- Performance with large inputs
- Memory usage
- Error handling
- Edge cases

#### Regression Tests
- Ensure existing functionality works
- Prevent breaking changes
- Validate bug fixes

## Extensibility

### Adding New Algorithms

1. **Create New Method**: Add to AnagramChecker class
2. **Add UI Button**: Create button in AnagramCheckerDemo
3. **Add Event Handler**: Connect button to new method
4. **Add Tests**: Create test cases for new algorithm
5. **Update Documentation**: Document new functionality

### Adding New Features

1. **Word Lists**: Load from file or database
2. **Anagram Generation**: Find all possible anagrams
3. **History**: Keep track of previous checks
4. **Export**: Save results to file
5. **Themes**: Multiple UI themes

### Configuration Options

1. **Case Sensitivity**: Configurable case handling
2. **Space Handling**: Configurable space processing
3. **Algorithm Selection**: Choose between different algorithms
4. **Performance Settings**: Adjust for different use cases

## Security Considerations

### Input Validation
- **Null Checks**: Handle null inputs gracefully
- **Length Limits**: Prevent extremely long inputs
- **Character Validation**: Handle special characters appropriately
- **Memory Limits**: Prevent memory exhaustion

### Error Handling
- **Graceful Degradation**: Continue operation despite errors
- **User Feedback**: Clear error messages
- **Logging**: Log errors for debugging
- **Recovery**: Provide ways to recover from errors

## Performance Considerations

### Algorithm Selection
- **Recursive Matching**: Good for educational purposes
- **Frequency Counting**: Better for performance
- **Sorting**: Simple but less efficient
- **Array Counting**: Most efficient for limited character sets

### Memory Management
- **String Operations**: Minimize string creation
- **Recursive Depth**: Monitor call stack usage
- **Garbage Collection**: Efficient object lifecycle
- **Resource Cleanup**: Proper resource management

### UI Responsiveness
- **Background Processing**: Use threads for long operations
- **Progress Indicators**: Show progress for long operations
- **Cancellation**: Allow users to cancel operations
- **Responsive Design**: Adapt to different screen sizes
