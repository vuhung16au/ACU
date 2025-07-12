package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Basic tests for the JavaFX application classes.
 * Note: These are unit tests for class instantiation and basic functionality.
 * Full UI testing would require TestFX framework.
 */
public class NodeStyleRotateDemoTest {
    
    @Test
    public void testLauncherClassExists() {
        // Test that the Launcher class can be instantiated
        assertDoesNotThrow(() -> {
            Launcher launcher = new Launcher();
            assertNotNull(launcher);
        });
    }
    
    @Test
    public void testSimpleNodeStyleRotateDemoClassExists() {
        // Test that the SimpleNodeStyleRotateDemo class can be instantiated
        assertDoesNotThrow(() -> {
            SimpleNodeStyleRotateDemo demo = new SimpleNodeStyleRotateDemo();
            assertNotNull(demo);
        });
    }
    
    @Test
    public void testNodeStyleRotateDemoClassExists() {
        // Test that the NodeStyleRotateDemo class can be instantiated
        assertDoesNotThrow(() -> {
            NodeStyleRotateDemo demo = new NodeStyleRotateDemo();
            assertNotNull(demo);
        });
    }
    
    @Test
    public void testMainMethodsExist() {
        // Test that main methods exist and can be called without throwing exceptions
        assertDoesNotThrow(() -> {
            // These would normally launch JavaFX applications, but we're just testing
            // that the methods exist and don't throw immediate exceptions
            String[] args = {};
            
            // Test Launcher main method exists
            assertDoesNotThrow(() -> {
                Launcher.class.getDeclaredMethod("main", String[].class);
            });
            
            // Test SimpleNodeStyleRotateDemo main method exists
            assertDoesNotThrow(() -> {
                SimpleNodeStyleRotateDemo.class.getDeclaredMethod("main", String[].class);
            });
            
            // Test NodeStyleRotateDemo main method exists  
            assertDoesNotThrow(() -> {
                NodeStyleRotateDemo.class.getDeclaredMethod("main", String[].class);
            });
        });
    }
}
