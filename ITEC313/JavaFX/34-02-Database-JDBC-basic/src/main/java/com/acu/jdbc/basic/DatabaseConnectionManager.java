package com.acu.jdbc.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages database connections using standard JDBC.
 * Handles the creation and configuration of database connections.
 *
 * @author Nicola Bicocchi
 */
public class DatabaseConnectionManager {
    private final static Logger LOG = LoggerFactory.getLogger(DatabaseConnectionManager.class);

    public DatabaseConnectionManager() {
        initializeDatabaseConnection();
    }

    /**
     * Initializes the database connection configuration
     */
    private void initializeDatabaseConnection() {
        LOG.info("** initializeDatabaseConnection() **");
        try {
            // Load the PostgreSQL JDBC driver
            Class.forName(DatabaseUtils.JDBC_Driver_PostgreSQL);
            LOG.info("PostgreSQL JDBC driver loaded successfully");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load PostgreSQL JDBC driver", e);
        }
    }

    /**
     * Gets a new connection to the database
     * @return Connection to the database
     * @throws java.sql.SQLException if connection cannot be obtained
     */
    public java.sql.Connection getConnection() throws java.sql.SQLException {
        return java.sql.DriverManager.getConnection(DatabaseUtils.JDBC_URL_PostgreSQL);
    }

    /**
     * Closes the connection manager (no-op for standard JDBC)
     */
    public void close() {
        LOG.info("Database connection manager closed");
        // No specific cleanup needed for standard JDBC connections
    }
}
