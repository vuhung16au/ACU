import java.util.*;

/**
 * Advanced - Advanced Collections Utility Methods
 * 
 * This example demonstrates advanced Collections utility methods
 * including complex operations, performance considerations, and real-world scenarios.
 * 
 * Python developers note:
 * - Java's Collections utilities are optimized for performance
 * - Some operations modify the original collection (in-place operations)
 * - Java provides more specialized methods than Python's built-in functions
 * - Understanding when to use which method is crucial for performance
 */
public class Advanced {
    
    public static void main(String[] args) {
        System.out.println("=== Advanced Collections Utility Methods ===\n");
        
        demonstrateAdvancedSorting();
        demonstratePerformanceOptimizations();
        demonstrateRealWorldScenarios();
        demonstrateCollectionTransformations();
    }
    
    /**
     * Demonstrates advanced sorting techniques
     * Python equivalent: sorted(list, key=function, reverse=True)
     */
    public static void demonstrateAdvancedSorting() {
        System.out.println("1. ADVANCED SORTING TECHNIQUES");
        System.out.println("==============================");
        
        // Create a list of products with multiple attributes
        List<Product> products = new ArrayList<>();
        products.add(new Product("Laptop", 1200.0, 4.5, 50));
        products.add(new Product("Mouse", 25.0, 4.2, 100));
        products.add(new Product("Keyboard", 80.0, 4.8, 30));
        products.add(new Product("Monitor", 300.0, 4.6, 20));
        products.add(new Product("Headphones", 150.0, 4.3, 75));
        
        System.out.println("Original products: " + products);
        
        // Sort by price (ascending)
        Collections.sort(products, (p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()));
        System.out.println("Sorted by price (ascending): " + products);
        
        // Sort by rating (descending), then by name
        Collections.sort(products, (p1, p2) -> {
            int ratingCompare = Double.compare(p2.getRating(), p1.getRating());
            return ratingCompare != 0 ? ratingCompare : p1.getName().compareTo(p2.getName());
        });
        System.out.println("Sorted by rating (descending), then by name: " + products);
        
        // Sort by stock level (ascending), then by price (descending)
        Collections.sort(products, (p1, p2) -> {
            int stockCompare = Integer.compare(p1.getStock(), p2.getStock());
            return stockCompare != 0 ? stockCompare : Double.compare(p2.getPrice(), p1.getPrice());
        });
        System.out.println("Sorted by stock (ascending), then by price (descending): " + products);
        
        System.out.println();
    }
    
    /**
     * Demonstrates performance optimizations and considerations
     * Python equivalent: Understanding when to use different data structures
     */
    public static void demonstratePerformanceOptimizations() {
        System.out.println("2. PERFORMANCE OPTIMIZATIONS");
        System.out.println("=============================");
        
        // Demonstrate the importance of sorted lists for binary search
        List<Integer> unsortedList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            unsortedList.add((int)(Math.random() * 1000));
        }
        
        List<Integer> sortedList = new ArrayList<>(unsortedList);
        Collections.sort(sortedList);
        
        int searchValue = 500;
        
        // Binary search on sorted list (fast)
        long startTime = System.nanoTime();
        int sortedIndex = Collections.binarySearch(sortedList, searchValue);
        long sortedTime = System.nanoTime() - startTime;
        
        // Linear search on unsorted list (slow)
        startTime = System.nanoTime();
        int unsortedIndex = unsortedList.indexOf(searchValue);
        long unsortedTime = System.nanoTime() - startTime;
        
        System.out.println("Searching for value: " + searchValue);
        System.out.println("Binary search on sorted list: " + sortedTime + " nanoseconds");
        System.out.println("Linear search on unsorted list: " + unsortedTime + " nanoseconds");
        System.out.println("Performance ratio: " + (unsortedTime / (double)sortedTime) + "x slower");
        
