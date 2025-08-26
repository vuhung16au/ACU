# Spring Boot Batch Processing Demo

This project demonstrates how to create a basic batch-driven solution using Spring Boot and Spring Batch. The application imports data from a CSV spreadsheet, transforms it with custom code, and stores the final results in a database.

## Project Description

This is a learning project that showcases the fundamental concepts of batch processing in Spring Boot. The application:

1. **Reads data** from a CSV file (`sample-data.csv`) containing person records
2. **Transforms the data** by converting names to uppercase
3. **Stores the results** in an HSQL database

The project demonstrates a complete ETL (Extract, Transform, Load) pipeline using Spring Batch.

## Technologies Used

- **Spring Boot 3.5.3**: Main framework for building the application
- **Spring Batch**: Framework for batch processing
- **HSQL Database**: In-memory database for data storage
- **Maven**: Build tool and dependency management
- **JUnit 5**: Testing framework
- **Java 17**: Programming language

## Main Concepts Used

### 1. Batch Processing
Batch processing is a computing technique where a group of transactions is collected over a period of time and then processed together as a single unit. This approach provides:
- Efficient resource utilization
- Data consistency
- Robust error handling
- Scalability for large datasets

### 2. Spring Batch Components

#### Job
A complete batch process consisting of one or more steps. In this project, we have one job called `importUserJob`.

#### Step
A phase of the job that processes data. Each step consists of:
- **Reader**: Reads data from the source (CSV file)
- **Processor**: Transforms the data (converts names to uppercase)
- **Writer**: Writes data to the destination (database)

#### Chunk Processing
Data is processed in chunks (groups of items) rather than one item at a time. This provides:
- Memory efficiency
- Transaction management
- Better performance

### 3. Data Flow
```
CSV File → Reader → Processor → Writer → Database
```

## Project Structure

```
src/
├── main/
│   ├── java/com/acu/batch/
│   │   ├── BatchConfiguration.java          # Batch job configuration
│   │   ├── BatchProcessingApplication.java  # Main application class
│   │   ├── JobCompletionNotificationListener.java # Job completion listener
│   │   ├── Person.java                      # Data model
│   │   └── PersonItemProcessor.java         # Data transformation logic
│   └── resources/
│       ├── sample-data.csv                  # Input CSV data
│       └── schema-all.sql                   # Database schema
└── test/
    ├── java/com/acu/batch/
    │   └── BatchConfigurationTest.java      # Integration tests
    └── resources/
        └── application-test.yml             # Test configuration
```

## How to Build the Project

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Build Commands

1. **Clean the project**:
   ```bash
   mvn clean
   ```

2. **Compile the project**:
   ```bash
   mvn compile
   ```

3. **Run tests**:
   ```bash
   mvn test
   ```

4. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

## How to Run the Project

### Option 1: Using Maven
```bash
mvn spring-boot:run
```

### Option 2: Using JAR file
```bash
# First build the JAR
mvn clean package

# Then run it
java -jar target/batch-0.0.1-SNAPSHOT.jar
```

## Expected Output

When you run the application, you should see output similar to this:

```
Converting (Person[firstName=Jill, lastName=Doe]) into (Person[firstName=JILL, lastName=DOE])
Converting (Person[firstName=Joe, lastName=Doe]) into (Person[firstName=JOE, lastName=DOE])
Converting (Person[firstName=Justin, lastName=Doe]) into (Person[firstName=JUSTIN, lastName=DOE])
Converting (Person[firstName=Jane, lastName=Doe]) into (Person[firstName=JANE, lastName=DOE])
Converting (Person[firstName=John, lastName=Doe]) into (Person[firstName=JOHN, lastName=DOE])
!!! JOB FINISHED! Time to verify the results
Found <Person[firstName=JILL, lastName=DOE]> in the database.
Found <Person[firstName=JOE, lastName=DOE]> in the database.
Found <Person[firstName=JUSTIN, lastName=DOE]> in the database.
Found <Person[firstName=JANE, lastName=DOE]> in the database.
Found <Person[firstName=JOHN, lastName=DOE]> in the database.
```

## Testing

The project includes integration tests that verify the batch job completes successfully:

```bash
mvn test
```

The test ensures that:
- The batch job starts and completes without errors
- The job exit status is `COMPLETED`

## Configuration Details

### Database Configuration
- **Database**: HSQL (in-memory)
- **Schema**: Automatically created from `schema-all.sql`
- **Table**: `people` with columns `person_id`, `first_name`, `last_name`

### Batch Configuration
- **Chunk Size**: 3 items per chunk
- **Job Name**: `importUserJob`
- **Step Name**: `step1`

### Input Data
The CSV file (`sample-data.csv`) contains:
```csv
Jill,Doe
Joe,Doe
Justin,Doe
Jane,Doe
John,Doe
```

## Key Features Demonstrated

1. **File Reading**: Reading from CSV files using `FlatFileItemReader`
2. **Data Transformation**: Custom processing logic in `PersonItemProcessor`
3. **Database Writing**: Writing to database using `JdbcBatchItemWriter`
4. **Job Monitoring**: Using `JobExecutionListener` to monitor job completion
5. **Error Handling**: Spring Batch's built-in error handling and retry mechanisms
6. **Testing**: Integration testing with Spring Batch Test utilities

## Learning Objectives

After completing this project, you should understand:

- How to set up a Spring Batch application
- The role of Readers, Processors, and Writers
- How to configure batch jobs and steps
- How to handle job completion and monitoring
- How to test batch applications
- The benefits of chunk processing

## Next Steps

To extend this project, you could:

1. Add more complex data transformations
2. Implement error handling and retry logic
3. Add multiple steps to the job
4. Implement parallel processing
5. Add job scheduling
6. Use a persistent database instead of in-memory HSQL

## References

- [Spring Batch Documentation](https://docs.spring.io/spring-batch/docs/current/reference/html/)
- [Spring Boot Batch Guide](https://spring.io/guides/gs/batch-processing/)
- [Spring Batch Reference Documentation](https://docs.spring.io/spring-batch/reference/)
