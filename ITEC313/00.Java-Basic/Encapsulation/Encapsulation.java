/**
 * Encapsulation.java
 * 
 * This program demonstrates encapsulation in Java - one of the four fundamental
 * principles of Object-Oriented Programming (OOP).
 * 
 * Encapsulation combines data (fields) and methods that operate on that data
 * into a single unit (class) and hides the internal state from outside access.
 * 
 * Key Concepts:
 * - Private fields (data hiding)
 * - Public getter and setter methods
 * - Controlled access to object state
 * - Information hiding and data protection
 * 
 * Python Comparison:
 * - In Python, encapsulation is achieved through naming conventions (_private)
 * - Java enforces encapsulation through access modifiers (private, public, protected)
 * - Python doesn't have true private fields like Java
 */

public class Encapsulation {
    
    public static void main(String[] args) {
        System.out.println("=== Java Encapsulation Examples ===\n");
        
        // Example 1: Basic Encapsulation with Student class
        demonstrateBasicEncapsulation();
        
        // Example 2: Bank Account with validation
        demonstrateBankAccountEncapsulation();
        
        // Example 3: Temperature converter with controlled access
        demonstrateTemperatureConverter();
        
        // Example 4: Complex object with nested encapsulation
        demonstrateComplexEncapsulation();
    }
    
    /**
     * Demonstrates basic encapsulation principles
     * 
     * Python Comparison:
     * - Python: class Student: def __init__(self): self._name = "" (convention only)
     * - Java: private String name; (enforced by compiler)
     */
    private static void demonstrateBasicEncapsulation() {
        System.out.println("1. Basic Encapsulation Example:");
        System.out.println("================================");
        
        // Create a student object
        Student student = new Student();
        
        // Set values using public methods (controlled access)
        student.setName("Alice Johnson");
        student.setAge(20);
        student.setStudentId("S12345");
        
        // Display student information using getters
        System.out.println("Student Information:");
        System.out.println("Name: " + student.getName());
        System.out.println("Age: " + student.getAge());
        System.out.println("Student ID: " + student.getStudentId());
        
        // Try to access private field directly (this would cause compilation error)
        // student.name = "Bob"; // This line would not compile!
        
        System.out.println();
    }
    
    /**
     * Demonstrates encapsulation with validation
     * 
     * Python Comparison:
     * - Python: Use @property decorator for validation
     * - Java: Use setter methods with validation logic
     */
    private static void demonstrateBankAccountEncapsulation() {
        System.out.println("2. Bank Account with Validation:");
        System.out.println("================================");
        
        BankAccount account = new BankAccount();
        
        // Valid operations
        account.setAccountNumber("1234567890");
        account.setBalance(1000.0);
        account.deposit(500.0);
        account.withdraw(200.0);
        
        System.out.println("Account Number: " + account.getAccountNumber());
        System.out.println("Current Balance: $" + account.getBalance());
        
        // Invalid operations (will be prevented by validation)
        account.setBalance(-1000.0); // Should be prevented
        account.withdraw(2000.0);    // Should be prevented
        
        System.out.println("Final Balance: $" + account.getBalance());
        System.out.println();
    }
    
    /**
     * Demonstrates encapsulation with computed properties
     * 
     * Python Comparison:
     * - Python: Use @property for computed attributes
     * - Java: Use getter methods that compute values
     */
    private static void demonstrateTemperatureConverter() {
        System.out.println("3. Temperature Converter:");
        System.out.println("========================");
        
        TemperatureConverter converter = new TemperatureConverter();
        
        // Set temperature in Celsius
        converter.setCelsius(25.0);
        
        // Get temperature in different units (computed properties)
        System.out.println("Temperature in Celsius: " + converter.getCelsius() + "°C");
        System.out.println("Temperature in Fahrenheit: " + converter.getFahrenheit() + "°F");
        System.out.println("Temperature in Kelvin: " + converter.getKelvin() + "K");
        
        // Set temperature in Fahrenheit
        converter.setFahrenheit(98.6);
        System.out.println("\nAfter setting to 98.6°F:");
        System.out.println("Celsius: " + converter.getCelsius() + "°C");
        System.out.println("Fahrenheit: " + converter.getFahrenheit() + "°F");
        System.out.println("Kelvin: " + converter.getKelvin() + "K");
        
        System.out.println();
    }
    
    /**
     * Demonstrates complex encapsulation with nested objects
     * 
     * Python Comparison:
     * - Python: Nested objects with property decorators
     * - Java: Nested objects with private fields and public methods
     */
    private static void demonstrateComplexEncapsulation() {
        System.out.println("4. Complex Encapsulation (Library System):");
        System.out.println("==========================================");
        
        // Create a library with encapsulated book management
        Library library = new Library();
        
        // Add books to the library
        library.addBook("The Great Gatsby", "F. Scott Fitzgerald", "978-0743273565");
        library.addBook("To Kill a Mockingbird", "Harper Lee", "978-0446310789");
        library.addBook("1984", "George Orwell", "978-0451524935");
        
        // Display library information
        System.out.println("Library Name: " + library.getName());
        System.out.println("Total Books: " + library.getTotalBooks());
        System.out.println("Available Books: " + library.getAvailableBooks());
        
        // Check out a book
        boolean checkoutResult = library.checkoutBook("The Great Gatsby");
        System.out.println("Checkout 'The Great Gatsby': " + (checkoutResult ? "Success" : "Failed"));
        
        // Try to check out the same book again
        checkoutResult = library.checkoutBook("The Great Gatsby");
        System.out.println("Checkout 'The Great Gatsby' again: " + (checkoutResult ? "Success" : "Failed"));
        
        // Return a book
        boolean returnResult = library.returnBook("The Great Gatsby");
        System.out.println("Return 'The Great Gatsby': " + (returnResult ? "Success" : "Failed"));
        
        System.out.println("Final Available Books: " + library.getAvailableBooks());
        System.out.println();
    }
}

