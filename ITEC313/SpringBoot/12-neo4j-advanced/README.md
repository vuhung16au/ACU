# Spring Boot Neo4j Demo Project

This project demonstrates how to use Spring Boot to connect to Neo4j and perform CRUD (Create, Read, Update, Delete) operations. It's designed as a learning resource to understand the integration between Spring Boot and Neo4j.

## Project Overview

The project implements an advanced graph database system with multiple entity types and complex relationships. It demonstrates:

### Core Features
- **Multi-Entity Graph Model**: Person, Technology, Company, Project, and Skill entities
- **Complex Relationships**: Various relationship types between entities
- **Rich Data Model**: Properties and attributes for each entity type
- **Graph Queries**: Advanced Cypher queries for relationship traversal
- **RESTful API**: Complete CRUD operations for all entities
- **Data Population**: Automatic sample data creation with realistic relationships
- **JSON Data Loading**: External JSON file support for sample data configuration
- **Repository Pattern**: Custom repository interfaces with advanced query methods
- **Service Layer**: Business logic separation with comprehensive services
- **Profile-based Configuration**: Environment-specific configurations (test vs production)

### Graph Relationships Demonstrated
- **Person → Company**: Works at relationships
- **Person → Technology**: Knowledge and expertise
- **Person → Project**: Project assignments
- **Person → Skill**: Skill assessments
- **Person → Person**: Team relationships, mentoring, reporting
- **Project → Technology**: Technology stack usage
- **Company → Company**: Partnerships and competition
- **Technology → Technology**: Dependencies and similarities

### Entity Properties
- **Person**: name, age, email, location, experienceYears
- **Technology**: name, category, version, popularityScore
- **Company**: name, industry, size, foundedYear
- **Project**: name, description, status, startDate, budget
- **Skill**: name, level, category

## Technologies Used

- **Spring Boot 3.2.0**: Main framework for building the application
- **Spring Data Neo4j**: For Neo4j database integration
- **Neo4j**: Graph database for data storage
- **Maven**: Build tool and dependency management
- **Docker**: Containerization for Neo4j database
- **TestContainers**: For integration testing with containerized Neo4j
- **JUnit 5**: Testing framework

## Project Structure

```
src/
├── main/
│   ├── java/com/acu/neo4j/
│   │   ├── Neo4jApplication.java      # Main Spring Boot application
│   │   ├── Person.java                # Person entity with enhanced properties
│   │   ├── Technology.java            # Technology entity
│   │   ├── Company.java               # Company entity
│   │   ├── Project.java               # Project entity
│   │   ├── Skill.java                 # Skill entity
│   │   ├── PersonRepository.java      # Person repository with custom queries
│   │   ├── TechnologyRepository.java  # Technology repository
│   │   ├── CompanyRepository.java     # Company repository
│   │   ├── ProjectRepository.java     # Project repository
│   │   ├── SkillRepository.java       # Skill repository
│   │   ├── PersonService.java         # Business logic service
│   │   ├── PersonController.java      # REST controller
│   │   └── DataPopulationService.java # Sample data population service
│   └── resources/
│       └── application.properties     # Application configuration
├── test/
│   └── java/com/acu/neo4j/
│       ├── Neo4jApplicationTests.java # Basic context test
│       └── PersonServiceTest.java     # Comprehensive service tests
docker/
└── docker-compose.yml                 # Neo4j container configuration
scripts/
└── demo.sh                           # Demo script for complete workflow
```

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Docker and Docker Compose

## Getting Started

### Option 1: Quick Demo (Recommended)

For a complete demonstration of the project, run the demo script:

```bash
./scripts/demo.sh
```

This script will:
1. Start Neo4j database using Docker Compose
2. Launch the Spring Boot application
3. Store sample data in Neo4j
4. Retrieve and display data from Neo4j
5. Keep the application running for manual testing
6. Clean up all resources when stopped (Ctrl+C)

### Option 2: Manual Setup

#### 1. Start Neo4j Database

First, start the Neo4j database using Docker Compose:

```bash
cd docker
docker-compose up -d
```

This will start Neo4j on:
- HTTP: http://localhost:7474 (Neo4j Browser)
- Bolt: bolt://localhost:7687 (Application connection)

Default credentials:
- Username: `neo4j`
- Password: `Sydney@9876`

