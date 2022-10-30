package com.kodilla.tictactoe;

import java.io.Serializable;

class Player implements Serializable {

    private final String playerName;
    private final String playerMark;
    private final Type type;

    public Player(String playerName, String playerMark, Type type) {
        this.playerName = playerName;
        this.playerMark = playerMark;
        this.type = type;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlayerMark() {
        return playerMark;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        COMPUTER,
        HUMAN
    }
}
