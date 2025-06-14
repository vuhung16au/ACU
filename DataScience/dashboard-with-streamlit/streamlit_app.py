import streamlit as st  # Streamlit for creating web applications
import pandas as pd  # Pandas for data manipulation and analysis
import matplotlib.pyplot as plt  # Matplotlib for creating visualizations
import datetime  # For handling date and time operations
import warnings  # To manage warning messages
import os  # For operating system related operations like file paths
import tempfile  # For creating temporary files for plots
from cachetools import cached, TTLCache  # For caching function results to improve performance
import plotly.express as px  # For interactive visualizations
import plotly.graph_objects as go  # For advanced plotly visualizations
from plotly.subplots import make_subplots  # For creating subplots
import seaborn as sns  # For statistical visualizations
import numpy as np  # For numerical operations

# Suppress FutureWarning messages from seaborn to keep the console output clean
warnings.filterwarnings("ignore", category=FutureWarning, module="seaborn")

# Set page configuration
st.set_page_config(
    page_title="Sales Analytics Dashboard",
    page_icon="📊",
    layout="wide",
    initial_sidebar_state="expanded"
)

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
    
    # Load the CSV file from URL with appropriate settings
    csv_data = pd.read_csv(
        "https://raw.githubusercontent.com/vuhung16au/ACU/refs/heads/main/DataScience/dashboard-with-streamlit/sales_data/sales_data.csv",
        parse_dates=["order_date"],  # Automatically parse order_date column as datetime
        dayfirst=False,              # Specify that dates are in YYYY-MM-DD format
        date_format="%Y-%m-%d",      # Provide explicit date format
        low_memory=False,            # Avoid potential mixed-type inference warnings
        dtype=dtype_dict             # Apply our predefined data types
    )
    # Ensure order_date is properly converted to datetime format
    csv_data['order_date'] = pd.to_datetime(csv_data['order_date'])

# Create a cache with a Time-To-Live (TTL) of 300 seconds (5 minutes)
cache = TTLCache(maxsize=128, ttl=300)

@cached(cache)
def get_unique_categories():
    """
    Return a sorted list of unique categories from the data.
    Results are cached for performance.
    """
    global csv_data
    if csv_data is None:
        return []
    cats = sorted(csv_data['categories'].dropna().unique().tolist())
    cats = [cat.capitalize() for cat in cats]
    return cats

def get_date_range():
    """
    Get the minimum and maximum dates from the dataset to set range filters.
    """
    global csv_data
    if csv_data is None or csv_data.empty:
        return None, None
    return csv_data['order_date'].min(), csv_data['order_date'].max()

def filter_data(start_date, end_date, category):
    """
    Filter the dataset based on date range and category selection.
    """
    global csv_data
    
    if csv_data is None:
        return pd.DataFrame()

    if isinstance(start_date, str):
        start_date = datetime.datetime.strptime(start_date, '%Y-%m-%d').date()
    if isinstance(end_date, str):
        end_date = datetime.datetime.strptime(end_date, '%Y-%m-%d').date()

    df = csv_data.loc[
        (csv_data['order_date'] >= pd.to_datetime(start_date)) &
        (csv_data['order_date'] <= pd.to_datetime(end_date))
    ].copy()

    if category != "All Categories":
        df = df.loc[df['categories'].str.capitalize() == category].copy()

    return df

def get_previous_period(start_date, end_date):
    """
    Given a start and end date, return the previous period of the same length.
    """
    if isinstance(start_date, str):
        start_date = datetime.datetime.strptime(start_date, '%Y-%m-%d').date()
    if isinstance(end_date, str):
        end_date = datetime.datetime.strptime(end_date, '%Y-%m-%d').date()
    delta = end_date - start_date
    prev_end = start_date - datetime.timedelta(days=1)
    prev_start = prev_end - delta
    return prev_start, prev_end