#### 2. Build the Project

```bash
mvn clean compile
```

#### 3. Run Tests

```bash
mvn test
```

The tests use TestContainers to automatically spin up a Neo4j container for testing.

#### 4. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

Once the application is running, you can use the following REST endpoints:

### Person Endpoints
```bash
# Create a Person
POST http://localhost:8080/api/people
Content-Type: application/json

{
  "name": "John Doe",
  "age": 30,
  "email": "john.doe@example.com",
  "location": "New York",
  "experienceYears": 5
}

# Get All People
GET http://localhost:8080/api/people

# Get Person by ID
GET http://localhost:8080/api/people/{id}

# Get People by Name
GET http://localhost:8080/api/people/name/{name}

# Get People Older Than Age
GET http://localhost:8080/api/people/older-than/{age}

# Get All People Ordered by Name
GET http://localhost:8080/api/people/ordered

# Delete Person
DELETE http://localhost:8080/api/people/{id}
```

### Repository Query Methods
The project includes custom repository methods for advanced queries:

#### PersonRepository
- `findByName(String name)` - Find people by name
- `findPeopleOlderThan(int minAge)` - Find people older than specified age
- `findAllOrderedByName()` - Get all people ordered by name

#### TechnologyRepository
- `findByName(String name)` - Find technologies by name
- `findByCategory(String category)` - Find technologies by category
- `findPopularTechnologies(Integer minScore)` - Find technologies with popularity score above threshold
- `findTechnologiesByPerson(String personName)` - Find technologies known by a specific person

#### CompanyRepository
- `findByName(String name)` - Find companies by name
- `findByIndustry(String industry)` - Find companies by industry
- `findBySize(String size)` - Find companies by size
- `findCompaniesByPerson(String personName)` - Find companies where a person works
- `findPartnerCompanies(String companyName)` - Find partner companies

#### ProjectRepository
- `findByName(String name)` - Find projects by name
- `findByStatus(String status)` - Find projects by status
- `findProjectsByPerson(String personName)` - Find projects a person works on
- `findTechnologiesByProject(String projectName)` - Find technologies used in a project
- `findProjectsByBudget(Integer minBudget)` - Find projects with budget above threshold

#### SkillRepository
- `findByName(String name)` - Find skills by name
- `findByCategory(String category)` - Find skills by category
- `findByLevel(String level)` - Find skills by level
- `findSkillsByPerson(String personName)` - Find skills possessed by a person
- `findExpertSkills()` - Find all expert-level skills

## Demo Script

The project includes a comprehensive demo script (`scripts/demo.sh`) that demonstrates the complete workflow:

### Features
- **Automated Setup**: Starts Neo4j and Spring Boot application automatically
- **Data Operations**: Stores and retrieves sample data
- **Health Checks**: Ensures services are ready before proceeding
- **Colored Output**: Provides clear, formatted output with status indicators
- **Automatic Cleanup**: Properly shuts down all services when stopped

### What the Demo Shows
1. **Multi-Entity Data Storage**: Creates sample data for all entity types:
   - **5 People** with enhanced properties (name, age, email, location, experience)
   - **8 Technologies** (Java, Spring Boot, Neo4j, Docker, Kubernetes, Python, React, PostgreSQL)
   - **3 Companies** (TechCorp, StartupXYZ, GlobalSoft)
   - **3 Projects** (E-commerce Platform, Mobile Banking App, Data Analytics Dashboard)
   - **6 Skills** (Problem Solving, Team Leadership, Agile Methodology, System Design, Code Review, Testing)

2. **Complex Relationships**: Establishes realistic relationships between entities:
   - People working at companies
   - People knowing technologies
   - People working on projects
   - People having skills
   - Team relationships and mentoring
   - Project technology stacks
   - Company partnerships

3. **Data Retrieval**: Demonstrates various query operations:
   - Get all entities of each type
   - Find technologies by person
   - Find projects by person
   - Find companies by person
   - Find skills by person
   - Find popular technologies
   - Find expert skills

4. **Automatic Data Population**: The `DataPopulationService` automatically:
   - Clears existing data on startup
   - Creates all sample entities
   - Establishes relationships between entities
   - Provides console output showing the creation process

