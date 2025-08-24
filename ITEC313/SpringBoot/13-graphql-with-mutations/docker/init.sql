-- Create authors table
CREATE TABLE authors (
    id VARCHAR(50) PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL
);

-- Create books table
CREATE TABLE books (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(500) NOT NULL,
    page_count INTEGER NOT NULL,
    author_id VARCHAR(50) NOT NULL,
    FOREIGN KEY (author_id) REFERENCES authors(id)
);

-- Insert sample authors
INSERT INTO authors (id, first_name, last_name) VALUES
('author-1', 'Donald', 'Horne'),
('author-2', 'Norman', 'Lindsay');

-- Insert sample books
INSERT INTO books (id, name, page_count, author_id) VALUES
('book-1', 'The Lucky Country', 300, 'author-1'),
('book-2', 'The Magic Pudding: Being The Adventures of Bunyip Bluegum and his friends Bill Barnacle and Sam Sawnoff', 250, 'author-2');
