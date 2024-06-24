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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Controller
public class ChatController {

    private ChatService chatService;
    private ProfileService profileService;

    public ChatController(ChatService chatService, ProfileService profileService) {
        this.chatService = chatService;
        this.profileService = profileService;
    }

    @GetMapping("/chat")
    public String chatPage(Model model, @RequestParam(name = "profileId") UUID profileId, Authentication auth) {

        List<ChatMessage> chatMessages = Arrays.asList(
                new ChatMessage("Hallo!", true, LocalDateTime.now().plusMinutes(-20)),
                new ChatMessage("Was geht?", false, LocalDateTime.now().plusMinutes(-18)),
                new ChatMessage("Nicht viel", true, LocalDateTime.now().plusMinutes(-17)),
                new ChatMessage("Aha cool", false, LocalDateTime.now().plusMinutes(-14)),
                new ChatMessage("Was ist eigentlich dein Sternzeichen? Sowas finde ich nämlich mega wichtig und so, weil ich kann echt nur mit Waage und Krebs, alle anderen fucken mich nur ab. Also wenn du nicht Waage oder Krebs bist kannst du mich direkt blockieren.", true, LocalDateTime.now().plusMinutes(-12)),
                new ChatMessage("Dann halt nicht, tschüss!", false, LocalDateTime.now().plusMinutes(-2))
        );
        model.addAttribute("messages", chatMessages);
        Profile dummy = new Profile(UUID.randomUUID(), "Lara", "Meyer", LocalDate.parse("2000-01-01"), "female", "Kommunikationsdesign", 5, "Hallo ich bin Lara", "pfp1.jpeg", new String[] {});
        model.addAttribute("profile", new ProfileDTO(dummy));

        return "chat";
    }

    @GetMapping("/messages")
    public @ResponseBody List<String> getMessages(@RequestParam(name = "profileId") UUID profileId, Authentication  auth) {
        List<String> messageStrings = new ArrayList<>();
        return messageStrings;
    }

    @PostMapping("/chat/send")
    @ResponseStatus(HttpStatus.CREATED)
    public void sendMessage(@ModelAttribute(name = "messageInput") String message, @RequestParam(name = "profileId") UUID profileId, Authentication  auth) {

    }

}
