package com.acu.javafx.loancalculator;

import java.util.Date;

/**
 * Loan class for calculating loan payments.
 * This class provides functionality to calculate monthly and total payments
 * based on annual interest rate, number of years, and loan amount.
 * 
 * @author ACU JavaFX Team
 * @version 1.0
 */
public class Loan {
    private double annualInterestRate;
    private int numberOfYears;
    private double loanAmount;
    private Date loanDate;

    /**
     * No-arg constructor with default values:
     * - Annual interest rate: 2.5%
     * - Number of years: 1
     * - Loan amount: $1000
     */
    public Loan() {
        this(2.5, 1, 1000);
    }

    /**
     * Construct a loan with specified annual interest rate,
     * number of years, and loan amount.
     * 
     * @param annualInterestRate the annual interest rate (percentage)
     * @param numberOfYears the number of years for the loan
     * @param loanAmount the loan amount
     */
    public Loan(double annualInterestRate, int numberOfYears, double loanAmount) {
        this.annualInterestRate = annualInterestRate;
        this.numberOfYears = numberOfYears;
        this.loanAmount = loanAmount;
        loanDate = new Date();
    }

    /**
     * Return the annual interest rate.
     * 
     * @return the annual interest rate
     */
    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    /**
     * Set a new annual interest rate.
     * 
     * @param annualInterestRate the new annual interest rate
     */
    public void setAnnualInterestRate(double annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }

    /**
     * Return the number of years.
     * 
     * @return the number of years
     */
    public int getNumberOfYears() {
        return numberOfYears;
    }

    /**
     * Set a new number of years.
     * 
     * @param numberOfYears the new number of years
     */
    public void setNumberOfYears(int numberOfYears) {
        this.numberOfYears = numberOfYears;
    }

    /**
     * Return the loan amount.
     * 
     * @return the loan amount
     */
    public double getLoanAmount() {
        return loanAmount;
    }

    /**
     * Set a new loan amount.
     * 
     * @param loanAmount the new loan amount
     */
    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    /**
     * Find monthly payment using the loan payment formula.
     * 
     * @return the monthly payment amount
     */
    public double getMonthlyPayment() {
        double monthlyInterestRate = annualInterestRate / 1200;
        double monthlyPayment = loanAmount * monthlyInterestRate / 
            (1 - (1 / Math.pow(1 + monthlyInterestRate, numberOfYears * 12)));
        return monthlyPayment;
    }

    /**
     * Find total payment by multiplying monthly payment by total number of payments.
     * 
     * @return the total payment amount
     */
    public double getTotalPayment() {
        double totalPayment = getMonthlyPayment() * numberOfYears * 12;
        return totalPayment;
    }

    /**
     * Return the loan date.
     * 
     * @return the loan date
     */
    public Date getLoanDate() {
        return loanDate;
    }
} 