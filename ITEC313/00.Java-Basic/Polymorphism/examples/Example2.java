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
// Interface for payment processing
interface PaymentProcessor {
    boolean processPayment(double amount);
    String getPaymentMethod();
    double getTransactionFee();
}

// Interface for refund processing
interface Refundable {
    boolean processRefund(double amount);
    String getRefundReason();
}

// Abstract class for payment base functionality
abstract class Payment {
    protected String customerId;
    protected double amount;
    protected String currency;
    
    public Payment(String customerId, double amount, String currency) {
        this.customerId = customerId;
        this.amount = amount;
        this.currency = currency;
    }
    
    // Abstract method that must be implemented by subclasses
    public abstract void validatePayment();
    
    // Concrete method
    public void displayPaymentInfo() {
        System.out.println("Customer ID: " + customerId);
        System.out.println("Amount: " + amount + " " + currency);
    }
    
    public String getCustomerId() {
        return customerId;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public String getCurrency() {
        return currency;
    }
}

// Concrete class implementing PaymentProcessor and extending Payment
class CreditCardPayment extends Payment implements PaymentProcessor, Refundable {
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    
    public CreditCardPayment(String customerId, double amount, String currency, 
                           String cardNumber, String expiryDate, String cvv) {
        super(customerId, amount, currency);
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }
    
    @Override
    public void validatePayment() {
        System.out.println("Validating credit card payment...");
        System.out.println("Card number: " + maskCardNumber(cardNumber));
        System.out.println("Expiry date: " + expiryDate);
    }
    
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing credit card payment of " + amount + " " + currency);
        // Simulate payment processing
        return Math.random() > 0.1; // 90% success rate
    }
    
    @Override
    public String getPaymentMethod() {
        return "Credit Card";
    }
    
    @Override
    public double getTransactionFee() {
        return amount * 0.025; // 2.5% fee
    }
    
    @Override
    public boolean processRefund(double amount) {
        System.out.println("Processing credit card refund of " + amount + " " + currency);
        return Math.random() > 0.05; // 95% success rate
    }
    
    @Override
    public String getRefundReason() {
        return "Customer request";
    }
    
    private String maskCardNumber(String cardNumber) {
        if (cardNumber.length() > 4) {
            return "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
        }
        return cardNumber;
    }
    
    public String getCardNumber() {
        return cardNumber;
    }
    
    public String getExpiryDate() {
        return expiryDate;
    }
}

// Concrete class implementing PaymentProcessor
class PayPalPayment extends Payment implements PaymentProcessor {
    private String email;
    private String password;
    
    public PayPalPayment(String customerId, double amount, String currency, 
                        String email, String password) {
        super(customerId, amount, currency);
        this.email = email;
        this.password = password;
    }
    
    @Override
    public void validatePayment() {
        System.out.println("Validating PayPal payment...");
        System.out.println("Email: " + email);
    }
    
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing PayPal payment of " + amount + " " + currency);
        // Simulate payment processing
        return Math.random() > 0.05; // 95% success rate
    }
    
    @Override
    public String getPaymentMethod() {
        return "PayPal";
    }
    
    @Override
    public double getTransactionFee() {
        return amount * 0.029 + 0.30; // $0.30 + 2.9%
    }
    
    public String getEmail() {
        return email;
    }
}

// Concrete class implementing PaymentProcessor and Refundable
class BankTransferPayment extends Payment implements PaymentProcessor, Refundable {
    private String accountNumber;
    private String routingNumber;
    private String bankName;
    
    public BankTransferPayment(String customerId, double amount, String currency,
                             String accountNumber, String routingNumber, String bankName) {
        super(customerId, amount, currency);
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
        this.bankName = bankName;
    }
    
    @Override
    public void validatePayment() {
        System.out.println("Validating bank transfer payment...");
        System.out.println("Bank: " + bankName);
        System.out.println("Account: " + maskAccountNumber(accountNumber));
    }
    
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing bank transfer of " + amount + " " + currency);
        // Simulate payment processing
        return Math.random() > 0.02; // 98% success rate
    }
    
    @Override
    public String getPaymentMethod() {
        return "Bank Transfer";
    }
    
    @Override
    public double getTransactionFee() {
        return 5.0; // Fixed $5 fee
    }
    
    @Override
    public boolean processRefund(double amount) {
        System.out.println("Processing bank transfer refund of " + amount + " " + currency);
        return Math.random() > 0.1; // 90% success rate
    }
    
    @Override
    public String getRefundReason() {
        return "Processing error";
    }
    
    private String maskAccountNumber(String accountNumber) {
        if (accountNumber.length() > 4) {
            return "****" + accountNumber.substring(accountNumber.length() - 4);
        }
        return accountNumber;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public String getRoutingNumber() {
        return routingNumber;
    }
    
    public String getBankName() {
        return bankName;
    }
}

