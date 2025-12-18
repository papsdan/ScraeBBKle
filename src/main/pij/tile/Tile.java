package pij.tile;


public class Tile {
    private char letter;
    private final int value;
    private final boolean isWildcard;
    private boolean isWildcardAssigned;

    public Tile(char letter, int value) {
        this.letter = letter;
        this.value = value;
        this.isWildcard = letter == '_';
        this.isWildcardAssigned = false;
    }

    public char getLetter() {
        return letter;
    }

    public int getValue() {
        return value;
    }

    public boolean isWildcard() {
        return isWildcard;
    }

    public void setWildcardLetter(char letter) {
        if (this.isWildcard && !this.isWildcardAssigned) {
            this.letter = letter;
            this.isWildcardAssigned = true;
        } else {
            throw new RuntimeException("Wildcard letter already set");
        }
    }

    @Override
    public String toString() {
        return "[" + letter + value + "]";
    }
    
}
