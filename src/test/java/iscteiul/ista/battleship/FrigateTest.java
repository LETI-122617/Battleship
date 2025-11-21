package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FrigateTest {

    @Nested
    @DisplayName("Geometria e Orientação")
    class GeometryTests {
    @Test
    @DisplayName("Frigate: correct size and category")
    void testBasicProperties() {
        Frigate f = new Frigate(Compass.NORTH, new Position(2, 2));
        assertEquals(4, f.getSize());
        assertEquals("Fragata", f.getCategory());
    }

    @Test
    @DisplayName("Frigate: NORTH orientation produces correct positions")
    void testNorthOrientation() {
        Frigate f = new Frigate(Compass.NORTH, new Position(3, 3));
        List<IPosition> pos = f.getPositions();

        assertEquals(4, pos.size());
        assertEquals(3, pos.get(0).getRow());
        assertEquals(4, pos.get(1).getRow());  // updated
        assertEquals(5, pos.get(2).getRow());  // updated
        assertEquals(6, pos.get(3).getRow());  // updated
        // All share same column
        assertTrue(pos.stream().allMatch(p -> p.getColumn() == 3));
    }
    @Test
    @DisplayName("Frigate: SOUTH orientation produces correct positions")
    void testSouthOrientation() {
        Frigate f = new Frigate(Compass.SOUTH, new Position(1, 4));
        List<IPosition> pos = f.getPositions();

        assertEquals(4, pos.size());
        assertEquals(1, pos.get(0).getRow());
        assertEquals(2, pos.get(1).getRow());
        assertEquals(3, pos.get(2).getRow());
        assertEquals(4, pos.get(3).getRow());
        assertTrue(pos.stream().allMatch(p -> p.getColumn() == 4));
    }

    @Test
    @DisplayName("Frigate: EAST orientation produces correct positions")
    void testEastOrientation() {
        Frigate f = new Frigate(Compass.EAST, new Position(5, 2));
        List<IPosition> pos = f.getPositions();

        assertEquals(4, pos.size());
        // all share the same row
        assertTrue(pos.stream().allMatch(p -> p.getRow() == 5));

        assertEquals(2, pos.get(0).getColumn());
        assertEquals(3, pos.get(1).getColumn());
        assertEquals(4, pos.get(2).getColumn());
        assertEquals(5, pos.get(3).getColumn());
    }

    @Test
    @DisplayName("Frigate: WEST orientation produces correct positions")
    void testWestOrientation() {
        Frigate f = new Frigate(Compass.WEST, new Position(6, 6));
        List<IPosition> pos = f.getPositions();

        assertEquals(4, pos.size());
        assertTrue(pos.stream().allMatch(p -> p.getRow() == 6));

        assertEquals(6, pos.get(0).getColumn());
        assertEquals(7, pos.get(1).getColumn());
        assertEquals(8, pos.get(2).getColumn());
        assertEquals(9, pos.get(3).getColumn());
    }

    @Test
    @DisplayName("Frigate: invalid bearing throws IllegalArgumentException")
    void testInvalidBearing() {
        assertThrows(IllegalArgumentException.class,
                () -> new Frigate(Compass.UNKNOWN, new Position(0, 0)));
    }
    }

    @Nested
    @DisplayName("Estado do Navio")
    class StateTests {
    @Test
    @DisplayName("Frigate: stillFloating behavior works")
    void testStillFloating() {
        Frigate f = new Frigate(Compass.EAST, new Position(3, 3));
        assertTrue(f.stillFloating());

        f.shoot(new Position(3, 4));
        assertTrue(f.stillFloating(), "One hit still leaves the ship floating");

        // hit all cells
        for (IPosition p : f.getPositions()) f.shoot(new Position(p.getRow(), p.getColumn()));
        assertFalse(f.stillFloating(), "All positions hit => not floating");
    }

    @Test
    @DisplayName("Frigate: occupies works for all its cells")
    void testOccupies() {
        Frigate f = new Frigate(Compass.EAST, new Position(2, 2));

        assertTrue(f.occupies(new Position(2,2)));
        assertTrue(f.occupies(new Position(2,3)));
        assertTrue(f.occupies(new Position(2,4)));
        assertTrue(f.occupies(new Position(2,5)));

        assertFalse(f.occupies(new Position(2,6)));
    }
    }
}