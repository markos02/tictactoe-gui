package com.kodilla.tictactoe;

import java.io.*;

public class GameSaverLoader {

    final static File savedGameFile = new File("savedGame.list");

    public static boolean areSavedGames() {
        return (savedGameFile.length() != 0);
    }

    public static void saveGame(GameWindow gameWindow) {

        SavedGame savedGame = new SavedGame(gameWindow);

        try {
            new PrintWriter(savedGameFile).close();
            ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream(savedGameFile));
            oos.writeObject(savedGame);
            oos.close();
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public static void loadGame() {

        GameWindow gameWindow;

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(savedGameFile));
            Object loadedGame = ois.readObject();

            if(loadedGame instanceof SavedGame) {

                String[][] board = ((SavedGame) loadedGame).getBoard();
                int boardSize = board.length;
                Player player1 = ((SavedGame) loadedGame).getPlayer1();
                Player player2 = ((SavedGame) loadedGame).getPlayer2();
                DifficultyLevel difficultyLevel = ((SavedGame) loadedGame).getDifficultyLevel();
                Player turn = ((SavedGame) loadedGame).getTurn();

                gameWindow = new GameWindow(boardSize, player1, player2, difficultyLevel);
                if (gameWindow.turn != turn) {
                    gameWindow.changeTurn();
                }

                for (int row = 0; row < boardSize; row++) {
                    for (int column = 0; column < boardSize; column++) {
                        String mark = board[row][column];
                        gameWindow.getBoard().getBoardStatus()[row][column].setMark(mark);
                    }
                }

                ois.close();
                gameWindow.playGame();

            }

            ois.close();

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
