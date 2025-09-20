package com.acu.javafx.convexhull;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * JavaFX application that demonstrates the Convex Hull algorithm.
 * Users can add points by clicking on the canvas and find the convex hull.
 */
public class ConvexHullDemo extends Application {
    
    private static final double CANVAS_WIDTH = 600;
    private static final double CANVAS_HEIGHT = 400;
    private static final double POINT_RADIUS = 4;
    
    private Canvas canvas;
    private GraphicsContext gc;
    private List<Point> points;
    private List<Point> convexHull;
    private TextField numPointsField;
    private Label statusLabel;
    private Label hullInfoLabel;
    
    @Override
    public void start(Stage primaryStage) {
        // Initialize data structures
        points = new ArrayList<>();
        convexHull = new ArrayList<>();
        
        // Create the main layout
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        
        // Create title
        Label titleLabel = new Label("Convex Hull Algorithm Demo");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        titleLabel.setAlignment(Pos.CENTER);
        
        // Create description
        Label descriptionLabel = new Label(
            "Click on the canvas to add points, then click 'Find Convex Hull' to see the result. " +
            "Use 'Add Random Points' to generate random points, or 'Clear All' to start over."
        );
        descriptionLabel.setWrapText(true);
        descriptionLabel.setStyle("-fx-font-size: 14px;");
        
        // Create control panel
        VBox controlPanel = createControlPanel();
        
        // Create canvas
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        clearCanvas();
        
        // Add mouse click handler to canvas
        canvas.setOnMouseClicked(this::handleCanvasClick);
        
        // Create status panel
        VBox statusPanel = createStatusPanel();
        
        // Layout the main components
        VBox topPanel = new VBox(10, titleLabel, descriptionLabel);
        topPanel.setAlignment(Pos.CENTER);
        
        HBox mainContent = new HBox(20);
        mainContent.getChildren().addAll(controlPanel, canvas, statusPanel);
        
        root.setTop(topPanel);
        root.setCenter(mainContent);
        
        // Create scene and show stage
        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setTitle("Convex Hull Algorithm Demo");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    /**
     * Creates the control panel with buttons and input fields.
     */
    private VBox createControlPanel() {
        VBox controlPanel = new VBox(10);
        controlPanel.setPadding(new Insets(10));
        controlPanel.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-border-width: 1;");
        
        Label controlTitle = new Label("Controls");
        controlTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        // Number of points input
        HBox numPointsBox = new HBox(5);
        numPointsBox.setAlignment(Pos.CENTER_LEFT);
        Label numPointsLabel = new Label("Number of points:");
        numPointsField = new TextField("10");
        numPointsField.setPrefWidth(60);
        numPointsBox.getChildren().addAll(numPointsLabel, numPointsField);
        
        // Buttons
        Button findHullBtn = new Button("Find Convex Hull");
        findHullBtn.setPrefWidth(150);
        findHullBtn.setOnAction(e -> findConvexHull());
        
        Button addRandomBtn = new Button("Add Random Points");
        addRandomBtn.setPrefWidth(150);
        addRandomBtn.setOnAction(e -> addRandomPoints());
        
        Button addPointBtn = new Button("Add Point");
        addPointBtn.setPrefWidth(150);
        addPointBtn.setOnAction(e -> addSinglePoint());
        
        Button removePointBtn = new Button("Remove Point");
        removePointBtn.setPrefWidth(150);
        removePointBtn.setOnAction(e -> removeLastPoint());
        
        Button clearBtn = new Button("Clear All");
        clearBtn.setPrefWidth(150);
        clearBtn.setOnAction(e -> clearAll());
        
        // Instructions
        Label instructionsLabel = new Label("Instructions:");
        instructionsLabel.setStyle("-fx-font-weight: bold;");
        
        Label instruction1 = new Label("• Click on canvas to add points");
        Label instruction2 = new Label("• Use 'Add Random Points' for quick demo");
        Label instruction3 = new Label("• Click 'Find Convex Hull' to see result");
        Label instruction4 = new Label("• Use 'Clear All' to start over");
        
        controlPanel.getChildren().addAll(
            controlTitle,
            numPointsBox,
            findHullBtn,
            addRandomBtn,
            addPointBtn,
            removePointBtn,
            clearBtn,
            new Separator(),
            instructionsLabel,
            instruction1,
            instruction2,
            instruction3,
            instruction4
        );
        
        return controlPanel;
    }
    
    /**
     * Creates the status panel with information about the current state.
     */
    private VBox createStatusPanel() {
        VBox statusPanel = new VBox(10);
        statusPanel.setPadding(new Insets(10));
        statusPanel.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #ccc; -fx-border-width: 1;");
        
        Label statusTitle = new Label("Status");
        statusTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        statusLabel = new Label("Ready to add points");
        statusLabel.setWrapText(true);
        
        hullInfoLabel = new Label("");
        hullInfoLabel.setWrapText(true);
        hullInfoLabel.setStyle("-fx-font-size: 12px;");
        
        statusPanel.getChildren().addAll(statusTitle, statusLabel, hullInfoLabel);
        
        return statusPanel;
    }
    
    /**
     * Handles mouse clicks on the canvas to add points.
     */
    private void handleCanvasClick(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        
        // Add point to the list
        Point newPoint = new Point(x, y);
        points.add(newPoint);
        
        // Redraw the canvas
        redrawCanvas();
        
        // Update status
        statusLabel.setText("Added point at (" + String.format("%.1f", x) + ", " + String.format("%.1f", y) + ")");
        updateHullInfo();
    }
    
    /**
     * Adds random points to the canvas.
     */
    private void addRandomPoints() {
        try {
            int numPoints = Integer.parseInt(numPointsField.getText());
            if (numPoints < 3) {
                statusLabel.setText("Error: Need at least 3 points for convex hull");
                return;
            }
            
            Random random = new Random();
            points.clear();
            convexHull.clear();
            
            for (int i = 0; i < numPoints; i++) {
                double x = 50 + random.nextDouble() * (CANVAS_WIDTH - 100);
                double y = 50 + random.nextDouble() * (CANVAS_HEIGHT - 100);
                points.add(new Point(x, y));
            }
            
            redrawCanvas();
            statusLabel.setText("Added " + numPoints + " random points");
            updateHullInfo();
            
        } catch (NumberFormatException e) {
            statusLabel.setText("Error: Please enter a valid number");
        }
    }
    
    /**
     * Adds a single random point.
     */
    private void addSinglePoint() {
        Random random = new Random();
        double x = 50 + random.nextDouble() * (CANVAS_WIDTH - 100);
        double y = 50 + random.nextDouble() * (CANVAS_HEIGHT - 100);
        points.add(new Point(x, y));
        
        redrawCanvas();
        statusLabel.setText("Added random point");
        updateHullInfo();
    }
    
    /**
     * Removes the last added point.
     */
    private void removeLastPoint() {
        if (!points.isEmpty()) {
            points.remove(points.size() - 1);
            convexHull.clear();
            redrawCanvas();
            statusLabel.setText("Removed last point");
            updateHullInfo();
        } else {
            statusLabel.setText("No points to remove");
        }
    }
    
    /**
     * Clears all points and the convex hull.
     */
    private void clearAll() {
        points.clear();
        convexHull.clear();
        clearCanvas();
        statusLabel.setText("Cleared all points");
        hullInfoLabel.setText("");
    }
    
    /**
     * Finds the convex hull of the current points.
     */
    private void findConvexHull() {
        if (points.size() < 3) {
            statusLabel.setText("Error: Need at least 3 points to find convex hull");
            return;
        }
        
        try {
            convexHull = ConvexHullAlgorithm.findConvexHull(points);
            redrawCanvas();
            statusLabel.setText("Found convex hull with " + convexHull.size() + " vertices");
            updateHullInfo();
            
        } catch (IllegalArgumentException e) {
            statusLabel.setText("Error: " + e.getMessage());
        }
    }
    
    /**
     * Redraws the canvas with all points and the convex hull.
     */
    private void redrawCanvas() {
        clearCanvas();
        
        // Draw the convex hull if it exists
        if (!convexHull.isEmpty()) {
            drawConvexHull();
        }
        
        // Draw all points
        drawPoints();
    }
    
    /**
     * Clears the canvas.
     */
    private void clearCanvas() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        
        // Draw border
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.strokeRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
    }
    
