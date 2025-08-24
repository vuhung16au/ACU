# Database Setup

This guide covers setting up the PostgreSQL database with Docker and understanding the sample data.

## Prerequisites

- Docker and Docker Compose installed on your system

## Quick Setup

1. **Start PostgreSQL and pgAdmin**:
   ```bash
   cd docker
   docker-compose up -d
   ```

2. **Verify database is running**:
   - PostgreSQL: `localhost:5432`
   - pgAdmin: `http://localhost:8080` (313@acu.com / password)

3. **Database credentials**:
   - Database: `graphql_db`
   - Username: `postgres`
   - Password: `postgres`

## Sample Data

The database will be automatically populated with:

- **1002 authors** with realistic names from various backgrounds
- **2002 books** with diverse titles, genres, and page counts (150-800 pages)
- **15 genres** with descriptions covering various literary categories
- **Sample reviews** to demonstrate the review system functionality

### Sample Genres

The system includes 15 predefined genres:
- Fiction, Non-Fiction, Science Fiction, Fantasy, Mystery
- Thriller, Romance, Adventure, Historical Fiction, Biography
- Children, Horror, Poetry, Drama, Comedy

### Available Books

The database contains **2002 books** written by **1002 authors**, including:

1. **"The Lucky Country"** by Donald Horne (1964)
   - A book about Australia that has become a nickname for the country
   - ID: `book-1`

2. **"The Magic Pudding: Being The Adventures of Bunyip Bluegum and his friends Bill Barnacle and Sam Sawnoff"** by Norman Lindsay (1918)
   - A classic Australian children's book
   - ID: `book-2`

3. **Plus 2000 additional books** with realistic titles such as:
   - "The Secret of Paris" by Mia Taylor
   - "The Dark Castle in Prague" by various authors
   - "Helen and the Bridge" by various authors
   - "Big Ocean" by various authors
   - And many more with diverse themes, locations, and characters

## Database Schema

### Core Tables

```sql
-- Authors table
CREATE TABLE authors (
    id VARCHAR(50) PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL
);

-- Books table
CREATE TABLE books (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    page_count INTEGER,
    genre VARCHAR(100),
    author_id VARCHAR(50) NOT NULL,
    FOREIGN KEY (author_id) REFERENCES authors(id)
);

-- Users table
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);
```

### Many-to-Many Relationship Tables

```sql
-- Genres table
CREATE TABLE genres (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT
);

-- Junction table for many-to-many relationship
CREATE TABLE book_genres (
    book_id VARCHAR(50) NOT NULL,
    genre_id VARCHAR(50) NOT NULL,
    PRIMARY KEY (book_id, genre_id),
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE
);
```

### One-to-Many Relationship Tables

```sql
-- Reviews table for one-to-many relationship
CREATE TABLE reviews (
    id VARCHAR(50) PRIMARY KEY,
    book_id VARCHAR(50) NOT NULL,
    user_id BIGINT NOT NULL,
    rating INTEGER CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
```

## Docker Setup

The `docker/` directory contains:

- `docker-compose.yml` - Docker setup for PostgreSQL and pgAdmin
- `init.sql` - Database initialization script
- `add_cursor_column.sql` - Migration script for genres and book_genres
- `add_reviews_table.sql` - Migration script for reviews table
- `README.md` - Docker setup instructions

## Data Generation

Sample data is generated using templates that create believable book titles and author names. Books are automatically linked to genres based on their existing genre field.

For more information about the data generation process, see the `script/generate_sample_data.py` script.
```

