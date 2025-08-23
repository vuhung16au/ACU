package com.acu.junit;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Calculator class demonstrating JUnit 5 features.
 */
@DisplayName("Calculator Tests")
class CalculatorTests {
    
    private Calculator calculator;
    
    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }
    
    @Nested
    @DisplayName("Addition Tests")
    class AdditionTests {
        
        @Test
        @DisplayName("Should add two positive numbers")
        void shouldAddTwoPositiveNumbers() {
            assertEquals(4, calculator.add(2, 2));
        }
        
        @Test
        @DisplayName("Should add positive and negative numbers")
        void shouldAddPositiveAndNegativeNumbers() {
            assertEquals(0, calculator.add(5, -5));
        }
        
        @Test
        @DisplayName("Should add two negative numbers")
        void shouldAddTwoNegativeNumbers() {
            assertEquals(-10, calculator.add(-3, -7));
        }
        
        @ParameterizedTest
        @CsvSource({
            "1, 2, 3",
            "0, 0, 0",
            "-1, 1, 0",
            "100, 200, 300"
        })
        @DisplayName("Should add various number combinations")
        void shouldAddVariousNumberCombinations(int a, int b, int expected) {
            assertEquals(expected, calculator.add(a, b));
        }
    }
    
    @Nested
    @DisplayName("Subtraction Tests")
    class SubtractionTests {
        
        @Test
        @DisplayName("Should subtract two positive numbers")
        void shouldSubtractTwoPositiveNumbers() {
            assertEquals(2, calculator.subtract(5, 3));
        }
        
        @Test
        @DisplayName("Should subtract negative number")
        void shouldSubtractNegativeNumber() {
            assertEquals(8, calculator.subtract(3, -5));
        }
        
        @ParameterizedTest
        @CsvSource({
            "5, 3, 2",
            "0, 0, 0",
            "1, 1, 0",
            "10, 15, -5"
        })
        @DisplayName("Should subtract various number combinations")
        void shouldSubtractVariousNumberCombinations(int a, int b, int expected) {
            assertEquals(expected, calculator.subtract(a, b));
        }
    }
    
    @Nested
    @DisplayName("Multiplication Tests")
    class MultiplicationTests {
        
        @Test
        @DisplayName("Should multiply two positive numbers")
        void shouldMultiplyTwoPositiveNumbers() {
            assertEquals(6, calculator.multiply(2, 3));
        }
        
        @Test
        @DisplayName("Should multiply by zero")
        void shouldMultiplyByZero() {
            assertEquals(0, calculator.multiply(5, 0));
        }
        
        @Test
        @DisplayName("Should multiply negative numbers")
        void shouldMultiplyNegativeNumbers() {
            assertEquals(15, calculator.multiply(-3, -5));
        }
        
        @ParameterizedTest
        @CsvSource({
            "2, 3, 6",
            "0, 5, 0",
            "-2, 4, -8",
            "10, 10, 100"
        })
        @DisplayName("Should multiply various number combinations")
        void shouldMultiplyVariousNumberCombinations(int a, int b, int expected) {
            assertEquals(expected, calculator.multiply(a, b));
        }
    }
    
    @Nested
    @DisplayName("Division Tests")
    class DivisionTests {
        
        @Test
        @DisplayName("Should divide two positive numbers")
        void shouldDivideTwoPositiveNumbers() {
            assertEquals(2.5, calculator.divide(5, 2), 0.001);
        }
        
        @Test
        @DisplayName("Should divide by one")
        void shouldDivideByOne() {
            assertEquals(7.0, calculator.divide(7, 1), 0.001);
        }
        
        @Test
        @DisplayName("Should throw exception when dividing by zero")
        void shouldThrowExceptionWhenDividingByZero() {
            ArithmeticException exception = assertThrows(ArithmeticException.class, () -> {
                calculator.divide(5, 0);
            });
            
            assertEquals("Division by zero is not allowed", exception.getMessage());
        }
        
        @ParameterizedTest
        @CsvSource({
            "10, 2, 5.0",
            "15, 3, 5.0",
            "7, 2, 3.5",
            "0, 5, 0.0"
        })
        @DisplayName("Should divide various number combinations")
        void shouldDivideVariousNumberCombinations(int a, int b, double expected) {
            assertEquals(expected, calculator.divide(a, b), 0.001);
        }
    }
    
    @Test
    @DisplayName("Calculator instance should not be null")
    void calculatorInstanceShouldNotBeNull() {
        assertNotNull(calculator);
    }
    
    @Test
    @DisplayName("Should handle edge cases")
    void shouldHandleEdgeCases() {
        // Test with Integer.MAX_VALUE
        assertEquals(Integer.MAX_VALUE + 1, calculator.add(Integer.MAX_VALUE, 1));
        
        // Test with Integer.MIN_VALUE
        assertEquals(Integer.MIN_VALUE - 1, calculator.subtract(Integer.MIN_VALUE, 1));
    }
}
