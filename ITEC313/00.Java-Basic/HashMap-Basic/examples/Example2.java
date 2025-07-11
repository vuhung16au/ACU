/**
 * Example2.java
 * 
 * HashMap example demonstrating iteration and utility methods.
 * This example shows different ways to iterate through a HashMap
 * and use various utility methods.
 * 
 * Key concepts:
 * - Iterating over keys, values, and entries
 * - Using utility methods like getOrDefault, putIfAbsent
 * - Working with null values
 * 
 * Python equivalent: Dictionary iteration and utility methods
 */

import java.util.HashMap;
import java.util.Map;

public class Example2 {
    
    public static void main(String[] args) {
        System.out.println("=== Example 2: HashMap Iteration and Utilities ===\n");
        
        // Create a HashMap to store country populations
        HashMap<String, Long> populations = new HashMap<>();
        
        // Add some country populations
        populations.put("China", 1439323776L);
        populations.put("India", 1380004385L);
        populations.put("United States", 331002651L);
        populations.put("Indonesia", 273523615L);
        populations.put("Pakistan", 220892340L);
        
        System.out.println("Country populations: " + populations);
        
        // Method 1: Iterate over keys only
        // Python equivalent: for country in populations:
        System.out.println("\n1. Countries (keys only):");
        for (String country : populations.keySet()) {
            System.out.println("   " + country);
        }
        
        // Method 2: Iterate over values only
        // Python equivalent: for population in populations.values():
        System.out.println("\n2. Populations (values only):");
        for (Long population : populations.values()) {
            System.out.println("   " + population);
        }
        
        // Method 3: Iterate over key-value pairs
        // Python equivalent: for country, population in populations.items():
        System.out.println("\n3. Countries and populations:");
        for (Map.Entry<String, Long> entry : populations.entrySet()) {
            String country = entry.getKey();
            Long population = entry.getValue();
            System.out.println("   " + country + ": " + population);
        }
        
        // Using getOrDefault() method
        // Python equivalent: populations.get('Brazil', 0)
        System.out.println("\n4. Using getOrDefault():");
        Long brazilPopulation = populations.getOrDefault("Brazil", 0L);
        Long chinaPopulation = populations.getOrDefault("China", 0L);
        System.out.println("   Brazil population: " + brazilPopulation);
        System.out.println("   China population: " + chinaPopulation);
        
        // Using putIfAbsent() method
        // Python equivalent: populations.setdefault('Brazil', 212559417)
        System.out.println("\n5. Using putIfAbsent():");
        populations.putIfAbsent("Brazil", 212559417L); // Will add
        populations.putIfAbsent("China", 1500000000L); // Won't change existing
        System.out.println("   After putIfAbsent: " + populations);
        
        // Using replace() method
        System.out.println("\n6. Using replace():");
        populations.replace("India", 1400000000L); // Will update
        populations.replace("France", 67000000L);  // Won't add if key doesn't exist
        System.out.println("   After replace: " + populations);
        
        // Working with null values
        System.out.println("\n7. Working with null values:");
        HashMap<String, String> contacts = new HashMap<>();
        contacts.put("Alice", "alice@email.com");
        contacts.put("Bob", null);  // Bob has no email
        contacts.put("Charlie", "charlie@email.com");
        
        System.out.println("   Contacts: " + contacts);
        
        // Check for null values
        boolean hasNullValue = contacts.containsValue(null);
        System.out.println("   Contains null value: " + hasNullValue);
        
        // Safe iteration with null handling
        System.out.println("   Safe iteration:");
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            String name = entry.getKey();
            String email = entry.getValue();
            
            if (email == null) {
                System.out.println("     " + name + ": <no email>");
            } else {
                System.out.println("     " + name + ": " + email);
            }
        }
        
        System.out.println("\n=== Example 2 completed! ===");
    }
} 