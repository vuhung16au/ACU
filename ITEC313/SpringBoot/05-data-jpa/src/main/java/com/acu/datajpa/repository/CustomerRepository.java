package com.acu.datajpa.repository;

import com.acu.datajpa.entity.Customer;
import com.acu.datajpa.entity.CustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Customer repository with Spring Data JPA
 * 
 * This repository demonstrates:
 * - Basic CRUD operations
 * - Custom query methods
 * - JPQL queries
 * - Pagination and sorting
 * - Projections
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Basic query methods
    Optional<Customer> findByEmail(String email);
    
    List<Customer> findByStatus(CustomerStatus status);
    
    List<Customer> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName, String lastName);
    
    boolean existsByEmail(String email);
    
    long countByStatus(CustomerStatus status);

    // Pagination and sorting
    Page<Customer> findByStatus(CustomerStatus status, Pageable pageable);
    
    Page<Customer> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName, String lastName, Pageable pageable);

    // Custom JPQL queries
    @Query("SELECT c FROM Customer c WHERE c.email LIKE %:emailDomain")
    List<Customer> findByEmailDomain(@Param("emailDomain") String emailDomain);
    
    @Query("SELECT c FROM Customer c WHERE c.createdAt >= :startDate")
    List<Customer> findCustomersCreatedAfter(@Param("startDate") java.time.LocalDateTime startDate);
    
    @Query("SELECT COUNT(c) FROM Customer c WHERE c.status = :status AND c.createdAt >= :startDate")
    long countActiveCustomersAfter(@Param("status") CustomerStatus status, 
                                   @Param("startDate") java.time.LocalDateTime startDate);

    // Complex queries with joins
    @Query("SELECT DISTINCT c FROM Customer c JOIN c.orders o WHERE o.status = :orderStatus")
    List<Customer> findCustomersWithOrdersByStatus(@Param("orderStatus") String orderStatus);
    
    @Query("SELECT c FROM Customer c WHERE SIZE(c.orders) > :minOrders")
    List<Customer> findCustomersWithMoreThanOrders(@Param("minOrders") int minOrders);

    // Aggregation queries
    @Query("SELECT AVG(SIZE(c.orders)) FROM Customer c")
    Double getAverageOrdersPerCustomer();
    
    @Query("SELECT c.status, COUNT(c) FROM Customer c GROUP BY c.status")
    List<Object[]> getCustomerCountByStatus();

    // Native SQL queries
    @Query(value = "SELECT * FROM customers WHERE YEAR(created_at) = :year", nativeQuery = true)
    List<Customer> findCustomersByYear(@Param("year") int year);
    
    @Query(value = "SELECT COUNT(*) FROM customers WHERE status = :status", nativeQuery = true)
    long countCustomersByStatusNative(@Param("status") String status);
}
