package com.example.aprg_projekt.security;

import com.example.aprg_projekt.models.Account;
import com.example.aprg_projekt.models.Profile;
import com.example.aprg_projekt.models.Role;
import com.example.aprg_projekt.repositories.AccountRepository;
import com.example.aprg_projekt.repositories.ChatRepository;
import com.example.aprg_projekt.repositories.ProfileRepository;
import com.example.aprg_projekt.services.ProfileService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;

@Configuration
class DefaultUserConfig {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;
    private final ChatRepository chatRepository;

    private ResourceLoader resourceLoader;

    private static final Path UPLOAD_DIRECTORY = Paths.get("uploads/");
    private static final Path STATIC_DIRECTORY = Paths.get("classpath:static/");

    DefaultUserConfig(AccountRepository accountRepository,
                      PasswordEncoder passwordEncoder,
                      ProfileRepository profileRepository,
                      ChatRepository chatRepository,
                      ResourceLoader resourceLoader) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.profileRepository = profileRepository;
        this.chatRepository = chatRepository;
        this.resourceLoader = resourceLoader;
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
    CommandLineRunner onApplicationStart(ProfileService profileService) {
        return _ -> {
            Path filePath = UPLOAD_DIRECTORY.resolve("");
            try {
                FileUtils.deleteDirectory(new File(filePath.toString()));
            } catch (IOException e) {}

            if (!Files.exists(UPLOAD_DIRECTORY)) {
                Files.createDirectories(UPLOAD_DIRECTORY);
            }

            ResourcePatternResolver resourcePatResolver = new PathMatchingResourcePatternResolver();
            Resource[] directory = resourcePatResolver.getResources("classpath:static/assets/defaultImages/*");
            for(Resource resource : directory) {
                Path picPath = UPLOAD_DIRECTORY.resolve(resource.getFilename());
                Files.copy(resource.getInputStream(), picPath);
            }

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

            addProfileToDatabaseOnce("sara@haw-hamburg.de", new Profile(UUID.randomUUID(),"Sara", "Müller", LocalDate.parse("2001-01-01"), "FEMALE", "Media Systems", 3, "Hallo ich bin Sara", "default_pfp_1.png", new String[] {"default_pic_1.jpeg"}));
            addProfileToDatabaseOnce("lara@haw-hamburg.de", new Profile(UUID.randomUUID(),"Lara", "Meyer", LocalDate.parse("2000-01-01"), "FEMALE", "Kommunikationsdesign", 5, "Hallo ich bin Lara", "default_pfp_2.png", new String[] {"default_pic_2.jpeg"}));
            addProfileToDatabaseOnce("tamara@haw-hamburg.de", new Profile(UUID.randomUUID(),"Tamara", "Schmidt", LocalDate.parse("2002-03-03"), "FEMALE", "Media Systems", 6, "Hallo ich bin Tamara", "default_pfp_3.png", new String[] {"default_pic_3.jpeg"}));
            addProfileToDatabaseOnce("olli@haw-hamburg.de", new Profile(UUID.randomUUID(),"Olli", "Schmidt", LocalDate.parse("2003-03-03"), "MALE", "Angewandte Informatik", 2, "Hallo ich bin Olli", "default_pfp_7.png", new String[] {"default_pic_7.jpeg"}));
            addProfileToDatabaseOnce("lukas@haw-hamburg.de", new Profile(UUID.randomUUID(),"Lukas", "Schmidt", LocalDate.parse("2002-03-03"), "MALE", "Media Systems", 1, "Hallo ich bin Lukas", "default_pfp_4.png", new String[] {"default_pic_4.jpeg"}));
            addProfileToDatabaseOnce("tim@haw-hamburg.de", new Profile(UUID.randomUUID(),"Tim", "Schmidt", LocalDate.parse("2002-03-03"), "MALE", "Media Systems", 3, "Hallo ich bin Tim", "default_pfp_8.png", new String[] {"default_pic_8.jpeg"}));
            addProfileToDatabaseOnce("jana@haw-hamburg.de", new Profile(UUID.randomUUID(),"Jana", "Schmidt", LocalDate.parse("2001-03-03"), "FEMALE", "Media Systems", 4, "Hallo ich bin Jana", "default_pfp_5.png", new String[] {"default_pic_5.jpeg"}));
            addProfileToDatabaseOnce("dani@haw-hamburg.de", new Profile(UUID.randomUUID(),"Dani", "Schmidt", LocalDate.parse("2001-03-03"), "DIVERSE", "Media Systems", 4, "Hallo ich bin Dani", "default_pfp_6.png", new String[] {"default_pic_6.jpeg"}));


            Optional<Profile> laraProfile = profileRepository.findByEmail("lara@haw-hamburg.de");
            Optional<Profile> saraProfile = profileRepository.findByEmail("sara@haw-hamburg.de");

            profileRepository.rate("lara@haw-hamburg.de", saraProfile.get().getId(), true);
            profileRepository.rate("sara@haw-hamburg.de", laraProfile.get().getId(), true);

            profileService.addGenderInterest("sara@haw-hamburg.de", Arrays.asList(new String[]{"MALE", "FEMALE"}));

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
        if(profile.getImageNames().length > 0) {
            profileRepository.addImage(email, profile.getImageNames()[0]);
        }
    }
}
