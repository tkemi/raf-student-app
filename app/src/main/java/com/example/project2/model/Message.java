package com.example.project2.model;

public class Message {

    public static final int TYPE_RESPONSE = 0;
    public static final int TYPE_MESSAGE = 1;

    private String senderIndex;
    private String date;
    private String text;
    private int type;

    public Message() {
    }

    public Message(String date, String text, int type) {
        this.date = date;
        this.text = text;
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSenderIndex() {
        return senderIndex;
    }

    public void setSenderIndex(String senderIndex) {
        this.senderIndex = senderIndex;
    }
}
