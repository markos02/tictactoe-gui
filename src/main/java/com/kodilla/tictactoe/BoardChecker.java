package com.kodilla.tictactoe;

public class BoardChecker {

    private final int column;
    private final int row;
    private final int value;

    public BoardChecker(int row, int column, int value) {
        this.column = column;
        this.row = row;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    @Override
    public String toString() {
        return "row:" + row +
                ", column:" + column +
                ", value=" + value +
                '\n';
    }
}
