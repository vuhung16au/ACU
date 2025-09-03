# corrplot: Correlation Matrix Visualization Examples

## Overview
This document provides comprehensive examples of corrplot, R's powerful package for visualizing correlation matrices. The examples demonstrate various visualization methods using simple, generated sample data to illustrate key concepts and functions.

## What is corrplot?
corrplot is an R package that provides a visual exploratory tool on correlation matrix that supports automatic variable reordering to help detect hidden patterns among variables. It's particularly useful for understanding relationships between multiple variables in a dataset.

## Key Concepts
- **Correlation Matrix**: A table showing correlation coefficients between variables
- **Visualization Methods**: Different ways to represent correlation values (circles, numbers, colors, etc.)
- **Color Schemes**: Color palettes to represent correlation strength and direction
- **Reordering**: Automatic arrangement of variables to reveal patterns
- **Significance Testing**: Statistical testing of correlation significance

## Sample Data Used
The examples use three simple datasets:

1. **Student Performance Data** (100 students)
   - Math scores, science scores, English scores, study hours, sleep hours, exercise hours
   
2. **Financial Data** (100 records)
   - Stock price, volume, volatility, market cap, P/E ratio, dividend yield
   
3. **Weather Data** (100 records)
   - Temperature, humidity, pressure, wind speed, precipitation, visibility

## Visualization Examples

### 1. Basic Correlation Plot (Circles)
```r
corrplot(cor_students, 
         method = "circle",
         title = "Student Performance Correlation Matrix",
         mar = c(0, 0, 2, 0))
```

**Features demonstrated:**
- Basic circle method for correlation visualization
- Circle size represents correlation strength
- Color represents correlation direction (positive/negative)
- Clean, minimal appearance

### 2. Correlation Plot with Numbers
```r
corrplot(cor_students, 
         method = "number",
         title = "Student Performance Correlation Matrix (Numbers)",
         mar = c(0, 0, 2, 0),
         number.cex = 0.8,
         tl.cex = 0.8)
```

**Features demonstrated:**
- Numerical display of correlation coefficients
- Customizable text sizes for numbers and labels
- Precise correlation values for detailed analysis
- Easy to read exact correlation values

### 3. Correlation Plot with Color Gradient
```r
corrplot(cor_students, 
         method = "color",
         title = "Student Performance Correlation Matrix (Color)",
         mar = c(0, 0, 2, 0),
         col = brewer.pal(n = 8, name = "RdBu"),
         tl.cex = 0.8)
```

**Features demonstrated:**
- Color-coded correlation matrix
- Red-Blue color scheme (RdBu) for positive/negative correlations
- Custom color palette using RColorBrewer
- Intuitive color interpretation

### 4. Mixed Correlation Plot
```r
# Upper triangle: circles
corrplot(cor_students, 
         method = "circle",
         type = "upper",
         title = "Student Performance Correlation Matrix (Mixed)",
         mar = c(0, 0, 2, 0),
         tl.cex = 0.8)

# Lower triangle: numbers
corrplot(cor_students, 
         method = "number",
         type = "lower",
         add = TRUE,
         tl.pos = "n",
         number.cex = 0.7)
```

**Features demonstrated:**
- Combination of two visualization methods
- Upper triangle shows circles, lower triangle shows numbers
- Layered plotting with `add = TRUE`
- Efficient use of space and information

### 5. Financial Data Correlation (Ellipses)
```r
corrplot(cor_financial, 
         method = "ellipse",
         title = "Financial Data Correlation Matrix",
         mar = c(0, 0, 2, 0),
         col = brewer.pal(n = 8, name = "PuOr"),
         tl.cex = 0.8)
```

**Features demonstrated:**
- Ellipse method for correlation visualization
- Purple-Orange color scheme (PuOr)
- Different dataset (financial) to show versatility
- Ellipse shape indicates correlation strength and direction

### 6. Weather Data Correlation (Squares)
```r
corrplot(cor_weather, 
         method = "square",
         title = "Weather Data Correlation Matrix",
         mar = c(0, 0, 2, 0),
         col = brewer.pal(n = 8, name = "YlOrRd"),
         tl.cex = 0.8)
```

**Features demonstrated:**
- Square method for correlation visualization
- Yellow-Orange-Red color scheme (YlOrRd)
- Weather dataset example
- Square size and color represent correlation

### 7. Reordered Correlation Plot
```r
corrplot(cor_students, 
         method = "circle",
         order = "hclust",
         title = "Student Performance Correlation Matrix (Reordered)",
         mar = c(0, 0, 2, 0),
         tl.cex = 0.8)
```

**Features demonstrated:**
- Hierarchical clustering reordering
- Automatic variable arrangement to reveal patterns
- Clustering of highly correlated variables
- Better visualization of correlation structure

### 8. Correlation Plot with Significance Testing
```r
corrplot(cor_students, 
         method = "circle",
         p.mat = matrix(0.01, nrow = nrow(cor_students), ncol = ncol(cor_students)),
         sig.level = 0.05,
         title = "Student Performance Correlation Matrix (with Significance)",
         mar = c(0, 0, 2, 0),
         tl.cex = 0.8)
```

