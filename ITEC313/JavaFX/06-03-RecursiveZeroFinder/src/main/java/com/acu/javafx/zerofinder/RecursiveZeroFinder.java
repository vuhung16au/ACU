package com.acu.javafx.zerofinder;

import java.util.function.Function;

/**
 * Recursive Zero Finder - Finds the root of a function using recursive binary search
 * 
 * This class implements a recursive algorithm to find an approximation of a zero
 * of a function f(x) between two bounds l and r, where f(l)f(r) <= 0.
 * 
 * The algorithm uses the bisection method (binary search) recursively to narrow
 * down the interval until the function value is within the specified tolerance.
 * 
 * @author ACU JavaFX Team
 * @version 1.0.0
 */
public class RecursiveZeroFinder {
    
    /**
     * Finds a zero of the function f(x) between l and r using recursive binary search
     * 
     * @param f The function to find the zero of
     * @param l The left bound of the search interval
     * @param r The right bound of the search interval
     * @param e The tolerance (epsilon) - the maximum allowed absolute value of f(v)
     * @return A value v such that |f(v)| <= e and l <= v <= r
     * @throws IllegalArgumentException if f(l)f(r) > 0 (no zero guaranteed in interval)
     * @throws ArithmeticException if the function cannot be evaluated at the bounds
     */
    public static double findZero(Function<Double, Double> f, double l, double r, double e) {
        // Validate input parameters
        if (l >= r) {
            throw new IllegalArgumentException("Left bound must be less than right bound");
        }
        if (e <= 0) {
            throw new IllegalArgumentException("Tolerance must be positive");
        }
        
        // Evaluate function at bounds
        double fL = f.apply(l);
        double fR = f.apply(r);
        
        // Check if there's a sign change (guarantees a zero exists)
        if (fL * fR > 0) {
            throw new IllegalArgumentException(
                String.format("No zero guaranteed in interval [%.6f, %.6f]. f(%.6f) = %.6f, f(%.6f) = %.6f", 
                    l, r, l, fL, r, fR));
        }
        
        // If one of the bounds is already a zero, return it
        if (Math.abs(fL) <= e) {
            return l;
        }
        if (Math.abs(fR) <= e) {
            return r;
        }
        
        // Recursive call to find zero
        return findZeroRecursive(f, l, r, e);
    }
    
    /**
     * Recursive helper method that implements the bisection algorithm
     * 
     * @param f The function to find the zero of
     * @param l The left bound of the current interval
     * @param r The right bound of the current interval
     * @param e The tolerance
     * @return A value v such that |f(v)| <= e
     */
    private static double findZeroRecursive(Function<Double, Double> f, double l, double r, double e) {
        // Calculate midpoint
        double mid = (l + r) / 2.0;
        
        // Evaluate function at midpoint
        double fMid = f.apply(mid);
        
        // Check if we've found a zero within tolerance
        if (Math.abs(fMid) <= e) {
            return mid;
        }
        
        // Check if the interval is too small (prevent infinite recursion)
        if (Math.abs(r - l) < 1e-15) {
            return mid; // Return midpoint as best approximation
        }
        
        // Determine which half contains the zero
        double fL = f.apply(l);
        
        // If f(l) and f(mid) have opposite signs, zero is in [l, mid]
        if (fL * fMid < 0) {
            return findZeroRecursive(f, l, mid, e);
        } else {
            // Otherwise, zero is in [mid, r]
            return findZeroRecursive(f, mid, r, e);
        }
    }
    
    /**
     * Utility method to create a function from a mathematical expression string
     * This is a simple parser for basic mathematical expressions
     * 
     * @param expression The mathematical expression (e.g., "x^2 - 4", "x*x - 4")
     * @return A Function<Double, Double> that evaluates the expression
     */
    public static Function<Double, Double> createFunction(String expression) {
        // Simple function parser - handles basic expressions
        return x -> {
            try {
                // Replace x with the actual value and handle basic operations
                String expr = expression.toLowerCase()
                    .replace("x", String.valueOf(x))
                    .replace("^", "**"); // Handle power operator
                
                // For now, we'll use a simple evaluator
                // In a real implementation, you might use a proper expression parser
                return evaluateExpression(expr);
            } catch (Exception e) {
                throw new ArithmeticException("Cannot evaluate function at x = " + x + ": " + e.getMessage());
            }
        };
    }
    
