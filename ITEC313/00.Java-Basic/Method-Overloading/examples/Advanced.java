// Advanced.java
// Advanced method overloading: varargs and object types
// Shows how Java resolves overloaded methods with variable arguments and Object
// Python comparison: Python uses *args and dynamic typing

public class Advanced {
    // Overloaded printAll method: accepts any number of integers
    public void printAll(int... values) {
        // In Python: def print_all(*values):
        System.out.print("Integers: ");
        for (int v : values) {
            System.out.print(v + " ");
        }
        System.out.println();
    }

    // Overloaded printAll method: accepts any number of strings
    public void printAll(String... values) {
        // In Python: def print_all(*values):
        System.out.print("Strings: ");
        for (String v : values) {
            System.out.print(v + " ");
        }
        System.out.println();
    }

    // Overloaded printAll method: accepts any number of objects
    public void printAll(Object... values) {
        // In Python: def print_all(*values):
        System.out.print("Objects: ");
        for (Object v : values) {
            System.out.print(v + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Advanced adv = new Advanced();
        adv.printAll(new int[]{1, 2, 3, 4});                // Integers (explicit array to avoid ambiguity)
        adv.printAll("a", "b", "c");                      // Strings
        adv.printAll(1, "two", 3.0, true);                 // Objects (mixed types)
        // In Python, *args allows any type and any number of arguments in one function
    }
} 