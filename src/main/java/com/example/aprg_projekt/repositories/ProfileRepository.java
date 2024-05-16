package com.example.aprg_projekt.repositories;

import com.example.aprg_projekt.models.Profile;
import com.example.aprg_projekt.utils.ProfileRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
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
public interface ProfileRepository extends CrudRepository<Profile, UUID> {

    @Query("""
        SELECT *
        FROM profile
        WHERE id = (SELECT id FROM account WHERE email = :email)
    """)
    Profile findByEmail(String email);

    @Query("""
        SELECT *
        FROM profile
        WHERE id = (SELECT id FROM account WHERE id = :accountId)
    """)
    Profile findByAcountId(UUID accountId);

    @Query("SELECT * FROM profile")
    List<Profile> getAll();
}
