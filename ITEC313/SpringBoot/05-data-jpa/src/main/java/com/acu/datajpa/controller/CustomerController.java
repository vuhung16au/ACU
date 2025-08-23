package com.acu.datajpa.controller;

import com.acu.datajpa.entity.Customer;
import com.acu.datajpa.entity.CustomerStatus;
import com.acu.datajpa.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for Customer operations with JPA integration
 * 
 * This controller demonstrates:
 * - JPA entity integration
 * - Pagination and sorting
 * - Repository query methods
 * - Transaction management
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Get all customers with pagination
     */
    @GetMapping
    public ResponseEntity<Page<Customer>> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Customer> customers = customerService.getAllCustomers(pageable);
        return ResponseEntity.ok(customers);
    }

    /**
     * Get customer by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    /**
     * Get customer by email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {
        Customer customer = customerService.getCustomerByEmail(email);
        return ResponseEntity.ok(customer);
    }

    /**
     * Create a new customer
     */
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        return ResponseEntity.ok(createdCustomer);
    }

    /**
     * Create a new customer (alternative method)
     */
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createCustomerAlternative(@RequestBody Map<String, Object> customerData) {
        Customer customer = new Customer();
        customer.setFirstName((String) customerData.get("firstName"));
        customer.setLastName((String) customerData.get("lastName"));
        customer.setEmail((String) customerData.get("email"));
        customer.setPhone((String) customerData.get("phone"));
        
        String statusStr = (String) customerData.get("status");
        if (statusStr != null) {
            customer.setStatus(CustomerStatus.valueOf(statusStr));
        }
        
        Customer createdCustomer = customerService.createCustomer(customer);
        
        Map<String, Object> response = Map.of(
            "id", createdCustomer.getId(),
            "firstName", createdCustomer.getFirstName(),
            "lastName", createdCustomer.getLastName(),
            "email", createdCustomer.getEmail(),
            "phone", createdCustomer.getPhone(),
            "status", createdCustomer.getStatus().name()
        );
        
        return ResponseEntity.ok(response);
    }

    /**
     * Update customer
     */
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, 
                                                  @RequestBody Customer customerDetails) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerDetails);
        return ResponseEntity.ok(updatedCustomer);
    }

    /**
     * Update customer (alternative method)
     */
    @PutMapping("/{id}/update")
    public ResponseEntity<Map<String, Object>> updateCustomerAlternative(@PathVariable Long id, 
                                                                        @RequestBody Map<String, Object> customerData) {
        Customer customerDetails = new Customer();
        customerDetails.setFirstName((String) customerData.get("firstName"));
        customerDetails.setLastName((String) customerData.get("lastName"));
        customerDetails.setEmail((String) customerData.get("email"));
        customerDetails.setPhone((String) customerData.get("phone"));
        
        String statusStr = (String) customerData.get("status");
        if (statusStr != null) {
            customerDetails.setStatus(CustomerStatus.valueOf(statusStr));
        }
        
        Customer updatedCustomer = customerService.updateCustomer(id, customerDetails);
        
        Map<String, Object> response = Map.of(
            "id", updatedCustomer.getId(),
            "firstName", updatedCustomer.getFirstName(),
            "lastName", updatedCustomer.getLastName(),
            "email", updatedCustomer.getEmail(),
            "phone", updatedCustomer.getPhone(),
            "status", updatedCustomer.getStatus().name()
        );
        
        return ResponseEntity.ok(response);
    }

    /**
     * Delete customer
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get customers by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Customer>> getCustomersByStatus(@PathVariable CustomerStatus status) {
        List<Customer> customers = customerService.getCustomersByStatus(status);
        return ResponseEntity.ok(customers);
    }

    /**
     * Search customers by name
     */
    @GetMapping("/search")
    public ResponseEntity<List<Customer>> searchCustomersByName(
            @RequestParam String firstName,
            @RequestParam String lastName) {
        List<Customer> customers = customerService.searchCustomersByName(firstName, lastName);
        return ResponseEntity.ok(customers);
    }

    /**
     * Get customers by email domain
     */
    @GetMapping("/domain/{emailDomain}")
    public ResponseEntity<List<Customer>> getCustomersByEmailDomain(@PathVariable String emailDomain) {
        List<Customer> customers = customerService.getCustomersByEmailDomain(emailDomain);
        return ResponseEntity.ok(customers);
    }

    /**
     * Update customer status
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Customer> updateCustomerStatus(@PathVariable Long id, 
                                                        @RequestParam CustomerStatus status) {
        Customer customer = customerService.updateCustomerStatus(id, status);
        return ResponseEntity.ok(customer);
    }

    /**
     * Get customer statistics
     */
    @GetMapping("/statistics")
    public ResponseEntity<List<Object[]>> getCustomerStatistics() {
        List<Object[]> statistics = customerService.getCustomerCountByStatus();
        return ResponseEntity.ok(statistics);
    }

    /**
     * Get average orders per customer
     */
    @GetMapping("/statistics/average-orders")
    public ResponseEntity<Double> getAverageOrdersPerCustomer() {
        Double average = customerService.getAverageOrdersPerCustomer();
        return ResponseEntity.ok(average);
    }
}
