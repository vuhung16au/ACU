import java.util.Scanner;
import java.util.Random;

/**
 * IfElseConditions.java
 * 
 * This program demonstrates if-else conditions in Java:
 * - Basic if statements
 * - If-else and else-if chains
 * - Nested if statements
 * - Conditional expressions
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
public class IfElseConditions {
    
    public static void main(String[] args) {
        System.out.println("=== If-Else Conditions Demo ===\n");
        
        // Demonstrate basic if-else
        demonstrateBasicIfElse();
        
        // Demonstrate nested conditions
        demonstrateNestedConditions();
        
        // Demonstrate logical operators
        demonstrateLogicalOperators();
        
        // Demonstrate ternary operator
        demonstrateTernaryOperator();
        
        // Demonstrate grade calculation with predefined scores
        demonstrateGradeCalculation();
        
        // Demonstrate age categorization
        demonstrateAgeCategorization();
        
        // Demonstrate complex business logic
        demonstrateBusinessLogic();

        // Demonstrate subtraction quiz
        demonstrateSubtractionQuiz();

        System.out.println("\n=== Demo Complete ===");
    }
    
    public static void demonstrateBasicIfElse() {
        System.out.println("1. BASIC IF-ELSE STATEMENTS");
        System.out.println("===========================");
        
        int temperature = 25;
        System.out.printf("Current temperature: %d°C%n", temperature);
        
        // Simple if-else
        if (temperature > 30) {
            System.out.println("It's hot outside!");
        } else if (temperature > 20) {
            System.out.println("Nice weather!");
        } else if (temperature > 10) {
            System.out.println("It's cool outside.");
        } else {
            System.out.println("It's cold outside!");
        }
        
        // Checking even/odd
        int number = 42;
        System.out.printf("\nChecking if %d is even or odd:%n", number);
        if (number % 2 == 0) {
            System.out.println(number + " is even");
        } else {
            System.out.println(number + " is odd");
        }
        
        System.out.println();
    }
    
    public static void demonstrateNestedConditions() {
        System.out.println("2. NESTED CONDITIONS");
        System.out.println("====================");
        
        int age = 20;
        boolean hasLicense = true;
        boolean hasInsurance = true;
        
        System.out.printf("Age: %d, Has License: %b, Has Insurance: %b%n", 
                         age, hasLicense, hasInsurance);
        
        if (age < 0) {
            System.out.println("Invalid age");
        } else if (age >= 18) {
            System.out.println("Age requirement met");
            if (hasLicense) {
                System.out.println("License requirement met");
                if (hasInsurance) {
                    System.out.println("✓ Can rent a car!");
                } else {
                    System.out.println("✗ Need insurance to rent a car");
                }
            } else {
                System.out.println("✗ Need a driver's license");
            }
        } else {
            System.out.println("✗ Must be 18 or older");
        }
        
        System.out.println();
    }
    
    public static void demonstrateLogicalOperators() {
        System.out.println("3. LOGICAL OPERATORS");
        System.out.println("====================");
        
        boolean isWeekend = true;
        boolean isHoliday = false;
        boolean hasWork = false;
        
        System.out.printf("Weekend: %b, Holiday: %b, Has Work: %b%n", 
                         isWeekend, isHoliday, hasWork);
        
        // AND operator
        if (isWeekend && !hasWork) {
            System.out.println("Time to relax!");
        }
        
        // OR operator
        if (isWeekend || isHoliday) {
            System.out.println("No need to rush today");
        }
        
        // Complex logical expression
        if ((isWeekend || isHoliday) && !hasWork) {
            System.out.println("Perfect day for leisure activities");
        }
        
        // NOT operator
        if (!hasWork) {
            System.out.println("Free from work obligations");
        }
        
        System.out.println();
    }
    
    public static void demonstrateTernaryOperator() {
        System.out.println("4. TERNARY OPERATOR");
        System.out.println("===================");
        
        int score = 85;
        
        // Basic ternary
        String result = (score >= 60) ? "Pass" : "Fail";
        System.out.printf("Score: %d -> Result: %s%n", score, result);
        
        // Nested ternary
        String grade = (score >= 90) ? "A" : 
                      (score >= 80) ? "B" : 
                      (score >= 70) ? "C" : 
                      (score >= 60) ? "D" : "F";
        System.out.printf("Grade: %s%n", grade);
        
        // Ternary with different return types
        int bonus = (score > 90) ? 1000 : 500;
        System.out.printf("Bonus: $%d%n", bonus);
        
        // Using ternary in method calls
        System.out.println("Performance: " + ((score >= 80) ? "Excellent" : "Needs Improvement"));
        
        System.out.println();
    }
    
    public static void demonstrateGradeCalculation() {
        System.out.println("5. GRADE CALCULATION EXAMPLES");
        System.out.println("=============================");
        
        // Test different scores
        int[] scores = {95, 87, 73, 65, 45};
        
        for (int score : scores) {
            System.out.printf("Score: %d%n", score);
            
            // Calculate grade
            char letterGrade;
            String description;
            
            if (score >= 90) {
                letterGrade = 'A';
                description = "Excellent";
            } else if (score >= 80) {
                letterGrade = 'B';
                description = "Good";
            } else if (score >= 70) {
                letterGrade = 'C';
                description = "Average";
            } else if (score >= 60) {
                letterGrade = 'D';
                description = "Below Average";
            } else {
                letterGrade = 'F';
                description = "Failing";
            }
            
            // Display results
            System.out.printf("Letter Grade: %c%n", letterGrade);
            System.out.printf("Description: %s%n", description);
            
            // Additional feedback
            if (score >= 60) {
                System.out.println("Congratulations! You passed!");
            } else {
                System.out.println("You need to retake the test.");
            }
            System.out.println();
        }
    }
    
    public static void demonstrateAgeCategorization() {
        System.out.println("6. AGE CATEGORY CLASSIFIER");
        System.out.println("==========================");
        
        // Test different ages
        int[] ages = {1, 8, 16, 25, 70};
        
        for (int age : ages) {
            System.out.printf("Age: %d%n", age);
            
            String category;
            String activities;
            
            if (age < 2) {
                category = "Infant";
                activities = "Sleeping, feeding, basic motor development";
            } else if (age < 13) {
                category = "Child";
                activities = "Playing, learning, school activities";
            } else if (age < 20) {
                category = "Teenager";
                activities = "High school, sports, socializing";
            } else if (age < 60) {
                category = "Adult";
                activities = "Career, family, responsibilities";
            } else {
                category = "Senior";
                activities = "Retirement, leisure, wisdom sharing";
            }
            
            System.out.printf("Category: %s%n", category);
            System.out.printf("Typical activities: %s%n", activities);
            
            // Special conditions
            if (age >= 18) {
                System.out.println("✓ Eligible to vote");
            }
            if (age >= 21) {
                System.out.println("✓ Legal drinking age (in some countries)");
            }
            if (age >= 65) {
                System.out.println("✓ Eligible for senior discounts");
            }
            System.out.println();
        }
    }
    
    public static void demonstrateBusinessLogic() {
        System.out.println("7. BUSINESS LOGIC EXAMPLES");
        System.out.println("===========================");
        
        // E-commerce discount logic
        double purchaseAmount = 150.0;
        boolean isVIP = true;
        boolean isFirstTime = false;
        
        System.out.printf("Purchase Amount: $%.2f%n", purchaseAmount);
        System.out.printf("VIP Customer: %b%n", isVIP);
        System.out.printf("First Time Customer: %b%n", isFirstTime);
        
        double discount = 0.0;
        
        // Complex discount calculation
        if (purchaseAmount >= 200) {
            discount = 0.20; // 20% discount for large purchases
        } else if (purchaseAmount >= 100) {
            discount = 0.10; // 10% discount for medium purchases
        } else if (purchaseAmount >= 50) {
            discount = 0.05; // 5% discount for small purchases
        }
        
        // Additional VIP discount
        if (isVIP) {
            discount += 0.05; // Extra 5% for VIP
        }
        
        // First-time customer bonus
        if (isFirstTime) {
            discount += 0.03; // Extra 3% for first-time customers
        }
        
        // Cap discount at 30%
        if (discount > 0.30) {
            discount = 0.30;
        }
        
        double finalPrice = purchaseAmount * (1 - discount);
        
        System.out.printf("Discount Rate: %.1f%%%n", discount * 100);
        System.out.printf("Final Price: $%.2f%n", finalPrice);
        System.out.printf("Total Savings: $%.2f%n", purchaseAmount - finalPrice);
        
        System.out.println();
    }

    public static void demonstrateSubtractionQuiz() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Generate two random numbers
        int num1 = random.nextInt(100);
        int num2 = random.nextInt(100);
        int correctAnswer = num1 - num2;

        // Ask the user for their answer
        System.out.printf("What is %d - %d?%n", num1, num2);
        int userAnswer = scanner.nextInt();

        // Check the user's answer
        if (userAnswer == correctAnswer) {
            System.out.println("Correct!");
        } else {
            System.out.printf("Incorrect. The correct answer is %d.%n", correctAnswer);
        }

        // close scanner
        scanner.close();
    }
}
