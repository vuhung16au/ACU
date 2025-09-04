# Data Cleansing Essentials with R - Modular Structure

## 🏗️ **Modular Architecture Overview**

This project has been refactored into a modular, function-based structure for better maintainability, reusability, and organization. The large monolithic script has been broken down into **16 focused files**, each containing specific functionality.

## 📁 **File Structure**

```
02-01-Data-Cleansing/
├── 00-master-script.R              # Master script that loads all modules
├── 01-setup-environment.R          # Environment setup and package management
├── 02-data-loading.R               # Data loading and initial assessment
├── 03-data-quality-assessment.R    # Data quality assessment and initial viz
├── 04-data-standardization.R       # Data standardization and column cleaning
├── 05-data-type-conversion.R       # Data type conversion and parsing
├── 06-missing-value-treatment.R    # Missing value handling
├── 07-outlier-detection.R          # Outlier detection and treatment
├── 08-text-normalization.R         # Text field normalization
├── 09-data-validation.R            # Business rule validation
├── 10-duplicate-detection.R        # Duplicate detection and removal
├── 11-derived-variables.R          # Creation of new variables
├── 12-data-quality-summary.R       # Quality reporting and summaries
├── 13-visualizations.R             # Data visualization generation
├── 14-export-data.R                # Data export and file saving
├── 15-main-execution.R             # Main workflow orchestration
├── Data-Cleansing-Refactored.R     # Original refactored version (reference)
└── README-Modular.md               # This documentation file
```

## 🚀 **Quick Start**

### **Option 1: Run Complete Workflow**
```r
# Load the master script
source("00-master-script.R")

# Execute complete data cleansing workflow
main()
```

### **Option 2: Run Individual Modules**
```r
# Load specific modules as needed
source("01-setup-environment.R")
source("02-data-loading.R")

# Use individual functions
setup_environment()
sales_data <- load_dataset()
```

### **Option 3: Custom Workflow**
```r
# Load all modules
source("00-master-script.R")

# Create custom workflow
setup_environment()
data <- load_dataset()
clean_data <- standardize_data(data)
clean_data <- convert_data_types(clean_data)
# ... continue with specific steps
```

## 🔧 **Module Details**

### **01-setup-environment.R**
- **Purpose**: Initialize R environment and load required packages
- **Functions**: `setup_environment()`
- **Dependencies**: None
- **Output**: Loaded packages and created directories

### **02-data-loading.R**
- **Purpose**: Load dataset and perform initial assessment
- **Functions**: `load_dataset()`, `check_parsing_issues()`, `display_data_overview()`
- **Dependencies**: tidyverse, readr
- **Output**: Loaded dataset and initial analysis

### **03-data-quality-assessment.R**
- **Purpose**: Comprehensive data quality analysis
- **Functions**: `assess_data_quality()`, `generate_initial_visualization()`
- **Dependencies**: naniar, skimr, ggplot2
- **Output**: Quality metrics and initial visualizations

### **04-data-standardization.R**
- **Purpose**: Standardize data structure and column names
- **Functions**: `standardize_data()`
- **Dependencies**: janitor
- **Output**: Cleaned column names and standardized structure

### **05-data-type-conversion.R**
- **Purpose**: Convert data types and parse dates/times
- **Functions**: `convert_data_types()`
- **Dependencies**: lubridate, forcats
- **Output**: Properly typed dataset with parsed dates

### **06-missing-value-treatment.R**
- **Purpose**: Handle missing values with appropriate strategies
- **Functions**: `treat_missing_values()`
- **Dependencies**: forcats, naniar
- **Output**: Dataset with treated missing values and indicators

### **07-outlier-detection.R**
- **Purpose**: Detect and treat statistical outliers
- **Functions**: `detect_and_treat_outliers()`
- **Dependencies**: dplyr, stats
- **Output**: Dataset with outlier treatment and flags

### **08-text-normalization.R**
- **Purpose**: Normalize text fields and standardize categories
- **Functions**: `normalize_text_fields()`
- **Dependencies**: forcats, stringr
- **Output**: Standardized categorical variables

### **09-data-validation.R**
- **Purpose**: Validate data against business rules
- **Functions**: `validate_data()`
- **Dependencies**: dplyr
- **Output**: Validation results and error summaries

### **10-duplicate-detection.R**
- **Purpose**: Find and remove duplicate records
- **Functions**: `detect_and_remove_duplicates()`
- **Dependencies**: janitor, dplyr
- **Output**: Cleaned dataset and duplicate information

