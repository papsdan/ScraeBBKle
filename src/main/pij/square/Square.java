package pij.square;

import pij.tile.Tile;

public abstract class Square {

    private final SquareType squareType;
    private Tile tile;

    public Square(SquareType squareType) {
        this.squareType = squareType;
    }
    public void multiplierValidator(int multiplier) {
        if (multiplier < -9 || multiplier > 99) {
            throw new IllegalArgumentException("Premium square value must be between -9 and 99");
        }
    }
    public String formatPremiumDisplay(int multiplier, char suffix) {
        String prefix = "";
        if (multiplier >= 0 && multiplier <= 9) {
            prefix = " ";
        } else {
            prefix = "";
        }
        return prefix + multiplier + suffix;
    }

    public abstract int getMultiplier();

    public SquareType getSquareType() {
        return this.squareType;
    }

    public abstract String getDisplayString();


    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public boolean isSquareOccupied() {
        return this.tile != null;
    }

    public Tile getTile() {
        return this.tile;
    }
}

