package com.acu.javafx.closestpair;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX application demonstrating the Closest Pair algorithm.
 * This application allows users to interactively add points and visualize
 * the closest pair algorithm in action.
 */
public class ClosestPairDemo {

    private Stage stage;
    private Canvas canvas;
    private GraphicsContext gc;
    private List<Point2D> points = new ArrayList<>();
    private ClosestPair closestPair;
    private Point2D closestPoint1, closestPoint2;
    private double closestDistance = Double.POSITIVE_INFINITY;
    
    // UI Components
    private Label statusLabel;
    private Button clearButton;
    private Button randomButton;
    private Button findClosestButton;
    private Slider pointCountSlider;
    private Label pointCountLabel;

    /**
     * Start the JavaFX application.
     */
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        
        // Initialize the UI
        initializeUI();
        
        // Set up the stage
        stage.setTitle("Closest Pair Algorithm Demo");
        stage.setScene(createScene());
        stage.setResizable(true);
        stage.show();
        
        // Initialize the canvas
        initializeCanvas();
    }

    /**
     * Initialize the user interface components.
     */
    private void initializeUI() {
        // Create status label
        statusLabel = new Label("Click on the canvas to add points, or use the controls below.");
        statusLabel.setWrapText(true);
        statusLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        // Create control buttons
        clearButton = new Button("Clear All Points");
        clearButton.setOnAction(e -> clearPoints());
        
        randomButton = new Button("Generate Random Points");
        randomButton.setOnAction(e -> generateRandomPoints());
        
        findClosestButton = new Button("Find Closest Pair");
        findClosestButton.setOnAction(e -> findClosestPair());
        findClosestButton.setDisable(true);
        
        // Create point count slider
        pointCountLabel = new Label("Number of random points: 10");
        pointCountSlider = new Slider(5, 50, 10);
        pointCountSlider.setShowTickLabels(true);
        pointCountSlider.setShowTickMarks(true);
        pointCountSlider.setMajorTickUnit(5);
        pointCountSlider.setMinorTickCount(4);
        pointCountSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            pointCountLabel.setText("Number of random points: " + newVal.intValue());
        });
    }

    /**
     * Create the main scene with all UI components.
     */
    private Scene createScene() {
        // Create canvas
        canvas = new Canvas(800, 600);
        gc = canvas.getGraphicsContext2D();
        
        // Set up canvas mouse events
        canvas.setOnMouseClicked(this::handleCanvasClick);
        
        // Create control panel
        VBox controlPanel = new VBox(10);
        controlPanel.setPadding(new Insets(10));
        controlPanel.setAlignment(Pos.TOP_CENTER);
        controlPanel.setPrefWidth(200);
        controlPanel.setStyle("-fx-background-color: #f0f0f0;");
        
        // Add controls to panel
        controlPanel.getChildren().addAll(
            statusLabel,
            new Separator(),
            clearButton,
            randomButton,
            new Separator(),
            pointCountLabel,
            pointCountSlider,
            new Separator(),
            findClosestButton
        );
        
        // Create main layout
        HBox mainLayout = new HBox(10);
        mainLayout.setPadding(new Insets(10));
        mainLayout.getChildren().addAll(canvas, controlPanel);
        
        return new Scene(mainLayout);
    }

    /**
     * Initialize the canvas with default settings.
     */
    private void initializeCanvas() {
        // Set up drawing context
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
        // Set up StdDraw
        StdDraw.setCanvas(canvas);
        StdDraw.setScale(0, 1.0);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setPenRadius(0.005);
        
        // Draw grid
        drawGrid();
    }

    /**
     * Handle mouse clicks on the canvas to add points.
     */
    private void handleCanvasClick(MouseEvent event) {
        double x = event.getX() / canvas.getWidth();
        double y = 1.0 - (event.getY() / canvas.getHeight()); // Flip Y coordinate
        
        Point2D newPoint = new Point2D(x, y);
        points.add(newPoint);
        
        // Draw the new point
        drawPoint(newPoint, Color.BLUE);
        
        // Update UI
        updateStatus();
        findClosestButton.setDisable(points.size() < 2);
    }

    /**
     * Draw a point on the canvas.
     */
    private void drawPoint(Point2D point, Color color) {
        StdDraw.setPenColor(color);
        StdDraw.filledCircle(point.x(), point.y(), 0.01);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.circle(point.x(), point.y(), 0.01);
    }

    /**
     * Draw a line between two points.
     */
    private void drawLine(Point2D p1, Point2D p2, Color color) {
        StdDraw.setPenColor(color);
        StdDraw.setPenRadius(0.003);
        StdDraw.line(p1.x(), p1.y(), p2.x(), p2.y());
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(Color.BLACK);
    }

    /**
     * Draw a grid on the canvas.
     */
    private void drawGrid() {
        StdDraw.setPenColor(Color.LIGHTGRAY);
        StdDraw.setPenRadius(0.001);
        
        // Draw vertical lines
        for (int i = 1; i < 10; i++) {
            double x = i / 10.0;
            StdDraw.line(x, 0, x, 1);
        }
        
        // Draw horizontal lines
        for (int i = 1; i < 10; i++) {
            double y = i / 10.0;
            StdDraw.line(0, y, 1, y);
        }
        
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setPenRadius(0.005);
    }

    /**
     * Clear all points from the canvas.
     */
    private void clearPoints() {
        points.clear();
        closestPoint1 = null;
        closestPoint2 = null;
        closestDistance = Double.POSITIVE_INFINITY;
        
        // Redraw canvas
        StdDraw.clear();
        drawGrid();
        
        updateStatus();
        findClosestButton.setDisable(true);
    }

    /**
     * Generate random points on the canvas.
     */
    private void generateRandomPoints() {
        clearPoints();
        
        int count = (int) pointCountSlider.getValue();
        for (int i = 0; i < count; i++) {
            double x = StdRandom.uniform(0.1, 0.9);
            double y = StdRandom.uniform(0.1, 0.9);
            Point2D point = new Point2D(x, y);
            points.add(point);
            drawPoint(point, Color.BLUE);
        }
        
        updateStatus();
        findClosestButton.setDisable(points.size() < 2);
    }

    /**
     * Find the closest pair of points using the ClosestPair algorithm.
     */
    private void findClosestPair() {
        if (points.size() < 2) {
            showAlert("Not enough points", "Please add at least 2 points to find the closest pair.");
            return;
        }
        
        // Convert List to array
        Point2D[] pointsArray = points.toArray(new Point2D[0]);
        
        // Find closest pair
        closestPair = new ClosestPair(pointsArray);
        closestPoint1 = closestPair.either();
        closestPoint2 = closestPair.other();
        closestDistance = closestPair.distance();
        
        // Visualize the result
        visualizeClosestPair();
        
        updateStatus();
    }

    /**
     * Visualize the closest pair by highlighting the points and drawing a line.
     */
    private void visualizeClosestPair() {
        if (closestPoint1 != null && closestPoint2 != null) {
            // Redraw all points in blue
            for (Point2D point : points) {
                drawPoint(point, Color.BLUE);
            }
            
            // Highlight the closest pair in red
            drawPoint(closestPoint1, Color.RED);
            drawPoint(closestPoint2, Color.RED);
            
            // Draw line between closest pair
            drawLine(closestPoint1, closestPoint2, Color.RED);
        }
    }

    /**
     * Update the status label with current information.
     */
    private void updateStatus() {
        String status = String.format("Points: %d", points.size());
        
        if (closestPoint1 != null && closestPoint2 != null) {
            status += String.format(" | Closest distance: %.4f", closestDistance);
        }
        
        statusLabel.setText(status);
    }

    /**
     * Show an alert dialog.
     */
    private void showAlert(String title, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }
} 