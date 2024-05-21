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

    public void registerAccount(AccountDTO accountDTO) {
        Optional<Account> a = accountRepository.findByEmail(accountDTO.getEmail());
        if (!a.isPresent()) {
            throw new IllegalArgumentException("Email " + accountDTO.getEmail() + " is already in use");
        }

        accountRepository.save(new Account(accountDTO));
    }

    public boolean authenticate(AccountDTO accountDTO) {
        Optional<Account> a = accountRepository.findByEmail(accountDTO.getEmail());
        if(!a.isPresent()) {
            return false;
        }
        if(a.get().getPassword().equals(accountDTO.getPassword())) {
            return true;
        }
        return false;
    }

}
