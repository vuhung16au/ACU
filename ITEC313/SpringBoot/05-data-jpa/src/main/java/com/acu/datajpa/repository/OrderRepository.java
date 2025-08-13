package com.acu.datajpa.repository;

import com.acu.datajpa.entity.Order;
import com.acu.datajpa.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Order repository with Spring Data JPA
 * 
 * This repository demonstrates:
 * - Basic CRUD operations
 * - Custom query methods
 * - JPQL queries with joins
 * - Aggregation queries
 * - Date range queries
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Basic query methods
    Optional<Order> findByOrderNumber(String orderNumber);
    
    List<Order> findByStatus(OrderStatus status);
    
    List<Order> findByCustomerId(Long customerId);
    
    List<Order> findByCustomerEmail(String customerEmail);
    
    boolean existsByOrderNumber(String orderNumber);

    // Date range queries
    List<Order> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<Order> findByStatusAndCreatedAtBetween(OrderStatus status, LocalDateTime startDate, LocalDateTime endDate);

    // Amount queries
    List<Order> findByTotalAmountGreaterThan(BigDecimal amount);
    
    List<Order> findByTotalAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);

    // Pagination and sorting
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);
    
    Page<Order> findByCustomerId(Long customerId, Pageable pageable);
    
    Page<Order> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    // Custom JPQL queries
    @Query("SELECT o FROM Order o WHERE o.customer.email = :email")
    List<Order> findOrdersByCustomerEmail(@Param("email") String email);
    
    @Query("SELECT o FROM Order o WHERE o.totalAmount > :minAmount AND o.status = :status")
    List<Order> findHighValueOrdersByStatus(@Param("minAmount") BigDecimal minAmount, 
                                           @Param("status") OrderStatus status);
    
    @Query("SELECT o FROM Order o JOIN o.orderItems oi WHERE oi.product.id = :productId")
    List<Order> findOrdersContainingProduct(@Param("productId") Long productId);

    // Aggregation queries
    @Query("SELECT AVG(o.totalAmount) FROM Order o WHERE o.status = :status")
    Double getAverageOrderAmountByStatus(@Param("status") OrderStatus status);
    
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.createdAt >= :startDate")
    BigDecimal getTotalRevenueAfter(@Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT o.status, COUNT(o), SUM(o.totalAmount) FROM Order o GROUP BY o.status")
    List<Object[]> getOrderStatisticsByStatus();

    // Complex queries with multiple joins
    @Query("SELECT o FROM Order o JOIN o.customer c JOIN o.orderItems oi " +
           "WHERE c.status = :customerStatus AND oi.product.category = :productCategory")
    List<Order> findOrdersByCustomerStatusAndProductCategory(
            @Param("customerStatus") String customerStatus,
            @Param("productCategory") String productCategory);

    // Native SQL queries
    @Query(value = "SELECT * FROM orders WHERE DATE(created_at) = CURDATE()", nativeQuery = true)
    List<Order> findTodaysOrders();
    
    @Query(value = "SELECT COUNT(*) FROM orders WHERE status = :status AND DATE(created_at) = CURDATE()", 
           nativeQuery = true)
    long countTodaysOrdersByStatus(@Param("status") String status);
    
    @Query(value = "SELECT SUM(total_amount) FROM orders WHERE YEAR(created_at) = :year", nativeQuery = true)
    BigDecimal getTotalRevenueByYear(@Param("year") int year);
}
