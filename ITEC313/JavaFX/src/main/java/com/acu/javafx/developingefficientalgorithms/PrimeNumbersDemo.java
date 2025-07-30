package com.acu.javafx.developingefficientalgorithms;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX demonstration of Prime Number algorithms.
 * Shows different approaches for primality testing and prime number generation.
 * Basic primality test: O(√n)
 * Sieve of Eratosthenes: O(n log log n)
 */
public class PrimeNumbersDemo {
    private Stage stage;
    private TextArea outputArea;
    private TextField inputField;
    private Button testButton;
    private Button generateButton;
    private Button sieveButton;
    private Label complexityLabel;
    private Label descriptionLabel;

    public void show() {
        stage = new Stage();
        stage.setTitle("Prime Numbers Algorithm Demo");
        stage.setWidth(900);
        stage.setHeight(700);

        // Create UI components
        createUI();

        Scene scene = new Scene(createMainLayout());
        stage.setScene(scene);
        stage.show();
    }

    private void createUI() {
        // Title
        Label titleLabel = new Label("Prime Numbers Algorithm Demo");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.DARKBLUE);

        // Description
        descriptionLabel = new Label(
            "Prime numbers are natural numbers greater than 1 that have no positive divisors other than 1 and itself.\n" +
            "Basic primality test: O(√n) - Check divisibility up to square root\n" +
            "Sieve of Eratosthenes: O(n log log n) - Generate all primes up to n\n" +
            "Optimized primality test: O(√n) with early termination"
        );
        descriptionLabel.setWrapText(true);
        descriptionLabel.setStyle("-fx-font-size: 14px;");

        // Complexity information
        complexityLabel = new Label("Basic Test: O(√n), Sieve: O(n log log n), Optimized: O(√n)");
        complexityLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2E8B57;");

        // Input controls
        HBox inputBox = new HBox(10);
        Label inputLabel = new Label("Test number:");
        inputField = new TextField();
        inputField.setPromptText("Enter a number to test");
        inputField.setPrefWidth(150);
        
        testButton = new Button("Test Primality");
        testButton.setOnAction(e -> testPrimality());
        
        generateButton = new Button("Generate Primes");
        generateButton.setOnAction(e -> generatePrimes());
        
        sieveButton = new Button("Sieve of Eratosthenes");
        sieveButton.setOnAction(e -> runSieve());

