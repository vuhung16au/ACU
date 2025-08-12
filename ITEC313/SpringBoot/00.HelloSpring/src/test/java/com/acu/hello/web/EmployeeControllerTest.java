package com.acu.hello.web;

import com.acu.hello.model.Employee;
import com.acu.hello.model.Employees;
import com.acu.hello.repo.EmployeeDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeDAO employeeDAO;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void resetInMemoryData() {
        // Ensure a known starting state for each test
        Employees employees = employeeDAO.getAllEmployees();
        employees.setEmployeeList(new ArrayList<>());
        employees.getEmployeeList().add(new Employee(1, "Ava", "Williams", "ava.williams@sydney.example"));
        employees.getEmployeeList().add(new Employee(2, "Liam", "Nguyen", "liam.nguyen@nsw.example"));
        employees.getEmployeeList().add(new Employee(3, "Olivia", "Brown", "olivia.brown@acu.edu.au"));
    }

    @Test
    @DisplayName("GET /employees/ returns the seeded list")
    void getEmployees_returnsSeededList() throws Exception {
        mockMvc.perform(get("/employees/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.employeeList", hasSize(3)))
                .andExpect(jsonPath("$.employeeList[0].firstName", is("Ava")))
                .andExpect(jsonPath("$.employeeList[1].email", is("liam.nguyen@nsw.example")));
    }

    @Test
    @DisplayName("POST /employees/ adds a new employee and returns 201 with Location header")
    void addEmployee_createsAndReturnsLocation() throws Exception {
        Employee newEmp = new Employee(null, "Noah", "Smith", "noah.smith@sydney.example");

        mockMvc.perform(post("/employees/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEmp)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/employees/4")));

        // Verify it was added
        mockMvc.perform(get("/employees/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeList", hasSize(4)))
                .andExpect(jsonPath("$.employeeList[3].firstName", is("Noah")))
                .andExpect(jsonPath("$.employeeList[3].email", is("noah.smith@sydney.example")));
    }
}
