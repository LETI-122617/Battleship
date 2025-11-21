package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarrackTest {

    @Test
    @DisplayName("Carrack: correct size and category")
    void testBasicProperties() {
        Carrack c = new Carrack(Compass.NORTH, new Position(2, 2));
        assertEquals(3, c.getSize());
        assertEquals("Nau", c.getCategory());
    }

    @Test
    @DisplayName("Carrack: NORTH orientation produces correct positions")
    void testNorthOrientation() {
        Carrack c = new Carrack(Compass.NORTH, new Position(3, 4));
        List<IPosition> pos = c.getPositions();

        assertEquals(3, pos.size());
        assertEquals(3, pos.get(0).getRow());
        assertEquals(4, pos.get(1).getRow());
        assertEquals(5, pos.get(2).getRow());
        assertTrue(pos.stream().allMatch(p -> p.getColumn() == 4));
    }

    @Test
    @DisplayName("Carrack: EAST orientation produces correct positions")
    void testEastOrientation() {
        Carrack c = new Carrack(Compass.EAST, new Position(5, 3));
        List<IPosition> pos = c.getPositions();

        assertEquals(3, pos.size());
        assertTrue(pos.stream().allMatch(p -> p.getRow() == 5));
        assertEquals(3, pos.get(0).getColumn());
        assertEquals(4, pos.get(1).getColumn());
        assertEquals(5, pos.get(2).getColumn());
    }

    @Test
    @DisplayName("Carrack: SOUTH orientation produces correct positions")
    void testSouthOrientation() {
        Carrack c = new Carrack(Compass.SOUTH, new Position(1, 6));
        List<IPosition> pos = c.getPositions();

        assertEquals(3, pos.size());
        assertEquals(1, pos.get(0).getRow());
        assertEquals(2, pos.get(1).getRow());
        assertEquals(3, pos.get(2).getRow());
        assertTrue(pos.stream().allMatch(p -> p.getColumn() == 6));
    }

    @Test
    @DisplayName("Carrack: WEST orientation produces correct positions")
    void testWestOrientation() {
        Carrack c = new Carrack(Compass.WEST, new Position(4, 5));
        List<IPosition> pos = c.getPositions();

        assertEquals(3, pos.size());
        assertTrue(pos.stream().allMatch(p -> p.getRow() == 4));

        assertEquals(5, pos.get(0).getColumn());
        assertEquals(6, pos.get(1).getColumn());  // corrected
        assertEquals(7, pos.get(2).getColumn());  // corrected
    }

    @Test
    @DisplayName("Carrack: occupies returns true for all its positions and false for outside")
    void testOccupies() {
        Carrack c = new Carrack(Compass.SOUTH, new Position(2, 2));
        assertTrue(c.occupies(new Position(2, 2)));
        assertTrue(c.occupies(new Position(3, 2)));
        assertTrue(c.occupies(new Position(4, 2)));
        assertFalse(c.occupies(new Position(5, 2)));
    }

    @Test
    @DisplayName("Carrack: stillFloating logic works")
    void testStillFloatingAndShoot() {
        Carrack c = new Carrack(Compass.WEST, new Position(6, 6));
        assertTrue(c.stillFloating());

        // Hit one position
        c.shoot(new Position(6,6));
        assertTrue(c.stillFloating(), "With one hit, the ship should still float");

        // Hit all positions
        for (IPosition p : c.getPositions()) {
            c.shoot(new Position(p.getRow(), p.getColumn()));
        }
        assertFalse(c.stillFloating(), "With all positions hit, the ship should not float");
    }

    @Test
    @DisplayName("Carrack: invalid bearing throws IllegalArgumentException")
    void testInvalidBearingThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new Carrack(Compass.UNKNOWN, new Position(0, 0)));
    }
}