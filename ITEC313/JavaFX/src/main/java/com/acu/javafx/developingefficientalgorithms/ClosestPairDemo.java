package com.acu.javafx.developingefficientalgorithms;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.Arrays;
import java.util.Random;

/**
 * JavaFX demonstration of Closest Pair algorithm.
 * Shows the divide-and-conquer approach to find the closest pair of points.
 * Time Complexity: O(n log n) - Log-linear time complexity
 */
public class ClosestPairDemo {
    private Stage stage;
    private TextArea outputArea;
    private Button generateButton;
    private Button findClosestButton;
    private Button bruteForceButton;
    private Label complexityLabel;
    private Label descriptionLabel;
    private Point[] points;

    public static class Point {
        double x, y;
        
        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
        
        public double distance(Point other) {
            double dx = this.x - other.x;
            double dy = this.y - other.y;
            return Math.sqrt(dx * dx + dy * dy);
        }
        
        @Override
        public String toString() {
            return "(" + String.format("%.2f", x) + ", " + String.format("%.2f", y) + ")";
        }
    }

    public void show() {
        stage = new Stage();
        stage.setTitle("Closest Pair Algorithm Demo");
        stage.setWidth(900);
        stage.setHeight(700);

        // Initialize points
        generateRandomPoints();

        // Create UI components
        createUI();

        Scene scene = new Scene(createMainLayout());
        stage.setScene(scene);
        stage.show();
    }

    private void createUI() {
        // Title
        Label titleLabel = new Label("Closest Pair Algorithm Demo");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.DARKBLUE);

        // Description
        descriptionLabel = new Label(
            "Closest Pair algorithm finds the two points with minimum distance between them.\n" +
            "Uses divide-and-conquer approach: O(n log n) time complexity\n" +
            "Brute force approach: O(n²) time complexity\n" +
            "Space Complexity: O(n) - Linear space complexity"
        );
        descriptionLabel.setWrapText(true);
        descriptionLabel.setStyle("-fx-font-size: 14px;");

        // Complexity information
        complexityLabel = new Label("Divide-and-Conquer: O(n log n), Brute Force: O(n²)");
        complexityLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2E8B57;");

        // Control buttons
        generateButton = new Button("Generate New Points");
        generateButton.setOnAction(e -> {
            generateRandomPoints();
            updatePointsDisplay();
        });
        
        findClosestButton = new Button("Find Closest Pair (Divide-and-Conquer)");
        findClosestButton.setOnAction(e -> findClosestPair());
        
        bruteForceButton = new Button("Find Closest Pair (Brute Force)");
        bruteForceButton.setOnAction(e -> findClosestPairBruteForce());

