package com.acu.javafx.loancalculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Loan class.
 * Tests loan calculation functionality and edge cases.
 * 
 * @author ACU JavaFX Team
 * @version 1.0
 */
public class LoanTest {
    
    private Loan loan;
    
    @BeforeEach
    void setUp() {
        loan = new Loan(8.25, 30, 200000);
    }
    
    @Test
    void testLoanConstructor() {
        assertEquals(8.25, loan.getAnnualInterestRate(), 0.001);
        assertEquals(30, loan.getNumberOfYears());
        assertEquals(200000, loan.getLoanAmount(), 0.001);
        assertNotNull(loan.getLoanDate());
    }
    
    @Test
    void testDefaultConstructor() {
        Loan defaultLoan = new Loan();
        assertEquals(2.5, defaultLoan.getAnnualInterestRate(), 0.001);
        assertEquals(1, defaultLoan.getNumberOfYears());
        assertEquals(1000, defaultLoan.getLoanAmount(), 0.001);
    }
    
    @Test
    void testMonthlyPaymentCalculation() {
        // Test with known values
        double monthlyPayment = loan.getMonthlyPayment();
        assertTrue(monthlyPayment > 0, "Monthly payment should be positive");
        assertTrue(monthlyPayment < 2000, "Monthly payment should be reasonable");
    }
    
    @Test
    void testTotalPaymentCalculation() {
        double totalPayment = loan.getTotalPayment();
        double monthlyPayment = loan.getMonthlyPayment();
        
        // Total payment should be monthly payment * number of months
        double expectedTotal = monthlyPayment * loan.getNumberOfYears() * 12;
        assertEquals(expectedTotal, totalPayment, 0.01);
    }
    
    @Test
    void testSetters() {
        loan.setAnnualInterestRate(5.0);
        assertEquals(5.0, loan.getAnnualInterestRate(), 0.001);
        
        loan.setNumberOfYears(15);
        assertEquals(15, loan.getNumberOfYears());
        
        loan.setLoanAmount(150000);
        assertEquals(150000, loan.getLoanAmount(), 0.001);
    }
    
    @Test
    void testHighInterestRate() {
        Loan highInterestLoan = new Loan(15.0, 10, 100000);
        double monthlyPayment = highInterestLoan.getMonthlyPayment();
        assertTrue(monthlyPayment > 0, "Monthly payment should be positive");
    }
    
    @Test
    void testShortTermLoan() {
        Loan shortTermLoan = new Loan(5.0, 1, 50000);
        double monthlyPayment = shortTermLoan.getMonthlyPayment();
        assertTrue(monthlyPayment > 0, "Monthly payment should be positive");
    }
} 