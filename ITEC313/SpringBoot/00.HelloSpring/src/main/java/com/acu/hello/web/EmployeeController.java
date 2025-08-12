package com.acu.hello.web;

import com.acu.hello.model.Employee;
import com.acu.hello.model.Employees;
import com.acu.hello.repo.EmployeeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeDAO employeeDao;

    @GetMapping("/")
    public Employees getEmployees() {
        return employeeDao.getAllEmployees();
    }

    @PostMapping("/")
    public ResponseEntity<Object> addEmployee(@RequestBody Employee employee) {
        // Generate a simple incremental ID
        Integer id = employeeDao.getAllEmployees().getEmployeeList().size() + 1;
        employee.setId(id);
        employeeDao.addEmployee(employee);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(employee.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
