package com.example.beproj3.Models;

public class Chats {
    public String chat;
    public String time;
    public String who_tells;
    public String type;
    public String url;

    public Chats(){ }

    public Chats(String chat, String time, String who_tells, String type,String url) {
        this.chat = chat;
        this.time = time;
        this.who_tells = who_tells;
        this.type = type;
        this.url = url;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWho_tells() {
        return who_tells;
    }

    public void setWho_tells(String who_tells) {
        this.who_tells = who_tells;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
