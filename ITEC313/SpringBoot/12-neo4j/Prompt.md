# Populate neo4j with sample data

The objectives are:
- Show graph relationships between nodes
- Show graph relationships between nodes and properties
- Show graph relationships between nodes and relationships

Help me to create a script to populate neo4j with sample data.

Currently, we have 
- Node: Person
- Keys: age, name
   
Suggest me what Nodes, Keys, Relationships, etc. to create.

First, give me a plan, don't change anything.


# Create `scripts/demo.sh`

to demonstrate the use of the project
- start docker compose
- run `mvn spring-boot:run`
- store data in neo4j
- retrieve data from neo4j
- stop docker compose

update `README.md` to include the new script


# Run `mvn test` without interactions

`mvn test` runs without users'confirmation, interactions. 
Don't ask users to input anything.

# Create a project to demonstrate the use of the following technologies:

- spring boot 
- neo4j 

The main objective is to demonstrate how to using spring boot to connect to neo4j and perform CRUD operations.

The project does ONLY 2 things:
- Store data in neo4j
- Retrieve data from neo4j
Keep it as simple as possible, as this is for learning purposes.

build tool: maven
save `pom.xml` in the root of the project
package ID: `com.acu.neo4j`
@Web https://raw.githubusercontent.com/spring-guides/gs-accessing-data-neo4j/refs/heads/main/complete/pom.xml


## docker 
save to `docker/docker-compose.yml` in the root of the project
- install neo4j (latest) in docker compose 
- neo4j password: `Sydney@9876`

create .gitignore file in the root of the project (java, spring boot, maven, docker)

Create a README.md file in the root of the project to
- describe the project
- how to build, run, and test the project
- describe the technologies used

Fetch the following URLs for more details 

@Web https://spring.io/guides/gs/accessing-data-neo4j

@Web https://raw.githubusercontent.com/spring-guides/gs-accessing-data-neo4j/refs/heads/main/complete/pom.xml

@Web https://raw.githubusercontent.com/spring-guides/gs-accessing-data-neo4j/refs/heads/main/complete/src/main/resources/application.properties

@Web https://raw.githubusercontent.com/spring-guides/gs-accessing-data-neo4j/refs/heads/main/complete/src/main/java/com/example/accessingdataneo4j/PersonRepository.java

@Web https://raw.githubusercontent.com/spring-guides/gs-accessing-data-neo4j/refs/heads/main/complete/src/main/java/com/example/accessingdataneo4j/Person.java

@Web https://raw.githubusercontent.com/spring-guides/gs-accessing-data-neo4j/refs/heads/main/complete/src/main/java/com/example/accessingdataneo4j/AccessingDataNeo4jApplication.java

Add MINIMUM test cases to the project.
- use Spring Boot Test
- Store data in neo4j
- Retrieve data from neo4j
- Make sure that the test cases are working as expected

Make sure that project is working as expected
- mvn clean 
- mvn compile
- mvn test
- mvn spring-boot:run
works without errors








