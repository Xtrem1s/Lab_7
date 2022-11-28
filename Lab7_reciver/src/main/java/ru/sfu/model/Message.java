package ru.sfu.model;

import java.io.Serializable;

public class Message implements Serializable {
    private String message;
    private TableGame tableGame;

    public Message(String message, TableGame tableGame) {
        this.message = message;
        this.tableGame = tableGame;
    }

    public Message() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TableGame getTableGame() {
        return tableGame;
    }

    public void setTableGame(TableGame tableGame) {
        this.tableGame = tableGame;
    }
}
