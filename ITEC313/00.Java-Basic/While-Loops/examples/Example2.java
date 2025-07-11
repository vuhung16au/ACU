// Example2.java
// Demonstrates a do-while loop in Java
// For graduate students transitioning from Python

public class Example2 {
    public static void main(String[] args) {
        int count = 0;
        do {
            System.out.println("Count: " + count);
            count++;
        } while (count < 5);
        // Python does not have a direct do-while loop, but similar logic:
        // count = 0
        // while True:
        //     print(count)
        //     count += 1
        //     if count >= 5:
        //         break
    }
} 