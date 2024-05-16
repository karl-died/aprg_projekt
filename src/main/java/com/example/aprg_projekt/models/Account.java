package com.example.aprg_projekt.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Table("ACCOUNT")
public class Account {

    @Id
    private UUID id;
    private String email;
    private String password;
    private Profile profile;

    public Account() {
    }

    public Account(UUID id, String email, String password, Profile profile) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.profile = profile;
    }

    public Account(AccountDTO accountDTO) {
        this.email = accountDTO.getEmail();
        this.password = accountDTO.getPassword();
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", profile=" + profile +
                '}';
    }
}