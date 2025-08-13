-- V2__Insert_sample_data.sql
-- Migration to insert sample data for development and testing

-- Insert sample products
INSERT INTO products (name, description, price, category, stock_quantity, sku, image_url, is_active, created_at, updated_at) VALUES
('Laptop Pro', 'High-performance laptop with latest specifications', 1299.99, 'ELECTRONICS', 50, 'LAPTOP-001', 'https://example.com/laptop.jpg', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Smartphone X', 'Latest smartphone with advanced camera features', 899.99, 'ELECTRONICS', 100, 'PHONE-001', 'https://example.com/phone.jpg', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Running Shoes', 'Comfortable running shoes for professional athletes', 129.99, 'SPORTS', 75, 'SHOES-001', 'https://example.com/shoes.jpg', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Coffee Maker', 'Automatic coffee maker with programmable features', 199.99, 'HOME_AND_GARDEN', 30, 'COFFEE-001', 'https://example.com/coffee.jpg', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Programming Book', 'Comprehensive guide to Spring Boot development', 49.99, 'BOOKS', 200, 'BOOK-001', 'https://example.com/book.jpg', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Yoga Mat', 'Premium yoga mat for home workouts', 39.99, 'SPORTS', 150, 'YOGA-001', 'https://example.com/yoga.jpg', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Bluetooth Headphones', 'Wireless headphones with noise cancellation', 299.99, 'ELECTRONICS', 80, 'HEADPHONES-001', 'https://example.com/headphones.jpg', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Garden Tool Set', 'Complete set of essential garden tools', 89.99, 'HOME_AND_GARDEN', 45, 'GARDEN-001', 'https://example.com/garden.jpg', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample customers
INSERT INTO customers (first_name, last_name, email, phone, status, created_at, updated_at) VALUES
('John', 'Doe', 'john.doe@example.com', '+1234567890', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Jane', 'Smith', 'jane.smith@example.com', '+1234567891', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Bob', 'Johnson', 'bob.johnson@example.com', '+1234567892', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Alice', 'Brown', 'alice.brown@example.com', '+1234567893', 'INACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Charlie', 'Wilson', 'charlie.wilson@example.com', '+1234567894', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Diana', 'Davis', 'diana.davis@example.com', '+1234567895', 'PENDING', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Edward', 'Miller', 'edward.miller@example.com', '+1234567896', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Fiona', 'Garcia', 'fiona.garcia@example.com', '+1234567897', 'SUSPENDED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample orders
INSERT INTO orders (order_number, customer_id, status, total_amount, notes, created_at, updated_at) VALUES
('ORD-001', 1, 'CONFIRMED', 1299.99, 'Customer requested express shipping', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('ORD-002', 2, 'PROCESSING', 899.99, 'Standard shipping', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('ORD-003', 3, 'SHIPPED', 129.99, 'Delivered to customer', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('ORD-004', 1, 'DELIVERED', 199.99, 'Customer satisfied', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('ORD-005', 5, 'PENDING', 49.99, 'Awaiting payment confirmation', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('ORD-006', 2, 'CONFIRMED', 299.99, 'Gift order', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('ORD-007', 7, 'CANCELLED', 89.99, 'Customer cancelled', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('ORD-008', 3, 'REFUNDED', 39.99, 'Product returned', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample order items
INSERT INTO order_items (order_id, product_id, product_name, quantity, price, notes, created_at, updated_at) VALUES
(1, 1, 'Laptop Pro', 1, 1299.99, 'High-end configuration', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2, 'Smartphone X', 1, 899.99, '256GB model', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 3, 'Running Shoes', 1, 129.99, 'Size 10', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 4, 'Coffee Maker', 1, 199.99, 'Black color', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 5, 'Programming Book', 1, 49.99, 'Digital version included', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 7, 'Bluetooth Headphones', 1, 299.99, 'Premium model', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 8, 'Garden Tool Set', 1, 89.99, 'Complete set', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 6, 'Yoga Mat', 1, 39.99, 'Blue color', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
