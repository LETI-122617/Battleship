package iscteiul.ista.battleship;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

class FleetTest {

    @Nested
    @DisplayName("Impressão e Relatórios")
    class PrintTests {
    @Test
    @DisplayName("Fleet: printAllShips executa sem lançar exceções")
    void testPrintAllShips() {
        Fleet fleet = new Fleet();
        fleet.addShip(new Barge(Compass.NORTH, new Position(0, 0)));
        fleet.addShip(new Caravel(Compass.EAST, new Position(2, 2)));

        assertDoesNotThrow(fleet::printAllShips);
    }

    @Test
    @DisplayName("Fleet: printFloatingShips executa sem lançar exceções")
    void testPrintFloatingShipsMethod() {
        Fleet fleet = new Fleet();
        Barge floating = new Barge(Compass.NORTH, new Position(1, 1));
        Barge sunk = new Barge(Compass.NORTH, new Position(3, 3));

        assertTrue(fleet.addShip(floating));
        assertTrue(fleet.addShip(sunk));

        // afundar o segundo
        sunk.shoot(new Position(3, 3));

        assertDoesNotThrow(fleet::printFloatingShips);
    }

    @Test
    @DisplayName("Fleet: printShipsByCategory executa sem lançar exceções")
    void testPrintShipsByCategory() {
        Fleet fleet = new Fleet();
        fleet.addShip(new Barge(Compass.NORTH, new Position(0, 0)));
        fleet.addShip(new Caravel(Compass.EAST, new Position(2, 2)));

        assertDoesNotThrow(() -> fleet.printShipsByCategory("Barca"));
        assertDoesNotThrow(() -> fleet.printShipsByCategory("Caravela"));
        // categoria sem navios também não deve rebentar
        assertDoesNotThrow(() -> fleet.printShipsByCategory("Galeao"));
    }

    @Test
    @DisplayName("Fleet: printStatus executa sem lançar exceções")
    void testPrintStatus() {
        Fleet fleet = new Fleet();
        fleet.addShip(new Barge(Compass.NORTH, new Position(0, 0)));
        fleet.addShip(new Caravel(Compass.EAST, new Position(2, 2)));
        fleet.addShip(new Galleon(Compass.SOUTH, new Position(4, 1)));

        assertDoesNotThrow(fleet::printStatus);
    }

    @Test
    @DisplayName("Fleet: printShips (método estático) executa sem lançar exceções")
    void testStaticPrintShips() {
        ArrayList<IShip> ships = new ArrayList<>();
        ships.add(new Barge(Compass.NORTH, new Position(0, 0)));
        ships.add(new Caravel(Compass.EAST, new Position(1, 1)));

        assertDoesNotThrow(() -> Fleet.printShips(ships));
    }
    }

    @Nested
    @DisplayName("Gestão de Frota (Adicionar)")
    class ManagementTests {
    @Test
    @DisplayName("Fleet: addShip accepts a valid ship inside the board")
    void testAddValidShip() {
        Fleet fleet = new Fleet();
        IShip barge = new Barge(Compass.NORTH, new Position(0, 0));

        boolean added = fleet.addShip(barge);

        assertTrue(added, "Valid ship should be added");
        assertEquals(1, fleet.getShips().size());
        assertSame(barge, fleet.getShips().get(0));
    }

    @Test
    @DisplayName("Fleet: addShip rejects ship that does not fit inside board")
    void testAddShipOutsideBoard() {
        Fleet fleet = new Fleet();
        // Caravel EAST at (0,9) would occupy (0,9) and (0,10) -> fora do tabuleiro
        IShip caravelOutside = new Caravel(Compass.EAST, new Position(0, 9));

        boolean added = fleet.addShip(caravelOutside);

        assertFalse(added, "Ship that goes outside the board must not be added");
        assertTrue(fleet.getShips().isEmpty());
    }

    @Test
    @DisplayName("Fleet: addShip rejects ship that is too close to an existing one")
    void testAddShipTooClose() {
        Fleet fleet = new Fleet();
        IShip first = new Barge(Compass.NORTH, new Position(5, 5));
        IShip tooClose = new Barge(Compass.NORTH, new Position(5, 6)); // adjacente

        assertTrue(fleet.addShip(first), "First ship should be added");
        boolean addedSecond = fleet.addShip(tooClose);

        assertFalse(addedSecond, "Second ship too close should be rejected");
        assertEquals(1, fleet.getShips().size());
    }
    }

    @Nested
    @DisplayName("Pesquisa e Consulta")
    class QueryTests {
    @Test
    @DisplayName("Fleet: getShipsLike returns only ships of the requested category")
    void testGetShipsLike() {
        Fleet fleet = new Fleet();
        IShip barge1 = new Barge(Compass.NORTH, new Position(0, 0));
        IShip barge2 = new Barge(Compass.NORTH, new Position(0, 2));
        IShip caravel = new Caravel(Compass.SOUTH, new Position(3, 3));

        assertTrue(fleet.addShip(barge1));
        assertTrue(fleet.addShip(barge2));
        assertTrue(fleet.addShip(caravel));

        List<IShip> barges = fleet.getShipsLike("Barca");
        List<IShip> caravels = fleet.getShipsLike("Caravela");

        assertEquals(2, barges.size(), "There should be two Barges");
        assertTrue(barges.contains(barge1) && barges.contains(barge2));
        assertEquals(1, caravels.size(), "There should be one Caravel");
        assertTrue(caravels.contains(caravel));
    }

    @Test
    @DisplayName("Fleet: getFloatingShips returns only ships that are still floating")
    void testGetFloatingShips() {
        Fleet fleet = new Fleet();
        Barge floating = new Barge(Compass.NORTH, new Position(1, 1));
        Barge toSink = new Barge(Compass.NORTH, new Position(3, 3));

        assertTrue(fleet.addShip(floating));
        assertTrue(fleet.addShip(toSink));

        // No tiros ainda → ambos a flutuar
        List<IShip> initialFloating = fleet.getFloatingShips();
        assertEquals(2, initialFloating.size());

        // Afundar o segundo
        toSink.shoot(new Position(3, 3));

        List<IShip> afterHit = fleet.getFloatingShips();
        assertEquals(1, afterHit.size(), "Only one ship should be floating after hit");
        assertTrue(afterHit.contains(floating));
        assertFalse(afterHit.contains(toSink));
    }

    @Test
    @DisplayName("Fleet: shipAt returns the ship at a given position or null if none")
    void testShipAt() {
        Fleet fleet = new Fleet();
        Barge barge = new Barge(Compass.NORTH, new Position(2, 2));
        Caravel caravel = new Caravel(Compass.EAST, new Position(4, 4)); // (4,4) e (4,5)

        assertTrue(fleet.addShip(barge));
        assertTrue(fleet.addShip(caravel));

        IShip ship1 = fleet.shipAt(new Position(2, 2));
        IShip ship2 = fleet.shipAt(new Position(4, 5));
        IShip none = fleet.shipAt(new Position(0, 0));

        assertSame(barge, ship1, "Barge should be found at its position");
        assertSame(caravel, ship2, "Caravel should be found at one of its positions");
        assertNull(none, "shipAt should return null when there is no ship");
    }
    }
}