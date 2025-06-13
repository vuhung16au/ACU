"""
Synthetic Sales Data Generator

This script generates a synthetic dataset of sales records for educational and demonstration purposes.
It creates a CSV file with randomized yet realistic patterns of sales data that can be used for
dashboarding and data analysis exercises.

The generated dataset includes:
- Order information (ID, date)
- Customer information (ID, name)
- Product details (ID, name, category)
- Sales metrics (quantity, price, total)

Dependencies:
- polars: Fast DataFrame library for data manipulation
- numpy: Library for numerical operations
- datetime: For date and time operations
"""

import polars as pl  # Polars is a fast DataFrame library implemented in Rust
import numpy as np   # NumPy for numerical operations and array handling
from datetime import datetime, timedelta  # For date generation and manipulation

def generate(nrows: int, filename: str):
    """
    Generate synthetic sales data and save it to a CSV file.
    
    Parameters:
    -----------
    nrows : int
        Number of rows (sales records) to generate
    filename : str
        Path and filename where the CSV will be saved
    
    Returns:
    --------
    None
        The function saves the data directly to a CSV file
    """
    
    # Define product names that will be used in the synthetic data
    # These represent common office and electronic items
    names = np.asarray(
        [
            "Laptop",
            "Smartphone",
            "Desk",
            "Chair",
            "Monitor",
            "Printer",
            "Paper",
            "Pen",
            "Notebook",
            "Coffee Maker",
            "Cabinet",
            "Plastic Cups",
        ]
    )
    
    # Define categories for each product
    # Each category corresponds to the product at the same index in the names array
    categories = np.asarray(
        [
            "Electronics",
            "Electronics",
            "Office",
            "Office",
            "Electronics",
            "Electronics",
            "Stationery",
            "Stationery",
            "Stationery",
            "Electronics",
            "Office",
            "Sundry",
        ]
    )
    # Generate random product IDs from the available list of products
    # This creates an array of indices that will be used to select products from the names array
    product_id = np.random.randint(len(names), size=nrows)
    
    # Generate random quantities for each order, between 1 and 10 items
    quantity = np.random.randint(1, 11, size=nrows)
    
    # Generate random prices between $1.99 and $100.00 
    # First generates integers between 199 and 10000, then divides by 100 to get dollars and cents
    price = np.random.randint(199, 10000, size=nrows) / 100
    
    # Generate random order dates between January 1, 2020 and December 31, 2025
    start_date = datetime(2020, 1, 1)  # Starting date for the range
    end_date = datetime(2024, 12, 31)  # Ending date for the range
    date_range = (end_date - start_date).days  # Calculate the number of days in the range
    
    # Create random dates as a numpy array and convert each date to string format 'YYYY-MM-DD'
    # This list comprehension:
    # 1. Iterates nrows times
    # 2. For each iteration, generates a random number of days to add to the start date
    # 3. Converts the resulting datetime to a string in 'YYYY-MM-DD' format
    order_dates = np.array([(start_date + timedelta(days=np.random.randint(0, date_range))).strftime('%Y-%m-%d') for _ in range(nrows)])
    
    # Define all columns for the dataset as a dictionary
    # Each key is the column name and each value is the array of data for that column
    columns = {
        "order_id": np.arange(nrows),  # Sequential order IDs starting from 0
        "order_date": order_dates,  # Random dates generated above
        "customer_id": np.random.randint(100, 1000, size=nrows),  # Random customer IDs between 100 and 999
        "customer_name": [f"Customer_{i}" for i in np.random.randint(2**15, size=nrows)],  # Generate unique customer names
        "product_id": product_id + 200,  # Product IDs starting from 200
        "product_names": names[product_id],  # Map product IDs to product names
        "categories": categories[product_id],  # Map product IDs to their categories
        "quantity": quantity,  # Random quantities generated above
        "price": price,  # Random prices generated above
        "total": price * quantity,  # Calculate total cost (price × quantity) for each order
    }
    
    # Create a Polars DataFrame from the dictionary of columns
    # Polars is used instead of pandas for better performance with large datasets
    df = pl.DataFrame(columns)
    
    # Write the DataFrame to a CSV file
    # - separator=',' ensures a standard CSV format
    # - include_header=True adds column names as the first row
    df.write_csv(filename, separator=',', include_header=True)

# Main execution block

# Generate 100,000 rows of synthetic sales data and save to CSV file
# The data will be saved in the 'sales_data' directory as 'sales_data.csv'
generate(100_000, "./sales_data/sales_data.csv")

# Alternate configurations (commented out)
# Uncomment to generate a larger dataset of 500,000 rows
# generate(500_000, "./sales_data/sales_data.csv")

# Uncomment to save the data to a different location (useful for Windows WSL environments)
# generate(100_000, "/mnt/d/sales_data/sales_data.csv")