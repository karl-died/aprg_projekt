package com.example.aprg_projekt.controllers;

import com.example.aprg_projekt.models.Account;
import com.example.aprg_projekt.models.Profile;
import com.example.aprg_projekt.models.ProfileDTO;
import com.example.aprg_projekt.services.ProfileService;
import com.example.aprg_projekt.utils.Redirect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
class MainController {

    private static final String AUTHENTICATED_KEY = "authenticated";
    private static final String AUTHORITIES_KEY = "authorities";
    private static final String EMAIL_KEY = "email";

    private final ProfileService profileService;

    public MainController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/")
    String landingPage(Model model, Authentication authentication) {
        if (authentication == null) {
            model.addAttribute(AUTHENTICATED_KEY, false);
        } else {
            model.addAttribute(AUTHENTICATED_KEY, authentication.isAuthenticated());

        }
        return "index";
    }

    @GetMapping("/login")
    String loginPage(Model model, Authentication authentication) {
        if(authentication == null) {
            return "login";
        } else {
            return Redirect.to("/");
        }
    }

    @GetMapping("/register")
    String registrationPage(Model model, Authentication authentication) {
        return "register";
    }

    @GetMapping("/rate")
    String ratePage(Model model, Authentication authentication) {
        if (authentication.getPrincipal() instanceof Account account) {
            Optional<Profile> profile = profileService.getOneNonRatedProfile(account.getEmail());
            if (profile.isPresent()) {
                model.addAttribute("profiles", new ProfileDTO(profile.get()));
            }
        }
        return "rate";
    }

    @PostMapping("/rate")
    String rateProfile(@RequestParam(name = "profileId") UUID profileId,
                     @RequestParam(name = "isLike") boolean isLike,
                     Authentication authentication) {
        if (authentication.getPrincipal() instanceof Account account) {
            profileService.rate(account.getEmail(), profileId, isLike);
        }
        return Redirect.to("/rate");
    }
}