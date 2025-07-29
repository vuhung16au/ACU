/**
 * Advanced.java
 * 
 * This program demonstrates advanced in Java:
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
public class Advanced {
    
    public static void main(String[] args) {
        System.out.println("=== Advanced: Complex Switch Patterns ===\n");
        
        // Example 1: Nested switch statements
        demonstrateNestedSwitch();
        
        // Example 2: Switch expressions with yield
        demonstrateSwitchWithYield();
        
        // Example 3: Real-world scenario: Calculator
        demonstrateCalculator();
        
        // Example 4: Switch with complex conditions
        demonstrateComplexConditions();
        
        System.out.println("=== Advanced Complete ===\n");
    }
    
    /**
     * Demonstrates nested switch statements
     * Shows how switch statements can be nested for complex logic
     */
    public static void demonstrateNestedSwitch() {
        System.out.println("Nested Switch Statements:");
        System.out.println("-------------------------");
        
        String[] commands = {"MOVE NORTH", "MOVE SOUTH", "ATTACK", "DEFEND", "HEAL"};
        
        for (String command : commands) {
            String[] parts = command.split(" ");
            String action = parts[0];
            String direction = parts.length > 1 ? parts[1] : "";
            
            switch (action) {
                case "MOVE":
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
                            System.out.println("Invalid direction");
                            break;
                    }
                    break;
                case "ATTACK":
                    System.out.println("Attacking enemy");
                    break;
                case "DEFEND":
                    System.out.println("Defending position");
                    break;
                case "HEAL":
                    System.out.println("Healing wounds");
                    break;
                default:
                    System.out.println("Unknown command: " + command);
                    break;
            }
        }
        System.out.println();
    }
    
    /**
     * Demonstrates switch expressions with yield (Java 14+)
     * Yield allows for more complex logic within switch expressions
     */
    public static void demonstrateSwitchWithYield() {
        System.out.println("Switch Expressions with Yield:");
        System.out.println("-----------------------------");
        
        int[] scores = {85, 92, 78, 95, 88};
        
        for (int score : scores) {
            String grade = switch (score / 10) {
                case 10, 9 -> {
                    if (score == 100) {
                        yield "Perfect A+";
                    } else if (score >= 95) {
                        yield "A+";
                    } else {
                        yield "A";
                    }
                }
                case 8 -> {
                    if (score >= 85) {
                        yield "B+";
                    } else {
                        yield "B";
                    }
                }
                case 7 -> "C";
                case 6 -> "D";
                default -> "F";
            };
            
            System.out.println("Score: " + score + " -> Grade: " + grade);
        }
        System.out.println();
    }
    
    /**
     * Demonstrates a real-world calculator using switch
     * Shows practical application of switch statements
     */
    public static void demonstrateCalculator() {
        System.out.println("Calculator with Switch:");
        System.out.println("----------------------");
        
        // Simulate calculator operations
        double[] numbers1 = {10, 20, 15, 8};
        double[] numbers2 = {5, 4, 3, 2};
        String[] operations = {"+", "-", "*", "/"};
        
        for (int i = 0; i < numbers1.length; i++) {
            double num1 = numbers1[i];
            double num2 = numbers2[i];
            String op = operations[i];
            
            double result = switch (op) {
                case "+" -> num1 + num2;
                case "-" -> num1 - num2;
                case "*" -> num1 * num2;
                case "/" -> {
                    if (num2 == 0) {
                        yield Double.POSITIVE_INFINITY; // Handle division by zero
                    } else {
                        yield num1 / num2;
                    }
                }
                default -> 0.0;
            };
            
            System.out.printf("%.1f %s %.1f = %.1f%n", num1, op, num2, result);
        }
        System.out.println();
    }
    
    /**
     * Demonstrates switch with complex conditions
     * Shows how switch can handle complex decision logic
     */
    public static void demonstrateComplexConditions() {
        System.out.println("Complex Switch Conditions:");
        System.out.println("-------------------------");
        
        int[] ages = {5, 12, 18, 25, 35, 50, 65, 80};
        
        for (int age : ages) {
            String category = switch (age) {
                case 0, 1, 2, 3, 4, 5 -> "Toddler";
                case 6, 7, 8, 9, 10, 11, 12 -> "Child";
                case 13, 14, 15, 16, 17 -> "Teenager";
                case 18, 19, 20, 21, 22, 23, 24, 25 -> "Young Adult";
                case 26, 27, 28, 29, 30, 31, 32, 33, 34, 35 -> "Adult";
                case 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50 -> "Middle-aged";
                case 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65 -> "Senior";
                default -> "Elderly";
            };
            
            System.out.println("Age " + age + " -> " + category);
        }
        System.out.println();
    }
} 