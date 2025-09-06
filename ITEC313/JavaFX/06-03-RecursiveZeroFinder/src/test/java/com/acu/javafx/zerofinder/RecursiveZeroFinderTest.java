package com.acu.javafx.zerofinder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Function;

/**
 * Test class for RecursiveZeroFinder
 * 
 * This class tests the core zero finding functionality, including edge cases,
 * error conditions, and various mathematical functions.
 */
public class RecursiveZeroFinderTest {
    
    private static final double TOLERANCE = 1e-6;
    private static final double LARGER_TOLERANCE = 1e-3;
    
    @Test
    @DisplayName("Test finding zero of x^2 - 4 in interval [0, 4]")
    void testQuadraticFunction() {
        // f(x) = x^2 - 4, zero at x = 2
        Function<Double, Double> f = x -> x * x - 4;
        
        double zero = RecursiveZeroFinder.findZero(f, 0, 4, TOLERANCE);
        
        // Should find zero near x = 2
        assertEquals(2.0, zero, TOLERANCE);
        assertEquals(0.0, f.apply(zero), TOLERANCE);
    }
    
    @Test
    @DisplayName("Test finding zero of x^2 - 4 in interval [-4, 0]")
    void testQuadraticFunctionNegative() {
        // f(x) = x^2 - 4, zero at x = -2
        Function<Double, Double> f = x -> x * x - 4;
        
        double zero = RecursiveZeroFinder.findZero(f, -4, 0, TOLERANCE);
        
        // Should find zero near x = -2
        assertEquals(-2.0, zero, TOLERANCE);
        assertEquals(0.0, f.apply(zero), TOLERANCE);
    }
    
    @Test
    @DisplayName("Test finding zero of x - 3 in interval [0, 5]")
    void testLinearFunction() {
        // f(x) = x - 3, zero at x = 3
        Function<Double, Double> f = x -> x - 3;
        
        double zero = RecursiveZeroFinder.findZero(f, 0, 5, TOLERANCE);
        
        // Should find zero at x = 3
        assertEquals(3.0, zero, TOLERANCE);
        assertEquals(0.0, f.apply(zero), TOLERANCE);
    }
    
    @Test
    @DisplayName("Test finding zero of x^3 - 8 in interval [0, 3]")
    void testCubicFunction() {
        // f(x) = x^3 - 8, zero at x = 2
        Function<Double, Double> f = x -> x * x * x - 8;
        
        double zero = RecursiveZeroFinder.findZero(f, 0, 3, TOLERANCE);
        
        // Should find zero at x = 2
        assertEquals(2.0, zero, TOLERANCE);
        assertEquals(0.0, f.apply(zero), TOLERANCE);
    }
    
    @Test
    @DisplayName("Test finding zero of sin(x) in interval [0, π]")
    void testTrigonometricFunction() {
        // f(x) = sin(x), zero at x = 0
        Function<Double, Double> f = Math::sin;
        
        double zero = RecursiveZeroFinder.findZero(f, 0, Math.PI, TOLERANCE);
        
        // Should find zero at x = 0 (since sin(0) = 0)
        assertEquals(0.0, zero, TOLERANCE);
        assertEquals(0.0, f.apply(zero), TOLERANCE);
    }
    
    @Test
    @DisplayName("Test finding zero of e^x - 1 in interval [-1, 1]")
    void testExponentialFunction() {
        // f(x) = e^x - 1, zero at x = 0
        Function<Double, Double> f = x -> Math.exp(x) - 1;
        
        double zero = RecursiveZeroFinder.findZero(f, -1, 1, TOLERANCE);
        
        // Should find zero at x = 0
        assertEquals(0.0, zero, TOLERANCE);
        assertEquals(0.0, f.apply(zero), TOLERANCE);
    }
    
    @Test
    @DisplayName("Test with very small tolerance")
    void testVerySmallTolerance() {
        // f(x) = x - 1, zero at x = 1
        Function<Double, Double> f = x -> x - 1;
        double verySmallTolerance = 1e-10;
        
        double zero = RecursiveZeroFinder.findZero(f, 0, 2, verySmallTolerance);
        
        // Should find zero very close to x = 1
        assertEquals(1.0, zero, verySmallTolerance);
        assertEquals(0.0, f.apply(zero), verySmallTolerance);
    }
    
    @Test
    @DisplayName("Test with larger tolerance")
    void testLargerTolerance() {
        // f(x) = x - 1, zero at x = 1
        Function<Double, Double> f = x -> x - 1;
        
        double zero = RecursiveZeroFinder.findZero(f, 0, 2, LARGER_TOLERANCE);
        
        // Should find zero within larger tolerance
        assertEquals(1.0, zero, LARGER_TOLERANCE);
        assertTrue(Math.abs(f.apply(zero)) <= LARGER_TOLERANCE);
    }
    
