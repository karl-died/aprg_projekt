package com.example.aprg_projekt.repositories;

import com.example.aprg_projekt.models.Profile;
import com.example.aprg_projekt.utils.ProfileRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProfileRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(String email, Profile profile) {
        UUID profileID = UUID.randomUUID();
        String query = "BEGIN;" +
                "INSERT INTO profile VALUES (?, ?, ?, ?);" +
                "UPDATE account SET profileId = ? WHERE email = ?;" +
                "COMMIT;";

        jdbcTemplate.update(query,
                profileID,
                profile.getDegreeCourse(),
                profile.getAboutMe(),
                profile.getSemester(),
                profileID,
                email);
    }

    public Profile findByEmail(String email) {
        List<UUID> profileId = jdbcTemplate.query("SELECT profileId FROM account WHERE email = ?",
                new Object[] {email},
                new RowMapper<UUID>() {
            @Override
            public UUID mapRow(ResultSet resultSet, int i) throws SQLException {
                return UUID.fromString(resultSet.getString("profileId"));
            }
        });

        if(profileId.isEmpty()) {
            throw new NoSuchElementException("Account with email " + email + " not found");
        }
        if(profileId.size() > 1) {
            throw new IllegalStateException("More than one account found for email " + email);
        }
        List<Profile> profiles = jdbcTemplate.query("SELECT * FROM profile WHERE id = ?",
                new Object[] {profileId.getFirst()},
                new ProfileRowMapper());

        if(profiles.isEmpty()) {
            throw new NoSuchElementException("Profile attached to the account with email " + email);
        }
        if(profiles.size() > 1) {
            throw new IllegalStateException("More than one profile attached to the account with email " + email);
        }
        return profiles.getFirst();
    }

    public Profile findById(UUID id) {
        List<Profile> profile = jdbcTemplate.query("SELECT * FROM profile WHERE id = ?",
                new Object[]{id},
                new ProfileRowMapper());
        if(profile.isEmpty()) {
            throw new NoSuchElementException("Profile with id " + id + " not found");
        }
        return profile.getFirst();
    }
}
