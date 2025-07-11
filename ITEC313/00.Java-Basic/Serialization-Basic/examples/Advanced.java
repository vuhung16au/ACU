import java.io.*;
import java.util.*;

/**
 * Advanced.java - Advanced Serialization Concepts
 * 
 * This example demonstrates advanced serialization techniques including:
 * - Externalization for custom control
 * - Serialization proxies for better design
 * - Performance considerations
 * - Security implications
 * 
 * Key differences from Python:
 * - Java provides Externalizable interface for custom control
 * - Java serialization proxies are more explicit
 * - Java has built-in security mechanisms
 * 
 * @author ITEC313 - Object-Oriented Programming
 */
public class Advanced {
    
    /**
     * Externalizable class for custom serialization control
     * Similar to Python's __getstate__/__setstate__ but more explicit
     */
    public static class ExternalizablePerson implements Externalizable {
        private String name;
        private int age;
        private String email;
        private transient String computedHash; // Won't be serialized
        
        // Required no-arg constructor for Externalizable
        public ExternalizablePerson() {
            System.out.println("  ExternalizablePerson: Default constructor called");
        }
        
        public ExternalizablePerson(String name, int age, String email) {
            this.name = name;
            this.age = age;
            this.email = email;
            this.computedHash = computeHash();
        }
        
        private String computeHash() {
            return name + age + email; // Simple hash for demonstration
        }
        
        // Custom externalization - write only what we want
        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            System.out.println("  ExternalizablePerson: Custom serialization");
            out.writeUTF(name);
            out.writeInt(age);
            out.writeUTF(email);
            // Note: computedHash is not written (transient)
        }
        
        // Custom externalization - read and reconstruct
        @Override
        public void readExternal(ObjectInput in) throws IOException {
            System.out.println("  ExternalizablePerson: Custom deserialization");
            name = in.readUTF();
            age = in.readInt();
            email = in.readUTF();
            computedHash = computeHash(); // Recompute after deserialization
        }
        
        // Getters
        public String getName() { return name; }
        public int getAge() { return age; }
        public String getEmail() { return email; }
        public String getComputedHash() { return computedHash; }
        
