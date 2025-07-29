/**
 * SwitchStatement.java
 * 
 * This program demonstrates switch statements in Java:
 * - Switch case syntax
 * - Fall-through behavior
 * - Switch expressions
 * - Switch statement optimization
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
public class SwitchStatement {
    
    /**
     * Main method demonstrating various switch statement patterns
     */
    public static void main(String[] args) {
        System.out.println("=== Java Switch Statement Examples ===\n");
        
        // Demonstrate traditional switch statement
        demonstrateTraditionalSwitch();
        
        // Demonstrate switch with break statements
        demonstrateSwitchWithBreak();
        
        // Demonstrate switch with fall-through (intentional)
        demonstrateSwitchFallThrough();
        
        // Demonstrate switch with default case
        demonstrateSwitchWithDefault();
        
        // Demonstrate enhanced switch expression (Java 14+)
        demonstrateEnhancedSwitch();
        
        // Demonstrate switch with multiple values
        demonstrateSwitchMultipleValues();
        
        // Demonstrate switch with enum
        demonstrateSwitchWithEnum();
        
        System.out.println("\n=== Switch Statement Examples Complete ===");
    }
    
    /**
     * Demonstrates a basic traditional switch statement
     * 
     * Python equivalent would be:
     * if day == 1: print("Monday")
     * elif day == 2: print("Tuesday")
     * # etc.
     */
    public static void demonstrateTraditionalSwitch() {
        System.out.println("1. Traditional Switch Statement:");
        System.out.println("--------------------------------");
        
        int day = 3;
        
        switch (day) {
            case 1:
                System.out.println("Monday");
                break;
            case 2:
                System.out.println("Tuesday");
                break;
            case 3:
                System.out.println("Wednesday");
                break;
            case 4:
                System.out.println("Thursday");
                break;
            case 5:
                System.out.println("Friday");
                break;
            case 6:
                System.out.println("Saturday");
                break;
            case 7:
                System.out.println("Sunday");
                break;
        }
        System.out.println();
    }
    
    /**
     * Demonstrates switch statement with break statements
     * Break statements are crucial in Java - without them, execution falls through
     * 
     * Python equivalent: if-elif-else chain (no fall-through behavior)
     */
    public static void demonstrateSwitchWithBreak() {
        System.out.println("2. Switch with Break Statements:");
        System.out.println("--------------------------------");
        
        int grade = 85;
        
        switch (grade / 10) {  // Integer division to get grade range
            case 10:
            case 9:
                System.out.println("Grade: A");
                break;
            case 8:
                System.out.println("Grade: B");
                break;
            case 7:
                System.out.println("Grade: C");
                break;
            case 6:
                System.out.println("Grade: D");
                break;
            default:
                System.out.println("Grade: F");
                break;
        }
        System.out.println();
    }
    
    /**
     * Demonstrates intentional fall-through in switch statements
     * This is a feature unique to Java - Python doesn't have this behavior
     */
    public static void demonstrateSwitchFallThrough() {
        System.out.println("3. Switch with Intentional Fall-Through:");
        System.out.println("----------------------------------------");
        
        int month = 2;
        
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                System.out.println("This month has 31 days");
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                System.out.println("This month has 30 days");
                break;
            case 2:
                System.out.println("This month has 28 or 29 days (February)");
                break;
        }
        System.out.println();
    }
    
    /**
     * Demonstrates switch statement with default case
     * Default case is similar to Python's else clause
     */
    public static void demonstrateSwitchWithDefault() {
        System.out.println("4. Switch with Default Case:");
        System.out.println("----------------------------");
        
        String direction = "NORTH";
        
        switch (direction) {
            case "NORTH":
                System.out.println("Moving North");
                break;
            case "SOUTH":
                System.out.println("Moving South");
                break;
            case "EAST":
                System.out.println("Moving East");
                break;
            case "WEST":
                System.out.println("Moving West");
                break;
            default:
                System.out.println("Invalid direction: " + direction);
                break;
        }
        System.out.println();
    }
    
    /**
     * Demonstrates enhanced switch expression (Java 14+)
     * This is more similar to Python's conditional expressions
     * 
     * Python equivalent: result = "Even" if num % 2 == 0 else "Odd"
     */
    public static void demonstrateEnhancedSwitch() {
        System.out.println("5. Enhanced Switch Expression (Java 14+):");
        System.out.println("----------------------------------------");
        
        int num = 7;
        
        // Enhanced switch expression - returns a value
        String result = switch (num % 2) {
            case 0 -> "Even";
            case 1 -> "Odd";
            default -> "Unknown";
        };
        
        System.out.println("Number " + num + " is " + result);
        
        // Another example with multiple statements
        String message = switch (num) {
            case 1 -> "One";
            case 2 -> "Two";
            case 3 -> "Three";
            case 4 -> "Four";
            case 5 -> "Five";
            default -> "Greater than 5";
        };
        
        System.out.println("Number " + num + " in words: " + message);
        System.out.println();
    }
    
    /**
     * Demonstrates switch with multiple values in a single case
     * This is a more concise way to handle multiple conditions
     */
    public static void demonstrateSwitchMultipleValues() {
        System.out.println("6. Switch with Multiple Values:");
        System.out.println("-------------------------------");
        
        char vowel = 'e';
        
        switch (Character.toLowerCase(vowel)) {
            case 'a', 'e', 'i', 'o', 'u':
                System.out.println("'" + vowel + "' is a vowel");
                break;
            default:
                System.out.println("'" + vowel + "' is a consonant");
                break;
        }
        System.out.println();
    }
    
    /**
     * Demonstrates switch statement with enum
     * Enums provide type safety and are commonly used with switch statements
     */
    public static void demonstrateSwitchWithEnum() {
        System.out.println("7. Switch with Enum:");
        System.out.println("--------------------");
        
        TrafficLight light = TrafficLight.YELLOW;
        
        switch (light) {
            case RED:
                System.out.println("Stop!");
                break;
            case YELLOW:
                System.out.println("Prepare to stop");
                break;
            case GREEN:
                System.out.println("Go!");
                break;
        }
        System.out.println();
    }
    
    /**
     * Enum for traffic light demonstration
     * Enums in Java are similar to Python's Enum class
     */
    public enum TrafficLight {
        RED, YELLOW, GREEN
    }
} 