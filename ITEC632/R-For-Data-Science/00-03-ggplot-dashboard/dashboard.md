# Introduction

This dashboard provides a comprehensive analysis of sales data using R
and ggplot2. The analysis includes key performance indicators, trends,
and visualizations to understand sales performance across different time
periods and metrics.

## Problem Statement

Create a sales dashboard that provides insights into: - Overall sales
performance - Sales trends over time - Order volume analysis - Product
quantity patterns - Monthly and yearly performance metrics

## Dataset

The sales dataset contains comprehensive transaction data including: -
**Source**: Sales transaction records - **Observations**: 2,825
records - **Variables**: 25 attributes - **Time Period**: 2023-2024 -
**Key Metrics**: Sales, Orders, Quantity

# Data Loading and Understanding

    # Load required libraries
    library(ggplot2)
    library(dplyr)
    library(lubridate)
    library(scales)
    library(gridExtra)
    library(grid)

    # Set theme for white background plots
    theme_set(theme_minimal() + 
              theme(panel.background = element_rect(fill = "white", color = NA),
                    plot.background = element_rect(fill = "white", color = NA)))

    # Load the dataset
    sales_data <- read.csv("sales-dataset/sales.csv")

    # Display basic information about the dataset
    cat("Dataset Information:\n")

    ## Dataset Information:

    cat("Shape:", nrow(sales_data), "rows,", ncol(sales_data), "columns\n")

    ## Shape: 5353 rows, 25 columns

    cat("Column names:", paste(names(sales_data), collapse = ", "), "\n")

    ## Column names: ORDERNUMBER, QUANTITYORDERED, PRICEEACH, ORDERLINENUMBER, SALES, ORDERDATE, STATUS, QTR_ID, MONTH_ID, YEAR_ID, PRODUCTLINE, MSRP, PRODUCTCODE, CUSTOMERNAME, PHONE, ADDRESSLINE1, ADDRESSLINE2, CITY, STATE, POSTALCODE, COUNTRY, TERRITORY, CONTACTLASTNAME, CONTACTFIRSTNAME, DEALSIZE

    # Display first few rows
    head(sales_data, 10)

    ##    ORDERNUMBER QUANTITYORDERED PRICEEACH ORDERLINENUMBER   SALES
    ## 1        10003              16     81.08               1 1297.24
    ## 2        10003              57     44.27               2 2523.54
    ## 3        10003              10     63.56               3  635.61
    ## 4        10003              12     75.11               4  901.31
    ## 5        10003              89     84.70               5 7538.59
    ## 6        10005              23     87.92               1 2022.22
    ## 7        10005              22     39.16               2  861.45
    ## 8        10005              49     47.24               3 2314.99
    ## 9        10006              65     60.86               1 3955.66
    ## 10       10006              30     77.87               2 2336.16
    ##           ORDERDATE     STATUS QTR_ID MONTH_ID YEAR_ID      PRODUCTLINE  MSRP
    ## 1  01/17/2015 00:00  Cancelled      1        1    2015      Motorcycles 121.4
    ## 2  09/06/2015 00:00    Shipped      3        9    2015 Trucks and Buses 103.4
    ## 3  05/11/2015 00:00   Disputed      2        5    2015           Planes  50.1
    ## 4  11/06/2015 00:00   Disputed      4       11    2015           Planes 100.9
    ## 5  09/10/2015 00:00    On Hold      3        9    2015 Trucks and Buses 207.2
    ## 6  01/05/2015 00:00    On Hold      1        1    2015            Ships 137.9
    ## 7  07/25/2015 00:00 In Process      3        7    2015           Trains 157.8
    ## 8  08/05/2015 00:00   Disputed      3        8    2015     Classic Cars 155.9
    ## 9  01/07/2015 00:00   Disputed      1        1    2015     Classic Cars 187.5
    ## 10 04/27/2015 00:00   Resolved      2        4    2015           Trains 103.3
    ##    PRODUCTCODE                CUSTOMERNAME      PHONE  ADDRESSLINE1
    ## 1     S10_4698        Blauer See Auto, Co. 8449173427  6714 Pine Rd
    ## 2    S700_2610            FunGiftIdeas.com 6246687835  386 Cedar Ln
    ## 3     S18_1984  Men 'R' US Retailers, Ltd. 2425731550 2534 Cedar Ln
    ## 4     S18_2957 West Coast Collectables Co. 1687923080  2199 Oak Ave
    ## 5     S24_1785             Vitachrome Inc. 6263966448  2606 Oak Ave
    ## 6     S24_3856          Muscle Machine Inc 4147066136  5171 Main St
    ## 7     S12_3148             Mini Wheels Co. 2222749931  8405 Oak Ave
    ## 8     S24_3969             Microscale Inc. 4077763886 9346 Cedar Ln
    ## 9     S50_1392      Saveley & Henriot, Co. 7592623285  5892 Oak Ave
    ## 10    S24_2000         L'ordine Souveniers 5469205806  5687 Oak Ave
    ##    ADDRESSLINE2         CITY STATE POSTALCODE     COUNTRY TERRITORY
    ## 1                  San Diego    TX      25040   Singapore      EMEA
    ## 2                Los Angeles    CA      82964     Belgium      <NA>
    ## 3                    Chicago    AZ      25570     Belgium      EMEA
    ## 4                   New York    CA      79263 Philippines     Japan
    ## 5                   New York    CA      16950     Germany     Japan
    ## 6               Philadelphia    AZ      27934   Singapore      <NA>
    ## 7                    Chicago    TX      90650         USA      EMEA
    ## 8                Los Angeles    CA      92442      Canada     Japan
    ## 9                    Houston    CA      64660      Sweden     Japan
    ## 10                  New York    NY      76700       Italy      EMEA
    ##    CONTACTLASTNAME CONTACTFIRSTNAME DEALSIZE
    ## 1            Brown             John   Medium
    ## 2         Martinez              Tom    Small
    ## 3          Johnson            Sarah    Small
    ## 4        Rodriguez            Sarah    Large
    ## 5          Johnson            Chris   Medium
    ## 6            Brown            Sarah    Large
    ## 7           Miller             John   Medium
    ## 8          Johnson            David    Large
    ## 9        Rodriguez            David   Medium
    ## 10           Jones             John   Medium

    # Data summary
    summary(sales_data)

    ##   ORDERNUMBER    QUANTITYORDERED   PRICEEACH      ORDERLINENUMBER
    ##  Min.   :10003   Min.   : 6.00   Min.   : 26.88   Min.   : 1.00  
    ##  1st Qu.:10217   1st Qu.:27.00   1st Qu.: 53.99   1st Qu.: 2.00  
    ##  Median :10370   Median :39.00   Median : 76.61   Median : 3.00  
    ##  Mean   :11848   Mean   :43.13   Mean   : 73.65   Mean   : 4.51  
    ##  3rd Qu.:13171   3rd Qu.:52.00   3rd Qu.: 99.48   3rd Qu.: 6.00  
    ##  Max.   :17228   Max.   :97.00   Max.   :100.00   Max.   :18.00  
    ##      SALES          ORDERDATE            STATUS              QTR_ID     
    ##  Min.   :  193.3   Length:5353        Length:5353        Min.   :1.000  
    ##  1st Qu.: 1942.2   Class :character   Class :character   1st Qu.:2.000  
    ##  Median : 3065.0   Mode  :character   Mode  :character   Median :3.000  
    ##  Mean   : 3401.7                                         Mean   :2.618  
    ##  3rd Qu.: 4512.5                                         3rd Qu.:4.000  
    ##  Max.   :14082.8                                         Max.   :4.000  
    ##     MONTH_ID         YEAR_ID     PRODUCTLINE             MSRP      
    ##  Min.   : 1.000   Min.   :2015   Length:5353        Min.   : 33.0  
    ##  1st Qu.: 4.000   1st Qu.:2019   Class :character   1st Qu.: 72.9  
    ##  Median : 7.000   Median :2023   Mode  :character   Median :105.0  
    ##  Mean   : 6.819   Mean   :2021                      Mean   :112.2  
    ##  3rd Qu.:10.000   3rd Qu.:2024                      3rd Qu.:147.3  
    ##  Max.   :12.000   Max.   :2025                      Max.   :214.0  
    ##  PRODUCTCODE        CUSTOMERNAME          PHONE           ADDRESSLINE1      
    ##  Length:5353        Length:5353        Length:5353        Length:5353       
    ##  Class :character   Class :character   Class :character   Class :character  
    ##  Mode  :character   Mode  :character   Mode  :character   Mode  :character  
    ##                                                                             
    ##                                                                             
    ##                                                                             
    ##  ADDRESSLINE2           CITY              STATE            POSTALCODE       
    ##  Length:5353        Length:5353        Length:5353        Length:5353       
    ##  Class :character   Class :character   Class :character   Class :character  
    ##  Mode  :character   Mode  :character   Mode  :character   Mode  :character  
    ##                                                                             
    ##                                                                             
    ##                                                                             
    ##    COUNTRY           TERRITORY         CONTACTLASTNAME    CONTACTFIRSTNAME  
    ##  Length:5353        Length:5353        Length:5353        Length:5353       
    ##  Class :character   Class :character   Class :character   Class :character  
    ##  Mode  :character   Mode  :character   Mode  :character   Mode  :character  
    ##                                                                             
    ##                                                                             
    ##                                                                             
    ##    DEALSIZE        
    ##  Length:5353       
    ##  Class :character  
    ##  Mode  :character  
    ##                    
    ##                    
    ## 

    # Check for missing values
    missing_values <- colSums(is.na(sales_data))
    cat("Missing values per column:\n")

    ## Missing values per column:

    print(missing_values)

    ##      ORDERNUMBER  QUANTITYORDERED        PRICEEACH  ORDERLINENUMBER 
    ##                0                0                0                0 
    ##            SALES        ORDERDATE           STATUS           QTR_ID 
    ##                0                0                0                0 
    ##         MONTH_ID          YEAR_ID      PRODUCTLINE             MSRP 
    ##                0                0                0                0 
    ##      PRODUCTCODE     CUSTOMERNAME            PHONE     ADDRESSLINE1 
    ##                0                0                0                0 
    ##     ADDRESSLINE2             CITY            STATE       POSTALCODE 
    ##                0                0                0                0 
    ##          COUNTRY        TERRITORY  CONTACTLASTNAME CONTACTFIRSTNAME 
    ##                0             1732                0                0 
    ##         DEALSIZE 
    ##                0

