/**
 * Pattern Matching Enhancements Demo - Java 25 Feature
 * 
 * Pattern Matching has been enhanced in Java 25 with improved switch expressions,
 * record patterns, and more sophisticated matching capabilities.
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * @author Java 25 Features Demo
 * Date: 2025
 */

import java.util.*;

public class PatternMatchingDemo {
    
    // Example records for pattern matching
    record Point(int x, int y) {}
    record Circle(Point center, double radius) implements Shape {}
    record Rectangle(Point topLeft, Point bottomRight) implements Shape {}
    record Triangle(Point a, Point b, Point c) implements Shape {}
    
    // Sealed interface for type-safe pattern matching (Java 17 sealed classes)
    sealed interface Shape permits Circle, Rectangle, Triangle {}
    
    // Example for nested records
    record Person(String name, int age, Address address) {}
    record Address(String street, String city, String country) {}
    
    // Example enum for pattern matching
    enum Color { RED, GREEN, BLUE, YELLOW, BLACK, WHITE }
    
    public static void main(String[] args) {
        System.out.println("=== Java 25 Pattern Matching Enhancements Demo ===\n");
        
        basicPatternMatching();
        recordPatterns();
        nestedPatternMatching();
        guardedPatterns();
        switchExpressionEnhancements();
        instanceofPatterns();
        nullHandlingInPatterns();
    }
    
