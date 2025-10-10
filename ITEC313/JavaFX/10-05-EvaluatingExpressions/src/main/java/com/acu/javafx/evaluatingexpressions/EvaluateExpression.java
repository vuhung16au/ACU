package com.acu.javafx.evaluatingexpressions;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Evaluates arithmetic infix expressions using two-stack algorithm (Dijkstra):
 * - operandStack holds numbers
 * - operatorStack holds operators
 * Supports +, -, *, /, parentheses, and spaces. Unary minus handled.
 */
public final class EvaluateExpression {

    private EvaluateExpression() {}

    /**
     * Evaluate a string arithmetic expression.
     * @param expression input like "(1 + 2) * 3 - 4/2"
     * @return computed double value
     * @throws IllegalArgumentException if the expression is invalid
     */
    public static double evaluate(String expression) {
        if (expression == null) {
            throw new IllegalArgumentException("Expression cannot be null");
        }
        String expr = expression.trim();
        if (expr.isEmpty()) {
            throw new IllegalArgumentException("Expression cannot be empty");
        }

        Deque<Double> operandStack = new ArrayDeque<>();
        Deque<Character> operatorStack = new ArrayDeque<>();

        // Previous token type used to detect unary minus
        boolean expectUnary = true;

        for (int i = 0; i < expr.length(); ) {
            char ch = expr.charAt(i);

            if (Character.isWhitespace(ch)) { // skip spaces
                i++;
                continue;
            }

            if (ch == '(') { // push '('
                operatorStack.push(ch);
                i++;
                expectUnary = true;
            } else if (ch == ')') { // compute until '('
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    applyTopOperator(operandStack, operatorStack);
                }
                if (operatorStack.isEmpty() || operatorStack.pop() != '(') {
                    throw new IllegalArgumentException("Mismatched parentheses");
                }
                i++;
                expectUnary = false;
            } else if (isOperator(ch)) {
                // unary minus: treat as 0 - number/expression
                if (ch == '-' && expectUnary) {
                    operandStack.push(0.0);
                } else {
                    // reduce existing operators with higher or equal precedence
                    while (!operatorStack.isEmpty() &&
                            precedence(operatorStack.peek()) >= precedence(ch)) {
                        if (operatorStack.peek() == '(') break;
                        applyTopOperator(operandStack, operatorStack);
                    }
                }
                operatorStack.push(ch);
                i++;
                expectUnary = true;
            } else if (Character.isDigit(ch) || ch == '.') {
                // parse number literal (supports decimals)
                int start = i;
                while (i < expr.length() && (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.')) {
                    i++;
                }
                String numStr = expr.substring(start, i);
                try {
                    double value = Double.parseDouble(numStr);
                    operandStack.push(value);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid number: " + numStr);
                }
                expectUnary = false;
            } else {
                throw new IllegalArgumentException("Unexpected character: " + ch);
            }
        }

        // apply remaining operators
        while (!operatorStack.isEmpty()) {
            char op = operatorStack.peek();
            if (op == '(' || op == ')') {
                throw new IllegalArgumentException("Mismatched parentheses");
            }
            applyTopOperator(operandStack, operatorStack);
        }

        if (operandStack.size() != 1) {
            throw new IllegalArgumentException("Invalid expression");
        }
        return operandStack.pop();
    }

    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    private static int precedence(char op) {
        return switch (op) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '(' -> 0;
            default -> -1;
        };
    }

    private static void applyTopOperator(Deque<Double> operands, Deque<Character> operators) {
        if (operators.isEmpty()) {
            throw new IllegalArgumentException("Operator stack is empty");
        }
        char op = operators.pop();
        if (operands.size() < 2) {
            throw new IllegalArgumentException("Insufficient operands for operator: " + op);
        }
        double b = operands.pop();
        double a = operands.pop();
        double r;
        switch (op) {
            case '+': r = a + b; break;
            case '-': r = a - b; break;
            case '*': r = a * b; break;
            case '/':
                if (b == 0.0) throw new IllegalArgumentException("Division by zero");
                r = a / b; break;
            default:
                throw new IllegalArgumentException("Unknown operator: " + op);
        }
        operands.push(r);
    }
}


