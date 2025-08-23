package com.acu.datastructure.stack;

/**
 * Main class to demonstrate the custom Stack implementation using Australian data.
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("=== Custom Stack Implementation Demo (Aussie Edition) ===\n");
        
        // Create a new stack for Australian cities
        MyStack<String> cityStack = new MyStack<>();
        
        System.out.println("1. Testing basic operations with Australian cities:");
        System.out.println("   Initial stack is empty: " + cityStack.isEmpty());
        System.out.println("   Initial size: " + cityStack.size());
        
        // Push some Australian cities
        System.out.println("\n2. Pushing Australian cities:");
        cityStack.push("Sydney");
        cityStack.push("Melbourne");
        cityStack.push("Brisbane");
        cityStack.push("Perth");
        cityStack.push("Adelaide");
        
        System.out.println("   Stack size after pushing 5 cities: " + cityStack.size());
        System.out.println("   Stack is empty: " + cityStack.isEmpty());
        
        // Peek at the top element
        System.out.println("\n3. Peeking at top city:");
        System.out.println("   Top city: " + cityStack.peek());
        System.out.println("   Stack size after peek: " + cityStack.size()); // Should still be 5
        
        // Pop cities
        System.out.println("\n4. Popping cities (LIFO order):");
        System.out.println("   Popped: " + cityStack.pop()); // Should be "Adelaide"
        System.out.println("   Popped: " + cityStack.pop()); // Should be "Perth"
        System.out.println("   Popped: " + cityStack.pop()); // Should be "Brisbane"
        
        System.out.println("   Stack size after popping 3 cities: " + cityStack.size());
        System.out.println("   Stack is empty: " + cityStack.isEmpty());
        
        // Test with Australian states/territories
        System.out.println("\n5. Testing with Australian states and territories:");
        MyStack<String> stateStack = new MyStack<>();
        
        stateStack.push("NSW");
        stateStack.push("VIC");
        stateStack.push("QLD");
        stateStack.push("WA");
        stateStack.push("SA");
        stateStack.push("TAS");
        stateStack.push("NT");
        stateStack.push("ACT");
        
        System.out.println("   State stack size: " + stateStack.size());
        System.out.println("   Top state/territory: " + stateStack.peek());
        
        // Demonstrate LIFO behavior with states
        System.out.println("\n6. Demonstrating LIFO (Last In, First Out) behavior with states:");
        while (!stateStack.isEmpty()) {
            System.out.println("   Popped: " + stateStack.pop());
        }
        
        // Test with Australian landmarks
        System.out.println("\n7. Testing with Australian landmarks:");
        MyStack<String> landmarkStack = new MyStack<>();
        
        landmarkStack.push("Sydney Opera House");
        landmarkStack.push("Uluru");
        landmarkStack.push("Great Barrier Reef");
        landmarkStack.push("Bondi Beach");
        landmarkStack.push("Twelve Apostles");
        
        System.out.println("   Landmark stack size: " + landmarkStack.size());
        System.out.println("   Top landmark: " + landmarkStack.peek());
        
        // Pop some landmarks
        System.out.println("\n8. Popping some landmarks:");
        System.out.println("   Popped: " + landmarkStack.pop()); // Twelve Apostles
        System.out.println("   Popped: " + landmarkStack.pop()); // Bondi Beach
        System.out.println("   Remaining landmarks: " + landmarkStack.size());
        
        // Test with population numbers (in thousands)
        System.out.println("\n9. Testing with population numbers (in thousands):");
        MyStack<Integer> populationStack = new MyStack<>();
        
        populationStack.push(5312); // Sydney
        populationStack.push(5078); // Melbourne
        populationStack.push(2487); // Brisbane
        populationStack.push(2140); // Perth
        populationStack.push(1412); // Adelaide
        
        System.out.println("   Population stack size: " + populationStack.size());
        System.out.println("   Top population: " + populationStack.peek() + "k");
        
        // Demonstrate LIFO behavior with populations
        System.out.println("\n10. Demonstrating LIFO behavior with populations:");
        while (!populationStack.isEmpty()) {
            int pop = populationStack.pop();
            System.out.println("   Popped: " + pop + "k");
        }
        
        // Test clear functionality
        System.out.println("\n11. Testing clear functionality:");
        MyStack<String> testStack = new MyStack<>();
        testStack.push("Kangaroo");
        testStack.push("Koala");
        testStack.push("Emu");
        System.out.println("   Before clear - Size: " + testStack.size() + ", Empty: " + testStack.isEmpty());
        testStack.clear();
        System.out.println("   After clear - Size: " + testStack.size() + ", Empty: " + testStack.isEmpty());
        
        System.out.println("\n=== Aussie Stack Demo completed successfully! ===");
        System.out.println("Run 'mvn test' to see the comprehensive test suite.");
        System.out.println("G'day mate! 🦘");
    }
}
