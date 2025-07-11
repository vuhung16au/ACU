/**
 * Example 1: Basic Inheritance - Bank Account Hierarchy
 * 
 * This example demonstrates:
 * - Basic inheritance with 'extends' keyword
 * - Method overriding
 * - Constructor chaining
 * 
 * Key Differences from Python:
 * - Java uses 'extends' instead of parentheses
 * - Java requires explicit 'super()' calls
 * - Java uses '@Override' annotation
 */

// Base class for all bank accounts
class BankAccount {
    protected String accountNumber;
    protected double balance;
    protected String owner;
    
    public BankAccount(String accountNumber, String owner, double initialBalance) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = initialBalance;
    }
    
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("  Deposited: $" + amount);
        }
    }
    
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("  Withdrawn: $" + amount);
        } else {
            System.out.println("  Insufficient funds or invalid amount");
        }
    }
    
    public void displayInfo() {
        System.out.println("  Account: " + accountNumber);
        System.out.println("  Owner: " + owner);
        System.out.println("  Balance: $" + balance);
    }
}

// Savings account extends bank account
class SavingsAccount extends BankAccount {
    private double interestRate;
    
    public SavingsAccount(String accountNumber, String owner, double initialBalance, double interestRate) {
        super(accountNumber, owner, initialBalance);  // Call parent constructor
        this.interestRate = interestRate;
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();  // Call parent method
        System.out.println("  Interest Rate: " + (interestRate * 100) + "%");
    }
    
    public void addInterest() {
        double interest = balance * interestRate;
        balance += interest;
        System.out.println("  Interest added: $" + interest);
    }
}

// Checking account extends bank account
class CheckingAccount extends BankAccount {
    private int transactionCount;
    private static final int FREE_TRANSACTIONS = 3;
    private static final double TRANSACTION_FEE = 2.0;
    
    public CheckingAccount(String accountNumber, String owner, double initialBalance) {
        super(accountNumber, owner, initialBalance);
        this.transactionCount = 0;
    }
    
    @Override
    public void withdraw(double amount) {
        super.withdraw(amount);  // Call parent method
        transactionCount++;
        applyTransactionFee();
    }
    
    @Override
    public void deposit(double amount) {
        super.deposit(amount);  // Call parent method
        transactionCount++;
        applyTransactionFee();
    }
    
    private void applyTransactionFee() {
        if (transactionCount > FREE_TRANSACTIONS) {
            balance -= TRANSACTION_FEE;
            System.out.println("  Transaction fee: $" + TRANSACTION_FEE);
        }
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("  Transactions: " + transactionCount);
    }
}

// Main class to demonstrate the inheritance
public class Example1 {
    public static void main(String[] args) {
        System.out.println("=== Bank Account Inheritance Example ===\n");
        
        // Create different types of accounts
        BankAccount basicAccount = new BankAccount("BA001", "John Doe", 1000.0);
        SavingsAccount savings = new SavingsAccount("SA001", "Jane Smith", 5000.0, 0.05);
        CheckingAccount checking = new CheckingAccount("CA001", "Bob Johnson", 2000.0);
        
        // Demonstrate basic account operations
        System.out.println("Basic Bank Account:");
        basicAccount.displayInfo();
        basicAccount.deposit(500.0);
        basicAccount.withdraw(200.0);
        basicAccount.displayInfo();
        
        System.out.println("\nSavings Account:");
        savings.displayInfo();
        savings.deposit(1000.0);
        savings.addInterest();
        savings.displayInfo();
        
        System.out.println("\nChecking Account:");
        checking.displayInfo();
        checking.deposit(300.0);
        checking.withdraw(100.0);
        checking.withdraw(50.0);
        checking.displayInfo();
        
        System.out.println("\n=== Example Complete ===");
    }
} 