/**
 * Unnamed Variables & Patterns Demo - Java 25 Feature
 * 
 * Unnamed Variables & Patterns (JEP 443) improve code readability and conciseness
 * in pattern matching and lambda expressions by allowing unused variables to be unnamed.
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * @author Java 25 Features Demo
 * Date: 2025
 */

import java.util.*;
import java.util.stream.IntStream;

public class UnnamedVariablesDemo {
    
    // Example record for pattern matching demonstrations
    record Point(int x, int y) {}
    record Circle(Point center, double radius) {}
    record Rectangle(Point topLeft, Point bottomRight) {}
    
    // Example sealed class hierarchy for pattern matching
    sealed interface Shape permits Circle, Rectangle {}
    
    public static void main(String[] args) {
        System.out.println("=== Java 25 Unnamed Variables & Patterns Demo ===\n");
        
        unnamedVariablesInLambdas();
        unnamedVariablesInLoops();
        unnamedPatternsInSwitch();
        unnamedPatternsInInstanceof();
        unnamedVariablesInTryCatch();
        nestedPatternsWithUnnamed();
    }
    
    /**
     * Demonstrates unnamed variables in lambda expressions
     */
    private static void unnamedVariablesInLambdas() {
        System.out.println("1. Unnamed Variables in Lambda Expressions:");
        
        List<String> items = List.of("apple", "banana", "cherry", "date");
        
        // Traditional approach - we have to name unused parameters
        System.out.println("Traditional lambda (with unused parameter names):");
        items.stream()
             .filter(item -> item.length() > 5)
             .forEach(item -> System.out.println("- " + item));
        
        // Java 25 approach with unnamed variables (simulated)
        // In actual Java 25: items.stream().filter(_ -> _.length() > 5).forEach(_ -> System.out.println("- " + _));
        System.out.println("\nWith unnamed variables (Java 25 concept):");
        items.stream()
             .filter(item -> item.length() > 5)  // Would be: filter(_ -> _.length() > 5)
             .forEach(item -> System.out.println("- " + item));  // Would be: forEach(_ -> System.out.println("- " + _))
        
        // Multiple parameter lambda with some unnamed
        Map<String, Integer> wordLengths = new HashMap<>();
        items.forEach(item -> wordLengths.put(item, item.length()));
        
        System.out.println("\nProcessing map entries with unnamed variables:");
        wordLengths.entrySet().forEach(entry -> {
            // In Java 25, if we only care about the value:
            // wordLengths.entrySet().forEach((_, value) -> System.out.println("Length: " + value));
            System.out.println("Word: " + entry.getKey() + ", Length: " + entry.getValue());
        });
        
        System.out.println();
    }
    
    /**
     * Demonstrates unnamed variables in for-each loops
     */
    private static void unnamedVariablesInLoops() {
        System.out.println("2. Unnamed Variables in Loops:");
        
        List<List<String>> nestedList = List.of(
            List.of("a", "b", "c"),
            List.of("d", "e", "f"),
            List.of("g", "h", "i")
        );
        
        // Traditional approach
        System.out.println("Traditional nested loop:");
        for (List<String> subList : nestedList) {
            for (String item : subList) {
                System.out.print(item + " ");
            }
        }
        System.out.println();
        
        // Java 25 with unnamed variables (simulated)
        System.out.println("With unnamed variables (if we only need count):");
        int totalItems = 0;
        for (List<String> subList : nestedList) {
            for (String ignored : subList) {  // In Java 25: for (_ : subList)
                totalItems++;
            }
        }
        System.out.println("Total items: " + totalItems);
        
        // Using unnamed variables in range loops
        System.out.println("\nRange loop with unnamed variable:");
        IntStream.range(0, 5)
                .forEach(i -> System.out.print("* "));  // In Java 25: forEach(_ -> System.out.print("* "))
        System.out.println();
        System.out.println();
    }
    
