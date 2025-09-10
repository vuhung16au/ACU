# Ames Housing Dataset - K-Means Clustering Analysis

## Dataset Overview

The Ames Housing dataset is a comprehensive collection of residential property data from Ames, Iowa, containing detailed information about 2,930 houses sold between 2006 and 2010. This dataset serves as an excellent alternative to the Boston Housing dataset and is widely used in machine learning and data science education.

## Dataset Characteristics

- **Total Observations**: 2,930 houses
- **Total Variables**: 82 features
- **Time Period**: 2006-2010
- **Location**: Ames, Iowa, USA
- **Data Source**: Ames Assessor's Office

## Variable Categories

### 1. Identification Variables (2)
- **Order**: Observation number
- **PID**: Parcel identification number

### 2. Nominal Variables (23)
- **MSSubClass**: Type of dwelling involved in the sale
- **MSZoning**: General zoning classification
- **Street**: Type of road access to property
- **Alley**: Type of alley access to property
- **LandContour**: Flatness of the property
- **LotConfig**: Lot configuration
- **Neighborhood**: Physical locations within Ames city limits
- **Condition1**: Proximity to various conditions
- **Condition2**: Proximity to various conditions (if more than one)
- **BldgType**: Type of dwelling
- **HouseStyle**: Style of dwelling
- **RoofStyle**: Type of roof
- **RoofMatl**: Roof material
- **Exterior1st**: Exterior covering on house
- **Exterior2nd**: Exterior covering on house (if more than one)
- **MasVnrType**: Masonry veneer type
- **Foundation**: Type of foundation
- **Heating**: Type of heating
- **CentralAir**: Central air conditioning
- **GarageType**: Garage location
- **MiscFeature**: Miscellaneous feature not covered in other categories
- **SaleType**: Type of sale
- **SaleCondition**: Condition of sale

### 3. Ordinal Variables (23)
- **LotShape**: General shape of property
- **Utilities**: Type of utilities available
- **LandSlope**: Slope of property
- **OverallQual**: Rates the overall material and finish of the house
- **OverallCond**: Rates the overall condition of the house
- **ExterQual**: Evaluates the quality of the material on the exterior
- **ExterCond**: Evaluates the present condition of the material on the exterior
- **BsmtQual**: Evaluates the height of the basement
- **BsmtCond**: Evaluates the general condition of the basement
- **BsmtExposure**: Refers to walkout or garden level walls
- **BsmtFinType1**: Rating of basement finished area
- **BsmtFinType2**: Rating of basement finished area (if multiple types)
- **HeatingQC**: Heating quality and condition
- **Electrical**: Electrical system
- **KitchenQual**: Kitchen quality
- **Functional**: Home functionality
- **FireplaceQu**: Fireplace quality
- **GarageFinish**: Interior finish of the garage
- **GarageQual**: Garage quality
- **GarageCond**: Garage condition
- **PavedDrive**: Paved driveway
- **PoolQC**: Pool quality
- **Fence**: Fence quality

### 4. Discrete Variables (14)
- **YearBuilt**: Original construction date
- **YearRemodAdd**: Remodel date
- **BsmtFullBath**: Basement full bathrooms
- **BsmtHalfBath**: Basement half bathrooms
- **FullBath**: Full bathrooms above grade
- **HalfBath**: Half baths above grade
- **BedroomAbvGr**: Bedrooms above grade
- **KitchenAbvGr**: Kitchens above grade
- **TotRmsAbvGrd**: Total rooms above grade
- **Fireplaces**: Number of fireplaces
- **GarageYrBlt**: Year garage was built
- **GarageCars**: Size of garage in car capacity
- **MoSold**: Month Sold
- **YrSold**: Year Sold

### 5. Continuous Variables (20)
- **LotFrontage**: Linear feet of street connected to property
- **LotArea**: Lot size in square feet
- **MasVnrArea**: Masonry veneer area in square feet
- **BsmtFinSF1**: Type 1 finished square feet
- **BsmtFinSF2**: Type 2 finished square feet
- **BsmtUnfSF**: Unfinished square feet of basement area
- **TotalBsmtSF**: Total square feet of basement area
- **FirstFlrSF**: First Floor square feet
- **SecondFlrSF**: Second floor square feet
- **LowQualFinSF**: Low quality finished square feet
- **GrLivArea**: Above grade (ground) living area square feet
- **GarageArea**: Size of garage in square feet
- **WoodDeckSF**: Wood deck area in square feet
- **OpenPorchSF**: Open porch area in square feet
- **EnclosedPorch**: Enclosed porch area in square feet
- **Threeseasonporch**: Three season porch area in square feet
- **ScreenPorch**: Screen porch area in square feet
- **PoolArea**: Pool area in square feet
- **MiscVal**: $Value of miscellaneous feature
- **SalePrice**: Sale price (target variable)