        // Output area
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(25);
        outputArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");
    }

    private VBox createMainLayout() {
        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));

        // Title and description
        VBox headerBox = new VBox(10);
        headerBox.getChildren().addAll(
            new Label("Closest Pair Algorithm Demo"),
            descriptionLabel,
            complexityLabel
        );

        // Control buttons
        VBox controlBox = new VBox(10);
        controlBox.getChildren().addAll(
            new Label("Algorithm Controls:"),
            createControlButtons()
        );

        // Points display
        VBox pointsBox = new VBox(10);
        pointsBox.getChildren().addAll(
            new Label("Current Points:"),
            createPointsDisplay()
        );

        // Output area
        VBox outputBox = new VBox(10);
        outputBox.getChildren().addAll(
            new Label("Results:"),
            outputArea
        );
        VBox.setVgrow(outputArea, Priority.ALWAYS);

        mainLayout.getChildren().addAll(headerBox, controlBox, pointsBox, outputBox);
        return mainLayout;
    }

    private HBox createControlButtons() {
        HBox buttonBox = new HBox(10);
        
        generateButton = new Button("Generate New Points");
        generateButton.setOnAction(e -> {
            generateRandomPoints();
            updatePointsDisplay();
        });
        
        findClosestButton = new Button("Find Closest Pair (Divide-and-Conquer)");
        findClosestButton.setOnAction(e -> findClosestPair());
        
        bruteForceButton = new Button("Find Closest Pair (Brute Force)");
        bruteForceButton.setOnAction(e -> findClosestPairBruteForce());

        buttonBox.getChildren().addAll(generateButton, findClosestButton, bruteForceButton);
        return buttonBox;
    }

    private HBox createPointsDisplay() {
        HBox pointsDisplay = new HBox(5);
        pointsDisplay.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10; -fx-border-color: #ccc; -fx-border-width: 1;");
        
        for (int i = 0; i < points.length; i++) {
            Label pointLabel = new Label("P" + i + ": " + points[i].toString());
            pointLabel.setStyle("-fx-background-color: white; -fx-padding: 5; -fx-border-color: #999; -fx-border-width: 1; -fx-min-width: 80; -fx-alignment: center;");
            pointsDisplay.getChildren().add(pointLabel);
        }
        
        return pointsDisplay;
    }

    private void updatePointsDisplay() {
        outputArea.clear();
        outputArea.appendText("Generated " + points.length + " random points:\n");
        for (int i = 0; i < points.length; i++) {
            outputArea.appendText("P" + i + ": " + points[i].toString() + "\n");
        }
        outputArea.appendText("\n");
    }

    private void generateRandomPoints() {
        Random random = new Random();
        points = new Point[10]; // Generate 10 points
        
        for (int i = 0; i < points.length; i++) {
            double x = random.nextDouble() * 100; // Random x in [0, 100)
            double y = random.nextDouble() * 100; // Random y in [0, 100)
            points[i] = new Point(x, y);
        }
    }

    private void findClosestPair() {
        outputArea.clear();
        outputArea.appendText("=== Closest Pair (Divide-and-Conquer) ===\n\n");
        
        outputArea.appendText("Points:\n");
        for (int i = 0; i < points.length; i++) {
            outputArea.appendText("P" + i + ": " + points[i].toString() + "\n");
        }
        outputArea.appendText("\n");

        long startTime = System.nanoTime();
        ClosestPairResult result = closestPair(points);
        long time = System.nanoTime() - startTime;

        outputArea.appendText("Algorithm Steps:\n");
        outputArea.appendText("1. Sort points by x-coordinate\n");
        outputArea.appendText("2. Divide points into left and right halves\n");
        outputArea.appendText("3. Recursively find closest pairs in each half\n");
        outputArea.appendText("4. Find closest pair that spans the dividing line\n");
        outputArea.appendText("5. Return the minimum of the three distances\n\n");

        outputArea.appendText("Results:\n");
        outputArea.appendText("Closest pair: " + result.point1.toString() + " and " + result.point2.toString() + "\n");
        outputArea.appendText("Distance: " + String.format("%.4f", result.distance) + "\n");
        outputArea.appendText("Time: " + time / 1000000.0 + " ms\n");
        outputArea.appendText("Time Complexity: O(n log n)\n");
        outputArea.appendText("Space Complexity: O(n)\n");
    }

    private void findClosestPairBruteForce() {
        outputArea.clear();
        outputArea.appendText("=== Closest Pair (Brute Force) ===\n\n");
        
        outputArea.appendText("Points:\n");
        for (int i = 0; i < points.length; i++) {
            outputArea.appendText("P" + i + ": " + points[i].toString() + "\n");
        }
        outputArea.appendText("\n");

        long startTime = System.nanoTime();
        ClosestPairResult result = closestPairBruteForce(points);
        long time = System.nanoTime() - startTime;

        outputArea.appendText("Algorithm Steps:\n");
        outputArea.appendText("1. Compare all pairs of points\n");
        outputArea.appendText("2. Calculate distance between each pair\n");
        outputArea.appendText("3. Keep track of the minimum distance found\n\n");

        outputArea.appendText("Results:\n");
        outputArea.appendText("Closest pair: " + result.point1.toString() + " and " + result.point2.toString() + "\n");
        outputArea.appendText("Distance: " + String.format("%.4f", result.distance) + "\n");
        outputArea.appendText("Time: " + time / 1000000.0 + " ms\n");
        outputArea.appendText("Time Complexity: O(n²)\n");
        outputArea.appendText("Space Complexity: O(1)\n");
    }

    public static class ClosestPairResult {
        Point point1, point2;
        double distance;
        
        public ClosestPairResult(Point p1, Point p2, double dist) {
            this.point1 = p1;
            this.point2 = p2;
            this.distance = dist;
        }
    }

    /**
     * Divide-and-conquer closest pair algorithm
     * Time Complexity: O(n log n)
     * @param points array of points
     * @return closest pair result
     */
    public static ClosestPairResult closestPair(Point[] points) {
        if (points.length < 2) {
            return null;
        }
        
        // Sort points by x-coordinate
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints, (p1, p2) -> Double.compare(p1.x, p2.x));
        
        return closestPairRecursive(sortedPoints, 0, sortedPoints.length - 1);
    }

    private static ClosestPairResult closestPairRecursive(Point[] points, int left, int right) {
        if (right - left <= 2) {
            return closestPairBruteForce(Arrays.copyOfRange(points, left, right + 1));
        }
        
        int mid = (left + right) / 2;
        double midX = points[mid].x;
        
        // Recursively find closest pairs in left and right halves
        ClosestPairResult leftResult = closestPairRecursive(points, left, mid);
        ClosestPairResult rightResult = closestPairRecursive(points, mid + 1, right);
        
        // Find the minimum distance from the two halves
        ClosestPairResult minResult = (leftResult.distance < rightResult.distance) ? leftResult : rightResult;
        
        // Find closest pair that spans the dividing line
        ClosestPairResult stripResult = closestPairInStrip(points, left, right, midX, minResult.distance);
        
        if (stripResult != null && stripResult.distance < minResult.distance) {
            return stripResult;
        }
        
        return minResult;
    }

    private static ClosestPairResult closestPairInStrip(Point[] points, int left, int right, double midX, double minDist) {
        // Collect points within minDist of the dividing line
        Point[] strip = new Point[right - left + 1];
        int stripSize = 0;
        
        for (int i = left; i <= right; i++) {
            if (Math.abs(points[i].x - midX) < minDist) {
                strip[stripSize++] = points[i];
            }
        }
        
        // Sort strip by y-coordinate
        Arrays.sort(strip, 0, stripSize, (p1, p2) -> Double.compare(p1.y, p2.y));
        
        // Check only the next 7 points for each point in the strip
        ClosestPairResult minResult = null;
        double minDistance = minDist;
        
        for (int i = 0; i < stripSize; i++) {
            for (int j = i + 1; j < Math.min(i + 8, stripSize); j++) {
                double dist = strip[i].distance(strip[j]);
                if (dist < minDistance) {
                    minDistance = dist;
                    minResult = new ClosestPairResult(strip[i], strip[j], dist);
                }
            }
        }
        
        return minResult;
    }

    /**
     * Brute force closest pair algorithm
     * Time Complexity: O(n²)
     * @param points array of points
     * @return closest pair result
     */
    public static ClosestPairResult closestPairBruteForce(Point[] points) {
        if (points.length < 2) {
            return null;
        }
        
        Point closest1 = points[0];
        Point closest2 = points[1];
        double minDistance = closest1.distance(closest2);
        
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double distance = points[i].distance(points[j]);
                if (distance < minDistance) {
                    minDistance = distance;
                    closest1 = points[i];
                    closest2 = points[j];
                }
            }
        }
        
        return new ClosestPairResult(closest1, closest2, minDistance);
    }
} 