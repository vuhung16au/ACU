package com.acu.genericcli.generics;

import java.util.ArrayList;

/**
 * Demonstration of ArrayList with generics.
 * Shows how generics provide type safety for ArrayList operations.
 */
public class TestArrayListNew {
    
    /**
     * Runs the ArrayList with generics demonstration and outputs results to console.
     */
    public static void runDemo() {
        System.out.println("=== ArrayList with Generics Demonstration ===\n");
        
        // Create ArrayList with generics
        System.out.println("1. Creating ArrayList<String>:");
        ArrayList<String> cityList = new ArrayList<>();
        
        // Add elements
        cityList.add("London");
        cityList.add("Denver");
        cityList.add("Paris");
        cityList.add("Miami");
        cityList.add("Seoul");
        cityList.add("Tokyo");
        
        System.out.println("Initial list: " + cityList);
        System.out.println("List size: " + cityList.size() + "\n");
        
        // Demonstrate type safety
        System.out.println("2. Type Safety Demonstration:");
        System.out.println("Adding elements (all OK):");
        cityList.add("New York");
        cityList.add("Sydney");
        System.out.println("After adding: " + cityList + "\n");
        
        System.out.println("3. Retrieving elements (no casting needed):");
        System.out.println("First city: " + cityList.get(0));
        System.out.println("Last city: " + cityList.get(cityList.size() - 1));
        System.out.println("City at index 3: " + cityList.get(3) + "\n");
        
        // Demonstrate iteration
        System.out.println("4. Iterating through the list:");
        for (int i = 0; i < cityList.size(); i++) {
            System.out.println("City " + i + ": " + cityList.get(i));
        }
        System.out.println();
        
        // Demonstrate enhanced for loop
        System.out.println("5. Enhanced for loop (no casting needed):");
        for (String city : cityList) {
            System.out.println("City: " + city);
        }
        System.out.println();
        
        // Demonstrate ArrayList with Integer
        System.out.println("6. ArrayList<Integer> demonstration:");
        ArrayList<Integer> numberList = new ArrayList<>();
        numberList.add(1);
        numberList.add(2);
        numberList.add(3);
        numberList.add(4);
        numberList.add(5);
        
        System.out.println("Number list: " + numberList);
        
        // Calculate sum (no casting needed)
        int sum = 0;
        for (Integer number : numberList) {
            sum += number;
        }
        System.out.println("Sum of numbers: " + sum + "\n");
        
        // Demonstrate ArrayList with Double
        System.out.println("7. ArrayList<Double> demonstration:");
        ArrayList<Double> doubleList = new ArrayList<>();
        doubleList.add(1.1);
        doubleList.add(2.2);
        doubleList.add(3.3);
        doubleList.add(4.4);
        
        System.out.println("Double list: " + doubleList);
        
        // Calculate average (no casting needed)
        double total = 0.0;
        for (Double number : doubleList) {
            total += number;
        }
        double average = total / doubleList.size();
        System.out.println("Average: " + average + "\n");
        
        // Demonstrate ArrayList with custom objects
        System.out.println("8. ArrayList with custom objects:");
        ArrayList<Person> personList = new ArrayList<>();
        personList.add(new Person("John", 25));
        personList.add(new Person("Jane", 30));
        personList.add(new Person("Bob", 35));
        
        System.out.println("Person list: " + personList);
        
        // Access person properties (no casting needed)
        for (Person person : personList) {
            System.out.println("Person: " + person.getName() + ", Age: " + person.getAge());
        }
        System.out.println();
        
        // Demonstrate benefits over raw types
        System.out.println("=== Benefits of Generics over Raw Types ===");
        System.out.println("1. Type Safety: Compile-time error detection");
        System.out.println("2. No Casting: Direct access to elements");
        System.out.println("3. Better Performance: No runtime type checking");
        System.out.println("4. Code Clarity: Intent is clear from type parameter");
        System.out.println("5. IDE Support: Better autocomplete and error detection\n");
        
        // Demonstrate what happens without generics (raw types)
        System.out.println("=== Raw Types (Without Generics) ===");
        System.out.println("ArrayList list = new ArrayList(); // Raw type");
        System.out.println("list.add(\"String\");");
        System.out.println("list.add(42);");
        System.out.println("list.add(3.14);");
        System.out.println("// Requires casting when retrieving:");
        System.out.println("String s = (String) list.get(0);");
        System.out.println("Integer i = (Integer) list.get(1);");
        System.out.println("Double d = (Double) list.get(2);\n");
        
        System.out.println("=== Key Points ===");
        System.out.println("1. ArrayList<E> provides type safety");
        System.out.println("2. No explicit casting required when retrieving elements");
        System.out.println("3. Compile-time error detection for type mismatches");
        System.out.println("4. Better performance due to elimination of runtime checks");
        System.out.println("5. Code is more readable and maintainable");
        System.out.println("6. IDE provides better support with generics");
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