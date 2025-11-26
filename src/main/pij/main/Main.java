package pij.main;

import pij.tile.Tile;

public class Main {
    static void main(String[] args) {
        Tile t = new Tile('a',2);
        System.out.println(t.getLetter());
        System.out.println(t.getValue());
    }
}
