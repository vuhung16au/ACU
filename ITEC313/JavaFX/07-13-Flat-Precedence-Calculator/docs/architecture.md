# Flat Precedence Calculator Architecture

## Overview

The Flat Precedence Calculator is built using a clean, modular architecture that separates concerns between the user interface and the calculation logic. This design makes the code maintainable, testable, and extensible.

## Architecture Components

### 1. Presentation Layer (JavaFX UI)

**FlatPrecedenceCalculator.java**
- Main JavaFX application class
- Handles user interface and user interactions
- Manages the application lifecycle
- Delegates calculations to the engine

**Responsibilities:**
- Create and manage UI components
- Handle user input events
- Display results and calculation steps
- Error handling and user feedback

### 2. Business Logic Layer

**FlatPrecedenceCalculatorEngine.java**
- Core calculation logic
- Expression parsing and evaluation
- Step-by-step calculation tracking
- Input validation and error handling

**Responsibilities:**
- Parse mathematical expressions
- Implement flat precedence algorithm
- Generate calculation steps for visualization
- Validate input and handle errors

### 3. Data Transfer Objects

**CalculationStep.java**
- Immutable data class for calculation steps
- Contains expression, operation, result, and step number
- Used for step-by-step visualization

## Design Patterns

### 1. Separation of Concerns
- **UI Layer**: Handles presentation and user interaction
- **Business Layer**: Contains calculation logic
- **Data Layer**: Simple data structures for information transfer

### 2. Single Responsibility Principle
- Each class has a single, well-defined responsibility
- Calculator engine only handles calculations
- UI class only handles presentation
- CalculationStep only holds data

### 3. Immutable Data Objects
- CalculationStep is immutable once created
- Prevents accidental modification of calculation history
- Ensures data integrity

## Class Diagram

```
┌─────────────────────────────────────┐
│        FlatPrecedenceCalculator     │
│                                     │
│  - expressionInput: TextField       │
│  - resultArea: TextArea             │
│  - stepsArea: TextArea              │
│  - calculator: CalculatorEngine     │
│                                     │
│  + start(Stage): void               │
│  + evaluateExpression(): void       │
│  - createInputSection(): VBox       │
│  - createResultSection(): VBox      │
│  - createStepsSection(): VBox       │
└─────────────────────────────────────┘
                    │
                    │ uses
                    ▼
┌─────────────────────────────────────┐
│    FlatPrecedenceCalculatorEngine   │
│                                     │
│  + calculate(String): double        │
│  + calculateWithSteps(String): List │
│  - parseExpression(String): List    │
│  - evaluateFlatPrecedence(List): double │
│  - performOperation(double, String, double): double │
│  - findFirstOperator(List): int     │
│  - isOperator(char): boolean        │
│  - isOperator(String): boolean      │
└─────────────────────────────────────┘
                    │
                    │ creates
                    ▼
┌─────────────────────────────────────┐
│         CalculationStep             │
│                                     │
│  - expression: String               │
│  - operation: String                │
│  - result: double                   │
│  - stepNumber: int                  │
│                                     │
│  + getExpression(): String          │
│  + getOperation(): String           │
│  + getResult(): double              │
│  + getStepNumber(): int             │
│  + toString(): String               │
└─────────────────────────────────────┘
```

## Data Flow

### 1. User Input Flow
```
User Input → TextField → evaluateExpression() → Calculator Engine → Result Display
```

### 2. Calculation Flow
```
Expression String → Parse Tokens → Validate → Evaluate Left-to-Right → Return Result
```

### 3. Step Visualization Flow
```
Expression String → Parse Tokens → Generate Steps → Display Steps in UI
```

## Error Handling Strategy

### 1. Input Validation
- **Null/Empty Input**: Checked at entry point
- **Invalid Characters**: Detected during parsing
- **Malformed Numbers**: Caught during number parsing
- **Invalid Expressions**: Validated before evaluation

### 2. Arithmetic Errors
- **Division by Zero**: Caught during operation execution
- **Overflow/Underflow**: Handled by Java's built-in mechanisms

### 3. User Feedback
- **Error Messages**: Displayed in result area
- **Clear Indication**: Steps area shows "No calculation steps available due to error"
- **Non-blocking**: Errors don't crash the application

## Testing Architecture

### 1. Unit Tests
- **CalculatorEngineTest**: Tests core calculation logic
- **Isolated Testing**: Each component tested independently
- **Comprehensive Coverage**: All methods and edge cases tested

### 2. Test Categories
- **Basic Operations**: Addition, subtraction, multiplication, division
- **Flat Precedence**: Verification of left-to-right evaluation
- **Edge Cases**: Division by zero, invalid expressions
- **Step Visualization**: Calculation step verification
- **Error Handling**: Invalid input scenarios

## Extensibility

### 1. Adding New Operators
To add a new operator (e.g., modulo `%`):

1. **Update Parser**: Add operator to `isOperator()` methods
2. **Update Evaluator**: Add case to `performOperation()` method
3. **Add Tests**: Create test cases for the new operator
4. **Update Documentation**: Document the new operator

### 2. Adding New Features
- **Parentheses Support**: Extend parser to handle grouping
- **Function Support**: Add support for mathematical functions
- **History**: Track calculation history
- **Export**: Save results to file

### 3. UI Enhancements
- **Keyboard Shortcuts**: Add keyboard navigation
- **Themes**: Support for different UI themes
- **Responsive Design**: Adapt to different window sizes
- **Accessibility**: Add screen reader support

## Performance Considerations

### 1. Memory Usage
- **Token Storage**: O(n) where n is number of tokens
- **Step Tracking**: O(m) where m is number of operations
- **UI Components**: Minimal memory footprint

### 2. Processing Time
- **Parsing**: O(k) where k is input string length
- **Evaluation**: O(n) where n is number of operations
- **Step Generation**: O(n) for step-by-step visualization

### 3. Optimization Opportunities
- **Caching**: Cache parsed expressions for repeated calculations
- **Lazy Evaluation**: Only generate steps when needed
- **String Building**: Use StringBuilder for large result strings

## Security Considerations

### 1. Input Sanitization
- **Character Validation**: Only allow valid mathematical characters
- **Length Limits**: Prevent extremely long expressions
- **Number Validation**: Ensure numbers are within reasonable bounds

### 2. Error Information
- **Safe Error Messages**: Don't expose internal implementation details
- **User-Friendly**: Provide clear, actionable error messages
- **Logging**: Log errors for debugging without exposing to users

## Future Enhancements

### 1. Advanced Features
- **Scientific Calculator**: Add trigonometric and logarithmic functions
- **Variable Support**: Allow variables in expressions
- **Graphing**: Visualize mathematical functions
- **Unit Conversion**: Support for different units

### 2. Integration
- **Database**: Store calculation history
- **Export**: Save results to various formats
- **API**: Provide programmatic access
- **Web Version**: Create web-based version

### 3. User Experience
- **Undo/Redo**: Support for operation history
- **Favorites**: Save frequently used expressions
- **Templates**: Pre-defined expression templates
- **Help System**: Built-in help and examples
