# Spring Boot MongoDB Web Application

A complete web application for managing customers using Spring Boot, MongoDB, and Thymeleaf templates with a modern, responsive user interface.

## Features

- **Customer Management**: Full CRUD operations for customers
- **Modern Web Interface**: Clean, responsive web interface using Thymeleaf with CSS styling
- **Advanced Search Functionality**: Search customers by first name, last name, city, or country with dropdown selection
- **MongoDB Integration**: Persistent data storage with MongoDB
- **Docker Support**: Easy setup with Docker and Docker Compose
- **User-Friendly Navigation**: Intuitive navigation between different pages
- **Form Validation**: Client-side validation for all input fields
- **Responsive Design**: Mobile-friendly interface with modern styling

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Docker and Docker Compose (for MongoDB)

## Quick Start

### 1. Start MongoDB

```bash
# Navigate to the docker directory
cd docker

# Start MongoDB and Mongo Express
docker-compose up -d
```

### 2. Run the Application

```bash
# Navigate back to the project root
cd ..

# Run the Spring Boot application
mvn spring-boot:run
```

### 3. Access the Application

- **Main Application**: http://localhost:8080
- **Mongo Express**: http://localhost:8081 (admin/admin123)

## Web Application Features

### Customer List (`/customers`)
- View all customers in a responsive table format
- Modern styling with alternating row colors
- Direct action links for Edit and Delete operations
- Navigation buttons for Add New Customer and Search
- Empty state handling when no customers exist

### Add Customer (`/customers/add`)
- Clean form interface with proper validation
- Required field indicators
- Responsive input fields with modern styling
- Cancel button to return to customer list

### Edit Customer (`/customers/edit/{id}`)
- Pre-populated form with existing customer data
- Same modern styling as add form
- Form validation for all fields
- Cancel button to return to customer list

### Delete Customer (`/customers/delete/{id}`)
- Confirmation page with customer details display
- Warning message about irreversible action
- Customer information preview before deletion
- Cancel option to abort deletion

### Search Customers (`/customers/search`)
- **Advanced Search Interface**: Dropdown to select search criteria (First Name, Last Name, City, Country)
- **Dynamic Results**: Real-time search results display
- **Results Table**: Same styling as main customer list
- **Empty State**: User-friendly message when no results found
- **Clear Functionality**: Reset search form
- **Navigation**: Easy return to customer list

## API Endpoints

| Method | URL | Description |
|--------|-----|-------------|
| GET | `/` | Redirects to customer list |
| GET | `/customers` | Show customer list with modern UI |
| GET | `/customers/add` | Show add customer form |
| POST | `/customers/add` | Add new customer |
| GET | `/customers/edit/{id}` | Show edit customer form |
| POST | `/customers/edit/{id}` | Update customer |
| GET | `/customers/delete/{id}` | Show delete confirmation |
| POST | `/customers/delete/{id}` | Delete customer |
| GET | `/customers/search` | Show search form with dropdown |
| POST | `/customers/search` | Search customers with results display |

## Database Schema

### Customer Collection
```json
{
  "_id": "ObjectId",
  "firstName": "String (required)",
  "lastName": "String (required)", 
  "city": "String (required)",
  "country": "String (required)"
}
```

## Search Functionality

The application provides comprehensive search capabilities:

- **Search Types**: First Name, Last Name, City, Country
- **Search Methods**: Uses Spring Data MongoDB query methods
- **Case Sensitivity**: MongoDB default case-sensitive search
- **Results Display**: Formatted table with action links
- **User Experience**: Dropdown selection for search criteria

### Repository Search Methods
```java
List<Customer> findByFirstName(String firstName);
List<Customer> findByLastName(String lastName);
List<Customer> findByCity(String city);
List<Customer> findByCountry(String country);
```

## Project Structure

```
src/
├── main/
│   ├── java/com/acu/mongodb/demo/
│   │   ├── AccessingDataMongodbApplication.java  # Main application class
│   │   ├── Customer.java                         # Customer entity
│   │   ├── CustomerRepository.java               # MongoDB repository
│   │   ├── CustomerController.java               # Web controller
│   │   └── HomeController.java                   # Home page controller
│   └── resources/
│       ├── application.properties                # Application configuration
│       └── templates/customers/
│           ├── list.html                         # Customer list page
│           ├── add.html                          # Add customer form
│           ├── edit.html                         # Edit customer form
│           ├── delete.html                       # Delete confirmation
│           └── search.html                       # Search interface
└── test/
    └── java/com/acu/mongodb/demo/
        └── CustomerRepositoryTests.java          # Repository tests
```

## Configuration

### Application Properties
```properties
# MongoDB Configuration
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=customerdb

# Server Configuration
server.port=8080

# Thymeleaf Configuration
spring.thymeleaf.cache=false
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
```

## Development

### Running Tests
```bash
mvn test
```

### Building the Application
```bash
mvn clean package
```

### Running with JAR
```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

## Docker Setup

The project includes Docker configuration for MongoDB and Mongo Express:

- **MongoDB**: Port 27017
- **Mongo Express**: Port 8081 (Web UI for MongoDB)

See `docker/README.md` for detailed Docker setup instructions.

## Technologies Used

- **Spring Boot 3.2.0**: Main framework
- **Spring Data MongoDB**: Database integration with query methods
- **Thymeleaf**: Template engine with modern styling
- **MongoDB**: NoSQL database
- **Maven**: Build tool
- **Docker**: Containerization
- **CSS**: Modern styling with responsive design



