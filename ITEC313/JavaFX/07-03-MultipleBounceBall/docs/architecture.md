# Multiple Bounce Ball - Architecture and Design Patterns

## System Architecture

### 1. Application Structure

```
MultipleBounceBall (Application)
├── MultipleBallPane (Custom Pane)
│   ├── Timeline (Animation Engine)
│   ├── Ball Collection (List<Node>)
│   └── Event Handlers
├── UI Components
│   ├── ScrollBar (Speed Control)
│   ├── Button "+" (Add Ball)
│   └── Button "-" (Remove Ball)
└── Layout (BorderPane)
```

### 2. Component Responsibilities

#### MultipleBounceBall (Main Application)
- **Purpose**: JavaFX Application entry point
- **Responsibilities**:
  - Initialize the primary stage
  - Set up UI layout and components
  - Configure event handlers
  - Manage application lifecycle

#### MultipleBallPane (Custom Pane)
- **Purpose**: Manages ball animation and collection
- **Responsibilities**:
  - Ball lifecycle management (add/remove)
  - Animation control (play/pause/speed)
  - Boundary collision detection
  - Ball movement calculations
  - Event handling for mouse interactions

#### Ball (Custom Circle)
- **Purpose**: Represents individual bouncing balls
- **Responsibilities**:
  - Position tracking (x, y coordinates)
  - Velocity management (dx, dy vectors)
  - Visual representation (color, size)
  - Collision response

## Design Patterns

### 1. Observer Pattern

#### Event Handling
```java
// Button event observers
btAdd.setOnAction(e -> ballPane.add());
btSubtract.setOnAction(e -> ballPane.subtract());

// Mouse event observers
ballPane.setOnMousePressed(e -> ballPane.pause());
ballPane.setOnMouseReleased(e -> ballPane.play());
```

#### Property Binding
```java
// Scroll bar value bound to animation rate
ballPane.rateProperty().bind(sbSpeed.valueProperty());
```

### 2. Strategy Pattern

#### Animation Rate Control
```java
public void increaseSpeed() {
    animation.setRate(animation.getRate() + 0.1);
}

public void decreaseSpeed() {
    animation.setRate(
        animation.getRate() > 0 ? animation.getRate() - 0.1 : 0);
}
```

#### Ball Creation Strategy
```java
public void add() {
    Color color = new Color(Math.random(), 
        Math.random(), Math.random(), 0.5);
    getChildren().add(new Ball(30, 30, 20, color)); 
}
```

### 3. Composite Pattern

#### Scene Graph Structure
```
Scene
└── BorderPane
    ├── ScrollBar (Top)
    ├── MultipleBallPane (Center)
    │   ├── Ball 1
    │   ├── Ball 2
    │   └── Ball N
    └── HBox (Bottom)
        ├── Button "+"
        └── Button "-"
```

### 4. Factory Pattern

#### Ball Factory
```java
// Ball creation with random properties
Color color = new Color(Math.random(), 
    Math.random(), Math.random(), 0.5);
Ball ball = new Ball(30, 30, 20, color);
```

## Animation Architecture

### 1. Timeline-Based Animation

#### Animation Engine
```java
private Timeline animation;

public MultipleBallPane() {
    animation = new Timeline(
        new KeyFrame(Duration.millis(50), e -> moveBall()));
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.play();
}
```

#### Frame Processing
```java
protected void moveBall() {
    for (Node node: this.getChildren()) {
        Ball ball = (Ball)node;
        // Boundary checking and position updates
        // Collision detection and response
    }
}
```

### 2. Physics Engine

#### Movement Algorithm
```java
// Boundary collision detection
if (ball.getCenterX() < ball.getRadius() || 
    ball.getCenterX() > getWidth() - ball.getRadius()) {
    ball.dx *= -1; // Reverse horizontal direction
}
if (ball.getCenterY() < ball.getRadius() || 
    ball.getCenterY() > getHeight() - ball.getRadius()) {
    ball.dy *= -1; // Reverse vertical direction
}

// Position update
ball.setCenterX(ball.dx + ball.getCenterX());
ball.setCenterY(ball.dy + ball.getCenterY());
```

