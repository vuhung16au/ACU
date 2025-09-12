package com.acu.javafx.calculator;

import java.util.*;

/**
 * Calculator engine that implements flat operator precedence.
 * 
 * In flat precedence, all operators (+, -, *, /) have equal precedence
 * and are evaluated from left to right. For example:
 * 4 + 3 - 2 * 10 = ((4 + 3) - 2) * 10 = (7 - 2) * 10 = 5 * 10 = 50
 * 
 * This is different from standard mathematical precedence where
 * multiplication and division have higher precedence than addition and subtraction.
 */
public class FlatPrecedenceCalculatorEngine {
    
    /**
     * Represents a step in the calculation process for visualization
     */
    public static class CalculationStep {
        private final String expression;
        private final String operation;
        private final double result;
        private final int stepNumber;
        
        public CalculationStep(String expression, String operation, double result, int stepNumber) {
            this.expression = expression;
            this.operation = operation;
            this.result = result;
            this.stepNumber = stepNumber;
        }
        
        public String getExpression() { return expression; }
        public String getOperation() { return operation; }
        public double getResult() { return result; }
        public int getStepNumber() { return stepNumber; }
        
        @Override
        public String toString() {
            return String.format("Step %d: %s = %.2f", stepNumber, operation, result);
        }
    }
    
    /**
     * Calculates the result of an expression using flat precedence
     * 
     * @param expression The mathematical expression to evaluate
     * @return The result of the calculation
     * @throws IllegalArgumentException if the expression is invalid
     */
    public double calculate(String expression) throws IllegalArgumentException {
        if (expression == null || expression.trim().isEmpty()) {
            throw new IllegalArgumentException("Expression cannot be null or empty");
        }
        
        // Parse the expression into tokens
        List<String> tokens = parseExpression(expression.trim());
        
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("No valid tokens found in expression");
        }
        
        // Validate that we have at least one number
        if (tokens.size() == 1) {
            try {
                return Double.parseDouble(tokens.get(0));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid number: " + tokens.get(0));
            }
        }
        
        // Calculate using flat precedence (left to right)
        return evaluateFlatPrecedence(tokens);
    }
    
    /**
     * Calculates the result and returns detailed steps for visualization
     * 
     * @param expression The mathematical expression to evaluate
     * @return List of calculation steps
     * @throws IllegalArgumentException if the expression is invalid
     */
    public List<CalculationStep> calculateWithSteps(String expression) throws IllegalArgumentException {
        if (expression == null || expression.trim().isEmpty()) {
            throw new IllegalArgumentException("Expression cannot be null or empty");
        }
        
        List<String> tokens = parseExpression(expression.trim());
        List<CalculationStep> steps = new ArrayList<>();
        
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("No valid tokens found in expression");
        }
        
        if (tokens.size() == 1) {
            try {
                double result = Double.parseDouble(tokens.get(0));
                steps.add(new CalculationStep(tokens.get(0), "Single number", result, 1));
                return steps;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid number: " + tokens.get(0));
            }
        }
        
        // Create a copy of tokens for step-by-step evaluation
        List<String> workingTokens = new ArrayList<>(tokens);
        int stepNumber = 1;
        
        // Process operations from left to right
        while (workingTokens.size() > 1) {
            // Find the first operator
            int operatorIndex = findFirstOperator(workingTokens);
            if (operatorIndex == -1) {
                break;
            }
            
            // Get the operation details
            String leftOperand = workingTokens.get(operatorIndex - 1);
            String operator = workingTokens.get(operatorIndex);
            String rightOperand = workingTokens.get(operatorIndex + 1);
            
            // Perform the calculation
            double leftValue = Double.parseDouble(leftOperand);
            double rightValue = Double.parseDouble(rightOperand);
            double result = performOperation(leftValue, operator, rightValue);
            
            // Create step description
            String stepExpression = String.format("%s %s %s", leftOperand, operator, rightOperand);
            String operation = String.format("%.2f %s %.2f", leftValue, operator, rightValue);
            
            steps.add(new CalculationStep(stepExpression, operation, result, stepNumber++));
            
            // Replace the three tokens (left, operator, right) with the result
            workingTokens.set(operatorIndex - 1, String.valueOf(result));
            workingTokens.remove(operatorIndex); // Remove operator
            workingTokens.remove(operatorIndex); // Remove right operand (index shifted)
        }
        
        return steps;
    }
    
    /**
     * Parses an expression into tokens (numbers and operators)
     * 
     * @param expression The expression to parse
     * @return List of tokens
     */
    private List<String> parseExpression(String expression) {
        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();
        
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            
            if (Character.isDigit(c) || c == '.' || (c == '-' && (i == 0 || isOperator(expression.charAt(i - 1))))) {
                // Part of a number (including negative numbers)
                currentToken.append(c);
            } else if (isOperator(c)) {
                // Operator found, save current number if any
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken = new StringBuilder();
                }
                tokens.add(String.valueOf(c));
            } else if (Character.isWhitespace(c)) {
                // Whitespace, save current number if any
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken = new StringBuilder();
                }
            } else {
                throw new IllegalArgumentException("Invalid character in expression: " + c);
            }
        }
        
        // Add the last token if any
        if (currentToken.length() > 0) {
            tokens.add(currentToken.toString());
        }
        
        return tokens;
    }
    
    /**
     * Evaluates the expression using flat precedence (left to right)
     * 
     * @param tokens List of tokens (numbers and operators)
     * @return The final result
     */
    private double evaluateFlatPrecedence(List<String> tokens) {
        if (tokens.size() < 3 || tokens.size() % 2 == 0) {
            throw new IllegalArgumentException("Invalid expression format");
        }
        
        // Start with the first number
        double result = Double.parseDouble(tokens.get(0));
        
        // Process operations from left to right
        for (int i = 1; i < tokens.size(); i += 2) {
            String operator = tokens.get(i);
            double operand = Double.parseDouble(tokens.get(i + 1));
            result = performOperation(result, operator, operand);
        }
        
        return result;
    }
    
    /**
     * Performs a single operation
     * 
     * @param left Left operand
     * @param operator The operator (+, -, *, /)
     * @param right Right operand
     * @return The result of the operation
     */
    private double performOperation(double left, String operator, double right) {
        switch (operator) {
            case "+":
                return left + right;
            case "-":
                return left - right;
            case "*":
                return left * right;
            case "/":
                if (right == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return left / right;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
    
    /**
     * Finds the first operator in the token list
     * 
     * @param tokens List of tokens
     * @return Index of the first operator, or -1 if none found
     */
    private int findFirstOperator(List<String> tokens) {
        for (int i = 0; i < tokens.size(); i++) {
            if (isOperator(tokens.get(i))) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Checks if a character is a valid operator
     * 
     * @param c The character to check
     * @return true if the character is an operator
     */
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
    
    /**
     * Checks if a string is a valid operator
     * 
     * @param s The string to check
     * @return true if the string is an operator
     */
    private boolean isOperator(String s) {
        return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/");
    }
}
