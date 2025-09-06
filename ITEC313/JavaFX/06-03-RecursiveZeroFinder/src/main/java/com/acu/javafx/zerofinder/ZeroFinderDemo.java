package com.acu.javafx.zerofinder;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.function.Function;

/**
 * JavaFX Application for Recursive Zero Finder
 * 
 * This application provides a graphical user interface for finding zeros of functions
 * using the recursive binary search algorithm. Users can input a function expression,
 * search bounds, and tolerance, then find the zero interactively.
 * 
 * @author ACU JavaFX Team
 * @version 1.0.0
 */
public class ZeroFinderDemo extends Application {
    
    private TextField functionField;
    private TextField leftBoundField;
    private TextField rightBoundField;
    private TextField toleranceField;
    private TextArea resultArea;
    private Button findZeroButton;
    private Button clearButton;
    private Button exitButton;
    
    @Override
    public void start(Stage primaryStage) {
        // Set up the main window
        primaryStage.setTitle("Recursive Zero Finder - JavaFX Demo");
        primaryStage.setResizable(true);
        
        // Create the main layout
        VBox mainLayout = createMainLayout();
        
        // Create the scene
        Scene scene = new Scene(mainLayout, 600, 500);
        primaryStage.setScene(scene);
        
        // Show the stage
        primaryStage.show();
    }
    
    /**
     * Creates the main layout of the application
     * 
     * @return The main VBox layout
     */
    private VBox createMainLayout() {
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);
        
        // Add title
        Text title = new Text("Recursive Zero Finder");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        mainLayout.getChildren().add(title);
        
        // Add description
        Text description = new Text("Find the root of a function using recursive binary search");
        description.setFont(Font.font("Arial", 14));
        mainLayout.getChildren().add(description);
        
        // Add input section
        VBox inputSection = createInputSection();
        mainLayout.getChildren().add(inputSection);
        
        // Add button section
        HBox buttonSection = createButtonSection();
        mainLayout.getChildren().add(buttonSection);
        
        // Add result section
        VBox resultSection = createResultSection();
        mainLayout.getChildren().add(resultSection);
        
