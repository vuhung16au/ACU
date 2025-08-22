package com.acu.car.management.persistence.repository;

import com.acu.car.management.persistence.model.User;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Repository for User entity providing database operations
 */
public class UserRepository implements Repository<User, Long> {
    // Database connection pool
    private final HikariDataSource dataSource;

    /**
     * Constructor that initializes the repository and checks/creates the users table
     */
    public UserRepository(HikariDataSource dataSource) {
        this.dataSource = dataSource;
        checkTable();
    }

    /**
     * Checks if the users table exists, creates it if not
     */
    private void checkTable() {
        String sql = "SELECT * FROM users LIMIT 1";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
        } catch (SQLException e) {
            // Must be disabled in production!
            initTable();
        }
    }

    /**
     * Creates the users table with required schema
     */
    private void initTable() {
        String sql = "DROP TABLE IF EXISTS users;" +
                "CREATE TABLE users " +
                "(id SERIAL, " +
                "username VARCHAR(20) DEFAULT NULL, " +
                "password VARCHAR(20) DEFAULT NULL, " +
                "PRIMARY KEY (id))";
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Find a user by their ID
     */
    @Override
    public Optional<User> findById(Long Id) {
        String sql = "SELECT * FROM users WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, Id);
            ResultSet rs = statement.executeQuery();

            if (!rs.next()) {
                return Optional.empty();
            }

            return Optional.of(new User(rs.getLong("id"), rs.getString("username"), rs.getString("password")));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Find all users in the database
     */
    @Override
    public Iterable<User> findAll() {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getLong("id"), rs.getString("username"), rs.getString("password")));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Save a user (insert if new, update if existing)
     */
    @Override
    public User save(User entity) {
        if (Objects.isNull(entity.getId())) {
            return insert(entity);
        }

        Optional<User> user = findById(entity.getId());
        if (user.isEmpty()) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }

    /**
     * Insert a new user into the database
     */
    private User insert(User entity) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getUsername());
            statement.setString(2, entity.getPassword());
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
     * Update an existing user in the database
     */
    private User update(User entity) {
        String sql = "UPDATE users SET username=?, password=? WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getUsername());
            statement.setString(2, entity.getPassword());
            statement.setLong(3, entity.getId());
            statement.executeUpdate();
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Delete a user by their ID
     */
    @Override
    public void deleteById(Long Id) {
        String sql = "DELETE FROM users WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, Id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Delete all users from the database
     */
    @Override
    public void deleteAll() {
        String sql = "DELETE FROM users";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
