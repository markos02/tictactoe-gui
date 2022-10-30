package com.kodilla.tictactoe;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GameWindow {

    private static final Image IMAGE = new Image("file:src/main/resources/Background.jpg");
    public final Stage window = new Stage();

    private final Board board;
    final Player player1;
    final Player player2;
    private final DifficultyLevel difficultyLevel;

    private final TicTacToe gameLogic = new TicTacToe(this);
    private final InfoWindow infoWindow;
    private final GameMenu gameMenu;
    public Player turn;


    public GameWindow(int boardSize, Player player1, Player player2, DifficultyLevel difficultyLevel) {

        this.player1 = player1;
        this.player2 = player2;

        this.infoWindow = new InfoWindow(this.player1.getPlayerName() + "'s turn");
        this.gameMenu = new GameMenu(this);
        this.board = new Board(boardSize, this);
        this.difficultyLevel = difficultyLevel;

        if (player2.getPlayerMark().equals(Mark.O_MARK)) {
            this.turn = player2;
            gameLogic.getComputerMove();
        }
        this.turn = player1;
    }

    public GameWindow(int boardSize, Player player1, Player player2) {

        this.player1 = player1;
        this.player2 = player2;

        if (player1.getPlayerMark().equals(Mark.O_MARK)) {
            this.turn = player1;
        } else {
            this.turn = player2;
        }
        this.infoWindow = new InfoWindow(this.player1.getPlayerName() + "'s turn");
        this.gameMenu = new GameMenu(this);
        this.board = new Board(boardSize, this);
        this.difficultyLevel = DifficultyLevel.EASY;
    }

    public void playGame() {

        window.setTitle("Tic Tac Toe");
        window.setOnCloseRequest(e -> {
            e.consume();
            gameMenu.closeGame();
        });

        BorderPane layout = new BorderPane();

        //Background
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(IMAGE, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        layout.setBackground(background);

        //Game window menu layout
        layout.setTop(infoWindow.getStackPane());
        layout.setCenter(board.getStackPane());
        layout.setBottom(gameMenu.getStackPane());

        Scene gameScene = new Scene(layout, 600, 550);
        window.setScene(gameScene);
        window.show();
    }

    public void changeTurn() {
        if (turn == player1) {
            turn = player2;
        } else {
            turn = player1;
        }
        infoWindow.setInfo(turn.getPlayerName() + "'s turn");
    }

    public Board getBoard() {
        return board;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public TicTacToe getGameLogic() {
        return gameLogic;
    }

    public InfoWindow getInfoWindow() {
        return infoWindow;
    }

    public GameMenu getGameMenu() {
        return gameMenu;
    }
}

