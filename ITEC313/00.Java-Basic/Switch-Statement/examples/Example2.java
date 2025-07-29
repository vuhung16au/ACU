/**
 * Example2.java
 * 
 * This program demonstrates example in Java:
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
public class Example2 {
    
    public static void main(String[] args) {
        System.out.println("=== Example 2: Advanced Switch Patterns ===\n");
        
        // Example 1: Enhanced switch expressions
        demonstrateEnhancedSwitch();
        
        // Example 2: Switch with multiple values
        demonstrateMultipleValues();
        
        // Example 2: Switch with complex logic
        demonstrateComplexLogic();
        
        // Example 4: Switch with enum
        demonstrateEnumSwitch();
        
        System.out.println("=== Example 2 Complete ===\n");
    }
    
    /**
     * Demonstrates enhanced switch expressions (Java 14+)
     * These are more similar to Python's conditional expressions
     */
    public static void demonstrateEnhancedSwitch() {
        System.out.println("Enhanced Switch Expressions:");
        System.out.println("---------------------------");
        
        int[] numbers = {2, 5, 8, 11, 15};
        
        for (int num : numbers) {
            // Enhanced switch expression - returns a value directly
            String category = switch (num) {
                case 1, 2, 3 -> "Small";
                case 4, 5, 6 -> "Medium";
                case 7, 8, 9 -> "Large";
                case 10, 11, 12 -> "Extra Large";
                default -> "Huge";
            };
            
            System.out.println("Number " + num + " is " + category);
        }
        System.out.println();
    }
    
    /**
     * Demonstrates switch with multiple values in a single case
     * This is more concise than multiple separate cases
     */
    public static void demonstrateMultipleValues() {
        System.out.println("Switch with Multiple Values:");
        System.out.println("---------------------------");
        
        char[] characters = {'a', 'e', 'i', 'o', 'u', 'b', 'c', 'd', 'f', 'g'};
        
        for (char c : characters) {
            String type = switch (Character.toLowerCase(c)) {
                case 'a', 'e', 'i', 'o', 'u' -> "Vowel";
                case 'b', 'c', 'd', 'f', 'g' -> "Consonant";
                default -> "Other";
            };
            
            System.out.println("'" + c + "' is a " + type);
        }
        System.out.println();
    }
    
    /**
     * Demonstrates switch with more complex logic
     * Shows how switch can be used for complex decision making
     */
    public static void demonstrateComplexLogic() {
        System.out.println("Complex Switch Logic:");
        System.out.println("--------------------");
        
        int[] temperatures = {-5, 15, 25, 35, 45, 55};
        
        for (int temp : temperatures) {
            String clothing = switch (temp / 10) {
                case -1, 0 -> "Heavy coat and gloves";
                case 1 -> "Light jacket";
                case 2 -> "T-shirt and jeans";
                case 3 -> "Shorts and t-shirt";
                case 4 -> "Light summer clothes";
                default -> "Stay indoors!";
            };
            
            System.out.println("Temperature " + temp + "°C: " + clothing);
        }
        System.out.println();
    }
    
    /**
     * Demonstrates switch with enum
     * Enums provide type safety and are commonly used with switch
     */
    public static void demonstrateEnumSwitch() {
        System.out.println("Switch with Enum:");
        System.out.println("-----------------");
        
        Season[] seasons = {Season.SPRING, Season.SUMMER, Season.AUTUMN, Season.WINTER};
        
        for (Season season : seasons) {
            String description = switch (season) {
                case SPRING -> "Flowers bloom, temperature rises";
                case SUMMER -> "Hot weather, long days";
                case AUTUMN -> "Leaves fall, temperature drops";
                case WINTER -> "Cold weather, short days";
            };
            
            System.out.println(season + ": " + description);
        }
        System.out.println();
    }
    
    /**
     * Enum for seasons demonstration
     * Enums in Java provide type safety and are similar to Python's Enum
     */
    public enum Season {
        SPRING, SUMMER, AUTUMN, WINTER
    }
} 