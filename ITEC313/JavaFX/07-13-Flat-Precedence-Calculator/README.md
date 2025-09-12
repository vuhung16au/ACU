# Flat Precedence Calculator - JavaFX Application

## Overview

This JavaFX application implements a calculator that treats all operators (+, -, *, /) with **equal precedence**, evaluating expressions from left to right. This is different from standard mathematical precedence where multiplication and division have higher precedence than addition and subtraction.

## Key Concept: Flat Precedence

In flat precedence, all operators are treated equally and evaluated from left to right:

**Example: `4 + 3 - 2 * 10`**
- Standard precedence: `4 + 3 - (2 * 10) = 4 + 3 - 20 = -13`
- **Flat precedence**: `((4 + 3) - 2) * 10 = (7 - 2) * 10 = 5 * 10 = 50`

# How to run the app
```bash
# Run the application
mvn clean javafx:run

# Run tests
mvn test

# Build JAR file
mvn clean package
```

# Example Expressions

| Expression | Flat Precedence Result | Standard Precedence Result |
|------------|------------------------|----------------------------|
| `4 + 3 - 2 * 10` | `50` | `-13` |
| `2 + 3 * 4 - 1` | `19` | `13` |
| `10 - 2 + 3 * 2` | `22` | `14` |
| `12 / 3 + 2` | `6` | `6` |
| `20 - 4 / 2` | `8` | `18` |

# Supported Operations
- **Addition**: `+`
- **Subtraction**: `-`
- **Multiplication**: `*`
- **Division**: `/`
- **Negative Numbers**: `-5`, `-3.14`
- **Decimal Numbers**: `3.14`, `2.5`

# Input Format
- **With Spaces**: `4 + 3 - 2 * 10`
- **Without Spaces**: `4+3-2*10`
- **Mixed**: `4+ 3 -2* 10`

# Technical Details

# Algorithm
The calculator uses a simple left-to-right evaluation algorithm:

1. **Parse Expression**: Split into numbers and operators
2. **Validate Input**: Check for valid numbers and operators
3. **Evaluate Left-to-Right**: Process operations in order
4. **Return Result**: Final calculated value

# Error Handling
- **Invalid Characters**: Non-numeric, non-operator characters
- **Division by Zero**: Arithmetic exception
- **Empty Expressions**: Null or empty input
- **Malformed Numbers**: Invalid number formats


# Run the application
```
mvn javafx:run
```

