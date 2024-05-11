package com.example.aprg_projekt.repositories;

import com.example.aprg_projekt.models.Account;
import com.example.aprg_projekt.utils.AccountRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public class AccountRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Account account) {
        String query = "INSERT INTO account (email, password, firstName, lastName, dateOfBirth, gender) VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(query,
                account.getEmail(),
                account.getPassword(),
                account.getFirstName(),
                account.getLastName(),
                account.getDateOfBirth(),
                account.getGender());
    }

    public Account findByEmail(String email) {
        String query = "SELECT * FROM account WHERE email = ?";
        Account result = jdbcTemplate.queryForObject(query, new Object[]{email}, new AccountRowMapper() {});
        return result;
    }

    public Account findById(UUID id) {
        String query = "SELECT * FROM account WHERE id = ?";
        Account result = jdbcTemplate.queryForObject(query, new Object[]{id}, new AccountRowMapper());
        return result;
    }

    public boolean emailTaken(String email) {
        String query = "SELECT * FROM account WHERE email = ?";
        List<Account> result = jdbcTemplate.query(query, new Object[]{email}, new AccountRowMapper() {});

        return !result.isEmpty();
    }
}
