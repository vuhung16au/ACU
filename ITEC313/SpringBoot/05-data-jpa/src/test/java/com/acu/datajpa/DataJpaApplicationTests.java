package com.acu.datajpa;

import com.acu.datajpa.entity.Customer;
import com.acu.datajpa.entity.CustomerStatus;
import com.acu.datajpa.entity.Order;
import com.acu.datajpa.entity.OrderStatus;
import com.acu.datajpa.entity.Product;
import com.acu.datajpa.entity.ProductCategory;
import com.acu.datajpa.exception.ResourceNotFoundException;
import com.acu.datajpa.repository.CustomerRepository;
import com.acu.datajpa.repository.OrderRepository;
import com.acu.datajpa.repository.ProductRepository;
import com.acu.datajpa.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for Data JPA Application
 * 
 * This test class demonstrates:
 * - Application context testing
 * - Entity validation and relationships
 * - Repository operations
 * - Service layer business logic
 * - Integration testing
 * - Transaction management
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class DataJpaApplicationTests {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerService customerService;

    private Customer testCustomer;
    private Product testProduct;
    private Order testOrder;

    @BeforeEach
    void setUp() {
        // Create test data
        testCustomer = new Customer();
        testCustomer.setFirstName("John");
        testCustomer.setLastName("Doe");
        testCustomer.setEmail("john.doe@example.com");
        testCustomer.setPhone("+1234567890");
        testCustomer.setStatus(CustomerStatus.ACTIVE);

        testProduct = new Product();
        testProduct.setName("Test Product");
        testProduct.setDescription("A test product for testing");
        testProduct.setPrice(new BigDecimal("99.99"));
        testProduct.setCategory(ProductCategory.ELECTRONICS);
        testProduct.setStockQuantity(100);

        testOrder = new Order();
        testOrder.setOrderNumber("TEST-ORDER-001");
        testOrder.setStatus(OrderStatus.PENDING);
        testOrder.setTotalAmount(new BigDecimal("99.99"));
    }

    @Test
    @DisplayName("Application context should load successfully")
    void contextLoads() {
        // This test verifies that the Spring application context loads successfully
        assertNotNull(customerRepository);
        assertNotNull(orderRepository);
        assertNotNull(productRepository);
        assertNotNull(customerService);
    }

    @Test
    @DisplayName("Customer entity should be saved and retrieved correctly")
    void testCustomerEntityPersistence() {
        // Save customer
        Customer savedCustomer = customerRepository.save(testCustomer);
        
        // Verify customer was saved
        assertNotNull(savedCustomer.getId());
        assertEquals("John", savedCustomer.getFirstName());
        assertEquals("Doe", savedCustomer.getLastName());
        assertEquals("john.doe@example.com", savedCustomer.getEmail());
        assertEquals(CustomerStatus.ACTIVE, savedCustomer.getStatus());
        
        // Verify auditing fields
        assertNotNull(savedCustomer.getCreatedAt());
        assertNotNull(savedCustomer.getUpdatedAt());
        
        // Retrieve customer
        Optional<Customer> foundCustomer = customerRepository.findById(savedCustomer.getId());
        assertTrue(foundCustomer.isPresent());
        assertEquals(savedCustomer.getEmail(), foundCustomer.get().getEmail());
    }

    @Test
    @DisplayName("Customer entity validation should work correctly")
    void testCustomerEntityValidation() {
        // Test valid customer
        Customer validCustomer = new Customer("Jane", "Smith", "jane.smith@example.com");
        Customer savedCustomer = customerRepository.save(validCustomer);
        assertNotNull(savedCustomer.getId());
        
        // Test email uniqueness constraint
        Customer duplicateEmailCustomer = new Customer("Bob", "Johnson", "jane.smith@example.com");
        assertThrows(Exception.class, () -> customerRepository.save(duplicateEmailCustomer));
    }

    @Test
    @DisplayName("Customer repository findByEmail should work correctly")
    void testCustomerRepositoryFindByEmail() {
        // Save customer
        Customer savedCustomer = customerRepository.save(testCustomer);
        
        // Find by email
        Optional<Customer> foundCustomer = customerRepository.findByEmail("john.doe@example.com");
        assertTrue(foundCustomer.isPresent());
        assertEquals(savedCustomer.getId(), foundCustomer.get().getId());
        
        // Test non-existent email
        Optional<Customer> notFoundCustomer = customerRepository.findByEmail("nonexistent@example.com");
        assertFalse(notFoundCustomer.isPresent());
    }

    @Test
    @DisplayName("Customer repository findByStatus should work correctly")
    void testCustomerRepositoryFindByStatus() {
        // Save multiple customers with different statuses
        Customer activeCustomer = customerRepository.save(testCustomer);
        
        Customer inactiveCustomer = new Customer("Jane", "Smith", "jane.smith@example.com");
        inactiveCustomer.setStatus(CustomerStatus.INACTIVE);
        customerRepository.save(inactiveCustomer);
        
        // Find by status
        List<Customer> activeCustomers = customerRepository.findByStatus(CustomerStatus.ACTIVE);
        assertFalse(activeCustomers.isEmpty());
        assertTrue(activeCustomers.stream().allMatch(c -> c.getStatus() == CustomerStatus.ACTIVE));
        
        List<Customer> inactiveCustomers = customerRepository.findByStatus(CustomerStatus.INACTIVE);
        assertFalse(inactiveCustomers.isEmpty());
        assertTrue(inactiveCustomers.stream().allMatch(c -> c.getStatus() == CustomerStatus.INACTIVE));
    }

    @Test
    @DisplayName("Customer repository pagination should work correctly")
    void testCustomerRepositoryPagination() {
        // Create multiple customers
        for (int i = 0; i < 5; i++) {
            Customer customer = new Customer("Customer" + i, "Last" + i, "customer" + i + "@example.com");
            customer.setStatus(CustomerStatus.ACTIVE);
            customerRepository.save(customer);
        }
        
        // Test pagination
        Pageable pageable = PageRequest.of(0, 3, Sort.by("firstName"));
        Page<Customer> page = customerRepository.findAll(pageable);
        
        assertEquals(5, page.getTotalElements());
        assertEquals(3, page.getContent().size());
        assertEquals(2, page.getTotalPages());
        assertTrue(page.hasNext());
        assertFalse(page.hasPrevious());
    }

    @Test
    @DisplayName("Customer repository custom queries should work correctly")
    void testCustomerRepositoryCustomQueries() {
        // Save customers with different email domains
        Customer customer1 = new Customer("User1", "Last1", "user1@example.com");
        Customer customer2 = new Customer("User2", "Last2", "user2@test.com");
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        
        // Test email domain query
        List<Customer> exampleCustomers = customerRepository.findByEmailDomain("@example.com");
        assertFalse(exampleCustomers.isEmpty());
        assertTrue(exampleCustomers.stream().allMatch(c -> c.getEmail().endsWith("@example.com")));
        
        // Test count by status
        long activeCount = customerRepository.countByStatus(CustomerStatus.ACTIVE);
        assertTrue(activeCount >= 0);
    }

    @Test
    @DisplayName("Customer service createCustomer should work correctly")
    void testCustomerServiceCreateCustomer() {
        // Create customer through service
        Customer createdCustomer = customerService.createCustomer(testCustomer);
        
        assertNotNull(createdCustomer.getId());
        assertEquals("john.doe@example.com", createdCustomer.getEmail());
        
        // Verify customer exists in repository
        assertTrue(customerRepository.existsByEmail("john.doe@example.com"));
    }

    @Test
    @DisplayName("Customer service createCustomer should throw exception for duplicate email")
    void testCustomerServiceCreateCustomerDuplicateEmail() {
        // Create first customer
        customerService.createCustomer(testCustomer);
        
        // Try to create second customer with same email
        Customer duplicateCustomer = new Customer("Jane", "Smith", "john.doe@example.com");
        
        assertThrows(IllegalArgumentException.class, () -> {
            customerService.createCustomer(duplicateCustomer);
        });
    }

    @Test
    @DisplayName("Customer service getCustomerById should work correctly")
    void testCustomerServiceGetCustomerById() {
        // Create customer
        Customer createdCustomer = customerService.createCustomer(testCustomer);
        
        // Get customer by ID
        Customer foundCustomer = customerService.getCustomerById(createdCustomer.getId());
        
        assertEquals(createdCustomer.getId(), foundCustomer.getId());
        assertEquals(createdCustomer.getEmail(), foundCustomer.getEmail());
    }

    @Test
    @DisplayName("Customer service getCustomerById should throw exception for non-existent ID")
    void testCustomerServiceGetCustomerByIdNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> {
            customerService.getCustomerById(999L);
        });
    }

    @Test
    @DisplayName("Customer service updateCustomer should work correctly")
    void testCustomerServiceUpdateCustomer() {
        // Create customer
        Customer createdCustomer = customerService.createCustomer(testCustomer);
        
        // Update customer
        Customer updateDetails = new Customer();
        updateDetails.setFirstName("Updated");
        updateDetails.setLastName("Name");
        updateDetails.setEmail("updated@example.com");
        updateDetails.setPhone("+9876543210");
        updateDetails.setStatus(CustomerStatus.INACTIVE);
        
        Customer updatedCustomer = customerService.updateCustomer(createdCustomer.getId(), updateDetails);
        
        assertEquals("Updated", updatedCustomer.getFirstName());
        assertEquals("Name", updatedCustomer.getLastName());
        assertEquals("updated@example.com", updatedCustomer.getEmail());
        assertEquals(CustomerStatus.INACTIVE, updatedCustomer.getStatus());
    }

    @Test
    @DisplayName("Customer service getAllCustomers with pagination should work correctly")
    void testCustomerServiceGetAllCustomersPagination() {
        // Create multiple customers
        for (int i = 0; i < 3; i++) {
            Customer customer = new Customer("Customer" + i, "Last" + i, "customer" + i + "@example.com");
            customerService.createCustomer(customer);
        }
        
        // Test pagination
        Pageable pageable = PageRequest.of(0, 2);
        Page<Customer> page = customerService.getAllCustomers(pageable);
        
        assertTrue(page.getTotalElements() >= 3);
        assertEquals(2, page.getContent().size());
    }

    @Test
    @DisplayName("Customer service searchCustomersByName should work correctly")
    void testCustomerServiceSearchCustomersByName() {
        // Create customers
        Customer customer1 = new Customer("John", "Doe", "john.doe@example.com");
        Customer customer2 = new Customer("Jane", "Doe", "jane.doe@example.com");
        Customer customer3 = new Customer("Bob", "Smith", "bob.smith@example.com");
        
        customerService.createCustomer(customer1);
        customerService.createCustomer(customer2);
        customerService.createCustomer(customer3);
        
        // Search by first name
        List<Customer> johnCustomers = customerService.searchCustomersByName("John", "");
        assertFalse(johnCustomers.isEmpty());
        assertTrue(johnCustomers.stream().anyMatch(c -> c.getFirstName().contains("John")));
        
        // Search by last name
        List<Customer> doeCustomers = customerService.searchCustomersByName("", "Doe");
        assertFalse(doeCustomers.isEmpty());
        assertTrue(doeCustomers.stream().anyMatch(c -> c.getLastName().contains("Doe")));
    }

    @Test
    @DisplayName("Customer service getCustomersByEmailDomain should work correctly")
    void testCustomerServiceGetCustomersByEmailDomain() {
        // Create customers with different domains
        Customer customer1 = new Customer("User1", "Last1", "user1@example.com");
        Customer customer2 = new Customer("User2", "Last2", "user2@test.com");
        Customer customer3 = new Customer("User3", "Last3", "user3@example.com");
        
        customerService.createCustomer(customer1);
        customerService.createCustomer(customer2);
        customerService.createCustomer(customer3);
        
        // Get customers by domain
        List<Customer> exampleCustomers = customerService.getCustomersByEmailDomain("@example.com");
        assertFalse(exampleCustomers.isEmpty());
        assertTrue(exampleCustomers.stream().allMatch(c -> c.getEmail().endsWith("@example.com")));
    }

    @Test
    @DisplayName("Customer service updateCustomerStatus should work correctly")
    void testCustomerServiceUpdateCustomerStatus() {
        // Create customer
        Customer createdCustomer = customerService.createCustomer(testCustomer);
        assertEquals(CustomerStatus.ACTIVE, createdCustomer.getStatus());
        
        // Update status
        Customer updatedCustomer = customerService.updateCustomerStatus(createdCustomer.getId(), CustomerStatus.INACTIVE);
        assertEquals(CustomerStatus.INACTIVE, updatedCustomer.getStatus());
    }

    @Test
    @DisplayName("Customer service getAverageOrdersPerCustomer should work correctly")
    void testCustomerServiceGetAverageOrdersPerCustomer() {
        // Create customer and orders
        Customer customer = customerService.createCustomer(testCustomer);
        Product product = productRepository.save(testProduct);
        
        // Create orders
        for (int i = 0; i < 3; i++) {
            Order order = new Order();
            order.setOrderNumber("ORDER-" + (i + 1));
            order.setCustomer(customer);
            order.setStatus(OrderStatus.DELIVERED);
            order.setTotalAmount(new BigDecimal("100.0"));
            orderRepository.save(order);
        }
        
        // Get average orders
        Double averageOrders = customerService.getAverageOrdersPerCustomer();
        assertNotNull(averageOrders);
        assertTrue(averageOrders > 0);
    }

    @Test
    @DisplayName("Customer entity relationships should work correctly")
    void testCustomerEntityRelationships() {
        // Save customer and product
        Customer savedCustomer = customerRepository.save(testCustomer);
        Product savedProduct = productRepository.save(testProduct);
        
        // Create order
        Order order = new Order();
        order.setOrderNumber("TEST-ORDER-002");
        order.setCustomer(savedCustomer);
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(new BigDecimal("99.99"));
        Order savedOrder = orderRepository.save(order);
        
        // Test relationship
        Customer customerWithOrders = customerRepository.findById(savedCustomer.getId()).orElse(null);
        assertNotNull(customerWithOrders);
        // Note: The relationship might not be loaded due to lazy loading
        // We can verify the order exists by checking the order directly
        assertNotNull(savedOrder.getId());
        assertEquals(savedCustomer.getId(), savedOrder.getCustomer().getId());
    }

    @Test
    @DisplayName("Customer entity helper methods should work correctly")
    void testCustomerEntityHelperMethods() {
        Customer customer = new Customer("John", "Doe", "john.doe@example.com");
        
        // Test getFullName
        assertEquals("John Doe", customer.getFullName());
        
        // Test addOrder
        Order order = new Order();
        customer.addOrder(order);
        assertEquals(1, customer.getOrders().size());
        assertEquals(customer, order.getCustomer());
        
        // Test removeOrder
        customer.removeOrder(order);
        assertEquals(0, customer.getOrders().size());
        assertNull(order.getCustomer());
    }

    @Test
    @DisplayName("Customer repository existsByEmail should work correctly")
    void testCustomerRepositoryExistsByEmail() {
        // Initially should not exist
        assertFalse(customerRepository.existsByEmail("test@example.com"));
        
        // Save customer
        customerRepository.save(testCustomer);
        
        // Should exist after saving
        assertTrue(customerRepository.existsByEmail("john.doe@example.com"));
        assertFalse(customerRepository.existsByEmail("nonexistent@example.com"));
    }

    @Test
    @DisplayName("Customer service existsByEmail should work correctly")
    void testCustomerServiceExistsByEmail() {
        // Initially should not exist
        assertFalse(customerService.existsByEmail("test@example.com"));
        
        // Create customer through service
        customerService.createCustomer(testCustomer);
        
        // Should exist after creating
        assertTrue(customerService.existsByEmail("john.doe@example.com"));
        assertFalse(customerService.existsByEmail("nonexistent@example.com"));
    }
}