### Running the Demo
```bash
# Make sure the script is executable
chmod +x scripts/demo.sh

# Run the demo
./scripts/demo.sh
```

### Demo Output
The script provides real-time feedback with colored output:
- 🔵 **Blue**: Information messages
- 🟢 **Green**: Success messages
- 🟡 **Yellow**: Warning messages
- 🔴 **Red**: Error messages

### Stopping the Demo
Press `Ctrl+C` to stop the demo. The script will automatically:
- Stop the Spring Boot application
- Shut down Docker containers
- Clean up all resources

## Testing the Application

### Using curl

1. Create a person with enhanced properties:
```bash
curl -X POST http://localhost:8080/api/people \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Alice Johnson",
    "age": 25,
    "email": "alice.johnson@example.com",
    "location": "San Francisco",
    "experienceYears": 3
  }'
```

2. Get all people:
```bash
curl http://localhost:8080/api/people
```

3. Get people older than 20:
```bash
curl http://localhost:8080/api/people/older-than/20
```

4. Get people by name:
```bash
curl http://localhost:8080/api/people/name/Alice
```

5. Get people ordered by name:
```bash
curl http://localhost:8080/api/people/ordered
```

### Using Neo4j Browser

You can also access the Neo4j Browser at http://localhost:7474 to run Cypher queries directly:

#### Basic Queries
```cypher
// View all Person nodes
MATCH (p:Person) RETURN p

// Find people older than 25
MATCH (p:Person) WHERE p.age > 25 RETURN p

// Count total people
MATCH (p:Person) RETURN count(p)
```

#### Advanced Graph Queries
```cypher
// Find all people who know Java and work on projects using Spring Boot
MATCH (p:Person)-[:KNOWS]->(t1:Technology {name: 'Java'})
MATCH (p)-[:WORKS_ON]->(proj:Project)-[:USES_TECHNOLOGY]->(t2:Technology {name: 'Spring Boot'})
RETURN p, t1, proj, t2

// Find all team members working on the same project
MATCH (p1:Person)-[:WORKS_ON]->(proj:Project)<-[:WORKS_ON]-(p2:Person)
WHERE p1 <> p2
RETURN p1.name, p2.name, proj.name

// Find all technologies used in projects with budget > 300
MATCH (proj:Project)-[:USES_TECHNOLOGY]->(tech:Technology)
WHERE proj.budget > 300
RETURN proj.name, collect(tech.name) as technologies

// Find all people with expert skills
MATCH (p:Person)-[:HAS_SKILL]->(s:Skill {level: 'Expert'})
RETURN p.name, collect(s.name) as expert_skills

// Find company partnerships
MATCH (c1:Company)-[:PARTNERS_WITH]->(c2:Company)
RETURN c1.name, c2.name
```

## Data Loading

### JSON Sample Data Loading

The application supports loading sample data from JSON files for easy configuration and customization:

#### Quick Setup
1. **JSON File Location**: `src/main/resources/data/sample.json`
2. **Automatic Loading**: Data is loaded automatically when the application starts
3. **Fallback Support**: Falls back to hardcoded data if JSON loading fails

#### Features
- ✅ **External Configuration**: Sample data is externalized in JSON format
- ✅ **Easy Modification**: Simply edit the JSON file to change sample data
- ✅ **Error Handling**: Graceful fallback to hardcoded data if JSON fails
- ✅ **Version Control**: JSON file can be version controlled separately
- ✅ **No Additional Dependencies**: Uses existing Spring Boot Jackson support

#### Usage
```bash
# Edit sample data
vim src/main/resources/data/sample.json

# Restart application to load new data
mvn spring-boot:run
```

For detailed information about JSON data loading, see [README-JSON-Loading.md](README-JSON-Loading.md).

### Relationship Creation

The application includes advanced relationship creation capabilities to connect nodes in the graph database. This solves the common issue of having nodes but no relationships in Neo4j.

#### Problem
Your Neo4j database currently has 79 nodes but 0 relationships. This happens because entity classes were only creating nodes without defining relationships between them.

#### Solution
The application now includes relationship definitions and services to create relationships between existing nodes.

#### What's Been Added

