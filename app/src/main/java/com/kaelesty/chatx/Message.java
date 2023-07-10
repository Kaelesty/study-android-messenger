package com.kaelesty.chatx;

public class Message {
    private String text;
    private String senderID;
    private String recieverID;

    public String getText() {
        return text;
    }

    public String getSenderID() {
        return senderID;
    }

    public String getRecieverID() {
        return recieverID;
    }

    public Message() {}

    public Message(String text, String senderID, String recieverID) {
        this.text = text;
        this.senderID = senderID;
        this.recieverID = recieverID;
    }
}
