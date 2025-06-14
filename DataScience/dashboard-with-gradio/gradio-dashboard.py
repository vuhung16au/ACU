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
        "categories": "string",
        "shipping_method": "string",
        "delivery_date": "string",
        "order_status": "string",
        "customer_segment": "string",
        "customer_location": "string",
        "customer_age": "Int64",
        "product_cost": "float",
        "discount": "float",
        "rating": "Int64",
        "sales_rep": "string",
        "campaign_id": "string",
        "store_id": "Int64",
        "store_region": "string",
        "payment_method": "string",
        "payment_status": "string",
        "profit": "float"
    }
    
    # Load the CSV file with appropriate settings
    csv_data = pd.read_csv(
        "./sales_data/sales_data.csv",
        parse_dates=["order_date"],  # Automatically parse order_date column as datetime
        dayfirst=False,              # Specify that dates are in YYYY-MM-DD format
        date_format="%Y-%m-%d",      # Provide explicit date format
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
    df['revenue'] = (df['price'] * df['quantity']).round(2)  # Round to 2 decimal places
    
    # Calculate key metrics
    total_revenue = df['revenue'].sum()  # Sum of all revenue
    total_orders = df['order_id'].nunique()  # Count of unique orders
    # Calculate average order value (avoid division by zero) and round to 2 decimal places
    avg_order_value = round(total_revenue / total_orders, 2) if total_orders else 0

    # Find the top-performing category by revenue
    cat_revenues = df.groupby('categories')['revenue'].sum().round(2).sort_values(ascending=False)
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

    # Calculate revenue for each row and round to 2 decimal places
    df['revenue'] = (df['price'] * df['quantity']).round(2)
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
    
    # Calculate revenue and round to 2 decimal places
    df['revenue'] = (df['price'] * df['quantity']).round(2)
    
    # Group by date and sum revenues for each day
    plot_data = df.groupby(df['order_date'].dt.date)['revenue'].sum().round(2).reset_index()
    
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
    
    # Calculate revenue and round to 2 decimal places
    df['revenue'] = (df['price'] * df['quantity']).round(2)
    
    # Group by category and sum revenues
    cat_data = df.groupby('categories')['revenue'].sum().round(2).reset_index()
    
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
    
    # Calculate revenue and round to 2 decimal places
    df['revenue'] = (df['price'] * df['quantity']).round(2)
    
    # Group by product names and sum revenues
    prod_data = df.groupby('product_names')['revenue'].sum().round(2).reset_index()
    
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

# Import additional libraries for new visualizations
import plotly.express as px
import plotly.graph_objects as go
from plotly.subplots import make_subplots
import seaborn as sns
import io
import base64
from PIL import Image 
import tempfile
try:
    import folium
    from folium.plugins import HeatMap
    import geopy.distance
except ImportError:
    pass  # Handle case where geospatial libraries are not installed

def get_sales_trends_by_period(start_date, end_date, category, period='month'):
    """
    Get sales trends over time, grouped by the specified period (month or quarter).
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
        period: Time period for grouping ('month' or 'quarter')
    
    Returns:
        DataFrame with data aggregated by the specified period
    """
    df = filter_data(start_date, end_date, category)
    if df.empty:
        return pd.DataFrame()
    
    # Calculate revenue
    df['revenue'] = (df['price'] * df['quantity']).round(2)
    
    # Create period columns for grouping
    df['year'] = df['order_date'].dt.year
    df['month'] = df['order_date'].dt.month
    df['quarter'] = df['order_date'].dt.quarter
    
    # Group by period and calculate metrics
    if period == 'month':
        grouped = df.groupby(['year', 'month']).agg(
            revenue=('revenue', 'sum'),
            orders=('order_id', 'nunique')
        ).reset_index()
        
        # Create readable period labels
        grouped['period'] = grouped.apply(
            lambda x: f"{int(x['year'])}-{int(x['month']):02d}", axis=1
        )
        grouped['period_for_sort'] = pd.to_datetime(
            grouped.apply(
                lambda x: f"{int(x['year'])}-{int(x['month']):02d}-01", axis=1
            ), 
            format="%Y-%m-%d"
        )
    else:  # quarter
        grouped = df.groupby(['year', 'quarter']).agg(
            revenue=('revenue', 'sum'),
            orders=('order_id', 'nunique')
        ).reset_index()
        
        # Create readable period labels
        grouped['period'] = grouped.apply(
            lambda x: f"{int(x['year'])} Q{int(x['quarter'])}", axis=1
        )
        grouped['period_for_sort'] = pd.to_datetime(
            grouped.apply(
                lambda x: f"{int(x['year'])}-{(int(x['quarter']) * 3 - 2):02d}-01", 
                axis=1
            ),
            format="%Y-%m-%d"
        )
    
    # Sort by date for proper time series display
    grouped = grouped.sort_values('period_for_sort')
    
    return grouped

def create_sales_trends_plot(start_date, end_date, category, period='month'):
    """
    Create a line chart showing sales trends over time with dual y-axis for revenue and orders.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
        period: Time period for grouping ('month' or 'quarter')
    
    Returns:
        Temporary file path to the saved plot image
    """
    trend_data = get_sales_trends_by_period(start_date, end_date, category, period)
    
    if trend_data.empty:
        with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
            plt.figure(figsize=(10, 6))
            plt.text(0.5, 0.5, 'No data available', ha='center', va='center')
            plt.title("Sales Trends Over Time")
            plt.tight_layout()
            plt.savefig(tmpfile.name)
            plt.close()
        return tmpfile.name
    
    # Create a figure with secondary y-axis
    fig = make_subplots(specs=[[{"secondary_y": True}]])
    
    # Add revenue line
    fig.add_trace(
        go.Scatter(
            x=trend_data['period'],
            y=trend_data['revenue'],
            name="Revenue",
            line=dict(color='blue', width=3)
        ),
        secondary_y=False
    )
    
    # Add orders line
    fig.add_trace(
        go.Scatter(
            x=trend_data['period'],
            y=trend_data['orders'],
            name="Orders",
            line=dict(color='red', width=3)
        ),
        secondary_y=True
    )
    
    # Set titles
    fig.update_layout(
        title_text=f"Sales Trends by {period.capitalize()}",
        xaxis_title="Time Period",
        legend=dict(orientation="h", yanchor="bottom", y=1.02, xanchor="right", x=1)
    )
    
    # Set y-axes titles
    fig.update_yaxes(title_text="Revenue", secondary_y=False)
    fig.update_yaxes(title_text="Order Count", secondary_y=True)
    
    # Save to a temporary file
    with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
        fig.write_image(tmpfile.name, width=1000, height=600)
    
    return tmpfile.name

def get_yoy_comparison_data(start_date, end_date, category, metric='revenue'):
    """
    Calculate year-over-year comparison data for the specified metric.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
        metric: The metric to compare ('revenue' or 'orders')
    
    Returns:
        DataFrame with year-over-year comparison data
    """
    df = filter_data(start_date, end_date, category)
    if df.empty:
        return pd.DataFrame()
    
    # Calculate revenue
    df['revenue'] = (df['price'] * df['quantity']).round(2)
    
    # Create month and year columns
    df['year'] = df['order_date'].dt.year
    df['month'] = df['order_date'].dt.month
    df['month_name'] = df['order_date'].dt.strftime('%b')
    
    # Group by year and month
    if metric == 'revenue':
        grouped = df.groupby(['year', 'month', 'month_name'])['revenue'].sum().reset_index()
    else:  # orders
        grouped = df.groupby(['year', 'month', 'month_name'])['order_id'].nunique().reset_index()
        grouped.rename(columns={'order_id': 'orders'}, inplace=True)
    
    # Sort by month for consistent comparison
    grouped = grouped.sort_values(['month', 'year'])
    
    return grouped

def create_yoy_comparison_plot(start_date, end_date, category, metric='revenue'):
    """
    Create a bar chart showing year-over-year comparison.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
        metric: The metric to compare ('revenue' or 'orders')
    
    Returns:
        Temporary file path to the saved plot image
    """
    yoy_data = get_yoy_comparison_data(start_date, end_date, category, metric)
    
    if yoy_data.empty:
        with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
            plt.figure(figsize=(10, 6))
            plt.text(0.5, 0.5, 'No data available', ha='center', va='center')
            plt.title(f"Year-over-Year {metric.capitalize()} Comparison")
            plt.tight_layout()
            plt.savefig(tmpfile.name)
            plt.close()
        return tmpfile.name
    
    # Create the figure
    fig = px.bar(
        yoy_data,
        x='month_name',
        y=metric,
        color='year',
        barmode='group',
        title=f"Year-over-Year {metric.capitalize()} Comparison",
        labels={'month_name': 'Month', 'year': 'Year', metric: metric.capitalize()},
        color_discrete_sequence=px.colors.qualitative.Set2
    )
    
    # Order months chronologically
    month_order = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 
                  'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
    fig.update_xaxes(categoryorder='array', categoryarray=month_order)
    
    # Save to a temporary file
    with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
        fig.write_image(tmpfile.name, width=1000, height=600)
    
    return tmpfile.name

def get_geographic_sales_data(start_date, end_date, category):
    """
    Get sales data aggregated by region for geographical visualization.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
    
    Returns:
        DataFrame with sales data aggregated by region
    """
    df = filter_data(start_date, end_date, category)
    if df.empty:
        return pd.DataFrame()
    
    # Calculate revenue
    df['revenue'] = (df['price'] * df['quantity']).round(2)
    
    # Group by region
    geo_data = df.groupby('store_region').agg(
        revenue=('revenue', 'sum'),
        orders=('order_id', 'nunique'),
        customers=('customer_id', 'nunique')
    ).reset_index()
    
    return geo_data

def create_geo_map_visualization(start_date, end_date, category):
    """
    Create a choropleth map showing sales distribution by region.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
    
    Returns:
        Temporary file path to the saved plot image
    """
    geo_data = get_geographic_sales_data(start_date, end_date, category)
    
    if geo_data.empty:
        with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
            plt.figure(figsize=(10, 6))
            plt.text(0.5, 0.5, 'No data available', ha='center', va='center')
            plt.title("Geographic Sales Distribution")
            plt.tight_layout()
            plt.savefig(tmpfile.name)
            plt.close()
        return tmpfile.name
    
    # For static image, we'll create a bar chart
    # In a real implementation, you could create an interactive map with folium
    fig = px.bar(
        geo_data,
        x='store_region',
        y='revenue',
        color='revenue',
        text='revenue',
        title="Sales Revenue by Region",
        labels={'store_region': 'Region', 'revenue': 'Revenue'},
        color_continuous_scale=px.colors.sequential.Viridis
    )
    
    # Format the text labels
    fig.update_traces(texttemplate='%{text:.2s}', textposition='outside')
    
    # Adjust layout for better readability
    fig.update_layout(uniformtext_minsize=8, uniformtext_mode='hide')
    
    # Save to a temporary file
    with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
        fig.write_image(tmpfile.name, width=1000, height=600)
    
    return tmpfile.name

def get_customer_concentration_data(start_date, end_date, category):
    """
    Get customer concentration data for heat map visualization.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
    
    Returns:
        DataFrame with customer concentration data
    """
    df = filter_data(start_date, end_date, category)
    if df.empty:
        return pd.DataFrame()
    
    # Calculate revenue
    df['revenue'] = (df['price'] * df['quantity']).round(2)
    
    # Group by customer location and segment
    concentration_data = df.groupby(['customer_location', 'customer_segment']).agg(
        revenue=('revenue', 'sum'),
        orders=('order_id', 'nunique'),
        customers=('customer_id', 'nunique')
    ).reset_index()
    
    # Sort by revenue for better visualization
    concentration_data = concentration_data.sort_values('revenue', ascending=False).head(20)
    
    return concentration_data

def create_customer_heatmap(start_date, end_date, category):
    """
    Create a heat map showing customer concentration by location and segment.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
    
    Returns:
        Temporary file path to the saved plot image
    """
    concentration_data = get_customer_concentration_data(start_date, end_date, category)
    
    if concentration_data.empty:
        with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
            plt.figure(figsize=(10, 6))
            plt.text(0.5, 0.5, 'No data available', ha='center', va='center')
            plt.title("Customer Concentration Heat Map")
            plt.tight_layout()
            plt.savefig(tmpfile.name)
            plt.close()
        return tmpfile.name
    
    # Pivot the data for heatmap
    pivot_data = concentration_data.pivot_table(
        index='customer_location', 
        columns='customer_segment',
        values='revenue',
        aggfunc='sum'
    ).fillna(0)
    
    # Create heatmap using Seaborn
    plt.figure(figsize=(12, 10))
    sns.heatmap(
        pivot_data, 
        annot=True, 
        fmt='.0f',
        cmap='viridis',
        linewidths=0.5,
        cbar_kws={'label': 'Revenue'}
    )
    plt.title('Customer Concentration: Revenue by Location and Segment')
    plt.tight_layout()
    
    # Save to a temporary file
    with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
        plt.savefig(tmpfile.name)
        plt.close()
    
    return tmpfile.name

def get_product_performance_data(start_date, end_date, category):
    """
    Get product performance data for scatter plot visualization.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
    
    Returns:
        DataFrame with product performance metrics
    """
    df = filter_data(start_date, end_date, category)
    if df.empty:
        return pd.DataFrame()
    
    # Calculate metrics for each product
    product_data = df.groupby('product_names').agg(
        sales_volume=('quantity', 'sum'),
        revenue=('total', 'sum'),
        cost=('product_cost', 'mean'),
        rating=('rating', 'mean')
    ).reset_index()
    
    # Calculate profit margin
    product_data['profit'] = product_data['revenue'] - (product_data['cost'] * product_data['sales_volume'])
    product_data['profit_margin'] = (product_data['profit'] / product_data['revenue'] * 100).round(1)
    
    return product_data

def create_product_scatter_plot(start_date, end_date, category):
    """
    Create a scatter plot comparing product sales volume vs. profit margin.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
    
    Returns:
        Temporary file path to the saved plot image
    """
    product_data = get_product_performance_data(start_date, end_date, category)
    
    if product_data.empty:
        with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
            plt.figure(figsize=(10, 6))
            plt.text(0.5, 0.5, 'No data available', ha='center', va='center')
            plt.title("Product Performance: Sales Volume vs. Profit Margin")
            plt.tight_layout()
            plt.savefig(tmpfile.name)
            plt.close()
        return tmpfile.name
    
    # Create scatter plot
    fig = px.scatter(
        product_data,
        x='sales_volume',
        y='profit_margin',
        size='revenue',
        color='rating',
        hover_name='product_names',
        # text='product_names',  # Removed to prevent text overlay
        title='Product Performance: Sales Volume vs. Profit Margin',
        labels={
            'sales_volume': 'Sales Volume (Units)',
            'profit_margin': 'Profit Margin (%)',
            'revenue': 'Revenue',
            'rating': 'Avg. Rating'
        },
        color_continuous_scale=px.colors.sequential.Viridis,
        size_max=50
    )
    
    # Customize the layout
    fig.update_layout(
        xaxis=dict(title='Sales Volume (Units)'),
        yaxis=dict(title='Profit Margin (%)'),
        coloraxis_colorbar=dict(title='Avg. Rating')
    )
    
    # Remove textposition update since no text is shown
    # fig.update_traces(textposition='top center', textfont_size=10)
    
    # Save to a temporary file
    with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
        fig.write_image(tmpfile.name, width=1000, height=600)
    
    return tmpfile.name

def get_product_mix_data(start_date, end_date, category):
    """
    Get product mix data for pie chart visualization.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
    
    Returns:
        DataFrame with product mix contribution to revenue
    """
    df = filter_data(start_date, end_date, category)
    if df.empty:
        return pd.DataFrame()
    
    # Calculate revenue
    df['revenue'] = (df['price'] * df['quantity']).round(2)
    
    # Group by category
    product_mix = df.groupby('categories')['revenue'].sum().reset_index()
    
    # Calculate percentage contribution
    total_revenue = product_mix['revenue'].sum()
    product_mix['percentage'] = (product_mix['revenue'] / total_revenue * 100).round(1)
    
    # Sort by revenue
    product_mix = product_mix.sort_values('revenue', ascending=False)
    
    return product_mix

def create_product_mix_pie_chart(start_date, end_date, category):
    """
    Create a pie chart showing product mix contribution to revenue.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
    
    Returns:
        Temporary file path to the saved plot image
    """
    product_mix = get_product_mix_data(start_date, end_date, category)
    
    if product_mix.empty:
        with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
            plt.figure(figsize=(10, 6))
            plt.text(0.5, 0.5, 'No data available', ha='center', va='center')
            plt.title("Product Mix Contribution to Revenue")
            plt.tight_layout()
            plt.savefig(tmpfile.name)
            plt.close()
        return tmpfile.name
    
    # Create pie chart
    fig = px.pie(
        product_mix,
        values='revenue',
        names='categories',
        title='Product Mix: Revenue Contribution by Category',
        hover_data=['percentage'],
        labels={'percentage': 'Percentage (%)'},
        color_discrete_sequence=px.colors.qualitative.Set3
    )
    
    # Customize the layout
    fig.update_traces(
        textposition='inside', 
        textinfo='percent+label',
        hovertemplate='<b>%{label}</b><br>Revenue: $%{value:,.2f}<br>Percentage: %{customdata[0]:.1f}%'
    )
    
    # Save to a temporary file
    with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
        fig.write_image(tmpfile.name, width=1000, height=600)
    
    return tmpfile.name

def get_sales_funnel_data(start_date, end_date, category):
    """
    Get sales funnel data showing conversion through sales stages.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
        
    Returns:
        DataFrame with sales funnel data
    """
    df = filter_data(start_date, end_date, category)
    if df.empty:
        return pd.DataFrame()
    
    # We'll simulate a sales funnel by counting orders in different statuses
    # In a real implementation, you would have actual funnel stage data
    
    # Define the funnel stages and their order
    stages = ['Processing', 'Shipped', 'Completed', 'Cancelled', 'Returned']
    
    # Count orders in each stage
    funnel_data = df.groupby('order_status')['order_id'].nunique().reindex(stages).fillna(0).reset_index()
    funnel_data.columns = ['stage', 'value']
    
    # Calculate conversion rates between stages
    total_orders = funnel_data['value'].sum()
    funnel_data['percentage'] = (funnel_data['value'] / total_orders * 100).round(1)
    
    return funnel_data

def create_conversion_rates_funnel(start_date, end_date, category):
    """
    Create a funnel chart showing conversion rates through sales stages.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
        
    Returns:
        Temporary file path to the saved plot image
    """
    funnel_data = get_sales_funnel_data(start_date, end_date, category)
    
    if funnel_data.empty:
        with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
            plt.figure(figsize=(10, 6))
            plt.text(0.5, 0.5, 'No data available', ha='center', va='center')
            plt.title("Sales Funnel Analysis")
            plt.tight_layout()
            plt.savefig(tmpfile.name)
            plt.close()
        return tmpfile.name
    
    # Create funnel chart
    fig = go.Figure(go.Funnel(
        y=funnel_data['stage'],
        x=funnel_data['value'],
        textposition="inside",
        textinfo="value+percent initial",
        opacity=0.8,
        marker={"color": px.colors.sequential.Viridis},
        connector={"line": {"color": "royalblue", "dash": "solid", "width": 3}}
    ))
    
    # Update layout
    fig.update_layout(
        title_text="Sales Funnel Analysis: Conversion Through Stages",
        font_size=14
    )
    
    # Save to a temporary file
    with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
        fig.write_image(tmpfile.name, width=1000, height=600)
    
    return tmpfile.name

def get_abandonment_data(start_date, end_date, category):
    """
    Get abandonment points data.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
        
    Returns:
        DataFrame with abandonment points data
    """
    df = filter_data(start_date, end_date, category)
    if df.empty:
        return pd.DataFrame()
    
    # For abandonment, we'll look at cancelled and returned orders
    # In a real implementation, you would have actual abandonment stage data
    
    # Look at cancelled and returned orders
    abandoned = df[df['order_status'].isin(['Cancelled', 'Returned'])]
    
    # Group by product category to see where abandonment occurs most
    abandonment_data = abandoned.groupby('categories').agg(
        orders=('order_id', 'nunique'),
        revenue_lost=('total', 'sum')
    ).reset_index()
    
    # Calculate percentage of total abandoned orders
    total_abandoned = abandonment_data['orders'].sum()
    if total_abandoned > 0:
        abandonment_data['percentage'] = (abandonment_data['orders'] / total_abandoned * 100).round(1)
    else:
        abandonment_data['percentage'] = 0
    
    # Sort by number of orders
    abandonment_data = abandonment_data.sort_values('orders', ascending=False)
    
    return abandonment_data

def create_abandonment_visualization(start_date, end_date, category):
    """
    Create a visualization of abandonment points.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
        
    Returns:
        Temporary file path to the saved plot image
    """
    abandonment_data = get_abandonment_data(start_date, end_date, category)
    
    if abandonment_data.empty:
        with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
            plt.figure(figsize=(10, 6))
            plt.text(0.5, 0.5, 'No data available', ha='center', va='center')
            plt.title("Abandonment Points Analysis")
            plt.tight_layout()
            plt.savefig(tmpfile.name)
            plt.close()
        return tmpfile.name
    
    # Create a figure with two subplots
    fig = make_subplots(
        rows=1, cols=2,
        specs=[[{"type": "bar"}, {"type": "pie"}]],
        subplot_titles=("Revenue Lost by Category", "Order Abandonment Distribution"),
        column_widths=[0.6, 0.4]
    )
    
    # Add bar chart for revenue lost
    fig.add_trace(
        go.Bar(
            x=abandonment_data['categories'],
            y=abandonment_data['revenue_lost'],
            text=abandonment_data['revenue_lost'].apply(lambda x: f"${x:,.2f}"),
            textposition='auto',
            name="Revenue Lost",
            marker_color=px.colors.qualitative.Set3
        ),
        row=1, col=1
    )
    
    # Add pie chart for order distribution
    fig.add_trace(
        go.Pie(
            labels=abandonment_data['categories'],
            values=abandonment_data['orders'],
            textinfo='percent+label',
            name="Order Distribution",
            marker=dict(colors=px.colors.qualitative.Set3)
        ),
        row=1, col=2
    )
    
    # Update layout
    fig.update_layout(
        title_text="Abandonment Points Analysis",
        height=600,
        showlegend=False
    )
    
    # Update x-axis for the bar chart
    fig.update_xaxes(
        title_text="Category",
        tickangle=45,
        row=1, col=1
    )
    
    # Update y-axis for the bar chart
    fig.update_yaxes(
        title_text="Revenue Lost ($)",
        row=1, col=1
    )
    
    # Save to a temporary file
    with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
        fig.write_image(tmpfile.name, width=1200, height=600)
    
    return tmpfile.name

def get_performance_vs_targets_data(start_date, end_date, category):
    """
    Get performance vs. targets data.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
        
    Returns:
        DataFrame with performance vs. targets data
    """
    df = filter_data(start_date, end_date, category)
    if df.empty:
        return pd.DataFrame()
    
    # For this demo, we'll create simulated targets since actual targets aren't in the data
    # In a real implementation, you would have actual target data
    
    # Calculate actual revenue by store region
    region_data = df.groupby('store_region').agg(
        actual_revenue=('total', 'sum'),
        actual_orders=('order_id', 'nunique')
    ).reset_index()
    
    # Create simulated targets (110% of actual as a simple demonstration)
    # In a real scenario, these would be predefined targets from your business planning
    region_data['target_revenue'] = (region_data['actual_revenue'] * 1.1).round(2)
    region_data['target_orders'] = (region_data['actual_orders'] * 1.1).round(0)
    
    # Calculate performance metrics
    region_data['revenue_performance'] = (region_data['actual_revenue'] / region_data['target_revenue'] * 100).round(1)
    region_data['orders_performance'] = (region_data['actual_orders'] / region_data['target_orders'] * 100).round(1)
    
    # Calculate variance
    region_data['revenue_variance'] = (region_data['actual_revenue'] - region_data['target_revenue']).round(2)
    region_data['orders_variance'] = (region_data['actual_orders'] - region_data['target_orders']).round(0)
    
    return region_data

def create_gauge_charts(start_date, end_date, category):
    """
    Create gauge charts showing progress toward sales goals.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
        
    Returns:
        Temporary file path to the saved plot image
    """
    performance_data = get_performance_vs_targets_data(start_date, end_date, category)
    
    if performance_data.empty:
        with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
            plt.figure(figsize=(10, 6))
            plt.text(0.5, 0.5, 'No data available', ha='center', va='center')
            plt.title("Performance vs. Targets: Gauge Charts")
            plt.tight_layout()
            plt.savefig(tmpfile.name)
            plt.close()
        return tmpfile.name
    
    # Calculate overall performance
    total_actual_revenue = performance_data['actual_revenue'].sum()
    total_target_revenue = performance_data['target_revenue'].sum()
    total_revenue_performance = (total_actual_revenue / total_target_revenue * 100).round(1)
    
    total_actual_orders = performance_data['actual_orders'].sum()
    total_target_orders = performance_data['target_orders'].sum()
    total_orders_performance = (total_actual_orders / total_target_orders * 100).round(1)
    
    # Create a figure with two gauge charts
    fig = make_subplots(
        rows=1, cols=2,
        specs=[[{"type": "indicator"}, {"type": "indicator"}]],
        subplot_titles=("Revenue Performance", "Orders Performance")
    )
    
    # Add revenue gauge
    fig.add_trace(
        go.Indicator(
            mode="gauge+number+delta",
            value=total_revenue_performance,
            title={"text": "Revenue Performance", "font": {"size": 24}},
            delta={"reference": 100, "suffix": "%"},
            gauge={
                "axis": {"range": [0, 150], "tickwidth": 1, "tickcolor": "darkblue"},
                "bar": {"color": "darkblue"},
                "bgcolor": "white",
                "borderwidth": 2,
                "bordercolor": "gray",
                "steps": [
                    {"range": [0, 60], "color": "red"},
                    {"range": [60, 80], "color": "orange"},
                    {"range": [80, 100], "color": "yellow"},
                    {"range": [100, 150], "color": "green"}
                ]
            },
            number={"suffix": "%", "font": {"size": 24}}
        ),
        row=1, col=1
    )
    
    # Add orders gauge
    fig.add_trace(
        go.Indicator(
            mode="gauge+number+delta",
            value=total_orders_performance,
            title={"text": "Orders Performance", "font": {"size": 24}},
            delta={"reference": 100, "suffix": "%"},
            gauge={
                "axis": {"range": [0, 150], "tickwidth": 1, "tickcolor": "darkblue"},
                "bar": {"color": "darkblue"},
                "bgcolor": "white",
                "borderwidth": 2,
                "bordercolor": "gray",
                "steps": [
                    {"range": [0, 60], "color": "red"},
                    {"range": [60, 80], "color": "orange"},
                    {"range": [80, 100], "color": "yellow"},
                    {"range": [100, 150], "color": "green"}
                ]
            },
            number={"suffix": "%", "font": {"size": 24}}
        ),
        row=1, col=2
    )
    
    # Update layout
    fig.update_layout(
        title_text="Performance vs. Targets: Progress Toward Goals",
        height=500
    )
    
    # Save to a temporary file
    with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
        fig.write_image(tmpfile.name, width=1000, height=500)
    
    return tmpfile.name

def create_variance_analysis(start_date, end_date, category):
    """
    Create a variance analysis chart (actual vs. forecast).
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
        
    Returns:
        Temporary file path to the saved plot image
    """
    performance_data = get_performance_vs_targets_data(start_date, end_date, category)
    
    if performance_data.empty:
        with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
            plt.figure(figsize=(10, 6))
            plt.text(0.5, 0.5, 'No data available', ha='center', va='center')
            plt.title("Variance Analysis: Actual vs. Forecast")
            plt.tight_layout()
            plt.savefig(tmpfile.name)
            plt.close()
        return tmpfile.name
    
    # Create a figure with two subplots
    fig = make_subplots(
        rows=2, cols=1,
        subplot_titles=("Revenue: Actual vs. Target by Region", "Orders: Actual vs. Target by Region"),
        vertical_spacing=0.15
    )
    
    # Add revenue comparison
    fig.add_trace(
        go.Bar(
            name="Actual Revenue",
            x=performance_data['store_region'],
            y=performance_data['actual_revenue'],
            marker_color='blue',
            text=[f"${x:,.2f}" for x in performance_data['actual_revenue']],
            textposition='auto'
        ),
        row=1, col=1
    )
    
    fig.add_trace(
        go.Bar(
            name="Target Revenue",
            x=performance_data['store_region'],
            y=performance_data['target_revenue'],
            marker_color='lightblue',
            text=[f"${x:,.2f}" for x in performance_data['target_revenue']],
            textposition='auto'
        ),
        row=1, col=1
    )
    
    # Add orders comparison
    fig.add_trace(
        go.Bar(
            name="Actual Orders",
            x=performance_data['store_region'],
            y=performance_data['actual_orders'],
            marker_color='green',
            text=performance_data['actual_orders'],
            textposition='auto'
        ),
        row=2, col=1
    )
    
    fig.add_trace(
        go.Bar(
            name="Target Orders",
            x=performance_data['store_region'],
            y=performance_data['target_orders'],
            marker_color='lightgreen',
            text=performance_data['target_orders'],
            textposition='auto'
        ),
        row=2, col=1
    )
    
    # Update layout
    fig.update_layout(
        title_text="Variance Analysis: Actual vs. Target",
        height=800,
        barmode='group'
    )
    
    # Update axes titles
    fig.update_xaxes(title_text="Region", row=1, col=1)
    fig.update_xaxes(title_text="Region", row=2, col=1)
    
    fig.update_yaxes(title_text="Revenue ($)", row=1, col=1)
    fig.update_yaxes(title_text="Orders Count", row=2, col=1)
    
    # Save to a temporary file
    with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
        fig.write_image(tmpfile.name, width=1000, height=800)
    
    return tmpfile.name

def get_customer_segmentation_data(start_date, end_date, category):
    """
    Get customer segmentation data for visualization.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
        
    Returns:
        DataFrame with customer segmentation data
    """
    df = filter_data(start_date, end_date, category)
    if df.empty:
        return pd.DataFrame()
    
    # Calculate revenue
    df['revenue'] = (df['price'] * df['quantity']).round(2)
    
    # Group by customer segment
    segment_data = df.groupby('customer_segment').agg(
        revenue=('revenue', 'sum'),
        customers=('customer_id', 'nunique'),
        orders=('order_id', 'nunique')
    ).reset_index()
    
    # Calculate average order value per segment
    segment_data['avg_order_value'] = (segment_data['revenue'] / segment_data['orders']).round(2)
    
    return segment_data

def create_customer_segmentation_chart(start_date, end_date, category):
    """
    Create a visualization of customer segmentation.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
        
    Returns:
        Temporary file path to the saved plot image
    """
    segment_data = get_customer_segmentation_data(start_date, end_date, category)
    
    if segment_data.empty:
        with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
            plt.figure(figsize=(10, 6))
            plt.text(0.5, 0.5, 'No data available', ha='center', va='center')
            plt.title("Customer Segmentation Analysis")
            plt.tight_layout()
            plt.savefig(tmpfile.name)
            plt.close()
        return tmpfile.name
    
    # Create a figure with two subplots
    fig = make_subplots(
        rows=1, cols=2,
        specs=[[{"type": "pie"}, {"type": "bar"}]],
        subplot_titles=("Customer Distribution", "Revenue by Segment")
    )
    
    # Add pie chart for customer distribution
    fig.add_trace(
        go.Pie(
            labels=segment_data['customer_segment'],
            values=segment_data['customers'],
            name="Customer Distribution",
            textinfo='percent+label'
        ),
        row=1, col=1
    )
    
    # Add bar chart for revenue by segment
    fig.add_trace(
        go.Bar(
            x=segment_data['customer_segment'],
            y=segment_data['revenue'],
            text=[f"${x:,.2f}" for x in segment_data['revenue']],
            textposition='auto',
            name="Revenue"
        ),
        row=1, col=2
    )
    
    # Update layout
    fig.update_layout(
        title_text="Customer Segmentation Analysis",
        height=500,
        showlegend=False
    )
    
    # Save to a temporary file
    with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
        fig.write_image(tmpfile.name, width=1000, height=500)
    
    return tmpfile.name

def get_customer_ltv_data(start_date, end_date, category):
    """
    Get customer lifetime value data for visualization.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
        
    Returns:
        DataFrame with customer LTV data
    """
    df = filter_data(start_date, end_date, category)
    if df.empty:
        return pd.DataFrame()
    
    # Calculate revenue
    df['revenue'] = (df['price'] * df['quantity']).round(2)
    
    # Group by customer
    customer_data = df.groupby('customer_id').agg(
        total_revenue=('revenue', 'sum'),
        order_count=('order_id', 'nunique'),
        first_order=('order_date', 'min'),
        last_order=('order_date', 'max')
    ).reset_index()
    
    # Calculate customer lifetime in days
    customer_data['lifetime_days'] = (customer_data['last_order'] - customer_data['first_order']).dt.days
    
    # Calculate LTV (average revenue per day * lifetime)
    customer_data['ltv'] = (customer_data['total_revenue'] / customer_data['lifetime_days']).round(2)
    
    return customer_data

def create_customer_ltv_visualization(start_date, end_date, category):
    """
    Create a visualization of customer lifetime value analysis.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
        
    Returns:
        Temporary file path to the saved plot image
    """
    ltv_data = get_customer_ltv_data(start_date, end_date, category)
    
    if ltv_data.empty:
        with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
            plt.figure(figsize=(10, 6))
            plt.text(0.5, 0.5, 'No data available', ha='center', va='center')
            plt.title("Customer Lifetime Value Analysis")
            plt.tight_layout()
            plt.savefig(tmpfile.name)
            plt.close()
        return tmpfile.name
    
    # Create scatter plot
    fig = px.scatter(
        ltv_data,
        x='lifetime_days',
        y='total_revenue',
        size='order_count',
        color='ltv',
        title='Customer Lifetime Value Analysis',
        labels={
            'lifetime_days': 'Customer Lifetime (Days)',
            'total_revenue': 'Total Revenue ($)',
            'order_count': 'Number of Orders',
            'ltv': 'LTV ($/day)'
        },
        color_continuous_scale=px.colors.sequential.Viridis
    )
    
    # Update layout
    fig.update_layout(
        xaxis_title='Customer Lifetime (Days)',
        yaxis_title='Total Revenue ($)',
        coloraxis_colorbar=dict(title='LTV ($/day)')
    )
    
    # Save to a temporary file
    with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
        fig.write_image(tmpfile.name, width=1000, height=600)
    
    return tmpfile.name

def get_retention_churn_data(start_date, end_date, category):
    """
    Get customer retention and churn data for visualization.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
        
    Returns:
        DataFrame with retention and churn data
    """
    df = filter_data(start_date, end_date, category)
    if df.empty:
        return pd.DataFrame()
    
    # Calculate revenue
    df['revenue'] = (df['price'] * df['quantity']).round(2)
    
    # Group by customer and month
    df['month'] = df['order_date'].dt.to_period('M')
    monthly_data = df.groupby(['customer_id', 'month']).agg(
        revenue=('revenue', 'sum'),
        orders=('order_id', 'nunique')
    ).reset_index()
    
    # Calculate retention and churn
    retention_data = monthly_data.groupby('month').agg(
        active_customers=('customer_id', 'nunique'),
        total_revenue=('revenue', 'sum')
    ).reset_index()
    
    # Calculate month-over-month retention rate
    retention_data['retention_rate'] = retention_data['active_customers'].pct_change() * 100
    
    return retention_data

def create_retention_churn_analysis(start_date, end_date, category):
    """
    Create a visualization of customer retention and churn analysis.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
        
    Returns:
        Temporary file path to the saved plot image
    """
    retention_data = get_retention_churn_data(start_date, end_date, category)
    
    if retention_data.empty:
        with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
            plt.figure(figsize=(10, 6))
            plt.text(0.5, 0.5, 'No data available', ha='center', va='center')
            plt.title("Customer Retention and Churn Analysis")
            plt.tight_layout()
            plt.savefig(tmpfile.name)
            plt.close()
        return tmpfile.name
    
    # Create a figure with two subplots
    fig = make_subplots(
        rows=2, cols=1,
        subplot_titles=("Active Customers Over Time", "Retention Rate Trend"),
        vertical_spacing=0.15
    )
    
    # Add active customers line
    fig.add_trace(
        go.Scatter(
            x=retention_data['month'].astype(str),
            y=retention_data['active_customers'],
            mode='lines+markers',
            name='Active Customers',
            line=dict(color='blue', width=2)
        ),
        row=1, col=1
    )
    
    # Add retention rate line
    fig.add_trace(
        go.Scatter(
            x=retention_data['month'].astype(str),
            y=retention_data['retention_rate'],
            mode='lines+markers',
            name='Retention Rate',
            line=dict(color='green', width=2)
        ),
        row=2, col=1
    )
    
    # Update layout
    fig.update_layout(
        title_text="Customer Retention and Churn Analysis",
        height=800,
        showlegend=True
    )
    
    # Update axes titles
    fig.update_xaxes(title_text="Month", row=1, col=1)
    fig.update_xaxes(title_text="Month", row=2, col=1)
    
    fig.update_yaxes(title_text="Number of Active Customers", row=1, col=1)
    fig.update_yaxes(title_text="Retention Rate (%)", row=2, col=1)
    
    # Save to a temporary file
    with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
        fig.write_image(tmpfile.name, width=1000, height=800)
    
    return tmpfile.name

def get_sales_rep_performance_data(start_date, end_date, category):
    """
    Get sales representative performance data for visualization.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
        
    Returns:
        DataFrame with sales rep performance data
    """
    df = filter_data(start_date, end_date, category)
    if df.empty:
        return pd.DataFrame()
    
    # Calculate revenue
    df['revenue'] = (df['price'] * df['quantity']).round(2)
    
    # Group by sales representative
    rep_data = df.groupby('sales_rep').agg(
        revenue=('revenue', 'sum'),
        orders=('order_id', 'nunique'),
        customers=('customer_id', 'nunique')
    ).reset_index()
    
    # Calculate average order value
    rep_data['avg_order_value'] = (rep_data['revenue'] / rep_data['orders']).round(2)
    
    return rep_data

def create_individual_performance_comparison(start_date, end_date, category):
    """
    Create a visualization comparing individual sales representative performance.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
        
    Returns:
        Temporary file path to the saved plot image
    """
    rep_data = get_sales_rep_performance_data(start_date, end_date, category)
    
    if rep_data.empty:
        with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
            plt.figure(figsize=(10, 6))
            plt.text(0.5, 0.5, 'No data available', ha='center', va='center')
            plt.title("Individual Sales Rep Performance")
            plt.tight_layout()
            plt.savefig(tmpfile.name)
            plt.close()
        return tmpfile.name
    
    # Create a figure with two subplots
    fig = make_subplots(
        rows=1, cols=2,
        specs=[[{"type": "bar"}, {"type": "bar"}]],
        subplot_titles=("Revenue by Sales Rep", "Orders by Sales Rep")
    )
    
    # Add revenue bars
    fig.add_trace(
        go.Bar(
            x=rep_data['sales_rep'],
            y=rep_data['revenue'],
            text=[f"${x:,.2f}" for x in rep_data['revenue']],
            textposition='auto',
            name="Revenue"
        ),
        row=1, col=1
    )
    
    # Add orders bars
    fig.add_trace(
        go.Bar(
            x=rep_data['sales_rep'],
            y=rep_data['orders'],
            text=rep_data['orders'],
            textposition='auto',
            name="Orders"
        ),
        row=1, col=2
    )
    
    # Update layout
    fig.update_layout(
        title_text="Individual Sales Rep Performance",
        height=500,
        showlegend=False
    )
    
    # Update axes titles
    fig.update_xaxes(title_text="Sales Representative", row=1, col=1)
    fig.update_xaxes(title_text="Sales Representative", row=1, col=2)
    
    fig.update_yaxes(title_text="Revenue ($)", row=1, col=1)
    fig.update_yaxes(title_text="Number of Orders", row=1, col=2)
    
    # Save to a temporary file
    with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
        fig.write_image(tmpfile.name, width=1000, height=500)
    
    return tmpfile.name

def create_team_performance_metrics(start_date, end_date, category):
    """
    Create a visualization of team performance metrics.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
        
    Returns:
        Temporary file path to the saved plot image
    """
    rep_data = get_sales_rep_performance_data(start_date, end_date, category)
    
    if rep_data.empty:
        with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
            plt.figure(figsize=(10, 6))
            plt.text(0.5, 0.5, 'No data available', ha='center', va='center')
            plt.title("Team Performance Metrics")
            plt.tight_layout()
            plt.savefig(tmpfile.name)
            plt.close()
        return tmpfile.name
    
    # Create a figure with two subplots
    fig = make_subplots(
        rows=1, cols=2,
        specs=[[{"type": "bar"}, {"type": "bar"}]],
        subplot_titles=("Average Order Value by Rep", "Customers per Rep")
    )
    
    # Add average order value bars
    fig.add_trace(
        go.Bar(
            x=rep_data['sales_rep'],
            y=rep_data['avg_order_value'],
            text=[f"${x:,.2f}" for x in rep_data['avg_order_value']],
            textposition='auto',
            name="Avg Order Value"
        ),
        row=1, col=1
    )
    
    # Add customers per rep bars
    fig.add_trace(
        go.Bar(
            x=rep_data['sales_rep'],
            y=rep_data['customers'],
            text=rep_data['customers'],
            textposition='auto',
            name="Customers"
        ),
        row=1, col=2
    )
    
    # Update layout
    fig.update_layout(
        title_text="Team Performance Metrics",
        height=500,
        showlegend=False
    )
    
    # Update axes titles
    fig.update_xaxes(title_text="Sales Representative", row=1, col=1)
    fig.update_xaxes(title_text="Sales Representative", row=1, col=2)
    
    fig.update_yaxes(title_text="Average Order Value ($)", row=1, col=1)
    fig.update_yaxes(title_text="Number of Customers", row=1, col=2)
    
    # Save to a temporary file
    with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
        fig.write_image(tmpfile.name, width=1000, height=500)
    
    return tmpfile.name

def get_marketing_sales_data(start_date, end_date, category):
    """
    Get marketing and sales correlation data for visualization.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
        
    Returns:
        DataFrame with marketing and sales data
    """
    df = filter_data(start_date, end_date, category)
    if df.empty:
        return pd.DataFrame()
    
    # Calculate revenue
    df['revenue'] = (df['price'] * df['quantity']).round(2)
    
    # Group by campaign
    campaign_data = df.groupby('campaign_id').agg(
        revenue=('revenue', 'sum'),
        orders=('order_id', 'nunique'),
        customers=('customer_id', 'nunique')
    ).reset_index()
    
    return campaign_data

def create_marketing_sales_correlation(start_date, end_date, category):
    """
    Create a visualization of marketing spend vs. sales outcomes.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
        
    Returns:
        Temporary file path to the saved plot image
    """
    campaign_data = get_marketing_sales_data(start_date, end_date, category)
    
    if campaign_data.empty:
        with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
            plt.figure(figsize=(10, 6))
            plt.text(0.5, 0.5, 'No data available', ha='center', va='center')
            plt.title("Marketing vs. Sales Correlation")
            plt.tight_layout()
            plt.savefig(tmpfile.name)
            plt.close()
        return tmpfile.name
    
    # Create scatter plot
    fig = px.scatter(
        campaign_data,
        x='orders',
        y='revenue',
        size='customers',
        color='campaign_id',
        title='Marketing Campaign Performance',
        labels={
            'orders': 'Number of Orders',
            'revenue': 'Revenue ($)',
            'customers': 'Number of Customers',
            'campaign_id': 'Campaign'
        }
    )
    
    # Update layout
    fig.update_layout(
        xaxis_title='Number of Orders',
        yaxis_title='Revenue ($)'
    )
    
    # Save to a temporary file
    with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
        fig.write_image(tmpfile.name, width=1000, height=600)
    
    return tmpfile.name

def get_price_volume_data(start_date, end_date, category):
    """
    Get price and volume data for visualization.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
        
    Returns:
        DataFrame with price and volume data
    """
    df = filter_data(start_date, end_date, category)
    if df.empty:
        return pd.DataFrame()
    
    # Group by product
    product_data = df.groupby('product_names').agg(
        avg_price=('price', 'mean'),
        total_quantity=('quantity', 'sum'),
        revenue=('total', 'sum')
    ).reset_index()
    
    return product_data

def create_price_volume_analysis(start_date, end_date, category):
    """
    Create a visualization of price changes vs. order volume.
    
    Args:
        start_date: The starting date for filtering
        end_date: The ending date for filtering
        category: The selected category
        
    Returns:
        Temporary file path to the saved plot image
    """
    price_volume_data = get_price_volume_data(start_date, end_date, category)
    
    if price_volume_data.empty:
        with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
            plt.figure(figsize=(10, 6))
            plt.text(0.5, 0.5, 'No data available', ha='center', va='center')
            plt.title("Price vs. Volume Analysis")
            plt.tight_layout()
            plt.savefig(tmpfile.name)
            plt.close()
        return tmpfile.name
    
    # Create scatter plot
    fig = px.scatter(
        price_volume_data,
        x='avg_price',
        y='total_quantity',
        size='revenue',
        color='revenue',
        title='Price vs. Volume Analysis',
        labels={
            'avg_price': 'Average Price ($)',
            'total_quantity': 'Total Quantity Sold',
            'revenue': 'Revenue ($)'
        },
        color_continuous_scale=px.colors.sequential.Viridis
    )
    
    # Update layout
    fig.update_layout(
        xaxis_title='Average Price ($)',
        yaxis_title='Total Quantity Sold',
        coloraxis_colorbar=dict(title='Revenue ($)')
    )
    
    # Save to a temporary file
    with tempfile.NamedTemporaryFile(delete=False, suffix=".png") as tmpfile:
        fig.write_image(tmpfile.name, width=1000, height=600)
    
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

    # Create the original plots
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

    # Create new visualizations
    # Sales Trends
    sales_trends_monthly_path = create_sales_trends_plot(start_date, end_date, category, 'month')
    sales_trends_quarterly_path = create_sales_trends_plot(start_date, end_date, category, 'quarter')
    yoy_comparison_path = create_yoy_comparison_plot(start_date, end_date, category, 'revenue')
    
    # Geographic Analysis
    geo_map_path = create_geo_map_visualization(start_date, end_date, category)
    customer_heatmap_path = create_customer_heatmap(start_date, end_date, category)
    
    # Product Performance
    product_scatter_path = create_product_scatter_plot(start_date, end_date, category)
    product_mix_path = create_product_mix_pie_chart(start_date, end_date, category)
    
    # Customer Insights
    customer_segment_path = create_customer_segmentation_chart(start_date, end_date, category)
    customer_ltv_path = create_customer_ltv_visualization(start_date, end_date, category)
    retention_churn_path = create_retention_churn_analysis(start_date, end_date, category)
    
    # Funnel Analysis
    conversion_funnel_path = create_conversion_rates_funnel(start_date, end_date, category)
    abandonment_path = create_abandonment_visualization(start_date, end_date, category)
    
    # Performance vs Targets
    gauge_charts_path = create_gauge_charts(start_date, end_date, category)
    variance_analysis_path = create_variance_analysis(start_date, end_date, category)
    
    # Sales Representative Performance
    individual_performance_path = create_individual_performance_comparison(start_date, end_date, category)
    team_performance_path = create_team_performance_metrics(start_date, end_date, category)
    
    # Correlation Analysis
    marketing_sales_path = create_marketing_sales_correlation(start_date, end_date, category)
    price_volume_path = create_price_volume_analysis(start_date, end_date, category)

    # Get data for the data table
    table_data = get_data_for_table(start_date, end_date, category)

    # Return all components that need to be updated
    return (
        revenue_over_time_path,
        revenue_by_category_path,
        top_products_path,
        sales_trends_monthly_path,
        sales_trends_quarterly_path,
        yoy_comparison_path,
        geo_map_path,
        customer_heatmap_path,
        product_scatter_path,
        product_mix_path,
        customer_segment_path,
        customer_ltv_path,
        retention_churn_path,
        conversion_funnel_path,
        abandonment_path,
        gauge_charts_path,
        variance_analysis_path,
        individual_performance_path,
        team_performance_path,
        marketing_sales_path,
        price_volume_path,
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
        gr.Markdown("# Sales & Revenue Intelligence Dashboard")

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
        
        # Tabs for different visualization categories
        with gr.Tabs():
            # Original plots
            with gr.Tab("Revenue Visuals"):
                with gr.Tabs():
                    with gr.Tab("Revenue Over Time"):
                        revenue_over_time_image = gr.Image(label="Revenue Over Time", container=False)
                    with gr.Tab("Revenue by Category"):
                        revenue_by_category_image = gr.Image(label="Revenue by Category", container=False)
                    with gr.Tab("Top Products"):
                        top_products_image = gr.Image(label="Top Products", container=False)
            
            # Sales Trends Over Time
            with gr.Tab("Sales Trends"):
                with gr.Tabs():
                    with gr.Tab("Monthly Trends"):
                        sales_trends_monthly_image = gr.Image(label="Monthly Sales Trends", container=False)
                    with gr.Tab("Quarterly Trends"):
                        sales_trends_quarterly_image = gr.Image(label="Quarterly Sales Trends", container=False)
                    with gr.Tab("Year-over-Year Comparison"):
                        yoy_comparison_image = gr.Image(label="Year-over-Year Comparison", container=False)
            
            # Geographic Analysis
            with gr.Tab("Geographic Analysis"):
                with gr.Tabs():
                    with gr.Tab("Regional Distribution"):
                        geo_map_image = gr.Image(label="Sales Distribution by Region", container=False)
                    with gr.Tab("Customer Concentration"):
                        customer_heatmap_image = gr.Image(label="Customer Concentration Heat Map", container=False)
            
            # Product Performance
            with gr.Tab("Product Performance"):
                with gr.Tabs():
                    with gr.Tab("Sales vs. Margin"):
                        product_scatter_image = gr.Image(label="Product Performance: Sales Volume vs. Profit Margin", container=False)
                    with gr.Tab("Product Mix"):
                        product_mix_image = gr.Image(label="Product Mix Contribution to Revenue", container=False)
            
            # Customer Insights
            with gr.Tab("Customer Insights"):
                with gr.Tabs():
                    with gr.Tab("Customer Segmentation"):
                        customer_segment_image = gr.Image(label="Customer Segmentation: New vs. Returning", container=False)
                    with gr.Tab("Customer Lifetime Value"):
                        customer_ltv_image = gr.Image(label="Customer Lifetime Value Analysis", container=False)
                    with gr.Tab("Retention & Churn"):
                        retention_churn_image = gr.Image(label="Customer Retention and Churn Analysis", container=False)
            
            # Funnel Analysis
            with gr.Tab("Funnel Analysis"):
                with gr.Tabs():
                    with gr.Tab("Conversion Rates"):
                        conversion_funnel_image = gr.Image(label="Sales Funnel: Conversion Rates", container=False)
                    with gr.Tab("Abandonment Points"):
                        abandonment_image = gr.Image(label="Abandonment Points Analysis", container=False)
            
            # Performance vs Targets
            with gr.Tab("Performance vs Targets"):
                with gr.Tabs():
                    with gr.Tab("Progress Gauges"):
                        gauge_charts_image = gr.Image(label="Progress Toward Sales Goals", container=False)
                    with gr.Tab("Variance Analysis"):
                        variance_analysis_image = gr.Image(label="Variance Analysis: Actual vs. Target", container=False)
            
            # Sales Representative Performance
            with gr.Tab("Sales Rep Performance"):
                with gr.Tabs():
                    with gr.Tab("Individual Performance"):
                        individual_performance_image = gr.Image(label="Individual Sales Rep Performance", container=False)
                    with gr.Tab("Team Performance"):
                        team_performance_image = gr.Image(label="Team Performance Metrics", container=False)
            
            # Correlation Analysis
            with gr.Tab("Correlation Analysis"):
                with gr.Tabs():
                    with gr.Tab("Marketing vs. Sales"):
                        marketing_sales_image = gr.Image(label="Marketing Spend vs. Sales Outcomes", container=False)
                    with gr.Tab("Price vs. Volume"):
                        price_volume_image = gr.Image(label="Price Changes vs. Order Volume", container=False)

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
                    sales_trends_monthly_image,
                    sales_trends_quarterly_image,
                    yoy_comparison_image,
                    geo_map_image,
                    customer_heatmap_image,
                    product_scatter_image,
                    product_mix_image,
                    customer_segment_image,
                    customer_ltv_image,
                    retention_churn_image,
                    conversion_funnel_image,
                    abandonment_image,
                    gauge_charts_image,
                    variance_analysis_image,
                    individual_performance_image,
                    team_performance_image,
                    marketing_sales_image,
                    price_volume_image,
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
                sales_trends_monthly_image,
                sales_trends_quarterly_image,
                yoy_comparison_image,
                geo_map_image,
                customer_heatmap_image,
                product_scatter_image,
                product_mix_image,
                customer_segment_image,
                customer_ltv_image,
                retention_churn_image,
                conversion_funnel_image,
                abandonment_image,
                gauge_charts_image,
                variance_analysis_image,
                individual_performance_image,
                team_performance_image,
                marketing_sales_image,
                price_volume_image,
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