        @Override
        public String toString() {
            return String.format("ExternalizablePerson{name='%s', age=%d, email='%s', hash='%s'}", 
                               name, age, email, computedHash);
        }
    }
    
    /**
     * Demonstrates Externalizable interface
     */
    public static void demonstrateExternalizable() {
        System.out.println("=== Externalizable Interface ===");
        
        ExternalizablePerson person = new ExternalizablePerson("John Doe", 30, "john@example.com");
        System.out.println("Original person: " + person);
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("data/externalizable_person.ser"))) {
            
            oos.writeObject(person);
            System.out.println("✓ Externalizable person serialized");
            
        } catch (IOException e) {
            System.err.println("✗ Externalizable serialization failed: " + e.getMessage());
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("data/externalizable_person.ser"))) {
            
            ExternalizablePerson restored = (ExternalizablePerson) ois.readObject();
            System.out.println("✓ Externalizable person deserialized: " + restored);
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("✗ Externalizable deserialization failed: " + e.getMessage());
        }
    }
    
    /**
     * Serialization proxy pattern for better design
     * This pattern provides better control over serialization
     */
    public static class PersonProxy implements Serializable {
        private static final long serialVersionUID = 1L;
        
        // Private constructor - only used internally
        private PersonProxy(String name, int age, String email) {
            this.name = name;
            this.age = age;
            this.email = email;
        }
        
        private final String name;
        private final int age;
        private final String email;
        
        // Factory method for creating instances
        public static PersonProxy create(String name, int age, String email) {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Name cannot be null or empty");
            }
            if (age < 0 || age > 150) {
                throw new IllegalArgumentException("Age must be between 0 and 150");
            }
            return new PersonProxy(name, age, email);
        }
        
        // Custom serialization - write proxy data
        private void writeObject(ObjectOutputStream out) throws IOException {
            System.out.println("  PersonProxy: Writing proxy data");
            out.writeUTF(name);
            out.writeInt(age);
            out.writeUTF(email != null ? email : "");
        }
        
        // Custom deserialization - read and validate
        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            System.out.println("  PersonProxy: Reading proxy data");
            String name = in.readUTF();
            int age = in.readInt();
            String email = in.readUTF();
            
            // Validate data during deserialization
            if (name.trim().isEmpty()) {
                throw new InvalidObjectException("Name cannot be empty");
            }
            if (age < 0 || age > 150) {
                throw new InvalidObjectException("Invalid age: " + age);
            }
            
            // Set final fields using reflection (simplified)
            try {
                java.lang.reflect.Field nameField = PersonProxy.class.getDeclaredField("name");
                java.lang.reflect.Field ageField = PersonProxy.class.getDeclaredField("age");
                java.lang.reflect.Field emailField = PersonProxy.class.getDeclaredField("email");
                
                nameField.setAccessible(true);
                ageField.setAccessible(true);
                emailField.setAccessible(true);
                
                nameField.set(this, name);
                ageField.set(this, age);
                emailField.set(this, email.isEmpty() ? null : email);
                
            } catch (Exception e) {
                throw new InvalidObjectException("Failed to reconstruct object: " + e.getMessage());
            }
        }
        
        // Getters
        public String getName() { return name; }
        public int getAge() { return age; }
        public String getEmail() { return email; }
        
        @Override
        public String toString() {
            return String.format("PersonProxy{name='%s', age=%d, email='%s'}", name, age, email);
        }
    }
    
    /**
     * Demonstrates serialization proxy pattern
     */
    public static void demonstrateSerializationProxy() {
        System.out.println("\n=== Serialization Proxy Pattern ===");
        
        PersonProxy person = PersonProxy.create("Jane Smith", 25, "jane@example.com");
        System.out.println("Original proxy person: " + person);
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("data/proxy_person.ser"))) {
            
            oos.writeObject(person);
            System.out.println("✓ Proxy person serialized");
            
        } catch (IOException e) {
            System.err.println("✗ Proxy serialization failed: " + e.getMessage());
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("data/proxy_person.ser"))) {
            
            PersonProxy restored = (PersonProxy) ois.readObject();
            System.out.println("✓ Proxy person deserialized: " + restored);
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("✗ Proxy deserialization failed: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates performance considerations with large objects
     */
    public static class LargeObject implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private String name;
        private byte[] largeData; // Simulate large data
        private List<String> strings;
        
        public LargeObject(String name, int dataSize) {
            this.name = name;
            this.largeData = new byte[dataSize];
            this.strings = new ArrayList<>();
            
            // Fill with some data
            for (int i = 0; i < dataSize; i++) {
                largeData[i] = (byte) (i % 256);
            }
            
            for (int i = 0; i < 1000; i++) {
                strings.add("String " + i);
            }
        }
        
        // Getters
        public String getName() { return name; }
        public byte[] getLargeData() { return largeData; }
        public List<String> getStrings() { return strings; }
        
        @Override
        public String toString() {
            return String.format("LargeObject{name='%s', dataSize=%d, strings=%d}", 
                               name, largeData.length, strings.size());
        }
    }
    
    /**
     * Demonstrates performance comparison
     */
    public static void demonstratePerformance() {
        System.out.println("\n=== Performance Considerations ===");
        
        // Create large object
        LargeObject largeObj = new LargeObject("Performance Test", 100000);
        System.out.println("Created large object: " + largeObj);
        
        // Measure serialization time
        long startTime = System.currentTimeMillis();
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("data/large_object.ser"))) {
            
            oos.writeObject(largeObj);
            long serializationTime = System.currentTimeMillis() - startTime;
            System.out.println("✓ Large object serialized in " + serializationTime + "ms");
            
        } catch (IOException e) {
            System.err.println("✗ Large object serialization failed: " + e.getMessage());
        }
        
        // Measure deserialization time
        startTime = System.currentTimeMillis();
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("data/large_object.ser"))) {
            
            LargeObject restored = (LargeObject) ois.readObject();
            long deserializationTime = System.currentTimeMillis() - startTime;
            System.out.println("✓ Large object deserialized in " + deserializationTime + "ms");
            System.out.println("  Restored object: " + restored);
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("✗ Large object deserialization failed: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates security considerations
     */
    public static class SecureObject implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private String sensitiveData;
        private transient String secretKey; // Won't be serialized
        
        public SecureObject(String sensitiveData, String secretKey) {
            this.sensitiveData = sensitiveData;
            this.secretKey = secretKey;
        }
        
        // Custom serialization with encryption (simplified)
        private void writeObject(ObjectOutputStream out) throws IOException {
            System.out.println("  SecureObject: Encrypting sensitive data");
            // In real applications, you'd encrypt the data here
            out.defaultWriteObject();
        }
        
        // Custom deserialization with decryption (simplified)
        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            System.out.println("  SecureObject: Decrypting sensitive data");
            in.defaultReadObject();
            // In real applications, you'd decrypt the data here
            this.secretKey = "reconstructed_key"; // Reconstruct transient field
        }
        
        // Getters
        public String getSensitiveData() { return sensitiveData; }
        public String getSecretKey() { return secretKey; }
        
        @Override
        public String toString() {
            return String.format("SecureObject{data='%s', key='%s'}", 
                               sensitiveData, secretKey != null ? "***" : "null");
        }
    }
    
    /**
     * Demonstrates security considerations
     */
    public static void demonstrateSecurity() {
        System.out.println("\n=== Security Considerations ===");
        
        SecureObject secureObj = new SecureObject("confidential_info", "secret_key_123");
        System.out.println("Original secure object: " + secureObj);
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("data/secure_object.ser"))) {
            
            oos.writeObject(secureObj);
            System.out.println("✓ Secure object serialized");
            
        } catch (IOException e) {
            System.err.println("✗ Secure serialization failed: " + e.getMessage());
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("data/secure_object.ser"))) {
            
            SecureObject restored = (SecureObject) ois.readObject();
            System.out.println("✓ Secure object deserialized: " + restored);
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("✗ Secure deserialization failed: " + e.getMessage());
        }
    }
    
    /**
     * Demonstrates best practices summary
     */
    public static void demonstrateBestPractices() {
        System.out.println("\n=== Best Practices Summary ===");
        
        System.out.println("1. Always implement Serializable interface");
        System.out.println("2. Use serialVersionUID for version control");
        System.out.println("3. Mark sensitive fields as transient");
        System.out.println("4. Use Externalizable for custom control");
        System.out.println("5. Implement custom serialization for validation");
        System.out.println("6. Consider performance for large objects");
        System.out.println("7. Handle security implications");
        System.out.println("8. Use serialization proxies for complex objects");
        System.out.println("9. Always handle exceptions properly");
        System.out.println("10. Test serialization/deserialization thoroughly");
        
        System.out.println("\nKey differences from Python:");
        System.out.println("- Java requires explicit interface implementation");
        System.out.println("- Java provides more control over serialization");
        System.out.println("- Java serialization is more type-safe");
        System.out.println("- Java has built-in security mechanisms");
    }
    
    /**
     * Main method to run all advanced examples
     */
    public static void main(String[] args) {
        System.out.println("🚀 Advanced Serialization Concepts");
        System.out.println("===================================");
        
        // Create data directory if it doesn't exist
        new File("data").mkdirs();
        
        demonstrateExternalizable();
        demonstrateSerializationProxy();
        demonstratePerformance();
        demonstrateSecurity();
        demonstrateBestPractices();
        
        System.out.println("\n✅ Advanced serialization demonstration complete!");
    }
} 