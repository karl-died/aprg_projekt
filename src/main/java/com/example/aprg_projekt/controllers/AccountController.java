package com.example.aprg_projekt.controllers;


import com.example.aprg_projekt.models.Account;
import com.example.aprg_projekt.models.AccountDTO;
import com.example.aprg_projekt.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/registration")
    public void register(@RequestBody AccountDTO account) {
        accountService.registerAccount(account);
    }

    @PostMapping("/login")
    public String login(@RequestBody AccountDTO account) {
        return accountService.authenticate(account)? "successful" : "unsuccessful";
    }
}
