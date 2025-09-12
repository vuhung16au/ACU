package com.acu.javafx.postfix;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PostfixEvaluatorTest {
    
    @Test
    void testBasicOperations() {
        assertEquals(9.0, PostfixEvaluator.evaluate("1 2 + 3 *"), 0.001);
        assertEquals(5.0, PostfixEvaluator.evaluate("3 2 +"), 0.001);
        assertEquals(1.0, PostfixEvaluator.evaluate("3 2 -"), 0.001);
        assertEquals(6.0, PostfixEvaluator.evaluate("3 2 *"), 0.001);
        assertEquals(1.5, PostfixEvaluator.evaluate("3 2 /"), 0.001);
    }
    
    @Test
    void testComplexExpression() {
        assertEquals(45.0, PostfixEvaluator.evaluate("2 3 + 4 5 + *"), 0.001);
        assertEquals(-4.0, PostfixEvaluator.evaluate("1 2 + 3 4 + -"), 0.001);
    }
    
    @Test
    void testSingleNumber() {
        assertEquals(42.0, PostfixEvaluator.evaluate("42"), 0.001);
    }
    
    @Test
    void testStepByStepEvaluation() {
        List<PostfixEvaluator.Step> steps = PostfixEvaluator.evaluateWithSteps("1 2 + 3 *");
        assertEquals(6, steps.size()); // 2 pushes + 1 operation + 1 push + 1 operation + 1 result
        
        assertEquals("1", steps.get(0).token);
        assertEquals("PUSH", steps.get(0).action);
        assertEquals(1, steps.get(0).stackState.size());
        assertEquals(1.0, steps.get(0).stackState.get(0), 0.001);
        
        assertEquals("2", steps.get(1).token);
        assertEquals("PUSH", steps.get(1).action);
        assertEquals(2, steps.get(1).stackState.size());
        
        assertEquals("+", steps.get(2).token);
        assertEquals("OPERATE", steps.get(2).action);
        assertEquals(1, steps.get(2).stackState.size());
        assertEquals(3.0, steps.get(2).stackState.get(0), 0.001);
    }
    
    @Test
    void testInvalidExpressions() {
        assertThrows(IllegalArgumentException.class, () -> PostfixEvaluator.evaluate(""));
        assertThrows(IllegalArgumentException.class, () -> PostfixEvaluator.evaluate("1 +"));
        assertThrows(IllegalArgumentException.class, () -> PostfixEvaluator.evaluate("1 2 3 +"));
        assertThrows(IllegalArgumentException.class, () -> PostfixEvaluator.evaluate("1 2 + +"));
        assertThrows(IllegalArgumentException.class, () -> PostfixEvaluator.evaluate("1 0 /"));
        assertThrows(IllegalArgumentException.class, () -> PostfixEvaluator.evaluate("1 2 %"));
    }
}
