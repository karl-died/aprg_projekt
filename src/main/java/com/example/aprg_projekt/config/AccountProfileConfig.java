package com.example.aprg_projekt.config;

import com.example.aprg_projekt.models.Account;
import com.example.aprg_projekt.models.Profile;
import com.example.aprg_projekt.repositories.AccountRepository;
import com.example.aprg_projekt.repositories.ProfileRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Locale;

@Configuration
public class AccountProfileConfig {

    @Bean
    CommandLineRunner init(AccountRepository accountRepository,
                           ProfileRepository profileRepository) {
        return args -> {
            Profile profile = new Profile(
                    "Informatik",
                    2,
                    "Hello its me"
            );

            Account account = new Account(
                    "ernie@example.com",
                    "password",
                    "Ernie",
                    "Example",
                    LocalDate.of(2001,1,1),
                    "male",
                    profile
            );
            profileRepository.save(profile);
            accountRepository.save(account);

        };
    }
}
