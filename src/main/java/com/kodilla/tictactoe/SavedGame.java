package com.kodilla.tictactoe;

import java.io.Serializable;

public class SavedGame implements Serializable {

    private final String[][] board;
    private final Player player1;
    private final Player player2;
    private final DifficultyLevel difficultyLevel;
    private final Player turn;

    public SavedGame(GameWindow gameWindow) {

        this.board = saveBoard(gameWindow.getBoard());
        this.player1 = gameWindow.player1;
        this.player2 = gameWindow.player2;
        this.difficultyLevel = gameWindow.getDifficultyLevel();
        this.turn = gameWindow.turn;

    }

    private String[][] saveBoard(Board board) {

        int size = board.boardSize;
        String[][] tempBoard = new String[size][size];

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                tempBoard[row][column] = board.getBoardStatus()[row][column].getMark();
            }
        }
        return tempBoard;
    }


    public String[][] getBoard() {
        return board;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public Player getTurn() {
        return turn;
    }
}
