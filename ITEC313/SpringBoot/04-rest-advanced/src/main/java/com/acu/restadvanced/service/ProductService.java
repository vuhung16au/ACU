package com.acu.restadvanced.service;

import com.acu.restadvanced.dto.ProductDto;
import com.acu.restadvanced.exception.BusinessException;
import com.acu.restadvanced.exception.ResourceNotFoundException;
import com.acu.restadvanced.model.Product;
import com.acu.restadvanced.model.ProductCategory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Product service with in-memory storage
 * 
 * This service demonstrates:
 * - Business logic implementation
 * - In-memory data storage
 * - Exception handling
 * - Data transformation between DTO and entity
 */
@Service
public class ProductService {

    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public ProductService() {
        // Initialize with sample data
        initializeSampleData();
    }

    /**
     * Get all products with optional filtering
     */
    public List<Product> getAllProducts(String category, BigDecimal minPrice, BigDecimal maxPrice) {
        return products.values().stream()
                .filter(product -> category == null || product.getCategory().name().equalsIgnoreCase(category))
                .filter(product -> minPrice == null || product.getPrice().compareTo(minPrice) >= 0)
                .filter(product -> maxPrice == null || product.getPrice().compareTo(maxPrice) <= 0)
                .sorted(Comparator.comparing(Product::getName))
                .collect(Collectors.toList());
    }

    /**
     * Get product by ID
     */
    public Product getProductById(Long id) {
        Product product = products.get(id);
        if (product == null) {
            throw new ResourceNotFoundException("Product", "id", id);
        }
        return product;
    }

    /**
     * Create a new product
     */
    public Product createProduct(ProductDto productDto) {
        validateProductData(productDto);
        
        Product product = new Product();
        product.setId(idGenerator.getAndIncrement());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategory(productDto.getCategory());
        product.setStockQuantity(productDto.getStockQuantity());
        
        products.put(product.getId(), product);
        return product;
    }

    /**
     * Update an existing product
     */
    public Product updateProduct(Long id, ProductDto productDto) {
        Product existingProduct = getProductById(id);
        validateProductData(productDto);
        
        existingProduct.setName(productDto.getName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setCategory(productDto.getCategory());
        existingProduct.setStockQuantity(productDto.getStockQuantity());
        
        return existingProduct;
    }

    /**
     * Delete a product
     */
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        products.remove(product.getId());
    }

    /**
     * Update product stock
     */
    public Product updateStock(Long id, Integer quantity) {
        Product product = getProductById(id);
        
        if (quantity < 0) {
            throw new BusinessException("Stock quantity cannot be negative");
        }
        
        product.setStockQuantity(quantity);
        return product;
    }

    /**
     * Get products by category
     */
    public List<Product> getProductsByCategory(ProductCategory category) {
        return products.values().stream()
                .filter(product -> product.getCategory() == category)
                .sorted(Comparator.comparing(Product::getName))
                .collect(Collectors.toList());
    }

    /**
     * Search products by name
     */
    public List<Product> searchProductsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return new ArrayList<>(products.values());
        }
        
        String searchTerm = name.toLowerCase().trim();
        return products.values().stream()
                .filter(product -> product.getName().toLowerCase().contains(searchTerm))
                .sorted(Comparator.comparing(Product::getName))
                .collect(Collectors.toList());
    }

    /**
     * Get product statistics
     */
    public Map<String, Object> getProductStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalProducts", products.size());
        stats.put("totalValue", products.values().stream()
                .mapToDouble(p -> p.getPrice().doubleValue() * p.getStockQuantity())
                .sum());
        
        Map<ProductCategory, Long> categoryCount = products.values().stream()
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.counting()));
        stats.put("categoryDistribution", categoryCount);
        
        Optional<Product> mostExpensive = products.values().stream()
                .max(Comparator.comparing(Product::getPrice));
        stats.put("mostExpensiveProduct", mostExpensive.orElse(null));
        
        return stats;
    }

    /**
     * Validate product data
     */
    private void validateProductData(ProductDto productDto) {
        if (productDto.getName() == null || productDto.getName().trim().isEmpty()) {
            throw new BusinessException("Product name is required");
        }
        
        if (productDto.getPrice() == null || productDto.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Product price must be greater than zero");
        }
        
        if (productDto.getCategory() == null) {
            throw new BusinessException("Product category is required");
        }
    }

    /**
     * Initialize sample data
     */
    private void initializeSampleData() {
        createProduct(new ProductDto("Laptop", "High-performance laptop with latest specifications", 
                new BigDecimal("1299.99"), ProductCategory.ELECTRONICS, 50));
        
        createProduct(new ProductDto("Smartphone", "Latest smartphone with advanced camera features", 
                new BigDecimal("899.99"), ProductCategory.ELECTRONICS, 100));
        
        createProduct(new ProductDto("Running Shoes", "Comfortable running shoes for professional athletes", 
                new BigDecimal("129.99"), ProductCategory.SPORTS, 75));
        
        createProduct(new ProductDto("Coffee Maker", "Automatic coffee maker with programmable features", 
                new BigDecimal("199.99"), ProductCategory.HOME_AND_GARDEN, 30));
        
        createProduct(new ProductDto("Programming Book", "Comprehensive guide to Spring Boot development", 
                new BigDecimal("49.99"), ProductCategory.BOOKS, 200));
    }
}
