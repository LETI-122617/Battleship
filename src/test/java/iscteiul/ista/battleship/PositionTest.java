package iscteiul.ista.battleship;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

// O IntelliJ cria por vezes 'import org.junit.Test', podes apagar e usar o 'jupiter.api.Test'

class PositionTest {

    @Test
    @DisplayName("Teste 1: Criação e verificação de coordenadas da posição.")
    void testPositionCreationAndCoordinates() {
        // 1. Arrange (Preparação)
        int linhaEsperada = 5;
        int colunaEsperada = 3;

        // 2. Act (Execução)
        Position posicao = new Position(linhaEsperada, colunaEsperada);

        // 3. Assert (Verificação) - Utilizamos as asserções [cite: 90]
        assertEquals(linhaEsperada, posicao.getRow(), "A linha deve ser 5.");
        assertEquals(colunaEsperada, posicao.getColumn(), "A coluna deve ser 3.");


    }
    @Test
    @DisplayName("Teste 2: Mudança de estado da posição (Ocupar e Acertar).")
    void testPositionStateChanges() {
        // 1. Arrange
        Position posicao = new Position(1, 1);

        // 2. Assert Estado Inicial
        assertFalse(posicao.isOccupied(), "Inicialmente, não deve estar ocupada.");
        assertFalse(posicao.isHit(), "Inicialmente, não deve ter sido atingida.");

        // 3. Act & Assert: Ocupar
        posicao.occupy(); // Linha 'occupy()' fica verde
        assertTrue(posicao.isOccupied(), "Deve estar ocupada após chamar occupy().");

        // 4. Act & Assert: Acertar
        posicao.shoot(); // Linha 'shoot()' fica verde
        assertTrue(posicao.isHit(), "Deve ter sido atingida após chamar shoot().");
    }

    @Test
    @DisplayName("Teste 3: Verificação de Adjacência.")
    void testPositionIsAdjacentTo() {
        Position centro = new Position(5, 5);

        // Caso 1: Vizinhança Direta (Deve ser adjacente - TRUE)
        Position vizinho = new Position(5, 6);
        assertTrue(centro.isAdjacentTo(vizinho), "5,6 deve ser adjacente a 5,5 (horizontal).");

        // Caso 2: Posição Longe (Não deve ser adjacente - FALSE)
        Position longe = new Position(8, 8);
        assertFalse(centro.isAdjacentTo(longe), "8,8 não deve ser adjacente a 5,5.");

        // Caso 3: Ela Própria (Não deve ser adjacente - FALSE, se a lógica o impedir)
        assertFalse(centro.isAdjacentTo(centro), "Uma posição não é adjacente a si própria.");

    }
}