// Advanced.java
// Demonstrates advanced while loop usage in Java
// For graduate students transitioning from Python
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Advanced {
    public static void main(String[] args) {
        // Infinite loop with break
        int i = 0;
        while (true) {
            if (i >= 3) break; // Exits loop when i reaches 3
            System.out.println("Infinite loop iteration: " + i);
            i++;
        }
        // Python equivalent:
        // i = 0
        // while True:
        //     if i >= 3:
        //         break
        //     print(i)
        //     i += 1

        // Using continue
        for (int j = 0; j < 5; j++) {
            if (j % 2 == 0) continue; // Skip even numbers
            System.out.println("Odd number: " + j);
        }
        // Python equivalent:
        // for j in range(5):
        //     if j % 2 == 0:
        //         continue
        //     print(j)

        // Reading from a file using a while loop
        try (BufferedReader br = new BufferedReader(new FileReader("data/input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("File line: " + line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        // Python equivalent:
        // with open('data/input.txt') as f:
        //     for line in f:
        //         print(line.strip())
    }
} 