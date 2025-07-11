/**
 * Variable Scope Demonstration
 * 
 * This program illustrates different types of variable scope
 * in Java and their accessibility rules.
 */
public class ScopeDemo {
    
    // Class-level (static) variable
    private static int classVariable = 100;
    
    // Instance variable
    private int instanceVariable = 200;
    
    public static void main(String[] args) {
        System.out.println("=== Variable Scope Demonstration ===\n");
        
        ScopeDemo demo = new ScopeDemo();
        demo.demonstrateMethodScope();
        demo.demonstrateBlockScope();
        demo.demonstrateLoopScope();
        demo.demonstrateParameterScope(42);
        demonstrateStaticScope();
    }
    
    public void demonstrateMethodScope() {
        System.out.println("1. METHOD SCOPE");
        System.out.println("===============");
        
        // Method-local variables
        int localVariable = 10;
        String localString = "Hello";
        
        System.out.printf("Local variable: %d%n", localVariable);
        System.out.printf("Local string: %s%n", localString);
        System.out.printf("Instance variable: %d%n", instanceVariable);
        System.out.printf("Class variable: %d%n", classVariable);
        
        // Local variables shadow instance variables with same name
        int instanceVariable = 300;  // Shadows this.instanceVariable
        System.out.printf("Shadowed instance variable: %d%n", instanceVariable);
        System.out.printf("Original instance variable: %d%n", this.instanceVariable);
        
        System.out.println();
    }
    
    public void demonstrateBlockScope() {
        System.out.println("2. BLOCK SCOPE");
        System.out.println("==============");
        
        int outerVariable = 50;
        System.out.printf("Outer variable: %d%n", outerVariable);
        
        // Block scope
        {
            int blockVariable = 60;
            outerVariable = 55;  // Can modify outer variable
            
            System.out.printf("Inside block - block variable: %d%n", blockVariable);
            System.out.printf("Inside block - modified outer variable: %d%n", outerVariable);
            
            // Nested block
            {
                int nestedVariable = 70;
                System.out.printf("Nested block variable: %d%n", nestedVariable);
                System.out.printf("Access to block variable: %d%n", blockVariable);
            }
            // nestedVariable is not accessible here
        }
        // blockVariable is not accessible here
        
        System.out.printf("After block - outer variable: %d%n", outerVariable);
        
        System.out.println();
    }
    
    public void demonstrateLoopScope() {
        System.out.println("3. LOOP SCOPE");
        System.out.println("=============");
        
        // Traditional for loop
        System.out.println("Traditional for loop:");
        for (int i = 0; i < 3; i++) {
            int loopLocal = i * 10;
            System.out.printf("Loop iteration %d, local variable: %d%n", i, loopLocal);
        }
        // i and loopLocal are not accessible here
        
        // Enhanced for loop
        System.out.println("\nEnhanced for loop:");
        int[] numbers = {10, 20, 30};
        for (int number : numbers) {
            System.out.printf("Array element: %d%n", number);
        }
        // number is not accessible here
        
        // While loop
        System.out.println("\nWhile loop with block scope:");
        int counter = 0;
        while (counter < 2) {
            int whileLocal = counter + 100;
            System.out.printf("While iteration, local: %d%n", whileLocal);
            counter++;
        }
        System.out.printf("Counter after while loop: %d%n", counter);
        
        System.out.println();
    }
    
    public void demonstrateParameterScope(int parameter) {
        System.out.println("4. PARAMETER SCOPE");
        System.out.println("==================");
        
        System.out.printf("Method parameter: %d%n", parameter);
        
        // Parameters can be modified locally
        parameter = parameter * 2;
        System.out.printf("Modified parameter: %d%n", parameter);
        
        // Parameters shadow instance/class variables
        int classVariable = 999;  // Shadows ScopeDemo.classVariable
        System.out.printf("Shadowed class variable: %d%n", classVariable);
        System.out.printf("Original class variable: %d%n", ScopeDemo.classVariable);
        
        System.out.println();
    }
    
    public static void demonstrateStaticScope() {
        System.out.println("5. STATIC SCOPE");
        System.out.println("===============");
        
        // Static methods can only access static variables directly
        System.out.printf("Class variable from static method: %d%n", classVariable);
        
        // To access instance variables, need an object reference
        ScopeDemo instance = new ScopeDemo();
        System.out.printf("Instance variable via object: %d%n", instance.instanceVariable);
        
        // Local variables in static methods
        int staticLocalVariable = 777;
        System.out.printf("Local variable in static method: %d%n", staticLocalVariable);
        
        System.out.println();
    }
}
