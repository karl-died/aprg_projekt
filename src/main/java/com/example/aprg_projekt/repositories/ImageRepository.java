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
import java.util.Optional;
import java.util.UUID;

@Repository
public class ImageRepository {


    private JdbcTemplate jdbcTemplate;

    public ImageRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void removeImages(String email) {
        String sql = "DELETE FROM image WHERE profileId = (SELECT account_profile.profileId FROM account_profile WHERE account_profile.email = ?)";

        jdbcTemplate.update(sql, new Object[]{email});
    }

    public Optional<Profile> getOneNonRatedProfile(UUID accountId) {
        String query= """
            SELECT *
            FROM profile_image
            WHERE NOT EXISTS (
                SELECT * 
                FROM r_rating 
                WHERE r_rating.accountId = ? AND r_rating.ratedId = profile_image.accountId
            )
            AND profile_image.accountId <> ?
            AND profile_image.gender IN (
                SELECT gender.name
                FROM r_interested_in
                JOIN gender ON gender.id = r_interested_in.genderId
                WHERE r_interested_in.accountId = ? 
            )       
            LIMIT 1;
        """;
        List<Profile> profile = jdbcTemplate.query(query, new Object[]{accountId, accountId, accountId}, new ProfileRowMapper());
        if(profile.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(profile.getFirst());
        }
    }

}
