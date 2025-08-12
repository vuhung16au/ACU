package com.acu.hello.repo;

import com.acu.hello.model.Employee;
import com.acu.hello.model.Employees;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDAO {

    private static final Employees employees = new Employees();

    static {
        // Sydney-themed seed data
        employees.getEmployeeList().add(new Employee(1, "Ava", "Williams", "ava.williams@sydney.example"));
        employees.getEmployeeList().add(new Employee(2, "Liam", "Nguyen", "liam.nguyen@nsw.example"));
        employees.getEmployeeList().add(new Employee(3, "Olivia", "Brown", "olivia.brown@acu.edu.au"));
    }

    public Employees getAllEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        employees.getEmployeeList().add(employee);
    }
}
