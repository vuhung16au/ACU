-- Sample data initialization for development profile
-- This file is executed when defer-datasource-initialization is true

-- Insert sample products
INSERT INTO products (name, description, price, category, stock_quantity, sku, image_url, is_active, created_at, updated_at) VALUES
('Laptop Pro', 'High-performance laptop with latest specifications', 1299.99, 'ELECTRONICS', 50, 'LAPTOP-001', 'https://example.com/laptop.jpg', true, NOW(), NOW()),
('Smartphone X', 'Latest smartphone with advanced camera features', 899.99, 'ELECTRONICS', 100, 'PHONE-001', 'https://example.com/phone.jpg', true, NOW(), NOW()),
('Running Shoes', 'Comfortable running shoes for professional athletes', 129.99, 'SPORTS', 75, 'SHOES-001', 'https://example.com/shoes.jpg', true, NOW(), NOW()),
('Coffee Maker', 'Automatic coffee maker with programmable features', 199.99, 'HOME_AND_GARDEN', 30, 'COFFEE-001', 'https://example.com/coffee.jpg', true, NOW(), NOW()),
('Programming Book', 'Comprehensive guide to Spring Boot development', 49.99, 'BOOKS', 200, 'BOOK-001', 'https://example.com/book.jpg', true, NOW(), NOW()),
('Yoga Mat', 'Premium yoga mat for home workouts', 39.99, 'SPORTS', 150, 'YOGA-001', 'https://example.com/yoga.jpg', true, NOW(), NOW()),
('Bluetooth Headphones', 'Wireless headphones with noise cancellation', 299.99, 'ELECTRONICS', 80, 'HEADPHONES-001', 'https://example.com/headphones.jpg', true, NOW(), NOW()),
('Garden Tool Set', 'Complete set of essential garden tools', 89.99, 'HOME_AND_GARDEN', 45, 'GARDEN-001', 'https://example.com/garden.jpg', true, NOW(), NOW());

-- Insert sample customers
INSERT INTO customers (first_name, last_name, email, phone, status, created_at, updated_at) VALUES
('John', 'Doe', 'john.doe@example.com', '+1234567890', 'ACTIVE', NOW(), NOW()),
('Jane', 'Smith', 'jane.smith@example.com', '+1234567891', 'ACTIVE', NOW(), NOW()),
('Bob', 'Johnson', 'bob.johnson@example.com', '+1234567892', 'ACTIVE', NOW(), NOW()),
('Alice', 'Brown', 'alice.brown@example.com', '+1234567893', 'INACTIVE', NOW(), NOW()),
('Charlie', 'Wilson', 'charlie.wilson@example.com', '+1234567894', 'ACTIVE', NOW(), NOW()),
('Diana', 'Davis', 'diana.davis@example.com', '+1234567895', 'PENDING', NOW(), NOW()),
('Edward', 'Miller', 'edward.miller@example.com', '+1234567896', 'ACTIVE', NOW(), NOW()),
('Fiona', 'Garcia', 'fiona.garcia@example.com', '+1234567897', 'SUSPENDED', NOW(), NOW());

-- Insert sample orders
INSERT INTO orders (order_number, customer_id, status, total_amount, notes, created_at, updated_at) VALUES
('ORD-001', 1, 'CONFIRMED', 1299.99, 'Customer requested express shipping', NOW(), NOW()),
('ORD-002', 2, 'PROCESSING', 899.99, 'Standard shipping', NOW(), NOW()),
('ORD-003', 3, 'SHIPPED', 129.99, 'Delivered to customer', NOW(), NOW()),
('ORD-004', 1, 'DELIVERED', 199.99, 'Customer satisfied', NOW(), NOW()),
('ORD-005', 5, 'PENDING', 49.99, 'Awaiting payment confirmation', NOW(), NOW()),
('ORD-006', 2, 'CONFIRMED', 299.99, 'Gift order', NOW(), NOW()),
('ORD-007', 7, 'CANCELLED', 89.99, 'Customer cancelled', NOW(), NOW()),
('ORD-008', 3, 'REFUNDED', 39.99, 'Product returned', NOW(), NOW());

-- Insert sample order items
INSERT INTO order_items (order_id, product_id, product_name, quantity, price, notes, created_at, updated_at) VALUES
(1, 1, 'Laptop Pro', 1, 1299.99, 'High-end configuration', NOW(), NOW()),
(2, 2, 'Smartphone X', 1, 899.99, '256GB model', NOW(), NOW()),
(3, 3, 'Running Shoes', 1, 129.99, 'Size 10', NOW(), NOW()),
(4, 4, 'Coffee Maker', 1, 199.99, 'Black color', NOW(), NOW()),
(5, 5, 'Programming Book', 1, 49.99, 'Digital version included', NOW(), NOW()),
(6, 7, 'Bluetooth Headphones', 1, 299.99, 'Premium model', NOW(), NOW()),
(7, 8, 'Garden Tool Set', 1, 89.99, 'Complete set', NOW(), NOW()),
(8, 6, 'Yoga Mat', 1, 39.99, 'Blue color', NOW(), NOW());
