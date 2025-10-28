package com.acu.javafx.knighttour;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit tests for KnightTourSolver class
 * 
 * These tests verify the correctness of the Knight's Tour algorithm
 * and related functionality without requiring JavaFX UI components.
 * 
 * @author ACU JavaFX Team
 * @version 1.0.0
 */
@DisplayName("Knight's Tour Solver Tests")
class KnightTourSolverTest {
    
    private ChessBoard chessBoard;
    private KnightTourSolver solver;
    
    @BeforeEach
    void setUp() {
        // Create a mock chessboard for testing
        chessBoard = new ChessBoard(8, 60);
        solver = new KnightTourSolver(chessBoard);
    }
    
    @Nested
    @DisplayName("Knight Move Validation Tests")
    class KnightMoveValidationTests {
        
        @Test
        @DisplayName("Should validate correct knight moves")
        void shouldValidateCorrectKnightMoves() {
            // Test valid L-shaped moves
            ChessBoard.Position from = new ChessBoard.Position(3, 3);
            
            // Valid moves from center position
            assertTrue(KnightTourSolver.isValidKnightMove(from, new ChessBoard.Position(1, 2))); // Up-left
            assertTrue(KnightTourSolver.isValidKnightMove(from, new ChessBoard.Position(1, 4))); // Up-right
            assertTrue(KnightTourSolver.isValidKnightMove(from, new ChessBoard.Position(2, 1))); // Left-up
            assertTrue(KnightTourSolver.isValidKnightMove(from, new ChessBoard.Position(2, 5))); // Right-up
            assertTrue(KnightTourSolver.isValidKnightMove(from, new ChessBoard.Position(4, 1))); // Left-down
            assertTrue(KnightTourSolver.isValidKnightMove(from, new ChessBoard.Position(4, 5))); // Right-down
            assertTrue(KnightTourSolver.isValidKnightMove(from, new ChessBoard.Position(5, 2))); // Down-left
            assertTrue(KnightTourSolver.isValidKnightMove(from, new ChessBoard.Position(5, 4))); // Down-right
        }
        
        @Test
        @DisplayName("Should reject invalid knight moves")
        void shouldRejectInvalidKnightMoves() {
            ChessBoard.Position from = new ChessBoard.Position(3, 3);
            
            // Test invalid moves
            assertFalse(KnightTourSolver.isValidKnightMove(from, new ChessBoard.Position(3, 3))); // Same position
            assertFalse(KnightTourSolver.isValidKnightMove(from, new ChessBoard.Position(3, 4))); // Adjacent
            assertFalse(KnightTourSolver.isValidKnightMove(from, new ChessBoard.Position(3, 5))); // Two squares straight
            assertFalse(KnightTourSolver.isValidKnightMove(from, new ChessBoard.Position(1, 3))); // Two squares straight
            assertFalse(KnightTourSolver.isValidKnightMove(from, new ChessBoard.Position(1, 1))); // Diagonal
            assertFalse(KnightTourSolver.isValidKnightMove(from, new ChessBoard.Position(0, 0))); // Far diagonal
        }
        
        @Test
        @DisplayName("Should get all possible moves from a position")
        void shouldGetAllPossibleMoves() {
            ChessBoard.Position center = new ChessBoard.Position(3, 3);
            List<ChessBoard.Position> moves = KnightTourSolver.getPossibleMoves(center);
            
            assertEquals(8, moves.size(), "Should have exactly 8 possible moves from center");
            
            // Verify all moves are valid knight moves
            for (ChessBoard.Position move : moves) {
                assertTrue(KnightTourSolver.isValidKnightMove(center, move), 
                    "Move " + move + " should be valid");
            }
        }
        
        @Test
        @DisplayName("Should handle corner positions correctly")
        void shouldHandleCornerPositions() {
            ChessBoard.Position corner = new ChessBoard.Position(0, 0);
            List<ChessBoard.Position> moves = KnightTourSolver.getPossibleMoves(corner);
            
            assertEquals(2, moves.size(), "Corner position should have 2 possible moves");
            
            // Verify the moves are correct
            assertTrue(moves.contains(new ChessBoard.Position(1, 2)));
            assertTrue(moves.contains(new ChessBoard.Position(2, 1)));
        }
        
        @Test
        @DisplayName("Should handle edge positions correctly")
        void shouldHandleEdgePositions() {
            ChessBoard.Position edge = new ChessBoard.Position(0, 3);
            List<ChessBoard.Position> moves = KnightTourSolver.getPossibleMoves(edge);
            
            assertEquals(4, moves.size(), "Edge position should have 4 possible moves");
            
            // Verify the moves are correct
            assertTrue(moves.contains(new ChessBoard.Position(1, 1)));
            assertTrue(moves.contains(new ChessBoard.Position(1, 5)));
            assertTrue(moves.contains(new ChessBoard.Position(2, 2)));
            assertTrue(moves.contains(new ChessBoard.Position(2, 4)));
        }
    }
    
    @Nested
    @DisplayName("ChessBoard Position Tests")
    class ChessBoardPositionTests {
        
        @Test
        @DisplayName("Should create position correctly")
        void shouldCreatePositionCorrectly() {
            ChessBoard.Position pos = new ChessBoard.Position(3, 4);
            
            assertEquals(3, pos.row);
            assertEquals(4, pos.col);
        }
        
