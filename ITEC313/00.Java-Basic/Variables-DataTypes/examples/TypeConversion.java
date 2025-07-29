/**
 * TypeConversion.java
 * 
 * This program demonstrates type conversion in Java:
 * - Core concepts and principles
 * - Implementation techniques
 * - Best practices and patterns
 * - Practical examples and usage
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
public class TypeConversion {
    
    public static void main(String[] args) {
        System.out.println("=== Advanced Type Conversion Examples ===\n");
        
        demonstrateOverflow();
        demonstratePrecisionLoss();
        demonstrateStringConversions();
        demonstrateCharacterConversions();
    }
    
    public static void demonstrateOverflow() {
        System.out.println("1. OVERFLOW SCENARIOS");
        System.out.println("=====================");
        
        // Integer overflow
        int maxInt = Integer.MAX_VALUE;
        int overflowInt = maxInt + 1;  // Wraps around to negative
        
        System.out.printf("Integer.MAX_VALUE: %d%n", maxInt);
        System.out.printf("MAX_VALUE + 1: %d (overflow!)%n", overflowInt);
        
        // Long to int conversion with potential overflow
        long largeLong = 3_000_000_000L;
        int convertedInt = (int) largeLong;  // Data loss occurs
        
        System.out.printf("Large long: %d%n", largeLong);
        System.out.printf("Converted to int: %d (data loss!)%n", convertedInt);
        
        System.out.println();
    }
    
    public static void demonstratePrecisionLoss() {
        System.out.println("2. PRECISION LOSS");
        System.out.println("=================");
        
        // Double to float precision loss
        double preciseDouble = 1.23456789012345;
        float lessprecise = (float) preciseDouble;
        
        System.out.printf("Original double: %.15f%n", preciseDouble);
        System.out.printf("Converted float: %.15f%n", (double)lessprecise);
        
        // Large integer to float precision loss
        int largeInt = 16777217;  // 2^24 + 1
        float floatVersion = largeInt;
        int backToInt = (int) floatVersion;
        
        System.out.printf("Original int: %d%n", largeInt);
        System.out.printf("As float, back to int: %d%n", backToInt);
        
        System.out.println();
    }
    
    public static void demonstrateStringConversions() {
        System.out.println("3. STRING CONVERSIONS");
        System.out.println("=====================");
        
        // Various string to number conversions
        String[] numberStrings = {"123", "123.456", "1.23e4", "FF", "true"};
        
        for (String str : numberStrings) {
            try {
                System.out.printf("String: %-8s -> ", str);
                
                if (str.equals("FF")) {
                    int hexValue = Integer.parseInt(str, 16);
                    System.out.printf("Hex to int: %d%n", hexValue);
                } else if (str.equals("true")) {
                    boolean boolValue = Boolean.parseBoolean(str);
                    System.out.printf("Boolean: %b%n", boolValue);
                } else if (str.contains(".") || str.contains("e")) {
                    double doubleValue = Double.parseDouble(str);
                    System.out.printf("Double: %.3f%n", doubleValue);
                } else {
                    int intValue = Integer.parseInt(str);
                    System.out.printf("Int: %d%n", intValue);
                }
            } catch (NumberFormatException e) {
                System.out.printf("Invalid format!%n");
            }
        }
        
        System.out.println();
    }
    
    public static void demonstrateCharacterConversions() {
        System.out.println("4. CHARACTER CONVERSIONS");
        System.out.println("========================");
        
        // Character to numeric conversions
        char digit = '5';
        int numericValue = digit - '0';  // ASCII arithmetic
        int asciiValue = (int) digit;
        
        System.out.printf("Character: %c%n", digit);
        System.out.printf("Numeric value: %d%n", numericValue);
        System.out.printf("ASCII value: %d%n", asciiValue);
        
        // Numeric to character conversions
        int asciiCode = 65;
        char character = (char) asciiCode;
        
        System.out.printf("ASCII code: %d -> Character: %c%n", asciiCode, character);
        
        // Unicode examples
        char[] unicodeChars = {'\u0041', '\u03B1', '\u4E2D'};
        System.out.print("Unicode characters: ");
        for (char c : unicodeChars) {
            System.out.printf("%c ", c);
        }
        System.out.println();
        
        System.out.println();
    }
}
