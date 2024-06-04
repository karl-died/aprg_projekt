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
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Manages the authorization for all endpoints of this web application.
 */
@Configuration
@EnableWebSecurity
class AuthorizationConfig {

    @Bean
    SecurityFilterChain filters(HttpSecurity http) throws Exception {
        var loginEndpoint = "/login";
        http.formLogin(form -> form // Customized login page.
                .loginPage(loginEndpoint)
                // This failure handler inserts an error-flag parameter into the
                // login-url when a user login attempt has failed. This is needed to show an
                // error message in the custom login form.
                .failureHandler(new SimpleUrlAuthenticationFailureHandler(loginEndpoint + "?error=true"))

                .permitAll());

        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
        ); // Show landing page after successful logout


        http.authorizeHttpRequests(authz -> authz
                .requestMatchers("/admin").hasAuthority(Role.ADMIN) // This endpoint is only available for users with the ROLE_ADMIN.
                .requestMatchers("/styles.css").permitAll()
                .requestMatchers("/static/**").anonymous()
                .requestMatchers("/images/**").permitAll()
                .requestMatchers("/register").anonymous()
                .requestMatchers("/error").permitAll()
                .requestMatchers("/login").anonymous()
                .requestMatchers("/logout").authenticated()
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
