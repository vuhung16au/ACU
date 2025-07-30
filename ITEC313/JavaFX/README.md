# JavaFX Learning Repository

A comprehensive collection of JavaFX examples and demonstrations covering fundamental concepts, UI controls, event handling, animations, and advanced data structures. This repository serves as a complete learning resource for JavaFX development and related Java programming concepts.

## 📋 Overview

This repository contains a structured collection of JavaFX projects organized by topic, from basic "Hello World" applications to complex data structures and algorithms. Each project demonstrates specific JavaFX concepts and programming techniques, making it an ideal resource for learning JavaFX development.

### 🎯 Learning Objectives

- **JavaFX Fundamentals**: Stage, Scene, Nodes, and basic UI components
- **Event Handling**: Mouse, keyboard, and custom event processing
- **UI Controls**: Buttons, text fields, sliders, and advanced controls
- **Animations**: Transitions, timelines, and interactive animations
- **Data Structures**: Lists, stacks, queues, trees, and algorithms
- **File I/O**: Text and binary file operations
- **Advanced Topics**: Generics, recursion, and efficient algorithms

## 🚀 How to Build and Run

### Prerequisites

- **Java**: OpenJDK 11 or higher (recommended: OpenJDK 24)
- **Maven**: Version 3.6+ (recommended: 3.9+)
- **JavaFX**: Automatically managed by Maven dependencies

### Quick Start

1. **Clone the repository**:
   ```bash
   git clone https://github.com/vuhung16au/ACU/
   cd JavaFX
   ```

2. **Navigate to any project folder**:
   ```bash
   cd 01-01-JavaFX-HelloWorld/demo
   ```

You will see a `pom.xml` file. This is a Maven project file. It is used to manage the project dependencies and build the project.

3. **Build and run**:
   ```bash
   # Using Maven (recommended)
   mvn clean compile
   mvn javafx:run
   
   # Or use provided scripts
   ./run.sh          # macOS/Linux
   run.bat           # Windows
   ```

### Platform-Specific Instructions

#### macOS
```bash
# Install dependencies
brew install openjdk@24 maven

# Set JAVA_HOME
export JAVA_HOME=$(/usr/libexec/java_home -v 24)

# Run any project
cd 01-01-JavaFX-HelloWorld/demo

```

#### Windows
```bash
# Download and install OpenJDK 24 and Maven
# Set environment variables in System Properties

# Run any project
cd 01-01-JavaFX-HelloWorld/demo
run.bat
```

#### Linux
```bash
# Ubuntu/Debian
sudo apt install openjdk-24-jdk maven

# Fedora/RHEL
sudo dnf install java-24-openjdk-devel maven

# Run any project
cd 01-01-JavaFX-HelloWorld/demo
./run.sh
```

## 📁 Project Structure and Descriptions

### 🎨 JavaFX Fundamentals (01-xx Series)

#### 01-01-JavaFX-HelloWorld
- **Description**: Basic JavaFX "Hello World" application
- **Concepts**: Stage, Scene, basic application structure
- **Features**: Simple window with text display
- **Learning**: JavaFX application lifecycle and basic setup

#### 01-02-JavaFX.Button
- **Description**: Interactive button demonstration with counter
- **Concepts**: Button controls, event handling, FXML layout
- **Features**: Click counter, dynamic text updates, reset functionality
- **Learning**: Event-driven programming and UI interaction

#### 01-03-Panes.UI.Controls.Shapes
- **Description**: Comprehensive demonstration of JavaFX panes, controls, and shapes
- **Concepts**: BorderPane, VBox, HBox, various UI controls, 2D shapes
- **Features**: Interactive shape manipulation, color picker, sliders, real-time updates
- **Learning**: Layout management, UI controls, and 2D graphics

#### 01-04-NodeStyleRotateDemo
- **Description**: CSS styling and node rotation examples
- **Concepts**: CSS styling, node rotation, property binding
- **Features**: Interactive rotation controls, styled components, hover effects
- **Learning**: Visual customization and dynamic transformations

#### 01-05-MoreShapes
- **Description**: Additional shape demonstrations
- **Concepts**: Advanced 2D shapes and graphics
- **Features**: Various geometric shapes and patterns

#### 01-06-TextFontColor
- **Description**: Text formatting and color demonstrations
- **Concepts**: Text styling, font management, color properties
- **Features**: Dynamic text formatting and color changes

