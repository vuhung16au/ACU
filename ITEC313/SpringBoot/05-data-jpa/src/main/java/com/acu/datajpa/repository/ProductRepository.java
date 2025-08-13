package com.acu.datajpa.repository;

import com.acu.datajpa.entity.Product;
import com.acu.datajpa.entity.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Product repository with Spring Data JPA
 * 
 * This repository demonstrates:
 * - Basic CRUD operations
 * - Custom query methods
 * - JPQL queries
 * - Aggregation queries
 * - Search functionality
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Basic query methods
    Optional<Product> findBySku(String sku);
    
    List<Product> findByCategory(ProductCategory category);
    
    List<Product> findByIsActiveTrue();
    
    List<Product> findByStockQuantityGreaterThan(int quantity);
    
    boolean existsBySku(String sku);

    // Search methods
    List<Product> findByNameContainingIgnoreCase(String name);
    
    List<Product> findByDescriptionContainingIgnoreCase(String description);
    
    List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String name, String description);

    // Price queries
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    List<Product> findByPriceLessThan(BigDecimal maxPrice);
    
    List<Product> findByPriceGreaterThan(BigDecimal minPrice);

    // Stock queries
    List<Product> findByStockQuantityLessThan(int maxStock);
    
    List<Product> findByStockQuantityGreaterThanAndIsActiveTrue(int minStock);

    // Pagination and sorting
    Page<Product> findByCategory(ProductCategory category, Pageable pageable);
    
    Page<Product> findByIsActiveTrue(Pageable pageable);
    
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Custom JPQL queries
    @Query("SELECT p FROM Product p WHERE p.category = :category AND p.price <= :maxPrice")
    List<Product> findProductsByCategoryAndMaxPrice(@Param("category") ProductCategory category,
                                                   @Param("maxPrice") BigDecimal maxPrice);
    
    @Query("SELECT p FROM Product p WHERE p.stockQuantity = 0 AND p.isActive = true")
    List<Product> findOutOfStockActiveProducts();
    
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% OR p.description LIKE %:keyword%")
    List<Product> searchProductsByKeyword(@Param("keyword") String keyword);

    // Aggregation queries
    @Query("SELECT AVG(p.price) FROM Product p WHERE p.category = :category")
    Double getAveragePriceByCategory(@Param("category") ProductCategory category);
    
    @Query("SELECT p.category, COUNT(p), AVG(p.price) FROM Product p GROUP BY p.category")
    List<Object[]> getProductStatisticsByCategory();
    
    @Query("SELECT COUNT(p) FROM Product p WHERE p.stockQuantity = 0")
    long countOutOfStockProducts();

    // Complex queries
    @Query("SELECT p FROM Product p WHERE p.category = :category AND p.price >= :minPrice " +
           "AND p.stockQuantity > 0 AND p.isActive = true")
    List<Product> findAvailableProductsByCategoryAndMinPrice(
            @Param("category") ProductCategory category,
            @Param("minPrice") BigDecimal minPrice);

    // Native SQL queries
    @Query(value = "SELECT * FROM products WHERE stock_quantity <= :threshold", nativeQuery = true)
    List<Product> findLowStockProducts(@Param("threshold") int threshold);
    
    @Query(value = "SELECT category, COUNT(*) as count FROM products GROUP BY category", nativeQuery = true)
    List<Object[]> getProductCountByCategoryNative();
    
    @Query(value = "SELECT * FROM products WHERE MATCH(name, description) AGAINST(:searchTerm)", nativeQuery = true)
    List<Product> fullTextSearch(@Param("searchTerm") String searchTerm);
}
