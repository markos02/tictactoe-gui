package com.kodilla.tictactoe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Stream;

public class Ranking {

    private static final Path FILE = Paths.get("C:\\Users\\mk\\Documents\\Private\\dev\\kodilla-course\\tictactoe-gui\\src\\main\\resources\\ranking.txt");

    public static void display() {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Tic Tac Toe: Ranking");
        window.setMinWidth(300);
        window.setMinHeight(500);

        TableView<RankingLine> tableLayout = new TableView<>();

        TableColumn<RankingLine, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(100);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableLayout.getColumns().add(nameColumn);

        TableColumn<RankingLine, Integer> totalGamesColumn = new TableColumn<>("Total Games");
        totalGamesColumn.setMinWidth(100);
        totalGamesColumn.setCellValueFactory(new PropertyValueFactory<>("totalGames"));
        tableLayout.getColumns().add(totalGamesColumn);

        TableColumn<RankingLine, Integer> gamesWonColumn = new TableColumn<>("Games Won");
        gamesWonColumn.setMinWidth(100);
        gamesWonColumn.setCellValueFactory(new PropertyValueFactory<>("gamesWon"));
        gamesWonColumn.setSortType(TableColumn.SortType.DESCENDING);
        tableLayout.getColumns().add(gamesWonColumn);

        TableColumn<RankingLine, LocalDate> dateColumn = new TableColumn<>("Date");
        dateColumn.setMinWidth(100);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableLayout.getColumns().add(dateColumn);

        try {
            tableLayout.setItems(getRank());
        } catch (IOException ignored) {
        }

        tableLayout.getSortOrder().add(gamesWonColumn);

        StackPane titleLayout = new StackPane();

        Label titleLabel = new Label("Game ranking");
        titleLabel.setFont(Font.font(20));
        titleLayout.getChildren().add(titleLabel);

        Button closeButton = new Button("Close");
        closeButton.setTranslateX(300);
        closeButton.setTranslateY(25);
        closeButton.setOnAction(e -> window.close());
        titleLayout.getChildren().add(closeButton);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(titleLayout);
        borderPane.setBottom(tableLayout);

        Scene scene = new Scene(borderPane);
        window.setScene(scene);
        window.showAndWait();
    }

    public static ObservableList<RankingLine> getRank() throws IOException {

        ObservableList<RankingLine> ranking = FXCollections.observableArrayList();

        Stream<String> stream = Files.lines(FILE);
        stream.skip(1)
                .forEach(e -> ranking.add(new RankingLine(e)));

        return ranking;
    }

    public static class RankingLine {

        private final String name;
        private final int totalGames;
        private final int gamesWon;
        private final LocalDate date;

        public RankingLine(String name, int totalGames, int gamesWon, LocalDate date) {
            this.name = name;
            this.totalGames = totalGames;
            this.gamesWon = gamesWon;
            this.date = date;
        }

        public RankingLine(String rankLine) {

            int indexTotalGames = rankLine.indexOf('|');
            int indexGamesWon = rankLine.indexOf('|', indexTotalGames + 1);
            int indexDate = rankLine.indexOf('|', indexGamesWon + 1);

            this.name = rankLine.substring(0, indexTotalGames - 1);
            this.totalGames = Integer.parseInt(rankLine.substring(indexTotalGames + 2, indexGamesWon - 1));
            this.gamesWon = Integer.parseInt(rankLine.substring(indexGamesWon + 2, indexDate - 1));
            this.date = LocalDate.parse(rankLine.substring(indexDate + 2));
        }

        public String getName() {
            return name;
        }

        public int getTotalGames() {
            return totalGames;
        }

        public int getGamesWon() {
            return gamesWon;
        }

        public LocalDate getDate() {
            return date;
        }
    }
}
