-- Add cursor column to books table
ALTER TABLE books ADD COLUMN IF NOT EXISTS cursor VARCHAR(255);

-- Update existing books to have cursor values (base64 encoded IDs)
UPDATE books SET cursor = encode(id::bytea, 'base64') WHERE cursor IS NULL;

-- Create index on cursor for better pagination performance
CREATE INDEX IF NOT EXISTS idx_books_cursor ON books(cursor);
