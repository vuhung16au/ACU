/**
 * ForLoops.java
 * 
 * This program demonstrates for loops in Java:
 * - Basic for loop syntax
 * - Enhanced for-each loops
 * - Nested for loops
 * - Loop control statements
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */


public class ForLoops {
    
    public static void main(String[] args) {
        System.out.println("=== Java For Loops Demonstration ===\n");
        
        // Demonstrate different types of for loops
        demonstrateTraditionalForLoop();

        demonstrateEnhancedForLoop();
        
        demonstrateNestedForLoops();
        
        demonstrateForLoopWithArrays();
        
        demonstrateForLoopWithCollections();
        
        demonstrateForLoopWithBreakAndContinue();

        demonstrateGreatestCommonDivisor();

        demonstratePalindrome();

        demonstratePrimeNumber();

        System.out.println("\n=== For Loops Demonstration Complete ===");
    }
    

    public static void demonstratePrimeNumber() {

       System.out.println("10. Prime Number Check:");

       int num = 37;
       boolean isPrime = true;

        // Check for prime number
        if (num <= 1) {
            isPrime = false; // 0 and 1 are not prime numbers
        } else {
            for (int i = 2; i <= Math.sqrt(num); i++) {
                if (num % i == 0) {
                    isPrime = false; // Found a divisor, not prime
                    break;
                }
            }
        }
        
        if (isPrime) {
            System.out.println("   " + num + " is a prime number.");
        } else {
            System.out.println("   " + num + " is not a prime number.");
        }

        System.out.println();
    }

    public static void demonstratePalindrome() {

        System.out.println("9. Palindrome Check:");
        String str = "noon";
        String reversed = "";

        // Reverse the string
        for (int i = str.length() - 1; i >= 0; i--) {
            reversed += str.charAt(i);
        }

        // Check if palindrome
        if (str.equals(reversed)) {
            System.out.println("   \"" + str + "\" is a palindrome.");
        } else {
            System.out.println("   \"" + str + "\" is not a palindrome.");
        }

        System.out.println();
    }


    public static void demonstrateGreatestCommonDivisor() {

        System.out.println("8. Greatest Common Divisor (GCD):");
        
        int a = 20;
        int b = 15;
        int gcd = 1;
      
        // Find GCD using while loop 
        while (a != 0 && b != 0) {
            if (a > b) {
                a = a % b;
            } else {
                b = b % a;
            }
        }
        gcd = a + b; // GCD is the non-zero value left
        
        // Find GCD using a for loop
        // for (int i = 1; i <= a && i <= b; i++) {
        //     if (a % i == 0 && b % i == 0) {
        //         gcd = i;
        //     }
        // }
        
        System.out.println("   GCD of " + a + " and " + b + " is: " + gcd);

        System.out.println();
    }

    public static void demonstrateTraditionalForLoop() {
        System.out.println("1. Traditional For Loop:");
        System.out.println("   (Similar to Python's for i in range())");
        
        // Basic counting loop (0 to 4)
        System.out.println("   Counting from 0 to 4:");
        for (int i = 0; i < 5; i++) {
            System.out.println("   i = " + i);
        }
        
        // Counting with different step (2 to 10, step 2)
        System.out.println("\n   Counting from 2 to 10, step 2:");
        for (int i = 2; i <= 10; i += 2) {
            System.out.println("   i = " + i);
        }
        
        // Counting backwards (5 to 1)
        System.out.println("\n   Counting backwards from 5 to 1:");
        for (int i = 5; i >= 1; i--) {
            System.out.println("   i = " + i);
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates enhanced for loop (for-each loop)
     * In Python: for item in collection:
     * In Java: for (Type item : collection)
     */
    public static void demonstrateEnhancedForLoop() {
        System.out.println("2. Enhanced For Loop (For-Each):");
        System.out.println("   (Similar to Python's for item in collection)");
        
        // Array of strings
        String[] fruits = {"apple", "banana", "orange", "grape"};
        System.out.println("   Iterating over array of fruits:");

        // Looping through array of fruits
        for (String fruit : fruits) {
            System.out.println("   Fruit: " + fruit);
        }
        
        // Array of integers
        int[] numbers = {10, 20, 30, 40, 50};
        System.out.println("\n   Iterating over array of numbers:");
        for (int number : numbers) {
            System.out.println("   Number: " + number);
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates nested for loops
     * Useful for working with 2D arrays or nested structures
     */
    public static void demonstrateNestedForLoops() {
        System.out.println("3. Nested For Loops:");
        System.out.println("   (Useful for 2D arrays and nested structures)");
        
        // Simple nested loop - multiplication table
        System.out.println("   Multiplication table (1-3):");
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                System.out.printf("   %d x %d = %d", i, j, i * j);
                if (j < 3) System.out.print(" | ");
            }
            System.out.println();
        }
        
        // Pattern printing with nested loops
        System.out.println("\n   Pattern printing:");
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print("   *");
            }
            System.out.println();
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates for loops with arrays
     * Shows both traditional and enhanced for loops with arrays
     */
    public static void demonstrateForLoopWithArrays() {
        System.out.println("4. For Loops with Arrays:");
        
        // Create a sample array
        int[] scores = {85, 92, 78, 96, 88};
        
        // Traditional for loop with array
        System.out.println("   Traditional for loop with array:");
        for (int i = 0; i < scores.length; i++) {
            System.out.println("   Score[" + i + "] = " + scores[i]);
        }
        
        // Enhanced for loop with array
        System.out.println("\n   Enhanced for loop with array:");
        for (int score : scores) {
            System.out.println("   Score: " + score);
        }
        
        // Finding maximum value in array
        System.out.println("\n   Finding maximum score:");
        int maxScore = scores[0];
        for (int score : scores) {
            if (score > maxScore) {
                maxScore = score;
            }
        }
        System.out.println("   Maximum score: " + maxScore);
        
        System.out.println();
    }
    
    /**
     * Demonstrates for loops with collections
     * Shows how to iterate over different collection types
     */
    public static void demonstrateForLoopWithCollections() {
        System.out.println("5. For Loops with Collections:");
        
        // Using enhanced for loop with String array (most common collection)
        String[] names = {"Alice", "Bob", "Charlie", "Diana"};

        System.out.println("   Iterating over names:");
        for (String name : names) {
            System.out.println("   Hello, " + name + "!");
        }
        
        // Simulating a list-like structure with array
        System.out.println("\n   Processing a list of numbers:");

        int[] numbers = {1, 2, 3, 4, 5};

        int sum = 0;
        for (int num : numbers) {
            sum += num;
        }
        System.out.println("   Sum of numbers: " + sum);
        System.out.println("   Average: " + (double) sum / numbers.length);
        
        System.out.println();
    }
    
    /**
     * Demonstrates for loops with break and continue statements
     * Shows control flow within loops
     */
    public static void demonstrateForLoopWithBreakAndContinue() {
        System.out.println("6. For Loops with Break and Continue:");
        
        // Using break to exit loop early
        System.out.println("   Using break to find first number > 50:");

        int[] numbers = {10, 25, 35, 60, 45, 70};
        for (int num : numbers) {
            if (num > 50) {
                System.out.println("   Found first number > 50: " + num);
                break; // Exit the loop immediately
            }
        }
        
        // Using continue to skip iterations
        System.out.println("\n   Using continue to skip even numbers:");
        for (int i = 1; i <= 10; i++) {
            if (i % 2 == 0) {
                continue; // Skip even numbers
            }
            System.out.println("   Odd number: " + i);
        }
        
        System.out.println();
    }
} 