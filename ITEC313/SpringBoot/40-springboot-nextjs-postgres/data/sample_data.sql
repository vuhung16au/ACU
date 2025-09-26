-- Schema and seed data for Java Programming Blog
-- Auto-executed by Postgres via docker-entrypoint-initdb.d

-- Drop tables if they exist (for idempotent init in fresh volumes)
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS users;

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

-- Sample Users (5)
INSERT INTO users (name, email, password) VALUES
('Alice Johnson', 'alice@example.com', 'password1'),
('Bob Smith', 'bob@example.com', 'password2'),
('Carol Lee', 'carol@example.com', 'password3'),
('David Kim', 'david@example.com', 'password4'),
('Eva Brown', 'eva@example.com', 'password5');

-- Sample Posts (12)
INSERT INTO posts (title, content, user_id) VALUES
('Getting Started with Java', 'An introduction to Java programming language and tools.', 1),
('Understanding Spring Boot', 'Spring Boot simplifies Java application development.', 2),
('JPA and Hibernate Basics', 'Learn ORM with JPA and Hibernate.', 3),
('RESTful APIs with Spring', 'Designing and building REST APIs using Spring.', 1),
('Testing in Spring Boot', 'Unit and integration testing techniques.', 4),
('Next.js for Java Devs', 'Using Next.js to build modern frontends.', 5),
('PostgreSQL Tips', 'Indexes, constraints, and performance basics.', 2),
('Pagination Patterns', 'How to paginate API results effectively.', 3),
('Error Handling in APIs', 'Consistent error responses and exception mappers.', 4),
('Dockerizing Java Apps', 'Containerizing Spring Boot with Docker.', 1),
('CI/CD Basics', 'Automating builds and deployments.', 5),
('Security Essentials', 'Overview of securing your Java applications.', 2);

-- Sample Comments (28)
INSERT INTO comments (content, user_id, post_id) VALUES
('Great intro, thanks!', 2, 1),
('Very helpful overview.', 3, 1),
('Can you cover profiles?', 1, 2),
('More examples please.', 4, 2),
('Clear explanation.', 5, 3),
('What about ManyToMany?', 2, 3),
('Love the REST best practices.', 3, 4),
('Include OpenAPI examples?', 5, 4),
('How to mock DB?', 1, 5),
('Good pointers on dev tools.', 2, 5),
('Nice comparison with React.', 1, 6),
('SSR vs SSG differences?', 3, 6),
('Indexing helped a lot.', 4, 7),
('Constraints saved me!', 5, 7),
('Cursor-based vs offset?', 2, 8),
('Thanks for page/size tips.', 1, 8),
('How to map errors to 4xx?', 5, 9),
('Add RFC references?', 3, 9),
('Jib vs Dockerfile?', 4, 10),
('Great multi-stage build idea.', 2, 10),
('What about GitHub Actions?', 1, 11),
('Show sample workflow file?', 3, 11),
('JWT vs Session?', 5, 12),
('OWASP cheatsheets link?', 4, 12),
('Any book recommendations?', 2, 1),
('When to choose Spring?', 3, 2),
('Schema migration tools?', 1, 7),
('How to write unit tests?', 5, 5);


