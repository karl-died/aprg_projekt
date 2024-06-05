package com.example.aprg_projekt.controllers;

import com.example.aprg_projekt.models.Account;
import com.example.aprg_projekt.models.ChatMessage;
import com.example.aprg_projekt.models.Profile;
import com.example.aprg_projekt.models.ProfileDTO;
import com.example.aprg_projekt.services.ChatService;
import com.example.aprg_projekt.services.ProfileService;
import com.example.aprg_projekt.utils.Redirect;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/chat")
public class ChatController {

    private ChatService chatService;
    private ProfileService profileService;

    public ChatController(ChatService chatService, ProfileService profileService) {
        this.chatService = chatService;
        this.profileService = profileService;
    }

    @GetMapping("/with")
    public String chatPage(Model model, @RequestParam(name = "profileId") UUID profileId, Authentication  auth) {
        if (auth.getPrincipal() instanceof Account account) {
            List<ChatMessage> chatMessages = chatService.getChatMessages(account.getEmail(), profileId);
            model.addAttribute("chatMessages", chatMessages);
            Optional<Profile> profile = profileService.getById(profileId);
            model.addAttribute("profile", new ProfileDTO(profile.get()));
        }
        return "chat";
    }

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.CREATED)
    public void sendMessage(@ModelAttribute(name = "messageInput") String message, @RequestParam(name = "profileId") UUID profileId, Authentication  auth) {
        if (auth.getPrincipal() instanceof Account account) {
            chatService.postMessage(account.getEmail(), profileId, message, LocalDateTime.now());
        }
    }

}
