package com.example.aprg_projekt.controllers;

import com.example.aprg_projekt.models.Account;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class MainController {

    private static final String AUTHENTICATED_KEY = "authenticated";
    private static final String AUTHORITIES_KEY = "authorities";
    private static final String EMAIL_KEY = "email";


    @GetMapping("/")
    String landingPage(Model model, Authentication authentication) {
        if (authentication == null) {
            model.addAttribute(AUTHENTICATED_KEY, false);
        } else {
            model.addAttribute(AUTHENTICATED_KEY, authentication.isAuthenticated());
        }
        System.out.println("hello");
        return "index";
    }

    @GetMapping("/welcome")
    String welcomePage(Model model, Authentication authentication) {
        if (authentication.getPrincipal() instanceof Account account) { // Converts getPrincipal() to User and stores the object into the variable user that is accessible within the following if-block.
            model.addAttribute(EMAIL_KEY, account.getEmail());
        }
        return "welcome";
    }

    @GetMapping("/register")
    String registrationPage(Model model, Authentication authentication) {
        return "register";
    }
}