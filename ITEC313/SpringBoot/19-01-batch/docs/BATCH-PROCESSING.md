# Batch Processing in Spring

## What is Batch Processing?

Batch processing is a computing technique where a group of transactions is collected over a period of time and then processed together as a single unit. Unlike real-time processing, batch processing allows for:

- **Efficient resource utilization**: Processing large volumes of data when system resources are available
- **Data consistency**: Ensuring all related data is processed together
- **Error handling**: Providing robust mechanisms for handling failures and retries
- **Scalability**: Processing large datasets without overwhelming the system

### Common Use Cases for Batch Processing

- **Data migration**: Moving data from one system to another
- **ETL (Extract, Transform, Load)**: Processing and transforming data for analytics
- **Report generation**: Creating periodic reports from large datasets
- **Data cleanup**: Removing or updating outdated records
- **Integration**: Synchronizing data between different systems

## How to Implement Batch Processing in Spring

Spring Batch is a lightweight, comprehensive batch framework designed to enable the development of robust batch applications vital for the daily operations of enterprise systems. Here's how to implement it:

### 1. Project Setup

#### Using Spring Initializr
1. Navigate to [start.spring.io](https://start.spring.io)
2. Choose Maven or Gradle and Java
3. Add dependencies:
   - **Spring Batch**: Core batch processing functionality
   - **HyperSQL Database**: In-memory database for testing (or your preferred database)

#### Manual Dependencies (Maven)
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-batch</artifactId>
</dependency>
<dependency>
    <groupId>org.hsqldb</groupId>
    <artifactId>hsqldb</artifactId>
    <scope>runtime</scope>
</dependency>
```

### 2. Core Components

#### Business Data Model
Create a data model to represent your business entities:

```java
public record Person(String firstName, String lastName) {
    // Spring Boot automatically creates constructor, getters, toString, etc.
}
```

#### Item Processor
Implement transformation logic:

```java
@Component
public class PersonItemProcessor implements ItemProcessor<Person, Person> {
    
    @Override
    public Person process(Person person) throws Exception {
        final String firstName = person.firstName().toUpperCase();
        final String lastName = person.lastName().toUpperCase();
        
        final Person transformedPerson = new Person(firstName, lastName);
        
        log.info("Converting ({}) into ({})", person, transformedPerson);
        
        return transformedPerson;
    }
}
```

### 3. Batch Configuration

#### Reader, Processor, and Writer Configuration

```java
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    
    @Bean
    public FlatFileItemReader<Person> reader() {
        return new FlatFileItemReaderBuilder<Person>()
            .name("personItemReader")
            .resource(new ClassPathResource("sample-data.csv"))
            .delimited()
            .names("firstName", "lastName")
            .fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                setTargetType(Person.class);
            }})
            .build();
    }
    
    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }
    
    @Bean
    public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Person>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
            .dataSource(dataSource)
            .build();
    }
}
```

#### Job and Step Configuration

```java
@Bean
public Job importUserJob(JobRepository jobRepository, 
                        JobCompletionNotificationListener listener, 
                        Step step1) {
    return new JobBuilder("importUserJob", jobRepository)
        .incrementer(new RunIdIncrementer())
        .listener(listener)
        .flow(step1)
        .end()
        .build();
}

@Bean
public Step step1(JobRepository jobRepository,
                  PlatformTransactionManager transactionManager,
                  FlatFileItemReader<Person> reader,
                  PersonItemProcessor processor,
                  JdbcBatchItemWriter<Person> writer) {
    return new StepBuilder("step1", jobRepository)
        .<Person, Person>chunk(3, transactionManager)
        .reader(reader)
        .processor(processor)
        .writer(writer)
        .build();
}
```

### 4. Job Completion Listener

```java
@Component
public class JobCompletionNotificationListener implements JobExecutionListener {
    
    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
    
    private final JdbcTemplate jdbcTemplate;
    
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");
            
            jdbcTemplate.query("SELECT first_name, last_name FROM people",
                (rs, row) -> new Person(
                    rs.getString(1),
                    rs.getString(2)
                )
            ).forEach(person -> log.info("Found <{}> in the database.", person));
        }
    }
}
```

### 5. Application Entry Point

```java
@SpringBootApplication
public class BatchProcessingApplication {
    
    public static void main(String[] args) throws Exception {
        System.exit(SpringApplication.exit(SpringApplication.run(BatchProcessingApplication.class, args)));
    }
}
```

### 6. Data Sources

#### Input Data (CSV)
```csv
Jill,Doe
Joe,Doe
Justin,Doe
Jane,Doe
John,Doe
```

#### Database Schema
```sql
DROP TABLE people IF EXISTS;
CREATE TABLE people (
    person_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL
);
```

## Key Concepts

### 1. Chunk Processing
- **Chunk**: A group of items processed together
- **Chunk size**: Number of items in each chunk (e.g., 3 in the example)
- **Benefits**: Memory efficiency and transaction management

### 2. Step Execution
- **Step**: A phase of the job that processes data
- **Reader**: Reads data from the source
- **Processor**: Transforms the data
- **Writer**: Writes data to the destination

### 3. Job Execution
- **Job**: A complete batch process consisting of one or more steps
- **Job Repository**: Stores metadata about job executions
- **Job Parameters**: Input parameters that can be passed to jobs

### 4. Error Handling
- **Skip Policy**: Define conditions for skipping items
- **Retry Policy**: Configure retry behavior for failed items
- **Rollback**: Automatic rollback of failed chunks

## Best Practices

1. **Chunk Size Optimization**: Balance between memory usage and performance
2. **Error Handling**: Implement proper skip and retry policies
3. **Monitoring**: Use job listeners for monitoring and logging
4. **Testing**: Test with realistic data volumes
5. **Resource Management**: Ensure proper cleanup of resources

## Advanced Features

- **Parallel Processing**: Process multiple chunks simultaneously
- **Partitioning**: Distribute work across multiple threads or nodes
- **Remote Chunking**: Process chunks on remote workers
- **Job Scheduling**: Schedule jobs to run at specific times
- **Job Restart**: Resume failed jobs from the last successful point

## Conclusion

Spring Batch provides a robust, scalable framework for implementing batch processing solutions. It handles the complex aspects of batch processing while allowing developers to focus on business logic. The framework is particularly well-suited for enterprise applications that require reliable, high-performance batch processing capabilities.
