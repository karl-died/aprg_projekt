package com.example.aprg_projekt.services;


import com.example.aprg_projekt.models.Account;
import com.example.aprg_projekt.models.Profile;
import com.example.aprg_projekt.repositories.AccountRepository;
import com.example.aprg_projekt.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AccountRepository accountRepository;

    public Optional<Profile> getById(UUID id) {
        Optional<Profile> profile = profileRepository.findById(id);
        return profile;
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

    public List<Profile> getNonRatedProfiles(String email) {
        Optional<Account> account = accountRepository.findByEmail(email);
        if(!account.isPresent()) {
            throw new IllegalArgumentException("Account not found");
        }

        UUID id = account.get().getId();
        List<Profile> profiles = profileRepository.getNonRatedProfiles(id);
        return profiles;
    }

    public Optional<Profile> getOneNonRatedProfile(String email) {
        Optional<Account> account = accountRepository.findByEmail(email);
        if(!account.isPresent()) {
            throw new IllegalArgumentException("Account not found");
        }

        UUID id = account.get().getId();
        return profileRepository.getOneNonRatedProfile(id);
    }

    public List<Profile> getLikedProfiles(String email) {
        Optional<Account> account = accountRepository.findByEmail(email);
        if(!account.isPresent()) {
            throw new IllegalArgumentException("Account not found");
        }

        UUID id = account.get().getId();
        List<Profile> profiles = profileRepository.getLikedProfiles(id);
        return profiles;
    }

    public void rate(String email, UUID ratedId, boolean isLike) {
        profileRepository.rate(email, ratedId, isLike);
    }

    public boolean isAllowedToViewProfile(String email, UUID profileId) {
        Optional<Account> account = accountRepository.findByEmail(email);
        if(!account.isPresent()) {
            throw new IllegalArgumentException("Account not found");
        }
        List<Profile> likedProfiles = profileRepository.getLikedProfiles(account.get().getId());
        Optional<Profile> ownProfile = profileRepository.findByEmail(email);
        if(!ownProfile.isPresent()) {
            throw new IllegalArgumentException("Profile not found");
        }

        for(Profile profile : likedProfiles) {
            if(profile.getId().equals(profileId) || ownProfile.get().getId().equals(ownProfile.get().getId())) {
                return true;
            }
        }

        return false;
    }
}
