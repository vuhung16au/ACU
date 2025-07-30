package com.acu.javafx.liststackqueuepriorityqueue;

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
 * Main JavaFX application demonstrating Lists, Stacks, Queues, and Priority Queues.
 * This application provides a GUI interface to run various examples from the lecture.
 */
public class ListStackQueuePriorityQueueApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX List Stack Queue PriorityQueue Demo");

        // Create main layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);

        // Title
        Label titleLabel = new Label("JavaFX List Stack Queue PriorityQueue Demo");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setAlignment(Pos.CENTER);

        // Description
        Label descriptionLabel = new Label(
            "This application demonstrates various data structures and algorithms:\n" +
            "• Lists (ArrayList vs LinkedList)\n" +
            "• Comparators and Sorting\n" +
            "• Stacks and Expression Evaluation\n" +
            "• Queues and Priority Queues\n" +
            "• JavaFX Animations with Collections"
        );
        descriptionLabel.setFont(Font.font("Arial", 14));
        descriptionLabel.setAlignment(Pos.CENTER);
        descriptionLabel.setWrapText(true);

        // Create buttons for different demos
        Button testArrayLinkedListBtn = new Button("Test Array and LinkedList");
        Button geometricObjectComparatorBtn = new Button("Geometric Object Comparator");
        Button sortStringByLengthBtn = new Button("Sort String by Length");
        Button sortStringIgnoreCaseBtn = new Button("Sort String Ignore Case");
        Button multipleBounceBallBtn = new Button("Multiple Bounce Ball Animation");
        Button priorityQueueDemoBtn = new Button("Priority Queue Demo");
        Button evaluateExpressionBtn = new Button("Evaluate Expression");

        // Style buttons
        String buttonStyle = """
            -fx-background-color: #4CAF50;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-padding: 10px 20px;
            -fx-cursor: hand;
            """;
        
        testArrayLinkedListBtn.setStyle(buttonStyle);
        geometricObjectComparatorBtn.setStyle(buttonStyle);
        sortStringByLengthBtn.setStyle(buttonStyle);
        sortStringIgnoreCaseBtn.setStyle(buttonStyle);
        multipleBounceBallBtn.setStyle(buttonStyle);
        priorityQueueDemoBtn.setStyle(buttonStyle);
        evaluateExpressionBtn.setStyle(buttonStyle);

        // Set button actions
        testArrayLinkedListBtn.setOnAction(e -> runTestArrayAndLinkedList());
        geometricObjectComparatorBtn.setOnAction(e -> runGeometricObjectComparator());
        sortStringByLengthBtn.setOnAction(e -> runSortStringByLength());
        sortStringIgnoreCaseBtn.setOnAction(e -> runSortStringIgnoreCase());
        multipleBounceBallBtn.setOnAction(e -> runMultipleBounceBall());
        priorityQueueDemoBtn.setOnAction(e -> runPriorityQueueDemo());
        evaluateExpressionBtn.setOnAction(e -> runEvaluateExpression());

        // Create button layout
        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(
            testArrayLinkedListBtn,
            geometricObjectComparatorBtn,
            sortStringByLengthBtn,
            sortStringIgnoreCaseBtn,
            multipleBounceBallBtn,
            priorityQueueDemoBtn,
            evaluateExpressionBtn
        );

        // Add all components to root
        root.getChildren().addAll(titleLabel, descriptionLabel, buttonBox);

        // Create scene
        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Runs the TestArrayAndLinkedList demo
     */
    private void runTestArrayAndLinkedList() {
        TestArrayAndLinkedList.main(new String[0]);
    }

    /**
     * Runs the GeometricObjectComparator demo
     */
    private void runGeometricObjectComparator() {
        GeometricObjectComparatorDemo.main(new String[0]);
    }

    /**
     * Runs the SortStringByLength demo
     */
    private void runSortStringByLength() {
        SortStringByLength.main(new String[0]);
    }

    /**
     * Runs the SortStringIgnoreCase demo
     */
    private void runSortStringIgnoreCase() {
        SortStringIgnoreCase.main(new String[0]);
    }

    /**
     * Runs the MultipleBounceBall demo
     */
    private void runMultipleBounceBall() {
        MultipleBounceBall.main(new String[0]);
    }

    /**
     * Runs the PriorityQueueDemo
     */
    private void runPriorityQueueDemo() {
        PriorityQueueDemo.main(new String[0]);
    }

    /**
     * Runs the EvaluateExpression demo
     */
    private void runEvaluateExpression() {
        // Create a simple dialog for expression input
        TextInputDialog dialog = new TextInputDialog("(3+4)*2");
        dialog.setTitle("Evaluate Expression");
        dialog.setHeaderText("Enter an arithmetic expression:");
        dialog.setContentText("Expression:");

        dialog.showAndWait().ifPresent(expression -> {
            try {
                int result = EvaluateExpression.evaluateExpression(expression);
                showAlert("Result", "Expression: " + expression + "\nResult: " + result);
            } catch (Exception ex) {
                showAlert("Error", "Invalid expression: " + expression + "\nError: " + ex.getMessage());
            }
        });
    }

    /**
     * Shows an alert dialog
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Main method for IDE support
     */
    public static void main(String[] args) {
        launch(args);
    }
} 