        // Demonstrate shuffle performance
        List<Integer> largeList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            largeList.add(i);
        }
        
        startTime = System.nanoTime();
        Collections.shuffle(largeList);
        long shuffleTime = System.nanoTime() - startTime;
        
        System.out.println("\nShuffling 10,000 elements: " + shuffleTime + " nanoseconds");
        
        System.out.println();
    }
    
    /**
     * Demonstrates real-world scenarios using Collections utilities
     * Python equivalent: Practical applications of list operations
     */
    public static void demonstrateRealWorldScenarios() {
        System.out.println("3. REAL-WORLD SCENARIOS");
        System.out.println("========================");
        
        // Scenario 1: Student grade management
        List<StudentGrade> grades = new ArrayList<>();
        grades.add(new StudentGrade("Alice", 85));
        grades.add(new StudentGrade("Bob", 92));
        grades.add(new StudentGrade("Charlie", 78));
        grades.add(new StudentGrade("David", 95));
        grades.add(new StudentGrade("Eve", 88));
        
        System.out.println("Student grades: " + grades);
        
        // Find top 3 students
        Collections.sort(grades, (g1, g2) -> Integer.compare(g2.getGrade(), g1.getGrade()));
        System.out.println("Top 3 students: " + grades.subList(0, Math.min(3, grades.size())));
        
        // Find average grade
        double average = grades.stream().mapToInt(StudentGrade::getGrade).average().orElse(0.0);
        System.out.println("Average grade: " + average);
        
        // Scenario 2: Inventory management
        List<InventoryItem> inventory = new ArrayList<>();
        inventory.add(new InventoryItem("Laptop", 10, 1200.0));
        inventory.add(new InventoryItem("Mouse", 50, 25.0));
        inventory.add(new InventoryItem("Keyboard", 15, 80.0));
        inventory.add(new InventoryItem("Monitor", 5, 300.0));
        
        System.out.println("\nInventory: " + inventory);
        
        // Sort by value (price * quantity)
        Collections.sort(inventory, (i1, i2) -> Double.compare(i2.getValue(), i1.getValue()));
        System.out.println("Sorted by total value: " + inventory);
        
        // Find low stock items (quantity < 10)
        List<InventoryItem> lowStock = new ArrayList<>();
        for (InventoryItem item : inventory) {
            if (item.getQuantity() < 10) {
                lowStock.add(item);
            }
        }
        System.out.println("Low stock items: " + lowStock);
        
        System.out.println();
    }
    
    /**
     * Demonstrates collection transformations and manipulations
     * Python equivalent: List comprehensions and transformations
     */
    public static void demonstrateCollectionTransformations() {
        System.out.println("4. COLLECTION TRANSFORMATIONS");
        System.out.println("=============================");
        
        // Create a deck of cards
        List<String> deck = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        
        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(rank + " of " + suit);
            }
        }
        
        System.out.println("Original deck size: " + deck.size());
        System.out.println("First 5 cards: " + deck.subList(0, 5));
        
        // Shuffle the deck
        Collections.shuffle(deck);
        System.out.println("After shuffling, first 5 cards: " + deck.subList(0, 5));
        
        // Deal cards (simulate dealing to 4 players)
        List<List<String>> hands = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            hands.add(new ArrayList<>());
        }
        
        for (int i = 0; i < 13; i++) { // Deal 13 cards each
            for (int j = 0; j < 4; j++) {
                hands.get(j).add(deck.get(i * 4 + j));
            }
        }
        
        System.out.println("\nDealt hands:");
        for (int i = 0; i < hands.size(); i++) {
            Collections.sort(hands.get(i)); // Sort each hand
            System.out.println("Player " + (i + 1) + ": " + hands.get(i));
        }
        
        // Demonstrate rotate operation
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
        System.out.println("\nOriginal numbers: " + numbers);
        
        // Rotate by 3 positions
        Collections.rotate(numbers, 3);
        System.out.println("After rotating by 3: " + numbers);
        
        // Rotate back
        Collections.rotate(numbers, -3);
        System.out.println("After rotating back by -3: " + numbers);
        
        System.out.println();
    }
    
    /**
     * Product class for demonstration
     */
    static class Product {
        private String name;
        private double price;
        private double rating;
        private int stock;
        
        public Product(String name, double price, double rating, int stock) {
            this.name = name;
            this.price = price;
            this.rating = rating;
            this.stock = stock;
        }
        
        public String getName() { return name; }
        public double getPrice() { return price; }
        public double getRating() { return rating; }
        public int getStock() { return stock; }
        
        @Override
        public String toString() {
            return name + "($" + price + ", " + rating + "★, " + stock + " in stock)";
        }
    }
    
    /**
     * StudentGrade class for demonstration
     */
    static class StudentGrade {
        private String name;
        private int grade;
        
        public StudentGrade(String name, int grade) {
            this.name = name;
            this.grade = grade;
        }
        
        public String getName() { return name; }
        public int getGrade() { return grade; }
        
        @Override
        public String toString() {
            return name + "(" + grade + ")";
        }
    }
    
    /**
     * InventoryItem class for demonstration
     */
    static class InventoryItem {
        private String name;
        private int quantity;
        private double price;
        
        public InventoryItem(String name, int quantity, double price) {
            this.name = name;
            this.quantity = quantity;
            this.price = price;
        }
        
        public String getName() { return name; }
        public int getQuantity() { return quantity; }
        public double getPrice() { return price; }
        public double getValue() { return quantity * price; }
        
        @Override
        public String toString() {
            return name + "(" + quantity + " @ $" + price + ")";
        }
    }
} 