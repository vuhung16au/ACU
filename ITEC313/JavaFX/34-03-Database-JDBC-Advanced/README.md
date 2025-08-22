# Introduction 

This project is a minimal JavaFX + JDBC demo that showcases database connection to PostgreSQL.
- Uses HikariCP for connection pooling.
- Demonstrates simple CRUD around `Product`.
- Runs local PostgreSQL via Docker Compose.

## Key Components: HikariCP and JDBC

### HikariCP (`com.zaxxer.hikari`)
**What it is**: HikariCP is a high-performance JDBC connection pool library that manages database connections efficiently.

**Why we use it**: 
- **Connection pooling**: Instead of creating a new database connection for each query (which is expensive), HikariCP maintains a pool of reusable connections
- **Performance**: Significantly reduces connection overhead and improves application responsiveness
- **Resource management**: Prevents connection leaks and manages connection lifecycle automatically

**How we use it in our project**:
```java
// Configuration in ProductsApp.java
HikariConfig config = new HikariConfig();
config.setDriverClassName("org.postgresql.Driver");
config.setJdbcUrl("jdbc:postgresql://localhost:5432/jdbc_advanced?user=postgres&password=postgres&ssl=false");
config.setLeakDetectionThreshold(2000);
HikariDataSource dataSource = new HikariDataSource(config);
```

### JDBC (`java.sql.*`)
**What it is**: Java Database Connectivity (JDBC) is the standard Java API for connecting to relational databases and executing SQL statements.

**Why we use it**: 
- **Database abstraction**: Provides a uniform interface to work with different database systems
- **SQL execution**: Allows us to execute SQL queries, updates, and stored procedures
- **Result handling**: Manages query results and metadata

**How we use it in our project**:
```java
// In ProductRepository.java - getting connections from the pool
try (Connection connection = dataSource.getConnection();
     PreparedStatement stmt = connection.prepareStatement(sql)) {
    stmt.setString(1, productName);
    ResultSet rs = stmt.executeQuery();
    // Process results...
}
```

**Key JDBC classes we use**:
- `Connection`: Represents a connection to the database
- `PreparedStatement`: Pre-compiled SQL statements for better performance and security
- `ResultSet`: Contains the results of a query
- `SQLException`: Handles database-related errors

## Getting started

### Prerequisites
- Java 21 (JDK)
- Maven 3.8+
- Docker & Docker Compose (for local PostgreSQL)

### Project structure
- JavaFX desktop app with a PostgreSQL backend accessed via JDBC.
- A connection pool is provided by HikariCP for performance.

### Database connection
- Default JDBC URL (matches `ProductsApp`):
  - `jdbc:postgresql://localhost:5432/jdbc_advanced?user=postgres&password=postgres&ssl=false`

## Run the database with Docker Compose

From the project root:

```bash
docker compose up -d
```

Services (from `docker-compose.yaml`):
- `postgres` (PostgreSQL 17, port 5432)
  - user: `postgres`
  - password: `postgres`
  - database: `jdbc_advanced`
- `pgadmin` (pgAdmin4, http://localhost:8888)
  - email: `313@acu.com`
  - password: `password`

Connect pgAdmin to Postgres:
- Host: `postgres`
- Port: `5432`
- Username: `postgres`
- Password: `postgres`

Useful commands:
```bash
docker compose logs -f postgres
docker compose ps
docker compose down
```

## Build and run the app

Run directly with Maven (recommended):
```bash
mvn clean javafx:run
```

Build the project:
```bash
mvn clean package
```

Note: The app requires the database to be reachable at the JDBC URL above. Start Docker Compose first or point the URL to your own PostgreSQL instance.

## Dependencies (from `pom.xml`)

- JavaFX
  - `org.openjfx:javafx-controls:21` — UI controls
  - `org.openjfx:javafx-fxml:21` — FXML loading
- Database
  - `org.postgresql:postgresql:42.7.1` — PostgreSQL JDBC driver
  - `com.zaxxer:HikariCP:5.1.0` — JDBC connection pool (HikariCP)
  

The Java module (`module-info.java`) declares:
- `requires java.sql;` — JDBC API
- `requires javafx.controls;` and `requires javafx.fxml;` — JavaFX APIs
- `requires com.zaxxer.hikari;` — HikariCP types used at startup
  

## Why and how we use `com.zaxxer.hikari` (HikariCP)

### Why
- **Connection pooling**: Reuses PostgreSQL connections instead of opening a new one per query. This lowers latency and avoids exhausting DB resources.
- **Performance & stability**: HikariCP is a fast, lightweight, production‑grade pool with sensible defaults.
- **Simple usage**: Code calls `dataSource.getConnection()`; the pool manages the lifecycle.

### How it's wired in this project
- **Dependency (Maven)**: HikariCP and the PostgreSQL driver are declared.

```xml
<dependency>
  <groupId>com.zaxxer</groupId>
  <artifactId>HikariCP</artifactId>
  <version>5.1.0</version>
</dependency>
<dependency>
  <groupId>org.postgresql</groupId>
  <artifactId>postgresql</artifactId>
  <version>42.7.1</version>
</dependency>
```

- **Java module**: The module opens JavaFX packages and requires Hikari.

```java
open module com.acu.javafx.products {
    requires java.desktop;
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.zaxxer.hikari;
}
```

- **Pool initialization (on app startup)**: A `HikariDataSource` is created and passed to the controller.

```java
// ProductsApp.java
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

private HikariDataSource initDataSource(String driver, String url) {
    HikariConfig config = new HikariConfig();
    config.setDriverClassName(driver);
    config.setJdbcUrl(url);
    config.setLeakDetectionThreshold(2000);
    return new HikariDataSource(config);
}
```

- **Controller wiring**: The controller receives the `HikariDataSource` and constructs the repository.

```java
// OverviewController.java
public void initDataSource(HikariDataSource hikariDataSource) {
    this.productRepository = new ProductRepository(hikariDataSource);
    Iterable<Product> saved = productRepository.findAll();
    // ... populate UI
}
```

- **Repository usage**: All CRUD operations obtain connections from the pool.

```java
// ProductRepository.java
try (Connection connection = dataSource.getConnection();
     PreparedStatement stmt = connection.prepareStatement(sql)) {
    // execute query/update
}
```

### Can I remove HikariCP?
- For small demos, you can replace it with `DriverManager.getConnection(...)` (no pooling). If you do, also remove `requires com.zaxxer.hikari` from `module-info.java` and the Hikari dependency from `pom.xml`.
- For multi‑operation sessions or larger datasets, keep HikariCP for better responsiveness and resource management.

# Notes 

- Data validation is not implemented.
- No error handling.
- No unit tests.


