package com.example.project2.model;

public class WallMessage {

    private String meesage;
    private String url;
    private String name;
    private String date;

    public WallMessage() {
    }

    public WallMessage(String meesage, String url, String name, String date) {
        this.meesage = meesage;
        this.url = url;
        this.name = name;
        this.date = date;
    }

    public String getMeesage() {
        return meesage;
    }

    public void setMeesage(String meesage) {
        this.meesage = meesage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
