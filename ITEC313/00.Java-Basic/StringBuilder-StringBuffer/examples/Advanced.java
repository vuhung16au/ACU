// Advanced.java
// Advanced operations with StringBuilder and StringBuffer
//
// Python comparison: Use list and ''.join() for efficient string concatenation in Python.

public class Advanced {
    public static void main(String[] args) {
        // Chaining methods
        StringBuilder sb = new StringBuilder("Start");
        sb.append("-").append("Middle").append("-End");
        System.out.println(sb.toString()); // Output: Start-Middle-End

        // StringBuffer: reverse and insert
        StringBuffer sbf = new StringBuffer("Java");
        sbf.reverse();
        sbf.insert(0, "Reversed: ");
        System.out.println(sbf.toString()); // Output: Reversed: avaJ
    }
} 