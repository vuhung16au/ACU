/**
 * Example2.java
 * 
 * Example demonstrating abstract classes with employee hierarchy
 * Shows abstract methods with different implementations
 */

import java.util.ArrayList;
import java.util.List;

// Abstract class for employees
abstract class Employee {
    protected String name;
    protected String id;
    protected double baseSalary;
    
    public Employee(String name, String id, double baseSalary) {
        this.name = name;
        this.id = id;
        this.baseSalary = baseSalary;
    }
    
    // Abstract methods that must be implemented by subclasses
    public abstract double calculateSalary();
    public abstract String getEmployeeType();
    public abstract void performWork();
    
    // Concrete methods
    public String getName() {
        return name;
    }
    
    public String getId() {
        return id;
    }
    
    public double getBaseSalary() {
        return baseSalary;
    }
    
    public void displayInfo() {
        System.out.println("Employee Information:");
        System.out.println("Name: " + name);
        System.out.println("ID: " + id);
        System.out.println("Type: " + getEmployeeType());
        System.out.println("Base Salary: $" + baseSalary);
        System.out.println("Total Salary: $" + calculateSalary());
    }
}

// Concrete class for full-time employees
class FullTimeEmployee extends Employee {
    private double bonus;
    private int yearsOfService;
    
    public FullTimeEmployee(String name, String id, double baseSalary, 
                          double bonus, int yearsOfService) {
        super(name, id, baseSalary);
        this.bonus = bonus;
        this.yearsOfService = yearsOfService;
    }
    
    @Override
    public double calculateSalary() {
        // Full-time employees get bonus and yearly increment
        double yearlyIncrement = baseSalary * 0.05 * yearsOfService;
        return baseSalary + bonus + yearlyIncrement;
    }
    
    @Override
    public String getEmployeeType() {
        return "Full-Time Employee";
    }
    
    @Override
    public void performWork() {
        System.out.println(name + " is working 40 hours per week");
    }
    
    public double getBonus() {
        return bonus;
    }
    
    public int getYearsOfService() {
        return yearsOfService;
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Bonus: $" + bonus);
        System.out.println("Years of Service: " + yearsOfService);
    }
}

// Concrete class for part-time employees
class PartTimeEmployee extends Employee {
    private int hoursWorked;
    private double hourlyRate;
    
    public PartTimeEmployee(String name, String id, double baseSalary, 
                          int hoursWorked, double hourlyRate) {
        super(name, id, baseSalary);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }
    
    @Override
    public double calculateSalary() {
        // Part-time employees are paid by the hour
        return hoursWorked * hourlyRate;
    }
    
    @Override
    public String getEmployeeType() {
        return "Part-Time Employee";
    }
    
    @Override
    public void performWork() {
        System.out.println(name + " is working " + hoursWorked + " hours this week");
    }
    
    public int getHoursWorked() {
        return hoursWorked;
    }
    
    public double getHourlyRate() {
        return hourlyRate;
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Hours Worked: " + hoursWorked);
        System.out.println("Hourly Rate: $" + hourlyRate);
    }
}

// Concrete class for contract employees
class ContractEmployee extends Employee {
    private int contractDuration; // in months
    private double projectBonus;
    
    public ContractEmployee(String name, String id, double baseSalary, 
                          int contractDuration, double projectBonus) {
        super(name, id, baseSalary);
        this.contractDuration = contractDuration;
        this.projectBonus = projectBonus;
    }
    
    @Override
    public double calculateSalary() {
        // Contract employees get project bonus
        return baseSalary + projectBonus;
    }
    
    @Override
    public String getEmployeeType() {
        return "Contract Employee";
    }
    
    @Override
    public void performWork() {
        System.out.println(name + " is working on contract for " + contractDuration + " months");
    }
    
    public int getContractDuration() {
        return contractDuration;
    }
    
    public double getProjectBonus() {
        return projectBonus;
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Contract Duration: " + contractDuration + " months");
        System.out.println("Project Bonus: $" + projectBonus);
    }
}

// Example usage
public class Example2 {
    public static void main(String[] args) {
        System.out.println("=== Example 2: Employee Abstract Class ===\n");
        
        // Create different types of employees
        FullTimeEmployee fullTime = new FullTimeEmployee("John Smith", "FT001", 50000.0, 5000.0, 3);
        PartTimeEmployee partTime = new PartTimeEmployee("Jane Doe", "PT001", 0.0, 20, 25.0);
        ContractEmployee contract = new ContractEmployee("Bob Johnson", "CT001", 60000.0, 12, 10000.0);
        
        // Demonstrate individual employee types
        System.out.println("Full-Time Employee:");
        fullTime.displayInfo();
        fullTime.performWork();
        System.out.println();
        
        System.out.println("Part-Time Employee:");
        partTime.displayInfo();
        partTime.performWork();
        System.out.println();
        
        System.out.println("Contract Employee:");
        contract.displayInfo();
        contract.performWork();
        System.out.println();
        
        // Demonstrate polymorphism and calculate total payroll
        List<Employee> employees = new ArrayList<>();
        employees.add(fullTime);
        employees.add(partTime);
        employees.add(contract);
        
        double totalPayroll = 0;
        System.out.println("=== Payroll Summary ===");
        for (Employee emp : employees) {
            System.out.println(emp.getName() + " (" + emp.getEmployeeType() + "): $" + emp.calculateSalary());
            totalPayroll += emp.calculateSalary();
        }
        System.out.println("Total Payroll: $" + totalPayroll);
        System.out.println();
        
        // Demonstrate work performance
        System.out.println("=== Work Performance ===");
        for (Employee emp : employees) {
            emp.performWork();
        }
    }
} 