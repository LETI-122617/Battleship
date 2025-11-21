import static org.junit.jupiter.api.Assertions.*;
class FrigateTest {
  package iscteiul.ista.battleship;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class FrigateTest {

    @Test
    @DisplayName("Criação da Frigate (NORTE)")
    void testFrigateNorth() {
        Compass bearing = Compass.NORTH;
        Position pos = new Position(0, 2);
        Frigate frigate = new Frigate(bearing, pos);
        assertEquals(4, frigate.getSize());
        assertEquals("Fragata", frigate.getCategory());
        assertEquals(4, frigate.getPositions().size());
        for (int i = 0; i < 4; i++) {
            assertEquals(0 + i, frigate.getPositions().get(i).getRow());
            assertEquals(2, frigate.getPositions().get(i).getColumn());
        }
    }

    @Test
    @DisplayName("Criação da Frigate (SUL)")
    void testFrigateSouth() {
        Compass bearing = Compass.SOUTH;
        Position pos = new Position(5, 1);
        Frigate frigate = new Frigate(bearing, pos);
        for (int i = 0; i < 4; i++) {
            assertEquals(5 + i, frigate.getPositions().get(i).getRow());
            assertEquals(1, frigate.getPositions().get(i).getColumn());
        }
    }

    @Test
    @DisplayName("Criação da Frigate (ESTE)")
    void testFrigateEast() {
        Compass bearing = Compass.EAST;
        Position pos = new Position(2, 1);
        Frigate frigate = new Frigate(bearing, pos);
        for (int i = 0; i < 4; i++) {
            assertEquals(1 + i, frigate.getPositions().get(i).getColumn());
            assertEquals(2, frigate.getPositions().get(i).getRow());
        }
    }

    @Test
    @DisplayName("Criação da Frigate (OESTE)")
    void testFrigateWest() {
        Compass bearing = Compass.WEST;
        Position pos = new Position(0, 7);
        Frigate frigate = new Frigate(bearing, pos);
        for (int i = 0; i < 4; i++) {
            assertEquals(7 + i, frigate.getPositions().get(i).getColumn());
            assertEquals(0, frigate.getPositions().get(i).getRow());
        }
    }

    @Test
    @DisplayName("Frigate lança exceção para bearing inválido")
    void testFrigateInvalidBe Position(0, 0);
        assertThrows(IllegaaringThrows() {
        Position pos = newlArgumentException.class, () -> {
            new Frigate(Compass.valueOf("INVALID"), pos);
        });
    }

    @Test
    @DisplayName("Frigate lança exceção para bearing nulo")
    void testFrigateNullBearingThrows() {
        Position pos = new Position(0, 1);
        assertThrows(AssertionError.class, () -> {
            new Frigate(null, pos);
        });
    }


    @Test
    @DisplayName("Frigate ocupa posições corretamente")
    void testOccupiesPosition() {
        Compass bearing = Compass.EAST;
        Position pos = new Position(1, 1);
        Frigate frigate = new Frigate(bearing, pos);
        assertTrue(frigate.occupies(new Position(1, 2)));
        assertFalse(frigate.occupies(new Position(0, 0)));
    }

    @Test
    @DisplayName("Testa extremos da Frigate")
    void testTopBottomLeftRightMost() {
        Compass bearing = Compass.WEST;
        Position pos = new Position(5, 0);
        Frigate frigate = new Frigate(bearing, pos);
        assertEquals(5, frigate.getTopMostPos());
        assertEquals(5, frigate.getBottomMostPos());
        assertEquals(0, frigate.getLeftMostPos());
        assertEquals(3, frigate.getRightMostPos());
    }

    @Test
    @DisplayName("Frigate responde a tiro")
    void testShootAndFloating() {
        Compass bearing = Compass.NORTH;
        Position pos = new Position(2, 3);
        Frigate frigate = new Frigate(bearing, pos);
        assertTrue(frigate.stillFloating());
        for (int i = 0; i < 4; i++) {
            frigate.shoot(new Position(2 + i, 3));
        }
        assertFalse(frigate.stillFloating());
    }
}
}
