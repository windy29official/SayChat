package com.windypermadi.saychat.adapter;

public class Chat {
    private String sender;
    private String receiver;
    private String message;

    public Chat(){

    }

    public Chat(String sender, String receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }
}
