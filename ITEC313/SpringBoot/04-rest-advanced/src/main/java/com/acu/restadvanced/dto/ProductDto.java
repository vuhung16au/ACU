package com.acu.restadvanced.dto;

import com.acu.restadvanced.model.ProductCategory;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * Data Transfer Object for Product operations
 * 
 * This class demonstrates:
 * - DTO pattern for API requests/responses
 * - Validation annotations for input validation
 * - Separation of concerns between entity and DTO
 */
public class ProductDto {

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Product description is required")
    @Size(min = 10, max = 500, message = "Product description must be between 10 and 500 characters")
    private String description;

    @NotNull(message = "Product price is required")
    @DecimalMin(value = "0.01", message = "Product price must be greater than 0")
    @DecimalMax(value = "999999.99", message = "Product price cannot exceed 999,999.99")
    private BigDecimal price;

    @NotNull(message = "Product category is required")
    private ProductCategory category;

    @Min(value = 0, message = "Stock quantity cannot be negative")
    @Max(value = 10000, message = "Stock quantity cannot exceed 10,000")
    private Integer stockQuantity = 0;

    // Constructors
    public ProductDto() {}

    public ProductDto(String name, String description, BigDecimal price, ProductCategory category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public ProductDto(String name, String description, BigDecimal price, ProductCategory category, Integer stockQuantity) {
        this(name, description, price, category);
        this.stockQuantity = stockQuantity;
    }

    // Getters and Setters
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

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
}
