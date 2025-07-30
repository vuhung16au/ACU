package com.acu.javafx.developingefficientalgorithms;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * JavaFX demonstration of Euclid's Algorithm for finding GCD.
 * Shows the step-by-step process of finding the greatest common divisor.
 * Time Complexity: O(log min(a,b)) - Logarithmic time complexity
 */
public class EuclidDemo {
    private Stage stage;
    private TextArea outputArea;
    private TextField aField;
    private TextField bField;
    private Button calculateButton;
    private Button stepButton;
    private Label complexityLabel;
    private Label descriptionLabel;

    public void show() {
        stage = new Stage();
        stage.setTitle("Euclid's Algorithm Demo");
        stage.setWidth(800);
        stage.setHeight(600);

        // Create UI components
        createUI();

        Scene scene = new Scene(createMainLayout());
        stage.setScene(scene);
        stage.show();
    }

    private void createUI() {
        // Title
        Label titleLabel = new Label("Euclid's Algorithm Demo");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.DARKBLUE);

        // Description
        descriptionLabel = new Label(
            "Euclid's Algorithm finds the Greatest Common Divisor (GCD) of two numbers.\n" +
            "The algorithm is based on the principle that GCD(a,b) = GCD(b, a mod b).\n" +
            "Time Complexity: O(log min(a,b)) - Logarithmic time complexity\n" +
            "Space Complexity: O(1) - Constant space complexity"
        );
        descriptionLabel.setWrapText(true);
        descriptionLabel.setStyle("-fx-font-size: 14px;");

        // Complexity information
        complexityLabel = new Label("Time Complexity: O(log min(a,b)), Space Complexity: O(1)");
        complexityLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2E8B57;");

        // Input controls
        HBox inputBox = new HBox(10);
        Label aLabel = new Label("a =");
        aField = new TextField();
        aField.setPromptText("Enter first number");
        aField.setPrefWidth(100);
        
        Label bLabel = new Label("b =");
        bField = new TextField();
        bField.setPromptText("Enter second number");
        bField.setPrefWidth(100);
        
        calculateButton = new Button("Calculate GCD");
        calculateButton.setOnAction(e -> calculateGCD());
        
        stepButton = new Button("Step-by-Step");
        stepButton.setOnAction(e -> calculateGCDStepByStep());

        inputBox.getChildren().addAll(aLabel, aField, bLabel, bField, calculateButton, stepButton);