# Data Preprocessing

## Date Processing

    # Convert ORDERDATE to proper date format
    sales_data$ORDERDATE <- as.Date(sales_data$ORDERDATE, format = "%m/%d/%Y")

    # Extract additional date components
    sales_data$Year <- year(sales_data$ORDERDATE)
    sales_data$Month <- month(sales_data$ORDERDATE)
    sales_data$Month_Name <- month.name[sales_data$Month]

    # Ensure numeric columns are properly formatted
    sales_data$SALES <- as.numeric(sales_data$SALES)
    sales_data$QUANTITYORDERED <- as.numeric(sales_data$QUANTITYORDERED)
    sales_data$PRICEEACH <- as.numeric(sales_data$PRICEEACH)

    cat("After date processing:\n")

    ## After date processing:

    cat("Date range:", min(sales_data$ORDERDATE), "to", max(sales_data$ORDERDATE), "\n")

    ## Date range: 16440 to 20239

    cat("Years covered:", paste(unique(sales_data$Year), collapse = ", "), "\n")

    ## Years covered: 2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025

## Data Quality Check

    # Check data types
    cat("Data types:\n")

    ## Data types:

    str(sales_data)

    ## 'data.frame':    5353 obs. of  28 variables:
    ##  $ ORDERNUMBER     : int  10003 10003 10003 10003 10003 10005 10005 10005 10006 10006 ...
    ##  $ QUANTITYORDERED : num  16 57 10 12 89 23 22 49 65 30 ...
    ##  $ PRICEEACH       : num  81.1 44.3 63.6 75.1 84.7 ...
    ##  $ ORDERLINENUMBER : int  1 2 3 4 5 1 2 3 1 2 ...
    ##  $ SALES           : num  1297 2524 636 901 7539 ...
    ##  $ ORDERDATE       : Date, format: "2015-01-17" "2015-09-06" ...
    ##  $ STATUS          : chr  "Cancelled" "Shipped" "Disputed" "Disputed" ...
    ##  $ QTR_ID          : int  1 3 2 4 3 1 3 3 1 2 ...
    ##  $ MONTH_ID        : int  1 9 5 11 9 1 7 8 1 4 ...
    ##  $ YEAR_ID         : int  2015 2015 2015 2015 2015 2015 2015 2015 2015 2015 ...
    ##  $ PRODUCTLINE     : chr  "Motorcycles" "Trucks and Buses" "Planes" "Planes" ...
    ##  $ MSRP            : num  121.4 103.4 50.1 100.9 207.2 ...
    ##  $ PRODUCTCODE     : chr  "S10_4698" "S700_2610" "S18_1984" "S18_2957" ...
    ##  $ CUSTOMERNAME    : chr  "Blauer See Auto, Co." "FunGiftIdeas.com" "Men 'R' US Retailers, Ltd." "West Coast Collectables Co." ...
    ##  $ PHONE           : chr  "8449173427" "6246687835" "2425731550" "1687923080" ...
    ##  $ ADDRESSLINE1    : chr  "6714 Pine Rd" "386 Cedar Ln" "2534 Cedar Ln" "2199 Oak Ave" ...
    ##  $ ADDRESSLINE2    : chr  "" "" "" "" ...
    ##  $ CITY            : chr  "San Diego" "Los Angeles" "Chicago" "New York" ...
    ##  $ STATE           : chr  "TX" "CA" "AZ" "CA" ...
    ##  $ POSTALCODE      : chr  "25040" "82964" "25570" "79263" ...
    ##  $ COUNTRY         : chr  "Singapore" "Belgium" "Belgium" "Philippines" ...
    ##  $ TERRITORY       : chr  "EMEA" NA "EMEA" "Japan" ...
    ##  $ CONTACTLASTNAME : chr  "Brown" "Martinez" "Johnson" "Rodriguez" ...
    ##  $ CONTACTFIRSTNAME: chr  "John" "Tom" "Sarah" "Sarah" ...
    ##  $ DEALSIZE        : chr  "Medium" "Small" "Small" "Large" ...
    ##  $ Year            : num  2015 2015 2015 2015 2015 ...
    ##  $ Month           : num  1 9 5 11 9 1 7 8 1 4 ...
    ##  $ Month_Name      : chr  "January" "September" "May" "November" ...

    # Check for any remaining missing values
    cat("\nMissing values after processing:\n")

    ## 
    ## Missing values after processing:

    missing_after <- colSums(is.na(sales_data))
    print(missing_after)

    ##      ORDERNUMBER  QUANTITYORDERED        PRICEEACH  ORDERLINENUMBER 
    ##                0                0                0                0 
    ##            SALES        ORDERDATE           STATUS           QTR_ID 
    ##                0                0                0                0 
    ##         MONTH_ID          YEAR_ID      PRODUCTLINE             MSRP 
    ##                0                0                0                0 
    ##      PRODUCTCODE     CUSTOMERNAME            PHONE     ADDRESSLINE1 
    ##                0                0                0                0 
    ##     ADDRESSLINE2             CITY            STATE       POSTALCODE 
    ##                0                0                0                0 
    ##          COUNTRY        TERRITORY  CONTACTLASTNAME CONTACTFIRSTNAME 
    ##                0             1732                0                0 
    ##         DEALSIZE             Year            Month       Month_Name 
    ##                0                0                0                0