/**
 * Student class demonstrating basic encapsulation
 * 
 * Python Comparison:
 * - Python: class Student: def __init__(self): self._name = ""
 * - Java: private fields with public getters/setters
 */
class Student {
    // Private fields - cannot be accessed directly from outside the class
    // In Python, this would be self._name (convention only, not enforced)
    private String name;
    private int age;
    private String studentId;
    
    // Constructor
    public Student() {
        this.name = "";
        this.age = 0;
        this.studentId = "";
    }
    
    // Public getter methods - provide controlled access to private fields
    public String getName() {
        return name;
    }
    
    public int getAge() {
        return age;
    }
    
    public String getStudentId() {
        return studentId;
    }
    
    // Public setter methods - provide controlled modification of private fields
    public void setName(String name) {
        // Validation can be added here
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
    }
    
    public void setAge(int age) {
        // Validation: age must be positive and reasonable
        if (age >= 0 && age <= 120) {
            this.age = age;
        }
    }
    
    public void setStudentId(String studentId) {
        if (studentId != null && !studentId.trim().isEmpty()) {
            this.studentId = studentId;
        }
    }
}

/**
 * BankAccount class demonstrating encapsulation with validation
 * 
 * Python Comparison:
 * - Python: Use @property decorator for validation
 * - Java: Use setter methods with validation logic
 */
class BankAccount {
    private String accountNumber;
    private double balance;
    
    public BankAccount() {
        this.accountNumber = "";
        this.balance = 0.0;
    }
    
    // Getters
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public double getBalance() {
        return balance;
    }
    
    // Setters with validation
    public void setAccountNumber(String accountNumber) {
        if (accountNumber != null && accountNumber.length() >= 10) {
            this.accountNumber = accountNumber;
        }
    }
    
    public void setBalance(double balance) {
        // Validation: balance cannot be negative
        if (balance >= 0) {
            this.balance = balance;
            System.out.println("Balance set to: $" + balance);
        } else {
            System.out.println("Error: Cannot set negative balance");
        }
    }
    
    // Business methods that use private fields
    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            System.out.println("Deposited: $" + amount);
        } else {
            System.out.println("Error: Invalid deposit amount");
        }
    }
    
    public void withdraw(double amount) {
        if (amount > 0 && amount <= this.balance) {
            this.balance -= amount;
            System.out.println("Withdrawn: $" + amount);
        } else {
            System.out.println("Error: Insufficient funds or invalid amount");
        }
    }
}

/**
 * TemperatureConverter class demonstrating computed properties
 * 
 * Python Comparison:
 * - Python: Use @property for computed attributes
 * - Java: Use getter methods that compute values
 */
class TemperatureConverter {
    // Store temperature in Celsius (internal representation)
    private double celsius;
    
    public TemperatureConverter() {
        this.celsius = 0.0;
    }
    
    // Getter for Celsius (direct access)
    public double getCelsius() {
        return celsius;
    }
    
    // Setter for Celsius
    public void setCelsius(double celsius) {
        this.celsius = celsius;
    }
    
    // Computed property: Fahrenheit (calculated from Celsius)
    public double getFahrenheit() {
        return (celsius * 9.0 / 5.0) + 32.0;
    }
    
    // Setter for Fahrenheit (converts to Celsius for storage)
    public void setFahrenheit(double fahrenheit) {
        this.celsius = (fahrenheit - 32.0) * 5.0 / 9.0;
    }
    
    // Computed property: Kelvin (calculated from Celsius)
    public double getKelvin() {
        return celsius + 273.15;
    }
    
    // Setter for Kelvin (converts to Celsius for storage)
    public void setKelvin(double kelvin) {
        this.celsius = kelvin - 273.15;
    }
}

/**
 * Book class for the library system
 */
class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean isAvailable;
    
    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;
    }
    
    // Getters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public boolean isAvailable() { return isAvailable; }
    
    // Setters
    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }
}

/**
 * Library class demonstrating complex encapsulation
 * 
 * Python Comparison:
 * - Python: Use list comprehension and property decorators
 * - Java: Use private collections with public methods
 */
class Library {
    private String name;
    private java.util.List<Book> books;
    
    public Library() {
        this.name = "Central Library";
        this.books = new java.util.ArrayList<>();
    }
    
    // Getters
    public String getName() {
        return name;
    }
    
    public int getTotalBooks() {
        return books.size();
    }
    
    public int getAvailableBooks() {
        // Count available books (computed property)
        int count = 0;
        for (Book book : books) {
            if (book.isAvailable()) {
                count++;
            }
        }
        return count;
    }
    
    // Business methods
    public void addBook(String title, String author, String isbn) {
        Book book = new Book(title, author, isbn);
        books.add(book);
        System.out.println("Added book: " + title);
    }
    
    public boolean checkoutBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equals(title) && book.isAvailable()) {
                book.setAvailable(false);
                System.out.println("Checked out: " + title);
                return true;
            }
        }
        System.out.println("Book not available: " + title);
        return false;
    }
    
    public boolean returnBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equals(title) && !book.isAvailable()) {
                book.setAvailable(true);
                System.out.println("Returned: " + title);
                return true;
            }
        }
        System.out.println("Book not found or already returned: " + title);
        return false;
    }
} 