**Features demonstrated:**
- P-value matrix for significance testing
- Significance level threshold (0.05)
- Visual indication of statistically significant correlations
- Statistical rigor in correlation analysis

### 9. Custom Styled Correlation Plot
```r
corrplot(cor_students, 
         method = "shade",
         title = "Student Performance Correlation Matrix (Custom Style)",
         mar = c(0, 0, 2, 0),
         col = c("#FF0000", "#FFFFFF", "#0000FF"),
         tl.cex = 0.8,
         tl.col = "black",
         bg = "white")
```

**Features demonstrated:**
- Shade method for correlation visualization
- Custom color scheme (red, white, blue)
- Custom text label colors
- Background color customization

### 10. Summary Correlation Plot (Pie Charts)
```r
corrplot(cor_summary, 
         method = "pie",
         title = "Combined Data Correlation Matrix",
         mar = c(0, 0, 2, 0),
         tl.cex = 0.8)
```

**Features demonstrated:**
- Pie chart method for correlation visualization
- Combined dataset from multiple domains
- Pie segments represent correlation strength
- Unique visual representation

## Key corrplot Functions Used

### Main Function
- `corrplot()`: Primary function for creating correlation plots

### Method Parameters
- `method`: Visualization method ("circle", "number", "color", "ellipse", "square", "shade", "pie")
- `type`: Plot type ("full", "upper", "lower")
- `order`: Variable ordering ("original", "hclust", "alphabet", "FPC", "AOE")
- `col`: Color palette for correlation values
- `tl.cex`: Text label size
- `number.cex`: Number size (when using "number" method)

### Advanced Features
- `p.mat`: P-value matrix for significance testing
- `sig.level`: Significance level threshold
- `add`: Whether to add to existing plot
- `mar`: Plot margins
- `bg`: Background color

## Visualization Methods Explained

### 1. Circle Method
- **Appearance**: Circles of varying sizes
- **Size**: Represents correlation strength
- **Color**: Represents correlation direction
- **Use Case**: General purpose, easy to interpret

### 2. Number Method
- **Appearance**: Numerical correlation coefficients
- **Precision**: Exact correlation values
- **Readability**: Easy to read precise values
- **Use Case**: Detailed analysis, reporting

### 3. Color Method
- **Appearance**: Color-coded squares
- **Intensity**: Represents correlation strength
- **Hue**: Represents correlation direction
- **Use Case**: Quick pattern recognition

### 4. Ellipse Method
- **Appearance**: Elliptical shapes
- **Shape**: Indicates correlation strength and direction
- **Aesthetics**: More visually appealing
- **Use Case**: Presentations, publications

### 5. Square Method
- **Appearance**: Square tiles
- **Size**: Represents correlation strength
- **Color**: Represents correlation direction
- **Use Case**: Compact visualization

### 6. Shade Method
- **Appearance**: Shaded squares
- **Intensity**: Represents correlation strength
- **Direction**: Represents correlation direction
- **Use Case**: Custom styling

### 7. Pie Method
- **Appearance**: Pie chart segments
- **Segments**: Represent correlation strength
- **Uniqueness**: Distinctive visual style
- **Use Case**: Creative presentations

## Best Practices

1. **Choose Appropriate Method**: Select visualization method based on your audience and purpose
2. **Use Meaningful Color Schemes**: Choose colors that are intuitive and accessible
3. **Consider Reordering**: Use hierarchical clustering to reveal patterns
4. **Test Significance**: Include statistical significance when appropriate
5. **Customize Labels**: Ensure text is readable and informative
6. **Combine Methods**: Use mixed methods for comprehensive visualization

## Running the Examples

To run these examples:

1. Install required packages:
   ```r
   install.packages("corrplot")
   install.packages("RColorBrewer")
   ```

2. Load the libraries:
   ```r
   library(corrplot)
   library(RColorBrewer)
   ```

3. Run the script:
   ```r
   source("corrplot.R")
   ```

## Output

The script generates 10 high-quality PNG images saved to the `images/` directory:
- `01_basic_correlation.png`
- `02_correlation_numbers.png`
- `03_correlation_color.png`
- `04_mixed_correlation.png`
- `05_financial_correlation.png`
- `06_weather_correlation.png`
- `07_reordered_correlation.png`
- `08_confidence_correlation.png`
- `09_custom_correlation.png`
- `10_summary_correlation.png`

## Additional Resources

- [corrplot CRAN Documentation](https://cran.r-project.org/web/packages/corrplot/)
- [corrplot GitHub Repository](https://github.com/taiyun/corrplot)
- [RColorBrewer Documentation](https://cran.r-project.org/web/packages/RColorBrewer/)

## Conclusion

corrplot provides a comprehensive and flexible system for visualizing correlation matrices in R. These examples demonstrate the various visualization methods, customization options, and advanced features available. The package excels at revealing patterns in correlation data through multiple visualization approaches, making it an essential tool for exploratory data analysis and correlation studies.

Whether you need a simple correlation overview or a detailed statistical analysis, corrplot offers the right combination of methods and customization options to create effective visualizations for your data.
