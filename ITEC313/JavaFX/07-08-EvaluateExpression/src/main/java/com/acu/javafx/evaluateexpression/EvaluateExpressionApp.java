package com.acu.javafx.evaluateexpression;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * JavaFX application for evaluating mathematical expressions.
 * This application provides a graphical user interface for the EvaluateExpression functionality.
 */
public class EvaluateExpressionApp extends Application {

    private TextField expressionField;
    private TextArea resultArea;
    private Label titleLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Expression Evaluator");

        // Create the main layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        // Create title
        titleLabel = new Label("Expression Evaluator");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setAlignment(Pos.CENTER);

        // Create input section
        VBox inputSection = new VBox(10);
        inputSection.setAlignment(Pos.CENTER);

        Label inputLabel = new Label("Enter Expression:");
        inputLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        expressionField = new TextField();
        expressionField.setPromptText("e.g., (3+4)*2-1");
        expressionField.setPrefWidth(300);
        expressionField.setFont(Font.font("Arial", 14));

        // Create buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        Button evaluateButton = new Button("Evaluate");
        evaluateButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        evaluateButton.setOnAction(e -> evaluateExpression());

        Button clearButton = new Button("Clear");
        clearButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        clearButton.setOnAction(e -> clearFields());

        buttonBox.getChildren().addAll(evaluateButton, clearButton);

        // Create result section
        VBox resultSection = new VBox(10);
        resultSection.setAlignment(Pos.CENTER);

        Label resultLabel = new Label("Result:");
        resultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setPrefRowCount(5);
        resultArea.setPrefWidth(400);
        resultArea.setFont(Font.font("Arial", 12));

        // Add components to sections
        inputSection.getChildren().addAll(inputLabel, expressionField, buttonBox);
        resultSection.getChildren().addAll(resultLabel, resultArea);

        // Add sections to main layout
        root.getChildren().addAll(titleLabel, inputSection, resultSection);

        // Create scene
        Scene scene = new Scene(root, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        // Set up keyboard shortcuts
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    evaluateExpression();
                    break;
                case ESCAPE:
                    clearFields();
                    break;
            }
        });
    }

    /**
     * Evaluates the expression entered by the user.
     */
    private void evaluateExpression() {
        String expression = expressionField.getText().trim();
        
        if (expression.isEmpty()) {
            showError("Please enter an expression to evaluate.");
            return;
        }

        try {
            int result = EvaluateExpression.evaluateExpression(expression);
            resultArea.setText(String.format("Expression: %s\nResult: %d", expression, result));
        } catch (Exception ex) {
            showError("Invalid expression: " + expression + "\nError: " + ex.getMessage());
        }
    }

    /**
     * Clears all input and output fields.
     */
    private void clearFields() {
        expressionField.clear();
        resultArea.clear();
        expressionField.requestFocus();
    }

    /**
     * Shows an error message to the user.
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Evaluation Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
} 