package com.acu.javafx.generics;

import javafx.scene.control.TextArea;
import java.util.ArrayList;
import java.util.List;

/**
 * Demonstration of the lower bounded wildcard (? super T).
 * Shows how to work with supertypes in a generic context.
 */
public class SuperWildCardDemo {
    
    /**
     * Runs the lower bounded wildcard demonstration and outputs results to the provided TextArea.
     * @param outputArea the TextArea to display the output
     */
    public static void runDemo(TextArea outputArea) {
        StringBuilder output = new StringBuilder();
        
        output.append("=== Lower Bounded Wildcard (? super T) Demonstration ===\n\n");
        
        // Create lists of different types
        List<Number> numberList = new ArrayList<>();
        List<Object> objectList = new ArrayList<>();
        List<Integer> integerList = new ArrayList<>();
        
        output.append("Created lists:\n");
        output.append("Number list: ").append(numberList).append("\n");
        output.append("Object list: ").append(objectList).append("\n");
        output.append("Integer list: ").append(integerList).append("\n\n");
        
        // Demonstrate adding elements
        output.append("=== Adding Elements with ? super T ===\n");
        
        output.append("1. Adding to List<? super Integer>:\n");
        addInteger(numberList, 10);
        addInteger(numberList, 20);
        addInteger(objectList, 30);
        addInteger(objectList, 40);
        
        output.append("After adding integers:\n");
        output.append("Number list: ").append(numberList).append("\n");
        output.append("Object list: ").append(objectList).append("\n\n");
        
        output.append("2. Adding to List<? super Number>:\n");
        addNumber(numberList, 3.14);
        addNumber(numberList, 2.718);
        addNumber(objectList, 1.414);
        
        output.append("After adding numbers:\n");
        output.append("Number list: ").append(numberList).append("\n");
        output.append("Object list: ").append(objectList).append("\n\n");
        
        // Demonstrate reading limitations
        output.append("=== Reading Limitations ===\n");
        output.append("When reading from ? super T, can only read as Object:\n");
        output.append("Object obj = list.get(0); // OK\n");
        output.append("Integer i = list.get(0); // Compile error\n");
        output.append("Number n = list.get(0); // Compile error\n\n");
        
        // Demonstrate practical usage
        output.append("=== Practical Usage Examples ===\n");
        
        output.append("1. Collection.addAll() method:\n");
        output.append("   public boolean addAll(Collection<? super E> c)\n");
        output.append("   - Allows adding elements to a collection of supertype\n\n");
        
        output.append("2. Comparator usage:\n");
        output.append("   public static <T> void sort(List<T> list, Comparator<? super T> c)\n");
        output.append("   - Allows using a comparator for T or its supertype\n\n");
        
        output.append("3. Consumer pattern:\n");
        output.append("   public void forEach(Consumer<? super T> action)\n");
        output.append("   - Allows consuming T or its supertype\n\n");
        
        // Demonstrate with actual method calls
        output.append("=== Method Demonstrations ===\n");
        
        // Demonstrate copy method
        List<Number> sourceNumbers = new ArrayList<>();
        sourceNumbers.add(1);
        sourceNumbers.add(2.5);
        sourceNumbers.add(3);
        
        List<Object> destObjects = new ArrayList<>();
        
        output.append("Source Number list: ").append(sourceNumbers).append("\n");
        output.append("Destination Object list: ").append(destObjects).append("\n");
        
        copyNumbers(sourceNumbers, destObjects);
        
        output.append("After copying: ").append(destObjects).append("\n\n");
        
        // Demonstrate with different types
        output.append("=== Type Hierarchy Demonstration ===\n");
        
        List<Object> objectDest = new ArrayList<>();
        List<Number> numberDest = new ArrayList<>();
        
        output.append("Copying Integer list to different destinations:\n");
        copyIntegers(integerList, objectDest);  // Integer -> Object (OK)
        copyIntegers(integerList, numberDest);  // Integer -> Number (OK)
        
        output.append("Object destination: ").append(objectDest).append("\n");
        output.append("Number destination: ").append(numberDest).append("\n\n");
        
        output.append("=== Key Points ===\n");
        output.append("1. ? super T represents T or any supertype of T\n");
        output.append("2. Allows adding elements of type T\n");
        output.append("3. Can only read as Object\n");
        output.append("4. Useful for write operations (Producer extends, Consumer super)\n");
        output.append("5. Common in Java Collections Framework\n");
        output.append("6. Provides flexibility for adding elements safely\n");
        
        outputArea.setText(output.toString());
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