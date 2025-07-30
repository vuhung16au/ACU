package com.acu.javafx.primenumbers;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.scene.control.ScrollPane;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class PrimeNumbersDemo extends Application {

    private TextArea outputArea;
    private TextField inputField;
    private ComboBox<String> algorithmComboBox;
    private Button runButton;
    private Button clearButton;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Prime Numbers Algorithms Demo");

        // Create the main layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);

        // Title
        Label titleLabel = new Label("Prime Numbers Algorithms");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setAlignment(Pos.CENTER);

        // Algorithm selection
        Label algorithmLabel = new Label("Select Algorithm:");
        algorithmLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        algorithmComboBox = new ComboBox<>();
        algorithmComboBox.getItems().addAll(
            "PrimeNumber - First 50 primes",
            "PrimeNumbers - Find primes <= n",
            "EfficientPrimeNumbers - Optimized algorithm",
            "SieveOfEratosthenes - Sieve algorithm"
        );
        algorithmComboBox.setValue("PrimeNumber - First 50 primes");
        algorithmComboBox.setPrefWidth(300);

        // Input field
        Label inputLabel = new Label("Enter n (for algorithms that need input):");
        inputLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        inputField = new TextField();
        inputField.setPromptText("Enter a number (e.g., 100)");
        inputField.setPrefWidth(200);

        // Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        
        runButton = new Button("Run Algorithm");
        runButton.setPrefWidth(120);
        runButton.setOnAction(e -> runAlgorithm());
        
        clearButton = new Button("Clear Output");
        clearButton.setPrefWidth(120);
        clearButton.setOnAction(e -> clearOutput());

        buttonBox.getChildren().addAll(runButton, clearButton);

        // Output area
        Label outputLabel = new Label("Output:");
        outputLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(20);
        outputArea.setPrefColumnCount(80);
        outputArea.setFont(Font.font("Monospaced", 12));

        ScrollPane scrollPane = new ScrollPane(outputArea);
        scrollPane.setPrefViewportWidth(800);
        scrollPane.setPrefViewportHeight(400);

        // Add all components to the main layout
        root.getChildren().addAll(
            titleLabel,
            algorithmLabel,
            algorithmComboBox,
            inputLabel,
            inputField,
            buttonBox,
            outputLabel,
            scrollPane
        );

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(850);
        primaryStage.setMinHeight(700);
        primaryStage.show();
    }

    private void runAlgorithm() {
        String selectedAlgorithm = algorithmComboBox.getValue();
        String input = inputField.getText().trim();
        
        // Capture console output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);

        try {
            switch (selectedAlgorithm) {
                case "PrimeNumber - First 50 primes":
                    PrimeNumber.main(new String[]{});
                    break;
                case "PrimeNumbers - Find primes <= n":
                    if (input.isEmpty()) {
                        appendOutput("Please enter a number for this algorithm.\n");
                        return;
                    }
                    simulateUserInput(input);
                    PrimeNumbers.main(new String[]{});
                    break;
                case "EfficientPrimeNumbers - Optimized algorithm":
                    if (input.isEmpty()) {
                        appendOutput("Please enter a number for this algorithm.\n");
                        return;
                    }
                    simulateUserInput(input);
                    EfficientPrimeNumbers.main(new String[]{});
                    break;
                case "SieveOfEratosthenes - Sieve algorithm":
                    if (input.isEmpty()) {
                        appendOutput("Please enter a number for this algorithm.\n");
                        return;
                    }
                    simulateUserInput(input);
                    SieveOfEratosthenes.main(new String[]{});
                    break;
            }
        } catch (Exception e) {
            appendOutput("Error running algorithm: " + e.getMessage() + "\n");
        } finally {
            System.out.flush();
            System.setOut(old);
        }

        String output = baos.toString();
        appendOutput("=== " + selectedAlgorithm + " ===\n");
        appendOutput(output);
        appendOutput("\n" + "=".repeat(50) + "\n\n");
    }

    private void simulateUserInput(String input) {
        // Create a custom Scanner that returns our input
        System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));
    }

    private void appendOutput(String text) {
        outputArea.appendText(text);
        // Auto-scroll to bottom
        outputArea.setScrollTop(Double.MAX_VALUE);
    }

    private void clearOutput() {
        outputArea.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
} 