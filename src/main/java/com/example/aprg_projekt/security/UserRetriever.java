package com.example.aprg_projekt.security;

import com.example.aprg_projekt.repositories.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class UserRetriever implements UserDetailsService {

    private final AccountRepository accountRepository;

    UserRetriever(AccountRepository userRepository) {
        this.accountRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return accountRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find user account: " + email));
    }
}
