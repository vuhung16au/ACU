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