def get_dashboard_stats(start_date, end_date, category):
    """
    Calculate key statistics for the dashboard based on filtered data.
    """
    df = filter_data(start_date, end_date, category)
    if df.empty:
        return {
            'total_revenue': {'value': 0, 'trend': 0, 'formatted': "$0.00", 'arrow': '', 'color': 'normal'},
            'total_orders': {'value': 0, 'trend': 0, 'formatted': "0", 'arrow': '', 'color': 'normal'},
            'avg_order_value': {'value': 0, 'trend': 0, 'formatted': "$0.00", 'arrow': '', 'color': 'normal'},
            'top_category': {'value': "N/A", 'trend': 0, 'formatted': "N/A", 'arrow': '', 'color': 'normal'},
            'fulfillment_time': {'value': 0, 'trend': 0, 'formatted': "0 days", 'arrow': '', 'color': 'normal'},
        }

    df['revenue'] = (df['price'] * df['quantity']).round(2)
    total_revenue = df['revenue'].sum()
    total_orders = df['order_id'].nunique()
    avg_order_value = round(total_revenue / total_orders, 2) if total_orders else 0
    
    # Previous period calculations
    prev_start, prev_end = get_previous_period(start_date, end_date)
    prev_df = filter_data(prev_start, prev_end, category)
    
    if not prev_df.empty:
        prev_df['revenue'] = (prev_df['price'] * prev_df['quantity']).round(2)
        prev_total_revenue = prev_df['revenue'].sum()
        prev_total_orders = prev_df['order_id'].nunique()
        prev_avg_order_value = round(prev_total_revenue / prev_total_orders, 2) if prev_total_orders else 0
    else:
        prev_total_revenue = prev_total_orders = prev_avg_order_value = 0

    # Calculate trends
    def calculate_trend(current, previous):
        if previous == 0:
            return 0
        return ((current - previous) / previous) * 100

    revenue_trend = calculate_trend(total_revenue, prev_total_revenue)
    orders_trend = calculate_trend(total_orders, prev_total_orders)
    aov_trend = calculate_trend(avg_order_value, prev_avg_order_value)

    # Helper for trend color
    def get_trend_color(trend):
        if trend > 0:
            return 'normal'
        elif trend < 0:
            return 'inverse'
        return 'normal'

    return {
        'total_revenue': {
            'value': total_revenue,
            'trend': revenue_trend,
            'formatted': f"${total_revenue:,.2f}",
            'arrow': '▲' if revenue_trend > 0 else '▼',
            'color': get_trend_color(revenue_trend)
        },
        'total_orders': {
            'value': total_orders,
            'trend': orders_trend,
            'formatted': f"{total_orders:,}",
            'arrow': '▲' if orders_trend > 0 else '▼',
            'color': get_trend_color(orders_trend)
        },
        'avg_order_value': {
            'value': avg_order_value,
            'trend': aov_trend,
            'formatted': f"${avg_order_value:,.2f}",
            'arrow': '▲' if aov_trend > 0 else '▼',
            'color': get_trend_color(aov_trend)
        }
    }

def create_sales_trends_plot(start_date, end_date, category, period='month'):
    df = filter_data(start_date, end_date, category)
    if df.empty:
        st.warning("No data available for this period.")
        return None
    df['revenue'] = (df['price'] * df['quantity']).round(2)
    if period == 'day':
        df['period'] = df['order_date'].dt.date.astype(str)
    elif period == 'week':
        df['period'] = df['order_date'].dt.isocalendar().week.astype(str)
    elif period == 'month':
        df['period'] = df['order_date'].dt.strftime('%Y-%m')
    elif period == 'quarter':
        df['period'] = df['order_date'].dt.to_period('Q').astype(str)
    else:
        df['period'] = df['order_date'].dt.year.astype(str)
    trend_data = df.groupby('period').agg({
        'revenue': 'sum',
        'order_id': 'nunique',
        'customer_id': 'nunique'
    }).reset_index()
    fig = make_subplots(specs=[[{"secondary_y": True}]])
    fig.add_trace(go.Scatter(x=trend_data['period'], y=trend_data['revenue'], name="Revenue", line=dict(color='blue')), secondary_y=False)
    fig.add_trace(go.Scatter(x=trend_data['period'], y=trend_data['order_id'], name="Orders", line=dict(color='green')), secondary_y=True)
    fig.update_layout(title_text=f"Sales Trends by {period.capitalize()}", xaxis_title=f"{period.capitalize()}", height=500)
    fig.update_yaxes(title_text="Revenue ($)", secondary_y=False)
    fig.update_yaxes(title_text="Number of Orders", secondary_y=True)
    return fig

def create_yoy_comparison_plot(start_date, end_date, category, metric='revenue'):
    df = filter_data(start_date, end_date, category)
    if df.empty:
        st.warning("No data available for this period.")
        return None
    df['revenue'] = (df['price'] * df['quantity']).round(2)
    df['year'] = df['order_date'].dt.year.astype(str)
    df['month'] = df['order_date'].dt.month.astype(str)
    if metric == 'revenue':
        yoy_data = df.groupby(['year', 'month'])['revenue'].sum().reset_index()
        y_label = "Revenue ($)"
        y_col = 'revenue'
    elif metric == 'orders':
        yoy_data = df.groupby(['year', 'month'])['order_id'].nunique().reset_index()
        yoy_data.rename(columns={'order_id': 'orders'}, inplace=True)
        y_label = "Number of Orders"
        y_col = 'orders'
    else:
        yoy_data = df.groupby(['year', 'month'])['customer_id'].nunique().reset_index()
        yoy_data.rename(columns={'customer_id': 'customers'}, inplace=True)
        y_label = "Number of Customers"
        y_col = 'customers'
    fig = go.Figure()
    for year in yoy_data['year'].unique():
        year_data = yoy_data[yoy_data['year'] == year]
        fig.add_trace(go.Scatter(x=year_data['month'], y=year_data[y_col], name=str(year), mode='lines+markers'))
    fig.update_layout(title_text=f"Year-over-Year Comparison: {metric.capitalize()}", xaxis_title="Month", yaxis_title=y_label, height=500)
    return fig

