/**
 * Example 1: Basic If-Else Conditions
 * 
 * This example demonstrates fundamental if-else statements in Java.
 * Shows simple conditional logic for decision making.
 * 
 * Course: ITEC313 - Object-Oriented Programming
 * Institution: Australian Catholic University (ACU)
 */

public class Example1 {
    
    public static void main(String[] args) {
        System.out.println("=== Example 1: Basic If-Else Conditions ===\n");
        
        // Example 1: Simple number comparison
        demonstrateNumberComparison();
        
        // Example 2: String comparison
        demonstrateStringComparison();
        
        // Example 3: Boolean logic
        demonstrateBooleanLogic();
        
        System.out.println("=== Example 1 Complete ===");
    }
    
    /**
     * Demonstrates basic number comparison using if-else
     * In Python: if number > 10: print("Large") else: print("Small")
     */
    public static void demonstrateNumberComparison() {
        System.out.println("1. Number Comparison");
        System.out.println("===================");
        
        int number = 15;
        System.out.printf("Number: %d%n", number);
        
        // Simple if-else
        if (number > 10) {
            System.out.println("The number is greater than 10");
        } else {
            System.out.println("The number is 10 or less");
        }
        
        // Multiple conditions with else-if
        if (number > 20) {
            System.out.println("The number is very large");
        } else if (number > 10) {
            System.out.println("The number is moderately large");
        } else if (number > 0) {
            System.out.println("The number is small but positive");
        } else {
            System.out.println("The number is zero or negative");
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates string comparison using if-else
     * In Python: if name == "Alice": print("Hello Alice")
     */
    public static void demonstrateStringComparison() {
        System.out.println("2. String Comparison");
        System.out.println("===================");
        
        String name = "Alice";
        System.out.printf("Name: %s%n", name);
        
        // String equality check
        if (name.equals("Alice")) {
            System.out.println("Hello Alice!");
        } else if (name.equals("Bob")) {
            System.out.println("Hello Bob!");
        } else {
            System.out.println("Hello stranger!");
        }
        
        // String length check
        if (name.length() > 5) {
            System.out.println("That's a long name");
        } else if (name.length() > 3) {
            System.out.println("That's a medium name");
        } else {
            System.out.println("That's a short name");
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates boolean logic with if-else
     * In Python: if is_raining and has_umbrella: print("Stay dry")
     */
    public static void demonstrateBooleanLogic() {
        System.out.println("3. Boolean Logic");
        System.out.println("================");
        
        boolean isRaining = true;
        boolean hasUmbrella = false;
        boolean hasCoat = true;
        
        System.out.printf("Raining: %b, Has Umbrella: %b, Has Coat: %b%n", 
                         isRaining, hasUmbrella, hasCoat);
        
        // Simple boolean check
        if (isRaining) {
            System.out.println("It's raining today");
        } else {
            System.out.println("It's not raining today");
        }
        
        // AND operator
        if (isRaining && hasUmbrella) {
            System.out.println("You can stay dry with your umbrella");
        } else if (isRaining && hasCoat) {
            System.out.println("Your coat will help, but you might get wet");
        } else if (isRaining) {
            System.out.println("You'll get wet without protection");
        } else {
            System.out.println("No need for rain protection");
        }
        
        // OR operator
        if (hasUmbrella || hasCoat) {
            System.out.println("You have some protection from the weather");
        } else {
            System.out.println("You have no weather protection");
        }
        
        System.out.println();
    }
} 