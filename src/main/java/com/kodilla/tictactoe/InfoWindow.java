package com.kodilla.tictactoe;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class InfoWindow {

    private final StackPane stackPane;
    private final Label info;

    public InfoWindow(String message) {
        stackPane = new StackPane();
        info = new Label(message);
        info.setAlignment(Pos.CENTER);
        info.setFont(Font.font(24));
        stackPane.getChildren().add(info);
    }

    public StackPane getStackPane() {
        return stackPane;
    }

    public void setInfo(String message) {
        info.setText(message);
    }
}