def create_geo_map_visualization(start_date, end_date, category):
    df = filter_data(start_date, end_date, category)
    if df.empty:
        st.warning("No data available for this period.")
        return None
    df['revenue'] = (df['price'] * df['quantity']).round(2)
    geo_data = df.groupby('customer_location').agg({
        'revenue': 'sum',
        'order_id': 'nunique',
        'customer_id': 'nunique'
    }).reset_index()
    fig = px.choropleth(
        geo_data,
        locations='customer_location',
        locationmode='country names',
        color='revenue',
        hover_name='customer_location',
        hover_data={
            'revenue': ':$.2f',
            'order_id': ':,.0f',
            'customer_id': ':,.0f'
        },
        color_continuous_scale=px.colors.sequential.Plasma,
        title="Geographic Sales Distribution"
    )
    fig.update_layout(height=600, geo=dict(showframe=False, showcoastlines=True, projection_type='equirectangular'))
    return fig

def create_customer_heatmap(start_date, end_date, category):
    df = filter_data(start_date, end_date, category)
    if df.empty:
        st.warning("No data available for this period.")
        return None
    df['hour'] = df['order_date'].dt.hour
    df['day_of_week'] = df['order_date'].dt.day_name()
    heatmap_data = pd.pivot_table(
        df,
        values='order_id',
        index='day_of_week',
        columns='hour',
        aggfunc='count'
    )
    days_order = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday']
    heatmap_data = heatmap_data.reindex(days_order)
    fig = go.Figure(data=go.Heatmap(
        z=heatmap_data.values,
        x=heatmap_data.columns,
        y=heatmap_data.index,
        colorscale='Viridis',
        text=heatmap_data.values,
        texttemplate='%{text:.0f}',
        textfont={"size": 10}
    ))
    fig.update_layout(title_text="Customer Activity Heatmap", xaxis_title="Hour of Day", yaxis_title="Day of Week", height=500)
    return fig

def create_product_scatter_plot(start_date, end_date, category):
    df = filter_data(start_date, end_date, category)
    if df.empty:
        st.warning("No data available for this period.")
        return None
    product_data = df.groupby('product_names').agg({
        'quantity': 'sum',
        'price': 'mean',
        'order_id': 'nunique',
        'rating': 'mean'
    }).reset_index()
    fig = px.scatter(
        product_data,
        x='price',
        y='quantity',
        size='order_id',
        color='rating',
        hover_name='product_names',
        hover_data={
            'price': ':$.2f',
            'quantity': ':,.0f',
            'order_id': ':,.0f',
            'rating': ':.1f'
        },
        title="Product Performance Analysis",
        labels={
            'price': 'Average Price ($)',
            'quantity': 'Total Quantity Sold',
            'order_id': 'Number of Orders',
            'rating': 'Average Rating'
        }
    )
    fig.update_layout(height=600, coloraxis_colorbar=dict(title='Rating'))
    return fig

def create_product_mix_pie_chart(start_date, end_date, category):
    df = filter_data(start_date, end_date, category)
    if df.empty:
        st.warning("No data available for this period.")
        return None
    df['revenue'] = (df['price'] * df['quantity']).round(2)
    mix_data = df.groupby('categories')['revenue'].sum().reset_index()
    mix_data = mix_data.sort_values('revenue', ascending=False)
    fig = px.pie(
        mix_data,
        values='revenue',
        names='categories',
        title="Product Mix by Revenue",
        hover_data=['revenue'],
        labels={'revenue': 'Revenue ($)'}
    )
    fig.update_layout(height=600, showlegend=True, legend=dict(orientation="h", yanchor="bottom", y=-0.2, xanchor="center", x=0.5))
    return fig

def create_customer_segmentation_chart(start_date, end_date, category):
    df = filter_data(start_date, end_date, category)
    if df.empty:
        st.warning("No data available for this period.")
        return None
    df['revenue'] = (df['price'] * df['quantity']).round(2)
    segment_data = df.groupby('customer_segment').agg({
        'revenue': 'sum',
        'customer_id': 'nunique',
        'order_id': 'nunique'
    }).reset_index()
    fig = make_subplots(
        rows=1, cols=2,
        specs=[[{"type": "pie"}, {"type": "bar"}]],
        subplot_titles=("Customer Distribution", "Revenue by Segment")
    )
    fig.add_trace(
        go.Pie(
            labels=segment_data['customer_segment'],
            values=segment_data['customer_id'],
            name="Customer Distribution",
            textinfo='percent+label'
        ),
        row=1, col=1
    )
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
    fig.update_layout(title_text="Customer Segmentation Analysis", height=500, showlegend=False)
    return fig

