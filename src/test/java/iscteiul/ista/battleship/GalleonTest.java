package iscteiul.ista.battleship;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class GalleonTest {

    @Test
    @DisplayName("Criação da Galleon (NORTE)")
    void testGalleonNorth() {
        Galleon galleon = new Galleon(Compass.NORTH, new Position(0, 0));
        assertEquals(5, galleon.getSize());
        assertEquals("Galeao", galleon.getCategory());
        assertEquals(5, galleon.getPositions().size());
    }

    @Test
    @DisplayName("Criação da Galleon (SUL)")
    void testGalleonSouth() {
        Galleon galleon = new Galleon(Compass.SOUTH, new Position(1, 1));
        assertEquals(5, galleon.getPositions().size());
    }

    @Test
    @DisplayName("Criação da Galleon (ESTE)")
    void testGalleonEast() {
        Galleon galleon = new Galleon(Compass.EAST, new Position(3, 1));
        assertEquals(5, galleon.getPositions().size());
    }

    @Test
    @DisplayName("Criação da Galleon (OESTE)")
    void testGalleonWest() {
        Galleon galleon = new Galleon(Compass.WEST, new Position(2, 2));
        assertEquals(5, galleon.getPositions().size());
    }

    @Test
    @DisplayName("Galleon lança AssertionError para bearing nulo")
    void testGalleonNullBearingThrows() {
        Position pos = new Position(1, 1);
        assertThrows(AssertionError.class, () -> {
            new Galleon(null, pos);
        });
    }

    @Test
    @DisplayName("Galleon lança IllegalArgumentException para bearing inválido")
    void testGalleonInvalidBearingThrows() {
        Position pos = new Position(1, 1);
        assertThrows(IllegalArgumentException.class, () -> {
            new Galleon(Compass.valueOf("INVALID"), pos);
        });
    }

    @Test
    @DisplayName("Galleon ocupa e não ocupa posições")
    void testOccupiesPosition() {
        Galleon galleon = new Galleon(Compass.SOUTH, new Position(2, 2));
        assertTrue(galleon.occupies(new Position(2, 2)));
        assertFalse(galleon.occupies(new Position(7, 7)));
    }

    @Test
    @DisplayName("Testa extremos da Galleon")
    void testTopBottomLeftRightMost() {
        Galleon galleon = new Galleon(Compass.EAST, new Position(1, 2));
        int minRow = galleon.getPositions().stream().mapToInt(IPosition::getRow).min().orElse(-1);
        int maxRow = galleon.getPositions().stream().mapToInt(IPosition::getRow).max().orElse(-1);
        int minCol = galleon.getPositions().stream().mapToInt(IPosition::getColumn).min().orElse(-1);
        int maxCol = galleon.getPositions().stream().mapToInt(IPosition::getColumn).max().orElse(-1);
        assertEquals(minRow, galleon.getTopMostPos());
        assertEquals(maxRow, galleon.getBottomMostPos());
        assertEquals(minCol, galleon.getLeftMostPos());
        assertEquals(maxCol, galleon.getRightMostPos());
    }

    @Test
    @DisplayName("Galleon afunda após tiros nas posições")
    void testShootAndFloating() {
        Galleon galleon = new Galleon(Compass.NORTH, new Position(0, 0));
        assertTrue(galleon.stillFloating());
        for (IPosition p : galleon.getPositions()) {
            galleon.shoot(p);
        }
        assertFalse(galleon.stillFloating());
    }
    @Test
    @DisplayName("Galleon lança IllegalArgumentException para Compass.UNKNOWN")
    void testGalleonUnknownBearingThrows() {
        Position pos = new Position(1, 1);
        assertThrows(IllegalArgumentException.class, () -> {
            new Galleon(Compass.UNKNOWN, pos);
        });
    }

    @Test
    @DisplayName("Criação da Galleon (ESTE) com coluna ajustada para cobertura total")
    void testGalleonEastFullCoverage() {
        Galleon galleon = new Galleon(Compass.EAST, new Position(3, 3));
        assertEquals(5, galleon.getPositions().size());
    }
    @Test
    @DisplayName("Criação da Galleon (OESTE) com coluna ajustada para cobertura total")
    void testGalleonWestFullCoverage() {
        Galleon galleon = new Galleon(Compass.WEST, new Position(2, 1));
        assertEquals(5, galleon.getPositions().size());
    }


}
