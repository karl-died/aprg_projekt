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
        Account a = accountRepository.findByEmail(account.getEmail());
        if (a != null) {
            throw new IllegalArgumentException("Email " + account.getEmail() + " is already in use");
        }


        accountRepository.save(new Account(account));
    }

    public boolean authenticate(AccountDTO accountDTO) {
        Account a = accountRepository.findByEmail(accountDTO.getEmail());
        if(a == null) {
            return false;
        }
        if(a.getPassword().equals(accountDTO.getPassword())) {
            return true;
        }
        return false;
    }

}
