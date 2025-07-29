/**
 * Example2.java
 * 
 * This program demonstrates example in Java:
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
public class Example2 {
    
    public static void main(String[] args) {
        System.out.println("=== Example 2: Constructor Chaining ===\n");
        
        // Create bank accounts using different constructors
        BankAccount account1 = new BankAccount();  // Default constructor
        BankAccount account2 = new BankAccount("123456789");  // Account number only
        BankAccount account3 = new BankAccount("987654321", 1000.0);  // Account number and balance
        BankAccount account4 = new BankAccount("555666777", 500.0, "John Doe");  // All parameters
        
        System.out.println("Account 1 (default): " + account1);
        System.out.println("Account 2 (number only): " + account2);
        System.out.println("Account 3 (number + balance): " + account3);
        System.out.println("Account 4 (all parameters): " + account4);
        
        // Demonstrate some operations
        account3.deposit(500.0);
        account4.withdraw(100.0);
        
        System.out.println("\nAfter transactions:");
        System.out.println("Account 3: " + account3);
        System.out.println("Account 4: " + account4);
    }
}

/**
 * BankAccount class demonstrating constructor chaining
 */
class BankAccount {
    private String accountNumber;
    private double balance;
    private String accountHolder;
    
    /**
     * Default constructor - sets default values
     * In Python: This would be like __init__(self, account_number="000000000", balance=0.0, holder="Unknown")
     */
    public BankAccount() {
        this("000000000", 0.0, "Unknown");
        System.out.println("   Default constructor called");
    }
    
    /**
     * Constructor with account number only - chains to default constructor
     * In Python: This would be like __init__(self, account_number, balance=0.0, holder="Unknown")
     */
    public BankAccount(String accountNumber) {
        this(accountNumber, 0.0, "Unknown");
        System.out.println("   Constructor with account number called");
    }
    
    /**
     * Constructor with account number and balance - chains to full constructor
     * In Python: This would be like __init__(self, account_number, balance, holder="Unknown")
     */
    public BankAccount(String accountNumber, double balance) {
        this(accountNumber, balance, "Unknown");
        System.out.println("   Constructor with account number and balance called");
    }
    
    /**
     * Full constructor with all parameters
     * In Python: This would be like __init__(self, account_number, balance, holder)
     */
    public BankAccount(String accountNumber, double balance, String accountHolder) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountHolder = accountHolder;
        System.out.println("   Full constructor called for account: " + accountNumber);
    }
    
    // Methods for banking operations
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("   Deposited $" + amount + " to account " + accountNumber);
        }
    }
    
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("   Withdrew $" + amount + " from account " + accountNumber);
        } else {
            System.out.println("   Insufficient funds or invalid amount for withdrawal");
        }
    }
    
    // Getter methods
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public String getAccountHolder() {
        return accountHolder;
    }
    
    @Override
    public String toString() {
        return String.format("BankAccount{number='%s', balance=$%.2f, holder='%s'}", 
                           accountNumber, balance, accountHolder);
    }
} 