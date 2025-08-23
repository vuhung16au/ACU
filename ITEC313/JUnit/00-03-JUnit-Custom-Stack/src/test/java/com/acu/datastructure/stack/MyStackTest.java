package com.acu.datastructure.stack;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for the MyStack class demonstrating JUnit 5 features.
 * This test class follows the project plan and demonstrates various testing concepts.
 */
@DisplayName("MyStack Tests")
class MyStackTest {
    
    private MyStack<String> stack;
    
    /**
     * Setup method that runs before each test method.
     * Ensures each test starts with a clean, known state.
     */
    @BeforeEach
    void setUp() {
        stack = new MyStack<>();
    }
    
    @Nested
    @DisplayName("Setup and Initialization Tests")
    class SetupAndInitializationTests {
        
        @Test
        @DisplayName("New stack should be empty")
        void testIsEmptyOnNewStack() {
            assertTrue(stack.isEmpty());
        }
        
        @Test
        @DisplayName("New stack should have size zero")
        void testSizeOnNewStack() {
            assertEquals(0, stack.size());
        }
        
        @Test
        @DisplayName("Stack constructor with negative capacity should throw exception")
        void testConstructorWithNegativeCapacity() {
            assertThrows(IllegalArgumentException.class, () -> {
                new MyStack<String>(-1);
            });
        }
        
        @Test
        @DisplayName("Stack constructor with zero capacity should work")
        void testConstructorWithZeroCapacity() {
            MyStack<String> zeroStack = new MyStack<>(0);
            assertTrue(zeroStack.isEmpty());
            assertEquals(0, zeroStack.size());
        }
    }
    
    @Nested
    @DisplayName("Basic Functionality Tests")
    class BasicFunctionalityTests {
        
        @Test
        @DisplayName("Push should increment size")
        void testPushIncrementsSize() {
            int initialSize = stack.size();
            stack.push("test");
            assertEquals(initialSize + 1, stack.size());
        }
        
        @Test
        @DisplayName("Push should add correct element")
        void testPushAddsCorrectElement() {
            String element = "test";
            stack.push(element);
            assertEquals(element, stack.peek());
        }
        
        @Test
        @DisplayName("Pop should decrement size")
        void testPopDecrementsSize() {
            stack.push("test");
            int sizeAfterPush = stack.size();
            stack.pop();
            assertEquals(sizeAfterPush - 1, stack.size());
        }
        
        @Test
        @DisplayName("Pop should return correct element")
        void testPopReturnsCorrectElement() {
            stack.push("first");
            stack.push("second");
            stack.push("third");
            
            assertEquals("third", stack.pop());
            assertEquals("second", stack.pop());
            assertEquals("first", stack.pop());
        }
        
        @Test
        @DisplayName("Peek should not remove element")
        void testPeekDoesNotRemoveElement() {
            stack.push("test");
            int sizeBeforePeek = stack.size();
            stack.peek();
            assertEquals(sizeBeforePeek, stack.size());
        }
        
        @Test
        @DisplayName("Peek should return top element")
        void testPeekReturnsTopElement() {
            stack.push("first");
            stack.push("second");
            assertEquals("second", stack.peek());
        }
    }
    
    @Nested
    @DisplayName("State Verification Tests")
    class StateVerificationTests {
        
        @Test
        @DisplayName("Stack should be empty after push and pop")
        void testIsEmptyAfterPushAndPop() {
            stack.push("test");
            stack.pop();
            assertTrue(stack.isEmpty());
        }
        
        @Test
        @DisplayName("Size should be correct after multiple pushes")
        void testSizeAfterMultiplePushes() {
            stack.push("first");
            stack.push("second");
            stack.push("third");
            assertEquals(3, stack.size());
        }
        
        @Test
        @DisplayName("Size should be correct after mixed operations")
        void testSizeAfterMixedOperations() {
            stack.push("first");
            stack.push("second");
            stack.pop();
            stack.push("third");
            stack.push("fourth");
            stack.pop();
            assertEquals(2, stack.size());
        }
        
        @Test
        @DisplayName("Stack should maintain correct order (LIFO)")
        void testLifoOrder() {
            stack.push("first");
            stack.push("second");
            stack.push("third");
            
            assertEquals("third", stack.pop());
            assertEquals("second", stack.pop());
            assertEquals("first", stack.pop());
        }
    }
    
    @Nested
    @DisplayName("Edge Case and Exception Tests")
    class EdgeCaseAndExceptionTests {
        
        @Test
        @DisplayName("Pop on empty stack should throw EmptyStackException")
        void testPopOnEmptyStackThrowsException() {
            assertThrows(EmptyStackException.class, () -> {
                stack.pop();
            });
        }
        
        @Test
        @DisplayName("Peek on empty stack should throw EmptyStackException")
        void testPeekOnEmptyStackThrowsException() {
            assertThrows(EmptyStackException.class, () -> {
                stack.peek();
            });
        }
        
        @Test
        @DisplayName("Multiple pops on empty stack should throw exception")
        void testMultiplePopsOnEmptyStack() {
            stack.push("test");
            stack.pop();
            
            assertThrows(EmptyStackException.class, () -> {
                stack.pop();
            });
        }
        
