-- Create authors table
CREATE TABLE authors (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE,
    biography VARCHAR(500),
    birth_date DATE,
    created_at DATE NOT NULL,
    updated_at DATE
);

-- Create books table
CREATE TABLE books (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description VARCHAR(1000),
    publication_year INTEGER NOT NULL,
    isbn VARCHAR(50) UNIQUE,
    price DECIMAL(10,2),
    page_count INTEGER,
    author_id BIGINT NOT NULL,
    created_at DATE NOT NULL,
    updated_at DATE,
    FOREIGN KEY (author_id) REFERENCES authors(id)
);

-- Create tags table
CREATE TABLE tags (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(200),
    created_at DATE NOT NULL,
    updated_at DATE
);

-- Create book_tags junction table for many-to-many relationship
CREATE TABLE book_tags (
    book_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (book_id, tag_id),
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX idx_authors_name ON authors(name);
CREATE INDEX idx_authors_email ON authors(email);
CREATE INDEX idx_books_title ON books(title);
CREATE INDEX idx_books_author_id ON books(author_id);
CREATE INDEX idx_books_publication_year ON books(publication_year);
CREATE INDEX idx_books_isbn ON books(isbn);
CREATE INDEX idx_tags_name ON tags(name);
CREATE INDEX idx_book_tags_book_id ON book_tags(book_id);
CREATE INDEX idx_book_tags_tag_id ON book_tags(tag_id);
