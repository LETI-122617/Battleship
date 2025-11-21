package iscteiul.ista.battleship;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class CarrackTest {

    @Test
    @DisplayName("Criação correta da Carrack (SUL)")
    void testCarrackSouth() {
        Compass bearing = Compass.SOUTH;
        Position pos = new Position(5, 3);
        Carrack carrack = new Carrack(bearing, pos);
        assertEquals(3, carrack.getSize());
        assertEquals("Nau", carrack.getCategory());
        assertEquals(3, carrack.getPositions().size());
        assertEquals(5, carrack.getPositions().get(0).getRow());
        assertEquals(6, carrack.getPositions().get(1).getRow());
        assertEquals(7, carrack.getPositions().get(2).getRow());
    }

    @Test
    @DisplayName("Criação correta da Carrack (ESTE)")
    void testCarrackEast() {
        Compass bearing = Compass.EAST;
        Position pos = new Position(0, 0);
        Carrack carrack = new Carrack(bearing, pos);
        assertEquals(3, carrack.getPositions().size());
        assertEquals(0, carrack.getPositions().get(0).getColumn());
        assertEquals(1, carrack.getPositions().get(1).getColumn());
        assertEquals(2, carrack.getPositions().get(2).getColumn());
    }

    @Test
    @DisplayName("Carrack lança exceção para bearing inválido")
    void testCarrackInvalidBearingThrows() {
        Position pos = new Position(0, 0);
        assertThrows(IllegalArgumentException.class, () -> {
            new Carrack(Compass.valueOf("INVALID"), pos);
        });
    }

    @Test
    @DisplayName("Carrack lança exceção para bearing nulo")
    void testCarrackNullBearingThrows() {
        Position pos = new Position(1, 1);
        assertThrows(AssertionError.class, () -> {
            new Carrack(null, pos);
        });
    }

    @Test
    @DisplayName("Carrack lança exceção para posição nula")
    void testCarrackNullPositionThrows() {
        Compass bearing = Compass.EAST;
        assertThrows(AssertionError.class, () -> {
            new Carrack(bearing, null);
        });
    }

    @Test
    @DisplayName("Carrack ocupa posições corretamente")
    void testOccupiesPosition() {
        Compass bearing = Compass.NORTH;
        Position pos = new Position(2, 2);
        Carrack carrack = new Carrack(bearing, pos);
        assertTrue(carrack.occupies(new Position(3, 2)));
        assertFalse(carrack.occupies(new Position(1, 1)));
    }

    @Test
    @DisplayName("Testa extremos da Carrack")
    void testTopBottomLeftRightMost() {
        Compass bearing = Compass.WEST;
        Position pos = new Position(6, 1);
        Carrack carrack = new Carrack(bearing, pos);
        assertEquals(6, carrack.getTopMostPos());
        assertEquals(6, carrack.getBottomMostPos());
        assertEquals(1, carrack.getLeftMostPos());
        assertEquals(3, carrack.getRightMostPos());
    }

    @Test
    @DisplayName("Testa proximidade de posição")
    void testTooCloseToIPosition() {
        Compass bearing = Compass.EAST;
        Position pos = new Position(3, 2);
        Carrack carrack = new Carrack(bearing, pos);
        Position close = new Position(3, 5); // adjacente
        Position far = new Position(9, 9);
        assertTrue(carrack.tooCloseTo(close));
        assertFalse(carrack.tooCloseTo(far));
    }

    @Test
    @DisplayName("Testa proximidade de outro barco")
    void testTooCloseToIShip() {
        Compass bearing = Compass.SOUTH;
        Position posA = new Position(2, 2);
        Position posB = new Position(4, 2);
        Carrack carrackA = new Carrack(bearing, posA);
        Carrack carrackB = new Carrack(bearing, posB);
        assertTrue(carrackA.tooCloseTo(carrackB));
    }


    @Test
    @DisplayName("Carrack responde a tiro")
    void testShootAndFloating() {
        Compass bearing = Compass.SOUTH;
        Position pos = new Position(1, 1);
        Carrack carrack = new Carrack(bearing, pos);
        assertTrue(carrack.stillFloating());
        carrack.shoot(new Position(1, 1));
        carrack.shoot(new Position(2, 1));
        carrack.shoot(new Position(3, 1));
        assertFalse(carrack.stillFloating());
    }
}
