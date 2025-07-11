// Example2.java
// Basic usage of StringBuffer in Java
// Shows thread-safe mutable string operations
//
// Python comparison: No direct equivalent to StringBuffer in Python.

public class Example2 {
    public static void main(String[] args) {
        StringBuffer sbf = new StringBuffer("Safe");
        sbf.append(" & Sound");
        System.out.println(sbf.toString()); // Output: Safe & Sound
    }
} 