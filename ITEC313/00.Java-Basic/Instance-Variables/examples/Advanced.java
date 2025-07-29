/**
 * Advanced.java
 * 
 * This program demonstrates advanced in Java:
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
public class Advanced {
    
    // Instance variables for the main class
    private String name;
    private int id;
    private Department department;
    private Project[] projects;
    private static int nextId = 1000;
    
    /**
     * Inner class representing a department
     */
    public static class Department {
        private String name;
        private String code;
        private Employee[] employees;
        private int employeeCount;
        
        public Department(String name, String code) {
            this.name = name;
            this.code = code;
            this.employees = new Employee[50];
            this.employeeCount = 0;
        }
        
        public void addEmployee(Employee employee) {
            if (employeeCount < employees.length) {
                employees[employeeCount++] = employee;
            }
        }
        
        public String getName() {
            return name;
        }
        
        public String getCode() {
            return code;
        }
        
        public Employee[] getEmployees() {
            return employees;
        }
        
        public int getEmployeeCount() {
            return employeeCount;
        }
    }
    
    /**
     * Inner class representing an employee (inheritance example)
     */
    public static class Employee {
        // Base instance variables
        protected String name;
        protected int id;
        protected double salary;
        protected Department department;
        
        // Static variable shared across all employees
        private static int totalEmployees = 0;
        
        public Employee(String name, double salary) {
            this.name = name;
            this.salary = salary;
            this.id = ++totalEmployees;
        }
        
        public String getName() {
            return name;
        }
        
        public int getId() {
            return id;
        }
        
        public double getSalary() {
            return salary;
        }
        
        public Department getDepartment() {
            return department;
        }
        
        public void setDepartment(Department department) {
            this.department = department;
        }
        
        public void setSalary(double salary) {
            this.salary = salary;
        }
        
        public void displayInfo() {
            System.out.println("Employee ID: " + id);
            System.out.println("Name: " + name);
            System.out.println("Salary: $" + salary);
            System.out.println("Department: " + (department != null ? department.getName() : "None"));
        }
        
        public static int getTotalEmployees() {
            return totalEmployees;
        }
    }
    
    /**
     * Inner class representing a manager (inherits from Employee)
     */
    public static class Manager extends Employee {
        // Additional instance variables for manager
        private Employee[] subordinates;
        private int subordinateCount;
        private String title;
        
        public Manager(String name, double salary, String title) {
            super(name, salary);  // Call parent constructor
            this.title = title;
            this.subordinates = new Employee[10];
            this.subordinateCount = 0;
        }
        
        public void addSubordinate(Employee employee) {
            if (subordinateCount < subordinates.length) {
                subordinates[subordinateCount++] = employee;
            }
        }
        
        public Employee[] getSubordinates() {
            return subordinates;
        }
        
        public int getSubordinateCount() {
            return subordinateCount;
        }
        
        public String getTitle() {
            return title;
        }
        
        @Override
        public void displayInfo() {
            super.displayInfo();  // Call parent method
            System.out.println("Title: " + title);
            System.out.println("Subordinates: " + subordinateCount);
        }
    }
    
    /**
     * Inner class representing a project
     */
    public static class Project {
        private String name;
        private String description;
        private double budget;
        private boolean isActive;
        private Employee[] team;
        private int teamSize;
        
        public Project(String name, String description, double budget) {
            this.name = name;
            this.description = description;
            this.budget = budget;
            this.isActive = true;
            this.team = new Employee[20];
            this.teamSize = 0;
        }
        
        public void addTeamMember(Employee employee) {
            if (teamSize < team.length) {
                team[teamSize++] = employee;
            }
        }
        
        public String getName() {
            return name;
        }
        
        public String getDescription() {
            return description;
        }
        
        public double getBudget() {
            return budget;
        }
        
        public boolean isActive() {
            return isActive;
        }
        
        public void setActive(boolean active) {
            isActive = active;
        }
        
        public Employee[] getTeam() {
            return team;
        }
        
        public int getTeamSize() {
            return teamSize;
        }
        
        public void displayInfo() {
            System.out.println("Project: " + name);
            System.out.println("Description: " + description);
            System.out.println("Budget: $" + budget);
            System.out.println("Active: " + isActive);
            System.out.println("Team Size: " + teamSize);
        }
    }
    
    /**
     * Constructor for Advanced class
     */
    public Advanced(String name) {
        this.name = name;
        this.id = nextId++;
        this.projects = new Project[5];
    }
    
    /**
     * Method to set department
     */
    public void setDepartment(Department department) {
        this.department = department;
    }
    
    /**
     * Method to add project
     */
    public void addProject(Project project) {
        for (int i = 0; i < projects.length; i++) {
            if (projects[i] == null) {
                projects[i] = project;
                return;
            }
        }
        System.out.println("Cannot add project: maximum projects reached");
    }
    
    /**
     * Method to display company information
     */
    public void displayInfo() {
        System.out.println("Company Information:");
        System.out.println("Name: " + name);
        System.out.println("ID: " + id);
        System.out.println("Department: " + (department != null ? department.getName() : "None"));
        System.out.println("Projects:");
        
        for (Project project : projects) {
            if (project != null) {
                project.displayInfo();
                System.out.println();
            }
        }
    }
    
    /**
     * Method demonstrating complex instance variable patterns
     */
    public void demonstrateComplexPatterns() {
        System.out.println("=== Demonstrating Complex Instance Variable Patterns ===\n");
        
        // Create departments
        Department itDept = new Department("Information Technology", "IT");
        Department hrDept = new Department("Human Resources", "HR");
        
        // Create employees
        Employee emp1 = new Employee("John Doe", 50000.0);
        Employee emp2 = new Employee("Jane Smith", 55000.0);
        Manager mgr1 = new Manager("Bob Johnson", 75000.0, "Senior Manager");
        
        // Set departments
        emp1.setDepartment(itDept);
        emp2.setDepartment(itDept);
        mgr1.setDepartment(hrDept);
        
        // Add employees to departments
        itDept.addEmployee(emp1);
        itDept.addEmployee(emp2);
        hrDept.addEmployee(mgr1);
        
        // Add subordinates to manager
        mgr1.addSubordinate(emp1);
        mgr1.addSubordinate(emp2);
        
        // Create projects
        Project project1 = new Project("Website Redesign", "Redesign company website", 50000.0);
        Project project2 = new Project("Database Migration", "Migrate to new database", 75000.0);
        
        // Add team members to projects
        project1.addTeamMember(emp1);
        project1.addTeamMember(emp2);
        project2.addTeamMember(mgr1);
        
        // Add projects to company
        this.addProject(project1);
        this.addProject(project2);
        
        // Display information
        System.out.println("Employee Information:");
        emp1.displayInfo();
        System.out.println();
        emp2.displayInfo();
        System.out.println();
        mgr1.displayInfo();
        System.out.println();
        
        System.out.println("Department Information:");
        System.out.println("IT Department - Employees: " + itDept.getEmployeeCount());
        System.out.println("HR Department - Employees: " + hrDept.getEmployeeCount());
        System.out.println();
        
        System.out.println("Project Information:");
        project1.displayInfo();
        System.out.println();
        project2.displayInfo();
        System.out.println();
        
        System.out.println("Total Employees: " + Employee.getTotalEmployees());
    }
    
    /**
     * Method demonstrating instance variable lifecycle
     */
    public void demonstrateLifecycle() {
        System.out.println("=== Demonstrating Instance Variable Lifecycle ===\n");
        
        // Create objects and show their state
        Employee emp = new Employee("Lifecycle Test", 60000.0);
        System.out.println("Initial state:");
        emp.displayInfo();
        
        // Modify instance variables
        emp.setSalary(65000.0);
        emp.setDepartment(new Department("Test Dept", "TEST"));
        
        System.out.println("\nAfter modifications:");
        emp.displayInfo();
        
        // Show static variable changes
        System.out.println("\nTotal employees created: " + Employee.getTotalEmployees());
    }
    
    /**
     * Main method to demonstrate Advanced concepts
     */
    public static void main(String[] args) {
        System.out.println("=== Advanced: Complex Instance Variable Patterns ===\n");
        
        // Create company
        Advanced company = new Advanced("TechCorp");
        
        // Demonstrate complex patterns
        company.demonstrateComplexPatterns();
        
        // Demonstrate lifecycle
        company.demonstrateLifecycle();
        
        // Display final company information
        System.out.println("Final Company Information:");
        company.displayInfo();
    }
} 