package com.acu.javafx.database;

import javafx.beans.property.*;

/**
 * Employee model class with JavaFX properties for database operations
 */
public class Employee {
    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty email;
    private final StringProperty department;
    private final DoubleProperty salary;

    public Employee() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.department = new SimpleStringProperty();
        this.salary = new SimpleDoubleProperty();
    }

    public Employee(int id, String name, String email, String department, double salary) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.department = new SimpleStringProperty(department);
        this.salary = new SimpleDoubleProperty(salary);
    }

    // ID property
    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public IntegerProperty idProperty() { return id; }

    // Name property
    public String getName() { return name.get(); }
    public void setName(String name) { this.name.set(name); }
    public StringProperty nameProperty() { return name; }

    // Email property
    public String getEmail() { return email.get(); }
    public void setEmail(String email) { this.email.set(email); }
    public StringProperty emailProperty() { return email; }

    // Department property
    public String getDepartment() { return department.get(); }
    public void setDepartment(String department) { this.department.set(department); }
    public StringProperty departmentProperty() { return department; }

    // Salary property
    public double getSalary() { return salary.get(); }
    public void setSalary(double salary) { this.salary.set(salary); }
    public DoubleProperty salaryProperty() { return salary; }

    @Override
    public String toString() {
        return String.format("Employee{id=%d, name='%s', email='%s', department='%s', salary=%.2f}",
                getId(), getName(), getEmail(), getDepartment(), getSalary());
    }
}
