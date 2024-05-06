package com.example.aprg_projekt.repositories;

import com.example.aprg_projekt.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {
}
