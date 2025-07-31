package com.acu.javafx.geometricobjectcomparator;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * JavaFX application demonstrating the use of GeometricObjectComparator
 * to sort geometric objects by their area.
 */
public class GeometricObjectComparatorDemo extends Application {

    private List<com.acu.javafx.geometricobjectcomparator.GeometricObject> geometricObjects;
    private TextArea outputArea;
    private VBox shapesContainer;
    private Button sortButton;
    private Button addCircleButton;
    private Button addRectangleButton;
    private Button clearButton;

    @Override
    public void start(Stage primaryStage) {
        geometricObjects = new ArrayList<>();
        
        // Initialize with some sample objects
        initializeSampleObjects();

        // Create the main layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);

        // Title
        Label titleLabel = new Label("Geometric Object Comparator Demo");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setStyle("-fx-text-fill: #2c3e50;");

        // Description
        Label descriptionLabel = new Label(
            "This application demonstrates sorting geometric objects by their area using a custom comparator."
        );
        descriptionLabel.setWrapText(true);
        descriptionLabel.setStyle("-fx-text-fill: #34495e;");

        // Control buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        addCircleButton = new Button("Add Circle");
        addRectangleButton = new Button("Add Rectangle");
        sortButton = new Button("Sort by Area");
        clearButton = new Button("Clear All");

        buttonBox.getChildren().addAll(addCircleButton, addRectangleButton, sortButton, clearButton);

        // Create main content area with horizontal layout
        HBox mainContentBox = new HBox(20);
        mainContentBox.setAlignment(Pos.TOP_CENTER);

        // Left side - Shapes visualization container (narrow)
        VBox leftPanel = new VBox(10);
        leftPanel.setAlignment(Pos.TOP_CENTER);
        leftPanel.setPrefWidth(300); // Fixed narrow width
        leftPanel.setMaxWidth(300);
        leftPanel.setPrefHeight(600); // Fixed height

        Label objectsLabel = new Label("Current Objects:");
        objectsLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        shapesContainer = new VBox(10);
        shapesContainer.setAlignment(Pos.CENTER);
        shapesContainer.setStyle("-fx-border-color: #bdc3c7; -fx-border-width: 1; -fx-padding: 10;");
        
        // Add scrollpane for shapes container
        ScrollPane shapesScrollPane = new ScrollPane(shapesContainer);
        shapesScrollPane.setFitToWidth(true);
        shapesScrollPane.setPrefHeight(600);
        shapesScrollPane.setStyle("-fx-background-color: transparent;");

        leftPanel.getChildren().addAll(objectsLabel, shapesScrollPane);

        // Right side - Output area (wider)
        VBox rightPanel = new VBox(10);
        rightPanel.setAlignment(Pos.TOP_CENTER);
        HBox.setHgrow(rightPanel, Priority.ALWAYS); // Allow it to grow

        Label outputLabel = new Label("Output:");
        outputLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        outputArea = new TextArea();
        outputArea.setPrefRowCount(40); // Increased height
        outputArea.setEditable(false);
        outputArea.setStyle("-fx-font-family: 'Monaco', 'Consolas', monospace; -fx-font-size: 12;");
        VBox.setVgrow(outputArea, Priority.ALWAYS); // Allow textarea to grow vertically

        rightPanel.getChildren().addAll(outputLabel, outputArea);

        // Add panels to main content box
        mainContentBox.getChildren().addAll(leftPanel, rightPanel);

        // Add components to root
        root.getChildren().addAll(
            titleLabel,
            descriptionLabel,
            buttonBox,
            mainContentBox
        );

        // Set up event handlers
        setupEventHandlers();

        // Create scene
        Scene scene = new Scene(root, 1000, 700); // Increased width to accommodate side-by-side layout
        primaryStage.setTitle("Geometric Object Comparator Demo");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);

        // Update display
        updateDisplay();

