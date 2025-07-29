# Loan Calculator - Main Concepts

## Overview

The Loan Calculator is a JavaFX application that demonstrates loan calculation functionality. It provides both a graphical user interface (GUI) and a console-based interface for calculating loan payments.

## Core Concepts

### 1. Loan Calculation Formula

The application uses the standard loan payment formula:

```
Monthly Payment = P × (r × (1 + r)^n) / ((1 + r)^n - 1)
```

Where:
- P = Principal (loan amount)
- r = Monthly interest rate (annual rate ÷ 12 ÷ 100)
- n = Total number of payments (years × 12)

### 2. JavaFX Architecture

The application follows the Model-View-Controller (MVC) pattern:

- **Model**: `Loan` class handles the business logic and calculations
- **View**: JavaFX UI components (GridPane, TextField, Button, Label)
- **Controller**: Event handlers in `LoanCalculator` class

### 3. Input Validation

The application includes comprehensive input validation:
- Numeric value checking
- Positive value validation
- Exception handling for invalid inputs
- User-friendly error messages

### 4. Cross-Platform Compatibility

The application is designed to run on multiple platforms:
- macOS (Intel and Apple Silicon)
- Windows (x86_64 and ARM64)
- Linux (x86_64 and ARM64)

## Key Features

### GUI Features
- Clean, intuitive interface
- Real-time calculation
- Formatted currency output
- Error dialogs for invalid input
- Responsive layout

### Console Features
- Interactive command-line interface
- Input validation
- Detailed error reporting
- Graceful error handling

## Technical Implementation

### JavaFX Components Used
- `GridPane`: Main layout container
- `TextField`: Input and output fields
- `Button`: Calculate action trigger
- `Label`: Field descriptions
- `Alert`: Error message dialogs

### Event Handling
- Lambda expressions for button click events
- Exception handling for calculation errors
- Input validation before processing

### Modular Design
- Separate `Loan` class for business logic
- `LoanCalculator` for GUI implementation
- `TestLoanClass` for console testing
- `Launcher` as application entry point

## Design Patterns

### 1. Single Responsibility Principle
Each class has a single, well-defined responsibility:
- `Loan`: Handles loan calculations
- `LoanCalculator`: Manages GUI and user interaction
- `TestLoanClass`: Provides console testing interface

### 2. Separation of Concerns
- Business logic separated from UI logic
- Input validation separated from calculation logic
- Error handling separated from main functionality

### 3. Error Handling Strategy
- Try-catch blocks for exception handling
- User-friendly error messages
- Graceful degradation on errors
- Input validation before processing

## Performance Considerations

### Memory Management
- Efficient object creation and disposal
- Minimal memory footprint
- Proper resource cleanup

### UI Responsiveness
- Non-blocking calculations
- Immediate user feedback
- Responsive error handling

## Security Considerations

### Input Validation
- Numeric input validation
- Range checking for reasonable values
- Exception handling for malformed input

### Data Integrity
- Immutable loan objects after creation
- Validation before calculation
- Safe mathematical operations 