# Dashboard Visualizations

## 1. Total Sales Overview

    # Calculate total sales
    total_sales <- sum(sales_data$SALES, na.rm = TRUE)

    # Create total sales visualization
    p1 <- ggplot(data.frame(metric = "Total Sales", value = total_sales), 
                 aes(x = metric, y = value)) +
      geom_bar(stat = "identity", fill = "steelblue", alpha = 0.7, width = 0.5) +
      geom_text(aes(label = dollar_format()(value)), 
                vjust = -0.5, size = 4, fontface = "bold") +
      labs(title = "Total Sales Overview", 
           x = "", 
           y = "Total Sales ($)") +
      theme_minimal() +
      theme(panel.background = element_rect(fill = "white", color = NA),
            plot.background = element_rect(fill = "white", color = NA),
            axis.text.x = element_blank(),
            axis.ticks.x = element_blank()) +
      scale_y_continuous(labels = dollar_format())

    print(p1)

<img src="dashboard_files/figure-markdown_strict/total-sales-1.png" style="display: block; margin: auto;" />

    cat("Total Sales:", dollar_format()(total_sales), "\n")

    ## Total Sales: $18,209,421

## 2. Sales by Years

    # Calculate sales by year
    sales_by_year <- sales_data %>%
      group_by(Year) %>%
      summarise(Total_Sales = sum(SALES, na.rm = TRUE), .groups = 'drop')

    # Create sales by years visualization
    p2 <- ggplot(sales_by_year, aes(x = Year, y = Total_Sales)) +
      geom_line(color = "steelblue", linewidth = 2) +
      geom_point(color = "darkblue", size = 3) +
      geom_text(aes(label = dollar_format()(Total_Sales)), 
                vjust = -1, size = 3.5) +
      labs(title = "Total Sales by Years", 
           x = "Year", 
           y = "Total Sales ($)") +
      theme_minimal() +
      theme(panel.background = element_rect(fill = "white", color = NA),
            plot.background = element_rect(fill = "white", color = NA)) +
      scale_y_continuous(labels = dollar_format()) +
      scale_x_continuous(breaks = unique(sales_by_year$Year))

    print(p2)

