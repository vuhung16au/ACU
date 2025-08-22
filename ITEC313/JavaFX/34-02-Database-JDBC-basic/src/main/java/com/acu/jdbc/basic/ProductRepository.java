package com.acu.jdbc.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;

/**
 * Repository class for handling product-related database operations.
 * Implements the Repository pattern for product entity.
 *
 * @author Nicola Bicocchi
 */
public class ProductRepository {
    private final static Logger LOG = LoggerFactory.getLogger(ProductRepository.class);
    private final DatabaseConnectionManager connectionManager;

    public ProductRepository(DatabaseConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    /**
     * Creates a table for products filled with some example data.
     * OK only for debug/dev purposes. Not to use in production!
     */
    public void initializeProductTable() {
        LOG.info("** initializeProductTable() **");
        try (Connection connection = connectionManager.getConnection(); 
             Statement statement = connection.createStatement()) {
            statement.addBatch("DROP TABLE IF EXISTS product");
            statement.addBatch("CREATE TABLE product (id SERIAL PRIMARY KEY, name VARCHAR(255) NOT NULL, price DECIMAL(10, 2) NOT NULL)");
            statement.addBatch("INSERT INTO product (name, price) VALUES('Laptop', 999.99)");
            statement.addBatch("INSERT INTO product (name, price) VALUES('Mouse', 29.99)");
            statement.addBatch("INSERT INTO product (name, price) VALUES('Keyboard', 89.99)");
            statement.addBatch("INSERT INTO product (name, price) VALUES('Monitor', 299.99)");
            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize product table", e);
        }
    }

    /**
     * Reads the content of the product table limiting results to 100
     * Useful for large tables
     */
    public void displayAllProducts() {
        LOG.info("** displayAllProducts() **");
        try (Connection connection = connectionManager.getConnection(); 
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM product LIMIT 100"); 
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                LOG.info(rowToString(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to display products", e);
        }
    }

    /**
     * Update the content of one record of the product table
     */
    public void updateProductPrice(int productId, java.math.BigDecimal newPrice) {
        LOG.info("** updateProductPrice() **");
        try (Connection connection = connectionManager.getConnection(); 
             PreparedStatement ps = connection.prepareStatement("UPDATE product SET price=? WHERE id=?")) {
            ps.setBigDecimal(1, newPrice);
            ps.setInt(2, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update product price", e);
        }
    }

    /**
     * Transform the current ResultSet row into a String
     */
    private String rowToString(ResultSet rs) throws SQLException {
        return String.format("--> id=%d, name=%s, price=%.2f", 
                           rs.getInt("id"), 
                           rs.getString("name"), 
                           rs.getBigDecimal("price"));
    }

    /**
     * Test a simple transaction involving changes to two different products.
     */
    public void demonstrateTransactionManagement() {
        LOG.info("** demonstrateTransactionManagement() **");
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE product SET price=? WHERE id=?")) {
            // disable auto-commit
            connection.setAutoCommit(false);

            // the first product has price 100.00
            ps.setBigDecimal(1, new java.math.BigDecimal("100.00"));
            ps.setInt(2, 1);
            ps.executeUpdate();

            // the second product has price 200.00
            ps.setBigDecimal(1, new java.math.BigDecimal("200.00"));
            ps.setInt(2, 2);
            ps.executeUpdate();

            // all changes are actually committed together
            connection.commit();

            // re-enable auto-commit
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to demonstrate transaction management", e);
        }
    }
}
