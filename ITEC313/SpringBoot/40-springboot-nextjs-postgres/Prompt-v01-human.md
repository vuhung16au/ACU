# Project Name: Java Programming Blog

The objective of the project is to create a blog website for a Java programming blog using spring boot (backend) and nextjs (frontend) and postgres database.

# Create docker compose file for the following services:
Save docker compose file in docker-compose.yml file.

- spring boot api
- nextjs frontend
- postgres database

Docker network: All services join a single user-defined bridge network app-network.

Mount points: 
- spring boot: <project_root>/backend
- nextjs: <project_root>/frontend
- postgres database: <you decide>

Use docker-compose.yml file to start all the services.

## Docker Configuration

Create docker-compose.yml with:
- **Spring Boot API**: Port 8080, Java 17+ image
- **Next.js Frontend**: Port 3000, Node 18+ image  
- **PostgreSQL**: Port 5432, version 15+
- **Environment variables** for database connection
- **Health checks** for service dependencies
- **Restart policies** for production readiness

# Spring Boot API

OpenAPI:
Add “Expose Swagger UI at /swagger-ui and OpenAPI JSON at /v3/api-docs.”

Implement the API endpoints for all the tables in the Postgres database.

Use ORM.
Handle CORS.

## Spring Boot API Requirements

docker Backend envs: 
- SPRING_DATASOURCE_URL = jdbc:postgresql://localhost:5432/java_programming_blog
- SPRING_DATASOURCE_USERNAME = java_programming_blog
- SPRING_DATASOURCE_PASSWORD = java_programming_blog
- SPRING_JPA_HIBERNATE_DDL_AUTO=validate|update

Spring boot version: 3.5.6

**Core Features:**
- RESTful API endpoints for all CRUD operations
- JPA/Hibernate ORM with PostgreSQL
- CORS configuration for Next.js frontend
- Input validation and error handling
- JWT authentication (optional)

**API Endpoints:**
- `GET /api/posts` - List all posts with pagination
- `GET /api/posts/{id}` - Get single post with comments
- `POST/PUT/DELETE /api/posts` - Manage posts
- `POST/PUT/DELETE /api/comments` - Manage comments
- `GET/POST /api/users` - User management

API surface:
Add GET /api/posts/{id}/comments and GET /api/users/{id}/posts for convenience.
-> you can decide the names of the endpoints.

**Technical Requirements:**
- Spring Data JPA repositories
- Service layer with business logic
- DTO pattern for data transfer
- Exception handling with proper HTTP status codes

# NextJS Frontend

Docker Frontend envs: NEXT_PUBLIC_API_BASE_URL=http://localhost:3000

nodejs version: 22
next.js version: 15

- Shows posts and comments for a given post id.
- Posts and comments are fetched from the Postgres database via backend api.

## Next.js Frontend Requirements

Use App Router in Next.js.

**Pages/Components:**
- Home page listing all blog posts
- Post detail page showing post + comments
- Comment form for adding new comments
- Responsive navigation header
- Loading states and error handling

**Features:**
- Client-side routing with Next.js App Router
- API integration with Spring Boot backend
- Form handling for comments
- Basic SEO optimization
- Mobile-responsive design

**Technical:**
- TypeScript recommended
- API calls using fetch or axios
- State management (useState/useEffect)
- Component-based architecture

## Optional Enhancements
- User authentication/registration
- Rich text editor for posts
- Image upload functionality
- Search functionality
- Categories/tags for posts
- User profiles and avatars

# Postgres Database

docker DB envs: 
- POSTGRES_DB: blogdb
- POSTGRES_USER: bloguser
- POSTGRES_PASSWORD: blogpassword

Postgres database version: 18

Table name: users
Columns: id, name, email, password

Table name: posts
Columns: id, title, content, user_id

Table name: comments
Columns: id, content, user_id, post_id

Create sample data for the database and migrate it to the database.
Save the sample data in a file called `data/sample_data.sql`.

Mount data/sample_data.sql into /docker-entrypoint-initdb.d/ so it auto-seeds.

## PostgreSQL Database Schema

**Enhanced Table Definitions:**

```sql
-- Users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Posts table  
CREATE TABLE posts (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Comments table
CREATE TABLE comments (
    id SERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    post_id INTEGER REFERENCES posts(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_posts_user_id ON posts(user_id);
CREATE INDEX idx_comments_post_id ON comments(post_id);
CREATE INDEX idx_comments_user_id ON comments(user_id);

```


**Data Requirements:**
- At least 3-5 sample users
- 10-15 sample blog posts 
- 20-30 sample comments across different posts

## Design/UI/UX 

- Use tailwind css for styling.

## Design/UI/UX Requirements

**Styling:**
- Tailwind CSS for utility-first styling
- Responsive design (mobile-first approach)
- Dark/light mode support (optional)

**UX Requirements:**
- Clean, readable blog layout
- Easy navigation between posts
- Clear comment sections
- Loading spinners for API calls
- Error messages for failed operations
- Accessible design (WCAG basics)

**Layout Suggestions:**
- Header with site title/navigation
- Post cards with title, excerpt, author
- Post detail with full content + comment thread
- Simple comment form at bottom of posts


# Project Structure

- backend: <project_root>/backend
- frontend: <project_root>/frontend
- postgres database: <project_root>/postgres
- `README.md`: Project description and instructions.
- `docker-compose.yml`: Docker compose file for the project.
- `data/sample_data.sql`: Sample data for the database.
- `LICENSE`: License for the project.
- `.gitignore`: Git ignore file. 
- `docs/`: Documentation for the project.
- `docs/spring-boot-api.md`: Documentation for the spring boot api.
- `docs/nextjs-frontend.md`: Documentation for the nextjs frontend.
- `docs/postgres-database.md`: Documentation for the postgres database. Including how to migrate the sample data to the database.

`README.md` should contain the following sections:
- Project description
- Briefly: How to install the project (docker, spring boot, nextjs, postgres)
- Briefly: How to run the project (docker, spring boot, nextjs, postgres)

# Other Requirements
- No authentication for now.
- Pagination for posts and comments.
- This is a learning project. So, keep it simple and easy to understand.
- CORS: Specify allowed origins: http://localhost:3000 in dev.
- Pagination: Define query params and default values, e.g. ?page=0&size=10&sort=created_at,DESC. Require these on list endpoints. You can decide the names of the query params.
- Handle errors gracefully.