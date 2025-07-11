/**
 * Instance Variables Demo
 * 
 * This program demonstrates instance variables in Java, including:
 * - Field declaration and initialization
 * - The 'this' keyword
 * - Instance variable scope and lifetime
 * - Differences from Python's instance variables
 * 
 * For Python developers: In Python, instance variables are created in __init__
 * and accessed with self.variable_name. In Java, they're declared at class level
 * and accessed with this.variable_name or directly.
 */

public class InstanceVariables {
    
    // Instance variables (fields) - declared at class level
    // In Python: these would be created in __init__(self, ...)
    private String name;           // Default value: null
    private int age;              // Default value: 0
    private double salary;        // Default value: 0.0
    private boolean isActive;     // Default value: false
    
    // Instance variable with explicit initialization
    private String department = "Engineering";
    
    // Static variable (shared across all instances)
    // In Python: this would be a class variable
    private static int totalEmployees = 0;
    
    /**
     * Constructor - initializes instance variables
     * In Python: this would be __init__(self, name, age, salary)
     */
    public InstanceVariables(String name, int age, double salary) {
        // Using 'this' keyword to distinguish instance variable from parameter
        // In Python: self.name = name
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.isActive = true;  // Set default value
        
        // Increment static variable
        totalEmployees++;
    }
    
    /**
     * Default constructor - demonstrates default initialization
     */
    public InstanceVariables() {
        // Instance variables get default values automatically
        // name = null, age = 0, salary = 0.0, isActive = false
        totalEmployees++;
    }
    
    /**
     * Getter method for name
     * In Python: def get_name(self): return self.name
     */
    public String getName() {
        return this.name;  // 'this' is optional here but shows clarity
    }
    
    /**
     * Setter method for name
     * In Python: def set_name(self, name): self.name = name
     */
    public void setName(String name) {
        this.name = name;  // 'this' is required to distinguish from parameter
    }
    
    /**
     * Getter for age
     */
    public int getAge() {
        return age;  // 'this' is optional when no parameter conflict
    }
    
    /**
     * Setter for age with validation
     */
    public void setAge(int age) {
        if (age >= 0 && age <= 150) {
            this.age = age;
        } else {
            System.out.println("Invalid age: " + age);
        }
    }
    
    /**
     * Getter for salary
     */
    public double getSalary() {
        return salary;
    }
    
    /**
     * Setter for salary with validation
     */
    public void setSalary(double salary) {
        if (salary >= 0) {
            this.salary = salary;
        } else {
            System.out.println("Invalid salary: " + salary);
        }
    }
    
    /**
     * Getter for isActive
     */
    public boolean isActive() {
        return isActive;
    }
    
    /**
     * Setter for isActive
     */
    public void setActive(boolean active) {
        isActive = active;
    }
    
    /**
     * Getter for department
     */
    public String getDepartment() {
        return department;
    }
    
    /**
     * Setter for department
     */
    public void setDepartment(String department) {
        this.department = department;
    }
    
    /**
     * Static method to get total employees
     * In Python: @classmethod or @staticmethod
     */
    public static int getTotalEmployees() {
        return totalEmployees;
    }
    
    /**
     * Method that demonstrates instance variable usage
     */
    public void displayInfo() {
        System.out.println("Employee Information:");
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Salary: $" + salary);
        System.out.println("Department: " + department);
        System.out.println("Active: " + isActive);
        System.out.println("Total Employees: " + totalEmployees);
        System.out.println();
    }
    
    /**
     * Method that modifies instance variables
     */
    public void promote(double raiseAmount) {
        if (raiseAmount > 0) {
            salary += raiseAmount;
            System.out.println(name + " received a raise of $" + raiseAmount);
            System.out.println("New salary: $" + salary);
        }
    }
    
    /**
     * Method that demonstrates instance variable scope
     */
    public void updateDepartment(String newDepartment) {
        // Local variable shadows instance variable
        String department = "Temporary";
        
        // Access local variable
        System.out.println("Local department: " + department);
        
        // Access instance variable using 'this'
        System.out.println("Instance department: " + this.department);
        
        // Update instance variable
        this.department = newDepartment;
        System.out.println("Updated department: " + this.department);
    }
    
    /**
     * Main method to demonstrate instance variables
     */
    public static void main(String[] args) {
        System.out.println("=== Instance Variables Demo ===\n");
        
        // Create employee with constructor
        System.out.println("Creating employee with constructor...");
        InstanceVariables emp1 = new InstanceVariables("Alice Johnson", 28, 75000.0);
        emp1.displayInfo();
        
        // Create employee with default constructor
        System.out.println("Creating employee with default constructor...");
        InstanceVariables emp2 = new InstanceVariables();
        emp2.setName("Bob Smith");
        emp2.setAge(32);
        emp2.setSalary(65000.0);
        emp2.setDepartment("Marketing");
        emp2.displayInfo();
        
        // Demonstrate instance variable modifications
        System.out.println("Demonstrating instance variable modifications...");
        emp1.promote(5000.0);
        emp1.updateDepartment("Senior Engineering");
        
        // Demonstrate static variable
        System.out.println("\nTotal employees created: " + InstanceVariables.getTotalEmployees());
        
        // Demonstrate validation
        System.out.println("\nDemonstrating validation...");
        emp2.setAge(-5);  // Invalid age
        emp2.setSalary(-1000);  // Invalid salary
        
        // Final state
        System.out.println("\nFinal employee states:");
        emp1.displayInfo();
        emp2.displayInfo();
    }
} 