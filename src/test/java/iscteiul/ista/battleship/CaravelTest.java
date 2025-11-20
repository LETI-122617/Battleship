package iscteiul.ista.battleship;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class CaravelTest {

    @Test
    @DisplayName("Criação correta da Caravel (NORTE)")
    void testCaravelNorth() {
        Compass bearing = Compass.NORTH;
        Position pos = new Position(2, 2);
        Caravel caravel = new Caravel(bearing, pos);
        assertEquals(2, caravel.getSize());
        assertEquals("Caravela", caravel.getCategory());
        assertEquals(2, caravel.getPositions().size());
        assertEquals(2, caravel.getPositions().get(0).getRow());
        assertEquals(3, caravel.getPositions().get(1).getRow());
        assertEquals(pos.getColumn(), caravel.getPositions().get(0).getColumn());
    }
//teste

    @Test
    @DisplayName("Criação correta da Caravel (ESTE)")
    void testCaravelEast() {
        Compass bearing = Compass.EAST;
        Position pos = new Position(3, 1);
        Caravel caravel = new Caravel(bearing, pos);
        assertEquals(2, caravel.getSize());
        assertEquals(2, caravel.getPositions().size());
        assertEquals(1, caravel.getPositions().get(0).getColumn());
        assertEquals(2, caravel.getPositions().get(1).getColumn());
    }

    @Test
    @DisplayName("Caravel lança exceção para bearing nulo")
    void testCaravelNullBearingThrows() {
        Position pos = new Position(1, 1);
        assertThrows(AssertionError.class, () -> {
            new Caravel(null, pos);
        });
    }


    @Test
    @DisplayName("Caravel lança exceção para bearing inválido")
    void testCaravelInvalidBearingThrows() {
        Position pos = new Position(1, 1);
        assertThrows(IllegalArgumentException.class, () -> {
            new Caravel(Compass.valueOf("INVALID"), pos);
        });
    }

    @Test
    @DisplayName("Caravel ocupa posições corretamente")
    void testOccupiesPosition() {
        Compass bearing = Compass.WEST;
        Position pos = new Position(0, 0);
        Caravel caravel = new Caravel(bearing, pos);
        assertTrue(caravel.occupies(new Position(0, 1)));
        assertFalse(caravel.occupies(new Position(2, 2)));
    }

    @Test
    @DisplayName("Testa extremos da Caravel")
    void testTopBottomLeftRightMost() {
        Compass bearing = Compass.SOUTH;
        Position pos = new Position(5, 7);
        Caravel caravel = new Caravel(bearing, pos);
        assertEquals(5, caravel.getTopMostPos());
        assertEquals(6, caravel.getBottomMostPos());
        assertEquals(7, caravel.getLeftMostPos());
        assertEquals(7, caravel.getRightMostPos());
    }

    @Test
    @DisplayName("Testa proximidade de posição para Caravel")
    void testTooCloseToIPosition() {
        Compass bearing = Compass.SOUTH;
        Position pos = new Position(3, 1);
        Caravel caravel = new Caravel(bearing, pos);
        Position close = new Position(3, 2); // adjacente
        Position far = new Position(7, 1);
        assertTrue(caravel.tooCloseTo(close));
        assertFalse(caravel.tooCloseTo(far));
    }

    @Test
    @DisplayName("Testa proximidade de outro barco")
    void testTooCloseToIShip() {
        Compass bearing = Compass.EAST;
        Position posA = new Position(4, 4);
        Position posB = new Position(4, 5);
        Caravel caravelA = new Caravel(bearing, posA);
        Caravel caravelB = new Caravel(bearing, posB);
        assertTrue(caravelA.tooCloseTo(caravelB));
    }

    @Test
    @DisplayName("Caravel responde a tiro")
    void testShootAndFloating() {
        Compass bearing = Compass.EAST;
        Position pos = new Position(8, 8);
        Caravel caravel = new Caravel(bearing, pos);
        assertTrue(caravel.stillFloating());
        caravel.shoot(new Position(8, 9)); // acerta numa posição
        caravel.shoot(new Position(8, 8)); // acerta noutra posição
        assertFalse(caravel.stillFloating());
    }
}
