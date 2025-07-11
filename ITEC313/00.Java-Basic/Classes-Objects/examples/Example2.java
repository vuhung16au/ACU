/**
 * Example 2: Advanced Class and Object Concepts
 * 
 * This example demonstrates more advanced object-oriented concepts:
 * - Object arrays and collections
 * - Method overloading
 * - Object comparison and equality
 * - Static vs instance methods
 * 
 * Key differences from Python:
 * - Java arrays have fixed size
 * - Java uses method overloading instead of default parameters
 * - Java requires explicit equals() method for object comparison
 */

public class Example2 {
    
    public static void main(String[] args) {
        System.out.println("=== Example 2: Advanced Class and Object Concepts ===\n");
        
        // Demonstrate object arrays
        demonstrateObjectArrays();
        
        // Demonstrate method overloading
        demonstrateMethodOverloading();
        
        // Demonstrate object comparison
        demonstrateObjectComparison();
        
        // Demonstrate static vs instance methods
        demonstrateStaticMethods();
        
        System.out.println("\n=== Example 2 Complete ===");
    }
    
    public static void demonstrateObjectArrays() {
        System.out.println("1. OBJECT ARRAYS");
        System.out.println("=================");
        
        // Create an array of Book objects
        // In Python: books = [Book("Title1", "Author1"), Book("Title2", "Author2")]
        Book[] books = new Book[3];
        
        books[0] = new Book("Java Programming", "John Doe", 29.99);
        books[1] = new Book("Python Basics", "Jane Smith", 24.99);
        books[2] = new Book("Data Structures", "Bob Johnson", 34.99);
        
        System.out.println("Books in library:");
        for (int i = 0; i < books.length; i++) {
            System.out.printf("  %d. %s%n", i + 1, books[i].getTitle());
        }
        
        // Demonstrate array operations
        System.out.println("\nBook details:");
        for (Book book : books) {
            book.displayInfo();
        }
        
        // Find most expensive book
        Book mostExpensive = books[0];
        for (Book book : books) {
            if (book.getPrice() > mostExpensive.getPrice()) {
                mostExpensive = book;
            }
        }
        System.out.printf("Most expensive book: %s ($%.2f)%n%n", 
                         mostExpensive.getTitle(), mostExpensive.getPrice());
    }
    
    public static void demonstrateMethodOverloading() {
        System.out.println("2. METHOD OVERLOADING");
        System.out.println("=====================");
        
        Calculator calc = new Calculator();
        
        // Different method calls based on parameters
        System.out.println("Adding integers: " + calc.add(5, 3));
        System.out.println("Adding doubles: " + calc.add(5.5, 3.2));
        System.out.println("Adding three integers: " + calc.add(1, 2, 3));
        System.out.println("Adding strings: " + calc.add("Hello", " World"));
        
        System.out.println();
    }
    
    public static void demonstrateObjectComparison() {
        System.out.println("3. OBJECT COMPARISON");
        System.out.println("====================");
        
        Book book1 = new Book("Java Programming", "John Doe", 29.99);
        Book book2 = new Book("Java Programming", "John Doe", 29.99);
        Book book3 = new Book("Python Basics", "Jane Smith", 24.99);
        
        // Demonstrate reference equality vs content equality
        System.out.println("book1 == book2: " + (book1 == book2));  // Reference comparison
        System.out.println("book1.equals(book2): " + book1.equals(book2));  // Content comparison
        System.out.println("book1.equals(book3): " + book1.equals(book3));
        
        System.out.println();
    }
    
    public static void demonstrateStaticMethods() {
        System.out.println("4. STATIC VS INSTANCE METHODS");
        System.out.println("=============================");
        
        // Static method can be called without object
        System.out.println("Total books created: " + Book.getTotalBooks());
        
        // Create some books
        Book book1 = new Book("Book 1", "Author 1", 10.0);
        Book book2 = new Book("Book 2", "Author 2", 15.0);
        
        System.out.println("Total books after creation: " + Book.getTotalBooks());
        
        // Instance method requires object
        book1.displayInfo();
        book2.displayInfo();
        
        System.out.println();
    }
}

/**
 * Book class demonstrating advanced OOP concepts
 */
class Book {
    private String title;
    private String author;
    private double price;
    private static int totalBooks = 0;  // Static variable shared across all instances
    
    // Constructor
    public Book(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
        totalBooks++;  // Increment static counter
    }
    
    // Getter methods
    public String getTitle() {
        return title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public double getPrice() {
        return price;
    }
    
    // Setter methods
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        }
    }
    
    // Instance method - requires object to call
    public void displayInfo() {
        System.out.printf("  Title: %s%n", title);
        System.out.printf("  Author: %s%n", author);
        System.out.printf("  Price: $%.2f%n", price);
        System.out.println();
    }
    
    // Static method - can be called without object
    public static int getTotalBooks() {
        return totalBooks;
    }
    
    // Override equals method for content comparison
    // In Python: def __eq__(self, other): return self.title == other.title and self.author == other.author
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;  // Same reference
        if (obj == null || getClass() != obj.getClass()) return false;  // Different class
        
        Book other = (Book) obj;
        return title.equals(other.title) && author.equals(other.author);
    }
    
    // Override toString method
    // In Python: def __str__(self): return f"Book({self.title}, {self.author})"
    @Override
    public String toString() {
        return "Book{title='" + title + "', author='" + author + "', price=" + price + "}";
    }
}

/**
 * Calculator class demonstrating method overloading
 * 
 * In Python, you would use default parameters:
 * def add(self, a, b, c=None):
 *     if c is None:
 *         return a + b
 *     return a + b + c
 */
class Calculator {
    
    // Method overloading - same name, different parameters
    public int add(int a, int b) {
        return a + b;
    }
    
    public double add(double a, double b) {
        return a + b;
    }
    
    public int add(int a, int b, int c) {
        return a + b + c;
    }
    
    public String add(String a, String b) {
        return a + b;
    }
} 