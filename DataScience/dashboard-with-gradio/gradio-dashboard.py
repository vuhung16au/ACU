import gradio as gr  # Gradio is a Python library for creating customizable UI components for ML models and data
import pandas as pd  # Pandas for data manipulation and analysis
import matplotlib.pyplot as plt  # Matplotlib for creating visualizations
import datetime  # For handling date and time operations
import warnings  # To manage warning messages
import os  # For operating system related operations like file paths
import tempfile  # For creating temporary files for plots
from cachetools import cached, TTLCache  # For caching function results to improve performance

# Suppress FutureWarning messages from seaborn to keep the console output clean
warnings.filterwarnings("ignore", category=FutureWarning, module="seaborn")

# Global variable to hold the CSV data loaded into a pandas DataFrame
csv_data = None  # Global variable to store the loaded dataset

def load_csv_data():
    """
    Function to load the CSV data into a global pandas DataFrame.
    Specifies data types for columns and handles date parsing.
    """
    global csv_data
    
    # Optional: specify column dtypes if known; adjust as necessary
    # This improves performance and ensures correct data typing
    dtype_dict = {
        "order_id": "Int64",         # Using Int64 to handle potential NaN values in integer columns
        "customer_id": "Int64",
        "product_id": "Int64",
        "quantity": "Int64",
        "price": "float",
        "total": "float",
        "customer_name": "string",
        "product_names": "string",
        "categories": "string"
    }
    
    # Load the CSV file with appropriate settings
    csv_data = pd.read_csv(
        "./sales_data/sales_data.csv",
        parse_dates=["order_date"],  # Automatically parse order_date column as datetime
        dayfirst=True,               # Specify that dates are in DD/MM/YYYY format
        low_memory=False,            # Avoid potential mixed-type inference warnings
        dtype=dtype_dict             # Apply our predefined data types (may show type hint error but works at runtime)
    )
    # Ensure order_date is properly converted to datetime format
    csv_data['order_date'] = pd.to_datetime(csv_data['order_date'])

# Load the data when the script starts
load_csv_data()

# Create a cache with a Time-To-Live (TTL) of 300 seconds (5 minutes)
# This improves performance by storing results of expensive function calls
cache = TTLCache(maxsize=128, ttl=300)

@cached(cache)
def get_unique_categories():
    """
    Return a sorted list of unique categories from the data.
    Results are cached for performance.
    
    Returns:
        list: Sorted list of unique categories, capitalized
    """
    global csv_data
    if csv_data is None:
        return []
    # Extract unique categories, drop any NaN values, and convert to a list
    cats = sorted(csv_data['categories'].dropna().unique().tolist())
    # Capitalize each category for consistent display
    cats = [cat.capitalize() for cat in cats]
    return cats

def get_date_range():
    """
    Get the minimum and maximum dates from the dataset to set range filters.
    
    Returns:
        tuple: (min_date, max_date) or (None, None) if data is not available
    """
    global csv_data
    if csv_data is None or csv_data.empty:
        return None, None
    # Return the earliest and latest dates in the dataset
    return csv_data['order_date'].min(), csv_data['order_date'].max()

def filter_data(start_date, end_date, category):
    """
    Filter the dataset based on date range and category selection.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category (or "All Categories")
    
    Returns:
        DataFrame: Filtered pandas DataFrame
    """
    global csv_data
    
    # Return empty DataFrame if csv_data is None
    if csv_data is None:
        return pd.DataFrame()

    # Convert string dates to datetime objects if necessary
    if isinstance(start_date, str):
        start_date = datetime.datetime.strptime(start_date, '%Y-%m-%d').date()
    if isinstance(end_date, str):
        end_date = datetime.datetime.strptime(end_date, '%Y-%m-%d').date()

    # Filter data by date range
    df = csv_data.loc[
        (csv_data['order_date'] >= pd.to_datetime(start_date)) &
        (csv_data['order_date'] <= pd.to_datetime(end_date))
    ].copy()  # Use copy() to avoid SettingWithCopyWarning

    # If a specific category is selected (not "All Categories"), filter by that category
    if category != "All Categories":
        df = df.loc[df['categories'].str.capitalize() == category].copy()

    return df

def get_dashboard_stats(start_date, end_date, category):
    """
    Calculate key statistics for the dashboard based on filtered data.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
    
    Returns:
        tuple: (total_revenue, total_orders, avg_order_value, top_category)
    """
    # Get filtered data based on parameters
    df = filter_data(start_date, end_date, category)
    if df.empty:
        return (0, 0, 0, "N/A")  # Return default values if no data matches the filters

    # Calculate revenue as price times quantity
    df['revenue'] = df['price'] * df['quantity']
    
    # Calculate key metrics
    total_revenue = df['revenue'].sum()  # Sum of all revenue
    total_orders = df['order_id'].nunique()  # Count of unique orders
    # Calculate average order value (avoid division by zero)
    avg_order_value = total_revenue / total_orders if total_orders else 0

    # Find the top-performing category by revenue
    cat_revenues = df.groupby('categories')['revenue'].sum().sort_values(ascending=False)
    top_category = cat_revenues.index[0] if not cat_revenues.empty else "N/A"

    # Return the capitalized top category or the original value if it's "N/A"
    return (total_revenue, total_orders, avg_order_value, 
            top_category.capitalize() if isinstance(top_category, str) and top_category != "N/A" else top_category)

