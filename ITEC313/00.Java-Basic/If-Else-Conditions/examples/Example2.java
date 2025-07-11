/**
 * Example 2: Advanced If-Else Conditions
 * 
 * This example demonstrates more complex conditional logic including:
 * - Nested if-else statements
 * - Ternary operators
 * - Complex boolean expressions
 * - Switch-like logic with if-else
 * 
 * Course: ITEC313 - Object-Oriented Programming
 * Institution: Australian Catholic University (ACU)
 */

public class Example2 {
    
    public static void main(String[] args) {
        System.out.println("=== Example 2: Advanced If-Else Conditions ===\n");
        
        // Example 1: Nested conditions
        demonstrateNestedConditions();
        
        // Example 2: Ternary operators
        demonstrateTernaryOperators();
        
        // Example 3: Complex boolean expressions
        demonstrateComplexBooleanExpressions();
        
        // Example 4: Switch-like logic
        demonstrateSwitchLikeLogic();
        
        System.out.println("=== Example 2 Complete ===");
    }
    
    /**
     * Demonstrates nested if-else conditions
     * Shows how to handle multiple dependent conditions
     */
    public static void demonstrateNestedConditions() {
        System.out.println("1. Nested Conditions");
        System.out.println("====================");
        
        int age = 25;
        boolean hasLicense = true;
        boolean hasInsurance = false;
        boolean hasGoodCredit = true;
        
        System.out.printf("Age: %d, License: %b, Insurance: %b, Good Credit: %b%n", 
                         age, hasLicense, hasInsurance, hasGoodCredit);
        
        // Nested conditions for car rental eligibility
        if (age >= 21) {
            System.out.println("✓ Age requirement met");
            
            if (hasLicense) {
                System.out.println("✓ License requirement met");
                
                if (hasInsurance) {
                    System.out.println("✓ Insurance requirement met");
                    System.out.println("✓ Can rent a car!");
                } else {
                    System.out.println("✗ Insurance required");
                    
                    if (hasGoodCredit) {
                        System.out.println("✓ Can purchase insurance at rental");
                        System.out.println("✓ Can rent a car with insurance purchase");
                    } else {
                        System.out.println("✗ Cannot purchase insurance");
                        System.out.println("✗ Cannot rent a car");
                    }
                }
            } else {
                System.out.println("✗ License required");
                System.out.println("✗ Cannot rent a car");
            }
        } else {
            System.out.println("✗ Must be 21 or older");
            System.out.println("✗ Cannot rent a car");
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates ternary operator usage
     * In Python: result = "Pass" if score >= 60 else "Fail"
     */
    public static void demonstrateTernaryOperators() {
        System.out.println("2. Ternary Operators");
        System.out.println("====================");
        
        int score = 85;
        int age = 18;
        boolean isStudent = true;
        
        System.out.printf("Score: %d, Age: %d, Student: %b%n", score, age, isStudent);
        
        // Basic ternary
        String result = (score >= 60) ? "Pass" : "Fail";
        System.out.printf("Result: %s%n", result);
        
        // Nested ternary for grade calculation
        String grade = (score >= 90) ? "A" : 
                      (score >= 80) ? "B" : 
                      (score >= 70) ? "C" : 
                      (score >= 60) ? "D" : "F";
        System.out.printf("Grade: %s%n", grade);
        
        // Ternary with different data types
        int discount = (age < 18) ? 20 : (isStudent ? 10 : 0);
        System.out.printf("Discount: %d%%%n", discount);
        
        // Ternary in method calls
        System.out.println("Status: " + ((score >= 80 && age >= 18) ? "Eligible" : "Not Eligible"));
        
        // Complex ternary with multiple conditions
        String category = (age < 13) ? "Child" : 
                         (age < 20) ? "Teenager" : 
                         (age < 65) ? "Adult" : "Senior";
        System.out.printf("Category: %s%n", category);
        
        System.out.println();
    }
    
    /**
     * Demonstrates complex boolean expressions
     * Shows how to combine multiple conditions logically
     */
    public static void demonstrateComplexBooleanExpressions() {
        System.out.println("3. Complex Boolean Expressions");
        System.out.println("==============================");
        
        boolean isWeekend = true;
        boolean isHoliday = false;
        boolean isRaining = false;
        boolean hasWork = false;
        boolean hasMoney = true;
        boolean isHealthy = true;
        
        System.out.printf("Weekend: %b, Holiday: %b, Raining: %b%n", isWeekend, isHoliday, isRaining);
        System.out.printf("Work: %b, Money: %b, Healthy: %b%n", hasWork, hasMoney, isHealthy);
        
        // Complex outdoor activity decision
        if ((isWeekend || isHoliday) && !hasWork && !isRaining && isHealthy) {
            System.out.println("✓ Perfect conditions for outdoor activities");
            
            if (hasMoney) {
                System.out.println("✓ Can go to amusement park or restaurant");
            } else {
                System.out.println("✓ Can go for a walk or visit free attractions");
            }
        } else if ((isWeekend || isHoliday) && !hasWork && isRaining) {
            System.out.println("✓ Indoor activities recommended");
            
            if (hasMoney) {
                System.out.println("✓ Can go to movies or shopping mall");
            } else {
                System.out.println("✓ Can stay home and watch TV or read");
            }
        } else if (hasWork) {
            System.out.println("✗ Need to work today");
        } else if (isRaining) {
            System.out.println("✗ Weather is not suitable for outdoor activities");
        } else if (!isHealthy) {
            System.out.println("✗ Health condition prevents outdoor activities");
        } else {
            System.out.println("✗ Other constraints prevent outdoor activities");
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates switch-like logic using if-else
     * Shows how to handle multiple exclusive conditions
     */
    public static void demonstrateSwitchLikeLogic() {
        System.out.println("4. Switch-Like Logic");
        System.out.println("====================");
        
        String dayOfWeek = "Wednesday";
        System.out.printf("Day: %s%n", dayOfWeek);
        
        // Switch-like logic using if-else
        if (dayOfWeek.equals("Monday")) {
            System.out.println("Start of the work week");
            System.out.println("Plan your week ahead");
        } else if (dayOfWeek.equals("Tuesday")) {
            System.out.println("Second day of the week");
            System.out.println("Focus on important tasks");
        } else if (dayOfWeek.equals("Wednesday")) {
            System.out.println("Middle of the week");
            System.out.println("Hump day - almost there!");
        } else if (dayOfWeek.equals("Thursday")) {
            System.out.println("Almost Friday");
            System.out.println("Wrap up your week");
        } else if (dayOfWeek.equals("Friday")) {
            System.out.println("TGIF - Thank Goodness It's Friday!");
            System.out.println("Weekend is coming");
        } else if (dayOfWeek.equals("Saturday") || dayOfWeek.equals("Sunday")) {
            System.out.println("Weekend!");
            System.out.println("Time to relax and recharge");
        } else {
            System.out.println("Invalid day of the week");
        }
        
        // Another example with numeric ranges
        int time = 14; // 24-hour format
        System.out.printf("Time: %d:00%n", time);
        
        if (time >= 6 && time < 12) {
            System.out.println("Good morning!");
        } else if (time >= 12 && time < 17) {
            System.out.println("Good afternoon!");
        } else if (time >= 17 && time < 22) {
            System.out.println("Good evening!");
        } else if (time >= 22 || time < 6) {
            System.out.println("Good night!");
        } else {
            System.out.println("Invalid time");
        }
        
        System.out.println();
    }
} 