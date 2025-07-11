/**
 * Example1.java - Basic Interface Implementation
 * 
 * This example demonstrates a simple payment system using interfaces.
 * In Python, this would be similar to defining abstract base classes.
 * 
 * Key concepts:
 * - Interface definition
 * - Interface implementation
 * - Polymorphism through interfaces
 */

// Interface defining payment behavior
interface PaymentMethod {
    // Interface constants (public static final by default)
    String CURRENCY = "USD";
    
    // Abstract methods that must be implemented
    boolean processPayment(double amount);
    String getPaymentInfo();
}

// Concrete class implementing the interface
class CreditCard implements PaymentMethod {
    private String cardNumber;
    private String cardHolder;
    
    public CreditCard(String cardNumber, String cardHolder) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
    }
    
    @Override
    public boolean processPayment(double amount) {
        // Simulate payment processing
        System.out.println("Processing credit card payment of $" + amount);
        System.out.println("Card: " + maskCardNumber(cardNumber));
        return true; // Assume successful
    }
    
    @Override
    public String getPaymentInfo() {
        return "Credit Card - " + cardHolder + " (" + maskCardNumber(cardNumber) + ")";
    }
    
    private String maskCardNumber(String cardNumber) {
        // Mask all but last 4 digits
        if (cardNumber.length() > 4) {
            return "*".repeat(cardNumber.length() - 4) + cardNumber.substring(cardNumber.length() - 4);
        }
        return cardNumber;
    }
}

// Another concrete class implementing the same interface
class PayPal implements PaymentMethod {
    private String email;
    
    public PayPal(String email) {
        this.email = email;
    }
    
    @Override
    public boolean processPayment(double amount) {
        // Simulate PayPal payment processing
        System.out.println("Processing PayPal payment of $" + amount);
        System.out.println("Email: " + email);
        return true; // Assume successful
    }
    
    @Override
    public String getPaymentInfo() {
        return "PayPal - " + email;
    }
}

// Main class to demonstrate the example
public class Example1 {
    public static void main(String[] args) {
        System.out.println("=== Payment System Interface Example ===\n");
        
        // Create different payment methods
        PaymentMethod creditCard = new CreditCard("1234567890123456", "John Doe");
        PaymentMethod paypal = new PayPal("john.doe@example.com");
        
        // Demonstrate polymorphism - same interface, different implementations
        System.out.println("Payment Methods:");
        System.out.println("1. " + creditCard.getPaymentInfo());
        System.out.println("2. " + paypal.getPaymentInfo());
        System.out.println();
        
        // Process payments using the interface
        double amount = 99.99;
        System.out.println("Processing payment of $" + amount + " " + PaymentMethod.CURRENCY);
        System.out.println();
        
        creditCard.processPayment(amount);
        System.out.println();
        
        paypal.processPayment(amount);
        System.out.println();
        
        // Demonstrate interface as type in collections
        PaymentMethod[] paymentMethods = {creditCard, paypal};
        System.out.println("All payment methods:");
        for (PaymentMethod method : paymentMethods) {
            System.out.println("- " + method.getPaymentInfo());
        }
    }
} 