def get_data_for_table(start_date, end_date, category):
    """
    Prepare data for display in the data table component.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
    
    Returns:
        DataFrame: Processed pandas DataFrame ready for display
    """
    df = filter_data(start_date, end_date, category)
    if df.empty:
        return pd.DataFrame()  # Return empty DataFrame if no data matches filters

    # Sort the data by order_id (ascending) and order_date (descending)
    df = df.sort_values(by=["order_id", "order_date"], ascending=[True, False]).copy() if not df.empty else df

    # Define desired column order for the table display
    columns_order = [
        "order_id", "order_date", "customer_id", "customer_name",
        "product_id", "product_names", "categories", "quantity",
        "price", "total"
    ]
    
    # Filter to include only columns that exist in the dataframe
    columns_order = [col for col in columns_order if col in df.columns]
    df = df[columns_order].copy()

    # Calculate revenue for each row
    df['revenue'] = df['price'] * df['quantity']
    return df

def get_plot_data(start_date, end_date, category):
    """
    Prepare data for the revenue over time plot.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
    
    Returns:
        DataFrame: Processed pandas DataFrame with revenue aggregated by date
    """
    df = filter_data(start_date, end_date, category)
    if df.empty:
        return pd.DataFrame()
    
    # Calculate revenue
    df['revenue'] = df['price'] * df['quantity']
    
    # Group by date and sum revenues for each day
    plot_data = df.groupby(df['order_date'].dt.date)['revenue'].sum().reset_index()
    
    # Rename the column for clarity
    plot_data.rename(columns={'order_date': 'date'}, inplace=True)
    return plot_data

def get_revenue_by_category(start_date, end_date, category):
    """
    Prepare data for the revenue by category plot.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
    
    Returns:
        DataFrame: Processed pandas DataFrame with revenue aggregated by category
    """
    df = filter_data(start_date, end_date, category)
    if df.empty:
        return pd.DataFrame()
    
    # Calculate revenue
    df['revenue'] = df['price'] * df['quantity']
    
    # Group by category and sum revenues
    cat_data = df.groupby('categories')['revenue'].sum().reset_index()
    
    # Sort from highest to lowest revenue
    cat_data = cat_data.sort_values(by='revenue', ascending=False)
    return cat_data

def get_top_products(start_date, end_date, category):
    """
    Prepare data for the top products plot.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
    
    Returns:
        DataFrame: Processed pandas DataFrame with top 10 products by revenue
    """
    df = filter_data(start_date, end_date, category)
    if df.empty:
        return pd.DataFrame()
    
    # Calculate revenue
    df['revenue'] = df['price'] * df['quantity']
    
    # Group by product names and sum revenues
    prod_data = df.groupby('product_names')['revenue'].sum().reset_index()
    
    # Sort from highest to lowest revenue and take top 10
    prod_data = prod_data.sort_values(by='revenue', ascending=False).head(10)
    return prod_data

def create_matplotlib_figure(data, x_col, y_col, title, xlabel, ylabel, orientation='v'):
    """
    Create a matplotlib figure and save it to a temporary file.
    
    Args:
        data: DataFrame with data to plot
        x_col: Column name for X axis
        y_col: Column name for Y axis
        title: Plot title
        xlabel: X axis label
        ylabel: Y axis label
        orientation: 'v' for vertical bars, 'h' for horizontal bars
    
    Returns:
        str: Path to the saved plot image
    """
    # Create a figure with specified dimensions
    plt.figure(figsize=(10, 6))
    
    if data.empty:
        # If no data is available, display a message in the plot
        plt.text(0.5, 0.5, 'No data available', ha='center', va='center')
    else:
        # Create either a vertical or horizontal bar chart based on orientation
        if orientation == 'v':
            plt.bar(data[x_col], data[y_col])
            plt.xticks(rotation=45, ha='right')  # Rotate labels for better readability
        else:
            plt.barh(data[x_col], data[y_col])
            plt.gca().invert_yaxis()  # Invert Y axis to show highest value at top

    # Set plot title and axis labels
    plt.title(title)
    plt.xlabel(xlabel)
    plt.ylabel(ylabel)
    plt.tight_layout()  # Adjust layout to make room for labels

    # Save the plot to a temporary file
    with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
        plt.savefig(tmpfile.name)
    plt.close()  # Close the figure to free memory
    return tmpfile.name

