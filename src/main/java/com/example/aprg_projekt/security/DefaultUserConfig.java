package com.example.aprg_projekt.security;

import com.example.aprg_projekt.models.Account;
import com.example.aprg_projekt.models.Role;
import com.example.aprg_projekt.repositories.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class DefaultUserConfig {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    DefaultUserConfig(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * CommandLineRunner is executed once when the application is started and the
     * Spring context has been loaded.
     * <p>
     * !! Only for demo purposes and should not be used in real environments !!
     *
     * @see <a href="https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/index.html"></a>
     */
    @Bean
    CommandLineRunner onApplicationStart() {
        return _ -> {
            addAccountToDatabaseOnce("user@haw-hamburg.de", "user", Role.USER);
            addAccountToDatabaseOnce("admin@haw-hamburg.de", "admin", Role.USER, Role.ADMIN);
        };
    }

    private void addUserToDatabaseOnce(String email, String password, String... authorities) {
        if (doesNotExistsInDatabase(email)) {
            addAccountToDatabaseOnce(email, password, authorities);
        }
    }

    private boolean doesNotExistsInDatabase(String email) {
        return accountRepository.findByEmail(email).isEmpty();
    }

    private void addAccountToDatabaseOnce(String email, String password, String... authorities) {
        var newDefaultUser = new Account(email, passwordEncoder.encode(password), authorities); // raw passwords must be encoded!
        accountRepository.save(newDefaultUser);
    }
}
