// Example1.java
// Basic method overloading example
// Shows how Java resolves overloaded methods at compile time
// Python comparison: Python uses dynamic typing and single function definitions

public class Example1 {
    // Overloaded print method: prints an integer
    public void print(int value) {
        // In Python: def print(value): print(value)
        System.out.println("Integer: " + value);
    }

    // Overloaded print method: prints a double
    public void print(double value) {
        // In Python: def print(value): print(value)
        System.out.println("Double: " + value);
    }

    // Overloaded print method: prints a string
    public void print(String value) {
        // In Python: def print(value): print(value)
        System.out.println("String: " + value);
    }

    public static void main(String[] args) {
        Example1 ex = new Example1();
        ex.print(42);           // Integer
        ex.print(3.14);         // Double
        ex.print("Hello Java"); // String
    }
} 