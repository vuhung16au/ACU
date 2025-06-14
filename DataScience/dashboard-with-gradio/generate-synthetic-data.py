"""
Synthetic Sales Data Generator

This script generates a synthetic dataset of sales records for educational and demonstration purposes.
It creates a CSV file with randomized yet realistic patterns of sales data that can be used for
dashboarding and data analysis exercises.

The generated dataset includes:
- Order information (ID, date, status, shipping method, delivery date)
- Customer information (ID, name, segment, location, age)
- Product details (ID, name, category, cost, rating)
- Sales metrics (quantity, price, total, profit, discount)
- Additional details (sales rep, campaign, store information, payment details)

The dataset mimics real-world business data with:
- Realistic correlations (e.g., shipping method affects delivery time)
- Natural distributions (e.g., higher probability of completed orders)
- Temporal patterns spanning from 2020 to present day
- Varied pricing structures with cost-based profit calculations

Dependencies:
- polars: Fast DataFrame library for data manipulation
- numpy: Library for numerical operations
- datetime: For date and time operations
- pandas: For date operations (specifically Timedelta)
- faker: For generating realistic fake data
"""

import polars as pl  # Polars is a fast DataFrame library implemented in Rust
import numpy as np   # NumPy for numerical operations and array handling
from datetime import datetime, timedelta  # For date and time generation and manipulation
import pandas as pd  # For Timedelta operations
import os
from faker import Faker  # For generating realistic fake data

