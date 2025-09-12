package com.acu.javafx.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Test class for FlatPrecedenceCalculatorEngine
 * 
 * Tests the core calculator logic with flat operator precedence.
 * All operators (+, -, *, /) have equal precedence and are evaluated left to right.
 */
@DisplayName("Flat Precedence Calculator Engine Tests")
public class FlatPrecedenceCalculatorEngineTest {
    
    private FlatPrecedenceCalculatorEngine calculator;
    
    @BeforeEach
    void setUp() {
        calculator = new FlatPrecedenceCalculatorEngine();
    }
    
    @Test
    @DisplayName("Test single number evaluation")
    void testSingleNumber() {
        assertEquals(42.0, calculator.calculate("42"), 0.001);
        assertEquals(3.14, calculator.calculate("3.14"), 0.001);
        assertEquals(-5.0, calculator.calculate("-5"), 0.001);
    }
    
    @Test
    @DisplayName("Test basic addition with flat precedence")
    void testBasicAddition() {
        assertEquals(7.0, calculator.calculate("4 + 3"), 0.001);
        assertEquals(10.0, calculator.calculate("2 + 3 + 5"), 0.001);
    }
    
    @Test
    @DisplayName("Test basic subtraction with flat precedence")
    void testBasicSubtraction() {
        assertEquals(1.0, calculator.calculate("4 - 3"), 0.001);
        assertEquals(-6.0, calculator.calculate("2 - 3 - 5"), 0.001);
    }
    
    @Test
    @DisplayName("Test basic multiplication with flat precedence")
    void testBasicMultiplication() {
        assertEquals(12.0, calculator.calculate("4 * 3"), 0.001);
        assertEquals(30.0, calculator.calculate("2 * 3 * 5"), 0.001);
    }
    
    @Test
    @DisplayName("Test basic division with flat precedence")
    void testBasicDivision() {
        assertEquals(2.0, calculator.calculate("6 / 3"), 0.001);
        assertEquals(1.0, calculator.calculate("15 / 3 / 5"), 0.001);
    }
    
    @Test
    @DisplayName("Test flat precedence: 4 + 3 - 2 * 10 = 50")
    void testFlatPrecedenceExample() {
        // This is the main example from the requirements
        // 4 + 3 - 2 * 10 = ((4 + 3) - 2) * 10 = (7 - 2) * 10 = 5 * 10 = 50
        assertEquals(50.0, calculator.calculate("4 + 3 - 2 * 10"), 0.001);
    }
    
    @Test
    @DisplayName("Test mixed operations with flat precedence")
    void testMixedOperations() {
        // 2 + 3 * 4 - 1 = ((2 + 3) * 4) - 1 = (5 * 4) - 1 = 20 - 1 = 19
        assertEquals(19.0, calculator.calculate("2 + 3 * 4 - 1"), 0.001);
        
        // 10 - 2 + 3 * 2 = ((10 - 2) + 3) * 2 = (8 + 3) * 2 = 11 * 2 = 22
        assertEquals(22.0, calculator.calculate("10 - 2 + 3 * 2"), 0.001);
        
        // 5 * 2 + 3 - 1 = ((5 * 2) + 3) - 1 = (10 + 3) - 1 = 13 - 1 = 12
        assertEquals(12.0, calculator.calculate("5 * 2 + 3 - 1"), 0.001);
    }
    
    @Test
    @DisplayName("Test division with flat precedence")
    void testDivisionWithFlatPrecedence() {
        // 12 / 3 + 2 = (12 / 3) + 2 = 4 + 2 = 6
        assertEquals(6.0, calculator.calculate("12 / 3 + 2"), 0.001);
        
        // 20 - 4 / 2 = (20 - 4) / 2 = 16 / 2 = 8
        assertEquals(8.0, calculator.calculate("20 - 4 / 2"), 0.001);
        
        // 8 / 2 * 3 = (8 / 2) * 3 = 4 * 3 = 12
        assertEquals(12.0, calculator.calculate("8 / 2 * 3"), 0.001);
    }
    
    @Test
    @DisplayName("Test negative numbers")
    void testNegativeNumbers() {
        assertEquals(-1.0, calculator.calculate("-2 + 1"), 0.001);
        assertEquals(-3.0, calculator.calculate("2 - 5"), 0.001);
        assertEquals(-6.0, calculator.calculate("-2 * 3"), 0.001);
        assertEquals(-2.0, calculator.calculate("-6 / 3"), 0.001);
    }
    
    @Test
    @DisplayName("Test decimal numbers")
    void testDecimalNumbers() {
        assertEquals(3.5, calculator.calculate("1.5 + 2.0"), 0.001);
        assertEquals(2.5, calculator.calculate("5.0 / 2.0"), 0.001);
        assertEquals(7.5, calculator.calculate("2.5 * 3.0"), 0.001);
    }
    
    @Test
    @DisplayName("Test expressions without spaces")
    void testExpressionsWithoutSpaces() {
        assertEquals(7.0, calculator.calculate("4+3"), 0.001);
        assertEquals(1.0, calculator.calculate("4-3"), 0.001);
        assertEquals(12.0, calculator.calculate("4*3"), 0.001);
        assertEquals(2.0, calculator.calculate("6/3"), 0.001);
    }
    
