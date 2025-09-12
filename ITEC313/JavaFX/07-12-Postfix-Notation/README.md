## 07-12 — Postfix Notation

JavaFX application that evaluates postfix expressions with interactive step-by-step visualization of stack operations. Perfect for learning how postfix notation works!

### What is Postfix Notation?

Postfix notation (also called Reverse Polish Notation) is a way of writing mathematical expressions without parentheses. Instead of `(1 + 2) * 3`, you write `1 2 + 3 *`. The expression is evaluated using a stack data structure.

### Core Algorithm (short and sharp)

1. **Scan left-to-right**: Process each token in the expression
2. **Push operands**: When you see a number, push it onto the stack
3. **Apply operators**: When you see an operator, pop two operands, apply the operation, push the result
4. **Final result**: The single value left on the stack is the answer

### Interactive Visualization Features

- **📊 Stack Visualization**: Visual stack showing values as blue rectangles (top to bottom)
- **👆 Step-by-Step Control**: Click "Step" to advance through each operation manually
- **🎯 Current Token Display**: Shows which token is currently being processed
- **📝 Operation Descriptions**: Detailed explanations of what happens at each step
- **🔄 Reset Functionality**: Start over with the same expression or try a new one
- **⚡ Instant Evaluation**: See the final result immediately, then step through to understand the process

### How to Use

1. **Enter Expression**: Type a postfix expression in the text field (e.g., `1 2 + 3 *`)
2. **Click Evaluate**: The app processes the expression and shows the result
3. **Step Through**: Click "Step" to see each operation one at a time
4. **Watch the Stack**: Observe how values are pushed and popped
5. **Read Descriptions**: Understand what each operation does
6. **Try New Expressions**: Enter different expressions to practice

### Run the Application

```bash
# Compile and test
mvn clean compile
mvn test

# Launch the JavaFX application
mvn -pl 07-12-Postfix-Notation javafx:run
```

### Example Walkthrough

**Input**: `1 2 + 3 *`

**Step-by-Step Visualization**:
- **Step 1**: Push `1` onto stack → Stack: `[1]`
- **Step 2**: Push `2` onto stack → Stack: `[1, 2]`
- **Step 3**: Pop `2` and `1`, compute `1 + 2 = 3`, push result → Stack: `[3]`
- **Step 4**: Push `3` onto stack → Stack: `[3, 3]`
- **Step 5**: Pop `3` and `3`, compute `3 * 3 = 9`, push result → Stack: `[9]`
- **Result**: `9`

### More Examples to Try

- `3 4 + 2 *` → Result: 14
- `5 1 2 + 4 * + 3 -` → Result: 14
- `2 3 4 + *` → Result: 14
- `1 2 3 4 + + +` → Result: 10

### Technical Implementation

- **PostfixEvaluator**: Core logic with step-by-step evaluation
- **Step Class**: Captures each operation with stack state and description
- **JavaFX UI**: Interactive visualization with stack display and controls
- **Comprehensive Tests**: Verify both evaluation logic and visualization steps

### Educational Value

This application is perfect for:
- Understanding postfix notation
- Learning stack data structures
- Visualizing algorithm execution
- Practicing with different expressions
- Seeing how mathematical expressions are processed
