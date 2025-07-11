// Example2.java
// Demonstrates class (static) variable scope in Java
// For students transitioning from Python to Java

class Example2 {
    // Static variable: Shared among all instances
    // In Python: class variable
    private static int sharedCount = 0;

    public Example2() {
        sharedCount++;
    }

    public void showSharedCount() {
        System.out.println("Shared count (static variable): " + sharedCount);
    }

    public static void main(String[] args) {
        Example2 a = new Example2();
        Example2 b = new Example2();
        a.showSharedCount(); // 2
        b.showSharedCount(); // 2
    }
} 