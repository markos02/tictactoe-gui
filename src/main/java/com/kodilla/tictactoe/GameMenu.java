package com.kodilla.tictactoe;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class GameMenu {

    private final StackPane stackPane;
    private final Button closeGameButton;
    private final Button newGameButton;
    private final Button finishedGameCloseButton;
    private final GameWindow gameWindow;

    public GameMenu(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        stackPane = new StackPane();

        closeGameButton = new Button("Close game");
        closeGameButton.setTranslateY(-20);
        closeGameButton.setFont(Font.font(18));
        closeGameButton.setMinSize(150, 18);
        closeGameButton.setOnAction(e -> closeGame());
        stackPane.getChildren().add(closeGameButton);

        newGameButton = new Button("Start new game");
        newGameButton.setTranslateX(-200);
        newGameButton.setTranslateY(-20);
        newGameButton.setFont(Font.font(18));
        newGameButton.setMinSize(150, 18);
        newGameButton.setVisible(false);
        newGameButton.setOnAction(e -> {
            MainMenu.window.show();
            gameWindow.window.close();
        });
        stackPane.getChildren().add(newGameButton);

        finishedGameCloseButton = new Button("Close");
        finishedGameCloseButton.setTranslateX(200);
        finishedGameCloseButton.setTranslateY(-20);
        finishedGameCloseButton.setFont(Font.font(18));
        finishedGameCloseButton.setMinSize(150, 18);
        finishedGameCloseButton.setOnAction(e -> gameWindow.window.close());
        finishedGameCloseButton.setVisible(false);
        stackPane.getChildren().add(finishedGameCloseButton);
    }

    public StackPane getStackPane() {
        return stackPane;
    }

    public void closeGame() {

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Close game");
        window.setMinWidth(250);

        Label label1 = new Label();
        label1.setText("Do you want to save the game?");

        Button yesButton = new Button("Yes");
        yesButton.setOnAction(e -> {
            gameWindow.getGameLogic().saveGame();
            window.close();
            gameWindow.window.close();
        });

        Button noButton = new Button("No");
        noButton.setOnAction(e -> {
            window.close();
            gameWindow.window.close();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> window.close());

        HBox layout = new HBox(10);
        layout.getChildren().addAll(label1, yesButton, noButton, cancelButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public void hideCloseButton(){
        closeGameButton.setVisible(false);
    }

    public void showNewGameButton() {
        newGameButton.setVisible(true);
    }

    public void showFinishedGameCloseButton() {
        finishedGameCloseButton.setVisible(true);
    }

}