<img src="dashboard_files/figure-markdown_strict/sales-by-years-1.png" style="display: block; margin: auto;" />

    # Display sales by year data
    print(sales_by_year)

    ## # A tibble: 11 × 2
    ##     Year Total_Sales
    ##    <dbl>       <dbl>
    ##  1  2015     848513.
    ##  2  2016    1140797.
    ##  3  2017     994419.
    ##  4  2018    1105815.
    ##  5  2019    1131357.
    ##  6  2020     817257.
    ##  7  2021     961766.
    ##  8  2022    1176829.
    ##  9  2023    3517000.
    ## 10  2024    4724183.
    ## 11  2025    1791487.

## 3. Orders by Years

    # Calculate orders by year
    orders_by_year <- sales_data %>%
      group_by(Year) %>%
      summarise(Total_Orders = n_distinct(ORDERNUMBER), .groups = 'drop')

    # Create orders by years visualization
    p3 <- ggplot(orders_by_year, aes(x = Year, y = Total_Orders)) +
      geom_line(color = "darkgreen", linewidth = 2) +
      geom_point(color = "darkolivegreen", size = 3) +
      geom_text(aes(label = comma_format()(Total_Orders)), 
                vjust = -1, size = 3.5) +
      labs(title = "Total Orders by Years", 
           x = "Year", 
           y = "Total Orders") +
      theme_minimal() +
      theme(panel.background = element_rect(fill = "white", color = NA),
            plot.background = element_rect(fill = "white", color = NA)) +
      scale_y_continuous(labels = comma_format()) +
      scale_x_continuous(breaks = unique(orders_by_year$Year))

    print(p3)

