/**
 * Example 1: Basic Final Variables and Constants
 * 
 * This example demonstrates the fundamental concepts of final variables
 * and constants in Java, showing how they differ from regular variables.
 * 
 * Key concepts:
 * - Final variables cannot be reassigned after initialization
 * - Constants are typically declared as public static final
 * - Naming conventions for constants (UPPER_CASE)
 * 
 * Python comparison:
 * - Python uses naming conventions (UPPER_CASE) for constants
 * - Java enforces immutability with the 'final' keyword
 */
public class Example1 {
    
    // Class constants - accessible from anywhere
    public static final String COMPANY_NAME = "TechCorp";
    public static final int MAX_EMPLOYEES = 1000;
    public static final double BASE_SALARY = 50000.0;
    
    // Final instance variables
    private final String employeeId;
    private final String department;
    
    public Example1(String id, String dept) {
        this.employeeId = id;    // Can only be set once
        this.department = dept;  // Can only be set once
    }
    
    /**
     * Demonstrates basic final variables
     */
    public void demonstrateBasicFinals() {
        System.out.println("=== Basic Final Variables ===");
        
        // Final local variables
        final String name = "John Doe";
        final int age = 30;
        final double salary = 75000.0;
        
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Salary: $" + salary);
        
        // These would cause compilation errors:
        // name = "Jane Doe";    // Error: cannot assign to final variable
        // age = 31;             // Error: cannot assign to final variable
        // salary = 80000.0;     // Error: cannot assign to final variable
        
        System.out.println("✓ Final variables cannot be reassigned");
    }
    
    /**
     * Demonstrates constants in calculations
     */
    public void demonstrateConstants() {
        System.out.println("\n=== Constants in Calculations ===");
        
        // Using class constants
        double bonus = BASE_SALARY * 0.15;  // 15% bonus
        double totalCompensation = BASE_SALARY + bonus;
        
        System.out.println("Company: " + COMPANY_NAME);
        System.out.println("Base Salary: $" + BASE_SALARY);
        System.out.println("Bonus (15%): $" + bonus);
        System.out.println("Total Compensation: $" + totalCompensation);
        System.out.println("Max Employees: " + MAX_EMPLOYEES);
        
        // Method-level constants
        final double TAX_RATE = 0.25;
        double afterTax = totalCompensation * (1 - TAX_RATE);
        
        System.out.println("Tax Rate: " + (TAX_RATE * 100) + "%");
        System.out.println("After Tax: $" + afterTax);
    }
    
    /**
     * Demonstrates final parameters
     */
    public void processEmployee(final String name, final int yearsOfService) {
        System.out.println("\n=== Final Method Parameters ===");
        System.out.println("Processing employee: " + name);
        System.out.println("Years of service: " + yearsOfService);
        
        // Calculate benefits based on years of service
        final double BENEFIT_MULTIPLIER = 0.05;
        double benefits = yearsOfService * BENEFIT_MULTIPLIER * BASE_SALARY;
        
        System.out.println("Benefits: $" + benefits);
        
        // These would cause compilation errors:
        // name = "Modified Name";           // Error: cannot assign to final parameter
        // yearsOfService = 10;              // Error: cannot assign to final parameter
        // BENEFIT_MULTIPLIER = 0.10;       // Error: cannot assign to final variable
    }
    
    /**
     * Demonstrates instance final variables
     */
    public void showEmployeeInfo() {
        System.out.println("\n=== Instance Final Variables ===");
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Department: " + department);
        
        // These would cause compilation errors:
        // this.employeeId = "NEW_ID";    // Error: cannot assign to final variable
        // this.department = "NEW_DEPT";  // Error: cannot assign to final variable
    }
    
    /**
     * Main method to run the example
     */
    public static void main(String[] args) {
        System.out.println("Example 1: Basic Final Variables and Constants");
        System.out.println("==============================================\n");
        
        Example1 example = new Example1("EMP001", "Engineering");
        
        example.demonstrateBasicFinals();
        example.demonstrateConstants();
        example.processEmployee("Alice Johnson", 5);
        example.showEmployeeInfo();
        
        System.out.println("\n=== Key Takeaways ===");
        System.out.println("✓ Final variables are immutable after initialization");
        System.out.println("✓ Constants use UPPER_CASE naming convention");
        System.out.println("✓ Final parameters cannot be modified within methods");
        System.out.println("✓ Instance final variables must be initialized in constructor");
    }
} 