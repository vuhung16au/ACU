/**
 * Advanced: Real-World If-Else Applications
 * 
 * This example demonstrates complex real-world applications of if-else conditions:
 * - E-commerce pricing logic
 * - Banking transaction validation
 * - Weather-based recommendations
 * - Game logic implementation
 * 
 * Course: ITEC313 - Object-Oriented Programming
 * Institution: Australian Catholic University (ACU)
 */

public class Advanced {
    
    public static void main(String[] args) {
        System.out.println("=== Advanced: Real-World If-Else Applications ===\n");
        
        // Example 1: E-commerce pricing system
        demonstrateEcommercePricing();
        
        // Example 2: Banking transaction validation
        demonstrateBankingValidation();
        
        // Example 3: Weather-based activity recommendations
        demonstrateWeatherRecommendations();
        
        // Example 4: Simple game logic
        demonstrateGameLogic();
        
        System.out.println("=== Advanced Examples Complete ===");
    }
    
    /**
     * Demonstrates complex e-commerce pricing logic
     * Shows how businesses use conditional logic for pricing
     */
    public static void demonstrateEcommercePricing() {
        System.out.println("1. E-commerce Pricing System");
        System.out.println("=============================");
        
        double basePrice = 100.0;
        String customerType = "VIP";
        boolean isHoliday = true;
        boolean isFirstTime = false;
        int quantity = 3;
        
        System.out.printf("Base Price: $%.2f%n", basePrice);
        System.out.printf("Customer Type: %s%n", customerType);
        System.out.printf("Holiday: %b, First Time: %b, Quantity: %d%n", 
                         isHoliday, isFirstTime, quantity);
        
        double finalPrice = basePrice;
        double discount = 0.0;
        
        // Quantity discount
        if (quantity >= 10) {
            discount += 0.15; // 15% for bulk orders
        } else if (quantity >= 5) {
            discount += 0.10; // 10% for medium orders
        } else if (quantity >= 2) {
            discount += 0.05; // 5% for small orders
        }
        
        // Customer type discount
        if (customerType.equals("VIP")) {
            discount += 0.10; // 10% VIP discount
        } else if (customerType.equals("Regular")) {
            discount += 0.05; // 5% regular customer discount
        }
        
        // Holiday discount
        if (isHoliday) {
            discount += 0.05; // 5% holiday discount
        }
        
        // First-time customer bonus
        if (isFirstTime) {
            discount += 0.08; // 8% first-time discount
        }
        
        // Cap total discount at 30%
        if (discount > 0.30) {
            discount = 0.30;
        }
        
        finalPrice = basePrice * (1 - discount);
        double savings = basePrice - finalPrice;
        
        System.out.printf("Total Discount: %.1f%%%n", discount * 100);
        System.out.printf("Final Price: $%.2f%n", finalPrice);
        System.out.printf("Total Savings: $%.2f%n", savings);
        
        // Shipping logic
        if (finalPrice >= 200) {
            System.out.println("✓ Free shipping included");
        } else if (finalPrice >= 100) {
            System.out.println("✓ Reduced shipping: $5.00");
        } else {
            System.out.println("✗ Standard shipping: $10.00");
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates banking transaction validation
     * Shows how financial systems use conditional logic
     */
    public static void demonstrateBankingValidation() {
        System.out.println("2. Banking Transaction Validation");
        System.out.println("=================================");
        
        double accountBalance = 1500.0;
        double transactionAmount = 200.0;
        String transactionType = "WITHDRAWAL";
        boolean isBusinessAccount = false;
        boolean isOverdraftEnabled = true;
        double overdraftLimit = 500.0;
        
        System.out.printf("Account Balance: $%.2f%n", accountBalance);
        System.out.printf("Transaction Amount: $%.2f%n", transactionAmount);
        System.out.printf("Transaction Type: %s%n", transactionType);
        System.out.printf("Business Account: %b, Overdraft: %b%n", 
                         isBusinessAccount, isOverdraftEnabled);
        
        boolean transactionApproved = false;
        String reason = "";
        
        // Validate transaction amount
        if (transactionAmount <= 0) {
            reason = "Invalid transaction amount";
        } else if (transactionType.equals("WITHDRAWAL")) {
            // Withdrawal validation
            if (transactionAmount > accountBalance) {
                if (isOverdraftEnabled && transactionAmount <= (accountBalance + overdraftLimit)) {
                    transactionApproved = true;
                    reason = "Overdraft approved";
                } else {
                    reason = "Insufficient funds";
                }
            } else {
                transactionApproved = true;
                reason = "Sufficient funds";
            }
        } else if (transactionType.equals("DEPOSIT")) {
            // Deposit validation
            if (transactionAmount <= 10000) { // Daily limit
                transactionApproved = true;
                reason = "Deposit within limits";
            } else {
                reason = "Deposit exceeds daily limit";
            }
        } else if (transactionType.equals("TRANSFER")) {
            // Transfer validation
            if (transactionAmount <= accountBalance) {
                if (isBusinessAccount || transactionAmount <= 5000) {
                    transactionApproved = true;
                    reason = "Transfer approved";
                } else {
                    reason = "Transfer amount exceeds limit for personal account";
                }
            } else {
                reason = "Insufficient funds for transfer";
            }
        } else {
            reason = "Invalid transaction type";
        }
        
        // Display results
        System.out.printf("Transaction Approved: %b%n", transactionApproved);
        System.out.printf("Reason: %s%n", reason);
        
        if (transactionApproved) {
            double newBalance = accountBalance;
            if (transactionType.equals("WITHDRAWAL")) {
                newBalance -= transactionAmount;
            } else if (transactionType.equals("DEPOSIT")) {
                newBalance += transactionAmount;
            }
            System.out.printf("New Balance: $%.2f%n", newBalance);
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates weather-based activity recommendations
     * Shows how to use multiple conditions for recommendations
     */
    public static void demonstrateWeatherRecommendations() {
        System.out.println("3. Weather-Based Activity Recommendations");
        System.out.println("========================================");
        
        int temperature = 22; // Celsius
        String weatherCondition = "Sunny";
        int windSpeed = 15; // km/h
        boolean isWeekend = true;
        boolean hasEquipment = true;
        
        System.out.printf("Temperature: %d°C%n", temperature);
        System.out.printf("Weather: %s%n", weatherCondition);
        System.out.printf("Wind Speed: %d km/h%n", windSpeed);
        System.out.printf("Weekend: %b, Has Equipment: %b%n", isWeekend, hasEquipment);
        
        // Activity recommendations based on weather
        if (weatherCondition.equals("Sunny")) {
            if (temperature >= 25) {
                System.out.println("✓ Perfect for swimming");
                System.out.println("✓ Great for beach activities");
                System.out.println("⚠ Remember sunscreen!");
            } else if (temperature >= 15) {
                System.out.println("✓ Good for outdoor sports");
                System.out.println("✓ Nice for hiking");
                System.out.println("✓ Suitable for cycling");
            } else {
                System.out.println("✓ Pleasant for walking");
                System.out.println("✓ Good for light outdoor activities");
            }
        } else if (weatherCondition.equals("Rainy")) {
            if (windSpeed > 30) {
                System.out.println("✗ Stay indoors - storm conditions");
            } else {
                System.out.println("✓ Indoor activities recommended");
                System.out.println("✓ Good for reading or movies");
                System.out.println("⚠ Bring umbrella if going out");
            }
        } else if (weatherCondition.equals("Cloudy")) {
            if (temperature >= 10) {
                System.out.println("✓ Good for outdoor activities");
                System.out.println("✓ Suitable for photography");
            } else {
                System.out.println("✓ Indoor activities preferred");
            }
        } else if (weatherCondition.equals("Snowy")) {
            if (hasEquipment) {
                System.out.println("✓ Perfect for skiing");
                System.out.println("✓ Great for snowboarding");
            } else {
                System.out.println("✓ Good for snowball fights");
                System.out.println("✓ Nice for building snowmen");
            }
        }
        
        // Weekend-specific recommendations
        if (isWeekend) {
            System.out.println("✓ Weekend bonus: More time for activities");
        } else {
            System.out.println("⚠ Weekday: Consider time constraints");
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates simple game logic using if-else
     * Shows how games use conditional logic for mechanics
     */
    public static void demonstrateGameLogic() {
        System.out.println("4. Simple Game Logic");
        System.out.println("====================");
        
        int playerHealth = 75;
        int playerLevel = 5;
        String playerClass = "Warrior";
        boolean hasPotion = true;
        boolean isInCombat = true;
        
        System.out.printf("Health: %d/100%n", playerHealth);
        System.out.printf("Level: %d%n", playerLevel);
        System.out.printf("Class: %s%n", playerClass);
        System.out.printf("Has Potion: %b, In Combat: %b%n", hasPotion, isInCombat);
        
        // Health status
        String healthStatus;
        if (playerHealth >= 80) {
            healthStatus = "Excellent";
        } else if (playerHealth >= 60) {
            healthStatus = "Good";
        } else if (playerHealth >= 40) {
            healthStatus = "Fair";
        } else if (playerHealth >= 20) {
            healthStatus = "Poor";
        } else {
            healthStatus = "Critical";
        }
        System.out.printf("Health Status: %s%n", healthStatus);
        
        // Combat recommendations
        if (isInCombat) {
            if (playerHealth < 30) {
                System.out.println("⚠ CRITICAL: Use potion immediately!");
                if (hasPotion) {
                    System.out.println("✓ Using health potion");
                    playerHealth = Math.min(100, playerHealth + 50);
                    hasPotion = false;
                    System.out.printf("New Health: %d/100%n", playerHealth);
                } else {
                    System.out.println("✗ No potions available - retreat recommended");
                }
            } else if (playerHealth < 50) {
                System.out.println("⚠ Low health - consider using potion");
                if (hasPotion) {
                    System.out.println("✓ Using health potion");
                    playerHealth = Math.min(100, playerHealth + 50);
                    hasPotion = false;
                    System.out.printf("New Health: %d/100%n", playerHealth);
                }
            } else {
                System.out.println("✓ Health is adequate for combat");
            }
        }
        
        // Class-specific abilities
        if (playerClass.equals("Warrior")) {
            if (playerLevel >= 5) {
                System.out.println("✓ Can use Shield Bash ability");
            }
            if (playerLevel >= 10) {
                System.out.println("✓ Can use Charge ability");
            }
        } else if (playerClass.equals("Mage")) {
            if (playerLevel >= 5) {
                System.out.println("✓ Can use Fireball spell");
            }
            if (playerLevel >= 10) {
                System.out.println("✓ Can use Teleport spell");
            }
        } else if (playerClass.equals("Archer")) {
            if (playerLevel >= 5) {
                System.out.println("✓ Can use Multi-Shot ability");
            }
            if (playerLevel >= 10) {
                System.out.println("✓ Can use Stealth ability");
            }
        }
        
        // Level-based rewards
        if (playerLevel >= 10) {
            System.out.println("✓ Unlocked advanced abilities");
        } else if (playerLevel >= 5) {
            System.out.println("✓ Unlocked intermediate abilities");
        } else {
            System.out.println("✓ Basic abilities available");
        }
        
        System.out.println();
    }
} 