## Features Selected for K-Means Clustering

For the k-means clustering analysis, five key continuous variables were selected based on their relevance to house characteristics and size:

### 1. LotArea
- **Description**: Lot size in square feet
- **Range**: 1,300 - 215,245 square feet
- **Mean**: 10,517 square feet
- **Importance**: Represents the total land area of the property

### 2. TotalBsmtSF
- **Description**: Total basement square feet
- **Range**: 0 - 6,110 square feet
- **Mean**: 1,057 square feet
- **Importance**: Indicates the total basement area available

### 3. FirstFlrSF
- **Description**: First floor square feet
- **Range**: 334 - 4,692 square feet
- **Mean**: 1,162 square feet
- **Importance**: Represents the main living area on the first floor

### 4. SecondFlrSF
- **Description**: Second floor square feet
- **Range**: 0 - 2,065 square feet
- **Mean**: 346 square feet
- **Importance**: Indicates additional living space on the second floor

### 5. GrLivArea
- **Description**: Above grade (ground) living area square feet
- **Range**: 334 - 5,642 square feet
- **Mean**: 1,515 square feet
- **Importance**: Total living area above ground level

## Target Variable

### SalePrice
- **Description**: Sale price of the house in dollars
- **Range**: $12,789 - $755,000
- **Mean**: $180,921
- **Median**: $163,000
- **Standard Deviation**: $79,442
- **Importance**: The target variable for predictive modeling

## Data Quality

### Missing Values
The dataset contains missing values in several variables:
- **LotFrontage**: 259 missing values (8.8%)
- **Alley**: 2,721 missing values (92.9%)
- **MasVnrType**: 8 missing values (0.3%)
- **MasVnrArea**: 8 missing values (0.3%)
- **BsmtQual**: 37 missing values (1.3%)
- **BsmtCond**: 37 missing values (1.3%)
- **BsmtExposure**: 38 missing values (1.3%)
- **BsmtFinType1**: 37 missing values (1.3%)
- **BsmtFinType2**: 38 missing values (1.3%)
- **BsmtFinSF1**: 1 missing value (0.03%)
- **BsmtFinSF2**: 1 missing value (0.03%)
- **BsmtUnfSF**: 1 missing value (0.03%)
- **TotalBsmtSF**: 1 missing value (0.03%)
- **Electrical**: 1 missing value (0.03%)
- **GarageType**: 81 missing values (2.8%)
- **GarageYrBlt**: 81 missing values (2.8%)
- **GarageFinish**: 81 missing values (2.8%)
- **GarageCars**: 1 missing value (0.03%)
- **GarageArea**: 1 missing value (0.03%)
- **GarageQual**: 81 missing values (2.8%)
- **GarageCond**: 81 missing values (2.8%)
- **PoolQC**: 2,909 missing values (99.3%)
- **Fence**: 2,348 missing values (80.1%)
- **MiscFeature**: 2,814 missing values (96.0%)

### Data Preprocessing for Clustering
For the k-means clustering analysis:
- Only observations with complete data for all five selected features were used
- This resulted in 2,930 observations (no missing values in selected features)
- Z-score standardization was applied to ensure all features are on the same scale

## Neighborhoods

The dataset includes 28 different neighborhoods in Ames:
1. Bloomington Heights
2. Bluestem
3. Briardale
4. Brookside
5. Clear Creek
6. College Creek
7. Crawford
8. Edwards
9. Gilbert
10. Greens
11. Green Hills
12. Iowa DOT and Rail Road
13. Landmark
14. Meadow Village
15. Mitchell
16. North Ames
17. Northridge
18. Northpark Villa
19. Northridge Heights
20. Northwest Ames
21. Old Town
22. South & West of Iowa State University
23. Sawyer
24. Sawyer West
25. Somerset
26. Stone Brook
27. Timberland
28. Veenker

## Usage in K-Means Clustering

This dataset is particularly well-suited for k-means clustering because:

1. **Diverse House Sizes**: Wide range of house sizes from small to very large
2. **Multiple Dimensions**: Five different size-related features provide rich clustering dimensions
3. **Clear Patterns**: Houses naturally group into size categories (small, medium, large, luxury)
4. **Business Relevance**: Clustering results can inform market segmentation and pricing strategies
5. **Feature Engineering**: Cluster labels and distances can be used as new features in predictive models
