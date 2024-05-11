package com.example.aprg_projekt.services;

import com.example.aprg_projekt.models.Account;
import com.example.aprg_projekt.models.AccountDTO;
import com.example.aprg_projekt.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void registerAccount(AccountDTO account) {
        boolean a = accountRepository.emailTaken(account.getEmail());
        if (a) {
            throw new IllegalArgumentException("Email " + account.getEmail() + " is already in use");
        }


        accountRepository.save(new Account(account));
    }

}
