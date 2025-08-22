package com.acu.jdbc.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class for testing basic operations with JDBC.
 * This is the main application class that orchestrates the JDBC demonstrations.
 *
 */
public class JdbcDemoApplication {
    private final static Logger LOG = LoggerFactory.getLogger(JdbcDemoApplication.class);
    private DatabaseConnectionManager connectionManager;
    private ProductRepository productRepository;
    private JdbcResultSetExamples resultSetExamples;

    private JdbcDemoApplication() {
        initializeComponents();
        runDemonstrations();
    }

    /**
     * Initialize all components
     */
    private void initializeComponents() {
        LOG.info("** Initializing Components **");
        connectionManager = new DatabaseConnectionManager();
        productRepository = new ProductRepository(connectionManager);
        resultSetExamples = new JdbcResultSetExamples(connectionManager);
    }

    /**
     * Run all JDBC demonstrations
     */
    private void runDemonstrations() {
        LOG.info("** Starting JDBC Demonstrations **");
        
        // Initialize product table
        productRepository.initializeProductTable();
        
        // Display products
        productRepository.displayAllProducts();
        
        // Update product price
        productRepository.updateProductPrice(1, new java.math.BigDecimal("1099.99"));
        
        // Display products again
        productRepository.displayAllProducts();
        
        // Demonstrate ResultSet operations
        resultSetExamples.demonstrateScrollableResultSet();
        
        // Demonstrate transaction management
        productRepository.demonstrateTransactionManagement();
        
        // Display final state
        productRepository.displayAllProducts();
        
        // Clean up
        cleanup();
    }

    /**
     * Clean up resources
     */
    private void cleanup() {
        LOG.info("** Cleaning up resources **");
        if (connectionManager != null) {
            connectionManager.close();
        }
    }

    public static void main(String[] args) {
        new JdbcDemoApplication();
    }
}
