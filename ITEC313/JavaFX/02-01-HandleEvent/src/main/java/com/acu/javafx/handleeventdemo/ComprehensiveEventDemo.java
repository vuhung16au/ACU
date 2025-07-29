package com.acu.javafx.handleeventdemo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Comprehensive JavaFX application demonstrating various event handling techniques.
 * This application showcases:
 * - Mouse events (click, drag, move)
 * - Keyboard events (key press, release, typing)
 * - Drag and drop events
 * - Wheel events (scrolling)
 * - Focus events (gain, lose focus)
 */
public class ComprehensiveEventDemo extends Application {
    
    // UI Components
    private VBox mainContainer;
    private TabPane tabPane;
    private TextArea eventLog;
    private Button clearButton;
    
    // Interactive elements for event demonstration
    private Circle mouseCircle;
    private Rectangle keyboardRect;
    private Rectangle dragRect;
    private Rectangle wheelRect;
    private TextField focusField;
    
    // Event counters
    private int mouseEventCount = 0;
    private int keyboardEventCount = 0;
    private int dragEventCount = 0;
    private int wheelEventCount = 0;
    private int focusEventCount = 0;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Comprehensive Event Handling Demo");
        
        // Initialize UI components
        createUI();
        setupEventHandlers();
        
        // Create main scene
        Scene scene = new Scene(mainContainer, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }
    
    /**
     * Creates the user interface components
     */
    private void createUI() {
        mainContainer = new VBox(10);
        mainContainer.setPadding(new Insets(10));
        mainContainer.setAlignment(Pos.TOP_CENTER);
        
        // Create title
        Text title = new Text("JavaFX Event Handling Demonstration");
        title.setFont(Font.font("Arial", 24));
        title.setFill(Color.DARKBLUE);
        
        // Create tab pane for different event types
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        
        // Create tabs for different event categories
        Tab mouseTab = createMouseEventTab();
        Tab keyboardTab = createKeyboardEventTab();
        Tab dragDropTab = createDragDropEventTab();
        Tab wheelTab = createWheelEventTab();
        Tab focusTab = createFocusEventTab();
        
        tabPane.getTabs().addAll(
            mouseTab,
            keyboardTab,
            dragDropTab,
            wheelTab,
            focusTab
        );
        
        // Create event log area
        eventLog = new TextArea();
        eventLog.setPrefRowCount(8);
        eventLog.setEditable(false);
        eventLog.setPromptText("Event log will appear here...");
        eventLog.setStyle("-fx-font-family: 'Monaco', 'Consolas', monospace; -fx-font-size: 12;");
        
        // Create clear button
        clearButton = new Button("Clear Event Log");
        clearButton.setStyle("-fx-background-color: #ff6b6b; -fx-text-fill: white; -fx-font-weight: bold;");
        
        // Add components to main container
        mainContainer.getChildren().addAll(title, tabPane, eventLog, clearButton);
    }
    
    /**
     * Creates the mouse event demonstration tab
     */
    private Tab createMouseEventTab() {
        Tab tab = new Tab("Mouse Events");
        tab.setClosable(false);
        
        VBox content = new VBox(10);
        content.setPadding(new Insets(10));
        content.setAlignment(Pos.TOP_CENTER);
        
        // Description
        Text description = new Text("Click, drag, and move over the circle to see mouse events");
        description.setFont(Font.font("Arial", 14));
        
        // Interactive circle
        Pane circlePane = new Pane();
        circlePane.setPrefSize(400, 300);
        circlePane.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-border-width: 1;");
        
        mouseCircle = new Circle(200, 150, 50);
        mouseCircle.setFill(Color.LIGHTBLUE);
        mouseCircle.setStroke(Color.DARKBLUE);
        mouseCircle.setStrokeWidth(2);
        
        circlePane.getChildren().add(mouseCircle);
        
        // Event counter
        Label counterLabel = new Label("Mouse Events: 0");
        counterLabel.setId("mouseCounter");
        
        content.getChildren().addAll(description, circlePane, counterLabel);
        tab.setContent(content);
        
        return tab;
    }
    
