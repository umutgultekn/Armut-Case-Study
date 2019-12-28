package com.ugultekin.casestudy.model;

import com.google.gson.annotations.SerializedName;

public class Card {

    @SerializedName("cardId")
    private String cardId;

    @SerializedName("dbfId")
    private String dbfId;

    @SerializedName("name")
    private String name;

    @SerializedName("cardSet")
    private String cardSet;

    @SerializedName("type")
    private String type;

    @SerializedName("rarity")
    private String rarity;

    @SerializedName("text")
    private String text;

    @SerializedName("flavor")
    private String flavor;

    @SerializedName("artist")
    private String artist;

    @SerializedName("playerClass")
    private String playerClass;

    @SerializedName("img")
    private String img;

    @SerializedName("imgGold")
    private String imgGold;

    @SerializedName("locale")
    private String locale;

    @SerializedName("cost")
    private String cost;

    @SerializedName("attack")
    private String attack;

    @SerializedName("health")
    private String health;

    @SerializedName("collectible")
    private boolean collectible;

    public String getCardId() {
        return cardId;
    }

    public String getDbfId() {
        return dbfId;
    }

    public String getName() {
        return name;
    }

    public String getCardSet() {
        return cardSet;
    }

    public String getType() {
        return type;
    }

    public String getRarity() {
        return rarity;
    }

    public String getText() {
        return text;
    }

    public String getFlavor() {
        return flavor;
    }

    public String getArtist() {
        return artist;
    }

    public String getPlayerClass() {
        return playerClass;
    }

    public String getImg() {
        return img;
    }

    public String getImgGold() {
        return imgGold;
    }

    public String getLocale() {
        return locale;
    }

    public String getCost() {
        return cost;
    }

    public String getAttack() {
        return attack;
    }

    public String getHealth() {
        return health;
    }

    public boolean isCollectible() {
        return collectible;
    }


}