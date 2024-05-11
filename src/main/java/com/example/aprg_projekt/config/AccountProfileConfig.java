package com.example.aprg_projekt.config;

import com.example.aprg_projekt.models.Account;
import com.example.aprg_projekt.models.Profile;
import com.example.aprg_projekt.repositories.AccountRepository;
import com.example.aprg_projekt.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;


import javax.sql.DataSource;
import java.lang.annotation.Annotation;
import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;

@Configuration
public class AccountProfileConfig {


    JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Bean
    CommandLineRunner init(AccountRepository accountRepository,
                           ProfileRepository profileRepository) {



        return args -> {
            jdbcTemplate.execute("DROP TABLE IF EXISTS account");
            jdbcTemplate.execute("DROP TABLE IF EXISTS profile");
            jdbcTemplate.execute("CREATE TABLE profile (" +
                    "id uuid DEFAULT gen_random_uuid() PRIMARY KEY, " +
                    "degreeCourse varchar(50) NOT NULL, " +
                    "aboutMe text NOT NULL, " +
                    "semester integer NOT NULL)");
            jdbcTemplate.execute("CREATE TABLE account (" +
                            "id uuid DEFAULT gen_random_uuid() PRIMARY KEY, " +
                            "email varchar(50) NOT NULL UNIQUE, " +
                            "password varchar(50) NOT NULL, " +
                            "firstName varchar(50) NOT NULL, " +
                            "lastName varchar(50) NOT NULL, " +
                            "dateOfBirth timestamp, " +
                            "gender varchar(20), " +
                            "profileId uuid references profile(id) UNIQUE)");

            UUID profileID = UUID.randomUUID();
            jdbcTemplate.execute("INSERT INTO profile (id, degreeCourse, aboutMe, semester) VALUES ('" + profileID + "', 'Media Systems', 'Hey, its me!', 2)");
            jdbcTemplate.execute("INSERT INTO account (email, password, firstName, lastName, dateOfBirth, gender, profileId) VALUES ('ernie.example@example.com', 'test123', 'Ernie', 'Example', '2001-01-01', 'male', '" + profileID + "')");



            Account account = new Account(
                    "dave.default@example.com",
                    "password",
                    "Dave",
                    "Default",
                    LocalDate.of(2002,2,2),
                    "male"
            );
            Profile profile = new Profile(
                    "Informatik",
                    1,
                    "Hey, im Dave"
            );
            accountRepository.save(account);
            profileRepository.save(account.getEmail(), profile);

        };
    }
}