        inputBox.getChildren().addAll(inputLabel, inputField, testButton, generateButton, sieveButton);

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
            new Label("Prime Numbers Algorithm Demo"),
            descriptionLabel,
            complexityLabel
        );

        // Input controls
        VBox controlBox = new VBox(10);
        controlBox.getChildren().addAll(
            new Label("Prime Number Controls:"),
            createInputControls()
        );

        // Output area
        VBox outputBox = new VBox(10);
        outputBox.getChildren().addAll(
            new Label("Results:"),
            outputArea
        );
        VBox.setVgrow(outputArea, Priority.ALWAYS);

        mainLayout.getChildren().addAll(headerBox, controlBox, outputBox);
        return mainLayout;
    }

    private HBox createInputControls() {
        HBox inputBox = new HBox(10);
        Label inputLabel = new Label("Test number:");
        inputField = new TextField();
        inputField.setPromptText("Enter a number to test");
        inputField.setPrefWidth(150);
        
        testButton = new Button("Test Primality");
        testButton.setOnAction(e -> testPrimality());
        
        generateButton = new Button("Generate Primes");
        generateButton.setOnAction(e -> generatePrimes());
        
        sieveButton = new Button("Sieve of Eratosthenes");
        sieveButton.setOnAction(e -> runSieve());

        inputBox.getChildren().addAll(inputLabel, inputField, testButton, generateButton, sieveButton);
        return inputBox;
    }

    private void testPrimality() {
        try {
            int n = Integer.parseInt(inputField.getText());
            if (n < 2) {
                outputArea.appendText("Please enter a number >= 2!\n");
                return;
            }

            outputArea.clear();
            outputArea.appendText("=== Primality Test for " + n + " ===\n\n");

            // Basic primality test
            long startTime = System.nanoTime();
            boolean isPrimeBasic = isPrimeBasic(n);
            long basicTime = System.nanoTime() - startTime;

            // Optimized primality test
            startTime = System.nanoTime();
            boolean isPrimeOptimized = isPrimeOptimized(n);
            long optimizedTime = System.nanoTime() - startTime;

            outputArea.appendText("Basic Primality Test:\n");
            outputArea.appendText("  Result: " + (isPrimeBasic ? "PRIME" : "NOT PRIME") + "\n");
            outputArea.appendText("  Time: " + basicTime / 1000000.0 + " ms\n");
            outputArea.appendText("  Complexity: O(√n)\n\n");

            outputArea.appendText("Optimized Primality Test:\n");
            outputArea.appendText("  Result: " + (isPrimeOptimized ? "PRIME" : "NOT PRIME") + "\n");
            outputArea.appendText("  Time: " + optimizedTime / 1000000.0 + " ms\n");
            outputArea.appendText("  Complexity: O(√n) with early termination\n\n");

            if (isPrimeBasic == isPrimeOptimized) {
                outputArea.appendText("✓ Results match!\n");
            } else {
                outputArea.appendText("✗ Results don't match!\n");
            }

            if (isPrimeBasic) {
                outputArea.appendText("✓ " + n + " is a prime number!\n");
            } else {
                outputArea.appendText("✗ " + n + " is not a prime number.\n");
            }

        } catch (NumberFormatException e) {
            outputArea.appendText("Please enter a valid number!\n");
        }
    }

    private void generatePrimes() {
        try {
            int limit = Integer.parseInt(inputField.getText());
            if (limit < 2) {
                outputArea.appendText("Please enter a number >= 2!\n");
                return;
            }

            outputArea.clear();
            outputArea.appendText("=== Generating Prime Numbers up to " + limit + " ===\n\n");

            // Generate primes using basic method
            long startTime = System.nanoTime();
            List<Integer> primesBasic = generatePrimesBasic(limit);
            long basicTime = System.nanoTime() - startTime;

            // Generate primes using sieve
            startTime = System.nanoTime();
            List<Integer> primesSieve = generatePrimesSieve(limit);
            long sieveTime = System.nanoTime() - startTime;

            outputArea.appendText("Basic Method:\n");
            outputArea.appendText("  Primes found: " + primesBasic.size() + "\n");
            outputArea.appendText("  Time: " + basicTime / 1000000.0 + " ms\n");
            outputArea.appendText("  Complexity: O(n√n)\n");
            outputArea.appendText("  First 10 primes: " + primesBasic.subList(0, Math.min(10, primesBasic.size())) + "\n\n");

            outputArea.appendText("Sieve of Eratosthenes:\n");
            outputArea.appendText("  Primes found: " + primesSieve.size() + "\n");
            outputArea.appendText("  Time: " + sieveTime / 1000000.0 + " ms\n");
            outputArea.appendText("  Complexity: O(n log log n)\n");
            outputArea.appendText("  First 10 primes: " + primesSieve.subList(0, Math.min(10, primesSieve.size())) + "\n\n");

            if (primesBasic.size() == primesSieve.size()) {
                outputArea.appendText("✓ Both methods found the same number of primes!\n");
            }

            double speedup = (double) basicTime / sieveTime;
            outputArea.appendText("Speedup: " + String.format("%.2f", speedup) + "x faster with Sieve method\n");

        } catch (NumberFormatException e) {
            outputArea.appendText("Please enter a valid number!\n");
        }
    }

    private void runSieve() {
        try {
            int limit = Integer.parseInt(inputField.getText());
            if (limit < 2) {
                outputArea.appendText("Please enter a number >= 2!\n");
                return;
            }

            outputArea.clear();
            outputArea.appendText("=== Sieve of Eratosthenes for numbers up to " + limit + " ===\n\n");

            long startTime = System.nanoTime();
            boolean[] isPrime = sieveOfEratosthenes(limit);
            long sieveTime = System.nanoTime() - startTime;

            outputArea.appendText("Sieve Process:\n");
            outputArea.appendText("  1. Create boolean array of size " + (limit + 1) + "\n");
            outputArea.appendText("  2. Mark multiples of each prime as non-prime\n");
            outputArea.appendText("  3. Collect remaining prime numbers\n\n");

            List<Integer> primes = new ArrayList<>();
            for (int i = 2; i <= limit; i++) {
                if (isPrime[i]) {
                    primes.add(i);
                }
            }

            outputArea.appendText("Results:\n");
            outputArea.appendText("  Total primes found: " + primes.size() + "\n");
            outputArea.appendText("  Time: " + sieveTime / 1000000.0 + " ms\n");
            outputArea.appendText("  Complexity: O(n log log n)\n\n");

            outputArea.appendText("Prime numbers: ");
            if (primes.size() <= 20) {
                outputArea.appendText(primes.toString() + "\n");
            } else {
                outputArea.appendText(primes.subList(0, 20) + "... (and " + (primes.size() - 20) + " more)\n");
            }

        } catch (NumberFormatException e) {
            outputArea.appendText("Please enter a valid number!\n");
        }
    }

    /**
     * Basic primality test
     * Time Complexity: O(√n)
     * @param n the number to test
     * @return true if n is prime, false otherwise
     */
    public static boolean isPrimeBasic(int n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        
        for (int i = 3; i <= Math.sqrt(n); i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    /**
     * Optimized primality test with early termination
     * Time Complexity: O(√n)
     * @param n the number to test
     * @return true if n is prime, false otherwise
     */
    public static boolean isPrimeOptimized(int n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        if (n < 9) return true;
        if (n % 3 == 0) return false;
        
        int sqrt = (int) Math.sqrt(n);
        for (int i = 5; i <= sqrt; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }

    /**
     * Generate primes using basic method
     * Time Complexity: O(n√n)
     * @param limit the upper limit
     * @return list of prime numbers
     */
    public static List<Integer> generatePrimesBasic(int limit) {
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= limit; i++) {
            if (isPrimeBasic(i)) {
                primes.add(i);
            }
        }
        return primes;
    }

    /**
     * Generate primes using Sieve of Eratosthenes
     * Time Complexity: O(n log log n)
     * @param limit the upper limit
     * @return list of prime numbers
     */
    public static List<Integer> generatePrimesSieve(int limit) {
        boolean[] isPrime = sieveOfEratosthenes(limit);
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= limit; i++) {
            if (isPrime[i]) {
                primes.add(i);
            }
        }
        return primes;
    }

    /**
     * Sieve of Eratosthenes algorithm
     * Time Complexity: O(n log log n)
     * @param limit the upper limit
     * @return boolean array where isPrime[i] is true if i is prime
     */
    public static boolean[] sieveOfEratosthenes(int limit) {
        boolean[] isPrime = new boolean[limit + 1];
        for (int i = 2; i <= limit; i++) {
            isPrime[i] = true;
        }
        
        for (int i = 2; i * i <= limit; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= limit; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        
        return isPrime;
    }
} 