def update_dashboard(start_date, end_date, category):
    """
    Main function to update all dashboard components when filters change.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
    
    Returns:
        tuple: Paths to plot images and data for all dashboard components
    """
    # Get key statistics
    total_revenue, total_orders, avg_order_value, top_category = get_dashboard_stats(start_date, end_date, category)

    # Generate data for each plot
    revenue_data = get_plot_data(start_date, end_date, category)
    category_data = get_revenue_by_category(start_date, end_date, category)
    top_products_data = get_top_products(start_date, end_date, category)

    # Create the three main plots
    revenue_over_time_path = create_matplotlib_figure(
        revenue_data, 'date', 'revenue',
        "Revenue Over Time", "Date", "Revenue"
    )
    revenue_by_category_path = create_matplotlib_figure(
        category_data, 'categories', 'revenue',
        "Revenue by Category", "Category", "Revenue"
    )
    top_products_path = create_matplotlib_figure(
        top_products_data, 'product_names', 'revenue',
        "Top Products", "Revenue", "Product Name", orientation='h'
    )

    # Get data for the data table
    table_data = get_data_for_table(start_date, end_date, category)

    # Return all components that need to be updated
    return (
        revenue_over_time_path,
        revenue_by_category_path,
        top_products_path,
        table_data,
        total_revenue,
        total_orders,
        avg_order_value,
        top_category
    )

def create_dashboard():
    """
    Create and configure the Gradio dashboard interface.
    
    Returns:
        Gradio Blocks: The configured dashboard interface
    """
    # Get date range for default filter values
    min_date, max_date = get_date_range()
    if min_date is None or max_date is None:
        # Use current date as fallback if data is not available
        min_date = datetime.datetime.now()
        max_date = datetime.datetime.now()

    # Convert dates to datetime format for Gradio components
    default_start_date = pd.to_datetime(min_date)
    default_end_date = pd.to_datetime(max_date)

    # Create Gradio Blocks interface with custom CSS
    with gr.Blocks(css="""
        footer {display: none !important;}  /* Hide the footer */
        .tabs {border: none !important;}    /* Remove borders from tabs */
        .gr-plot {border: none !important; box-shadow: none !important;}  /* Remove borders from plots */
    """) as dashboard:
        
        # Dashboard title
        gr.Markdown("# Sales Performance Dashboard")

        # Filter controls row - date range and category selection
        with gr.Row():
            start_date = gr.DateTime(
                label="Start Date",
                value=default_start_date.strftime('%Y-%m-%d'),  # Default to earliest date in data
                include_time=False,  # We only need date, not time
                type="datetime"
            )
            end_date = gr.DateTime(
                label="End Date",
                value=default_end_date.strftime('%Y-%m-%d'),  # Default to latest date in data
                include_time=False,
                type="datetime"
            )
            category_filter = gr.Dropdown(
                choices=["All Categories"] + get_unique_categories(),  # Add "All Categories" option
                label="Category",
                value="All Categories"  # Default to showing all categories
            )

        # Section title for key metrics
        gr.Markdown("# Key Metrics")

        # Key metrics display row
        with gr.Row():
            total_revenue = gr.Number(label="Total Revenue", value=0)
            total_orders = gr.Number(label="Total Orders", value=0)
            avg_order_value = gr.Number(label="Average Order Value", value=0)
            top_category = gr.Textbox(label="Top Category", value="N/A")

        # Section title for visualizations
        gr.Markdown("# Visualisations")
        
        # Tabs for different plots
        with gr.Tabs():
            with gr.Tab("Revenue Over Time"):
                revenue_over_time_image = gr.Image(label="Revenue Over Time", container=False)
            with gr.Tab("Revenue by Category"):
                revenue_by_category_image = gr.Image(label="Revenue by Category", container=False)
            with gr.Tab("Top Products"):
                top_products_image = gr.Image(label="Top Products", container=False)

        # Section title for raw data table
        gr.Markdown("# Raw Data")
        
        # Data table for displaying filtered dataset
        data_table = gr.DataFrame(
            label="Sales Data",
            type="pandas",  # Use pandas DataFrame type
            interactive=False  # Make it read-only
        )

        # Set up event handlers for when filters change
        for f in [start_date, end_date, category_filter]:
            f.change(
                fn=lambda s, e, c: update_dashboard(s, e, c),  # Update dashboard when filter changes
                inputs=[start_date, end_date, category_filter],  # Input components
                outputs=[
                    revenue_over_time_image, 
                    revenue_by_category_image, 
                    top_products_image,
                    data_table,
                    total_revenue, 
                    total_orders,
                    avg_order_value, 
                    top_category
                ]  # Output components to update
            )

        # Initialize the dashboard with default values when it loads
        dashboard.load(
            fn=lambda: update_dashboard(default_start_date, default_end_date, "All Categories"),
            outputs=[
                revenue_over_time_image, 
                revenue_by_category_image, 
                top_products_image,
                data_table,
                total_revenue, 
                total_orders,
                avg_order_value, 
                top_category
            ]
        )

    return dashboard

# Entry point for the application
if __name__ == "__main__":
    dashboard = create_dashboard()  # Create the dashboard
    dashboard.launch(share=False)   # Launch the dashboard locally (not shared publicly)

