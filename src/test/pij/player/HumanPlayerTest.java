package pij.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HumanPlayerTest {

    private HumanPlayer player;

    @BeforeEach
    public void setUp() {
        player = new HumanPlayer("Test Player");
    }

    @Test
    public void testPassMoveIsValid() {
        assertTrue(player.isValidMoveFormat(","));
    }

    @Test
    public void testValidFormatDownward() {
        assertTrue(player.isValidMoveFormat("HELLO,d5"));
    }

    @Test
    public void testValidFormatHorizontal() {
        assertTrue(player.isValidMoveFormat("HELLO,5d"));
    }

    @Test
    public void testValidFormatWithWildcard() {
        assertTrue(player.isValidMoveFormat("HELLo,d4"));
    }

    @Test
    public void testInvalidFormatNoComma() {
        assertFalse(player.isValidMoveFormat("HELLOd4"));
    }

    @Test
    public void testInvalidFormatTooManyCommas() {
        assertFalse(player.isValidMoveFormat("HE,LLO,d4"));
    }

    @Test
    public void testInvalidFormatWordWithNumbers() {
        assertFalse(player.isValidMoveFormat("HEL1O,d4"));
    }

    @Test
    public void testInvalidFormatPositionNoLetter() {
        assertFalse(player.isValidMoveFormat("HELLO,44"));
    }

    @Test
    public void testInvalidFormatPositionNoNumber() {
        assertFalse(player.isValidMoveFormat("HELLO,dd"));
    }

    @Test
    public void testInvalidFormatPositionUppercaseLetter() {
        assertFalse(player.isValidMoveFormat("HELLO,D4"));
    }


    @Test
    public void testInvalidEmptyInput() {
        assertFalse(player.isValidMoveFormat(""));
    }
}