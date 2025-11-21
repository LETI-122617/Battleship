package iscteiul.ista.battleship;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {
    @Test
    @DisplayName("Ship: stillFloating() returns true before any hit")
    void testStillFloatingInitially() {
        Ship s = new Barge(Compass.NORTH, new Position(3, 3));
        assertTrue(s.stillFloating(), "A new ship should be floating");
    }

    @Test
    @DisplayName("Ship: stillFloating() returns false after all positions are hit")
    void testStillFloatingAfterHit() {
        Ship s = new Barge(Compass.NORTH, new Position(3, 3));
        s.shoot(new Position(3, 3));
        assertFalse(s.stillFloating(), "A ship with all positions hit should not float");
    }

    @Test
    @DisplayName("Ship: occupies returns true for its positions")
    void testOccupies() {
        Ship s = new Barge(Compass.NORTH, new Position(2, 2));
        assertTrue(s.occupies(new Position(2, 2)));
        assertFalse(s.occupies(new Position(2, 3)));
    }

    @Test
    @DisplayName("Ship: top/bottom/left/right most positions computed correctly")
    void testExtremes() {
        Ship s = new Caravel(Compass.EAST, new Position(4, 4));
        // EAST Caravel occupies (4,4) and (4,5)

        assertEquals(4, s.getTopMostPos());
        assertEquals(4, s.getBottomMostPos());
        assertEquals(4, s.getLeftMostPos());
        assertEquals(5, s.getRightMostPos());
    }

    @Test
    @DisplayName("Ship: tooCloseTo returns true for adjacent ship")
    void testTooCloseToShip() {
        Ship s1 = new Barge(Compass.NORTH, new Position(5, 5));
        Ship s2 = new Barge(Compass.NORTH, new Position(5, 6)); // adjacent

        assertTrue(s1.tooCloseTo(s2));
    }

    @Test
    @DisplayName("Ship.buildShip: cria o tipo correto e devolve null para tipos inválidos")
    void testBuildShipFactory() {
        Position pos = new Position(0, 0);

        Ship barca   = Ship.buildShip("barca",   Compass.NORTH, pos);
        Ship caravela= Ship.buildShip("caravela",Compass.EAST,  pos);
        Ship nau     = Ship.buildShip("nau",     Compass.SOUTH, pos);
        Ship fragata = Ship.buildShip("fragata", Compass.WEST,  pos);
        Ship galeao  = Ship.buildShip("galeao",  Compass.NORTH, pos);
        Ship unknown = Ship.buildShip("submarino", Compass.NORTH, pos);

        assertTrue(barca    instanceof Barge);
        assertTrue(caravela instanceof Caravel);
        assertTrue(nau      instanceof Carrack);
        assertTrue(fragata  instanceof Frigate);
        assertTrue(galeao   instanceof Galleon);

        // tipo desconhecido deve devolver null
        assertNull(unknown);
    }

    @Test
    @DisplayName("Ship: tooCloseTo returns false for far ship")
    void testTooFarShip() {
        Ship s1 = new Barge(Compass.NORTH, new Position(1, 1));
        Ship s2 = new Barge(Compass.NORTH, new Position(8, 8));

        assertFalse(s1.tooCloseTo(s2));
    }

    @Test
    @DisplayName("Ship: toString() returns the expected formatted string")
    void testToStringMethod() {
        Ship s = new Barge(Compass.NORTH, new Position(2, 3));
        String expected = "[Barca n Linha = 2 Coluna = 3]";
        assertEquals(expected, s.toString());
    }

    @Test
    @DisplayName("Ship: shoot() marks the position as hit")
    void testShootMarksPosition() {
        Ship s = new Caravel(Compass.SOUTH, new Position(2, 3));
        // SOUTH Caravel occupies (2,3) and (3,3)

        s.shoot(new Position(3, 3));
        assertTrue(s.getPositions().get(1).isHit());
    }

}