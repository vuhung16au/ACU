/**
 * WrapperDemo.java
 * 
 * This program demonstrates wrapper demo in Java:
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
public class WrapperDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Wrapper Classes Detailed Demo ===\n");
        
        demonstrateUtilityMethods();
        demonstrateComparisons();
        demonstratePerformance();
        demonstrateCaching();
    }
    
    public static void demonstrateUtilityMethods() {
        System.out.println("1. WRAPPER CLASS UTILITY METHODS");
        System.out.println("=================================");
        
        // Integer utility methods
        System.out.println("Integer utilities:");
        System.out.printf("Integer.MAX_VALUE: %d%n", Integer.MAX_VALUE);
        System.out.printf("Integer.MIN_VALUE: %d%n", Integer.MIN_VALUE);
        System.out.printf("Integer.SIZE: %d bits%n", Integer.SIZE);
        System.out.printf("Integer.BYTES: %d bytes%n", Integer.BYTES);
        System.out.printf("Integer.toBinaryString(42): %s%n", Integer.toBinaryString(42));
        System.out.printf("Integer.toHexString(255): %s%n", Integer.toHexString(255));
        System.out.printf("Integer.toOctalString(64): %s%n", Integer.toOctalString(64));
        
        // Double utility methods
        System.out.println("\nDouble utilities:");
        System.out.printf("Double.MAX_VALUE: %e%n", Double.MAX_VALUE);
        System.out.printf("Double.MIN_VALUE: %e%n", Double.MIN_VALUE);
        System.out.printf("Double.isNaN(0.0/0.0): %b%n", Double.isNaN(0.0/0.0));
        System.out.printf("Double.isInfinite(1.0/0.0): %b%n", Double.isInfinite(1.0/0.0));
        System.out.printf("Double.isFinite(123.45): %b%n", Double.isFinite(123.45));
        
        // Character utility methods
        System.out.println("\nCharacter utilities:");
        char testChar = 'A';
        System.out.printf("Character.isLetter('%c'): %b%n", testChar, Character.isLetter(testChar));
        System.out.printf("Character.isDigit('5'): %b%n", Character.isDigit('5'));
        System.out.printf("Character.isWhitespace(' '): %b%n", Character.isWhitespace(' '));
        System.out.printf("Character.toLowerCase('A'): %c%n", Character.toLowerCase('A'));
        System.out.printf("Character.toUpperCase('a'): %c%n", Character.toUpperCase('a'));
        
        System.out.println();
    }
    
    public static void demonstrateComparisons() {
        System.out.println("2. WRAPPER CLASS COMPARISONS");
        System.out.println("=============================");
        
        // Reference vs value comparison
        Integer a = new Integer(100);  // Deprecated but illustrative
        Integer b = new Integer(100);  // Deprecated but illustrative
        Integer c = 100;               // Autoboxing
        Integer d = 100;               // Autoboxing
        
        System.out.println("Reference comparisons (==):");
        System.out.printf("new Integer(100) == new Integer(100): %b%n", a == b);
        System.out.printf("Integer.valueOf(100) == Integer.valueOf(100): %b%n", c == d);
        
        System.out.println("\nValue comparisons (.equals()):");
        System.out.printf("a.equals(b): %b%n", a.equals(b));
        System.out.printf("c.equals(d): %b%n", c.equals(d));
        
        // Comparison methods
        System.out.println("\nComparison methods:");
        Integer x = 42;
        Integer y = 100;
        System.out.printf("Integer.compare(42, 100): %d%n", Integer.compare(x, y));
        System.out.printf("x.compareTo(y): %d%n", x.compareTo(y));
        
        System.out.println();
    }
    
    public static void demonstratePerformance() {
        System.out.println("3. PERFORMANCE CONSIDERATIONS");
        System.out.println("==============================");
        
        int iterations = 1_000_000;
        
        // Primitive performance
        long startTime = System.nanoTime();
        int sum1 = 0;
        for (int i = 0; i < iterations; i++) {
            sum1 += i;
        }
        long primitiveTime = System.nanoTime() - startTime;
        
        // Wrapper performance
        startTime = System.nanoTime();
        Integer sum2 = 0;
        for (Integer i = 0; i < iterations; i++) {
            sum2 += i;  // Autoboxing/unboxing overhead
        }
        long wrapperTime = System.nanoTime() - startTime;
        
        System.out.printf("Primitive loop time: %.2f ms%n", primitiveTime / 1_000_000.0);
        System.out.printf("Wrapper loop time: %.2f ms%n", wrapperTime / 1_000_000.0);
        System.out.printf("Performance ratio: %.2fx slower%n", (double)wrapperTime / primitiveTime);
        
        System.out.println();
    }
    
    public static void demonstrateCaching() {
        System.out.println("4. INTEGER CACHING");
        System.out.println("==================");
        
        // Integer caching for values -128 to 127
        System.out.println("Values in cache range (-128 to 127):");
        Integer cached1 = 100;
        Integer cached2 = 100;
        System.out.printf("Integer.valueOf(100) == Integer.valueOf(100): %b%n", cached1 == cached2);
        
        System.out.println("\nValues outside cache range:");
        Integer notCached1 = 200;
        Integer notCached2 = 200;
        System.out.printf("Integer.valueOf(200) == Integer.valueOf(200): %b%n", notCached1 == notCached2);
        
        // Demonstrating the cache boundary
        System.out.println("\nCache boundary demonstration:");
        for (int i = 126; i <= 129; i++) {
            Integer val1 = i;
            Integer val2 = i;
            System.out.printf("Integer.valueOf(%d) == Integer.valueOf(%d): %b%n", 
                            i, i, val1 == val2);
        }
        
        System.out.println();
    }
}
