/**
 * ArrayListBasic.java
 * 
 * This program demonstrates fundamental ArrayList concepts in Java:
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
public class ArrayListBasic {
    
    public static void main(String[] args) {
        System.out.println("=== ArrayList Basic Operations ===\n");
        
        // Demonstrate basic ArrayList operations
        basicOperations();
        
        // Show ArrayList with different data types
        differentDataTypes();
        
        // Demonstrate ArrayList methods
        arrayListMethods();
        
        // Show ArrayList vs Arrays
        arrayListVsArrays();
        
        // Demonstrate sorting and searching
        sortingAndSearching();
        
        // Show practical examples
        practicalExamples();
    }
    
    /**
     * Demonstrates basic ArrayList creation and operations
     * Python equivalent: my_list = []
     */
    public static void basicOperations() {
        System.out.println("1. Basic ArrayList Operations:");
        System.out.println("=============================");
        
        // Create an empty ArrayList (Python: my_list = [])
        ArrayList<String> fruits = new ArrayList<>();
        System.out.println("Created empty ArrayList: " + fruits);
        
        // Add elements (Python: my_list.append("apple"))
        fruits.add("apple");
        fruits.add("banana");
        fruits.add("orange");
        System.out.println("After adding elements: " + fruits);
        
        // Get size (Python: len(my_list))
        System.out.println("Size of ArrayList: " + fruits.size());
        
        // Access element by index (Python: my_list[0])
        System.out.println("First element: " + fruits.get(0));
        
        // Check if element exists (Python: "apple" in my_list)
        System.out.println("Contains 'apple': " + fruits.contains("apple"));
        System.out.println("Contains 'grape': " + fruits.contains("grape"));
        
        // Remove element by value (Python: my_list.remove("banana"))
        fruits.remove("banana");
        System.out.println("After removing 'banana': " + fruits);
        
        // Remove element by index (Python: my_list.pop(0))
        String removed = fruits.remove(0);
        System.out.println("Removed element: " + removed);
        System.out.println("After removing first element: " + fruits);
        
        // Clear all elements (Python: my_list.clear())
        fruits.clear();
        System.out.println("After clearing: " + fruits);
        System.out.println("Is empty: " + fruits.isEmpty());
        System.out.println();
    }
    
    /**
     * Shows ArrayList with different data types
     * Python equivalent: my_list = [1, "hello", 3.14, True]
     */
    public static void differentDataTypes() {
        System.out.println("2. ArrayList with Different Data Types:");
        System.out.println("=====================================");
        
        // ArrayList of Integers (Python: numbers = [1, 2, 3, 4, 5])
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        System.out.println("Integer ArrayList: " + numbers);
        
        // ArrayList of Doubles (Python: decimals = [1.1, 2.2, 3.3])
        ArrayList<Double> decimals = new ArrayList<>();
        decimals.add(1.1);
        decimals.add(2.2);
        decimals.add(3.3);
        System.out.println("Double ArrayList: " + decimals);
        
        // ArrayList of Booleans (Python: flags = [True, False, True])
        ArrayList<Boolean> flags = new ArrayList<>();
        flags.add(true);
        flags.add(false);
        flags.add(true);
        System.out.println("Boolean ArrayList: " + flags);
        
        // ArrayList of Characters (Python: chars = ['a', 'b', 'c'])
        ArrayList<Character> chars = new ArrayList<>();
        chars.add('a');
        chars.add('b');
        chars.add('c');
        System.out.println("Character ArrayList: " + chars);
        System.out.println();
    }
    
    /**
     * Demonstrates various ArrayList methods
     * Shows the difference between Python list methods and Java ArrayList methods
     */
    public static void arrayListMethods() {
        System.out.println("3. ArrayList Methods:");
        System.out.println("====================");
        
        ArrayList<String> colors = new ArrayList<>();
        
        // Adding elements
        colors.add("red");           // Python: colors.append("red")
        colors.add("green");
        colors.add("blue");
        colors.add(1, "yellow");     // Python: colors.insert(1, "yellow")
        System.out.println("After adding elements: " + colors);
        
        // Setting element at index (Python: colors[0] = "purple")
        colors.set(0, "purple");
        System.out.println("After setting first element: " + colors);
        
        // Getting index of element (Python: colors.index("green"))
        int greenIndex = colors.indexOf("green");
        System.out.println("Index of 'green': " + greenIndex);
        
        // Getting last index of element (Python: colors.rindex("blue"))
        colors.add("blue");  // Add another blue
        int lastBlueIndex = colors.lastIndexOf("blue");
        System.out.println("Last index of 'blue': " + lastBlueIndex);
        
        // Sublist (Python: colors[1:3])
        List<String> sublist = colors.subList(1, 3);
        System.out.println("Sublist (1:3): " + sublist);
        
        // Check if empty (Python: len(colors) == 0)
        System.out.println("Is empty: " + colors.isEmpty());
        
        // Convert to array (Python: list(colors))
        String[] colorArray = colors.toArray(new String[0]);
        System.out.println("As array: " + Arrays.toString(colorArray));
        System.out.println();
    }
    
    /**
     * Compares ArrayList with regular arrays
     * Shows when to use ArrayList vs arrays
     */
    public static void arrayListVsArrays() {
        System.out.println("4. ArrayList vs Arrays:");
        System.out.println("=======================");
        
        // Regular array (fixed size)
        String[] array = new String[3];
        array[0] = "first";
        array[1] = "second";
        array[2] = "third";
        System.out.println("Regular array: " + Arrays.toString(array));
        System.out.println("Array length: " + array.length);
        
        // ArrayList (dynamic size)
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("first");
        arrayList.add("second");
        arrayList.add("third");
        arrayList.add("fourth");  // Can add more elements
        System.out.println("ArrayList: " + arrayList);
        System.out.println("ArrayList size: " + arrayList.size());
        
        // Converting between array and ArrayList
        String[] arrayFromList = arrayList.toArray(new String[0]);
        System.out.println("Converted to array: " + Arrays.toString(arrayFromList));
        
        ArrayList<String> listFromArray = new ArrayList<>(Arrays.asList(array));
        System.out.println("Converted to ArrayList: " + listFromArray);
        System.out.println();
    }
    
    /**
     * Demonstrates sorting and searching operations
     * Python equivalent: my_list.sort() and my_list.index(item)
     */
    public static void sortingAndSearching() {
        System.out.println("5. Sorting and Searching:");
        System.out.println("========================");
        
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(5);
        numbers.add(2);
        numbers.add(8);
        numbers.add(1);
        numbers.add(9);
        System.out.println("Original list: " + numbers);
        
        // Sort in ascending order (Python: numbers.sort())
        Collections.sort(numbers);
        System.out.println("Sorted (ascending): " + numbers);
        
        // Sort in descending order (Python: numbers.sort(reverse=True))
        Collections.sort(numbers, Collections.reverseOrder());
        System.out.println("Sorted (descending): " + numbers);
        
        // Binary search (requires sorted list)
        Collections.sort(numbers);  // Sort again for binary search
        int searchValue = 8;
        int index = Collections.binarySearch(numbers, searchValue);
        System.out.println("Binary search for " + searchValue + ": index " + index);
        
        // Find minimum and maximum (Python: min(numbers), max(numbers))
        int min = Collections.min(numbers);
        int max = Collections.max(numbers);
        System.out.println("Minimum: " + min + ", Maximum: " + max);
        System.out.println();
    }
    
    /**
     * Shows practical examples of ArrayList usage
     * Real-world scenarios where ArrayList is useful
     */
    public static void practicalExamples() {
        System.out.println("6. Practical Examples:");
        System.out.println("=====================");
        
        // Example 1: Student grades
        ArrayList<Double> grades = new ArrayList<>();
        grades.add(85.5);
        grades.add(92.0);
        grades.add(78.5);
        grades.add(95.0);
        grades.add(88.5);
        
        System.out.println("Student grades: " + grades);
        
        // Calculate average
        double sum = 0;
        for (Double grade : grades) {
            sum += grade;
        }
        double average = sum / grades.size();
        System.out.println("Average grade: " + average);
        
        // Find highest grade
        double highest = Collections.max(grades);
        System.out.println("Highest grade: " + highest);
        
        // Example 2: Shopping list
        ArrayList<String> shoppingList = new ArrayList<>();
        shoppingList.add("milk");
        shoppingList.add("bread");
        shoppingList.add("eggs");
        shoppingList.add("butter");
        
        System.out.println("\nShopping list: " + shoppingList);
        
        // Mark item as purchased (remove from list)
        shoppingList.remove("milk");
        System.out.println("After buying milk: " + shoppingList);
        
        // Add new item
        shoppingList.add("cheese");
        System.out.println("After adding cheese: " + shoppingList);
        
        // Check if we need to buy bread
        if (shoppingList.contains("bread")) {
            System.out.println("Still need to buy bread");
        } else {
            System.out.println("Bread is already purchased");
        }
        
        // Example 3: Number processing
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            numbers.add(i);
        }
        System.out.println("\nNumbers 1-10: " + numbers);
        
        // Remove even numbers
        numbers.removeIf(n -> n % 2 == 0);  // Python: numbers = [n for n in numbers if n % 2 != 0]
        System.out.println("Odd numbers only: " + numbers);
        
        // Double each number
        for (int i = 0; i < numbers.size(); i++) {
            numbers.set(i, numbers.get(i) * 2);
        }
        System.out.println("Doubled numbers: " + numbers);
        System.out.println();
    }
} 