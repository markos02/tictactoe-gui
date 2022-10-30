package com.kodilla.tictactoe;

import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.Serializable;

public class Board implements Serializable {

    final int boardSize;
    private final Tile[][] boardStatus;
    private final StackPane pane;
    private final GameWindow gameWindow;
    private final TicTacToe gameLogic;
    private int player1Counter;
    private int player2Counter;


    public Board(int size, GameWindow gameWindow) {
        this.boardSize = size;
        this.boardStatus = new Tile[size][size];
        this.pane = new StackPane();
        this.gameWindow = gameWindow;
        this.gameLogic = gameWindow.getGameLogic();

        if (boardSize == 3) {
            pane.setMinSize(300, 300);
        } else {
            pane.setMinSize(400, 400);
        }

        addTiles();
    }

    private void addTiles() {

        int offset;
        int size;

        if (boardSize == 3) {
            size = 100;
            offset = 100;
        } else {
            size = 40;
            offset = 175;
        }

        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                Tile tile = new Tile();
                tile.getStackPane().setTranslateX((column * size) - offset);
                tile.getStackPane().setTranslateY((row * size) - offset);
                pane.getChildren().add(tile.getStackPane());
                boardStatus[row][column] = tile;
            }
        }
    }

    public class Tile implements Serializable {

        private final StackPane pane;
        private final Label label;

        public Tile() {

            pane = new StackPane();
            pane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

            if (boardSize == 3) {
                pane.setMaxSize(100, 100);
            } else {
                pane.setMaxSize(40, 40);
            }

            label = new Label(Mark.EMPTY_MARK);
            label.setFont(Font.font(24));
            pane.getChildren().addAll(label);

            pane.setOnMouseClicked(e -> mouseClickHandler(this));
        }

        private void mouseClickHandler(Tile tile) {
            if (tile.getMark().equals(Mark.EMPTY_MARK)) {
                tile.setMark(gameWindow.turn.getPlayerMark());
                if (!gameLogic.CheckWinner()) {
                    gameWindow.changeTurn();
                    if (gameWindow.turn.getType().equals(Player.Type.COMPUTER)) {
                        gameLogic.getComputerMove();
                        gameWindow.changeTurn();
                    }
                }
            }
        }

        public StackPane getStackPane() {
            return pane;
        }

        public String getMark() {
            return label.getText();
        }

        public void setMark(String mark) {
            label.setText(mark);
        }
    }

    public String checkBoard() {

        Player winner = null;

        //Check rows
        for (int row = 0; row < boardSize; row++) {

            player1Counter = 0;
            player2Counter = 0;

            for (int column = 0; column < boardSize; column++) {

                winner = calculateCounters(boardStatus[row][column].getMark());

                if (winner != null) {
                    return winner.getPlayerName();
                }
            }
        }

        //Check columns
        for (int column = 0; column < boardSize; column++) {

            player1Counter = 0;
            player2Counter = 0;

            for (int row = 0; row < boardSize; row++) {

                winner = calculateCounters(boardStatus[row][column].getMark());

                if (winner != null) {
                    return winner.getPlayerName();
                }
            }
        }

        //Check diagonals TopLeft-BottomRight
        for (int i = boardSize - 1; i > -boardSize; i--) {

            player1Counter = 0;
            player2Counter = 0;
            int row = i;

            for (int column = 0; column < boardSize - i; column++) {

                try {
                    winner = calculateCounters(boardStatus[column][row].getMark());
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }

                if (winner != null) {
                    return winner.getPlayerName();
                }

                row++;

            }
        }

        //Check diagonals TopRight-BottomLeft
        for (int i = 0; i <= boardSize * 2; i++) {

            player1Counter = 0;
            player2Counter = 0;
            int row = i;

            for (int column = 0; column <= i; column++) {

                try {
                    winner = calculateCounters(boardStatus[column][row].getMark());
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }

                if (winner != null) {
                    return winner.getPlayerName();
                }

                row--;

            }
        }

        //Check if there is a tie
        if (this.isFull()) {
            return "Tie";
        }

        return null;
    }

    private Player calculateCounters(String mark) {

        int win;

        if (boardSize == 3) {
            win = 3;
        } else {
            win = 5;
        }

        if (mark.equals(gameWindow.player1.getPlayerMark())) {
            player1Counter++;
            player2Counter = 0;
        }

        if (mark.equals(gameWindow.player2.getPlayerMark())) {
            player2Counter++;
            player1Counter = 0;
        }

        if (mark.equals(Mark.EMPTY_MARK)) {
            player1Counter = 0;
            player2Counter = 0;
        }

        if (player1Counter == win) {
            return gameWindow.player1;
        }

        if (player2Counter == win) {
            return gameWindow.player2;
        }

        return null;
    }

    public boolean isFull() {

        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                if (getBoardStatus()[row][column].getMark().equals(Mark.EMPTY_MARK)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Board copyBoard() {
        Board result = new Board(this.boardSize, this.gameWindow);
        for (int row = 0; row < result.boardSize; row++) {
            for (int column = 0; column < result.boardSize; column++) {
                result.getBoardStatus()[row][column].setMark(boardStatus[row][column].getMark());

            }

        }
        return result;
    }

    public Tile[][] getBoardStatus() {
        return boardStatus;
    }

    public StackPane getStackPane() {
        return pane;
    }

    @Override
    public String toString() {

        StringBuilder string = new StringBuilder();

        for (int row = 0; row < boardSize; row++) {
            string.append('|');
            for (int column = 0; column < boardSize; column++) {
                if (boardStatus[row][column].getMark().equals(Mark.EMPTY_MARK)) {
                    string.append(" ");
                    string.append('|');
                } else {
                    string.append(boardStatus[row][column].getMark());
                    string.append('|');
                }

            }
            string.append('\n');

        }
        return string.toString();
    }
}
