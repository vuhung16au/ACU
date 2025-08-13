package com.acu.datajpa.entity;

/**
 * Product category enumeration
 */
public enum ProductCategory {
    ELECTRONICS("Electronics"),
    CLOTHING("Clothing"),
    BOOKS("Books"),
    HOME_AND_GARDEN("Home & Garden"),
    SPORTS("Sports"),
    TOYS("Toys"),
    AUTOMOTIVE("Automotive"),
    HEALTH_AND_BEAUTY("Health & Beauty"),
    FOOD_AND_BEVERAGES("Food & Beverages"),
    OTHER("Other");

    private final String displayName;

    ProductCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
