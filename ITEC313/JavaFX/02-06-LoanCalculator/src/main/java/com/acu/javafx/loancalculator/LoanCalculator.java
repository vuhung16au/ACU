package com.acu.javafx.loancalculator;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Separator;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * JavaFX application for loan calculation.
 * This application provides a graphical user interface for calculating
 * loan payments based on annual interest rate, number of years, and loan amount.
 * 
 * @author ACU JavaFX Team
 * @version 1.0
 */
public class LoanCalculator extends Application {
    private TextField tfAnnualInterestRate = new TextField();
    private TextField tfNumberOfYears = new TextField();
    private TextField tfLoanAmount = new TextField();
    private TextField tfMonthlyPayment = new TextField();
    private TextField tfTotalPayment = new TextField();
    private Button btCalculate = new Button("Calculate");
    
    @Override
    public void start(Stage primaryStage) {
    // Create Input Section
    Label inputHeader = new Label("Input");
    inputHeader.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
    GridPane inputGrid = new GridPane();
    inputGrid.setHgap(5);
    inputGrid.setVgap(5);
    inputGrid.add(new Label("Annual Interest Rate (%):"), 0, 0);
    inputGrid.add(tfAnnualInterestRate, 1, 0);
    inputGrid.add(new Label("Number of Years:"), 0, 1);
    inputGrid.add(tfNumberOfYears, 1, 1);
    inputGrid.add(new Label("Loan Amount:"), 0, 2);
    inputGrid.add(tfLoanAmount, 1, 2);
    inputGrid.add(btCalculate, 1, 3);
    GridPane.setHalignment(btCalculate, HPos.RIGHT);

    // Create Output Section
    Label outputHeader = new Label("Output");
    outputHeader.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
    GridPane outputGrid = new GridPane();
    outputGrid.setHgap(5);
    outputGrid.setVgap(5);
    outputGrid.add(new Label("Monthly Payment:"), 0, 0);
    outputGrid.add(tfMonthlyPayment, 1, 0);
    outputGrid.add(new Label("Total Payment:"), 0, 1);
    outputGrid.add(tfTotalPayment, 1, 1);

    VBox root = new VBox(10, inputHeader, inputGrid, new Separator(), outputHeader, outputGrid);
    root.setAlignment(Pos.CENTER);
    root.setStyle("-fx-padding: 15;");

        // Set properties for UI
        tfAnnualInterestRate.setAlignment(Pos.BOTTOM_RIGHT);
        tfNumberOfYears.setAlignment(Pos.BOTTOM_RIGHT);
        tfLoanAmount.setAlignment(Pos.BOTTOM_RIGHT);
        tfMonthlyPayment.setAlignment(Pos.BOTTOM_RIGHT);
        tfTotalPayment.setAlignment(Pos.BOTTOM_RIGHT);
        tfMonthlyPayment.setEditable(false);
        tfTotalPayment.setEditable(false);

        // Process events
        btCalculate.setOnAction(e -> calculateLoanPayment());

        // Create a scene and place it in the stage
    Scene scene = new Scene(root, 450, 300);
        primaryStage.setTitle("Loan Calculator"); // Set title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }
    
    /**
     * Calculate loan payment based on user input.
     * This method reads values from text fields, creates a Loan object,
     * and displays the calculated monthly and total payments.
     */
    private void calculateLoanPayment() {
        try {
            // Get values from text fields
            double interest = Double.parseDouble(tfAnnualInterestRate.getText());
            int year = Integer.parseInt(tfNumberOfYears.getText());
            double loanAmount = Double.parseDouble(tfLoanAmount.getText());

            // Validate input values
            if (interest < 0 || year <= 0 || loanAmount <= 0) {
                showError("Invalid Input", "Please enter positive values for all fields.");
                return;
            }

            // Create a loan object
            Loan loan = new Loan(interest, year, loanAmount);

            // Display monthly payment and total payment
            tfMonthlyPayment.setText(String.format("$%.2f", loan.getMonthlyPayment()));
            tfTotalPayment.setText(String.format("$%.2f", loan.getTotalPayment()));
            
        } catch (NumberFormatException ex) {
            showError("Invalid Input", "Please enter valid numbers for all fields.");
        } catch (Exception ex) {
            showError("Calculation Error", "An error occurred during calculation: " + ex.getMessage());
        }
    }
    
    /**
     * Show an error dialog with the specified title and message.
     * 
     * @param title the dialog title
     * @param message the error message
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
} 