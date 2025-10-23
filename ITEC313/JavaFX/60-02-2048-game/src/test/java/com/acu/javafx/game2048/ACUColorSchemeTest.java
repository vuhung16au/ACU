package com.acu.javafx.game2048;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ACUColorScheme class.
 * Tests color scheme functionality and ACU brand color usage.
 * 
 * @author ACU JavaFX Course
 * @version 1.0
 */
public class ACUColorSchemeTest {
    
    @Test
    public void testACUBrandColors() {
        // Test ACU Purple
        assertEquals(60, ACUColorScheme.ACU_PURPLE.getRed() * 255, 0.01);
        assertEquals(16, ACUColorScheme.ACU_PURPLE.getGreen() * 255, 0.01);
        assertEquals(83, ACUColorScheme.ACU_PURPLE.getBlue() * 255, 0.01);
        
        // Test ACU Red
        assertEquals(242, ACUColorScheme.ACU_RED.getRed() * 255, 0.01);
        assertEquals(18, ACUColorScheme.ACU_RED.getGreen() * 255, 0.01);
        assertEquals(12, ACUColorScheme.ACU_RED.getBlue() * 255, 0.01);
        
        // Test ACU Black
        assertEquals(0, ACUColorScheme.ACU_BLACK.getRed() * 255, 0.01);
        assertEquals(0, ACUColorScheme.ACU_BLACK.getGreen() * 255, 0.01);
        assertEquals(0, ACUColorScheme.ACU_BLACK.getBlue() * 255, 0.01);
        
        // Test ACU White
        assertEquals(255, ACUColorScheme.ACU_WHITE.getRed() * 255, 0.01);
        assertEquals(255, ACUColorScheme.ACU_WHITE.getGreen() * 255, 0.01);
        assertEquals(255, ACUColorScheme.ACU_WHITE.getBlue() * 255, 0.01);
    }
    
    @Test
    public void testTileBackgroundColors() {
        // Test empty tile background
        Color emptyTileColor = ACUColorScheme.getTileBackground(0);
        assertNotNull(emptyTileColor);
        
        // Test specific tile values
        Color tile2Color = ACUColorScheme.getTileBackground(2);
        assertNotNull(tile2Color);
        
        Color tile4Color = ACUColorScheme.getTileBackground(4);
        assertNotNull(tile4Color);
        
        Color tile8Color = ACUColorScheme.getTileBackground(8);
        assertNotNull(tile8Color);
        
        // Test 2048 tile (should use ACU Purple)
        Color tile2048Color = ACUColorScheme.getTileBackground(2048);
        assertEquals(ACUColorScheme.ACU_PURPLE, tile2048Color);
    }
    
    @Test
    public void testTileTextColors() {
        // Test low value tiles (should use dark text)
        Color lowValueTextColor = ACUColorScheme.getTileTextColor(2);
        assertEquals(ACUColorScheme.TILE_TEXT_DARK, lowValueTextColor);
        
        Color lowValueTextColor2 = ACUColorScheme.getTileTextColor(8);
        assertEquals(ACUColorScheme.TILE_TEXT_DARK, lowValueTextColor2);
        
        // Test high value tiles (should use light text)
        Color highValueTextColor = ACUColorScheme.getTileTextColor(16);
        assertEquals(ACUColorScheme.TILE_TEXT_LIGHT, highValueTextColor);
        
        Color highValueTextColor2 = ACUColorScheme.getTileTextColor(2048);
        assertEquals(ACUColorScheme.TILE_TEXT_LIGHT, highValueTextColor2);
    }
    
    @Test
    public void testGameColors() {
        // Test game background
        Color gameBackground = ACUColorScheme.getGameBackground();
        assertEquals(ACUColorScheme.GAME_BACKGROUND, gameBackground);
        
        // Test grid background
        Color gridBackground = ACUColorScheme.getGridBackground();
        assertEquals(ACUColorScheme.GRID_BACKGROUND, gridBackground);
        
        // Test text colors
        Color textDark = ACUColorScheme.getTextDark();
        assertEquals(ACUColorScheme.TEXT_DARK, textDark);
        
        Color textLight = ACUColorScheme.getTextLight();
        assertEquals(ACUColorScheme.TEXT_LIGHT, textLight);
    }
    
    @Test
    public void testColorConsistency() {
        // Test that colors are consistent across multiple calls
        Color color1 = ACUColorScheme.getTileBackground(2);
        Color color2 = ACUColorScheme.getTileBackground(2);
        assertEquals(color1, color2);
        
        Color textColor1 = ACUColorScheme.getTileTextColor(4);
        Color textColor2 = ACUColorScheme.getTileTextColor(4);
        assertEquals(textColor1, textColor2);
    }
    
    @Test
    public void testACUAdditionalColors() {
        // Test ACU Law and Business color
        assertEquals(181, ACUColorScheme.ACU_LAW_BUSINESS.getRed() * 255, 0.01);
        assertEquals(24, ACUColorScheme.ACU_LAW_BUSINESS.getGreen() * 255, 0.01);
        assertEquals(37, ACUColorScheme.ACU_LAW_BUSINESS.getBlue() * 255, 0.01);
        
        // Test Warm Stone color
        assertEquals(145, ACUColorScheme.ACU_WARM_STONE.getRed() * 255, 0.01);
        assertEquals(139, ACUColorScheme.ACU_WARM_STONE.getGreen() * 255, 0.01);
        assertEquals(131, ACUColorScheme.ACU_WARM_STONE.getBlue() * 255, 0.01);
        
        // Test Deep Charcoal color
        assertEquals(48, ACUColorScheme.ACU_DEEP_CHARCOAL.getRed() * 255, 0.01);
        assertEquals(44, ACUColorScheme.ACU_DEEP_CHARCOAL.getGreen() * 255, 0.01);
        assertEquals(42, ACUColorScheme.ACU_DEEP_CHARCOAL.getBlue() * 255, 0.01);
        
        // Test Soft Ivory color
        assertEquals(242, ACUColorScheme.ACU_SOFT_IVORY.getRed() * 255, 0.01);
        assertEquals(239, ACUColorScheme.ACU_SOFT_IVORY.getGreen() * 255, 0.01);
        assertEquals(235, ACUColorScheme.ACU_SOFT_IVORY.getBlue() * 255, 0.01);
    }
}