    /**
     * Demonstrates unnamed patterns in switch expressions
     */
    private static void unnamedPatternsInSwitch() {
        System.out.println("3. Unnamed Patterns in Switch Expressions:");
        
        List<Object> shapes = List.of(
            new Circle(new Point(0, 0), 5.0),
            new Rectangle(new Point(0, 0), new Point(10, 10)),
            new Point(3, 4),
            "Not a shape"
        );
        
        for (Object obj : shapes) {
            String description;
            if (obj instanceof Circle c) {
                description = "Circle with radius " + c.radius();
            } else if (obj instanceof Rectangle r) {
                description = "Rectangle from " + r.topLeft() + " to " + r.bottomRight();
            } else if (obj instanceof Point p) {
                description = "Point at (" + p.x() + ", " + p.y() + ")";
            } else if (obj == null) {
                description = "Null object";
            } else {
                description = "Unknown shape: " + obj;
            }
            System.out.println(description);
        }
        
        System.out.println("\nSimulating unnamed patterns (Java 25 concept):");
        for (Object obj : shapes) {
            String type;
            if (obj instanceof Circle) {
                type = "Circle"; // In Java 25: case Circle(_) -> "Circle";
            } else if (obj instanceof Rectangle) {
                type = "Rectangle"; // In Java 25: case Rectangle(_) -> "Rectangle";
            } else if (obj instanceof Point) {
                type = "Point"; // In Java 25: case Point(_) -> "Point";
            } else if (obj == null) {
                type = "Null";
            } else {
                type = "Unknown";
            }
            System.out.println("Type: " + type);
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates unnamed patterns in instanceof checks
     */
    private static void unnamedPatternsInInstanceof() {
        System.out.println("4. Unnamed Patterns in instanceof:");
        
        Object[] objects = {
            new Circle(new Point(1, 1), 3.0),
            new Rectangle(new Point(0, 0), new Point(5, 5)),
            "Hello World",
            42,
            null
        };
        
        for (Object obj : objects) {
            // Traditional instanceof with pattern variables
            if (obj instanceof Circle circle) {
                System.out.println("Found circle with radius: " + circle.radius());
            } else if (obj instanceof Rectangle rect) {
                System.out.println("Found rectangle area: " + 
                    ((rect.bottomRight().x() - rect.topLeft().x()) * 
                     (rect.bottomRight().y() - rect.topLeft().y())));
            } 
            // In Java 25, if we only need to know the type:
            // else if (obj instanceof String _) {
            //     System.out.println("Found a string");
            // } else if (obj instanceof Integer _) {
            //     System.out.println("Found an integer");
            // }
            else if (obj instanceof String ignored) {
                System.out.println("Found a string (length: " + ((String) obj).length() + ")");
            } else if (obj instanceof Integer ignored) {
                System.out.println("Found an integer (value: " + obj + ")");
            } else if (obj == null) {
                System.out.println("Found null");
            } else {
                System.out.println("Found unknown type: " + obj.getClass().getSimpleName());
            }
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates unnamed variables in try-catch blocks
     */
    private static void unnamedVariablesInTryCatch() {
        System.out.println("5. Unnamed Variables in Try-Catch:");
        
        String[] numbers = {"123", "456", "abc", "789", "def"};
        
        for (String numberStr : numbers) {
            try {
                int value = Integer.parseInt(numberStr);
                System.out.println("Parsed: " + value);
            } catch (NumberFormatException e) {
                // Traditional approach - we have to name the exception even if unused
                System.out.println("Failed to parse: " + numberStr);
            }
            // In Java 25, if we don't need the exception details:
            // catch (NumberFormatException _) {
            //     System.out.println("Failed to parse: " + numberStr);
            // }
        }
        
        // Multiple catch blocks with unnamed variables
        System.out.println("\nMultiple operations with exception handling:");
        try {
            performRiskyOperations();
        } catch (IllegalArgumentException e) {
            System.out.println("Argument error: " + e.getMessage());
        } catch (RuntimeException ignored) {  // In Java 25: catch (RuntimeException _)
            System.out.println("Some runtime error occurred");
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates nested patterns with unnamed variables
     */
    private static void nestedPatternsWithUnnamed() {
        System.out.println("6. Nested Patterns with Unnamed Variables:");
        
        List<Circle> circles = List.of(
            new Circle(new Point(0, 0), 1.0),
            new Circle(new Point(5, 5), 2.5),
            new Circle(new Point(-3, 4), 1.8)
        );
        
        for (Circle circle : circles) {
            // Traditional nested pattern matching (simulated)
            if (circle instanceof Circle c) {
                Point p = c.center();
                double radius = c.radius();
                if (p.x() == 0 && p.y() == 0) {
                    System.out.println("Circle at origin with radius " + radius);
                } else {
                    System.out.println("Circle at (" + p.x() + ", " + p.y() + ") with radius " + radius);
                }
            }
            
            // In Java 25, if we only care about the radius:
            // case Circle(_, double radius) -> System.out.println("Circle with radius " + radius);
            
            // Or if we only care about the position:
            // case Circle(Point p, _) -> System.out.println("Circle at " + p);
            
            // Current simulation:
            System.out.println("Simulated unnamed pattern - radius only: " + circle.radius());
        }
        
        System.out.println();
    }
    
    /**
     * Helper method that throws various exceptions for demonstration
     */
    private static void performRiskyOperations() {
        Random random = new Random();
        int choice = random.nextInt(3);
        
        switch (choice) {
            case 0 -> throw new IllegalArgumentException("Invalid argument");
            case 1 -> throw new RuntimeException("Generic runtime error");
            case 2 -> System.out.println("Operation completed successfully");
        }
    }
}