<img src="dashboard_files/figure-markdown_strict/orders-by-years-1.png" style="display: block; margin: auto;" />

    # Display orders by year data
    print(orders_by_year)

    ## # A tibble: 11 × 2
    ##     Year Total_Orders
    ##    <dbl>        <int>
    ##  1  2015           94
    ##  2  2016          114
    ##  3  2017           99
    ##  4  2018          118
    ##  5  2019          120
    ##  6  2020           84
    ##  7  2021          103
    ##  8  2022          118
    ##  9  2023          104
    ## 10  2024          144
    ## 11  2025           59

## 4. Total Quantity Overview

    # Calculate total quantity
    total_quantity <- sum(sales_data$QUANTITYORDERED, na.rm = TRUE)

    # Create total quantity visualization
    p4 <- ggplot(data.frame(metric = "Total Quantity", value = total_quantity), 
                 aes(x = metric, y = value)) +
      geom_bar(stat = "identity", fill = "darkorange", alpha = 0.7, width = 0.5) +
      geom_text(aes(label = comma_format()(value)), 
                vjust = -0.5, size = 4, fontface = "bold") +
      labs(title = "Total Quantity Overview", 
           x = "", 
           y = "Total Quantity") +
      theme_minimal() +
      theme(panel.background = element_rect(fill = "white", color = NA),
            plot.background = element_rect(fill = "white", color = NA),
            axis.text.x = element_blank(),
            axis.ticks.x = element_blank()) +
      scale_y_continuous(labels = comma_format())

    print(p4)

