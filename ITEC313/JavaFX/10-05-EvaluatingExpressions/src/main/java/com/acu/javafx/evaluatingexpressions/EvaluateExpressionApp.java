package com.acu.javafx.evaluatingexpressions;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Minimal JavaFX UI to enter an arithmetic expression and display the result.
 */
public class EvaluateExpressionApp extends Application {
    @Override
    public void start(Stage stage) {
        TextField input = new TextField();
        input.setPromptText("Enter expression, e.g. (1+2)*3-4/2");
        Button evalBtn = new Button("Evaluate");
        Label result = new Label();

        evalBtn.setOnAction(e -> {
            try {
                double value = EvaluateExpression.evaluate(input.getText());
                result.setText("Result: " + value);
            } catch (IllegalArgumentException ex) {
                result.setText("Error: " + ex.getMessage());
            }
        });

        GridPane root = new GridPane();
        root.setHgap(8);
        root.setVgap(8);
        root.setPadding(new Insets(12));
        root.addRow(0, new Label("Expression:"), input);
        root.add(evalBtn, 1, 1);
        root.add(result, 1, 2);

        stage.setTitle("Evaluating Expressions");
        stage.setScene(new Scene(root, 560, 160));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


