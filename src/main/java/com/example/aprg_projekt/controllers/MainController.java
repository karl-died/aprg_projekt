package com.example.aprg_projekt.controllers;

import com.example.aprg_projekt.models.Account;
import com.example.aprg_projekt.models.Profile;
import com.example.aprg_projekt.services.ProfileService;
import com.example.aprg_projekt.utils.Redirect;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

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

    @GetMapping("/welcome")
    String welcomePage(Model model, Authentication authentication) {
        if (authentication.getPrincipal() instanceof Account account) { // Converts getPrincipal() to User and stores the object into the variable user that is accessible within the following if-block.
            model.addAttribute(EMAIL_KEY, account.getEmail());
            String email = authentication.getName();
            Optional<Profile> profile = profileService.getByEmail(email);
            if (!profile.isPresent()) {
                return Redirect.to("/profile/edit");
            } else {
                System.out.println(profile.get());
            }
        }
        return "welcome";
    }

    @GetMapping("/register")
    String registrationPage(Model model, Authentication authentication) {
        return "register";
    }
}