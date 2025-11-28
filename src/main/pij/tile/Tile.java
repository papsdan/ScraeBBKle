package pij.tile;


public class Tile {
    private char letter;
    private final int value;
    private boolean isWildcard;

    public Tile(char letter, int value) {
        this.letter = letter;
        this.value = value;
        if(letter == '_'){
            this.isWildcard = true;
        }
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
        if(this.isWildcard){
            this.letter = letter;
        }
    }
    @Override
    public String toString() {
        return "[" + letter + value + "]";
    }
}
