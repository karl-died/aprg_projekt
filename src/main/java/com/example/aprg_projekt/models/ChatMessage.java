package com.example.aprg_projekt.models;

import java.time.LocalDateTime;

public class ChatMessage {
    private String message;
    private boolean isIncoming;
    private LocalDateTime dateSent;

    public ChatMessage(String message, boolean isIncoming, LocalDateTime dateSent) {
        this.message = message;
        this.isIncoming = isIncoming;
        this.dateSent = dateSent;
    }

    public ChatMessage() {
    }

    public String getMessage() {
        return message;
    }

    public boolean isIncoming() {
        return isIncoming;
    }

    public LocalDateTime getDateSent() {
        return dateSent;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setIncoming(boolean incoming) {
        isIncoming = incoming;
    }

    public void setDateSent(LocalDateTime dateSent) {
        this.dateSent = dateSent;
    }
}
