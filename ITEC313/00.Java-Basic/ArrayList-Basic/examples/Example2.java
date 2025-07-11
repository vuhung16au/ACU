import java.util.ArrayList;
import java.util.Collections;

/**
 * Example 2: ArrayList with Different Data Types and Iteration
 * 
 * This example shows how to work with ArrayLists of different data types
 * and various ways to iterate through them.
 * 
 * Python equivalent concepts:
 * - numbers = [1, 2, 3, 4, 5] -> ArrayList<Integer> numbers = new ArrayList<>();
 * - for item in list: -> for (Type item : list)
 * - list.sort() -> Collections.sort(list)
 * - sum(list) -> manual calculation with loop
 */
public class Example2 {
    
    public static void main(String[] args) {
        System.out.println("=== Example 2: ArrayList with Different Data Types ===\n");
        
        // ArrayList of integers
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(10);
        numbers.add(5);
        numbers.add(15);
        numbers.add(3);
        numbers.add(8);
        
        System.out.println("Integer ArrayList: " + numbers);
        
        // Calculate sum (Python: sum(numbers))
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        System.out.println("Sum: " + sum);
        
        // Calculate average
        double average = (double) sum / numbers.size();
        System.out.println("Average: " + average);
        
        // Find maximum and minimum (Python: max(numbers), min(numbers))
        int max = Collections.max(numbers);
        int min = Collections.min(numbers);
        System.out.println("Maximum: " + max);
        System.out.println("Minimum: " + min);
        
        // Sort the list (Python: numbers.sort())
        Collections.sort(numbers);
        System.out.println("Sorted: " + numbers);
        
        // ArrayList of doubles
        ArrayList<Double> prices = new ArrayList<>();
        prices.add(19.99);
        prices.add(29.99);
        prices.add(15.50);
        prices.add(45.00);
        
        System.out.println("\nDouble ArrayList: " + prices);
        
        // Calculate total price
        double total = 0;
        for (Double price : prices) {
            total += price;
        }
        System.out.println("Total price: " + total);
        
        // Apply discount (10% off)
        ArrayList<Double> discountedPrices = new ArrayList<>();
        for (Double price : prices) {
            discountedPrices.add(price * 0.9);
        }
        System.out.println("Prices with 10% discount: " + discountedPrices);
        
        // ArrayList of strings
        ArrayList<String> words = new ArrayList<>();
        words.add("apple");
        words.add("banana");
        words.add("cherry");
        words.add("date");
        words.add("elderberry");
        
        System.out.println("\nString ArrayList: " + words);
        
        // Find words longer than 5 characters
        ArrayList<String> longWords = new ArrayList<>();
        for (String word : words) {
            if (word.length() > 5) {
                longWords.add(word);
            }
        }
        System.out.println("Words longer than 5 characters: " + longWords);
        
        // Convert all words to uppercase
        ArrayList<String> upperWords = new ArrayList<>();
        for (String word : words) {
            upperWords.add(word.toUpperCase());
        }
        System.out.println("Uppercase words: " + upperWords);
        
        // ArrayList of booleans
        ArrayList<Boolean> flags = new ArrayList<>();
        flags.add(true);
        flags.add(false);
        flags.add(true);
        flags.add(true);
        flags.add(false);
        
        System.out.println("\nBoolean ArrayList: " + flags);
        
        // Count true values
        int trueCount = 0;
        for (Boolean flag : flags) {
            if (flag) {
                trueCount++;
            }
        }
        System.out.println("Number of true values: " + trueCount);
        
        // Different iteration methods
        System.out.println("\n=== Different Iteration Methods ===");
        
        // Method 1: Traditional for loop with index
        System.out.println("Traditional for loop:");
        for (int i = 0; i < numbers.size(); i++) {
            System.out.println("Index " + i + ": " + numbers.get(i));
        }
        
        // Method 2: Enhanced for loop (for-each)
        System.out.println("\nEnhanced for loop:");
        for (int number : numbers) {
            System.out.println("Number: " + number);
        }
        
        // Method 3: Using forEach with lambda (Java 8+)
        System.out.println("\nLambda forEach:");
        numbers.forEach(number -> System.out.println("Number: " + number));
        
        // Method 4: Using iterator
        System.out.println("\nIterator:");
        var iterator = numbers.iterator();
        while (iterator.hasNext()) {
            System.out.println("Number: " + iterator.next());
        }
    }
} 