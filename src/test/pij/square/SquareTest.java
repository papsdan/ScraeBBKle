package pij.square;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class SquareTest {

    @Test
    public void testPremiumLetterSquareConstructor(){
        PremiumLetterSquare square = new PremiumLetterSquare(12);
        assertEquals(12, square.getMultiplier());
        assertEquals(SquareType.PREMIUM_LETTER,square.getSquareType());
        assertThrows(IllegalArgumentException.class, () -> new PremiumLetterSquare(100));
        assertThrows(IllegalArgumentException.class, () -> new PremiumLetterSquare(-10));

    }
    @Test
    public void testPremiumWordSquareConstructor(){
        PremiumWordSquare square = new PremiumWordSquare(5);
        assertEquals(5, square.getMultiplier());
        assertEquals(SquareType.PREMIUM_WORD, square.getSquareType());
        assertThrows(IllegalArgumentException.class, () -> new PremiumWordSquare(100));
        assertThrows(IllegalArgumentException.class, () -> new PremiumWordSquare(-10));

    }

    @Test
    public void testRegularSquareConstructor(){
        RegularSquare square = new RegularSquare();
        assertEquals(SquareType.REGULAR, square.getSquareType());
        assertEquals(1, square.getMultiplier());
    }

    @Test
    public void testSquareDisplay(){
        PremiumLetterSquare square1 = new PremiumLetterSquare(12);
        assertEquals("12.", square1.getDisplayString());

        PremiumLetterSquare square2 = new PremiumLetterSquare(8);
        assertEquals(" 8.", square2.getDisplayString());

        PremiumLetterSquare square3 = new PremiumLetterSquare(-8);
        assertEquals("-8.", square3.getDisplayString());

        PremiumLetterSquare square4 = new PremiumLetterSquare(99);
        assertEquals("99.", square4.getDisplayString());

        PremiumWordSquare square5 = new PremiumWordSquare(12);
        assertEquals("12!", square5.getDisplayString());

        PremiumWordSquare square6 = new PremiumWordSquare(8);
        assertEquals(" 8!", square6.getDisplayString());

        PremiumWordSquare square7 = new PremiumWordSquare(-8);
        assertEquals("-8!", square7.getDisplayString());

        PremiumWordSquare square8 = new PremiumWordSquare(99);
        assertEquals("99!", square8.getDisplayString());

        RegularSquare square9 = new RegularSquare();
        assertEquals(" . ", square9.getDisplayString());

    }

}
