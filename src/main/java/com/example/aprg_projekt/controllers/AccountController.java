package com.example.aprg_projekt.controllers;


import com.example.aprg_projekt.models.Account;
import com.example.aprg_projekt.models.AccountDTO;
import com.example.aprg_projekt.services.AccountService;
import com.example.aprg_projekt.utils.Redirect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public String register(Model model, AccountDTO account) {
        System.out.println("Account: " + account.toString());
        accountService.registerAccount(account);
        return Redirect.to("/");
    }
}
