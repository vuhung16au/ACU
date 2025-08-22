package com.acu.car.management.persistence.repository;

import com.acu.car.management.persistence.model.Car;
import com.acu.car.management.persistence.model.Component;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Repository for Car entity providing database operations including component management
 */
public class CarRepository implements Repository<Car, Long> {
    // Logger for debugging and monitoring
    private static final Logger LOG = LoggerFactory.getLogger(CarRepository.class);
    // Database connection pool
    private final HikariDataSource dataSource;

    /**
     * Constructor that initializes the repository and checks/creates the required tables
     */
    public CarRepository(HikariDataSource dataSource) {
        this.dataSource = dataSource;
        if (!isTableFound("cars") || !isTableFound("components")) {
            initTable();
        }
    }

    /**
     * Checks if a table exists in the database
     */
    private boolean isTableFound(String tableName) {
        LOG.info("Checking table {}", tableName);
        String sql = String.format("SELECT * FROM %s LIMIT 1", tableName);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeQuery();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Creates the cars and components tables with proper schema and foreign key relationships
     */
    private void initTable() {
        LOG.info("Initializing table");
        String sql = """
                drop table if exists cars, components;
                create table cars(
                    id serial,
                    make varchar(50) default null,
                    model varchar(50) default null,
                    year integer default null,
                    color varchar(50) default null,
                    mileage double precision default null,
                    category varchar(50) default null,
                    primary key (id));
                create table components(
                    id serial,
                    carid bigint default null,
                    componentcode varchar(50) default null,
                    description varchar(50) default null,
                    cost double precision default null,
                    primary key (id),
                    foreign key (carid) references cars(id));
                """;
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Find a car by ID, including all its components
     */
    @Override
    public Optional<Car> findById(Long Id) {
        Optional<Car> optionalCar = findCarById(Id);
        if (optionalCar.isPresent()) {
            Set<Component> components = findComponentsByCar(optionalCar.get());
            for (Component component : components) {
                optionalCar.get().addComponent(component);
            }
        }
        return optionalCar;
    }

    /**
     * Find all cars, including all their components
     */
    @Override
    public Iterable<Car> findAll() {
        Iterable<Car> cars = findCarAll();
        for (Car car : cars) {
            Set<Component> components = findComponentsByCar(car);
            for (Component component : components) {
                car.addComponent(component);
            }
        }
        return cars;
    }

    /**
     * Save a car and its components (insert if new, update if existing)
     */
    @Override
    public Car save(Car entity) {
        if (Objects.isNull(entity.getId())) {
            Car saved = insertCar(entity);
            insertComponentsByCar(saved);
            return saved;
        }

        Optional<Car> car = findById(entity.getId());
        if (car.isEmpty()) {
            Car saved = insertCar(entity);
            insertComponentsByCar(saved);
            return saved;
        } else {
            Car saved = updateCar(entity);
            deleteComponentsByCar(saved);
            insertComponentsByCar(saved);
            return saved;
        }
    }

    /**
     * Delete a car and all its components by ID
     */
    @Override
    public void deleteById(Long Id) {
        Optional<Car> optionalCar = findCarById(Id);
        if (optionalCar.isPresent()) {
            deleteComponentsByCar(optionalCar.get());
            deleteCarById(optionalCar.get().getId());
        }
    }

    /**
     * Delete all cars and components from the database
     */
    @Override
    public void deleteAll() {
        deleteComponentsAll();
        deleteCarAll();
    }

    /**
     * Insert a new car into the database
     */
    private Car insertCar(Car entity) {
        LOG.info("Executing insertCar()");
        String sql = "INSERT INTO cars (make, model, year, color, mileage, category) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getMake());
            statement.setString(2, entity.getModel());
            statement.setInt(3, entity.getYear());
            statement.setString(4, entity.getColor());
            statement.setDouble(5, entity.getMileage());
            statement.setString(6, entity.getCategory());
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
     * Update an existing car in the database
     */
    private Car updateCar(Car entity) {
        LOG.info("Executing updateCar()");
        String sql = "UPDATE cars SET make=?, model=?, year=?, color=?, mileage=?, category=? WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getMake());
            statement.setString(2, entity.getModel());
            statement.setInt(3, entity.getYear());
            statement.setString(4, entity.getColor());
            statement.setDouble(5, entity.getMileage());
            statement.setString(6, entity.getCategory());
            statement.setLong(7, entity.getId());
            statement.executeUpdate();
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Delete a car by ID
     */
    private void deleteCarById(Long Id) {
        LOG.info("Executing deleteCarById()");
        String sql = "DELETE FROM cars WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, Id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Delete all cars from the database
     */
    private void deleteCarAll() {
        LOG.info("Executing deleteCarAll()");
        String sql = "DELETE FROM cars";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Find a car by ID without loading components
     */
    private Optional<Car> findCarById(Long Id) {
        LOG.info("Executing findCarById()");
        String sql = "SELECT * FROM cars WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, Id);
            ResultSet rs = statement.executeQuery();

            if (!rs.next()) {
                return Optional.empty();
            }

            return Optional.of(new Car(rs.getLong("id"), rs.getString("make"), rs.getString("model"), rs.getInt("year"), rs.getString("color"), rs.getDouble("mileage"), rs.getString("category")));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Find all cars without loading components
     */
    private Set<Car> findCarAll() {
        LOG.info("Executing findCarAll()");
        String sql = "SELECT * FROM cars";
        Set<Car> carSet = new HashSet<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                carSet.add(new Car(rs.getLong("id"), rs.getString("make"), rs.getString("model"), rs.getInt("year"), rs.getString("color"), rs.getDouble("mileage"), rs.getString("category")));
            }
            return carSet;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Find all components for a specific car
     */
    private Set<Component> findComponentsByCar(Car entity) {
        LOG.info("Executing findComponentsByCar()");
        String sql = "SELECT * FROM components WHERE carid=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, entity.getId());
            ResultSet rs = statement.executeQuery();

            Set<Component> components = new HashSet<>();
            while (rs.next()) {
                components.add(new Component(
                        rs.getLong("id"),
                        null,
                        rs.getString("componentcode"),
                        rs.getString("description"),
                        rs.getDouble("cost")
                ));
            }
            return components;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Insert all components for a car
     */
    private void insertComponentsByCar(Car entity) {
        LOG.info("Executing insertComponentsByCar()");
        String sql = "INSERT INTO components (carid, componentcode, description, cost) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (Component component : entity.getComponents()) {
                statement.setLong(1, entity.getId());
                statement.setString(2, component.getComponentCode());
                statement.setString(3, component.getDescription());
                statement.setDouble(4, component.getCost());
                statement.executeUpdate();
                try (ResultSet keys = statement.getGeneratedKeys()) {
                    keys.next();
                    component.setId(keys.getLong(1));
                }
            }
        } catch(SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Delete all components for a specific car
     */
    private void deleteComponentsByCar(Car entity) {
        LOG.info("Executing deleteComponentsByCar()");
        String sql = "DELETE FROM components WHERE carid=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Delete all components from the database
     */
    private void deleteComponentsAll() {
        LOG.info("Executing deleteComponentsAll()");
        String sql = "DELETE FROM components";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
