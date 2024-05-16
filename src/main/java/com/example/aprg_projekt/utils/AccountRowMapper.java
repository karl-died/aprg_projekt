package com.example.aprg_projekt.utils;

import com.example.aprg_projekt.models.Account;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

public class AccountRowMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        Account account = new Account();

        account.setId(UUID.fromString(rs.getString("id")));
        account.setEmail(rs.getString("email"));
        account.setPassword(rs.getString("password"));
        return account;
    }
}
