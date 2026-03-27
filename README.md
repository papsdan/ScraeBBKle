# ScraeBBKle
Java implementation of ScraeBBKle - a Scrabble style word board game

A fully playable **Scrabble-inspired word board game** built in Java. Two players take turns placing tiles on a grid to form valid words, with a scoring system, premium squares, wildcard tiles, and a computer AI opponent.

---

## Demo

Mid-game snapshot — Player 1 just played `RET` across row 10, connecting to tiles already on the board:

```
      a  b  c  d  e  f  g  h  i  j  k  l  m  n  o  p

 1   3.-3! .  .  2.  .   .   .  2!  .   .   . -4.  .   . -2!   1 
 2    .  12! .  .  2.  .   .   .  3!  .   .   .  2.  .   .  8!   2 
 3    .   .  2!  .   .   .  3.  .   .   .  3.  .   .   .  2!  .   3 
 4    .   .   . D2 O1 V4 E2  2.  .  2.  .   .   .  2!  .   .   4 
 5    .  2.  . I1  2!  .   . P3  2.  .   .   .  2!  .   .  2.   5 
 6    .   .   . N1  .  2!  . A1  .   .   .  0!  .   .   .   .   6 
 7    .   . T1 E2 N1 Z9 O1 N1  .   .  3.  .   .   .  3.  .   7 
 8    .   .   . D2  .   .   . F4  .  2.  .   .   .  2.  .   .   8 
 9    .   .  3.  .   .   .  3. I1  .   .  3.  .   .   .  3.  .   9 
10    .   .   .   .   . R1 E2 S1 T1  .   .  2!  .   .   .   .  10 
11    .  2.  .   . -1!  .   . H4  0.  .   .   . -1!  .   .  2.  11 
12    .  9!  .   .  2.  .   .   .  3!  .   .   .  2.  .   . 16!  12 
13    .   .   .   .  2!  .   .  4.  .  5.  .   .   .  2!  .   .  13 
14    .   .   .  3.  .   .   . 42.  .  1!  .   .   .  2.  .   .  14 

      a  b  c  d  e  f  g  h  i  j  k  l  m  n  o  p

Start position: d7
Player 1 score: 39  |  Player 2 score: 38

It's your turn, Player 1! Your tiles:
[L1], [R1], [C4], [O1], [A1], [S1], [I1]

Please enter your move ("WORD,d4" plays down, "WORD,4d" plays right, "," to pass):
>
```

> **Reading the board:** ` . ` = empty square &nbsp;|&nbsp; ` 2.` = letter ×2 &nbsp;|&nbsp; ` 3!` = word ×3 &nbsp;|&nbsp; `D2 ` = tile D (value 2)

---

## Features

- **Human vs Human, Human vs Computer, or Computer vs Computer** gameplay
- **Computer AI** that searches for valid moves using permutations of its tile rack
- **Custom board loading** — define your own board layout in a plain-text file
- **Premium squares** — letter and word multipliers, including negative values
- **Wildcard tiles** — blanks that can be assigned any letter during play
- **Full move validation** — every move checked against an abridged SOWPODS word list
- **Scoring engine** — premium stacking, 7-tile bonus (+60 pts), end-game deductions
- **Open/closed game modes** — optionally show the opponent's tile rack

---

## Tech

- **Java 25** (compatible with Java 21)
- **JUnit 5** — 20+ unit tests across all core components
- **OOP design** — packages, inheritance, polymorphism, encapsulation throughout
- No external dependencies beyond the standard library and JUnit

---

## Project Structure

```
src/
├── main/pij/
│   ├── main/        # Entry point
│   ├── board/       # Board grid, file loader
│   ├── square/      # Regular, PremiumLetter, PremiumWord square types
│   ├── tile/        # Tile and TileBag
│   ├── player/      # HumanPlayer and ComputerPlayer
│   ├── game/        # Game loop, scoring, move validation
│   └── move/        # Move parsing and representation
└── test/pij/        # JUnit 5 test suites
resources/
├── defaultBoard.txt
└── wordlist.txt
```

---

## Getting Started

```bash
# Compile
javac -d out -sourcepath src/main src/main/pij/main/Main.java

# Run
java -cp out pij.main.Main
```

On launch you'll be asked to choose a board, set each player to human or computer, and pick open or closed mode.

---

## How Moves Work

| Move | Format | Example |
|---|---|---|
| Play downward | `WORD,colRow` | `DINED,d4` |
| Play rightward | `WORD,rowCol` | `HELLO,4f` |
| Wildcard tile | lowercase letter in word | `HELLo,d4` |
| Pass turn | `,` | `,` |

---

*Built as part of the Programming in Java module at Birkbeck, University of London.*