<img src="dashboard_files/figure-markdown_strict/total-quantity-1.png" style="display: block; margin: auto;" />

    cat("Total Quantity:", total_quantity, "\n")

    ## Total Quantity: 230850

## 5. Quantity by Years

    # Calculate quantity by year
    quantity_by_year <- sales_data %>%
      group_by(Year) %>%
      summarise(Total_Quantity = sum(QUANTITYORDERED, na.rm = TRUE), .groups = 'drop')

    # Create quantity by years visualization
    p5 <- ggplot(quantity_by_year, aes(x = Year, y = Total_Quantity)) +
      geom_line(color = "darkorange", linewidth = 2) +
      geom_point(color = "orange", size = 3) +
      geom_text(aes(label = comma_format()(Total_Quantity)), 
                vjust = -1, size = 3.5) +
      labs(title = "Total Quantity by Years", 
           x = "Year", 
           y = "Total Quantity") +
      theme_minimal() +
      theme(panel.background = element_rect(fill = "white", color = NA),
            plot.background = element_rect(fill = "white", color = NA)) +
      scale_y_continuous(labels = comma_format()) +
      scale_x_continuous(breaks = unique(quantity_by_year$Year))

    print(p5)

<img src="dashboard_files/figure-markdown_strict/quantity-by-years-1.png" style="display: block; margin: auto;" />

    # Display quantity by year data
    print(quantity_by_year)

    ## # A tibble: 11 × 2
    ##     Year Total_Quantity
    ##    <dbl>          <dbl>
    ##  1  2015          13681
    ##  2  2016          18752
    ##  3  2017          15945
    ##  4  2018          18137
    ##  5  2019          18052
    ##  6  2020          13351
    ##  7  2021          15058
    ##  8  2022          18807
    ##  9  2023          34612
    ## 10  2024          46824
    ## 11  2025          17631