        @Test
        @DisplayName("Should implement equals correctly")
        void shouldImplementEqualsCorrectly() {
            ChessBoard.Position pos1 = new ChessBoard.Position(3, 4);
            ChessBoard.Position pos2 = new ChessBoard.Position(3, 4);
            ChessBoard.Position pos3 = new ChessBoard.Position(4, 3);
            
            assertEquals(pos1, pos2);
            assertNotEquals(pos1, pos3);
            assertNotEquals(pos1, null);
            assertNotEquals(pos1, "not a position");
        }
        
        @Test
        @DisplayName("Should implement hashCode correctly")
        void shouldImplementHashCodeCorrectly() {
            ChessBoard.Position pos1 = new ChessBoard.Position(3, 4);
            ChessBoard.Position pos2 = new ChessBoard.Position(3, 4);
            ChessBoard.Position pos3 = new ChessBoard.Position(4, 3);
            
            assertEquals(pos1.hashCode(), pos2.hashCode());
            assertNotEquals(pos1.hashCode(), pos3.hashCode());
        }
        
        @Test
        @DisplayName("Should implement toString correctly")
        void shouldImplementToStringCorrectly() {
            ChessBoard.Position pos = new ChessBoard.Position(3, 4);
            assertEquals("(3, 4)", pos.toString());
        }
    }
    
    @Nested
    @DisplayName("ChessBoard State Tests")
    class ChessBoardStateTests {
        
        @Test
        @DisplayName("Should initialize with no knight")
        void shouldInitializeWithNoKnight() {
            assertFalse(chessBoard.hasKnight());
            assertNull(chessBoard.getKnightPosition());
        }
        
        @Test
        @DisplayName("Should place knight correctly")
        void shouldPlaceKnightCorrectly() {
            chessBoard.placeKnight(3, 4);
            
            assertTrue(chessBoard.hasKnight());
            ChessBoard.Position pos = chessBoard.getKnightPosition();
            assertEquals(3, pos.row);
            assertEquals(4, pos.col);
        }
        
        @Test
        @DisplayName("Should track path correctly")
        void shouldTrackPathCorrectly() {
            chessBoard.placeKnight(0, 0);
            List<ChessBoard.Position> path = chessBoard.getPath();
            
            assertEquals(1, path.size());
            assertEquals(new ChessBoard.Position(0, 0), path.get(0));
            
            chessBoard.moveKnight(2, 1);
            path = chessBoard.getPath();
            
            assertEquals(2, path.size());
            assertEquals(new ChessBoard.Position(0, 0), path.get(0));
            assertEquals(new ChessBoard.Position(2, 1), path.get(1));
        }
        
        @Test
        @DisplayName("Should reset correctly")
        void shouldResetCorrectly() {
            chessBoard.placeKnight(3, 4);
            chessBoard.moveKnight(1, 2);
            
            chessBoard.reset();
            
            assertFalse(chessBoard.hasKnight());
            assertNull(chessBoard.getKnightPosition());
            assertTrue(chessBoard.getPath().isEmpty());
        }
    }
    
    @Nested
    @DisplayName("Solver State Tests")
    class SolverStateTests {
        
        @Test
        @DisplayName("Should initialize with correct state")
        void shouldInitializeWithCorrectState() {
            assertFalse(solver.isAnimating());
            assertNull(solver.getSolution());
            assertEquals(0, solver.getSolutionLength());
        }
        
        @Test
        @DisplayName("Should reset correctly")
        void shouldResetCorrectly() {
            solver.reset();
            
            assertFalse(solver.isAnimating());
            assertNull(solver.getSolution());
            assertEquals(0, solver.getSolutionLength());
        }
    }
    
    @Nested
    @DisplayName("Integration Tests")
    class IntegrationTests {
        
        @Test
        @DisplayName("Should handle complete workflow")
        void shouldHandleCompleteWorkflow() {
            // Place knight
            chessBoard.placeKnight(0, 0);
            assertTrue(chessBoard.hasKnight());
            
            // Move knight
            chessBoard.moveKnight(2, 1);
            List<ChessBoard.Position> path = chessBoard.getPath();
            assertEquals(2, path.size());
            
            // Reset
            chessBoard.reset();
            assertFalse(chessBoard.hasKnight());
            assertTrue(chessBoard.getPath().isEmpty());
        }
        
        @Test
        @DisplayName("Should validate knight moves from different positions")
        void shouldValidateKnightMovesFromDifferentPositions() {
            // Test from various positions
            ChessBoard.Position[] positions = {
                new ChessBoard.Position(0, 0),  // Corner
                new ChessBoard.Position(0, 3),  // Edge
                new ChessBoard.Position(3, 3),  // Center
                new ChessBoard.Position(7, 7)   // Opposite corner
            };
            
            for (ChessBoard.Position pos : positions) {
                List<ChessBoard.Position> moves = KnightTourSolver.getPossibleMoves(pos);
                
                // Verify all moves are valid
                for (ChessBoard.Position move : moves) {
                    assertTrue(KnightTourSolver.isValidKnightMove(pos, move),
                        "Move from " + pos + " to " + move + " should be valid");
                }
                
                // Verify moves are within bounds
                for (ChessBoard.Position move : moves) {
                    assertTrue(move.row >= 0 && move.row < 8 && move.col >= 0 && move.col < 8,
                        "Move " + move + " should be within bounds");
                }
            }
        }
    }
}