### **11-derived-variables.R**
- **Purpose**: Create new useful variables from existing data
- **Functions**: `create_derived_variables()`
- **Dependencies**: dplyr, lubridate, stringr
- **Output**: Enhanced dataset with new features

### **12-data-quality-summary.R**
- **Purpose**: Generate comprehensive quality reports
- **Functions**: `generate_quality_summary()`
- **Dependencies**: naniar, dplyr
- **Output**: Detailed quality metrics and summaries

### **13-visualizations.R**
- **Purpose**: Create comprehensive data visualizations
- **Functions**: `generate_visualizations()`
- **Dependencies**: ggplot2, gridExtra, naniar
- **Output**: Multiple PNG visualization files

### **14-export-data.R**
- **Purpose**: Export cleaned data and reports
- **Functions**: `export_cleaned_data()`
- **Dependencies**: readr
- **Output**: CSV files with cleaned data and reports

### **15-main-execution.R**
- **Purpose**: Orchestrate the complete data cleansing workflow
- **Functions**: `main()`
- **Dependencies**: All other modules
- **Output**: Complete data cleansing process

## 🎯 **Benefits of Modular Structure**

### **1. Maintainability**
- Each file has a single, clear responsibility
- Easy to locate and modify specific functionality
- Reduced complexity in individual files

### **2. Reusability**
- Functions can be used independently
- Easy to import specific modules into other projects
- Flexible workflow creation

### **3. Testing**
- Individual functions can be tested in isolation
- Easier to debug specific functionality
- Better error isolation

### **4. Collaboration**
- Multiple developers can work on different modules
- Clear separation of concerns
- Easier code review process

### **5. Learning**
- Beginners can understand individual concepts
- Progressive complexity building
- Clear learning path through modules

## 🔄 **Workflow Options**

### **Complete Workflow**
```r
source("00-master-script.R")
main()  # Runs all 15 steps automatically
```

### **Step-by-Step Execution**
```r
source("00-master-script.R")

# Execute each step manually
setup_environment()
sales_data <- load_dataset()
check_parsing_issues(sales_data)
display_data_overview(sales_data)
# ... continue with specific steps
```

### **Custom Workflows**
```r
source("00-master-script.R")

# Create custom cleaning pipeline
setup_environment()
data <- load_dataset()
clean_data <- standardize_data(data)
clean_data <- convert_data_types(clean_data)
clean_data <- treat_missing_values(clean_data)
# Export only what you need
write_csv(clean_data, "my_cleaned_data.csv")
```

## 📚 **Usage Examples**

### **Example 1: Quick Data Assessment**
```r
source("00-master-script.R")
data <- load_dataset()
assess_data_quality(data)
generate_initial_visualization(data)
```

### **Example 2: Data Standardization Only**
```r
source("00-master-script.R")
data <- load_dataset()
clean_data <- standardize_data(data)
clean_data <- convert_data_types(clean_data)
```

### **Example 3: Missing Value Analysis**
```r
source("00-master-script.R")
data <- load_dataset()
missing_summary <- naniar::miss_var_summary(data)
print(missing_summary)
```

## 🚨 **Important Notes**

1. **Load Order**: Always load the master script first, or load modules in numerical order
2. **Dependencies**: Some modules depend on others - check the dependency list above
3. **Function Names**: All functions are globally available after sourcing modules
4. **Data Flow**: Data flows through the pipeline as it's passed between functions
5. **Error Handling**: Each module includes basic error handling and informative messages

## 🔧 **Customization**

### **Modify Individual Functions**
- Edit specific `.R` files to customize functionality
- Functions are self-contained and can be modified independently
- Maintain function signatures for compatibility

### **Add New Modules**
- Create new numbered files (e.g., `16-custom-analysis.R`)
- Update `00-master-script.R` to include new modules
- Follow the established naming and documentation conventions

### **Extend Existing Functions**
- Add new parameters to existing functions
- Enhance functionality while maintaining backward compatibility
- Update documentation and examples accordingly

## 📖 **Documentation**

Each module file includes:
- Clear purpose description
- Function documentation with `@param` and `@return` tags
- Inline comments explaining complex logic
- Consistent coding style and formatting

This modular structure makes the data cleansing project more professional, maintainable, and user-friendly while preserving all the original functionality.
