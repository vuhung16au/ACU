/**
 * Basic Unit Tests for Variables and Data Types
 * 
 * Simple test cases to verify data type behavior and conversions.
 * This is a basic implementation without JUnit framework.
 */
public class VariablesDataTypesTest {
    
    private static int testsPassed = 0;
    private static int testsTotal = 0;
    
    public static void main(String[] args) {
        System.out.println("=== Variables and Data Types Tests ===\n");
        
        testPrimitiveTypes();
        testTypeConversions();
        testWrapperClasses();
        testLiterals();
        
        printTestResults();
    }
    
    public static void testPrimitiveTypes() {
        System.out.println("Testing Primitive Types...");
        
        // Test byte range
        assertTrue("Byte max value", Byte.MAX_VALUE == 127);
        assertTrue("Byte min value", Byte.MIN_VALUE == -128);
        
        // Test integer arithmetic
        int a = 10, b = 20;
        assertTrue("Integer addition", (a + b) == 30);
        
        // Test floating point precision
        double d1 = 0.1, d2 = 0.2, d3 = 0.3;
        assertFalse("Floating point precision issue", (d1 + d2) == d3);
        
        // Test boolean logic
        boolean t = true, f = false;
        assertTrue("Boolean AND", t && !f);
        assertTrue("Boolean OR", t || f);
        
        // Test character operations
        char c1 = 'A', c2 = 'a';
        assertTrue("Character ASCII difference", (c2 - c1) == 32);
        
        System.out.println("Primitive types tests completed.\n");
    }
    
    public static void testTypeConversions() {
        System.out.println("Testing Type Conversions...");
        
        // Test implicit conversions
        int intVal = 100;
        long longVal = intVal;
        assertTrue("Int to long conversion", longVal == 100L);
        
        double doubleVal = intVal;
        assertTrue("Int to double conversion", doubleVal == 100.0);
        
        // Test explicit conversions
        double d = 123.456;
        int truncated = (int) d;
        assertTrue("Double to int truncation", truncated == 123);
        
        // Test string conversions
        String numStr = "42";
        int parsed = Integer.parseInt(numStr);
        assertTrue("String to int parsing", parsed == 42);
        
        String converted = Integer.toString(parsed);
        assertTrue("Int to string conversion", converted.equals("42"));
        
        System.out.println("Type conversion tests completed.\n");
    }
    
    public static void testWrapperClasses() {
        System.out.println("Testing Wrapper Classes...");
        
        // Test autoboxing
        Integer wrapped = 42;
        assertTrue("Autoboxing", wrapped.intValue() == 42);
        
        // Test unboxing
        int unwrapped = wrapped;
        assertTrue("Unboxing", unwrapped == 42);
        
        // Test wrapper equality
        Integer i1 = 100, i2 = 100;
        assertTrue("Integer caching", i1 == i2);  // Cache range
        
        Integer i3 = 200, i4 = 200;
        assertFalse("Integer no caching", i3 == i4);  // Outside cache
        assertTrue("Integer equals", i3.equals(i4));
        
        // Test utility methods
        assertTrue("Integer max value", Integer.MAX_VALUE > 0);
        assertTrue("Character is letter", Character.isLetter('A'));
        assertTrue("Double is finite", Double.isFinite(123.45));
        
        System.out.println("Wrapper classes tests completed.\n");
    }
    
    public static void testLiterals() {
        System.out.println("Testing Literals...");
        
        // Test integer literals
        int decimal = 42;
        int binary = 0b101010;
        int octal = 052;
        int hex = 0x2A;
        assertTrue("Integer literal formats", 
                  decimal == binary && binary == octal && octal == hex);
        
        // Test floating-point literals
        double scientific = 1.23e2;
        assertTrue("Scientific notation", scientific == 123.0);
        
        // Test character literals
        char c1 = 'A';
        char c2 = '\u0041';
        assertTrue("Character literals", c1 == c2);
        
        // Test string literals
        String s1 = "Hello";
        String s2 = "Hello";
        assertTrue("String literal interning", s1 == s2);
        
        System.out.println("Literals tests completed.\n");
    }
    
    // Simple assertion methods
    public static void assertTrue(String message, boolean condition) {
        testsTotal++;
        if (condition) {
            testsPassed++;
            System.out.printf("✓ PASS: %s%n", message);
        } else {
            System.out.printf("✗ FAIL: %s%n", message);
        }
    }
    
    public static void assertFalse(String message, boolean condition) {
        assertTrue(message, !condition);
    }
    
    public static void printTestResults() {
        System.out.println("=== Test Results ===");
        System.out.printf("Tests passed: %d/%d%n", testsPassed, testsTotal);
        System.out.printf("Success rate: %.1f%%%n", 
                         (100.0 * testsPassed / testsTotal));
        
        if (testsPassed == testsTotal) {
            System.out.println("🎉 All tests passed!");
        } else {
            System.out.printf("❌ %d test(s) failed.%n", testsTotal - testsPassed);
        }
    }
}
