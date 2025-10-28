package com.acu.javafx.point2d;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Point2D Demo Application - JavaFX GUI demonstration of the Point2D class.
 * This application provides an interactive interface to test and visualize
 * the Point2D class functionality including constructors, equality, and distance calculations.
 * 
 * @author ACU JavaFX Course
 * @version 1.0
 */
public class Point2DDemo extends Application {
    
    // ACU Color Schema
    private static final Color ACU_PURPLE = Color.rgb(60, 16, 83);
    private static final Color ACU_RED = Color.rgb(242, 18, 12);
    private static final Color ACU_WARM_STONE = Color.rgb(145, 139, 131);
    private static final Color ACU_DEEP_CHARCOAL = Color.rgb(48, 44, 42);
    private static final Color ACU_SOFT_IVORY = Color.rgb(242, 239, 235);
    
    // UI Components
    private TextField x1Field, y1Field, x2Field, y2Field;
    private TextArea outputArea;
    private Circle point1Circle, point2Circle;
    private Pane canvasPane;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Point2D Class Demo - ACU JavaFX Course");
        
        // Create main layout
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F2EFEB;"); // ACU Soft Ivory background
        
        // Create header
        Label headerLabel = new Label("Point2D Class Demonstration");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        headerLabel.setTextFill(ACU_PURPLE);
        headerLabel.setPadding(new Insets(20));
        
        // Create input section
        VBox inputSection = createInputSection();
        
        // Create canvas for visualization
        canvasPane = createCanvasPane();
        
        // Create output section
        VBox outputSection = createOutputSection();
        
        // Create control buttons
        HBox buttonSection = createButtonSection();
        
        // Layout components
        VBox topSection = new VBox(20);
        topSection.getChildren().addAll(headerLabel, inputSection, buttonSection);
        topSection.setPadding(new Insets(20));
        
        root.setTop(topSection);
        root.setCenter(canvasPane);
        root.setBottom(outputSection);
        
        // Create scene and show stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * Creates the input section with text fields for point coordinates.
     */
    private VBox createInputSection() {
        VBox inputSection = new VBox(15);
        inputSection.setPadding(new Insets(20));
        inputSection.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #3C1053; -fx-border-width: 2; -fx-border-radius: 5;");
        
        Label inputLabel = new Label("Enter Point Coordinates:");
        inputLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        inputLabel.setTextFill(ACU_DEEP_CHARCOAL);
        
        // Point 1 input
        HBox point1Box = new HBox(10);
        point1Box.setAlignment(Pos.CENTER_LEFT);
        point1Box.getChildren().addAll(
            new Label("Point 1:"),
            new Label("X:"),
            x1Field = new TextField("0"),
            new Label("Y:"),
            y1Field = new TextField("0")
        );
        
        // Point 2 input
        HBox point2Box = new HBox(10);
        point2Box.setAlignment(Pos.CENTER_LEFT);
        point2Box.getChildren().addAll(
            new Label("Point 2:"),
            new Label("X:"),
            x2Field = new TextField("5"),
            new Label("Y:"),
            y2Field = new TextField("5")
        );
        
        // Set field properties
        x1Field.setPrefWidth(80);
        y1Field.setPrefWidth(80);
        x2Field.setPrefWidth(80);
        y2Field.setPrefWidth(80);
        
        inputSection.getChildren().addAll(inputLabel, point1Box, point2Box);
        return inputSection;
    }
    
    /**
     * Creates the canvas pane for visualizing points.
     */
    private Pane createCanvasPane() {
        canvasPane = new Pane();
        canvasPane.setPrefSize(400, 300);
        canvasPane.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #918B83; -fx-border-width: 1;");
        
        // Create circles for points
        point1Circle = new Circle(5);
        point1Circle.setFill(ACU_RED);
        point1Circle.setStroke(ACU_DEEP_CHARCOAL);
        point1Circle.setStrokeWidth(2);
        
        point2Circle = new Circle(5);
        point2Circle.setFill(ACU_PURPLE);
        point2Circle.setStroke(ACU_DEEP_CHARCOAL);
        point2Circle.setStrokeWidth(2);
        
        canvasPane.getChildren().addAll(point1Circle, point2Circle);
        
        return canvasPane;
    }
    
