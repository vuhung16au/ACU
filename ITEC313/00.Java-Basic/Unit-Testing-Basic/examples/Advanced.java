// Advanced.java
// More complex manual verification in Java
// For students familiar with Python

import java.util.Arrays;

public class Advanced {
    public static void main(String[] args) {
        System.out.println("=== Advanced Example: Array Equality and Edge Cases ===\n");
        testArrayEquality();
        testEdgeCases();
    }

    // Test if two arrays are equal
    static void testArrayEquality() {
        int[] arr1 = {1, 2, 3};
        int[] arr2 = {1, 2, 3};
        boolean areEqual = Arrays.equals(arr1, arr2);
        System.out.println("Arrays equal: " + areEqual + " (Expected: true)");
        // In Python: arr1 == arr2
        assert areEqual : "Arrays should be equal";
    }

    // Test edge cases (e.g., empty array)
    static void testEdgeCases() {
        int[] empty = {};
        int sum = sumArray(empty);
        System.out.println("Sum of empty array: " + sum + " (Expected: 0)");
        assert sum == 0 : "Sum of empty array should be 0";
    }

    // Sums elements of an array
    static int sumArray(int[] arr) {
        int sum = 0;
        for (int n : arr) {
            sum += n;
        }
        return sum;
    }
} 