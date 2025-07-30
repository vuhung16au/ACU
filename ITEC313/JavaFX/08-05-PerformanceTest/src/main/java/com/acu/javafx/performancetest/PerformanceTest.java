package com.acu.javafx.performancetest;

/**
 * Original PerformanceTest class demonstrating basic performance measurement.
 * This class measures execution time for different loop iterations.
 */
public class PerformanceTest {
    
    /**
     * Main method to run performance tests with different values of n.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        getTime(1000000);
        getTime(10000000);
        getTime(100000000);
        getTime(1000000000);
        getTime(10000000000L);
    }   
    
    /**
     * Measures and prints the execution time for a given number of iterations.
     * 
     * @param n the number of iterations to perform
     */
    public static void getTime(long n) {
        long startTime = System.currentTimeMillis();
        long k = 0;
        for (long i = 1; i <= n; i++) {
            k = k + 5;
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Execution time for n = " + n 
            + " is " + (endTime - startTime) + " milliseconds");
    }
} 