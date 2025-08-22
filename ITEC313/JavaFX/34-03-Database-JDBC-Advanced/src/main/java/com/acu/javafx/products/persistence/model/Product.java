package com.acu.javafx.products.persistence.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Domain model representing a product stored in the database.
 */
public class Product {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private Integer stockQuantity;
    private LocalDateTime createdAt;

    /**
     * No-arg constructor setting sensible defaults.
     */
    public Product() {
        this.stockQuantity = 0;
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Full constructor used when loading from the database.
     */
    public Product(Long id, String name, String description, BigDecimal price, String category, Integer stockQuantity, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.stockQuantity = stockQuantity != null ? stockQuantity : 0;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    }

    /**
     * Convenience constructor for creating new products before persisting.
     */
    public Product(String name, String description, BigDecimal price, String category, Integer stockQuantity) {
        this(null, name, description, price, category, stockQuantity, null);
    }

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && 
               Objects.equals(name, product.name) && 
               Objects.equals(description, product.description) && 
               Objects.equals(price, product.price) && 
               Objects.equals(category, product.category) && 
               Objects.equals(stockQuantity, product.stockQuantity) && 
               Objects.equals(createdAt, product.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, category, stockQuantity, createdAt);
    }

    @Override
    public String toString() {
        return "Product{" + 
               "id=" + id + 
               ", name='" + name + '\'' + 
               ", description='" + description + '\'' + 
               ", price=" + price + 
               ", category='" + category + '\'' + 
               ", stockQuantity=" + stockQuantity + 
               ", createdAt=" + createdAt + 
               '}';
    }
}