        return mainLayout;
    }
    
    /**
     * Creates the input section with text fields for function parameters
     * 
     * @return The input section VBox
     */
    private VBox createInputSection() {
        VBox inputSection = new VBox(15);
        inputSection.setPadding(new Insets(20));
        inputSection.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 10;");
        
        // Function input
        HBox functionBox = new HBox(10);
        functionBox.setAlignment(Pos.CENTER_LEFT);
        Label functionLabel = new Label("f(x) =");
        functionLabel.setMinWidth(60);
        functionField = new TextField();
        functionField.setPromptText("e.g., x*x - 4 or x^2 - 4");
        functionField.setText("x*x - 4"); // Default example
        functionField.setPrefWidth(200);
        functionBox.getChildren().addAll(functionLabel, functionField);
        
        // Bounds input
        HBox boundsBox = new HBox(20);
        boundsBox.setAlignment(Pos.CENTER);
        
        HBox leftBoundBox = new HBox(10);
        leftBoundBox.setAlignment(Pos.CENTER_LEFT);
        Label leftLabel = new Label("Left bound (l):");
        leftLabel.setMinWidth(100);
        leftBoundField = new TextField();
        leftBoundField.setPromptText("e.g., 0");
        leftBoundField.setText("0"); // Default example
        leftBoundField.setPrefWidth(100);
        leftBoundBox.getChildren().addAll(leftLabel, leftBoundField);
        
        HBox rightBoundBox = new HBox(10);
        rightBoundBox.setAlignment(Pos.CENTER_LEFT);
        Label rightLabel = new Label("Right bound (r):");
        rightLabel.setMinWidth(100);
        rightBoundField = new TextField();
        rightBoundField.setPromptText("e.g., 4");
        rightBoundField.setText("4"); // Default example
        rightBoundField.setPrefWidth(100);
        rightBoundBox.getChildren().addAll(rightLabel, rightBoundField);
        
        boundsBox.getChildren().addAll(leftBoundBox, rightBoundBox);
        
        // Tolerance input
        HBox toleranceBox = new HBox(10);
        toleranceBox.setAlignment(Pos.CENTER_LEFT);
        Label toleranceLabel = new Label("Tolerance (ε):");
        toleranceLabel.setMinWidth(100);
        toleranceField = new TextField();
        toleranceField.setPromptText("e.g., 0.001");
        toleranceField.setText("0.001"); // Default example
        toleranceField.setPrefWidth(100);
        toleranceBox.getChildren().addAll(toleranceLabel, toleranceField);
        
        inputSection.getChildren().addAll(functionBox, boundsBox, toleranceBox);
        
        return inputSection;
    }
    
    /**
     * Creates the button section with action buttons
     * 
     * @return The button section HBox
     */
    private HBox createButtonSection() {
        HBox buttonSection = new HBox(20);
        buttonSection.setAlignment(Pos.CENTER);
        
        // Find Zero button
        findZeroButton = new Button("Find Zero");
        findZeroButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        findZeroButton.setPrefWidth(120);
        findZeroButton.setOnAction(e -> findZero());
        
        // Clear button
        clearButton = new Button("Clear");
        clearButton.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-weight: bold;");
        clearButton.setPrefWidth(120);
        clearButton.setOnAction(e -> clearInputs());
        
        // Exit button
        exitButton = new Button("Exit");
        exitButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-font-weight: bold;");
        exitButton.setPrefWidth(120);
        exitButton.setOnAction(e -> System.exit(0));
        
        buttonSection.getChildren().addAll(findZeroButton, clearButton, exitButton);
        
        return buttonSection;
    }
    
    /**
     * Creates the result section with output area
     * 
     * @return The result section VBox
     */
    private VBox createResultSection() {
        VBox resultSection = new VBox(10);
        resultSection.setPadding(new Insets(20));
        resultSection.setStyle("-fx-background-color: #e8f5e8; -fx-background-radius: 10;");
        
        Label resultLabel = new Label("Results:");
        resultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        resultArea = new TextArea();
        resultArea.setPrefRowCount(8);
        resultArea.setEditable(false);
        resultArea.setWrapText(true);
        resultArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12;");
        
        resultSection.getChildren().addAll(resultLabel, resultArea);
        
        return resultSection;
    }
    
    /**
     * Handles the Find Zero button click event
     */
    private void findZero() {
        try {
            // Get input values
            String functionExpression = functionField.getText().trim();
            double leftBound = Double.parseDouble(leftBoundField.getText().trim());
            double rightBound = Double.parseDouble(rightBoundField.getText().trim());
            double tolerance = Double.parseDouble(toleranceField.getText().trim());
            
            // Validate inputs
            if (functionExpression.isEmpty()) {
                showError("Please enter a function expression");
                return;
            }
            
            if (leftBound >= rightBound) {
                showError("Left bound must be less than right bound");
                return;
            }
            
            if (tolerance <= 0) {
                showError("Tolerance must be positive");
                return;
            }
            
            // Create function from expression
            Function<Double, Double> function = RecursiveZeroFinder.createFunction(functionExpression);
            
            // Find the zero
            long startTime = System.nanoTime();
            double zero = RecursiveZeroFinder.findZero(function, leftBound, rightBound, tolerance);
            long endTime = System.nanoTime();
            
            // Calculate execution time
            double executionTime = (endTime - startTime) / 1_000_000.0; // Convert to milliseconds
            
            // Display results
            StringBuilder result = new StringBuilder();
            result.append("=== Zero Finding Results ===\n");
            result.append(String.format("Function: f(x) = %s\n", functionExpression));
            result.append(String.format("Search interval: [%.6f, %.6f]\n", leftBound, rightBound));
            result.append(String.format("Tolerance: %.6f\n", tolerance));
            result.append(String.format("Found zero: x = %.8f\n", zero));
            result.append(String.format("Function value at zero: f(%.8f) = %.8f\n", zero, function.apply(zero)));
            result.append(String.format("Execution time: %.3f ms\n", executionTime));
            result.append("\n=== Verification ===\n");
            result.append(String.format("|f(%.8f)| = %.8f\n", zero, Math.abs(function.apply(zero))));
            result.append(String.format("Tolerance check: %.8f <= %.6f ? %s\n", 
                Math.abs(function.apply(zero)), tolerance, 
                Math.abs(function.apply(zero)) <= tolerance ? "✓ PASS" : "✗ FAIL"));
            
            resultArea.setText(result.toString());
            
        } catch (NumberFormatException e) {
            showError("Please enter valid numbers for bounds and tolerance");
        } catch (IllegalArgumentException e) {
            showError("Invalid input: " + e.getMessage());
        } catch (ArithmeticException e) {
            showError("Mathematical error: " + e.getMessage());
        } catch (Exception e) {
            showError("Unexpected error: " + e.getMessage());
        }
    }
    
    /**
     * Handles the Clear button click event
     */
    private void clearInputs() {
        functionField.setText("x*x - 4");
        leftBoundField.setText("0");
        rightBoundField.setText("4");
        toleranceField.setText("0.001");
        resultArea.clear();
    }
    
    /**
     * Shows an error message in the result area
     * 
     * @param message The error message to display
     */
    private void showError(String message) {
        resultArea.setText("ERROR: " + message + "\n\nPlease check your inputs and try again.");
    }
    
    /**
     * Main method to launch the application
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
