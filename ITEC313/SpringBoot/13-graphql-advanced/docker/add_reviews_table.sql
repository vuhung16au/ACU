-- Migration script to add reviews table for book reviews
-- This script should be run after the initial database setup

-- Create reviews table
CREATE TABLE IF NOT EXISTS reviews (
    id VARCHAR(50) PRIMARY KEY,
    book_id VARCHAR(50) NOT NULL,
    user_id BIGINT NOT NULL,
    rating INTEGER CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_reviews_book_id ON reviews(book_id);
CREATE INDEX IF NOT EXISTS idx_reviews_user_id ON reviews(user_id);
CREATE INDEX IF NOT EXISTS idx_reviews_created_at ON reviews(created_at);

-- Insert comprehensive sample reviews for demonstration
INSERT INTO reviews (id, book_id, user_id, rating, comment, created_at) VALUES
-- 10 reviews for book-1 (Australia: The Lucky Country)
('review-1-1', 'book-1', 1, 5, 'Excellent book about Australia! Highly recommended.', CURRENT_TIMESTAMP),
('review-1-2', 'book-1', 1, 4, 'Great historical perspective on the Lucky Country.', CURRENT_TIMESTAMP),
('review-1-3', 'book-1', 1, 5, 'A must-read for anyone interested in Australian history and culture.', CURRENT_TIMESTAMP),
('review-1-4', 'book-1', 1, 4, 'Well-researched and engaging narrative about Australia''s development.', CURRENT_TIMESTAMP),
('review-1-5', 'book-1', 1, 3, 'Good overview but could dive deeper into indigenous perspectives.', CURRENT_TIMESTAMP),
('review-1-6', 'book-1', 1, 5, 'Fascinating insights into what makes Australia unique.', CURRENT_TIMESTAMP),
('review-1-7', 'book-1', 1, 4, 'Comprehensive coverage of Australia''s economic and social history.', CURRENT_TIMESTAMP),
('review-1-8', 'book-1', 1, 5, 'Brilliant analysis of the Australian character and society.', CURRENT_TIMESTAMP),
('review-1-9', 'book-1', 1, 4, 'Engaging writing style makes complex topics accessible.', CURRENT_TIMESTAMP),
('review-1-10', 'book-1', 1, 5, 'A classic that every Australian should read.', CURRENT_TIMESTAMP),

-- 10 reviews for book-2 (The Magic Pudding)
('review-2-1', 'book-2', 1, 5, 'A classic Australian children''s book. Wonderful illustrations!', CURRENT_TIMESTAMP),
('review-2-2', 'book-2', 1, 4, 'Charming story that captures the Australian spirit perfectly.', CURRENT_TIMESTAMP),
('review-2-3', 'book-2', 1, 5, 'Timeless tale that both children and adults can enjoy.', CURRENT_TIMESTAMP),
('review-2-4', 'book-2', 1, 4, 'The pudding that never runs out - what a delightful concept!', CURRENT_TIMESTAMP),
('review-2-5', 'book-2', 1, 5, 'Beautifully written with memorable characters and adventures.', CURRENT_TIMESTAMP),
('review-2-6', 'book-2', 1, 4, 'A true Australian classic that deserves its place in literature.', CURRENT_TIMESTAMP),
('review-2-7', 'book-2', 1, 3, 'Good story but some parts feel a bit dated for modern readers.', CURRENT_TIMESTAMP),
('review-2-8', 'book-2', 1, 5, 'The rhyming text and illustrations are absolutely delightful.', CURRENT_TIMESTAMP),
('review-2-9', 'book-2', 1, 4, 'Perfect bedtime story with a uniquely Australian flavor.', CURRENT_TIMESTAMP),
('review-2-10', 'book-2', 1, 5, 'A masterpiece of Australian children''s literature.', CURRENT_TIMESTAMP),

-- 10 reviews for book-3 (Ned Kelly: The Story of Australia's Most Famous Legend)
('review-3-1', 'book-3', 1, 3, 'Interesting biography, but could be more detailed.', CURRENT_TIMESTAMP),
('review-3-2', 'book-3', 1, 4, 'Good introduction to the life of Australia''s most famous outlaw.', CURRENT_TIMESTAMP),
('review-3-3', 'book-3', 1, 5, 'Fascinating account of the man behind the legend.', CURRENT_TIMESTAMP),
('review-3-4', 'book-3', 1, 4, 'Well-researched biography that separates fact from fiction.', CURRENT_TIMESTAMP),
('review-3-5', 'book-3', 1, 3, 'Decent overview but lacks the depth I was hoping for.', CURRENT_TIMESTAMP),
('review-3-6', 'book-3', 1, 4, 'Captures the complexity of Kelly''s character and times.', CURRENT_TIMESTAMP),
('review-3-7', 'book-3', 1, 5, 'Excellent historical context and engaging narrative.', CURRENT_TIMESTAMP),
('review-3-8', 'book-3', 1, 4, 'A balanced view of a controversial historical figure.', CURRENT_TIMESTAMP),
('review-3-9', 'book-3', 1, 3, 'Good for beginners but veterans might want more detail.', CURRENT_TIMESTAMP),
('review-3-10', 'book-3', 1, 4, 'Brings the legend to life with vivid storytelling.', CURRENT_TIMESTAMP),

-- 10 reviews for book-4 (The Unbearable Lightness of Being)
('review-4-1', 'book-4', 1, 4, 'Fascinating story set in Prague. Good character development.', CURRENT_TIMESTAMP),
('review-4-2', 'book-4', 1, 5, 'Philosophical depth combined with beautiful storytelling.', CURRENT_TIMESTAMP),
('review-4-3', 'book-4', 1, 4, 'Complex characters and themes that stay with you long after reading.', CURRENT_TIMESTAMP),
('review-4-4', 'book-4', 1, 5, 'A masterpiece of existential literature.', CURRENT_TIMESTAMP),
('review-4-5', 'book-4', 1, 3, 'Interesting concepts but sometimes too abstract for my taste.', CURRENT_TIMESTAMP),
('review-4-6', 'book-4', 1, 4, 'Beautifully written exploration of love, politics, and human nature.', CURRENT_TIMESTAMP),
('review-4-7', 'book-4', 1, 5, 'Kundera''s prose is simply magnificent.', CURRENT_TIMESTAMP),
('review-4-8', 'book-4', 1, 4, 'Thought-provoking novel that challenges conventional thinking.', CURRENT_TIMESTAMP),
('review-4-9', 'book-4', 1, 3, 'Good book but requires careful attention to fully appreciate.', CURRENT_TIMESTAMP),
('review-4-10', 'book-4', 1, 5, 'A profound meditation on the meaning of existence.', CURRENT_TIMESTAMP),

-- 10 reviews for book-5 (The Great Gatsby)
('review-5-1', 'book-5', 1, 5, 'Fitzgerald''s masterpiece of the American Dream.', CURRENT_TIMESTAMP),
('review-5-2', 'book-5', 1, 4, 'Beautiful prose and tragic story of the Jazz Age.', CURRENT_TIMESTAMP),
('review-5-3', 'book-5', 1, 5, 'A timeless classic that captures the essence of the 1920s.', CURRENT_TIMESTAMP),
('review-5-4', 'book-5', 1, 4, 'Gatsby''s pursuit of the American Dream is both inspiring and heartbreaking.', CURRENT_TIMESTAMP),
('review-5-5', 'book-5', 1, 5, 'The green light at the end of the dock - such powerful symbolism!', CURRENT_TIMESTAMP),
('review-5-6', 'book-5', 1, 4, 'Fitzgerald''s writing is pure poetry in prose form.', CURRENT_TIMESTAMP),
('review-5-7', 'book-5', 1, 3, 'Good story but the characters are hard to sympathize with.', CURRENT_TIMESTAMP),
('review-5-8', 'book-5', 1, 5, 'A perfect novel that gets better with each reading.', CURRENT_TIMESTAMP),
('review-5-9', 'book-5', 1, 4, 'The decadence and disillusionment of the era perfectly captured.', CURRENT_TIMESTAMP),
('review-5-10', 'book-5', 1, 5, 'One of the greatest American novels ever written.', CURRENT_TIMESTAMP),

-- Random reviews for books 6-20 (1-10 reviews each)
-- Book 6 (3 reviews)
('review-6-1', 'book-6', 1, 4, 'Engaging mystery with unexpected twists.', CURRENT_TIMESTAMP),
('review-6-2', 'book-6', 1, 3, 'Decent plot but the ending felt rushed.', CURRENT_TIMESTAMP),
('review-6-3', 'book-6', 1, 5, 'Couldn''t put it down! Highly recommend.', CURRENT_TIMESTAMP),

-- Book 7 (7 reviews)
('review-7-1', 'book-7', 1, 4, 'Well-written science fiction with interesting concepts.', CURRENT_TIMESTAMP),
('review-7-2', 'book-7', 1, 5, 'Mind-bending plot that keeps you guessing.', CURRENT_TIMESTAMP),
('review-7-3', 'book-7', 1, 3, 'Good ideas but execution could be better.', CURRENT_TIMESTAMP),
('review-7-4', 'book-7', 1, 4, 'Fascinating world-building and character development.', CURRENT_TIMESTAMP),
('review-7-5', 'book-7', 1, 5, 'A masterpiece of speculative fiction.', CURRENT_TIMESTAMP),
('review-7-6', 'book-7', 1, 4, 'Complex narrative that rewards careful reading.', CURRENT_TIMESTAMP),
('review-7-7', 'book-7', 1, 3, 'Interesting premise but sometimes confusing.', CURRENT_TIMESTAMP),

-- Book 8 (2 reviews)
('review-8-1', 'book-8', 1, 4, 'Heartwarming story about friendship and courage.', CURRENT_TIMESTAMP),
('review-8-2', 'book-8', 1, 5, 'Beautifully written with memorable characters.', CURRENT_TIMESTAMP),

-- Book 9 (9 reviews)
('review-9-1', 'book-9', 1, 4, 'Compelling historical fiction with rich detail.', CURRENT_TIMESTAMP),
('review-9-2', 'book-9', 1, 5, 'Brings the past to life with vivid storytelling.', CURRENT_TIMESTAMP),
('review-9-3', 'book-9', 1, 3, 'Good historical accuracy but slow pacing.', CURRENT_TIMESTAMP),
('review-9-4', 'book-9', 1, 4, 'Well-researched and engaging narrative.', CURRENT_TIMESTAMP),
('review-9-5', 'book-9', 1, 5, 'A sweeping epic that captures the era perfectly.', CURRENT_TIMESTAMP),
('review-9-6', 'book-9', 1, 4, 'Complex characters and intricate plot.', CURRENT_TIMESTAMP),
('review-9-7', 'book-9', 1, 3, 'Interesting period piece but too long.', CURRENT_TIMESTAMP),
('review-9-8', 'book-9', 1, 4, 'Masterful blend of fact and fiction.', CURRENT_TIMESTAMP),
('review-9-9', 'book-9', 1, 5, 'Unforgettable characters and dramatic events.', CURRENT_TIMESTAMP),

-- Book 10 (1 review)
('review-10-1', 'book-10', 1, 4, 'Thought-provoking exploration of human nature.', CURRENT_TIMESTAMP),

-- Book 11 (6 reviews)
('review-11-1', 'book-11', 1, 5, 'Brilliant psychological thriller.', CURRENT_TIMESTAMP),
('review-11-2', 'book-11', 1, 4, 'Keeps you on the edge of your seat.', CURRENT_TIMESTAMP),
('review-11-3', 'book-11', 1, 3, 'Good suspense but predictable ending.', CURRENT_TIMESTAMP),
('review-11-4', 'book-11', 1, 5, 'Masterful plotting and character development.', CURRENT_TIMESTAMP),
('review-11-5', 'book-11', 1, 4, 'Complex mystery with satisfying resolution.', CURRENT_TIMESTAMP),
('review-11-6', 'book-11', 1, 3, 'Engaging but could be more original.', CURRENT_TIMESTAMP),

-- Book 12 (4 reviews)
('review-12-1', 'book-12', 1, 4, 'Beautiful poetry that speaks to the soul.', CURRENT_TIMESTAMP),
('review-12-2', 'book-12', 1, 5, 'Lyrical and profound collection.', CURRENT_TIMESTAMP),
('review-12-3', 'book-12', 1, 3, 'Some gems but overall uneven.', CURRENT_TIMESTAMP),
('review-12-4', 'book-12', 1, 4, 'Evocative imagery and emotional depth.', CURRENT_TIMESTAMP),

-- Book 13 (8 reviews)
('review-13-1', 'book-13', 1, 4, 'Innovative fantasy world with unique magic system.', CURRENT_TIMESTAMP),
('review-13-2', 'book-13', 1, 5, 'Epic adventure with unforgettable characters.', CURRENT_TIMESTAMP),
('review-13-3', 'book-13', 1, 3, 'Good world-building but slow start.', CURRENT_TIMESTAMP),
('review-13-4', 'book-13', 1, 4, 'Complex political intrigue and personal growth.', CURRENT_TIMESTAMP),
('review-13-5', 'book-13', 1, 5, 'A new classic in the fantasy genre.', CURRENT_TIMESTAMP),
('review-13-6', 'book-13', 1, 4, 'Rich mythology and compelling storytelling.', CURRENT_TIMESTAMP),
('review-13-7', 'book-13', 1, 3, 'Interesting concepts but confusing plot.', CURRENT_TIMESTAMP),
('review-13-8', 'book-13', 1, 4, 'Satisfying conclusion to an epic journey.', CURRENT_TIMESTAMP),

-- Book 14 (5 reviews)
('review-14-1', 'book-14', 1, 4, 'Insightful analysis of modern society.', CURRENT_TIMESTAMP),
('review-14-2', 'book-14', 1, 5, 'Thought-provoking and well-argued.', CURRENT_TIMESTAMP),
('review-14-3', 'book-14', 1, 3, 'Interesting ideas but dense writing.', CURRENT_TIMESTAMP),
('review-14-4', 'book-14', 1, 4, 'Important contribution to contemporary thought.', CURRENT_TIMESTAMP),
('review-14-5', 'book-14', 1, 3, 'Good points but overly academic style.', CURRENT_TIMESTAMP),

-- Book 15 (10 reviews)
('review-15-1', 'book-15', 1, 5, 'Hilarious comedy that had me laughing out loud.', CURRENT_TIMESTAMP),
('review-15-2', 'book-15', 1, 4, 'Witty dialogue and clever plot twists.', CURRENT_TIMESTAMP),
('review-15-3', 'book-15', 1, 5, 'Perfect blend of humor and heart.', CURRENT_TIMESTAMP),
('review-15-4', 'book-15', 1, 4, 'Charming characters and delightful story.', CURRENT_TIMESTAMP),
('review-15-5', 'book-15', 1, 3, 'Funny but sometimes tries too hard.', CURRENT_TIMESTAMP),
('review-15-6', 'book-15', 1, 5, 'A feel-good book that brightens your day.', CURRENT_TIMESTAMP),
('review-15-7', 'book-15', 1, 4, 'Clever humor with surprising depth.', CURRENT_TIMESTAMP),
('review-15-8', 'book-15', 1, 5, 'One of the funniest books I''ve ever read.', CURRENT_TIMESTAMP),
('review-15-9', 'book-15', 1, 4, 'Light-hearted entertainment with substance.', CURRENT_TIMESTAMP),
('review-15-10', 'book-15', 1, 3, 'Good for a quick laugh but forgettable.', CURRENT_TIMESTAMP),

-- Book 16 (2 reviews)
('review-16-1', 'book-16', 1, 4, 'Compelling memoir with honest reflection.', CURRENT_TIMESTAMP),
('review-16-2', 'book-16', 1, 5, 'Raw and powerful personal story.', CURRENT_TIMESTAMP),

-- Book 17 (7 reviews)
('review-17-1', 'book-17', 1, 4, 'Gripping crime novel with realistic characters.', CURRENT_TIMESTAMP),
('review-17-2', 'book-17', 1, 5, 'Masterful detective story with perfect pacing.', CURRENT_TIMESTAMP),
('review-17-3', 'book-17', 1, 3, 'Good mystery but formulaic plot.', CURRENT_TIMESTAMP),
('review-17-4', 'book-17', 1, 4, 'Complex investigation with satisfying resolution.', CURRENT_TIMESTAMP),
('review-17-5', 'book-17', 1, 5, 'A new favorite in the crime genre.', CURRENT_TIMESTAMP),
('review-17-6', 'book-17', 1, 4, 'Well-crafted suspense with memorable characters.', CURRENT_TIMESTAMP),
('review-17-7', 'book-17', 1, 3, 'Decent thriller but predictable twists.', CURRENT_TIMESTAMP),

-- Book 18 (1 review)
('review-18-1', 'book-18', 1, 4, 'Beautifully written coming-of-age story.', CURRENT_TIMESTAMP),

-- Book 19 (6 reviews)
('review-19-1', 'book-19', 1, 5, 'Stunning literary fiction with poetic prose.', CURRENT_TIMESTAMP),
('review-19-2', 'book-19', 1, 4, 'Deeply moving exploration of human relationships.', CURRENT_TIMESTAMP),
('review-19-3', 'book-19', 1, 3, 'Beautiful writing but slow-paced plot.', CURRENT_TIMESTAMP),
('review-19-4', 'book-19', 1, 5, 'A masterpiece of contemporary literature.', CURRENT_TIMESTAMP),
('review-19-5', 'book-19', 1, 4, 'Complex characters and emotional depth.', CURRENT_TIMESTAMP),
('review-19-6', 'book-19', 1, 3, 'Good character study but lacks action.', CURRENT_TIMESTAMP),

-- Book 20 (8 reviews)
('review-20-1', 'book-20', 1, 4, 'Innovative science fiction with big ideas.', CURRENT_TIMESTAMP),
('review-20-2', 'book-20', 1, 5, 'Mind-expanding concepts and thrilling adventure.', CURRENT_TIMESTAMP),
('review-20-3', 'book-20', 1, 3, 'Interesting ideas but confusing execution.', CURRENT_TIMESTAMP),
('review-20-4', 'book-20', 1, 4, 'Ambitious scope with mostly successful delivery.', CURRENT_TIMESTAMP),
('review-20-5', 'book-20', 1, 5, 'A landmark work in the genre.', CURRENT_TIMESTAMP),
('review-20-6', 'book-20', 1, 4, 'Complex world-building and compelling characters.', CURRENT_TIMESTAMP),
('review-20-7', 'book-20', 1, 3, 'Good concept but overly complicated plot.', CURRENT_TIMESTAMP),
('review-20-8', 'book-20', 1, 4, 'Satisfying conclusion to an epic journey.', CURRENT_TIMESTAMP);

-- Note: The user_id references the default user (id=1) created in the initial setup
-- In a real application, you would have multiple users and their reviews