        primaryStage.show();
    }

    private void initializeSampleObjects() {
        geometricObjects.add(new com.acu.javafx.geometricobjectcomparator.Circle(5));
        geometricObjects.add(new com.acu.javafx.geometricobjectcomparator.Rectangle(4, 6));
        geometricObjects.add(new com.acu.javafx.geometricobjectcomparator.Circle(3));
        geometricObjects.add(new com.acu.javafx.geometricobjectcomparator.Rectangle(2, 8));
    }

    private void setupEventHandlers() {
        addCircleButton.setOnAction(e -> addRandomCircle());
        addRectangleButton.setOnAction(e -> addRandomRectangle());
        sortButton.setOnAction(e -> sortObjects());
        clearButton.setOnAction(e -> clearAll());
    }

    private void addRandomCircle() {
        double radius = Math.random() * 10 + 1; // Random radius between 1 and 11
        com.acu.javafx.geometricobjectcomparator.Circle circle = new com.acu.javafx.geometricobjectcomparator.Circle(radius);
        circle.setColor(getRandomColor());
        circle.setFilled(Math.random() > 0.5);
        geometricObjects.add(circle);
        updateDisplay();
    }

    private void addRandomRectangle() {
        double width = Math.random() * 10 + 1; // Random width between 1 and 11
        double height = Math.random() * 10 + 1; // Random height between 1 and 11
        com.acu.javafx.geometricobjectcomparator.Rectangle rectangle = new com.acu.javafx.geometricobjectcomparator.Rectangle(width, height);
        rectangle.setColor(getRandomColor());
        rectangle.setFilled(Math.random() > 0.5);
        geometricObjects.add(rectangle);
        updateDisplay();
    }

    private void sortObjects() {
        GeometricObjectComparator comparator = new GeometricObjectComparator();
        Collections.sort(geometricObjects, comparator);
        updateDisplay();
        outputArea.appendText("Objects sorted by area (ascending).\n");
    }

    private void clearAll() {
        geometricObjects.clear();
        updateDisplay();
        outputArea.clear();
    }

    private void updateDisplay() {
        // Clear the shapes container
        shapesContainer.getChildren().clear();

        // Add a label showing the number of objects
        Label countLabel = new Label("Total objects: " + geometricObjects.size());
        countLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        shapesContainer.getChildren().add(countLabel);

        // Create visual representation of each object
        for (int i = 0; i < geometricObjects.size(); i++) {
            com.acu.javafx.geometricobjectcomparator.GeometricObject obj = geometricObjects.get(i);
            
            HBox objectBox = new HBox(10);
            objectBox.setAlignment(Pos.CENTER_LEFT);
            objectBox.setPadding(new Insets(5));

            // Create visual shape
            javafx.scene.Node shapeNode = createVisualShape(obj);
            
            // Create info label
            String info = String.format("#%d: %s (Area: %.2f)", 
                i + 1, 
                obj.getClass().getSimpleName(), 
                obj.getArea());
            Label infoLabel = new Label(info);
            infoLabel.setStyle("-fx-font-family: 'Monaco', 'Consolas', monospace;");

            objectBox.getChildren().addAll(shapeNode, infoLabel);
            shapesContainer.getChildren().add(objectBox);
        }

        // Update output area
        updateOutputArea();
    }

    private javafx.scene.Node createVisualShape(com.acu.javafx.geometricobjectcomparator.GeometricObject obj) {
        if (obj instanceof com.acu.javafx.geometricobjectcomparator.Circle) {
            com.acu.javafx.geometricobjectcomparator.Circle circle = (com.acu.javafx.geometricobjectcomparator.Circle) obj;
            javafx.scene.shape.Circle visualCircle = new javafx.scene.shape.Circle(circle.getRadius() * 5); // Scale for visualization
            visualCircle.setFill(Color.valueOf(circle.getColor()));
            visualCircle.setStroke(Color.BLACK);
            visualCircle.setStrokeWidth(1);
            return visualCircle;
        } else if (obj instanceof com.acu.javafx.geometricobjectcomparator.Rectangle) {
            com.acu.javafx.geometricobjectcomparator.Rectangle rect = (com.acu.javafx.geometricobjectcomparator.Rectangle) obj;
            javafx.scene.shape.Rectangle visualRect = new javafx.scene.shape.Rectangle(
                rect.getWidth() * 5, rect.getHeight() * 5); // Scale for visualization
            visualRect.setFill(Color.valueOf(rect.getColor()));
            visualRect.setStroke(Color.BLACK);
            visualRect.setStrokeWidth(1);
            return visualRect;
        }
        return new Label("Unknown shape");
    }

    private void updateOutputArea() {
        StringBuilder output = new StringBuilder();
        output.append("=== Geometric Objects List ===\n");
        
        for (int i = 0; i < geometricObjects.size(); i++) {
            com.acu.javafx.geometricobjectcomparator.GeometricObject obj = geometricObjects.get(i);
            output.append(String.format("%d. %s\n", i + 1, obj.toString()));
            output.append(String.format("   Area: %.2f\n", obj.getArea()));
            output.append(String.format("   Perimeter: %.2f\n", obj.getPerimeter()));
            output.append("\n");
        }
        
        outputArea.setText(output.toString());
    }

    private String getRandomColor() {
        String[] colors = {"red", "blue", "green", "yellow", "orange", "purple", "pink", "brown"};
        return colors[(int) (Math.random() * colors.length)];
    }

    public static void main(String[] args) {
        launch(args);
    }
} 