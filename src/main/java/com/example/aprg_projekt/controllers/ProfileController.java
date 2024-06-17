package com.example.aprg_projekt.controllers;

import com.example.aprg_projekt.models.Account;
import com.example.aprg_projekt.models.GenderOption;
import com.example.aprg_projekt.models.Profile;
import com.example.aprg_projekt.models.ProfileDTO;
import com.example.aprg_projekt.services.ProfileService;
import com.example.aprg_projekt.utils.Redirect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/save")
    public String saveProfile(@ModelAttribute Profile profile,
                              @RequestParam(name = "image") MultipartFile image,
                              @RequestParam(name = "Interested In") List<String> interestedIn,
                              Authentication authentication) {
        profileService.save(authentication.getName(), profile);
        if(!image.isEmpty()) {
            try {
                profileService.addImage(authentication.getName(), image);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("error");
            }
        }

        profileService.addGenderInterest(authentication.getName(), interestedIn);
        return Redirect.to("/");
    }

    @GetMapping("/edit")
    public String editProfile(Model model, Authentication authentication) {
        Optional<Profile> profileOptional = profileService.getByEmail(authentication.getName());

        List<String> genders = profileService.getGenderOptions();

        List<GenderOption> genderOptions = new ArrayList<>();
        List<GenderOption> interestedInOptions = new ArrayList<>();



        if(profileOptional.isPresent()) {
            for (String gender : genders) {
                if(profileOptional.get().getGender().equals(gender)) {
                    genderOptions.add(new GenderOption(gender, "selected"));
                } else {
                    genderOptions.add(new GenderOption(gender, ""));
                }
                if(profileOptional.get().getInterestedIn().contains(gender)) {
                    interestedInOptions.add(new GenderOption(gender, "selected"));
                } else {
                    interestedInOptions.add(new GenderOption(gender, ""));
                }
            }
            Profile profile = profileOptional.get();
            model.addAttribute("exists", true);
            model.addAttribute("firstName", profile.getFirstName());
            model.addAttribute("lastName", profile.getLastName());
            model.addAttribute("dateOfBirth", profile.getDateOfBirth());
            model.addAttribute("gender", profile.getGender());
            model.addAttribute("degreeCourse", profile.getDegreeCourse());
            model.addAttribute("aboutMe", profile.getAboutMe());
            model.addAttribute("semester", profile.getSemester());
            model.addAttribute("imageNames", profile.getImageNames());
        } else {
            for (String gender : genders) {
                genderOptions.add(new GenderOption(gender, ""));
            }

            model.addAttribute("exists", false);
            model.addAttribute("firstName", "");
            model.addAttribute("lastName", "");
            model.addAttribute("dateOfBirth", "");
            model.addAttribute("gender", "");
            model.addAttribute("degreeCourse", "");
            model.addAttribute("aboutMe", "");
            model.addAttribute("semester", "");
        }

        model.addAttribute("genderOptions", genderOptions);
        model.addAttribute("interestedInOptions", interestedInOptions);


        return "editProfile";
    }

    @GetMapping("/")
    public String showProfile(Model model, @RequestParam(name = "profileId") UUID profileId, Authentication authentication) {
        if(!profileService.isAllowedToViewProfile(authentication.getName(), profileId)) {
            return Redirect.to("/");
        }
        Optional<Profile> profileOptional = profileService.getById(profileId);
        if(profileOptional.isPresent()) {
            model.addAttribute("profile", new ProfileDTO(profileOptional.get()));
        }
        return "viewProfile";
    }

    @GetMapping("/likes")
    public String showLikedProfiles(Model model, Authentication authentication) {
        if (authentication.getPrincipal() instanceof Account account) {
            List<Profile> likedProfiles = profileService.getLikedProfiles(account.getEmail());
            List<ProfileDTO> displayProfiles = new ArrayList<>();
            for (Profile profile : likedProfiles) {
                displayProfiles.add(new ProfileDTO(profile));
            }
            model.addAttribute("profiles", displayProfiles);
        }
        return "likedProfiles";
    }

    @GetMapping("/matches")
    public String showMatches(Model model, Authentication authentication) {
        if (authentication.getPrincipal() instanceof Account account) {
            List<Profile> likedProfiles = profileService.getMatches(account.getEmail());
            List<ProfileDTO> matches = new ArrayList<>();
            List<ProfileDTO> chats = new ArrayList<>();
            for (Profile profile : likedProfiles) {
                if(profile.getLastChatMessage() == null) {
                    matches.add(new ProfileDTO(profile));
                } else {
                    chats.add(new ProfileDTO(profile));
                }
            }
            model.addAttribute("matches", matches);
            model.addAttribute("chats", chats);
        }
        return "matches";
    }
}
