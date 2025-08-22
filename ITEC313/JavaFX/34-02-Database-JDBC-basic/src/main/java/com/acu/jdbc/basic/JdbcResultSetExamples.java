package com.acu.jdbc.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;

/**
 * Demonstrates various ResultSet types and operations in JDBC.
 * Shows scrollable, updatable, and sensitive ResultSet capabilities.
 *
 */
public class JdbcResultSetExamples {
    private final static Logger LOG = LoggerFactory.getLogger(JdbcResultSetExamples.class);
    private final DatabaseConnectionManager connectionManager;

    public JdbcResultSetExamples(DatabaseConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    /**
     * Test Scrollable ResultSet over the product table
     */
    public void demonstrateScrollableResultSet() {
        LOG.info("** demonstrateScrollableResultSet() **");
        try (Connection connection = connectionManager.getConnection(); 
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM product LIMIT 100", 
                                                              ResultSet.TYPE_SCROLL_SENSITIVE, 
                                                              ResultSet.CONCUR_UPDATABLE)) {
            ResultSet rs = ps.executeQuery();
            // Third record
            rs.absolute(2);
            LOG.info(rowToString(rs));
            // Previous record
            rs.previous();
            LOG.info(rowToString(rs));
            // +2 records from current position
            rs.relative(2);
            LOG.info(rowToString(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Failed to demonstrate scrollable ResultSet", e);
        }
    }

    /**
     * Test Updateable ResultSet over the product table
     */
    public void demonstrateUpdatableResultSet() {
        LOG.info("** demonstrateUpdatableResultSet() **");
        try (Connection connection = connectionManager.getConnection(); 
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM product LIMIT 100", 
                                                              ResultSet.TYPE_SCROLL_SENSITIVE, 
                                                              ResultSet.CONCUR_UPDATABLE); 
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                java.math.BigDecimal price = rs.getBigDecimal("price");
                rs.updateBigDecimal("price", price.add(new java.math.BigDecimal("10.00")));
                rs.updateRow();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to demonstrate updatable ResultSet", e);
        }
    }

    /**
     * Test Sensitive ResultSet over the product table
     */
    public void demonstrateSensitiveResultSet() {
        LOG.info("** demonstrateSensitiveResultSet() **");
        try (Connection connection = connectionManager.getConnection(); 
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM product LIMIT 100 OFFSET 0", 
                                                              ResultSet.TYPE_SCROLL_SENSITIVE, 
                                                              ResultSet.CONCUR_UPDATABLE)) {
            ResultSet rs = ps.executeQuery();
            for (int retry = 0; retry < 10; retry++) {
                System.out.printf("[%d] awaiting for external changes 10s...\n", retry);
                rs.beforeFirst();
                while (rs.next()) {
                    rs.refreshRow();
                    LOG.info(rowToString(rs));
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ignored) {
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to demonstrate sensitive ResultSet", e);
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
}
