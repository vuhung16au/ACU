# Spring Boot in 9 Days

A hands-on, project-based tutorial series to learn Spring Boot by building a suite of small, focused apps. Each day has clear goals, deliverables, and links to official docs and trusted resources.

## Audience
- Java developers (beginner to intermediate) new to Spring Boot.
- Students who learn best by building runnable projects.

## Prerequisites
- Java 17+ (Spring Boot 3.x requires 17)
- Maven 3.9+ (primary) and optionally Gradle 8+
- IDE (Spring Tool Suite or Visual Studio Code)
- Git, cURL or Postman
- Optional: Docker, Docker Compose
- Optional (for modules): Kafka (local broker), a relational DB (PostgreSQL), Mailtrap or SMTP sandbox

## What is Spring Boot (Intro + Basics)
Spring Boot is an opinionated framework that simplifies Spring app setup by providing:
- Auto-configuration based on classpath and properties
- Embedded servers (Tomcat/Jetty)
- Production-ready features (Actuator, metrics, health)
- Convention-over-configuration with sensible defaults
- Starters to reduce dependency management pain

Basics to grasp:
- Project structure (src/main/java, src/main/resources)
- Main class with `@SpringBootApplication`
- application.properties vs YAML
- Starters (spring-boot-starter-web, data-jpa, validation, security, test, etc.)
- Profiles, configuration properties, logging

References
- Wikipedia: https://en.wikipedia.org/wiki/Spring_Boot
- Spring Boot project: https://spring.io/projects/spring-boot
- Official docs: https://docs.spring.io/spring-boot/docs/current/reference/html/

## What Spring Boot can do (use cases)
- RESTful APIs and backend services
- Web apps with server-side rendering (Thymeleaf, Mustache)
- Data-driven apps with JPA, JDBC, MongoDB, Redis
- Messaging with Kafka/RabbitMQ
- Batch processing and scheduling
- Microservices with Spring Cloud (service discovery, config, resilience)
- GraphQL APIs
- Edge/API gateways and reactive services with WebFlux

Example use cases:
- Company employee REST service (CRUD + validation)
- Order processing with Kafka events
- Catalog service with JPA + MySQL/PostgreSQL
- API Gateway routing to microservices
- Email notifications and scheduled jobs
- i18n-ready web UI for global users

---

## Modules (scope overview)
- Intro
- Spring Boot Features
- Roadmap / Learning Path
- Quick Start
- Spring Tool Suite (STS)
- Spring Boot Core
- Build System (Maven focus, mention Gradle)
- Configuration
- Annotations
- Logging
- Build a Web App
- Build a RESTful Web Service (basic already in `00.HelloSpring`; extend)
- Consuming Web Services
- Spring Boot with Database and Data JPA
- Spring Boot with Kafka
- Spring Boot with Microservices
- Internationalization (i18n)
- Sending Email
- Spring Boot Testing

---

## 7-Day Learning Path

### Day 1 — Intro, Features, Quick Start, Tools
Goals
- Understand Spring Boot value prop and features
- Set up environment and IDE (STS)
- Run a quick-start app

Hands-on
- Review existing `00.HelloSpring`
- Create a fresh quick-start with Spring Initializr (Web + Actuator)
- Explore actuator endpoints, main class, properties

Readings
- spring.io Quickstart: https://spring.io/quickstart
- Spring Boot project page: https://spring.io/projects/spring-boot
- Tutorialspoint Spring Boot (index): https://www.tutorialspoint.com/spring_boot/index.htm
- GfG Roadmap: https://www.geeksforgeeks.org/springboot/best-way-to-master-spring-boot-a-complete-roadmap/
- STS: https://spring.io/tools

Deliverables
- `01-quick-start` app with README (what it runs, endpoints, profiles)

### Day 2 — Core, Configuration, Annotations, Logging
Goals
- Learn core concepts (auto-config, starters, profiles)
- Externalized config: properties, YAML, `@ConfigurationProperties`
- Common annotations and DI
- Logging setup (Logback), log levels, JSON logs (optional)

