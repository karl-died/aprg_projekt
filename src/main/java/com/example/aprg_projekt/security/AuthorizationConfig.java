package com.example.aprg_projekt.security;

import com.example.aprg_projekt.models.Role;
import com.example.aprg_projekt.repositories.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Manages the authorization for all endpoints of this web application.
 */
@Configuration
@EnableWebSecurity
class AuthorizationConfig {

    @Bean
    SecurityFilterChain filters(HttpSecurity http) throws Exception {

        http.formLogin(Customizer.withDefaults()); // Use default login screen from Spring

        http.logout(logout -> logout.logoutSuccessUrl("/")); // Show landing page after successful logout


        http.authorizeHttpRequests(authz -> authz
                .requestMatchers("/admin").hasAuthority(Role.ADMIN) // This endpoint is only available for users with the ROLE_ADMIN.
                .requestMatchers("/welcome").authenticated() // This endpoint is available for any logged-in user (regardless of the role).
                .requestMatchers("/styles.css").permitAll()
                .requestMatchers("/register").anonymous()// This is not an endpoint but access to other resources must be set as well. You may also use the * for multiple files, e.g., *.css or /img/*.*
                .requestMatchers("/error").permitAll()
                .requestMatchers("/").permitAll() // Make landing page publicly accessible
                .requestMatchers("/profiles/**").authenticated()
                .requestMatchers("/rate").authenticated()
                .anyRequest().authenticated() // Secure any other page (aka blacklist)
        );

        return http.build();
    }

    /**
     * Tell Spring what {@link PasswordEncoder} to use to encrypt passwords.
     *
     * @see <a href="https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/index.html"></a>
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Tells Spring to use our custom {@link UserRetriever} that
     * retrieves a user from the database during the login process.
     * Spring requires a {@link UserDetailsService} to find stored users.
     */
    @Bean
    UserDetailsService userDetailsService(AccountRepository userRepository) {
        return new UserRetriever(userRepository);
    }
}
