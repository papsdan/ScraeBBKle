package pij.square;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class SquareTest {

    @Test
    public void testSquareConstructorPremium(){
        Square square = new Square(12, SquareType.PREMIUM_LETTER);
        assertEquals(12,square.getmultiplier());
        assertEquals(SquareType.PREMIUM_LETTER,square.getsquareType());
        assertThrows(IllegalArgumentException.class, () -> new Square(100,SquareType.PREMIUM_LETTER));
        assertThrows(IllegalArgumentException.class, () -> new Square(-10,SquareType.PREMIUM_LETTER));

    }
    @Test
    public void testSquareConstructorRegular(){
        Square square = new Square(SquareType.REGULAR);
        assertEquals(SquareType.REGULAR,square.getsquareType());
        assertThrows(IllegalArgumentException.class, () -> new Square(12,SquareType.REGULAR));
        assertThrows(IllegalArgumentException.class, () -> new Square(SquareType.PREMIUM_WORD));

    }
    @Test
    public void testSquareToString(){
        Square square = new Square(12, SquareType.PREMIUM_LETTER);
        Square square2 = new Square(8, SquareType.PREMIUM_LETTER);
        Square square3 = new Square(-8, SquareType.PREMIUM_LETTER);
        Square square4 = new Square(99, SquareType.PREMIUM_LETTER);
        assertEquals("12.",square.toString());
        assertEquals(" 8.",square2.toString());
        assertEquals("-8.",square3.toString());
        assertEquals("99.",square4.toString());


        Square square5 = new Square(12, SquareType.PREMIUM_WORD);
        Square square6 = new Square(8, SquareType.PREMIUM_WORD);
        Square square7 = new Square(-8, SquareType.PREMIUM_WORD);
        Square square8 = new Square(99, SquareType.PREMIUM_WORD);
        assertEquals("12!",square5.toString());
        assertEquals(" 8!",square6.toString());
        assertEquals("-8!",square7.toString());
        assertEquals("99!",square8.toString());

        Square square9 = new Square(SquareType.REGULAR);
        assertEquals(" . ",square9.toString());

    }

}
