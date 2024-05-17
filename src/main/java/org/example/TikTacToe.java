package org.example;

import java.util.Scanner;
import java.util.logging.Logger;

public class TikTacToe {
    private static final Logger LOGGER = Logger.getLogger(TikTacToe.class.getName());
    private static final byte BOX_SIZE = 9;
    private enum GameState { PLAYING, WINNER_PLAYER, WINNER_COMPUTER, DRAW }
    private final char[] box = new char[BOX_SIZE];
    private GameState gameState = GameState.PLAYING;

    public void startGame() {
        initializeGame();
        try (Scanner scan = new Scanner(System.in)) {
            while (gameState == GameState.PLAYING) {
                displayBoard();
                playerTurn(scan);
                if (gameState == GameState.PLAYING) {
                    computerTurn();
                    checkGameState();
                }
            }
            displayEndMessage();
        }
    }

    private void initializeGame() {
        for (int i = 0; i < BOX_SIZE; i++) {
            box[i] = (char) ('1' + i);
        }
        LOGGER.info("Enter box number to select. Enjoy!\n");
    }

    private void displayBoard() {
        // Я честно не знаю, але у мене {} не працює, як це має бути.
        LOGGER.info("\n\n " + box[0] + " | " + box[1] + " | " + box[2] + " ");
        LOGGER.info("-----------");
        LOGGER.info(" " + box[3] + " | " + box[4] + " | " + box[5] + " ");
        LOGGER.info("-----------");
        LOGGER.info(" " + box[6] + " | " + box[7] + " | " + box[8] + " \n");
    }

    private void playerTurn(Scanner scan) {
        byte input;
        while (true) {
            LOGGER.info("Your turn: ");
            input = scan.nextByte();
            if (input > 0 && input <= BOX_SIZE && box[input - 1] != 'X' && box[input - 1] != 'O') {
                box[input - 1] = 'X';
                break;
            } else {
                LOGGER.warning("Invalid input or box already filled. Try again.");
            }
        }
    }

    private void computerTurn() {
        byte rand;
        while (true) {
            rand = (byte) (Math.random() * BOX_SIZE);
            if (box[rand] != 'X' && box[rand] != 'O') {
                box[rand] = 'O';
                LOGGER.info("Computer chose box " + (rand));
                break;
            }
        }
    }

    private void checkGameState() {
        if (isWinner('X')) {
            gameState = GameState.WINNER_PLAYER;
        } else if (isWinner('O')) {
            gameState = GameState.WINNER_COMPUTER;
        } else if (isBoardFull()) {
            gameState = GameState.DRAW;
        }
    }

    private boolean isWinner(char player) {
        return (box[0] == player && box[1] == player && box[2] == player) ||
                (box[3] == player && box[4] == player && box[5] == player) ||
                (box[6] == player && box[7] == player && box[8] == player) ||
                (box[0] == player && box[3] == player && box[6] == player) ||
                (box[1] == player && box[4] == player && box[7] == player) ||
                (box[2] == player && box[5] == player && box[8] == player) ||
                (box[0] == player && box[4] == player && box[8] == player) ||
                (box[2] == player && box[4] == player && box[6] == player);
    }

    private boolean isBoardFull() {
        for (int i = 0; i < BOX_SIZE; i++) {
            if (box[i] != 'X' && box[i] != 'O') {
                return false;
            }
        }
        return true;
    }

    private void displayEndMessage() {
        if (gameState == GameState.WINNER_PLAYER) {
            LOGGER.info("You won the game!\nCreated by Shreyas Saha. Thanks for playing!");
        } else if (gameState == GameState.WINNER_COMPUTER) {
            LOGGER.info("You lost the game!\nCreated by Shreyas Saha. Thanks for playing!");
        } else {
            LOGGER.info("It's a draw!\nCreated by Shreyas Saha. Thanks for playing!");
        }
    }
}
