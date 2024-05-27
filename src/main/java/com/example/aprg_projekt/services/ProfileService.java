package com.example.aprg_projekt.services;


import com.example.aprg_projekt.models.Profile;
import com.example.aprg_projekt.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public List<Profile> getAll() {
        Iterable<Profile> profiles = profileRepository.getAll();
        List<Profile> profileList = new ArrayList<>();
        for (Profile profile : profiles) {
            profileList.add(profile);
        }
        return profileList;
    }

    public Optional<Profile> getByEmail(String email) {
        Optional<Profile> profile = profileRepository.findByEmail(email);
        return profile;
    }

    public void save(String email, Profile profile) {
        Optional<Profile> existingProifle = profileRepository.findByEmail(email);
        if (existingProifle.isPresent()) {
            profileRepository.update(
                    email,
                    profile.getFirstName(),
                    profile.getLastName(),
                    profile.getDateOfBirth(),
                    profile.getGender(),
                    profile.getDegreeCourse(),
                    profile.getSemester(),
                    profile.getAboutMe()
            );
        } else {
            profileRepository.save(
                    email,
                    profile.getFirstName(),
                    profile.getLastName(),
                    profile.getDateOfBirth(),
                    profile.getGender(),
                    profile.getDegreeCourse(),
                    profile.getSemester(),
                    profile.getAboutMe()
            );
        }
    }
}
