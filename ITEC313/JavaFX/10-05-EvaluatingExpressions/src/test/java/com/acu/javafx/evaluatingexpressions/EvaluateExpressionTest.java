package com.acu.javafx.evaluatingexpressions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EvaluateExpressionTest {
    @Test
    void testSimple() {
        assertEquals(7.0, EvaluateExpression.evaluate("3+4"));
        assertEquals(14.0, EvaluateExpression.evaluate("2*7"));
        assertEquals(1.0, EvaluateExpression.evaluate("3-2"));
        assertEquals(2.0, EvaluateExpression.evaluate("4/2"));
    }

    @Test
    void testPrecedence() {
        assertEquals(11.0, EvaluateExpression.evaluate("3+4*2"));
        assertEquals(14.0, EvaluateExpression.evaluate("(3+4)*2"));
    }

    @Test
    void testUnaryMinus() {
        assertEquals(-3.0, EvaluateExpression.evaluate("-3"));
        assertEquals(1.0, EvaluateExpression.evaluate("-3+4"));
        assertEquals(-7.0, EvaluateExpression.evaluate("-(3+4)"));
    }

    @Test
    void testSpacesAndDecimals() {
        assertEquals(3.5, EvaluateExpression.evaluate(" 1.5 + 2.0 "));
        assertEquals(2.5, EvaluateExpression.evaluate(" 5 / 2 "));
    }

    @Test
    void testErrors() {
        assertThrows(IllegalArgumentException.class, () -> EvaluateExpression.evaluate(""));
        assertThrows(IllegalArgumentException.class, () -> EvaluateExpression.evaluate("(1+2"));
        assertThrows(IllegalArgumentException.class, () -> EvaluateExpression.evaluate("1/0"));
        assertThrows(IllegalArgumentException.class, () -> EvaluateExpression.evaluate("a+b"));
    }
}


