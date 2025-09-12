# Flat Precedence Calculator Algorithm

## Overview

The Flat Precedence Calculator implements a simple left-to-right evaluation algorithm that treats all operators (+, -, *, /) with equal precedence. This is fundamentally different from standard mathematical precedence rules.

## Algorithm Details

### 1. Expression Parsing

The algorithm first parses the input expression into tokens:

```java
private List<String> parseExpression(String expression) {
    List<String> tokens = new ArrayList<>();
    StringBuilder currentToken = new StringBuilder();
    
    for (int i = 0; i < expression.length(); i++) {
        char c = expression.charAt(i);
        
        if (Character.isDigit(c) || c == '.' || (c == '-' && (i == 0 || isOperator(expression.charAt(i - 1))))) {
            // Part of a number (including negative numbers)
            currentToken.append(c);
        } else if (isOperator(c)) {
            // Operator found, save current number if any
            if (currentToken.length() > 0) {
                tokens.add(currentToken.toString());
                currentToken = new StringBuilder();
            }
            tokens.add(String.valueOf(c));
        } else if (Character.isWhitespace(c)) {
            // Whitespace, save current number if any
            if (currentToken.length() > 0) {
                tokens.add(currentToken.toString());
                currentToken = new StringBuilder();
            }
        } else {
            throw new IllegalArgumentException("Invalid character in expression: " + c);
        }
    }
    
    // Add the last token if any
    if (currentToken.length() > 0) {
        tokens.add(currentToken.toString());
    }
    
    return tokens;
}
```

### 2. Flat Precedence Evaluation

The core algorithm evaluates expressions from left to right:

```java
private double evaluateFlatPrecedence(List<String> tokens) {
    if (tokens.size() < 3 || tokens.size() % 2 == 0) {
        throw new IllegalArgumentException("Invalid expression format");
    }
    
    // Start with the first number
    double result = Double.parseDouble(tokens.get(0));
    
    // Process operations from left to right
    for (int i = 1; i < tokens.size(); i += 2) {
        String operator = tokens.get(i);
        double operand = Double.parseDouble(tokens.get(i + 1));
        result = performOperation(result, operator, operand);
    }
    
    return result;
}
```

### 3. Step-by-Step Visualization

For educational purposes, the algorithm also provides step-by-step evaluation:

```java
public List<CalculationStep> calculateWithSteps(String expression) {
    List<String> tokens = parseExpression(expression.trim());
    List<CalculationStep> steps = new ArrayList<>();
    
    // Create a copy of tokens for step-by-step evaluation
    List<String> workingTokens = new ArrayList<>(tokens);
    int stepNumber = 1;
    
    // Process operations from left to right
    while (workingTokens.size() > 1) {
        // Find the first operator
        int operatorIndex = findFirstOperator(workingTokens);
        if (operatorIndex == -1) {
            break;
        }
        
        // Get the operation details
        String leftOperand = workingTokens.get(operatorIndex - 1);
        String operator = workingTokens.get(operatorIndex);
        String rightOperand = workingTokens.get(operatorIndex + 1);
        
        // Perform the calculation
        double leftValue = Double.parseDouble(leftOperand);
        double rightValue = Double.parseDouble(rightOperand);
        double result = performOperation(leftValue, operator, rightValue);
        
        // Create step description
        String stepExpression = String.format("%s %s %s", leftOperand, operator, rightOperand);
        String operation = String.format("%.2f %s %.2f", leftValue, operator, rightValue);
        
        steps.add(new CalculationStep(stepExpression, operation, result, stepNumber++));
        
        // Replace the three tokens (left, operator, right) with the result
        workingTokens.set(operatorIndex - 1, String.valueOf(result));
        workingTokens.remove(operatorIndex); // Remove operator
        workingTokens.remove(operatorIndex); // Remove right operand (index shifted)
    }
    
    return steps;
}
```

## Example Walkthrough

Let's trace through the evaluation of `4 + 3 - 2 * 10`:

### Step 1: Parsing
Input: `"4 + 3 - 2 * 10"`
Tokens: `["4", "+", "3", "-", "2", "*", "10"]`

### Step 2: Evaluation
1. Start with first number: `result = 4`
2. First operation: `4 + 3 = 7`
3. Second operation: `7 - 2 = 5`
4. Third operation: `5 * 10 = 50`

### Step 3: Step-by-Step Visualization
```
Step 1: 4.00 + 3.00 = 7.00
Step 2: 7.00 - 2.00 = 5.00
Step 3: 5.00 * 10.00 = 50.00
```

## Complexity Analysis

- **Time Complexity**: O(n) where n is the number of tokens
- **Space Complexity**: O(n) for token storage and step tracking
- **Parsing**: O(m) where m is the length of the input string

## Error Handling

The algorithm includes comprehensive error handling:

1. **Input Validation**: Null, empty, or invalid characters
2. **Number Format**: Invalid number representations
3. **Expression Format**: Malformed expressions
4. **Arithmetic Errors**: Division by zero
5. **Operator Validation**: Unknown operators

## Comparison with Standard Precedence

| Expression | Flat Precedence | Standard Precedence |
|------------|-----------------|-------------------|
| `4 + 3 - 2 * 10` | `50` | `-13` |
| `2 + 3 * 4 - 1` | `19` | `13` |
| `10 - 2 + 3 * 2` | `22` | `14` |

The flat precedence algorithm provides a different but consistent evaluation order that can be useful for educational purposes and specific use cases where left-to-right evaluation is desired.
