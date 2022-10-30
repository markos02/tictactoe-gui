package com.kodilla.tictactoe;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TicTacToe {

    private final GameWindow gameWindow;
    private final static Path PATH = Paths.get("C:\\Users\\mk\\Documents\\Private\\dev\\kodilla-course\\tictactoe-gui\\src\\main\\resources\\ranking.txt");

    public TicTacToe(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }

    public boolean CheckWinner() {

        Board board = gameWindow.getBoard();
        InfoWindow infoWindow = gameWindow.getInfoWindow();
        String winner = board.checkBoard();

        if (winner != null) {

            if (winner.equals("Tie")) {
                infoWindow.setInfo("It's a tie!");
                saveRankingToFile(gameWindow.player1, false);
                saveRankingToFile(gameWindow.player2, false);
            } else {
                infoWindow.setInfo(winner + " wins");
                if (winner.equals(gameWindow.player1.getPlayerName())) {
                    saveRankingToFile(gameWindow.player1, true);
                    saveRankingToFile(gameWindow.player2, false);
                } else {
                    saveRankingToFile(gameWindow.player1, false);
                    saveRankingToFile(gameWindow.player2, true);
                }
            }
            FinishedGame();
            return true;
        }
        return false;
    }

    private void FinishedGame() {

        GameMenu gameMenu = gameWindow.getGameMenu();

        gameMenu.hideCloseButton();
        gameMenu.showFinishedGameCloseButton();
        gameMenu.showNewGameButton();
    }

    public void getComputerMove() {

        Board board = gameWindow.getBoard();

        if (gameWindow.getDifficultyLevel() == DifficultyLevel.EASY) {
            computerMoveEasy(board);
        } else {
            computerMoveHard(board);
        }
        CheckWinner();
    }

    private boolean computerMoveEasy(Board board) {

        if (board.isFull()) {
            return false;
        }

        int size = board.boardSize;
        Random random = new Random();
        int row;
        int column;

        boolean illegalMove = true;

        while (illegalMove) {
            row = random.nextInt(size);
            column = random.nextInt(size);
            Board.Tile tile = board.getBoardStatus()[row][column];
            if (tile.getMark().equals(Mark.EMPTY_MARK)) {
                tile.setMark(gameWindow.turn.getPlayerMark());
                illegalMove = false;
            }
        }

        return true;
    }

    private boolean computerMoveHard(Board board) {

        if (board.isFull()) {
            return false;
        }

        int size = board.boardSize;
        List<BoardChecker> boardChecker = new ArrayList<>();
        int value;
        String currentMark;
        String computerMark = gameWindow.turn.getPlayerMark();
        Board tempBoard;

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {

                currentMark = board.getBoardStatus()[row][column].getMark();

                if (currentMark.equals(Mark.EMPTY_MARK)) {
                    tempBoard = board.copyBoard();
                    tempBoard.getBoardStatus()[row][column].setMark(computerMark);
                    value = calculateValue(tempBoard);
                    if (value == 10) {
                        board.getBoardStatus()[row][column].setMark(computerMark);
                        return true;
                    }
                    boardChecker.add(new BoardChecker(row, column, value));
                }

            }
        }

        int index = findMaxValue(boardChecker);
        int column = boardChecker.get(index).getColumn();
        int row = boardChecker.get(index).getRow();

        board.getBoardStatus()[row][column].setMark(computerMark);
        return true;

    }

    private int Counter;
    private int tempCounter;

    public int calculateValue(Board board) {

        boolean winner = false;
        int size = board.boardSize;
        int maxValue = 0;
        int win;

        if (size == 3) {
            win = 3;
        } else {
            win = 5;
        }

        //Check rows
        for (int row = 0; row < size; row++) {

            Counter = 0;
            tempCounter = 0;

            for (int column = 0; column < size; column++) {
                winner = calculateCounter(board.getBoardStatus()[row][column].getMark(), win);
            }

            if (Counter > maxValue) {
                maxValue = Counter;
            }

            if (winner) {
                return 10;
            }

        }

        //Check columns
        for (int column = 0; column < size; column++) {

            Counter = 0;
            tempCounter = 0;

            for (int row = 0; row < size; row++) {
                winner = calculateCounter(board.getBoardStatus()[row][column].getMark(), win);
            }


            if (Counter > maxValue) {
                maxValue = Counter;
            }

            if (winner) {
                return 10;
            }
        }

        //Check diagonals TopLeft-BottomRight
        for (int i = size - 1; i > -size; i--) {

            Counter = 0;
            tempCounter = 0;
            int row = i;

            for (int column = 0; column < size - i; column++) {

                try {
                    winner = calculateCounter(board.getBoardStatus()[row][column].getMark(), win);
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }

                row++;

            }

            if (Counter > maxValue) {
                maxValue = Counter;
            }

            if (winner) {
                return 10;
            }
        }

        //Check diagonals TopRight-BottomLeft
        for (int i = 0; i <= size * 2; i++) {

            Counter = 0;
            tempCounter = 0;
            int row = i;

            for (int column = 0; column <= i; column++) {

                try {
                    winner = calculateCounter(board.getBoardStatus()[row][column].getMark(), win);
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }

                row--;

            }

            if (Counter > maxValue) {
                maxValue = Counter;
            }

            if (winner) {
                return 10;
            }
        }

        return maxValue;
    }

    private boolean calculateCounter(String mark, int win) {

        if (mark.equals(gameWindow.turn.getPlayerMark())) {
            tempCounter++;
            if (tempCounter > Counter) {
                Counter = tempCounter;
            }
        } else {
            tempCounter = 0;
        }


        return Counter == win;
    }

    private int findMaxValue(List<BoardChecker> list) {

        int maxValue = 0;
        int index = 0;

        for (BoardChecker candidate : list) {
            if (candidate.getValue() > maxValue) {
                maxValue = candidate.getValue();
                index = list.indexOf(candidate);
            }
        }

        return index;
    }

    public void saveRankingToFile(Player player, boolean win) {

        String playerName = player.getPlayerName();
        StringBuilder stringBuilder = new StringBuilder();
        boolean playerNotFound = true;
        List<String> rankingList;

        try {

            rankingList = new ArrayList<>(Files.readAllLines(PATH));


            for (int i = 0; i < rankingList.size(); i++) {

                String rankLine = rankingList.get(i);

                int indexTotalGames = rankLine.indexOf('|');
                int indexGamesWon = rankLine.indexOf('|', indexTotalGames + 1);
                int indexDate = rankLine.indexOf('|', indexGamesWon + 1);

                String name = rankLine.substring(0, indexTotalGames - 1);

                if (name.equals(playerName)) {

                    playerNotFound = false;

                    int totalGames = Integer.parseInt(rankLine.substring(indexTotalGames + 2, indexGamesWon - 1));
                    totalGames++;

                    int gamesWon = Integer.parseInt(rankLine.substring(indexGamesWon + 2, indexDate - 1));
                    if (win) {
                        gamesWon++;
                    }

                    stringBuilder.append(name);
                    stringBuilder.append(" | ");
                    stringBuilder.append(totalGames);
                    stringBuilder.append(" | ");
                    stringBuilder.append(gamesWon);
                    stringBuilder.append(" | ");
                    stringBuilder.append(LocalDate.now());

                    rankingList.set(i, stringBuilder.toString());
                    break;
                }
            }

        } catch (IOException e) {
            gameWindow.getInfoWindow().setInfo("Initializing ranking file");
            initialiseRankingFile();
            try {
                rankingList = new ArrayList<>(Files.readAllLines(PATH));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        if (playerNotFound) {

            stringBuilder.append(playerName);
            stringBuilder.append(" | ");
            stringBuilder.append("1");
            stringBuilder.append(" | ");
            if (win) {
                stringBuilder.append("1");
            } else {
                stringBuilder.append("0");
            }
            stringBuilder.append(" | ");
            stringBuilder.append(LocalDate.now());

            rankingList.add(stringBuilder.toString());
        }

        try {
            Files.write(PATH, rankingList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void initialiseRankingFile() {

        File rankingFile = new File(String.valueOf(PATH));
        try {
            rankingFile.createNewFile();
            String header = "Name " + " | " + "Total games" + " | " + "Games won" + " | " + "Date";
            Files.write(PATH, header.getBytes());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void saveGame() {
        GameSaverLoader.saveGame(gameWindow);
    }
}
