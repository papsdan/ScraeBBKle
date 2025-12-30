package pij.board;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

public class MoveValidator {
    private final HashSet<String> validWords;

    public MoveValidator() throws IOException {
        String content = Files.readString(Path.of("resources/wordlist.txt"));
        String[] words = content.split("\n");
        this.validWords = new HashSet<>();
        for (String word : words) {
            this.validWords.add(word.trim().toLowerCase());
        }
    }

    public boolean isValidWord(String word) {
        return this.validWords.contains(word.toLowerCase());
    }

}

