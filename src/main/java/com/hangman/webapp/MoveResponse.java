package com.hangman.webapp;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MoveResponse {

    private boolean validGuess;
    private boolean gameOver;
    private boolean didWin;
    private String wordStatus;
    private int remainingAttempts;
}
