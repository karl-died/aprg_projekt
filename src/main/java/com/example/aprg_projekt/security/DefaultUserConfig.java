package com.example.aprg_projekt.security;

import com.example.aprg_projekt.models.Account;
import com.example.aprg_projekt.models.Profile;
import com.example.aprg_projekt.models.Role;
import com.example.aprg_projekt.repositories.AccountRepository;
import com.example.aprg_projekt.repositories.ProfileRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.UUID;

@Configuration
class DefaultUserConfig {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;

    DefaultUserConfig(AccountRepository accountRepository, PasswordEncoder passwordEncoder, ProfileRepository profileRepository) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.profileRepository = profileRepository;
    }

    /**
     * CommandLineRunner is executed once when the application is started and the
     * Spring context has been loaded.
     * <p>
     * !! Only for demo purposes and should not be used in real environments !!
     *
     * @see <a href="https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/index.html"></a>
     */
    @Bean
    CommandLineRunner onApplicationStart() {
        return _ -> {
            addAccountToDatabaseOnce("user@haw-hamburg.de", "user", Role.USER);
            addAccountToDatabaseOnce("admin@haw-hamburg.de", "admin", Role.USER, Role.ADMIN);
            addAccountToDatabaseOnce("sara@haw-hamburg.de", "sara", Role.USER);
            addAccountToDatabaseOnce("lara@haw-hamburg.de", "lara", Role.USER);
            addAccountToDatabaseOnce("tamara@haw-hamburg.de", "tamara", Role.USER);

            addProfileToDatabaseOnce("sara@haw-hamburg.de", new Profile(UUID.randomUUID(),"Sara", "Müller", LocalDate.parse("2001-01-01"), "female", "Media Systems", 3, "Hallo ich bin Sara", "profilePic.jpg", new String[] {}));
            addProfileToDatabaseOnce("lara@haw-hamburg.de", new Profile("Lara", "Meyer", LocalDate.parse("2000-01-01"), "female", "Kommunikationsdesign", 5, "Hallo ich bin Lara"));
            addProfileToDatabaseOnce("tamara@haw-hamburg.de", new Profile("Tamara", "Schmidt", LocalDate.parse("2002-03-03"), "female", "Media Systems", 6, "Hallo ich bin Tamara"));

            profileRepository.addImage("sara@haw-hamburg.de", "51921234-b982-4c3c-9938-a976241c8c2cScreenshot 2024-05-07 at 18.53.51.png");
        };
    }

    private void addUserToDatabaseOnce(String email, String password, String... authorities) {
        if (doesNotExistsInDatabase(email)) {
            addAccountToDatabaseOnce(email, password, authorities);
        }
    }

    private boolean doesNotExistsInDatabase(String email) {
        return accountRepository.findByEmail(email).isEmpty();
    }

    private void addAccountToDatabaseOnce(String email, String password, String... authorities) {
        var newDefaultUser = new Account(email, passwordEncoder.encode(password), authorities); // raw passwords must be encoded!
        accountRepository.save(newDefaultUser);
    }

    private void addProfileToDatabaseOnce(String email, Profile profile) {
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
