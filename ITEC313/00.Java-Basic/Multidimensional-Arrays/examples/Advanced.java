/**
 * Advanced.java
 * 
 * This program demonstrates advanced in Java:
 * - Core concepts and principles
 * - Implementation techniques
 * - Best practices and patterns
 * - Practical examples and usage
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * Institution: XYZ
 * @author XYZ
 * Date: July 11, 2025
 */
// Advanced.java
// Real-world example: seating chart using a 2D array
// For students transitioning from Python

public class Advanced {
    public static void main(String[] args) {
        // Represent a seating chart: 3 rows, 5 seats per row
        String[][] seats = {
            {"A1", "A2", "A3", "A4", "A5"},
            {"B1", "B2", "B3", "B4", "B5"},
            {"C1", "C2", "C3", "C4", "C5"}
        };

        // Mark some seats as reserved (null)
        seats[0][2] = null; // A3 reserved
        seats[2][4] = null; // C5 reserved

        // Print the seating chart
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j] == null) {
                    System.out.print("[X] ");
                } else {
                    System.out.print("[" + seats[i][j] + "] ");
                }
            }
            System.out.println();
        }

        // Python comparison:
        // seats = [["A1","A2","A3","A4","A5"],
        //          ["B1","B2","B3","B4","B5"],
        //          ["C1","C2","C3","C4","C5"]]
        // seats[0][2] = None
        // seats[2][4] = None
        // for row in seats:
        //     for seat in row:
        //         print("[X]" if seat is None else f"[{seat}]", end=' ')
        //     print()
    }
} 