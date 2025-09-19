/**
 * Structured Concurrency Demo - Java 25 Feature
 * 
 * Structured Concurrency (finalized in Java 25) simplifies concurrent programming
 * by treating related tasks as a single unit of work with proper error handling
 * and resource management.
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * @author Java 25 Features Demo
 * Date: 2025
 */

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class StructuredConcurrencyDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Java 25 Structured Concurrency Demo ===\n");
        
        traditionalConcurrencyProblems();
        structuredConcurrencyBenefits();
        errorHandlingWithStructuredConcurrency();
        resourceManagementExample();
        realWorldExample();
    }
    
    /**
     * Demonstrates problems with traditional concurrency approaches
     */
    private static void traditionalConcurrencyProblems() {
        System.out.println("1. Traditional Concurrency Problems:");
        
        // Traditional approach with ExecutorService
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        try {
            List<Future<String>> futures = new ArrayList<>();
            
            // Submit related tasks
            futures.add(executor.submit(() -> {
                Thread.sleep(1000);
                return "Database query result";
            }));
            
            futures.add(executor.submit(() -> {
                Thread.sleep(800);
                return "API call result";
            }));
            
            futures.add(executor.submit(() -> {
                Thread.sleep(1200);
                throw new RuntimeException("Service unavailable");
            }));
            
            // Problems with traditional approach:
            // 1. Complex error handling
            // 2. Resource leaks if not properly managed
            // 3. Difficult to cancel related tasks when one fails
            
            System.out.println("Traditional approach - collecting results:");
            for (int i = 0; i < futures.size(); i++) {
                try {
                    String result = futures.get(i).get(2, TimeUnit.SECONDS);
                    System.out.println("Task " + (i + 1) + ": " + result);
                } catch (Exception e) {
                    System.err.println("Task " + (i + 1) + " failed: " + e.getMessage());
                    // In traditional approach, other tasks continue running
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates structured concurrency benefits
     */
    private static void structuredConcurrencyBenefits() {
        System.out.println("2. Structured Concurrency Benefits:");
        
        // Simulating structured concurrency concept (actual API may differ)
        System.out.println("Structured concurrency simulation:");
        
        try {
            // In Java 25, this would use StructuredTaskScope
            CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(500);
                    return "User data fetched";
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            });
            
            CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(700);
                    return "Preferences loaded";
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            });
            
            CompletableFuture<String> task3 = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(600);
                    return "Permissions verified";
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            });
            
            // Structured approach - all tasks are related and managed together
            CompletableFuture<Void> allTasks = CompletableFuture.allOf(task1, task2, task3);
            
            try {
                allTasks.get(2, TimeUnit.SECONDS);
                System.out.println("All tasks completed successfully:");
                System.out.println("- " + task1.get());
                System.out.println("- " + task2.get());
                System.out.println("- " + task3.get());
            } catch (Exception e) {
                System.err.println("Structured task group failed: " + e.getMessage());
                // In structured concurrency, all related tasks are automatically cancelled
                task1.cancel(true);
                task2.cancel(true);
                task3.cancel(true);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates error handling in structured concurrency
     */
    private static void errorHandlingWithStructuredConcurrency() {
        System.out.println("3. Error Handling with Structured Concurrency:");
        
        // Simulating fail-fast behavior
        System.out.println("Demonstrating fail-fast behavior:");
        
        List<CompletableFuture<String>> tasks = new ArrayList<>();
        
        // Task 1: succeeds
        tasks.add(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(500);
                System.out.println("Task 1 completed successfully");
                return "Task 1 result";
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }));
        
        // Task 2: fails quickly
        tasks.add(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(200);
                throw new RuntimeException("Task 2 failed");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }));
        
        // Task 3: would take longer but should be cancelled
        tasks.add(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("Task 3 completed (this shouldn't print)");
                return "Task 3 result";
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Task 3 was cancelled");
                throw new RuntimeException("Cancelled");
            }
        }));
        
        try {
            // Wait for first failure
            CompletableFuture<Object> firstCompleted = CompletableFuture.anyOf(
                tasks.toArray(new CompletableFuture[0])
            );
            
            Object result = firstCompleted.get(3, TimeUnit.SECONDS);
            System.out.println("First completed: " + result);
            
        } catch (Exception e) {
            System.err.println("Task group failed: " + e.getCause().getMessage());
            // Cancel all remaining tasks
            tasks.forEach(task -> task.cancel(true));
            System.out.println("All related tasks cancelled due to failure");
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates resource management with structured concurrency
     */
    private static void resourceManagementExample() {
        System.out.println("4. Resource Management Example:");
        
        // Simulating automatic resource cleanup
        System.out.println("Structured concurrency ensures proper resource cleanup:");
        
        class ResourceManager implements AutoCloseable {
            private final String name;
            private boolean closed = false;
            
            public ResourceManager(String name) {
                this.name = name;
                System.out.println("Resource " + name + " acquired");
            }
            
            public String process() throws InterruptedException {
                if (closed) throw new IllegalStateException("Resource closed");
                Thread.sleep(300);
                return "Processed by " + name;
            }
            
            @Override
            public void close() {
                if (!closed) {
                    closed = true;
                    System.out.println("Resource " + name + " released");
                }
            }
        }
        
        // Try-with-resources ensures cleanup even with structured concurrency
        try (ResourceManager db = new ResourceManager("Database");
             ResourceManager cache = new ResourceManager("Cache");
             ResourceManager api = new ResourceManager("API")) {
            
            CompletableFuture<String> dbTask = CompletableFuture.supplyAsync(() -> {
                try {
                    return db.process();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            });
            
            CompletableFuture<String> cacheTask = CompletableFuture.supplyAsync(() -> {
                try {
                    return cache.process();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            });
            
            CompletableFuture<String> apiTask = CompletableFuture.supplyAsync(() -> {
                try {
                    return api.process();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            });
            
            // Wait for all tasks
            CompletableFuture.allOf(dbTask, cacheTask, apiTask).get(2, TimeUnit.SECONDS);
            
            System.out.println("Results:");
            System.out.println("- " + dbTask.get());
            System.out.println("- " + cacheTask.get());
            System.out.println("- " + apiTask.get());
            
        } catch (Exception e) {
            System.err.println("Operation failed: " + e.getMessage());
        }
        // Resources are automatically closed here
        
        System.out.println();
    }
    
    /**
     * Real-world example: User profile loading
     */
    private static void realWorldExample() {
        System.out.println("5. Real-World Example: User Profile Loading");
        
        class UserProfileService {
            public String loadBasicInfo(String userId) throws InterruptedException {
                Thread.sleep(300);
                return "User: " + userId + " (John Doe, age 30)";
            }
            
            public String loadPreferences(String userId) throws InterruptedException {
                Thread.sleep(400);
                return "Preferences: dark theme, notifications on";
            }
            
            public String loadRecentActivity(String userId) throws InterruptedException {
                Thread.sleep(500);
                if (new Random().nextBoolean()) {
                    throw new RuntimeException("Activity service temporarily unavailable");
                }
                return "Recent: 5 posts, 12 likes this week";
            }
            
            public String loadFriends(String userId) throws InterruptedException {
                Thread.sleep(350);
                return "Friends: 127 connections";
            }
        }
        
        UserProfileService service = new UserProfileService();
        String userId = "user123";
        
        System.out.println("Loading user profile with structured concurrency:");
        
        // All profile loading tasks are related and should be managed together
        CompletableFuture<String> basicInfo = CompletableFuture.supplyAsync(() -> {
            try {
                return service.loadBasicInfo(userId);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        });
        
        CompletableFuture<String> preferences = CompletableFuture.supplyAsync(() -> {
            try {
                return service.loadPreferences(userId);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        });
        
        CompletableFuture<String> activity = CompletableFuture.supplyAsync(() -> {
            try {
                return service.loadRecentActivity(userId);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        });
        
        CompletableFuture<String> friends = CompletableFuture.supplyAsync(() -> {
            try {
                return service.loadFriends(userId);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        });
        
        try {
            // In structured concurrency, if any critical component fails,
            // the entire operation can be cancelled
            CompletableFuture<Void> allTasks = CompletableFuture.allOf(
                basicInfo, preferences, activity, friends
            );
            
            allTasks.get(2, TimeUnit.SECONDS);
            
            System.out.println("Profile loaded successfully:");
            System.out.println("- " + basicInfo.get());
            System.out.println("- " + preferences.get());
            System.out.println("- " + activity.get());
            System.out.println("- " + friends.get());
            
        } catch (Exception e) {
            System.err.println("Profile loading failed: " + e.getCause().getMessage());
            System.out.println("Cancelling all related profile loading tasks...");
            
            // Cancel all related tasks
            basicInfo.cancel(true);
            preferences.cancel(true);
            activity.cancel(true);
            friends.cancel(true);
            
            System.out.println("All tasks cancelled - no partial state left");
        }
        
        System.out.println();
    }
}