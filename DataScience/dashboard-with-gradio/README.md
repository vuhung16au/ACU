# Sales Performance Dashboard Using Gradio

This repo details how to construct a modern data dashboard using Python and the Gradio library. We focus on building interactive web applications for data insights, using a synthetic sales dataset as an example. The dashboard features include filters for dates and categories, key metric displays, data visualizations, and a raw data table. The article also provides a comparison between Gradio and Streamlit, noting Gradio's ease of use for machine learning model interfaces compared to Streamlit's more general data application capabilities. Code snippets are included for dataset generation, Gradio installation, and dashboard creation.

## Setup and Run

1. **Create a virtual environment:**

   ```bash
   python3 -m venv .venv
   ```

2. **Activate the virtual environment:**

   ```bash
   source .venv/bin/activate
   ```

3. **Install requirements:**

   ```bash
   pip install -r requirements.txt
   ```

4. **Generate synthetic data:**

   ```bash
   python generate-synthetic-data.py
   ```
   
   This script creates a synthetic sales dataset that will be used by the dashboard. The data will be saved in the `sales_data` directory.

The generated dataset looks like this:

```

order_id,order_date,customer_id,customer_name,product_id,product_names,categories,quantity,price,total,shipping_method,delivery_date,order_status,customer_segment,customer_location,customer_age,product_cost,discount,rating,sales_rep,campaign_id,store_id,store_region,payment_method,payment_status,profit
1,2023-06-27,8911,Kayla Armstrong,206,Premium Daily Planner,Stationery,10,26.79,267.93,Standard,2023-07-07,Processing,Retail,Bakertown,54,16.16,0.0,5,Jeffrey Peters,"",4,Southeast,Bank Transfer,Paid,106.33
2,2020-02-19,8554,Claire Phillips,203,Ergonomic Wireless Mouse,Computing,6,58.57,351.41,Standard,2020-02-27,Completed,Retail,Lake Brittany,35,29.38,17.57,5,Sean Foster,SUMMER23,24,Southeast,Credit Card,Paid,157.56
3,2021-07-01,7389,Shannon Perez,212,Fast Wireless Charging Pad,Tech Accessories,3,46.37,139.1,Standard,2021-07-09,Completed,Retail,Sheppardside,28,31.93,0.0,2,James King,"",15,Southwest,Credit Card,Paid,43.31
4,2021-03-20,3133,Andrew Garcia,214,Voice-Controlled Smart Speaker,Smart Home,7,121.21,848.48,Standard,2021-03-28,Completed,Retail,New Michelle,29,70.59,84.85,5,Steven Spencer,"",7,Southwest,Credit Card,Paid,269.5
5,2024-01-07,2188,Lori Gonzalez,210,Water-Resistant Laptop Backpack,Travel,9,81.87,736.81,Standard,2024-01-17,Processing,Retail,Meganport,41,55.84,0.0,1,Andrew Campbell,HOLIDAY23,26,Southeast,Credit Card,Failed,234.25
6,2023-09-09,7346,Caitlin Newton,207,Professional Art Markers Set,Stationery,4,34.47,137.89,Standard,2023-09-18,Completed,Retail,New John,44,19.84,6.89,4,Jon Haley,"",30,West,Apple Pay,Paid,51.64
7,2024-11-12,4253,Rhonda Nixon,212,Fast Wireless Charging Pad,Tech Accessories,8,48.91,391.25,Express,2024-11-17,Completed,Wholesale,Johnsonborough,35,29.82,0.0,4,Andrew Campbell,"",3,Midwest,Credit Card,Paid,152.69
8,2020-06-22,3556,Blake Winters,204,Smart LED Desk Lamp,Accessories,10,48.91,489.14,Standard,2020-06-30,Completed,Retail,East Michaelville,22,25.19,0.0,4,Jeffrey Peters,HOLIDAY23,37,Midwest,PayPal,Paid,237.24
9,2022-04-20,4718,Darren Roberts,206,Premium Daily Planner,Stationery,6,23.31,139.84,Standard,2022-04-29,Completed,Retail,Lake Courtneyfurt,26,15.09,27.97,5,David Roberson,HOLIDAY23,34,Southeast,Credit Card,Paid,21.33
```
5. **Run the Gradio dashboard:**

   ```bash
   python gradio-dashboard.py
   ```

