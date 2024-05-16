package com.example.aprg_projekt.repositories;

import com.example.aprg_projekt.models.Account;
import com.example.aprg_projekt.utils.AccountRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, UUID> {

    @Query("SELECT * FROM account WHERE email = :email")
    public Account findByEmail(String email);
}