#### 01-07-ImageView
- **Description**: Image display and manipulation
- **Concepts**: ImageView, image loading, image properties
- **Features**: Image display with various properties

#### 01-08-LayoutPanes
- **Description**: Layout management demonstrations
- **Concepts**: Various layout panes, positioning, spacing
- **Features**: Different layout strategies and arrangements

#### 01-09-ClockPane
- **Description**: Clock display implementation
- **Concepts**: Custom drawing, time-based updates
- **Features**: Analog clock display with real-time updates

### 🎮 Event Handling and Animation (02-xx Series)

#### 02-01-HandleEvent
- **Description**: Basic event handling examples
- **Concepts**: Mouse events, keyboard events, event processing
- **Features**: Various event type demonstrations

#### 02-02-ControlCircleWithoutEventHandling
- **Description**: Circle control without event handling
- **Concepts**: Basic UI without event processing
- **Features**: Static circle display

#### 02-03-ControlCircle
- **Description**: Interactive circle control with event handling
- **Concepts**: Event-driven circle manipulation
- **Features**: Click to move, resize, and control circle

#### 02-04-ShowInnerClass
- **Description**: Inner class event handling demonstration
- **Concepts**: Inner classes, event handlers, class organization
- **Features**: Multiple event handling approaches

#### 02-05-AnonymousHandlerDemo
- **Description**: Anonymous event handler examples
- **Concepts**: Anonymous classes, lambda expressions
- **Features**: Different event handling patterns

#### 02-06-LoanCalculator
- **Description**: Financial calculator with JavaFX
- **Concepts**: Form handling, calculations, user input
- **Features**: Loan calculation with real-time updates

#### 02-07-KeyMouseEventDemo
- **Description**: Advanced keyboard and mouse event handling
- **Concepts**: Key codes, mouse coordinates, event properties
- **Features**: Comprehensive event demonstration

#### 02-08-ControlCircleWithMouseAndKey
- **Description**: Circle control with mouse and keyboard
- **Concepts**: Multi-input event handling
- **Features**: Combined mouse and keyboard controls

#### 02-09-DisplayResizableClock
- **Description**: Resizable clock with event handling
- **Concepts**: Window resizing, dynamic updates
- **Features**: Responsive clock display

#### 02-10-PathTransitionDemo
- **Description**: Path-based animation transitions
- **Concepts**: Path transitions, animation paths
- **Features**: Object movement along defined paths

#### 02-11-FlagRisingAnimation
- **Description**: Flag raising animation
- **Concepts**: Custom animations, visual effects
- **Features**: Animated flag movement

#### 02-12-FadeTransitionDemo
- **Description**: Fade in/out animations
- **Concepts**: Fade transitions, opacity changes
- **Features**: Smooth fade effects

#### 02-13-TimelineDemo
- **Description**: Timeline-based animations
- **Concepts**: Animation timelines, keyframes
- **Features**: Complex animation sequences

#### 02-14-ClockAnimation
- **Description**: Animated clock with moving hands
- **Concepts**: Time-based animations, rotation
- **Features**: Real-time clock animation

#### 02-15-BouncingBall
- **Description**: Bouncing ball animation
- **Concepts**: Physics simulation, collision detection
- **Features**: Realistic ball bouncing behavior

### 🎛️ Advanced UI Controls and Multimedia (03-xx Series)

#### 03-01-JavaFx-ControlUI-Multimedia-TicTacToe
- **Description**: Comprehensive UI controls and multimedia demonstration
- **Concepts**: All major JavaFX controls, media playback, game development
- **Features**: 
  - Basic controls (labels, buttons, checkboxes, radio buttons)
  - Input controls (text fields, password fields, text areas)
  - Selection controls (combo boxes, list views, scroll bars, sliders)
  - Multimedia playback with controls
  - Complete Tic-Tac-Toe game implementation
- **Learning**: Complete UI control mastery and multimedia integration

### 🔧 Java Programming Fundamentals (04-xx to 12-xx Series)

#### 04-01-Generics
- **Description**: Java generics concepts and implementation
- **Concepts**: Generic classes, bounded types, wildcards, type erasure
- **Features**: Generic stack implementation, matrix operations
- **Learning**: Type safety and generic programming

#### 05-01-FileClass
- **Description**: File I/O operations and file handling
- **Concepts**: File class, text I/O, PrintWriter, Scanner
- **Features**: File reading/writing, command-line file operations
- **Learning**: File system operations and text processing

