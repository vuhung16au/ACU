package com.acu.javafx.calculator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;

/**
 * JavaFX application for Flat Precedence Calculator
 * 
 * This application demonstrates a calculator that treats all operators
 * (+, -, *, /) with equal precedence, evaluating from left to right.
 * 
 * Example: 4 + 3 - 2 * 10 = ((4 + 3) - 2) * 10 = 50
 */
public class FlatPrecedenceCalculator extends Application {
    
    private TextField expressionInput;
    private TextArea resultArea;
    private TextArea stepsArea;
    private FlatPrecedenceCalculatorEngine calculator;
    
    @Override
    public void start(Stage primaryStage) {
        calculator = new FlatPrecedenceCalculatorEngine();
        
        // Create the main layout
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f5f5;");
        
        // Create title
        Text title = new Text("Flat Precedence Calculator");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setStyle("-fx-fill: #2c3e50;");
        
        // Create description
        Text description = new Text(
            "This calculator treats all operators (+, -, *, /) with equal precedence.\n" +
            "Operations are evaluated from left to right.\n" +
            "Example: 4 + 3 - 2 * 10 = ((4 + 3) - 2) * 10 = 50"
        );
        description.setFont(Font.font("Arial", 14));
        description.setStyle("-fx-fill: #34495e;");
        description.setWrappingWidth(600);
        
        // Create input section
        VBox inputSection = createInputSection();
        
        // Create result section
        VBox resultSection = createResultSection();
        
        // Create steps visualization section
        VBox stepsSection = createStepsSection();
        
        // Add all sections to root
        root.getChildren().addAll(
            title,
            description,
            inputSection,
            resultSection,
            stepsSection
        );
        
        // Create scene
        Scene scene = new Scene(root, 800, 700);
        primaryStage.setTitle("Flat Precedence Calculator");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    /**
     * Creates the input section with text field and evaluate button
     */
    private VBox createInputSection() {
        VBox inputSection = new VBox(10);
        inputSection.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 8;");
        
        Label inputLabel = new Label("Enter Expression:");
        inputLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        inputLabel.setStyle("-fx-text-fill: #2c3e50;");
        
        expressionInput = new TextField();
        expressionInput.setPromptText("e.g., 4 + 3 - 2 * 10");
        expressionInput.setFont(Font.font("Arial", 14));
        expressionInput.setPrefHeight(40);
        expressionInput.setStyle("-fx-background-radius: 5; -fx-border-radius: 5;");
        
        // Handle Enter key press
        expressionInput.setOnAction(e -> evaluateExpression());
        
        Button evaluateButton = new Button("Evaluate");
        evaluateButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        evaluateButton.setPrefHeight(40);
        evaluateButton.setPrefWidth(120);
        evaluateButton.setStyle(
            "-fx-background-color: #3498db; " +
            "-fx-text-fill: white; " +
            "-fx-background-radius: 5; " +
            "-fx-cursor: hand;"
        );
        evaluateButton.setOnAction(e -> evaluateExpression());
        
        // Hover effects
        evaluateButton.setOnMouseEntered(e -> 
            evaluateButton.setStyle(
                "-fx-background-color: #2980b9; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 5; " +
                "-fx-cursor: hand;"
            )
        );
        evaluateButton.setOnMouseExited(e -> 
            evaluateButton.setStyle(
                "-fx-background-color: #3498db; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 5; " +
                "-fx-cursor: hand;"
            )
        );
        
        HBox inputRow = new HBox(10);
        inputRow.getChildren().addAll(expressionInput, evaluateButton);
        inputRow.setAlignment(Pos.CENTER_LEFT);
        
        inputSection.getChildren().addAll(inputLabel, inputRow);
        return inputSection;
    }
    
    /**
     * Creates the result display section
     */
    private VBox createResultSection() {
        VBox resultSection = new VBox(10);
        resultSection.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 8;");
        
        Label resultLabel = new Label("Result:");
        resultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        resultLabel.setStyle("-fx-text-fill: #2c3e50;");
        
        resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setPrefRowCount(3);
        resultArea.setFont(Font.font("Arial", 16));
        resultArea.setStyle(
            "-fx-background-color: #ecf0f1; " +
            "-fx-border-color: #bdc3c7; " +
            "-fx-background-radius: 5; " +
            "-fx-border-radius: 5;"
        );
        
        resultSection.getChildren().addAll(resultLabel, resultArea);
        return resultSection;
    }
    
    /**
     * Creates the steps visualization section
     */
    private VBox createStepsSection() {
        VBox stepsSection = new VBox(10);
        stepsSection.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 8;");
        
        Label stepsLabel = new Label("Calculation Steps:");
        stepsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        stepsLabel.setStyle("-fx-text-fill: #2c3e50;");
        
        stepsArea = new TextArea();
        stepsArea.setEditable(false);
        stepsArea.setPrefRowCount(8);
        stepsArea.setFont(Font.font("Monaco", 12));
        stepsArea.setStyle(
            "-fx-background-color: #f8f9fa; " +
            "-fx-border-color: #bdc3c7; " +
            "-fx-background-radius: 5; " +
            "-fx-border-radius: 5;"
        );
        
        stepsSection.getChildren().addAll(stepsLabel, stepsArea);
        return stepsSection;
    }
    
    /**
     * Evaluates the expression and displays results
     */
    private void evaluateExpression() {
        String expression = expressionInput.getText().trim();
        
        if (expression.isEmpty()) {
            showError("Please enter an expression to evaluate.");
            return;
        }
        
        try {
            // Calculate the result
            double result = calculator.calculate(expression);
            
            // Get detailed steps
            List<FlatPrecedenceCalculatorEngine.CalculationStep> steps = 
                calculator.calculateWithSteps(expression);
            
            // Display result
            resultArea.setText(String.format("Expression: %s\nResult: %.2f", expression, result));
            
            // Display steps
            StringBuilder stepsText = new StringBuilder();
            stepsText.append("Evaluation Process:\n");
            stepsText.append("==================\n");
            
            for (FlatPrecedenceCalculatorEngine.CalculationStep step : steps) {
                stepsText.append(step.toString()).append("\n");
            }
            
            stepsText.append("\nFinal Result: ").append(String.format("%.2f", result));
            
            stepsArea.setText(stepsText.toString());
            
        } catch (IllegalArgumentException e) {
            showError("Invalid expression: " + e.getMessage());
        } catch (ArithmeticException e) {
            showError("Math error: " + e.getMessage());
        } catch (Exception e) {
            showError("Unexpected error: " + e.getMessage());
        }
    }
    
    /**
     * Shows an error message in the result area
     */
    private void showError(String message) {
        resultArea.setText("Error: " + message);
        stepsArea.setText("No calculation steps available due to error.");
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
