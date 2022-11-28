package ru.sfu.model;

import java.io.Serializable;


public class TableGame implements Serializable {

    int id;

    String gameName;

    int price;

    int playerAmount;

    String genre;

    public TableGame(){}

    public TableGame(int id, String gamename, int price, int playerAmount, String genre) {
        this.id = id;
        this.gameName = gamename;
        this.price = price;
        this.playerAmount = playerAmount;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPlayerAmount() {
        return playerAmount;
    }

    public void setPlayerAmount(int playerAmount) {
        this.playerAmount = playerAmount;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString(){
        return "id: " + id + ", name: " + gameName + ", price: " + price + ", playersAmount: " + playerAmount + ", genre: " + genre;
    }
}
