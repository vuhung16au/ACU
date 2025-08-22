package com.acu.javafx.coins.models;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Data model representing a cryptocurrency price entry
 * Contains symbol, price, and timestamp information
 */
public record CoinPrice(String symbol, BigDecimal price, Instant timestamp) {
    
    /**
     * Returns the cryptocurrency symbol
     * @return the symbol (e.g., "BTC", "ETH")
     */
    public String getSymbol() {
        return symbol;
    }
    
    /**
     * Returns the price as a formatted string for display
     * @return price as string or "N/A" if price is null
     */
    public String getPrice() {
        return price != null ? price.toString() : "N/A";
    }
    
    /**
     * Returns the timestamp as a formatted date-time string for display
     * @return formatted timestamp or "N/A" if timestamp is null
     */
    public String getTimestamp() {
        if (timestamp != null) {
            LocalDateTime dateTime = LocalDateTime.ofInstant(timestamp, ZoneId.systemDefault());
            return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return "N/A";
    }
}
