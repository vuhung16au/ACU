package com.acu.datajpa.service;

import com.acu.datajpa.entity.Customer;
import com.acu.datajpa.entity.CustomerStatus;
import com.acu.datajpa.exception.ResourceNotFoundException;
import com.acu.datajpa.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Customer service with business logic and transaction management
 * 
 * This service demonstrates:
 * - Business logic implementation
 * - Transaction management
 * - Repository integration
 * - Data validation
 * - Error handling
 */
@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Create a new customer
     */
    public Customer createCustomer(Customer customer) {
        // Validate email uniqueness
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + customer.getEmail());
        }
        
        return customerRepository.save(customer);
    }

    /**
     * Get customer by ID
     */
    @Transactional(readOnly = true)
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    /**
     * Get customer by email
     */
    @Transactional(readOnly = true)
    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with email: " + email));
    }

    /**
     * Update customer
     */
    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer customer = getCustomerById(id);
        
        // Check if email is being changed and if it's already taken
        if (!customer.getEmail().equals(customerDetails.getEmail()) &&
            customerRepository.existsByEmail(customerDetails.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + customerDetails.getEmail());
        }
        
        customer.setFirstName(customerDetails.getFirstName());
        customer.setLastName(customerDetails.getLastName());
        customer.setEmail(customerDetails.getEmail());
        customer.setPhone(customerDetails.getPhone());
        customer.setStatus(customerDetails.getStatus());
        
        return customerRepository.save(customer);
    }

    /**
     * Delete customer
     */
    public void deleteCustomer(Long id) {
        Customer customer = getCustomerById(id);
        customerRepository.delete(customer);
    }

    /**
     * Get all customers with pagination
     */
    @Transactional(readOnly = true)
    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    /**
     * Get customers by status
     */
    @Transactional(readOnly = true)
    public List<Customer> getCustomersByStatus(CustomerStatus status) {
        return customerRepository.findByStatus(status);
    }

    /**
     * Get customers by status with pagination
     */
    @Transactional(readOnly = true)
    public Page<Customer> getCustomersByStatus(CustomerStatus status, Pageable pageable) {
        return customerRepository.findByStatus(status, pageable);
    }

    /**
     * Search customers by name
     */
    @Transactional(readOnly = true)
    public List<Customer> searchCustomersByName(String firstName, String lastName) {
        return customerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                firstName, lastName);
    }

    /**
     * Search customers by name with pagination
     */
    @Transactional(readOnly = true)
    public Page<Customer> searchCustomersByName(String firstName, String lastName, Pageable pageable) {
        return customerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                firstName, lastName, pageable);
    }

    /**
     * Get customers by email domain
     */
    @Transactional(readOnly = true)
    public List<Customer> getCustomersByEmailDomain(String emailDomain) {
        return customerRepository.findByEmailDomain(emailDomain);
    }

    /**
     * Get customers created after a specific date
     */
    @Transactional(readOnly = true)
    public List<Customer> getCustomersCreatedAfter(LocalDateTime startDate) {
        return customerRepository.findCustomersCreatedAfter(startDate);
    }

    /**
     * Count customers by status
     */
    @Transactional(readOnly = true)
    public long countCustomersByStatus(CustomerStatus status) {
        return customerRepository.countByStatus(status);
    }

    /**
     * Count active customers after a specific date
     */
    @Transactional(readOnly = true)
    public long countActiveCustomersAfter(CustomerStatus status, LocalDateTime startDate) {
        return customerRepository.countActiveCustomersAfter(status, startDate);
    }

    /**
     * Get customers with orders by status
     */
    @Transactional(readOnly = true)
    public List<Customer> getCustomersWithOrdersByStatus(String orderStatus) {
        return customerRepository.findCustomersWithOrdersByStatus(orderStatus);
    }

    /**
     * Get customers with more than specified number of orders
     */
    @Transactional(readOnly = true)
    public List<Customer> getCustomersWithMoreThanOrders(int minOrders) {
        return customerRepository.findCustomersWithMoreThanOrders(minOrders);
    }

    /**
     * Get average orders per customer
     */
    @Transactional(readOnly = true)
    public Double getAverageOrdersPerCustomer() {
        return customerRepository.getAverageOrdersPerCustomer();
    }

    /**
     * Get customer count by status
     */
    @Transactional(readOnly = true)
    public List<Object[]> getCustomerCountByStatus() {
        return customerRepository.getCustomerCountByStatus();
    }

    /**
     * Get customers by year
     */
    @Transactional(readOnly = true)
    public List<Customer> getCustomersByYear(int year) {
        return customerRepository.findCustomersByYear(year);
    }

    /**
     * Update customer status
     */
    public Customer updateCustomerStatus(Long id, CustomerStatus status) {
        Customer customer = getCustomerById(id);
        customer.setStatus(status);
        return customerRepository.save(customer);
    }

    /**
     * Check if customer exists by email
     */
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }
}
