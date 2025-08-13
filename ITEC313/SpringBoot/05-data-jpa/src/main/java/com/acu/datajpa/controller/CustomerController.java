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
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        return ResponseEntity.ok(createdCustomer);
    }

    /**
     * Update customer
     */
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, 
                                                  @Valid @RequestBody Customer customerDetails) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerDetails);
        return ResponseEntity.ok(updatedCustomer);
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
