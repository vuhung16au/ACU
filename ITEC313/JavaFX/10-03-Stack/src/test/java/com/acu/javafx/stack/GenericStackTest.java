package com.acu.javafx.stack;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the GenericStack implementation
 */
public class GenericStackTest {
    
    private GenericStack<String> stack;
    
    @BeforeEach
    void setUp() {
        stack = new GenericStack<>();
    }
    
    @Test
    void testEmptyStack() {
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.getSize());
        assertThrows(IndexOutOfBoundsException.class, () -> stack.peek());
        assertThrows(IndexOutOfBoundsException.class, () -> stack.pop());
    }
    
    @Test
    void testPushAndPop() {
        stack.push("first");
        assertFalse(stack.isEmpty());
        assertEquals(1, stack.getSize());
        assertEquals("first", stack.peek());
        
        String result = stack.pop();
        assertEquals("first", result);
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.getSize());
    }
    
    @Test
    void testMultipleElements() {
        stack.push("first");
        stack.push("second");
        stack.push("third");
        
        assertEquals(3, stack.getSize());
        assertEquals("third", stack.peek());
        
        assertEquals("third", stack.pop());
        assertEquals("second", stack.pop());
        assertEquals("first", stack.pop());
        
        assertTrue(stack.isEmpty());
    }
    
    @Test
    void testToString() {
        stack.push("A");
        stack.push("B");
        stack.push("C");
        
        String expected = "stack: [A, B, C]";
        assertEquals(expected, stack.toString());
    }
    
    @Test
    void testIntegerStack() {
        GenericStack<Integer> intStack = new GenericStack<>();
        intStack.push(1);
        intStack.push(2);
        intStack.push(3);
        
        assertEquals(3, intStack.getSize());
        assertEquals(Integer.valueOf(3), intStack.peek());
        assertEquals(Integer.valueOf(3), intStack.pop());
    }
} 