package com.acu.javafx.loancalculator;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
        // Create UI
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.add(new Label("Annual Interest Rate (%):"), 0, 0);
        gridPane.add(tfAnnualInterestRate, 1, 0);
        gridPane.add(new Label("Number of Years:"), 0, 1);
        gridPane.add(tfNumberOfYears, 1, 1);
        gridPane.add(new Label("Loan Amount:"), 0, 2);
        gridPane.add(tfLoanAmount, 1, 2);
        gridPane.add(new Label("Monthly Payment:"), 0, 3);
        gridPane.add(tfMonthlyPayment, 1, 3);
        gridPane.add(new Label("Total Payment:"), 0, 4);
        gridPane.add(tfTotalPayment, 1, 4);
        gridPane.add(btCalculate, 1, 5);

        // Set properties for UI
        gridPane.setAlignment(Pos.CENTER);
        tfAnnualInterestRate.setAlignment(Pos.BOTTOM_RIGHT);
        tfNumberOfYears.setAlignment(Pos.BOTTOM_RIGHT);
        tfLoanAmount.setAlignment(Pos.BOTTOM_RIGHT);
        tfMonthlyPayment.setAlignment(Pos.BOTTOM_RIGHT);
        tfTotalPayment.setAlignment(Pos.BOTTOM_RIGHT);
        tfMonthlyPayment.setEditable(false);
        tfTotalPayment.setEditable(false);
        GridPane.setHalignment(btCalculate, HPos.RIGHT);

        // Process events
        btCalculate.setOnAction(e -> calculateLoanPayment());

        // Create a scene and place it in the stage
        Scene scene = new Scene(gridPane, 400, 250);
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