**Updated Entity Classes:**
- **Person.java**: Added relationship definitions for:
  - `WORKS_FOR` → Company (with position, startDate)
  - `WORKS_ON` → Project (with role, startDate)
  - `HAS_SKILL` → Skill (with proficiencyLevel, yearsOfExperience)
  - `KNOWS_TECHNOLOGY` → Technology (with proficiencyLevel, yearsOfExperience)

- **Company.java**: Added relationship definitions for:
  - `HAS_PROJECT` → Project (with projectType, startDate)
  - `USES_TECHNOLOGY` → Technology (with usageType, adoptionDate)

**New Services and Controllers:**
- **RelationshipService.java**: Service to create relationships between nodes
- **RelationshipController.java**: REST endpoints to manage relationships

#### Available Relationship Types
```
Person -> Company: WORKS_FOR
Person -> Project: WORKS_ON  
Person -> Skill: HAS_SKILL
Person -> Technology: KNOWS_TECHNOLOGY
Company -> Project: HAS_PROJECT
Company -> Technology: USES_TECHNOLOGY
```

#### How to Add Relationships

**Method 1: Using REST API (Recommended)**

1. **Start your application**:
   ```bash
   ./mvnw spring-boot:run
   ```

2. **Create relationships**:
   ```bash
   curl -X POST http://localhost:8080/api/relationships/create
   ```

3. **Check relationship info**:
   ```bash
   curl http://localhost:8080/api/relationships/info
   ```

4. **Get statistics**:
   ```bash
   curl http://localhost:8080/api/relationships/stats
   ```

**Method 2: Using the Script**

1. **Make sure your application is running**
2. **Run the script**:
   ```bash
   ./scripts/create-relationships.sh
   ```

**Method 3: Using Neo4j Browser**

After creating relationships via the API, you can view them in Neo4j Browser:

1. Open http://localhost:7474
2. Run these Cypher queries:

```cypher
-- View all relationships
MATCH (n)-[r]->(m) RETURN n, r, m LIMIT 25

-- View people working for companies
MATCH (p:Person)-[r:WORKS_FOR]->(c:Company) RETURN p, r, c

-- View people with skills
MATCH (p:Person)-[r:HAS_SKILL]->(s:Skill) RETURN p, r, s

-- View companies using technologies
MATCH (c:Company)-[r:USES_TECHNOLOGY]->(t:Technology) RETURN c, r, t

-- View people working on projects
MATCH (p:Person)-[r:WORKS_ON]->(pr:Project) RETURN p, r, pr

-- View people knowing technologies
MATCH (p:Person)-[r:KNOWS_TECHNOLOGY]->(t:Technology) RETURN p, r, t

-- View companies with projects
MATCH (c:Company)-[r:HAS_PROJECT]->(p:Project) RETURN c, r, p
```

#### Relationship Properties

Each relationship includes relevant properties:

- **WORKS_FOR**: position, startDate
- **WORKS_ON**: role, startDate
- **HAS_SKILL**: proficiencyLevel, yearsOfExperience
- **KNOWS_TECHNOLOGY**: proficiencyLevel, yearsOfExperience
- **HAS_PROJECT**: projectType, startDate
- **USES_TECHNOLOGY**: usageType, adoptionDate

#### Expected Results

After running the relationship creation:

1. **Neo4j Browser** should show relationships in the left sidebar (no longer "Relationships (0)")
2. **Graph view** will display connected nodes with relationship lines
3. **Console output** will show statistics about created relationships

#### Features
- ✅ **Automatic Relationship Creation**: Creates realistic relationships between existing nodes
- ✅ **Rich Properties**: Each relationship includes relevant metadata
- ✅ **Random Distribution**: Creates varied relationship patterns
- ✅ **Error Handling**: Graceful handling of missing nodes
- ✅ **Statistics**: Provides detailed relationship statistics

#### Troubleshooting

If you encounter issues:

1. **Make sure nodes exist first**: The relationship creation requires existing nodes
2. **Check application logs**: Look for any error messages
3. **Verify Neo4j connection**: Ensure Neo4j is running and accessible
4. **Clear database if needed**: You can clear and recreate the database if relationships get corrupted

#### Next Steps

Once relationships are created, you can:

1. **Explore the graph** in Neo4j Browser
2. **Write complex queries** to analyze relationships
3. **Add more relationship types** as needed
4. **Create visualization dashboards** using the relationship data

## Development

### Running Tests

