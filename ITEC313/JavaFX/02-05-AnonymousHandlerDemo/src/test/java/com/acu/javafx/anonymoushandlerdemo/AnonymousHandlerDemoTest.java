package com.acu.javafx.anonymoushandlerdemo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for AnonymousHandlerDemo functionality.
 * Tests SAM interface concepts and event handling patterns.
 */
public class AnonymousHandlerDemoTest {

    /**
     * Test that demonstrates SAM interface concept.
     * Verifies that functional interfaces work correctly.
     */
    @Test
    public void testSAMInterface() {
        // Test Runnable SAM interface with anonymous inner class
        Runnable anonymousRunnable = new Runnable() {
            @Override
            public void run() {
                // This should execute without error
            }
        };
        
        // Test Runnable SAM interface with lambda expression
        Runnable lambdaRunnable = () -> {
            // This should execute without error
        };
        
        // Both should execute without throwing exceptions
        assertDoesNotThrow(() -> anonymousRunnable.run());
        assertDoesNotThrow(() -> lambdaRunnable.run());
    }

    /**
     * Test that demonstrates custom SAM interface.
     * Verifies that custom functional interfaces work correctly.
     */
    @Test
    public void testCustomSAMInterface() {
        // Define a custom SAM interface
        @FunctionalInterface
        interface TestSAM {
            void execute(String message);
        }
        
        // Test with anonymous inner class
        TestSAM anonymousHandler = new TestSAM() {
            @Override
            public void execute(String message) {
                // Should execute without error
            }
        };
        
        // Test with lambda expression
        TestSAM lambdaHandler = (message) -> {
            // Should execute without error
        };
        
        // Both should execute without throwing exceptions
        assertDoesNotThrow(() -> anonymousHandler.execute("test"));
        assertDoesNotThrow(() -> lambdaHandler.execute("test"));
    }

    /**
     * Test that verifies the concept of SAM interfaces.
     * Ensures that interfaces with exactly one abstract method work correctly.
     */
    @Test
    public void testSAMInterfaceConcept() {
        // Test with multiple SAM interfaces
        Runnable runnable = () -> System.out.println("Runnable executed");
        java.util.function.Consumer<String> consumer = (s) -> System.out.println("Consumer: " + s);
        java.util.function.Supplier<String> supplier = () -> "Supplier result";
        
        // All should execute without throwing exceptions
        assertDoesNotThrow(() -> runnable.run());
        assertDoesNotThrow(() -> consumer.accept("test"));
        assertDoesNotThrow(() -> supplier.get());
    }

    /**
     * Test that demonstrates the equivalence between
     * anonymous inner classes and lambda expressions.
     */
    @Test
    public void testAnonymousVsLambda() {
        final String[] result = new String[1];
        
        // Anonymous inner class approach
        Runnable anonymousRunnable = new Runnable() {
            @Override
            public void run() {
                result[0] = "anonymous";
            }
        };
        
        // Lambda expression approach
        Runnable lambdaRunnable = () -> {
            result[0] = "lambda";
        };
        
        // Test anonymous inner class
        result[0] = "";
        anonymousRunnable.run();
        assertEquals("anonymous", result[0]);
        
        // Test lambda expression
        result[0] = "";
        lambdaRunnable.run();
        assertEquals("lambda", result[0]);
    }
} 