    /**
     * Creates the keyboard event demonstration tab
     */
    private Tab createKeyboardEventTab() {
        Tab tab = new Tab("Keyboard Events");
        tab.setClosable(false);
        
        VBox content = new VBox(10);
        content.setPadding(new Insets(10));
        content.setAlignment(Pos.TOP_CENTER);
        
        // Description
        Text description = new Text("Click on the rectangle and press keys to see keyboard events");
        description.setFont(Font.font("Arial", 14));
        
        // Interactive rectangle
        Pane rectPane = new Pane();
        rectPane.setPrefSize(400, 200);
        rectPane.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-border-width: 1;");
        
        keyboardRect = new Rectangle(100, 50, 200, 100);
        keyboardRect.setFill(Color.LIGHTGREEN);
        keyboardRect.setStroke(Color.DARKGREEN);
        keyboardRect.setStrokeWidth(2);
        
        rectPane.getChildren().add(keyboardRect);
        
        // Event counter
        Label counterLabel = new Label("Keyboard Events: 0");
        counterLabel.setId("keyboardCounter");
        
        content.getChildren().addAll(description, rectPane, counterLabel);
        tab.setContent(content);
        
        return tab;
    }
    
    /**
     * Creates the drag and drop event demonstration tab
     */
    private Tab createDragDropEventTab() {
        Tab tab = new Tab("Drag & Drop");
        tab.setClosable(false);
        
        VBox content = new VBox(10);
        content.setPadding(new Insets(10));
        content.setAlignment(Pos.TOP_CENTER);
        
        // Description
        Text description = new Text("Drag the rectangle to see drag and drop events");
        description.setFont(Font.font("Arial", 14));
        
        // Interactive rectangle
        Pane rectPane = new Pane();
        rectPane.setPrefSize(400, 200);
        rectPane.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-border-width: 1;");
        
        dragRect = new Rectangle(150, 75, 100, 50);
        dragRect.setFill(Color.LIGHTPINK);
        dragRect.setStroke(Color.HOTPINK);
        dragRect.setStrokeWidth(2);
        
        rectPane.getChildren().add(dragRect);
        
        // Event counter
        Label counterLabel = new Label("Drag Events: 0");
        counterLabel.setId("dragCounter");
        
        content.getChildren().addAll(description, rectPane, counterLabel);
        tab.setContent(content);
        
        return tab;
    }
    
    /**
     * Creates the wheel event demonstration tab
     */
    private Tab createWheelEventTab() {
        Tab tab = new Tab("Wheel Events");
        tab.setClosable(false);
        
        VBox content = new VBox(10);
        content.setPadding(new Insets(10));
        content.setAlignment(Pos.TOP_CENTER);
        
        // Description
        Text description = new Text("Scroll over the rectangle to see wheel events");
        description.setFont(Font.font("Arial", 14));
        
        // Interactive rectangle
        Pane rectPane = new Pane();
        rectPane.setPrefSize(400, 200);
        rectPane.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-border-width: 1;");
        
        wheelRect = new Rectangle(100, 50, 200, 100);
        wheelRect.setFill(Color.LIGHTGRAY);
        wheelRect.setStroke(Color.DARKGRAY);
        wheelRect.setStrokeWidth(2);
        
        rectPane.getChildren().add(wheelRect);
        
        // Event counter
        Label counterLabel = new Label("Wheel Events: 0");
        counterLabel.setId("wheelCounter");
        
        content.getChildren().addAll(description, rectPane, counterLabel);
        tab.setContent(content);
        
        return tab;
    }
    