    /**
     * Creates the output section with text area for results.
     */
    private VBox createOutputSection() {
        VBox outputSection = new VBox(10);
        outputSection.setPadding(new Insets(20));
        
        Label outputLabel = new Label("Output:");
        outputLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        outputLabel.setTextFill(ACU_DEEP_CHARCOAL);
        
        outputArea = new TextArea();
        outputArea.setPrefRowCount(8);
        outputArea.setEditable(false);
        outputArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12;");
        
        outputSection.getChildren().addAll(outputLabel, outputArea);
        return outputSection;
    }
    
    /**
     * Creates the button section with action buttons.
     */
    private HBox createButtonSection() {
        HBox buttonSection = new HBox(15);
        buttonSection.setAlignment(Pos.CENTER);
        buttonSection.setPadding(new Insets(10));
        
        Button createPointsBtn = new Button("Create Points");
        createPointsBtn.setStyle("-fx-background-color: #3C1053; -fx-text-fill: white; -fx-font-weight: bold;");
        createPointsBtn.setOnAction(e -> createAndDisplayPoints());
        
        Button testEqualityBtn = new Button("Test Equality");
        testEqualityBtn.setStyle("-fx-background-color: #F2120C; -fx-text-fill: white; -fx-font-weight: bold;");
        testEqualityBtn.setOnAction(e -> testEquality());
        
        Button calculateDistanceBtn = new Button("Calculate Distance");
        calculateDistanceBtn.setStyle("-fx-background-color: #918B83; -fx-text-fill: white; -fx-font-weight: bold;");
        calculateDistanceBtn.setOnAction(e -> calculateDistance());
        
        Button clearBtn = new Button("Clear Output");
        clearBtn.setStyle("-fx-background-color: #302C2A; -fx-text-fill: white; -fx-font-weight: bold;");
        clearBtn.setOnAction(e -> outputArea.clear());
        
        buttonSection.getChildren().addAll(createPointsBtn, testEqualityBtn, calculateDistanceBtn, clearBtn);
        return buttonSection;
    }
    
    /**
     * Creates Point2D objects and displays them on the canvas.
     */
    private void createAndDisplayPoints() {
        try {
            // Parse input values
            double x1 = Double.parseDouble(x1Field.getText());
            double y1 = Double.parseDouble(y1Field.getText());
            double x2 = Double.parseDouble(x2Field.getText());
            double y2 = Double.parseDouble(y2Field.getText());
            
            // Create Point2D objects
            Point2D point1 = new Point2D(x1, y1);
            Point2D point2 = new Point2D(x2, y2);
            
            // Update canvas visualization
            updateCanvasVisualization(x1, y1, x2, y2);
            
            // Display results
            StringBuilder output = new StringBuilder();
            output.append("=== Point Creation Results ===\n");
            output.append("Point 1: ").append(point1.toString()).append("\n");
            output.append("Point 2: ").append(point2.toString()).append("\n");
            output.append("Point 1 distance from origin: ").append(String.format("%.2f", point1.distanceFromOrigin())).append("\n");
            output.append("Point 2 distance from origin: ").append(String.format("%.2f", point2.distanceFromOrigin())).append("\n");
            output.append("Distance between points: ").append(String.format("%.2f", point1.distance(point2))).append("\n\n");
            
            outputArea.appendText(output.toString());
            
        } catch (NumberFormatException e) {
            outputArea.appendText("Error: Please enter valid numeric values for coordinates.\n\n");
        } catch (Exception e) {
            outputArea.appendText("Error: " + e.getMessage() + "\n\n");
        }
    }
    