## 6. Quantity by Months

    # Calculate quantity by month
    quantity_by_month <- sales_data %>%
      group_by(Month_Name) %>%
      summarise(Total_Quantity = sum(QUANTITYORDERED, na.rm = TRUE), .groups = 'drop') %>%
      mutate(Month_Name = factor(Month_Name, levels = month.name))

    # Create quantity by months visualization
    p6 <- ggplot(quantity_by_month, aes(x = Month_Name, y = Total_Quantity)) +
      geom_bar(stat = "identity", fill = "purple", alpha = 0.7) +
      geom_text(aes(label = comma_format()(Total_Quantity)), 
                vjust = -0.5, size = 3, angle = 45) +
      labs(title = "Total Quantity by Months", 
           x = "Month", 
           y = "Total Quantity") +
      theme_minimal() +
      theme(panel.background = element_rect(fill = "white", color = NA),
            plot.background = element_rect(fill = "white", color = NA),
            axis.text.x = element_text(angle = 45, hjust = 1)) +
      scale_y_continuous(labels = comma_format())

    print(p6)

<img src="dashboard_files/figure-markdown_strict/quantity-by-months-1.png" style="display: block; margin: auto;" />

    # Display quantity by month data
    print(quantity_by_month)

    ## # A tibble: 12 × 2
    ##    Month_Name Total_Quantity
    ##    <fct>               <dbl>
    ##  1 April               18624
    ##  2 August              17478
    ##  3 December            16048
    ##  4 February            18594
    ##  5 January             18578
    ##  6 July                16023
    ##  7 June                16604
    ##  8 March               18886
    ##  9 May                 19182
    ## 10 November            30897
    ## 11 October             23001
    ## 12 September           16935

# Summary and Analysis

## Key Performance Indicators

    # Calculate key performance indicators
    cat("=== KEY PERFORMANCE INDICATORS ===\n")

    ## === KEY PERFORMANCE INDICATORS ===

    cat("Total Sales:", dollar_format()(total_sales), "\n")

    ## Total Sales: $18,209,421

    cat("Total Quantity:", total_quantity, "\n")

    ## Total Quantity: 230850

## Year-over-Year Analysis

    cat("\n=== YEAR-OVER-YEAR ANALYSIS ===\n")

    ## 
    ## === YEAR-OVER-YEAR ANALYSIS ===

    for (i in 1:nrow(sales_by_year)) {
      year <- sales_by_year$Year[i]
      sales <- sales_by_year$Total_Sales[i]
      orders <- orders_by_year$Total_Orders[i]
      quantity <- quantity_by_year$Total_Quantity[i]
      
      cat("Year", year, ":\n")
      cat("  - Sales:", dollar_format()(sales), "\n")
      cat("  - Orders:", orders, "\n")
      cat("  - Quantity:", quantity, "\n")
      cat("\n")
    }

    ## Year 2015 :
    ##   - Sales: $848,513 
    ##   - Orders: 94 
    ##   - Quantity: 13681 
    ## 
    ## Year 2016 :
    ##   - Sales: $1,140,797 
    ##   - Orders: 114 
    ##   - Quantity: 18752 
    ## 
    ## Year 2017 :
    ##   - Sales: $994,419 
    ##   - Orders: 99 
    ##   - Quantity: 15945 
    ## 
    ## Year 2018 :
    ##   - Sales: $1,105,815 
    ##   - Orders: 118 
    ##   - Quantity: 18137 
    ## 
    ## Year 2019 :
    ##   - Sales: $1,131,357 
    ##   - Orders: 120 
    ##   - Quantity: 18052 
    ## 
    ## Year 2020 :
    ##   - Sales: $817,257 
    ##   - Orders: 84 
    ##   - Quantity: 13351 
    ## 
    ## Year 2021 :
    ##   - Sales: $961,766 
    ##   - Orders: 103 
    ##   - Quantity: 15058 
    ## 
    ## Year 2022 :
    ##   - Sales: $1,176,829 
    ##   - Orders: 118 
    ##   - Quantity: 18807 
    ## 
    ## Year 2023 :
    ##   - Sales: $3,517,000 
    ##   - Orders: 104 
    ##   - Quantity: 34612 
    ## 
    ## Year 2024 :
    ##   - Sales: $4,724,183 
    ##   - Orders: 144 
    ##   - Quantity: 46824 
    ## 
    ## Year 2025 :
    ##   - Sales: $1,791,487 
    ##   - Orders: 59 
    ##   - Quantity: 17631