    /**
     * Creates the focus event demonstration tab
     */
    private Tab createFocusEventTab() {
        Tab tab = new Tab("Focus Events");
        tab.setClosable(false);
        
        VBox content = new VBox(10);
        content.setPadding(new Insets(10));
        content.setAlignment(Pos.TOP_CENTER);
        
        // Description
        Text description = new Text("Click on and away from the text field to see focus events");
        description.setFont(Font.font("Arial", 14));
        
        // Interactive text field
        focusField = new TextField();
        focusField.setPromptText("Click here to gain focus, click elsewhere to lose focus");
        focusField.setPrefWidth(300);
        focusField.setStyle("-fx-font-size: 14;");
        
        // Event counter
        Label counterLabel = new Label("Focus Events: 0");
        counterLabel.setId("focusCounter");
        
        content.getChildren().addAll(description, focusField, counterLabel);
        tab.setContent(content);
        
        return tab;
    }
    
    /**
     * Sets up all event handlers for the application
     */
    private void setupEventHandlers() {
        // Clear button event
        clearButton.setOnAction(e -> {
            eventLog.clear();
            resetCounters();
        });
        
        // Mouse event handlers
        setupMouseEventHandlers();
        
        // Keyboard event handlers
        setupKeyboardEventHandlers();
        
        // Drag and drop event handlers
        setupDragDropEventHandlers();
        
        // Wheel event handlers
        setupWheelEventHandlers();
        
        // Focus event handlers
        setupFocusEventHandlers();
    }
    
    /**
     * Sets up mouse event handlers
     */
    private void setupMouseEventHandlers() {
        // Mouse click events
        mouseCircle.setOnMouseClicked(e -> {
            mouseEventCount++;
            logEvent("Mouse Click", String.format("Position: (%.1f, %.1f), Button: %s", 
                e.getX(), e.getY(), e.getButton()));
            updateCounter("mouseCounter", mouseEventCount);
        });
        
        // Mouse enter events
        mouseCircle.setOnMouseEntered(e -> {
            mouseEventCount++;
            logEvent("Mouse Enter", String.format("Position: (%.1f, %.1f)", e.getX(), e.getY()));
            updateCounter("mouseCounter", mouseEventCount);
            mouseCircle.setFill(Color.LIGHTGREEN);
        });
        
        // Mouse exit events
        mouseCircle.setOnMouseExited(e -> {
            mouseEventCount++;
            logEvent("Mouse Exit", String.format("Position: (%.1f, %.1f)", e.getX(), e.getY()));
            updateCounter("mouseCounter", mouseEventCount);
            mouseCircle.setFill(Color.LIGHTBLUE);
        });
        
        // Mouse drag events
        mouseCircle.setOnMouseDragged(e -> {
            mouseEventCount++;
            logEvent("Mouse Drag", String.format("Position: (%.1f, %.1f)", e.getX(), e.getY()));
            updateCounter("mouseCounter", mouseEventCount);
            
            // Move the circle with the mouse
            mouseCircle.setCenterX(e.getX());
            mouseCircle.setCenterY(e.getY());
        });
    }
    
    /**
     * Sets up keyboard event handlers
     */
    private void setupKeyboardEventHandlers() {
        // Key press events
        keyboardRect.setOnKeyPressed(e -> {
            keyboardEventCount++;
            logEvent("Key Press", String.format("Key: %s, Code: %s, Text: '%s'", 
                e.getText(), e.getCode(), e.getText()));
            updateCounter("keyboardCounter", keyboardEventCount);
            
            // Change color based on key
            if (e.getCode() == KeyCode.R) {
                keyboardRect.setFill(Color.RED);
            } else if (e.getCode() == KeyCode.G) {
                keyboardRect.setFill(Color.GREEN);
            } else if (e.getCode() == KeyCode.B) {
                keyboardRect.setFill(Color.BLUE);
            }
        });
        
        // Make rectangle focusable
        keyboardRect.setFocusTraversable(true);
        keyboardRect.setOnMouseClicked(e -> keyboardRect.requestFocus());
    }
    
