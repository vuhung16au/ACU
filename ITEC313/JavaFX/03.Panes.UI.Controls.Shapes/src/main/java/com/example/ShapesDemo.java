package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

/**
 * JavaFX Application demonstrating Panes, UI Controls, and Shapes
 * Features:
 * - Display various shapes (Circle, Rectangle, Line)
 * - Interactive UI controls to modify shapes
 * - Different pane layouts (VBox, HBox, BorderPane)
 */
public class ShapesDemo extends Application {
    
    // Shape objects
    private Circle circle;
    private Rectangle rectangle;
    private Line line;
    private Polygon triangle;
    
    // UI Controls
    private Slider circleRadiusSlider;
    private Slider rectangleWidthSlider;
    private Slider rectangleHeightSlider;
    private ColorPicker shapeColorPicker;
    private ComboBox<String> shapeSelector;
    private CheckBox fillShapeCheckBox;
    private TextField coordinateDisplay;
    
    // Panes
    private Pane drawingPane;
    private VBox controlPanel;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Panes, UI Controls & Shapes Demo");
        
        // Initialize the UI
        initializeShapes();
        initializeControls();
        initializePanes();
        
        // Create main layout using BorderPane
        BorderPane root = new BorderPane();
        root.setCenter(drawingPane);
        root.setRight(controlPanel);
        root.setPadding(new Insets(10));
        
        // Create and set the scene with larger window size
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(650);
        primaryStage.show();
        
