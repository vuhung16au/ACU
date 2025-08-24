-- Insert sample authors
INSERT INTO authors (name, email, biography, birth_date, created_at, updated_at) VALUES
('J.K. Rowling', 'jk.rowling@example.com', 'British author best known for the Harry Potter series', '1965-07-31', CURRENT_DATE, CURRENT_DATE),
('George R.R. Martin', 'grrm@example.com', 'American novelist and short story writer, creator of A Song of Ice and Fire', '1948-09-20', CURRENT_DATE, CURRENT_DATE),
('Stephen King', 'stephen.king@example.com', 'American author of horror, supernatural fiction, suspense, and fantasy novels', '1947-09-21', CURRENT_DATE, CURRENT_DATE),
('Agatha Christie', 'agatha.christie@example.com', 'English writer known for her detective novels', '1890-09-15', CURRENT_DATE, CURRENT_DATE),
('Ernest Hemingway', 'ernest.hemingway@example.com', 'American novelist, short story writer, and journalist', '1899-07-21', CURRENT_DATE, CURRENT_DATE),
('Donald Horne', 'donald.horne@example.com', 'Australian journalist, writer, social critic and academic', '1921-12-26', CURRENT_DATE, CURRENT_DATE);

-- Insert sample tags
INSERT INTO tags (name, description, created_at, updated_at) VALUES
('Fantasy', 'Fantasy literature and magical worlds', CURRENT_DATE, CURRENT_DATE),
('Horror', 'Horror and supernatural fiction', CURRENT_DATE, CURRENT_DATE),
('Mystery', 'Detective and mystery novels', CURRENT_DATE, CURRENT_DATE),
('Adventure', 'Adventure and action stories', CURRENT_DATE, CURRENT_DATE),
('Classic', 'Classic literature', CURRENT_DATE, CURRENT_DATE),
('Young Adult', 'Young adult fiction', CURRENT_DATE, CURRENT_DATE),
('Thriller', 'Suspense and thriller novels', CURRENT_DATE, CURRENT_DATE),
('Historical', 'Historical fiction', CURRENT_DATE, CURRENT_DATE),
('Non-fiction', 'Non-fiction literature and academic works', CURRENT_DATE, CURRENT_DATE);

-- Insert sample books
INSERT INTO books (title, description, publication_year, isbn, price, page_count, author_id, created_at, updated_at) VALUES
('Harry Potter and the Philosopher''s Stone', 'The first book in the Harry Potter series', 1997, '978-0747532699', 29.99, 223, 1, CURRENT_DATE, CURRENT_DATE),
('Harry Potter and the Chamber of Secrets', 'The second book in the Harry Potter series', 1998, '978-0747538493', 29.99, 251, 1, CURRENT_DATE, CURRENT_DATE),
('A Game of Thrones', 'The first book in A Song of Ice and Fire series', 1996, '978-0553103540', 34.99, 694, 2, CURRENT_DATE, CURRENT_DATE),
('The Shining', 'A horror novel about a haunted hotel', 1977, '978-0385121675', 24.99, 447, 3, CURRENT_DATE, CURRENT_DATE),
('Murder on the Orient Express', 'A detective novel featuring Hercule Poirot', 1934, '978-0062073495', 19.99, 256, 4, CURRENT_DATE, CURRENT_DATE),
('The Old Man and the Sea', 'A novel about an aging fisherman', 1952, '978-0684801223', 16.99, 127, 5, CURRENT_DATE, CURRENT_DATE),
('Harry Potter and the Prisoner of Azkaban', 'The third book in the Harry Potter series', 1999, '978-0747542155', 29.99, 317, 1, CURRENT_DATE, CURRENT_DATE),
('A Clash of Kings', 'The second book in A Song of Ice and Fire series', 1998, '978-0553108033', 34.99, 761, 2, CURRENT_DATE, CURRENT_DATE),
('The Lucky Country', 'A 1964 book about Australia that has become a nickname for the country. The title is generally used favourably, although the origin of the phrase was negative in the context of the book. Among other things, it has been used in reference to Australia''s natural resources, weather, history, its early dependency of the British system, distance from problems elsewhere in the world, and other sorts of supposed prosperity.', 1964, '978-0143180029', 22.99, 288, 6, CURRENT_DATE, CURRENT_DATE);

-- Insert book-tag relationships
INSERT INTO book_tags (book_id, tag_id) VALUES
(1, 1), (1, 6), -- Harry Potter and the Philosopher's Stone: Fantasy, Young Adult
(2, 1), (2, 6), -- Harry Potter and the Chamber of Secrets: Fantasy, Young Adult
(3, 1), (3, 8), -- A Game of Thrones: Fantasy, Historical
(4, 2), (4, 7), -- The Shining: Horror, Thriller
(5, 3), (5, 7), -- Murder on the Orient Express: Mystery, Thriller
(6, 5), (6, 4), -- The Old Man and the Sea: Classic, Adventure
(7, 1), (7, 6), -- Harry Potter and the Prisoner of Azkaban: Fantasy, Young Adult
(8, 1), (8, 8), -- A Clash of Kings: Fantasy, Historical
(9, 9); -- The Lucky Country: Non-fiction