## Monthly Patterns

    cat("=== MONTHLY QUANTITY PATTERNS ===\n")

    ## === MONTHLY QUANTITY PATTERNS ===

    for (i in 1:nrow(quantity_by_month)) {
      month <- quantity_by_month$Month_Name[i]
      quantity <- quantity_by_month$Total_Quantity[i]
      cat("-", month, ":", quantity, "units\n")
    }

    ## - 4 : 18624 units
    ## - 8 : 17478 units
    ## - 12 : 16048 units
    ## - 2 : 18594 units
    ## - 1 : 18578 units
    ## - 7 : 16023 units
    ## - 6 : 16604 units
    ## - 3 : 18886 units
    ## - 5 : 19182 units
    ## - 11 : 30897 units
    ## - 10 : 23001 units
    ## - 9 : 16935 units

# Business Insights

## Key Findings

1.  **Sales Performance**: The dashboard shows comprehensive sales
    performance across multiple metrics.

2.  **Temporal Trends**: Analysis reveals sales, order, and quantity
    trends over time periods.

3.  **Seasonal Patterns**: Monthly analysis provides insights into
    seasonal variations in product quantity.

4.  **Operational Metrics**: Key performance indicators help understand
    business efficiency.

## Recommendations

1.  **Performance Monitoring**: Use these visualizations for regular
    performance monitoring.

2.  **Trend Analysis**: Monitor year-over-year trends to identify growth
    patterns.

3.  **Seasonal Planning**: Use monthly patterns for inventory and
    resource planning.

4.  **Goal Setting**: Use historical data to set realistic targets for
    future periods.

## Dashboard Usage

This dashboard provides: - **Executive Summary**: High-level KPIs for
management reporting - **Trend Analysis**: Time-series visualizations
for trend identification - **Operational Insights**: Detailed metrics
for operational decision-making - **Performance Tracking**: Tools for
monitoring business performance

The visualizations are designed with white backgrounds for professional
presentation and can be easily integrated into reports and
presentations.

# Combined Dashboard

## Comprehensive Dashboard View

    # Create a combined dashboard layout
    combined_dashboard <- grid.arrange(
      p1, p2, p3,  # Top row: Total Sales, Sales by Years, Orders by Years
      p4, p5, p6,  # Bottom row: Total Quantity, Quantity by Years, Quantity by Months
      ncol = 3,
      nrow = 2,
      top = textGrob("Sales Dashboard - Comprehensive Analysis", 
                     gp = gpar(fontsize = 20, fontface = "bold")),
      bottom = textGrob("Data Source: Sales Dataset | Generated with R & ggplot2", 
                        gp = gpar(fontsize = 10, col = "gray50"))
    )

<img src="dashboard_files/figure-markdown_strict/combined-dashboard-1.png" style="display: block; margin: auto;" />

    print(combined_dashboard)

    ## TableGrob (4 x 3) "arrange": 8 grobs
    ##   z     cells    name                grob
    ## 1 1 (2-2,1-1) arrange      gtable[layout]
    ## 2 2 (2-2,2-2) arrange      gtable[layout]
    ## 3 3 (2-2,3-3) arrange      gtable[layout]
    ## 4 4 (3-3,1-1) arrange      gtable[layout]
    ## 5 5 (3-3,2-2) arrange      gtable[layout]
    ## 6 6 (3-3,3-3) arrange      gtable[layout]
    ## 7 7 (1-1,1-3) arrange text[GRID.text.434]
    ## 8 8 (4-4,1-3) arrange text[GRID.text.435]

This combined dashboard provides a comprehensive overview of all key
sales metrics in a single view, similar to professional business
intelligence dashboards. The layout is organized in a 2x3 grid with:

- **Top Row**: Total Sales, Sales by Years, Orders by Years
- **Bottom Row**: Total Quantity, Quantity by Years, Quantity by Months

The dashboard maintains consistent styling and white backgrounds for
professional presentation.