        // Set initial values and update display
        updateShapeDisplay();
    }
    
    /**
     * Initialize all shape objects with default properties
     * Shapes are aligned in a 2x2 grid layout for better organization
     */
    private void initializeShapes() {
        // Grid layout parameters
        int startX = 100;
        int startY = 100;
        int spacingX = 200;
        int spacingY = 150;
        
        // Create Circle (Top-left position)
        circle = new Circle(startX, startY, 50);
        circle.setFill(Color.LIGHTBLUE);
        circle.setStroke(Color.DARKBLUE);
        circle.setStrokeWidth(2);
        
        // Create Rectangle (Top-right position)
        rectangle = new Rectangle(startX + spacingX, startY - 40, 100, 80);
        rectangle.setFill(Color.LIGHTGREEN);
        rectangle.setStroke(Color.DARKGREEN);
        rectangle.setStrokeWidth(2);
        
        // Create Line (Bottom-left position)
        int lineY = startY + spacingY;
        line = new Line(startX - 50, lineY, startX + 50, lineY + 50);
        line.setStroke(Color.RED);
        line.setStrokeWidth(3);
        
        // Create Triangle (Bottom-right position)
        triangle = new Polygon();
        int triangleX = startX + spacingX;
        int triangleY = startY + spacingY;
        triangle.getPoints().addAll(new Double[]{
            (double) triangleX, (double) triangleY - 50,      // Top point
            (double) triangleX - 50, (double) triangleY + 50,  // Bottom left
            (double) triangleX + 50, (double) triangleY + 50   // Bottom right
        });
        triangle.setFill(Color.LIGHTYELLOW);
        triangle.setStroke(Color.ORANGE);
        triangle.setStrokeWidth(2);
    }
    
    /**
     * Initialize all UI controls and their event handlers
     */
    private void initializeControls() {
        // Circle radius slider
        circleRadiusSlider = new Slider(10, 100, 50);
        circleRadiusSlider.setShowTickLabels(true);
        circleRadiusSlider.setShowTickMarks(true);
        circleRadiusSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            circle.setRadius(newVal.doubleValue());
            updateCoordinateDisplay();
        });
        
        // Rectangle width slider
        rectangleWidthSlider = new Slider(20, 200, 100);
        rectangleWidthSlider.setShowTickLabels(true);
        rectangleWidthSlider.setShowTickMarks(true);
        rectangleWidthSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            rectangle.setWidth(newVal.doubleValue());
            updateCoordinateDisplay();
        });
        
        // Rectangle height slider
        rectangleHeightSlider = new Slider(20, 150, 80);
        rectangleHeightSlider.setShowTickLabels(true);
        rectangleHeightSlider.setShowTickMarks(true);
        rectangleHeightSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            rectangle.setHeight(newVal.doubleValue());
            updateCoordinateDisplay();
        });
        
        // Color picker for shapes
        shapeColorPicker = new ColorPicker(Color.LIGHTBLUE);
        shapeColorPicker.setPrefWidth(200);
        shapeColorPicker.setMaxWidth(Double.MAX_VALUE);
        shapeColorPicker.setOnAction(e -> updateShapeColors());
        
        // Shape selector ComboBox
        shapeSelector = new ComboBox<>();
        shapeSelector.getItems().addAll("Circle", "Rectangle", "Line", "Triangle");
        shapeSelector.setValue("Circle");
        shapeSelector.setPrefWidth(200);
        shapeSelector.setMaxWidth(Double.MAX_VALUE);
        shapeSelector.setOnAction(e -> highlightSelectedShape());
        
        // Fill shape checkbox
        fillShapeCheckBox = new CheckBox("Fill Shapes");
        fillShapeCheckBox.setSelected(true);
        fillShapeCheckBox.setOnAction(e -> toggleShapeFill());
        
        // Coordinate display
        coordinateDisplay = new TextField();
        coordinateDisplay.setEditable(false);
        coordinateDisplay.setPrefColumnCount(20);
    }
    
    /**
     * Initialize and arrange all panes
     */
    private void initializePanes() {
        // Create drawing pane
        drawingPane = new Pane();
        drawingPane.setPrefSize(650, 500);
        drawingPane.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc; -fx-border-width: 1;");
        
        // Add all shapes to drawing pane
        drawingPane.getChildren().addAll(circle, rectangle, line, triangle);
        
        // Create control panel using VBox
        controlPanel = new VBox(15);
        controlPanel.setPadding(new Insets(10));
        controlPanel.setAlignment(Pos.TOP_LEFT);
        controlPanel.setPrefWidth(300);
        controlPanel.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-width: 1;");
        
        // Add controls to panel with labels
        controlPanel.getChildren().addAll(
            new Label("Shape Controls"),
            new Separator(),
            
            createControlGroup("Shape Selection:", shapeSelector),
            createControlGroup("Shape Color:", shapeColorPicker),
            fillShapeCheckBox,
            new Separator(),
            
            createControlGroup("Circle Radius:", circleRadiusSlider),
            createControlGroup("Rectangle Width:", rectangleWidthSlider),
            createControlGroup("Rectangle Height:", rectangleHeightSlider),
            new Separator(),
            
            new Label("Coordinates:"),
            coordinateDisplay,
            new Separator(),
            
            createInfoPanel()
        );
    }
    
    /**
     * Create a control group with label and control
     */
    private VBox createControlGroup(String labelText, Control control) {
        VBox group = new VBox(5);
        Label label = new Label(labelText);
        label.setStyle("-fx-font-weight: bold;");
        
        // Ensure control fills available width
        if (control instanceof ComboBox || control instanceof ColorPicker) {
            control.setPrefWidth(200);
            control.setMaxWidth(Double.MAX_VALUE);
        }
        
        group.getChildren().addAll(label, control);
        return group;
    }
    
    /**
     * Create an information panel showing shape properties
     */
    private VBox createInfoPanel() {
        VBox infoPanel = new VBox(5);
        infoPanel.setStyle("-fx-background-color: #e8f4f8; -fx-padding: 10; -fx-border-radius: 5;");
        
        Label infoTitle = new Label("Shape Information");
        infoTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        
        Label circleInfo = new Label("• Circle: Center (100, 100) - Top Left");
        Label rectangleInfo = new Label("• Rectangle: Position (300, 60) - Top Right");
        Label lineInfo = new Label("• Line: From (50, 250) to (150, 300) - Bottom Left");
        Label triangleInfo = new Label("• Triangle: Centered (300, 250) - Bottom Right");
        
        infoPanel.getChildren().addAll(
            infoTitle,
            new Separator(),
            circleInfo,
            rectangleInfo,
            lineInfo,
            triangleInfo
        );
        
        return infoPanel;
    }
    
    /**
     * Update the color of all shapes based on color picker selection
     */
    private void updateShapeColors() {
        Color selectedColor = shapeColorPicker.getValue();
        
        if (fillShapeCheckBox.isSelected()) {
            circle.setFill(selectedColor);
            rectangle.setFill(selectedColor);
            triangle.setFill(selectedColor);
        }
        
        // Always update stroke colors
        circle.setStroke(selectedColor.darker());
        rectangle.setStroke(selectedColor.darker());
        line.setStroke(selectedColor);
        triangle.setStroke(selectedColor.darker());
    }
    
    /**
     * Highlight the currently selected shape
     */
    private void highlightSelectedShape() {
        // Reset all stroke widths
        circle.setStrokeWidth(2);
        rectangle.setStrokeWidth(2);
        line.setStrokeWidth(3);
        triangle.setStrokeWidth(2);
        
        // Highlight selected shape
        String selected = shapeSelector.getValue();
        switch (selected) {
            case "Circle":
                circle.setStrokeWidth(4);
                break;
            case "Rectangle":
                rectangle.setStrokeWidth(4);
                break;
            case "Line":
                line.setStrokeWidth(6);
                break;
            case "Triangle":
                triangle.setStrokeWidth(4);
                break;
        }
        
        updateCoordinateDisplay();
    }
    
    /**
     * Toggle fill property for shapes
     */
    private void toggleShapeFill() {
        if (fillShapeCheckBox.isSelected()) {
            updateShapeColors();
        } else {
            circle.setFill(Color.TRANSPARENT);
            rectangle.setFill(Color.TRANSPARENT);
            triangle.setFill(Color.TRANSPARENT);
        }
    }
    
    /**
     * Update coordinate display based on selected shape
     */
    private void updateCoordinateDisplay() {
        String selected = shapeSelector.getValue();
        String coordinates = "";
        
        switch (selected) {
            case "Circle":
                coordinates = String.format("Center: (%.0f, %.0f), Radius: %.0f", 
                    circle.getCenterX(), circle.getCenterY(), circle.getRadius());
                break;
            case "Rectangle":
                coordinates = String.format("Position: (%.0f, %.0f), Size: %.0fx%.0f", 
                    rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
                break;
            case "Line":
                coordinates = String.format("From: (%.0f, %.0f) to (%.0f, %.0f)", 
                    line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
                break;
            case "Triangle":
                coordinates = "Polygon with 3 vertices";
                break;
        }
        
        coordinateDisplay.setText(coordinates);
    }
    
    /**
     * Update the initial display
     */
    private void updateShapeDisplay() {
        updateShapeColors();
        highlightSelectedShape();
        updateCoordinateDisplay();
    }
    
    /**
     * Main method to launch the JavaFX application
     */
    public static void main(String[] args) {
        launch(args);
    }
}