        // Output area
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(20);
        outputArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");
    }

    private VBox createMainLayout() {
        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));

        // Title and description
        VBox headerBox = new VBox(10);
        headerBox.getChildren().addAll(
            new Label("Euclid's Algorithm Demo"),
            descriptionLabel,
            complexityLabel
        );

        // Input controls
        VBox controlBox = new VBox(10);
        controlBox.getChildren().addAll(
            new Label("Input Values:"),
            createInputControls()
        );

        // Output area
        VBox outputBox = new VBox(10);
        outputBox.getChildren().addAll(
            new Label("Calculation Results:"),
            outputArea
        );
        VBox.setVgrow(outputArea, Priority.ALWAYS);

        mainLayout.getChildren().addAll(headerBox, controlBox, outputBox);
        return mainLayout;
    }

    private HBox createInputControls() {
        HBox inputBox = new HBox(10);
        Label aLabel = new Label("a =");
        aField = new TextField();
        aField.setPromptText("Enter first number");
        aField.setPrefWidth(100);
        
        Label bLabel = new Label("b =");
        bField = new TextField();
        bField.setPromptText("Enter second number");
        bField.setPrefWidth(100);
        
        calculateButton = new Button("Calculate GCD");
        calculateButton.setOnAction(e -> calculateGCD());
        
        stepButton = new Button("Step-by-Step");
        stepButton.setOnAction(e -> calculateGCDStepByStep());

        inputBox.getChildren().addAll(aLabel, aField, bLabel, bField, calculateButton, stepButton);
        return inputBox;
    }

    private void calculateGCD() {
        try {
            int a = Integer.parseInt(aField.getText());
            int b = Integer.parseInt(bField.getText());
            
            if (a < 0 || b < 0) {
                outputArea.appendText("Please enter non-negative numbers!\n");
                return;
            }

            outputArea.clear();
            outputArea.appendText("=== Euclid's Algorithm for GCD(" + a + ", " + b + ") ===\n\n");

            long startTime = System.nanoTime();
            int gcd = euclidGCD(a, b);
            long time = System.nanoTime() - startTime;

            outputArea.appendText("Input: a = " + a + ", b = " + b + "\n");
            outputArea.appendText("GCD(" + a + ", " + b + ") = " + gcd + "\n");
            outputArea.appendText("Time: " + time / 1000000.0 + " ms\n");
            outputArea.appendText("Time Complexity: O(log min(a,b))\n");
            outputArea.appendText("Space Complexity: O(1)\n\n");

            // Show some properties
            outputArea.appendText("Properties:\n");
            outputArea.appendText("  • GCD(" + a + ", " + b + ") = " + gcd + "\n");
            outputArea.appendText("  • GCD(" + b + ", " + a + ") = " + euclidGCD(b, a) + " (commutative)\n");
            outputArea.appendText("  • GCD(" + a + ", 0) = " + euclidGCD(a, 0) + "\n");
            outputArea.appendText("  • GCD(0, " + b + ") = " + euclidGCD(0, b) + "\n");

        } catch (NumberFormatException e) {
            outputArea.appendText("Please enter valid numbers!\n");
        }
    }

    private void calculateGCDStepByStep() {
        try {
            int a = Integer.parseInt(aField.getText());
            int b = Integer.parseInt(bField.getText());
            
            if (a < 0 || b < 0) {
                outputArea.appendText("Please enter non-negative numbers!\n");
                return;
            }

            outputArea.clear();
            outputArea.appendText("=== Step-by-Step Euclid's Algorithm ===\n");
            outputArea.appendText("Finding GCD(" + a + ", " + b + ")\n\n");

            int step = 1;
            int tempA = a;
            int tempB = b;

            outputArea.appendText("Initial values: a = " + tempA + ", b = " + tempB + "\n\n");

            while (tempB != 0) {
                outputArea.appendText("Step " + step + ":\n");
                outputArea.appendText("  a = " + tempA + ", b = " + tempB + "\n");
                
                int remainder = tempA % tempB;
                outputArea.appendText("  " + tempA + " % " + tempB + " = " + remainder + "\n");
                
                tempA = tempB;
                tempB = remainder;
                
                outputArea.appendText("  New a = " + tempA + ", New b = " + tempB + "\n\n");
                step++;
            }

            outputArea.appendText("Final result: GCD(" + a + ", " + b + ") = " + tempA + "\n");
            outputArea.appendText("Total steps: " + (step - 1) + "\n");

        } catch (NumberFormatException e) {
            outputArea.appendText("Please enter valid numbers!\n");
        }
    }

    /**
     * Euclid's Algorithm for finding GCD
     * Time Complexity: O(log min(a,b))
     * @param a first number
     * @param b second number
     * @return GCD of a and b
     */
    public static int euclidGCD(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    /**
     * Recursive version of Euclid's Algorithm
     * Time Complexity: O(log min(a,b))
     * @param a first number
     * @param b second number
     * @return GCD of a and b
     */
    public static int euclidGCDRecursive(int a, int b) {
        if (b == 0) {
            return a;
        }
        return euclidGCDRecursive(b, a % b);
    }

    /**
     * Extended Euclid's Algorithm
     * Finds GCD and coefficients x, y such that ax + by = GCD(a,b)
     * @param a first number
     * @param b second number
     * @return array [gcd, x, y]
     */
    public static int[] extendedEuclid(int a, int b) {
        if (b == 0) {
            return new int[]{a, 1, 0};
        }
        
        int[] result = extendedEuclid(b, a % b);
        int gcd = result[0];
        int x = result[2];
        int y = result[1] - (a / b) * result[2];
        
        return new int[]{gcd, x, y};
    }
} 