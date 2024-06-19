package com.example.aprg_projekt.controllers;

import com.example.aprg_projekt.models.Account;
import com.example.aprg_projekt.models.ChatMessage;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/save")
    public String saveProfile(@ModelAttribute Profile profile,
                              @RequestParam(name = "image") MultipartFile image,
                              Authentication authentication) {
        /*
        profileService.save(authentication.getName(), profile);
        try {
            profileService.addImage(authentication.getName(), image);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error");
        }

         */
        return Redirect.to("/");
    }

    @GetMapping("/edit")
    public String editProfile(Model model, Authentication authentication) {
        Profile dummy = new Profile(UUID.randomUUID(),"Lara", "Meyer", LocalDate.parse("2000-01-01"), "female", "Kommunikationsdesign", 5, "Hallo ich bin Lara", "pfp2.jpeg", new String[] {"francis.jpg"});
        Profile profile = dummy;
        model.addAttribute("exists", true);
        model.addAttribute("firstName", profile.getFirstName());
        model.addAttribute("lastName", profile.getLastName());
        model.addAttribute("dateOfBirth", profile.getDateOfBirth());
        model.addAttribute("gender", profile.getGender());
        model.addAttribute("degreeCourse", profile.getDegreeCourse());
        model.addAttribute("aboutMe", profile.getAboutMe());
        model.addAttribute("semester", profile.getSemester());
        model.addAttribute("imageNames", profile.getImageNames());
        model.addAttribute("profilePicture", profile.getProfilePicture());

        return "editProfile";
    }

    @GetMapping("/edit/empty")
    public String editEmptyProfile(Model model, Authentication authentication) {

        model.addAttribute("exists", false);
        model.addAttribute("firstName", "");
        model.addAttribute("lastName", "");
        model.addAttribute("dateOfBirth", "");
        model.addAttribute("gender", "");
        model.addAttribute("degreeCourse", "");
        model.addAttribute("aboutMe", "");
        model.addAttribute("semester", "");
        model.addAttribute("profilePicture", "");

        return "editProfile";
    }





    @GetMapping("/matches")
    public String showMatches(Model model, Authentication authentication) {

        List<ProfileDTO>  matchedProfiles = Arrays.asList(
                new ProfileDTO(new Profile(UUID.randomUUID(),"Sara", "Müller", LocalDate.parse("2001-01-01"), "female", "Media Systems", 3, "Hallo ich bin Sara", "pfp2.jpeg", new String[] {})),
                new ProfileDTO(new Profile(UUID.randomUUID(),"Lara", "Müller", LocalDate.parse("2003-01-01"), "female", "Media Systems", 3, "Hallo ich bin Sara", "pfp1.jpeg", new String[] {})),
                new ProfileDTO(new Profile(UUID.randomUUID(),"Tamara", "Müller", LocalDate.parse("2000-01-01"), "female", "Media Systems", 3, "Hallo ich bin Sara", "pfp3.jpeg", new String[] {})),
                new ProfileDTO(new Profile(UUID.randomUUID(),"Frida", "Müller", LocalDate.parse("1999-01-01"), "female", "Media Systems", 3, "Hallo ich bin Sara", "pfp4.jpeg", new String[] {})),
                new ProfileDTO(new Profile(UUID.randomUUID(),"Lisa", "Müller", LocalDate.parse("2002-01-01"), "female", "Media Systems", 3, "Hallo ich bin Sara", "pfp5.jpeg", new String[] {})),
                new ProfileDTO(new Profile(UUID.randomUUID(),"Briana", "Müller", LocalDate.parse("2001-01-01"), "female", "Media Systems", 3, "Hallo ich bin Sara", "pfp6.jpeg", new String[] {}))
        );

        model.addAttribute("matches", matchedProfiles);

        List<ProfileDTO>  chats = Arrays.asList(
                new ProfileDTO(new Profile(UUID.randomUUID(),"Magdalena", "Müller", LocalDate.parse("2001-01-01"), "female", "Media Systems", 3, "Hallo ich bin Sara", "pfp7.jpeg", new String[] {})),
                new ProfileDTO(new Profile(UUID.randomUUID(),"Lara", "Müller", LocalDate.parse("2003-01-01"), "female", "Media Systems", 3, "Hallo ich bin Sara", "pfp8.jpeg", new String[] {})),
                new ProfileDTO(new Profile(UUID.randomUUID(),"Christine", "Müller", LocalDate.parse("2000-01-01"), "female", "Media Systems", 3, "Hallo ich bin Sara", "pfp1.jpeg", new String[] {})),
                new ProfileDTO(new Profile(UUID.randomUUID(),"Paula", "Müller", LocalDate.parse("1999-01-01"), "female", "Media Systems", 3, "Hallo ich bin Sara", "pfp2.jpeg", new String[] {})),
                new ProfileDTO(new Profile(UUID.randomUUID(),"Alana", "Müller", LocalDate.parse("2002-01-01"), "female", "Media Systems", 3, "Hallo ich bin Sara", "pfp3.jpeg", new String[] {})),
                new ProfileDTO(new Profile(UUID.randomUUID(),"Henriette", "Müller", LocalDate.parse("2001-01-01"), "female", "Media Systems", 3, "Hallo ich bin Sara", "pfp4.jpeg", new String[] {})),
                new ProfileDTO(new Profile(UUID.randomUUID(),"Fabienne", "Müller", LocalDate.parse("2003-01-01"), "female", "Media Systems", 3, "Hallo ich bin Sara", "pfp5.jpeg", new String[] {})),
                new ProfileDTO(new Profile(UUID.randomUUID(),"Franziska", "Müller", LocalDate.parse("2001-01-01"), "female", "Media Systems", 3, "Hallo ich bin Sara", "pfp6.jpeg", new String[] {}))
        );
        String[] messages = {"Hey, was geht?", "Hallo!", "Hey wie gehts?", "Hallöchen!", "Hallo", "Hallo, wie gehts? Ich bin Henriette und ich bin sehr froh dich kennen zu lernen! Mein Sternzeichen ist Zwilling falls es dich interessiert.", "Hallöchen!", "Hallo"};

        for(int i = 0; i < chats.size(); i++){
            chats.get(i).setLastChatMessage(new ChatMessage(messages[i], (i%2 == 0), LocalDateTime.now()));
        }

        model.addAttribute("chats", chats);

        return "matches";
    }

}
