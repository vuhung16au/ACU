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
            'customer_satisfaction': {'value': 0, 'trend': 0, 'formatted': "0.0", 'arrow': '', 'color': 'normal'},
            'profit_margin': {'value': 0, 'trend': 0, 'formatted': "0%", 'arrow': '', 'color': 'normal'},
            'repeat_customer_rate': {'value': 0, 'trend': 0, 'formatted': "0%", 'arrow': '', 'color': 'normal'}
        }

    # Calculate revenue metrics
    df['revenue'] = (df['price'] * df['quantity']).round(2)
    total_revenue = df['revenue'].sum()
    total_orders = df['order_id'].nunique()
    avg_order_value = round(total_revenue / total_orders, 2) if total_orders else 0
    
    # Calculate top category
    top_category = df.groupby('categories')['revenue'].sum().sort_values(ascending=False).index[0]
    top_category_revenue = df.groupby('categories')['revenue'].sum().max()
    
    # Calculate fulfillment time
    df['fulfillment_days'] = (pd.to_datetime(df['delivery_date']) - df['order_date']).dt.days
    avg_fulfillment_time = df['fulfillment_days'].mean()
    
    # Calculate customer satisfaction
    avg_rating = df['rating'].mean()
    
    # Calculate profit margin
    total_profit = df['profit'].sum()
    profit_margin = (total_profit / total_revenue * 100) if total_revenue else 0
    
    # Calculate repeat customer rate
    repeat_customers = df.groupby('customer_id').filter(lambda x: len(x) > 1)['customer_id'].nunique()
    total_customers = df['customer_id'].nunique()
    repeat_customer_rate = (repeat_customers / total_customers * 100) if total_customers else 0
    
    # Previous period calculations
    prev_start, prev_end = get_previous_period(start_date, end_date)
    prev_df = filter_data(prev_start, prev_end, category)
    
    if not prev_df.empty:
        prev_df['revenue'] = (prev_df['price'] * prev_df['quantity']).round(2)
        prev_total_revenue = prev_df['revenue'].sum()
        prev_total_orders = prev_df['order_id'].nunique()
        prev_avg_order_value = round(prev_total_revenue / prev_total_orders, 2) if prev_total_orders else 0
        prev_top_category = prev_df.groupby('categories')['revenue'].sum().sort_values(ascending=False).index[0]
        prev_avg_fulfillment_time = (pd.to_datetime(prev_df['delivery_date']) - prev_df['order_date']).dt.days.mean()
        prev_avg_rating = prev_df['rating'].mean()
        prev_total_profit = prev_df['profit'].sum()
        prev_profit_margin = (prev_total_profit / prev_total_revenue * 100) if prev_total_revenue else 0
        prev_repeat_customers = prev_df.groupby('customer_id').filter(lambda x: len(x) > 1)['customer_id'].nunique()
        prev_total_customers = prev_df['customer_id'].nunique()
        prev_repeat_customer_rate = (prev_repeat_customers / prev_total_customers * 100) if prev_total_customers else 0
    else:
        prev_total_revenue = prev_total_orders = prev_avg_order_value = 0
        prev_top_category = "N/A"
        prev_avg_fulfillment_time = 0
        prev_avg_rating = 0
        prev_profit_margin = 0
        prev_repeat_customer_rate = 0

    # Calculate trends
    def calculate_trend(current, previous):
        if previous == 0:
            return 0
        return ((current - previous) / previous) * 100

    revenue_trend = calculate_trend(total_revenue, prev_total_revenue)
    orders_trend = calculate_trend(total_orders, prev_total_orders)
    aov_trend = calculate_trend(avg_order_value, prev_avg_order_value)
    fulfillment_trend = calculate_trend(avg_fulfillment_time, prev_avg_fulfillment_time)
    rating_trend = calculate_trend(avg_rating, prev_avg_rating)
    profit_margin_trend = calculate_trend(profit_margin, prev_profit_margin)
    repeat_customer_trend = calculate_trend(repeat_customer_rate, prev_repeat_customer_rate)

    # Helper for trend color
    def get_trend_color(trend, inverse=False):
        if inverse:
            return 'normal' if trend < 0 else 'inverse'
        return 'normal' if trend > 0 else 'inverse'

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
        },
        'top_category': {
            'value': top_category,
            'trend': 0,
            'formatted': f"{top_category} (${top_category_revenue:,.2f})",
            'arrow': '',
            'color': 'normal'
        },
        'fulfillment_time': {
            'value': avg_fulfillment_time,
            'trend': fulfillment_trend,
            'formatted': f"{avg_fulfillment_time:.1f} days",
            'arrow': '▼' if fulfillment_trend < 0 else '▲',
            'color': get_trend_color(fulfillment_trend, inverse=True)
        },
        'customer_satisfaction': {
            'value': avg_rating,
            'trend': rating_trend,
            'formatted': f"{avg_rating:.1f}/5.0",
            'arrow': '▲' if rating_trend > 0 else '▼',
            'color': get_trend_color(rating_trend)
        },
        'profit_margin': {
            'value': profit_margin,
            'trend': profit_margin_trend,
            'formatted': f"{profit_margin:.1f}%",
            'arrow': '▲' if profit_margin_trend > 0 else '▼',
            'color': get_trend_color(profit_margin_trend)
        },
        'repeat_customer_rate': {
            'value': repeat_customer_rate,
            'trend': repeat_customer_trend,
            'formatted': f"{repeat_customer_rate:.1f}%",
            'arrow': '▲' if repeat_customer_trend > 0 else '▼',
            'color': get_trend_color(repeat_customer_trend)
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

def create_conversion_rates_funnel(start_date, end_date, category):
    """
    Creates a funnel chart showing conversion rates at different stages of the sales process.
    """
    df = filter_data(start_date, end_date, category)
    if df.empty:
        st.warning("No data available for this period.")
        return None
    
    # Calculate conversion metrics
    total_visitors = df['customer_id'].nunique()
    total_orders = df['order_id'].nunique()
    completed_orders = df[df['order_status'] == 'Completed']['order_id'].nunique()
    paid_orders = df[df['payment_status'] == 'Paid']['order_id'].nunique()
    
    # Create funnel data
    funnel_data = pd.DataFrame({
        'Stage': ['Visitors', 'Orders', 'Completed', 'Paid'],
        'Count': [total_visitors, total_orders, completed_orders, paid_orders]
    })
    
    # Calculate conversion rates
    funnel_data['Conversion'] = (funnel_data['Count'] / funnel_data['Count'].iloc[0] * 100).round(1)
    
    # Create funnel chart
    fig = go.Figure(go.Funnel(
        y=funnel_data['Stage'],
        x=funnel_data['Count'],
        textinfo="value+percent initial",
        textposition="inside",
        marker={"color": px.colors.sequential.Blues_r},
        connector={"line": {"color": "royalblue", "width": 1}}
    ))
    
    fig.update_layout(
        title="Sales Funnel Analysis",
        height=500,
        showlegend=False
    )
    
    return fig

def create_abandonment_visualization(start_date, end_date, category):
    """
    Creates a visualization showing cart abandonment rates and reasons.
    """
    df = filter_data(start_date, end_date, category)
    if df.empty:
        st.warning("No data available for this period.")
        return None
    
    # Calculate abandonment metrics
    total_carts = df['order_id'].nunique()
    abandoned_carts = df[df['order_status'] == 'Abandoned']['order_id'].nunique()
    completed_carts = df[df['order_status'] == 'Completed']['order_id'].nunique()
    
    # Create abandonment data
    abandonment_data = pd.DataFrame({
        'Status': ['Completed', 'Abandoned'],
        'Count': [completed_carts, abandoned_carts]
    })
    
    # Create pie chart
    fig = px.pie(
        abandonment_data,
        values='Count',
        names='Status',
        title="Cart Abandonment Analysis",
        color_discrete_sequence=px.colors.qualitative.Set3
    )
    
    fig.update_layout(
        height=500,
        showlegend=True,
        legend=dict(orientation="h", yanchor="bottom", y=-0.2, xanchor="center", x=0.5)
    )
    
    return fig

def create_gauge_charts(start_date, end_date, category):
    """
    Creates gauge charts showing performance against targets.
    """
    df = filter_data(start_date, end_date, category)
    if df.empty:
        st.warning("No data available for this period.")
        return None
    
    # Calculate actual performance metrics
    actual_revenue = df['price'].sum()
    actual_orders = df['order_id'].nunique()
    actual_customers = df['customer_id'].nunique()
    
    # Define target values (these should be configurable in a real application)
    target_revenue = actual_revenue * 1.2  # 20% higher than actual
    target_orders = actual_orders * 1.15   # 15% higher than actual
    target_customers = actual_customers * 1.1  # 10% higher than actual
    
    # Create subplots for gauge charts
    fig = make_subplots(
        rows=1, cols=3,
        specs=[[{"type": "indicator"}, {"type": "indicator"}, {"type": "indicator"}]],
        subplot_titles=("Revenue Target", "Orders Target", "Customers Target")
    )
    
    # Add gauge charts
    fig.add_trace(
        go.Indicator(
            mode="gauge+number",
            value=actual_revenue,
            title={'text': "Revenue"},
            gauge={'axis': {'range': [0, target_revenue]},
                  'bar': {'color': "darkblue"}},
            domain={'row': 0, 'column': 0}
        ),
        row=1, col=1
    )
    
    fig.add_trace(
        go.Indicator(
            mode="gauge+number",
            value=actual_orders,
            title={'text': "Orders"},
            gauge={'axis': {'range': [0, target_orders]},
                  'bar': {'color': "darkblue"}},
            domain={'row': 0, 'column': 1}
        ),
        row=1, col=2
    )
    
    fig.add_trace(
        go.Indicator(
            mode="gauge+number",
            value=actual_customers,
            title={'text': "Customers"},
            gauge={'axis': {'range': [0, target_customers]},
                  'bar': {'color': "darkblue"}},
            domain={'row': 0, 'column': 2}
        ),
        row=1, col=3
    )
    
    fig.update_layout(
        height=400,
        showlegend=False,
        title_text="Performance vs Targets"
    )
    
    return fig

def create_variance_analysis(start_date, end_date, category):
    """
    Creates a visualization showing variance analysis between actual and target metrics.
    """
    df = filter_data(start_date, end_date, category)
    if df.empty:
        st.warning("No data available for this period.")
        return None
    
    # Calculate actual and target metrics
    actual_metrics = {
        'Revenue': df['price'].sum(),
        'Orders': df['order_id'].nunique(),
        'Customers': df['customer_id'].nunique(),
        'AOV': df['price'].mean()
    }
    
    # Define target values (these should be configurable in a real application)
    target_metrics = {
        'Revenue': actual_metrics['Revenue'] * 1.2,
        'Orders': actual_metrics['Orders'] * 1.15,
        'Customers': actual_metrics['Customers'] * 1.1,
        'AOV': actual_metrics['AOV'] * 1.05
    }
    
    # Calculate variances
    variance_data = pd.DataFrame({
        'Metric': list(actual_metrics.keys()),
        'Actual': list(actual_metrics.values()),
        'Target': list(target_metrics.values())
    })
    
    variance_data['Variance'] = ((variance_data['Actual'] - variance_data['Target']) / variance_data['Target'] * 100).round(1)
    
    # Create bar chart
    fig = go.Figure()
    
    fig.add_trace(go.Bar(
        x=variance_data['Metric'],
        y=variance_data['Variance'],
        text=variance_data['Variance'].apply(lambda x: f"{x:+.1f}%"),
        textposition='auto',
        marker_color=np.where(variance_data['Variance'] >= 0, 'green', 'red')
    ))
    
    fig.update_layout(
        title="Variance Analysis (Actual vs Target)",
        xaxis_title="Metrics",
        yaxis_title="Variance (%)",
        height=500,
        showlegend=False
    )
    
    return fig

def create_retention_churn_analysis(start_date, end_date, category):
    """
    Creates a visualization showing customer retention and churn analysis.
    """
    df = filter_data(start_date, end_date, category)
    if df.empty:
        st.warning("No data available for this period.")
        return None
    
    # Calculate retention metrics
    df['month'] = df['order_date'].dt.to_period('M')
    customer_months = df.groupby('customer_id')['month'].nunique()
    
    retention_data = pd.DataFrame({
        'Months Active': customer_months.value_counts().index,
        'Customer Count': customer_months.value_counts().values
    }).sort_values('Months Active')
    
    # Create bar chart
    fig = px.bar(
        retention_data,
        x='Months Active',
        y='Customer Count',
        title="Customer Retention Analysis",
        labels={'Months Active': 'Number of Months Active', 'Customer Count': 'Number of Customers'}
    )
    
    fig.update_layout(
        height=500,
        showlegend=False
    )
    
    return fig

def create_individual_performance_comparison(start_date, end_date, category):
    """
    Creates a visualization comparing individual sales representative performance.
    """
    df = filter_data(start_date, end_date, category)
    if df.empty:
        st.warning("No data available for this period.")
        return None
    
    # Calculate performance metrics by sales rep
    rep_performance = df.groupby('sales_rep').agg({
        'price': 'sum',
        'order_id': 'nunique',
        'customer_id': 'nunique'
    }).reset_index()
    
    rep_performance.columns = ['Sales Rep', 'Revenue', 'Orders', 'Customers']
    rep_performance['AOV'] = (rep_performance['Revenue'] / rep_performance['Orders']).round(2)
    
    # Create bar chart
    fig = px.bar(
        rep_performance,
        x='Sales Rep',
        y='Revenue',
        title="Individual Sales Performance",
        text='Revenue',
        color='Revenue',
        color_continuous_scale=px.colors.sequential.Blues
    )
    
    fig.update_layout(
        height=400,
        showlegend=False,
        xaxis_title="Sales Representative",
        yaxis_title="Revenue ($)"
    )
    
    return fig

def create_team_performance_metrics(start_date, end_date, category):
    """
    Creates a visualization showing team performance metrics.
    """
    df = filter_data(start_date, end_date, category)
    if df.empty:
        st.warning("No data available for this period.")
        return None
    
    # Calculate team metrics
    total_orders = df['order_id'].nunique()
    total_customers = df['customer_id'].nunique()
    conversion_rate = (total_orders / total_customers * 100) if total_customers > 0 else 0
    
    team_metrics = {
        'Total Revenue': df['price'].sum(),
        'Total Orders': total_orders,
        'Total Customers': total_customers,
        'Average Order Value': df['price'].mean(),
        'Conversion Rate': conversion_rate
    }
    
    # Create metrics display
    fig = make_subplots(
        rows=2, cols=2,
        specs=[[{"type": "indicator"}, {"type": "indicator"}],
               [{"type": "indicator"}, {"type": "indicator"}]],
        subplot_titles=("Total Revenue", "Total Orders", "Total Customers", "Conversion Rate")
    )
    
    # Add indicators
    fig.add_trace(
        go.Indicator(
            mode="number",
            value=team_metrics['Total Revenue'],
            number={'prefix': "$", 'valueformat': ",.2f"}
        ),
        row=1, col=1
    )
    
    fig.add_trace(
        go.Indicator(
            mode="number",
            value=team_metrics['Total Orders'],
            number={'valueformat': ",.0f"}
        ),
        row=1, col=2
    )
    
    fig.add_trace(
        go.Indicator(
            mode="number",
            value=team_metrics['Total Customers'],
            number={'valueformat': ",.0f"}
        ),
        row=2, col=1
    )
    
    fig.add_trace(
        go.Indicator(
            mode="number",
            value=team_metrics['Conversion Rate'],
            number={'suffix': "%", 'valueformat': ".1f"}
        ),
        row=2, col=2
    )
    
    fig.update_layout(
        height=400,
        showlegend=False,
        title_text="Team Performance Metrics",
        grid={'rows': 2, 'columns': 2, 'pattern': "independent"}
    )
    
    return fig

def create_marketing_sales_correlation(start_date, end_date, category):
    """
    Creates a visualization showing correlation between marketing campaigns and sales.
    """
    df = filter_data(start_date, end_date, category)
    if df.empty:
        st.warning("No data available for this period.")
        return None
    
    # Calculate campaign performance
    campaign_performance = df.groupby('campaign_id').agg({
        'price': 'sum',
        'order_id': 'nunique',
        'customer_id': 'nunique'
    }).reset_index()
    
    # Create scatter plot
    fig = px.scatter(
        campaign_performance,
        x='order_id',
        y='price',
        size='customer_id',
        color='campaign_id',
        title="Marketing Campaign Performance",
        labels={
            'order_id': 'Number of Orders',
            'price': 'Total Revenue ($)',
            'customer_id': 'Number of Customers'
        }
    )
    
    fig.update_layout(
        height=400,
        showlegend=True
    )
    
    return fig

def create_price_volume_analysis(start_date, end_date, category):
    """
    Creates a visualization showing price-volume relationship analysis.
    """
    df = filter_data(start_date, end_date, category)
    if df.empty:
        st.warning("No data available for this period.")
        return None
    
    # Calculate price-volume metrics
    price_volume = df.groupby('product_names').agg({
        'price': 'mean',
        'quantity': 'sum'
    }).reset_index()
    
    # Create scatter plot
    fig = px.scatter(
        price_volume,
        x='price',
        y='quantity',
        hover_name='product_names',
        title="Price-Volume Analysis",
        labels={
            'price': 'Average Price ($)',
            'quantity': 'Total Quantity Sold'
        }
    )
    
    # Add trend line
    fig.add_trace(go.Scatter(
        x=price_volume['price'],
        y=np.poly1d(np.polyfit(price_volume['price'], price_volume['quantity'], 1))(price_volume['price']),
        mode='lines',
        name='Trend Line',
        line=dict(color='red', dash='dash')
    ))
    
    fig.update_layout(
        height=400,
        showlegend=True
    )
    
    return fig

def format_millions(value):
    """
    Format a number in millions with $ sign, e.g., $6,564M for 6,564,000.
    """
    if value >= 1_000_000:
        return f"${value/1_000_000:,.0f}M"
    return f"${value:,.2f}"

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
    
    # First row of metrics
    col1, col2, col3, col4 = st.columns(4)
    with col1:
        short_val = format_millions(stats['total_revenue']['value'])
        full_val = stats['total_revenue']['formatted']
        st.markdown(
            f'<div title="{full_val}"><b>Total Revenue</b><br><span style="font-size:2em">{short_val}</span></div>',
            unsafe_allow_html=True
        )
    with col2:
        st.metric("Total Orders", stats['total_orders']['formatted'], 
                 f"{stats['total_orders']['arrow']} {abs(stats['total_orders']['trend']):.1f}%", 
                 delta_color=stats['total_orders']['color'])
    with col3:
        st.metric("Average Order Value", stats['avg_order_value']['formatted'], 
                 f"{stats['avg_order_value']['arrow']} {abs(stats['avg_order_value']['trend']):.1f}%", 
                 delta_color=stats['avg_order_value']['color'])
    with col4:
        import re
        match = re.search(r"\\(([^)]+)\\)", stats['top_category']['formatted'])
        if match:
            full_cat_val = match.group(1)
            try:
                short_cat_val = format_millions(float(full_cat_val.replace('$','').replace(',','')))
            except:
                short_cat_val = full_cat_val
            cat_name = stats['top_category']['formatted'].split(' (')[0]
            short_display = f"{cat_name} ({short_cat_val})"
            st.markdown(
                f'<div title="{stats["top_category"]["formatted"]}"><b>Top Category</b><br>{short_display}</div>',
                unsafe_allow_html=True
            )
        else:
            st.markdown(
                f'<div title="{stats["top_category"]["formatted"]}"><b>Top Category</b><br>{stats["top_category"]["formatted"]}</div>',
                unsafe_allow_html=True
            )
    
    # Second row of metrics
    col5, col6, col7, col8 = st.columns(4)
    with col5:
        st.metric("Fulfillment Time", stats['fulfillment_time']['formatted'], 
                 f"{stats['fulfillment_time']['arrow']} {abs(stats['fulfillment_time']['trend']):.1f}%", 
                 delta_color=stats['fulfillment_time']['color'])
    with col6:
        st.metric("Customer Satisfaction", stats['customer_satisfaction']['formatted'], 
                 f"{stats['customer_satisfaction']['arrow']} {abs(stats['customer_satisfaction']['trend']):.1f}%", 
                 delta_color=stats['customer_satisfaction']['color'])
    with col7:
        st.metric("Profit Margin", stats['profit_margin']['formatted'], 
                 f"{stats['profit_margin']['arrow']} {abs(stats['profit_margin']['trend']):.1f}%", 
                 delta_color=stats['profit_margin']['color'])
    with col8:
        st.metric("Repeat Customer Rate", stats['repeat_customer_rate']['formatted'], 
                 f"{stats['repeat_customer_rate']['arrow']} {abs(stats['repeat_customer_rate']['trend']):.1f}%", 
                 delta_color=stats['repeat_customer_rate']['color'])
    
    # Rest of the dashboard tabs
    tab1, tab2, tab3, tab4, tab5, tab6 = st.tabs([
        "Sales Trends", 
        "Geographic Analysis", 
        "Product Analysis", 
        "Customer Analysis",
        "Sales Performance",
        "Team & Marketing"
    ])
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
        st.subheader("Retention and Churn Analysis")
        fig = create_retention_churn_analysis(start_date, end_date, selected_category)
        if fig:
            st.plotly_chart(fig, use_container_width=True)
    with tab5:
        st.subheader("Sales Funnel Analysis")
        fig = create_conversion_rates_funnel(start_date, end_date, selected_category)
        if fig:
            st.plotly_chart(fig, use_container_width=True)
        st.subheader("Abandonment Analysis")
        fig = create_abandonment_visualization(start_date, end_date, selected_category)
        if fig:
            st.plotly_chart(fig, use_container_width=True)
        st.subheader("Performance vs Targets")
        fig = create_gauge_charts(start_date, end_date, selected_category)
        if fig:
            st.plotly_chart(fig, use_container_width=True)
        st.subheader("Variance Analysis")
        fig = create_variance_analysis(start_date, end_date, selected_category)
        if fig:
            st.plotly_chart(fig, use_container_width=True)
    with tab6:
        st.subheader("Team Performance")
        col1, col2 = st.columns(2)
        with col1:
            st.subheader("Individual Performance")
            fig = create_individual_performance_comparison(start_date, end_date, selected_category)
            if fig:
                st.plotly_chart(fig, use_container_width=True)
        with col2:
            st.subheader("Team Metrics")
            fig = create_team_performance_metrics(start_date, end_date, selected_category)
            if fig:
                st.plotly_chart(fig, use_container_width=True)
        st.subheader("Marketing Analysis")
        col3, col4 = st.columns(2)
        with col3:
            st.subheader("Marketing-Sales Correlation")
            fig = create_marketing_sales_correlation(start_date, end_date, selected_category)
            if fig:
                st.plotly_chart(fig, use_container_width=True)
        with col4:
            st.subheader("Price-Volume Analysis")
            fig = create_price_volume_analysis(start_date, end_date, selected_category)
            if fig:
                st.plotly_chart(fig, use_container_width=True)
    st.header("Detailed Data")
    df = filter_data(start_date, end_date, selected_category)
    st.dataframe(df, use_container_width=True)

    # Footer
    st.markdown(
        """
        <hr style='margin-top: 2em; margin-bottom: 1em;'/>
        <div style='text-align: center; font-size: 1.1em;'>
            🚀 Live demo: <a href='https://dashboard-with-app-2nznrexscxmu6ckqpht2tr.streamlit.app/' target='_blank'>https://dashboard-with-app-2nznrexscxmu6ckqpht2tr.streamlit.app/</a><br/>
            💻 GitHub: <a href='https://github.com/vuhung16au/ACU/tree/main/DataScience/dashboard-with-streamlit' target='_blank'>https://github.com/vuhung16au/ACU/tree/main/DataScience/dashboard-with-streamlit</a>
        </div>
        """,
        unsafe_allow_html=True
    )

if __name__ == "__main__":
    create_dashboard() 