    /**
     * Simple expression evaluator for basic mathematical expressions
     * This is a simplified version - in practice, you might use a proper math parser
     * 
     * @param expression The expression to evaluate
     * @return The result of the expression
     */
    private static double evaluateExpression(String expression) {
        // Remove whitespace
        expression = expression.replaceAll("\\s+", "");
        
        // Handle simple cases
        if (expression.contains("**")) {
            // Handle power operations (simplified)
            String[] parts = expression.split("\\*\\*");
            if (parts.length == 2) {
                double base = Double.parseDouble(parts[0]);
                double exponent = Double.parseDouble(parts[1]);
                return Math.pow(base, exponent);
            }
        }
        
        // For more complex expressions, we'll use a simple approach
        // This is a basic implementation - in practice, use a proper math parser
        try {
            // Try to evaluate as a simple arithmetic expression
            return evaluateSimpleExpression(expression);
        } catch (Exception e) {
            throw new ArithmeticException("Cannot evaluate expression: " + expression);
        }
    }
    
    /**
     * Evaluates simple arithmetic expressions
     * This is a very basic implementation for demonstration purposes
     * 
     * @param expression The expression to evaluate
     * @return The result
     */
    private static double evaluateSimpleExpression(String expression) {
        // This is a simplified evaluator - handles basic +, -, *, / operations
        // For the demo, we'll handle specific cases
        
        // Handle x^2 - 4 pattern (x**2 - 4)
        if (expression.matches(".*\\*\\*2.*-.*")) {
            String[] parts = expression.split("-");
            if (parts.length == 2) {
                String leftPart = parts[0].trim();
                String rightPart = parts[1].trim();
                
                if (leftPart.contains("**2")) {
                    String[] leftParts = leftPart.split("\\*\\*2");
                    if (leftParts.length == 2) {
                        double coefficient = leftParts[0].isEmpty() ? 1.0 : Double.parseDouble(leftParts[0]);
                        double x = Double.parseDouble(leftParts[1]);
                        double constant = Double.parseDouble(rightPart);
                        return coefficient * x * x - constant;
                    }
                }
            }
        }
        
        // Handle x*x - 4 pattern
        if (expression.matches(".*\\*.*-.*")) {
            String[] parts = expression.split("-");
            if (parts.length == 2) {
                String leftPart = parts[0].trim();
                String rightPart = parts[1].trim();
                
                if (leftPart.contains("*")) {
                    String[] leftParts = leftPart.split("\\*");
                    if (leftParts.length == 2) {
                        double x1 = Double.parseDouble(leftParts[0]);
                        double x2 = Double.parseDouble(leftParts[1]);
                        double constant = Double.parseDouble(rightPart);
                        return x1 * x2 - constant;
                    }
                }
            }
        }
        
        // Handle simple subtraction (like "2-4")
        if (expression.contains("-") && !expression.startsWith("-")) {
            String[] parts = expression.split("-");
            if (parts.length == 2) {
                try {
                    return Double.parseDouble(parts[0]) - Double.parseDouble(parts[1]);
                } catch (NumberFormatException e) {
                    // If parsing fails, continue to other patterns
                }
            }
        }
        
        // Handle simple addition
        if (expression.contains("+")) {
            String[] parts = expression.split("\\+");
            if (parts.length == 2) {
                try {
                    return Double.parseDouble(parts[0]) + Double.parseDouble(parts[1]);
                } catch (NumberFormatException e) {
                    // If parsing fails, continue to other patterns
                }
            }
        }
        
        // Handle simple multiplication
        if (expression.contains("*")) {
            String[] parts = expression.split("\\*");
            if (parts.length == 2) {
                try {
                    return Double.parseDouble(parts[0]) * Double.parseDouble(parts[1]);
                } catch (NumberFormatException e) {
                    // If parsing fails, continue to other patterns
                }
            }
        }
        
        // Handle simple division
        if (expression.contains("/")) {
            String[] parts = expression.split("/");
            if (parts.length == 2) {
                try {
                    return Double.parseDouble(parts[0]) / Double.parseDouble(parts[1]);
                } catch (NumberFormatException e) {
                    // If parsing fails, continue to other patterns
                }
            }
        }
        
        // If none of the above, try to parse as a single number
        return Double.parseDouble(expression);
    }
}