Hands-on
- `02-core-config`: expose a few properties via REST; switch profiles; bind POJOs with `@ConfigurationProperties`; add custom logs

Readings
- External config: https://docs.spring.io/spring-boot/reference/features/external-config.html
- Logging: https://docs.spring.io/spring-boot/reference/features/logging.html
- Annotations overview (GfG): https://www.geeksforgeeks.org/advance-java/spring-boot/

Deliverables
- `02-core-config` app with config profiles and logging examples

### Day 3 — Build System (Maven + Gradle), Web App + i18n
Goals
- Understand Maven plugin and lifecycle; mention Gradle plugin
- Build a small Thymeleaf web app
- Add i18n (messages_xx.properties, LocaleResolver)

Hands-on
- `03-web-thymeleaf-i18n`: simple pages, forms, i18n toggle, static resources

Readings
- Spring Boot Maven Plugin: https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/htmlsingle/
- Spring Boot Gradle Plugin: https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/
- Serving Web Content: https://spring.io/guides/gs/serving-web-content/
- i18n (Baeldung): https://www.baeldung.com/spring-boot-internationalization

Deliverables
- `03-web-thymeleaf-i18n` app with 2 locales and README

### Day 4 — RESTful Services (extend 00.HelloSpring) + Consuming APIs
Goals
- Deepen REST: validation, error handling, DTOs, versioning, HATEOAS (optional)
- Add OpenAPI/Swagger UI and Actuator
- Consume external REST APIs with RestTemplate/WebClient

Hands-on
- `04-rest-advanced`: enhance `00.HelloSpring` with validation, global exception handler, Swagger UI
- `04-consume-rest`: consume a public API (e.g., JSONPlaceholder)

Readings
- Build REST: https://spring.io/guides/gs/rest-service/
- Consume REST: https://spring.io/guides/gs/consuming-rest/
- Actuator: https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html
- GfG REST intro: https://www.geeksforgeeks.org/springboot/spring-boot-introduction-to-restful-web-services/

Deliverables
- Two runnable apps with docs and example requests

### Day 5 — Data JPA + Databases
Goals
- Use Spring Data JPA with a relational DB
- Entities, repositories, relationships, pagination, transactions
- Profiles for H2 (dev) and MySQL/Postgres (prod)

Hands-on
- `05-data-jpa`: CRUD over entities, schema migration (Flyway optional), projection and query methods

Readings
- JPA access: https://spring.io/guides/gs/accessing-data-jpa
- Relational access: https://spring.io/guides/gs/relational-data-access
- MySQL: https://spring.io/guides/gs/accessing-data-mysql

Deliverables
- `05-data-jpa` app with H2 dev profile and SQL DB profile

### Day 6 — Kafka + Email + Scheduling
Goals
- Produce/consume messages with Spring for Apache Kafka
- Send email via JavaMailSender
- Add `@Scheduled` tasks

Hands-on
- `06-kafka`: producer/consumer, JSON payloads, error handling, retries
- `06-email-scheduling`: send templated emails (e.g., Thymeleaf), scheduled jobs

Readings
- Spring Kafka project: https://spring.io/projects/spring-kafka
- Kafka reference: https://docs.spring.io/spring-kafka/reference/
- Email: https://docs.spring.io/spring-boot/reference/io/email.html

Deliverables
- Two apps: one for Kafka, one for email+scheduling

### Day 7 — Microservices + Testing Strategy
Goals
- Build a simple microservice system (2-3 services)
- Service discovery, centralized config (Spring Cloud), resilience basics
- Testing: unit, slice, integration, Testcontainers (optional)

Hands-on
- `07-microservices`: service-a (API), service-b (data), gateway (optional)
- `07-testing-lab`: JUnit 5, `@SpringBootTest`, `@WebMvcTest`, `@DataJpaTest`; WireMock or Testcontainers optional

