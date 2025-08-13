package com.acu.restadvanced.controller;

import com.acu.restadvanced.dto.ProductDto;
import com.acu.restadvanced.model.Product;
import com.acu.restadvanced.model.ProductCategory;
import com.acu.restadvanced.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for ProductController with advanced REST features
 * 
 * This test demonstrates:
 * - MockMvc testing with HATEOAS
 * - JSON request/response testing
 * - Validation testing
 * - Error handling testing
 */
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private Product sampleProduct;
    private ProductDto sampleProductDto;

    @BeforeEach
    void setUp() {
        sampleProduct = new Product();
        sampleProduct.setId(1L);
        sampleProduct.setName("Test Product");
        sampleProduct.setDescription("A test product for testing purposes");
        sampleProduct.setPrice(new BigDecimal("99.99"));
        sampleProduct.setCategory(ProductCategory.ELECTRONICS);
        sampleProduct.setStockQuantity(100);

        sampleProductDto = new ProductDto();
        sampleProductDto.setName("Test Product");
        sampleProductDto.setDescription("A test product for testing purposes");
        sampleProductDto.setPrice(new BigDecimal("99.99"));
        sampleProductDto.setCategory(ProductCategory.ELECTRONICS);
        sampleProductDto.setStockQuantity(100);
    }

    @Test
    void getAllProducts_ShouldReturnProductsWithHateoas() throws Exception {
        // Given
        List<Product> products = Arrays.asList(sampleProduct);
        when(productService.getAllProducts(any(), any(), any())).thenReturn(products);

        // When & Then
        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$._embedded.productList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.productList[0].name").value("Test Product"))
                .andExpect(jsonPath("$._embedded.productList[0]._links.self.href").exists())
                .andExpect(jsonPath("$._embedded.productList[0]._links.category.href").exists());
    }

    @Test
    void getProductById_ShouldReturnProductWithHateoas() throws Exception {
        // Given
        when(productService.getProductById(1L)).thenReturn(sampleProduct);

        // When & Then
        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.category.href").exists());
    }

    @Test
    void createProduct_WithValidData_ShouldReturnCreatedProduct() throws Exception {
        // Given
        when(productService.createProduct(any(ProductDto.class))).thenReturn(sampleProduct);

        // When & Then
        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleProductDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    void createProduct_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        // Given
        ProductDto invalidDto = new ProductDto();
        invalidDto.setName(""); // Invalid: empty name

        // When & Then
        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Error"));
    }

    @Test
    void updateProduct_WithValidData_ShouldReturnUpdatedProduct() throws Exception {
        // Given
        when(productService.updateProduct(eq(1L), any(ProductDto.class))).thenReturn(sampleProduct);

        // When & Then
        mockMvc.perform(put("/api/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleProductDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    void deleteProduct_ShouldReturnNoContent() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/v1/products/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateStock_WithValidQuantity_ShouldReturnUpdatedProduct() throws Exception {
        // Given
        when(productService.updateStock(1L, 50)).thenReturn(sampleProduct);

        // When & Then
        mockMvc.perform(patch("/api/v1/products/1/stock")
                .param("quantity", "50"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getProductsByCategory_ShouldReturnProductsInCategory() throws Exception {
        // Given
        List<Product> products = Arrays.asList(sampleProduct);
        when(productService.getProductsByCategory(ProductCategory.ELECTRONICS)).thenReturn(products);

        // When & Then
        mockMvc.perform(get("/api/v1/products/category/ELECTRONICS"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$._embedded.productList[0].category").value("ELECTRONICS"));
    }

    @Test
    void searchProducts_ShouldReturnMatchingProducts() throws Exception {
        // Given
        List<Product> products = Arrays.asList(sampleProduct);
        when(productService.searchProductsByName("Test")).thenReturn(products);

        // When & Then
        mockMvc.perform(get("/api/v1/products/search")
                .param("name", "Test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$._embedded.productList[0].name").value("Test Product"));
    }

    @Test
    void getProductStatistics_ShouldReturnStatistics() throws Exception {
        // Given
        when(productService.getProductStatistics()).thenReturn(
                java.util.Map.of("totalProducts", 5, "totalValue", 1000.0));

        // When & Then
        mockMvc.perform(get("/api/v1/products/statistics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalProducts").value(5))
                .andExpect(jsonPath("$.totalValue").value(1000.0));
    }

    @Test
    void getCategories_ShouldReturnAllCategories() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/products/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("ELECTRONICS"))
                .andExpect(jsonPath("$[1]").value("CLOTHING"));
    }
}
