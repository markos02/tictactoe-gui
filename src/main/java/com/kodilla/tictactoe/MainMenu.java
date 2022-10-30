package com.kodilla.tictactoe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class MainMenu extends Application {

    private final StackPane layout = new StackPane();
    private final Image image = new Image("file:src/main/resources/Background.jpg");
    public static Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        window = primaryStage;

        //Background
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        layout.setBackground(background);

        // Game mode menu
        final Label newGameLabel = new Label("New Game");
        newGameLabel.setFont(Font.font(36));
        newGameLabel.setTranslateY(-175);
        layout.getChildren().add(newGameLabel);

        final Label gameModeLabel = new Label("Choose game mode");
        gameModeLabel.setFont(Font.font(28));
        gameModeLabel.setTranslateY(-125);
        layout.getChildren().add(gameModeLabel);

        final Button singlePlayerButton = new Button("Single player game" + "\n" + "(against computer)");
        singlePlayerButton.setTranslateY(-35);
        singlePlayerButton.setMinSize(200,75);
        singlePlayerButton.setFont(Font.font(20));
        singlePlayerButton.setOnAction(e -> {
            MenuSinglePlayer.showMenu();
            primaryStage.close();
        });

        final Button twoPlayersButton = new Button("Two players game");
        twoPlayersButton.setTranslateY(55);
        twoPlayersButton.setMinSize(200,75);
        twoPlayersButton.setFont(Font.font(20));
        twoPlayersButton.setOnAction(e -> {
            MenuTwoPlayers.showMenu();
            primaryStage.close();
        });

        layout.getChildren().addAll(singlePlayerButton, twoPlayersButton);

        // Load Game button
        final Button loadGameButton = new Button("Load Game");
        loadGameButton.setTranslateX(150);
        loadGameButton.setTranslateY(145);
        loadGameButton.setMinSize(100,30);
        loadGameButton.setOnAction(e -> GameSaverLoader.loadGame());
        loadGameButton.setVisible(GameSaverLoader.areSavedGames());
        layout.getChildren().add(loadGameButton);

        // Ranking button
        final Button rankingButton = new Button("Ranking");
        rankingButton.setTranslateX(150);
        rankingButton.setTranslateY(195);
        rankingButton.setMinSize(100,30);
        rankingButton.setOnAction(e -> Ranking.display());
        layout.getChildren().add(rankingButton);



        //Set stage
        Scene welcomeScreen = new Scene(layout, 450, 450, Color.BLACK);
        window.setTitle("Tic Tac Toe");
        window.setScene(welcomeScreen);
        window.show();
    }
}
