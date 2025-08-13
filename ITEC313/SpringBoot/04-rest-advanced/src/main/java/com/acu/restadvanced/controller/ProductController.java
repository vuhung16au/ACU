package com.acu.restadvanced.controller;

import com.acu.restadvanced.dto.ProductDto;
import com.acu.restadvanced.model.Product;
import com.acu.restadvanced.model.ProductCategory;
import com.acu.restadvanced.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * REST Controller for Product operations with advanced features
 * 
 * This controller demonstrates:
 * - Advanced REST API patterns
 * - HATEOAS support
 * - OpenAPI/Swagger documentation
 * - API versioning
 * - Comprehensive validation
 * - Error handling
 */
@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Product Management", description = "APIs for managing products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Get all products with optional filtering
     */
    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieve all products with optional filtering")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved products"),
        @ApiResponse(responseCode = "400", description = "Invalid filter parameters")
    })
    public ResponseEntity<CollectionModel<EntityModel<Product>>> getAllProducts(
            @Parameter(description = "Filter by category") @RequestParam(required = false) String category,
            @Parameter(description = "Minimum price filter") @RequestParam(required = false) BigDecimal minPrice,
            @Parameter(description = "Maximum price filter") @RequestParam(required = false) BigDecimal maxPrice) {
        
        List<Product> products = productService.getAllProducts(category, minPrice, maxPrice);
        
        List<EntityModel<Product>> productModels = products.stream()
                .map(this::createProductModel)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<Product>> collectionModel = CollectionModel.of(productModels);
        collectionModel.add(linkTo(methodOn(ProductController.class).getAllProducts(category, minPrice, maxPrice)).withSelfRel());
        collectionModel.add(linkTo(methodOn(ProductController.class).getProductStatistics()).withRel("statistics"));
        
        return ResponseEntity.ok(collectionModel);
    }

    /**
     * Get product by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieve a specific product by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved product"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<EntityModel<Product>> getProductById(
            @Parameter(description = "Product ID") @PathVariable Long id) {
        
        Product product = productService.getProductById(id);
        EntityModel<Product> productModel = createProductModel(product);
        
        return ResponseEntity.ok(productModel);
    }

    /**
     * Create a new product
     */
    @PostMapping
    @Operation(summary = "Create a new product", description = "Create a new product with validation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid product data")
    })
    public ResponseEntity<EntityModel<Product>> createProduct(
            @Parameter(description = "Product data") @Valid @RequestBody ProductDto productDto) {
        
        Product createdProduct = productService.createProduct(productDto);
        EntityModel<Product> productModel = createProductModel(createdProduct);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(productModel);
    }

    /**
     * Update an existing product
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Update an existing product by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product updated successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "400", description = "Invalid product data")
    })
    public ResponseEntity<EntityModel<Product>> updateProduct(
            @Parameter(description = "Product ID") @PathVariable Long id,
            @Parameter(description = "Updated product data") @Valid @RequestBody ProductDto productDto) {
        
        Product updatedProduct = productService.updateProduct(id, productDto);
        EntityModel<Product> productModel = createProductModel(updatedProduct);
        
        return ResponseEntity.ok(productModel);
    }

    /**
     * Delete a product
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "Delete a product by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "Product ID") @PathVariable Long id) {
        
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Update product stock
     */
    @PatchMapping("/{id}/stock")
    @Operation(summary = "Update product stock", description = "Update the stock quantity of a product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock updated successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "400", description = "Invalid stock quantity")
    })
    public ResponseEntity<EntityModel<Product>> updateStock(
            @Parameter(description = "Product ID") @PathVariable Long id,
            @Parameter(description = "New stock quantity") @RequestParam Integer quantity) {
        
        Product updatedProduct = productService.updateStock(id, quantity);
        EntityModel<Product> productModel = createProductModel(updatedProduct);
        
        return ResponseEntity.ok(productModel);
    }

    /**
     * Get products by category
     */
    @GetMapping("/category/{category}")
    @Operation(summary = "Get products by category", description = "Retrieve all products in a specific category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved products"),
        @ApiResponse(responseCode = "400", description = "Invalid category")
    })
    public ResponseEntity<CollectionModel<EntityModel<Product>>> getProductsByCategory(
            @Parameter(description = "Product category") @PathVariable ProductCategory category) {
        
        List<Product> products = productService.getProductsByCategory(category);
        
        List<EntityModel<Product>> productModels = products.stream()
                .map(this::createProductModel)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<Product>> collectionModel = CollectionModel.of(productModels);
        collectionModel.add(linkTo(methodOn(ProductController.class).getProductsByCategory(category)).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    /**
     * Search products by name
     */
    @GetMapping("/search")
    @Operation(summary = "Search products", description = "Search products by name")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved products")
    })
    public ResponseEntity<CollectionModel<EntityModel<Product>>> searchProducts(
            @Parameter(description = "Search term") @RequestParam(required = false) String name) {
        
        List<Product> products = productService.searchProductsByName(name);
        
        List<EntityModel<Product>> productModels = products.stream()
                .map(this::createProductModel)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<Product>> collectionModel = CollectionModel.of(productModels);
        collectionModel.add(linkTo(methodOn(ProductController.class).searchProducts(name)).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    /**
     * Get product statistics
     */
    @GetMapping("/statistics")
    @Operation(summary = "Get product statistics", description = "Retrieve product statistics and analytics")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved statistics")
    })
    public ResponseEntity<Map<String, Object>> getProductStatistics() {
        Map<String, Object> statistics = productService.getProductStatistics();
        return ResponseEntity.ok(statistics);
    }

    /**
     * Get available categories
     */
    @GetMapping("/categories")
    @Operation(summary = "Get available categories", description = "Retrieve all available product categories")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved categories")
    })
    public ResponseEntity<ProductCategory[]> getCategories() {
        return ResponseEntity.ok(ProductCategory.values());
    }

    /**
     * Create HATEOAS model for a product
     */
    private EntityModel<Product> createProductModel(Product product) {
        EntityModel<Product> productModel = EntityModel.of(product);
        
        // Add self link
        productModel.add(linkTo(methodOn(ProductController.class).getProductById(product.getId())).withSelfRel());
        
        // Add category link
        productModel.add(linkTo(methodOn(ProductController.class).getProductsByCategory(product.getCategory())).withRel("category"));
        
        // Add collection link
        productModel.add(linkTo(methodOn(ProductController.class).getAllProducts(null, null, null)).withRel("products"));
        
        return productModel;
    }
}
