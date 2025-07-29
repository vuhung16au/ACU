package com.acu.javafx.loancalculator;

import java.util.Scanner;

/**
 * Test class for the Loan class.
 * This class provides a console-based interface for testing loan calculations.
 * 
 * @author ACU JavaFX Team
 * @version 1.0
 */
public class TestLoanClass {
    /**
     * Main method to test the Loan class.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Create a Scanner
        Scanner input = new Scanner(System.in);

        try {
            // Enter yearly interest rate
            System.out.print("Enter annual interest rate, for example, 8.25: ");
            double annualInterestRate = input.nextDouble();

            // Enter number of years
            System.out.print("Enter number of years as an integer: ");
            int numberOfYears = input.nextInt();

            // Enter loan amount
            System.out.print("Enter loan amount, for example, 120000.95: ");
            double loanAmount = input.nextDouble();

            // Validate input
            if (annualInterestRate < 0 || numberOfYears <= 0 || loanAmount <= 0) {
                System.out.println("Error: All values must be positive.");
                return;
            }

            // Create Loan object
            Loan loan = new Loan(annualInterestRate, numberOfYears, loanAmount);

            // Display loan date, monthly payment, and total payment
            System.out.printf("The loan was created on %s\n" +
                "The monthly payment is %.2f\nThe total payment is %.2f\n",
                loan.getLoanDate().toString(), loan.getMonthlyPayment(), 
                loan.getTotalPayment());
                
        } catch (Exception ex) {
            System.out.println("Error: Please enter valid numeric values.");
            System.out.println("Error details: " + ex.getMessage());
        } finally {
            input.close();
        }
    }
} 