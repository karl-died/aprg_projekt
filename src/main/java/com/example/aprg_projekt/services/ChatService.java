package com.example.aprg_projekt.services;

import com.example.aprg_projekt.models.ChatMessage;
import com.example.aprg_projekt.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ChatService {

    private ChatRepository chatRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public void postMessage(String senderEmail, UUID recipientProfileId, String message, LocalDateTime dateSent) {
        chatRepository.postMessage(senderEmail, recipientProfileId, message, dateSent);
    }

    public List<ChatMessage> getChatMessages(String senderEmail, UUID recipientProfileId) {
        return chatRepository.getChatMessages(senderEmail, recipientProfileId);
    }
}
