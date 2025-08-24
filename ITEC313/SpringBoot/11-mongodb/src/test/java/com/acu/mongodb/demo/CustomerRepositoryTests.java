package com.acu.mongodb.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerRepositoryTests {

    @Autowired
    private CustomerRepository repository;

    @Test
    void testCustomerRepository() {
        // Get initial count
        long initialCount = repository.count();
        
        // Create a unique test customer name using timestamp
        String uniqueName = "TestCustomer" + System.currentTimeMillis();
        
        // Save a few customers
        repository.save(new Customer(uniqueName, "Test", "Sydney", "Australia"));
        repository.save(new Customer("Bob", "Johnson", "Melbourne", "Australia"));
        repository.save(new Customer("Charlie", "Williams", "Sydney", "Australia"));
        repository.save(new Customer("Diana", "Brown", "Brisbane", "Australia"));
        repository.save(new Customer("Eve", "Jones", "Perth", "Australia"));

        // Fetch all customers
        Iterable<Customer> customers = repository.findAll();
        assertThat(customers).hasSize((int) (initialCount + 5));

        // Fetch customers by city
        Iterable<Customer> sydneyCustomers = repository.findByCity("Sydney");
        assertThat(sydneyCustomers).hasSizeGreaterThanOrEqualTo(2);

        // Fetch customers by country
        Iterable<Customer> australiaCustomers = repository.findByCountry("Australia");
        assertThat(australiaCustomers).hasSizeGreaterThanOrEqualTo(5);

        // Fetch an individual customer by first name
        Iterable<Customer> testCustomers = repository.findByFirstName(uniqueName);
        assertThat(testCustomers).hasSize(1);
        Customer testCustomer = testCustomers.iterator().next();
        assertThat(testCustomer.lastName).isEqualTo("Test");
        assertThat(testCustomer.city).isEqualTo("Sydney");
        assertThat(testCustomer.country).isEqualTo("Australia");

        // Test finding customers by last name (Johnson)
        Iterable<Customer> johnsons = repository.findByLastName("Johnson");
        assertThat(johnsons).hasSizeGreaterThanOrEqualTo(1);
        
        // Test finding customers by last name that doesn't exist
        Iterable<Customer> nonExistent = repository.findByLastName("NonExistent");
        assertThat(nonExistent).isEmpty();
    }

}
