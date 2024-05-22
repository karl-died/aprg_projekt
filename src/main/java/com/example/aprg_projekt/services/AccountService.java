package com.example.aprg_projekt.services;

import com.example.aprg_projekt.models.Account;
import com.example.aprg_projekt.models.AccountDTO;
import com.example.aprg_projekt.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;


    public AccountService(
            AccountRepository accountRepository,
            PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerAccount(AccountDTO accountDTO) {
        Optional<Account> a = accountRepository.findByEmail(accountDTO.getEmail());
        if (a.isPresent()) {
            throw new IllegalArgumentException("Email " + accountDTO.getEmail() + " is already in use");
        }

        Account account = new Account();
        account.setEmail(accountDTO.getEmail());
        account.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        accountRepository.save(account);
    }
}
