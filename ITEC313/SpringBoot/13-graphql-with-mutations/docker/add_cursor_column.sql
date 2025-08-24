-- Add genre column to books table
ALTER TABLE books ADD COLUMN genre VARCHAR(100);

-- Add cursor column to books table (if not exists)
ALTER TABLE books ADD COLUMN cursor VARCHAR(255);

-- Update existing books with genre and cursor values
UPDATE books SET 
    genre = CASE 
        WHEN id = 'book-1' THEN 'Non-Fiction'
        WHEN id = 'book-2' THEN 'Children'
        ELSE 'Fiction'
    END,
    cursor = encode(id::bytea, 'base64')
WHERE genre IS NULL OR cursor IS NULL;

-- Create index on cursor for better pagination performance
CREATE INDEX IF NOT EXISTS idx_books_cursor ON books(cursor);

-- Migration script to add many-to-many relationship between books and genres
-- This script should be run after the initial database setup

-- Create genres table
CREATE TABLE IF NOT EXISTS genres (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT
);

-- Create book_genres junction table
CREATE TABLE IF NOT EXISTS book_genres (
    book_id VARCHAR(50) NOT NULL,
    genre_id VARCHAR(50) NOT NULL,
    PRIMARY KEY (book_id, genre_id),
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE
);

-- Insert sample genres
INSERT INTO genres (id, name, description) VALUES
('genre-1', 'Fiction', 'Imaginative literature that is not based on real events'),
('genre-2', 'Non-Fiction', 'Literature based on facts and real events'),
('genre-3', 'Science Fiction', 'Fiction dealing with futuristic science and technology'),
('genre-4', 'Fantasy', 'Fiction involving magical and supernatural elements'),
('genre-5', 'Mystery', 'Fiction involving crime and detective work'),
('genre-6', 'Thriller', 'Fiction designed to create excitement and suspense'),
('genre-7', 'Romance', 'Fiction focusing on romantic relationships'),
('genre-8', 'Adventure', 'Fiction involving exciting journeys and quests'),
('genre-9', 'Historical Fiction', 'Fiction set in the past'),
('genre-10', 'Biography', 'Non-fiction about real people''s lives'),
('genre-11', 'Children', 'Literature written for children'),
('genre-12', 'Horror', 'Fiction designed to frighten and scare'),
('genre-13', 'Poetry', 'Literary work in verse form'),
('genre-14', 'Drama', 'Literature intended for performance'),
('genre-15', 'Comedy', 'Humorous literature and entertainment');

-- Create book-genre relationships based on existing genre data
-- This maps the existing string genre field to the new genre entities
INSERT INTO book_genres (book_id, genre_id) 
SELECT b.id, g.id 
FROM books b 
JOIN genres g ON LOWER(b.genre) = LOWER(g.name)
WHERE b.genre IS NOT NULL;

-- Note: We keep the existing genre column for backward compatibility
-- You can optionally remove it later with: ALTER TABLE books DROP COLUMN genre;
