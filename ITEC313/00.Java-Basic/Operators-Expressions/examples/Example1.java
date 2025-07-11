// Example1.java
// Simple demonstration of arithmetic and assignment operators in Java
// For Python programmers: note type declarations and integer division differences

public class Example1 {
    public static void main(String[] args) {
        int a = 8;
        int b = 3;
        int sum = a + b; // Addition
        int div = a / b; // Integer division (Python: 8/3 = 2.666..., Java: 8/3 = 2)
        a += 5; // Assignment operator (a = a + 5)
        System.out.println("Sum: " + sum);
        System.out.println("Division: " + div);
        System.out.println("a after += 5: " + a);
    }
} 