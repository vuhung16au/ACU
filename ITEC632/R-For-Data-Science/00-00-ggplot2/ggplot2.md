# ggplot2: Data Visualization Examples

## Overview
This document provides comprehensive examples of ggplot2, R's powerful data visualization package. The examples demonstrate various plot types using simple, generated sample data to illustrate key concepts and functions.

## What is ggplot2?
ggplot2 is an R package for creating elegant and complex plots from data in a data frame. It implements the "Grammar of Graphics" which allows you to build plots by combining independent components.

## Key Concepts
- **Data**: The dataset containing variables to be plotted
- **Aesthetics (aes)**: Mappings from data variables to visual properties
- **Geometries (geom)**: The actual marks we put on a plot
- **Scales**: Control how data values are mapped to aesthetic values
- **Facets**: Create multiple plots based on categorical variables
- **Themes**: Control the overall appearance of the plot

## Sample Data Used
The examples use three simple datasets:

1. **Student Performance Data** (30 students)
   - Math scores, science scores, study hours, grade levels
   
2. **Monthly Sales Data** (12 months)
   - Sales figures and profit margins
   
3. **Product Categories** (5 categories)
   - Sales volume and profit margins by product type

## Plot Examples

### 1. Scatter Plot with Regression Line
```r
ggplot(students, aes(x = study_hours, y = math_score)) +
  geom_point(color = "steelblue", size = 3, alpha = 0.7) +
  geom_smooth(method = "lm", color = "red", se = TRUE) +
  labs(
    title = "Study Hours vs Math Score",
    x = "Study Hours per Week",
    y = "Math Score"
  ) +
  theme_minimal()
```

**Features demonstrated:**
- Basic scatter plot with `geom_point()`
- Linear regression line with `geom_smooth()`
- Custom colors and transparency
- Professional labels and titles
- Minimal theme for clean appearance

### 2. Bar Chart
```r
ggplot(product_data, aes(x = reorder(category, sales), y = sales)) +
  geom_bar(stat = "identity", fill = "steelblue", alpha = 0.8) +
  geom_text(aes(label = sales), vjust = -0.5, size = 4) +
  labs(
    title = "Sales by Product Category",
    x = "Product Category",
    y = "Sales ($)"
  ) +
  theme_minimal()
```

**Features demonstrated:**
- Bar chart with `geom_bar()`
- Reordering categories by value
- Value labels on bars
- Custom fill colors and transparency

### 3. Line Chart
```r
ggplot(monthly_sales, aes(x = month, y = sales, group = 1)) +
  geom_line(color = "steelblue", size = 2) +
  geom_point(color = "steelblue", size = 4) +
  geom_text(aes(label = sales), vjust = -1, size = 3) +
  labs(
    title = "Monthly Sales Trend",
    x = "Month",
    y = "Sales ($)"
  ) +
  theme_minimal()
```

**Features demonstrated:**
- Line chart with `geom_line()`
- Points on the line for emphasis
- Value labels above points
- Group aesthetic for single line

### 4. Box Plot
```r
ggplot(students, aes(x = grade_level, y = math_score, fill = grade_level)) +
  geom_boxplot(alpha = 0.7) +
  geom_jitter(width = 0.2, alpha = 0.5) +
  labs(
    title = "Math Score Distribution by Grade Level",
    x = "Grade Level",
    y = "Math Score"
  ) +
  theme_minimal()
```

**Features demonstrated:**
- Box plot with `geom_boxplot()`
- Individual points with `geom_jitter()`
- Fill colors by category
- Distribution comparison across groups

### 5. Histogram
```r
ggplot(students, aes(x = math_score)) +
  geom_histogram(bins = 15, fill = "steelblue", alpha = 0.7, color = "black") +
  geom_vline(aes(xintercept = mean(math_score)), color = "red", linetype = "dashed", size = 1) +
  labs(
    title = "Distribution of Math Scores",
    x = "Math Score",
    y = "Frequency"
  ) +
  theme_minimal()
```

**Features demonstrated:**
- Histogram with `geom_histogram()`
- Custom bin count
- Vertical line for mean value
- Custom colors and borders

### 6. Faceted Plot
```r
ggplot(students, aes(x = study_hours, y = math_score)) +
  geom_point(color = "steelblue", alpha = 0.7) +
  geom_smooth(method = "lm", color = "red", se = FALSE) +
  facet_wrap(~grade_level, scales = "free") +
  labs(
    title = "Study Hours vs Math Score by Grade Level",
    x = "Study Hours per Week",
    y = "Math Score"
  ) +
  theme_minimal()
```

**Features demonstrated:**
- Faceting with `facet_wrap()`
- Separate plots for each category
- Consistent scales across facets
- Regression lines for each group

### 7. Density Plot
```r
ggplot(students, aes(x = math_score, fill = grade_level)) +
  geom_density(alpha = 0.6) +
  labs(
    title = "Math Score Density by Grade Level",
    x = "Math Score",
    y = "Density"
  ) +
  theme_minimal()
```

**Features demonstrated:**
- Density plot with `geom_density()`
- Multiple density curves by category
- Transparency for overlapping areas
- Smooth distribution visualization

## Key ggplot2 Functions Used

### Geometries (geom_*)
- `geom_point()`: Scatter plots
- `geom_line()`: Line charts
- `geom_bar()`: Bar charts
- `geom_boxplot()`: Box plots
- `geom_histogram()`: Histograms
- `geom_density()`: Density plots
- `geom_smooth()`: Trend lines
- `geom_text()`: Text labels
- `geom_vline()`: Vertical lines

### Aesthetics (aes)
- `x`, `y`: Position variables
- `fill`: Fill color
- `color`: Border/line color
- `size`: Point/line size
- `alpha`: Transparency
- `linetype`: Line style

### Labels and Themes
- `labs()`: Title, subtitle, axis labels
- `theme_minimal()`: Clean, minimal theme
- `theme()`: Custom theme modifications

## Best Practices

1. **Start Simple**: Begin with basic plots and add complexity gradually
2. **Use Meaningful Aesthetics**: Map data variables to visual properties logically
3. **Consistent Styling**: Use consistent colors, fonts, and themes
4. **Clear Labels**: Always provide descriptive titles and axis labels
5. **Appropriate Scales**: Choose scales that best represent your data
6. **Facet Wisely**: Use faceting to show relationships across categories

## Running the Examples

To run these examples:

1. Install required packages:
   ```r
   install.packages("ggplot2")
   ```

2. Load the library:
   ```r
   library(ggplot2)
   ```

3. Run the script:
   ```r
   source("ggplot2.R")
   ```

## Output

The script generates 7 high-quality PNG images saved to the `images/` directory:
- `01_scatter_plot.png`
- `02_bar_chart.png`
- `03_line_chart.png`
- `04_box_plot.png`
- `05_histogram.png`
- `06_faceted_plot.png`
- `07_density_plot.png`

## Additional Resources

- [ggplot2 Official Documentation](https://ggplot2.tidyverse.org/)
- [R Graphics Cookbook](https://r-graphics.org/)
- [ggplot2 Cheat Sheet](https://github.com/rstudio/cheatsheets/blob/main/data-visualization.pdf)

## Conclusion

ggplot2 provides a powerful and flexible system for creating data visualizations in R. These examples demonstrate the fundamental building blocks and common plot types. The grammar-based approach makes it easy to create complex plots by combining simple components, while the consistent syntax ensures that once you learn the basics, you can quickly create sophisticated visualizations.