    /**
     * Demonstrates basic pattern matching improvements
     */
    private static void basicPatternMatching() {
        System.out.println("1. Basic Pattern Matching:");
        
        Object[] objects = {
            "Hello World",
            42,
            3.14,
            List.of(1, 2, 3),
            new Person("John", 30, new Address("123 Main St", "Anytown", "USA")),
            null
        };
        
        for (Object obj : objects) {
            String result;
            if (obj == null) {
                result = "null value";
            } else if (obj instanceof String s) {
                result = "String: '" + s + "' (length: " + s.length() + ")";
            } else if (obj instanceof Integer i) {
                result = "Integer: " + i + " (even: " + (i % 2 == 0) + ")";
            } else if (obj instanceof Double d) {
                result = "Double: " + d + " (rounded: " + Math.round(d) + ")";
            } else if (obj instanceof List<?> list) {
                result = "List with " + list.size() + " elements";
            } else if (obj instanceof Person p) {
                result = "Person: " + p.name() + ", age " + p.age();
            } else {
                result = "Unknown type: " + obj.getClass().getSimpleName();
            }
            System.out.println(result);
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates record pattern matching
     */
    private static void recordPatterns() {
        System.out.println("2. Record Pattern Matching:");
        
        List<Shape> shapes = List.of(
            new Circle(new Point(0, 0), 5.0),
            new Rectangle(new Point(1, 1), new Point(4, 4)),
            new Triangle(new Point(0, 0), new Point(3, 0), new Point(1, 2))
        );
        
        for (Shape shape : shapes) {
            // Enhanced pattern matching with record destructuring (simulated)
            String description;
            if (shape instanceof Circle circle) {
                Point center = circle.center();
                double radius = circle.radius();
                double area = Math.PI * radius * radius;
                description = String.format("Circle at (%d,%d) with radius %.1f, area: %.2f", 
                                          center.x(), center.y(), radius, area);
            } else if (shape instanceof Rectangle rect) {
                Point topLeft = rect.topLeft();
                Point bottomRight = rect.bottomRight();
                int width = bottomRight.x() - topLeft.x();
                int height = bottomRight.y() - topLeft.y();
                int area = width * height;
                description = String.format("Rectangle %dx%d, area: %d", width, height, area);
            } else if (shape instanceof Triangle triangle) {
                Point a = triangle.a();
                Point b = triangle.b();
                Point c = triangle.c();
                // Calculate triangle area using cross product
                double area = Math.abs((a.x() * (b.y() - c.y()) + 
                                      b.x() * (c.y() - a.y()) + 
                                      c.x() * (a.y() - b.y())) / 2.0);
                description = String.format("Triangle with vertices (%d,%d), (%d,%d), (%d,%d), area: %.2f",
                                          a.x(), a.y(), b.x(), b.y(), c.x(), c.y(), area);
            } else {
                description = "Unknown shape";
            }
            System.out.println(description);
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates nested pattern matching
     */
    private static void nestedPatternMatching() {
        System.out.println("3. Nested Pattern Matching:");
        
        List<Person> people = List.of(
            new Person("Alice", 25, new Address("123 Oak St", "Springfield", "USA")),
            new Person("Bob", 30, new Address("456 Pine Ave", "Toronto", "Canada")),
            new Person("Charlie", 35, new Address("789 Elm Dr", "London", "UK"))
        );
        
        for (Person person : people) {
            // Nested pattern matching with record destructuring (simulated)
            String info;
            if (person != null) {
                String name = person.name();
                int age = person.age();
                Address address = person.address();
                String street = address.street();
                String city = address.city();
                String country = address.country();
                
                if ("USA".equals(country)) {
                    info = String.format("%s (%d) lives in %s, %s - US resident", name, age, city, street);
                } else if ("Canada".equals(country)) {
                    info = String.format("%s (%d) lives in %s, %s - Canadian resident", name, age, city, street);
                } else {
                    info = String.format("%s (%d) lives in %s, %s, %s - International", name, age, city, street, country);
                }
            } else {
                info = "Unknown person type";
            }
            System.out.println(info);
        }
        
        // Pattern matching with collections
        System.out.println("\nPattern matching with collections:");
        List<List<Integer>> nestedLists = List.of(
            List.of(),
            List.of(1),
            List.of(1, 2),
            List.of(1, 2, 3, 4, 5)
        );
        
        for (List<Integer> list : nestedLists) {
            String result;
            switch (list.size()) {
                case 0 -> result = "Empty list";
                case 1 -> result = "Single element: " + list.get(0);
                case 2 -> result = "Two elements: " + list.get(0) + " and " + list.get(1);
                default -> result = "Multiple elements, first: " + list.get(0) + ", last: " + list.get(list.size() - 1);
            }
            System.out.println(result);
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates guarded patterns (pattern matching with conditions)
     */
    private static void guardedPatterns() {
        System.out.println("4. Guarded Patterns:");
        
        List<Shape> shapes = List.of(
            new Circle(new Point(0, 0), 1.0),    // Small circle at origin
            new Circle(new Point(5, 5), 10.0),   // Large circle away from origin
            new Rectangle(new Point(0, 0), new Point(2, 2)),  // Small square at origin
            new Rectangle(new Point(1, 1), new Point(10, 10))  // Large rectangle
        );
        
        for (Shape shape : shapes) {
            String category;
            // Guarded patterns with additional conditions (simulated)
            if (shape instanceof Circle circle) {
                Point center = circle.center();
                double radius = circle.radius();
                if (center.x() == 0 && center.y() == 0 && radius < 5) {
                    category = "Small circle at origin";
                } else if (radius >= 5) {
                    category = "Large circle at (" + center.x() + "," + center.y() + ")";
                } else {
                    category = "Regular circle";
                }
            } else if (shape instanceof Rectangle rect) {
                Point topLeft = rect.topLeft();
                Point bottomRight = rect.bottomRight();
                int width = bottomRight.x() - topLeft.x();
                int height = bottomRight.y() - topLeft.y();
                if (width == height && width <= 3) {
                    category = "Small square";
                } else if (width * height > 50) {
                    category = "Large rectangle";
                } else {
                    category = "Regular rectangle";
                }
            } else if (shape instanceof Triangle) {
                category = "Triangle shape";
            } else {
                category = "Unknown shape";
            }
            System.out.println(category);
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates enhanced switch expressions
     */
    private static void switchExpressionEnhancements() {
        System.out.println("5. Enhanced Switch Expressions:");
        
        // Multiple label patterns (using traditional switch)
        for (int i = 1; i <= 10; i++) {
            String category;
            switch (i) {
                case 1, 2, 3 -> category = "Small numbers";
                case 4, 5, 6 -> category = "Medium numbers";
                case 7, 8, 9 -> category = "Large numbers";
                case 10 -> category = "Maximum";
                default -> category = "Out of range";
            }
            System.out.println(i + " -> " + category);
        }
        
        // Enhanced enum pattern matching
        System.out.println("\nEnhanced enum pattern matching:");
        Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.BLACK, Color.WHITE};
        
        for (Color color : colors) {
            String info;
            switch (color) {
                case RED, GREEN, BLUE -> {
                    String type = "Primary color";
                    String usage = switch (color) {
                        case RED -> "for warnings and errors";
                        case GREEN -> "for success and nature";
                        case BLUE -> "for information and calm";
                        default -> "";
                    };
                    info = type + " " + usage;
                }
                case YELLOW -> info = "Secondary color for attention";
                case BLACK, WHITE -> info = "Neutral color for contrast";
                default -> info = "Unknown color";
            }
            System.out.println(color + ": " + info);
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates instanceof pattern improvements
     */
    private static void instanceofPatterns() {
        System.out.println("6. Enhanced instanceof Patterns:");
        
        Object[] values = {
            "Hello",
            List.of(1, 2, 3, 4, 5),
            new Circle(new Point(2, 3), 7.5),
            Map.of("key1", "value1", "key2", "value2"),
            42
        };
        
        for (Object value : values) {
            // Enhanced instanceof with pattern matching
            if (value instanceof String s && s.length() > 3) {
                System.out.println("Long string: " + s.toUpperCase());
            } else if (value instanceof List<?> list && list.size() > 3) {
                System.out.println("Large list with " + list.size() + " elements");
            } else if (value instanceof Circle circle) {
                Point center = circle.center();
                double radius = circle.radius();
                if (radius > 5) {
                    System.out.println("Large circle at " + center + " with radius " + radius);
                } else {
                    System.out.println("Circle: " + value);
                }
            } else if (value instanceof Map<?, ?> map && map.size() > 1) {
                System.out.println("Map with " + map.size() + " entries");
            } else if (value instanceof Integer i && i % 2 == 0) {
                System.out.println("Even number: " + i);
            } else {
                System.out.println("Other: " + value);
            }
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates null handling in pattern matching
     */
    private static void nullHandlingInPatterns() {
        System.out.println("7. Null Handling in Patterns:");
        
        Object[] values = {
            "Valid string",
            null,
            new Person("John", 25, null),  // Person with null address
            new Person("Jane", 30, new Address("123 St", "City", "Country")),
            42
        };
        
        for (Object value : values) {
            String result;
            if (value == null) {
                result = "Null value encountered";
            } else if (value instanceof String s) {
                result = "String: " + s;
            } else if (value instanceof Person person) {
                String name = person.name();
                int age = person.age();
                Address addr = person.address();
                if (addr == null) {
                    result = "Person " + name + " (age " + age + ") with no address";
                } else {
                    result = "Person " + name + " lives in " + addr.city();
                }
            } else if (value instanceof Integer i) {
                result = "Number: " + i;
            } else {
                result = "Unknown type";
            }
            System.out.println(result);
        }
        
        // Safe navigation with pattern matching
        System.out.println("\nSafe navigation with patterns:");
        Person[] people = {
            new Person("Alice", 25, new Address("123 St", "Springfield", "USA")),
            new Person("Bob", 30, null),
            null
        };
        
        for (Person person : people) {
            String location;
            if (person == null) {
                location = "No person";
            } else if (person != null) {
                String name = person.name();
                int age = person.age();
                Address address = person.address();
                if (address == null) {
                    location = name + " has no address";
                } else {
                    String street = address.street();
                    String city = address.city();
                    String country = address.country();
                    location = name + " lives in " + city + ", " + country;
                }
            } else {
                location = "Unknown person type";
            }
            System.out.println(location);
        }
        
        System.out.println();
    }
}