Readings
- Spring Cloud: https://spring.io/projects/spring-cloud
- Discovery: https://spring.io/guides/gs/service-registration-and-discovery/
- Spring Boot Testing: https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing
- Why Spring Boot for Microservices (GfG): https://www.geeksforgeeks.org/blogs/why-to-choose-spring-boot-for-microservices-development/

Deliverables
- Multi-module microservices example and a dedicated testing lab

---

### Day 8 — Spring MVC (classic Spring Web MVC)

Goals

- Understand Spring MVC architecture: DispatcherServlet, HandlerMapping, Controller, ViewResolver, Model
- Build server-rendered pages with Thymeleaf (controllers returning view names)
- Form binding and validation with `@ModelAttribute`, `BindingResult`, and Jakarta Validation
- Serve static resources and implement file upload with `MultipartFile`
- Apply MVC cross-cutting: `HandlerInterceptor` and user-friendly error pages with `@ControllerAdvice`

Hands-on

- `08-spring-mvc`: server-side web app
  - Create `HomeController` with a GET mapping that returns a welcome view
  - Build a form page bound to a DTO; validate on submit and render error messages
  - Add a file upload form and endpoint; store files in a temp folder and list uploaded files
  - Register a `HandlerInterceptor` to log request timing
  - Configure custom error pages (e.g., 404, 500) via templates
- Tests: `@WebMvcTest` for controller/form validation

Readings

