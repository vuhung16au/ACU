# =============================================================================
# TEXT NORMALIZATION
# =============================================================================
# File: 08-text-normalization.R
# Purpose: Text field normalization and categorical variable standardization functions

#' Normalize text fields and standardize categorical variables
#' @param data Dataset to normalize
#' @return Dataset with normalized text
normalize_text_fields <- function(data) {
  cat("\n=== TEXT NORMALIZATION ===\n")
  
  # Standardize text fields
  data_normalized <- data %>%
    mutate(
      # Standardize city names
      city = fct_recode(city,
                        "Sydney" = "sydney",
                        "Melbourne" = "melbourne", 
                        "Perth" = "perth"),
      
      # Standardize product line names
      product_line = fct_recode(product_line,
                                "Health and Beauty" = "Health and beauty",
                                "Electronic Accessories" = "Electronic accessories",
                                "Home and Lifestyle" = "Home and lifestyle",
                                "Sports and Travel" = "Sports and travel",
                                "Food and Beverages" = "Food and beverages",
                                "Fashion Accessories" = "Fashion accessories"),
      
      # Standardize payment methods
      payment = fct_recode(payment,
                           "E-wallet" = "Ewallet",
                           "Credit Card" = "Credit card"),
      
      # Relevel factors for consistent ordering
      city = fct_relevel(city, "Sydney", "Melbourne", "Perth", "Unknown"),
      customer_type = fct_relevel(customer_type, "Member", "Normal"),
      gender = fct_relevel(gender, "Male", "Female")
    )
  
  return(data_normalized)
}
