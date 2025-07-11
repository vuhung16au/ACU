/**
 * Advanced.java
 * 
 * Complex encapsulation example: Shopping Cart system
 * Demonstrates encapsulation with nested objects, collections, and complex business logic
 * 
 * Python Comparison:
 * - Python: Use nested classes and property decorators
 * - Java: Use private collections and public methods for complex operations
 */

import java.util.ArrayList;
import java.util.List;

public class Advanced {
    public static void main(String[] args) {
        System.out.println("=== Advanced: Shopping Cart Encapsulation ===\n");
        
        // Create a shopping cart
        ShoppingCart cart = new ShoppingCart();
        
        // Add items to the cart
        System.out.println("--- Adding Items ---");
        cart.addItem("Laptop", 999.99, 1);
        cart.addItem("Mouse", 29.99, 2);
        cart.addItem("Keyboard", 89.99, 1);
        cart.addItem("Monitor", 299.99, 1);
        
        // Display cart contents
        System.out.println("\n--- Cart Contents ---");
        cart.displayItems();
        
        // Update quantities
        System.out.println("\n--- Updating Quantities ---");
        cart.updateQuantity("Mouse", 3);
        cart.updateQuantity("Laptop", 0); // Remove item
        
        // Display updated cart
        System.out.println("\n--- Updated Cart ---");
        cart.displayItems();
        
        // Apply discount
        System.out.println("\n--- Applying Discount ---");
        cart.applyDiscount(0.10); // 10% discount
        
        // Final summary
        System.out.println("\n--- Final Summary ---");
        System.out.println("Subtotal: $" + String.format("%.2f", cart.getSubtotal()));
        System.out.println("Discount: $" + String.format("%.2f", cart.getDiscount()));
        System.out.println("Total: $" + String.format("%.2f", cart.getTotal()));
        System.out.println("Item Count: " + cart.getItemCount());
        
        // Clear cart
        System.out.println("\n--- Clearing Cart ---");
        cart.clear();
        System.out.println("Cart is empty: " + cart.isEmpty());
    }
}

/**
 * Product class representing a single product
 */
class Product {
    private String name;
    private double price;
    private int quantity;
    
    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    
    // Getters
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    
    // Setters with validation
    public void setQuantity(int quantity) {
        if (quantity >= 0) {
            this.quantity = quantity;
        }
    }
    
    // Computed properties
    public double getTotalPrice() {
        return price * quantity;
    }
    
    @Override
    public String toString() {
        return String.format("%s - $%.2f x %d = $%.2f", 
                           name, price, quantity, getTotalPrice());
    }
}

/**
 * ShoppingCart class demonstrating complex encapsulation
 */
class ShoppingCart {
    private List<Product> items;
    private double discountRate;
    
    public ShoppingCart() {
        this.items = new ArrayList<>();
        this.discountRate = 0.0;
    }
    
    // Getters
    public int getItemCount() {
        return items.size();
    }
    
    public boolean isEmpty() {
        return items.isEmpty();
    }
    
    public double getSubtotal() {
        double subtotal = 0.0;
        for (Product item : items) {
            subtotal += item.getTotalPrice();
        }
        return subtotal;
    }
    
    public double getDiscount() {
        return getSubtotal() * discountRate;
    }
    
    public double getTotal() {
        return getSubtotal() - getDiscount();
    }
    
    // Business logic methods
    public void addItem(String name, double price, int quantity) {
        if (price < 0 || quantity <= 0) {
            System.out.println("Error: Invalid price or quantity");
            return;
        }
        
        // Check if item already exists
        for (Product item : items) {
            if (item.getName().equals(name)) {
                // Update existing item
                int newQuantity = item.getQuantity() + quantity;
                item.setQuantity(newQuantity);
                System.out.println("Updated quantity for " + name + " to " + newQuantity);
                return;
            }
        }
        
        // Add new item
        Product newItem = new Product(name, price, quantity);
        items.add(newItem);
        System.out.println("Added " + quantity + "x " + name);
    }
    
    public void updateQuantity(String name, int newQuantity) {
        for (int i = 0; i < items.size(); i++) {
            Product item = items.get(i);
            if (item.getName().equals(name)) {
                if (newQuantity <= 0) {
                    // Remove item
                    items.remove(i);
                    System.out.println("Removed " + name + " from cart");
                } else {
                    // Update quantity
                    item.setQuantity(newQuantity);
                    System.out.println("Updated " + name + " quantity to " + newQuantity);
                }
                return;
            }
        }
        System.out.println("Item " + name + " not found in cart");
    }
    
    public void removeItem(String name) {
        updateQuantity(name, 0);
    }
    
    public void applyDiscount(double discountRate) {
        if (discountRate >= 0.0 && discountRate <= 1.0) {
            this.discountRate = discountRate;
            System.out.println("Applied " + (discountRate * 100) + "% discount");
        } else {
            System.out.println("Error: Invalid discount rate (must be 0.0 to 1.0)");
        }
    }
    
    public void clear() {
        items.clear();
        discountRate = 0.0;
        System.out.println("Cart cleared");
    }
    
    public void displayItems() {
        if (items.isEmpty()) {
            System.out.println("Cart is empty");
            return;
        }
        
        System.out.println("Cart Items:");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i));
        }
    }
    
    // Advanced methods
    public Product findMostExpensiveItem() {
        if (items.isEmpty()) {
            return null;
        }
        
        Product mostExpensive = items.get(0);
        for (Product item : items) {
            if (item.getTotalPrice() > mostExpensive.getTotalPrice()) {
                mostExpensive = item;
            }
        }
        return mostExpensive;
    }
    
    public double getAverageItemPrice() {
        if (items.isEmpty()) {
            return 0.0;
        }
        
        double totalPrice = 0.0;
        int totalQuantity = 0;
        
        for (Product item : items) {
            totalPrice += item.getTotalPrice();
            totalQuantity += item.getQuantity();
        }
        
        return totalQuantity > 0 ? totalPrice / totalQuantity : 0.0;
    }
} 