6. **Open the dashboard in your web browser:**

   Navigate to [http://127.0.0.1:7860](http://127.0.0.1:7860)

## Key Libraries

### Polars

[Polars](https://pola.rs/) is a lightning-fast DataFrame library for data manipulation and analysis, written in Rust. Key features include:

- **Performance**: Significantly faster than pandas for many operations due to its Rust implementation
- **Memory Efficiency**: Uses Arrow memory format for efficient memory usage
- **Lazy Evaluation**: Optimizes query execution plans for better performance
- **Multi-threaded**: Built from the ground up for parallel processing
- **API Familiarity**: Offers a pandas-like API for easier adoption

### Gradio

[Gradio](https://www.gradio.app/) is a Python library that allows you to quickly create customizable web interfaces for your machine learning models, data visualizations, and interactive applications. Features include:

- **Simplicity**: Create complex UIs with minimal code
- **Interactive Components**: Sliders, dropdowns, file uploads, and more
- **Shareable**: Easily share your interfaces via temporary links
- **Customizable**: Control layout and styling to match your needs
- **Integration**: Works well with popular ML frameworks and data libraries

### Additional Libraries

- **Plotly**: Interactive data visualization library for creating dynamic and interactive plots
- **Seaborn**: Statistical data visualization library built on top of matplotlib
- **Folium**: Python wrapper for Leaflet.js to create interactive maps
- **Geopy**: Python library for geocoding and distance calculations
- **Scikit-learn**: Machine learning library for data analysis and modeling
- **Kaleido**: Static image export for Plotly figures
- **Cachetools**: Provides various memoizing collections and decorators for performance optimization
- **Faker**: Generates fake data for testing and development purposes
- **Pandas**: Data manipulation and analysis library (used alongside Polars for specific operations)
- **NumPy**: Fundamental package for scientific computing with Python
- **Matplotlib**: Comprehensive library for creating static, animated, and interactive visualizations

In this dashboard application, these libraries work together to provide efficient data processing (Polars), an interactive web interface (Gradio), and performance optimization through caching (Cachetools).

## Faker

[Faker](https://faker.readthedocs.io/en/master/) is a Python package that generates fake data for various purposes, such as testing or populating databases. It can create realistic-looking data for names, addresses, dates, and more. 

## Project Files

### generate-synthetic-data.py

This script generates a synthetic sales dataset that simulates real-world business data. It creates records with randomized yet realistic patterns for:

- Product categories
- Sales amounts
- Dates
- Customer demographics
- Regional information
- Geographic coordinates for mapping

The generated data is saved to `sales_data/sales_data.csv` and serves as the foundation for the dashboard visualizations.

### gradio-dashboard.py

This is the main application script that:

- Loads and processes the sales data using Polars and Pandas
- Creates an interactive web interface with Gradio
- Implements filters, visualizations, and data tables
- Provides interactive maps using Folium
- Generates statistical insights using Scikit-learn
- Creates dynamic visualizations with Plotly and Seaborn
- Utilizes caching for performance optimization

When run, this script starts a web server that hosts the dashboard interface, making it accessible through your web browser.

### sales_data/

Directory containing the generated synthetic dataset used by the dashboard.

### images/

Directory containing screenshots and visualizations of the dashboard.

## Gradio vs. Streamlit Comparison

Both Gradio and Streamlit are popular Python frameworks for creating web-based data applications, but they have different strengths and use cases.

### Gradio Framework

**Strengths:**

- **ML Model Interfaces**: Excels at creating interfaces for machine learning models
- **Simplicity**: Generally requires less code for basic interfaces
- **Component Focus**: Designed around input/output components
- **API Integration**: Easy to turn interfaces into APIs
- **Customizable Themes**: Multiple built-in themes and styling options
- **Language Support**: Good support for NLP and audio processing tasks

**Limitations:**

- Less comprehensive for complex, multi-page applications
- Fewer built-in complex visualization components
- Community and ecosystem is growing but smaller than Streamlit's

### Streamlit Framework

**Strengths:**

- **Data Apps**: Better suited for complete data applications and dashboards
- **State Management**: More sophisticated state handling
- **Layout Control**: More options for controlling application layout
- **Ecosystem**: Larger ecosystem of components and extensions
- **Multi-page Support**: Native multi-page application capability
- **Caching**: Built-in caching mechanisms for data and computation

**Limitations:**

- Often requires more code than Gradio for simple interfaces
- Deployment can be more complex
- Less focused on ML model interfaces specifically

### When to Choose Gradio (like this project)

- You're primarily building interfaces for machine learning models
- You need a simple, quick solution with minimal code
- Your application focuses on processing inputs and generating outputs
- You want to easily share your interface with non-technical users
- You're integrating with other ML frameworks like HuggingFace

### When to Choose Streamlit

- You're building comprehensive data analytics applications
- Your app requires complex state management
- You need advanced layout control and multi-page capabilities
- Your application focuses more on visualizations than model interactions
- You're building a tool for data exploration rather than model demonstration

This project uses Gradio because it provides a straightforward way to create an interactive dashboard interface with minimal code while effectively handling the various components needed for our sales data visualization.

# Screenshots

![Dashboard Screenshot](images/gradio-dashboard.png)


More screenshots under `images/` folder.