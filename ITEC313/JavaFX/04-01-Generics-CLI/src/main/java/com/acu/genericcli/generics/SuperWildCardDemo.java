package com.acu.genericcli.generics;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstration of the lower bounded wildcard (? super T).
 * Shows how to work with supertypes in a generic context.
 */
public class SuperWildCardDemo {
    
    /**
     * Runs the lower bounded wildcard demonstration and outputs results to console.
     */
    public static void runDemo() {
        System.out.println("=== Lower Bounded Wildcard (? super T) Demonstration ===\n");
        
        // Create lists of different types
        List<Number> numberList = new ArrayList<>();
        List<Object> objectList = new ArrayList<>();
        List<Integer> integerList = new ArrayList<>();
        
        System.out.println("Created lists:");
        System.out.println("Number list: " + numberList);
        System.out.println("Object list: " + objectList);
        System.out.println("Integer list: " + integerList + "\n");
        
        // Demonstrate adding elements
        System.out.println("=== Adding Elements with ? super T ===");
        
        System.out.println("1. Adding to List<? super Integer>:");
        addInteger(numberList, 10);
        addInteger(numberList, 20);
        addInteger(objectList, 30);
        addInteger(objectList, 40);
        
        System.out.println("After adding integers:");
        System.out.println("Number list: " + numberList);
        System.out.println("Object list: " + objectList + "\n");
        
        System.out.println("2. Adding to List<? super Number>:");
        addNumber(numberList, 3.14);
        addNumber(numberList, 2.718);
        addNumber(objectList, 1.414);
        
        System.out.println("After adding numbers:");
        System.out.println("Number list: " + numberList);
        System.out.println("Object list: " + objectList + "\n");
        
        // Demonstrate reading limitations
        System.out.println("=== Reading Limitations ===");
        System.out.println("When reading from ? super T, can only read as Object:");
        System.out.println("Object obj = list.get(0); // OK");
        System.out.println("Integer i = list.get(0); // Compile error");
        System.out.println("Number n = list.get(0); // Compile error\n");
        
        // Demonstrate practical usage
        System.out.println("=== Practical Usage Examples ===");
        
        System.out.println("1. Collection.addAll() method:");
        System.out.println("   public boolean addAll(Collection<? super E> c)");
        System.out.println("   - Allows adding elements to a collection of supertype\n");
        
        System.out.println("2. Comparator usage:");
        System.out.println("   public static <T> void sort(List<T> list, Comparator<? super T> c)");
        System.out.println("   - Allows using a comparator for T or its supertype\n");
        
        System.out.println("3. Consumer pattern:");
        System.out.println("   public void forEach(Consumer<? super T> action)");
        System.out.println("   - Allows consuming T or its supertype\n");
        
        // Demonstrate with actual method calls
        System.out.println("=== Method Demonstrations ===");
        
        // Demonstrate copy method
        List<Number> sourceNumbers = new ArrayList<>();
        sourceNumbers.add(1);
        sourceNumbers.add(2.5);
        sourceNumbers.add(3);
        
        List<Object> destObjects = new ArrayList<>();
        
        System.out.println("Source Number list: " + sourceNumbers);
        System.out.println("Destination Object list: " + destObjects);
        
        copyNumbers(sourceNumbers, destObjects);
        
        System.out.println("After copying: " + destObjects + "\n");
        
        // Demonstrate with different types
        System.out.println("=== Type Hierarchy Demonstration ===");
        
        List<Object> objectDest = new ArrayList<>();
        List<Number> numberDest = new ArrayList<>();
        
        System.out.println("Copying Integer list to different destinations:");
        copyIntegers(integerList, objectDest);  // Integer -> Object (OK)
        copyIntegers(integerList, numberDest);  // Integer -> Number (OK)
        
        System.out.println("Object destination: " + objectDest);
        System.out.println("Number destination: " + numberDest + "\n");
        
        System.out.println("=== Key Points ===");
        System.out.println("1. ? super T represents T or any supertype of T");
        System.out.println("2. Allows adding elements of type T");
        System.out.println("3. Can only read as Object");
        System.out.println("4. Useful for write operations (Producer extends, Consumer super)");
        System.out.println("5. Common in Java Collections Framework");
        System.out.println("6. Provides flexibility for adding elements safely");
    }
    
    /**
     * Adds an integer to a list that can hold Integer or its supertype.
     * @param list the list to add to
     * @param value the integer value to add
     */
    private static void addInteger(List<? super Integer> list, Integer value) {
        list.add(value);
    }
    
    /**
     * Adds a number to a list that can hold Number or its supertype.
     * @param list the list to add to
     * @param value the number value to add
     */
    private static void addNumber(List<? super Number> list, Number value) {
        list.add(value);
    }
    
    /**
     * Copies numbers from source to destination using lower bounded wildcard.
     * @param source the source list
     * @param dest the destination list
     */
    private static void copyNumbers(List<? extends Number> source, List<? super Number> dest) {
        for (Number number : source) {
            dest.add(number);
        }
    }
    
    /**
     * Copies integers from source to destination using lower bounded wildcard.
     * @param source the source list
     * @param dest the destination list
     */
    private static void copyIntegers(List<Integer> source, List<? super Integer> dest) {
        for (Integer integer : source) {
            dest.add(integer);
        }
    }
} 