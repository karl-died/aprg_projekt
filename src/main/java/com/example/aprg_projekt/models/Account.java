package com.example.aprg_projekt.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;

@Table("ACCOUNT")
public class Account implements UserDetails, CredentialsContainer {

    @Id
    private UUID id;
    private String email;
    private String password;
    private Profile profile;
    private Set<Authority> authorities = new HashSet<>();

    public Account() {
    }

    public Account(UUID id, String email, String password, Profile profile) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.profile = profile;
    }

    public Account(AccountDTO accountDTO) {
        this.email = accountDTO.getEmail();
        this.password = accountDTO.getPassword();
    }

    public Account(String email, String password, Collection<String> authorities) {
        this.email = email;
        this.password = password;
        this.authorities.addAll(convert(authorities));
    }

    public Account(String email, String password, String... authorities) {
        this(email, password, Arrays.asList(authorities));
    }

    private static Collection<Authority> convert(Collection<String> strings) {
        return strings.stream().map(Authority::new).toList();
    }

    public boolean addAuthority(String authority) {
        return authorities.add(new Authority(authority));
    }

    @Override
    public void eraseCredentials() {
        password = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return convertToGrantedAuthorities(authorities);
    }

    private static List<SimpleGrantedAuthority> convertToGrantedAuthorities(Collection<Authority> authorities) {
        var result = new ArrayList<SimpleGrantedAuthority>();
        for (var authority : authorities) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority.role());
            result.add(simpleGrantedAuthority);
        }
        return result;
    }

    public UUID getId() {
        return id;
    }


    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", profile=" + profile +
                '}';
    }
}