package com.acu.javafx.products.persistence.repository;

import com.acu.javafx.products.persistence.model.Product;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * JDBC repository for {@link Product} entities using HikariCP.
 */
public class ProductRepository implements Repository<Product, Long> {
    private final HikariDataSource dataSource;

    /**
     * Creates repository and ensures the table exists (dev convenience).
     */
    public ProductRepository(HikariDataSource dataSource) {
        this.dataSource = dataSource;
        checkTable();
    }

    /**
     * Attempts a simple query to verify table existence; initializes if missing.
     */
    private void checkTable() {
        String sql = "SELECT * FROM products LIMIT 1";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet ignored = statement.executeQuery()) {
                // Intentionally no-op; existence check only
            }
        } catch (SQLException e) {
            // Must be disabled in production!
            initTable();
        }
    }

    /**
     * Initializes schema for local development.
     */
    private void initTable() {
        String sql = "DROP TABLE IF EXISTS products;" +
                "CREATE TABLE products " +
                "(id SERIAL PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "description TEXT, " +
                "price DECIMAL(10, 2) NOT NULL, " +
                "category VARCHAR(100), " +
                "stock_quantity INT NOT NULL DEFAULT 0, " +
                "created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP)";
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    /**
     * Finds a product by its id.
     */
    public Optional<Product> findById(Long id) {
        String sql = "SELECT * FROM products WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            if (!rs.next()) {
                return Optional.empty();
            }

            return Optional.of(new Product(
                rs.getLong("id"), 
                rs.getString("name"), 
                rs.getString("description"), 
                rs.getBigDecimal("price"), 
                rs.getString("category"), 
                rs.getInt("stock_quantity"), 
                rs.getTimestamp("created_at").toLocalDateTime()
            ));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    /**
     * Returns all products.
     */
    public Iterable<Product> findAll() {
        String sql = "SELECT * FROM products";
        List<Product> productList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                productList.add(new Product(
                    rs.getLong("id"), 
                    rs.getString("name"), 
                    rs.getString("description"), 
                    rs.getBigDecimal("price"), 
                    rs.getString("category"), 
                    rs.getInt("stock_quantity"), 
                    rs.getTimestamp("created_at").toLocalDateTime()
                ));
            }
            return productList;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    /**
     * Inserts new or updates existing product based on id presence.
     */
    public Product save(Product entity) {
        if (Objects.isNull(entity.getId())) {
            return insert(entity);
        }

        Optional<Product> product = findById(entity.getId());
        if (product.isEmpty()) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }

    /**
     * Inserts a new product and populates generated id.
     */
    private Product insert(Product entity) {
        String sql = "INSERT INTO products (name, description, price, category, stock_quantity, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescription());
            statement.setBigDecimal(3, entity.getPrice());
            statement.setString(4, entity.getCategory());
            statement.setInt(5, entity.getStockQuantity());
            statement.setTimestamp(6, Timestamp.valueOf(entity.getCreatedAt()));
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                keys.next();
                entity.setId(keys.getLong(1));
                return entity;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Updates an existing product.
     */
    private Product update(Product entity) {
        String sql = "UPDATE products SET name=?, description=?, price=?, category=?, stock_quantity=? WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescription());
            statement.setBigDecimal(3, entity.getPrice());
            statement.setString(4, entity.getCategory());
            statement.setInt(5, entity.getStockQuantity());
            statement.setLong(6, entity.getId());
            statement.executeUpdate();
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    /**
     * Deletes a product by id.
     */
    public void deleteById(Long id) {
        String sql = "DELETE FROM products WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
