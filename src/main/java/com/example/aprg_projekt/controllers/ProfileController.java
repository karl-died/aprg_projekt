package com.example.aprg_projekt.controllers;

import com.example.aprg_projekt.models.Profile;
import com.example.aprg_projekt.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/profile")
public class ProfileController {


    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }


    @GetMapping("/profiles")
    public List<Profile> getAll() {
        return profileService.getAll();
    }
}
