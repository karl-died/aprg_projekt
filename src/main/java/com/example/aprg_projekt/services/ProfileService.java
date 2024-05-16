package com.example.aprg_projekt.services;


import com.example.aprg_projekt.models.Profile;
import com.example.aprg_projekt.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