def generate_synthetic_sale_data(nrows: int, filename: str, seed: int = 42):
    """
    Generate synthetic sales data and save it to a CSV file.
    
    This function creates a comprehensive dataset with realistic sales patterns,
    customer information, and product details. The data is designed to have natural
    variability while maintaining logical relationships between variables.
    
    Parameters:
    -----------
    nrows : int
        Number of rows (sales records) to generate. Higher values create larger datasets
        which may be more suitable for performance testing or advanced analytics.
    
    filename : str
        Path and filename where the CSV will be saved. The function will create
        any necessary directories in the path if they don't exist.
    
    seed : int, default=42
        Random seed for reproducibility. Using the same seed will generate
        identical datasets, which is useful for educational demonstrations.
        
    Returns:
    --------
    None
        The function saves the data directly to a CSV file and prints a confirmation message.
    """
    # Initialize Faker and set random seeds for reproducibility
    # This ensures that the same "random" data is generated each time with the same seed
    fake = Faker()
    Faker.seed(seed)
    np.random.seed(seed)
    
    # Define product data with realistic product information
    # Each product has a name, category, and base price that reflects market values
    # This structured approach creates logical relationships between product categories and prices
    products = [
        {"name": "Premium Wireless Headphones", "category": "Audio", "base_price": 149.99},
        {"name": "Ultra HD Tablet", "category": "Computing", "base_price": 499.99},
        {"name": "Modern Bookshelf", "category": "Furniture", "base_price": 129.99},
        {"name": "Ergonomic Wireless Mouse", "category": "Computing", "base_price": 59.99},
        {"name": "Smart LED Desk Lamp", "category": "Accessories", "base_price": 45.99},
        {"name": "Advanced Fitness Tracker", "category": "Tech Accessories", "base_price": 89.99},
        {"name": "Premium Daily Planner", "category": "Stationery", "base_price": 24.99},
        {"name": "Professional Art Markers Set", "category": "Stationery", "base_price": 34.99},
        {"name": "Insulated Stainless Water Bottle", "category": "Kitchen", "base_price": 29.99},
        {"name": "High-Performance Blender", "category": "Kitchen", "base_price": 119.99},
        {"name": "Water-Resistant Laptop Backpack", "category": "Travel", "base_price": 79.99},
        {"name": "Ceramic Plant Pot Set", "category": "Home Decor", "base_price": 39.99},
        {"name": "Fast Wireless Charging Pad", "category": "Tech Accessories", "base_price": 49.99},
        {"name": "Adjustable Standing Desk", "category": "Office Furniture", "base_price": 299.99},
        {"name": "Voice-Controlled Smart Speaker", "category": "Smart Home", "base_price": 129.99}
    ]
    
    # Extract product info as arrays for efficient indexing in NumPy operations
    # This conversion optimizes performance when assigning product data to the dataset
    names = np.array([p["name"] for p in products])
    categories = np.array([p["category"] for p in products])
    base_prices = np.array([p["base_price"] for p in products])
    
    # Generate random product IDs from the available list of products
    # Each sale will be assigned one of the predefined products
    product_id = np.random.randint(len(names), size=nrows)
    
    # Generate random quantities for each order, between 1 and 10 items
    # This represents how many units of each product were purchased in a transaction
    quantity = np.random.randint(1, 11, size=nrows)
    
    # Apply price variability to simulate real-world price fluctuations
    # Multipliers create small variations (±10%) in price for the same product
    # This simulates sales, regional pricing differences, or other market factors
    price_multipliers = np.random.uniform(0.9, 1.1, size=nrows)
    price = base_prices[product_id] * price_multipliers
    
    # Generate random order dates between January 1, 2020 and current date
    # This creates a dataset spanning multiple years for trend analysis
    start_date = datetime(2020, 1, 1)  # Starting date for the range
    end_date = datetime.now()  # Ending date is today
    
    # Create order dates using Faker for more realistic temporal distribution
    # Faker tends to distribute dates more naturally than uniform random sampling
    order_dates = []
    order_dates_dt = []
    for _ in range(nrows):
        order_date = fake.date_between_dates(date_start=start_date, date_end=end_date)
        order_dates.append(order_date.strftime('%Y-%m-%d'))  # String format for CSV output
        order_dates_dt.append(order_date)  # Datetime objects for delivery date calculations
    
    order_dates = np.array(order_dates)
    
    # Generate customer names using Faker for realistic human names
    # This provides culturally diverse and naturally distributed names
    customer_names = [fake.name() for _ in range(nrows)]
    
    # Generate customer IDs with a logical ratio of orders to unique customers
    # This ensures repeat customers in the dataset, which is realistic for sales data
    # We aim for approximately 5 orders per customer on average
    num_unique_customers = min(nrows // 5, 10000)  # Cap at 10,000 unique customers for very large datasets
    customer_ids = np.random.randint(1000, 1000 + num_unique_customers, size=nrows)
    
    # Generate shipping methods with weighted probabilities
    # Standard shipping is most common (60%), while premium options are less frequent
    shipping_methods = np.random.choice(
        ["Standard", "Express", "Next Day", "Pickup"], 
        size=nrows, 
        p=[0.6, 0.2, 0.1, 0.1]  # Probability distribution of shipping methods
    )
    
    # Calculate delivery dates based on shipping method and order date
    # This creates a realistic correlation between shipping method and delivery time
    delivery_dates = []
    for i, order_date in enumerate(order_dates_dt):
        shipping_method = shipping_methods[i]
        # Different shipping methods have different delivery timeframes
        if shipping_method == "Next Day":
            min_days, max_days = 1, 3  # Next day might still take up to 3 days in real world
        elif shipping_method == "Express":
            min_days, max_days = 2, 5  # Express typically takes 2-5 days
        elif shipping_method == "Standard":
            min_days, max_days = 5, 10  # Standard shipping takes longer
        else:  # Pickup
            min_days, max_days = 0, 1  # Store pickup is typically same day or next day
            
        # Add a random number of days within the appropriate range for the shipping method
        delivery_date = order_date + timedelta(days=np.random.randint(min_days, max_days + 1))
        delivery_dates.append(delivery_date.strftime('%Y-%m-%d'))
    
    # Define all columns for the dataset as a dictionary
    # This approach allows us to build the dataset incrementally and calculate derived fields
    columns = {
        "order_id": np.arange(1, nrows + 1),  # Sequential order IDs starting from 1
        "order_date": order_dates,  # Random dates generated with Faker
        "customer_id": customer_ids,  # Random customer IDs
        "customer_name": customer_names,  # Realistic customer names from Faker
        "product_id": product_id + 200,  # Product IDs starting from 200 (offset for better readability)
        "product_names": names[product_id],  # Map product IDs to product names
        "categories": categories[product_id],  # Map product IDs to their categories
        "quantity": quantity,  # Random quantities generated above
        "price": np.round(price, 2),  # More realistic prices with cents
        "total": np.round(price * quantity, 2),  # Calculate total as price × quantity
        "shipping_method": shipping_methods,  # Add shipping method to base columns
        "delivery_date": delivery_dates,  # Add delivery dates to base columns
    }
    
    # Generate sales rep names first, then assign them to orders
    # This ensures the same sales rep names appear multiple times (like in real sales data)
    sales_reps = [fake.name() for _ in range(20)]  # Create 20 realistic sales rep names
    sales_rep_indices = np.random.randint(0, 20, size=nrows)  # Randomly assign reps to orders
    sales_rep_assignments = [sales_reps[i] for i in sales_rep_indices]  # Map indices to names
    
    # Add additional columns to the dataset with more realistic values
    columns.update({
        # Order information with weighted probabilities reflecting real business scenarios
        "order_status": np.random.choice(["Completed", "Processing", "Shipped", "Cancelled", "Returned"], 
                                         size=nrows, p=[0.65, 0.15, 0.1, 0.05, 0.05]),
        
        # Customer segmentation with business-like distributions
        "customer_segment": np.random.choice(["Retail", "Wholesale", "Corporate"], 
                                             size=nrows, p=[0.7, 0.2, 0.1]),
        
        # Realistic geographic distribution using Faker's city generator
        "customer_location": [fake.city() for _ in range(nrows)],
        
        # Customer age with normal distribution centered at 38 years
        # Normal distribution is more realistic than uniform for demographic data
        "customer_age": np.random.normal(loc=38, scale=14, size=nrows).astype(int).clip(18, 85),
        
        # Product cost represents wholesale cost to the business
        # Typically 50-70% of the retail price for most businesses
        "product_cost": np.round(price * np.random.uniform(0.5, 0.7, size=nrows), 2),
        
        # Discount amounts with weighted probabilities (most sales have no discount)
        "discount": np.round(np.random.choice([0, 0.05, 0.1, 0.15, 0.2], 
                                             size=nrows, p=[0.6, 0.2, 0.1, 0.05, 0.05]) * price * quantity, 2),
        
        # Product ratings with a positive skew (most products get good ratings)
        # This distribution is common in real rating systems
        "rating": np.random.choice([1, 2, 3, 4, 5], 
                                  size=nrows, p=[0.05, 0.1, 0.15, 0.3, 0.4]),
        
        # Sales team and campaign tracking
        "sales_rep": sales_rep_assignments,  # Fixed assignment of sales reps
        
        # Marketing campaign IDs with empty strings representing non-campaign sales
        "campaign_id": np.random.choice(["", "SUMMER23", "FALL23", "HOLIDAY23", "SPRING24", "BACKTOSCHOOL"], 
                                        size=nrows, p=[0.4, 0.12, 0.12, 0.16, 0.1, 0.1]),
        
        # Store information with IDs and geographical regions
        "store_id": np.random.randint(1, 51, size=nrows),  # 50 stores
        "store_region": np.random.choice(["Northeast", "Southeast", "Midwest", "Southwest", "West"], size=nrows),
        
        # Payment methods with realistic distribution (credit card most common)
        "payment_method": np.random.choice(["Credit Card", "PayPal", "Bank Transfer", "Cash", "Apple Pay", "Google Pay"], 
                                          size=nrows, p=[0.5, 0.2, 0.1, 0.1, 0.05, 0.05]),
        
        # Payment status (most payments successful in typical business)
        "payment_status": np.random.choice(["Paid", "Pending", "Failed"], 
                                          size=nrows, p=[0.85, 0.1, 0.05]),
    })
    
    # Calculate profit based on the actual cost, quantity sold, and any discounts applied
    # Profit = Total Revenue - Total Cost - Discounts
    columns["profit"] = np.round(
        columns["total"] - columns["product_cost"] * columns["quantity"] - columns["discount"], 
        2
    )
    
    # Create a Polars DataFrame from the dictionary of columns
    # Polars provides fast data processing capabilities for large datasets
    df = pl.DataFrame(columns)
    
    # Ensure the output directory exists before saving
    # This prevents file write errors if the directory doesn't exist
    os.makedirs(os.path.dirname(filename), exist_ok=True)
    
    # Write the DataFrame to a CSV file
    # CSV format is widely compatible with various data analysis tools
    df.write_csv(filename, separator=',', include_header=True)
    
    print(f"Generated {nrows} rows of synthetic sales data and saved to {filename}")

# Main execution block

# Generate 100,000 rows of synthetic sales data and save to CSV file
# This creates a reasonably sized dataset for most educational purposes
generate_synthetic_sale_data(100_000, "./sales_data/sales_data.csv")

# Alternate configurations (commented out)
# Uncomment to generate a larger dataset of 500,000 rows
# Larger datasets can be useful for performance testing or demonstrating big data techniques
# generate_synthetic_sale_data(500_000, "./sales_data/sales_data.csv")

# Uncomment to save the data to a different location (useful for Windows WSL environments)
# This path format is specifically for accessing Windows drives from WSL
# generate_synthetic_sale_data(100_000, "/mnt/d/sales_data/sales_data.csv")