        @Test
        @DisplayName("Push null should work")
        void testPushNull() {
            stack.push(null);
            assertNull(stack.peek());
            assertEquals(1, stack.size());
        }
        
        @Test
        @DisplayName("Pop null should work")
        void testPopNull() {
            stack.push(null);
            assertNull(stack.pop());
            assertTrue(stack.isEmpty());
        }
    }
    
    @Nested
    @DisplayName("Capacity and Performance Tests")
    class CapacityAndPerformanceTests {
        
        @Test
        @DisplayName("Stack should handle default capacity")
        void testDefaultCapacity() {
            MyStack<String> defaultStack = new MyStack<>();
            // Push more than default capacity (10)
            for (int i = 0; i < 15; i++) {
                defaultStack.push("element" + i);
            }
            assertEquals(15, defaultStack.size());
        }
        
        @Test
        @DisplayName("Stack should handle custom capacity")
        void testCustomCapacity() {
            MyStack<String> customStack = new MyStack<>(5);
            for (int i = 0; i < 10; i++) {
                customStack.push("element" + i);
            }
            assertEquals(10, customStack.size());
        }
        
        @Test
        @DisplayName("Clear should empty the stack")
        void testClear() {
            stack.push("first");
            stack.push("second");
            stack.push("third");
            
            stack.clear();
            
            assertTrue(stack.isEmpty());
            assertEquals(0, stack.size());
        }
        
        @Test
        @DisplayName("Clear on empty stack should work")
        void testClearOnEmptyStack() {
            stack.clear();
            assertTrue(stack.isEmpty());
            assertEquals(0, stack.size());
        }
    }
    
    @Nested
    @DisplayName("Generic Type Tests")
    class GenericTypeTests {
        
        @Test
        @DisplayName("Stack should work with Integer type")
        void testIntegerStack() {
            MyStack<Integer> intStack = new MyStack<>();
            intStack.push(1);
            intStack.push(2);
            intStack.push(3);
            
            assertEquals(3, intStack.pop());
            assertEquals(2, intStack.pop());
            assertEquals(1, intStack.pop());
        }
        
        @Test
        @DisplayName("Stack should work with Double type")
        void testDoubleStack() {
            MyStack<Double> doubleStack = new MyStack<>();
            doubleStack.push(1.5);
            doubleStack.push(2.7);
            doubleStack.push(3.14);
            
            assertEquals(3.14, doubleStack.pop(), 0.001);
            assertEquals(2.7, doubleStack.pop(), 0.001);
            assertEquals(1.5, doubleStack.pop(), 0.001);
        }
        
        @Test
        @DisplayName("Stack should work with custom objects")
        void testCustomObjectStack() {
            MyStack<TestObject> objectStack = new MyStack<>();
            TestObject obj1 = new TestObject("first");
            TestObject obj2 = new TestObject("second");
            
            objectStack.push(obj1);
            objectStack.push(obj2);
            
            assertEquals(obj2, objectStack.pop());
            assertEquals(obj1, objectStack.pop());
        }
    }
    
    @Nested
    @DisplayName("Parameterized Tests")
    class ParameterizedTests {
        
        @ParameterizedTest
        @ValueSource(strings = {"Sydney", "Melbourne", "Brisbane", "Perth", "Adelaide"})
        @DisplayName("Push and pop should work with Australian cities")
        void testPushAndPopWithAustralianCities(String input) {
            stack.push(input);
            assertEquals(input, stack.pop());
            assertTrue(stack.isEmpty());
        }
        
        @ParameterizedTest
        @ValueSource(ints = {1, 5, 10, 20, 50})
        @DisplayName("Stack should handle various sizes")
        void testStackWithVariousSizes(int size) {
            String[] aussieCities = {"Sydney", "Melbourne", "Brisbane", "Perth", "Adelaide", 
                                   "Canberra", "Darwin", "Hobart", "Gold Coast", "Newcastle"};
            for (int i = 0; i < size; i++) {
                stack.push(aussieCities[i % aussieCities.length]);
            }
            assertEquals(size, stack.size());
            
            for (int i = size - 1; i >= 0; i--) {
                assertEquals(aussieCities[i % aussieCities.length], stack.pop());
            }
            assertTrue(stack.isEmpty());
        }
        
        @ParameterizedTest
        @ValueSource(strings = {"NSW", "VIC", "QLD", "WA", "SA", "TAS", "NT", "ACT"})
        @DisplayName("Stack should work with Australian states and territories")
        void testStackWithAustralianStates(String state) {
            stack.push(state);
            assertEquals(state, stack.peek());
            assertEquals(state, stack.pop());
            assertTrue(stack.isEmpty());
        }
    }
    
    /**
     * Helper class for testing with custom objects.
     */
    private static class TestObject {
        private final String value;
        
        public TestObject(String value) {
            this.value = value;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            TestObject that = (TestObject) obj;
            return value.equals(that.value);
        }
        
        @Override
        public int hashCode() {
            return value.hashCode();
        }
        
        @Override
        public String toString() {
            return "TestObject{value='" + value + "'}";
        }
    }
}