def create_customer_ltv_visualization(start_date, end_date, category):
    df = filter_data(start_date, end_date, category)
    if df.empty:
        st.warning("No data available for this period.")
        return None
    df['revenue'] = (df['price'] * df['quantity']).round(2)
    customer_data = df.groupby('customer_id').agg(
        total_revenue=('revenue', 'sum'),
        order_count=('order_id', 'nunique'),
        first_order=('order_date', 'min'),
        last_order=('order_date', 'max')
    ).reset_index()
    customer_data['lifetime_days'] = (customer_data['last_order'] - customer_data['first_order']).dt.days
    customer_data['ltv'] = (customer_data['total_revenue'] / customer_data['lifetime_days']).round(2)
    fig = px.scatter(
        customer_data,
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
    fig.update_layout(xaxis_title='Customer Lifetime (Days)', yaxis_title='Total Revenue ($)', coloraxis_colorbar=dict(title='LTV ($/day)'))
    return fig

def create_dashboard():
    st.title("📊 Sales Analytics Dashboard")
    load_csv_data()
    min_date, max_date = get_date_range()
    if min_date is None or max_date is None:
        st.error("No data available. Please check your data source.")
        return
    st.sidebar.header("Filters")
    start_date = st.sidebar.date_input("Start Date", min_date, min_value=min_date, max_value=max_date)
    end_date = st.sidebar.date_input("End Date", max_date, min_value=min_date, max_value=max_date)
    categories = ["All Categories"] + get_unique_categories()
    selected_category = st.sidebar.selectbox("Select Category", categories)
    st.header("Key Performance Indicators")
    stats = get_dashboard_stats(start_date, end_date, selected_category)
    col1, col2, col3 = st.columns(3)
    with col1:
        st.metric("Total Revenue", stats['total_revenue']['formatted'], f"{stats['total_revenue']['arrow']} {abs(stats['total_revenue']['trend']):.1f}%", delta_color=stats['total_revenue']['color'])
    with col2:
        st.metric("Total Orders", stats['total_orders']['formatted'], f"{stats['total_orders']['arrow']} {abs(stats['total_orders']['trend']):.1f}%", delta_color=stats['total_orders']['color'])
    with col3:
        st.metric("Average Order Value", stats['avg_order_value']['formatted'], f"{stats['avg_order_value']['arrow']} {abs(stats['avg_order_value']['trend']):.1f}%", delta_color=stats['avg_order_value']['color'])
    tab1, tab2, tab3, tab4 = st.tabs(["Sales Trends", "Geographic Analysis", "Product Analysis", "Customer Analysis"])
    with tab1:
        st.subheader("Sales Trends")
        period = st.selectbox("Select Period", ["day", "week", "month", "quarter", "year"])
        fig = create_sales_trends_plot(start_date, end_date, selected_category, period)
        if fig:
            st.plotly_chart(fig, use_container_width=True)
        st.subheader("Year-over-Year Comparison")
        metric = st.selectbox("Select Metric", ["revenue", "orders", "customers"])
        fig = create_yoy_comparison_plot(start_date, end_date, selected_category, metric)
        if fig:
            st.plotly_chart(fig, use_container_width=True)
    with tab2:
        st.subheader("Geographic Sales Distribution")
        fig = create_geo_map_visualization(start_date, end_date, selected_category)
        if fig:
            st.plotly_chart(fig, use_container_width=True)
        st.subheader("Customer Activity Patterns")
        fig = create_customer_heatmap(start_date, end_date, selected_category)
        if fig:
            st.plotly_chart(fig, use_container_width=True)
    with tab3:
        st.subheader("Product Performance Analysis")
        fig = create_product_scatter_plot(start_date, end_date, selected_category)
        if fig:
            st.plotly_chart(fig, use_container_width=True)
        st.subheader("Product Mix Analysis")
        fig = create_product_mix_pie_chart(start_date, end_date, selected_category)
        if fig:
            st.plotly_chart(fig, use_container_width=True)
    with tab4:
        st.subheader("Customer Segmentation")
        fig = create_customer_segmentation_chart(start_date, end_date, selected_category)
        if fig:
            st.plotly_chart(fig, use_container_width=True)
        st.subheader("Customer Lifetime Value")
        fig = create_customer_ltv_visualization(start_date, end_date, selected_category)
        if fig:
            st.plotly_chart(fig, use_container_width=True)
    st.header("Detailed Data")
    df = filter_data(start_date, end_date, selected_category)
    st.dataframe(df, use_container_width=True)

if __name__ == "__main__":
    create_dashboard() 