package com.acu.javafx.game2048;

import javafx.scene.paint.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Defines the color scheme for the 2048 game using ACU brand colors.
 * Provides colors for different tile values and UI elements.
 * 
 * @author ACU JavaFX Course
 * @version 1.0
 */
public class ACUColorScheme {
    
    // ACU Brand Colors
    public static final Color ACU_PURPLE = Color.rgb(60, 16, 83);           // #3C1053
    public static final Color ACU_RED = Color.rgb(242, 18, 12);            // #F2120C
    public static final Color ACU_BLACK = Color.rgb(0, 0, 0);               // #000000
    public static final Color ACU_WHITE = Color.rgb(255, 255, 255);        // #FFFFFF
    public static final Color ACU_LAW_BUSINESS = Color.rgb(181, 24, 37);   // #B51825
    public static final Color ACU_WARM_STONE = Color.rgb(145, 139, 131);    // #918B83
    public static final Color ACU_DEEP_CHARCOAL = Color.rgb(48, 44, 42);    // #302C2A
    public static final Color ACU_SOFT_IVORY = Color.rgb(242, 239, 235);   // #F2EFEB
    
    // Game-specific colors
    public static final Color GAME_BACKGROUND = ACU_SOFT_IVORY;            // Background color
    public static final Color GRID_BACKGROUND = ACU_WARM_STONE;             // Grid background
    public static final Color TEXT_DARK = ACU_DEEP_CHARCOAL;                // Dark text
    public static final Color TEXT_LIGHT = ACU_WHITE;                      // Light text
    
    // Tile background colors for different values
    private static final Map<Integer, Color> TILE_BACKGROUNDS = new HashMap<>();
    
    // Tile text colors
    public static final Color TILE_TEXT_DARK = ACU_DEEP_CHARCOAL;
    public static final Color TILE_TEXT_LIGHT = ACU_WHITE;
    
    static {
        // Initialize tile background colors
        TILE_BACKGROUNDS.put(0, Color.rgb(238, 228, 218, 0.35));      // Empty tile (transparent)
        TILE_BACKGROUNDS.put(2, Color.rgb(238, 228, 218));            // Light beige
        TILE_BACKGROUNDS.put(4, Color.rgb(237, 224, 200));            // Slightly darker beige
        TILE_BACKGROUNDS.put(8, Color.rgb(242, 177, 121));            // Light orange
        TILE_BACKGROUNDS.put(16, Color.rgb(245, 149, 99));           // Orange
        TILE_BACKGROUNDS.put(32, Color.rgb(246, 124, 95));           // Red-orange
        TILE_BACKGROUNDS.put(64, Color.rgb(246, 94, 59));            // Red
        TILE_BACKGROUNDS.put(128, Color.rgb(237, 207, 114));         // Light yellow
        TILE_BACKGROUNDS.put(256, Color.rgb(237, 204, 97));          // Yellow
        TILE_BACKGROUNDS.put(512, Color.rgb(237, 200, 80));          // Darker yellow
        TILE_BACKGROUNDS.put(1024, Color.rgb(237, 197, 63));         // Gold
        TILE_BACKGROUNDS.put(2048, ACU_PURPLE);                       // ACU Purple for 2048
    }
    
    /**
     * Gets the background color for a tile with the specified value.
     * 
     * @param value The tile value
     * @return The background color for the tile
     */
    public static Color getTileBackground(int value) {
        return TILE_BACKGROUNDS.getOrDefault(value, ACU_PURPLE);
    }
    
    /**
     * Gets the text color for a tile with the specified value.
     * 
     * @param value The tile value
     * @return The text color for the tile
     */
    public static Color getTileTextColor(int value) {
        if (value <= 8) {
            return TILE_TEXT_DARK;
        } else {
            return TILE_TEXT_LIGHT;
        }
    }
    
    /**
     * Gets the game background color.
     * 
     * @return The game background color
     */
    public static Color getGameBackground() {
        return GAME_BACKGROUND;
    }
    
    /**
     * Gets the grid background color.
     * 
     * @return The grid background color
     */
    public static Color getGridBackground() {
        return GRID_BACKGROUND;
    }
    
    /**
     * Gets the dark text color.
     * 
     * @return The dark text color
     */
    public static Color getTextDark() {
        return TEXT_DARK;
    }
    
    /**
     * Gets the light text color.
     * 
     * @return The light text color
     */
    public static Color getTextLight() {
        return TEXT_LIGHT;
    }
}