    /**
     * Tests equality between two Point2D objects.
     */
    private void testEquality() {
        try {
            // Parse input values
            double x1 = Double.parseDouble(x1Field.getText());
            double y1 = Double.parseDouble(y1Field.getText());
            double x2 = Double.parseDouble(x2Field.getText());
            double y2 = Double.parseDouble(y2Field.getText());
            
            // Create Point2D objects
            Point2D point1 = new Point2D(x1, y1);
            Point2D point2 = new Point2D(x2, y2);
            
            // Test equality
            boolean areEqual = point1.equals(point2);
            
            // Display results
            StringBuilder output = new StringBuilder();
            output.append("=== Equality Test Results ===\n");
            output.append("Point 1: ").append(point1.toString()).append("\n");
            output.append("Point 2: ").append(point2.toString()).append("\n");
            output.append("Are equal: ").append(areEqual ? "YES" : "NO").append("\n");
            output.append("Point 1 hashCode: ").append(point1.hashCode()).append("\n");
            output.append("Point 2 hashCode: ").append(point2.hashCode()).append("\n\n");
            
            outputArea.appendText(output.toString());
            
        } catch (NumberFormatException e) {
            outputArea.appendText("Error: Please enter valid numeric values for coordinates.\n\n");
        } catch (Exception e) {
            outputArea.appendText("Error: " + e.getMessage() + "\n\n");
        }
    }
    
    /**
     * Calculates distance between two Point2D objects.
     */
    private void calculateDistance() {
        try {
            // Parse input values
            double x1 = Double.parseDouble(x1Field.getText());
            double y1 = Double.parseDouble(y1Field.getText());
            double x2 = Double.parseDouble(x2Field.getText());
            double y2 = Double.parseDouble(y2Field.getText());
            
            // Create Point2D objects
            Point2D point1 = new Point2D(x1, y1);
            Point2D point2 = new Point2D(x2, y2);
            
            // Calculate distances
            double distanceBetween = point1.distance(point2);
            double distance1FromOrigin = point1.distanceFromOrigin();
            double distance2FromOrigin = point2.distanceFromOrigin();
            
            // Display results
            StringBuilder output = new StringBuilder();
            output.append("=== Distance Calculation Results ===\n");
            output.append("Point 1: ").append(point1.toString()).append("\n");
            output.append("Point 2: ").append(point2.toString()).append("\n");
            output.append("Distance between points: ").append(String.format("%.4f", distanceBetween)).append("\n");
            output.append("Point 1 distance from origin: ").append(String.format("%.4f", distance1FromOrigin)).append("\n");
            output.append("Point 2 distance from origin: ").append(String.format("%.4f", distance2FromOrigin)).append("\n\n");
            
            outputArea.appendText(output.toString());
            
        } catch (NumberFormatException e) {
            outputArea.appendText("Error: Please enter valid numeric values for coordinates.\n\n");
        } catch (Exception e) {
            outputArea.appendText("Error: " + e.getMessage() + "\n\n");
        }
    }
    
    /**
     * Updates the canvas visualization with the current point positions.
     */
    private void updateCanvasVisualization(double x1, double y1, double x2, double y2) {
        // Scale coordinates to fit canvas (assuming canvas is 400x300)
        double scaleX = 400.0 / 20.0; // Scale to fit -20 to 20 range
        double scaleY = 300.0 / 20.0; // Scale to fit -20 to 20 range
        double offsetX = 200.0; // Center offset
        double offsetY = 150.0; // Center offset
        
        // Convert coordinates to canvas positions
        double canvasX1 = x1 * scaleX + offsetX;
        double canvasY1 = offsetY - y1 * scaleY; // Flip Y axis
        double canvasX2 = x2 * scaleX + offsetX;
        double canvasY2 = offsetY - y2 * scaleY; // Flip Y axis
        
        // Update circle positions
        point1Circle.setCenterX(canvasX1);
        point1Circle.setCenterY(canvasY1);
        point2Circle.setCenterX(canvasX2);
        point2Circle.setCenterY(canvasY2);
        
        // Make circles visible
        point1Circle.setVisible(true);
        point2Circle.setVisible(true);
    }
    
    /**
     * Main method to launch the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
