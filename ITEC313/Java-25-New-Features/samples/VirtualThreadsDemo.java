/**
 * Virtual Threads Improvements Demo - Java 25 Feature
 * 
 * Virtual Threads were introduced in Java 21 and have been improved in Java 25
 * with better performance, debugging capabilities, and integration.
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * @author Java 25 Features Demo
 * Date: 2025
 */

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.*;
import java.util.stream.IntStream;
import java.util.List;
import java.util.ArrayList;

public class VirtualThreadsDemo {
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Java 25 Virtual Threads Improvements Demo ===\n");
        
        basicVirtualThreads();
        performanceComparison();
        structuredTaskScope();
        virtualThreadPooling();
        debuggingImprovements();
        scalabilityDemo();
    }
    
    /**
     * Demonstrates basic virtual thread creation and usage
     */
    private static void basicVirtualThreads() throws InterruptedException {
        System.out.println("1. Basic Virtual Threads:");
        
        // Creating virtual threads (available since Java 21)
        Thread virtualThread1 = Thread.ofVirtual().name("virtual-1").start(() -> {
            System.out.println("Running in virtual thread: " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000); // This doesn't block platform threads
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        // Using virtual thread executor
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<String> future = executor.submit(() -> {
                Thread.sleep(500);
                return "Task completed in virtual thread: " + Thread.currentThread().getName();
            });
            
            System.out.println("Main thread continues immediately");
            System.out.println(future.get()); // Wait for completion
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        
        virtualThread1.join();
        System.out.println();
    }
    
    /**
     * Compares performance between platform and virtual threads
     */
    private static void performanceComparison() throws InterruptedException {
        System.out.println("2. Performance Comparison:");
        
        int numberOfTasks = 10_000;
        
        // Platform threads (limited by OS)
        System.out.println("Testing with platform threads (limited to thread pool):");
        long startTime = System.currentTimeMillis();
        
        try (ExecutorService platformExecutor = Executors.newFixedThreadPool(100)) {
            List<Future<Void>> futures = new ArrayList<>();
            
            for (int i = 0; i < numberOfTasks; i++) {
                Future<Void> future = platformExecutor.submit(() -> {
                    try {
                        Thread.sleep(10); // Simulate I/O
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    return null;
                });
                futures.add(future);
            }
            
            // Wait for all tasks to complete
            for (Future<Void> future : futures) {
                try {
                    future.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        
        long platformTime = System.currentTimeMillis() - startTime;
        System.out.println("Platform threads time: " + platformTime + " ms");
        
        // Virtual threads
        System.out.println("Testing with virtual threads:");
        startTime = System.currentTimeMillis();
        
        try (ExecutorService virtualExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<Void>> futures = new ArrayList<>();
            
            for (int i = 0; i < numberOfTasks; i++) {
                Future<Void> future = virtualExecutor.submit(() -> {
                    try {
                        Thread.sleep(10); // Simulate I/O
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    return null;
                });
                futures.add(future);
            }
            
            // Wait for all tasks to complete
            for (Future<Void> future : futures) {
                try {
                    future.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        
        long virtualTime = System.currentTimeMillis() - startTime;
        System.out.println("Virtual threads time: " + virtualTime + " ms");
        System.out.println("Performance improvement: " + 
                          String.format("%.2f%%", ((double)(platformTime - virtualTime) / platformTime) * 100));
        
        System.out.println();
    }
    
    /**
     * Demonstrates structured task scope (improved in Java 25)
     */
    private static void structuredTaskScope() {
        System.out.println("3. Structured Task Scope (Java 25 improvements):");
        
        // Note: This simulates the concept as the actual API may not be available
        System.out.println("Simulating structured concurrency with virtual threads:");
        
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<String>> tasks = new ArrayList<>();
            
            // Submit related tasks as a unit
            tasks.add(executor.submit(() -> {
                Thread.sleep(100);
                return "Database query completed";
            }));
            
            tasks.add(executor.submit(() -> {
                Thread.sleep(150);
                return "API call completed";
            }));
            
            tasks.add(executor.submit(() -> {
                Thread.sleep(80);
                return "Cache lookup completed";
            }));
            
            // Wait for all related tasks
            System.out.println("Waiting for all related tasks to complete...");
            for (Future<String> task : tasks) {
                try {
                    System.out.println("- " + task.get());
                } catch (ExecutionException e) {
                    System.err.println("Task failed: " + e.getCause().getMessage());
                }
            }
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Tasks interrupted");
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates virtual thread pooling improvements
     */
    private static void virtualThreadPooling() {
        System.out.println("4. Virtual Thread Pooling (Java 25 improvements):");
        
        // In Java 25, virtual thread creation and pooling is further optimized
        System.out.println("Creating large number of virtual threads efficiently:");
        
        long startTime = System.currentTimeMillis();
        List<Thread> threads = new ArrayList<>();
        
        // Create many virtual threads - this is efficient due to improvements
        for (int i = 0; i < 1_000_000; i++) {
            Thread vt = Thread.ofVirtual().name("worker-" + i).unstarted(() -> {
                // Minimal work to demonstrate creation overhead
                Math.sqrt(i);
            });
            threads.add(vt);
        }
        
        long creationTime = System.currentTimeMillis() - startTime;
        System.out.println("Created 1,000,000 virtual threads in: " + creationTime + " ms");
        
        // Start and join a subset to demonstrate execution
        startTime = System.currentTimeMillis();
        List<Thread> subset = threads.subList(0, 1000);
        subset.forEach(Thread::start);
        
        for (Thread thread : subset) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        long executionTime = System.currentTimeMillis() - startTime;
        System.out.println("Executed 1,000 virtual threads in: " + executionTime + " ms");
        
        System.out.println();
    }
    
    /**
     * Demonstrates debugging improvements for virtual threads in Java 25
     */
    private static void debuggingImprovements() throws InterruptedException {
        System.out.println("5. Debugging Improvements (Java 25):");
        
        // Enhanced thread naming and identification
        Thread virtualThread = Thread.ofVirtual()
            .name("debug-example")
            .uncaughtExceptionHandler((t, e) -> {
                System.err.println("Exception in virtual thread " + t.getName() + ": " + e.getMessage());
            })
            .start(() -> {
                System.out.println("Virtual thread info:");
                System.out.println("- Name: " + Thread.currentThread().getName());
                System.out.println("- Is virtual: " + Thread.currentThread().isVirtual());
                System.out.println("- Thread ID: " + Thread.currentThread().threadId());
                
                // Simulate some work with better stack trace information
                doSomeWork();
            });
        
        virtualThread.join();
        
        // Demonstrating thread dump information (improved in Java 25)
        System.out.println("\nVirtual thread monitoring:");
        Thread monitoringThread = Thread.ofVirtual().name("monitoring").start(() -> {
            System.out.println("Monitoring thread started");
            try {
                Thread.sleep(100);
                System.out.println("Monitoring completed");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        monitoringThread.join();
        System.out.println();
    }
    
    /**
     * Demonstrates scalability improvements
     */
    private static void scalabilityDemo() {
        System.out.println("6. Scalability Demo:");
        
        // Simulate high-concurrency scenario
        int numberOfClients = 100_000;
        System.out.println("Simulating " + numberOfClients + " concurrent client connections");
        
        CountDownLatch latch = new CountDownLatch(numberOfClients);
        long startTime = System.currentTimeMillis();
        
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            
            for (int i = 0; i < numberOfClients; i++) {
                final int clientId = i;
                executor.submit(() -> {
                    try {
                        // Simulate client request processing
                        simulateClientRequest(clientId);
                    } finally {
                        latch.countDown();
                    }
                });
            }
            
            // Wait for all clients to be processed
            try {
                latch.await(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println("Processed " + numberOfClients + " clients in: " + totalTime + " ms");
        System.out.println("Average processing time per client: " + 
                          String.format("%.3f ms", (double) totalTime / numberOfClients));
        
        System.out.println("\nNote: This would be impossible with platform threads due to memory limits");
        System.out.println("Virtual threads make this level of concurrency practical");
    }
    
    /**
     * Helper method for debugging demonstration
     */
    private static void doSomeWork() {
        try {
            // Simulate some processing
            Thread.sleep(50);
            
            // Chain of method calls for stack trace demonstration
            methodA();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private static void methodA() {
        methodB();
    }
    
    private static void methodB() {
        // This will show in stack traces with improved virtual thread debugging
        System.out.println("Work completed in nested method call");
    }
    
    /**
     * Simulates processing a client request
     */
    private static void simulateClientRequest(int clientId) {
        try {
            // Simulate I/O operation (database query, API call, etc.)
            Thread.sleep(10);
            
            // Simulate CPU work
            double result = Math.pow(clientId, 2) % 1000;
            
            // Simulate more I/O
            Thread.sleep(5);
            
            if (clientId % 10000 == 0) {
                System.out.println("Processed client " + clientId);
            }
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}