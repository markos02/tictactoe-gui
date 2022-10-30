package com.kodilla.tictactoe;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MenuTwoPlayers {

    private static Player player1;
    private static Player player2;
    private static final Image IMAGE = new Image("file:src/main/resources/Background.jpg");

    public static void showMenu() {

        final Stage window = new Stage();
        final StackPane layout = new StackPane();

        window.setTitle("Tic Tac Toe - Two Players menu");

        //Background
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(IMAGE, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        layout.setBackground(background);

        final Label titleLabel = new Label("Two players game");
        titleLabel.setFont(Font.font(24));
        titleLabel.setTranslateY(-200);
        layout.getChildren().add(titleLabel);

        //Players name menu
        final Label player1NameLabel = new Label("Player 1 (O) name:");
        player1NameLabel.setFont(Font.font(20));
        player1NameLabel.setTranslateX(-150);
        player1NameLabel.setTranslateY(-125);
        layout.getChildren().add(player1NameLabel);

        final TextField player1NameInput = new TextField("Player 1");
        player1NameInput.setFont(Font.font(20));
        player1NameInput.setMaxSize(150, 20);
        player1NameInput.setTranslateX(-150);
        player1NameInput.setTranslateY(-75);
        layout.getChildren().add(player1NameInput);

        final Label player2NameLabel = new Label("Player 2 (X) name:");
        player2NameLabel.setFont(Font.font(20));
        player2NameLabel.setTranslateX(150);
        player2NameLabel.setTranslateY(-125);
        layout.getChildren().add(player2NameLabel);

        final TextField player2NameInput = new TextField("Player 2");
        player2NameInput.setFont(Font.font(20));
        player2NameInput.setMaxSize(150, 20);
        player2NameInput.setTranslateX(150);
        player2NameInput.setTranslateY(-75);
        layout.getChildren().add(player2NameInput);

        //Board size menu
        final Label boardSizeLabel = new Label("Choose board size:");
        boardSizeLabel.setFont(Font.font(20));
        boardSizeLabel.setTranslateY(25);
        layout.getChildren().add(boardSizeLabel);

        final ToggleGroup toggleGroup = new ToggleGroup();
        final RadioButton boardSize3Button = new RadioButton("3x3 (classic TicTacToe)");
        boardSize3Button.setTranslateY(70);
        boardSize3Button.setToggleGroup(toggleGroup);
        boardSize3Button.setSelected(true);
        layout.getChildren().add(boardSize3Button);

        final RadioButton boardSize10Button = new RadioButton("10x10 (5 marks in a row to win)");
        boardSize10Button.setTranslateY(110);
        boardSize10Button.setToggleGroup(toggleGroup);
        layout.getChildren().add(boardSize10Button);

        //Start game button
        final Button startGameButton = new Button("Start Game");
        startGameButton.setTranslateY(175);
        startGameButton.setMinSize(100,50);
        startGameButton.setOnAction(e -> {

                    int boardSize;

                    if (boardSize3Button.isSelected()) {
                        boardSize = 3;
                    } else {
                        boardSize = 10;
                    }

                    player1 = new Player(player1NameInput.getText(), Mark.O_MARK, Player.Type.HUMAN);
                    player2 = new Player(player2NameInput.getText(), Mark.X_MARK, Player.Type.HUMAN);
                    window.close();
                    GameWindow gameWindow = new GameWindow(boardSize, player1, player2);
                    gameWindow.playGame();
                }
        );

        layout.getChildren().add(startGameButton);

        //Two Players Menu layout
        Scene scene = new Scene(layout, 600, 450);
        window.setScene(scene);
        window.show();
    }
}
