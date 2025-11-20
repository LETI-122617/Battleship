package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CompassTest {

    @Test
    @DisplayName("Teste 1: Conversão de char para Compass (Norte, Sul, Este, Oeste).")
    void testCharToCompassValid() {
        // Verifica se as letras correspondem às direções corretas
        assertEquals(Compass.NORTH, Compass.charToCompass('n'), "Char 'n' deve ser NORTH");
        assertEquals(Compass.SOUTH, Compass.charToCompass('s'), "Char 's' deve ser SOUTH");
        assertEquals(Compass.EAST, Compass.charToCompass('e'), "Char 'e' deve ser EAST");
        assertEquals(Compass.WEST, Compass.charToCompass('o'), "Char 'o' deve ser WEST");
    }

    @Test
    @DisplayName("Teste 2: Conversão de char inválido (Limites).")
    void testCharToCompassInvalid() {
        // Caracteres inválidos devem retornar UNKNOWN
        assertEquals(Compass.UNKNOWN, Compass.charToCompass('x'));
        assertEquals(Compass.UNKNOWN, Compass.charToCompass(' '));
        assertEquals(Compass.UNKNOWN, Compass.charToCompass('1'));
    }

    @Test
    @DisplayName("Teste 3: Verificar o caracter associado (Getter).")
    void testGetDirection() {
        // Verifica se o getter devolve o char correto configurado no Enum
        assertEquals('n', Compass.NORTH.getDirection());
        assertEquals('s', Compass.SOUTH.getDirection());
        assertEquals('e', Compass.EAST.getDirection());
        assertEquals('o', Compass.WEST.getDirection());
        assertEquals('u', Compass.UNKNOWN.getDirection());
    }
}