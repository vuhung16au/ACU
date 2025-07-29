/**
 * Example1.java
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
public class Example1 {
    
    public static void main(String[] args) {
        System.out.println("=== Example 1: Basic Switch Patterns ===\n");
        
        // Example 1: Simple number to word conversion
        convertNumberToWord();
        
        // Example 2: Grade calculation
        calculateGrade();
        
        // Example 3: Day of week
        getDayOfWeek();
        
        System.out.println("=== Example 1 Complete ===\n");
    }
    
    /**
     * Converts numbers 1-5 to their word representation
     * Demonstrates basic switch statement with break
     */
    public static void convertNumberToWord() {
        System.out.println("Number to Word Conversion:");
        System.out.println("-------------------------");
        
        int[] numbers = {1, 3, 5, 7, 2};
        
        for (int num : numbers) {
            switch (num) {
                case 1:
                    System.out.println(num + " -> One");
                    break;
                case 2:
                    System.out.println(num + " -> Two");
                    break;
                case 3:
                    System.out.println(num + " -> Three");
                    break;
                case 4:
                    System.out.println(num + " -> Four");
                    break;
                case 5:
                    System.out.println(num + " -> Five");
                    break;
                default:
                    System.out.println(num + " -> Unknown number");
                    break;
            }
        }
        System.out.println();
    }
    
    /**
     * Calculates letter grade based on numeric score
     * Demonstrates switch with fall-through for grade ranges
     */
    public static void calculateGrade() {
        System.out.println("Grade Calculation:");
        System.out.println("------------------");
        
        int[] scores = {95, 87, 73, 65, 45, 100};
        
        for (int score : scores) {
            String grade;
            
            switch (score / 10) {
                case 10:
                case 9:
                    grade = "A";
                    break;
                case 8:
                    grade = "B";
                    break;
                case 7:
                    grade = "C";
                    break;
                case 6:
                    grade = "D";
                    break;
                default:
                    grade = "F";
                    break;
            }
            
            System.out.println("Score: " + score + " -> Grade: " + grade);
        }
        System.out.println();
    }
    
    /**
     * Gets day name from day number
     * Demonstrates switch with default case
     */
    public static void getDayOfWeek() {
        System.out.println("Day of Week:");
        System.out.println("-------------");
        
        int[] days = {1, 3, 5, 7, 9, 12};
        
        for (int day : days) {
            String dayName;
            
            switch (day) {
                case 1:
                    dayName = "Monday";
                    break;
                case 2:
                    dayName = "Tuesday";
                    break;
                case 3:
                    dayName = "Wednesday";
                    break;
                case 4:
                    dayName = "Thursday";
                    break;
                case 5:
                    dayName = "Friday";
                    break;
                case 6:
                    dayName = "Saturday";
                    break;
                case 7:
                    dayName = "Sunday";
                    break;
                default:
                    dayName = "Invalid day";
                    break;
            }
            
            System.out.println("Day " + day + " -> " + dayName);
        }
        System.out.println();
    }
} 