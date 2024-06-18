package com.example.aprg_projekt.security;

import com.example.aprg_projekt.models.Account;
import com.example.aprg_projekt.models.Profile;
import com.example.aprg_projekt.models.Role;
import com.example.aprg_projekt.repositories.AccountRepository;
import com.example.aprg_projekt.repositories.ChatRepository;
import com.example.aprg_projekt.repositories.ProfileRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Optional;
import java.util.UUID;

@Configuration
class DefaultUserConfig {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;
    private final ChatRepository chatRepository;

    DefaultUserConfig(AccountRepository accountRepository,
                      PasswordEncoder passwordEncoder,
                      ProfileRepository profileRepository,
                      ChatRepository chatRepository) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.profileRepository = profileRepository;
        this.chatRepository = chatRepository;
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
            addAccountToDatabaseOnce("olli@haw-hamburg.de", "olli", Role.USER);
            addAccountToDatabaseOnce("lukas@haw-hamburg.de", "lukas", Role.USER);
            addAccountToDatabaseOnce("tim@haw-hamburg.de", "tim", Role.USER);
            addAccountToDatabaseOnce("jana@haw-hamburg.de", "jana", Role.USER);
            addAccountToDatabaseOnce("dani@haw-hamburg.de", "dani", Role.USER);

            addProfileToDatabaseOnce("sara@haw-hamburg.de", new Profile(UUID.randomUUID(),"Sara", "Müller", LocalDate.parse("2001-01-01"), "FEMALE", "Media Systems", 3, "Hallo ich bin Sara", "logo.png", new String[] {"logo.png"}));
            addProfileToDatabaseOnce("lara@haw-hamburg.de", new Profile("Lara", "Meyer", LocalDate.parse("2000-01-01"), "FEMALE", "Kommunikationsdesign", 5, "Hallo ich bin Lara"));
            addProfileToDatabaseOnce("tamara@haw-hamburg.de", new Profile("Tamara", "Schmidt", LocalDate.parse("2002-03-03"), "FEMALE", "Media Systems", 6, "Hallo ich bin Tamara"));
            addProfileToDatabaseOnce("olli@haw-hamburg.de", new Profile("Olli", "Schmidt", LocalDate.parse("2003-03-03"), "MALE", "Angewandte Informatik", 2, "Hallo ich bin Olli"));
            addProfileToDatabaseOnce("lukas@haw-hamburg.de", new Profile("Lukas", "Schmidt", LocalDate.parse("2002-03-03"), "MALE", "Media Systems", 1, "Hallo ich bin Lukas"));
            addProfileToDatabaseOnce("tim@haw-hamburg.de", new Profile("Tim", "Schmidt", LocalDate.parse("2002-03-03"), "MALE", "Media Systems", 3, "Hallo ich bin Tim"));
            addProfileToDatabaseOnce("jana@haw-hamburg.de", new Profile("Jana", "Schmidt", LocalDate.parse("2001-03-03"), "FEMALE", "Media Systems", 4, "Hallo ich bin Jana"));
            addProfileToDatabaseOnce("dani@haw-hamburg.de", new Profile("Dani", "Schmidt", LocalDate.parse("2001-03-03"), "DIVERSE", "Media Systems", 4, "Hallo ich bin Dani"));

            profileRepository.addImage("sara@haw-hamburg.de", "51921234-b982-4c3c-9938-a976241c8c2cScreenshot 2024-05-07 at 18.53.51.png");
            Optional<Profile> laraProfile = profileRepository.findByEmail("lara@haw-hamburg.de");
            Optional<Profile> saraProfile = profileRepository.findByEmail("sara@haw-hamburg.de");

            profileRepository.rate("lara@haw-hamburg.de", saraProfile.get().getId(), true);
            profileRepository.rate("sara@haw-hamburg.de", laraProfile.get().getId(), true);

            chatRepository.postMessage("lara@haw-hamburg.de", saraProfile.get().getId(), "Hallo ich bin Lara!", LocalDateTime.now().minus(10, ChronoUnit.MINUTES));
            chatRepository.postMessage("sara@haw-hamburg.de", laraProfile.get().getId(), "Hallo!", LocalDateTime.now().minus(2, ChronoUnit.MINUTES));
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
