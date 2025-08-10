/**
 * ArraysBasic.java
 * 
 * This program demonstrates fundamental array concepts in Java:
 * - Array declaration and initialization
 * - Array access and modification
 * - Common array operations
 * - Array traversal techniques
 * - Array utility methods
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
import java.util.Arrays;

public class ArraysBasic {
    
    public static void main(String[] args) {
        System.out.println("=== Arrays Basic Demo ===\n");
        
        // Demonstrate array declaration and initialization
        demonstrateArrayDeclaration();
        
        // Demonstrate array access and modification
        demonstrateArrayAccess();
        
        // Demonstrate array traversal
        demonstrateArrayTraversal();
        
        // Demonstrate common array operations
        demonstrateArrayOperations();
        
        // Interactive array example (hardcoded, no user input)
        hardcodedArrayExample();

        // Demonstrate a deck of cards
        demonstrateDeckOfCards();

        // Reverse a string 
        demonstrateReverseArray();

    // Demonstrate counting letters in a random char array
    demonstrateCountLettersInArray();

    // Demonstrate variable-length argument lists (varargs)
    demonstrateVarArgs();

    // Demonstrate java.util.Arrays utility methods
    demonstrateJavaUtilArrays();
        
        System.out.println("\n=== Demo Complete ===");
    }

    public static void demonstrateReverseArray() {
        System.out.println("7. REVERSE ARRAY EXAMPLE");
        System.out.println("==========================");

        int[] list1 = {1, 2, 3, 4, 5};
        int[] list2 = reverseArray(list1);

        System.out.printf("Original array: %s\n", java.util.Arrays.toString(list1));
        System.out.printf("Reversed array: %s\n", java.util.Arrays.toString(list2));
        System.out.println();
    }

    // Utility method to reverse an array
    public static int[] reverseArray(int[] list) {
        int[] reversed = new int[list.length];
        for (int i = 0; i < list.length; i++) {
            reversed[i] = list[list.length - 1 - i];
        }
        return reversed;
    }

    public static void demonstrateDeckOfCards() {
        System.out.println("6. DECK OF CARDS EXAMPLE");
        System.out.println("=========================");
        
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", 
                          "Jack", "Queen", "King", "Ace"};
        
        // Create a deck of cards
        String[] deck = new String[suits.length * ranks.length];
        int index = 0;
        for (String suit : suits) {
            for (String rank : ranks) {
                deck[index++] = rank + " of " + suit;
            }
        }
        
        // Display the deck
        System.out.printf("Deck of cards (%d cards):%n%s%n", deck.length, Arrays.toString(deck));

        // Shuffle the cards 
        // Shuffle the deck using Fisher-Yates algorithm
        System.out.println("\nShuffling the deck...");
        for (int i = deck.length - 1; i > 0; i--) {
            // Generate random index between 0 and i (inclusive)
            int j = (int) (Math.random() * (i + 1));
            
            // Swap cards at positions i and j
            String temp = deck[i];
            deck[i] = deck[j];
            deck[j] = temp;
        }

        // Display the shuffled deck
        System.out.printf("Shuffled deck:%n%s%n", Arrays.toString(deck));

        // Deal a few cards as an example
        System.out.println("\nDealing 5 cards:");
        for (int i = 0; i < 5; i++) {
            System.out.printf("Card %d: %s%n", i + 1, deck[i]);
        }



        
        System.out.println();
    }
    
    public static void demonstrateArrayDeclaration() {
        System.out.println("1. ARRAY DECLARATION AND INITIALIZATION");
        System.out.println("=======================================");
        
        // Different ways to declare arrays
        int[] numbers1 = new int[5];                    // Declaration with size
        int[] numbers2 = {10, 20, 30, 40, 50};         // Declaration with values
        int[] numbers3 = new int[]{1, 2, 3, 4, 5};     // Alternative syntax
        
        // Array of strings
        String[] names = {"Alice", "Bob", "Carol", "David"};
        
        // Array of doubles
        double[] prices = new double[3];
        prices[0] = 12.99;
        prices[1] = 25.50;
        prices[2] = 8.75;
        
        // Display array information
        System.out.printf("numbers1 length: %d%n", numbers1.length);
        System.out.printf("numbers2: %s%n", Arrays.toString(numbers2));
        System.out.printf("numbers3: %s%n", Arrays.toString(numbers3));
        System.out.printf("names: %s%n", Arrays.toString(names));
        System.out.printf("prices: %s%n", Arrays.toString(prices));
        
        // Array of characters
        char[] vowels = {'a', 'e', 'i', 'o', 'u'};
        System.out.printf("vowels: %s%n", Arrays.toString(vowels));
        
        // Boolean array
        boolean[] flags = new boolean[4]; // Default: all false
        flags[1] = true;
        flags[3] = true;
        System.out.printf("flags: %s%n", Arrays.toString(flags));
        
        System.out.println();
    }
    
    public static void demonstrateArrayAccess() {
        System.out.println("2. ARRAY ACCESS AND MODIFICATION");
        System.out.println("================================");
        
        int[] scores = {85, 92, 78, 96, 88};
        System.out.printf("Original scores: %s%n", Arrays.toString(scores));
        
        // Access individual elements
        System.out.printf("First score: %d%n", scores[0]);
        System.out.printf("Last score: %d%n", scores[scores.length - 1]);
        System.out.printf("Middle score: %d%n", scores[scores.length / 2]);
        
        // Modify elements
        scores[2] = 82; // Change third element
        System.out.printf("After modifying third score: %s%n", Arrays.toString(scores));
        
        // Bounds checking demonstration
        System.out.println("\nArray bounds information:");
        System.out.printf("Valid indices: 0 to %d%n", scores.length - 1);
        System.out.printf("Array length: %d%n", scores.length);
        
        // Safe array access method
        int index = 10;
        int value = getArrayElementSafely(scores, index);
        if (value != -1) {
            System.out.printf("scores[%d] = %d%n", index, value);
        } else {
            System.out.printf("Index %d is out of bounds%n", index);
        }
        
        System.out.println();
    }
    
    public static void demonstrateArrayTraversal() {
        System.out.println("3. ARRAY TRAVERSAL TECHNIQUES");
        System.out.println("=============================");
        
        String[] subjects = {"Math", "Science", "English", "History", "Art"};
        
        // Traditional for loop
        System.out.println("Using traditional for loop:");
        for (int i = 0; i < subjects.length; i++) {
            System.out.printf("  %d: %s%n", i + 1, subjects[i]);
        }
        
        // Enhanced for loop (for-each)
        System.out.println("\nUsing enhanced for loop:");
        for (String subject : subjects) {
            System.out.println("  Subject: " + subject);
        }
        
        // Reverse traversal
        System.out.println("\nReverse order:");
        for (int i = subjects.length - 1; i >= 0; i--) {
            System.out.printf("  %s", subjects[i]);
            if (i > 0) System.out.print(" -> ");
        }
        System.out.println();
        
        // While loop traversal
        System.out.println("\nUsing while loop:");
        int index = 0;
        while (index < subjects.length) {
            System.out.printf("  Position %d: %s%n", index, subjects[index]);
            index++;
        }
        
        System.out.println();
    }
    
    public static void demonstrateArrayOperations() {
        System.out.println("4. COMMON ARRAY OPERATIONS");
        System.out.println("==========================");
        
        int[] numbers = {64, 34, 25, 12, 22, 11, 90};
        System.out.printf("Original array: %s%n", Arrays.toString(numbers));
        
        // Find maximum and minimum
        int max = findMaximum(numbers);
        int min = findMinimum(numbers);
        System.out.printf("Maximum: %d%n", max);
        System.out.printf("Minimum: %d%n", min);
        
        // Calculate sum and average
        int sum = calculateSum(numbers);
        double average = calculateAverage(numbers);
        System.out.printf("Sum: %d%n", sum);
        System.out.printf("Average: %.2f%n", average);
        
        // Search for an element
        int searchValue = 25;
        int position = linearSearch(numbers, searchValue);
        if (position != -1) {
            System.out.printf("Found %d at index %d%n", searchValue, position);
        } else {
            System.out.printf("%d not found in array%n", searchValue);
        }
        
        // Count occurrences
        int[] duplicates = {1, 2, 3, 2, 4, 2, 5};
        int countValue = 2;
        int count = countOccurrences(duplicates, countValue);
        System.out.printf("Number %d appears %d times in %s%n", 
                         countValue, count, Arrays.toString(duplicates));
        
        // Copy array
        int[] copy = Arrays.copyOf(numbers, numbers.length);
        System.out.printf("Copied array: %s%n", Arrays.toString(copy));
        
        // Sort array (creates new sorted copy)
        int[] sorted = Arrays.copyOf(numbers, numbers.length);
        Arrays.sort(sorted);
        System.out.printf("Sorted array: %s%n", Arrays.toString(sorted));
        System.out.printf("Original unchanged: %s%n", Arrays.toString(numbers));
        
        System.out.println();
    }
    
    // Hardcoded array example (no user input)
    public static void hardcodedArrayExample() {
        System.out.println("5. HARDCODED ARRAY EXAMPLE");
        System.out.println("============================");
        int[] userArray = {7, 3, 9, 2, 5};
        System.out.printf("Hardcoded array: %s%n", Arrays.toString(userArray));
        System.out.printf("Sum: %d%n", calculateSum(userArray));
        System.out.printf("Average: %.2f%n", calculateAverage(userArray));
        System.out.printf("Maximum: %d%n", findMaximum(userArray));
        System.out.printf("Minimum: %d%n", findMinimum(userArray));
        int[] sortedArray = Arrays.copyOf(userArray, userArray.length);
        Arrays.sort(sortedArray);
        System.out.printf("Sorted: %s%n", Arrays.toString(sortedArray));
        System.out.println();
    }
    
    // Utility methods
    
    public static int getArrayElementSafely(int[] array, int index) {
        if (index >= 0 && index < array.length) {
            return array[index];
        }
        return -1; // Error indicator
    }
    
    public static int findMaximum(int[] array) {
        if (array.length == 0) return Integer.MIN_VALUE;
        
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }
    
    public static int findMinimum(int[] array) {
        if (array.length == 0) return Integer.MAX_VALUE;
        
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }
    
    public static int calculateSum(int[] array) {
        int sum = 0;
        for (int value : array) {
            sum += value;
        }
        return sum;
    }
    
    public static double calculateAverage(int[] array) {
        if (array.length == 0) return 0.0;
        return (double) calculateSum(array) / array.length;
    }
    
    public static int linearSearch(int[] array, int target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1; // Not found
    }
    
    public static int countOccurrences(int[] array, int target) {
        int count = 0;
        for (int value : array) {
            if (value == target) {
                count++;
            }
        }
        return count;
    }

    // 8. Count letters in an array demo (adapted from textbook Listing 7.4)
    public static void demonstrateCountLettersInArray() {
        System.out.println("8. COUNT LETTERS IN RANDOM CHAR ARRAY EXAMPLE");
        System.out.println("==============================================");

        // Create an array of random lowercase letters
        char[] chars = createRandomLowerCaseArray(100);

        System.out.println("The lowercase letters are:");
        displayCharArray(chars, 20); // 20 per line like sample

        // Count occurrences
        int[] counts = countLetters(chars);

        System.out.println();
        System.out.println("The occurrences of each letter are:");
        displayLetterCounts(counts, 10); // 10 pairs per line like sample
        System.out.println();
        System.out.println();
    }

    // Generate a random lowercase letter
    public static char getRandomLowerCaseLetter() {
        return (char)('a' + (int)(Math.random() * 26));
    }

    // Create an array filled with random lowercase letters
    public static char[] createRandomLowerCaseArray(int size) {
        char[] chars = new char[size];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = getRandomLowerCaseLetter();
        }
        return chars;
    }

    // Display characters with a specified number per line
    public static void displayCharArray(char[] chars, int perLine) {
        for (int i = 0; i < chars.length; i++) {
            if ((i + 1) % perLine == 0) {
                System.out.println(chars[i]);
            } else {
                System.out.print(chars[i] + " ");
            }
        }
    }

    // Count letter occurrences (assumes lowercase a-z)
    public static int[] countLetters(char[] chars) {
        int[] counts = new int[26];
        for (char c : chars) {
            if (c >= 'a' && c <= 'z') {
                counts[c - 'a']++;
            }
        }
        return counts;
    }

    // Display counts with given number of entries per line
    public static void displayLetterCounts(int[] counts, int perLine) {
        for (int i = 0; i < counts.length; i++) {
            String entry = counts[i] + " " + (char)('a' + i);
            if ((i + 1) % perLine == 0) {
                System.out.println(entry);
            } else {
                System.out.print(entry + " ");
            }
        }
    }

    // 9. Variable-length argument list (varargs) demo (textbook Listing 7.5 inspired)
    public static void demonstrateVarArgs() {
        System.out.println("9. VARIABLE-LENGTH ARGUMENT LIST (VARARGS) EXAMPLE");
        System.out.println("==================================================");

        // Call printMax with a variable-length list of arguments
        printMax(34, 3, 3, 2, 56.5);

        // Call printMax with an explicit array
        printMax(new double[]{1, 2, 3});

        // Additional examples showing flexibility
        printMax(); // No argument case
        printMax(10); // Single value
        printMax(5, -2, 99.9, 12.3, 99.8); // Multiple values

        System.out.println();
    }

    // Prints the maximum in a variable-length list (or array) of numbers
    public static void printMax(double... numbers) {
        if (numbers.length == 0) {
            System.out.println("No argument passed");
            return;
        }

        double result = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] > result) {
                result = numbers[i];
            }
        }
        System.out.println("The max value is " + result);
    }

    // 10. java.util.Arrays utility methods demo
    public static void demonstrateJavaUtilArrays() {
        System.out.println("10. java.util.Arrays UTILITY METHODS EXAMPLE");
        System.out.println("================================================");

        int[] data = {42, 7, 19, -3, 19, 0, 5, 100, 7};
        System.out.printf("Original data: %s%n", Arrays.toString(data));

        // sort(): in-place ascending sort (dual-pivot quicksort / TimSort depending on type)
        int[] sorted = Arrays.copyOf(data, data.length);
        Arrays.sort(sorted); // must be sorted before binarySearch
        System.out.printf("Sorted (Arrays.sort): %s%n", Arrays.toString(sorted));

        // parallelSort(): may use multiple threads for large arrays (threshold ~ 8k elements; here just demonstration)
        int[] parallelSorted = Arrays.copyOf(data, data.length);
        Arrays.parallelSort(parallelSorted);
        System.out.printf("Sorted (Arrays.parallelSort): %s%n", Arrays.toString(parallelSorted));

        // equals(): element-wise comparison, same length & all elements equal in order
        System.out.printf("sorted equals parallelSorted? %b%n", Arrays.equals(sorted, parallelSorted));

        // Modify one element then compare again
        parallelSorted[0] = Integer.MIN_VALUE; // force inequality
        System.out.printf("After change, sorted equals modified parallelSorted? %b%n", Arrays.equals(sorted, parallelSorted));

        // binarySearch(): only reliable on a sorted array (ascending order). Returns index or (-(insertionPoint) - 1) if not found.
        int targetPresent = 19;
        int targetMissing = 55;
        int foundIndex = Arrays.binarySearch(sorted, targetPresent);
        int missingIndex = Arrays.binarySearch(sorted, targetMissing);
        System.out.printf("binarySearch(sorted, %d) => index %d (value at index: %d)\n", targetPresent, foundIndex, sorted[foundIndex]);
        System.out.printf("binarySearch(sorted, %d) => %d (negative means not found; insertion point = %d)\n",
                targetMissing, missingIndex, -missingIndex - 1);

        // fill(): set all elements to a single value
        int[] filled = new int[8];
        Arrays.fill(filled, 3);
        System.out.printf("Filled array (all 3): %s%n", Arrays.toString(filled));

        // Partial fill (from index inclusive to toIndex exclusive)
        Arrays.fill(filled, 2, 6, 9);
        System.out.printf("After partial fill (indices 2..5 set to 9): %s%n", Arrays.toString(filled));

        // NOTE: For objects, Arrays.fill assigns the same reference (be mindful of mutability)

        System.out.println();
    }
}
