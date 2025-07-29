# Loan Calculator - Architecture Documentation

## System Architecture

### High-Level Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    JavaFX Application                      │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐      │
│  │   Launcher  │  │LoanCalculator│  │TestLoanClass│      │
│  │   (Entry)   │  │   (GUI)     │  │  (Console)  │      │
│  └─────────────┘  └─────────────┘  └─────────────┘      │
├─────────────────────────────────────────────────────────────┤
│                    Loan (Model)                           │
│              (Business Logic Layer)                       │
├─────────────────────────────────────────────────────────────┤
│                  JavaFX Framework                         │
│              (UI Framework Layer)                         │
├─────────────────────────────────────────────────────────────┤
│                    Java Runtime                           │
│              (Platform Layer)                             │
└─────────────────────────────────────────────────────────────┘
```

## Component Architecture

### 1. Launcher Class
**Purpose**: Application entry point
**Responsibilities**:
- Initialize the JavaFX application
- Delegate to the main application class
- Handle application startup

**Dependencies**:
- `LoanCalculator` class

### 2. LoanCalculator Class
**Purpose**: Main JavaFX GUI application
**Responsibilities**:
- Create and manage the user interface
- Handle user input and validation
- Coordinate with the Loan model
- Display calculation results

**Dependencies**:
- JavaFX framework
- `Loan` class for calculations
- JavaFX UI components

**Key Methods**:
- `start(Stage)`: Initialize the GUI
- `calculateLoanPayment()`: Process user input and display results
- `showError(String, String)`: Display error dialogs

### 3. Loan Class
**Purpose**: Business logic and loan calculations
**Responsibilities**:
- Perform loan payment calculations
- Store loan parameters
- Provide getter/setter methods
- Maintain loan state

**Dependencies**:
- Java standard library (Math, Date)

**Key Methods**:
- `getMonthlyPayment()`: Calculate monthly payment
- `getTotalPayment()`: Calculate total payment
- Constructor methods for loan creation

### 4. TestLoanClass
**Purpose**: Console-based testing interface
**Responsibilities**:
- Provide command-line interface
- Handle console input/output
- Test loan calculations
- Demonstrate loan functionality

**Dependencies**:
- Java standard library (Scanner)
- `Loan` class

## Data Flow Architecture

### GUI Data Flow
```
User Input → TextField → Validation → Loan Object → Calculation → Display Result
     ↓           ↓           ↓           ↓           ↓           ↓
  Annual     Number of    Positive    Create     Monthly/    Formatted
Interest    Years        Values      Loan       Total       Currency
Rate        Loan Amount              Object     Payment     Output
```

### Console Data Flow
```
Console Input → Scanner → Validation → Loan Object → Calculation → Console Output
     ↓           ↓           ↓           ↓           ↓           ↓
  Annual     Parse      Positive    Create     Monthly/    Formatted
Interest    Numbers    Values      Loan       Total       Text
Rate        Years      Loan Amount Object     Payment     Output
```

## Module Architecture

### Module Structure
```
com.acu.javafx.loancalculator
├── Launcher.java          # Application entry point
├── LoanCalculator.java    # Main GUI application
├── Loan.java             # Business logic model
└── TestLoanClass.java    # Console testing interface
```

### Module Dependencies
```
com.acu.javafx.loancalculator
├── requires javafx.controls
├── requires javafx.fxml
├── requires javafx.graphics
├── requires javafx.base
└── exports com.acu.javafx.loancalculator
```

## Build Architecture

### Maven Build Configuration
```
Project Structure
├── pom.xml                    # Maven configuration
├── src/
│   ├── main/
│   │   ├── java/             # Source code
│   │   └── resources/        # Resources
│   └── test/
│       └── java/             # Test code
├── target/                   # Build output
├── run.sh                    # Unix/Linux/macOS script
├── run.bat                   # Windows script
└── run_direct.sh            # Direct Java execution
```

### Cross-Platform Support
- **macOS**: Intel (x86_64) and Apple Silicon (ARM64)
- **Windows**: x86_64 and ARM64
- **Linux**: x86_64 and ARM64

## UI Architecture

### Layout Structure
```
GridPane (Main Container)
├── Row 0: Annual Interest Rate Label + TextField
├── Row 1: Number of Years Label + TextField
├── Row 2: Loan Amount Label + TextField
├── Row 3: Monthly Payment Label + TextField (Read-only)
├── Row 4: Total Payment Label + TextField (Read-only)
└── Row 5: Calculate Button
```

### Component Properties
- **Alignment**: CENTER for GridPane, BOTTOM_RIGHT for TextFields
- **Editable**: Monthly and Total Payment fields are read-only
- **Button Alignment**: RIGHT alignment for Calculate button
- **Window Size**: 400x250 pixels

## Error Handling Architecture

### Error Types
1. **Input Validation Errors**
   - Non-numeric input
   - Negative values
   - Zero values

2. **Calculation Errors**
   - Division by zero
   - Mathematical overflow
   - Invalid loan parameters

3. **System Errors**
   - JavaFX initialization failures
   - Memory allocation issues

### Error Handling Strategy
```
Exception → Catch Block → Error Type → User-Friendly Message → Display Dialog
     ↓           ↓           ↓              ↓                    ↓
  Number    Validate    Determine    Create Error    Show Alert
Format     Input       Error Type   Message        Dialog
Exception  Values      Category     Text           to User
```

## Performance Architecture

### Memory Management
- **Object Creation**: Minimal object creation during calculation
- **Resource Cleanup**: Proper disposal of JavaFX components
- **Memory Footprint**: Efficient use of memory for loan calculations

### UI Performance
- **Responsive Design**: Non-blocking calculations
- **Event Handling**: Efficient lambda expressions
- **Layout Management**: Optimized GridPane layout

## Security Architecture

### Input Validation
- **Type Checking**: Ensure numeric input
- **Range Validation**: Positive values only
- **Exception Handling**: Graceful error recovery

### Data Integrity
- **Immutable Objects**: Loan objects are immutable after creation
- **Validation**: All inputs validated before processing
- **Safe Operations**: Mathematical operations with bounds checking

## Testing Architecture

### Test Strategy
1. **Unit Tests**: Individual component testing
2. **Integration Tests**: Component interaction testing
3. **GUI Tests**: User interface testing
4. **Console Tests**: Command-line interface testing

### Test Coverage
- **Loan Class**: 100% method coverage
- **Calculation Logic**: All mathematical operations
- **Input Validation**: All validation scenarios
- **Error Handling**: All exception paths 