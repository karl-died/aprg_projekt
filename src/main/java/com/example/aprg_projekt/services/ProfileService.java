package com.example.aprg_projekt.services;


import com.example.aprg_projekt.models.Account;
import com.example.aprg_projekt.models.Profile;
import com.example.aprg_projekt.repositories.AccountRepository;
import com.example.aprg_projekt.repositories.ImageRepository;
import com.example.aprg_projekt.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountNotFoundException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ImageRepository imageRepository;

    String uploadDirectory = "uploads/";

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
                    profile.getAboutMe(),
                    profile.getProfilePicture()
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
                    profile.getAboutMe(),
                    profile.getProfilePicture()
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

        /*
        Optional<Profile> profileOptional = profileRepository.getOneNonRatedProfile(id);
        if(profileOptional.isPresent()) {
            String[] imageNames = imageRepository.getImages(email);
            System.out.println(Arrays.toString(imageNames));
            Profile profile = profileOptional.get();
            profile.setImageNames(imageNames);
            return Optional.of(profile);
        }
        */
        Optional<Profile> profileOptional = imageRepository.getOneNonRatedProfile(id);

        return profileOptional;
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

    public List<Profile> getMatches(String email) {
        Optional<Account> account = accountRepository.findByEmail(email);
        if(!account.isPresent()) {
            throw new IllegalArgumentException("Account not found");
        }

        UUID id = account.get().getId();
        List<Profile> profiles = profileRepository.getMatches(id);
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

    public void addImage(String email, MultipartFile image) throws IOException {
        String uniqueFileName = UUID.randomUUID().toString() + image.getOriginalFilename();
        Path uploadPath = Path.of(this.uploadDirectory);
        Path filePath = uploadPath.resolve(uniqueFileName);

        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Files.copy(image.getInputStream(), filePath);
        profileRepository.addImage(email, uniqueFileName);

        System.out.println(filePath);
    }

    public List<String> getGenderOptions() {
        return profileRepository.getGenderOptions();
    }

    public void addGenderInterest(String email, List<String> selectedGenders) {
        List<String> genders = profileRepository.getGenderOptions();
        Optional<Account> account = accountRepository.findByEmail(email);

        if(account.isPresent()) {
            for (String gender : genders) {
                if (selectedGenders.contains(gender)) {
                    profileRepository.addGenderInterest(account.get().getId(), gender);
                } else {
                    profileRepository.removeGenderInterest(account.get().getId(), gender);
                }
            }
        }
    }
}
