package com.hangman.webapp;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GameStatusFlowTests {

    private List<Character> winningScenario() {
        return new ArrayList<>(Arrays.asList('h', 'a', 'n', 'g', 'm'));
    }

    private List<Character> losingScenario() {
        return new ArrayList<>(Arrays.asList('k', 'l'));
    }

    @Test
    void fullGameFlow_vsComputerWinning() {
        int maxAttempts = 6;
        String word = "hangman";
        List<Character> guessChars = winningScenario();
        GameStatus gameStatus = new GameStatus(word, maxAttempts); // Example: Word is "hangman", 6 attempts allowed

        System.out.println("Welcome to Hangman!");
        System.out.println("You have " + gameStatus.getRemainingAttempts() + " attempts to guess the word.");

        MoveResponse moveResponse = new MoveResponse(true, false, false, "", maxAttempts);
        for (char guess : guessChars) {
            System.out.println("\nWord: " + gameStatus.getCurrentWordState());
            System.out.println("Remaining attempts: " + gameStatus.getRemainingAttempts());

            assert gameStatus.getRemainingAttempts() == maxAttempts;
            assert !gameStatus.isGameOver();
            assert !gameStatus.isGameWon();
            assert moveResponse.isValidGuess();
            assert !moveResponse.isDidWin();
            assert !moveResponse.isGameOver();

            moveResponse = gameStatus.makeMove(guess);
        }

        System.out.println("\nWord: " + gameStatus.getCurrentWordState());
        System.out.println("Remaining attempts: " + gameStatus.getRemainingAttempts());

        assert gameStatus.getRemainingAttempts() == maxAttempts;
        assert gameStatus.isGameOver();
        assert gameStatus.isGameWon();

        assert moveResponse.isValidGuess();
        assert moveResponse.isDidWin();
        assert moveResponse.isGameOver();
        assert moveResponse.getRemainingAttempts() == maxAttempts;
        assert Objects.equals(moveResponse.getWordStatus(), "h a n g m a n");
    }


    @Test
    void fullGameFlow_vsComputerLosing() {
        int maxAttempts = 2;
        String word = "hangman";
        List<Character> guessChars = losingScenario();
        GameStatus gameStatus = new GameStatus(word, maxAttempts); // Example: Word is "hangman", 6 attempts allowed

        System.out.println("Welcome to Hangman!");
        System.out.println("You have " + gameStatus.getRemainingAttempts() + " attempts to guess the word.");

        MoveResponse moveResponse = new MoveResponse(true, false, false, "", maxAttempts);
        for (int i = 0; i < guessChars.size(); i++) {
            System.out.println("\nWord: " + gameStatus.getCurrentWordState());
            System.out.println("Remaining attempts: " + gameStatus.getRemainingAttempts());

            assert gameStatus.getRemainingAttempts() == maxAttempts - i;
            assert !gameStatus.isGameOver();
            assert !gameStatus.isGameWon();
            assert moveResponse.isValidGuess();
            assert !moveResponse.isDidWin();
            assert !moveResponse.isGameOver();

            moveResponse = gameStatus.makeMove(guessChars.get(i));
        }

        System.out.println("\nWord: " + gameStatus.getCurrentWordState());
        System.out.println("Remaining attempts: " + gameStatus.getRemainingAttempts());

        assert gameStatus.getRemainingAttempts() == 0;
        assert gameStatus.isGameOver();
        assert !gameStatus.isGameWon();
        assert moveResponse.isValidGuess();
        assert !moveResponse.isDidWin();
        assert moveResponse.isGameOver();
        assert Objects.equals(moveResponse.getWordStatus(), "_ _ _ _ _ _ _");
    }

    @Test
    void fullGameFlow_vsComputerNotValidMove() {
        int maxAttempts = 2;
        String word = "hangman";
        List<Character> guessChars = new ArrayList<>(Arrays.asList('k', 'k'));
        GameStatus gameStatus = new GameStatus(word, maxAttempts); // Example: Word is "hangman", 6 attempts allowed

        System.out.println("Welcome to Hangman!");
        System.out.println("You have " + gameStatus.getRemainingAttempts() + " attempts to guess the word.");

        MoveResponse moveResponse = new MoveResponse(true, false, false, "", maxAttempts);
        for (int i = 0; i < guessChars.size(); i++) {
            System.out.println("\nWord: " + gameStatus.getCurrentWordState());
            System.out.println("Remaining attempts: " + gameStatus.getRemainingAttempts());

            assert gameStatus.getRemainingAttempts() == maxAttempts - i;
            assert moveResponse.isValidGuess();

            moveResponse = gameStatus.makeMove(guessChars.get(i));
        }

        assert gameStatus.getRemainingAttempts() == maxAttempts - 1;
        assert !moveResponse.isValidGuess();


    }

}