    /**
     * Sets up drag and drop event handlers
     */
    private void setupDragDropEventHandlers() {
        // Mouse drag detected
        dragRect.setOnDragDetected(e -> {
            dragEventCount++;
            logEvent("Drag Detected", String.format("Position: (%.1f, %.1f)", e.getX(), e.getY()));
            updateCounter("dragCounter", dragEventCount);
            
            // Start drag and drop
            Dragboard db = dragRect.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString("Dragged Rectangle");
            db.setContent(content);
            
            e.consume();
        });
        
        // Drag done
        dragRect.setOnDragDone(e -> {
            dragEventCount++;
            logEvent("Drag Done", String.format("Transfer Mode: %s", e.getTransferMode()));
            updateCounter("dragCounter", dragEventCount);
            
            if (e.getTransferMode() == TransferMode.MOVE) {
                dragRect.setFill(Color.LIGHTGREEN);
            }
            
            e.consume();
        });
    }
    
    /**
     * Sets up wheel event handlers
     */
    private void setupWheelEventHandlers() {
        // Wheel events
        wheelRect.setOnScroll(e -> {
            wheelEventCount++;
            logEvent("Wheel Scroll", String.format("Delta: (%.1f, %.1f), Text Delta: %.1f", 
                e.getDeltaX(), e.getDeltaY(), e.getTextDeltaY()));
            updateCounter("wheelCounter", wheelEventCount);
            
            // Scale the rectangle based on scroll
            double scaleX = wheelRect.getScaleX();
            double scaleY = wheelRect.getScaleY();
            
            if (e.getDeltaY() > 0) {
                wheelRect.setScaleX(scaleX * 1.1);
                wheelRect.setScaleY(scaleY * 1.1);
            } else {
                wheelRect.setScaleX(scaleX * 0.9);
                wheelRect.setScaleY(scaleY * 0.9);
            }
        });
    }
    
    /**
     * Sets up focus event handlers
     */
    private void setupFocusEventHandlers() {
        // Focus gained
        focusField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            focusEventCount++;
            if (newVal) {
                logEvent("Focus Gained", "Text field now has focus");
                focusField.setStyle("-fx-font-size: 14; -fx-background-color: lightgreen;");
            } else {
                logEvent("Focus Lost", "Text field lost focus");
                focusField.setStyle("-fx-font-size: 14; -fx-background-color: white;");
            }
            updateCounter("focusCounter", focusEventCount);
        });
    }
    
    /**
     * Logs an event to the event log area
     */
    private void logEvent(String eventType, String details) {
        String timestamp = java.time.LocalTime.now().toString();
        String logEntry = String.format("[%s] %s: %s%n", timestamp, eventType, details);
        eventLog.appendText(logEntry);
        
        // Auto-scroll to bottom
        eventLog.setScrollTop(Double.MAX_VALUE);
    }
    
    /**
     * Updates the event counter label
     */
    private void updateCounter(String counterId, int count) {
        // Find the label by ID and update it
        for (Tab tab : tabPane.getTabs()) {
            VBox content = (VBox) tab.getContent();
            for (javafx.scene.Node node : content.getChildren()) {
                if (node instanceof Label && node.getId() != null && node.getId().equals(counterId)) {
                    ((Label) node).setText(counterId.replace("Counter", "") + " Events: " + count);
                    break;
                }
            }
        }
    }
    
    /**
     * Resets all event counters
     */
    private void resetCounters() {
        mouseEventCount = 0;
        keyboardEventCount = 0;
        dragEventCount = 0;
        wheelEventCount = 0;
        focusEventCount = 0;
        
        updateCounter("mouseCounter", 0);
        updateCounter("keyboardCounter", 0);
        updateCounter("dragCounter", 0);
        updateCounter("wheelCounter", 0);
        updateCounter("focusCounter", 0);
    }
    
    /**
     * Main method for direct execution
     */
    public static void main(String[] args) {
        launch(args);
    }
} 