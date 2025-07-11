// Example1.java
// Basic usage of StringBuilder in Java
// Shows mutable string operations
//
// Python comparison: In Python, strings are immutable, so concatenation creates new objects.

public class Example1 {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("ACU");
        sb.append(" Student");
        System.out.println(sb.toString()); // Output: ACU Student
    }
} 