The project includes comprehensive tests that:
- Test CRUD operations
- Use TestContainers for isolated testing
- Verify data persistence and retrieval
- Test custom queries
- Use profile-based configuration to avoid conflicts

```bash
mvn test
```

### Code Quality

The project follows Spring Boot best practices:
- **Layered Architecture**: Controller → Service → Repository pattern
- **Dependency Injection**: Proper use of Spring's DI container
- **Repository Pattern**: Custom repository interfaces with advanced query methods
- **Service Layer**: Business logic separation with comprehensive services
- **Profile-based Configuration**: Environment-specific configurations (test vs production)
- **Comprehensive Test Coverage**: Unit and integration tests
- **Clean Code Structure**: Well-organized package structure and naming conventions

### Key Components

#### DataPopulationService
- Implements `CommandLineRunner` for automatic data population
- Uses `@Profile("!test")` to avoid running during tests
- Creates realistic sample data with proper relationships
- Provides console output for monitoring the population process

#### Repository Interfaces
- Extend `Neo4jRepository` for basic CRUD operations
- Include custom query methods using `@Query` annotations
- Support parameterized queries with `@Param` annotations
- Provide advanced graph traversal capabilities

#### Entity Classes
- Use `@Node` annotations for Neo4j node mapping
- Include `@Id` and `@GeneratedValue` for primary keys
- Support all Neo4j data types and relationships
- Follow JPA-like patterns for consistency

## Troubleshooting

### Common Issues

1. **Neo4j connection failed**: Ensure Docker is running and Neo4j container is started
2. **Port conflicts**: Check if ports 7474, 7687, or 8080 are already in use
3. **Test failures**: Ensure Docker has enough resources allocated
4. **DataPopulationService not running**: Check that the application is not running in test profile
5. **Repository query errors**: Verify that custom query syntax is correct and parameters are properly bound

### Logs

Check application logs for detailed information:
```bash
mvn spring-boot:run
```

### Profile Configuration

The application uses Spring profiles to control behavior:
- **Default profile**: Runs DataPopulationService and connects to Docker Neo4j
- **Test profile**: Skips DataPopulationService and uses TestContainers for isolated testing

To run with a specific profile:
```bash
# Run with default profile (includes data population)
mvn spring-boot:run

# Run with test profile (skips data population)
mvn spring-boot:run -Dspring.profiles.active=test
```

## Cleanup

### If you used the Demo Script
The demo script automatically cleans up when you press `Ctrl+C`. If you need to manually clean up:

```bash
# Stop Spring Boot application (if running)
pkill -f "spring-boot:run"

# Stop Docker containers
cd docker
docker-compose down -v
cd ..
```

### If you used Manual Setup
To stop and remove the Neo4j container:
```bash
cd docker
docker-compose down -v
```

## Sample Data Overview

The application automatically creates the following sample data:

### People
- **Alice Johnson** (28, Senior Developer, San Francisco, 5 years experience)
- **Bob Smith** (35, Team Lead, New York, 8 years experience)
- **Carol Davis** (42, Project Manager, Boston, 12 years experience)
- **David Wilson** (25, Junior Developer, Austin, 2 years experience)
- **Eve Brown** (30, DevOps Engineer, Seattle, 6 years experience)

### Technologies
- **Programming Languages**: Java (v17), Python (v3.11)
- **Frameworks**: Spring Boot (v3.2), React (v18.0)
- **Databases**: Neo4j (v5.0), PostgreSQL (v15.0)
- **Tools**: Docker (v24.0), Kubernetes (v1.28)

### Companies
- **TechCorp**: Enterprise software company (founded 1995)
- **StartupXYZ**: FinTech startup (founded 2020)
- **GlobalSoft**: Consulting SME (founded 2005)

### Projects
- **E-commerce Platform**: Active project with $500k budget
- **Mobile Banking App**: Active project with $300k budget
- **Data Analytics Dashboard**: Completed project with $200k budget

### Skills
- **Technical Skills**: Problem Solving, System Design, Code Review, Testing
- **Soft Skills**: Team Leadership
- **Process Skills**: Agile Methodology

This sample data provides a realistic foundation for exploring Neo4j's graph database capabilities and demonstrates complex relationship patterns commonly found in software development organizations.

# Screenshots