## Cross-Platform Architecture

### 1. Platform Detection

#### Maven Configuration
```xml
<extensions>
    <extension>
        <groupId>kr.motd.maven</groupId>
        <artifactId>os-maven-plugin</artifactId>
        <version>1.7.1</version>
    </extension>
</extensions>
```

#### Platform-Specific Dependencies
```xml
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-controls</artifactId>
    <version>${javafx.version}</version>
    <classifier>${os.detected.classifier}</classifier>
</dependency>
```

### 2. Build System Architecture

#### Maven Build Pipeline
```
Source Code → Compile → Test → Package → Deploy
     ↓
Platform Detection → Dependency Resolution → Execution
```

#### Script Generation
- **run.sh**: Unix/Linux/macOS execution
- **run.bat**: Windows execution
- **run_direct.sh**: Direct Java execution

## Performance Architecture

### 1. Rendering Pipeline

#### JavaFX Rendering
```
Application Thread → Scene Graph → Rendering Engine → GPU
     ↓
Event Handling → Animation Updates → Visual Updates
```

#### Frame Rate Management
- **50ms Intervals**: Balanced performance and smoothness
- **Non-blocking Animation**: Separate animation thread
- **Efficient Updates**: Minimal calculation overhead

### 2. Memory Management

#### Object Lifecycle
```
Ball Creation → Animation → Removal → Garbage Collection
     ↓
Memory Allocation → Active Use → Cleanup → Memory Release
```

#### Memory Optimization
- **Dynamic Creation**: Balls created on-demand
- **Proper Cleanup**: Removed balls are garbage collected
- **No Memory Leaks**: Proper event handler management

## Error Handling Architecture

### 1. Defensive Programming

#### Boundary Checking
```java
public void subtract() {
    if (getChildren().size() > 0) {
        getChildren().remove(getChildren().size() - 1); 
    }
}
```

#### Null Safety
```java
protected void moveBall() {
    for (Node node: this.getChildren()) {
        if (node instanceof Ball) {
            Ball ball = (Ball)node;
            // Process ball movement
        }
    }
}
```

### 2. Platform Compatibility

#### Runtime Checks
```bash
# Check Java availability
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    exit 1
fi
```

## Testing Architecture

### 1. Unit Testing Structure

#### Test Organization
```
src/test/java/
└── com/acu/javafx/multiplebounceball/
    ├── MultipleBounceBallTest.java
    ├── MultipleBallPaneTest.java
    └── BallTest.java
```

#### Test Categories
- **Component Tests**: Individual class testing
- **Integration Tests**: Component interaction testing
- **Performance Tests**: Animation and rendering tests

### 2. Test Configuration

#### Maven Surefire Configuration
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <configuration>
        <argLine>
            --add-opens javafx.graphics/javafx.scene=ALL-UNNAMED
            --add-opens javafx.controls/javafx.scene.control=ALL-UNNAMED
        </argLine>
    </configuration>
</plugin>
```

## Deployment Architecture

### 1. Executable JAR Creation

#### Maven Shade Plugin
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <configuration>
        <transformers>
            <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                <mainClass>com.acu.javafx.multiplebounceball.MultipleBounceBall</mainClass>
            </transformer>
        </transformers>
    </configuration>
</plugin>
```

### 2. Cross-Platform Distribution

#### Platform-Specific Builds
- **macOS**: Native ARM64 and x86_64 packages
- **Windows**: x86_64 and ARM64 executables
- **Linux**: x86_64 and ARM64 packages

#### Dependency Management
- **JavaFX Runtime**: Platform-specific distributions
- **Native Libraries**: Architecture-specific binaries
- **Configuration Files**: Platform-agnostic settings 