package com.example.aprg_projekt.repositories;

import com.example.aprg_projekt.models.Profile;
import com.example.aprg_projekt.utils.ProfileRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, UUID> {

    @Query("""
        SELECT *
        FROM profile_image
        WHERE accountId = (SELECT account.id FROM account WHERE account.email = :email)
    """)
    Optional<Profile> findByEmail(String email);

    @Query("""
        SELECT *
        FROM profile_image
        WHERE accountId = (SELECT id FROM account WHERE id = :accountId)
    """)
    Optional<Profile> findByAcountId(UUID accountId);

    @Modifying
    @Query("""
        INSERT INTO profile (
             firstName, 
             lastName, 
             dateOfBirth,
             gender,
             degreeCourse,
             semester,
             aboutMe,
             accountId,
             profilePictureName
        ) VALUES (
             :firstName,
             :lastName, 
             :dateOfBirth,
             :gender,
             :degreeCourse,
             :semester,
             :aboutMe,
             (SELECT id FROM account WHERE email = :email),
             :profilePictureName
        );
""")
    void save(String email,
              String firstName,
              String lastName,
              LocalDate dateOfBirth,
              String gender,
              String degreeCourse,
              Integer semester,
              String aboutMe,
              String profilePictureName);

    @Modifying
    @Query("""
        UPDATE profile 
        SET  firstName = :firstName, 
             lastName = :lastName, 
             dateOfBirth = :dateOfBirth,
             gender = :gender,
             degreeCourse = :degreeCourse,
             semester = :semester,
             aboutMe = :aboutMe,
             profilePictureName = :profilePictureName
        WHERE accountId = (SELECT id FROM account WHERE email = :email);
""")
    void update(String email,
                String firstName,
                String lastName,
                LocalDate dateOfBirth,
                String gender,
                String degreeCourse,
                Integer semester,
                String aboutMe,
                String profilePictureName);

    @Modifying
    @Query("""
        INSERT INTO image (name, profileId)
        VALUES (:name, (SELECT account_profile.profileId FROM account_profile WHERE account_profile.email = :email));
    """)
    void addImage(String email, String name);

    @Modifying
    @Query("""
        DELETE FROM image 
        WHERE name = :name;
    """)
    void removeImage(String name);

    @Query("""
        SELECT *
        FROM profile_image
        WHERE NOT EXISTS (
            SELECT * 
            FROM r_rating 
            WHERE r_rating.accountId = :accountId AND r_rating.ratedId = profile_image.accountId)
        AND profile_image.accountId <> :accountId;
    """)
    List<Profile> getNonRatedProfiles(UUID accountId);

    @Query("""
        SELECT *
        FROM profile_image
        WHERE NOT EXISTS (
            SELECT * 
            FROM r_rating 
            WHERE r_rating.accountId = :accountId AND r_rating.ratedId = profile_image.accountId
        )
        AND profile_image.accountId <> :accountId
        LIMIT 1;
    """)
    Optional<Profile> getOneNonRatedProfile(UUID accountId);

    @Query("""
        SELECT *
        FROM profile
        WHERE EXISTS (
            SELECT * 
            FROM r_rating 
            WHERE r_rating.accountId = :accountId 
            AND r_rating.ratedId = profile.accountId
            AND r_rating.isLike);
    """)
    List<Profile> getLikedProfiles(UUID accountId);




    @Modifying
    @Query("""
        INSERT INTO r_rating VALUES (
            (SELECT id FROM account WHERE email = :email), 
            (SELECT accountId FROM account_profile WHERE profileId = :ratedProfileId), 
            :isLike
        );
    """)
    void rate(String email, UUID ratedProfileId, boolean isLike);
}

