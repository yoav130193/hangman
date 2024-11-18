package com.hangman.webapp;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class GameStatus {
    private String wordToGuess; // The word the player has to guess
    private Set<Character> guessedLetters; // Set of guessed letters
    private int remainingAttempts; // Number of wrong guesses allowed
    private Set<Character> correctLetters; // Set of correctly guessed letters

    public GameStatus(String wordToGuess, int maxAttempts) {
        this.wordToGuess = wordToGuess.toLowerCase();
        this.remainingAttempts = maxAttempts;
        this.guessedLetters = new HashSet<>();
        this.correctLetters = new HashSet<>();
    }

    public MoveResponse makeMove(char guess) {
        boolean didWin = false;
        boolean validGuess = makeGuess(guess);
        if (!validGuess) {
            return new MoveResponse(false, false, false, getCurrentWordState(), getRemainingAttempts());
        }

        return new MoveResponse(true, isGameOver(), isGameWon(), getCurrentWordState(), getRemainingAttempts());
    }

    public boolean makeGuess(char guess) {
        guess = Character.toLowerCase(guess);

        if (guessedLetters.contains(guess)) {
            System.out.println("You already guessed that letter!");
            return false; // Guess was not counted
        }

        guessedLetters.add(guess);

        if (wordToGuess.contains(String.valueOf(guess))) {
            correctLetters.add(guess);
        } else {
            remainingAttempts--;
        }

        return true;
    }

    public boolean isGameWon() {
        for (char c : wordToGuess.toCharArray()) {
            if (!correctLetters.contains(c)) {
                return false; // There is still a letter not guessed
            }
        }
        return true; // All letters guessed
    }

    public boolean isGameOver() {
        return remainingAttempts <= 0 || isGameWon();
    }

    public String getCurrentWordState() {
        StringBuilder display = new StringBuilder();
        for (char c : wordToGuess.toCharArray()) {
            if (correctLetters.contains(c)) {
                display.append(c).append(" ");
            } else {
                display.append("_ ");
            }
        }
        return display.toString().trim();
    }

    public int getRemainingAttempts() {
        return remainingAttempts;
    }

}
