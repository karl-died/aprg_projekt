package com.example.aprg_projekt.repositories;

import com.example.aprg_projekt.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}
