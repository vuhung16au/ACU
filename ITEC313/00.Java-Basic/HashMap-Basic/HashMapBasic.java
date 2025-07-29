/**
 * HashMapBasic.java
 * 
 * This program demonstrates HashMap usage in Java:
 * - HashMap creation and initialization
 * - Key-value pair operations
 * - HashMap iteration techniques
 * - HashMap performance characteristics
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Collection;
import java.util.Iterator;

public class HashMapBasic {
    
    public static void main(String[] args) {
        System.out.println("=== HashMap Basic Examples ===\n");
        
        // Example 1: Basic HashMap operations
        basicHashMapOperations();
        
        // Example 2: HashMap with different data types
        hashMapWithDifferentTypes();
        
        // Example 3: HashMap iteration methods
        hashMapIteration();
        
        // Example 4: HashMap utility methods
        hashMapUtilityMethods();
        
        // Example 5: HashMap with null values
        hashMapWithNullValues();
        
        System.out.println("\n=== All examples completed successfully! ===");
    }
    
    /**
     * Demonstrates basic HashMap operations similar to Python dict operations
     * Python equivalent: my_dict = {} or my_dict = dict()
     */
    public static void basicHashMapOperations() {
        System.out.println("1. Basic HashMap Operations:");
        System.out.println("   (Similar to Python dict operations)");
        
        // Creating a HashMap (Python: my_dict = {})
        HashMap<String, Integer> scores = new HashMap<>();
        
        // Adding key-value pairs (Python: my_dict['key'] = value)
        scores.put("Alice", 95);
        scores.put("Bob", 87);
        scores.put("Charlie", 92);
        scores.put("Diana", 89);
        
        System.out.println("   Initial HashMap: " + scores);
        
        // Getting a value (Python: my_dict['key'])
        int aliceScore = scores.get("Alice");
        System.out.println("   Alice's score: " + aliceScore);
        
        // Checking if key exists (Python: 'key' in my_dict)
        boolean hasEve = scores.containsKey("Eve");
        System.out.println("   Contains 'Eve': " + hasEve);
        
        // Updating a value (Python: my_dict['key'] = new_value)
        scores.put("Bob", 90); // Bob improved his score
        System.out.println("   After updating Bob's score: " + scores);
        
        // Removing a key-value pair (Python: del my_dict['key'])
        scores.remove("Charlie");
        System.out.println("   After removing Charlie: " + scores);
        
        // Getting size (Python: len(my_dict))
        int size = scores.size();
        System.out.println("   HashMap size: " + size);
        
        // Checking if empty (Python: len(my_dict) == 0)
        boolean isEmpty = scores.isEmpty();
        System.out.println("   Is empty: " + isEmpty);
        
        System.out.println();
    }
    
    /**
     * Demonstrates HashMap with different data types
     * Shows how to use various types as keys and values
     */
    public static void hashMapWithDifferentTypes() {
        System.out.println("2. HashMap with Different Data Types:");
        
        // HashMap with String keys and Integer values
        HashMap<String, Integer> ageMap = new HashMap<>();
        ageMap.put("John", 25);
        ageMap.put("Jane", 30);
        ageMap.put("Mike", 28);
        
        System.out.println("   Age map: " + ageMap);
        
        // HashMap with Integer keys and String values
        HashMap<Integer, String> gradeMap = new HashMap<>();
        gradeMap.put(90, "A");
        gradeMap.put(80, "B");
        gradeMap.put(70, "C");
        gradeMap.put(60, "D");
        
        System.out.println("   Grade map: " + gradeMap);
        
        // HashMap with String keys and Double values
        HashMap<String, Double> priceMap = new HashMap<>();
        priceMap.put("Apple", 0.50);
        priceMap.put("Banana", 0.30);
        priceMap.put("Orange", 0.75);
        
        System.out.println("   Price map: " + priceMap);
        
        // HashMap with String keys and Boolean values
        HashMap<String, Boolean> statusMap = new HashMap<>();
        statusMap.put("Online", true);
        statusMap.put("Offline", false);
        statusMap.put("Away", false);
        
        System.out.println("   Status map: " + statusMap);
        
        System.out.println();
    }
    
    /**
     * Demonstrates different ways to iterate through HashMap
     * Python equivalent: for key, value in my_dict.items():
     */
    public static void hashMapIteration() {
        System.out.println("3. HashMap Iteration Methods:");
        
        HashMap<String, String> capitals = new HashMap<>();
        capitals.put("Australia", "Canberra");
        capitals.put("Canada", "Ottawa");
        capitals.put("France", "Paris");
        capitals.put("Japan", "Tokyo");
        capitals.put("Brazil", "Brasília");
        
        System.out.println("   Countries and capitals: " + capitals);
        
        // Method 1: Iterate over keys (Python: for key in my_dict:)
        System.out.println("   Method 1 - Keys only:");
        for (String country : capitals.keySet()) {
            System.out.println("     " + country);
        }
        
        // Method 2: Iterate over values (Python: for value in my_dict.values():)
        System.out.println("   Method 2 - Values only:");
        for (String capital : capitals.values()) {
            System.out.println("     " + capital);
        }
        
        // Method 3: Iterate over key-value pairs (Python: for key, value in my_dict.items():)
        System.out.println("   Method 3 - Key-value pairs:");
        for (Map.Entry<String, String> entry : capitals.entrySet()) {
            String country = entry.getKey();
            String capital = entry.getValue();
            System.out.println("     " + country + " -> " + capital);
        }
        
        // Method 4: Using Iterator (more traditional Java approach)
        System.out.println("   Method 4 - Using Iterator:");
        Iterator<Map.Entry<String, String>> iterator = capitals.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            System.out.println("     " + entry.getKey() + " -> " + entry.getValue());
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates useful HashMap utility methods
     * Shows methods that don't have direct Python equivalents
     */
    public static void hashMapUtilityMethods() {
        System.out.println("4. HashMap Utility Methods:");
        
        HashMap<String, Integer> inventory = new HashMap<>();
        inventory.put("Apples", 50);
        inventory.put("Bananas", 30);
        inventory.put("Oranges", 25);
        
        System.out.println("   Initial inventory: " + inventory);
        
        // getOrDefault() - returns default value if key doesn't exist
        // Python equivalent: my_dict.get('key', default_value)
        int appleCount = inventory.getOrDefault("Apples", 0);
        int grapeCount = inventory.getOrDefault("Grapes", 0);
        System.out.println("   Apples: " + appleCount + ", Grapes: " + grapeCount);
        
        // putIfAbsent() - only adds if key doesn't exist
        // Python equivalent: my_dict.setdefault('key', value)
        inventory.putIfAbsent("Apples", 100); // Won't change existing value
        inventory.putIfAbsent("Grapes", 15);  // Will add new key-value pair
        System.out.println("   After putIfAbsent: " + inventory);
        
        // replace() - only updates if key exists
        inventory.replace("Bananas", 35);
        inventory.replace("Pears", 10); // Won't add if key doesn't exist
        System.out.println("   After replace: " + inventory);
        
        // containsValue() - check if value exists
        boolean hasFifty = inventory.containsValue(50);
        boolean hasHundred = inventory.containsValue(100);
        System.out.println("   Contains value 50: " + hasFifty);
        System.out.println("   Contains value 100: " + hasHundred);
        
        // clear() - remove all entries (Python: my_dict.clear())
        HashMap<String, Integer> tempMap = new HashMap<>(inventory);
        tempMap.clear();
        System.out.println("   After clear: " + tempMap);
        
        System.out.println();
    }
    
    /**
     * Demonstrates HashMap behavior with null values
     * Python doesn't have null, but has None
     */
    public static void hashMapWithNullValues() {
        System.out.println("5. HashMap with Null Values:");
        System.out.println("   (Note: Python uses None instead of null)");
        
        HashMap<String, String> userProfiles = new HashMap<>();
        
        // Adding null values
        userProfiles.put("Alice", "alice@email.com");
        userProfiles.put("Bob", null);  // Bob has no email
        userProfiles.put("Charlie", "charlie@email.com");
        userProfiles.put(null, "unknown@email.com"); // null as key
        
        System.out.println("   User profiles: " + userProfiles);
        
        // Checking for null values
        boolean hasNullValue = userProfiles.containsValue(null);
        boolean hasNullKey = userProfiles.containsKey(null);
        
        System.out.println("   Contains null value: " + hasNullValue);
        System.out.println("   Contains null key: " + hasNullKey);
        
        // Getting null values
        String bobEmail = userProfiles.get("Bob");
        String unknownEmail = userProfiles.get(null);
        
        System.out.println("   Bob's email: " + bobEmail);
        System.out.println("   Unknown email: " + unknownEmail);
        
        // Safe way to handle null values
        System.out.println("   Safe null handling:");
        for (Map.Entry<String, String> entry : userProfiles.entrySet()) {
            String user = entry.getKey();
            String email = entry.getValue();
            
            if (user == null) {
                System.out.println("     User: <null>, Email: " + email);
            } else if (email == null) {
                System.out.println("     User: " + user + ", Email: <null>");
            } else {
                System.out.println("     User: " + user + ", Email: " + email);
            }
        }
        
        System.out.println();
    }
} 