#### 05-02-BinaryIO
- **Description**: Binary file input/output operations
- **Concepts**: Binary streams, data serialization
- **Features**: Binary file reading and writing
- **Learning**: Binary data handling

#### 06-01-Recursion
- **Description**: Recursive programming techniques
- **Concepts**: Recursive methods, call stack, mathematical functions
- **Features**: 
  - Factorial and Fibonacci calculations
  - Recursive sorting and searching
  - Tower of Hanoi problem
  - Directory size calculation
- **Learning**: Recursive problem-solving approaches

#### 07-01-List-Stack-Queue-PriorityQueue
- **Description**: Fundamental data structures implementation
- **Concepts**: Lists, stacks, queues, priority queues
- **Features**: 
  - ArrayList vs LinkedList comparisons
  - Stack operations and expression evaluation
  - Queue implementations and priority queues
- **Learning**: Data structure fundamentals and collections

#### 07-02-Sets-Maps
- **Description**: Set and Map data structures
- **Concepts**: HashSet, TreeSet, HashMap, TreeMap
- **Features**: Set operations, map key-value pairs
- **Learning**: Advanced collection types

#### 08-01-DevelopingEfficientAlgorithms
- **Description**: Algorithm efficiency and optimization
- **Concepts**: Time complexity, algorithm analysis
- **Features**: Algorithm performance comparisons
- **Learning**: Algorithm design and analysis

#### 09-01-Sorting
- **Description**: Sorting algorithm implementations
- **Concepts**: Various sorting algorithms, performance analysis
- **Features**: Multiple sorting method demonstrations
- **Learning**: Algorithm implementation and comparison

#### 10-01-Implementing-Lists-Stacks-Queues-PriorityQueues
- **Description**: Custom data structure implementations
- **Concepts**: Custom implementations of fundamental data structures
- **Features**: Hand-crafted data structure classes
- **Learning**: Data structure internals and implementation

#### 11-01-BinarySearchTrees
- **Description**: Binary search tree implementation
- **Concepts**: BST operations, tree traversal, balancing
- **Features**: Tree insertion, deletion, and search operations
- **Learning**: Tree data structures and algorithms

#### 12-01-AVL-Trees
- **Description**: Self-balancing AVL tree implementation
- **Concepts**: AVL tree properties, rotations, balancing
- **Features**: 
  - LL, LR, RR, RL rotation types
  - Automatic rebalancing after insertions/deletions
  - Logarithmic time complexity operations
- **Learning**: Advanced tree balancing techniques

#### 12-02-Hashing
- **Description**: Hash table and hashing algorithms
- **Concepts**: Hash functions, collision resolution, hash tables
- **Features**: Hash table implementation and operations
- **Learning**: Hash-based data structures

## 🛠️ Development Guidelines

### Project Structure
Each project follows a consistent structure:
```
project-name/
├── src/
│   ├── main/
│   │   ├── java/          # Java source files
│   │   └── resources/     # FXML, CSS, media files
│   └── test/              # Unit tests
├── images/                # Screenshots and documentation
├── pom.xml               # Maven configuration
├── README.md             # Project documentation
└── run scripts           # Platform-specific run scripts
```

### Best Practices
- **Modular Design**: Each project focuses on specific concepts
- **Cross-Platform**: All projects work on macOS, Windows, and Linux
- **Documentation**: Comprehensive README files for each project
- **Testing**: Unit tests where applicable
- **Clean Code**: Well-structured, readable code examples

## 🎓 Educational Value

This repository serves as a complete curriculum for:
- **JavaFX Development**: From basics to advanced UI creation
- **Event-Driven Programming**: Interactive application development
- **Animation and Graphics**: Visual programming techniques
- **Data Structures**: Implementation and usage of fundamental structures
- **Algorithm Design**: Efficient problem-solving approaches
- **Software Engineering**: Best practices and project organization

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test on multiple platforms
5. Submit a pull request

## TODO 

This repo is implemented and tested on macOS Silicon (ARM64).
Testing and Pull Request are welcome for other platforms (Windows, Linux, etc.) are welcome.

## 📄 License

MIT License

---

**Note**: This repository provides a comprehensive learning path for JavaFX development and related Java programming concepts, suitable for both beginners and advanced learners. 