    @Test
    @DisplayName("Test when left bound is already a zero")
    void testLeftBoundIsZero() {
        // f(x) = x - 2, zero at x = 2
        Function<Double, Double> f = x -> x - 2;
        
        double zero = RecursiveZeroFinder.findZero(f, 2, 5, TOLERANCE);
        
        // Should return the left bound since it's already a zero
        assertEquals(2.0, zero, TOLERANCE);
        assertEquals(0.0, f.apply(zero), TOLERANCE);
    }
    
    @Test
    @DisplayName("Test when right bound is already a zero")
    void testRightBoundIsZero() {
        // f(x) = x - 2, zero at x = 2
        Function<Double, Double> f = x -> x - 2;
        
        double zero = RecursiveZeroFinder.findZero(f, 0, 2, TOLERANCE);
        
        // Should return the right bound since it's already a zero
        assertEquals(2.0, zero, TOLERANCE);
        assertEquals(0.0, f.apply(zero), TOLERANCE);
    }
    
    @Test
    @DisplayName("Test error when no zero exists in interval")
    void testNoZeroInInterval() {
        // f(x) = x^2 + 1, no real zeros
        Function<Double, Double> f = x -> x * x + 1;
        
        // Should throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            RecursiveZeroFinder.findZero(f, 0, 2, TOLERANCE);
        });
    }
    
    @Test
    @DisplayName("Test error when left bound >= right bound")
    void testInvalidBounds() {
        // f(x) = x - 1
        Function<Double, Double> f = x -> x - 1;
        
        // Should throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            RecursiveZeroFinder.findZero(f, 2, 1, TOLERANCE);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            RecursiveZeroFinder.findZero(f, 1, 1, TOLERANCE);
        });
    }
    
    @Test
    @DisplayName("Test error when tolerance is non-positive")
    void testInvalidTolerance() {
        // f(x) = x - 1
        Function<Double, Double> f = x -> x - 1;
        
        // Should throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            RecursiveZeroFinder.findZero(f, 0, 2, 0);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            RecursiveZeroFinder.findZero(f, 0, 2, -0.001);
        });
    }
    
    @Test
    @DisplayName("Test function creation from expression")
    void testFunctionCreation() {
        // Test creating function from expression
        Function<Double, Double> f1 = RecursiveZeroFinder.createFunction("x*x - 4");
        
        // Test the function works correctly
        assertEquals(0.0, f1.apply(2.0), TOLERANCE);
        assertEquals(-3.0, f1.apply(1.0), TOLERANCE);
        assertEquals(5.0, f1.apply(3.0), TOLERANCE);
    }
    
    @Test
    @DisplayName("Test function creation with simple expressions")
    void testSimpleFunctionCreation() {
        // Test simple arithmetic expressions
        Function<Double, Double> f1 = RecursiveZeroFinder.createFunction("x - 3");
        Function<Double, Double> f2 = RecursiveZeroFinder.createFunction("x + 2");
        Function<Double, Double> f3 = RecursiveZeroFinder.createFunction("x * 2");
        Function<Double, Double> f4 = RecursiveZeroFinder.createFunction("x / 2");
        
        // Test function values
        assertEquals(-1.0, f1.apply(2.0), TOLERANCE);
        assertEquals(4.0, f2.apply(2.0), TOLERANCE);
        assertEquals(4.0, f3.apply(2.0), TOLERANCE);
        assertEquals(1.0, f4.apply(2.0), TOLERANCE);
    }
    
    @Test
    @DisplayName("Test error handling in function creation")
    void testFunctionCreationError() {
        // Test invalid expression
        assertThrows(ArithmeticException.class, () -> {
            Function<Double, Double> f = RecursiveZeroFinder.createFunction("invalid expression");
            f.apply(1.0);
        });
    }
    
    @Test
    @DisplayName("Test convergence with multiple iterations")
    void testConvergence() {
        // f(x) = x^3 - x - 1, zero near x = 1.3247
        Function<Double, Double> f = x -> x * x * x - x - 1;
        
        double zero = RecursiveZeroFinder.findZero(f, 1, 2, TOLERANCE);
        
        // Should converge to the zero
        assertTrue(Math.abs(f.apply(zero)) <= TOLERANCE);
        assertTrue(zero >= 1.0 && zero <= 2.0);
    }
    
    @Test
    @DisplayName("Test with very narrow interval")
    void testNarrowInterval() {
        // f(x) = x - 1.5, zero at x = 1.5
        Function<Double, Double> f = x -> x - 1.5;
        
        double zero = RecursiveZeroFinder.findZero(f, 1.4, 1.6, TOLERANCE);
        
        // Should find zero at x = 1.5
        assertEquals(1.5, zero, TOLERANCE);
        assertEquals(0.0, f.apply(zero), TOLERANCE);
    }
    
    @Test
    @DisplayName("Test with wide interval")
    void testWideInterval() {
        // f(x) = x - 100, zero at x = 100
        Function<Double, Double> f = x -> x - 100;
        
        double zero = RecursiveZeroFinder.findZero(f, 0, 200, TOLERANCE);
        
        // Should find zero at x = 100
        assertEquals(100.0, zero, TOLERANCE);
        assertEquals(0.0, f.apply(zero), TOLERANCE);
    }
}
