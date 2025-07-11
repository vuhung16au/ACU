// WhileLoops.java
// Main source file for demonstrating while, do-while, and infinite loops in Java
// For graduate students transitioning from Python
// Java 8+ compatible

public class WhileLoops {
    public static void main(String[] args) {
        // 1. Basic while loop
        int n = 0;
        while (n < 3) {
            System.out.println("[while] n = " + n);
            n++;
        }
        // Python equivalent:
        // n = 0
        // while n < 3:
        //     print(n)
        //     n += 1

        // 2. do-while loop
        int m = 0;
        do {
            System.out.println("[do-while] m = " + m);
            m++;
        } while (m < 3);
        // Python does not have do-while, but can simulate:
        // m = 0
        // while True:
        //     print(m)
        //     m += 1
        //     if m >= 3:
        //         break

        // 3. Infinite loop with break
        int i = 0;
        while (true) {
            if (i >= 2) break;
            System.out.println("[infinite] i = " + i);
            i++;
        }
        // Python equivalent:
        // i = 0
        // while True:
        //     if i >= 2:
        //         break
        //     print(i)
        //     i += 1

        // 4. Loop with continue
        for (int j = 0; j < 4; j++) {
            if (j == 2) continue; // Skip when j == 2
            System.out.println("[continue] j = " + j);
        }
        // Python equivalent:
        // for j in range(4):
        //     if j == 2:
        //         continue
        //     print(j)
    }
} 