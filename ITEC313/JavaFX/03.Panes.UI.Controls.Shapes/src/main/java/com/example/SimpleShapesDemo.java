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
 * Simplified JavaFX Application demonstrating basic Panes, UI Controls, and Shapes
 * This version focuses on core concepts with a cleaner implementation
 */
public class SimpleShapesDemo extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Shapes Demo - Simple Version");
        
        // Create main layout (BorderPane - a type of Pane)
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        
        // Create drawing area (Pane for free-form layout)
        Pane drawingArea = createDrawingArea();
        
        // Create control panel (VBox - vertical arrangement)
        VBox controlPanel = createControlPanel();
        
        // Arrange components in BorderPane
        root.setCenter(drawingArea);
        root.setRight(controlPanel);
        
        // Create scene and show stage
        Scene scene = new Scene(root, 700, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * Create the drawing area with shapes
     */
    private Pane createDrawingArea() {
        Pane pane = new Pane();
        pane.setPrefSize(450, 400);
        pane.setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #cccccc;");
        
        // Create and add shapes
        
        // 1. Circle
        Circle circle = new Circle(100, 100, 40);
        circle.setFill(Color.LIGHTBLUE);
        circle.setStroke(Color.BLUE);
        circle.setStrokeWidth(2);
        
        // 2. Rectangle
        Rectangle rectangle = new Rectangle(200, 50, 80, 60);
        rectangle.setFill(Color.LIGHTGREEN);
        rectangle.setStroke(Color.GREEN);
        rectangle.setStrokeWidth(2);
        
        // 3. Line
        Line line = new Line(50, 200, 180, 250);
        line.setStroke(Color.RED);
        line.setStrokeWidth(3);
        
        // 4. Triangle (using Polygon)
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(new Double[]{
            350.0, 80.0,   // top
            320.0, 140.0,  // bottom left
            380.0, 140.0   // bottom right
        });
        triangle.setFill(Color.YELLOW);
        triangle.setStroke(Color.ORANGE);
        triangle.setStrokeWidth(2);
        
        // Add all shapes to the pane
        pane.getChildren().addAll(circle, rectangle, line, triangle);
        
        return pane;
    }
    
    /**
     * Create the control panel with UI controls
     */
    private VBox createControlPanel() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(10));
        panel.setPrefWidth(200);
        panel.setAlignment(Pos.TOP_LEFT);
        panel.setStyle("-fx-background-color: white; -fx-border-color: #cccccc;");
        
        // Title
        Label title = new Label("UI Controls Demo");
        title.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        // Slider control
        Label sliderLabel = new Label("Slider Control:");
        Slider slider = new Slider(0, 100, 50);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        
        // ComboBox control
        Label comboLabel = new Label("ComboBox Control:");
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Circle", "Rectangle", "Line", "Triangle");
        comboBox.setValue("Circle");
        
        // CheckBox control
        CheckBox checkBox = new CheckBox("Enable Feature");
        checkBox.setSelected(true);
        
        // Button control
        Button button = new Button("Action Button");
        button.setPrefWidth(120);
        
        // ColorPicker control
        Label colorLabel = new Label("Color Picker:");
        ColorPicker colorPicker = new ColorPicker(Color.BLUE);
        
        // TextField control
        Label textLabel = new Label("Text Field:");
        TextField textField = new TextField("Sample text");
        
        // Information panel (HBox - horizontal arrangement)
        HBox infoPanel = new HBox(5);
        infoPanel.setStyle("-fx-background-color: #e8f4f8; -fx-padding: 8;");
        Label infoIcon = new Label("ℹ");
        infoIcon.setStyle("-fx-font-size: 16px; -fx-text-fill: blue;");
        Label infoText = new Label("Interactive UI");
        infoPanel.getChildren().addAll(infoIcon, infoText);
        
        // Add all controls to the panel
        panel.getChildren().addAll(
            title,
            new Separator(),
            sliderLabel, slider,
            comboLabel, comboBox,
            checkBox,
            button,
            colorLabel, colorPicker,
            textLabel, textField,
            new Separator(),
            infoPanel
        );
        
        return panel;
    }
    
    /**
     * Alternative main method
     */
    public static void main(String[] args) {
        launch(args);
    }
}
