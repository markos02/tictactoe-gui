module com.kodilla.tictactoe {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.kodilla.tictactoe to javafx.fxml;
    exports com.kodilla.tictactoe;
}