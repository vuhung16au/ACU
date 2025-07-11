// Advanced.java
// Advanced demonstration of operator combinations, bitwise operations, and precedence
// For Python programmers: note the use of bitwise operators and precedence rules

public class Advanced {
    public static void main(String[] args) {
        int a = 6, b = 4, c = 2;
        int result = a + b * c; // Multiplication before addition
        int bitwise = (a & b) | (b ^ c); // Bitwise AND, OR, XOR
        int shift = a << 2; // Left shift (multiplies by 4)
        boolean logic = (a > b) && (b < c || a == 6); // Logical AND/OR
        System.out.println("a + b * c = " + result);
        System.out.println("Bitwise: (a & b) | (b ^ c) = " + bitwise);
        System.out.println("Shift: a << 2 = " + shift);
        System.out.println("Logic: (a > b) && (b < c || a == 6) = " + logic);
    }
} 