    @Test
    @DisplayName("Test division by zero")
    void testDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> {
            calculator.calculate("5 / 0");
        });
        
        assertThrows(ArithmeticException.class, () -> {
            calculator.calculate("10 + 5 / 0");
        });
    }
    
    @Test
    @DisplayName("Test invalid expressions")
    void testInvalidExpressions() {
        // Empty expression
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculate("");
        });
        
        // Null expression
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculate(null);
        });
        
        // Invalid characters
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculate("4 + 3 % 2");
        });
        
        // Invalid number format
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculate("4 + abc");
        });
        
        // Expression starting with operator
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculate("+ 4 + 3");
        });
        
        // Expression ending with operator
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculate("4 + 3 +");
        });
    }
    
    @Test
    @DisplayName("Test calculation steps for flat precedence example")
    void testCalculationSteps() {
        List<FlatPrecedenceCalculatorEngine.CalculationStep> steps = 
            calculator.calculateWithSteps("4 + 3 - 2 * 10");
        
        assertEquals(3, steps.size());
        
        // Step 1: 4 + 3 = 7
        assertEquals("4 + 3", steps.get(0).getExpression());
        assertEquals("4.00 + 3.00", steps.get(0).getOperation());
        assertEquals(7.0, steps.get(0).getResult(), 0.001);
        assertEquals(1, steps.get(0).getStepNumber());
        
        // Step 2: 7 - 2 = 5
        assertEquals("7.0 - 2", steps.get(1).getExpression());
        assertEquals("7.00 - 2.00", steps.get(1).getOperation());
        assertEquals(5.0, steps.get(1).getResult(), 0.001);
        assertEquals(2, steps.get(1).getStepNumber());
        
        // Step 3: 5 * 10 = 50
        assertEquals("5.0 * 10", steps.get(2).getExpression());
        assertEquals("5.00 * 10.00", steps.get(2).getOperation());
        assertEquals(50.0, steps.get(2).getResult(), 0.001);
        assertEquals(3, steps.get(2).getStepNumber());
    }
    
    @Test
    @DisplayName("Test calculation steps for single number")
    void testCalculationStepsSingleNumber() {
        List<FlatPrecedenceCalculatorEngine.CalculationStep> steps = 
            calculator.calculateWithSteps("42");
        
        assertEquals(1, steps.size());
        assertEquals("42", steps.get(0).getExpression());
        assertEquals("Single number", steps.get(0).getOperation());
        assertEquals(42.0, steps.get(0).getResult(), 0.001);
        assertEquals(1, steps.get(0).getStepNumber());
    }
    
    @Test
    @DisplayName("Test calculation steps for simple addition")
    void testCalculationStepsSimpleAddition() {
        List<FlatPrecedenceCalculatorEngine.CalculationStep> steps = 
            calculator.calculateWithSteps("2 + 3 + 5");
        
        assertEquals(2, steps.size());
        
        // Step 1: 2 + 3 = 5
        assertEquals("2 + 3", steps.get(0).getExpression());
        assertEquals("2.00 + 3.00", steps.get(0).getOperation());
        assertEquals(5.0, steps.get(0).getResult(), 0.001);
        
        // Step 2: 5 + 5 = 10
        assertEquals("5.0 + 5", steps.get(1).getExpression());
        assertEquals("5.00 + 5.00", steps.get(1).getOperation());
        assertEquals(10.0, steps.get(1).getResult(), 0.001);
    }
    
    @Test
    @DisplayName("Test calculation steps with division")
    void testCalculationStepsWithDivision() {
        List<FlatPrecedenceCalculatorEngine.CalculationStep> steps = 
            calculator.calculateWithSteps("12 / 3 + 2");
        
        assertEquals(2, steps.size());
        
        // Step 1: 12 / 3 = 4
        assertEquals("12 / 3", steps.get(0).getExpression());
        assertEquals("12.00 / 3.00", steps.get(0).getOperation());
        assertEquals(4.0, steps.get(0).getResult(), 0.001);
        
        // Step 2: 4 + 2 = 6
        assertEquals("4.0 + 2", steps.get(1).getExpression());
        assertEquals("4.00 + 2.00", steps.get(1).getOperation());
        assertEquals(6.0, steps.get(1).getResult(), 0.001);
    }
    
    @Test
    @DisplayName("Test toString method of CalculationStep")
    void testCalculationStepToString() {
        FlatPrecedenceCalculatorEngine.CalculationStep step = 
            new FlatPrecedenceCalculatorEngine.CalculationStep("4 + 3", "4.00 + 3.00", 7.0, 1);
        
        assertEquals("Step 1: 4.00 + 3.00 = 7.00", step.toString());
    }
    
    @Test
    @DisplayName("Test complex expression with multiple operations")
    void testComplexExpression() {
        // 1 + 2 * 3 - 4 / 2 + 5 = ((((1 + 2) * 3) - 4) / 2) + 5 = (((3 * 3) - 4) / 2) + 5 = ((9 - 4) / 2) + 5 = (5 / 2) + 5 = 2.5 + 5 = 7.5
        assertEquals(7.5, calculator.calculate("1 + 2 * 3 - 4 / 2 + 5"), 0.001);
    }
    
    @Test
    @DisplayName("Test edge case with zero")
    void testEdgeCaseWithZero() {
        assertEquals(0.0, calculator.calculate("0 + 0"), 0.001);
        assertEquals(0.0, calculator.calculate("5 * 0"), 0.001);
        assertEquals(5.0, calculator.calculate("5 + 0"), 0.001);
    }
}
