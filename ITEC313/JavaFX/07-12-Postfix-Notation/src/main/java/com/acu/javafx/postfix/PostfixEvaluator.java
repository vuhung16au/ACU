package com.acu.javafx.postfix;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Simple postfix expression evaluator using a stack.
 * Supports basic arithmetic: +, -, *, /
 */
public class PostfixEvaluator {
    
    public static class Step {
        public final String token;
        public final String action;
        public final List<Double> stackState;
        public final String description;
        
        public Step(String token, String action, List<Double> stackState, String description) {
            this.token = token;
            this.action = action;
            this.stackState = new ArrayList<>(stackState);
            this.description = description;
        }
    }
    
    /**
     * Evaluate a postfix expression string with step-by-step visualization.
     * @param expression space-separated tokens (numbers and operators)
     * @return list of steps showing the evaluation process
     * @throws IllegalArgumentException if expression is invalid
     */
    public static List<Step> evaluateWithSteps(String expression) {
        if (expression == null || expression.trim().isEmpty()) {
            throw new IllegalArgumentException("Empty expression");
        }
        
        List<Step> steps = new ArrayList<>();
        Stack<Double> stack = new Stack<>();
        String[] tokens = expression.trim().split("\\s+");
        
        for (String token : tokens) {
            if (isNumber(token)) {
                // Push operand onto stack
                double value = Double.parseDouble(token);
                stack.push(value);
                steps.add(new Step(token, "PUSH", getStackState(stack), 
                    String.format("Push %s onto stack", token)));
            } else if (isOperator(token)) {
                // Apply operator to top two operands
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Not enough operands for operator: " + token);
                }
                double b = stack.pop(); // second operand (top of stack)
                double a = stack.pop(); // first operand
                double result = applyOperator(a, b, token);
                stack.push(result);
                steps.add(new Step(token, "OPERATE", getStackState(stack), 
                    String.format("Pop %.1f and %.1f, compute %.1f %s %.1f = %.1f, push result", 
                        a, b, a, token, b, result)));
            } else {
                throw new IllegalArgumentException("Invalid token: " + token);
            }
        }
        
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid expression: too many operands");
        }
        
        steps.add(new Step("", "RESULT", getStackState(stack), 
            String.format("Final result: %.2f", stack.peek())));
        
        return steps;
    }
    
    /**
     * Evaluate a postfix expression string.
     * @param expression space-separated tokens (numbers and operators)
     * @return the result as a double
     * @throws IllegalArgumentException if expression is invalid
     */
    public static double evaluate(String expression) {
        List<Step> steps = evaluateWithSteps(expression);
        return steps.get(steps.size() - 1).stackState.get(0);
    }
    
    private static List<Double> getStackState(Stack<Double> stack) {
        return new ArrayList<>(stack);
    }
    
    private static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || 
               token.equals("*") || token.equals("/");
    }
    
    private static double applyOperator(double a, double b, String op) {
        return switch (op) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> {
                if (b == 0) throw new IllegalArgumentException("Division by zero");
                yield a / b;
            }
            default -> throw new IllegalArgumentException("Unknown operator: " + op);
        };
    }
}