// Main class demonstrating interface and abstract class polymorphism
public class Example2 {
    
    // Method demonstrating interface polymorphism
    public static void processPayment(PaymentProcessor processor, double amount) {
        System.out.println("=== Processing Payment ===");
        System.out.println("Payment Method: " + processor.getPaymentMethod());
        System.out.println("Transaction Fee: " + String.format("%.2f", processor.getTransactionFee()));
        
        boolean success = processor.processPayment(amount);
        if (success) {
            System.out.println("✅ Payment processed successfully!");
        } else {
            System.out.println("❌ Payment failed!");
        }
        System.out.println();
    }
    
    // Method demonstrating abstract class polymorphism
    public static void validatePayment(Payment payment) {
        System.out.println("=== Validating Payment ===");
        payment.displayPaymentInfo();
        payment.validatePayment();
        System.out.println();
    }
    
    // Method demonstrating refundable interface
    public static void processRefund(Refundable refundable, double amount) {
        System.out.println("=== Processing Refund ===");
        System.out.println("Refund Reason: " + refundable.getRefundReason());
        
        boolean success = refundable.processRefund(amount);
        if (success) {
            System.out.println("✅ Refund processed successfully!");
        } else {
            System.out.println("❌ Refund failed!");
        }
        System.out.println();
    }
    
    // Method demonstrating polymorphic collections
    public static void demonstratePolymorphicCollections() {
        System.out.println("=== Demonstrating Polymorphic Collections ===");
        
        // Array of PaymentProcessor (interface polymorphism)
        PaymentProcessor[] processors = {
            new CreditCardPayment("CUST001", 100.0, "USD", "1234567890123456", "12/25", "123"),
            new PayPalPayment("CUST002", 50.0, "USD", "user@example.com", "password"),
            new BankTransferPayment("CUST003", 200.0, "USD", "1234567890", "987654321", "Chase Bank")
        };
        
        // Process all payments polymorphically
        for (PaymentProcessor processor : processors) {
            processPayment(processor, 100.0);
        }
        
        // Array of Payment (abstract class polymorphism)
        Payment[] payments = {
            new CreditCardPayment("CUST004", 75.0, "USD", "6543210987654321", "06/24", "456"),
            new PayPalPayment("CUST005", 25.0, "USD", "buyer@example.com", "secret"),
            new BankTransferPayment("CUST006", 150.0, "USD", "0987654321", "123456789", "Wells Fargo")
        };
        
        // Validate all payments polymorphically
        for (Payment payment : payments) {
            validatePayment(payment);
        }
        
        // Array of Refundable (interface polymorphism)
        Refundable[] refundables = {
            new CreditCardPayment("CUST007", 0.0, "USD", "1111111111111111", "01/26", "789"),
            new BankTransferPayment("CUST008", 0.0, "USD", "1111111111", "999999999", "Bank of America")
        };
        
        // Process all refunds polymorphically
        for (Refundable refundable : refundables) {
            processRefund(refundable, 50.0);
        }
    }
    
    // Method demonstrating method overloading
    public static void demonstrateMethodOverloading() {
        System.out.println("=== Demonstrating Method Overloading ===");
        
        // Different method signatures for same method name
        displayTransactionInfo("Simple transaction");
        displayTransactionInfo("Transaction with amount", 100.0);
        displayTransactionInfo("Transaction with details", 200.0, "USD");
        
        System.out.println();
    }
    
    // Overloaded methods
    public static void displayTransactionInfo(String message) {
        System.out.println("Transaction Info: " + message);
    }
    
    public static void displayTransactionInfo(String message, double amount) {
        System.out.println("Transaction Info: " + message + " - Amount: $" + amount);
    }
    
    public static void displayTransactionInfo(String message, double amount, String currency) {
        System.out.println("Transaction Info: " + message + " - Amount: " + amount + " " + currency);
    }
    
    public static void main(String[] args) {
        System.out.println("💳 Interface and Abstract Class Polymorphism Example");
        System.out.println("===================================================");
        System.out.println();
        
        // Demonstrate different aspects of polymorphism
        demonstrateMethodOverloading();
        demonstratePolymorphicCollections();
        
        System.out.println("✅ Interface and abstract class polymorphism example completed!");
        System.out.println();
        System.out.println("Key Points:");
        System.out.println("- Interfaces provide a contract for polymorphic behavior");
        System.out.println("- Abstract classes can provide both abstract and concrete methods");
        System.out.println("- Classes can implement multiple interfaces");
        System.out.println("- Polymorphic collections can hold objects of different types");
        System.out.println("- Method overloading provides compile-time polymorphism");
    }
} 