- [Spring MVC (GeeksforGeeks)](https://www.geeksforgeeks.org/java/spring-mvc/)
- [Spring Framework Reference: Web MVC](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
- [Serving Web Content with Spring MVC (Guide)](https://spring.io/guides/gs/serving-web-content/)

Deliverables

- `08-spring-mvc` app with views, form validation, file upload, interceptor, and tests

---

### Day 9 — Hibernate & ORM Basics

Goals

- Understand ORM and Hibernate as a JPA provider; when and why to use ORM
- Map entities and relationships (`@OneToMany`, `@ManyToOne`, `@ManyToMany`), cascading, and fetch strategies
- Write queries via Spring Data derived methods, JPQL, and pagination/sorting
- Manage transactions and avoid common pitfalls (N+1 selects, LazyInitializationException)
- Optional: database migrations with Flyway

Hands-on

- `09-hibernate-orm`:
  - Define entities, e.g., `Author` (1) — `Book` (N); optionally `Tag` (M–N)
  - Create Spring Data JPA repositories with derived queries and a JPQL example
  - Seed data using `CommandLineRunner`; expose simple REST endpoints to browse entities with pagination
  - Demonstrate lazy vs eager fetching; use `@EntityGraph` or fetch joins to fix N+1
  - Profiles: `dev` with H2; `prod` with PostgreSQL/MySQL

Readings

- [Hibernate Tutorial (GeeksforGeeks)](https://www.geeksforgeeks.org/java/hibernate-tutorial/)
- [Accessing Data with JPA (Guide)](https://spring.io/guides/gs/accessing-data-jpa)
- [Hibernate ORM Documentation](https://hibernate.org/orm/documentation/)
- [Spring Data JPA Reference](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)

Deliverables

- `09-hibernate-orm` app with entity relationships, queries, pagination, and a README

---

## Module Details and Pointers

### Intro
- What/Why Spring Boot, architecture, starters, actuator
- Links
  - Wikipedia: https://en.wikipedia.org/wiki/Spring_Boot
  - Spring Boot: https://spring.io/projects/spring-boot

### Spring Boot Features
- Auto-config, embedded server, actuator, devtools, starters, metrics, observability

### Roadmap / Learning Path
- Follow the 7-Day plan above; reference:
  - GfG Roadmap: https://www.geeksforgeeks.org/springboot/best-way-to-master-spring-boot-a-complete-roadmap/

### Quick Start
- Use Spring Initializr (Web, Actuator)
- Links
  - spring.io Quickstart: https://spring.io/quickstart
  - Guide: https://spring.io/guides/gs/spring-boot

### Spring Tool Suite
- Install STS, import Maven project, run Boot dashboard
- Link: https://spring.io/tools

### Spring Boot Core
- `@SpringBootApplication`, main(), auto-config, beans, DI, lifecycle
- Starters, profiles, actuator

### Build System (Maven focus, mention Gradle)
- Maven plugin: build, repackage, run
- Gradle plugin basics
- Links
  - Maven Plugin: https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/htmlsingle/
  - Gradle Plugin: https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/

### Configuration
- application.properties/yaml, environment, profiles
- `@ConfigurationProperties`, `@Value`, property binding & validation
- Link: https://docs.spring.io/spring-boot/reference/features/external-config.html

### Annotations
- Core: `@SpringBootApplication`, `@Configuration`, `@Bean`, `@Component`/`@Service`/`@Repository`, `@Autowired`
- Web: `@RestController`, `@Controller`, `@RequestMapping`, `@GetMapping`, `@PostMapping`
- Data: `@Entity`, `@Id`, `@GeneratedValue`, `@Transactional`
- Other: `@ConfigurationProperties`, `@EnableScheduling`, `@EnableAsync`, `@Validated`

### Logging
- Logback defaults, log levels per package, JSON logs (optional), external config
- Link: https://docs.spring.io/spring-boot/reference/features/logging.html

### Build a Web App
- Thymeleaf templates, forms, validation, static resources, i18n
- Link: https://spring.io/guides/gs/serving-web-content/

### Build a RESTful Web Service
- Extend `00.HelloSpring` with:
  - Validation (Jakarta Validation), global exception handling (`@ControllerAdvice`)
  - Versioning strategies, HATEOAS (optional), Swagger/OpenAPI
- Links
  - REST guide: https://spring.io/guides/gs/rest-service/
  - GfG REST intro: https://www.geeksforgeeks.org/springboot/spring-boot-introduction-to-restful-web-services/
  - OpenAPI (springdoc): https://springdoc.org/

### Consuming Web Services
- RestTemplate vs WebClient (reactive), timeouts, retries, error handling
- Links
  - Consuming REST: https://spring.io/guides/gs/consuming-rest/
  - WebClient guide (reactive): https://docs.spring.io/spring-framework/reference/web/webflux-webclient.html

### Spring Boot with Database and Data JPA
- Entities, repos, relationships, pagination/sorting, DTO vs entity
- Profiles: H2 for dev, PostgreSQL/MySQL for prod
- Links
  - JPA: https://spring.io/guides/gs/accessing-data-jpa
  - MySQL: https://spring.io/guides/gs/accessing-data-mysql
  - Relational access: https://spring.io/guides/gs/relational-data-access

### Spring Boot with Kafka
- Producers/consumers, topics, serialization, DLQ, retries
- Links
  - Spring Kafka project: https://spring.io/projects/spring-kafka
  - Reference: https://docs.spring.io/spring-kafka/reference/

### Spring Boot with Microservices
- Spring Cloud (Discovery/Eureka, Config, LoadBalancer, Resilience4j)
- API Gateway (Spring Cloud Gateway), centralized config
- Links
  - Spring Cloud: https://spring.io/projects/spring-cloud
  - Discovery: https://spring.io/guides/gs/service-registration-and-discovery/
  - LoadBalancer: https://spring.io/guides/gs/spring-cloud-loadbalancer/

### Internationalization
- Message bundles, LocaleResolver, Thymeleaf integration
- Link: https://www.baeldung.com/spring-boot-internationalization

### Sending Email
- JavaMailSender, templates, mail sandbox (Mailtrap)
- Link: https://docs.spring.io/spring-boot/reference/io/email.html

### Spring Boot Testing
- JUnit 5, Spring Boot Test slices (`@WebMvcTest`, `@DataJpaTest`)
- MockMvc, Mockito; Testcontainers (optional)
- Link: https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing

---

## Project Folder Structure (series layout)

At repo root
- README.md (overview)
- Spring-Boot-in-7-Days.md (this plan)
- 00.HelloSpring/ (provided)
- 01-quick-start/
- 02-core-config/
- 03-web-thymeleaf-i18n/
- 04-rest-advanced/
- 04-consume-rest/
- 05-data-jpa/
- 06-kafka/
- 06-email-scheduling/
- 07-microservices/
- 07-testing-lab/

Each module (example)
- <module>/
  - pom.xml
  - README.md (goals, endpoints, how to run, links)
  - scripts/
    - test_endpoints.sh (if applicable)
  - src/
    - main/
      - java/com/acu/<module>/**.java
      - resources/
        - application.yml
        - application-<profile>.yml
        - templates/ (if web)
        - static/ (if web)
        - messages.properties (i18n if needed)
    - test/
      - java/com/acu/<module>/**Test.java
  - docker/
    - docker-compose.yml (if needed, e.g., DB, Kafka)

Notes
- Prefer H2 for dev; profile-based DB swap for prod (MySQL/Postgres)
- Reuse scripts and common README snippets where practical
- Keep each module runnable independently

---

## Reference Tutorials (curated)

Tutorials
- Tutorialspoint (index): https://www.tutorialspoint.com/spring_boot/index.htm
- Tutorialspoint Quick Start: https://www.tutorialspoint.com/spring_boot/spring_boot_quick_start.htm
- GeeksforGeeks Spring Boot: https://www.geeksforgeeks.org/advance-java/spring-boot/
- GfG REST Intro: https://www.geeksforgeeks.org/springboot/spring-boot-introduction-to-restful-web-services/
- GfG Why Spring Boot for Microservices: https://www.geeksforgeeks.org/blogs/why-to-choose-spring-boot-for-microservices-development/
- GfG Roadmap: https://www.geeksforgeeks.org/springboot/best-way-to-master-spring-boot-a-complete-roadmap/
- GfG Create REST API with Spring Boot: https://www.geeksforgeeks.org/java/how-to-create-a-rest-api-using-java-spring-boot/

Official Guides
- Building an Application with Spring Boot: https://spring.io/guides/gs/spring-boot
- Building a RESTful Web Service: https://spring.io/guides/gs/rest-service/
- Consuming a RESTful Web Service: https://spring.io/guides/gs/consuming-rest/
- Accessing data with JPA: https://spring.io/guides/gs/accessing-data-jpa
- Relational data access: https://spring.io/guides/gs/relational-data-access
- Accessing data with MySQL: https://spring.io/guides/gs/accessing-data-mysql
- Messaging with Redis: https://spring.io/guides/gs/messaging-redis
- Accessing data with Neo4j: https://spring.io/guides/gs/accessing-data-neo4j
- GraphQL server: https://spring.io/guides/gs/graphql-server

Core Docs
- Spring Boot docs: https://docs.spring.io/spring-boot/docs/current/reference/html/
- External config: https://docs.spring.io/spring-boot/reference/features/external-config.html
- Logging: https://docs.spring.io/spring-boot/reference/features/logging.html
- Actuator: https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html
- Maven Plugin: https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/htmlsingle/
- Gradle Plugin: https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/
- Spring Kafka: https://spring.io/projects/spring-kafka
- Spring Cloud: https://spring.io/projects/spring-cloud
- Email: https://docs.spring.io/spring-boot/reference/io/email.html
- STS: https://spring.io/tools

---

## Success Criteria
- Each day produces a runnable subproject with a clear README and at least one verifiable outcome (endpoint, page, consumer).
- Students can run all modules locally with minimal setup (profiles + in-memory defaults).
- Coverage of all requested modules with practical, incremental complexity.
