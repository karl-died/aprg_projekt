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
        FROM profile
        WHERE accountId = (SELECT account.id FROM account WHERE account.email = :email)
    """)
    Optional<Profile> findByEmail(String email);

    @Query("""
        SELECT *
        FROM profile
        WHERE accountId = (SELECT id FROM account WHERE id = :accountId)
    """)
    Optional<Profile> findByAcountId(UUID accountId);

    @Query("SELECT * FROM profile")
    List<Profile> getAll();

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
             accountId
        ) VALUES (
             :firstName,
             :lastName, 
             :dateOfBirth,
             :gender,
             :degreeCourse,
             :semester,
             :aboutMe,
             (SELECT id FROM account WHERE email = :email) 
        );
""")
    void save(String email,
              String firstName,
              String lastName,
              LocalDate dateOfBirth,
              String gender,
              String degreeCourse,
              Integer semester,
              String aboutMe);


    @Query("""
        UPDATE profile 
        SET  firstName = :firstName, 
             lastName = :lastName, 
             dateOfBirth = :dateOfBirth,
             gender = :gender,
             degreeCourse = :degreeCourse,
             semester = :semester,
             aboutMe = :aboutMe
        WHERE profileId = (SELECT id FROM account WHERE email = :email);
""")
    void update(String email,
                String firstName,
                String lastName,
                LocalDate dateOfBirth,
                String gender,
                String degreeCourse,
                Integer semester,
                String aboutMe);

}

