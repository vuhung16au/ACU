/**
 * SerializationBasic.java
 * 
 * This program demonstrates Java object serialization:
 * - Object serialization process
 * - File I/O with objects
 * - Serialization security
 * - Custom serialization
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
import java.io.*;
import java.util.*;
public class SerializationBasic {
    
    /**
     * Simple class to demonstrate serialization
     * In Python, you'd use pickle or json for similar functionality
     */
    public static class Person implements Serializable {
        // In Python, you'd use __dict__ or dataclasses
        private String name;
        private int age;
        private transient String password; // transient fields are not serialized
        
        public Person(String name, int age, String password) {
            this.name = name;
            this.age = age;
            this.password = password;
        }
        
        // Getters and setters (Java convention)
        public String getName() { return name; }
        public int getAge() { return age; }
        public String getPassword() { return password; }
        
        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + 
                   ", password='" + (password != null ? "***" : "null") + "'}";
        }
    }
    
    /**
     * Demonstrates basic serialization to a file
     * Similar to Python's pickle.dump() but more explicit
     */
    public static void demonstrateBasicSerialization() {
        System.out.println("\n=== Basic Serialization ===");
        
        Person person = new Person("Alice", 25, "secret123");
        System.out.println("Original object: " + person);
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("data/person.ser"))) {
            
            // Serialize the object (like pickle.dump() in Python)
            oos.writeObject(person);
            System.out.println("✓ Object serialized to data/person.ser");
            
        } catch (IOException e) {
            System.err.println("✗ Serialization failed: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates basic deserialization from a file
     * Similar to Python's pickle.load() but more explicit
     */
    public static void demonstrateBasicDeserialization() {
        System.out.println("\n=== Basic Deserialization ===");
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("data/person.ser"))) {
            
            // Deserialize the object (like pickle.load() in Python)
            Person person = (Person) ois.readObject();
            System.out.println("✓ Object deserialized: " + person);
            System.out.println("  Note: password is null because it was transient");
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("✗ Deserialization failed: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates serializing collections
     * In Python, you'd serialize lists/dicts directly with pickle
     */
    public static void demonstrateCollectionSerialization() {
        System.out.println("\n=== Collection Serialization ===");
        
        List<Person> people = Arrays.asList(
            new Person("Bob", 30, "pass1"),
            new Person("Charlie", 35, "pass2"),
            new Person("Diana", 28, "pass3")
        );
        
        System.out.println("Original list: " + people);
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("data/people.ser"))) {
            
            oos.writeObject(people);
            System.out.println("✓ Collection serialized to data/people.ser");
            
        } catch (IOException e) {
            System.err.println("✗ Collection serialization failed: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates deserializing collections
     */
    public static void demonstrateCollectionDeserialization() {
        System.out.println("\n=== Collection Deserialization ===");
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("data/people.ser"))) {
            
            @SuppressWarnings("unchecked")
            List<Person> people = (List<Person>) ois.readObject();
            System.out.println("✓ Collection deserialized: " + people);
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("✗ Collection deserialization failed: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates error handling in serialization
     * Shows what happens when trying to serialize non-serializable objects
     */
    public static void demonstrateSerializationErrors() {
        System.out.println("\n=== Serialization Error Handling ===");
        
        // Create a non-serializable object
        Object nonSerializable = new Object();
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("data/error.ser"))) {
            
            oos.writeObject(nonSerializable);
            System.out.println("✓ Non-serializable object serialized");
            
        } catch (IOException e) {
            System.err.println("✗ Expected error for non-serializable object: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates custom serialization using writeObject/readObject
     * Similar to Python's __getstate__/__setstate__ but more explicit
     */
    public static class CustomSerializable implements Serializable {
        private String data;
        private int count;
        
        public CustomSerializable(String data, int count) {
            this.data = data;
            this.count = count;
        }
        
        // Custom serialization method (like __getstate__ in Python)
        private void writeObject(ObjectOutputStream out) throws IOException {
            System.out.println("  Custom serialization: writing data");
            out.defaultWriteObject(); // Write normal fields
            out.writeObject("Custom field: " + data + " (count: " + count + ")");
        }
        
        // Custom deserialization method (like __setstate__ in Python)
        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            System.out.println("  Custom deserialization: reading data");
            in.defaultReadObject(); // Read normal fields
            String customField = (String) in.readObject();
            System.out.println("  Custom field read: " + customField);
        }
        
        @Override
        public String toString() {
            return "CustomSerializable{data='" + data + "', count=" + count + "}";
        }
    }
    
    /**
     * Demonstrates custom serialization
     */
    public static void demonstrateCustomSerialization() {
        System.out.println("\n=== Custom Serialization ===");
        
        CustomSerializable obj = new CustomSerializable("test data", 42);
        System.out.println("Original object: " + obj);
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("data/custom.ser"))) {
            
            oos.writeObject(obj);
            System.out.println("✓ Custom object serialized");
            
        } catch (IOException e) {
            System.err.println("✗ Custom serialization failed: " + e.getMessage());
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("data/custom.ser"))) {
            
            CustomSerializable restored = (CustomSerializable) ois.readObject();
            System.out.println("✓ Custom object deserialized: " + restored);
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("✗ Custom deserialization failed: " + e.getMessage());
        }
    }
    
    /**
     * Main method to run all serialization demonstrations
     */
    public static void main(String[] args) {
        System.out.println("🚀 Java Serialization Basic Demo");
        System.out.println("=================================");
        System.out.println("This program demonstrates Java object serialization concepts.");
        System.out.println("Key differences from Python:");
        System.out.println("- Uses Serializable interface (vs pickle in Python)");
        System.out.println("- More explicit serialization/deserialization");
        System.out.println("- Type-safe serialization");
        System.out.println("- Automatic object graph serialization");
        
        // Create data directory if it doesn't exist
        new File("data").mkdirs();
        
        // Run all demonstrations
        demonstrateBasicSerialization();
        demonstrateBasicDeserialization();
        demonstrateCollectionSerialization();
        demonstrateCollectionDeserialization();
        demonstrateSerializationErrors();
        demonstrateCustomSerialization();
        
        System.out.println("\n✅ Serialization demonstration complete!");
        System.out.println("📁 Check the 'data/' directory for serialized files");
    }
} 