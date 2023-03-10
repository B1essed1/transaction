package com.example.transaction_5.entities;


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Collection;
import java.util.UUID;


@Entity
@Getter
@Setter
public class Users implements UserDetails {

    @Id
    private String id = UUID.randomUUID().toString();
    private String phone;
    private String password;

    @Column(length = 10)
    private String status = "ACTIVE";


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.phone;
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
        if (this.status == "ACTIVE")
            return true;
        else
            return false;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
}
