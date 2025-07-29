/**
 * VariablesDataTypes.java
 * 
 * This program demonstrates variables and data types in Java:
 * - Primitive data types
 * - Reference data types
 * - Variable declaration and initialization
 * - Type conversion and casting
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
public class VariablesDataTypes {
    
    public static void main(String[] args) {
        System.out.println("=== Java Variables and Data Types Demo ===\n");
        
        // Demonstrate primitive data types
        demonstratePrimitiveTypes();
        
        // Demonstrate wrapper classes
        demonstrateWrapperClasses();
        
        // Demonstrate type conversion
        demonstrateTypeConversion();
        
        // Demonstrate literals
        demonstrateLiterals();
        
        // Demonstrate variable scope
        demonstrateVariableScope();
    }
    
    /**
     * Demonstrates all primitive data types in Java
     */
    public static void demonstratePrimitiveTypes() {
        System.out.println("1. PRIMITIVE DATA TYPES");
        System.out.println("========================");
        
        // Integer types
        byte byteVar = 127;                    // 8-bit signed integer (-128 to 127)
        short shortVar = 32767;                // 16-bit signed integer (-32,768 to 32,767)
        int intVar = 2147483647;               // 32-bit signed integer
        long longVar = 9223372036854775807L;   // 64-bit signed integer (note the 'L' suffix)
        
        // Floating-point types
        float floatVar = 3.14159f;             // 32-bit floating point (note the 'f' suffix)
        double doubleVar = 3.141592653589793;  // 64-bit floating point (default for decimals)
        
        // Character type
        char charVar = 'A';                    // 16-bit Unicode character
        char unicodeChar = '\u0041';           // Unicode representation of 'A'
        
        // Boolean type
        boolean booleanVar = true;             // true or false only
        
        // Display all primitive types
        System.out.printf("byte:    %d (size: %d bytes)%n", byteVar, Byte.BYTES);
        System.out.printf("short:   %d (size: %d bytes)%n", shortVar, Short.BYTES);
        System.out.printf("int:     %d (size: %d bytes)%n", intVar, Integer.BYTES);
        System.out.printf("long:    %d (size: %d bytes)%n", longVar, Long.BYTES);
        System.out.printf("float:   %.5f (size: %d bytes)%n", floatVar, Float.BYTES);
        System.out.printf("double:  %.15f (size: %d bytes)%n", doubleVar, Double.BYTES);
        System.out.printf("char:    %c (Unicode: %s, size: %d bytes)%n", charVar, unicodeChar, Character.BYTES);
        System.out.printf("boolean: %b (size: JVM dependent)%n", booleanVar);
        
        System.out.println();
    }
    
    /**
     * Demonstrates wrapper classes and autoboxing/unboxing
     */
    public static void demonstrateWrapperClasses() {
        System.out.println("2. WRAPPER CLASSES");
        System.out.println("==================");
        
        // Wrapper class objects
        Integer integerWrapper = Integer.valueOf(42);
        Double doubleWrapper = Double.valueOf(3.14159);
        Boolean booleanWrapper = Boolean.valueOf(true);
        Character characterWrapper = Character.valueOf('Z');
        
        // Autoboxing (primitive to wrapper)
        Integer autoboxed = 100;               // Automatic conversion from int to Integer
        Double autoboxedDouble = 2.718;       // Automatic conversion from double to Double
        
        // Unboxing (wrapper to primitive)
        int unboxed = integerWrapper;          // Automatic conversion from Integer to int
        double unboxedDouble = doubleWrapper;  // Automatic conversion from Double to double
        
        System.out.printf("Integer wrapper: %d%n", integerWrapper);
        System.out.printf("Double wrapper: %.5f%n", doubleWrapper);
        System.out.printf("Boolean wrapper: %b%n", booleanWrapper);
        System.out.printf("Character wrapper: %c%n", characterWrapper);
        System.out.printf("Autoboxed integer: %d%n", autoboxed);
        System.out.printf("Unboxed integer: %d%n", unboxed);
        
        // Wrapper class utility methods
        System.out.printf("Integer.MAX_VALUE: %d%n", Integer.MAX_VALUE);
        System.out.printf("Double.MAX_VALUE: %e%n", Double.MAX_VALUE);
        System.out.printf("Character.isLetter('A'): %b%n", Character.isLetter('A'));
        System.out.printf("Integer.parseInt(\"123\"): %d%n", Integer.parseInt("123"));
        
        System.out.println();
    }
    
    /**
     * Demonstrates type conversion (casting)
     */
    public static void demonstrateTypeConversion() {
        System.out.println("3. TYPE CONVERSION");
        System.out.println("==================");
        
        // Implicit conversion (widening)
        int intValue = 100;
        long longValue = intValue;      // int to long (automatic)
        float floatValue = intValue;    // int to float (automatic)
        double doubleValue = floatValue; // float to double (automatic)
        
        System.out.println("Implicit Conversion (Widening):");
        System.out.printf("int %d -> long %d%n", intValue, longValue);
        System.out.printf("int %d -> float %.1f%n", intValue, floatValue);
        System.out.printf("float %.1f -> double %.1f%n", floatValue, doubleValue);
        
        // Explicit conversion (narrowing) - requires casting
        double doubleSource = 123.456;
        int intFromDouble = (int) doubleSource;     // Truncates decimal part
        float floatFromDouble = (float) doubleSource;
        
        long longSource = 300L;
        int intFromLong = (int) longSource;         // Potential data loss if too large
        
        System.out.println("\nExplicit Conversion (Narrowing):");
        System.out.printf("double %.3f -> int %d (truncated)%n", doubleSource, intFromDouble);
        System.out.printf("double %.3f -> float %.3f%n", doubleSource, floatFromDouble);
        System.out.printf("long %d -> int %d%n", longSource, intFromLong);
        
        // String conversions
        String numberString = "456";
        int parsedInt = Integer.parseInt(numberString);
        String intToString = Integer.toString(parsedInt);
        
        System.out.println("\nString Conversions:");
        System.out.printf("String \"%s\" -> int %d%n", numberString, parsedInt);
        System.out.printf("int %d -> String \"%s\"%n", parsedInt, intToString);
        
        System.out.println();
    }
    
    /**
     * Demonstrates different types of literals
     */
    public static void demonstrateLiterals() {
        System.out.println("4. LITERALS");
        System.out.println("===========");
        
        // Integer literals
        int decimal = 42;           // Decimal literal
        int binary = 0b101010;      // Binary literal (Java 7+)
        int octal = 052;            // Octal literal (leading zero)
        int hexadecimal = 0x2A;     // Hexadecimal literal
        
        System.out.println("Integer Literals:");
        System.out.printf("Decimal: %d%n", decimal);
        System.out.printf("Binary (0b101010): %d%n", binary);
        System.out.printf("Octal (052): %d%n", octal);
        System.out.printf("Hexadecimal (0x2A): %d%n", hexadecimal);
        
        // Floating-point literals
        double scientificNotation = 1.23e4;     // Scientific notation (12300.0)
        float floatLiteral = 3.14f;             // Float literal with 'f' suffix
        double doubleLiteral = 2.718;           // Double literal (default)
        
        System.out.println("\nFloating-point Literals:");
        System.out.printf("Scientific notation (1.23e4): %.1f%n", scientificNotation);
        System.out.printf("Float literal (3.14f): %.2f%n", floatLiteral);
        System.out.printf("Double literal (2.718): %.3f%n", doubleLiteral);
        
        // Character and String literals
        char singleChar = 'A';
        char escapeChar = '\n';                 // Newline character
        char unicodeChar = '\u03B1';            // Greek letter alpha (α)
        String stringLiteral = "Hello, World!";
        String multiLineString = "Line 1\nLine 2\nLine 3";
        
        System.out.println("\nCharacter and String Literals:");
        System.out.printf("Single character: %c%n", singleChar);
        System.out.printf("Unicode character (\\u03B1): %c%n", unicodeChar);
        System.out.printf("String literal: %s%n", stringLiteral);
        System.out.println("Multi-line string:");
        System.out.println(multiLineString);
        
        // Underscore in numeric literals (Java 7+)
        long bigNumber = 1_000_000_000L;        // Underscores for readability
        double pi = 3.141_592_653_589_793;
        
        System.out.println("\nNumeric Literals with Underscores:");
        System.out.printf("Big number (1_000_000_000L): %d%n", bigNumber);
        System.out.printf("Pi (3.141_592_653_589_793): %.15f%n", pi);
        
        System.out.println();
    }
    
    /**
     * Demonstrates variable scope concepts
     */
    public static void demonstrateVariableScope() {
        System.out.println("5. VARIABLE SCOPE");
        System.out.println("=================");
        
        // Method-level (local) variables
        int localVariable = 10;
        System.out.printf("Local variable: %d%n", localVariable);
        
        // Block scope
        {
            int blockVariable = 20;
            System.out.printf("Block variable inside block: %d%n", blockVariable);
            System.out.printf("Local variable accessible in block: %d%n", localVariable);
        }
        // blockVariable is not accessible here
        
        // Loop scope
        for (int i = 0; i < 3; i++) {
            int loopVariable = i * 10;
            if (i == 1) {
                System.out.printf("Loop variable (i=%d): %d%n", i, loopVariable);
            }
        }
        // i and loopVariable are not accessible here
        
        System.out.println("Variable scope demonstration completed.");
        System.out.println();
    }
}
