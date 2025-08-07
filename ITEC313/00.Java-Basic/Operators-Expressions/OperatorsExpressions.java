/**
 * OperatorsExpressions.java
 * 
 * This program demonstrates operators and expressions in Java:
 * - Arithmetic operators
 * - Comparison operators
 * - Logical operators
 * - Assignment operators
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
// OperatorsExpressions.java
// Demonstrates Java operators and expressions for educational purposes
// Course: ITEC313 - Advanced Programming Concepts
// Target audience: Python programmers transitioning to Java
//
// This file covers:
// - Arithmetic, assignment, comparison, logical, bitwise, ternary, increment/decrement operators
// - Operator precedence and associativity
// - Expressions and evaluation order
//
// Each section includes detailed comments and Python comparisons.

public class OperatorsExpressions {
    // Entry point of the program
    public static void main(String[] args) {
        // Arithmetic Operators
        arithmeticOperators();
        // Assignment Operators
        assignmentOperators();
        // Comparison Operators
        comparisonOperators();
        // Logical Operators
        logicalOperators();
        // Bitwise Operators
        bitwiseOperators();
        // Ternary Operator
        ternaryOperator();
        // Increment/Decrement Operators
        incrementDecrementOperators();
        // Operator Precedence
        operatorPrecedence();
    }

    // Arithmetic Operators: +, -, *, /, %
    static void arithmeticOperators() {
        System.out.println("\n== Arithmetic Operators ==");
        int a = 2025, b = 8;
        System.out.println("a + b = " + (a + b)); // Addition
        System.out.println("a - b = " + (a - b)); // Subtraction
        System.out.println("a * b = " + (a * b)); // Multiplication
        System.out.println("a / b = " + (a / b)); // Integer division
        System.out.println("a % b = " + (a % b)); // Modulus
        // Python: division (/) always returns float, Java: int/int = int
    }

    // Assignment Operators: =, +=, -=, *=, /=, %=
    static void assignmentOperators() {
        System.out.println("\n== Assignment Operators ==");
        int x = 2025;
        x += 3; // x = x + 3
        System.out.println("x += 3 -> " + x);
        x -= 2; // x = x - 2
        System.out.println("x -= 2 -> " + x);
        x *= 4; // x = x * 4
        System.out.println("x *= 4 -> " + x);
        x /= 3; // x = x / 3
        System.out.println("x /= 3 -> " + x);
        x %= 2; // x = x % 2
        System.out.println("x %= 2 -> " + x);
        // Python: same operators, but no type declarations
    }

    // Comparison Operators: ==, !=, >, <, >=, <=
    static void comparisonOperators() {
        System.out.println("\n== Comparison Operators ==");
        int a = 8, b = 2025;
        System.out.println("a == b: " + (a == b));
        System.out.println("a != b: " + (a != b));
        System.out.println("a > b: " + (a > b));
        System.out.println("a < b: " + (a < b));
        System.out.println("a >= b: " + (a >= b));
        System.out.println("a <= b: " + (a <= b));
        // Python: same syntax for comparisons
    }

    // Logical Operators: &&, ||, !
    static void logicalOperators() {
        System.out.println("\n== Logical Operators ==");
        boolean x = true, y = false;
        System.out.println("x && y: " + (x && y)); // Logical AND
        System.out.println("x || y: " + (x || y)); // Logical OR
        System.out.println("!x: " + (!x));         // Logical NOT
        // Python: uses 'and', 'or', 'not' instead of &&, ||, !
    }

    // Bitwise Operators: &, |, ^, ~, <<, >>, >>>
    static void bitwiseOperators() {
        System.out.println("\n== Bitwise Operators ==");
        int a = 5, b = 3;
        System.out.println("a & b: " + (a & b));   // Bitwise AND
        System.out.println("a | b: " + (a | b));   // Bitwise OR
        System.out.println("a ^ b: " + (a ^ b));   // Bitwise XOR
        System.out.println("~a: " + (~a));         // Bitwise NOT
        System.out.println("a << 1: " + (a << 1)); // Left shift
        System.out.println("a >> 1: " + (a >> 1)); // Right shift
        System.out.println("a >>> 1: " + (a >>> 1)); // Unsigned right shift
        // Python: no >>> operator, ~a is bitwise NOT
    }

    // Ternary Operator: condition ? trueExpr : falseExpr
    static void ternaryOperator() {
        System.out.println("\n== Ternary Operator ==");
        int a = 2025, b = 8;
        int max = (a > b) ? a : b;
        System.out.println("max = " + max);
        // Python: uses 'a if condition else b'
    }

    // Increment/Decrement Operators: ++, --
    static void incrementDecrementOperators() {
        System.out.println("\n== Increment/Decrement Operators ==");
        int x = 2025;
        System.out.println("x++: " + (x++)); // Post-increment
        System.out.println("After x++: " + x);
        System.out.println("++x: " + (++x)); // Pre-increment
        System.out.println("x--: " + (x--)); // Post-decrement
        System.out.println("After x--: " + x);
        System.out.println("--x: " + (--x)); // Pre-decrement
        // Python: no ++ or -- operators
    }

    // Operator Precedence and Associativity
    static void operatorPrecedence() {
        System.out.println("\n== Operator Precedence ==");
        int result = 2 + 3 * 4;
        System.out.println("2 + 3 * 4 = " + result); // Multiplication before addition
        result = (2 + 3) * 4;
        System.out.println("(2 + 3) * 4 = " + result); // Parentheses change order
        // Python: same precedence rules
    }
} 