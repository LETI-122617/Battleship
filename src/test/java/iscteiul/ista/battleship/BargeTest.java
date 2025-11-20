package iscteiul.ista.battleship;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class BargeTest {


    @Test
    @DisplayName("Criação correta da Barge")
    void testBargeCreation() {
        Compass bearing = Compass.NORTH;
        Position pos = new Position(0, 0);
        Barge barge = new Barge(bearing, pos);
        assertEquals(1, barge.getSize());
        assertEquals("Barca", barge.getCategory());
        assertEquals(1, barge.getPositions().size());
        assertEquals(pos.getRow(), barge.getPositions().get(0).getRow());
        assertEquals(pos.getColumn(), barge.getPositions().get(0).getColumn());
        assertEquals(bearing, barge.getBearing());
        assertEquals(pos, barge.getPosition());
        assertTrue(barge.stillFloating());

    }

    @Test//teste
    @DisplayName("Barge ocupa posição corretamente")
    void testOccupiesPosition() {
        Compass bearing =Compass.NORTH;
        Position pos = new Position(2, 2);
        Barge barge = new Barge(bearing, pos);
        Position p1 = new Position(2, 2);
        Position p2 = new Position(1, 1);
        assertTrue(barge.occupies(p1));
        assertFalse(barge.occupies(p2));
    }


    @Test
    @DisplayName("Barge responde a tiro e deixa de flutuar")
    void testShootAndFloating() {
        Compass bearing = Compass.NORTH;
        Position pos = new Position(1, 1);
        Barge barge = new Barge(bearing, pos);
        assertTrue(barge.stillFloating());
        barge.shoot(new Position(1, 1));
        assertFalse(barge.stillFloating());
    }

    @Test
    @DisplayName("Testa valores extremos de Ship")
    void testTopBottomLeftRightMost() {
        Compass bearing = Compass.NORTH;
        Position pos = new Position(2, 3);
        Barge barge = new Barge(bearing, pos);
        assertEquals(2, barge.getTopMostPos());
        assertEquals(2, barge.getBottomMostPos());
        assertEquals(3, barge.getLeftMostPos());
        assertEquals(3, barge.getRightMostPos());
    }

    @Test
    @DisplayName("Testa proximidade de outras posições")
    void testTooCloseToIPosition() {
        Compass bearing = Compass.NORTH;
        Position pos = new Position(4, 4);
        Barge barge = new Barge(bearing, pos);
        Position close = new Position(4, 5); // adjacente
        Position far = new Position(6, 6);
        assertTrue(barge.tooCloseTo(close));
        assertFalse(barge.tooCloseTo(far));
    }

    @Test
    @DisplayName("Testa erro no construtor com bearing nulo")
    void testBargeNullBearingThrows() {
        Position pos = new Position(0, 0);
        assertThrows(AssertionError.class, () -> {
            new Barge(null, pos);
        });
    }

    @Test
    @DisplayName("Testa erro no construtor com posição nula")
    void testBargeNullPositionThrows() {
        Compass bearing = Compass.NORTH;
        assertThrows(AssertionError.class, () -> {
            new Barge(bearing, null);
        });
    }

    @Test
    @DisplayName("Testa que não é demasiado próximo de barge igual")
    void testTooCloseToIShipSelf() {
        Compass bearing = Compass.NORTH;
        Position pos = new Position(7, 7);
        Barge barge1 = new Barge(bearing, pos);
        Barge barge2 = new Barge(bearing, new Position(7, 7)); // mesma posição
        assertTrue(barge1.tooCloseTo(barge2));
    }

    @Test
    @DisplayName("Testa equals da posição")
    void testPositionEquals() {
        Position pos1 = new Position(3, 4);
        Position pos2 = new Position(3, 4);
        Position pos3 = new Position(5, 6);
        assertTrue(pos1.equals(pos2));
        assertFalse(pos1.equals(pos3));
    }
}
