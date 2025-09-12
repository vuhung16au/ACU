package com.acu.javafx.postfix;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

/**
 * JavaFX UI for postfix expression evaluation with step-by-step visualization.
 * Enter expression in text field, click Evaluate to see animated stack operations.
 */
public class App extends Application {
    
    private final TextField inputField = new TextField();
    private final TextArea resultArea = new TextArea();
    private final Button evaluateButton = new Button("Evaluate");
    private final Button stepButton = new Button("Step");
    private final Button resetButton = new Button("Reset");
    
    private final VBox stackVisualization = new VBox(5);
    private final TextArea stepDescription = new TextArea();
    private final Label currentTokenLabel = new Label("Current token: ");
    
    private List<PostfixEvaluator.Step> steps;
    private int currentStepIndex = 0;
    private Timeline timeline;
    
    @Override
    public void start(Stage stage) {
        stage.setTitle("Postfix Expression Evaluator with Visualization");
        
        // Input section
        Label inputLabel = new Label("Enter postfix expression:");
        inputField.setPromptText("e.g., 1 2 + 3 *");
        inputField.setPrefColumnCount(20);
        
        HBox inputBox = new HBox(10, inputLabel, inputField, evaluateButton);
        inputBox.setAlignment(Pos.CENTER_LEFT);
        inputBox.setPadding(new Insets(10));
        
        // Control buttons
        HBox controlBox = new HBox(10, stepButton, resetButton);
        controlBox.setAlignment(Pos.CENTER);
        controlBox.setPadding(new Insets(5));
        
        // Visualization section
        Label vizLabel = new Label("Stack Visualization:");
        stackVisualization.setPrefHeight(200);
        stackVisualization.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-padding: 10;");
        
        stepDescription.setEditable(false);
        stepDescription.setPrefRowCount(3);
        stepDescription.setPrefColumnCount(50);
        
        VBox visualizationBox = new VBox(5, vizLabel, stackVisualization, currentTokenLabel, stepDescription);
        visualizationBox.setPadding(new Insets(10));
        
        // Result section
        Label resultLabel = new Label("Result:");
        resultArea.setEditable(false);
        resultArea.setPrefRowCount(4);
        resultArea.setPrefColumnCount(30);
        
        VBox resultBox = new VBox(5, resultLabel, resultArea);
        resultBox.setPadding(new Insets(10));
        
        // Event handlers
        evaluateButton.setOnAction(e -> evaluateExpression());
        inputField.setOnAction(e -> evaluateExpression());
        stepButton.setOnAction(e -> showNextStep());
        resetButton.setOnAction(e -> resetVisualization());
        
        // Layout
        BorderPane root = new BorderPane();
        root.setTop(inputBox);
        root.setCenter(visualizationBox);
        root.setBottom(resultBox);
        root.setRight(controlBox);
        
        Scene scene = new Scene(root, 700, 500);
        stage.setScene(scene);
        stage.show();
    }
    
    private void evaluateExpression() {
        String expression = inputField.getText().trim();
        if (expression.isEmpty()) {
            resultArea.setText("Please enter a postfix expression.");
            return;
        }
        
        try {
            steps = PostfixEvaluator.evaluateWithSteps(expression);
            currentStepIndex = 0;
            resetVisualization();
            showNextStep();
            
            double result = PostfixEvaluator.evaluate(expression);
            resultArea.setText(String.format("Expression: %s\nResult: %.2f", expression, result));
        } catch (IllegalArgumentException e) {
            resultArea.setText("Error: " + e.getMessage());
        } catch (Exception e) {
            resultArea.setText("Unexpected error: " + e.getMessage());
        }
    }
    
    private void showNextStep() {
        if (steps == null || currentStepIndex >= steps.size()) {
            return;
        }
        
        PostfixEvaluator.Step step = steps.get(currentStepIndex);
        currentTokenLabel.setText("Current token: " + step.token);
        stepDescription.setText(step.description);
        
        // Update stack visualization
        stackVisualization.getChildren().clear();
        stackVisualization.getChildren().add(new Text("Stack (top to bottom):"));
        
        for (int i = step.stackState.size() - 1; i >= 0; i--) {
            Rectangle rect = new Rectangle(100, 30);
            rect.setFill(Color.LIGHTBLUE);
            rect.setStroke(Color.BLACK);
            
            Text value = new Text(String.format("%.1f", step.stackState.get(i)));
            value.setFont(Font.font(14));
            
            StackPane stackItem = new StackPane(rect, value);
            stackVisualization.getChildren().add(stackItem);
        }
        
        if (step.stackState.isEmpty()) {
            Text emptyText = new Text("(empty)");
            emptyText.setFill(Color.GRAY);
            stackVisualization.getChildren().add(emptyText);
        }
        
        currentStepIndex++;
        
        if (currentStepIndex >= steps.size()) {
            stepButton.setText("Complete");
            stepButton.setDisable(true);
        }
    }
    
    private void resetVisualization() {
        stackVisualization.getChildren().clear();
        stackVisualization.getChildren().add(new Text("Stack (top to bottom):"));
        Text emptyText = new Text("(empty)");
        emptyText.setFill(Color.GRAY);
        stackVisualization.getChildren().add(emptyText);
        
        currentTokenLabel.setText("Current token: ");
        stepDescription.setText("Click 'Step' to see each operation, or 'Evaluate' to process a new expression.");
        
        currentStepIndex = 0;
        stepButton.setText("Step");
        stepButton.setDisable(false);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