    /**
     * Draws all points on the canvas.
     */
    private void drawPoints() {
        gc.setFill(Color.BLACK);
        
        for (Point point : points) {
            gc.fillOval(point.getX() - POINT_RADIUS, point.getY() - POINT_RADIUS, 
                       POINT_RADIUS * 2, POINT_RADIUS * 2);
        }
    }
    
    /**
     * Draws the convex hull.
     */
    private void drawConvexHull() {
        if (convexHull.size() < 2) {
            return;
        }
        
        // Draw hull edges
        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        
        for (int i = 0; i < convexHull.size(); i++) {
            Point current = convexHull.get(i);
            Point next = convexHull.get((i + 1) % convexHull.size());
            
            gc.strokeLine(current.getX(), current.getY(), next.getX(), next.getY());
        }
        
        // Draw hull vertices with different color
        gc.setFill(Color.RED);
        for (Point point : convexHull) {
            gc.fillOval(point.getX() - POINT_RADIUS, point.getY() - POINT_RADIUS, 
                       POINT_RADIUS * 2, POINT_RADIUS * 2);
        }
    }
    
    /**
     * Updates the hull information display.
     */
    private void updateHullInfo() {
        if (convexHull.isEmpty()) {
            hullInfoLabel.setText("Points: " + points.size() + "\nConvex Hull: Not calculated");
        } else {
            double area = ConvexHullAlgorithm.calculateHullArea(convexHull);
            double perimeter = ConvexHullAlgorithm.calculateHullPerimeter(convexHull);
            
            hullInfoLabel.setText(String.format(
                "Points: %d\nConvex Hull: %d vertices\nArea: %.2f\nPerimeter: %.2f",
                points.size(), convexHull.size(), area, perimeter
            ));
        }
    }
    
    /**
     * Main method to launch the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
