package com.acu.javafx.generics;

import javafx.scene.control.TextArea;
import java.util.ArrayList;

/**
 * Demonstration of ArrayList with generics.
 * Shows how generics provide type safety for ArrayList operations.
 */
public class TestArrayListNew {
    
    /**
     * Runs the ArrayList with generics demonstration and outputs results to the provided TextArea.
     * @param outputArea the TextArea to display the output
     */
    public static void runDemo(TextArea outputArea) {
        StringBuilder output = new StringBuilder();
        
        output.append("=== ArrayList with Generics Demonstration ===\n\n");
        
        // Create ArrayList with generics
        output.append("1. Creating ArrayList<String>:\n");
        ArrayList<String> cityList = new ArrayList<>();
        
        // Add elements
        cityList.add("London");
        cityList.add("Denver");
        cityList.add("Paris");
        cityList.add("Miami");
        cityList.add("Seoul");
        cityList.add("Tokyo");
        
        output.append("Initial list: ").append(cityList).append("\n");
        output.append("List size: ").append(cityList.size()).append("\n\n");
        
        // Demonstrate type safety
        output.append("2. Type Safety Demonstration:\n");
        output.append("Adding elements (all OK):\n");
        cityList.add("New York");
        cityList.add("Sydney");
        output.append("After adding: ").append(cityList).append("\n\n");
        
        output.append("3. Retrieving elements (no casting needed):\n");
        output.append("First city: ").append(cityList.get(0)).append("\n");
        output.append("Last city: ").append(cityList.get(cityList.size() - 1)).append("\n");
        output.append("City at index 3: ").append(cityList.get(3)).append("\n\n");
        
        // Demonstrate iteration
        output.append("4. Iterating through the list:\n");
        for (int i = 0; i < cityList.size(); i++) {
            output.append("City ").append(i).append(": ").append(cityList.get(i)).append("\n");
        }
        output.append("\n");
        
        // Demonstrate enhanced for loop
        output.append("5. Enhanced for loop (no casting needed):\n");
        for (String city : cityList) {
            output.append("City: ").append(city).append("\n");
        }
        output.append("\n");
        
        // Demonstrate ArrayList with Integer
        output.append("6. ArrayList<Integer> demonstration:\n");
        ArrayList<Integer> numberList = new ArrayList<>();
        numberList.add(1);
        numberList.add(2);
        numberList.add(3);
        numberList.add(4);
        numberList.add(5);
        
        output.append("Number list: ").append(numberList).append("\n");
        
        // Calculate sum (no casting needed)
        int sum = 0;
        for (Integer number : numberList) {
            sum += number;
        }
        output.append("Sum of numbers: ").append(sum).append("\n\n");
        
        // Demonstrate ArrayList with Double
        output.append("7. ArrayList<Double> demonstration:\n");
        ArrayList<Double> doubleList = new ArrayList<>();
        doubleList.add(1.1);
        doubleList.add(2.2);
        doubleList.add(3.3);
        doubleList.add(4.4);
        
        output.append("Double list: ").append(doubleList).append("\n");
        
        // Calculate average (no casting needed)
        double total = 0.0;
        for (Double number : doubleList) {
            total += number;
        }
        double average = total / doubleList.size();
        output.append("Average: ").append(average).append("\n\n");
        
        // Demonstrate ArrayList with custom objects
        output.append("8. ArrayList with custom objects:\n");
        ArrayList<Person> personList = new ArrayList<>();
        personList.add(new Person("John", 25));
        personList.add(new Person("Jane", 30));
        personList.add(new Person("Bob", 35));
        
        output.append("Person list: ").append(personList).append("\n");
        
        // Access person properties (no casting needed)
        for (Person person : personList) {
            output.append("Person: ").append(person.getName()).append(", Age: ").append(person.getAge()).append("\n");
        }
        output.append("\n");
        
        // Demonstrate benefits over raw types
        output.append("=== Benefits of Generics over Raw Types ===\n");
        output.append("1. Type Safety: Compile-time error detection\n");
        output.append("2. No Casting: Direct access to elements\n");
        output.append("3. Better Performance: No runtime type checking\n");
        output.append("4. Code Clarity: Intent is clear from type parameter\n");
        output.append("5. IDE Support: Better autocomplete and error detection\n\n");
        
        // Demonstrate what happens without generics (raw types)
        output.append("=== Raw Types (Without Generics) ===\n");
        output.append("ArrayList list = new ArrayList(); // Raw type\n");
        output.append("list.add(\"String\");\n");
        output.append("list.add(42);\n");
        output.append("list.add(3.14);\n");
        output.append("// Requires casting when retrieving:\n");
        output.append("String s = (String) list.get(0);\n");
        output.append("Integer i = (Integer) list.get(1);\n");
        output.append("Double d = (Double) list.get(2);\n\n");
        
        output.append("=== Key Points ===\n");
        output.append("1. ArrayList<E> provides type safety\n");
        output.append("2. No explicit casting required when retrieving elements\n");
        output.append("3. Compile-time error detection for type mismatches\n");
        output.append("4. Better performance due to elimination of runtime checks\n");
        output.append("5. Code is more readable and maintainable\n");
        output.append("6. IDE provides better support with generics\n");
        
        outputArea.setText(output.toString());
    }
    
    /**
     * Simple Person class for demonstration.
     */
    private static class Person {
        private String name;
        private int age;
        
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
        
        public String getName() {
            return name;
        }
        
        public int getAge() {
            return age;
        }
        
        @Override
        public String toString() {
            return name + "(" + age + ")";
        }
    }
} 