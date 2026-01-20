package pij.board;


import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


public class MoveValidatorTest {

    @Test
    public void validMoveLowerCaseTest() throws IOException {
        MoveValidator move = new MoveValidator();
        assertTrue(move.isValidWord("dined"));
    }
    @Test
    public void validMoveUpperCaseTest() throws IOException {
        MoveValidator move = new MoveValidator();
        assertTrue(move.isValidWord("DINED"));
    }
    @Test
    public void validMoveMixedCaseTest() throws IOException {
        MoveValidator move = new MoveValidator();
        assertTrue(move.isValidWord("diNed"));
    }
    @Test
    public void validMoveInvalidWordTest() throws IOException {
        MoveValidator move = new MoveValidator();
        assertFalse(move.isValidWord("dined12"));
    }
}
