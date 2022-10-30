package com.kodilla.tictactoe;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MenuSinglePlayer {

    private static Player playerHuman;
    private static Player playerComputer;
    private static final String COMPUTER_NAME = "Computer";
    private static final Image IMAGE = new Image("file:src/main/resources/Background.jpg");

    public static void showMenu() {

        final Stage window = new Stage();
        final StackPane layout = new StackPane();

        window.setTitle("Tic Tac Toe - Single Player menu");

        //Background
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(IMAGE, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        layout.setBackground(background);

        //Title
        final Label titleLabel = new Label("Single player game");
        titleLabel.setFont(Font.font(24));
        titleLabel.setTranslateY(-200);
        layout.getChildren().add(titleLabel);

        //Players name menu
        final Label playerNameLabel = new Label("Player's name:");
        playerNameLabel.setFont(Font.font(20));
        playerNameLabel.setTranslateX(-175);
        playerNameLabel.setTranslateY(-125);
        layout.getChildren().add(playerNameLabel);

        final TextField playerNameInput = new TextField("Player");
        playerNameInput.setFont(Font.font(20));
        playerNameInput.setMaxSize(150, 20);
        playerNameInput.setTranslateX(-175);
        playerNameInput.setTranslateY(-75);
        layout.getChildren().add(playerNameInput);

        //Board size menu
        final Label boardSizeLabel = new Label("Choose board size:");
        boardSizeLabel.setFont(Font.font(20));
        boardSizeLabel.setTranslateX(-175);
        boardSizeLabel.setTranslateY(25);
        layout.getChildren().add(boardSizeLabel);

        final ToggleGroup toggleGroup = new ToggleGroup();
        final RadioButton boardSize3Button = new RadioButton("3x3 (classic TicTacToe)");
        boardSize3Button.setTranslateX(-175);
        boardSize3Button.setTranslateY(70);
        boardSize3Button.setToggleGroup(toggleGroup);
        boardSize3Button.setSelected(true);
        layout.getChildren().add(boardSize3Button);

        final RadioButton boardSize10Button = new RadioButton("10x10 (5 marks in a row to win)");
        boardSize10Button.setTranslateX(-175);
        boardSize10Button.setTranslateY(110);
        boardSize10Button.setToggleGroup(toggleGroup);
        layout.getChildren().add(boardSize10Button);

        //Players mark menu
        final Label markLabel = new Label("Do you want to play O or X?");
        markLabel.setFont(Font.font(20));
        markLabel.setTranslateX(150);
        markLabel.setTranslateY(-125);
        layout.getChildren().add(markLabel);

        final ToggleGroup toggleGroup2 = new ToggleGroup();
        final RadioButton markOButton = new RadioButton("O");
        markOButton.setTranslateX(110);
        markOButton.setTranslateY(-75);
        markOButton.setToggleGroup(toggleGroup2);
        markOButton.setSelected(true);
        layout.getChildren().add(markOButton);

        final RadioButton markXButton = new RadioButton("X");
        markXButton.setTranslateX(190);
        markXButton.setTranslateY(-75);
        markXButton.setToggleGroup(toggleGroup2);
        layout.getChildren().add(markXButton);

        //Difficulty menu
        final Label difficultyLabel = new Label("Choose difficulty level");
        difficultyLabel.setFont(Font.font(20));
        difficultyLabel.setTranslateX(150);
        difficultyLabel.setTranslateY(25);
        layout.getChildren().add(difficultyLabel);

        final ToggleGroup toggleGroup3 = new ToggleGroup();
        final RadioButton easyButton = new RadioButton("Easy");
        easyButton.setTranslateX(110);
        easyButton.setTranslateY(90);
        easyButton.setToggleGroup(toggleGroup3);
        layout.getChildren().add(easyButton);

        final RadioButton hardButton = new RadioButton("Hard");
        hardButton.setTranslateX(190);
        hardButton.setTranslateY(90);
        hardButton.setToggleGroup(toggleGroup3);
        hardButton.setSelected(true);
        layout.getChildren().add(hardButton);

        //Start game button
        final Button startGameButton = new Button("Start Game");
        startGameButton.setTranslateY(175);
        startGameButton.setMinSize(100,50);
        startGameButton.setOnAction(e -> {

                    DifficultyLevel difficulty;
                    int boardSize;

                    if (boardSize3Button.isSelected()) {
                        boardSize = 3;
                    } else {
                        boardSize = 10;
                    }

                    if (easyButton.isSelected()) {
                        difficulty = DifficultyLevel.EASY;
                    } else {
                        difficulty = DifficultyLevel.HARD;
                    }

                    if (markOButton.isSelected()) {
                        playerHuman = new Player(playerNameInput.getText(), Mark.O_MARK, Player.Type.HUMAN);
                        playerComputer = new Player(COMPUTER_NAME, Mark.X_MARK, Player.Type.COMPUTER);
                    } else {
                        playerHuman = new Player(playerNameInput.getText(), Mark.X_MARK, Player.Type.HUMAN);
                        playerComputer = new Player(COMPUTER_NAME, Mark.O_MARK, Player.Type.COMPUTER);
                    }

                    window.close();
                    GameWindow gameWindow = new GameWindow(boardSize, playerHuman, playerComputer, difficulty);
                    gameWindow.playGame();
                }
        );

        layout.getChildren().add(startGameButton);

        //Single Player Menu layout
        Scene scene = new Scene(layout, 600, 450);
        window.setScene(scene);
        window.show();
    }
}
