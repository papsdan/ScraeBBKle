package pij.square;

import pij.tile.Tile;

public class RegularSquare extends Square {

    public  RegularSquare() {
        super(SquareType.REGULAR);
    }

    @Override
    public String getDisplayString() {
        if (isSquareOccupied()) {
            Tile tile = getTile();
            return String.format("%c%-2d", Character.toUpperCase(tile.getLetter()), tile.getValue());
        }
        return " . ";
    }

    @Override
    public int getMultiplier(){
        return 1;
    }


}
